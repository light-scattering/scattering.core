package eu.scattering.core.test.core.immutable;

import eu.scattering.core.design.elements.data.position.FPos3DI;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Configuration.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(5)
@DisplayName("FPosition")
public class FPositionTest {

    @Test
    @DisplayName("Create with parameters")
    public void createWithParameters() {
        FPos3DI fPosition = factory.getFPos3DI(1, 2, 3);

        Assertions.assertAll("Check values",
                () -> assertEquals(1, fPosition.getD0(), "The X value is incorrect"),
                () -> assertEquals(2, fPosition.getD1(), "The Y value is incorrect"),
                () -> assertEquals(3, fPosition.getD2(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FPos3DI fPosition = factory.getFPos3DI("{\"pos3DI\":[1,2,3]}");

        Assertions.assertAll("Check values",
                () -> assertEquals(1, fPosition.getD0(), "The X value is incorrect"),
                () -> assertEquals(2, fPosition.getD1(), "The Y value is incorrect"),
                () -> assertEquals(3, fPosition.getD2(), "The Z value is incorrect")
        );
    }
}
