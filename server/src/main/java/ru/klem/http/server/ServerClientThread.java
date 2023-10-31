package ru.klem.http.server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;
public class ServerClientThread extends Thread{
    private static final Logger logger = LogManager.getLogger(ServerClientThread.class.getName());
    private Socket socket;
    private Request request;
    private MyWebApplication myWebApplication;
    int connectNumber;

    ServerClientThread(Socket inSocket, int counter, MyWebApplication myWebApplication, Request request) {
        this.socket = inSocket;
        this.connectNumber = counter;
        this.myWebApplication = myWebApplication;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            myWebApplication.execute(request, socket.getOutputStream());
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            try {
                socket.close();
                logger.debug("Соединение номер: " + connectNumber + "  closed");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
