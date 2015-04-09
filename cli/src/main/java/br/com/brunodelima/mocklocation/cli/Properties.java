package br.com.brunodelima.mocklocation.cli;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bruno on 09/04/15.
 */
public class Properties {

    private static final String KEY_SELECTED = "selected";
    public static final String KEY_ADDRESS = "address-";

    String filePath;
    java.util.Properties properties;

    public Properties(String filePath) {
        this.filePath = filePath;
        properties = new java.util.Properties();
    }

    public synchronized void load() throws IOException {
        properties.load(new FileInputStream(filePath));
    }

//    public void store() throws IOException {
//        properties.store(new FileOutputStream(filePath), "");
//    }

    public void save() {
        try {
            properties.save(new FileOutputStream(filePath), "");
        } catch (FileNotFoundException e) {
        }
    }

    public String getSelectedId() {
        String addressKey = KEY_ADDRESS + properties.get(KEY_SELECTED);
        return properties.getProperty(addressKey);
    }

    public void putAdress(int i, String hostAddress) {
        properties.put(KEY_ADDRESS +i, hostAddress);
    }

    public void setSelected(int index) {
        properties.put(KEY_SELECTED, String.valueOf(index));
    }
}
