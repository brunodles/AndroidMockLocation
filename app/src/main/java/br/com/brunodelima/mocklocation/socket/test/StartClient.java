package br.com.brunodelima.mocklocation.socket.test;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import br.com.brunodelima.mocklocation.socket.SocketClient;

/**
 * Created by bruno on 06/01/15.
 */
public class StartClient {
    private static final String KEY_HOST_IP = "KEY_HOST_IP";
    private static final String PROPERTIES_FILE_NAME = "client.properties";

    Scanner scanner = new Scanner(System.in);
    Properties properties = new Properties();
    SocketClient host;

    public StartClient() {
        try {
            properties.load(new FileInputStream(new File(PROPERTIES_FILE_NAME)));
        } catch (IOException e) {
            System.err.println("Can't read Properties.");
        }
    }

    private String requestIpToUser() throws IOException {
        String ip = properties.getProperty(KEY_HOST_IP, null);
        System.out.println("Type android ip.");
        if (ip != null)
            System.out.printf("(Last connected IP \"%s\"\n.Type \"r\" to use that.)\n", ip);

        String line = scanner.nextLine();
        if (!"r".equals(line))
            ip = line;

        System.out.printf("Connecting to %s\n", ip);
        host = new SocketClient(ip, 30123);
        System.out.println("Connected.\nJust paste wanted locations here.");
        properties.setProperty(KEY_HOST_IP, ip);

        saveProperties();
        return ip;
    }

    private void saveProperties() throws IOException {
        File file = new File(PROPERTIES_FILE_NAME);
        properties.store(new FileOutputStream(file), "");
    }

    private String tradeMessages(String out) throws IOException {
        host.sendMessage(out);
        return host.reciveMessge();
    }

    public void doLoop() throws IOException {
        String response;
        do {
            response = tradeMessages(scanner.nextLine());
            System.out.printf("Response: %s\n", response);
        } while (response != null || !"null".equalsIgnoreCase(response));
    }

    public static void main(String[] args) {
        StartClient client = new StartClient();
        while (true) {
            try {
                client.requestIpToUser();
                client.doLoop();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            System.out.println("Disconnected!");
        }
    }
}
