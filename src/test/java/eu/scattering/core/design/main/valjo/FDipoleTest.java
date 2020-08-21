package eu.scattering.core.design.main.valjo;

import eu.scattering.core.injection.EngineFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class FDipoleTest {

    @Test
    @DisplayName("Create with parameters")
    public void createWithParameters() {
        FDipole fDipole = EngineFactory.getFDipole(1, 2, 3);

        assertAll("Check values",
                () -> assertEquals(1, fDipole.getPositionX(), "The X value is incorrect"),
                () -> assertEquals(2, fDipole.getPositionY(), "The Y value is incorrect"),
                () -> assertEquals(3, fDipole.getPositionZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FDipole fDipole = EngineFactory.getFDipole("{\"dipole\":[1,2,3]}");

        assertAll("Check values",
                () -> assertEquals(1, fDipole.getPositionX(), "The X value is incorrect"),
                () -> assertEquals(2, fDipole.getPositionY(), "The Y value is incorrect"),
                () -> assertEquals(3, fDipole.getPositionZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Get values")
    public void getValues() {
        FDipole fDipole = EngineFactory.getFDipole(1, 2, 3);

        assertThat(fDipole.getPosition()).containsExactly(1, 2, 3);
    }

}
