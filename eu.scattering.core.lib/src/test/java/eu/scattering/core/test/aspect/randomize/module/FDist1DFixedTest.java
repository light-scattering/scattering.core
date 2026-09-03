package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(5)
@DisplayName("FDist1DFixedTest")
public class FDist1DFixedTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        var random = factory.random();
        FRandDist1D dist = random.dist1D().fixed(5);

        for (int i = 0 ; i < 10 ; i++) {
            assertEquals(5, dist.produce(),
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        var random = factory.random();
        FRandDist1D dist = random.dist1D().fixed(5);

        double[] arr = new double[1];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            assertEquals(5, arr[0],
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        var random = factory.random();
        FRandDist1D dist = random.dist1D().fixed(5);

        double[] arr = new double[0];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
