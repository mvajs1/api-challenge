package in.reqres.configuration;

import java.net.URL;
import org.apache.commons.configuration2.CombinedConfiguration;
import org.apache.commons.configuration2.builder.combined.CombinedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class ConfigurationProvider {

    public static final String APP_ENV = "appEnv";
    public static final String DEFAULT_ENV = "local";

    private static ConfigurationProvider provider;

    private CombinedConfiguration configuration;

    private ConfigurationProvider() {
        configuration = new CombinedConfiguration();
        if (System.getProperty(APP_ENV) == null) {
            System.setProperty(APP_ENV, DEFAULT_ENV);
        }

        CombinedConfiguration combinedConfiguration = getCombinedConfiguration(
                String.format("config-%s.xml", System.getProperty(APP_ENV)));

        configuration.addConfiguration(combinedConfiguration);
    }

    private static ConfigurationProvider getInstance() {
        if (provider == null) {
            provider = new ConfigurationProvider();
        }
        return provider;
    }

    /**
     * Method to retrieve property
     *
     * @param key Property key
     * @return String representation of property value
     */
    public static String getProperty(String key) {
        if (System.getProperty(key) != null && !System.getProperty(key).isEmpty()) {
            return System.getProperty(key);
        }

        Object property = getInstance().configuration.getProperty(key);
        if (property != null) {
            return property.toString();
        }
        return null;
    }

    private CombinedConfiguration getCombinedConfiguration(String fileName) {
        URL configUrl = Thread.currentThread().getContextClassLoader()
                .getResource(fileName);

        CombinedConfigurationBuilder builder = new CombinedConfigurationBuilder()
                .configure(new Parameters().fileBased().setURL(configUrl));
        if (configUrl != null) {
            try {
                return builder.getConfiguration();
            } catch (ConfigurationException e) {
                throw new RuntimeException(String.format("Unable to load configuration. %s", e));
            }
        } else {
            return new CombinedConfiguration();
        }
    }
}
