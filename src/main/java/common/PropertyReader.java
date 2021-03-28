package common;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static final String CONFIG_PROPERTIES = "config.properties";
    private int port;

    private void readPort() throws IOException {

        Properties properties = new Properties();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + CONFIG_PROPERTIES + "' not found in the classpath");
            }
        }

        port = (Integer.parseInt(properties.getProperty("port")));
    }

    public int getPort() throws IOException {
        if (port == 0) {
            readPort();
        }
        return port;
    }
}
