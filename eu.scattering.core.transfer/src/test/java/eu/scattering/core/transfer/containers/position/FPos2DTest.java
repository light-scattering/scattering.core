package eu.scattering.core.transfer.containers.position;

import eu.scattering.core.transfer.container.position.PositionFactory;
import eu.scattering.core.transfer.container.position.PositionFactoryConcrete;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FPos2D")
public class FPos2DTest {
    private PositionFactory factory = PositionFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPos2DBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dto = factory.getFPos2D(1, 2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPos2DAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPos2D(1, 2);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPos2D(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FPos2DJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPos2D(1, 2);
            var dto2a = factory.getFPos2D(1, 2);
            var dto2b = factory.getFPos2D(3, 4);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(), "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(), "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var dto1 = factory.getFPos2D(1, 2);
            var dto2a = factory.getFPos2D(1, 2);
            var dto2b = factory.getFPos2D(3, 4);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a, "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b, "The objects should be different")
            );
        }
    }
}
