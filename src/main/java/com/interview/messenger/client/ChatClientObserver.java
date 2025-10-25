package com.interview.messenger.client;

import com.interview.messenger.domain.model.ChatMessage;

public interface ChatClientObserver {
    void onMessage(ChatMessage message);

    void onDisconnected(String reason);
}
