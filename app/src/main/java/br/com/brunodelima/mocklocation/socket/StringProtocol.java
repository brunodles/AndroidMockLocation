package br.com.brunodelima.mocklocation.socket;/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 *
 * @author Bruno
 */
public abstract class StringProtocol implements Protocol {

    @Override
    public void processMessage(InputStream input, OutputStream output) throws IOException {
        String message = new BufferedReader(new InputStreamReader(input)).readLine();
        if (message == null || isBreakMessage(message)) {
            sendMessage(output, null);
            throw new IOException("Break Message");
        }
        String outLine = processMessage(message);
        sendMessage(output, outLine);
    }

    private void sendMessage(OutputStream output, String outLine) {
        new PrintWriter(output, true).println(outLine);
    }

    @Override
    public void onError(Exception e) {
    }

    public abstract String processMessage(String message);

    public abstract boolean isBreakMessage(String message);
}
