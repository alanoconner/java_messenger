package com.interview.messenger.infrastructure.network;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Network protocol helpers shared by client and server.
 */
public final class Protocol {

    public static final String HELLO_PREFIX = "HELLO|";
    public static final String MESSAGE_PREFIX = "MESSAGE|";

    private Protocol() {
    }

    public static String encodePayload(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String decodePayload(String encoded) {
        return new String(Base64.getDecoder().decode(encoded), StandardCharsets.UTF_8);
    }
}
