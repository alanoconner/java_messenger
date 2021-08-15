

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class clientConnect {

     public static String host;
     public static int portNumber =0;

    private String userName;
    private String serverHost;
    private int serverPort;
    private Scanner userInputScanner;
    static ClientUI clientUI = new ClientUI();


    public static void main(String[] args){
        String name = null;
        Scanner scan = new Scanner(System.in);
        //System.out.println("Please input username:");
        clientUI.setText("Please input username, port and host (e.g. Mike 0000 127.0.0.1):" + "\n");
        String nameporthost = "";
        while(name == null && nameporthost.length()<4){
            System.out.println(getUIText());////I don't know why, but without this line it never break the cycle
            //name = getUIText();
            //if(name.trim().equals("")){
            //    System.out.println("Invalid. Please enter again:");
            //    clientUI.setText("Invalid. Please enter again:");
            //    name = null;
            //}
            if(getUIText()!=null){
                nameporthost=getUIText();
            }
            if(nameporthost.length()>7){
                String[] data = nameporthost.split(" ");
                name = data[0];
                portNumber = Integer.parseInt(data[1]);
                host = data[2];

            }
        }

        clientConnect clientConnect = new clientConnect(name, host, portNumber);
        clientConnect.startClient(scan);
    }

    private clientConnect(String userName, String host, int portNumber){
        this.userName = userName;
        this.serverHost = host;
        this.serverPort = portNumber;
    }

    private void startClient(Scanner scan){
        try{
            Socket socket = new Socket(serverHost, serverPort);
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