package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FRandDist2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist2DUniformTest")
public class FDist2DUniformTest {

    @Test
    @DisplayName("Construct with primitives")
    void constructWithPrimitives() {
        var random = factory.random();
        FRandDist2D dist = random.dist2D().uniform(1.1, 1.2, 5.1, 5.2);

        double sumX = 0;
        double sumY = 0;
        for (int i = 0 ; i < 10 ; i++) {
            FPos2D result = dist.produce();
            sumX += result.getD0();
            sumY += result.getD1();
        }

        assertTrue(sumX > 11 && sumX < 12,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 51 && sumY < 52,
                "The sum of random Y values is out of bounds");
    }

    @Test
    @DisplayName("Construct with FPairPos2D")
    void constructWithFPairPos2D() {
        var random = factory.random();
        FRandDist2D dist = random.dist2D().uniform(factory.getFPairPos2D(1.1, 5.1, 1.2, 5.2));

        double sumX = 0;
        double sumY = 0;
        for (int i = 0 ; i < 10 ; i++) {
            FPos2D result = dist.produce();
            sumX += result.getD0();
            sumY += result.getD1();
        }

        assertTrue(sumX > 11 && sumX < 12,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 51 && sumY < 52,
                "The sum of random Y values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        var random = factory.random();
        FRandDist2D dist = random.dist2D().uniform(factory.getFPairPos2D(1.1, 5.1, 1.2, 5.2));

        double sumX = 0;
        double sumY = 0;
        double[] arr = new double[2];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            sumX += arr[0];
            sumY += arr[1];
        }

        assertTrue(sumX > 11 && sumX < 12,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 51 && sumY < 52,
                "The sum of random Y values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        var random = factory.random();
        FRandDist2D dist = random.dist2D().uniform(factory.getFPairPos2D(1.1, 5.1, 1.2, 5.2));

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
