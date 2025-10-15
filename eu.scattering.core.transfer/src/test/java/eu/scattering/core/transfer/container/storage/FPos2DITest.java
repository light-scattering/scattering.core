package eu.scattering.core.transfer.container.storage;

import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FPos2DI")
public class FPos2DITest {
    private static final XXXFactory factory = XXXFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPos2DIBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dto = factory.getFPos2DI(1, 2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPos2DIAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPos2DI(1, 2);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPos2DI(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("To double")
        void parseToDouble() {
            var dtoOrigin = factory.getFPos2DI(1, 2);
            var dtoTarget = dtoOrigin.toDouble();

            Assertions.assertAll("Check values",
                    () -> assertEquals(dtoOrigin.getD0(), dtoTarget.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(dtoOrigin.getD1(), dtoTarget.getD1(), "The D1 value is incorrect"),
                    () -> assertTrue(dtoTarget instanceof FPos2D, "The type of the target object is erroneous")
            );
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FPos2DIJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPos2DI(1, 2);
            var dto2a = factory.getFPos2DI(1, 2);
            var dto2b = factory.getFPos2DI(3, 4);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(), "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(), "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var dto1 = factory.getFPos2DI(1, 2);
            var dto2a = factory.getFPos2DI(1, 2);
            var dto2b = factory.getFPos2DI(3, 4);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a, "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b, "The objects should be different")
            );
        }
    }
}
