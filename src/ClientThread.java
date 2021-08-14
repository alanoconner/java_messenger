import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientThread implements Runnable {
    private Socket socket;
    private PrintWriter clientOut;
    private serverPool server;
    //
    BufferedReader datain;
    BufferedWriter dataout;

    public ClientThread(serverPool server, Socket socket){
        this.server = server;
        this.socket = socket;
    }

    private PrintWriter getWriter(){
        return clientOut;
    }

    @Override
    public void run() {
        try{
            // setup
            datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner in = new Scanner(socket.getInputStream());


            // start communicating
            while(!socket.isClosed()){
                String input = in.nextLine();

                for(ClientThread client : server.getClients()) {
                    PrintWriter thatClientOut = client.getWriter();
                    if (thatClientOut != null) {
                        thatClientOut.write(input + "\r\n");
                        thatClientOut.flush();
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}