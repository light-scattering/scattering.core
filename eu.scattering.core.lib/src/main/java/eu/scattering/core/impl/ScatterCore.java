package eu.scattering.core.impl;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.impl.factory.ScatterFactoryDef;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public final class ScatterCore {

    public static ScatterFactory createFactory() {

        return ScatterFactoryDef.create();
    }

    public static ScatterFactory createFactory(long seed) {

        return ScatterFactoryDef.create(seed);
    }

    //--------------------------------------------------

    public static String getVersion() {
        Properties props = loadProperties();

        assert props != null;

        return props.getProperty("application.version", "UNKNOWN");
    }

    public static Properties getDiagnostics() {
        Properties props = loadProperties();

        assert props != null;

        return props;
    }

    //--------------------------------------------------

    private static Properties loadProperties() {
        URL url = ScatterCore.class.getResource("/version.properties");

        if (url == null) {
            return null;
        }

        Properties props = new Properties();

        try (InputStream is = url.openStream()) {
            props.load(is);
        } catch (IOException e) {
            throw new IllegalStateException("The properties could not be loaded");
        }

        return props;
    }
}
