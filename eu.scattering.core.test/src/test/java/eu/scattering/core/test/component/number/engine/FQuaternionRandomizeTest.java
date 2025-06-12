package eu.scattering.core.test.component.number.engine;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FQuaternionRandom")
public class FQuaternionRandomizeTest {

    @Test
    @DisplayName("Set random position with range")
    void setRandomPositionWithRange() {
        FQuaternion fQuaternion = factory.getFQuaternion();
        FPairPos4D range = factory.getFPairPos4D(0.1, 0.1, 0.1, 0.1, 0.2, 0.2, 0.2, 0.2);

        FQuaternion results = factory.getFRandEngShared().rndPos(fQuaternion, range);

        assertAll("Validate FComplex position",
                () -> assertTrue(fQuaternion.getRe() > 0.1 && fQuaternion.getRe() < 0.2,
                        "The real part is erroneous"),
                () -> assertTrue(fQuaternion.getI() > 0.1 && fQuaternion.getI() < 0.2,
                        "The imaginary part I is erroneous"),
                () -> assertTrue(fQuaternion.getJ() > 0.1 && fQuaternion.getJ() < 0.2,
                        "The imaginary part J is erroneous"),
                () -> assertTrue(fQuaternion.getK() > 0.1 && fQuaternion.getK() < 0.2,
                        "The imaginary part K is erroneous"),
                () -> assertSame(fQuaternion, results,
                        "The reference should stay the same")
        );
    }
}
