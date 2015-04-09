package br.com.brunodelima.mocklocation.cli;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
        // TODO show all commands descriptions
        for (Describable describable : describableList)
            print("%s\n    %s\n",describable.getRegex(),describable.getDescription());
    }

    @Override
    public String getDescription() {
        return "Show this help.";
    }
}
