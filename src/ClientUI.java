import javax.swing.*;

public class ClientUI {

    JFrame jFrame;
    JTextArea msg_log;
    JTextField msg_enter;

    public static void main(String[] args) {
        new ClientUI();
    }

    private ClientUI(){
        jFrame = new JFrame("Client UI");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setAlwaysOnTop(true);
        jFrame.setVisible(true);


    }
}
