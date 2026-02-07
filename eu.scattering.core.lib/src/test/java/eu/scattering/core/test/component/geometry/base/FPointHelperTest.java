package eu.scattering.core.test.component.geometry.base;

import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FPointHelper")
public class FPointHelperTest {

    @Test
    @DisplayName("Exactness")
    void isExact() {
        var helper = factory.getFPointHelper();

        assertTrue(helper.isExact(1, 2, 3, 1, 2, 3),
                "Values should be equal");
    }

    @Test
    @DisplayName("Exactness (fail)")
    void isExactFail() {
        var helper = factory.getFPointHelper();

        Assertions.assertAll("Validate exactness",
                () -> assertFalse(helper.isExact(1, 2, 3, 1 + 2 * epsilon, 2, 3),
                "Values should not be equal"),
                () -> assertFalse(helper.isExact(1, 2, 3, 1, 2 + 2 * epsilon, 3),
                        "Values should not be equal"),
                () -> assertFalse(helper.isExact(1, 2, 3, 1, 2, 3 + 2 * epsilon),
                        "Values should not be equal")
        );
    }

    @Test
    @DisplayName("Similarity")
    void isSimilar() {
        var helper = factory.getFPointHelper();

        Assertions.assertAll("Validate exactness",
                () -> assertTrue(helper.isSimilar(1, 2, 3, 1 + 0.5 * epsilon, 2, 3),
                        "Values should be similar"),
                () -> assertTrue(helper.isSimilar(1, 2, 3, 1, 2 + 0.5 * epsilon, 3),
                        "Values should be similar"),
                () -> assertTrue(helper.isSimilar(1, 2, 3, 1, 2, 3 + 0.5 * epsilon),
                        "Values should be similar")
        );
    }

    @Test
    @DisplayName("Similarity (fail)")
    void isSimilarFail() {
        var helper = factory.getFPointHelper();

        Assertions.assertAll("Validate exactness",
                () -> assertFalse(helper.isSimilar(1, 2, 3, 1 + 2 * epsilon, 2, 3),
                        "Values should not be similar"),
                () -> assertFalse(helper.isSimilar(1, 2, 3, 1, 2 + 2 * epsilon, 3),
                        "Values should not be similar"),
                () -> assertFalse(helper.isSimilar(1, 2, 3, 1, 2, 3 + 2 * epsilon),
                        "Values should not be similar")
        );
    }

    @Test
    @DisplayName("Get magnitude")
    void getMagnitude() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();

        double magnitude = Math.sqrt((x * x) + (y * y) + (z * z));

        assertEquals(magnitude, helper.getMagnitude(x, y, z),
                epsilon, "The magnitude is erroneous");
    }

    @Test
    @DisplayName("Get magnitude P2")
    void getMagnitudeP2() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();

        double magnitudeP2 = (x * x) + (y * y) + (z * z);

        assertEquals(magnitudeP2, helper.getMagnitudeP2(x, y, z),
                epsilon, "The squared magnitude is erroneous");
    }

    @Test
    @DisplayName("Get magnitude with FPos3D")
    void getMagnitudeWithFPos3D() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();

        double magnitude = Math.sqrt((x * x) + (y * y) + (z * z));

        assertEquals(magnitude, helper.getMagnitude(factory.getFPos3D(x, y, z)),
                epsilon, "The magnitude is erroneous");
    }

    @Test
    @DisplayName("Set magnitude")
    void setMagnitude() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();

        FPos3D results = helper.setMagnitude(x, y, z, 1);

        assertEquals(1, helper.getMagnitude(results.getD0(), results.getD1(), results.getD2()),
                epsilon, "The magnitude is erroneous");
    }

    @Test
    @DisplayName("Set magnitude with FPos3D")
    void setMagnitudeWithFPos3D() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();

        FPos3D results = helper.setMagnitude(factory.getFPos3D(x, y, z), 1);

        assertEquals(1, helper.getMagnitude(results.getD0(), results.getD1(), results.getD2()),
                epsilon, "The magnitude is erroneous");
    }

    @Test
    @DisplayName("Get distance")
    void getDistance() {
        var helper = factory.getFPointHelper();

        double aX = rand.nextDouble();
        double aY = rand.nextDouble();
        double aZ = rand.nextDouble();
        double bX = rand.nextDouble();
        double bY = rand.nextDouble();
        double bZ = rand.nextDouble();

        double dimX = aX - bX;
        double dimY = aY - bY;
        double dimZ = aZ - bZ;

        double distance = Math.sqrt((dimX * dimX) + (dimY * dimY) + (dimZ * dimZ));


        assertEquals(distance, helper.getDistance(aX, aY, aZ, bX, bY, bZ),
                epsilon, "The distance is erroneous");
    }

    @Test
    @DisplayName("Get distance P2")
    void getDistanceP2() {
        var helper = factory.getFPointHelper();

        double aX = rand.nextDouble();
        double aY = rand.nextDouble();
        double aZ = rand.nextDouble();
        double bX = rand.nextDouble();
        double bY = rand.nextDouble();
        double bZ = rand.nextDouble();

        double dimX = aX - bX;
        double dimY = aY - bY;
        double dimZ = aZ - bZ;

        double distanceP2 = (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);


        assertEquals(distanceP2, helper.getDistanceP2(aX, aY, aZ, bX, bY, bZ),
                epsilon, "The squared distance is erroneous");
    }

    @Test
    @DisplayName("Set distance")
    void setDistance() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();
        double refX = rand.nextDouble();
        double refY = rand.nextDouble();
        double refZ = rand.nextDouble();

        FPos3D results = helper.setDistance(x, y, z, refX, refY, refZ, 1);

        assertEquals(1, helper.getDistance(x, y, z, results.getD0(), results.getD1(), results.getD2()),
                epsilon, "The distance is erroneous");
    }

    @Test
    @DisplayName("Set distance with FPos3D")
    void setDistanceWithFPos3D() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();
        double refX = rand.nextDouble();
        double refY = rand.nextDouble();
        double refZ = rand.nextDouble();

        FPos3D results = helper.setDistance(x, y, z, factory.getFPos3D(refX, refY, refZ), 1);

        assertEquals(1, helper.getDistance(x, y, z, results.getD0(), results.getD1(), results.getD2()),
                epsilon, "The distance is erroneous");
    }

    @Test
    @DisplayName("Set distance with center")
    void setDistanceWithCenter() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();
        double refX = rand.nextDouble();
        double refY = rand.nextDouble();
        double refZ = rand.nextDouble();

        FPos3D results = helper.setDistance(factory.getFPos3D(x, y, z), refX, refY, refZ, 1);

        assertEquals(1, helper.getDistance(x, y, z, results.getD0(), results.getD1(), results.getD2()),
                epsilon, "The distance is erroneous");
    }

    @Test
    @DisplayName("Set distance with center and FPos3D")
    void setDistanceWithCenterAndFPos3D() {
        var helper = factory.getFPointHelper();

        double x = rand.nextDouble();
        double y = rand.nextDouble();
        double z = rand.nextDouble();
        double refX = rand.nextDouble();
        double refY = rand.nextDouble();
        double refZ = rand.nextDouble();

        FPos3D results = helper.setDistance(factory.getFPos3D(x, y, z), factory.getFPos3D(refX, refY, refZ), 1);

        assertEquals(1, helper.getDistance(x, y, z, results.getD0(), results.getD1(), results.getD2()),
                epsilon, "The distance is erroneous");
    }
}
