package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.design.storage.transfer.position.p2.variants.FPairPos3D;
import eu.scattering.core.design.utility.type.Location;
import eu.scattering.core.impl.FactoryDef;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointProducer")
public class FPointProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FPointProducer producer = factory.getFPointProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FPointProducer producer = factory.getFPointProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFPoint(lengthCurrent, lengthCurrent, lengthCurrent);
        }, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(1, 1, 1),
                        "The FPoint A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2, 2),
                        "The FPoint B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FPointProducer producer = factory.getFPointProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFPoint(lengthCurrent, lengthCurrent, lengthCurrent);
        });

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(1, 1, 1),
                        "The FPoint A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2, 2),
                        "The FPoint B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 1)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 2);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getX() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 5)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 10)
                .withCustomRule((factory) -> factory.getFPoint().setX(3), 15);

        int qLength1 = 0;
        int qLength2 = 0;
        int qLength3 = 0;

        for (FPoint fPoint : producer.getList()) {

            if (fPoint.getX() == 1) {
                qLength1++;
            } else if (fPoint.getX() == 2) {
                qLength2++;
            } else if (fPoint.getX() == 3) {
                qLength3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qLength1, "Distribution 1 is erroneous");
        assertEquals(10, qLength2, "Distribution 2 is erroneous");
        assertEquals(15, qLength3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed")
    void iterateFixed() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 1)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 1)
                .withCustomRule((factory) -> factory.getFPoint().setX(3), 1);

        int qLength1 = 0;
        int qLength2 = 0;
        int qLength3 = 0;

        for (FPoint fPoint : producer.getListFixed(8)) {

            if (fPoint.getX() == 1) {
                qLength1++;
            } else if (fPoint.getX() == 2) {
                qLength2++;
            } else if (fPoint.getX() == 3) {
                qLength3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(8, qLength1 + qLength2 + qLength3, "The number of elements is incorrect");
        assertEquals(3, qLength1, 1, "Distribution 1 is erroneous");
        assertEquals(3, qLength2, 1, "Distribution 2 is erroneous");
        assertEquals(3, qLength3, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, random")
    void iterateRandom() {
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 20)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 20)
                .withCustomRule((factory) -> factory.getFPoint().setX(3), 20);

        List<FPoint> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getX() != 1) {
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
        FPointProducer producer = factory.getFPointProducer();

        producer
                .withCustomRule((factory) -> factory.getFPoint().setX(1), 20)
                .withCustomRule((factory) -> factory.getFPoint().setX(2), 20)
                .withCustomRule((factory) -> factory.getFPoint().setX(3), 20);

        List<FPoint> results = producer.stream().limit(60).toList();

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getX() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Preset zero")
    void presetZero() {
        FPointProducer producer = factory.getFPointProducer()
                .withZero(1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(0, 0, 0),
                        "The FPoint A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0),
                        "The FPoint B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset zero (simple)")
    void presetZeroSimple() {
        FPointProducer producer = factory.getFPointProducer()
                .withZero();

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.isExact(0, 0, 0),
                        "The FPoint A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0),
                        "The FPoint B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset in range")
    void presetInRange() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FPointProducer producer = factory.getFPointProducer()
                .withInRange(range, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(Math.abs(resultA.getX()) < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getY()) < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getZ()) < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in range (simple)")
    void presetInRangeSimple() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FPointProducer producer = factory.getFPointProducer()
                .withInRange(range);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(Math.abs(resultA.getX()) < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getY()) < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getZ()) < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in sphere")
    void presetInSphere() {
        FPointProducer producer = factory.getFPointProducer()
                .withInSphere(0.01, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in sphere (simple)")
    void presetInSphereSimple() {
        FPointProducer producer = factory.getFPointProducer()
                .withInSphere(0.01);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on sphere")
    void presetOnSphere() {
        FPointProducer producer = factory.getFPointProducer()
                .withOnSphere(0.01, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on sphere (simple)")
    void presetOnSphereSimple() {
        FPointProducer producer = factory.getFPointProducer()
                .withOnSphere(0.01);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in spherical shell")
    void presetInSphericalShell() {
        FPointProducer producer = factory.getFPointProducer()
                .withInShell(0.01, 0.02, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getMagnitude() >= 0.01 && resultA.getMagnitude() < 0.02 ,
                        "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in spherical shell (simple)")
    void presetInSphericalShellSimple() {
        FPointProducer producer = factory.getFPointProducer()
                .withInShell(0.01, 0.02);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA.getMagnitude() >= 0.01 && resultA.getMagnitude() < 0.02 ,
                        "Position is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset distribution")
    void presetDistribution() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FDist3D dist = factory.getFRand().getFDist3DUniform(range);
        FPointProducer producer = factory.getFPointProducer()
                .withDist(dist, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(Math.abs(resultA.getX()) < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getY()) < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getZ()) < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset distribution (simple)")
    void presetDistributionSimple() {
        FPairPos3D range = factory.getFPairPos3D(-0.01, -0.01, -0.01, 0.01, 0.01, 0.01);
        FDist3D dist = factory.getFRand().getFDist3DUniform(range);
        FPointProducer producer = factory.getFPointProducer()
                .withDist(dist);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(Math.abs(resultA.getX()) < 0.01,
                        "Value X is incorrect"),
                () -> assertTrue(Math.abs(resultA.getY()) < 0.01,
                        "Value Y is incorrect"),
                () -> assertTrue(Math.abs(resultA.getZ()) < 0.01,
                        "Value Z is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Produce with aspect")
    void produceWithAspect() {
        AtomicInteger length = new AtomicInteger(1);

        FPointProducer producer = factory.getFPointProducer().withCustomRule((factory, aspect) -> {
            int lengthCurrent = length.getAndIncrement();

            return aspect.onSphere(factory.getFPoint(lengthCurrent));
        }, 1);

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(1, resultA.getMagnitude(),
                        epsilon, "The FPoint A magnitude is erroneous"),
                () -> assertEquals(2, resultB.getMagnitude(),
                        epsilon, "The FPoint B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce with aspect (simple)")
    void produceWithAspectSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FPointProducer producer = factory.getFPointProducer().withCustomRule((factory, aspect) -> {
            int lengthCurrent = length.getAndIncrement();

            return aspect.onSphere(factory.getFPoint(lengthCurrent));
        });

        FPoint resultA = producer.produce();
        FPoint resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertEquals(1, resultA.getMagnitude(),
                        epsilon, "The FPoint A magnitude is erroneous"),
                () -> assertEquals(2, resultB.getMagnitude(),
                        epsilon, "The FPoint B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Facade - Custom rule (Function)")
    void facadeCustomRuleFunction() {
        long seed = 123;

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        Producer<FPoint> producerA = factoryA.getFPointProducer((factory) -> {
            double x = factoryA.getFRand().nextDouble();
            double y = factoryA.getFRand().nextDouble();
            double z = factoryA.getFRand().nextDouble();

            return factory.getFPoint(x, y, z);
        });

        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withCustomRule((factory) -> {
                    double x = factoryB.getFRand().nextDouble();
                    double y = factoryB.getFRand().nextDouble();
                    double z = factoryB.getFRand().nextDouble();

                    return factory.getFPoint(x, y, z);
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

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        Producer<FPoint> producerA = factoryA.getFPointProducer((factory, random) -> {
            double x = random.getFRand().nextDouble();
            double y = random.getFRand().nextDouble();
            double z = random.getFRand().nextDouble();

            return factory.getFPoint(x, y, z);
        });

        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withCustomRule((factory, random) -> {
                    double x = random.getFRand().nextDouble();
                    double y = random.getFRand().nextDouble();
                    double z = random.getFRand().nextDouble();

                    return factory.getFPoint(x, y, z);
                });

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Distribution")
    void facadeDistribution() {
        long seed = 123;

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        FDist3D distA = factoryA.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);
        FDist3D distB = factoryB.getFRand().getFDist3DUniform(-1, 1, -1, 1, -1, 1);

        Producer<FPoint> producerA = factoryA.getFPointProducer(distA);
        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withDist(distB);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - Range")
    void facadeRange() {
        long seed = 123;

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        FPairPos3D range = factory.getFPairPos3D(-1, -1, -1, 1, 1, 1);

        Producer<FPoint> producerA = factoryA.getFPointProducer(range);
        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withInRange(range);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - In Sphere")
    void facadeInSphere() {
        long seed = 123;

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        Producer<FPoint> producerA = factoryA.getFPointProducer(10, Location.IN_SPHERE);
        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withInSphere(10);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - On Sphere")
    void facadeOnSphere() {
        long seed = 123;

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        Producer<FPoint> producerA = factoryA.getFPointProducer(10, Location.ON_SPHERE);
        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withOnSphere(10);

        for (int i = 0 ; i < 10 ; i++) {
            var valA = producerA.produce();
            var valB = producerB.produce();

            assertTrue(valA.isExact(valB), "Elements must be equal");
        }
    }

    @Test
    @DisplayName("Facade - In Spherical Shell")
    void facadeInSphericalShell() {
        long seed = 123;

        ScatFactory factoryA = FactoryDef.create(seed);
        ScatFactory factoryB = FactoryDef.create(seed);

        Producer<FPoint> producerA = factoryA.getFPointProducer(0.01, 0.02);
        Producer<FPoint> producerB = factoryB.getFPointProducer()
                .withInShell(0.01, 0.02);

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

        Producer<FPoint> producer = factory.getFPointProducer(10, Location.IN_SPHERE)
                .addCorrection((fPoint, randomizer) -> fPoint.setDistance(center, 1));

        center.set(1, 2, 3);

        FPoint fPointA = producer.produce();

        Assertions.assertAll("Validate FPoint A",
                () -> assertEquals(fPointA.getDistance(center), 1,
                        EPSILON, "The position is erroneous")
        );

        center.set(4, 5, 6);

        FPoint fPointB = producer.produce();

        Assertions.assertAll("Validate FPoint B",
                () -> assertEquals(fPointB.getDistance(center), 1,
                        EPSILON, "The position is erroneous"),
                () -> assertNotSame(fPointA, fPointB,
                        "The reference should be different")
        );
    }

    @Test
    @DisplayName("Add mutation")
    void addMutation() {
        Producer<FPoint> producer = factory.getFPointProducer(10, Location.IN_SPHERE)
                .addMutation((list) -> list.forEach(e -> {
                    e.setX(Math.abs(e.getX()));
                    e.setY(Math.abs(e.getY()));
                    e.setZ(Math.abs(e.getZ()));
                }));

        List<FPoint> results = producer.getListFixed(2);

        Assertions.assertAll("Validate FPoint A",
                () -> assertTrue(results.get(0).getX() > 0,
                        "The x value of FPoint A is erroneous"),
                () -> assertTrue(results.get(0).getY() > 0,
                        "The y value of FPoint A is erroneous"),
                () -> assertTrue(results.get(0).getZ() > 0,
                        "The z value of FPoint A is erroneous")
        );

        Assertions.assertAll("Validate FPoint B",
                () -> assertTrue(results.get(1).getX() > 0,
                        "The x value of FPoint B is erroneous"),
                () -> assertTrue(results.get(1).getY() > 0,
                        "The y value of FPoint B is erroneous"),
                () -> assertTrue(results.get(1).getZ() > 0,
                        "The z value of FPoint B is erroneous")
        );
    }

    @Test
    @DisplayName("Add validation")
    void addValidation() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> producer = factory.getFPointProducer(range)
                .addValidation((fPoint, results) -> {
                    for (FPoint result : results) {
                        if (fPoint.getDistance(result) < 2) {
                            return false;
                        }
                    }

                    return true;
                });

        List<FPoint> results = new ArrayList<>();

        while (true) {
            FPoint candidate = producer.produce();

            if (candidate != null) {
                results.add(candidate);
            } else {
                break;
            }
        }

        Assertions.assertAll("Validate results",
                () -> assertTrue(results.size() > 0 && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Validate list")
    void validateList() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> producer = factory.getFPointProducer(range)
                .addValidation((fPoint, results) -> {
                    for (FPoint result : results) {
                        if (fPoint.getDistance(result) < 2) {
                            return false;
                        }
                    }

                    return true;
                });

        List<FPoint> results = producer.getList();

        Assertions.assertAll("Validate results",
                () -> assertTrue(results.size() > 0 && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Validate list (fixed)")
    void validateListFixed() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> producer = factory.getFPointProducer(range)
                .addValidation((fPoint, results) -> {
                    for (FPoint result : results) {
                        if (fPoint.getDistance(result) < 2) {
                            return false;
                        }
                    }

                    return true;
                });

        List<FPoint> results = producer.getListFixed(100);

        Assertions.assertAll("Validate results",
                () -> assertTrue(results.size() > 0 && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }

    @Test
    @DisplayName("Validate list (randomized)")
    void validateListRandomized() {
        FPairPos3D range = factory.getFPairPos3D(1);

        Producer<FPoint> producer = factory.getFPointProducer(range)
                .addValidation((fPoint, results) -> {
                    for (FPoint result : results) {
                        if (fPoint.getDistance(result) < 2) {
                            return false;
                        }
                    }

                    return true;
                });

        List<FPoint> results = producer.getListRandomized(100);

        Assertions.assertAll("Validate results",
                () -> assertTrue(results.size() > 0 && results.size() < 5,
                        "The number of generated elements is erroneous")
        );
    }
}
