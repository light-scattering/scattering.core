package eu.scattering.core.test.storage.immutable;

import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.FMatrix3x3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.transfer.primitive.FPos4D;
import eu.scattering.core.design.transfer.complex.FRotQt;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(1)
@DisplayName("FRotQt")
public class FRotQtTest {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FRotBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            FPos4D qt = factory.getFPos4D(1, 2, 3, 4);
            FPos3D offset = factory.getFPos3D(5, 6, 7);

            var matrixTemplate = new double[3][3];

            matrixTemplate[0][0] = 1;
            matrixTemplate[0][1] = 2;
            matrixTemplate[0][2] = 3;
            matrixTemplate[1][0] = 4;
            matrixTemplate[1][1] = 5;
            matrixTemplate[1][2] = 6;
            matrixTemplate[2][0] = 7;
            matrixTemplate[2][1] = 8;
            matrixTemplate[2][2] = 9;

            FMatrix3x3D matrix = factory.getFMatrix3x3D(matrixTemplate);

            FRotQt dto = factory.getFRotQt(qt, offset, matrix);

            Assertions.assertAll("Check values",
                    () -> assertEquals(qt, dto.getQuaternion(), "The quaternion is incorrect"),
                    () -> assertEquals(offset, dto.getOffset(), "The offset is incorrect"),
                    () -> assertEquals(matrix, dto.getMatrix(), "The rotation matrix is incorrect")

            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FRotAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            FPos4D qt = factory.getFPos4D(1, 2, 3, 4);
            FPos3D offset = factory.getFPos3D(5, 6, 7);

            var matrixTemplate = new double[3][3];

            matrixTemplate[0][0] = 1;
            matrixTemplate[0][1] = 2;
            matrixTemplate[0][2] = 3;
            matrixTemplate[1][0] = 4;
            matrixTemplate[1][1] = 5;
            matrixTemplate[1][2] = 6;
            matrixTemplate[2][0] = 7;
            matrixTemplate[2][1] = 8;
            matrixTemplate[2][2] = 9;

            FMatrix3x3D matrix = factory.getFMatrix3x3D(matrixTemplate);

            FRotQt dtoOrigin = factory.getFRotQt(qt, offset, matrix);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FRotQt dtoCopy = factory.getFRotQt(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }
    }
}
