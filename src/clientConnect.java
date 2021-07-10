import java.io.*;
import java.net.Socket;


public class clientConnect {

    InputStream in;
    OutputStream out;
    DataInputStream datain;
    DataOutputStream dataout;
    Socket socket;



    public static void main(String[] args) {
        new clientConnect(3435);
    }

    public clientConnect(int port){
        try {
            socket = new Socket("127.0.0.1", port);

            out = socket.getOutputStream();
            in = socket.getInputStream();
            dataout = new DataOutputStream(out);
            datain = new DataInputStream(in);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
