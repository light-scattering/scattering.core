package eu.scattering.core.test.mutables.geometry.construct.engine;

import eu.scattering.core.design.mutables.geometry.construct.ray.FRay;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.test.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.factory;
import static eu.scattering.core.test.Configuration.rot;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FRayRotation")
public class FRayRotationTest {

    @Test
    @DisplayName("Rotate (simple)")
    void rotateSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FRay fRay = factory.getRefFRay(factory.getFVector(0, -1, 0, 0, 1, 0));

        rot.rotQtAround(fRay, fVector, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate (simple, negative)")
    void rotateSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FRay fRay = factory.getRefFRay(factory.getFVector(0, -1, 0, 0, 1, 0));

        rot.rotQtAround(fRay, fVector, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate (below base)")
    void rotateBelowBase() {
        FVector fVector = factory.getFVector(-2, 2, 0, 0, 2, 0);
        FRay fRay = factory.getRefFRay(factory.getFVector(-1, 0, 0, 1, 0, 0));

        rot.rotQtAround(fRay, fVector, Math.PI);

        assertTrue(fVector.isSimilar(-2, 2, 0, 0, -2, 0),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate (above head)")
    void rotateAboveHead() {
        FVector fVector = factory.getFVector(0, 2, 0, 2, 2, 0);
        FRay fRay = factory.getRefFRay(factory.getFVector(-1, 0, 0, 1, 0, 0));

        rot.rotQtAround(fRay, fVector, Math.PI);

        assertTrue(fVector.isSimilar(0, -2, 0, 2, -2, 0),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate  (throw IllegalStateException)")
    void rotateThrowIllegalStateException() {
        FVector fVector = TestHelper.getRandFVector();
        FRay fRay = factory.getRefFRay(factory.getFVector());

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.rotQtAround(fRay, fVector, Math.PI * 0.5),
                "The direction of the FRay is not defined");
    }
}
