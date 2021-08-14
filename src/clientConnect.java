import java.io.*;
import java.net.Socket;


public class clientConnect {


    BufferedReader datain;
    BufferedWriter dataout;
    Socket socket;
    ClientUI clientUI;

    public static void main(String[] args) {
        new clientConnect(3435);
    }


    public clientConnect(int port){
        try {
            socket  =  new Socket("127.0.0.1", port);
            clientUI=  new ClientUI();
            datain  =  new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout =  new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            dataout.write("Client initialized new connection");


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
