package br.com.brunodelima.mocklocation.cli.commands;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;
import br.com.brunodelima.socket.Broadcast;

/**
 * Created by bruno on 07/04/15.
 */
public class FindDevicesCommand extends PropertyCommand {

    public static final int DEFAULT_DELAY = 5000;
    public static final int DEFAULT_PORT = 30122;

    public FindDevicesCommand(Properties properties) {
        super("(?:find|list)(?: (\\d+))?", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        List<InetAddress> addresses;
        try {
            if ((matcher.size() > 0) && (null != matcher.get(0)))
                addresses = tryToFindDevices(Integer.valueOf(matcher.get(0)));
            else
                addresses = tryToFindDevices(DEFAULT_DELAY);
            print(addresses);
            updateProperties(addresses);
        } catch (NumberFormatException e) {
            System.err.println("Wrong parameter value.");
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private List<InetAddress> tryToFindDevices(int delay) throws IOException {
        return Broadcast.getServerAdresses("locations", "230.0.0.1", DEFAULT_PORT, delay);
    }

    private void print(List<InetAddress> addresses) {
        if (addresses.isEmpty())
            showEmptyMessage();
        else
            showAddresses(addresses);
    }

    private void showEmptyMessage() {
        print("Device not found.");
    }

    private void showAddresses(List<InetAddress> adresses) {
        print("Found devices:");
        int size = adresses.size();
        String format = " %0" + (String.valueOf(size).length()) + "d %s - %s\n";
        for (int i = 0; i < size; i++) {
            InetAddress adress = adresses.get(i);
            print(format, i, adress.getHostAddress(), adress.getHostName());
        }
    }

    private void updateProperties(List<InetAddress> addresses) {
        for (int i = 0; i < addresses.size(); i++)
            properties.putAdress(i, addresses.get(i).getHostAddress());
        properties.save();
    }

    @Override
    public String getDescription() {
        return "Search for every device running mocklocation service in your LAN.";
    }
}
