package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
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
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(0, 0, 0, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(1, 1, 1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(-1, 1, 1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(1, -1, 1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(1, 1, -1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(-1, -1, 1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(-1, 1, -1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(1, -1, -1, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(1, -1, -1, 1), 0, 0, 0, 2))
        );
    }

    @Test
    @DisplayName("Box intersection - Positive")
    void boxIntersectionPositive() {
        FSphereHelper helper = factory.getFSphereHelper();

        Assertions.assertAll("Validate intersection",
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(2 - epsilon, 0, 0, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(-2 + epsilon, 0, 0, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(0, 2 - epsilon, 0, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(0, -2 + epsilon, 0, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(0, 0, 2 - epsilon, 1), 0, 0, 0, 2)),
                () -> assertTrue(helper.isIntersecting(
                        factory.getFSphere(0, 0, -2 + epsilon, 1), 0, 0, 0, 2))
        );
    }

    @Test
    @DisplayName("Box intersection - Negative")
    void boxIntersectionNegative() {
        FSphereHelper helper = factory.getFSphereHelper();

        Assertions.assertAll("Validate intersection",
                () -> assertFalse(helper.isIntersecting(
                        factory.getFSphere(2 + epsilon, 0, 0, 1), 0, 0, 0, 2)),
                () -> assertFalse(helper.isIntersecting(
                        factory.getFSphere(-2 - epsilon, 0, 0, 1), 0, 0, 0, 2)),
                () -> assertFalse(helper.isIntersecting(
                        factory.getFSphere(0, 2 + epsilon, 0, 1), 0, 0, 0, 2)),
                () -> assertFalse(helper.isIntersecting(
                        factory.getFSphere(0, -2 - epsilon, 0, 1), 0, 0, 0, 2)),
                () -> assertFalse(helper.isIntersecting(
                        factory.getFSphere(0, 0, 2 + epsilon, 1), 0, 0, 0, 2)),
                () -> assertFalse(helper.isIntersecting(
                        factory.getFSphere(0, 0, -2 - epsilon, 1), 0, 0, 0, 2))
        );
    }
}
