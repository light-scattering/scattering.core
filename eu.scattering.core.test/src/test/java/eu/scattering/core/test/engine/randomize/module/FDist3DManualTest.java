package eu.scattering.core.test.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist3DManualTest")
public class FDist3DManualTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DManual((rnd, arr) -> {
            arr[0] = rnd.nextDouble(0.001, 0.002);
            arr[1] = rnd.nextDouble(3.001, 3.002);
            arr[2] = rnd.nextDouble(5.001, 5.002);
        });

        double sumX = 0;
        double sumY = 0;
        double sumZ = 0;
        for (int i = 0 ; i < 10 ; i++) {
            FPos3D result = dist.produce();
            sumX += result.getD0();
            sumY += result.getD1();
            sumZ += result.getD2();
        }

        assertTrue(sumX > 0.01 && sumX < 0.02,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 30.01 && sumY < 30.02,
                "The sum of random Y values is out of bounds");
        assertTrue(sumZ > 50.01 && sumZ < 50.02,
                "The sum of random Z values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DManual((rnd, arr) -> {
            arr[0] = rnd.nextDouble(0.001, 0.002);
            arr[1] = rnd.nextDouble(3.001, 3.002);
            arr[2] = rnd.nextDouble(5.001, 5.002);
        });

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

        assertTrue(sumX > 0.01 && sumX < 0.02,
                "The sum of random X values is out of bounds");
        assertTrue(sumY > 30.01 && sumY < 30.02,
                "The sum of random Y values is out of bounds");
        assertTrue(sumZ > 50.01 && sumZ < 50.02,
                "The sum of random Z values is out of bounds");
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DManual((rnd, arr) -> {
            arr[0] = rnd.nextDouble(0.001, 0.002);
            arr[1] = rnd.nextDouble(3.001, 3.002);
            arr[2] = rnd.nextDouble(5.001, 5.002);
        });

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
