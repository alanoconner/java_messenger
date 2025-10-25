package com.interview.messenger.client.ui;

import com.interview.messenger.domain.model.ChatMessage;

public interface ChatView {
    void showMessage(ChatMessage message);

    void showInfo(String message);

    void showError(String message);

    void setMessageHandler(MessageHandler handler);

    interface MessageHandler {
        void onMessageTyped(String message);
    }
}
