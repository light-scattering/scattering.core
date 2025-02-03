package eu.scattering.core.test.helper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.factory;
import static eu.scattering.core.test.Config.epsilon;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
