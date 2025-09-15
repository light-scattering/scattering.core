package eu.scattering.core.test.component.geometry.shape;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
