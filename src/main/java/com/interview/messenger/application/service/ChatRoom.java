package com.interview.messenger.application.service;

import com.interview.messenger.application.port.ChatParticipant;
import com.interview.messenger.domain.model.ChatMessage;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Core use case of the application. Coordinates the registration of
 * participants and ensures that messages are delivered to everybody in the
 * room.
 */
public class ChatRoom {

    private final Set<ChatParticipant> participants = ConcurrentHashMap.newKeySet();

    public void registerParticipant(ChatParticipant participant) {
        Objects.requireNonNull(participant, "participant");
        if (participants.add(participant)) {
            broadcastSystemMessage(participant.getDisplayName() + " joined the chat.");
        }
    }

    public void removeParticipant(ChatParticipant participant) {
        if (participant == null) {
            return;
        }
        boolean removed = participants.remove(participant);
        participant.onDisconnect();
        if (removed) {
            broadcastSystemMessage(participant.getDisplayName() + " left the chat.");
        }
    }

    public void broadcastUserMessage(ChatParticipant sender, String messageText) {
        Objects.requireNonNull(sender, "sender");
        Objects.requireNonNull(messageText, "messageText");
        ChatMessage message = ChatMessage.userMessage(sender.getDisplayName(), messageText);
        broadcast(message);
    }

    public void broadcastSystemMessage(String messageText) {
        ChatMessage message = ChatMessage.systemMessage(messageText);
        broadcast(message);
    }

    private void broadcast(ChatMessage message) {
        for (ChatParticipant participant : participants) {
            participant.deliver(message);
        }
    }
}
