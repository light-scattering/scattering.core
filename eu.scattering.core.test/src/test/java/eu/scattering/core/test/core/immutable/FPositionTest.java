package eu.scattering.core.test.core.immutable;

import eu.scattering.core.design.core.immutable.position.FPosition;
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
        FPosition fPosition = factory.getFPosition(1, 2, 3);

        Assertions.assertAll("Check values",
                () -> assertEquals(1, fPosition.getX(), "The X value is incorrect"),
                () -> assertEquals(2, fPosition.getY(), "The Y value is incorrect"),
                () -> assertEquals(3, fPosition.getZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FPosition fPosition = factory.getFPosition("{\"dipole\":[1,2,3]}");

        Assertions.assertAll("Check values",
                () -> assertEquals(1, fPosition.getX(), "The X value is incorrect"),
                () -> assertEquals(2, fPosition.getY(), "The Y value is incorrect"),
                () -> assertEquals(3, fPosition.getZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Get values")
    public void getValues() {
        FPosition fPosition = factory.getFPosition(1, 2, 3);

        assertThat(fPosition.get()).containsExactly(1, 2, 3);
    }

}
