import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class serverPool {

     ServerSocket serverSocket;
     Socket socket;
     ArrayList<ClientThread> clientConnects;
     Thread clientThread;


    public static void main(String[] args) {
        new serverPool(3435);
    }

     public serverPool(int port){

         try {
             serverSocket = new ServerSocket(port);
             clientConnects = new ArrayList<>();

             System.out.println("connection created");

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

     public ArrayList<ClientThread> getClients(){
        return clientConnects ;
     }


}
