# Java Messenger

A polished client/server chat application implemented in Java and organised
according to Clean Architecture and SOLID principles. The project exposes a
TCP based server and a Swing desktop client that demonstrates the separation of
concerns between domain logic, application use-cases, and infrastructure code.

## Features

- Multi-client TCP chat server with broadcast messaging
- Swing-based client UI that renders rich message metadata
- Lightweight text protocol with base64 encoded payloads
- Clear layering with interchangeable infrastructure components
- Thorough inline documentation and architecture notes for interviews

## Project Layout

See [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) for a detailed overview of the
package responsibilities and data flow.

```
src/main/java
├── com.interview.messenger.app            # Application entry points
├── com.interview.messenger.application    # Use cases and ports
├── com.interview.messenger.client         # UI + client controller
├── com.interview.messenger.domain         # Core domain model
└── com.interview.messenger.infrastructure # TCP protocol & server
```

## Building

This project uses the standard directory layout so it can be built with any
JDK (11 or newer). The following commands assume the repository root as the
working directory.

```bash
# Compile all sources
javac -d out $(find src/main/java -name "*.java")

# Run the server on the default port (4444)
java -cp out com.interview.messenger.app.ServerApplication

# Run the client (requires a running server)
java -cp out com.interview.messenger.app.ClientApplication 127.0.0.1 4444 Alice
```

> 💡 Run the server in one terminal and start multiple clients (each with a
> unique username) in separate terminals to see message broadcasting in action.

## Contributing

Improvements and alternative front-ends (CLI, web, mobile) can be added by
implementing the existing ports or by introducing adapters that map to the
`ChatRoom` use-case.
