package eu.scattering.core.geometry;

import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import eu.scattering.core.helper.HelperRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.Configuration.jitter;
import static org.junit.jupiter.api.Assertions.*;

// ADD REFERENCE BLOCK

public class IFVectorTest2 {

    @Test
    @DisplayName("Get magnitude")
    void getMagnitude() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint();
        IFPoint fPointHead = FactoryGeometry.getIFPoint();
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        assertEquals(0, fVector.getMagnitude(), jitter, "The magnitude should be zero");

        fPointBase.set(1, 1, 1);
        fPointHead.set(2, 2, 2);

        assertEquals(Math.sqrt(3), fVector.getMagnitude(), jitter, "The magnitude is incorrect");

        fPointBase.set(HelperRandom.getTestPoint());
        fPointHead.set(HelperRandom.getTestPoint());

        double dimX = fVector.getDimX() * fVector.getDimX();
        double dimY = fVector.getDimY() * fVector.getDimY();
        double dimZ = fVector.getDimZ() * fVector.getDimZ();
        double magnitude = Math.sqrt(dimX + dimY + dimZ);

        assertEquals(magnitude, fVector.getMagnitude(), jitter, "The magnitude is incorrect");
    }

    @Test
    @DisplayName("Set magnitude")
    void setMagnitude() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(3, 3, 3);
        IFPoint fPointHead = FactoryGeometry.getIFPoint(5, 5, 5);
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        fVector.setMagnitude(Math.sqrt(3));

        assertAll("Validate IFPoint values",
                () -> assertEquals(3, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(3, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(3, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(4, fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(4, fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(4, fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );

        fPointBase.set(HelperRandom.getTestPoint());
        fPointHead.set(HelperRandom.getTestPoint());

        fVector.setMagnitude(1);

        assertEquals(1, fVector.getMagnitude(), jitter, "The magnitude is incorrect");
    }

    // Cannot be zero

    @Test
    @DisplayName("Set magnitude (throw IllegalArgumentException)")
    void setMagnitudeThrowIllegalArgumentException() {
        IFVector fVector = FactoryGeometry.getIFVector(FactoryGeometry.getIFPoint(), FactoryGeometry.getIFPoint());

        assertThrows(IllegalArgumentException.class, () -> fVector.setMagnitude(-1),
                "The magnitude must be a positive value");
    }

    @Test
    @DisplayName("Normalize")
    void normalize() {
        IFPoint fPointBase = HelperRandom.getTestPoint();
        IFPoint fPointHead = HelperRandom.getTestPoint();
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        fVector.normalize();

        assertEquals(1, fVector.getMagnitude(), jitter, "The magnitude is incorrect");
    }

    // Cannot be zero

    @Test
    @DisplayName("Reflect")
    void reflect() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 2, 3);
        IFPoint fPointHead = HelperRandom.getTestPoint();
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        IFPoint fPointRef = fPointHead.copy().sub(fPointBase).reflect().add(fPointBase);

        fVector.reflect();

        assertAll("Validate IFPoint values",
                () -> assertEquals(1, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(2, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(3, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(fPointRef.getX(), fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(fPointRef.getY(), fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(fPointRef.getZ(), fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Invert")
    void invert() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 2, 3);
        IFPoint fPointHead = FactoryGeometry.getIFPoint(4, 5, 6);
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        fVector.invert();

        // REF

        assertAll("Validate IFPoint values",
                () -> assertEquals(4, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(5, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(6, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(2, fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(3, fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Get inclination")
    void getInclination() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
        IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 2, 0);
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        assertEquals(Math.PI * 0.25, fVector.getInclination(), jitter,
                "The IFVector inclination is incorrect");

        assertAll("Validate IFPoint values",
                () -> assertEquals(1, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(1, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(0, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(2, fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(2, fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(0, fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Set inclination")
    void setInclination() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
        IFPoint fPointHead = FactoryGeometry.getIFPoint(1, 2, 0);
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        fVector.setInclination(Math.PI * 0.5);

        // REF

        assertAll("Validate IFPoint values",
                () -> assertEquals(1, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(1, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(0, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(2, fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(0, fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Get azimuth")
    void getAzimuth() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
        IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 1);
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        assertEquals(Math.PI * 0.25, fVector.getAzimuth(), jitter,
                "The IFVector inclination is incorrect");

        assertAll("Validate IFPoint values",
                () -> assertEquals(1, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(1, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(0, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(2, fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Set azimuth")
    void setAzimuth() {
        IFPoint fPointBase = FactoryGeometry.getIFPoint(1, 1, 0);
        IFPoint fPointHead = FactoryGeometry.getIFPoint(2, 1, 0);
        IFVector fVector = FactoryGeometry.getIFVector(fPointBase, fPointHead);

        fVector.setAzimuth(Math.PI * 0.5);

        // REF

        assertAll("Validate IFPoint values",
                () -> assertEquals(1, fVector.getBase().getX(),
                        "Base - The X value is incorrect"),
                () -> assertEquals(1, fVector.getBase().getY(),
                        "Base - The Y value is incorrect"),
                () -> assertEquals(0, fVector.getBase().getZ(),
                        "Base - The Z value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getX(),
                        "Head - The X value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getY(),
                        "Head - The Y value is incorrect"),
                () -> assertEquals(1, fVector.getHead().getZ(),
                        "Head - The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Get angle")
    void getAngle() {
        IFPoint fPointBaseA = FactoryGeometry.getIFPoint(1, 1, 1);
        IFPoint fPointHeadA = FactoryGeometry.getIFPoint(2, 2, 2);
        IFVector fVectorA = FactoryGeometry.getIFVector(fPointBaseA, fPointHeadA);

        IFPoint fPointBaseB = FactoryGeometry.getIFPoint(1, -1, 1);
        IFPoint fPointHeadB = FactoryGeometry.getIFPoint(2, -2, 2);
        IFVector fVectorB = FactoryGeometry.getIFVector(fPointBaseB, fPointHeadB);

        assertEquals(Math.PI * 0.5, fVectorA.getAngle(fVectorB), jitter, "The angle is incorrect");
        assertEquals(Math.PI * 0.5, fVectorB.getAngle(fVectorA), jitter, "The angle is incorrect");

        assertAll("Validate IFPoint values (IFVector A)",
                () -> assertEquals(1, fVectorA.getBase().getX(),
                        "Base A - The X value is incorrect"),
                () -> assertEquals(1, fVectorA.getBase().getY(),
                        "Base A - The Y value is incorrect"),
                () -> assertEquals(1, fVectorA.getBase().getZ(),
                        "Base A - The Z value is incorrect"),
                () -> assertEquals(2, fVectorA.getHead().getX(),
                        "Head A - The X value is incorrect"),
                () -> assertEquals(2, fVectorA.getHead().getY(),
                        "Head A - The Y value is incorrect"),
                () -> assertEquals(2, fVectorA.getHead().getZ(),
                        "Head A - The Z value is incorrect")
        );

        assertAll("Validate IFPoint values (IFVector B)",
                () -> assertEquals(1, fVectorB.getBase().getX(),
                        "Base B - The X value is incorrect"),
                () -> assertEquals(-1, fVectorB.getBase().getY(),
                        "Base B - The Y value is incorrect"),
                () -> assertEquals(1, fVectorB.getBase().getZ(),
                        "Base B - The Z value is incorrect"),
                () -> assertEquals(2, fVectorB.getHead().getX(),
                        "Head B - The X value is incorrect"),
                () -> assertEquals(-2, fVectorB.getHead().getY(),
                        "Head B - The Y value is incorrect"),
                () -> assertEquals(2, fVectorB.getHead().getZ(),
                        "Head B - The Z value is incorrect")
        );
    }

    // Both vectora are points
}
