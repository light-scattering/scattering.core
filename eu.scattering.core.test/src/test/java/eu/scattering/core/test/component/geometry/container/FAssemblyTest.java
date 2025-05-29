package eu.scattering.core.test.component.geometry.container;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FAssembly")
public class FAssemblyTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FAssembly<FSphere> fAssembly = factory.getFAssembly();

        Assertions.assertAll("Validate FAssembly",
                () -> assertEquals(0, fAssembly.explode().size(),
                        "The number of FPoints is incorrect")
        );
    }

    @Test
    @DisplayName("Register elements")
    void registerElements() {
        FAssembly<FVector> fAssembly = factory.getFAssembly();

        FVector fVectorA = factory.getFVector(-1, -2, -3, 4, 5, 6);
        FVector fVectorB = factory.getFVector(-6, -5, -4, 3, 2, 1);

        var registerA = fAssembly.register(fVectorA);
        var registerB = fAssembly.register(fVectorB);

        var registerRedundant = fAssembly.register(fVectorA);

        Assertions.assertAll("Validate FAssembly",
                () -> assertTrue(registerA,
                        "The addition of FVector A should be successful"),
                () -> assertTrue(registerB,
                        "The addition of FVector B should be successful"),
                () -> assertFalse(registerRedundant,
                        "The addition of FVector is redundant"),
                () -> assertEquals(4, fAssembly.explode().size(),
                        "The number of FPoints is incorrect")
        );
    }

    @Test
    @DisplayName("Register elements (duplicate points)")
    void registerElementsDuplicatePoints() {
        FAssembly<FVector> fAssembly = factory.getFAssembly();

        FPoint base = factory.getFPoint(1,2, 3);

        FVector fVectorA = factory.getRefFVector(base, factory.getFPoint(4, 5, 6));
        FVector fVectorB = factory.getRefFVector(base, factory.getFPoint(6, 5, 4));

        fAssembly.register(fVectorA);
        fAssembly.register(fVectorB);

        Assertions.assertAll("Validate FAssembly",
                () -> assertEquals(3, fAssembly.explode().size(),
                        "The number of FPoints is incorrect")
        );
    }

    @Test
    @DisplayName("Register elements (various types)")
    void registerElementsVariousTypes() {
        FAssembly<Geometry> fAssembly = factory.getFAssembly();

        FVector fVector = factory.getFVector(1, 2, 3, 4, 5, 6);
        FPoint fPoint = factory.getFPoint(7, 8, 9);

        var registerA = fAssembly.register(fVector);
        var registerB = fAssembly.register(fPoint);

        Assertions.assertAll("Validate FAssembly",
                () -> assertTrue(registerA,
                        "The addition of FVector A should be successful"),
                () -> assertTrue(registerB,
                        "The addition of FVector B should be successful"),
                () -> assertEquals(3, fAssembly.explode().size(),
                        "The number of FPoints is incorrect")
        );
    }

    @Test
    @DisplayName("Apply element")
    void applyElement() {
        FAssembly<FVector> fAssembly = factory.getFAssembly();

        FVector fVectorX = factory.getFVector(1, 0, 0);
        FVector fVectorY = factory.getFVector(0, 1, 0);
        FVector fVectorZ = factory.getFVector(0, 0, 1);

        fAssembly.register(fVectorX);
        fAssembly.register(fVectorY);
        fAssembly.register(fVectorZ);

        fAssembly.applyGeometry(e -> e.shiftForward(1));

        Assertions.assertAll("Validate FAssembly",
                () -> assertEquals(6, fAssembly.explode().size(),
                        "The number of FPoints is incorrect")
        );
    }

    @Test
    void something() {
        FAssembly<FVector> fAssembly = factory.getFAssembly();

        FVector fVectorX = factory.getFVector(1, 0, 0);
        FVector fVectorY = factory.getFVector(0, 1, 0);
        FVector fVectorZ = factory.getFVector(0, 0, 1);

        fAssembly.register(fVectorX);
        fAssembly.register(fVectorY);
        fAssembly.register(fVectorZ);

        FAssembly<FVector> fAssembly2 = fAssembly.copy();

        int i = 5;
    }
}
