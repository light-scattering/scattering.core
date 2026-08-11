package eu.scattering.cli.util;

import picocli.CommandLine;

import java.net.URL;
import java.util.Properties;

public class VersionProvider implements CommandLine.IVersionProvider {

    private Properties loadProperties() throws Exception {
        URL url = getClass().getResource("/version.properties");

        if (url == null) {
            return null;
        }

        Properties props = new Properties();
        props.load(url.openStream());

        return props;
    }

    @Override
    public String[] getVersion() throws Exception {
        Properties props = loadProperties();

        if (props == null) {
            return new String[] {
                "No version info found"
            };
        }

        return new String[] {
            props.getProperty("application.version")
        };
    }

    public String[] getInfo() throws Exception {
        Properties props = loadProperties();

        if (props == null) {
            return new String[] {
                "No version info found"
            };
        }

        return new String[] {
                "Version - " + props.getProperty("application.version"),
                "Branch  - " + props.getProperty("git.branch"),
                "Commit  - " + props.getProperty("git.commit"),
                "Time    - " + props.getProperty("build.time")
        };
    }
}