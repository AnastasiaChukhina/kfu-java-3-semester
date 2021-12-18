package ru.kpfu.listeners;

import ru.kpfu.server.Server;

import java.io.IOException;

public interface ServerEventListener {
    void init(Server server);
    void handle(String data) throws IOException;
}
