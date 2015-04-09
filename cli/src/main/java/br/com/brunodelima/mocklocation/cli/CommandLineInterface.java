package br.com.brunodelima.mocklocation.cli;

import java.util.Scanner;

/**
 * Created by bruno on 04/04/15.
 */
public class CommandLineInterface implements Executable {

    private final HelpCommand helpCommand;
    private Command last;
    private Command first;

    public CommandLineInterface() {
        helpCommand = new HelpCommand();
        addToChain(new FindDevicesCommand());
        addToChain(new UseDeviceCommand());
        addToChain(helpCommand);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommandLineInterface cli = new CommandLineInterface();
        while (true) {
            String next = sc.next();
            cli.execute(next);
        }
    }

    private void addToChain(Command command) {
        helpCommand.addDescribable(command);
        if (last == null)
            last = command;
        else
            last = last.next(command);
        if (first == null)
            first = command;
    }

    @Override
    public void execute(String text) {
        first.execute(text);
    }
}
