package eu.scattering.core.test.storage.transfer;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FPoly")
public class FPolyTest {

    @Nested
    @DisplayName("Basic")
    class FPolyBasicTest {

        @Test
        @DisplayName("Core")
        void getCoreTest() {
            var dto = factory.getFPoly(1, 2, 3);

            assertEquals(3, dto.size());
            assertEquals(1, dto.getRefCore()[0]);
            assertEquals(1, dto.at(0));
            assertEquals(2, dto.getRefCore()[1]);
            assertEquals(2, dto.at(1));
            assertEquals(3, dto.getRefCore()[2]);
            assertEquals(3, dto.at(2));

            assertThrows(ArrayIndexOutOfBoundsException.class, () -> dto.at(-1));
            assertThrows(ArrayIndexOutOfBoundsException.class, () -> dto.at(3));
        }
    }

    @Nested
    @DisplayName("Advanced")
    class FPolyAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPoly(1, 2, 3);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPoly(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy);
        }

        @Test
        @DisplayName("Get value")
        void getValueTest() {
            var dto = factory.getFPoly(3, 2, 1);

            assertEquals(6, dto.value(1), 1E-6);
            assertEquals(2, dto.value(-1), 1E-6);
            assertEquals(11, dto.value(2), 1E-6);
            assertEquals(3, dto.value(-2), 1E-6);
        }
    }

    @Nested
    @DisplayName("Java")
    class FPolyJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPoly(1, 2, 3);
            var dto2a = factory.getFPoly(1, 2, 3);
            var dto2b = factory.getFPoly(4, 5, 6);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode()),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode())
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var dto1 = factory.getFPoly(1, 2, 3);
            var dto2a = factory.getFPoly(1, 2, 3);
            var dto2b = factory.getFPoly(4, 5, 6);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a),
                    () -> assertNotEquals(dto1, dto2b)
            );
        }
    }
}
