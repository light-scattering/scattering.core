package eu.scattering.core.test.helper;

import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static eu.scattering.core.test.Config.epsilon;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointRandom")
public class FTrigHelperTest {

    @Test
    @DisplayName("Parse radian to Degree")
    void parseRadianToDegree() {
        var helper = factory.getFTrigHelper();

        assertAll("Values",
                () -> assertEquals(360, helper.convertRadToDeg(2 * Math.PI)),
                () -> assertEquals(180, helper.convertRadToDeg(Math.PI)),
                () -> assertEquals(90, helper.convertRadToDeg(Math.PI / 2))
        );
    }

    @Test
    @DisplayName("Parse degree to radian")
    void parseDegreeToRadian() {
        var helper = factory.getFTrigHelper();

        assertAll("Values",
                () -> assertEquals(2 * Math.PI, helper.convertDegToRad(360)),
                () -> assertEquals(Math.PI, helper.convertDegToRad(180)),
                () -> assertEquals(Math.PI / 2, helper.convertDegToRad(90))
        );
    }

    @Test
    @DisplayName("Get angle - A")
    void getAngleBetweenVectorsA() {
        var helper = factory.getFTrigHelper();

        var base = factory.getFPos3D(0, 0, 0);
        var headA = factory.getFPos3D(1, 0, 0);
        var headB = factory.getFPos3D(0, 1, 0);

        var angle = helper.getAngleBetweenVectors(base, headA, headB);

        assertEquals(Math.PI / 2, angle, epsilon, "The angle is invalid");
    }

    @Test
    @DisplayName("Get angle - B")
    void getAngleBetweenVectorsB() {
        var helper = factory.getFTrigHelper();

        var base = factory.getFPos3D(0, 0, 0);
        var headA = factory.getFPos3D(1, 0, 0);
        var headB = factory.getFPos3D(-1, 0, 0);

        var angle = helper.getAngleBetweenVectors(base, headA, headB);

        assertEquals(Math.PI, angle, epsilon, "The angle is invalid");
    }

    @Test
    @DisplayName("Get angle - C")
    void getAngleBetweenVectorsC() {
        var helper = factory.getFTrigHelper();

        var base = factory.getFPos3D(0, 0, 0);
        var headA = factory.getFPos3D(1, 0, 0);
        var headB = factory.getFPos3D(1, 1, 0);

        var angle = helper.getAngleBetweenVectors(base, headA, headB);

        assertEquals(Math.PI / 4, angle, epsilon, "The angle is invalid");
    }

    @Test
    @DisplayName("Get angle - D")
    void getAngleBetweenVectorsD() {
        var helper = factory.getFTrigHelper();

        var base = factory.getFPos3D(0, 0, 0);
        var headA = factory.getFPos3D(1, 0, 1);
        var headB = factory.getFPos3D(1, 1, 1);

        var angle = helper.getAngleBetweenVectors(base, headA, headB);

        assertEquals(Math.asin(1 / Math.sqrt(3)), angle, epsilon, "The angle is invalid");
    }

    @Test
    @DisplayName("Get angle - offset")
    void getAngleBetweenVectorsOffset() {
        var helper = factory.getFTrigHelper();

        var base = factory.getFPos3D(1, 2, 3);
        var headA = factory.getFPos3D(2, 2, 4);
        var headB = factory.getFPos3D(2, 3, 4);

        var angle = helper.getAngleBetweenVectors(base, headA, headB);

        assertEquals(Math.asin(1 / Math.sqrt(3)), angle, epsilon, "The angle is invalid");
    }

    @Test
    @DisplayName("Get angle SSS - A")
    void getAngleSSSA() {
        FTrigHelper helper = factory.getFTrigHelper();

        double angle = helper.getAngle(3, 4, 5);

        assertEquals(Math.PI * 0.5, angle, epsilon, "The angle is incorrect");
    }

    @Test
    @DisplayName("Get angle SSS - B")
    void getAngleSSSB() {
        FTrigHelper helper = factory.getFTrigHelper();

        double resA = helper.getAngle(2, 2, 2 * Math.sqrt(2));
        double resB = helper.getAngle(2 * Math.sqrt(2), 2, 2);
        double resC = helper.getAngle(2, 2 * Math.sqrt(2), 2);

        Assertions.assertAll("Validate results",
                () -> assertEquals(Math.PI * 0.50, resA, epsilon,
                        "Result A is incorrect"),
                () -> assertEquals(Math.PI * 0.25, resB, epsilon,
                        "Result B is incorrect"),
                () -> assertEquals(Math.PI * 0.25, resC, epsilon,
                        "Result C is incorrect")
        );
    }

    @Test
    @DisplayName("Is valid")
    void isValidA() {
        FTrigHelper helper = factory.getFTrigHelper();

        Assertions.assertAll("Validate triangle",
                () -> assertTrue(helper.isValid(3, 4, 5),
                        "The triangle should be valid"),
                () -> assertTrue(helper.isValid(3, 5, 4),
                        "The triangle should be valid"),
                () -> assertTrue(helper.isValid(4, 5, 3),
                        "The triangle should be valid"),
                () -> assertFalse(helper.isValid(1, 2, 5),
                        "The triangle should not be valid"),
                () -> assertFalse(helper.isValid(1, 5, 2),
                        "The triangle should not be valid"),
                () -> assertFalse(helper.isValid(2, 5, 1),
                        "The triangle should not be valid")
        );
    }
}
