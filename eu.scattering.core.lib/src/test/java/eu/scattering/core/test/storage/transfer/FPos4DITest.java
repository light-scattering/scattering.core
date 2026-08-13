package eu.scattering.core.test.storage.transfer;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FPos4DI")
public class FPos4DITest {

    @Nested
    @DisplayName("Basic")
    class FPos4DIBasicTest {

        @Test
        @DisplayName("Values A")
        void getValuesATest() {
            var dto = factory.getFPos4DI(1, 2, 3, 4);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }

        @Test
        @DisplayName("Values B")
        void getValuesBTest() {
            var dto = factory.getFPos4DI(factory.getFPos3DI(1, 2, 3), 4);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }

        @Test
        @DisplayName("Values C")
        void getValuesCTest() {
            var dto = factory.getFPos4DI(1, factory.getFPos3DI(2, 3, 4));

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }

        @Test
        @DisplayName("Values D")
        void getValuesDTest() {
            var dto = factory.getFPos4DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }

        @Test
        @DisplayName("Values E")
        void getValuesETest() {
            var dto = factory.getFPos4DI(factory.getFPos2DI(1, 2), 3, 4);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }

        @Test
        @DisplayName("Values F")
        void getValuesFTest() {
            var dto = factory.getFPos4DI(1, factory.getFPos2DI(2, 3), 4);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }

        @Test
        @DisplayName("Values G")
        void getValuesGTest() {
            var dto = factory.getFPos4DI(1, 2, factory.getFPos2DI(3, 4));

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.getD0(), "The D0 value is incorrect"),
                    () -> assertEquals(2, dto.getD1(), "The D1 value is incorrect"),
                    () -> assertEquals(3, dto.getD2(), "The D2 value is incorrect"),
                    () -> assertEquals(4, dto.getD3(), "The D3 value is incorrect")
            );
        }
    }

    @Nested
    @DisplayName("Advanced")
    class FPos4DIAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPos4DI(1, 2, 3, 4);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPos4DI(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }
    }

    @Nested
    @DisplayName("Java")
    class FPos4DIJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPos4DI(1, 2, 3, 4);
            var dto2a = factory.getFPos4DI(1, 2, 3, 4);
            var dto2b = factory.getFPos4DI(5, 6, 7, 8);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(), "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(), "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var dto1 = factory.getFPos4DI(1, 2, 3, 4);
            var dto2a = factory.getFPos4DI(1, 2, 3, 4);
            var dto2b = factory.getFPos4DI(5, 6, 7, 8);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a, "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b, "The objects should be different")
            );
        }
    }
}
