import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private serverPool server;
    ClientUI clientUI;
    //
    BufferedReader datain;
    BufferedWriter dataout;

    public ClientThread(serverPool server, Socket socket){
        this.server = server;
        this.socket = socket;
    }



    @Override
    public void run() {
        try{
            // setup
            datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientUI = new ClientUI();



            // start communicating
            while(!socket.isClosed()){
                String input = clientUI.getText();
                if(input!=null){
                    for(ClientThread client : server.getClients()) {

                        if (client != null) {
                            dataout.write(input + "\r\n");
                            dataout.flush();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}