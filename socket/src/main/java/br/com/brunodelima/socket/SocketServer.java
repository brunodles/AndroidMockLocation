package br.com.brunodelima.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Bruno de Lima
 */
public class SocketServer extends SimpleThread {

    private int port;
    private ServerSocket serverSocket = null;
    private List<SocketServerThread> clientes = new ArrayList<SocketServerThread>();
    private final Protocol protocol;

    public SocketServer(int port, Protocol protocol) throws IOException {
        this.port = port;
        this.protocol = protocol;
        serverSocket = new ServerSocket(port);
    }

    @Override
    protected void finalize() throws Throwable {
        this.interrupt();
        try {
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        super.finalize();
    }

    @Override
    protected void onLoop() {
        try {
            SocketServerThread client = new SocketServerThread(new SocketBase(serverSocket.accept()), protocol);
            clientes.add(client);
            client.start();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @Override
    public void interrupt() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
        for (SocketServerThread cliente : clientes) {
            cliente.interrupt();
        }
        super.interrupt();
    }
}
