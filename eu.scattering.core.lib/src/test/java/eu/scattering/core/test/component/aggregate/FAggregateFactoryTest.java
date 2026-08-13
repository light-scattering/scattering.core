package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;
import org.junit.jupiter.api.*;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;
import static eu.scattering.core.test.TestConfig.epsilon;
import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FAggregate")
public class FAggregateFactoryTest {

    @Nested
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
        @DisplayName("Construct hexagonal pack 2D")
        void constructHexagonal2D() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().d2Hex(25, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().size() > 500),
                    () -> assertTrue(fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() < EPSILON)
            );
        }

        @Test
        @DisplayName("Construct hexagonal pack 3D")
        void constructHexagonal3D() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().d3Hex(10, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().size() > 500),
                    () -> assertTrue(fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() < EPSILON)
            );
        }

        @Test
        @DisplayName("Construct full circle")
        void constructFullCircle() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().fullCircle(20, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().size() > 1000),
                    () -> assertTrue(fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() < EPSILON)
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
                    () -> assertTrue(fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() < EPSILON)
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
    @DisplayName("Module - Template")
    class ModuleTemplateTest {

        @Test
        @DisplayName("Construct monodisperse")
        void constructMono() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(100, 1);

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
            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(100, 10, 1);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(100, fAggregate.getRefParticles().size(),
                            "The number of particles is incorrect"),
                    () -> assertTrue(fAggregate.getFStatParticleRadius().std(true) > 0,
                            "The particle radius should not be constant")
            );
        }

        @Test
        @DisplayName("Construct polydisperse (limited)")
        void constructPolyLimited() {
            FAggregate fAggregate = factory.getFAggregateContext().base().polydisperse(100, 10, 1, 1, 0.1);

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
