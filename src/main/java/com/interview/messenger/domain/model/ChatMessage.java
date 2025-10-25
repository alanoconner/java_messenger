package com.interview.messenger.domain.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Immutable representation of a message exchanged in the chat room.
 */
public final class ChatMessage {

    public enum Type {
        USER,
        SYSTEM
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    private final String sender;
    private final String text;
    private final Instant timestamp;
    private final Type type;

    private ChatMessage(String sender, String text, Instant timestamp, Type type) {
        this.sender = Objects.requireNonNull(sender, "sender");
        this.text = Objects.requireNonNull(text, "text");
        this.timestamp = Objects.requireNonNull(timestamp, "timestamp");
        this.type = Objects.requireNonNull(type, "type");
    }

    public static ChatMessage userMessage(String sender, String text) {
        return new ChatMessage(sender, text, Instant.now(), Type.USER);
    }

    public static ChatMessage systemMessage(String text) {
        return new ChatMessage("System", text, Instant.now(), Type.SYSTEM);
    }

    public static ChatMessage of(String sender, String text, Instant timestamp, Type type) {
        return new ChatMessage(sender, text, timestamp, type);
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Type getType() {
        return type;
    }

    /**
     * Produces a human friendly representation that includes the timestamp.
     */
    public String toDisplayString() {
        String prefix = type == Type.SYSTEM ? "[SYSTEM]" : sender;
        return String.format("[%s] %s: %s", FORMATTER.format(timestamp), prefix, text);
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", timestamp=" + timestamp +
                ", type=" + type +
                '}';
    }
}
