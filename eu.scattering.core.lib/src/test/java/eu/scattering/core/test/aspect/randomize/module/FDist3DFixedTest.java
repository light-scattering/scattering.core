package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(5)
@DisplayName("FDist3DFixedTest")
public class FDist3DFixedTest {

    @Test
    @DisplayName("Construct with primitives")
    void constructWithPrimitives() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DFixed(1, 2, 3);

        for (int i = 0 ; i < 10 ; i++) {
            assertEquals(factory.getFPos3D(1, 2, 3), dist.produce(),
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Construct with FPos3D")
    void constructWithFPos3D() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DFixed(factory.getFPos3D(1, 2, 3));

        for (int i = 0 ; i < 10 ; i++) {
            assertEquals(factory.getFPos3D(1, 2, 3), dist.produce(),
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DFixed(factory.getFPos3D(1, 2, 3));

        double[] arr = new double[3];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            assertEquals(1, arr[0],
                    "The value X is erroneous");
            assertEquals(2, arr[1],
                    "The value Y is erroneous");
            assertEquals(3, arr[2],
                    "The value Z is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FRandGenerator random = factory.getFRand();
        FDist3D dist = random.getFDist3DFixed(factory.getFPos3D(1, 2, 3));

        double[] arr = new double[2];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
