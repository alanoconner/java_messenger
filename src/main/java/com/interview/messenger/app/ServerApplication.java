package com.interview.messenger.app;

import com.interview.messenger.application.service.ChatRoom;
import com.interview.messenger.infrastructure.server.TcpChatServer;

/**
 * Entry point for launching the chat server.
 */
public final class ServerApplication {

    private ServerApplication() {
    }

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 4444;
        ChatRoom chatRoom = new ChatRoom();
        try (TcpChatServer server = new TcpChatServer(port, chatRoom)) {
            System.out.println("Chat server starting on port " + port);
            server.start();
            System.out.println("Press Ctrl+C to stop the server.");
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
