package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.FLayer.FLayer;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FLayer")
public class FLayerTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FLayerBasicTest {

        @Test
        @DisplayName("Creation")
        void creationTest() {
            FLayer fLayer = factory.getFLayer();

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(0, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.getNumberOfLayers(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(0, fLayer.getSum(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Single layer incrementation")
        void singleLayerIncTest() {
            FLayer fLayer = factory.getFLayer();

            fLayer.inc();
            fLayer.inc();
            fLayer.inc();
            fLayer.inc();
            fLayer.inc();

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(5, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.getNumberOfLayers(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(5, fLayer.getSum(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Single layer incrementation (indirect)")
        void singleLayerIncIndirectTest() {
            FLayer fLayer = factory.getFLayer();

            fLayer.inc(0);
            fLayer.inc(0);
            fLayer.inc(0);
            fLayer.inc(0);
            fLayer.inc(0);

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, fLayer.get(),
                            "Layer 0 value is incorrect"),
                    () -> assertEquals(5, fLayer.get(0),
                            "Layer 0 value is incorrect (indirect)"),
                    () -> assertEquals(0, fLayer.get(1),
                            "Layer 1 value is incorrect"),
                    () -> assertEquals(0, fLayer.getNumberOfLayers(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(5, fLayer.getSum(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Multi layer incrementation")
        void multiLayerIncTest() {
            FLayer fLayer = factory.getFLayer();

            fLayer.inc(1);
            fLayer.inc(2);
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
                    () -> assertEquals(3, fLayer.getNumberOfLayers(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(6, fLayer.getSum(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Multi layer incrementation (distant)")
        void multiLayerIncDistantTest() {
            FLayer fLayer = factory.getFLayer();

            fLayer.inc(5);
            fLayer.inc(5);

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
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(2, fLayer.get(5),
                            "Layer 3 value is incorrect"),
                    () -> assertEquals(5, fLayer.getNumberOfLayers(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(2, fLayer.getSum(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Multi layer incrementation (reset)")
        void multiLayerIncRstTest() {
            FLayer fLayer = factory.getFLayer();

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
                    () -> assertEquals(2, fLayer.getNumberOfLayers(),
                            "The number of layers is incorrect"),
                    () -> assertEquals(3, fLayer.getSum(),
                            "The sum is incorrect")
            );
        }

        @Test
        @DisplayName("Illegal layer exception - increment")
        void wrongLayerIncExceptionTest() {
            FLayer fLayer = factory.getFLayer();

            assertThrows(IllegalArgumentException.class, () -> fLayer.inc(-1),
                    "Accessing an incorrect layer should throw an exception");
        }

        @Test
        @DisplayName("Illegal layer exception - get")
        void wrongLayerGetExceptionTest() {
            FLayer fLayer = factory.getFLayer();

            assertThrows(IllegalArgumentException.class, () -> fLayer.get(-1),
                    "Accessing an incorrect layer should throw an exception");
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core")
    class FLayerCoreTest {

        @Test
        @DisplayName("Equals")
        void equalsTest() {
            FLayer fLayer1 = factory.getFLayer();
            FLayer fLayer2 = factory.getFLayer();

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
        void equalsFailTest() {
            FLayer fLayer1 = factory.getFLayer();
            FLayer fLayer2 = factory.getFLayer();

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
    class FRotAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            FLayer dtoOrigin = factory.getFLayer();

            dtoOrigin.inc(0);
            dtoOrigin.inc(1);
            dtoOrigin.inc(2);
            dtoOrigin.inc(3);
            dtoOrigin.inc(4);
            dtoOrigin.inc(5);
            dtoOrigin.inc(6);

            dtoOrigin.reset();

            dtoOrigin.inc(1);
            dtoOrigin.inc(2);
            dtoOrigin.inc(2);
            dtoOrigin.inc(3);
            dtoOrigin.inc(3);
            dtoOrigin.inc(3);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FLayer dtoCopy = factory.getFLayer(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy,
                    "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("Iteration")
        void iterationTest() {
            FLayer fLayer = factory.getFLayer();

            fLayer.inc(1);
            fLayer.inc(2);
            fLayer.inc(2);
            fLayer.inc(3);
            fLayer.inc(3);
            fLayer.inc(3);

            double[] values = new double[fLayer.getNumberOfLayers() + 1];

            fLayer.iterate((index, value) -> values[index] = fLayer.get(index));

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, values[0],
                            "Layer 0 value is erroneous"),
                    () -> assertEquals(1, values[1],
                            "Layer 1 value is erroneous"),
                    () -> assertEquals(2, values[2],
                            "Layer 2 value is erroneous"),
                    () -> assertEquals(3, values[3],
                            "Layer 3" + "value is erroneous")
            );
        }
    }
}
