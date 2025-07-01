package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.impl.ConfigDef.SHAPE_DELTA;
import static eu.scattering.core.impl.ConfigDef.SHAPE_EPSILON;
import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
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
        for (FSphere fSphere : producer.getListAuto()) {

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

        assertThrows(IllegalStateException.class, producer::getListAuto,
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
                .withFixedRadius(5, 1);

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
                .withFixedRadius(5);

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
    @DisplayName("Preset base fixed radius")
    void presetBaseFixedRadius() {
        FPointProducer pCenter = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withCenterAndFixedRadius(pCenter, 5, 1);

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
    @DisplayName("Preset base fixed radius (simple)")
    void presetBaseFixedRadiusSimple() {
        FPointProducer pCenter = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withCenterAndFixedRadius(pCenter, 5);

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

        FPointProducer pCenter = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FSphereProducer producer = factory.getFSphereProducer()
                .withCenterAndDistRadius(pCenter, radius, 1);

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
                .withCenterAndDistRadius(pCenter, radius);

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
    @DisplayName("Produce with engine")
    void produceWithEngine() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withCustomRule((factoryLocal, engine) -> {
                    FPoint fPoint = factory.getFPoint(1, 0, 0);

                    engine.onSphere(fPoint);

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
    @DisplayName("Produce with engine (simple)")
    void produceWithEngineSimple() {
        FSphereProducer producer = factory.getFSphereProducer()
                .withCustomRule((factoryLocal, engine) -> {
                    FPoint fPoint = factory.getFPoint(1, 0, 0);

                    engine.onSphere(fPoint);

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
                .withFixedRadius(1);

        FSphere resultA = producerA.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("", resultA.getTag(),
                        "The default tag is erroneous"),
                () -> assertEquals(SHAPE_EPSILON, resultA.getEpsilon(),
                        "The default epsilon value is erroneous"),
                () -> assertEquals(SHAPE_DELTA, resultA.getDelta(),
                        "The default delta value is erroneous"),
                () -> assertNull(resultA.getCache(),
                        "The default cache should be null")
        );

        FCache cache = factory.getFCache();

        FSphereProducer producerB = producerA
                .setTag("123")
                .setEpsilon(1)
                .setDelta(2)
                .setCache(cache);

        FSphere resultB = producerB.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertEquals("123", resultB.getTag(),
                        "The tag is erroneous"),
                () -> assertEquals(1, resultB.getEpsilon(),
                        "The epsilon value is erroneous"),
                () -> assertEquals(2, resultB.getDelta(),
                        "The delta value is erroneous"),
                () -> assertSame(cache, resultB.getCache(),
                        "The cache value is erroneous"),
                () -> assertSame(producerA, producerB,
                        "The reference should not change")
        );

        FSphereProducer producerC = producerB
                .createCache();

        FSphere resultC = producerC.produce();

        Assertions.assertAll("Validate FSphere values",
                () -> assertNotNull(resultC.getCache(),
                        "The cache value should not be null"),
                () -> assertNotSame(cache, resultC.getCache(),
                        "The cache value is erroneous"),
                () -> assertSame(producerB, producerC,
                        "The reference should not change")
        );
    }
}
