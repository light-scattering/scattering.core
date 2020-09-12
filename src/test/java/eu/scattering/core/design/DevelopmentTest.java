package eu.scattering.core.design;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.implementation.FactoryDefault;
import eu.scattering.core.implementation.FactoryDevelopment;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("Development")
public class DevelopmentTest {

    private Factory mainFactory = new FactoryDefault();
    private Factory mainFactoryDevelopment = new FactoryDevelopment();

    private Development<?> getTestInstance() {

        return mainFactory.getFPoint();
    }

    private Development<?> getTestInstanceDevelopment() {

        return mainFactoryDevelopment.getFPoint();
    }

    @BeforeEach
    void beforeEach() {

        getTestInstanceDevelopment().devResetNumberOfInstances();
        getTestInstanceDevelopment().devGetClassStatistics().ifPresent(e -> e.setEnabled(true));
        getTestInstanceDevelopment().devGetClassStatistics().ifPresent(Statistics::reset);
    }

    @Nested
    @DisplayName("Statistics implementation")
    class DevStatistics {

        @Nested
        @DisplayName("Class")
        class DevClass {

            @Test
            @DisplayName("Get Statistics for classes")
            void getStatisticsForClasses() {

                assertTrue(getTestInstanceDevelopment().devGetClassStatistics().isPresent(),
                        "The Statistics object should be available");
            }

            @Test
            @DisplayName("Get Statistics for classes (disabled)")
            void getStatisticsForClassesDisabled() {

                assertFalse(getTestInstance().devGetClassStatistics().isPresent(),
                        "The Statistics object should not be available");
            }

            @Test
            @DisplayName("Validate number of initially registered events for classes")
            void validateNumberOfInitiallyRegisteredEventsForClasses() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                assertThat(statistics.getMethodNames())
                        .hasSize(0);
            }

            @Test
            @DisplayName("Register single class event")
            void registerSingleClassEvent() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(1)
                        .containsExactlyInAnyOrder("test event 1");
            }

            @Test
            @DisplayName("Register single class event (throw ArithmeticException)")
            void registerSingleClassEventThrowArithmeticException() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                assertThrows(ArithmeticException.class,
                        () -> statistics.recordEvent("test event 1", -10L),
                        "The event time must be represented by a positive value");
            }

            @Test
            @DisplayName("Register single class event (throw NullPointerException)")
            void registerSingleClassEventThrowNullPointerException() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                assertThrows(NullPointerException.class,
                        () -> statistics.recordEvent(null, 10L),
                        "The method name must be specified");
            }

            @Test
            @DisplayName("Register multiple unique events")
            void registerMultipleUniqueEvents() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 2", 10L);
                statistics.recordEvent("test event 3", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(3)
                        .containsExactlyInAnyOrder("test event 1", "test event 2", "test event 3");
            }

            @Test
            @DisplayName("Register multiple mixed events")
            void registerMultipleMixedEvents() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 2", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(2)
                        .containsExactlyInAnyOrder("test event 1", "test event 2");
            }

            @Test
            @DisplayName("Reset events")
            void resetEvents() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 2", 10L);
                statistics.recordEvent("test event 3", 10L);

                statistics.reset();

                statistics.recordEvent("test event 4", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(1)
                        .containsExactlyInAnyOrder("test event 4");
            }

            @Test
            @DisplayName("Validate active status")
            void validateActiveStatus() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                assertTrue(statistics.isEnabled(),
                        "The registration of events is not consistent with the default value");
            }

            @Test
            @DisplayName("Validate inactive status")
            void validateInactiveStatusTrue() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.setEnabled(false);

                assertFalse(statistics.isEnabled(),
                        "The registration of events should be suspended");
            }

            @Test
            @DisplayName("Register event in the suspended state")
            void recordEventSuspended() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.setEnabled(false);
                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(0);
            }

        }

        @Nested
        @DisplayName("Object (additional)")
        class DevObject {

            @Test
            @DisplayName("Get Statistics for objects")
            void getStatisticsForObjects() {

                assertTrue(getTestInstanceDevelopment().devGetStatistics().isPresent(),
                        "The Statistics object should be available");
            }

            @Test
            @DisplayName("Get Statistics for object (disabled)")
            void getStatisticsForObjectsDisabled() {

                assertTrue(getTestInstance().devGetStatistics().isEmpty(),
                        "The Statistics object should not be available");
            }

            @Test
            @DisplayName("Validate number of initially registered events for objects")
            void validateNumberOfInitiallyRegisteredEventsForObjects() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                assertThat(statistics.getMethodNames())
                        .hasSize(0);
            }

            @Test
            @DisplayName("Register single object event (enabled)")
            void registerSingleObjectEventEnabled() {
                Development<?> element = getTestInstanceDevelopment();
                element.devSetStatisticsEnabled(true);

                Optional<Statistics> statisticsOptional = element.devGetStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(1)
                        .containsExactlyInAnyOrder("test event 1");
            }

            @Test
            @DisplayName("Register single object event (disabled)")
            void registerSingleObjectEventDisabled() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getMethodNames())
                        .hasSize(0);
            }

        }

        @Nested
        @DisplayName("Method")
        class DevMethod {

            @Test
            @DisplayName("Get method")
            void getMethod() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                Optional<Integer> iterations = statistics.getNumberOfIterations("test event 1");
                Optional<List<Integer>> executionTimes = statistics.getExecutionTimes("test event 1");

                assertAll("Validate results",
                        () -> assertTrue(iterations.isPresent(), "The requested method should be available"),
                        () -> assertTrue(executionTimes.isPresent(), "The requested method should be available")
                );
            }

            @Test
            @DisplayName("Get method (throw NullPointerException)")
            void getMethodThrowNullPointerException() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertAll("Validate results",
                        () -> assertThrows(NullPointerException.class, () -> statistics.getNumberOfIterations(null),
                                "The method name must be defined"),
                        () -> assertThrows(NullPointerException.class, () -> statistics.getExecutionTimes(null),
                                "The method name must be defined")
                );
            }

            @Test
            @DisplayName("Get non-existent method")
            void getNonExistentMethod() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                Optional<Integer> iterations = statistics.getNumberOfIterations("test event 2");
                Optional<List<Integer>> executionTimes = statistics.getExecutionTimes("test event 2");

                assertAll("Validate results",
                        () -> assertTrue(iterations.isEmpty(), "The requested method should not be available"),
                        () -> assertTrue(executionTimes.isEmpty(), "The requested method should not be available")
                );
            }

            @Test
            @DisplayName("Get execution times")
            void getExecutionTimes() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<List<Integer>> methodOptional = statistics.getExecutionTimes("test event 1");
                List<Integer> method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertThat(method)
                        .hasSize(3)
                        .containsExactlyInAnyOrder(10, 20, 30);
            }

            @Test
            @DisplayName("Get number of iterations")
            void getNumberOfIterations() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<List<Integer>> methodOptional = statistics.getExecutionTimes("test event 1");
                List<Integer> method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertEquals(3, method.size(),
                        "The number of iterations is incorrect");
            }

        }

    }

    @Nested
    @DisplayName("Development methods")
    class DevelopmentMethods {

        @Test
        @DisplayName("Number of instances (initial)")
        void getNumberOfInstancesInitial() {

            getTestInstanceDevelopment().devResetNumberOfInstances();

            assertEquals(1,
                    getTestInstanceDevelopment().devGetNumberOfInstances().orElseGet(() -> fail("Empty optional")),
                    "There should be exactly one instance");
        }

        @Test
        @DisplayName("Number of instances")
        void getNumberOfInstances() {

            getTestInstanceDevelopment().devResetNumberOfInstances();

            getTestInstanceDevelopment();
            getTestInstanceDevelopment();

            assertEquals(3,
                    getTestInstanceDevelopment().devGetNumberOfInstances().orElseGet(() -> fail("Empty optional")),
                    "There should be exactly three instances");
        }

        @Test
        @DisplayName("Number of instances (disabled)")
        void getNumberOfInstancesDisabled() {

            assertEquals(Optional.empty(), getTestInstance().devGetNumberOfInstances(),
                    "The number of instances should not be available");
        }

        @Test
        @DisplayName("Reset number of instances")
        void resetNumberOfInstances() {

            getTestInstanceDevelopment().devResetNumberOfInstances();

            getTestInstanceDevelopment();
            getTestInstanceDevelopment();

            getTestInstanceDevelopment().devResetNumberOfInstances();

            assertEquals(1,
                    getTestInstanceDevelopment().devGetNumberOfInstances().orElseGet(() -> fail("Empty optional")),
                    "There should be exactly one instance");
        }

        @Test
        @DisplayName("Get meta-data")
        void getMetaData() {

            assertEquals("", getTestInstanceDevelopment().devGetLabel(),
                    "The meta-data should be empty");
        }

        @Test
        @DisplayName("Set meta-data")
        void setMetaData() {

            Development<?> element = getTestInstanceDevelopment();

            element.devSetLabel("test");

            assertEquals("test", element.devGetLabel(),
                    "The meta-data is incorrect");
        }

    }

}
