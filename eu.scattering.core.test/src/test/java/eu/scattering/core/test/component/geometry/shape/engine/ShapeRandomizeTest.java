package eu.scattering.core.test.component.geometry.shape.engine;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.extension.Producer;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.test.TestHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("ShapeRandom")
public class ShapeRandomizeTest {

    @Test
    @DisplayName("Attach linear (single)")
    void attachLinearSingle() {
        FSphere fSphereRef = factory.getFSphere();
        FSphere fSphereArg = TestHelper.getRandFSphere();

        boolean results = factory.getFRandEngine().attachLinear(fSphereRef, fSphereArg);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fSphereArg))
        );
    }

    @Test
    @DisplayName("Attach linear (single) - same element")
    void attachLinearSingleSameElement() {
        FSphere fSphereRef = factory.getFSphere();

        boolean results = factory.getFRandEngine().attachLinear(fSphereRef, fSphereRef);

        Assertions.assertAll("Validate position",
                () -> assertFalse(results)
        );
    }

    @Test
    @DisplayName("Attach linear")
    void attachLinear() {
        FSphere fSphereRef = factory.getFSphere();
        FSphere fSphereCenter = TestHelper.getRandFSphere();

        Producer<FPoint> fPointProducer = factory.getFPointProducer()
                .withOnSphere(2)
                .withOnSphere(2.5)
                .withOnSphere(3)
                .withOnSphere(3.5);
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(fPointProducer, 1)
                .validateNoOverlap();

        FAssembly<FSphere> fAssembly = factory.getFAssembly(fSphereProducer.getListFixed(10));
        fAssembly.register(fSphereCenter);

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fAssembly.translate(offset);

        boolean results = factory.getFRandEngine().attachLinear(fSphereRef, fSphereCenter, fAssembly, 100);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fAssembly) >= 1),
                () -> assertEquals(0, fSphereRef.overlaps(fAssembly))
        );
    }

    @Test
    @DisplayName("Attach spherical (single)")
    void attachSphericalSingle() {
        FSphere fSphereRef = factory.getFSphere(factory.getFRand().nextDoubleInShell(8.01, 11.99));
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(10));

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fSphereRef.translate(offset);
        fSphereArg.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachSpherical(fSphereRef, fSphereArg, offset.getD0(), offset.getD1(), offset.getD2());

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fSphereArg))
        );
    }

    @Test
    @DisplayName("Attach spherical (single) with FPoint")
    void attachSphericalSingleWithFPoint() {
        FSphere fSphereRef = factory.getFSphere(factory.getFRand().nextDoubleInShell(8.01, 11.99));
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(10));

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fSphereRef.translate(offset);
        fSphereArg.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachSpherical(fSphereRef, fSphereArg, factory.getFPoint(offset));

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fSphereArg))
        );
    }

    @Test
    @DisplayName("Attach spherical (single) with FPos3D")
    void attachSphericalSingleWithFPos3D() {
        FSphere fSphereRef = factory.getFSphere(factory.getFRand().nextDoubleInShell(8.01, 11.99));
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(10));

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fSphereRef.translate(offset);
        fSphereArg.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachSpherical(fSphereRef, fSphereArg, offset);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fSphereArg))
        );
    }

    @Test
    @DisplayName("Attach spherical")
    void attachSpherical() {
        FSphere fSphereRef = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(5));
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(5));

        Producer<FPoint> fPointProducer = factory.getFPointProducer().withInSphere(10);
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(fPointProducer, 1)
                .validateNoOverlap();

        FAssembly<FSphere> fAssembly = factory.getFAssembly(fSphereProducer.getListFixed(50));

        fAssembly.register(fSphereRef);
        fAssembly.register(fSphereArg);

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fAssembly.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachSpherical(fSphereRef, fSphereArg, offset.getD0(), offset.getD1(), offset.getD2(), fAssembly, 100);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fAssembly) >= 1),
                () -> assertEquals(0, fSphereRef.overlaps(fAssembly))
        );
    }

    @Test
    @DisplayName("Attach spherical with FPoint")
    void attachSphericalWithFPoint() {
        FSphere fSphereRef = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(5));
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(5));

        Producer<FPoint> fPointProducer = factory.getFPointProducer().withInSphere(10);
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(fPointProducer, 1)
                .validateNoOverlap();

        FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProducer.getListFixed(20));

        fAssembly.register(fSphereRef);
        fAssembly.register(fSphereArg);

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fAssembly.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachSpherical(fSphereRef, fSphereArg, factory.getFPoint(offset), fAssembly, 100);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fAssembly) >= 1),
                () -> assertEquals(0, fSphereRef.overlaps(fAssembly))
        );
    }

    @Test
    @DisplayName("Attach spherical with FPos3D")
    void attachSphericalWithFPos3D() {
        FSphere fSphereRef = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(5));
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleOnSphere(5));

        Producer<FPoint> fPointProducer = factory.getFPointProducer().withInSphere(10);
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(fPointProducer, 1)
                .validateNoOverlap();

        FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProducer.getListFixed(50));

        fAssembly.register(fSphereRef);
        fAssembly.register(fSphereArg);

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fAssembly.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachSpherical(fSphereRef, fSphereArg, offset, fAssembly, 100);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fAssembly) >= 1),
                () -> assertEquals(0, fSphereRef.overlaps(fAssembly))
        );
    }

    @Test
    @DisplayName("Attach linear and spherical")
    void attachLinearAndSpherical() {
        FSphere fSphereRef = factory.getFSphere();
        FSphere fSphereArg = factory.getFSphere(factory.getFRand().nextDoubleInSphere(5));

        Producer<FPoint> fPointProducer = factory.getFPointProducer().withInSphere(10);
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(fPointProducer, 1)
                .validateNoOverlap();

        FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProducer.getListFixed(50));

        fAssembly.register(fSphereRef);
        fAssembly.register(fSphereArg);

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fAssembly.translate(offset);

        boolean results = factory.getFRandEngine()
                .attachLinearAndSpherical(fSphereRef, fSphereArg, fAssembly, 100);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fAssembly) >= 1),
                () -> assertEquals(0, fSphereRef.overlaps(fAssembly))
        );
    }

    @Test
    @DisplayName("Project")
    void project() {
        FSphere fSphereRef = factory.getFSphere();

        Producer<FPoint> fPointProducer = factory.getFPointProducer().withInSphere(10);
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(fPointProducer, 1)
                .validateNoOverlap();

        FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProducer.getListFixed(50));

        fAssembly.register(fSphereRef);

        FPos3D offset = factory.getFRand().nextDoubleInSphere(100);

        fAssembly.translate(offset);

        boolean results = factory.getFRandEngine()
                .project(fSphereRef, factory.getFSphere(offset, 10), fAssembly, 100);

        Assertions.assertAll("Validate position",
                () -> assertTrue(results),
                () -> assertTrue(fSphereRef.touches(fAssembly) >= 1),
                () -> assertEquals(0, fSphereRef.overlaps(fAssembly))
        );
    }
}
