package br.com.brunodelima.mocklocation.cli;

import java.util.List;

/**
 * Created by bruno on 04/04/15.
 */
public class CommandLineInterface implements Executable{

//    List<String> findDevices();
//
//    void useDevice(int index);
//
//    void sendLocation(String location);

    Executable chain;

    public CommandLineInterface() {
        // TODO build the chain
    }

    @Override
    public void execute(String text) {
        chain.execute(text);
    }
}
