import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ServerThread implements Runnable {
    private Socket socket;

    BufferedReader datain;
    BufferedWriter dataout;
    ClientUI clientUI;

    @Override
    public void run(){

        System.out.println("Local Port :" + socket.getLocalPort());

        try{
            datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientUI = new ClientUI();

            while(!socket.isClosed()){
                if (datain.readLine()!=null){
                    String income_msg = datain.readLine();
                    dataout.write(income_msg);
                }


            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
