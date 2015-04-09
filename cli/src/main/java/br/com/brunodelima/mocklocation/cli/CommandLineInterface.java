package br.com.brunodelima.mocklocation.cli;

import java.io.IOException;
import java.util.Scanner;

import br.com.brunodelima.mocklocation.cli.commands.FindDevicesCommand;
import br.com.brunodelima.mocklocation.cli.commands.HelpCommand;
import br.com.brunodelima.mocklocation.cli.commands.SelectedCommand;
import br.com.brunodelima.mocklocation.cli.commands.SendLocationCommand;
import br.com.brunodelima.mocklocation.cli.commands.UseDeviceCommand;

/**
 * Created by bruno on 04/04/15.
 */
public class CommandLineInterface implements Executable {

    private final HelpCommand helpCommand;
    private Command last;
    private Command first;

    public CommandLineInterface() {
        Properties properties = new Properties("./cli.properties");
        try {
            properties.load();
        } catch (IOException e) {
        }
        helpCommand = new HelpCommand();
        addToChain(new FindDevicesCommand(properties));
        addToChain(new UseDeviceCommand(properties));
        addToChain(new SendLocationCommand(properties));
        addToChain(new SelectedCommand(properties));
        addToChain(helpCommand);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CommandLineInterface cli = new CommandLineInterface();
        while (true) {
            String next = sc.nextLine();
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
