package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FDraftProducer")
public class FDraftProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FDraftProducer producer = factory.getFDraftProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FDraftProducer producer = factory.getFDraftProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fDraft) -> {
            int lengthCurrent = length.getAndIncrement();

            fDraft.getRefOrigin().set(
                    lengthCurrent, lengthCurrent, lengthCurrent,
                    lengthCurrent, lengthCurrent, lengthCurrent
            );

            return fDraft;
        });

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(1, 1, 1, 1, 1, 1),
                        "The FDraft A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(2, 2, 2, 2, 2, 2),
                        "The FDraft B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FDraftProducer producer = factory.getFDraftProducer();

        producer
                .addConfig((fDraft) -> fDraft.set(
                        factory.getFPairPos3D(0, 0, 0, 1, 0, 0)), 0.25)
                .addConfig((fDraft) -> fDraft.set(
                        factory.getFPairPos3D(0, 0, 0, 2, 0, 0)), 0.75);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRefOrigin().getHeadX() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Preset set unit X")
    void presetSetUnitX() {
        FDraftProducer producer = factory.getFDraftProducer();
        producer.setPresetUnitX();

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit X")
    void presetAddUnitX() {
        FDraftProducer producer = factory.getFDraftProducer().addPresetUnitX(1);

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set unit Y")
    void presetSetUnitY() {
        FDraftProducer producer = factory.getFDraftProducer();
        producer.setPresetUnitY();

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit Y")
    void presetAddUnitY() {
        FDraftProducer producer = factory.getFDraftProducer().addPresetUnitY(1);

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 1, 0),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset set unit Z")
    void presetSetUnitZ() {
        FDraftProducer producer = factory.getFDraftProducer();
        producer.setPresetUnitZ();

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Preset add unit Z")
    void presetAddUnitZ() {
        FDraftProducer producer = factory.getFDraftProducer().addPresetUnitZ(1);

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 0, 0, 1),
                        "The value is incorrect"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
