package br.com.brunodelima.mocklocation.cli;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bruno on 07/04/15.
 */
abstract class Command implements Executable {

    protected String regex;
    protected Pattern pattern;
    private Executable next;

    public Command(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(regex);
    }

    @Override
    public void execute(String text) {
        Matcher matcher = pattern.matcher(text);
        if (matcher.matches())
            this.run(getGroups(matcher));
        if (next != null)
            next.execute(text);
    }

    private ArrayList<String> getGroups(Matcher matcher) {
        int count = matcher.groupCount();
        ArrayList<String> params = new ArrayList<>();
        for (int i = 1; i <= count; i++)
            params.add(matcher.group(i));
        return params;
    }

    protected abstract void run(ArrayList<String> matcher);

    protected void print(String string) {
        System.out.println(string);
    }

    protected void print(String string, Object... objects) {
        System.out.printf(string, objects);
    }

    protected void print(Throwable throwable) {
        throwable.printStackTrace(System.err);
    }

    public Command next(Command next) {
        this.next = next;
        return next;
    }
}
