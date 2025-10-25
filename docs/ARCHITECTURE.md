# Architecture Overview

The project follows a lightweight Clean Architecture structure to keep the
business rules independent from infrastructure details.

```
src/main/java
├── com.interview.messenger.domain        // Enterprise business rules
│   └── model                              // Entities (ChatMessage)
├── com.interview.messenger.application   // Application business rules
│   ├── port                               // Interfaces used by the domain
│   └── service                            // Use cases (ChatRoom)
├── com.interview.messenger.infrastructure // Framework & drivers
│   ├── network                            // TCP protocol helpers
│   ├── serialization                      // Message formatting
│   └── server                             // TCP server implementation
└── com.interview.messenger.client         // Interface adapters
    ├── app                                // Controllers orchestrating UI & networking
    ├── ui                                 // Swing based UI implementation
    └── TcpChatClient                      // Network client
```

## Flow

1. **Domain** defines the `ChatMessage` entity independent of any UI or
   transport.
2. **Application** exposes the `ChatRoom` use case and the
   `ChatParticipant`/`MessageFormatter` ports. It depends only on the domain.
3. **Infrastructure** implements those ports using TCP sockets and provides
   the server bootstrap code.
4. **Interface Adapters** (the client package) translate user input into
   use-case invocations and display the results.
5. **App Layer** (`com.interview.messenger.app`) contains the entry points that
   wire everything together.

This separation ensures that the business logic can be reused with different
UI technologies or transport mechanisms with minimal effort.

## Network Protocol

- Clients initiate the connection with a `HELLO|<base64-username>` message.
- User messages use the format `MESSAGE|<base64-body>`.
- The server broadcasts messages in the format
  `TYPE|TIMESTAMP|SENDER|MESSAGE`, where textual values are base64 encoded and
  `TYPE` is either `USER` or `SYSTEM`.

The protocol is intentionally simple and human readable, making it easy to
extend or replace with alternative transports during an interview discussion.
