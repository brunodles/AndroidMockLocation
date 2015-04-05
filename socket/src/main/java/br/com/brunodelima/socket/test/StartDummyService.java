package br.com.brunodelima.socket.test;

import java.io.IOException;

import br.com.brunodelima.socket.SocketServer;
import br.com.brunodelima.socket.StringProtocol;

/**
 * Created by bruno on 06/01/15.
 */
public class StartDummyService {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting");
        SocketServer socketServer = new SocketServer(30123, new LogMessagesProtocol());
        socketServer.start();
        System.out.println("Started");
    }

    private static final class LogMessagesProtocol extends StringProtocol {

        @Override
        public String processMessage(String message) {
            String response = String.format("Received message \"%s\"", message);
            System.out.println(response);
            return message;
        }

        @Override
        public boolean isBreakMessage(String message) {
            return "QUIT".equalsIgnoreCase(message);
        }

        @Override
        public void onError(Exception e) {
            e.printStackTrace(System.err);
        }


    }
}
