import javax.swing.*;
import java.awt.*;

public class ClientUI {

    JFrame jFrame;
    JTextArea msg_log;     // all chat field
    JTextField msg_enter;  // typing field

    public static void main(String[] args) {
        new ClientUI();
    }

    private ClientUI(){
        jFrame = new JFrame("Client UI");
        msg_enter = new JTextField(15);
        msg_log = new JTextArea();


        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setAlwaysOnTop(true);
        jFrame.setVisible(true);

        jFrame.add(msg_enter, BorderLayout.SOUTH);
        jFrame.add(msg_log, BorderLayout.CENTER);
        msg_log.setEditable(false);


    }
}
