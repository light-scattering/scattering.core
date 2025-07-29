package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.vector.FVectorHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorHelper")
public class FVectorHelperTest {

    @Test
    @DisplayName("Is near zero")
    void isNearZero() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isNearZeroLength(1, 2, 3, 1 + 0.5 * epsilon, 2 + 0.5 * epsilon, 3 + 0.5 * epsilon),
                "The two FPoints should be at the same position");
    }

    @Test
    @DisplayName("Is near zero (fail)")
    void isNearZeroFail() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertFalse(helper.isNearZeroLength(1, 2, 3, 1 + 2 * epsilon, 2 + 2 * epsilon, 3 + 2 * epsilon),
                "The two FPoints should not be at the same position");
    }

    @Test
    @DisplayName("Get magnitude P2")
    void getMagnitudeP2() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertEquals(3, helper.getMagnitudeP2(1, 1, 1, 2, 2, 2),
                epsilon, "The squared magnitude is erroneous");
    }

    @Test
    @DisplayName("Get magnitude")
    void getMagnitude() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertEquals(Math.sqrt(3), helper.getMagnitude(1, 1, 1, 2, 2, 2),
                epsilon, "The magnitude is erroneous");
    }

    @Test
    @DisplayName("Is parallel")
    void isParallel() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isParallel(0, 0, 0, 2, 2, 2, 0, 0, 0, 4, 4, 4),
                "The two FVectors should be parallel");
    }

    @Test
    @DisplayName("Is parallel (fail)")
    void isParallelFail() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertFalse(helper.isParallel(0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 0),
                "The two FVectors should not be parallel");
    }

    @Test
    @DisplayName("Is anti-parallel")
    void isAntiParallel() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isAntiParallel(0, 0, 0, 2, 2, 2, 0, 0, 0, -4, -4, -4),
                "The two FVectors should be anti parallel");
    }

    @Test
    @DisplayName("Is anti-parallel (fail)")
    void isAntiParallelFail() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertFalse(helper.isAntiParallel(0, 0, 0, 2, 3, 2, 0, 0, 0, -4, -4, -4),
                "The two FVectors should not be anti parallel");
    }

    @Test
    @DisplayName("Is collinear")
    void isCollinear() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isCollinear(0, 0, 0, 2, 2, 2, 0, 0, 0, 4, 4, 4),
                "The two FVectors should be collinear");
    }

    @Test
    @DisplayName("Is collinear (opposite direction")
    void isCollinearOppositeDirection() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isCollinear(0, 0, 0, 2, 2, 2, 0, 0, 0, -4, -4, -4),
                "The two FVectors should be collinear");
    }

    @Test
    @DisplayName("Is collinear (fail)")
    void isCollinearFail() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertFalse(helper.isCollinear(0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 2, 0),
                "The two FVectors should not be collinear");
    }

    @Test
    @DisplayName("Is collinear (common)")
    void isCollinearCommon() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isCollinearBaseCommon(1, 1, 1, 2, 2, 2, -2, -2, -2),
                "The elements should be collinear");
    }

    @Test
    @DisplayName("Is collinear (common, fail)")
    void isCollinearCommonFail() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertFalse(helper.isCollinearBaseCommon(1, 1, 1, 2, 3, 2, -2, -2, -2),
                "The elements should be collinear");
    }

    @Test
    @DisplayName("Is collinear (common) with FPos3D")
    void isCollinearCommonWithFPos3D() {
        FVectorHelper helper = factory.getFVectorHelper();

        assertTrue(helper.isCollinearBaseCommon(1, 1, 1, factory.getFPos3D(2, 2, 2), factory.getFPos3D(-2, -2, -2)),
                "The elements should be collinear");
    }
}
