package br.com.brunodelima.mocklocation.cli.checker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bruno on 25/04/15.
 */
public class Ipv4Checker {
    private static final String REGEX = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    private static final int MAX_VALUE = 254;
    private static final int MIN_VALUE = 0;
    private final Matcher matcher;

    private Ipv4Checker(String text) {
        matcher = Pattern.compile(REGEX).matcher(text);
    }

    public static boolean isValid(String ip) {
        return new Ipv4Checker(ip).isVallid();
    }

    public boolean isVallid() {
        return matcher.find() && checkValues();
    }

    private boolean checkValues() {
        for (int i = 1; i <= matcher.groupCount(); i++) {
            String sValue = matcher.group(i);
            int value = Integer.parseInt(sValue);
            if ((value < MIN_VALUE) || (value > MAX_VALUE))
                return false;
        }
        return true;
    }

}
