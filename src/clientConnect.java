import java.io.*;
import java.net.Socket;


public class clientConnect {

    InputStream in;
    OutputStream out;
    BufferedReader datain;
    BufferedWriter dataout;
    Socket socket;

    public static void main(String[] args) {
        new clientConnect(3435);
    }


    public clientConnect(int port){
        try {
            socket = new Socket("127.0.0.1", port);

            out = socket.getOutputStream();
            in = socket.getInputStream();
            datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            dataout.write("Client initialized new connection");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String onReceivemsg(){
        try {
            return datain.readLine();
        } catch (IOException e) {
            return "message type is no appropriate";
        }
    }
    public void onSendmsg(String msg){
        try {
            dataout.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
