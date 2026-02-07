package eu.scattering.core.test.component.aggregate.aspect;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.storage.transfer.position.p2.variants.FPairPos3D;
import eu.scattering.core.design.transfer.complex.FRotQt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FAggregateRotation")
public class FAggregateRotateTest {

    @Test
    @DisplayName("Rotate Rg (simple)")
    void rotateRgWithFVectorSimple() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FVector fVectorRef = factory.getFVector(0, 1, 0);

        FAggregate results = rot.rotRgAround(fAggregate, fVectorRef, Math.PI * 0.5);

        assertAll("Validate rotation",
            () -> assertSame(fAggregate, results),
            () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
            () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Rg (simple, negative)")
    void rotateRgWithFVectorSimpleNegative() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FVector fVectorRef = factory.getFVector(0, 1, 0);

        FAggregate results = rot.rotRgAround(fAggregate, fVectorRef, -Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, 1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, 2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Rg (throw IllegalArgumentException)")
    void rotateRgWithFVectorThrowIllegalArgumentException() {
        Shape fSphereA = factory.getFSphere(1, 2, 3);
        Shape fSphereB = factory.getFSphere(4, 5, 6);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FVector fVectorRef = factory.getFVector();

        double angle = Math.abs(rand.nextDouble() % Math.PI);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> rot.rotRgAround(fAggregate, fVectorRef, angle),
                "The direction of the provided FVector is not defined");
    }

    @Test
    @DisplayName("Rotate Rg with primitives")
    void rotateRgWithPrimitives() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FAggregate results = rot.rotRgAround(fAggregate, 0, 0, 0, 0, 1, 0, Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Rg with FPairPos3D")
    void rotateRgWithFPairPos3D() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FPairPos3D fPairPos3D = factory.getFPairPos3D(0, 0, 0, 0, 1, 0);

        FAggregate results = rot.rotRgAround(fAggregate, fPairPos3D, Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Qt with FVector (simple)")
    void rotateQtWithFVectorSimple() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FVector fVectorRef = factory.getFVector(0, 1, 0);

        FAggregate results = rot.rotQtAround(fAggregate, fVectorRef, Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Qt with FVector (simple, negative)")
    void rotateQtWithFVectorSimpleNegative() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FVector fVectorRef = factory.getFVector(0, 1, 0);

        FAggregate results = rot.rotQtAround(fAggregate, fVectorRef, -Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, 1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, 2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Qt with primitives")
    void rotateQtWithPrimitives() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FAggregate results = rot.rotQtAround(fAggregate, 0, 0, 0, 0, 1, 0, Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate Qt with FPairPos3D")
    void rotateQtWithFPairPos3D() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FPairPos3D fPairPos3D = factory.getFPairPos3D(0, 0, 0, 0, 1, 0);

        FAggregate results = rot.rotQtAround(fAggregate, fPairPos3D, Math.PI * 0.5);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }

    @Test
    @DisplayName("Rotate with FRotQt")
    void rotateWithFRotQt() {
        Shape fSphereA = factory.getFSphere(-1, 1, 0);
        Shape fSphereB = factory.getFSphere(-2, 2, 0);

        FAggregate fAggregate = factory.getRefFAggregate(List.of(fSphereA, fSphereB));

        FRotQt qt = factory.getFRot().getRotQt(factory.getFVector(0, 2, 0).toFPairPos3D(), Math.PI * 0.5);

        FAggregate results = rot.rotQt(fAggregate, qt);

        assertAll("Validate rotation",
                () -> assertSame(fAggregate, results),
                () -> assertTrue(factory.getFSphere(0, 1, -1).isSimilar(fSphereA)),
                () -> assertTrue(factory.getFSphere(0, 2, -2).isSimilar(fSphereB))
        );
    }
}
