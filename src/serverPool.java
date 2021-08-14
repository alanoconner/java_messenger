import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class serverPool implements Runnable {

     ServerSocket serverSocket;
     ServerThread serverThread;
     ArrayList<ClientThread> clientConnects;
     int port;


    public static void main(String[] args) {
        new serverPool(3435);
    }

     public serverPool(int port){
        this.port = port;
     }

     public ArrayList<ClientThread> getClients(){
        return clientConnects ;
     }


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            clientConnects = new ArrayList<>();
            Thread serverthread = new Thread(serverThread);
            serverthread.start();

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("connection created");
                ClientThread clientThread = new ClientThread(this,socket);
                Thread thread = new Thread(clientThread);
                thread.start();

                clientConnects.add(clientThread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
