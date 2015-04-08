package br.com.brunodelima.mocklocation.cli;

import java.util.Scanner;

/**
 * Created by bruno on 04/04/15.
 */
public class CommandLineInterface implements Executable {

//    List<String> findDevices();
//
//    void useDevice(int index);
//
//    void sendLocation(String location);

    Command chain;

    public CommandLineInterface() {
        // TODO build the chain
        chain = new HelpCommand();
        chain.next(new FindDevicesCommand())
                .next(new UseDeviceCommand());
//                .next()
    }

    public static void main(String[] args) {
        System.out.println("Test ChainOfResponsibility");
        Scanner sc = new Scanner(System.in);
        CommandLineInterface cli = new CommandLineInterface();
        while (true) {
            String next = sc.next();
            cli.execute(next);
        }
    }

    @Override
    public void execute(String text) {
        chain.execute(text);
    }
}
