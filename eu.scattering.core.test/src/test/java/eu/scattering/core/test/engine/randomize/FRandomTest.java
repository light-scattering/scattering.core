package eu.scattering.core.test.engine.randomize;

import eu.scattering.core.design.FactoryDesign;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
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
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FEngineAdvanceTest {

        @Test
        @DisplayName("Get random 2D with range")
        void nextDouble2DRange() {
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
            FactoryDesign factory = FactoryDef.create();

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
    }
}
