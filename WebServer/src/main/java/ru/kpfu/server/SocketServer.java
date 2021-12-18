package ru.kpfu.server;

import ru.kpfu.exceptions.ServerException;
import ru.kpfu.listeners.ServerEventListener;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SocketServer implements Server {
    private final String DELIMITER = "\r\n";
    private int port;
    private boolean started;
    private ServerSocket server;
    private Socket socket;
    private ServerEventListener listener;

    public SocketServer(int port) {
        this.port = port;
        this.started = false;
    }

    @Override
    public void registerListener(ServerEventListener listener) {
        if (started)
            throw new ServerException("Server has been started already.");
        listener.init(this);
        this.listener = listener;
    }

    @Override
    public void start() {
        try {
            server = new ServerSocket(this.port);
            started = true;
            while (true) {
                socket = server.accept();
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                String s = br.readLine();
                listener.handle(s);
            }
        } catch (IOException ex) {
            throw new ServerException("Problem with starting server.", ex);
        }
    }

    @Override
    public void sendResponse(String response) throws IOException {
        if (!started) {
            throw new ServerException("Server hasn't been started yet.");
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
        bw.write(createResponse(response, 200, "OK"));
        bw.flush();
    }

    private String createResponse(String content, int code, String status){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %d %s%s", "HTTP/1.1", code, status, DELIMITER));
        sb.append("Content-type:text/plain;Charset:UTF-8").append(DELIMITER);
        sb.append(String.format("%s%d%s%s", "Content-length:", content.length(), DELIMITER, DELIMITER));
        sb.append(String.format("%s%s", content, DELIMITER));
        return sb.toString();
    }
}
