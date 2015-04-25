package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;
import br.com.brunodelima.mocklocation.cli.checker.Ipv4Checker;

/**
 * Created by bruno on 07/04/15.
 */
public class UseDeviceCommand extends PropertyCommand {

    public static final String COMMAND_REGEX = "use (\\d+|local|[\\d\\.]+)";

    public UseDeviceCommand(Properties properties) {
        super(COMMAND_REGEX, properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        String s = matcher.get(0);
        if (Ipv4Checker.isValid(s))
            useIp(s);
        else if ("local".equalsIgnoreCase(s))
            useLocal();
        else
            useIndex(Integer.parseInt(s));
    }

    private void useIp(String ip) {
        properties.putAdress(99, ip);
        properties.setSelected(99);
        properties.save();
        printIp(ip);
    }

    private void useLocal() {
        String ip = "127.0.0.1";
        useIp(ip);
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
