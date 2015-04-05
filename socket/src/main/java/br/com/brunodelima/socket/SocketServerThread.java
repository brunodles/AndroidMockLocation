package br.com.brunodelima.socket;

import java.io.IOException;

/**
 *
 * @author Bruno de Lima
 */
class SocketServerThread extends SimpleThread {

    private SocketBase client;
    private final Protocol protocol;

    public SocketServerThread(SocketBase client, Protocol protocol) {
        this.client = client;
        this.protocol = protocol;
    }

    @Override
    protected void onLoop() {
        try {
            protocol.processMessage(client.getSocket().getInputStream(), client.getSocket().getOutputStream());
        } catch (Exception ex) {
            protocol.onError(ex);
        }
    }

    @Override
    public void interrupt() {
        try {
            client.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.interrupt();
    }
}
