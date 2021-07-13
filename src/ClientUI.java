import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.net.Socket;

import static javax.swing.text.DefaultCaret.ALWAYS_UPDATE;

public class ClientUI implements ActionListener {

    JFrame jFrame;
    JTextArea msg_log;     // all chat field
    JTextField msg_enter;  // typing field
    DefaultCaret caret;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientUI();
            }
        });
    }

    private ClientUI(){
        jFrame = new JFrame("Client UI");
        msg_enter = new JTextField();
        msg_log = new JTextArea();



        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setAlwaysOnTop(true);
        jFrame.setVisible(true);
        caret = (DefaultCaret)msg_log.getCaret();
        caret.setUpdatePolicy(ALWAYS_UPDATE);




        jFrame.add(msg_enter, BorderLayout.SOUTH);
        jFrame.add(msg_log, BorderLayout.CENTER);
        msg_log.setEditable(false);


        //////////////////////////////////////////////////////
        ////////////////////////////////////////////////////// initializing connection
       // new clientConnect(3435);

        msg_enter.addActionListener(this);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = msg_enter.getText();
        msg_enter.setText("");
        if(!msg.equals("")){
            msg_log.append("  " +msg+"\n" );
        }


    }


}
