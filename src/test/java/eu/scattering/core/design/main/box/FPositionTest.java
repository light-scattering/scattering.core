package eu.scattering.core.design.main.box;

import eu.scattering.core.SpringConfigCore;
import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.box.position.FPosition;
import eu.scattering.core.design.support.helper.RandomHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(5)
@DisplayName("FPosition")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SpringConfigCore.class })
public class FPositionTest {

    @Value("${jitter}")
    private double jitter;

    @Autowired
    private Factory factory;

    private RandomHelper random;

    @BeforeEach
    void beforeEach() {

        random = factory.getRandomHelper();
    }

    @Test
    @DisplayName("Create with parameters")
    public void createWithParameters() {
        FPosition fPosition = factory.getFPosition(1, 2, 3);

        assertAll("Check values",
                () -> assertEquals(1, fPosition.getX(), "The X value is incorrect"),
                () -> assertEquals(2, fPosition.getY(), "The Y value is incorrect"),
                () -> assertEquals(3, fPosition.getZ(), "The Z value is incorrect")
        );
    }

    @Test
    @DisplayName("Create with String")
    public void createWithString() {
        FPosition fPosition = factory.getFPosition("{\"dipole\":[1,2,3]}");

        assertAll("Check values",
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
