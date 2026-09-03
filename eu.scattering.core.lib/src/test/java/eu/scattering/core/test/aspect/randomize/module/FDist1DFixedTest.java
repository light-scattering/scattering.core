package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.FDistFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;
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
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dist = random.d1().fixed(5);

        for (int i = 0 ; i < 10 ; i++) {
            assertEquals(5, dist.produce(),
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dist = random.d1().fixed(5);

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
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dist = random.d1().fixed(5);

        double[] arr = new double[0];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
