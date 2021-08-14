import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.BorderLayout.*;


public class ClientUI extends JFrame implements ActionListener  {

    JFrame frame;
    JPanel jPanel;
    JTextArea msg_log;     // all chat field
    JTextField msg_enter;  // typing field
    JScrollPane scrollPane;
    String msg;



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClientUI();
            }
        });
    }

    public ClientUI(){
        frame = new JFrame("Chat Window");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500,500);

        jPanel = new JPanel(new BorderLayout());
        msg_enter= new JTextField();

        msg_log = new JTextArea();
        msg_log.setEditable(false);
        msg_log.setLineWrap(true);

        jPanel.add(msg_log, CENTER);
        jPanel.add(msg_enter, SOUTH);


        scrollPane = new JScrollPane(msg_log);
        jPanel.add(scrollPane);

        msg_enter.addActionListener(this);

        frame.add(jPanel);
        frame.setVisible(true);

    }



    @Override
    public void actionPerformed(ActionEvent e) {
        //msg_log.append(msg_enter.getText()+"\n");
        msg= msg_enter.getText();
        msg_enter.setText(null);
        msg= null;
    }

    public String getText(){
        return msg;
    }

    public void setText (String msg){
        msg_log.append(msg);
    }


}




