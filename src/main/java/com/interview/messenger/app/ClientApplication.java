package com.interview.messenger.app;

import com.interview.messenger.client.TcpChatClient;
import com.interview.messenger.client.app.ChatClientController;
import com.interview.messenger.client.ui.SwingChatView;

/**
 * Entry point for the Swing client.
 */
public final class ClientApplication {

    private ClientApplication() {
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: ClientApplication <host> <port> <username>");
            System.exit(1);
        }
        String host = args[0];
        int port = Integer.parseInt(args[1]);
        String username = args[2];

        SwingChatView view = new SwingChatView("Messenger - " + username);
        TcpChatClient client = new TcpChatClient();
        ChatClientController controller = new ChatClientController(client, view);
        view.showWindow();

        new Thread(() -> {
            try {
                controller.connect(host, port, username);
            } catch (Exception e) {
                view.showError("Unable to connect: " + e.getMessage());
                controller.close();
            }
        }, "chat-connection").start();
    }
}
