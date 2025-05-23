package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FComplexProducer")
public class FComplexProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FComplexProducer producer = factory.getFComplexProducer().setPresetEmpty();

        FComplex resultA = producer.produce();
        FComplex resultB = producer.produce();

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(resultA.isExact(0, 0),
                        "The FComplex A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0),
                        "The FComplex B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce custom")
    void produceCustom() {
        FComplexProducer producer = factory.getFComplexProducer();

        AtomicInteger length = new AtomicInteger(1);
        producer.setConfig((fComplex) -> {
            int lengthCurrent = length.getAndIncrement();

            fComplex.set(lengthCurrent, lengthCurrent);

            return fComplex;
        });

        FComplex resultA = producer.produce();
        FComplex resultB = producer.produce();

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(resultA.isExact(1, 1),
                        "The FComplex A value is erroneous"),
                () -> assertTrue(resultB.isExact(2, 2),
                        "The FComplex B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FComplexProducer producer = factory.getFComplexProducer();

        producer
                .addConfig((fComplex) -> fComplex.setRe(1), 0.25)
                .addConfig((fComplex) -> fComplex.setRe(2), 0.75);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRe() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate FComplex values",
                () -> assertTrue(countFinalA < countFinalB,
                        "The distribution is erroneous")
        );
    }
}
