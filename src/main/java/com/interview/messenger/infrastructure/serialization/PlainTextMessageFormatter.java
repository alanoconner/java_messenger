package com.interview.messenger.infrastructure.serialization;

import com.interview.messenger.application.port.MessageFormatter;
import com.interview.messenger.domain.model.ChatMessage;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;

/**
 * Simple text based message formatter. The payload uses the following format:
 * <pre>
 *     TYPE|TIMESTAMP|SENDER|MESSAGE
 * </pre>
 * All textual fields are base64 encoded to ensure transport safety.
 */
public class PlainTextMessageFormatter implements MessageFormatter {

    private static final String DELIMITER = "|";

    @Override
    public String format(ChatMessage message) {
        Objects.requireNonNull(message, "message");
        String sender = encode(message.getSender());
        String body = encode(message.getText());
        return String.join(DELIMITER,
                message.getType().name(),
                Long.toString(message.getTimestamp().toEpochMilli()),
                sender,
                body);
    }

    @Override
    public ChatMessage parse(String payload) {
        Objects.requireNonNull(payload, "payload");
        String[] parts = payload.split("\\|", 4);
        if (parts.length < 4) {
            throw new IllegalArgumentException("Payload does not contain all expected fields: " + payload);
        }
        ChatMessage.Type type = ChatMessage.Type.valueOf(parts[0]);
        Instant timestamp = Instant.ofEpochMilli(Long.parseLong(parts[1]));
        String sender = decode(parts[2]);
        String text = decode(parts[3]);
        return ChatMessage.of(sender, text, timestamp, type);
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
    }
}
