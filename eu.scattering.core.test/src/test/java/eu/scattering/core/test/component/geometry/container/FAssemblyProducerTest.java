package eu.scattering.core.test.component.geometry.container;

import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.container.assembly.FAssemblyProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FAssemblyProducer")
public class FAssemblyProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FAssemblyProducer<?> producer = factory.getFAssemblyProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer.withCustomRule((factoryLocal) -> {
            int lengthCurrent = length.getAndIncrement();

            FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();

            fAssembly.register(factory.getFSphere(lengthCurrent, lengthCurrent, lengthCurrent));

            return fAssembly;
        }, 1);

        FAssembly<FSphere> resultA = producer.produce();
        FAssembly<FSphere> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertTrue(resultA.getGeometries().get(0).getRefCenter().isExact(1, 1, 1),
                        "The FAssembly A value is erroneous"),
                () -> assertTrue(resultB.getGeometries().get(0).getRefCenter().isExact(2, 2, 2),
                        "The FAssembly B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer.withCustomRule((factoryLocal) -> {
            int lengthCurrent = length.getAndIncrement();

            FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();

            fAssembly.register(factory.getFSphere(lengthCurrent, lengthCurrent, lengthCurrent));

            return fAssembly;
        });

        FAssembly<FSphere> resultA = producer.produce();
        FAssembly<FSphere> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertTrue(resultA.getGeometries().get(0).getRefCenter().isExact(1, 1, 1),
                        "The FAssembly A value is erroneous"),
                () -> assertTrue(resultB.getGeometries().get(0).getRefCenter().isExact(2, 2, 2),
                        "The FAssembly B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 1)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getGeometries().get(0).getRadius() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FAssembly values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 5)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 10)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(3));
                    return fAssembly;
                }, 15);

        int qCountA = 0;
        int aCountB = 0;
        int qCountC = 0;

        for (FAssembly<FSphere> fAssembly : producer.getListAuto()) {

            if (fAssembly.getGeometries().get(0).getRadius() == 1) {
                qCountA++;
            } else if (fAssembly.getGeometries().get(0).getRadius() == 2) {
                aCountB++;
            } else if (fAssembly.getGeometries().get(0).getRadius() == 3) {
                qCountC++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qCountA, "Distribution 1 is erroneous");
        assertEquals(10, aCountB, "Distribution 2 is erroneous");
        assertEquals(15, qCountC, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed")
    void iterateFixed() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 1)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 1)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(3));
                    return fAssembly;
                }, 1);

        int qCountA = 0;
        int qCountB = 0;
        int qCountC = 0;

        for (FAssembly<FSphere> fAssembly : producer.getListFixed(8)) {

            if (fAssembly.getGeometries().get(0).getRadius() == 1) {
                qCountA++;
            } else if (fAssembly.getGeometries().get(0).getRadius() == 2) {
                qCountB++;
            } else if (fAssembly.getGeometries().get(0).getRadius() == 3) {
                qCountC++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(8, qCountA + qCountB + qCountC, "The number of elements is incorrect");
        assertEquals(3, qCountA, 1, "Distribution 1 is erroneous");
        assertEquals(3, qCountB, 1, "Distribution 2 is erroneous");
        assertEquals(3, qCountC, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, random")
    void iterateRandom() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 20)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 20)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(3));
                    return fAssembly;
                }, 20);

        List<FAssembly<FSphere>> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getGeometries().get(0).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FAssemblyProducer<FSphere> producer = factory.getFAssemblyProducer();

        producer
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(1));
                    return fAssembly;
                }, 20)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(2));
                    return fAssembly;
                }, 20)
                .withCustomRule((factoryLocal) -> {
                    FAssembly<FSphere> fAssembly = factoryLocal.getFAssembly();
                    fAssembly.register(factory.getFSphere(3));
                    return fAssembly;
                }, 20);

        List<FAssembly<FSphere>> results = producer.stream().limit(60).collect(Collectors.toList());

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getGeometries().get(0).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Produce with engine")
    void produceWithEngine() {
        FAssemblyProducer<FPoint> producer = factory.getFAssemblyProducer();

        producer.withCustomRule((factoryLocal, engine) -> {
            FAssembly<FPoint> fAssembly = factoryLocal.getFAssembly();

            FPoint fPoint = factory.getFPoint(1, 0, 0);

            engine.varyAngle(fPoint);

            fAssembly.register(fPoint);

            return fAssembly;
        }, 1);

        FAssembly<FPoint> resultA = producer.produce();
        FAssembly<FPoint> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertEquals(1, resultA.getGeometries().get(0).getMagnitude(),
                        epsilon, "The FPoint A magnitude is erroneous"),
                () -> assertEquals(1, resultB.getGeometries().get(0).getMagnitude(),
                        epsilon, "The FPoint B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce with engine (simple)")
    void produceWithEngineSimple() {
        FAssemblyProducer<FPoint> producer = factory.getFAssemblyProducer();

        producer.withCustomRule((factoryLocal, engine) -> {
            FAssembly<FPoint> fAssembly = factoryLocal.getFAssembly();

            FPoint fPoint = factory.getFPoint(1, 0, 0);

            engine.varyAngle(fPoint);

            fAssembly.register(fPoint);

            return fAssembly;
        });

        FAssembly<FPoint> resultA = producer.produce();
        FAssembly<FPoint> resultB = producer.produce();

        Assertions.assertAll("Validate FAssembly values",
                () -> assertEquals(1, resultA.getGeometries().get(0).getMagnitude(),
                        epsilon, "The FPoint A magnitude is erroneous"),
                () -> assertEquals(1, resultB.getGeometries().get(0).getMagnitude(),
                        epsilon, "The FPoint B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
