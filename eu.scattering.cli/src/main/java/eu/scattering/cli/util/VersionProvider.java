package eu.scattering.cli.util;

import picocli.CommandLine;

import java.net.URL;
import java.util.Properties;

public class VersionProvider implements CommandLine.IVersionProvider {

    @Override
    public String[] getVersion() throws Exception {
        URL url = getClass().getResource("/version.properties");

        if (url == null) {
            return new String[] {"No version info found"};
        }

        Properties props = new Properties();
        props.load(url.openStream());

        return new String[] {
                "Version - " + props.getProperty("application.version"),
                "Branch  - " + props.getProperty("git.branch"),
                "Commit  - " + props.getProperty("git.commit"),
                "Built   - " + props.getProperty("build.time")
        };
    }
}
