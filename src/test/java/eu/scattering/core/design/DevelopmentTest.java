package eu.scattering.core.design;

import eu.scattering.core.design.development.Development;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.development.statistics.StatisticsMethod;
import eu.scattering.core.design.injection.MainFactory;
import eu.scattering.core.implementation.injection.MainFactoryDefault;
import eu.scattering.core.implementation.injection.MainFactoryDevelopment;
import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("Development")
public class DevelopmentTest {

    private MainFactory mainFactory = new MainFactoryDefault();
    private MainFactory mainFactoryDevelopment = new MainFactoryDevelopment();

    private Development<?> getTestInstance() {

        return mainFactory.getFPoint();
    }

    private Development<?> getTestInstanceDevelopment() {

        return mainFactoryDevelopment.getFPoint();
    }

    @BeforeEach
    void beforeEach() {

        getTestInstanceDevelopment().devResetNumberOfInstances();
        getTestInstanceDevelopment().devGetClassStatistics().ifPresent(e -> e.setEnabled());
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

                assertThat(statistics.getRegisteredMethodNames())
                        .hasSize(0);
            }

            @Test
            @DisplayName("Register single class event")
            void registerSingleClassEvent() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getRegisteredMethodNames())
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

                assertThat(statistics.getRegisteredMethodNames())
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

                assertThat(statistics.getRegisteredMethodNames())
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

                assertThat(statistics.getRegisteredMethodNames())
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

                statistics.setDisabled();

                assertFalse(statistics.isEnabled(),
                        "The registration of events should be suspended");
            }

            @Test
            @DisplayName("Register event in the suspended state")
            void recordEventSuspended() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.setDisabled();
                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getRegisteredMethodNames())
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

                assertThat(statistics.getRegisteredMethodNames())
                        .hasSize(0);
            }

            @Test
            @DisplayName("Register single object event (enabled)")
            void registerSingleObjectEventEnabled() {
                Development<?> element = getTestInstanceDevelopment();
                element.objectStatisticsEnable();

                Optional<Statistics> statisticsOptional = element.devGetStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getRegisteredMethodNames())
                        .hasSize(1)
                        .containsExactlyInAnyOrder("test event 1");
            }

            @Test
            @DisplayName("Register single object event (disabled)")
            void registerSingleObjectEventDisabled() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThat(statistics.getRegisteredMethodNames())
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

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");

                assertTrue(methodOptional.isPresent(), "The requested method should be available");
            }

            @Test
            @DisplayName("Get method (throw NullPointerException)")
            void getMethodThrowNullPointerException() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                assertThrows(NullPointerException.class, () -> statistics.getRegisteredMethod(null),
                        "The method name must be defined");
            }

            @Test
            @DisplayName("Get non-existent method")
            void getNonExistentMethod() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 2");

                assertTrue(methodOptional.isEmpty(), "The requested method should not be available");
            }

            @Test
            @DisplayName("Get execution times")
            void getExecutionTimes() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertThat(method.getExecutionTimes())
                        .hasSize(3)
                        .containsExactlyInAnyOrder(10L, 20L, 30L);
            }

            @Test
            @DisplayName("Register time (validate list)")
            void registerTimeValidateList() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                method.recordExecutionTime(20L);
                method.recordExecutionTime(30L);

                assertThat(method.getExecutionTimes())
                        .hasSize(3)
                        .containsExactlyInAnyOrder(10L, 20L, 30L);
            }

            @Test
            @DisplayName("Register time (validate iterations)")
            void registerTimeValidateIterations() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                method.recordExecutionTime(20L);
                method.recordExecutionTime(30L);

                assertEquals(3, method.getNumberOfIterations(),
                        "The number of iterations is incorrect");
            }

            @Test
            @DisplayName("Register time (throw ArithmeticException)")
            void registerTimeThrowArithmeticException() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertThrows(ArithmeticException.class, () -> method.recordExecutionTime(-10L),
                        "The event time must be a positive value");
            }

            @Test
            @DisplayName("Get number of iterations")
            void getNumberOfIterations() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertEquals(3, method.getNumberOfIterations(),
                        "The number of iterations is incorrect");
            }

            @Test
            @DisplayName("Get time total")
            void getTimeTotal() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertEquals(60L, method.getTimeTotal(),
                        "The total time is incorrect");
            }

            @Test
            @DisplayName("Get time avg")
            void getTimeAvg() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertEquals(20L, method.getTimeAvg(),
                        "The averaged time is incorrect");
            }

            @Test
            @DisplayName("Get time min")
            void getTimeMin() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertEquals(10L, method.getTimeMin(),
                        "The min time is incorrect");
            }

            @Test
            @DisplayName("Get time max")
            void getTimeMax() {
                Optional<Statistics> statisticsOptional = getTestInstanceDevelopment().devGetClassStatistics();
                Statistics statistics = statisticsOptional.orElseGet(() -> fail("Empty optional"));

                statistics.recordEvent("test event 1", 10L);
                statistics.recordEvent("test event 1", 20L);
                statistics.recordEvent("test event 1", 30L);

                Optional<StatisticsMethod> methodOptional = statistics.getRegisteredMethod("test event 1");
                StatisticsMethod method = methodOptional.orElseGet(() -> fail("Empty optional"));

                assertEquals(30L, method.getTimeMax(),
                        "The max time is incorrect");
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
