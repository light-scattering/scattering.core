package eu.scattering.core.test.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.*;

@Timeout(5)
@DisplayName("FDist1DFixedTest")
public class FDist1DFixedTest {

    @Test
    @DisplayName("Set value")
    void setValue() {
        FRandGenerator random = factory.getFRandGenerator();
    }
}
