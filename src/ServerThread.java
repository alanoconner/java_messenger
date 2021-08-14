import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class ServerThread implements Runnable {
    private Socket socket;

    private final LinkedList<String> messagesToSend;
    private boolean hasMessages = false;

    InputStream in;
    OutputStream out;
    BufferedReader datain;
    BufferedWriter dataout;

    public ServerThread(Socket socket){
        this.socket = socket;
        messagesToSend = new LinkedList<String>();
    }

    public void addNextMessage(String message){
        synchronized (messagesToSend){
            hasMessages = true;
            messagesToSend.push(message);
        }
    }

    @Override
    public void run(){


        System.out.println("Local Port :" + socket.getLocalPort());


        try{
            in = socket.getInputStream();
            out = socket.getOutputStream();
            datain = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            dataout = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            while(!socket.isClosed()){

                if(hasMessages){
                    String nextSend = "";
                    synchronized(messagesToSend){
                        nextSend = messagesToSend.pop();
                        hasMessages = !messagesToSend.isEmpty();
                    }
                    dataout.write(" > " + nextSend);
                    dataout.flush();
                }
            }
        }
        catch(IOException ex){
            ex.printStackTrace();
        }

    }
}
