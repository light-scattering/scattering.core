package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.impl.factory.ScatterFactoryDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.impl.ScatterCoreConfig.SHAPE_DELTA;
import static eu.scattering.core.impl.ScatterCoreConfig.SHAPE_EPSILON;
import static eu.scattering.core.test.TestConfig.epsilon;
import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FSphereProducer")
public class FSphereProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FSphereProducer producer = factory.getFSphereProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger radius = new AtomicInteger(1);

        FSphereProducer producer = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(radius.getAndIncrement()), 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(1, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(2, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger radius = new AtomicInteger(1);

        FSphereProducer producer = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(radius.getAndIncrement()));

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(1, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(2, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 1)
                .withCustomRule((factory) -> factory.getFSphere(2), 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRadius() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(countFinalA * 1.5 < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 5)
                .withCustomRule((factory) -> factory.getFSphere(2), 10)
                .withCustomRule((factory) -> factory.getFSphere(3), 15);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (FSphere fSphere : producer.getList()) {

            if (fSphere.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (fSphere.getRadius() == 1) {
                qRadius1++;
            } else if (fSphere.getRadius() == 2) {
                qRadius2++;
            } else if (fSphere.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qRadius1, "Distribution 1 is erroneous");
        assertEquals(10, qRadius2, "Distribution 2 is erroneous");
        assertEquals(15, qRadius3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, auto (exception)")
    void iterateAutoException() {
        FSphereProducer producer = factory.getFSphereProducer();

        assertThrows(IllegalStateException.class, producer::getList,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Iterate, fixed (single)")
    void iterateFixedSingle() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 5)
                .withCustomRule((factory) -> factory.getFSphere(2), 10)
                .withCustomRule((factory) -> factory.getFSphere(3), 15);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (FSphere fSphere : producer.getListFixed(30)) {

            if (fSphere.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (fSphere.getRadius() == 1) {
                qRadius1++;
            } else if (fSphere.getRadius() == 2) {
                qRadius2++;
            } else if (fSphere.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qRadius1, "Distribution 1 is erroneous");
        assertEquals(10, qRadius2, "Distribution 2 is erroneous");
        assertEquals(15, qRadius3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed (multiple)")
    void iterateFixedMultiple() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 5)
                .withCustomRule((factory) -> factory.getFSphere(2), 10)
                .withCustomRule((factory) -> factory.getFSphere(3), 15);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (FSphere fSphere : producer.getListFixed(60)) {

            if (fSphere.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (fSphere.getRadius() == 1) {
                qRadius1++;
            } else if (fSphere.getRadius() == 2) {
                qRadius2++;
            } else if (fSphere.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(10, qRadius1, "Distribution 1 is erroneous");
        assertEquals(20, qRadius2, "Distribution 2 is erroneous");
        assertEquals(30, qRadius3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed (below)")
    void iterateFixedBelow() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 1)
                .withCustomRule((factory) -> factory.getFSphere(2), 1)
                .withCustomRule((factory) -> factory.getFSphere(3), 1);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (FSphere fSphere : producer.getListFixed(8)) {

            if (fSphere.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (fSphere.getRadius() == 1) {
                qRadius1++;
            } else if (fSphere.getRadius() == 2) {
                qRadius2++;
            } else if (fSphere.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(8, qRadius1 + qRadius2 + qRadius3, "The number of elements is incorrect");
        assertEquals(3, qRadius1, 1, "Distribution 1 is erroneous");
        assertEquals(3, qRadius2, 1, "Distribution 2 is erroneous");
        assertEquals(3, qRadius3, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed (above)")
    void iterateFixedAbove() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 1)
                .withCustomRule((factory) -> factory.getFSphere(2), 1)
                .withCustomRule((factory) -> factory.getFSphere(3), 1);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (FSphere fSphere : producer.getListFixed(10)) {

            if (fSphere.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (fSphere.getRadius() == 1) {
                qRadius1++;
            } else if (fSphere.getRadius() == 2) {
                qRadius2++;
            } else if (fSphere.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(10, qRadius1 + qRadius2 + qRadius3, "The number of elements is incorrect");
        assertEquals(3, qRadius1, 1, "Distribution 1 is erroneous");
        assertEquals(3, qRadius2, 1, "Distribution 2 is erroneous");
        assertEquals(3, qRadius3, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed (random)")
    void iterateFixedRandom() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 20)
                .withCustomRule((factory) -> factory.getFSphere(2), 20)
                .withCustomRule((factory) -> factory.getFSphere(3), 20);

        List<FSphere> results = producer.getListFixed(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Iterate, fixed (zero)")
    void iterateFixedZero() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 5)
                .withCustomRule((factory) -> factory.getFSphere(2), 10)
                .withCustomRule((factory) -> factory.getFSphere(3), 15);

        List<FSphere> results = producer.getListFixed(0);

        assertEquals( 0, results.size(), "The number of elements is incorrect");
    }

    @Test
    @DisplayName("Iterate, fixed (state exception)")
    void iterateFixedStateException() {
        FSphereProducer producer = factory.getFSphereProducer();

        assertThrows(IllegalStateException.class, () -> producer.getListFixed(1),
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Iterate, fixed (argument exception)")
    void iterateFixedArgumentException() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 1)
                .withCustomRule((factory) -> factory.getFSphere(2), 1)
                .withCustomRule((factory) -> factory.getFSphere(3), 1);

        assertThrows(IllegalArgumentException.class, () -> producer.getListFixed(-1),
                "The argument should be at least zero");
    }

    @Test
    @DisplayName("Iterate, random")
    void iterateRandom() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 20)
                .withCustomRule((factory) -> factory.getFSphere(2), 20)
                .withCustomRule((factory) -> factory.getFSphere(3), 20);

        List<FSphere> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Iterate, random (zero)")
    void iterateRandomZero() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 5)
                .withCustomRule((factory) -> factory.getFSphere(2), 10)
                .withCustomRule((factory) -> factory.getFSphere(3), 15);

        List<FSphere> results = producer.getListRandomized(0);

        assertEquals( 0, results.size(), "The number of elements is incorrect");
    }

    @Test
    @DisplayName("Iterate, random (state exception)")
    void iterateRandomStateException() {
        FSphereProducer producer = factory.getFSphereProducer();

        assertThrows(IllegalStateException.class, () -> producer.getListRandomized(1),
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Iterate, random (argument exception)")
    void iterateRandomArgumentException() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 1)
                .withCustomRule((factory) -> factory.getFSphere(2), 1)
                .withCustomRule((factory) -> factory.getFSphere(3), 1);

        assertThrows(IllegalArgumentException.class, () -> producer.getListRandomized(-1),
                "The argument should be at least zero");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((fSphere) -> factory.getFSphere(1), 1)
                .withCustomRule((fSphere) -> factory.getFSphere(2), 1);

        List<FSphere> list = producer.stream().limit(100).toList();

        Assertions.assertAll("Validate values",
                () -> assertTrue(list.stream().anyMatch(e -> e.getRadius() == 1),
                        "The distribution is erroneous"),
                () -> assertTrue(list.stream().anyMatch(e -> e.getRadius() == 2),
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Stream, random")
    void streamRandom() {
        FSphereProducer producer = factory.getFSphereProducer();

        producer
                .withCustomRule((factory) -> factory.getFSphere(1), 20)
                .withCustomRule((factory) -> factory.getFSphere(2), 20)
                .withCustomRule((factory) -> factory.getFSphere(3), 20);

        List<FSphere> results = producer.stream().limit(60).toList();

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Stream (state exception)")
    void streamStateException() {
        FSphereProducer producer = factory.getFSphereProducer();

        assertThrows(IllegalStateException.class, producer::stream,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Preset fixed radius")
    void presetFixedRadius() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withFixRadius(5, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(5, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(5, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset fixed radius (simple)")
    void presetFixedRadiusSimple() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withFixRadius(5);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(0, 0, 0),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(0, 0, 0),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(5, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(5, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dist radius")
    void presetDistRadius() {
        FDist1D radius = factory.getFRand()
                .getFDist1DUniform(epsilon, 0.001);

        FSphereProducer producer = factory.getFSphereProducer()
                .withDistRadius(radius, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Tag B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset dist radius (simple)")
    void presetDistRadiusSimple() {
        FDist1D radius = factory.getFRand()
                .getFDist1DUniform(epsilon, 0.001);

        FSphereProducer producer = factory.getFSphereProducer()
                .withDistRadius(radius);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset center and fixed radius")
    void presetCenterAndFixedRadius() {
        FPointProducer pCenter = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(pCenter, 5, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(1, 2, 3),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(1, 2, 3),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(5, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(5, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dist center and fixed radius")
    void presetDistCenterAndFixedRadius() {
        FDist3D center = factory.getFRand()
                .getFDist3DManual((random, results) -> {
                    results[0] = 1.0;
                    results[1] = 2.0;
                    results[2] = 3.0;
                });

        FSphereProducer producer = factory.getFSphereProducer()
                .withDistCenterAndFixRadius(center, 5, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(1, 2, 3),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(1, 2, 3),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(5, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(5, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset center and fixed radius (simple)")
    void presetCenterAndFixedRadiusSimple() {
        FPointProducer pCenter = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withProdCenterAndFixRadius(pCenter, 5);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(1, 2, 3),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(1, 2, 3),
                        "The FSphere B center position is erroneous"),
                () -> assertEquals(5, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(5, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset center and dist radius")
    void presetCenterAndDistRadius() {
        FDist1D radius = factory.getFRand()
                .getFDist1DUniform(epsilon, 0.001);

        FPointProducer center = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withProdCenterAndDistRadius(center, radius, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(1, 2, 3),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(1, 2, 3),
                        "The FSphere B center position is erroneous"),
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset dist center and dist radius")
    void presetDistCenterAndDistRadius() {
        FDist1D radius = factory.getFRand()
                .getFDist1DUniform(epsilon, 0.001);

        FDist3D center = factory.getFRand()
                .getFDist3DManual((random, results) -> {
                    results[0] = 1.0;
                    results[1] = 2.0;
                    results[2] = 3.0;
                });

        FSphereProducer producer = factory.getFSphereProducer()
                .withDistCenterAndDistRadius(center, radius, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(1, 2, 3),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(1, 2, 3),
                        "The FSphere B center position is erroneous"),
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset center and dist radius (simple)")
    void presetCenterAndDistRadiusSimple() {
        FDist1D radius = factory.getFRand()
                .getFDist1DUniform(epsilon, 0.001);

        FPointProducer pCenter = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withProdCenterAndDistRadius(pCenter, radius);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertTrue(resultA.getRefCenter().isExact(1, 2, 3),
                        "The FSphere A center position is erroneous"),
                () -> assertTrue(resultB.getRefCenter().isExact(1, 2, 3),
                        "The FSphere B center position is erroneous"),
                () -> assertTrue(Math.abs(resultA.getRadius()) < 0.01,
                        "Radius A is incorrect"),
                () -> assertTrue(Math.abs(resultB.getRadius()) < 0.01,
                        "Radius B is incorrect"),
                () -> assertEquals(-1, resultA.getIndex(),
                        "Index A is incorrect"),
                () -> assertEquals(-1, resultB.getIndex(),
                        "Index B is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Produce with aspect")
    void produceWithAspect() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withCustomRule((factoryLocal, aspect) -> {
                    FPoint fPoint = factory.getFPoint(1, 0, 0);

                    aspect.onSphere(fPoint);

                    return factoryLocal.getFSphere(fPoint.getX(), fPoint.getY(), fPoint.getZ(), 1);
                }, 1);

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals(1, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(1, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce with aspect (simple)")
    void produceWithAspectSimple() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withCustomRule((factoryLocal, aspect) -> {
                    FPoint fPoint = factory.getFPoint(1, 0, 0);

                    aspect.onSphere(fPoint);

                    return factoryLocal.getFSphere(fPoint.getX(), fPoint.getY(), fPoint.getZ(), 1);
                });

        FSphere resultA = producer.produce();
        FSphere resultB = producer.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals(1, resultA.getRadius(),
                        epsilon, "The Sphere A radius is erroneous"),
                () -> assertEquals(1, resultB.getRadius(),
                        epsilon, "The Sphere B radius is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Setters")
    void setters() {

        FSphereProducer producerA = factory.getFSphereProducer()
                .withFixRadius(1);

        FSphere resultA = producerA.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("", resultA.getMeta()),
                () -> assertEquals(SHAPE_EPSILON, resultA.getEpsilon()),
                () -> assertEquals(SHAPE_DELTA, resultA.getDelta())
        );

        FSphereProducer producerB = producerA
                .setMeta("123")
                .setEpsilon(1)
                .setDelta(2);

        FSphere resultB = producerB.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("123", resultB.getMeta()),
                () -> assertEquals(1, resultB.getEpsilon()),
                () -> assertEquals(2, resultB.getDelta()),
                () -> assertSame(producerA, producerB)
        );
    }

    @Test
    @DisplayName("Setters with list")
    void settersWithList() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withFixRadius(1)
                .setMeta("123")
                .setEpsilon(1)
                .setDelta(2);

        FSphere fSphere = producer.getList().getFirst();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("123", fSphere.getMeta()),
                () -> assertEquals(1, fSphere.getEpsilon()),
                () -> assertEquals(2, fSphere.getDelta())
        );

        FSphere fSphereFixed = producer.getListFixed(1).getFirst();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("123", fSphereFixed.getMeta()),
                () -> assertEquals(1, fSphereFixed.getEpsilon()),
                () -> assertEquals(2, fSphereFixed.getDelta())
        );

        FSphere fSphereRandomized = producer.getListRandomized(1).getFirst();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("123", fSphereRandomized.getMeta()),
                () -> assertEquals(1, fSphereRandomized.getEpsilon()),
                () -> assertEquals(2, fSphereRandomized.getDelta())
        );
    }

    @Test
    @DisplayName("Setters with stream")
    void settersWithStream() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withFixRadius(1)
                .setMeta("123")
                .setEpsilon(1)
                .setDelta(2);

        FSphere fSphere = producer.stream().limit(1).toList().getFirst();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("123", fSphere.getMeta()),
                () -> assertEquals(1, fSphere.getEpsilon()),
                () -> assertEquals(2, fSphere.getDelta())
        );
    }

    @Test
    @DisplayName("Facade - Custom rule (Function)")
    void facadeCustomRuleFunction() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        Producer<FSphere> producerA = factoryA.getFSphereProducer((factory) -> {
            double x = factoryA.getFRand().nextDouble();
            double y = factoryA.getFRand().nextDouble();
            double z = factoryA.getFRand().nextDouble();
            double r = factoryA.getFRand().nextDouble();

            return factory.getFSphere(x, y, z, r);
        });

        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withCustomRule((factory) -> {
                    double x = factoryB.getFRand().nextDouble();
                    double y = factoryB.getFRand().nextDouble();
                    double z = factoryB.getFRand().nextDouble();
                    double r = factoryB.getFRand().nextDouble();

                    return factory.getFSphere(x, y, z, r);
                });

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Custom rule (BiFunction)")
    void facadeCustomRuleBiFunction() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        Producer<FSphere> producerA = factoryA.getFSphereProducer((factory, random) -> {
            double x = random.generator().nextDouble();
            double y = random.generator().nextDouble();
            double z = random.generator().nextDouble();
            double r = random.generator().nextDouble();

            return factory.getFSphere(x, y, z, r);
        });

        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withCustomRule((factory, random) -> {
                    double x = random.generator().nextDouble();
                    double y = random.generator().nextDouble();
                    double z = random.generator().nextDouble();
                    double r = random.generator().nextDouble();

                    return factory.getFSphere(x, y, z, r);
                });

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Fixed radius")
    void facadeFixedRadius() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        Producer<FSphere> producerA = factoryA.getFSphereProducer(5);
        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withFixRadius(5);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Dist radius")
    void facadeDistRadius() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        FDist1D distA = factoryA.getFRand().getFDist1DUniform(1, 2);
        FDist1D distB = factoryB.getFRand().getFDist1DUniform(1, 2);

        Producer<FSphere> producerA = factoryA.getFSphereProducer(distA);
        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withDistRadius(distB);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Center and fixed radius")
    void facadeCenterFixedRadius() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        FDist3D distPA = factoryA.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);
        FDist3D distPB = factoryB.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);

        Producer<FPoint> prodA = factoryA.getFPointProducer(distPA);
        Producer<FPoint> prodB = factoryB.getFPointProducer(distPB);

        Producer<FSphere> producerA = factoryA.getFSphereProducer(prodA, 5);
        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withProdCenterAndFixRadius(prodB, 5);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Center and dist radius")
    void facadeCenterDistRadius() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        FDist3D distPA = factoryA.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);
        FDist3D distPB = factoryB.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);

        Producer<FPoint> prodA = factoryA.getFPointProducer(distPA);
        Producer<FPoint> prodB = factoryB.getFPointProducer(distPB);

        FDist1D distRA = factoryA.getFRand().getFDist1DUniform(1, 2);
        FDist1D distRB = factoryB.getFRand().getFDist1DUniform(1, 2);

        Producer<FSphere> producerA = factoryA.getFSphereProducer(prodA, distRA);
        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withProdCenterAndDistRadius(prodB, distRB);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Dist center and fixed radius")
    void facadeDistCenterFixedRadius() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        FDist3D distPA = factoryA.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);
        FDist3D distPB = factoryB.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);

        Producer<FSphere> producerA = factoryA.getFSphereProducer(distPA, 5);
        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withDistCenterAndFixRadius(distPB, 5);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Dist center and dist radius")
    void facadeDistCenterDistRadius() {
        long seed = 123;

        ScatterFactory factoryA = ScatterFactoryDef.create(seed);
        ScatterFactory factoryB = ScatterFactoryDef.create(seed);

        FDist3D distPA = factoryA.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);
        FDist3D distPB = factoryB.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);

        FDist1D distRA = factoryA.getFRand().getFDist1DUniform(1, 2);
        FDist1D distRB = factoryB.getFRand().getFDist1DUniform(1, 2);

        Producer<FSphere> producerA = factoryA.getFSphereProducer(distPA, distRA);
        Producer<FSphere> producerB = factoryB.getFSphereProducer()
                .withDistCenterAndDistRadius(distPB, distRB);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Add correction")
    void addCorrection() {
        FPoint center = factory.getFPoint();

        Producer<FSphere> producer = factory.getFSphereProducer(1)
                .addCorrection((fSphere, randomizer) -> fSphere.setCenter(center))
                .addCorrection((fSphere, randomizer) -> fSphere.setMeta("TEST"));

        center.set(1, 2, 3);

        FSphere fSphereA = producer.produce();

        Assertions.assertAll("Validate FSphere A",
                () -> assertEquals("TEST", fSphereA.getMeta(),
                        "The tag is erroneous"),
                () -> assertTrue(fSphereA.isExact(factory.getFSphere(1, 2, 3, 1)),
                        "The position is erroneous")
        );

        center.set(4, 5, 6);

        FSphere fSphereB = producer.produce();

        Assertions.assertAll("Validate FSphere B",
                () -> assertEquals("TEST", fSphereB.getMeta(),
                        "The tag is erroneous"),
                () -> assertTrue(fSphereB.isExact(factory.getFSphere(4, 5, 6, 1)),
                        "The position is erroneous"),
                () -> assertNotSame(fSphereA, fSphereB,
                        "The reference should be different")
        );
    }

    @Test
    @DisplayName("Add mutation")
    void addMutation() {
        Producer<FSphere> producer = factory.getFSphereProducer(1)
                .addMutation((list) -> list.forEach(e -> e.setMeta("TEST")));

        List<FSphere> results = producer.getListFixed(2);

        Assertions.assertAll("Validate FSphere A",
                () -> assertEquals("TEST", results.getFirst().getMeta(),
                        "The tag is erroneous")
        );

        Assertions.assertAll("Validate FSphere B",
                () -> assertEquals("TEST", results.get(1).getMeta(),
                        "The tag is erroneous"),
                () -> assertNotSame(results.getFirst(), results.get(1),
                        "The reference should be different")
        );
    }

    @Test
    @DisplayName("Add validation")
    void addValidation() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .addValidation(((fSphere, results) -> fSphere.overlaps(results) == 0));

        List<FSphere> results = new ArrayList<>();

        while (true) {
            FSphere candidate = producer.produce();

            if (candidate != null) {
                results.add(candidate);
            } else {
                break;
            }
        }

        Assertions.assertAll("Validate results",
                () -> assertTrue(!results.isEmpty() && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Validate list")
    void validateList() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .addValidation(((fSphere, results) -> fSphere.overlaps(results) == 0));

        List<FSphere> results = producer.getList();

        Assertions.assertAll("Validate results",
                () -> assertTrue(!results.isEmpty() && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Validate list (fixed)")
    void validateListFixed() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .addValidation(((fSphere, results) -> fSphere.overlaps(results) == 0));

        List<FSphere> results = producer.getListFixed(100);

        Assertions.assertAll("Validate results",
                () -> assertTrue(!results.isEmpty() && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Validate list (randomized)")
    void validateListRandomized() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .addValidation(((fSphere, results) -> fSphere.overlaps(results) == 0))
                .setSkipOnFailure(true);

        List<FSphere> results = producer.getListRandomized(100);

        Assertions.assertAll("Validate results",
                () -> assertTrue(!results.isEmpty() && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Mutate add coat")
    void mutateAddCoat() {
        FPairPos3D range = factory.getFPairPos3D(10);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .mutateAddCoat(3, 2, 1);

        List<FSphere> results = producer.getListRandomized(5);

        for (FSphere fSphere : results) {

            Assertions.assertAll("Validate coats",
                    () -> assertEquals(3, fSphere.getCoatCount(),
                            "The number of coats is erroneous"),
                    () -> assertEquals(3, fSphere.getCoatWidth(0),
                            "Coat 0 width is erroneous"),
                    () -> assertEquals(2, fSphere.getCoatWidth(1),
                            "Coat 1 width is erroneous"),
                    () -> assertEquals(1, fSphere.getCoatWidth(2),
                            "Coat 2 width is erroneous"),
                    () -> assertEquals(7, fSphere.getRadius(),
                            "The radius is erroneous")
            );
        }
    }

    @Test
    @DisplayName("Correct add coat")
    void correctAddCoat() {
        FPairPos3D range = factory.getFPairPos3D(10);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .correctAddCoat(3, 2, 1);

        for (int i = 0 ; i < 5 ; i++) {
            FSphere fSphere = producer.produce();

            Assertions.assertAll("Validate coats",
                    () -> assertEquals(3, fSphere.getCoatCount(),
                            "The number of coats is erroneous"),
                    () -> assertEquals(3, fSphere.getCoatWidth(0),
                            "Coat 0 width is erroneous"),
                    () -> assertEquals(2, fSphere.getCoatWidth(1),
                            "Coat 1 width is erroneous"),
                    () -> assertEquals(1, fSphere.getCoatWidth(2),
                            "Coat 2 width is erroneous"),
                    () -> assertEquals(7, fSphere.getRadius(),
                            "The radius is erroneous")
            );
        }
    }

    @Test
    @DisplayName("Validate no overlap")
    void validateNoOverlap() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> center = factory.getFPointProducer(range);

        Producer<FSphere> producer = factory.getFSphereProducer(center, 1)
                .validateNoOverlap();

        List<FSphere> results = new ArrayList<>();

        while (true) {
            FSphere candidate = producer.produce();

            if (candidate != null) {
                results.add(candidate);
            } else {
                break;
            }
        }

        Assertions.assertAll("Validate results",
                () -> assertTrue(!results.isEmpty() && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }
}
