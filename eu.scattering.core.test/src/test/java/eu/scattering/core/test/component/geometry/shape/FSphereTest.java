package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.test.TestHelper;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.SHAPE_DELTA;
import static eu.scattering.core.impl.ConfigDef.SHAPE_EPSILON;
import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

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

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(0, fSphere.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(0, fSphere.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(0, fSphere.getCenterZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, fSphere.getRadius(),
                            "The radius is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with position")
        void constructWithPosition() {
            FSphere fSphere = factory.getFSphere(1, 2, 3);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, fSphere.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, fSphere.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, fSphere.getCenterZ(),
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
                    () -> assertEquals(1, fSphere.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, fSphere.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, fSphere.getCenterZ(),
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

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(0, fSphere.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(0, fSphere.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(0, fSphere.getCenterZ(),
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

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, fSphere.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, fSphere.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, fSphere.getCenterZ(),
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
                    () -> assertEquals(1, fSphere.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, fSphere.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, fSphere.getCenterZ(),
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

            Shape results = fSphere.setCenter(1, 2, 3);
            FPos3D position = fSphere.getCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, position.getD0(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, position.getD1(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, position.getD2(),
                            "The Z value is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Set position with FPoint")
        void setPositionWithFPoint() {
            FSphere fSphere = TestHelper.getRandFSphere();

            FPoint posSet = factory.getFPoint(1, 2, 3);

            Shape results = fSphere.setCenter(posSet);
            FPos3D position = fSphere.getCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, position.getD0(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, position.getD1(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, position.getD2(),
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

            Shape results = fSphere.setCenter(factory.getFPos3D(1, 2, 3));
            FPos3D position = fSphere.getCenter();

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(1, position.getD0(),
                            "The X value is incorrect"),
                    () -> assertEquals(2, position.getD1(),
                            "The Y value is incorrect"),
                    () -> assertEquals(3, position.getD2(),
                            "The Z value is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Translate")
        void translate() {
            FSphere fSphere = factory.getFSphere(1, 1, 1, 1);

            Shape results = fSphere.translate(1, 2, 3);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(2, results.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(3, results.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(4, results.getCenterZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, results.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Translate with FPoint")
        void translateWithFPoint() {
            FSphere fSphere = factory.getFSphere(1, 1, 1, 1);

            Shape results = fSphere.translate(factory.getFPoint(1, 2, 3));

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(2, results.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(3, results.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(4, results.getCenterZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, results.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Translate with FPos3D")
        void translateWithFPos3D() {
            FSphere fSphere = factory.getFSphere(1, 1, 1, 1);

            Shape results = fSphere.translate(factory.getFPos3D(1, 2, 3));

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(2, results.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(3, results.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(4, results.getCenterZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(1, results.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Scale")
        void scale() {
            FSphere fSphere = factory.getFSphere(2, 3, 4, 1);

            Shape results = fSphere.scale(2);

            Assertions.assertAll("Validate FSphere values",
                    () -> assertEquals(4, results.getCenterX(),
                            "The X value is incorrect"),
                    () -> assertEquals(6, results.getCenterY(),
                            "The Y value is incorrect"),
                    () -> assertEquals(8, results.getCenterZ(),
                            "The Z value is incorrect"),
                    () -> assertEquals(2, results.getRadius(),
                            "The radius is incorrect"),
                    () -> assertSame(fSphere, results,
                            "The FSphere reference should not change")
            );
        }

        @Test
        @DisplayName("Get distance center")
        void getDistCenter() {
            FSphere fSphereA = factory.getFSphere(2, 2, 2, 1);
            FSphere fSphereB = factory.getFSphere(-2, -2, -2, 3);

            double distA = fSphereA.getDistCenter(fSphereB);
            double distB = fSphereB.getDistCenter(fSphereA);

            Assertions.assertAll("Validate distance values",
                    () -> assertEquals(4 * Math.sqrt(3), distA,
                            epsilon, "The distance A is incorrect"),
                    () -> assertEquals(4 * Math.sqrt(3), distB,
                            epsilon, "The distance B is incorrect"),
                    () -> assertEquals(factory.getFSphere(2, 2, 2, 1), fSphereA,
                            "Shape A should not change"),
                    () -> assertEquals(factory.getFSphere(-2, -2, -2, 3), fSphereB,
                            "Shape B should not change")
            );
        }

        @Test
        @DisplayName("Get distance center P2")
        void getDistCenterP2() {
            FSphere fSphereA = factory.getFSphere(2, 2, 2, 1);
            FSphere fSphereB = factory.getFSphere(-2, -2, -2, 3);

            double distA = fSphereA.getDistCenterP2(fSphereB);
            double distB = fSphereB.getDistCenterP2(fSphereA);

            Assertions.assertAll("Validate distance values",
                    () -> assertEquals(4 * 4 * 3, distA,
                            epsilon, "The distance A is incorrect"),
                    () -> assertEquals(4 * 4 * 3, distB,
                            epsilon, "The distance B is incorrect"),
                    () -> assertEquals(factory.getFSphere(2, 2, 2, 1), fSphereA,
                            "Shape A should not change"),
                    () -> assertEquals(factory.getFSphere(-2, -2, -2, 3), fSphereB,
                            "Shape B should not change")
            );
        }

        @Test
        @DisplayName("Set distance center")
        void setDistCenter() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(-2, -2, -2, 3);

            Shape results = fSphereA.setDistCenter(fSphereB, 4 * Math.sqrt(3));

            double distA = fSphereA.getDistCenter(fSphereB);
            double distB = fSphereB.getDistCenter(fSphereA);

            Assertions.assertAll("Validate distance values",
                    () -> assertEquals(4 * Math.sqrt(3), distA,
                            epsilon, "The distance A is incorrect"),
                    () -> assertEquals(4 * Math.sqrt(3), distB,
                            epsilon, "The distance B is incorrect"),
                    () -> assertTrue(factory.getFSphere(2, 2, 2, 1).isSimilar(fSphereA),
                            "Shape A is not correct"),
                    () -> assertEquals(factory.getFSphere(-2, -2, -2, 3), fSphereB,
                            "Shape B should not change"),
                    () -> assertSame(fSphereA, results,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Set distance center (exception)")
        void setDistCenterException() {
            FSphere fSphereA = factory.getFSphere(-2, -2, -2, 1);
            FSphere fSphereB = factory.getFSphere(-2, -2, -2, 3);

            assertThrows(IllegalStateException.class, () -> fSphereA.setDistCenter(fSphereB, 1),
                    "The operation can not be performed");
        }

        @Test
        @DisplayName("Epsilon")
        void setEpsilon() {
            Shape fSphere = factory.getFSphere();

            assertEquals(SHAPE_EPSILON, fSphere.getEpsilon(),
                    "The default epsilon value is incorrect");

            Shape results = fSphere.setEpsilon(123);

            assertEquals(123, fSphere.getEpsilon(),
                    "The epsilon value is incorrect");
            assertSame(results, fSphere,
                    "The reference should not change");
        }

        @Test
        @DisplayName("Delta")
        void setDelta() {
            Shape fSphere = factory.getFSphere();

            assertEquals(SHAPE_DELTA, fSphere.getDelta(),
                    "The default delta value is incorrect");

            Shape results = fSphere.setDelta(123);

            assertEquals(123, fSphere.getDelta(),
                    "The delta value is incorrect");
            assertSame(results, fSphere,
                    "The reference should not change");
        }

        @Test
        @DisplayName("Index")
        void setIndex() {
            Shape fSphere = factory.getFSphere();

            assertEquals(-1, fSphere.getIndex(),
                    "The default index value is incorrect");

            Shape results = fSphere.setIndex(123);

            assertEquals(123, fSphere.getIndex(),
                    "The index value is incorrect");
            assertSame(results, fSphere,
                    "The reference should not change");
        }

        @Test
        @DisplayName("Tag")
        void setTag() {
            Shape fSphere = factory.getFSphere();

            assertEquals("", fSphere.getTag(),
                    "The default tag value is incorrect");

            Shape results = fSphere.setTag("123");

            assertEquals("123", fSphere.getTag(),
                    "The tag value is incorrect");
            assertSame(results, fSphere,
                    "The reference should not change");
        }

        @Test
        @DisplayName("Cache (set)")
        void setFCache() {
            Shape fSphere = factory.getFSphere();

            assertNull(fSphere.getCache(),
                    "The cache value should be null");

            FCache cache = factory.getFCache();
            Shape results = fSphere.setCache(cache);

            assertSame(cache, fSphere.getCache(),
                    "The cache instance is incorrect");
            assertSame(results, fSphere,
                    "The reference should not change");
        }

        @Test
        @DisplayName("Cache (create)")
        void createFCache() {
            Shape fSphere = factory.getFSphere();

            assertNull(fSphere.getCache(),
                    "The cache value should be null");

            Shape results = fSphere.createCache();

            assertNotNull(fSphere.getCache(),
                    "The cache instance should not be null");
            assertSame(results, fSphere,
                    "The reference should not change");
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

            double x = fSphere.getCenterX();
            double y = fSphere.getCenterY();
            double z = fSphere.getCenterZ();

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

            double x = fSphere.getCenterX();
            double y = fSphere.getCenterY();
            double z = fSphere.getCenterZ();

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

            double x = fSphere.getCenterX();
            double y = fSphere.getCenterY();
            double z = fSphere.getCenterZ();

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

            double x = fSphere.getCenterX();
            double y = fSphere.getCenterY();
            double z = fSphere.getCenterZ();

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
        @DisplayName("Overlapping shapes (epsilon)")
        void getOverlappingShapesEpsilon() {
            List<Shape> in = new ArrayList<>();

            Shape fSphereRef = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(5, 0, 0, 1)
                    .setDelta(-1);
            Shape fSphereC = factory.getFSphere(5, 5, 5, 1)
                    .setDelta(-1);

            FAssembly<Shape> fAssembly = factory.getFAssembly();

            fAssembly.register(fSphereRef);
            fAssembly.register(fSphereA);
            fAssembly.register(fSphereB);
            fAssembly.register(fSphereC);

            fSphereRef.getOverlappingShapes(in, fAssembly.getListGeometry());
            fSphereRef.getOverlappingShapes(in, fAssembly.getListGeometry());

            Assertions.assertAll("Validate positions",
                    () -> assertEquals(2, in.size(),
                            "The size of the list is incorrect"),
                    () -> assertTrue(in.contains(fSphereA),
                            "The list should contain shape A"),
                    () -> assertTrue(in.contains(fSphereB),
                            "The list should contain shape B")
            );
        }

        @Test
        @DisplayName("Overlapping shapes (delta)")
        void getOverlappingShapesDelta() {
            List<Shape> in = new ArrayList<>();

            Shape fSphereRef = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1);
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(5, 0, 0, 1)
                    .setEpsilon(-1);
            Shape fSphereC = factory.getFSphere(5, 5, 5, 1)
                    .setEpsilon(-1);

            FAssembly<Shape> fAssembly = factory.getFAssembly();

            fAssembly.register(fSphereRef);
            fAssembly.register(fSphereA);
            fAssembly.register(fSphereB);
            fAssembly.register(fSphereC);

            fSphereRef.getOverlappingShapes(in, fAssembly.getListGeometry());
            fSphereRef.getOverlappingShapes(in, fAssembly.getListGeometry());

            Assertions.assertAll("Validate positions",
                    () -> assertEquals(2, in.size(),
                            "The size of the list is incorrect"),
                    () -> assertTrue(in.contains(fSphereA),
                            "The list should contain shape A"),
                    () -> assertTrue(in.contains(fSphereB),
                            "The list should contain shape B")
            );
        }

        @Test
        @DisplayName("SortByDistance")
        void sortByDistance() {
            List<Shape> in = new ArrayList<>();

            FSphere fSphereRef = factory.getFSphere(0, 0, 0, 5);
            FSphere fSphereA = factory.getFSphere(0, 5, 0, 1);
            FSphere fSphereB = factory.getFSphere(5, 5, 5, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            in.add(fSphereA);
            in.add(fSphereB);
            in.add((fSphereC));

            fSphereRef.sortByDistance(in);

            Assertions.assertAll("Validate positions",
                    () -> assertEquals(3, in.size(),
                            "The size of the list is incorrect"),
                    () -> assertSame(fSphereA, in.get(1),
                            "The position of shape A is incorrect"),
                    () -> assertSame(fSphereB, in.get(2),
                            "The position of shape B is incorrect"),
                    () -> assertSame(fSphereC, in.get(0),
                            "The position of shape C is incorrect")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) - same position")
        void enclosesEpsilonSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) - distant")
        void enclosesEpsilonDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) A")
        void enclosesEpsilonA() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(4.5, 0, 0, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) B")
        void enclosesEpsilonB() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(4, 0, 0, 1 - 0.15)
                    .setDelta(-1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) C")
        void enclosesEpsilonC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(3.99));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The sphere should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (epsilon) D")
        void enclosesEpsilonD() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(4.1));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The sphere should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) - same position")
        void enclosesDeltaSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setEpsilon(-1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) - distant")
        void enclosesDeltaDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setEpsilon(-1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) A")
        void enclosesDeltaA() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.1);
            Shape fSphereB = factory.getFSphere(4.5, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) B")
        void enclosesDeltaB() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.1);
            Shape fSphereB = factory.getFSphere(4, 0, 0, 1 - 0.15)
                    .setEpsilon(-1)
                    .setDelta(0.1);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The spheres should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) C")
        void enclosesDeltaC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(3.99));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate enclosure",
                    () -> assertTrue(fSphereA.encloses(fSphereB),
                            "The sphere should be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The sphere should not be enclosed")
            );
        }

        @Test
        @DisplayName("Encloses (delta) D")
        void enclosesDeltaD() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(4.1));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate enclosure",
                    () -> assertFalse(fSphereA.encloses(fSphereB),
                            "The sphere should not be enclosed"),
                    () -> assertFalse(fSphereB.encloses(fSphereA),
                            "The sphere should not be enclosed")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon)")
        void intersectsEpsilon() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) - same position")
        void intersectsEpsilonSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) - distant")
        void intersectsEpsilonDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) - point contact")
        void intersectsEpsilonPointContact() {
            Shape fSphereA = factory.getFSphere(-1, 0, 0, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) A")
        void intersectsEpsilonA() {
            Shape fSphereA = factory.getFSphere(-1 + 0.01, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) B")
        void intersectsEpsilonB() {
            Shape fSphereA = factory.getFSphere(-1 + 0.1, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) C")
        void intersectsEpsilonC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(5));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) D")
        void intersectsEpsilonD() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(3.99));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (epsilon) E")
        void intersectsEpsilonE() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6.01));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta)")
        void intersectsDelta() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setEpsilon(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) - same position")
        void intersectsDeltaSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setEpsilon(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) - distant")
        void intersectsDeltaDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setEpsilon(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) - point contact")
        void intersectsDeltaPointContact() {
            Shape fSphereA = factory.getFSphere(-1, 0, 0, 1)
                    .setEpsilon(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setEpsilon(-1);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) A")
        void intersectsDeltaA() {
            Shape fSphereA = factory.getFSphere(-1 + 0.01, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) B")
        void intersectsDeltaB() {
            Shape fSphereA = factory.getFSphere(-1 + 0.1, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) C")
        void intersectsDeltaC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(5));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate intersection",
                    () -> assertTrue(fSphereA.intersects(fSphereB),
                            "The spheres should intersect"),
                    () -> assertTrue(fSphereB.intersects(fSphereA),
                            "The spheres should intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) D")
        void intersectsDeltaD() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(3.99));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Intersects (delta) E")
        void intersectsDeltaE() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6.01));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate intersection",
                    () -> assertFalse(fSphereA.intersects(fSphereB),
                            "The spheres should not intersect"),
                    () -> assertFalse(fSphereB.intersects(fSphereA),
                            "The spheres should not intersect")
            );
        }

        @Test
        @DisplayName("Touches (epsilon)")
        void touchesEpsilon() {
            Shape fSphereA = factory.getFSphere(0, 1, 0, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, -1, 0, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) - same position")
        void touchesEpsilonSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) - distant")
        void touchesEpsilonDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) A")
        void touchesEpsilonA() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) B")
        void touchesEpsilonB() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1 - 0.01)
                    .setEpsilon(0.05)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) C")
        void touchesEpsilonC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) D")
        void touchesEpsilonD() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6.1));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) E")
        void touchesEpsilonE() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(5.9));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) A - fail")
        void touchesEpsilonFailA() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(0.01)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1)
                    .setEpsilon(0.01)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (epsilon) B - fail")
        void touchesEpsilonFailB() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(0.01)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1)
                    .setEpsilon(0.01)
                    .setDelta(-1);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta)")
        void touchesDelta() {
            Shape fSphereA = factory.getFSphere(0, 1, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(0, -1, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) - same position")
        void touchesDeltaSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) - distant")
        void touchesDeltaDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) A")
        void touchesDeltaA() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);
            Shape fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) B")
        void touchesDeltaB() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1 - 0.01)
                    .setEpsilon(-1)
                    .setDelta(0.05);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) C")
        void touchesDeltaC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.touches(fSphereB),
                            "The spheres should be in point contact"),
                    () -> assertTrue(fSphereB.touches(fSphereA),
                            "The spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) D")
        void touchesDeltaD() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6.1));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) E")
        void touchesDeltaE() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(5.9));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) A - fail")
        void touchesDeltaFailA() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Touches (delta) B - fail")
        void touchesDeltaFailB() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.touches(fSphereB),
                            "The spheres should not be in point contact"),
                    () -> assertFalse(fSphereB.touches(fSphereA),
                            "The spheres should not be in point contact")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - same position")
        void overlapsEpsilonSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - distant")
        void overlapsEpsilonDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - A")
        void overlapsEpsilonA() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleInSphere(5.9));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - B")
        void overlapsEpsilonB() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(5.9));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - C")
        void overlapsEpsilonC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setDelta(-1);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6.1));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon)")
        void overlapsEpsilon() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1.01, 0, 0, 1)
                    .setDelta(-1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon) - fail")
        void overlapsEpsilonFail() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(1.01, 0, 0, 1)
                    .setEpsilon(0.05)
                    .setDelta(-1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon, min)")
        void overlapsEpsilonMin() {
            Shape fSphereA = factory.getFSphere(0.03, 0, 0, 0.01)
                    .setEpsilon(1E-6)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01)
                    .setEpsilon(1E-6)
                    .setDelta(-1);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (epsilon, min) - fail")
        void overlapsEpsilonFailMin() {
            Shape fSphereA = factory.getFSphere(0.03, 0, 0, 0.01)
                    .setEpsilon(1E-4)
                    .setDelta(-1);
            Shape fSphereB = factory.getFSphere(0.01001, 0, 0, 0.01)
                    .setEpsilon(1E-4)
                    .setDelta(-1);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) A - fail")
        void overlapsDeltaFailA() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(1 + 0.025, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) B - fail")
        void overlapsDeltaFailB() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(1 - 0.025, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate point contact",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - same position")
        void overlapsDeltaSamePosition() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 2)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(1, 2, 3, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - distant")
        void overlapsDeltaDistant() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);
            Shape fSphereB = factory.getFSphere(-1, -1, -1, 1)
                    .setEpsilon(-1)
                    .setDelta(0.01);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should not overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should not overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - A")
        void overlapsDeltaA() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleInSphere(5.9));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - B")
        void overlapsDeltaB() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(5.9));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - C")
        void overlapsDeltaC() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 5)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            FRandGenerator rand = factory.getFRand();

            fSphereB.setCenter(rand.nextDoubleOnSphere(6.1));

            FPos3D translation = rand.nextDouble3D(factory.getFPairPos3D(100));

            fSphereA.translate(translation);
            fSphereB.translate(translation);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta)")
        void overlapsDelta() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);
            Shape fSphereB = factory.getFSphere(1.01, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.005);

            Assertions.assertAll("Validate overlap",
                    () -> assertTrue(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertTrue(fSphereB.overlaps(fSphereA),
                            "The spheres should overlap")
            );
        }

        @Test
        @DisplayName("Overlaps (delta) - fail")
        void overlapsDeltaFail() {
            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);
            Shape fSphereB = factory.getFSphere(1.01, 0, 0, 1)
                    .setEpsilon(-1)
                    .setDelta(0.05);

            Assertions.assertAll("Validate overlap",
                    () -> assertFalse(fSphereA.overlaps(fSphereB),
                            "The spheres should overlap"),
                    () -> assertFalse(fSphereB.overlaps(fSphereA),
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
        @DisplayName("Attach linear enclosed")
        void attachLinearEnclosed() {
            Shape fSphereRef = factory.getFSphere(10);

            Producer<FPoint> fPointProducer = factory.getFPointProducer()
                    .withInSphere(8.9);
            Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                    .withCenterAndFixedRadius(fPointProducer, 1);

            Shape fSphereArg = fSphereProducer.produce();

            FPos3D offset = factory.getFRand().nextDoubleInSphere(1000);

            fSphereRef.translate(offset);
            fSphereArg.translate(offset);

            assertTrue(fSphereRef.encloses(fSphereArg),
                    "The argument sphere should be enclosed");

            boolean isPositioned = fSphereRef.attachLinear(fSphereArg);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isPositioned,
                            "The reference sphere should be positioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach linear distant")
        void attachLinearDistant() {
            Shape fSphereRef = factory.getFSphere(1);

            Producer<FPoint> fPointProducer = factory.getFPointProducer()
                    .withRadius(10);
            Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                    .withCenterAndFixedRadius(fPointProducer, 1);

            Shape fSphereArg = fSphereProducer.produce();

            FPos3D offset = factory.getFRand().nextDoubleInSphere(1000);

            fSphereRef.translate(offset);
            fSphereArg.translate(offset);

            assertFalse(fSphereRef.overlaps(fSphereArg),
                    "The argument sphere should be not overlap");

            boolean isPositioned = fSphereRef.attachLinear(fSphereArg);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isPositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach linear touching")
        void attachLinearTouching() {
            Shape fSphereRef = factory.getFSphere(1);

            Producer<FPoint> fPointProducer = factory.getFPointProducer()
                    .withRadius(2);
            Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                    .withCenterAndFixedRadius(fPointProducer, 1);

            Shape fSphereArg = fSphereProducer.produce();

            FPos3D offset = factory.getFRand().nextDoubleInSphere(1000);

            fSphereRef.translate(offset);
            fSphereArg.translate(offset);

            assertTrue(fSphereRef.touches(fSphereArg),
                    "The argument sphere should be in point contact");

            boolean isPositioned = fSphereRef.attachLinear(fSphereArg);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(isPositioned,
                            "The reference sphere should be repositioned"),
                    () -> assertTrue(fSphereRef.touches(fSphereArg),
                            "Spheres should be in point contact")
            );
        }

        @Test
        @DisplayName("Attach linear fail, zero")
        void attachLinearFailZero() {
            Shape fSphereRef = factory.getFSphere(1);
            Shape fSphereArg = factory.getFSphere(5);

            FPos3D offset = factory.getFRand().nextDoubleInSphere(1000);

            fSphereRef.translate(offset);
            fSphereArg.translate(offset);

            boolean isPositioned = fSphereRef.attachLinear(fSphereArg);

            Assertions.assertAll("Validate results",
                    () -> assertFalse(isPositioned,
                            "The reference sphere should be repositioned")
            );
        }




//        @Test
//        @DisplayName("Attach (field) - empty")
//        void attachFieldEmpty() {
//            FSphere fSphereRef = factory.getFSphere();
//            FSphere fSphereArg = factory.getFSphere();
//
//            FAssembly<FSphere> fSphereField = factory.getFAssembly();
//
//            factory.getFRandEngine().inSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);
//
//            boolean isRepositioned = fSphereRef.attachLinear(fSphereArg, epsilon, -1, fSphereField, 0);
//
//            Assertions.assertAll("Validate results",
//                    () -> assertTrue(isRepositioned,
//                            "The number of repositions is incorrect"),
//                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
//                            "Spheres should be in point contact")
//            );
//        }
//
//        @Test
//        @DisplayName("Attach (field) - self")
//        void attachFieldOrigin() {
//            FSphere fSphereRef = factory.getFSphere();
//            FSphere fSphereArg = factory.getFSphere();
//
//            FAssembly<FSphere> fSphereField = factory.getFAssembly();
//            fSphereField.register(fSphereRef);
//            fSphereField.register(fSphereArg);
//
//            factory.getFRandEngine().inSphere(fSphereArg.getRefCenter(), fSphereArg.getRadius() * 0.75);
//
//            boolean repositions = fSphereRef.attachLinear(fSphereArg, epsilon, -1 ,fSphereField, 0);
//
//            Assertions.assertAll("Validate results",
//                    () -> assertTrue(repositions,
//                            "The number of repositions is incorrect"),
//                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
//                            "Spheres should be in point contact")
//            );
//        }
//
//        @Test
//        @DisplayName("Attach (field) - bounce 1")
//        void attachFieldBounce1() {
//            FSphere fSphereRef = factory.getFSphere(0, 1, 0, 1);
//            FSphere fSphereArg = factory.getFSphere(0, 0, 0, 2);
//            FSphere fSphereField1 = factory.getFSphere(-0.25, 3, 0, 1);
//
//            FAssembly<FSphere> fSphereField = factory.getFAssembly();
//            fSphereField.register(fSphereRef);
//            fSphereField.register(fSphereArg);
//            fSphereField.register(fSphereField1);
//
//            boolean repositions = fSphereRef.attachLinear(fSphereArg, epsilon, -1, fSphereField, 5);
//
//            Assertions.assertAll("Validate results",
//                    () -> assertTrue(repositions,
//                            "The number of repositions is incorrect"),
//                    () -> assertTrue(fSphereRef.touches(fSphereArg, epsilon, -1),
//                            "Spheres should be in point contact (arg)"),
//                    () -> assertTrue(fSphereRef.touches(fSphereField1, epsilon, -1),
//                            "Spheres should be in point contact (neighbour)")
//            );
//        }

//        @Test
//        @DisplayName("Push (field, error) - incorrect bounce")
//        void pushFieldErrorBounce1() {
//            FSphere fSphereRef = factory.getFSphere(0, 1, 0, 1);
//            FSphere fSphereArg = factory.getFSphere(0, 0, 0, 2);
//            FSphere fSphereField1 = factory.getFSphere(-0.25, 3, 0, 1);
//
//            FAssembly<FSphere> fSphereField = factory.getFAssembly();
//            fSphereField.register(fSphereRef);
//            fSphereField.register(fSphereArg);
//            fSphereField.register(fSphereField1);
//
//            boolean repositions = fSphereRef.attachLinear(fSphereArg, epsilon, -1, fSphereField, 0);
//
//            Assertions.assertAll("Validate results",
//                    () -> assertFalse(repositions,
//                            "The number of repositions is incorrect"),
//                    () -> assertFalse(fSphereRef.touches(fSphereField1, epsilon, -1),
//                            "The spheres should not touch (neighbour)")
//            );
//        }
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
        @DisplayName("Is exact center")
        void isExactCenter() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 1);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3, 2);

            assertTrue((fSphereRef.isExactCenter(fSphereArg)), "FSphere centers should be exact");
        }

        @Test
        @DisplayName("Is exact center (fail)")
        void isExactCenterFail() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 1);
            FSphere fSphereArg = factory.getFSphere(1, 2, 4, 1);

            assertFalse((fSphereRef.isExactCenter(fSphereArg)), "FSphere centers should not be exact");
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
        @DisplayName("Is similar center")
        void isSimilarCenter() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 1);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 0.5), 2);

            assertTrue((fSphereRef.isSimilarCenter(fSphereArg)), "FSphere centers should be similar");
        }

        @Test
        @DisplayName("Is similar center (fail)")
        void isSimilarCenterFail() {
            FSphere fSphereRef = factory.getFSphere(1, 2, 3, 1);
            FSphere fSphereArg = factory.getFSphere(1, 2, 3 + (epsilon * 2), 2);

            assertFalse((fSphereRef.isSimilarCenter(fSphereArg)), "FSphere centers should not be similar");
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
