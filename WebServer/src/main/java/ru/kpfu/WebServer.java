package ru.kpfu;

import ru.kpfu.exceptions.InvalidRequestException;
import ru.kpfu.exceptions.UnknownActionException;
import ru.kpfu.listeners.CalcListener;
import ru.kpfu.server.Server;
import ru.kpfu.server.SocketServer;

import java.io.IOException;

public class WebServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        Server server = new SocketServer(PORT);
        try {
            server.registerListener(new CalcListener());
            server.start();
        }catch (InvalidRequestException e){
            server.sendResponse("Invalid request. Request format is: /<action>?a=<number1>&b=<number2>");
        }catch (UnknownActionException e) {
            server.sendResponse("Unknown action. You can use: plus, subtract, multiply, divide.");
        } catch (NumberFormatException e){
            server.sendResponse("Parameters are not a numbers.");
        } catch (IllegalArgumentException e){
            server.sendResponse("Denominator can't be equal to zero.");
        }
    }
}
