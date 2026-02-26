package eu.scattering.core.test.storage.transfer;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FPairPos4D")
public class FPairPos4DTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPairPos4DBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dtoPosA = factory.getFPos4D(1, 2, 3, 4);
            var dtoPosB = factory.getFPos4D(5, 6, 7, 8);
            var dto = factory.getFPairPos4D(dtoPosA, dtoPosB);

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
    class FPairPos4DAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPairPos4D(factory.getFPos4D(1, 2, 3, 4), factory.getFPos4D(5, 6, 7, 8));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPairPos4D(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy,
                    "The parsed JSON object is erroneous");
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FPairPos3DJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPairPos4D(factory.getFPos4D(1, 2, 3, 4), factory.getFPos4D(5, 6, 7, 8));
            var dto2a = factory.getFPairPos4D(factory.getFPos4D(1, 2, 3, 4), factory.getFPos4D(5, 6, 7, 8));
            var dto2b = factory.getFPairPos4D(factory.getFPos4D(8, 7, 6, 5), factory.getFPos4D(4, 3, 2, 1));

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
            var dto1 = factory.getFPairPos4D(factory.getFPos4D(1, 2, 3, 4), factory.getFPos4D(5, 6, 7, 8));
            var dto2a = factory.getFPairPos4D(factory.getFPos4D(1, 2, 3, 4), factory.getFPos4D(5, 6, 7, 8));
            var dto2b = factory.getFPairPos4D(factory.getFPos4D(8, 7, 6, 5), factory.getFPos4D(4, 3, 2, 1));

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
            var dto1 = factory.getFPairPos4D(factory.getFPos4D(1, 2, 3, 4), factory.getFPos4D(5, 6, 7, 8));
            var dto2a = factory.getFPairPos4D(1, 2, 3, 4, 5, 6, 7, 8);
            var dto2b = factory.getFPairPos4D(8, 7, 6, 5, 4, 3, 2, 1);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a,
                            "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b,
                            "The objects should be different")
            );
        }
    }
}
