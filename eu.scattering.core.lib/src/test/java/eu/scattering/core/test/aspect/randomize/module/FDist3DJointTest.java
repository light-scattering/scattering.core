package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist3DCompositeTest")
public class FDist3DJointTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        var random = factory.random();
        FRandDist1D dX = random.dist1D().uniform(1.1, 1.2);
        FRandDist1D dY = random.dist1D().uniform(-1.1, -1.2);
        FRandDist1D dZ = random.dist1D().uniform(-0.1, 0.1);
        FRandDist3D dist = random.dist3D().joint(dX, dY, dZ);

        for (int i = 0 ; i < 10 ; i++) {
            FPos3D res = dist.produce();
            assertTrue(res.getD0() >= 1.1 && res.getD0() < 1.2,
                    "Value X is erroneous");
            assertTrue(res.getD1() >= -1.2 && res.getD1() < -1.1,
                    "Value Y is erroneous");
            assertTrue(res.getD2() >= -0.1 && res.getD2() < 0.1,
                    "Value Z is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        var random = factory.random();
        FRandDist1D dX = random.dist1D().uniform(1.1, 1.2);
        FRandDist1D dY = random.dist1D().uniform(-1.1, -1.2);
        FRandDist1D dZ = random.dist1D().uniform(-0.1, 0.1);
        FRandDist3D dist = random.dist3D().joint(dX, dY, dZ);

        double[] arr = new double[3];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            assertTrue(arr[0] >= 1.1 && arr[0] < 1.2,
                    "Value X is erroneous");
            assertTrue(arr[1] >= -1.2 && arr[1] < -1.1,
                    "Value Y is erroneous");
            assertTrue(arr[2] >= -0.1 && arr[2] < 0.1,
                    "Value Z is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        var random = factory.random();
        FRandDist1D dX = random.dist1D().uniform(1.1, 1.2);
        FRandDist1D dY = random.dist1D().uniform(-1.1, -1.2);
        FRandDist1D dZ = random.dist1D().uniform(-0.1, 0.1);
        FRandDist3D dist = random.dist3D().joint(dX, dY, dZ);

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
