package br.com.brunodelima.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @author Bruno de Lima
 */
class SocketBase {

    //    private static final String ENCODING = "UTF-8";
    private Socket socket;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public SocketBase(Socket socket) throws IOException {
        this.socket = socket;
//        out = new BufferedWriter(new OutputStreamWriter(com.brunolima.socket.getOutputStream(), ENCODING));
//        in = new BufferedReader(new InputStreamReader(com.brunolima.socket.getInputStream(), ENCODING));
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            out.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.finalize();
    }

    public String reciveMessge() throws IOException {
        final String line = in.readLine();
        return line;
    }

    public void sendMessage(String message) throws IOException {
        out.println(message);
//        out.write(message);
//        out.flush();
    }

    public BufferedReader getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    public Socket getSocket() {
        return socket;
    }
}
