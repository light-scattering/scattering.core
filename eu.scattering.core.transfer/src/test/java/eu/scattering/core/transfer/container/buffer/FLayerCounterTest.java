package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FLayerCounter")
public class FLayerCounterTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FLayerCounterBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(1, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(0, fLayer.addSelf(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Single layer incrementation")
        void singleLayerInc() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.inc();
            fLayer.inc();
            fLayer.inc();
            fLayer.inc();

            int addedLayers = fLayer.inc();

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(5, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(1, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(5, fLayer.addSelf(),
                            "The sum is incorrect"),
                    () -> assertEquals(0, addedLayers,
                            "The number of added layers is incorrect")
            );
        }

        @Test
        @DisplayName("Single layer incrementation (index)")
        void singleLayerIncIndex() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.inc(0);
            fLayer.inc(0);
            fLayer.inc(0);
            fLayer.inc(0);

            int addedLayers = fLayer.inc(0);

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(5, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(1, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(5, fLayer.addSelf(),
                            "The sum is incorrect"),
                    () -> assertEquals(0, addedLayers,
                            "The number of added layers is incorrect")
            );
        }

        @Test
        @DisplayName("Multi layer incrementation")
        void multiLayerInc() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            int addedLayersA = fLayer.inc(1);
            int addedLayersB = fLayer.inc(2);

            fLayer.inc(2);
            fLayer.inc(3);
            fLayer.inc(3);
            fLayer.inc(3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(2, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(3, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(4, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(6, fLayer.addSelf(),
                            "The sum is incorrect"),
                    () -> assertEquals(1, addedLayersA,
                            "The number of added layers is incorrect"),
                    () -> assertEquals(1, addedLayersB,
                            "The number of added layers is incorrect")
            );
        }

        @Test
        @DisplayName("Multi layer incrementation (distant)")
        void multiLayerIncDistant() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            int addedLayersA = fLayer.inc(5);
            int addedLayersB = fLayer.inc(5);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(2, fLayer.get(5),
                            "Layer 5 value is incorrect"),
                    () -> assertEquals(6, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(2, fLayer.addSelf(),
                            "The sum is incorrect"),
                    () -> assertEquals(5, addedLayersA,
                            "The number of added layers is incorrect"),
                    () -> assertEquals(0, addedLayersB,
                            "The number of added layers is incorrect")
            );
        }

        @Test
        @DisplayName("Single layer setter")
        void singleLayerSet() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(2);
            fLayer.set(4);

            int addedLayers = fLayer.set(6);

            Assertions.assertAll("Check values",
                    () -> assertEquals(6, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(6, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(1, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(6, fLayer.addSelf(),
                            "The sum is incorrect"),
                    () -> assertEquals(0, addedLayers,
                            "The number of added layers is incorrect")
            );
        }

        @Test
        @DisplayName("Multi layer setter")
        void multiLayerSet() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            int addedLayersA = fLayer.set(5, 2);
            int addedLayersB = fLayer.set(5, 5);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(5, fLayer.get(5),
                            "Layer 5 value is incorrect"),
                    () -> assertEquals(6, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(5, fLayer.addSelf(),
                            "The sum is incorrect"),
                    () -> assertEquals(5, addedLayersA,
                            "The number of added layers is incorrect"),
                    () -> assertEquals(0, addedLayersB,
                            "The number of added layers is incorrect")
            );
        }

        @Test
        @DisplayName("Reset")
        void reset() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.inc(1);
            fLayer.inc(2);
            fLayer.inc(2);
            fLayer.inc(3);
            fLayer.inc(3);
            fLayer.inc(3);

            fLayer.reset();

            fLayer.inc(0);
            fLayer.inc(1);
            fLayer.inc(2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(3, fLayer.size(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(3, fLayer.addSelf(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Illegal layer exception - increment")
        void wrongLayerIncException() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            assertThrows(IllegalArgumentException.class, () -> fLayer.inc(-1),
                    "Accessing an incorrect layer should throw an exception");
        }

        @Test
        @DisplayName("Illegal layer exception - get")
        void wrongLayerGetException() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            assertThrows(IllegalArgumentException.class, () -> fLayer.get(-1),
                    "Accessing an incorrect layer should throw an exception");
        }

        @Test
        @DisplayName("Illegal layer exception - set")
        void wrongLayerSetException() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            assertThrows(IllegalArgumentException.class, () -> fLayer.set(-1),
                    "Accessing an incorrect layer should throw an exception");
        }

        @Test
        @DisplayName("Illegal layer exception - set, value")
        void wrongLayerSetValueException() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            assertThrows(IllegalArgumentException.class, () -> fLayer.set(0, -1),
                    "Setting an incorrect value should throw an exception");
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core")
    class FLayerCounterCoreTest {

        @Test
        @DisplayName("Equals")
        void equals() {
            FLayerCounter fLayer1 = factory.getFLayerCounter();
            FLayerCounter fLayer2 = factory.getFLayerCounter();

            fLayer1.inc(0);
            fLayer1.inc(1);
            fLayer1.inc(5);

            fLayer2.inc(2);
            fLayer2.inc(3);
            fLayer2.inc(4);

            fLayer2.reset();

            fLayer2.inc(0);
            fLayer2.inc(1);
            fLayer2.inc(5);

            Assertions.assertAll("Check equality",
                    () -> assertEquals(fLayer1, fLayer2,
                            "Layers should be equal"),
                    () -> assertEquals(fLayer2, fLayer1,
                            "Layers should be equall"),
                    () -> assertEquals(fLayer1.hashCode(), fLayer2.hashCode(),
                            "Hash codes should be the same")
            );
        }

        @Test
        @DisplayName("Equals (fail)")
        void equalsFail() {
            FLayerCounter fLayer1 = factory.getFLayerCounter();
            FLayerCounter fLayer2 = factory.getFLayerCounter();

            fLayer1.inc(0);
            fLayer1.inc(1);
            fLayer1.inc(5);

            fLayer2.inc(2);
            fLayer2.inc(3);
            fLayer2.inc(4);

            Assertions.assertAll("Check equality",
                    () -> assertNotEquals(fLayer1, fLayer2,
                            "Layers should not be equal"),
                    () -> assertNotEquals(fLayer2, fLayer1,
                            "Layers should not be equal")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FLayerCounterAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSON() {
            FLayerCounter dtoOrigin = factory.getFLayerCounter();

            dtoOrigin.inc(0);
            dtoOrigin.inc(1);
            dtoOrigin.inc(2);
            dtoOrigin.inc(3);
            dtoOrigin.inc(4);
            dtoOrigin.inc(5);
            dtoOrigin.inc(6);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FLayerCounter dtoCopy = factory.getFLayerCounter(jsonOrigin);

            assertEquals(dtoOrigin.size(), dtoCopy.size(),
                    "The parsed JSON is erroneous");
        }

        @Test
        @DisplayName("Add zero")
        void addZero() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            fLayer.add();

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(4, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(6, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(5, fLayer.size(),
                            "The size is incorrect")
            );
        }

        @Test
        @DisplayName("Add multiple")
        void addMultiple() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            FLayerCounter fLayerA = factory.getFLayerCounter();

            fLayerA.set(1, 2);
            fLayerA.set(3, 1);
            fLayerA.set(4, 1);

            FLayerCounter fLayerB = factory.getFLayerCounter();

            fLayerB.set(4, 2);
            fLayerB.set(5, 1);

            fLayer.add(fLayerA, fLayerB);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(6, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(9, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(5),
                            "Layer 5 value is incorrect"),
                    () -> assertEquals(6, fLayer.size(),
                            "The size is incorrect")
            );
        }

        @Test
        @DisplayName("Average zero")
        void averageZero() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            fLayer.avg();

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(4, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(6, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(5, fLayer.size(),
                            "The size is incorrect")
            );
        }

        @Test
        @DisplayName("Average multiple")
        void averageMultiple() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            FLayerCounter fLayerA = factory.getFLayerCounter();

            fLayerA.set(1, 2);
            fLayerA.set(3, 1);
            fLayerA.set(4, 1);

            FLayerCounter fLayerB = factory.getFLayerCounter();

            fLayerB.set(4, 2);
            fLayerB.set(5, 1);

            fLayer.avg(fLayerA, fLayerB);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(2, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(3, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(5),
                            "Layer 5 value is incorrect"),
                    () -> assertEquals(6, fLayer.size(),
                            "The size is incorrect")
            );
        }

        @Test
        @DisplayName("Max zero")
        void maxZero() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            fLayer.max();

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(4, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(6, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(5, fLayer.size(),
                            "The size is incorrect")
            );
        }

        @Test
        @DisplayName("Max multiple")
        void maxMultiple() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            FLayerCounter fLayerA = factory.getFLayerCounter();

            fLayerA.set(1, 2);
            fLayerA.set(3, 1);
            fLayerA.set(4, 1);

            FLayerCounter fLayerB = factory.getFLayerCounter();

            fLayerB.set(4, 2);
            fLayerB.set(5, 1);

            fLayer.max(fLayerA, fLayerB);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fLayer.get(0),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(4, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(2),
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(3),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(6, fLayer.get(4),
                            "Layer 4 value is incorrect"),
                    () -> assertEquals(1, fLayer.get(5),
                            "Layer 5 value is incorrect"),
                    () -> assertEquals(6, fLayer.size(),
                            "The size is incorrect")
            );
        }

        @Test
        @DisplayName("Add self")
        void addSelf() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            double results = fLayer.addSelf();

            Assertions.assertAll("Check values",
                    () -> assertEquals(12, results,
                            1E-6, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Average self")
        void averageSelf() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            double results = fLayer.avgSelf();

            Assertions.assertAll("Check values",
                    () -> assertEquals((double) 12 / 5, results,
                            1E-6, "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Max self")
        void maxSelf() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            double results = fLayer.maxSelf();

            Assertions.assertAll("Check values",
                    () -> assertEquals(6, results,
                            "The value is incorrect")
            );
        }

        @Test
        @DisplayName("Iterate")
        void iterate() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            fLayer.set(0, 2);
            fLayer.set(1, 4);
            fLayer.set(4, 6);

            int[] results = new int[5];

            int index = 0;
            for (Integer value : fLayer) {
                results[index++] = value;
            }

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, results[0],
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(4, results[1],
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, results[2],
                            "Layer 2 value is incorrect"),
                    () -> assertEquals(0, results[3],
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(6, results[4],
                            "Layer 4 value is incorrect")
            );
        }

        @Test
        @DisplayName("Is empty")
        void isEmpty() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            assertTrue(fLayer.isEmpty());

            fLayer.set(4, 6);

            assertFalse(fLayer.isEmpty());

            fLayer.reset();

            assertTrue(fLayer.isEmpty());
        }

        @Test
        @DisplayName("Is zero layer only")
        void isZeroLayerOnly() {
            FLayerCounter fLayer = factory.getFLayerCounter();

            assertTrue(fLayer.isZeroLayerOnly());

            fLayer.set(0, 2);

            assertTrue(fLayer.isZeroLayerOnly());

            fLayer.set(1, 4);

            assertFalse(fLayer.isZeroLayerOnly());
        }
    }
}
