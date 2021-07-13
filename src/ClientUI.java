import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClientUI implements ActionListener {

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

        //////////////////////////////////////////////////////
        ////////////////////////////////////////////////////// initializing connection
        new clientConnect(3435);

        msg_enter.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = msg_enter.getText();
        msg_enter.setText("");
        msg_log.append("  " +msg+"\n" );

    }
}
