package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 19/04/15.
 */
public class ForwardCommand extends PropertyCommand {

    public ForwardCommand(Properties properties) {
        super("forward", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        // TODO adb forward <local <remote>
        // Need to find the android sdk path
    }

    @Override
    public String getDescription() {
        return null;
    }
}
