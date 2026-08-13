package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.TestConfig.epsilon;
import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FSphere helper")
public class FSphereHelperTest {

    @Test
    @DisplayName("Get volume radius")
    void getVolumeRadius() {
        double radius = 5;
        double volume = factory.getFSphereHelper().getVolume(5);

        assertEquals(radius, factory.getFSphereHelper().getVolumeRadius(volume), epsilon);
    }

    @Test
    @DisplayName("Get surface radius")
    void getSurfaceRadius() {
        double radius = 5;
        double surface = factory.getFSphereHelper().getSurface(5);

        assertEquals(radius, factory.getFSphereHelper().getSurfaceRadius(surface), epsilon);
    }

    @Test
    @DisplayName("Box intersection")
    void boxIntersection() {
        FSphereHelper helper = factory.getFSphereHelper();

        Assertions.assertAll("Validate intersection",
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, 0, 0, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(1, 1, 1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(-1, 1, 1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(1, -1, 1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(1, 1, -1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(-1, -1, 1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(-1, 1, -1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(1, -1, -1, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(1, -1, -1, 1), -1, -1, -1, 2))
        );
    }

    @Test
    @DisplayName("Box intersection - Positive")
    void boxIntersectionPositive() {
        FSphereHelper helper = factory.getFSphereHelper();

        Assertions.assertAll("Validate intersection",
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(2 - epsilon, 0, 0, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(-2 + epsilon, 0, 0, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, 2 - epsilon, 0, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, -2 + epsilon, 0, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, 0, 2 - epsilon, 1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, 0, -2 + epsilon, 1), -1, -1, -1, 2))
        );
    }

    @Test
    @DisplayName("Box intersection - Negative")
    void boxIntersectionNegative() {
        FSphereHelper helper = factory.getFSphereHelper();

        Assertions.assertAll("Validate intersection",
                () -> assertFalse(helper.intersectsCube(
                        factory.getFSphere(2 + epsilon, 0, 0, 1), -1, -1, -1, 2)),
                () -> assertFalse(helper.intersectsCube(
                        factory.getFSphere(-2 - epsilon, 0, 0, 1), -1, -1, -1, 2)),
                () -> assertFalse(helper.intersectsCube(
                        factory.getFSphere(0, 2 + epsilon, 0, 1), -1, -1, -1, 2)),
                () -> assertFalse(helper.intersectsCube(
                        factory.getFSphere(0, -2 - epsilon, 0, 1), -1, -1, -1, 2)),
                () -> assertFalse(helper.intersectsCube(
                        factory.getFSphere(0, 0, 2 + epsilon, 1), -1, -1, -1, 2)),
                () -> assertFalse(helper.intersectsCube(
                        factory.getFSphere(0, 0, -2 - epsilon, 1), -1, -1, -1, 2))
        );
    }

    @Test
    @DisplayName("Box intersection - Size")
    void boxIntersectionSize() {
        FSphereHelper helper = factory.getFSphereHelper();

        Assertions.assertAll("Validate intersection",
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, 0, 0, 0.1), -1, -1, -1, 2)),
                () -> assertTrue(helper.intersectsCube(
                        factory.getFSphere(0, 0, 0, 10), -1, -1, -1, 2))
        );
    }
}
