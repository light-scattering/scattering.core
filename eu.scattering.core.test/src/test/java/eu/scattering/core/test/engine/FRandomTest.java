package eu.scattering.core.test.engine;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.engine.randomize.processor.FRandProcessor;
import eu.scattering.core.transfer.container.position.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.position.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static eu.scattering.core.test.Config.*;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("FEngine")
public class FRandomTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality - Basic")
    class FEngineBasic {

        @Test
        @DisplayName("Get retry limit - Default")
        void getRetryLimitDefault() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.empty(), fRandom.getRetryLimit()));
        }

        @Test
        @DisplayName("Set retry limit")
        void setRetryLimit() {
            int value = 5;

            FRandProcessor fRandom = factory.getFRandProcessor();

            fRandom.setRetryLimit(value);

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.of(value), fRandom.getRetryLimit()));
        }

        @Test
        @DisplayName("Set retry limit - Throw exception")
        void setRetryLimitThrowException() {
            int value = -1;

            FRandProcessor fRandom = factory.getFRandProcessor();

            assertThrows(IllegalArgumentException.class,
                    () -> fRandom.setRetryLimit(value));
        }

        @Test
        @DisplayName("Clear retry limit")
        void clearRetryLimit() {
            int value = 5;

            FRandProcessor fRandom = factory.getFRandProcessor();

            fRandom.setRetryLimit(value);
            fRandom.clearRetryLimit();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.empty(), fRandom.getRetryLimit()));
        }

        @Test
        @DisplayName("Get separation distance - Default")
        void getSeparationDistanceDefault() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.empty(), fRandom.getProximityLimit()));
        }

        @Test
        @DisplayName("Set separation distance")
        void setSeparationDistance() {
            double value = 1.2345;

            FRandProcessor fRandom = factory.getFRandProcessor();

            fRandom.setProximityLimit(value);

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.of(value), fRandom.getProximityLimit()));
        }

        @Test
        @DisplayName("Set separation distance - Throw exception")
        void setSeparationDistanceThrowException() {
            double value = -1.2345;

            FRandProcessor fRandom = factory.getFRandProcessor();

            assertThrows(IllegalArgumentException.class,
                    () -> fRandom.setProximityLimit(value));
        }

        @Test
        @DisplayName("Clear separation distance")
        void clearSeparationDistance() {
            double value = 1.2345;

            FRandProcessor fRandom = factory.getFRandProcessor();

            fRandom.setProximityLimit(value);
            fRandom.clearProximityLimit();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.empty(), fRandom.getProximityLimit()));
        }

        @Test
        @DisplayName("Get seed - Enabled")
        void getSeedEnabled() {
            long seed = 12345;

            FRandProcessor fRandom = factory.getFRandProcessor(seed);

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.of(seed), fRandom.getSeed()));
        }

        @Test
        @DisplayName("Get seed - Disabled")
        void getSeedDisabled() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            Assertions.assertAll("Validate return value",
                    () -> assertEquals(Optional.empty(), fRandom.getSeed()));
        }

        @Test
        @DisplayName("Validate randomization - Seed enabled")
        void validateRandomizationSeedEnabled() {
            long seed = 12345;

            FRandProcessor fRandom1 = factory.getFRandProcessor(seed);

            double val1A = fRandom1.nextDouble();
            double val1B = fRandom1.nextDouble();
            double val1C = fRandom1.nextDouble();
            double val1D = fRandom1.nextDouble();
            double val1E = fRandom1.nextDouble();

            FRandProcessor fRandom2 = factory.getFRandProcessor(seed);

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
            FRandProcessor fRandom1 = factory.getFRandProcessor();

            double val1A = fRandom1.nextDouble();

            FRandProcessor fRandom2 = factory.getFRandProcessor();

            double val2A = fRandom2.nextDouble();

            Assertions.assertAll("Validate return value",
                    () -> assertNotEquals(val1A, val2A, "Values should not be equal"));
        }

        @Test
        @DisplayName("Get random with range")
        void nextDouble1DRange() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double min = 0;
            double max = 0.00001;

            double value = fRandom.nextDouble(min, max);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value >= 0 && value < max, "The value is not in the range"));
        }

        @Test
        @DisplayName("Get random with range and exclusion")
        void nextDouble1DRangeWithExclusion() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 1;

            double min = 0;
            double max = 1.5;

            fRandom.setProximityLimit(separationDistance);

            double value = fRandom.nextDouble(min, max, 0);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value >= 1 && value < max, "The value is not in the range"));
        }

        @Test
        @DisplayName("Get random with range and exclusion - Retry limit")
        void validateRetryLimit() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 1;
            int retryLimit = 100;

            double min = 0;
            double max = 0.5;

            fRandom.setProximityLimit(separationDistance);
            fRandom.setRetryLimit(retryLimit);

            assertThrows(ArithmeticException.class,
                    ()-> fRandom.nextDouble(min, max, 0));
        }

        @Test
        @DisplayName("Get random with reversed range - Seed enabled")
        void nextDoubleWithReversedRangeSeedEnabled() {
            long seed = 12345;

            FRandProcessor fRandom = factory.getFRandProcessor(seed);

            double min = 0;
            double max = 0.00001;

            double value = fRandom.nextDouble(max, min);

            Assertions.assertAll("Validate random value",
                    () -> assertTrue(value >= 0 && value < max, "The value is not in the range"));
        }

        @Test
        @DisplayName("Get random with reversed range - Seed disabled")
        void nextDoubleWithReversedRangeSeedDisabled() {
            FRandProcessor fRandom = factory.getFRandProcessor();

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

            FRandProcessor fRandom = factory.getFRandProcessor(seed);

            double min = 0;
            double max = 0;

            assertThrows(IllegalArgumentException.class,
                    () -> fRandom.nextDouble(max, min));
        }

        @Test
        @DisplayName("Get random with zero range - Seed disabled")
        void nextDoubleWithZeroRangeSeedDisabled() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double min = 0;
            double max = 0;

            assertThrows(IllegalArgumentException.class,
                    () -> fRandom.nextDouble(max, min));
        }
    }

    @Nested
    @Tag("Utilities")
    @DisplayName("Functionality - Utilities")
    class FEngineUtils {

        @Test
        @DisplayName("Validate 1D")
        void validate1D() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 0.1;

            double exc1 = -1;
            double exc2 = 1;

            fRandom.setProximityLimit(separationDistance);

            Assertions.assertAll("Validate 1D point",
                    () -> assertTrue(fRandom.valExc1D(0.85, exc1, exc2)),
                    () -> assertTrue(fRandom.valExc1D(1.15, exc1, exc2)),
                    () -> assertTrue(fRandom.valExc1D(-0.85, exc1, exc2)),
                    () -> assertTrue(fRandom.valExc1D(-1.15, exc1, exc2)),
                    () -> assertFalse(fRandom.valExc1D(0.95, exc1, exc2)),
                    () -> assertFalse(fRandom.valExc1D(1.05, exc1, exc2)),
                    () -> assertFalse(fRandom.valExc1D(-0.95, exc1, exc2)),
                    () -> assertFalse(fRandom.valExc1D(-1.05, exc1, exc2))
            );
        }

        @Test
        @DisplayName("Validate 2D")
        void validate2D() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 0.1;

            FPos2D exc1 = factory.getFPos2D(-1, 0);
            FPos2D exc2 = factory.getFPos2D(1, 0);
            FPos2D exc3 = factory.getFPos2D(0, -1);
            FPos2D exc4 = factory.getFPos2D(0, 1);

            fRandom.setProximityLimit(separationDistance);

            Assertions.assertAll("Validate 2D point",
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(0.85, 0), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(1.15, 0), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(-0.85, 0), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(-1.15, 0), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(0, 0.85), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(0, 1.15), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(0, -0.85), exc1, exc2, exc3, exc4)),
                    () -> assertTrue(fRandom.valExc2D(factory.getFPos2D(0, -1.15), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(0.95, 0), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(1.05, 0), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(-0.95, 0), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(-1.05, 0), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(0, 0.95), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(0, 1.05), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(0, -0.95), exc1, exc2, exc3, exc4)),
                    () -> assertFalse(fRandom.valExc2D(factory.getFPos2D(0, -1.05), exc1, exc2, exc3, exc4)));
        }

        @Test
        @DisplayName("Validate 3D")
        void validate3D() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 0.1;

            FPos3D exc1 = factory.getFPos3D(-1, 0, 0);
            FPos3D exc2 = factory.getFPos3D(1, 0, 0);
            FPos3D exc3 = factory.getFPos3D(0, -1, 0);
            FPos3D exc4 = factory.getFPos3D(0, 1, 0);
            FPos3D exc5 = factory.getFPos3D(0, 0, 1);
            FPos3D exc6 = factory.getFPos3D(0, 0, -1);

            fRandom.setProximityLimit(separationDistance);

            Assertions.assertAll("Validate 2D point",
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0.85, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(1.15, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(-0.85, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(-1.15, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, 0.85, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, 1.15, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, -0.85, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, -1.15, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, 0, 0.85), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, 0, 1.15), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, 0, -0.85), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertTrue(fRandom.valExc3D(factory.getFPos3D(0, 0, -1.15), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0.95, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(1.05, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(-0.95, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(-1.05, 0, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, 0.95, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, 1.05, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, -0.95, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, -1.05, 0), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, 0, 0.95), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, 0, 1.05), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, 0, -0.95), exc1, exc2, exc3, exc4, exc5, exc6)),
                    () -> assertFalse(fRandom.valExc3D(factory.getFPos3D(0, 0, -1.05), exc1, exc2, exc3, exc4, exc5, exc6)));
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FEngineAdvanced {

        @Test
        @DisplayName("Get random 2D with range")
        void nextDouble2DRange() {
            FRandProcessor fRandom = factory.getFRandProcessor();

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
        @DisplayName("Get random 2D with range and exclusion")
        void nextDouble2DRangeWithExclusion() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 1;
            double range = 1.5;

            FPos2D rangeMin = factory.getFPos2D(-range, -range);
            FPos2D rangeMax = factory.getFPos2D(range, range);
            FPairPos2D range2D = factory.getFPairPos2D(rangeMin, rangeMax);

            fRandom.setProximityLimit(separationDistance);

            FPos2D value = fRandom.nextDouble2D(range2D, factory.getFPos2D(0, 0));

            double distX = value.getD0() * value.getD0();
            double distY = value.getD1() * value.getD1();
            double dist = Math.sqrt(distX + distY);

            Assertions.assertAll("Validate random value",
                    () -> assertFalse(dist < separationDistance));
        }

        @Test
        @DisplayName("Get random 3D with range")
        void nextDouble3DRange() {
            FRandProcessor fRandom = factory.getFRandProcessor();

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
        @DisplayName("Get random 3D with range and exclusion")
        void nextDouble3DRangeWithExclusion() {
            FRandProcessor fRandom = factory.getFRandProcessor();

            double separationDistance = 1;
            double range = 1.5;

            FPos3D rangeMin = factory.getFPos3D(-range, -range, -range);
            FPos3D rangeMax = factory.getFPos3D(range, range, range);
            FPairPos3D range3D = factory.getFPairPos3D(rangeMin, rangeMax);

            fRandom.setProximityLimit(separationDistance);

            FPos3D value = fRandom.nextDouble3D(range3D, factory.getFPos3D(0, 0, 0));

            double distX = value.getD0() * value.getD0();
            double distY = value.getD1() * value.getD1();
            double distZ = value.getD2() * value.getD2();
            double dist = Math.sqrt(distX + distY + distZ);

            Assertions.assertAll("Validate random value",
                    () -> assertFalse(dist < separationDistance));
        }

        @Test
        @DisplayName("Get position on sphere - Seed enabled")
        void getPositionOnSphereWithSeed() {
            long seed = 12345;
            double radius = 5;
            double jitter = 1E-8;

            FRandProcessor randomA = factory.getFRandProcessor(seed);
            FPos3D posA = randomA.nextDoubleOnSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandProcessor randomB = factory.getFRandProcessor(seed);
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
            double radius = 5;
            double jitter = 1E-8;

            FRandProcessor randomA = factory.getFRandProcessor();
            FPos3D posA = randomA.nextDoubleOnSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandProcessor randomB = factory.getFRandProcessor();
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
            long seed = 12345;
            double radius = 5;

            FRandProcessor randomA = factory.getFRandProcessor(seed);
            FPos3D posA = randomA.nextDoubleInSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandProcessor randomB = factory.getFRandProcessor(seed);
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
            double radius = 5;

            FRandProcessor randomA = factory.getFRandProcessor();
            FPos3D posA = randomA.nextDoubleInSphere(radius);
            FPoint pointA = factory.getFPoint(posA);

            FRandProcessor randomB = factory.getFRandProcessor();
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
            long seed = 12345;
            double radius = 5;
            double jitter = 1E-8;

            FRandProcessor randomA = factory.getFRandProcessor(seed);
            FPos2D posA = randomA.nextDoubleOnCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandProcessor randomB = factory.getFRandProcessor(seed);
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
            double radius = 5;
            double jitter = 1E-8;

            FRandProcessor randomA = factory.getFRandProcessor();
            FPos2D posA = randomA.nextDoubleOnCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandProcessor randomB = factory.getFRandProcessor();
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
            long seed = 12345;
            double radius = 5;

            FRandProcessor randomA = factory.getFRandProcessor(seed);
            FPos2D posA = randomA.nextDoubleInCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandProcessor randomB = factory.getFRandProcessor(seed);
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
            double radius = 5;

            FRandProcessor randomA = factory.getFRandProcessor();
            FPos2D posA = randomA.nextDoubleInCircle(radius);
            FPoint pointA = factory.getFPoint(factory.getFPos3D(posA, 0));

            FRandProcessor randomB = factory.getFRandProcessor();
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
