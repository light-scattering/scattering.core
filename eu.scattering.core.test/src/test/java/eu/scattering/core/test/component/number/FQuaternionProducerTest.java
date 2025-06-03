package eu.scattering.core.test.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Timeout(5)
@DisplayName("FQuaternionProducer")
public class FQuaternionProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        FQuaternionProducer producer = factory.getFQuaternionProducer();

        FQuaternion resultA = producer.produce();
        FQuaternion resultB = producer.produce();

        Assertions.assertAll("Validate FQuaternion values",
                () -> assertTrue(resultA.isExact(0, 0, 0, 0),
                        "The FQuaternion A value is erroneous"),
                () -> assertTrue(resultB.isExact(0, 0, 0, 0),
                        "The FQuaternion B value is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }
}
