package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist3DUniformTest")
public class FDist3DUniformTest {

    @Test
    @DisplayName("Construct with primitives")
    void constructWithPrimitives() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DUniform(1.1, 1.2, 3.1, 3.2, 5.1, 5.2);

        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        for (int i = 0 ; i < 10 ; i++) {
            FPos3D result = dist.produce();
            sumX += result.getD0();
            sumY += result.getD1();
            sumZ += result.getD2();
        }

        assertTrue(sumX > 11 && sumX < 12,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 31 && sumY < 32,
                "The sum of random Y values is out of bounds");
        assertTrue(sumZ > 51 && sumZ < 52,
                "The sum of random Z values is out of bounds");
    }

    @Test
    @DisplayName("Construct with FPairPos3D")
    void constructWithFPairPos3D() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DUniform(
                factory.getFPairPos3D(1.1, 3.1, 5.1, 1.2, 3.2, 5.2));

        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        for (int i = 0 ; i < 10 ; i++) {
            FPos3D result = dist.produce();
            sumX += result.getD0();
            sumY += result.getD1();
            sumZ += result.getD2();
        }

        assertTrue(sumX > 11 && sumX < 12,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 31 && sumY < 32,
                "The sum of random Y values is out of bounds");
        assertTrue(sumZ > 51 && sumZ < 52,
                "The sum of random Z values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DUniform(
                factory.getFPairPos3D(1.1, 3.1, 5.1, 1.2, 3.2, 5.2));

        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        double[] arr = new double[3];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            sumX += arr[0];
            sumY += arr[1];
            sumZ += arr[2];
        }

        assertTrue(sumX > 11 && sumX < 12,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 31 && sumY < 32,
                "The sum of random Y values is out of bounds");
        assertTrue(sumZ > 51 && sumZ < 52,
                "The sum of random Z values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DUniform(
                factory.getFPairPos3D(1.1, 3.1, 5.1, 1.2, 3.2, 5.2));

        double[] arr = new double[2];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
