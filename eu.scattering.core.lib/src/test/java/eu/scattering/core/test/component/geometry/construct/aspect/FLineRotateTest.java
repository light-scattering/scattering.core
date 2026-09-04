package eu.scattering.core.test.component.geometry.construct.aspect;

import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.test.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.TestConfig.factory;
import static eu.scattering.core.test.TestConfig.rot;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FLineRotation")
public class FLineRotateTest {

    @Test
    @DisplayName("Rotate Qt (simple)")
    void rotateQtSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(0, 1, 0));

        rot.mutate().rotQtAround(fVector, fLine, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Qt (simple, negative)")
    void rotateQtSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(0, 1, 0));

        rot.mutate().rotQtAround(fVector, fLine, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Qt (below base)")
    void rotateQtBelowBase() {
        FVector fVector = factory.getFVector(-2, 2, 0, 0, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(-1, 0, 0, 1, 0, 0));

        rot.mutate().rotQtAround(fVector, fLine, Math.PI);

        assertTrue(fVector.isSimilar(-2, -2, 0, 0, -2, 0),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Qt (above head)")
    void rotateQtAboveHead() {
        FVector fVector = factory.getFVector(0, 2, 0, 2, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(-1, 0, 0, 1, 0, 0));

        rot.mutate().rotQtAround(fVector, fLine, Math.PI);

        assertTrue(fVector.isSimilar(0, -2, 0, 2, -2, 0),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Qt (throw IllegalStateException)")
    void rotateQtThrowIllegalStateException() {
        FVector fVector = TestHelper.getRandFVector();
        FLine fLine = factory.getRefFLine(factory.getFVector());

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.mutate().rotQtAround(fVector, fLine, Math.PI * 0.5),
                "The direction of the FLine is not defined");
    }

    @Test
    @DisplayName("Rotate Rg (simple)")
    void rotateRgSimple() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(0, 1, 0));

        rot.mutate().rotRgAround(fVector, fLine, Math.PI * 0.5);

        assertTrue(fVector.isSimilar(0, 1, -1, 0, 2, -2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (simple, negative)")
    void rotateRgSimpleNegative() {
        FVector fVector = factory.getFVector(-1, 1, 0, -2, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(0, 1, 0));

        rot.mutate().rotRgAround(fVector, fLine, -(Math.PI * 0.5));

        assertTrue(fVector.isSimilar(0, 1, 1, 0, 2, 2),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (below base)")
    void rotateRgBelowBase() {
        FVector fVector = factory.getFVector(-2, 2, 0, 0, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(-1, 0, 0, 1, 0, 0));

        rot.mutate().rotRgAround(fVector, fLine, Math.PI);

        assertTrue(fVector.isSimilar(-2, -2, 0, 0, -2, 0),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (above head)")
    void rotateRgAboveHead() {
        FVector fVector = factory.getFVector(0, 2, 0, 2, 2, 0);
        FLine fLine = factory.getRefFLine(factory.getFVector(-1, 0, 0, 1, 0, 0));

        rot.mutate().rotRgAround(fVector, fLine, Math.PI);

        assertTrue(fVector.isSimilar(0, -2, 0, 2, -2, 0),
                "The position of the rotated FVector is erroneous");
    }

    @Test
    @DisplayName("Rotate Rg (throw IllegalStateException)")
    void rotateRgThrowIllegalStateException() {
        FVector fVector = TestHelper.getRandFVector();
        FLine fLine = factory.getRefFLine(factory.getFVector());

        Assertions.assertThrows(IllegalStateException.class,
                () -> rot.mutate().rotRgAround(fVector, fLine, Math.PI * 0.5),
                "The direction of the FLine is not defined");
    }
}
