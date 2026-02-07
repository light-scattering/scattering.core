package eu.scattering.core.test.component.number.aspect;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.storage.transfer.position.p2.variants.FPairPos2D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FComplexRandom")
public class FComplexRandomizeTest {

    @Test
    @DisplayName("Set random position with range")
    void setRandomPositionWithRange() {
        FComplex fComplex = factory.getFComplex();
        FPairPos2D range = factory.getFPairPos2D(0.1, 0.1, 0.2, 0.2);

        FComplex results = factory.getRandAspect().inRange(fComplex, range);

        assertAll("Validate FComplex position",
                () -> assertTrue(fComplex.getRe() > 0.1 && fComplex.getRe() < 0.2,
                        "The real part is erroneous"),
                () -> assertTrue(fComplex.getIm() > 0.1 && fComplex.getIm() < 0.2,
                        "The imaginary part is erroneous"),
                () -> assertSame(fComplex, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Set random position in circle")
    void setRandomPositionInCircle() {
        FComplex fComplex = factory.getFComplex();

        FComplex results = factory.getRandAspect().inCircle(fComplex, 0.1);

        assertAll("Validate FComplex position",
                () -> assertTrue(fComplex.getDistance(0, 0) < 0.1,
                        "The distance is erroneous"),
                () -> assertSame(fComplex, results,
                        "The reference should stay the same")
        );
    }

    @Test
    @DisplayName("Set random position on circle")
    void setRandomPositionOnCircle() {
        FComplex fComplex = factory.getFComplex();

        FComplex results = factory.getRandAspect().onCircle(fComplex, 0.1);

        assertAll("Validate FComplex position",
                () -> assertEquals(0.1, fComplex.getDistance(0, 0),
                        epsilon, "The distance is erroneous"),
                () -> assertSame(fComplex, results,
                        "The reference should stay the same")
        );
    }
}
