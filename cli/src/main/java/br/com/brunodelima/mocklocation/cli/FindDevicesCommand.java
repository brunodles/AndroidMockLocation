package br.com.brunodelima.mocklocation.cli;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import br.com.brunodelima.socket.Broadcast;

/**
 * Created by bruno on 07/04/15.
 */
public class FindDevicesCommand extends Command {

    public FindDevicesCommand() {
        super("find-devices");
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        try {
            List<InetAddress> addresses = tryToFindDevices();
            printOut(addresses);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private void printOut(List<InetAddress> addresses) {
        if (addresses.isEmpty())
            showEmptyMessage();
        else
            showAdresses(addresses);
    }

    private List<InetAddress> tryToFindDevices() throws IOException {
        return Broadcast.getServerAdresses("locations", "230.0.0.1", 30122, 5000);
    }

    private void showAdresses(List<InetAddress> adresses) {
        print("Found devices:");
        for (int i = 0; i < adresses.size(); i++) {
            InetAddress adress = adresses.get(i);
            print(" %02d %s - %s\n", i, adress.getHostAddress(), adress.getCanonicalHostName());
        }
    }

    private void showEmptyMessage() {
        print("Device not found.");
    }

    @Override
    public String getDescription() {
        return "Search for every device running mocklocation service in your LAN.";
    }
}
