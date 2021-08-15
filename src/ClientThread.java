

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private serverPool server;
    public String inputedMessage;

    public ClientThread(serverPool server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    public ClientThread(){

    }

    private PrintWriter getWriter(){
        return clientOut;
    }

    @Override
    public void run() {
        try{
            // setup
            this.clientOut = new PrintWriter(socket.getOutputStream(), false);
            Scanner in = new Scanner(socket.getInputStream());

            // start communicating
            while(!socket.isClosed()){
                if(in.hasNextLine()){

                    String input = in.nextLine();
                    //String inputUI = inputedMessage;


                    for(ClientThread thatClient : server.getClients()){
                        PrintWriter thatClientOut = thatClient.getWriter();
                        if(thatClientOut != null&&input!=null){
                            thatClientOut.write(input + "\r\n");
                            thatClientOut.flush();
                            //inputUI = null;
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scannerSetText(String msg){
        inputedMessage = msg;
    }
}