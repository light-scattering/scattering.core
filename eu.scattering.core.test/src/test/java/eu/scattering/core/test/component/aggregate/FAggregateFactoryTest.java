package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.*;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("FAggregate")
public class FAggregateFactoryTest {

    @Nested
    @Tag("Geometry")
    @DisplayName("Module - Geometry")
    class ModuleGeometryTest {

        @Test
        @DisplayName("Construct simple 1D")
        void constructSimple1D() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().d1(11, 2);

            FPairPos3D boundary = fAggregate.getBoundary();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(11, fAggregate.getRefParticles().size()),
                    () -> assertEquals(2, fAggregate.getFStatParticleRadius().mean()),
                    () -> assertEquals(-22, boundary.getPosA().getD0(), 1E-1),
                    () -> assertEquals(-2, boundary.getPosA().getD1(), 1E-1),
                    () -> assertEquals(-2, boundary.getPosA().getD2(), 1E-1),
                    () -> assertEquals(22, boundary.getPosB().getD0(), 1E-1),
                    () -> assertEquals(2, boundary.getPosB().getD1(), 1E-1),
                    () -> assertEquals(2, boundary.getPosB().getD2(), 1E-1)
            );
        }

        @Test
        @DisplayName("Construct simple 2D")
        void constructSimple2D() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().d2(5, 7, 2);

            FPairPos3D boundary = fAggregate.getBoundary();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(5 * 7, fAggregate.getRefParticles().size()),
                    () -> assertEquals(2, fAggregate.getFStatParticleRadius().mean()),
                    () -> assertEquals(-10, boundary.getPosA().getD0(), 1E-1),
                    () -> assertEquals(-14, boundary.getPosA().getD1(), 1E-1),
                    () -> assertEquals(-2, boundary.getPosA().getD2(), 1E-1),
                    () -> assertEquals(10, boundary.getPosB().getD0(), 1E-1),
                    () -> assertEquals(14, boundary.getPosB().getD1(), 1E-1),
                    () -> assertEquals(2, boundary.getPosB().getD2(), 1E-1)
            );
        }

        @Test
        @DisplayName("Construct simple 3D")
        void constructSimple3D() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().d3(5, 7, 9, 2);

            FPairPos3D boundary = fAggregate.getBoundary();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(5 * 7 * 9, fAggregate.getRefParticles().size()),
                    () -> assertEquals(2, fAggregate.getFStatParticleRadius().mean()),
                    () -> assertEquals(-10, boundary.getPosA().getD0(), 1E-1),
                    () -> assertEquals(-14, boundary.getPosA().getD1(), 1E-1),
                    () -> assertEquals(-18, boundary.getPosA().getD2(), 1E-1),
                    () -> assertEquals(10, boundary.getPosB().getD0(), 1E-1),
                    () -> assertEquals(14, boundary.getPosB().getD1(), 1E-1),
                    () -> assertEquals(18, boundary.getPosB().getD2(), 1E-1)
            );
        }

        @Test
        @DisplayName("Construct full circle")
        void constructFullCircle() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().fullCircle(20, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().size() > 1000),
                    () -> assertTrue(fAggregate.getLinearOverlapFactor() < EPSILON)
            );
        }

        @Test
        @DisplayName("Construct full circle - Fail")
        void constructFullCircleFail() {

            assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateContext().geometry().fullCircle(0, 1));
            assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateContext().geometry().fullCircle(5, -1));
        }

        @Test
        @DisplayName("Construct full sphere")
        void constructFullSphere() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().fullSphere(10, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().size() > 1000),
                    () -> assertTrue(fAggregate.getLinearOverlapFactor() < EPSILON)
            );
        }

        @Test
        @DisplayName("Construct full sphere - Fail")
        void constructFullSphereFail() {

            assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateContext().geometry().fullSphere(0, 1));
            assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateContext().geometry().fullSphere(5, -1));
        }
    }

    @Nested
    @Tag("Template")
    @DisplayName("Module - Template")
    class ModuleTemplateTest {

        @Test
        @DisplayName("Construct monodisperse")
        void constructMono() {
            ScatFactory factory = FactoryDef.create(1766972568500L);
            System.out.println("Debug Seed Check: " + factory.getFRand().nextDouble());
            FAggregate fAggregate = factory.getFAggregateContext().template().monodisperse(100, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(100, fAggregate.getRefParticles().size(),
                            "The number of particles is incorrect"),
                    () -> assertEquals(1, fAggregate.getFStatParticleRadius().mean(),
                            epsilon, "The particle radius is erroneous")
            );
        }

        @Test
        @DisplayName("Construct polydisperse")
        void constructPoly() {
            ScatFactory factory = FactoryDef.create(1766972568500L);
            System.out.println("Debug Seed Check: " + factory.getFRand().nextDouble());
            FAggregate fAggregate = factory.getFAggregateContext().template().polydisperse(100, 10, 1, 8);

            String model = factory.getExportAspect().getFAggregateContext().toNGSolve(fAggregate);

            for (Shape shape : fAggregate.getRefParticles()) {
                assertTrue(shape.getRadius() > 8);
            }
double rand = factory.getFRand().nextDouble();
            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(100, fAggregate.getRefParticles().size(),
                            "The number of particles is incorrect"),
                    () -> assertEquals(10, fAggregate.getFStatParticleRadius().mean(),
                            1, "The particle avg radius is erroneous"),
                    () -> assertEquals(1, fAggregate.getFStatParticleRadius().std(true),
                            0.1, "The particle std radius is erroneous")
            );
        }
    }
}
