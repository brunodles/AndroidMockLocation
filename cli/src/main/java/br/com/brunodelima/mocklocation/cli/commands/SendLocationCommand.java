package br.com.brunodelima.mocklocation.cli.commands;

import java.io.IOException;
import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;
import br.com.brunodelima.socket.SocketClient;

/**
 * Created by bruno on 07/04/15.
 */
public class SendLocationCommand extends PropertyCommand {

    public SendLocationCommand(Properties properties) {
        super("send (.++)", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        String selectedId = properties.getSelectedId();
        print("Send location to %s\n", selectedId);
        try {
            String result = send(selectedId, matcher.get(0));
            print(result);
        } catch (IOException e) {
            print("Can't send message " + e.getCause());
        }
    }

    private String send(String selectedId, String message) throws IOException {
        SocketClient client = new SocketClient(selectedId, 30123);
        client.sendMessage(message);
        String response = client.reciveMessge();
        client.getSocket().close();
        return response;
    }

    @Override
    public String getDescription() {
        return "Send locations to selected device.";
    }
}