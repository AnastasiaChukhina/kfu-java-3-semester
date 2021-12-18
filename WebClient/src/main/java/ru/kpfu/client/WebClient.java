package ru.kpfu.client;

import ru.kpfu.exceptions.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebClient {
    private final int PORT = 80;
    private final String HTTP_VERSION = "HTTP/1.1";
    private final String QUERY_SYMBOL = "?";
    private final String AMPERSAND = "&";
    private final String DELIMITER = "\r\n";
    private String host;
    private String method;
    private String path;
    private String body;
    private Map<String, String> params;
    private List<String> methods;
    private Map<String, String> headers;

    public WebClient(String host) {
        this.host = host;
        getMethods();
        getHeaders();
    }

    public String send() {
        Socket socket = openSocket();
        sendRequest(socket);
        return getResponse(socket);
    }

    public void setMethod(String method) {
        if (!methods.contains(method.toUpperCase()))
            throw new InvalidMethodException("Invalid method.");
        this.method = method.toUpperCase();
    }

    public void setPath(String path) {
        if (path != null) {
            if (!isAbsolute(path))
                path = getAbsolutePath(path);
            this.path = path;
        }
    }

    public void addParameter(String key, String value) {
        if (key != null && value != null) {
            if (params == null) params = new HashMap<>();
            params.put(key, value);
        }
    }

    public void addParameters(Map<String, String> params) {
        for (Map.Entry<String, String> param : params.entrySet()) {
            addParameter(param.getKey(), param.getValue());
        }
    }

    public void setBody(String body) {
        if(body != null) this.body = body;
    }

    private void sendRequest(Socket socket) {
        checkData();
        try {
            PrintWriter pr = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            getPath();
            pr.println(String.format("%s %s %s", method, path, HTTP_VERSION));
            for (Map.Entry<String, String> header : headers.entrySet()) {
                pr.println(header.getKey() + ":" + header.getValue());
            }
            pr.println();
            if (body != null) pr.println(body);
            pr.flush();
        } catch (IOException e) {
            throw new RequestSendingException("Can't send request.", e);
        }
    }

    private String getResponse(Socket socket) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null && !line.isEmpty()) {
                sb.append(line).append(DELIMITER);
            }
        } catch (IOException e) {
            throw new ResponseReadingException("Can't read response", e);
        }
        return sb.toString();
    }

    private void checkData() {
        if (path == null) path = "/";
        if (method == null)
            throw new UnknownMethodException("Method not found.");
    }

    private void getPath() {
        if (params != null) {
            path = path + QUERY_SYMBOL;
            for (Map.Entry<String, String> param : params.entrySet()) {
                path = String.format("%s%s=%s%s", path, param.getKey(), param.getValue(), AMPERSAND);
            }
        }
    }

    private String getAbsolutePath(String path) {
        return "/" + path;
    }

    private boolean isAbsolute(String path) {
        return path.startsWith("/");
    }

    private Socket openSocket() {
        try {
            return new Socket(host, PORT);
        } catch (IOException e) {
            throw new HostNotFoundException("Unknown host.", e);
        }
    }

    private void getHeaders() {
        headers = new HashMap<>();
        headers.put("HOST", host);
    }

    private void getMethods() {
        methods = new ArrayList<>();
        methods.add("GET");
        methods.add("POST");
    }
}
