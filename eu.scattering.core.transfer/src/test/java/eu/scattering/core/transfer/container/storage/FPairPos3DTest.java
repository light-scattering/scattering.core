package eu.scattering.core.transfer.container.storage;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FPairPos3D")
public class FPairPos3DTest {
    private static final XXXFactory factory = XXXFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPairPos3DBasicTest {

        @Test
        @DisplayName("Construct with min/max")
        void constructWithMinMax() {
            double min = -1;
            double max = 2;

            var dto = factory.getFPairPos3D(min, max);

            Assertions.assertAll("Check values",
                    () -> assertEquals(dto.getPosA(), factory.getFPos3D(min, min, min),
                            "Position A is incorrect"),
                    () -> assertEquals(dto.getPosB(), factory.getFPos3D(max, max, max),
                            "Position B is incorrect")
            );
        }

        @Test
        @DisplayName("Construct with range")
        void constructWithRange() {
            double range = 5;

            var dto = factory.getFPairPos3D(range);

            Assertions.assertAll("Check values",
                    () -> assertEquals(dto.getPosA(), factory.getFPos3D(-range, -range, -range),
                            "Position A is incorrect"),
                    () -> assertEquals(dto.getPosB(), factory.getFPos3D(range, range, range),
                            "Position B is incorrect")
            );
        }

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dtoPosA = factory.getFPos3D(1, 2, 3);
            var dtoPosB = factory.getFPos3D(4, 5, 6);
            var dto = factory.getFPairPos3D(dtoPosA, dtoPosB);

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
    class FPairPos3DAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPairPos3D(jsonOrigin);

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
            var dto1 = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2a = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2b = factory.getFPairPos3D(factory.getFPos3D(6, 5, 4), factory.getFPos3D(3, 2, 1));

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
            var dto1 = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2a = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2b = factory.getFPairPos3D(factory.getFPos3D(6, 5, 4), factory.getFPos3D(3, 2, 1));

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
            var dto1 = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2a = factory.getFPairPos3D(1, 2, 3, 4, 5, 6);
            var dto2b = factory.getFPairPos3D(6, 5, 4, 3, 2, 1);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a,
                            "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b,
                            "The objects should be different")
            );
        }
    }
}
