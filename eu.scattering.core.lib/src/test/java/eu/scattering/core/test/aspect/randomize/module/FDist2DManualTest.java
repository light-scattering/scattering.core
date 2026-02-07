package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.FDist2D;
import eu.scattering.core.design.storage.transfer.position.p1.variants.FPos2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist2DManualTest")
public class FDist2DManualTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DManual((rnd, arr) -> {
            arr[0] = rnd.nextDouble(0.001, 0.002);
            arr[1] = rnd.nextDouble(3.001, 3.002);
        });

        double sumX = 0;
        double sumY = 0;
        for (int i = 0 ; i < 10 ; i++) {
            FPos2D result = dist.produce();
            sumX += result.getD0();
            sumY += result.getD1();
        }

        assertTrue(sumX > 0.01 && sumX < 0.02,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 30.01 && sumY < 30.02,
                "The sum of random Y values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DManual((rnd, arr) -> {
            arr[0] = rnd.nextDouble(0.001, 0.002);
            arr[1] = rnd.nextDouble(3.001, 3.002);
        });

        double sumX = 0;
        double sumY = 0;
        double[] arr = new double[2];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            sumX += arr[0];
            sumY += arr[1];
        }

        assertTrue(sumX > 0.01 && sumX < 0.02,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 30.01 && sumY < 30.02,
                "The sum of random Y values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DManual((rnd, arr) -> {
            arr[0] = rnd.nextDouble(0.001, 0.002);
            arr[1] = rnd.nextDouble(3.001, 3.002);
        });

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
