package br.com.brunodelima.mocklocation.cli.checker;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by bruno on 25/04/15.
 */
public class Ipv4CheckerTest {

    private static final String[] validIps = {
            "127.0.0.1",
            "192.168.0.1",
            "10.0.0.1"
    };
    private static final String[] invalidIps = {
            "127.0.0.255",
            "192.168.0.300",
            "255.0.0.0"
    };

    @Test
    public void testIsValid() throws Exception {
        for (String ip : validIps)
            assertTrue("Valid ip expected", Ipv4Checker.isValid(ip));
    }

    @Test
    public void testIsInvalid(){
        for (String ip : invalidIps)
            assertFalse("Invalid ip expected", Ipv4Checker.isValid(ip));
    }
}