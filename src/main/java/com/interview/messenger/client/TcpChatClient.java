package com.interview.messenger.client;

import com.interview.messenger.application.port.MessageFormatter;
import com.interview.messenger.domain.model.ChatMessage;
import com.interview.messenger.infrastructure.network.Protocol;
import com.interview.messenger.infrastructure.serialization.PlainTextMessageFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * TCP client responsible for connecting to the chat server and notifying the
 * registered observer about incoming messages.
 */
public class TcpChatClient implements AutoCloseable {

    private final MessageFormatter formatter;
    private final ExecutorService listenerExecutor;

    private Socket socket;
    private PrintWriter writer;
    private ChatClientObserver observer;
    private volatile boolean running;

    public TcpChatClient() {
        this(new PlainTextMessageFormatter());
    }

    public TcpChatClient(MessageFormatter formatter) {
        this.formatter = Objects.requireNonNull(formatter, "formatter");
        this.listenerExecutor = Executors.newSingleThreadExecutor();
    }

    public void connect(String host, int port, String username, ChatClientObserver observer) throws IOException {
        Objects.requireNonNull(host, "host");
        Objects.requireNonNull(username, "username");
        this.observer = Objects.requireNonNull(observer, "observer");
        this.socket = new Socket(host, port);
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        this.running = true;
        sendHello(username);
        listenForMessages();
    }

    private void sendHello(String username) {
        writer.println(Protocol.HELLO_PREFIX + Protocol.encodePayload(username));
    }

    private void listenForMessages() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        listenerExecutor.submit(() -> {
            try {
                String line;
                while (running && (line = reader.readLine()) != null) {
                    ChatMessage message = formatter.parse(line);
                    observer.onMessage(message);
                }
                if (running) {
                    observer.onDisconnected("Server closed the connection.");
                }
            } catch (Exception e) {
                if (running) {
                    observer.onDisconnected("Connection closed: " + e.getMessage());
                }
            } finally {
                closeQuietly();
            }
        });
    }

    public void sendMessage(String text) {
        if (writer != null) {
            writer.println(Protocol.MESSAGE_PREFIX + Protocol.encodePayload(text));
        }
    }

    @Override
    public void close() {
        running = false;
        closeQuietly();
        listenerExecutor.shutdownNow();
    }

    private void closeQuietly() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException ignored) {
        }
        writer = null;
    }
}
