package eu.scattering.core.test.transfer;

import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FPairPos2D")
public class FPairPos2DTest {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPairPos2DBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dtoPosA = factory.getFPos2D(1, 2);
            var dtoPosB = factory.getFPos2D(3, 4);
            var dto = factory.getFPairPos2D(dtoPosA, dtoPosB);

            Assertions.assertAll("Check values",
                    () -> assertEquals(dto.getPosA(), dtoPosA,
                            "Position A is incorrect"),
                    () -> assertEquals(dto.getPosB(), dtoPosB,
                            "Position B is incorrect")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPairPos2DAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPairPos2D(factory.getFPos2D(1, 2), factory.getFPos2D(3, 4));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPairPos2D(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy,
                    "The parsed JSON object is erroneous");
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FPairPos2DJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPairPos2D(factory.getFPos2D(1, 2), factory.getFPos2D(3, 4));
            var dto2a = factory.getFPairPos2D(factory.getFPos2D(1, 2), factory.getFPos2D(3, 4));
            var dto2b = factory.getFPairPos2D(factory.getFPos2D(4, 3), factory.getFPos2D(2, 1));

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(),
                            "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(),
                            "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality A")
        void validateEqualityTestA() {
            var dto1 = factory.getFPairPos2D(factory.getFPos2D(1, 2), factory.getFPos2D(3, 4));
            var dto2a = factory.getFPairPos2D(factory.getFPos2D(1, 2), factory.getFPos2D(3, 4));
            var dto2b = factory.getFPairPos2D(factory.getFPos2D(4, 3), factory.getFPos2D(2, 1));

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a,
                            "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b,
                            "The objects should be different")
            );
        }

        @Test
        @DisplayName("Equality B")
        void validateEqualityTestB() {
            var dto1 = factory.getFPairPos2D(factory.getFPos2D(1, 2), factory.getFPos2D(3, 4));
            var dto2a = factory.getFPairPos2D(1, 2, 3, 4);
            var dto2b = factory.getFPairPos2D(4, 3, 2, 1);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a,
                            "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b,
                            "The objects should be different")
            );
        }
    }
}
