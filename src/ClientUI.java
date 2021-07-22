import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




public class ClientUI implements ActionListener {

    JFrame jFrame;
    JTextArea msg_log;     // all chat field
    JTextField msg_enter;  // typing field
    JScrollPane scrollPane;
    clientConnect clientConnect;


    int PORT = 3435;



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
        scrollPane = new JScrollPane(msg_log);



        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setSize(500, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setAlwaysOnTop(true);
        jFrame.setVisible(true);
        jFrame.setResizable(false);
        jFrame.add(scrollPane);


        jFrame.add(msg_enter, BorderLayout.SOUTH);
        jFrame.add(msg_log, BorderLayout.CENTER);
        msg_log.setEditable(false);
        msg_log.setLineWrap(true);


        msg_enter.addActionListener(this);


        clientConnect = new clientConnect(PORT);
        while(true){
            msg_print();
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = msg_enter.getText();
        if(msg.equals("")) return;
        clientConnect.onSendmsg(msg);
        msg_enter.setText(null);
    }
    synchronized void msg_print(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String msg = clientConnect.onReceivemsg();
                msg_log.append("  " + msg + "\n");
                msg_log.setCaretPosition(msg_log.getDocument().getLength());
            }
        });

    }




}
