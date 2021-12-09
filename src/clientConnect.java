

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class clientConnect {

     public static String host;
     public static int portNumber =0;

    private String userName;
    private String hostNumber;
    private int portnumber;
    private Scanner userInputScanner;
    static ClientUI clientUI = new ClientUI();


    public static void main(String[] args){
        String name = null;
        Scanner scan = new Scanner(System.in);

        clientUI.setText("Please input username, port and host (e.g. Mike 0000 127.0.0.1):" + "\n");
        String nameporthost = "";

        while(name == null && nameporthost.length()<4){

            System.out.flush();
            if(getUIText()!=null){
                nameporthost=getUIText();
                if(nameporthost.length()>7){
                    String[] data = nameporthost.split(" ");
                    name = data[0];
                    portNumber = Integer.parseInt(data[1]);
                    host = data[2];
                }
            }
        } // while

        clientConnect clientConnect = new clientConnect(name, host, portNumber);
        clientConnect.clientStart();
    }

    private clientConnect(String userName, String host, int portNumber){
        this.userName = userName;
        this.hostNumber = host;
        this.portnumber = portNumber;
    }

    private void clientStart(){
        try{
            Socket socket = new Socket(hostNumber, portnumber);
            Thread.sleep(1000);

            ServerThread serverThread = new ServerThread(socket, userName);
            Thread serverAccessThread = new Thread(serverThread);
            serverAccessThread.start();
            String notSpam = null;
            while(serverAccessThread.isAlive()){
                if(getUIText()!=null&&!getUIText().equals(notSpam)){
                    serverThread.addNextMessage(getUIText());
                    notSpam = getUIText();
                }
            }
        }catch(IOException ex){
            System.err.println("Unable to set connection, please try again");
            clientUI.setText("Unable to set connection, please try again");
            ex.printStackTrace();
        }catch(InterruptedException ex){
            System.out.println("Interrupted");
            clientUI.setText("Interrupted");
        }
    }

    public static void setUIText(String txt){
        clientUI.setText(txt);
    }
    public static String getUIText(){
        return clientUI.getText();
    }
}