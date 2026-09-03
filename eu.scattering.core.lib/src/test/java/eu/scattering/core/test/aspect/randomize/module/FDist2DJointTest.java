package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.FDistFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FDist2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FDist2DCompositeTest")
public class FDist2DJointTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dX = random.d1().uniform(1.1, 1.2);
        FDist1D dY = random.d1().uniform(-1.1, -1.2);
        FDist2D dist = random.d2().joint(dX, dY);

        for (int i = 0 ; i < 10 ; i++) {
            FPos2D res = dist.produce();
            assertTrue(res.getD0() >= 1.1 && res.getD0() < 1.2,
                    "Value X is erroneous");
            assertTrue(res.getD1() >= -1.2 && res.getD1() < -1.1,
                    "Value Y is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dX = random.d1().uniform(1.1, 1.2);
        FDist1D dY = random.d1().uniform(-1.1, -1.2);
        FDist2D dist = random.d2().joint(dX, dY);

        double[] arr = new double[2];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            assertTrue(arr[0] >= 1.1 && arr[0] < 1.2,
                    "Value X is erroneous");
            assertTrue(arr[1] >= -1.2 && arr[1] < -1.1,
                    "Value Y is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FDistFactoryContext random = factory.random().distributions();
        FDist1D dX = random.d1().uniform(1.1, 1.2);
        FDist1D dY = random.d1().uniform(-1.1, -1.2);
        FDist2D dist = random.d2().joint(dX, dY);

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
