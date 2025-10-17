package eu.scattering.core.test.storage.immutable;

import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.FPairPos2D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FPairPos2DI")
public class FPairPos2DITest {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPairPos2DIBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var dtoPosA = factory.getFPos2DI(1, 2);
            var dtoPosB = factory.getFPos2DI(3, 4);
            var dto = factory.getFPairPos2DI(dtoPosA, dtoPosB);

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
    class FPairPos2DIAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var dtoOrigin = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFPairPos2DI(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy,
                    "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("To double")
        void parseToDouble() {
            var dtoOrigin = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));
            var dtoTarget = dtoOrigin.toDouble();

            Assertions.assertAll("Check values",
                    () -> assertEquals(dtoOrigin.getPosA().toDouble(), dtoTarget.getPosA(),
                            "Position A is incorrect"),
                    () -> assertEquals(dtoOrigin.getPosB().toDouble(), dtoTarget.getPosB(),
                            "Position B is incorrect"),
                    () -> assertTrue(dtoTarget instanceof FPairPos2D,
                            "The type of the target object is erroneous")
            );
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FPairPos2DIJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var dto1 = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));
            var dto2a = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));
            var dto2b = factory.getFPairPos2DI(factory.getFPos2DI(4, 3), factory.getFPos2DI(2, 1));

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
            var dto1 = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));
            var dto2a = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));
            var dto2b = factory.getFPairPos2DI(factory.getFPos2DI(4, 3), factory.getFPos2DI(2, 1));

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
            var dto1 = factory.getFPairPos2DI(factory.getFPos2DI(1, 2), factory.getFPos2DI(3, 4));
            var dto2a = factory.getFPairPos2DI(1, 2, 3, 4);
            var dto2b = factory.getFPairPos2DI(4, 3, 2, 1);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a,
                            "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b,
                            "The objects should be different")
            );
        }
    }
}
