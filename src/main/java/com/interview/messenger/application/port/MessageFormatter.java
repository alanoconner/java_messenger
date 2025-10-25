package com.interview.messenger.application.port;

import com.interview.messenger.domain.model.ChatMessage;

/**
 * Converts messages to/from a wire format.
 */
public interface MessageFormatter {

    String format(ChatMessage message);

    ChatMessage parse(String payload);
}
