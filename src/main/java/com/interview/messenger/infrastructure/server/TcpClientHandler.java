package com.interview.messenger.infrastructure.server;

import com.interview.messenger.application.port.ChatParticipant;
import com.interview.messenger.application.port.MessageFormatter;
import com.interview.messenger.application.service.ChatRoom;
import com.interview.messenger.domain.model.ChatMessage;
import com.interview.messenger.infrastructure.network.Protocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

/**
 * Handles the lifecycle of a single TCP client connection.
 */
public class TcpClientHandler implements Runnable, ChatParticipant {

    private final Socket socket;
    private final ChatRoom chatRoom;
    private final MessageFormatter formatter;

    private volatile boolean running = true;
    private PrintWriter writer;
    private String displayName = "Unknown";

    public TcpClientHandler(Socket socket, ChatRoom chatRoom, MessageFormatter formatter) {
        this.socket = Objects.requireNonNull(socket, "socket");
        this.chatRoom = Objects.requireNonNull(chatRoom, "chatRoom");
        this.formatter = Objects.requireNonNull(formatter, "formatter");
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            this.writer = writer;
            handleHandshake(reader);
            chatRoom.registerParticipant(this);
            listenForMessages(reader);
        } catch (IOException e) {
            running = false;
        } finally {
            chatRoom.removeParticipant(this);
            closeQuietly();
        }
    }

    private void handleHandshake(BufferedReader reader) throws IOException {
        String hello = reader.readLine();
        if (hello == null || !hello.startsWith(Protocol.HELLO_PREFIX)) {
            throw new IOException("Client did not send HELLO message");
        }
        String encoded = hello.substring(Protocol.HELLO_PREFIX.length());
        String candidate = Protocol.decodePayload(encoded).trim();
        if (candidate.isEmpty()) {
            throw new IOException("Client did not provide a valid name");
        }
        this.displayName = candidate;
    }

    private void listenForMessages(BufferedReader reader) throws IOException {
        while (running && !socket.isClosed()) {
            String line = reader.readLine();
            if (line == null) {
                break;
            }
            if (line.startsWith(Protocol.MESSAGE_PREFIX)) {
                String encoded = line.substring(Protocol.MESSAGE_PREFIX.length());
                String messageText = Protocol.decodePayload(encoded);
                chatRoom.broadcastUserMessage(this, messageText);
            }
        }
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public void deliver(ChatMessage message) {
        if (writer != null) {
            synchronized (this) {
                writer.println(formatter.format(message));
            }
        }
    }

    @Override
    public void onDisconnect() {
        running = false;
    }

    private void closeQuietly() {
        try {
            socket.close();
        } catch (IOException ignored) {
        }
    }
}
