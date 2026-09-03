package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.FDistFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist1DManualTest")
public class FDist1DManualTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dist = random.d1().custom((rnd, arr) -> arr[0] = rnd.nextDouble(0.001, 0.002));

        double sum = 0;
        for (int i = 0 ; i < 10 ; i++) {
            sum += dist.produce();
        }

        assertTrue(sum > 0.01 && sum < 0.02,
                "The sum of random values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dist = random.d1().custom((rnd, arr) -> arr[0] = rnd.nextDouble(0.001, 0.002));

        double sum = 0;
        double[] arr = new double[1];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            sum += arr[0];
        }

        assertTrue(sum > 0.01 && sum < 0.02,
                "The sum of random values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dist = random.d1().custom((rnd, arr) -> arr[0] = rnd.nextDouble(0.001, 0.002));

        double[] arr = new double[0];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
