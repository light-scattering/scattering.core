package eu.scattering.core.transfer.containers.position;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FPairPos3D")
public class FPairPos3DTest {
    private PositionFactory factory = PositionFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPairPos3DBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dtoPosA = factory.getFPos3D(1, 2, 3);
            var dtoPosB = factory.getFPos3D(4, 5, 6);
            var dto = factory.getFPairPos3D(dtoPosA, dtoPosB);

            Assertions.assertAll("Check values",
                    () -> assertEquals(dto.getPosA(), dtoPosA, "Position A is incorrect"),
                    () -> assertEquals(dto.getPosB(), dtoPosB, "Position B is incorrect")
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

            JSONObject jsonOrigin = dtoOrigin.exportToJSON();

            var dtoCopy = factory.getFPairPos3D(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
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
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(), "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(), "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var dto1 = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2a = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var dto2b = factory.getFPairPos3D(factory.getFPos3D(6, 5, 4), factory.getFPos3D(3, 2, 1));

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a, "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b, "The objects should be different")
            );
        }
    }
}
