package br.com.brunodelima.mocklocation;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Scanner;

import br.com.brunodelima.socket.Broadcast;
import br.com.brunodelima.socket.SocketClient;

/**
 * Created by bruno on 06/01/15.
 */
public class StartDirectClient {

    private String ip = "192.168.25.168";
    private int port = 30123;

    public static void main(String[] args) {
        System.out.println("Initializing");
        StartDirectClient startDirectClient = new StartDirectClient();
        try {
            System.out.println("Try to find devices");
            startDirectClient.findServer();
        } catch (Exception e) {
        }
        startDirectClient.console();
    }

    public void console() {
        System.out.println("Write a location or a GoogleMaps URL.\n"
                + "Samples:\n"
                + " Location(latitude, longitude)\n"
                + "    \"-14.2400732,-53.1805017\"\n"
                + " GoogleMaps URL\n"
                + "    \"https://www.google.com.br/maps/place/Brasil/@-14.2400732,-53.1805017,4z\"\n");
        Scanner scanner = new Scanner(System.in);
        String response = null;
        String input;
        do {
            try {
                System.out.print(">");
                input = scanner.nextLine();
                response = sendMessage(input);
                System.out.println(response);
            } catch (IOException ex) {
                ex.printStackTrace(System.err);
            }
        } while (!"QUIT".equalsIgnoreCase(response));
    }

    private String sendMessage(String message) throws IOException {
        SocketClient client = new SocketClient(ip, 30123);
        client.sendMessage(message);
        String response = client.reciveMessge();
        client.getSocket().close();
        return response;

    }

    private void findServer() throws IOException {
        List<InetAddress> serverAdresses = Broadcast.getServerAdresses("locations", "230.0.0.1", 30122, 5000);
        if (!serverAdresses.isEmpty()) {
            ip = serverAdresses.get(0).getHostAddress();
            System.out.println("Device found at " + ip);
        } else {
            System.out.println("Device not found.");
        }
    }
}
