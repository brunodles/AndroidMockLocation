package br.com.brunodelima.mocklocation.cli.commands;

import java.io.IOException;
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
        // Reference: adb forward <local <remote>
        // Need to find the android sdk path
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(String.format("adb forward %1$s %1$s", FindDevicesCommand.DEFAULT_PORT));
            runtime.exec(String.format("adb forward %1$s %1$s", SendLocationCommand.DEFAULT_PORT));
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public String getDescription() {
        return null;
    }
}
