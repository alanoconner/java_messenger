import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class serverPool {
     InputStream in;
     OutputStream out;
     DataInputStream datain;
     DataOutputStream dataout;
     ServerSocket serverSocket;
     Socket socket;

    public static void main(String[] args) {
        new serverPool(3435);
    }

     public serverPool(int port){
         try {
             serverSocket = new ServerSocket(port);
             socket = serverSocket.accept();

             in = socket.getInputStream();
             out = socket.getOutputStream();

             datain = new DataInputStream(in);
             dataout = new DataOutputStream(out);




         } catch (IOException e) {
             e.printStackTrace();
         }
     }
}
