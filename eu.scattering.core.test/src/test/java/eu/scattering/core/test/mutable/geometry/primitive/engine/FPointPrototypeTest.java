package eu.scattering.core.test.mutable.geometry.primitive.engine;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointPrototype")
public class FPointPrototypeTest {

    @Test
    @DisplayName("Apply with fixed state")
    void applyWithFixedState() {
        FPoint fPoint = factory.getFPoint(0, 0, 0);

        List<Double> intermediate = new ArrayList<>();

        FPoint fPointRes = proto.applyWithFixedState(fPoint,
                p -> intermediate.add(p.set(1, 2, 3).getMagnitudeP2()));

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(0, fPoint.getX(), "The X value is incorrect"),
                () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect"),
                () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                () -> assertEquals(14, intermediate.get(0), epsilon, "The value is incorrect"),
                () -> assertSame(fPoint, fPointRes, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Apply with fixed length")
    void applyWithFixedLength() {
        FPoint fPoint = factory.getFPoint(1, 0, 0);

        FPoint fPointRes = proto.applyWithFixedMagnitude(fPoint,
                p -> p.set(-10, 0, 0));

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(-1, fPoint.getX(), "The X value is incorrect"),
                () -> assertEquals(0, fPoint.getY(), "The Y value is incorrect"),
                () -> assertEquals(0, fPoint.getZ(), "The Z value is incorrect"),
                () -> assertSame(fPoint, fPointRes, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with double (fixed state)")
    void terminateWithDoubleFixedState() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);

        double res = proto.toDoubleWithFixedState(fPoint, p -> {
            p.reflectThroughCenter();
            return p.getX() + p.getY() + p.getZ();
        });

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(1, fPoint.getX(), "The X value is incorrect"),
                () -> assertEquals(2, fPoint.getY(), "The Y value is incorrect"),
                () -> assertEquals(3, fPoint.getZ(), "The Z value is incorrect"),
                () -> assertEquals(-6, res, "The value is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with boolean (fixed state)")
    void terminateWithBooleanFixedState() {
        FPoint fPoint = factory.getFPoint(1, 2, 3);

        boolean res = proto.toBooleanWithFixedState(fPoint, p -> {
            p.reflectThroughCenter();
            return p.getX() + p.getY() + p.getZ() == -6;
        });

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(1, fPoint.getX(), "The X value is incorrect"),
                () -> assertEquals(2, fPoint.getY(), "The Y value is incorrect"),
                () -> assertEquals(3, fPoint.getZ(), "The Z value is incorrect"),
                () -> assertTrue(res, "The value is incorrect")
        );
    }
}
