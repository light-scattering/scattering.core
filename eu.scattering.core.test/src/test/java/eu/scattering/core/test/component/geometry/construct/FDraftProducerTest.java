package eu.scattering.core.test.component.geometry.construct;

import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.draft.FDraftProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FDraftProducer")
public class FDraftProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FDraftProducer producer = factory.getFDraftProducer();

        FDraft resultA = producer.produce();
        FDraft resultB = producer.produce();

        Assertions.assertAll("Validate FDraft values",
                () -> assertTrue(resultA.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The FDraft A value is erroneous"),
                () -> assertTrue(resultB.getRefOrigin().isExact(0, 0, 0, 1, 0, 0),
                        "The FDraft B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
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
                .setConfig((fDraft) -> fDraft.set(
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
    @DisplayName("Preset unit X")
    void presetUnitX() {
        FDraftProducer producer = factory.getFDraftProducer().setPresetUnitX();

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
    @DisplayName("Preset unit Y")
    void presetUnitY() {
        FDraftProducer producer = factory.getFDraftProducer().setPresetUnitY();

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
    @DisplayName("Preset unit Z")
    void presetUnitZ() {
        FDraftProducer producer = factory.getFDraftProducer().setPresetUnitZ();

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
