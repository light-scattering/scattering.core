package eu.scattering.core.test.mutables.number.engine;

import eu.scattering.core.design.mutables.number.complex.FComplex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Configuration.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FComplexPrototype")
public class FComplexPrototypeTest {

    @Test
    @DisplayName("Apply with fixed state")
    void applyWithFixedState() {
        FComplex fComplex = factory.getFComplex(0, 0);

        List<Double> intermediate = new ArrayList<>();

        FComplex fComplexRes = proto.applyWithFixedState(fComplex,
                p -> intermediate.add(p.setRe(2).setIm(2).getMagnitude()));

        Assertions.assertAll("Validate FComplex values",
                () -> assertEquals(0, fComplex.getRe(), "The 're' value is incorrect"),
                () -> assertEquals(0, fComplex.getIm(), "The 'im' value is incorrect"),
                () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                () -> assertEquals(2 * Math.sqrt(2), intermediate.get(0), epsilon, "The value is incorrect"),
                () -> assertSame(fComplexRes, fComplex, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with double (fixed state)")
    void terminateWithDoubleFixedState() {
        FComplex fComplex = factory.getFComplex(1, 2);

        double res = proto.toDoubleWithFixedState(fComplex, p -> {
            p.add(3, 4);
            return p.getRe() + p.getIm();
        });

        Assertions.assertAll("Validate FComplex values",
                () -> assertEquals(1, fComplex.getRe(), "The 're' value is incorrect"),
                () -> assertEquals(2, fComplex.getIm(), "The 'im' value is incorrect"),
                () -> assertEquals(10, res, "The value is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with boolean (fixed state)")
    void terminateWithBooleanFixedState() {
        FComplex fComplex = factory.getFComplex(1, 2);

        boolean res = proto.toBooleanWithFixedState(fComplex, p -> {
            p.add(3, 4);
            return p.getRe() + p.getIm() == 10;
        });

        Assertions.assertAll("Validate FComplex values",
                () -> assertEquals(1, fComplex.getRe(), "The 're' value is incorrect"),
                () -> assertEquals(2, fComplex.getIm(), "The 'im' value is incorrect"),
                () -> assertTrue(res, "The value is incorrect")
        );
    }
}
