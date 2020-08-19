package eu.scattering.core;

import eu.scattering.core.dev.IDev;
import eu.scattering.core.dev.stats.IStats;
import eu.scattering.core.dev.stats.IStatsMethod;
import eu.scattering.core.main.MainFactory;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class DevTest {

    private static boolean initialDevEnabled;
    private static boolean initialDevObjectStatsSuspended;

    private static IDev<?> getTestInstance() {

        return MainFactory.getIFLine();
    }

    @BeforeAll
    static void beforeAll() {

        initialDevEnabled = Config.isDevEnabled();
        initialDevObjectStatsSuspended = Config.isDevObjectStatsSuspended();
    }

    @AfterAll
    static void afterAll() {

        Config.setDevEnabled(initialDevEnabled);
        Config.setDevObjectStatsSuspended(initialDevObjectStatsSuspended);
    }

    @BeforeEach
    void beforeEach() {

        Config.setDevEnabled(true);
        Config.setDevObjectStatsSuspended(true);

        getTestInstance().devGetClassStats().ifPresent(IStats::reset);
        getTestInstance().devResetNumberOfInstances();
    }

    @Nested
    @DisplayName("IStats implementation")
    class DevIStats {

        @Nested
        @DisplayName("Class")
        class DevClass {

            @Test
            @DisplayName("Get IStats for classes")
            void getIStatsForClasses() {

                assertTrue(getTestInstance().devGetClassStats().isPresent(),
                        "The IStat object should be available");
            }

            @Test
            @DisplayName("Get IStats for classes (disabled)")
            void getIStatsForClassesDisabled() {
                Config.setDevEnabled(false);

                assertTrue(getTestInstance().devGetClassStats().isEmpty(),
                        "The IStat object should not be available");
            }

            @Test
            @DisplayName("Validate number of initially registered events for classes")
            void validateNumberOfInitiallyRegisteredEventsForClasses() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                assertThat(stats.getMethodNames())
                        .hasSize(0);
            }

            @Test
            @DisplayName("Register single class event")
            void registerSingleClassEvent() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                assertThat(stats.getMethodNames())
                        .hasSize(1)
                        .containsExactlyInAnyOrder("test event 1");
            }

            @Test
            @DisplayName("Register single class event (throw NullPointerException)")
            void registerSingleClassEventThrowNullPointerException() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                assertThrows(ArithmeticException.class,
                        () -> stats.recordEvent("test event 1", -10L),
                        "The event time must be a positive value");
            }

            @Test
            @DisplayName("Register single class event (throw ArithmeticException)")
            void registerSingleClassEventThrowArithmeticException() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                assertThrows(NullPointerException.class, () -> stats.recordEvent(null, 10L),
                        "The method name must be specified");
            }

            @Test
            @DisplayName("Register multiple unique events")
            void registerMultipleUniqueEvents() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

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
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

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
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

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
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                assertFalse(stats.isSuspended(),
                        "The registration of events is not consistent with the default value");
            }

            @Test
            @DisplayName("Validate suspended status (true)")
            void validateSuspendedStatusTrue() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.setSuspended(true);

                assertTrue(stats.isSuspended(),
                        "The registration of events should be suspended");
            }

            @Test
            @DisplayName("Validate suspended status (false)")
            void validateSuspendedStatusFalse() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.setSuspended(false);

                assertFalse(stats.isSuspended(),
                        "The registration of events should not be suspended");
            }

            @Test
            @DisplayName("Register event in the suspended state")
            void recordEventSuspended() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

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

                assertTrue(getTestInstance().devGetStats().isPresent(),
                        "The IStat object should be available");
            }

            @Test
            @DisplayName("Get IStats for object (disabled)")
            void getIStatsForObjectsDisabled() {
                Config.setDevEnabled(false);

                assertTrue(getTestInstance().devGetStats().isEmpty(),
                        "The IStat object should not be available");
            }

            @Test
            @DisplayName("Validate number of initially registered events for objects")
            void validateNumberOfInitiallyRegisteredEventsForObjects() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                assertThat(stats.getMethodNames())
                        .hasSize(0);
            }

            @Test
            @DisplayName("Register single object event (enabled)")
            void registerSingleObjectEventEnabled() {
                Config.setDevObjectStatsSuspended(false);

                Optional<IStats> statsOpt = getTestInstance().devGetStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                assertThat(stats.getMethodNames())
                        .hasSize(1)
                        .containsExactlyInAnyOrder("test event 1");
            }

            @Test
            @DisplayName("Register single object event (disabled)")
            void registerSingleObjectEventDisabled() {
                Optional<IStats> statsOpt = getTestInstance().devGetStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

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
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");

                assertTrue(statsMethodOpt.isPresent(), "The requested method should be available");
            }

            @Test
            @DisplayName("Get method (throw NullPointerException)")
            void getMethodThrowNullPointerException() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                assertThrows(NullPointerException.class, () -> stats.getMethod(null),
                        "The method name must be defined");
            }

            @Test
            @DisplayName("Get non-existent method")
            void getNonExistentMethod() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 2");

                assertTrue(statsMethodOpt.isEmpty(), "The requested method should not be available");
            }

            @Test
            @DisplayName("Get execution times")
            void getExecutionTimes() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);
                stats.recordEvent("test event 1", 20L);
                stats.recordEvent("test event 1", 30L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertThat(statsMethod.getExecutionTimes())
                        .hasSize(3)
                        .containsExactlyInAnyOrder(10L, 20L, 30L);
            }

            @Test
            @DisplayName("Register time (validate list)")
            void registerTimeValidateList() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                statsMethod.recordExecutionTime(20L);
                statsMethod.recordExecutionTime(30L);

                assertThat(statsMethod.getExecutionTimes())
                        .hasSize(3)
                        .containsExactlyInAnyOrder(10L, 20L, 30L);
            }

            @Test
            @DisplayName("Register time (validate iterations)")
            void registerTimeValidateIterations() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                statsMethod.recordExecutionTime(20L);
                statsMethod.recordExecutionTime(30L);

                assertEquals(3, statsMethod.getNumberOfIterations(),
                        "The number of iterations is incorrect");
            }

            @Test
            @DisplayName("Register time (throw ArithmeticException)")
            void registerTimeThrowArithmeticException() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertThrows(ArithmeticException.class, () -> statsMethod.recordExecutionTime(-10L),
                        "The event time must be a positive value");
            }

            @Test
            @DisplayName("Get number of iterations")
            void getNumberOfIterations() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);
                stats.recordEvent("test event 1", 20L);
                stats.recordEvent("test event 1", 30L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertEquals(3, statsMethod.getNumberOfIterations(),
                        "The number of iterations is incorrect");
            }

            @Test
            @DisplayName("Get time total")
            void getTimeTotal() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);
                stats.recordEvent("test event 1", 20L);
                stats.recordEvent("test event 1", 30L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertEquals(60L, statsMethod.getTimeTotal(),
                        "The total time is incorrect");
            }

            @Test
            @DisplayName("Get time avg")
            void getTimeAvg() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);
                stats.recordEvent("test event 1", 20L);
                stats.recordEvent("test event 1", 30L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertEquals(20L, statsMethod.getTimeAvg(),
                        "The averaged time is incorrect");
            }

            @Test
            @DisplayName("Get time min")
            void getTimeMin() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);
                stats.recordEvent("test event 1", 20L);
                stats.recordEvent("test event 1", 30L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertEquals(10L, statsMethod.getTimeMin(),
                        "The min time is incorrect");
            }

            @Test
            @DisplayName("Get time max")
            void getTimeMax() {
                Optional<IStats> statsOpt = getTestInstance().devGetClassStats();
                IStats stats = statsOpt.orElseGet(() -> fail("Empty optional"));

                stats.recordEvent("test event 1", 10L);
                stats.recordEvent("test event 1", 20L);
                stats.recordEvent("test event 1", 30L);

                Optional<IStatsMethod> statsMethodOpt = stats.getMethod("test event 1");
                IStatsMethod statsMethod = statsMethodOpt.orElseGet(() -> fail("Empty optional"));

                assertEquals(30L, statsMethod.getTimeMax(),
                        "The max time is incorrect");
            }

        }

    }

    @Nested
    @DisplayName("IDev methods")
    class IDevMethods {

        @Test
        @DisplayName("Number of instances (initial)")
        void getNumberOfInstancesInitial() {

            getTestInstance().devResetNumberOfInstances();

            assertEquals(1, getTestInstance().devGetNumberOfInstances().orElseGet(() -> fail("Empty optional")),
                    "There should be exactly one instance");
        }

        @Test
        @DisplayName("Number of instances")
        void getNumberOfInstances() {

            getTestInstance().devResetNumberOfInstances();

            getTestInstance();
            getTestInstance();

            assertEquals(3, getTestInstance().devGetNumberOfInstances().orElseGet(() -> fail("Empty optional")),
                    "There should be exactly three instances");
        }

        @Test
        @DisplayName("Number of instances (disabled)")
        void getNumberOfInstancesDisabled() {

            Config.setDevEnabled(false);

            assertEquals(Optional.empty(), getTestInstance().devGetNumberOfInstances(),
                    "The number of instances field should not be available");
        }

        @Test
        @DisplayName("Reset number of instances")
        void resetNumberOfInstances() {

            getTestInstance().devResetNumberOfInstances();

            getTestInstance();
            getTestInstance();

            getTestInstance().devResetNumberOfInstances();

            assertEquals(1, getTestInstance().devGetNumberOfInstances().orElseGet(() -> fail("Empty optional")),
                    "There should be exactly one instance");
        }

        @Test
        @DisplayName("Get meta-data")
        void getMetaData() {

            assertEquals("", getTestInstance().devGetMeta(),
                    "The meta-data should be empty");
        }

        @Test
        @DisplayName("Set meta-data")
        void setMetaData() {

            IDev<?> fElement = getTestInstance();

            fElement.devSetMeta("test");

            assertEquals("test", fElement.devGetMeta(),
                    "The meta-data is incorrect");
        }

    }

}
