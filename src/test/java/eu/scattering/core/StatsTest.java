package eu.scattering.core;

import eu.scattering.core.debug.stats.IStats;
import eu.scattering.core.debug.stats.IStatsMethod;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class StatsTest {

    @BeforeEach
    void beforeEach() {

        Configuration.setDevEnabled(true);
        Configuration.setDevObjectStatsSuspended(true);

        FactoryGeometry.getIFPoint().devGetClassStats().ifPresent(e -> e.reset());
    }

    @Nested
    @DisplayName("Class")
    class DevBase {

        @Test
        @DisplayName("Get IStats for classes")
        void getIStatsForClasses() {

            assertTrue(FactoryGeometry.getIFPoint().devGetClassStats().isPresent(),
                    "The IStat object should be available");
        }

        @Test
        @DisplayName("Get IStats for classes (disabled)")
        void getIStatsForClassesDisabled() {
            Configuration.setDevEnabled(false);

            assertTrue(FactoryGeometry.getIFPoint().devGetClassStats().isEmpty(),
                    "The IStat object should not be available");
        }

        @Test
        @DisplayName("Validate number of initially registered events for classes")
        void validateNumberOfInitiallyRegisteredEventsForClasses() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(0);
        }

        @Test
        @DisplayName("Register single class event (dynamic)")
        void registerSingleClassEventDynamic() {
            IFPoint fPoint = FactoryGeometry.getIFPoint()
                    .set(1, 1, 1);

            Optional<IStats> statsOpt = fPoint.devGetClassStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("set(double, double, double)");
        }

        @Test
        @DisplayName("Register single class event")
        void registerSingleClassEvent() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("test event 1");
        }

        @Test
        @DisplayName("Register single class event (throw NullPointerException)")
        void registerSingleClassEventThrowNullPointerException() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            assertThrows(ArithmeticException.class,
                    () -> stats.recordEvent("test event 1", -10L),
                    "The event time must be a positive value");
        }

        @Test
        @DisplayName("Register single class event (throw ArithmeticException)")
        void registerSingleClassEventThrowArithmeticException() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            assertThrows(NullPointerException.class, () -> stats.recordEvent(null, 10L),
                    "The method name must be specified");
        }

        @Test
        @DisplayName("Register multiple unique events")
        void registerMultipleUniqueEvents() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 2", 10L);
            stats.recordEvent("test event 3", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(3)
                    .containsExactlyInAnyOrder("test event 1", "test event 2", "test event 3");
        }

        @Test
        @DisplayName("Register multiple mixed events")
        void registerMultipleMixedEvents() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 2", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(2)
                    .containsExactlyInAnyOrder("test event 1", "test event 2");
        }

        @Test
        @DisplayName("Reset events")
        void resetEvents() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 2", 10L);
            stats.recordEvent("test event 3", 10L);

            stats.reset();

            stats.recordEvent("test event 4", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("test event 4");
        }

        @Test
        @DisplayName("Validate suspended status")
        void validateSuspendedStatus() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            assertFalse(stats.isSuspended(),
                    "The registration of events is not consistent with the default value");
        }

        @Test
        @DisplayName("Validate suspended status (true)")
        void validateSuspendedStatusTrue() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.setSuspended(true);

            assertTrue(stats.isSuspended(),
                    "The registration of events should be suspended");
        }

        @Test
        @DisplayName("Validate suspended status (false)")
        void validateSuspendedStatusFalse() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.setSuspended(false);

            assertFalse(stats.isSuspended(),
                    "The registration of events should not be suspended");
        }

        @Test
        @DisplayName("Register event in the suspended state")
        void recordEventSuspended() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.setSuspended(true);
            stats.recordEvent("test event 1", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(0);
        }

    }

    @Nested
    @DisplayName("Object (additional)")
    class DevObject {

        @Test
        @DisplayName("Get IStats for objects")
        void getIStatsForObjects() {

            assertTrue(FactoryGeometry.getIFPoint().devGetStats().isPresent(),
                    "The IStat object should be available");
        }

        @Test
        @DisplayName("Get IStats for object (disabled)")
        void getIStatsForObjectsDisabled() {
            Configuration.setDevEnabled(false);

            assertTrue(FactoryGeometry.getIFPoint().devGetStats().isEmpty(),
                    "The IStat object should not be available");
        }

        @Test
        @DisplayName("Validate number of initially registered events for objects")
        void validateNumberOfInitiallyRegisteredEventsForObjects() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(0);
        }

        @Test
        @DisplayName("Register single object event (dynamic / enabled)")
        void registerSingleObjectEventDynamicEnabled() {
            Configuration.setDevObjectStatsSuspended(false);

            IFPoint fPoint = FactoryGeometry.getIFPoint()
                    .set(1, 1, 1);

            Optional<IStats> statsOpt = fPoint.devGetStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("set(double, double, double)");
        }

        @Test
        @DisplayName("Register single object event (dynamic / disabled)")
        void registerSingleObjectEventDynamicDisabled() {
            IFPoint fPoint = FactoryGeometry.getIFPoint()
                    .set(1, 1, 1);

            Optional<IStats> statsOpt = fPoint.devGetStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(0);
        }

        @Test
        @DisplayName("Register single object event (enabled)")
        void registerSingleObjectEventEnabled() {
            Configuration.setDevObjectStatsSuspended(false);

            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("test event 1");
        }

        @Test
        @DisplayName("Register single object event (disabled)")
        void registerSingleObjectEventDisabled() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(0);
        }

    }

    @Nested
    @DisplayName("Method")
    class DevMethod {

        @Test
        @DisplayName("Get method")
        void getMethod() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");

            assertTrue(statsMethodOpt.isPresent(), "The requested method should be available");
        }

        @Test
        @DisplayName("Get method (throw NullPointerException)")
        void getMethodThrowNullPointerException() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            assertThrows(NullPointerException.class, () -> stats.getMethod(null),
                    "The method name must be defined");
        }

        @Test
        @DisplayName("Get non-existent method")
        void getNonExistentMethod() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 2");

            assertTrue(statsMethodOpt.isEmpty(), "The requested method should not be available");
        }

        @Test
        @DisplayName("Get execution times")
        void getExecutionTimes() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 20L);
            stats.recordEvent("test event 1", 30L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertThat(statsMethod.getExecutionTimes())
                    .hasSize(3)
                    .containsExactlyInAnyOrder(10L, 20L, 30L);
        }

        @Test
        @DisplayName("Register time (validate list)")
        void registerTimeValidateList() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            statsMethod.recordExecutionTime(20L);
            statsMethod.recordExecutionTime(30L);

            assertThat(statsMethod.getExecutionTimes())
                    .hasSize(3)
                    .containsExactlyInAnyOrder(10L, 20L, 30L);
        }

        @Test
        @DisplayName("Register time (validate iterations)")
        void registerTimeValidateIterations() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            statsMethod.recordExecutionTime(20L);
            statsMethod.recordExecutionTime(30L);

            assertEquals(3, statsMethod.getNumberOfIterations(),
                    "The number of iterations is incorrect");
        }

        @Test
        @DisplayName("Register time (throw ArithmeticException)")
        void registerTimeThrowArithmeticException() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertThrows(ArithmeticException.class, () -> statsMethod.recordExecutionTime(-10L),
                    "The event time must be a positive value");
        }

        @Test
        @DisplayName("Get number of iterations")
        void getNumberOfIterations() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 20L);
            stats.recordEvent("test event 1", 30L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertEquals(3, statsMethod.getNumberOfIterations(),
                    "The number of iterations is incorrect");
        }

        @Test
        @DisplayName("Get time total")
        void getTimeTotal() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 20L);
            stats.recordEvent("test event 1", 30L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertEquals(60L, statsMethod.getTimeTotal(),
                    "The total time is incorrect");
        }

        @Test
        @DisplayName("Get time avg")
        void getTimeAvg() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 20L);
            stats.recordEvent("test event 1", 30L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertEquals(20L, statsMethod.getTimeAvg(),
                    "The averaged time is incorrect");
        }

        @Test
        @DisplayName("Get time min")
        void getTimeMin() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 20L);
            stats.recordEvent("test event 1", 30L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertEquals(10L, statsMethod.getTimeMin(),
                    "The min time is incorrect");
        }

        @Test
        @DisplayName("Get time max")
        void getTimeMax() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 20L);
            stats.recordEvent("test event 1", 30L);

            Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
            IStatsMethod statsMethod = statsMethodOpt.get();

            assertEquals(30L, statsMethod.getTimeMax(),
                    "The max time is incorrect");
        }

    }

}
