package eu.scattering.core.test.engine.randomize;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.impl.FactoryDef;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FEngine")
public class FRandomTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality - Basic")
    class FEngineBasicTest {

        @Test
        @DisplayName("Get seed - Enabled")
        void getSeedEnabled() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.of(seed), fRandom.getSeed()));
        }

        @Test
        @DisplayName("Get seed - Disabled")
        void getSeedDisabled() {
            FRandGenerator fRandom = FactoryDef.create().getFRand();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.empty(), fRandom.getSeed()));
        }

        @Test
        @DisplayName("Validate randomization - Seed enabled")
        void validateRandomizationSeedEnabled() {
            long seed = 12345;

            FRandGenerator fRandom1 = FactoryDef.create(seed).getFRand();

            double val1A = fRandom1.nextDouble();
            double val1B = fRandom1.nextDouble();
            double val1C = fRandom1.nextDouble();
            double val1D = fRandom1.nextDouble();
            double val1E = fRandom1.nextDouble();

            FRandGenerator fRandom2 = FactoryDef.create(seed).getFRand();

            double val2A = fRandom2.nextDouble();
            double val2B = fRandom2.nextDouble();
            double val2C = fRandom2.nextDouble();
            double val2D = fRandom2.nextDouble();
            double val2E = fRandom2.nextDouble();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(val1A, val2A, "Values should be equal"),
                    () -> assertEquals(val1B, val2B, "Values should be equal"),
                    () -> assertEquals(val1C, val2C, "Values should be equal"),
                    () -> assertEquals(val1D, val2D, "Values should be equal"),
                    () -> assertEquals(val1E, val2E, "Values should be equal"));
        }

        @Test
        @DisplayName("Validate randomization - Seed disabled")
        void validateRandomizationSeedDisabled() {
            FRandGenerator fRandom1 = FactoryDef.create().getFRand();

            double val1A = fRandom1.nextDouble();

            FRandGenerator fRandom2 = FactoryDef.create().getFRand();

            double val2A = fRandom2.nextDouble();

            Assertions.assertAll("Validate return value",
                    () -> assertNotEquals(val1A, val2A, "Values should not be equal"));
        }

        @Test
        @DisplayName("Get random with range")
        void nextDouble1DRange() {
            FRandGenerator fRandom = FactoryDef.create().getFRand();

            double min = 0;
            double max = 0.00001;

            double value = fRandom.nextDouble(min, max);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value >= 0 && value < max, "The value is not in the range"));
        }

        @Test
        @DisplayName("Get random with reversed range - Seed enabled")
        void nextDoubleWithReversedRangeSeedEnabled() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            double min = 0;
            double max = 0.00001;

            double value = fRandom.nextDouble(max, min);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value >= 0 && value < max, "The value is not in the range"));
        }

        @Test
        @DisplayName("Get random with reversed range - Seed disabled")
        void nextDoubleWithReversedRangeSeedDisabled() {
            FRandGenerator fRandom = FactoryDef.create().getFRand();

            double min = 0;
            double max = 0.00001;

            double value = fRandom.nextDouble(max, min);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value >= 0 && value < max, "The value is not in the range"));
        }

        @Test
        @DisplayName("Get random with zero range - Seed enabled")
        void nextDoubleWithZeroRangeSeedEnabled() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            double min = 0;
            double max = 0;

            assertThrows(IllegalArgumentException.class,
                    () -> fRandom.nextDouble(max, min));
        }

        @Test
        @DisplayName("Get random with zero range - Seed disabled")
        void nextDoubleWithZeroRangeSeedDisabled() {
            FRandGenerator fRandom = FactoryDef.create().getFRand();

            double min = 0;
            double max = 0;

            assertThrows(IllegalArgumentException.class,
                    () -> fRandom.nextDouble(max, min));
        }

        @Test
        @DisplayName("Get random long")
        void nextLong() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            long valA = fRandom.nextLong();
            long valB = fRandom.nextLong();

            assertNotEquals(valA, valB,
                    "Values should not be equal");
        }

        @Test
        @DisplayName("Get random long with range")
        void nextLongWithRange() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            boolean has0 = false;
            boolean has1 = false;
            for (int i = 0 ; i < 100 ; i++) {
                long value = fRandom.nextLong(0L, 2L);

                if (value == 0) {
                    has0 = true;
                }

                if (value == 1) {
                    has1 = true;
                }

                assertTrue(value == 0 || value == 1,
                        "The value is out of range");
            }

            assertTrue(has0 && has1,
                    "The range is erroneous");
        }

        @Test
        @DisplayName("Get random integer")
        void nextInteger() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            int valA = fRandom.nextInteger();
            int valB = fRandom.nextInteger();

            assertNotEquals(valA, valB,
                    "Values should not be equal");
        }

        @Test
        @DisplayName("Get random integer with range")
        void nextIntegerWithRange() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            boolean has0 = false;
            boolean has1 = false;
            for (int i = 0 ; i < 100 ; i++) {
                long value = fRandom.nextInteger(0, 2);

                if (value == 0) {
                    has0 = true;
                }

                if (value == 1) {
                    has1 = true;
                }

                assertTrue(value == 0 || value == 1,
                        "The value is out of range");
            }

            assertTrue(has0 && has1,
                    "The range is erroneous");
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FEngineAdvanceTest {

        @Test
        @DisplayName("Get random 2D with range")
        void nextDouble2DRange() {
            ScatFactory factory = FactoryDef.create();

            FRandGenerator fRandom = factory.getFRand();

            double range = 0.00001;

            FPos2D rangeMin = factory.getFPos2D(-range, -range);
            FPos2D rangeMax = factory.getFPos2D(range, range);
            FPairPos2D range2D = factory.getFPairPos2D(rangeMin, rangeMax);

            FPos2D value = fRandom.nextDouble2D(range2D);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value.getD0() >= -range && value.getD0() < range),
                    () -> assertTrue(value.getD1() >= -range && value.getD1() < range));
        }

        @Test
        @DisplayName("Get random 3D with range")
        void nextDouble3DRange() {
            ScatFactory factory = FactoryDef.create();

            FRandGenerator fRandom = factory.getFRand();

            double range = 0.00001;

            FPos3D rangeMin = factory.getFPos3D(-range, -range, -range);
            FPos3D rangeMax = factory.getFPos3D(range, range, range);
            FPairPos3D range3D = factory.getFPairPos3D(rangeMin, rangeMax);

            FPos3D value = fRandom.nextDouble3D(range3D);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value.getD0() >= -range && value.getD0() < range),
                    () -> assertTrue(value.getD1() >= -range && value.getD1() < range),
                    () -> assertTrue(value.getD2() >= -range && value.getD2() < range));
        }

        @Test
        @DisplayName("Get random 4D with range")
        void nextDouble4DRange() {
            ScatFactory factory = FactoryDef.create();

            FRandGenerator fRandom = factory.getFRand();

            double range = 0.00001;

            FPos4D rangeMin = factory.getFPos4D(-range, -range, -range, -range);
            FPos4D rangeMax = factory.getFPos4D(range, range, range, range);
            FPairPos4D range4D = factory.getFPairPos4D(rangeMin, rangeMax);

            FPos4D value = fRandom.nextDouble4D(range4D);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value.getD0() >= -range && value.getD0() < range),
                    () -> assertTrue(value.getD1() >= -range && value.getD1() < range),
                    () -> assertTrue(value.getD2() >= -range && value.getD2() < range),
                    () -> assertTrue(value.getD3() >= -range && value.getD3() < range));
        }

        @Test
        @DisplayName("Get position on sphere - Seed enabled")
        void getPositionOnSphereWithSeed() {
            ScatFactory factory = FactoryDef.create();

            long seed = 12345;
            double radius = 5;
            double jitter = 1E-8;

            FRandGenerator randomA = FactoryDef.create(seed).getFRand();
            FPos3D posA = randomA.nextDoubleOnSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandGenerator randomB = FactoryDef.create(seed).getFRand();
            FPos3D posB = randomB.nextDoubleOnSphere(radius);
            FPoint pointB = factory.getFPoint(posB);

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on sphere",
                    () -> assertTrue(Math.abs(lengthA - radius) < jitter),
                    () -> assertTrue(Math.abs(lengthB - radius) < jitter),
                    () -> assertTrue(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position on sphere - Seed disabled")
        void getPositionOnSphereWithoutSeed() {
            ScatFactory factory = FactoryDef.create();

            double radius = 5;
            double jitter = 1E-8;

            FRandGenerator randomA = factory.getFRand();
            FPos3D posA = randomA.nextDoubleOnSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandGenerator randomB = factory.getFRand();
            FPos3D posB = randomB.nextDoubleOnSphere(radius);
            FPoint pointB = factory.getFPoint(posB);

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on sphere",
                    () -> assertTrue(Math.abs(lengthA - radius) < jitter),
                    () -> assertTrue(Math.abs(lengthB - radius) < jitter),
                    () -> assertFalse(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position in sphere - Seed enabled")
        void getPositionInSphereWithSeed() {
            ScatFactory factory = FactoryDef.create();

            long seed = 12345;
            double radius = 5;

            FRandGenerator randomA = FactoryDef.create(seed).getFRand();
            FPos3D posA = randomA.nextDoubleInSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandGenerator randomB = FactoryDef.create(seed).getFRand();
            FPos3D posB = randomB.nextDoubleInSphere(radius);
            FPoint pointB = factory.getFPoint(posB);

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on sphere",
                    () -> assertTrue(lengthA < radius),
                    () -> assertTrue(lengthB < radius),
                    () -> assertTrue(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position in sphere - Seed disabled")
        void getPositionInSphereWithoutSeed() {
            ScatFactory factory = FactoryDef.create();

            double radius = 5;

            FRandGenerator randomA = factory.getFRand();
            FPos3D posA = randomA.nextDoubleInSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandGenerator randomB = factory.getFRand();
            FPos3D posB = randomB.nextDoubleInSphere(radius);
            FPoint pointB = factory.getFPoint(posB);

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on sphere",
                    () -> assertTrue(lengthA < radius),
                    () -> assertTrue(lengthB < radius),
                    () -> assertFalse(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position on circle - Seed enabled")
        void getPositionOnCircleWithSeed() {
            ScatFactory factory = FactoryDef.create();

            long seed = 12345;
            double radius = 5;
            double jitter = 1E-8;

            FRandGenerator randomA = FactoryDef.create(seed).getFRand();
            FPos2D posA = randomA.nextDoubleOnCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandGenerator randomB = FactoryDef.create(seed).getFRand();
            FPos2D posB = randomB.nextDoubleOnCircle(radius);
            FPoint pointB = factory.getFPoint(factory.getFPos3D(posB, 0));

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on sphere",
                    () -> assertTrue(Math.abs(lengthA - radius) < jitter),
                    () -> assertTrue(Math.abs(lengthB - radius) < jitter),
                    () -> assertTrue(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position on circle - Seed disabled")
        void getPositionOnCircleWithoutSeed() {
            ScatFactory factory = FactoryDef.create();

            double radius = 5;
            double jitter = 1E-8;

            FRandGenerator randomA = factory.getFRand();
            FPos2D posA = randomA.nextDoubleOnCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandGenerator randomB = factory.getFRand();
            FPos2D posB = randomB.nextDoubleOnCircle(radius);
            FPoint pointB = factory.getFPoint(factory.getFPos3D(posB, 0));

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on sphere",
                    () -> assertTrue(Math.abs(lengthA - radius) < jitter),
                    () -> assertTrue(Math.abs(lengthB - radius) < jitter),
                    () -> assertFalse(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position in circle - Seed enabled")
        void getPositionInCircleWithSeed() {
            ScatFactory factory = FactoryDef.create();

            long seed = 12345;
            double radius = 5;

            FRandGenerator randomA = FactoryDef.create(seed).getFRand();
            FPos2D posA = randomA.nextDoubleInCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandGenerator randomB = FactoryDef.create(seed).getFRand();
            FPos2D posB = randomB.nextDoubleInCircle(radius);
            FPoint pointB = factory.getFPoint(factory.getFPos3D(posB, 0));

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on circle",
                    () -> assertTrue(lengthA < radius),
                    () -> assertTrue(lengthB < radius),
                    () -> assertTrue(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Get position in circle - Seed disabled")
        void getPositionInCircleWithoutSeed() {
            ScatFactory factory = FactoryDef.create();

            double radius = 5;

            FRandGenerator randomA = factory.getFRand();
            FPos2D posA = randomA.nextDoubleInCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandGenerator randomB = factory.getFRand();
            FPos2D posB = randomB.nextDoubleInCircle(radius);
            FPoint pointB = factory.getFPoint(factory.getFPos3D(posB, 0));

            double lengthA = pointA.getMagnitude();
            double lengthB = pointB.getMagnitude();

            assertAll("Validate point on circle",
                    () -> assertTrue(lengthA < radius),
                    () -> assertTrue(lengthB < radius),
                    () -> assertFalse(pointA.isExact(pointB))
            );
        }

        @Test
        @DisplayName("Shuffle list - Seed enabled")
        void shuffleListWithSeed() {
            List<Integer> listA = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            List<Integer> listB = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

            for (int i = 0 ; i < 10 ; i++) {
                assertEquals(listA.get(i), listB.get(i),
                        "Lists should be equal");
            }

            FRandGenerator fRandomA = FactoryDef.create(123).getFRand();
            FRandGenerator fRandomB = FactoryDef.create(123).getFRand();

            fRandomA.shuffle(listA);
            fRandomB.shuffle(listB);

            boolean areExact = true;

            for (int i = 0 ; i < 10 ; i++) {
                if (!Objects.equals(listA.get(i), listB.get(i))) {
                    areExact = false;
                    break;
                }
            }

            assertTrue(areExact, "List elements should have same order");
        }

        @Test
        @DisplayName("Shuffle list - Seed disabled")
        void shuffleListWithoutSeed() {
            List<Integer> listA = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
            List<Integer> listB = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));

            for (int i = 0 ; i < 10 ; i++) {
                assertEquals(listA.get(i), listB.get(i),
                        "Lists should be equal");
            }

            FRandGenerator fRandomA = FactoryDef.create().getFRand();
            FRandGenerator fRandomB = FactoryDef.create().getFRand();

            fRandomA.shuffle(listA);
            fRandomB.shuffle(listB);

            boolean areExact = true;

            for (int i = 0 ; i < 10 ; i++) {
                if (!Objects.equals(listA.get(i), listB.get(i))) {
                    areExact = false;
                    break;
                }
            }

            assertFalse(areExact, "List elements should have different order");
        }

        @Test
        @DisplayName("Get list element")
        void getListElement() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            List<Integer> list = List.of(1, 2, 3, 4, 5);

            int valA = fRandom.getElement(list, false);
            int valB = fRandom.getElement(list, false);

            Assertions.assertAll("Validate elements",
                    () -> assertNotEquals(valA, valB,
                            "The elements should be different (with the predefined seed"),
                    () -> assertEquals(5, list.size(),
                            "The number of elements should not change"));
        }

        @Test
        @DisplayName("Get list element with removal")
        void getListElementWithRemoval() {
            long seed = 12345;

            FRandGenerator fRandom = FactoryDef.create(seed).getFRand();

            List<Integer> list = new ArrayList<>(List.of(1, 2, 3, 4, 5));

            int valA = fRandom.getElement(list, true);
            int valB = fRandom.getElement(list, true);

            fRandom.getElement(list, true);
            fRandom.getElement(list, true);
            fRandom.getElement(list, true);

            Assertions.assertAll("Validate elements",
                    () -> assertNotEquals(valA, valB,
                            "The elements should be different (with the predefined seed"),
                    () -> assertThrows(IllegalArgumentException.class, () -> fRandom.getElement(list, true),
                            "The list should be empty"),
                    () -> assertEquals(0, list.size(),
                            "The list should be empty"));
        }


    }
}
