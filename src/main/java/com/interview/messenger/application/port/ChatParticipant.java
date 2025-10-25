package com.interview.messenger.application.port;

import com.interview.messenger.domain.model.ChatMessage;

/**
 * Represents a participant in the chat room. Implementations are responsible
 * for delivering messages to the underlying transport or UI.
 */
public interface ChatParticipant {

    String getDisplayName();

    void deliver(ChatMessage message);

    void onDisconnect();
}
