package eu.scattering.core.test.mutable.geometry.primitive.engine;

import eu.scattering.core.design.mutable.geometry.primitive.vector.FVector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorPrototype")
public class FVectorPrototypeTest {
    @Test
    @DisplayName("Apply with fixed state")
    void applyWithFixedState() {
        FVector fVector = factory.getFVector();

        List<Double> intermediate = new ArrayList<>();

        FVector fVectorRes = proto.applyWithFixedState(fVector,
                p -> intermediate.add(p.set(1, 2, 3, 4, 5, 6).getMagnitudeP2()));

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(0, fVector.getBaseX(), "The base X value is incorrect"),
                () -> assertEquals(0, fVector.getBaseY(), "The base Y value is incorrect"),
                () -> assertEquals(0, fVector.getBaseZ(), "The base Z value is incorrect"),
                () -> assertEquals(0, fVector.getHeadX(), "The head X value is incorrect"),
                () -> assertEquals(0, fVector.getHeadY(), "The head Y value is incorrect"),
                () -> assertEquals(0, fVector.getHeadZ(), "The head Z value is incorrect"),
                () -> assertEquals(1, intermediate.size(), "The size of the array list is incorrect"),
                () -> assertEquals(27, intermediate.get(0), epsilon, "The value is incorrect"),
                () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Apply with fixed length")
    void applyWithFixedLength() {
        FVector fVector = factory.getFVector(1, 0, 0);

        FVector fVectorRes = proto.applyWithFixedMagnitude(fVector,
                p -> p.setHead(-10, 0, 0));

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(0, fVector.getBaseX(), "The base X value is incorrect"),
                () -> assertEquals(0, fVector.getBaseY(), "The base Y value is incorrect"),
                () -> assertEquals(0, fVector.getBaseZ(), "The base Z value is incorrect"),
                () -> assertEquals(-1, fVector.getHeadX(), "The head X value is incorrect"),
                () -> assertEquals(0, fVector.getHeadY(), "The head Y value is incorrect"),
                () -> assertEquals(0, fVector.getHeadZ(), "The head Z value is incorrect"),
                () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Apply with centered position")
    void applyWithCenteredPosition() {
        FVector fVector = factory.getFVector(5, 0, 0, 9, 0, 0);

        FVector fVectorRes = proto.applyWithCenteredPosition(fVector,
                p -> p.getRefHead().reflect(factory.getFPoint(9, 0, 0)));

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(5, fVector.getBaseX(), "The base X value is incorrect"),
                () -> assertEquals(0, fVector.getBaseY(), "The base Y value is incorrect"),
                () -> assertEquals(0, fVector.getBaseZ(), "The base Z value is incorrect"),
                () -> assertEquals(19, fVector.getHeadX(), "The head X value is incorrect"),
                () -> assertEquals(0, fVector.getHeadY(), "The head Y value is incorrect"),
                () -> assertEquals(0, fVector.getHeadZ(), "The head Z value is incorrect"),
                () -> assertSame(fVector, fVectorRes, "The reference is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with double (fixed state)")
    void terminateWithDoubleFixedState() {
        FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

        double res = proto.toDoubleWithFixedState(fVector, p -> {
            p.reflect(factory.getFPoint());
            return p.getMagnitudeP2();
        });

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(1, fVector.getBaseX(), "The base X value is incorrect"),
                () -> assertEquals(2, fVector.getBaseY(), "The base Y value is incorrect"),
                () -> assertEquals(3, fVector.getBaseZ(), "The base Z value is incorrect"),
                () -> assertEquals(4, fVector.getHeadX(), "The head X value is incorrect"),
                () -> assertEquals(5, fVector.getHeadY(), "The head Y value is incorrect"),
                () -> assertEquals(6, fVector.getHeadZ(), "The head Z value is incorrect"),
                () -> assertEquals(27, res, "The value is incorrect")
        );
    }

    @Test
    @DisplayName("Terminate with boolean (fixed state)")
    void terminateWithBooleanFixedState() {
        FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);

        boolean res = proto.toBooleanWithFixedState(fVector, p -> {
            p.reflect(factory.getFPoint());
            return p.isNearZeroLength();
        });

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(1, fVector.getBaseX(), "The base X value is incorrect"),
                () -> assertEquals(2, fVector.getBaseY(), "The base Y value is incorrect"),
                () -> assertEquals(3, fVector.getBaseZ(), "The base Z value is incorrect"),
                () -> assertEquals(4, fVector.getHeadX(), "The head X value is incorrect"),
                () -> assertEquals(5, fVector.getHeadY(), "The head Y value is incorrect"),
                () -> assertEquals(6, fVector.getHeadZ(), "The head Z value is incorrect"),
                () -> assertFalse(res, "The value is incorrect")
        );
    }
}
