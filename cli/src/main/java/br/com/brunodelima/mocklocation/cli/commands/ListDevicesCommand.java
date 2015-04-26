package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 26/04/15.
 */
public class ListDevicesCommand extends PropertyCommand {

    public ListDevicesCommand(Properties properties) {
        super("list", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        for (int i = 0; i < 100; i++) {
            String address = properties.getAddress(i);
            if (address != null)
                System.out.printf("%s - %s\n", i, address);
        }
    }

    @Override
    public String getDescription() {
        return "List founded devices.";
    }
}
