package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 07/04/15.
 */
public class UseDeviceCommand extends PropertyCommand {

    public UseDeviceCommand(Properties properties) {
        super("use (\\d+|local)", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        String s = matcher.get(0);
        print(s);
        if ("local".equalsIgnoreCase(s))
            useLocal();
        else
            useIndex(Integer.parseInt(s));
    }

    private void useLocal() {
        String ip = "127.0.0.1";
        properties.putAdress(99, ip);
        properties.setSelected(99);
        properties.save();
        printIp(ip);
    }

    private void useIndex(int index) {
        properties.setSelected(index);
        properties.save();
        printIp(properties.getAddress(index));
    }

    private void printIp(String ip) {
        print("Selected ip %s\n", ip);
    }

    @Override
    public String getDescription() {
        return "Select one device to send locations update.";
    }
}
