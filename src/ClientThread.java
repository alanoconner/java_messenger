

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter dataOut;
    private serverPool serverpool;
    public String inputedMessage;

    public ClientThread(serverPool serverpool, Socket socket){
        this.serverpool = serverpool;
        this.socket = socket;
    }

    public ClientThread(){

    }

    private PrintWriter getWriter(){
        return dataOut;
    }

    @Override
    public void run() {
        try{
            // setup
            this.dataOut = new PrintWriter(socket.getOutputStream(), false);
            Scanner in = new Scanner(socket.getInputStream());

            // communication
            while(!socket.isClosed()){
                if(in.hasNextLine()){

                    String input = in.nextLine();

                    for(ClientThread thatClient : serverpool.getClients()){
                        PrintWriter toClient = thatClient.getWriter();
                        if(toClient != null&&input!=null){
                            toClient.write(input + "\r\n");
                            toClient.flush();
                        }
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void scannerSetText(String msg){

    }
}