package eu.scattering.core.transfer.containers.position;

import eu.scattering.core.transfer.containers.position.FPairPos4D.FPairPos4D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FPairPos4D")
public class FPairPos4DITest {
    private PositionFactory factory = PositionFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPairPos4DIBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dtoPosA = factory.getFPos4DI(1, 2, 3, 4);
            var dtoPosB = factory.getFPos4DI(5, 6, 7, 8);
            var dto = factory.getFPairPos4DI(dtoPosA, dtoPosB);

            Assertions.assertAll("Check values",
                    () -> assertEquals(dto.getPosA(), dtoPosA, "Position A is incorrect"),
                    () -> assertEquals(dto.getPosB(), dtoPosB, "Position B is incorrect")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPairPos4DIAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPairPos4DI(factory.getFPos4DI(1, 2, 3, 4), factory.getFPos4DI(5, 6, 7, 8));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPairPos4DI(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("To double")
        void parseToDouble() {
            var dtoOrigin = factory.getFPairPos4DI(factory.getFPos4DI(1, 2, 3, 4), factory.getFPos4DI(5, 6, 7, 8));
            var dtoTarget = dtoOrigin.toDouble();

            Assertions.assertAll("Check values",
                    () -> assertEquals(dtoOrigin.getPosA().toDouble(), dtoTarget.getPosA(), "Position A is incorrect"),
                    () -> assertEquals(dtoOrigin.getPosB().toDouble(), dtoTarget.getPosB(), "Position B is incorrect"),
                    () -> assertTrue(dtoTarget instanceof FPairPos4D, "The type of the target object is erroneous")
            );
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FPairPos3DJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPairPos4DI(factory.getFPos4DI(1, 2, 3, 4), factory.getFPos4DI(5, 6, 7, 8));
            var dto2a = factory.getFPairPos4DI(factory.getFPos4DI(1, 2, 3, 4), factory.getFPos4DI(5, 6, 7, 8));
            var dto2b = factory.getFPairPos4DI(factory.getFPos4DI(8, 7, 6, 5), factory.getFPos4DI(4, 3, 2, 1));

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(), "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(), "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var dto1 = factory.getFPairPos4DI(factory.getFPos4DI(1, 2, 3, 4), factory.getFPos4DI(5, 6, 7, 8));
            var dto2a = factory.getFPairPos4DI(factory.getFPos4DI(1, 2, 3, 4), factory.getFPos4DI(5, 6, 7, 8));
            var dto2b = factory.getFPairPos4DI(factory.getFPos4DI(8, 7, 6, 5), factory.getFPos4DI(4, 3, 2, 1));

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a, "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b, "The objects should be different")
            );
        }
    }
}
