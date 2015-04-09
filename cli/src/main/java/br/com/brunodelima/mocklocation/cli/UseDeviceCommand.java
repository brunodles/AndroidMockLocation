package br.com.brunodelima.mocklocation.cli;

import java.util.ArrayList;

/**
 * Created by bruno on 07/04/15.
 */
public class UseDeviceCommand extends Command {

    public UseDeviceCommand() {
        super("use-device=(\\d+)");
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        int index = Integer.parseInt(matcher.get(0));
        print("Selected "+index);
        // TODO need to save selected index
    }

    @Override
    public String getDescription() {
        return "Select one device to send locations update.";
    }
}
