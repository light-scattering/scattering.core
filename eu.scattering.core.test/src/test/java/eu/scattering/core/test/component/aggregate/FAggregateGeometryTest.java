package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FAggregate geometry")
public class FAggregateGeometryTest {

    @Test
    @DisplayName("Construct simple 1D")
    void constructSimple1D() {
        FAggregate fAggregate = factory.getFAggregateGeo1d(11, 2);

        FPairPos3D boundary = fAggregate.getBoundary();

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(11, fAggregate.getRefParticles().size()),
                () -> assertEquals(2, fAggregate.getParticleRadius().mean()),
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
        FAggregate fAggregate = factory.getFAggregateGeo2d(5, 7, 2);

        FPairPos3D boundary = fAggregate.getBoundary();

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(5 * 7, fAggregate.getRefParticles().size()),
                () -> assertEquals(2, fAggregate.getParticleRadius().mean()),
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
        FAggregate fAggregate = factory.getFAggregateGeo3d(5, 7, 9, 2);

        FPairPos3D boundary = fAggregate.getBoundary();

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(5 * 7 * 9, fAggregate.getRefParticles().size()),
                () -> assertEquals(2, fAggregate.getParticleRadius().mean()),
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
        FAggregate fAggregate = factory.getFAggregateGeoFullCircle(20, 1);

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(fAggregate.getRefParticles().size() > 1000),
                () -> assertTrue(fAggregate.getLinearOverlapFactor() < EPSILON)
        );
    }

    @Test
    @DisplayName("Construct full circle - Fail")
    void constructFullCircleFail() {

        assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateGeoFullCircle(0, 1));
        assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateGeoFullCircle(5, -1));
    }

    @Test
    @DisplayName("Construct full sphere")
    void constructFullSphere() {
        FAggregate fAggregate = factory.getFAggregateGeoFullSphere(10, 1);

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(fAggregate.getRefParticles().size() > 1000),
                () -> assertTrue(fAggregate.getLinearOverlapFactor() < EPSILON)
        );
    }

    @Test
    @DisplayName("Construct full sphere - Fail")
    void constructFullSphereFail() {

        assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateGeoFullSphere(0, 1));
        assertThrows(IllegalArgumentException.class, () -> factory.getFAggregateGeoFullSphere(5, -1));
    }
}
