

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;

public class ServerThread implements Runnable {
    private Socket socket;
    private String userName;
    private boolean isAlive;
    private final LinkedList<String> msgToSend;
    private boolean isAnyMessage = false;

    public ServerThread(Socket socket, String userName){
        this.socket = socket;
        this.userName = userName;
        msgToSend = new LinkedList<String>();
    }

    public void addNextMessage(String message){
        synchronized (msgToSend){
            isAnyMessage = true;
            msgToSend.push(message);
        }
    }

    @Override
    public void run(){
        System.out.println("Client initialized new connection:" + userName);
        System.out.println("Local Port :" + socket.getLocalPort());
        System.out.println("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort());

        clientConnect.setUIText("Client initialized new connection:" + userName + "\n");
        clientConnect.setUIText("Local Port :" + socket.getLocalPort() + "\n");
        clientConnect.setUIText("Server = " + socket.getRemoteSocketAddress() + ":" + socket.getPort() + "\n");


        try{
            PrintWriter serverOut = new PrintWriter(socket.getOutputStream(), false);
            InputStream serverInStream = socket.getInputStream();
            Scanner serverIn = new Scanner(serverInStream);




            while(!socket.isClosed()){
                if(serverInStream.available() > 0){
                    if(serverIn.hasNextLine()){
                        String msg = serverIn.nextLine();
                        //System.out.println(msg);
                        clientConnect.setUIText(msg+"\n");
                    }
                }
                if(isAnyMessage){
                    String nextSend = "";
                    synchronized(msgToSend){
                        nextSend = msgToSend.pop();
                        isAnyMessage = !msgToSend.isEmpty();
                    }
                    serverOut.println(userName + " > " + nextSend);
                    //clientConnect.setUIText(userName + " > " + nextSend+ "\n");////////////////
                    serverOut.flush();
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}