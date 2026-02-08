package eu.scattering.core.test.statistics;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Timeout(5)
@DisplayName("FStatHelper")
public class FStatHelperTest {

    @Test
    @DisplayName("Get absolute error")
    void getAbsErr() {
        var helper = factory.getStatisticsHelper();

        double arg1 = 1.5;
        double arg2 = 2;

        assertAll("Validate error",
                () -> assertEquals(0.5, helper.getAbsErr(arg1, arg2)),
                () -> assertEquals(0.5, helper.getAbsErr(arg2, arg1))
        );
    }

    @Test
    @DisplayName("Validate absolute error")
    void valAbsErr() {
        var helper = factory.getStatisticsHelper();

        double arg1 = 1.5;
        double arg2 = 2;

        assertAll("Validate error",
                () -> assertTrue(helper.valAbsErr(arg1, arg2, 1)),
                () -> assertTrue(helper.valAbsErr(arg2, arg1, 1)),
                () -> assertFalse(helper.valAbsErr(arg1, arg2, 0.1)),
                () -> assertFalse(helper.valAbsErr(arg2, arg1, 0.1))
        );
    }

    @Test
    @DisplayName("Get relative error")
    void getRelErr() {
        var helper = factory.getStatisticsHelper();

        double arg1 = 2;
        double arg2 = 4;

        assertAll("Validate error",
                () -> assertEquals(1, helper.getRelErr(arg1, arg2)),
                () -> assertEquals(0.5, helper.getRelErr(arg2, arg1))
        );
    }

    @Test
    @DisplayName("Validate relative error")
    void valRelErr() {
        var helper = factory.getStatisticsHelper();

        double arg1 = 2;
        double arg2 = 4;

        assertAll("Validate error",
                () -> assertTrue(helper.valRelErr(arg1, arg2, 2)),
                () -> assertTrue(helper.valRelErr(arg2, arg1, 1)),
                () -> assertFalse(helper.valRelErr(arg1, arg2, 0.5)),
                () -> assertFalse(helper.valRelErr(arg2, arg1, 0.1))

        );
    }
}
