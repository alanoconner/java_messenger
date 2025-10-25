package com.interview.messenger.client.ui;

import com.interview.messenger.domain.model.ChatMessage;

import javax.swing.*;
import java.awt.*;

/**
 * Swing based UI implementation for the chat client.
 */
public class SwingChatView implements ChatView {

    private final JFrame frame;
    private final JTextArea messageArea;
    private final JTextField inputField;
    private MessageHandler handler;

    public SwingChatView(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);

        inputField = new JTextField();
        inputField.addActionListener(e -> {
            if (handler != null) {
                String text = inputField.getText().trim();
                if (!text.isEmpty()) {
                    handler.onMessageTyped(text);
                }
                inputField.setText("");
            }
        });

        frame.add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.add(inputField, BorderLayout.SOUTH);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);
    }

    public void showWindow() {
        SwingUtilities.invokeLater(() -> frame.setVisible(true));
    }

    @Override
    public void showMessage(ChatMessage message) {
        SwingUtilities.invokeLater(() -> {
            messageArea.append(message.toDisplayString());
            messageArea.append(System.lineSeparator());
            messageArea.setCaretPosition(messageArea.getDocument().getLength());
        });
    }

    @Override
    public void showInfo(String message) {
        appendPlainText("[INFO] " + message);
    }

    @Override
    public void showError(String message) {
        appendPlainText("[ERROR] " + message);
    }

    private void appendPlainText(String text) {
        SwingUtilities.invokeLater(() -> {
            messageArea.append(text);
            messageArea.append(System.lineSeparator());
            messageArea.setCaretPosition(messageArea.getDocument().getLength());
        });
    }

    @Override
    public void setMessageHandler(MessageHandler handler) {
        this.handler = handler;
    }
}
