package eu.scattering.core;

import eu.scattering.core.debug.IStats;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DebugTest {

    @Nested
    class DevBase {

        @BeforeEach
        void beforeEach() {

            Configuration.setDevStatsActive(true);
            Configuration.setDevStatsObjectEventsSuspended(false);
        }

        @Test
        @DisplayName("Get IStats object")
        void getIStatsObject() {

            assertTrue(FactoryGeometry.getIFPoint().devGetClassStats().isPresent(),
                    "The IStat object should be available");
        }

        @Test
        @DisplayName("Get IStats object (disabled)")
        void getIStatsObjectDisabled() {
            Configuration.setDevStatsActive(false);

            assertTrue(FactoryGeometry.getIFPoint().devGetClassStats().isEmpty(),
                    "The IStat object should not be available");
        }

        @Test
        @DisplayName("Validate number of initially registered events")
        void validateNumberOfInitiallyRegisteredEvents() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(0);
        }

        @Test
        @DisplayName("Register single event (dynamic)")
        void registerSingleEventDynamic() {
            IFPoint fPoint = FactoryGeometry.getIFPoint()
                    .set(1, 1, 1);

            Optional<IStats> statsOpt = fPoint.devGetClassStats();
            IStats stats = statsOpt.get();

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("set(double, double, double)");
        }

        @Test
        @DisplayName("Register single event")
        void registerSingleEvent() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactlyInAnyOrder("test event 1");
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
        @DisplayName("Register multiple events")
        void registerMultipleEvents() {
            Optional<IStats> statsOpt = FactoryGeometry.getIFPoint().devGetClassStats();
            IStats stats = statsOpt.get();

            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 10L);
            stats.recordEvent("test event 1", 10L);

            assertThat(stats.getMethodNames())
                    .hasSize(1)
                    .containsExactly("test event 1");
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
}
