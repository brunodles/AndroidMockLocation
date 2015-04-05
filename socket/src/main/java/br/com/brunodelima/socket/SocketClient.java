package br.com.brunodelima.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Bruno de Lima
 */
public class SocketClient extends SocketBase {

    private String host;
    private int port;

    public SocketClient(String host, int port) throws UnknownHostException, IOException {
        super(new Socket(host, port));
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
