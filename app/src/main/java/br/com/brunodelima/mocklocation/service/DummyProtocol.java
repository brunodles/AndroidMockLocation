package br.com.brunodelima.mocklocation.service;

import android.util.Log;

import br.com.brunodelima.mocklocation.socket.StringProtocol;


/**
 * Created by bruno on 06/01/15.
 */
public class DummyProtocol
        extends StringProtocol {

    private static final String TAG = "MockLocationProtocol";

    @Override
    public String processMessage(String message) {
        Log.i(TAG, String.format("processMessage \"%s\"", message));
        return String.format("received \"%s\"", message);
    }

    @Override
    public boolean isBreakMessage(String message) {
        return "STOP".equalsIgnoreCase(message);
    }
}