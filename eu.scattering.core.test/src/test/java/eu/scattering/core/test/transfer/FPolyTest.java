package eu.scattering.core.test.transfer;

import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FPoly")
public class FPolyTest {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPolyBasicTest {

        @Test
        @DisplayName("Core")
        void getCoreTest() {
            var dto = factory.getFPoly(1, 2, 3);

            assertEquals(3, dto.size());
            assertEquals(1, dto.getCore()[0]);
            assertEquals(2, dto.getCore()[1]);
            assertEquals(3, dto.getCore()[2]);
        }
    }

    @Nested
    @Tag("Advanced")
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
            var dto = factory.getFPoly(1, 2, 3);

            assertEquals(6, dto.getValue(1), 1E-6);
            assertEquals(2, dto.getValue(-1), 1E-6);
            assertEquals(11, dto.getValue(2), 1E-6);
            assertEquals(3, dto.getValue(-2), 1E-6);
        }
    }

    @Nested
    @Tag("Java")
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
