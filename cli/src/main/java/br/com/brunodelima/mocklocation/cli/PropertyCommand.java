package br.com.brunodelima.mocklocation.cli;

import br.com.brunodelima.mocklocation.cli.Command;
import br.com.brunodelima.mocklocation.cli.Properties;

/**
 * Created by bruno on 09/04/15.
 */
public abstract class PropertyCommand extends Command {
    protected Properties properties;

    public PropertyCommand(String regex, Properties properties) {
        super(regex);
        this.properties = properties;
    }
}
