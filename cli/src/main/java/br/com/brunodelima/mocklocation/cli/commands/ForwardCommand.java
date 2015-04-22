package br.com.brunodelima.mocklocation.cli.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 19/04/15.
 */
public class ForwardCommand extends PropertyCommand {

    private final String executablePath;
    private final Runtime runtime;

    public ForwardCommand(Properties properties) {
        super("forward", properties);
        executablePath = String.format("%s/platform-tools/adb", properties.getSdkPath());
        runtime = Runtime.getRuntime();
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        try {
            forwardPort(FindDevicesCommand.DEFAULT_PORT);
            forwardPort(SendLocationCommand.DEFAULT_PORT);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        }
    }

    private void forwardPort(int port) throws IOException {
        // Reference: adb forward <local <remote>
        String command = String.format("%s forward tcp:%2$s tcp:%2$s", executablePath, port);
        System.out.println(command);
        Process process = runtime.exec(command);
        readProcessStream(process.getInputStream());
        readProcessStream(process.getErrorStream());
    }

    private void readProcessStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = bufferedReader.readLine()) != null)
            System.out.println(line);
    }

    @Override
    public String getDescription() {
        return null;
    }
}
