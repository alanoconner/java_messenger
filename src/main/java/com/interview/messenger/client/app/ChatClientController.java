package com.interview.messenger.client.app;

import com.interview.messenger.client.ChatClientObserver;
import com.interview.messenger.client.TcpChatClient;
import com.interview.messenger.client.ui.ChatView;
import com.interview.messenger.domain.model.ChatMessage;

import java.io.IOException;

/**
 * Coordinates the UI and the TCP client.
 */
public class ChatClientController implements ChatClientObserver, ChatView.MessageHandler, AutoCloseable {

    private final TcpChatClient client;
    private final ChatView view;

    public ChatClientController(TcpChatClient client, ChatView view) {
        this.client = client;
        this.view = view;
        this.view.setMessageHandler(this);
    }

    public void connect(String host, int port, String username) throws IOException {
        view.showInfo("Connecting to " + host + ":" + port + " as " + username + "...");
        client.connect(host, port, username, this);
        view.showInfo("Connected.");
    }

    @Override
    public void onMessage(ChatMessage message) {
        view.showMessage(message);
    }

    @Override
    public void onDisconnected(String reason) {
        view.showError(reason);
        client.close();
    }

    @Override
    public void onMessageTyped(String message) {
        client.sendMessage(message);
    }

    @Override
    public void close() {
        client.close();
    }
}
