package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;

import br.com.brunodelima.mocklocation.cli.Properties;
import br.com.brunodelima.mocklocation.cli.PropertyCommand;

/**
 * Created by bruno on 20/04/15.
 */
public class SdkPathCommand extends PropertyCommand {

    public SdkPathCommand(Properties properties) {
        super("sdk(?: (.+))?", properties);
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        String param = matcher.get(0);
        if (param == null)
            showPath();
        else
            setPath(param);
    }

    private void showPath() {
        System.out.printf("sdkPath > %s\n", properties.getSdkPath());
    }

    private void setPath(String param) {
        System.out.printf("new sdkPath > %s\n", param);
        properties.setSdkPath(param);
        properties.save();
    }

    @Override
    public String getDescription() {
        return "View or set android sdk path";
    }
}
