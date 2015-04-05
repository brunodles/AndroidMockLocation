package br.com.brunodelima.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

// Talvez seja mais interessante deixar os servidores aguardando,
// a mensagem do cliente, e au receber uma mensagem responder

/**
 * Essa classe pode/deve ser usar para localizar ou criar um servidor broadcast.
 * <br/>* O Servidor ficará enviando mensagens de broadcast, a cada 5 segundos.
 * <br/>* O Cliente ficará aguardando receber a mensagem e através dela
 * identifica o ip do servidor.
 *
 * @author Bruno
 */
public final class Broadcast {

    private static Cliente getBroadCastClientThread(int port, String group, final String pass, int timeout) throws UnknownHostException, IOException {
        //        String result = "";
        MulticastSocket socket = new MulticastSocket(port);
        InetAddress address = InetAddress.getByName(group);
        socket.joinGroup(address);
        Cliente thread = new Cliente(pass, socket);
        thread.start();
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException ex) {
        }
        //        System.out.println("PreparaMudarC5anRun");
        thread.setCanRun(false);
        //        System.out.println("Mudou");
        socket.leaveGroup(address);
        socket.close();
        return thread;
    }

    private Broadcast() {
    }

    /**
     * Cria uma thread que ficará aguardando uma mensagem broadcast de um
     * cliente. <br/> A Thread não retorna iniciada, usar ".start";
     *
     * @param pass  - validação do serviço, caso deseje deixar vários serviços na
     *              mesma porta e grupo
     * @param group - ip de broadcast, normalmente é "230.0.0.1"
     * @param port  - porta do servidor
     * @return retona a thread, para iniciar-la > ".start();"
     */
    public static Server getNewServer(String pass, String group, int port) throws SocketException {
        return new Server(pass, group, port);
    }

    /**
     * Envia um pacote de broadcast, para um servidor. Aguarda por 5 segundo as
     * respostas dos servidores
     *
     * @param pass  - validação do serviço, caso deseje deixar vários serviços na
     *              mesma porta e grupo
     * @param group - ip de broadcast, normalmente é "230.0.0.1"
     * @param port  - porta do servidor
     * @return Retorna o ip do servidor
     */
    public static List<String> getServerIP(final String pass, String group, int port) throws UnknownHostException, IOException {
        return getServerIP(pass, group, port, 5);
    }

    /**
     * Envia um pacote de broadcast, para um servidor.
     *
     * @param pass    - validação do serviço, caso deseje deixar vários serviços na
     *                mesma porta e grupo
     * @param group   - ip de broadcast, normalmente é "230.0.0.1"
     * @param port    - porta do servidor
     * @param timeout - Tempo de espera em segundos
     * @return Retorna o ip do servidor
     */
    public static List<String> getServerIP(final String pass, String group, int port, int timeout) throws UnknownHostException, IOException {
        Cliente thread = getBroadCastClientThread(port, group, pass, timeout);
        return thread.getIps();
    }

    public static List<InetAddress> getServerAdresses(final String pass, String group, int port, int timeout) throws UnknownHostException, IOException {
        Cliente thread = getBroadCastClientThread(port, group, pass, timeout);
        return thread.getAdressList();
    }

    /**
     * Lista todos os ips de Broadcast possíveis
     *
     * @return
     */
    public static ArrayList<InetAddress> getBroadcastAddresses() {
        ArrayList<InetAddress> listOfBroadcasts = new ArrayList();
        Enumeration list;
        try {
            list = NetworkInterface.getNetworkInterfaces();

            while (list.hasMoreElements()) {
                NetworkInterface iface = (NetworkInterface) list.nextElement();

                if (iface == null) {
                    continue;
                }

                if (!iface.isLoopback() && iface.isUp()) {
//                    System.out.println("Found non-loopback, up interface:" + iface);

                    for (InterfaceAddress address : iface.getInterfaceAddresses()) {
                        if (address == null) {
                            continue;
                        }

                        InetAddress broadcast = address.getBroadcast();
                        if (broadcast != null) {
                            listOfBroadcasts.add(broadcast);
                        }
                    }

//                    Iterator it = iface.getInterfaceAddresses().iterator();
//                    while (it.hasNext()) {
//                        InterfaceAddress address = (InterfaceAddress) it.next();
//
////                        System.out.println("Found address: " + address);
//
//                        if (address == null) {
//                            continue;
//                        }
//                        InetAddress broadcast = address.getBroadcast();
//                        if (broadcast != null) {
//                            listOfBroadcasts.add(broadcast);
//                        }
//                    }
                }
            }
        } catch (SocketException ex) {
            return new ArrayList<InetAddress>();
        }

        return listOfBroadcasts;
    }

    private static class Cliente extends Thread {

        String pass;
        List<InetAddress> adressList;
        MulticastSocket socket;
        boolean canRun = true;

        public Cliente(String pass, MulticastSocket socket) {
            this.pass = pass;
            this.socket = socket;
            adressList = new ArrayList<InetAddress>();
        }

        public void setCanRun(boolean canRun) {
            this.canRun = canRun;
        }

        public List<String> getIps() {
            ArrayList<String> result = new ArrayList<String>();
            for (InetAddress inetAddress : adressList) {
                result.add(inetAddress.getHostAddress());
            }
            return result;
        }

        public List<InetAddress> getAdressList() {
            return adressList;
        }

        @Override
        public void run() {

            DatagramPacket packet;
            while (canRun) {
                try {
                    byte[] buf = new byte[256];
                    packet = new DatagramPacket(buf, buf.length);
//                    System.out.println("Wait..");
                    socket.receive(packet);
//                    System.out.println("received");

                    String received = new String(packet.getData(), 0, packet.getLength());
                    if (received.equals(pass)) {
                        final InetAddress address = packet.getAddress();
                        //                System.out.println(packet.getAddress().getHostName());
                        if (!adressList.contains(address)) {
                            adressList.add(address);
                        }
                        //            result = endereco;
                    }
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
     * Classe do servidor, Thread que aguarda as mensagens de broadcast
     */
    public static class Server extends Thread {

        protected DatagramSocket socket = null;
        protected boolean waitClients = true;
        private long waitTime = 5000;
        String pass;
        String group = "";
        int port;

        Server(String pass, String group, int port) throws SocketException {
            super();
            this.pass = pass;
            this.group = group;
            this.port = port;
            socket = new DatagramSocket(port);
        }

        public void stopWaiting() {
            waitClients = false;
        }

        @Override
        public void run() {
            while (waitClients) {
                try {
                    byte[] buf = pass.getBytes();

                    InetAddress groupIP = InetAddress.getByName(group);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, groupIP, port);
                    socket.send(packet);

                    try {
                        sleep((long) (Math.random() * waitTime));
                    } catch (InterruptedException e) {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    waitClients = false;
                }
            }
            socket.close();
        }
    }
}
