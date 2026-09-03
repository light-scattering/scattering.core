package eu.scattering.core.test.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.distribution.FDistFactoryContext;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.FDist2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Timeout(5)
@DisplayName("FDist2DFixedTest")
public class FDist2DFixedTest {

    @Test
    @DisplayName("Construct with primitives")
    void constructWithPrimitives() {
        FDistFactoryContext random = factory.random().distributions();
        FDist2D dist = random.d2().fixed(1, 2);

        for (int i = 0 ; i < 10 ; i++) {
            assertEquals(factory.getFPos2D(1, 2), dist.produce(),
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Construct with FPos2D")
    void constructWithFPos2D() {
        FDistFactoryContext random = factory.random().distributions();
        FDist2D dist = random.d2().fixed(factory.getFPos2D(1, 2));

        for (int i = 0 ; i < 10 ; i++) {
            assertEquals(factory.getFPos2D(1, 2), dist.produce(),
                    "The value is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array")
    void produceValueArray() {
        FDistFactoryContext random = factory.random().distributions();
        FDist2D dist = random.d2().fixed(factory.getFPos2D(1, 2));

        double[] arr = new double[2];
        for (int i = 0 ; i < 10 ; i++) {
            dist.produce(arr);
            assertEquals(1, arr[0],
                    "The value X is erroneous");
            assertEquals(2, arr[1],
                    "The value Y is erroneous");
        }
    }

    @Test
    @DisplayName("Produce value array, IllegalArgumentException")
    void produceValueArrayNullPointerException() {
        FDistFactoryContext random = factory.random().distributions();
        FDist2D dist = random.d2().fixed(factory.getFPos2D(1, 2));

        double[] arr = new double[1];

        assertThrows(IllegalArgumentException.class, () -> dist.produce(arr),
                "The array size is erroneous");
    }
}
