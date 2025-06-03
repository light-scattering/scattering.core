package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.complex.FComplexProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FComplexProducer")
public class FComplexProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FComplexProducer producer = factory.getFComplexProducer();

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
}
