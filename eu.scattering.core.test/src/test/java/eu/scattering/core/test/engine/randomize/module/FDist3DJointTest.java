package eu.scattering.core.test.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDist3DCompositeTest")
public class FDist3DJointTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FRandGenerator random = factory.getFRand();
        FDist1D dX = random.getFDist1DUniform(1.1, 1.2);
        FDist1D dY = random.getFDist1DUniform(-1.1, -1.2);
        FDist1D dZ = random.getFDist1DUniform(-0.1, 0.1);
        FDist3D dist = random.getFDist3DJoint(dX, dY, dZ);

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
        FRandGenerator random = factory.getFRand();
        FDist1D dX = random.getFDist1DUniform(1.1, 1.2);
        FDist1D dY = random.getFDist1DUniform(-1.1, -1.2);
        FDist1D dZ = random.getFDist1DUniform(-0.1, 0.1);
        FDist3D dist = random.getFDist3DJoint(dX, dY, dZ);

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
        FRandGenerator random = factory.getFRand();
        FDist1D dX = random.getFDist1DUniform(1.1, 1.2);
        FDist1D dY = random.getFDist1DUniform(-1.1, -1.2);
        FDist1D dZ = random.getFDist1DUniform(-0.1, 0.1);
        FDist3D dist = random.getFDist3DJoint(dX, dY, dZ);

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
