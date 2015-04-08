package br.com.brunodelima.mocklocation.cli;

import java.util.ArrayList;

/**
 * Created by bruno on 07/04/15.
 */
public class SendLocationCommand extends Command {

    public SendLocationCommand() {
        super("send-location .+");
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        // TODO send location to selected device
        print("Run send location");
    }
}
