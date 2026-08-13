package eu.scattering;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Release")
public class ReleaseTest {

    @Test
    @DisplayName("Version")
    void versionTest() throws Exception {
        String vrExpected = System.getProperty("expected.app.version");

        assertTrue(vrExpected != null && !vrExpected.isEmpty());

        System.out.println("JAR Version: " + vrExpected);

        String jarName = "scatter-cli-" + vrExpected + ".jar";
        Path jarPath = Paths.get("build", "libs", jarName);

        ProcessBuilder processBuilder = new ProcessBuilder(
            "java", "-jar", jarPath.toAbsolutePath().toString(), "--version"
        );

        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        String vrActual;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            vrActual = reader.readLine();
        }

        process.waitFor();

        assertEquals(vrExpected.trim(), vrActual.trim(),
                "The JAR version output does not match the root project version.");
    }
}
