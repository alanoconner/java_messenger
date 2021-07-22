import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class serverPool {
     InputStream in;
     OutputStream out;
     BufferedReader datain;
     BufferedWriter dataout;
     ServerSocket serverSocket;
     Socket socket;
     ArrayList<clientConnect> clientConnects;
     Thread sendAndReceiveThread;


    public static void main(String[] args) {
        new serverPool(3435);
    }

     public serverPool(int port){

         try {
             serverSocket = new ServerSocket(port);
             clientConnects = new ArrayList<>();
             socket = serverSocket.accept();

             in = socket.getInputStream();
             out = socket.getOutputStream();
             datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             dataout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             sendAndReceiveThread = new Thread(new Runnable() {
                 @Override
                 public void run() {
                    while(true){
                        onReceive();
                        onSend();
                        try {
                            if(datain.readLine().equals("/serverstop")){
                                dataout.write("Client initialized server suicide");
                                datain.close();
                                dataout.close();
                                in.close();
                                out.close();
                                socket.close();
                                sendAndReceiveThread.interrupt();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                 }
             });


         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    public void  onReceive(){
        try {
            dataout.write(datain.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void onSend(){
        try {
            dataout.write(datain.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
