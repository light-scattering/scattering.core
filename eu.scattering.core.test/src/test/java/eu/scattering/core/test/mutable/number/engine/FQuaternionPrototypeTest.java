package eu.scattering.core.test.mutable.number.engine;

import eu.scattering.core.design.mutable.number.quaternion.FQuaternion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static eu.scattering.core.test.Config.proto;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FComplexPrototype")
public class FQuaternionPrototypeTest {

    @Test
    @DisplayName("Apply with fixed state")
    void applyWithFixedState() {
        FQuaternion fQuaternion = factory.getFQuaternion(0, 0, 0, 0);

        List<Double> intermediate = new ArrayList<>();

        FQuaternion fComplexRes = proto.applyWithFixedState(fQuaternion,
                p -> intermediate.add(p.setRe(1).setI(2).setJ(3).setK(4).getMagnitude()));

        Assertions.assertAll("Validate FComplex values",
                () -> assertEquals(0, fQuaternion.getRe(), "The 're' value is incorrect"),
                () -> assertEquals(0, fQuaternion.getI(), "The 'i' value is incorrect"),
                () -> assertEquals(0, fQuaternion.getJ(), "The 'j' value is incorrect"),
                () -> assertEquals(0, fQuaternion.getK(), "The 'k' value is incorrect"),
                () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                () -> assertTrue(intermediate.get(0) > 0, "The value is incorrect"),
                () -> assertSame(fComplexRes, fQuaternion, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with double (fixed state)")
    void terminateWithDoubleFixedState() {
        FQuaternion fQuaternion = factory.getFQuaternion(1, 2, 3, 4);

        double res = proto.toDoubleWithFixedState(fQuaternion, p -> {
            p.add(3, 4, 5, 6);
            return p.getRe() + p.getI() + p.getJ() + p.getK();
        });

        Assertions.assertAll("Validate FComplex values",
                () -> assertEquals(1, fQuaternion.getRe(), "The 're' value is incorrect"),
                () -> assertEquals(2, fQuaternion.getI(), "The 'i' value is incorrect"),
                () -> assertEquals(3, fQuaternion.getJ(), "The 'j' value is incorrect"),
                () -> assertEquals(4, fQuaternion.getK(), "The 'k' value is incorrect"),
                () -> assertEquals(28, res, "The value is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with boolean (fixed state)")
    void terminateWithBooleanFixedState() {
        FQuaternion fQuaternion = factory.getFQuaternion(1, 2, 3, 4);

        boolean res = proto.toBooleanWithFixedState(fQuaternion, p -> {
            p.add(3, 4, 5, 6);
            return p.getRe() + p.getI() + p.getJ() + p.getK() == 28;
        });

        Assertions.assertAll("Validate FComplex values",
                () -> assertEquals(1, fQuaternion.getRe(), "The 're' value is incorrect"),
                () -> assertEquals(2, fQuaternion.getI(), "The 'i' value is incorrect"),
                () -> assertEquals(3, fQuaternion.getJ(), "The 'j' value is incorrect"),
                () -> assertEquals(4, fQuaternion.getK(), "The 'k' value is incorrect"),
                () -> assertTrue(res, "The value is incorrect")
        );
    }
}
