package eu.scattering.core.test.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.FDist2D;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist2DUniformTest")
public class FDist2DUniformTest {

    @Test
    @DisplayName("Construct with primitives")
    void constructWithPrimitives() {
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DUniform(1.1, 1.2, 5.1, 5.2);

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
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DUniform(factory.getFPairPos2D(1.1, 5.1, 1.2, 5.2));

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
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DUniform(factory.getFPairPos2D(1.1, 5.1, 1.2, 5.2));

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
        FRandGenerator random = factory.getFRand();
        FDist2D dist = random.getFDist2DUniform(factory.getFPairPos2D(1.1, 5.1, 1.2, 5.2));

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
