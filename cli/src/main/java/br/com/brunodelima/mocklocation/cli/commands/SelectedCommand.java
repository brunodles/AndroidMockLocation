package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 09/04/15.
 */
public class SelectedCommand extends PropertyCommand {

    public SelectedCommand(Properties properties) {
        super("selected|show|ip", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        print("Selected ip %s\n",properties.getSelectedIp());
    }

    @Override
    public String getDescription() {
        return "Show selected ip.";
    }
}
