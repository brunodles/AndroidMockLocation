package br.com.brunodelima.mocklocation.cli.commands;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import br.com.brunodelima.mocklocation.cli.Command;
import br.com.brunodelima.mocklocation.cli.Describable;

/**
 * Created by bruno on 07/04/15.
 */
public class HelpCommand extends Command {

    List<Describable> describableList;

    public HelpCommand() {
        super("help|-h");
        describableList = new LinkedList<>();
    }

    public HelpCommand addDescribable(Describable describable) {
        describableList.add(describable);
        return this;
    }

    @Override
    protected void run(ArrayList<String> matcher) {
        for (Describable describable : describableList)
            print("%s\n    %s\n", describable.getRegex(), describable.getDescription());
    }

    @Override
    public String getDescription() {
        return "Show this help.";
    }
}
