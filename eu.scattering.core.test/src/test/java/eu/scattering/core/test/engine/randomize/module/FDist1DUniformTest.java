package eu.scattering.core.test.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist1DUniformTest")
public class FDist1DUniformTest {

    @Test
    @DisplayName("Construct with primitives A")
    void constructWithPrimitivesA() {
        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DUniform(1.1, 1.2);

        double sum = 0;
        for (int i = 0 ; i < 10 ; i++) {
            sum += dist.produce();
        }

        assertTrue(sum > 11 && sum < 12,
                "The sum of random values is out of bounds");
    }

    @Test
    @DisplayName("Construct with primitives B")
    void constructWithPrimitivesB() {
        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DUniform(1.2, 1.1);

        double sum = 0;
        for (int i = 0 ; i < 10 ; i++) {
            sum += dist.produce();
        }

        assertTrue(sum > 11 && sum < 12,
                "The sum of random values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DUniform(1.1, 1.2);

        double sum = 0;
        double[] arr = new double[1];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            sum += arr[0];
        }

        assertTrue(sum > 11 && sum < 12,
                "The sum of random values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FRandGenerator random = factory.getFRand();
        FDist1D dist = random.getFDist1DUniform(1.1, 1.2);

        double[] arr = new double[0];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
