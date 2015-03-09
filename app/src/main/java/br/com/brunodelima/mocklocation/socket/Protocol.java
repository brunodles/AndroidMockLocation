package br.com.brunodelima.mocklocation.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Bruno de Lima
 */
public interface Protocol {

    void processMessage(InputStream input, OutputStream output) throws IOException;

    void onError(Exception e);
}
