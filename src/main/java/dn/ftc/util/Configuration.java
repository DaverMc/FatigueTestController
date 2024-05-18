package dn.ftc.util;

import dn.ftc.util.loging.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configuration {

    private final Properties properties;

    public Configuration() {
        this.properties = new Properties();
    }

    public boolean loadFromResource(String resourceName) {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resourceName);
        if(inputStream == null) return false;
        try {
            properties.load(new InputStreamReader(inputStream));
            return true;
        }catch (IOException e) {
            Logger.error(e);
            return false;
        }
    }

    public String getString(String key) {
        return properties.getProperty(key);
    }

    public int getInt(String key) {
        return Integer.parseInt(getString(key));
    }

    public float getFloat(String key) {
        return Float.parseFloat(getString(key));
    }

}
