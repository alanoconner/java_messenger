package com.interview.messenger.infrastructure.server;

import com.interview.messenger.application.port.MessageFormatter;
import com.interview.messenger.application.service.ChatRoom;
import com.interview.messenger.infrastructure.serialization.PlainTextMessageFormatter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TCP server that accepts connections and delegates handling to
 * {@link TcpClientHandler} instances. The server is responsible for the
 * lifecycle of the underlying executor.
 */
public class TcpChatServer implements AutoCloseable {

    private final int port;
    private final ChatRoom chatRoom;
    private final MessageFormatter formatter;
    private final ExecutorService executor;
    private final AtomicBoolean running = new AtomicBoolean(false);
    private ServerSocket serverSocket;
    private Future<?> acceptTask;

    public TcpChatServer(int port, ChatRoom chatRoom) {
        this(port, chatRoom, new PlainTextMessageFormatter(),
                Executors.newCachedThreadPool());
    }

    public TcpChatServer(int port,
                         ChatRoom chatRoom,
                         MessageFormatter formatter,
                         ExecutorService executor) {
        this.port = port;
        this.chatRoom = Objects.requireNonNull(chatRoom, "chatRoom");
        this.formatter = Objects.requireNonNull(formatter, "formatter");
        this.executor = Objects.requireNonNull(executor, "executor");
    }

    public void start() throws IOException {
        if (running.compareAndSet(false, true)) {
            serverSocket = new ServerSocket(port);
            acceptTask = executor.submit(this::acceptLoop);
        }
    }

    private void acceptLoop() {
        try {
            while (running.get()) {
                Socket clientSocket = serverSocket.accept();
                TcpClientHandler handler = new TcpClientHandler(clientSocket, chatRoom, formatter);
                executor.submit(handler);
            }
        } catch (IOException e) {
            if (running.get()) {
                throw new IllegalStateException("Server accept loop terminated unexpectedly", e);
            }
        }
    }

    @Override
    public void close() throws IOException {
        running.set(false);
        if (serverSocket != null) {
            serverSocket.close();
        }
        if (acceptTask != null) {
            acceptTask.cancel(true);
        }
        executor.shutdownNow();
    }
}
