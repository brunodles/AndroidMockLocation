package br.com.brunodelima.mocklocation;

import br.com.brunodelima.mocklocation.cli.CommandLineInterface;

/**
 * Created by bruno on 16/04/15.
 */
public class StartCLI {
    public static void main(String[] args) {
        String command = buildCommand(args);
        CommandLineInterface cli = new CommandLineInterface();
        cli.execute(command);
    }

    private static String buildCommand(String[] args) {
        StringBuilder builder = new StringBuilder();
        for (String arg : args)
            builder.append(arg)
                    .append(" ");
        return builder.toString().trim();
    }
}
