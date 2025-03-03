package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FSphere")
public class FSphereTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FSphereBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FSphere fSphere = factory.getFSphere(1);

            FPoint refCenter = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(0, refCenter.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(0, refCenter.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(0, refCenter.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect")
            );
        }

        @Test
        @DisplayName("Construct (fail)")
        void constructFail() {

            assertThrows(IllegalArgumentException.class, () -> factory.getFSphere(-1),
                    "The radius value is incorrect, an exception should be thrown");
        }

        @Test
        @DisplayName("Construct with parameters")
        void constructWithParameters() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 4);

            FPoint refCenter = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, refCenter.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, refCenter.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, refCenter.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(4, fSphere.getRadius(),
                            "The radius is incorrect")
            );
        }

        @Test
        @DisplayName("Constructor with parameters (fail)")
        void constructWithParametersFail() {

            assertThrows(IllegalArgumentException.class, () -> factory.getFSphere(1, 2, 3, -1),
                    "The radius value is incorrect, an exception should be thrown");
        }

        @Test
        @DisplayName("Construct with FPoint")
        void constructWithFPoint() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FSphere fSphere = factory.getRefFSphere(fPoint, 4);

            FPoint refCenter = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, refCenter.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, refCenter.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, refCenter.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(4, fSphere.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fPoint, refCenter,
                            "The core reference should not change")
            );
        }

        @Test
        @DisplayName("Constructor with FPoint (fail)")
        void constructWithFPointFail() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            assertThrows(IllegalArgumentException.class, () -> factory.getRefFSphere(fPoint, -1),
                    "The radius value is incorrect, an exception should be thrown");
        }

        @Test
        @DisplayName("Set radius")
        void setRadius() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FSphere results = fSphere.setRadius(10);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(10, fSphere.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(results, fSphere,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Set radius (inner)")
        void setRadiusInner() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FSphere results = fSphere.setInnerRadius(11);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(11, fSphere.getInnerRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(results, fSphere,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Set radius (outer)")
        void setRadiusOuter() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FSphere results = fSphere.setOuterRadius(12);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(12, fSphere.getOuterRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(results, fSphere,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Set center")
        void setCenter() {
            FPoint fPoint = TestHelper.getRandFPoint();
            FSphere fSphere = factory.getRefFSphere(fPoint, 1);

            FPoint fCenterA = TestHelper.getRandFPoint();

            FSphere results = fSphere.setRefCenter(fCenterA);

            fCenterA.set(1, 2, 3);

            FPoint fCenterB = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, fCenterB.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, fCenterB.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, fCenterB.getZ(),
                            "The Z value is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change"),
                    () -> assertSame(fCenterA, fCenterB,
                            "The FPoint reference should not change"),
                    () -> assertNotSame(fPoint, fCenterA,
                            "The FPoint reference should be different")
            );
        }

        @Test
        @DisplayName("Set position with primitives")
        void setPositionWithPrimitives() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FPoint position = factory.getFPoint();

            FSphere results = fSphere.setPosCenter(1, 2, 3);
            fSphere.getPosCenter(position);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, position.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, position.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, position.getZ(),
                            "The Z value is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Set position with FPoint")
        void setPositionWithFPoint() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FPoint posGet = factory.getFPoint();
            FPoint posSet = factory.getFPoint(1, 2, 3);

            FSphere results = fSphere.setPosCenter(posSet);
            fSphere.getPosCenter(posGet);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, posGet.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, posGet.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, posGet.getZ(),
                            "The Z value is incorrect"),
                    () -> assertTrue(posSet.isExact(1, 2, 3),
                            "The input FPoint values should not change"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Set position with FPos3D")
        void setPositionWithFPos3D() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FPoint posGet = factory.getFPoint();

            FSphere results = fSphere.setPosCenter(factory.getFPos3D(1, 2, 3));
            fSphere.getPosCenter(posGet);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, posGet.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, posGet.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, posGet.getZ(),
                            "The Z value is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Set position X")
        void setPositionX() {
            FSphere fSphere = factory.getFSphere(5, 5, 5, 1);

            FPoint posGet = factory.getFPoint();

            FSphere results = fSphere.setPosCenterX(1);
            fSphere.getPosCenter(posGet);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, posGet.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(5, posGet.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(5, posGet.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Set position Y")
        void setPositionY() {
            FSphere fSphere = factory.getFSphere(5, 5, 5, 1);

            FPoint posGet = factory.getFPoint();

            FSphere results = fSphere.setPosCenterY(1);
            fSphere.getPosCenter(posGet);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(5, posGet.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(1, posGet.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(5, posGet.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Set position Z")
        void setPositionZ() {
            FSphere fSphere = factory.getFSphere(5, 5, 5, 1);

            FPoint posGet = factory.getFPoint();

            FSphere results = fSphere.setPosCenterZ(1);
            fSphere.getPosCenter(posGet);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(5, posGet.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(5, posGet.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(1, posGet.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }
    }

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality - Advanced")
    class FSphereAdvancedTest {

        @Test
        @DisplayName("Get volume")
        void getVolume() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 5);

            double volGet = fSphere.getVolume();
            double volCalc = 4 * Math.PI * Math.pow(5, 3) / 3;

            assertEquals(volCalc, volGet,
                    epsilon, "The FSphere volume is erroneous");
        }

        @Test
        @DisplayName("Set volume")
        void setVolume() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 5);

            double vol = 100;
            double radius = Math.pow((0.75 * vol) / Math.PI, 1.0 / 3);

            FSphere results = fSphere.setVolume(vol);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertTrue(fSphere.getRefCenter().isExact(1, 2, 3),
                            "The FSphere position is incorrect"),
                    () -> assertEquals(radius, fSphere.getRadius(),
                            epsilon, "The radius is incorrect"),
                    () -> assertTrue(factory.getFStatHelper().valRelErr(vol, fSphere.getVolume(), 0.0001),
                            "The volume is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Get surface")
        void getSurface() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 5);

            double volGet = fSphere.getSurface();
            double volCalc = 4 * Math.PI * Math.pow(5, 2);

            assertEquals(volCalc, volGet,
                    epsilon, "The FSphere surface is erroneous");
        }

        @Test
        @DisplayName("Set surface")
        void setSurface() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 4);

            double sur = 100;
            double radius = Math.pow(0.25 * sur / Math.PI, 0.5);

            FSphere results = fSphere.setSurface(sur);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertTrue(fSphere.getRefCenter().isExact(1, 2, 3),
                            "The FSphere position is incorrect"),
                    () -> assertEquals(radius, fSphere.getRadius(),
                            epsilon, "The radius is incorrect"),
                    () -> assertTrue(factory.getFStatHelper().valRelErr(sur, fSphere.getSurface(), 0.0001),
                            "The surface is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Contains with parameters")
        void containsWithParameters() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 5);

            double max = fSphere.getRadius();

            double x = fSphere.getRefCenter().getX();
            double y = fSphere.getRefCenter().getY();
            double z = fSphere.getRefCenter().getZ();

            double mid = max * 0.5;

            Assertions.assertAll("Validate positions",
                    () -> assertTrue(fSphere.contains(x, y, z)),
                    () -> assertTrue(fSphere.contains(x + mid, y, z)),
                    () -> assertTrue(fSphere.contains(x - mid, y, z)),
                    () -> assertTrue(fSphere.contains(x, y + mid, z)),
                    () -> assertTrue(fSphere.contains(x, y - mid, z)),
                    () -> assertTrue(fSphere.contains(x, y, z + mid)),
                    () -> assertTrue(fSphere.contains(x, y, z - mid)),
                    () -> assertTrue(fSphere.contains(x + max, y, z)),
                    () -> assertTrue(fSphere.contains(x - max, y, z)),
                    () -> assertTrue(fSphere.contains(x, y + max, z)),
                    () -> assertTrue(fSphere.contains(x, y - max, z)),
                    () -> assertTrue(fSphere.contains(x, y, z + max)),
                    () -> assertTrue(fSphere.contains(x, y, z - max)),
                    () -> assertFalse(fSphere.contains(x + max, y + max, z + max)),
                    () -> assertFalse(fSphere.contains(x - max, y - max, z - max))
            );
        }

        @Test
        @DisplayName("Contains with parameters (min)")
        void containsWithParametersMin() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 0.01);

            double max = fSphere.getRadius();

            double x = fSphere.getRefCenter().getX();
            double y = fSphere.getRefCenter().getY();
            double z = fSphere.getRefCenter().getZ();

            double mid = max * 0.5;

            Assertions.assertAll("Validate positions",
                    () -> assertTrue(fSphere.contains(x, y, z)),
                    () -> assertTrue(fSphere.contains(x + mid, y, z)),
                    () -> assertTrue(fSphere.contains(x - mid, y, z)),
                    () -> assertTrue(fSphere.contains(x, y + mid, z)),
                    () -> assertTrue(fSphere.contains(x, y - mid, z)),
                    () -> assertTrue(fSphere.contains(x, y, z + mid)),
                    () -> assertTrue(fSphere.contains(x, y, z - mid)),
                    () -> assertTrue(fSphere.contains(x + max, y, z)),
                    () -> assertTrue(fSphere.contains(x - max, y, z)),
                    () -> assertTrue(fSphere.contains(x, y + max, z)),
                    () -> assertTrue(fSphere.contains(x, y - max, z)),
                    () -> assertTrue(fSphere.contains(x, y, z + max)),
                    () -> assertTrue(fSphere.contains(x, y, z - max)),
                    () -> assertFalse(fSphere.contains(x + max, y + max, z + max)),
                    () -> assertFalse(fSphere.contains(x - max, y - max, z - max))
            );
        }

        @Test
        @DisplayName("Contains with FPoint")
        void containsWithFPoint() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 5);

            double max = fSphere.getRadius();

            double x = fSphere.getRefCenter().getX();
            double y = fSphere.getRefCenter().getY();
            double z = fSphere.getRefCenter().getZ();

            double mid = max * 0.5;

            Assertions.assertAll("Validate positions",
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x + mid, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x - mid, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y + mid, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y - mid, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y, z + mid))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y, z - mid))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x + max, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x - max, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y + max, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y - max, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y, z + max))),
                    () -> assertTrue(fSphere.contains(factory.getFPoint(x, y, z - max))),
                    () -> assertFalse(fSphere.contains(factory.getFPoint(x + max, y + max, z + max))),
                    () -> assertFalse(fSphere.contains(factory.getFPoint(x - max, y - max, z - max)))
            );
        }

        @Test
        @DisplayName("Contains with FPos3D")
        void containsWithFPos3D() {
            FSphere fSphere = factory.getFSphere(1, 2, 3, 5);

            double max = fSphere.getRadius();

            double x = fSphere.getRefCenter().getX();
            double y = fSphere.getRefCenter().getY();
            double z = fSphere.getRefCenter().getZ();

            double mid = max * 0.5;

            Assertions.assertAll("Validate positions",
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x + mid, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x - mid, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y + mid, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y - mid, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y, z + mid))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y, z - mid))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x + max, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x - max, y, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y + max, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y - max, z))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y, z + max))),
                    () -> assertTrue(fSphere.contains(factory.getFPos3D(x, y, z - max))),
                    () -> assertFalse(fSphere.contains(factory.getFPos3D(x + max, y + max, z + max))),
                    () -> assertFalse(fSphere.contains(factory.getFPos3D(x - max, y - max, z - max)))
            );
        }

        @Test
        @DisplayName("Intersects - zero")
        void intersectsZero() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate intersections",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects")
        void intersects() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.001, 0, 0, 1);

            Assertions.assertAll("Validate intersections",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (min)")
        void intersectsMin() {
            FSphere fSphereA = factory.getFSphere(0.03, 0, 0, 0.01);
            FSphere fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01);

            Assertions.assertAll("Validate intersections",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects - fail")
        void intersectsFail() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersections",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (min) - fail")
        void intersectsFailMin() {
            FSphere fSphereA = factory.getFSphere(0.03, 0, 0, 0.01);
            FSphere fSphereB = factory.getFSphere(0.01, 0, 0, 0.01);

            Assertions.assertAll("Validate intersections",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (distant)")
        void intersectsDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate intersections",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Volume stream 3D")
        void volumeStream3D() {
            FStream3D fStream = factory.getFStream3D(5000);

            FSphere fSphere = factory.getFSphere(5, 5, 5, 1);

            double delta = 0.1;

            fSphere.getVolumeStream(fStream, delta);

            int elements = fStream.getNumberOfElements();

            fStream.iterate((index, d0, d1, d2, value) -> {
                assertTrue(fSphere.contains(d0, d1, d2));
            });

            double volUnit = delta * delta * delta;
            double volStream = elements * volUnit;
            double volCalc = fSphere.getVolume();

            Assertions.assertAll("Validate stream",
                    () -> assertTrue(elements > 0,
                            "The number of elements should be greater than zero"),
                    () -> assertTrue(factory.getFStatHelper().valRelErr(volCalc, volStream, 0.01),
                            "The volume relative error is erroneous")
            );
        }

        @Test
        @DisplayName("Volume stream 3DI")
        void volumeStream3DI() {
            FStream3DI fStream = factory.getFStream3DI(5000);

            FSphere fSphere = factory.getFSphere(5, 5, 5, 1);

            double delta = 0.1;

            fSphere.getVolumeStream(fStream, delta);

            int elements = fStream.getNumberOfElements();

            fStream.iterate((index, d0, d1, d2, value) -> {
                assertTrue(fSphere.contains(d0 * delta, d1 * delta, d2 * delta));
            });

            double volUnit = delta * delta * delta;
            double volStream = elements * volUnit;
            double volCalc = fSphere.getVolume();

            Assertions.assertAll("Validate stream",
                    () -> assertTrue(elements > 0,
                            "The number of elements should be greater than zero"),
                    () -> assertTrue(factory.getFStatHelper().valRelErr(volCalc, volStream, 0.01),
                            "The volume relative error is erroneous")
            );

        }
    }

}
