package eu.scattering.core.test;

import eu.scattering.core.impl.ScatterCore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ScatterCore main tests")
public class ScatterCoreTest {

    @Test
    @DisplayName("Validate version")
    void getVersion() {
        String verExpected = System.getProperty("expected.app.version");

        assertNotNull(verExpected);

        String verActual = ScatterCore.getVersion();

        assertEquals(verExpected, verActual, "The version is incorrect");
    }

    @Test
    @DisplayName("Validate diagnostics")
    void getDiagnostics() {
        Properties props = ScatterCore.getDiagnostics();

        assertNotNull(props);

        assertTrue(props.size() >= 4, "The diagnostics size is incorrect");
    }
}
