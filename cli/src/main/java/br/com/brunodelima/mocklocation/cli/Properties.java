package br.com.brunodelima.mocklocation.cli;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by bruno on 09/04/15.
 */
public class Properties {

    public static final String KEY_ADDRESS = "address-";
    private static final String KEY_SELECTED = "selected";
    private static final String KEY_SDK_PATH = "sdk_path";
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

    public String getSelectedIp() {
        int selected = Integer.valueOf(properties.getProperty(KEY_SELECTED));
        return getAddress(selected);
    }

    public void putAdress(int i, String hostAddress) {
        properties.put(KEY_ADDRESS + i, hostAddress);
    }

    public void setSelected(int index) {
        properties.put(KEY_SELECTED, String.valueOf(index));
    }

    public String getAddress(int index) {
        String addressKey = KEY_ADDRESS + index;
        return properties.getProperty(addressKey);
    }

    public String getSdkPath() {
        return properties.getProperty(KEY_SDK_PATH);
    }

    public void setSdkPath(String path) {
        properties.setProperty(KEY_SDK_PATH, path);
    }
}
