package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 07/04/15.
 */
public class UseDeviceCommand extends PropertyCommand {

    public UseDeviceCommand(Properties properties) {
        super("use (\\d+)", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        int index = Integer.parseInt(matcher.get(0));
        print("Selected ip %s\n",index);
        properties.setSelected(index);
        properties.save();
    }

    @Override
    public String getDescription() {
        return "Select one device to send locations update.";
    }
}
