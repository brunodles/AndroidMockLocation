package br.com.brunodelima.mocklocation.cli;

import java.util.List;

/**
 * Created by bruno on 04/04/15.
 */
public interface CommandLineInterface {

    List<String> findDevices();

    void useDevice(int index);

    void sendLocation(String location);
}
