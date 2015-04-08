package br.com.brunodelima.mocklocation.cli;

import java.util.ArrayList;

/**
 * Created by bruno on 07/04/15.
 */
public class HelpCommand extends Command {

    public HelpCommand() {
        super("help|-h");
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        // TODO show all commands descriptions
        print("Not implemented yet");
    }
}
