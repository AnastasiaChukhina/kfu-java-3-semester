package ru.kpfu.server;

import ru.kpfu.listeners.ServerEventListener;

import java.io.IOException;

public interface Server {
    void registerListener(ServerEventListener listener);
    void start();
    void sendResponse(String response) throws IOException;
}
