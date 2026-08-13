package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static eu.scattering.core.test.TestConfig.epsilon;
import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FVectorProducer")
public class FVectorProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FVectorProducer producer = factory.getFVectorProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        AtomicInteger length = new AtomicInteger(1);

        FVectorProducer producer = factory.getFVectorProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFVector(-lengthCurrent, -lengthCurrent, -lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);
        }, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(-1, -1, -1, 1, 1, 1),
                        "The FVector A value is erroneous"),
                () -> assertTrue(resultB.isExact(-2, -2, -2, 2, 2, 2),
                        "The FVector B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom (simple)")
    void produceCustomSimple() {
        AtomicInteger length = new AtomicInteger(1);

        FVectorProducer producer = factory.getFVectorProducer().withCustomRule((factory) -> {
            int lengthCurrent = length.getAndIncrement();

            return factory.getFVector(-lengthCurrent, -lengthCurrent, -lengthCurrent, lengthCurrent, lengthCurrent, lengthCurrent);
        });

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(-1, -1, -1, 1, 1, 1),
                        "The FVector A value is erroneous"),
                () -> assertTrue(resultB.isExact(-2, -2, -2, 2, 2, 2),
                        "The FVector B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((factory) -> factory.getFVector().setHeadX(1), 1)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(2), 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getHeadX() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((factory) -> factory.getFVector().setHeadX(1), 5)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(2), 10)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(3), 15);

        int qLength1 = 0;
        int qLength2 = 0;
        int qLength3 = 0;

        for (FVector fVector : producer.getList()) {

            if (fVector.getHeadX() == 1) {
                qLength1++;
            } else if (fVector.getHeadX() == 2) {
                qLength2++;
            } else if (fVector.getHeadX() == 3) {
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
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((factory) -> factory.getFVector().setHeadX(1), 1)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(2), 1)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(3), 1);

        int qLength1 = 0;
        int qLength2 = 0;
        int qLength3 = 0;

        for (FVector fVector : producer.getListFixed(8)) {

            if (fVector.getHeadX() == 1) {
                qLength1++;
            } else if (fVector.getHeadX() == 2) {
                qLength2++;
            } else if (fVector.getHeadX() == 3) {
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
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((factory) -> factory.getFVector().setHeadX(1), 20)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(2), 20)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(3), 20);

        List<FVector> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getHeadX() != 1) {
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
        FVectorProducer producer = factory.getFVectorProducer();

        producer
                .withCustomRule((factory) -> factory.getFVector().setHeadX(1), 20)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(2), 20)
                .withCustomRule((factory) -> factory.getFVector().setHeadX(3), 20);

        List<FVector> results = producer.stream().limit(60).collect(Collectors.toList());

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getHeadX() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Preset dir X")
    void presetDirX() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withDirOX(3, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 3, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dir X (simple)")
    void presetDirXSimple() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withDirOX(3);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 3, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dir Y")
    void presetDirY() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withDirOY(3, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 3, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dir Y (simple)")
    void presetDirYSimple() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withDirOY(3);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 3, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dir Z")
    void presetDirZ() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withDirOZ(1, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset dir Z (simple)")
    void presetDirZSimple() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withDirOZ(1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset base dir X")
    void presetBaseDirX() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndDirOX(pBase, 3, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(1, 2, 3, 4, 2, 3),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset base dir X (simple)")
    void presetBaseDirXSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndDirOX(pBase, 3);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(1, 2, 3, 4, 2, 3),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset base dir Y")
    void presetBaseDirY() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndDirOY(pBase, 3, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(1, 2, 3, 1, 5, 3),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset base dir Y (simple)")
    void presetBaseDirYSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndDirOY(pBase, 3);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(1, 2, 3, 1, 5, 3),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset base dir Z")
    void presetBaseDirZ() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndDirOZ(pBase, 3, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(1, 2, 3, 1, 2, 6),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset base dir Z (simple)")
    void presetBaseDirZSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndDirOZ(pBase, 3);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.isExact(1, 2, 3, 1, 2, 6),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset in radius")
    void presetInRadius() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withInSphere(0.01, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "Value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset in radius (simple)")
    void presetInRadiusSimple() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withInSphere(0.01);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "Value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on radius")
    void presetOnRadius() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withOnSphere(0.01, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "The position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "The value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset on radius (simple)")
    void presetOnRadiusSimple() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withOnSphere(0.01);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "The position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "The value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset base in radius")
    void presetBaseInRadius() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndInSphere(pBase, 0.01, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset base in radius (simple)")
    void presetBaseInRadiusSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndInSphere(pBase, 0.01);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getMagnitude() < 0.01,
                        "Position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset base on radius")
    void presetBaseOnRadius() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndOnSphere(pBase, 0.01, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "The position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "The value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset base on radius (simple)")
    void presetBaseOnRadiusSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndOnSphere(pBase, 0.01);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(resultA.getMagnitude(), 0.01,
                        epsilon, "The position is incorrect"),
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "The value is incorrect"),
                () -> assertFalse(resultA.isExact(resultB),
                        "Values should be different")
        );
    }

    @Test
    @DisplayName("Preset base")
    void presetBase() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBase(pBase, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Base position is incorrect"),
                () -> assertTrue(resultA.getRefHead().isExact(0, 0, 0),
                        "Head position is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "References should be different")
        );
    }

    @Test
    @DisplayName("Preset base (simple)")
    void presetBaseSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBase(pBase);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Base position is incorrect"),
                () -> assertTrue(resultA.getRefHead().isExact(0, 0, 0),
                        "Head position is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "References should be different")
        );
    }

    @Test
    @DisplayName("Preset head")
    void presetHead() {
        FPointProducer pHead = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withHead(pHead, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "Base position is incorrect"),
                () -> assertTrue(resultA.getRefHead().isExact(1, 2, 3),
                        "Head position is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "References should be different")
        );
    }

    @Test
    @DisplayName("Preset head (simple)")
    void presetHeadSimple() {
        FPointProducer pHead = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FVectorProducer producer = factory.getFVectorProducer()
                .withHead(pHead);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getRefBase().isExact(0, 0, 0),
                        "Base position is incorrect"),
                () -> assertTrue(resultA.getRefHead().isExact(1, 2, 3),
                        "Head position is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "References should be different")
        );
    }

    @Test
    @DisplayName("Preset base and head")
    void presetBaseAndHead() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FPointProducer pHead = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(4, 5, 6));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndHead(pBase, pHead, 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Base position is incorrect"),
                () -> assertTrue(resultA.getRefHead().isExact(4, 5, 6),
                        "Head position is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "References should be different")
        );
    }

    @Test
    @DisplayName("Preset base and head (simple)")
    void presetBaseAndHeadSimple() {
        FPointProducer pBase = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(1, 2, 3));

        FPointProducer pHead = factory.getFPointProducer()
                .withCustomRule((factory) -> factory.getFPoint(4, 5, 6));

        FVectorProducer producer = factory.getFVectorProducer()
                .withBaseAndHead(pBase, pHead);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertTrue(resultA.getRefBase().isExact(1, 2, 3),
                        "Base position is incorrect"),
                () -> assertTrue(resultA.getRefHead().isExact(4, 5, 6),
                        "Head position is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "References should be different")
        );
    }

    @Test
    @DisplayName("Produce with aspect")
    void produceWithAspect() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withCustomRule((factory, aspect) ->
                        aspect.onSphere(factory.getFVector(1, 2, 3, 2, 2, 3)), 1);

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(1, resultA.getMagnitude(),
                        epsilon, "The FVector A magnitude is erroneous"),
                () -> assertEquals(1, resultB.getMagnitude(),
                        epsilon, "The FVector B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce with aspect (simple)")
    void produceWithAspectSimple() {
        FVectorProducer producer = factory.getFVectorProducer()
                .withCustomRule((factory, aspect) ->
                        aspect.onSphere(factory.getFVector(1, 2, 3, 2, 2, 3)));

        FVector resultA = producer.produce();
        FVector resultB = producer.produce();

        Assertions.assertAll("Validate FVector values",
                () -> assertEquals(1, resultA.getMagnitude(),
                        epsilon, "The FVector A magnitude is erroneous"),
                () -> assertEquals(1, resultB.getMagnitude(),
                        epsilon, "The FVector B magnitude is erroneous"),
                () -> assertNotEquals(resultA, resultB,
                        "Elements should have different values"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
