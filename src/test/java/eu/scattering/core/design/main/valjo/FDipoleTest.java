package eu.scattering.core.design.main.valjo;

import eu.scattering.core.injection.MainFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FDipole")
public class FDipoleTest {

    @Test
    @DisplayName("Create with parameters")
    public void createWithParameters() {
        FDipole fDipole = MainFactory.getFDipole(1, 2, 3);

        assertAll("Check values",
                () -> assertEquals(1, fDipole.getPositionX(), "The X value is incorrect"),
                () -> assertEquals(2, fDipole.getPositionY(), "The Y value is incorrect"),
                () -> assertEquals(3, fDipole.getPositionZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FDipole fDipole = MainFactory.getFDipole("{\"dipole\":[1,2,3]}");

        assertAll("Check values",
                () -> assertEquals(1, fDipole.getPositionX(), "The X value is incorrect"),
                () -> assertEquals(2, fDipole.getPositionY(), "The Y value is incorrect"),
                () -> assertEquals(3, fDipole.getPositionZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Get values")
    public void getValues() {
        FDipole fDipole = MainFactory.getFDipole(1, 2, 3);

        assertThat(fDipole.getPosition()).containsExactly(1, 2, 3);
    }

}
