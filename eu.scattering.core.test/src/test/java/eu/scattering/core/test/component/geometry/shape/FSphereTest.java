package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

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
        @DisplayName("Constructor with FPoint and radius (fail)")
        void constructWithFPointAndRadiusFail() {
            FPoint fPoint = factory.getFPoint(1, 2, 3);

            assertThrows(IllegalArgumentException.class, () -> factory.getRefFSphere(fPoint, -1),
                    "The radius value is incorrect, an exception should be thrown");
        }

        @Test
        @DisplayName("Set radius")
        void setRadius() {
            FSphere fSphere = TestHelper.getRandFSphere();

            Shape results = fSphere.setRadius(10);

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

            Shape results = fSphere.setRadiusInner(11);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(11, fSphere.getRadiusInner(),
                            "The radius is incorrect"),
                    () -> assertSame(results, fSphere,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Set radius (outer)")
        void setRadiusOuter() {
            FSphere fSphere = TestHelper.getRandFSphere();

            Shape results = fSphere.setRadius(12);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(12, fSphere.getRadius(),
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

            Shape results = fSphere.setCenter(1, 2, 3);
            fSphere.getCenter(position);

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

            Shape results = fSphere.setCenter(posSet);
            fSphere.getCenter(posGet);

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

            Shape results = fSphere.setCenter(factory.getFPos3D(1, 2, 3));
            fSphere.getCenter(posGet);

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

            Shape results = fSphere.setVolume(vol);

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

            Shape results = fSphere.setSurface(sur);

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
        @DisplayName("Encloses (epsilon) - same position")
        void enclosesEpsilonSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB, 0, -1),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0, -1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) - distant")
        void enclosesEpsilonDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB, 0, -1),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0, -1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) A")
        void enclosesEpsilonA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereB = factory.getFSphere(4.5, 0, 0, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB, 0.1, -1),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0.1, -1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) B")
        void enclosesEpsilonB() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereB = factory.getFSphere(4, 0, 0, 1 - 0.15);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB, 0.1, -1),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, 0.1, -1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) - same position")
        void enclosesDeltaSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB, -1, 0.01),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, -1, 0.01),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) - distant")
        void enclosesDeltaDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB, -1, 0.01),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, -1, 0.01),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) A")
        void enclosesDeltaA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereB = factory.getFSphere(4.5, 0, 0, 1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB, -1, 0.1),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, -1, 0.1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) B")
        void enclosesDeltaB() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereB = factory.getFSphere(4, 0, 0, 1 - 0.15);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB, -1, 0.1),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA, -1, 0.1),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon)")
        void intersectsEpsilon() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB, 0, -1),
                            "The sphere should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA, 0, -1),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) - same position")
        void intersectsEpsilonSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 0, -1),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 0, -1),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) - distant")
        void intersectsEpsilonDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 0, -1),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 0, -1),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) - point contact")
        void intersectsEpsilonPointContact() {
            FSphere fSphereA = factory.getFSphere(-1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 1E-3, -1),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 1E-3, -1),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) A")
        void intersectsEpsilonA() {
            FSphere fSphereA = factory.getFSphere(-1 + 0.01, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, 0.05, -1),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, 0.05, -1),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) B")
        void intersectsEpsilonB() {
            FSphere fSphereA = factory.getFSphere(-1 + 0.1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB, 0.05, -1),
                            "The sphere should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA, 0.05, -1),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta)")
        void intersectsDelta() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB, -1, 0.01),
                            "The sphere should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA, -1, 0.01),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) - same position")
        void intersectsDeltaSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, -1, 0.01),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, -1, 0.01),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) - distant")
        void intersectsDeltaDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, -1, 0.01),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, -1, 0.01),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) - point contact")
        void intersectsDeltaPointContact() {
            FSphere fSphereA = factory.getFSphere(-1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, -1, 0.01),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, -1, 0.01),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) A")
        void intersectsDeltaA() {
            FSphere fSphereA = factory.getFSphere(-1 + 0.01, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB, -1, 0.05),
                            "The sphere should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA, -1, 0.05),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) B")
        void intersectsDeltaB() {
            FSphere fSphereA = factory.getFSphere(-1 + 0.1, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB, -1, 0.05),
                            "The sphere should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA, -1, 0.05),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Touches (epsilon)")
        void touchesEpsilon() {
            FSphere fSphereA = factory.getFSphere(0, 1, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, -1, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, 1E-3, -1),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, 1E-3, -1),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) - same position")
        void touchesEpsilonSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0, -1),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0, -1),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) - distant")
        void touchesEpsilonDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0, -1),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0, -1),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) A")
        void touchesEpsilonA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, 0.05, -1),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, 0.05, -1),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) B")
        void touchesEpsilonB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1 - 0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, 0.05, -1),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, 0.05, -1),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) A - fail")
        void touchesEpsilonFailA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0.01, -1),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0.01, -1),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) B - fail")
        void touchesEpsilonFailB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, 0.01, -1),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, 0.01, -1),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta)")
        void touchesDelta() {
            FSphere fSphereA = factory.getFSphere(0, 1, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, -1, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) - same position")
        void touchesDeltaSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) - distant")
        void touchesDeltaDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) A")
        void touchesDeltaA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, -1, 0.05),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, -1, 0.05),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) B")
        void touchesDeltaB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1, 0, 0, 1 - 0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB, -1, 0.05),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA, -1, 0.05),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) A - fail")
        void touchesDeltaFailA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) B - fail")
        void touchesDeltaFailB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        //-------

        @Test
        @DisplayName("Overlaps (epsilon) - same position")
        void overlapsEpsilonSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, 0, -1),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, 0, -1),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - distant")
        void overlapsEpsilonDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, 0, -1),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, 0, -1),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon)")
        void overlapsEpsilon() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.01, 0, 0, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, 0.005, -1),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, 0.005, -1),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - fail")
        void overlapsEpsilonFail() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.01, 0, 0, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, 0.05, -1),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, 0.05, -1),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon, min)")
        void overlapsEpsilonMin() {
            FSphere fSphereA = factory.getFSphere(0.03, 0, 0, 0.01);
            FSphere fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, 1E-6, -1),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, 1E-6, -1),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon, min) - fail")
        void overlapsEpsilonFailMin() {
            FSphere fSphereA = factory.getFSphere(0.03, 0, 0, 0.01);
            FSphere fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, 1E-4, -1),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, 1E-4, -1),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) A - fail")
        void overlapsDeltaFailA() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) B - fail")
        void overlapsDeltaFailB() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB, -1, 0.01),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA, -1, 0.01),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - same position")
        void overlapsDeltaSamePosition() {
            FSphere fSphereA = factory.getFSphere(1, 2, 3, 2);
            FSphere fSphereB = factory.getFSphere(1, 2, 3, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, -1, 0.01),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, -1, 0.01),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - distant")
        void overlapsDeltaDistant() {
            FSphere fSphereA = factory.getFSphere(1, 1, 1, 1);
            FSphere fSphereB = factory.getFSphere(-1, -1, -1, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, -1, 0.01),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, -1, 0.01),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta)")
        void overlapsDelta() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.01, 0, 0, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB, -1, 0.005),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA, -1, 0.005),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - fail")
        void overlapsDeltaFail() {
            FSphere fSphereA = factory.getFSphere(3, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(1.01, 0, 0, 1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB, -1, 0.05),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA, -1, 0.05),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Volume stream 3D")
        void volumeStream3D() {
            FStream3D fStream = factory.getFStream3D(5000);

            FSphere fSphere = factory.getFSphere(5, 5, 5, 1);

            double delta = 0.1;

            fSphere.getVolumeBuffer(fStream, delta);

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

            fSphere.getVolumeBuffer(fStream, delta);

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

            factory.getFRandEngine().inSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);

            assertTrue(fSphereRef.overlaps(fSphereArg, 0, -1), "Spheres should overlap");

            boolean isRepositioned = fSphereRef.attachLinear(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach - distant")
        void attachDistant() {
            FSphere fSphereRef = factory.getFSphere(1);
            FSphere fSphereArg = (FSphere) TestHelper.getRandFSphere().setRadius(1);

            factory.getFRandEngine().onSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 3);

            assertFalse(fSphereRef.overlaps(fSphereArg, 0, -1), "Spheres should not overlap");

            boolean isRepositioned = fSphereRef.attachLinear(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach - same position")
        void attachSamePosition() {
            FSphere fSphereRef = TestHelper.getRandFSphere();
            FSphere fSphereArg = (FSphere) fSphereRef.copy().setRadius(1);

            assertTrue(fSphereRef.overlaps(fSphereArg, 0, -1), "Spheres should overlap");

            boolean isRepositioned = fSphereRef.attachLinear(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach - point contact")
        void attachPointContact() {
            FSphere fSphereRef = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereArg = factory.getFSphere(2, 0, 0, 1);

            boolean isRepositioned = fSphereRef.attachLinear(fSphereArg, 0);

            Assertions.assertAll("Validate results",
                    () -> assertFalse(isRepositioned,
                            "The reference sphere should not be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach (field) - empty")
        void attachFieldEmpty() {
            FSphere fSphereRef = factory.getFSphere();
            FSphere fSphereArg = factory.getFSphere();

            FAssembly<FSphere> fSphereField = factory.getFAssembly();

            factory.getFRandEngine().inSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);

            boolean isRepositioned = fSphereRef.attachLinear(fSphereArg, epsilon, fSphereField, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isRepositioned,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach (field) - self")
        void attachFieldOrigin() {
            FSphere fSphereRef = factory.getFSphere();
            FSphere fSphereArg = factory.getFSphere();

            FAssembly<FSphere> fSphereField = factory.getFAssembly();
            fSphereField.register(fSphereRef);
            fSphereField.register(fSphereArg);

            factory.getFRandEngine().inSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);

            boolean repositions = fSphereRef.attachLinear(fSphereArg, epsilon, fSphereField, 0);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(repositions,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach (field) - bounce 1")
        void attachFieldBounce1() {
            FSphere fSphereRef = factory.getFSphere(0, 1, 0, 1);
            FSphere fSphereArg = factory.getFSphere(0, 0, 0, 2);
            FSphere fSphereField1 = factory.getFSphere(-0.25, 3, 0, 1);

            FAssembly<FSphere> fSphereField = factory.getFAssembly();
            fSphereField.register(fSphereRef);
            fSphereField.register(fSphereArg);
            fSphereField.register(fSphereField1);

            boolean repositions = fSphereRef.attachLinear(fSphereArg, epsilon, fSphereField, 5);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(repositions,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "Spheres should be in point contact (arg)"),
                    () -> assertTrue(fSphereRef.touches(fSphereField1, epsilon, -1),
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

            FAssembly<FSphere> fSphereField = factory.getFAssembly();
            fSphereField.register(fSphereRef);
            fSphereField.register(fSphereArg);
            fSphereField.register(fSphereField1);

            boolean repositions = fSphereRef.attachLinear(fSphereArg, epsilon, fSphereField, 0);

            Assertions.assertAll("Validate results",
                    () -> assertFalse(repositions,
                            "The number of repositions is incorrect"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
                            "The spheres should touch (arg)"),
                    () -> assertFalse(fSphereRef.touches(fSphereField1, epsilon, -1),
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

            FSphere fSphereRef = factory.getFSphere(1).set(json);

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
        @DisplayName("Is exact (geometry)")
        void isExactGeometry() {
            Geometry fSphereRef = factory.getFSphere(1, 2, 3, 4);
            Geometry fSphereArg = factory.getFSphere(1, 2, 3, 4);

            assertTrue((fSphereRef.isExact(fSphereArg)), "FSpheres should be exact");
        }

        @Test
        @DisplayName("Is exact (geometry, fail) A")
        void isExactGeometryFailA() {
            Geometry fSphere = factory.getFSphere(1, 2, 3, 4);
            FPoint fPoint = factory.getFPoint(5, 6, 7);

            assertFalse((fSphere.isExact(fPoint)), "Geometries should not be exact");
        }

        @Test
        @DisplayName("Is exact (geometry, fail) B")
        void isExactGeometryFailB() {
            Geometry fSphereRef = factory.getFSphere(1, 2, 3, 4);
            Geometry fSphereArg = factory.getFSphere(5, 6, 7, 8);

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
        @DisplayName("Is similar (fail) A")
        void isSimilarFailA() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3, 4 + (epsilon * 1.5));

            assertFalse((fSphereRef.isSimilar(fSphereArg)), "FSpheres should not be similar");
        }

        @Test
        @DisplayName("Is similar (fail) B")
        void isSimilarFailB() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 4);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 1.5), 4);

            assertFalse((fSphereRef.isSimilar(fSphereArg)), "FSpheres should not be similar");
        }

        @Test
        @DisplayName("Is similar (geometry)")
        void isSimilarGeometry() {
            Geometry fSphereRef = factory.getFSphere(1, 2, 3, 4);
            Geometry fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 0.5), 4 + (epsilon * 0.5));

            assertTrue((fSphereRef.isSimilar(fSphereArg)), "FSpheres should be similar");
        }

        @Test
        @DisplayName("Is similar (geometry, fail) A")
        void isSimilarGeometryFailA() {
            Geometry fSphere = factory.getFSphere(1, 2, 3, 4);
            Geometry fPoint = factory.getFPoint(1, 2, 3);

            assertFalse((fSphere.isSimilar(fPoint)), "Geometries should not be similar");
        }

        @Test
        @DisplayName("Is similar (geometry, fail) B")
        void isSimilarGeometryFailB() {
            Geometry fSphereRef = factory.getFSphere(1, 2, 3, 4);
            Geometry fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 1.5), 4);

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
        @DisplayName("Copy geometry")
        void copyGeometry() {
            FSphere fSphereA = TestHelper.getRandFSphere();
            Geometry fSphereB = fSphereA.copyGeometry();

            Assertions.assertAll("Validate similarity",
                    () -> assertNotSame(fSphereA, fSphereB,
                            "FSpheres represent different objects"),
                    () -> assertTrue(fSphereA.isExact((FSphere) fSphereB),
                            "FSpheres should have the same values"),
                    () -> assertNotSame(fSphereA.getRefCenter(), ((FSphere) fSphereB).getRefCenter(),
                            "The center FPoints should be different")
            );
        }
    }
}
