package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.HashSet;

import static eu.scattering.core.test.Config.*;
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
            FSphere fSphere = factory.getFSphere();

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
        @DisplayName("Construct with position")
        void constructWithPosition() {
            FSphere fSphere = factory.getFSphere(1, 2, 3);

            FPoint refCenter = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, refCenter.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, refCenter.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, refCenter.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with FPoint")
        void constructWithFPoint() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);
            FSphere fSphere = factory.getRefFSphere(fPoint);

            FPoint refCenter = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, refCenter.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, refCenter.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, refCenter.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fPoint, refCenter,
                            "The core reference should not change")
            );
        }

        @Test
        @DisplayName("Construct with radius")
        void constructWithRadius() {
            FSphere fSphere = factory.getFSphere(5);

            FPoint refCenter = fSphere.getRefCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(0, refCenter.getX(),
                            "The X value is incorrect"),
                    () -> assertEquals(0, refCenter.getY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(0, refCenter.getZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(5, fSphere.getRadius(),
                            "The radius is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with radius (fail)")
        void constructWithRadiusFail() {

            assertThrows(IllegalArgumentException.class, () -> factory.getFSphere(-1),
                    "The radius value is incorrect, an exception should be thrown");
        }

        @Test
        @DisplayName("Construct with position and radius")
        void constructWithPositionAndRadius() {
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
        @DisplayName("Constructor with position and radius (fail)")
        void constructWithPositionAndRadiusFail() {

            assertThrows(IllegalArgumentException.class, () -> factory.getFSphere(1, 2, 3, -1),
                    "The radius value is incorrect, an exception should be thrown");
        }

        @Test
        @DisplayName("Construct with FPoint and radius")
        void constructWithFPointAndRadius() {
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
        @DisplayName("Constructor with FPoint  and radius (fail)")
        void constructWithFPointAndRadiusFail() {
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
        @DisplayName("Encloses - same position")
        void enclosesSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB, 0),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses - distant")
        void enclosesDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB, 0),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) A")
        void enclosesEpsilonA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereB = factory.getFSphere(4, 0, 0, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB, 0.1),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0.1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) B")
        void enclosesEpsilonB() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereB = factory.getFSphere(4, 0, 0, 1 - 0.15);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB, 0.1),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0.1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Intersects")
        void intersects() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB, 0 ),
                            "The sphere should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA, 0),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects - same position")
        void intersectsSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 0),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 0),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects - distant")
        void intersectsDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 0),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 0),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects - point contact")
        void intersectsPointContact() {
            FSphere fSphereA = factory.getFSphere(-1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 1E-3),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 1E-3),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) A")
        void intersectsEpsilonA() {
            FSphere fSphereA = factory.getFSphere(-1 + 0.01, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 0.05),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 0.05),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) B")
        void intersectsEpsilonB() {
            FSphere fSphereA = factory.getFSphere(-1 + 0.1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB, 0.05),
                            "The sphere should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA, 0.05),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Touches")
        void touches() {
            FSphere fSphereA = factory.getFSphere(0, 1, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, -1, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, 1E-3),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, 1E-3),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches - same position")
        void touchesSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches - distant")
        void touchesDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) A")
        void overlapsEpsilonA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, 0.05),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, 0.05),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) B")
        void overlapsEpsilonB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, 0.05),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, 0.05),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) A - fail")
        void overlapsEpsilonFailA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) B - fail")
        void overlapsEpsilonFailB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Overlaps - same position")
        void overlapsSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, 0),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, 0),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps - distant")
        void overlapsDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, 0),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, 0),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon)")
        void overlapsEpsilon() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.01, 0, 0, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, 0.005),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, 0.005),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - fail")
        void overlapsEpsilonFail() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.01, 0, 0, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, 0.05),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, 0.05),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon, min)")
        void overlapsEpsilonMin() {
            FSphere fSphereA = factory.getFSphere(0.03, 0, 0, 0.01);
            FSphere fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, 1E-6),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, 1E-6),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon, min) - fail")
        void overlapsEpsilonFailMin() {
            FSphere fSphereA = factory.getFSphere(0.03, 0, 0, 0.01);
            FSphere fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, 1E-4),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, 1E-4),
                            "The spheres should not overlap")
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

            fStream.iterate((index, d0, d1, d2, value) ->
                    assertTrue(fSphere.contains(d0, d1, d2)));

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

            fStream.iterate((index, d0, d1, d2, value) ->
                    assertTrue(fSphere.contains(d0 * delta, d1 * delta, d2 * delta)));

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
        @DisplayName("Attach")
        void attach() {
            FSphere fSphereRef = factory.getFSphere(1);
            FSphere fSphereArg = TestHelper.getRandFSphere();

            factory.getFRandEngine().rndPosInSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);

            assertTrue(fSphereRef.overlaps(fSphereArg, 0), "Spheres should overlap");

            boolean isRepositioned = fSphereRef.attach(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach - distant")
        void attachDistant() {
            FSphere fSphereRef = factory.getFSphere(1);
            FSphere fSphereArg = TestHelper.getRandFSphere().setRadius(1);

            factory.getFRandEngine().rndPosOnSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 3);

            assertFalse(fSphereRef.overlaps(fSphereArg, 0), "Spheres should not overlap");

            boolean isRepositioned = fSphereRef.attach(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach - same position")
        void attachSamePosition() {
            FSphere fSphereRef = TestHelper.getRandFSphere();
            FSphere fSphereArg = fSphereRef.copy().setRadius(1);

            assertTrue(fSphereRef.overlaps(fSphereArg, 0), "Spheres should overlap");

            boolean isRepositioned = fSphereRef.attach(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach - point contact")
        void attachPointContact() {
            FSphere fSphereRef = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereArg = factory.getFSphere(2, 0, 0, 1);

            boolean isRepositioned = fSphereRef.attach(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertFalse(isRepositioned,
                            "The reference sphere should not be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach (field) - empty")
        void attachFieldEmpty() {
            FSphere fSphereRef = factory.getFSphere();
            FSphere fSphereArg = factory.getFSphere();

            Collection<FSphere> fSphereField = new HashSet<>();

            factory.getFRandEngine().rndPosInSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);

            int isRepositioned = fSphereRef.attach(fSphereArg, epsilon, fSphereField, 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, isRepositioned,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach (field) - self")
        void attachFieldOrigin() {
            FSphere fSphereRef = factory.getFSphere();
            FSphere fSphereArg = factory.getFSphere();

            Collection<FSphere> fSphereField = new HashSet<>();
            fSphereField.add(fSphereRef);
            fSphereField.add(fSphereArg);

            factory.getFRandEngine().rndPosInSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);

            int repositions = fSphereRef.attach(fSphereArg, epsilon, fSphereField, 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, repositions,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach (field) - bounce 1")
        void attachFieldBounce1() {
            FSphere fSphereRef = factory.getFSphere(0, 1, 0, 1);
            FSphere fSphereArg = factory.getFSphere(0, 0, 0, 2);
            FSphere fSphereField1 = factory.getFSphere(-0.25, 3, 0, 1);

            Collection<FSphere> fSphereField = new HashSet<>();
            fSphereField.add(fSphereRef);
            fSphereField.add(fSphereArg);
            fSphereField.add(fSphereField1);

            int repositions = fSphereRef.attach(fSphereArg, epsilon, fSphereField, 5);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(2, repositions,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "Spheres should be in point contact (arg)"),
                    () -> assertTrue(fSphereRef.touches(fSphereField1, epsilon),
                            "Spheres should be in point contact (neighbour)")
            );
        }

//        @Test
//        @DisplayName("Push (field) - bounce 1 - same position")
//        void pushFieldBounce1SamePosition() {
//            FSphere fSphereRef = factory.getFSphere(0, 1, 0, 1);
//            FSphere fSphereArg = factory.getFSphere(0, 0, 0, 2);
//            FSphere fSphereField1 = factory.getFSphere(0, 3, 0, 1);
//
//            Collection<FSphere> fSphereField = new HashSet<>();
//            fSphereField.add(fSphereRef);
//            fSphereField.add(fSphereArg);
//            fSphereField.add(fSphereField1);
//
//            int repositions = fSphereRef.push(fSphereArg, epsilon, fSphereField, 5);
//
//            Assertions.assertAll("Validate results",
//                    () -> assertEquals(2, repositions,
//                            "The number of repositions is incorrect"),
//                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
//                            "The spheres should touch (arg)"),
//                    () -> assertTrue(fSphereRef.touches(fSphereField1, epsilon),
//                            "The spheres should touch (neighbour)")
//            );
//        }

        @Test
        @DisplayName("Push (field, error) - incorrect bounce")
        void pushFieldErrorBounce1() {
            FSphere fSphereRef = factory.getFSphere(0, 1, 0, 1);
            FSphere fSphereArg = factory.getFSphere(0, 0, 0, 2);
            FSphere fSphereField1 = factory.getFSphere(-0.25, 3, 0, 1);

            Collection<FSphere> fSphereField = new HashSet<>();
            fSphereField.add(fSphereRef);
            fSphereField.add(fSphereArg);
            fSphereField.add(fSphereField1);

            int repositions = fSphereRef.attach(fSphereArg, epsilon, fSphereField, 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(-1, repositions,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon),
                            "The spheres should touch (arg)"),
                    () -> assertFalse(fSphereRef.touches(fSphereField1, epsilon),
                            "The spheres should not touch (neighbour)")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FSphereCoreTest {

        @Test
        @DisplayName("JSON parser")
        void parseJSON() {
            FSphere fSphere = TestHelper.getRandFSphere();

            JSONObject json = fSphere.toJSON();

            FSphere fSphereRef = factory.getFSphere(1).applyStateFrom(json);

            Assertions.assertAll("Validate JSON parser",
                    () -> assertNotSame(fSphere, fSphereRef,
                            "FSphere references should point at different objects"),
                    () -> assertTrue(fSphere.isExact(fSphereRef),
                            "FSpheres should be exact")
            );
        }

        @Test
        @DisplayName("Is exact")
        void isExact() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3, 4);

            assertTrue((fSphereRef.isExact(fSphereArg)), "FSpheres should be exact");
        }

        @Test
        @DisplayName("Is exact (fail)")
        void isExactFail() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(5, 6, 7, 8);

            assertFalse((fSphereRef.isExact(fSphereArg)), "FSpheres should not be exact");
        }

        @Test
        @DisplayName("Is similar")
        void isSimilar() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 0.5), 4 + (epsilon * 0.5));

            assertTrue((fSphereRef.isSimilar(fSphereArg)), "FSpheres should be similar");
        }

        @Test
        @DisplayName("Is exact (fail) A")
        void isSimilarFailA() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3, 4 + (epsilon * 1.5));

            assertFalse((fSphereRef.isSimilar(fSphereArg)), "FSpheres should not be similar");
        }

        @Test
        @DisplayName("Is exact (fail) B")
        void isSimilarFailB() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 1.5), 4);

            assertFalse((fSphereRef.isSimilar(fSphereArg)), "FSpheres should not be similar");
        }

        @Test
        @DisplayName("Get hash code")
        void getHashCode() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3, 4);

            assertEquals(fSphereRef.hashCode(), fSphereArg.hashCode(),
                    "Two identical FSpheres should have the same hash code");
        }

        @Test
        @DisplayName("Get hash code (fail)")
        void getHashCodeFail() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(5, 6, 7, 8);

            assertNotEquals(fSphereRef.hashCode(), fSphereArg.hashCode(),
                    "Two different FSpheres should not have the same hash code");
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FSphere fSphereA = TestHelper.getRandFSphere();
            FSphere fSphereB = fSphereA.copy();

            Assertions.assertAll("Validate similarity",
                    () -> assertNotSame(fSphereA, fSphereB,
                            "FSpheres represent different objects"),
                    () -> assertTrue(fSphereA.isExact(fSphereB),
                            "FSpheres should have the same values"),
                    () -> assertNotSame(fSphereA.getRefCenter(), fSphereB.getRefCenter(),
                            "The center FPoints should be different")
            );
        }

        @Test
        @DisplayName("Copy zero")
        void copyZero() {
            FSphere fSphereA = TestHelper.getRandFSphere();
            FSphere fSphereB = fSphereA.copyZero();

            Assertions.assertAll("Validate similarity",
                    () -> assertNotSame(fSphereA, fSphereB,
                            "FSpheres represent different objects"),
                    () -> assertFalse(fSphereA.isExact(fSphereB),
                            "FSpheres should not have the same values"),
                    () -> assertNotSame(fSphereA.getRefCenter(), fSphereB.getRefCenter(),
                            "The center FPoints should be different"),
                    () -> assertTrue(fSphereB.isExact(factory.getFSphere(1)),
                            "FSpheres should have the same values")
            );
        }
    }
}
