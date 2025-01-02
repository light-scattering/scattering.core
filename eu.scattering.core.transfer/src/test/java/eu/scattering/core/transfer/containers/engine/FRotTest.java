package eu.scattering.core.transfer.containers.engine;

import eu.scattering.core.transfer.containers.ContainerFactory;
import eu.scattering.core.transfer.containers.ContainerFactoryConcrete;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Timeout(1)
@DisplayName("FRot")
public class FRotTest {
    private ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FRotBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var rotAngle = 5d;
            var rotAxis = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var rotCoreCode = factory.getFPos4D(1, 2, 3, 4);

            var rotCoreMatrixOrigin = new double[3][3];

            rotCoreMatrixOrigin[0][0] = 1;
            rotCoreMatrixOrigin[0][1] = 2;
            rotCoreMatrixOrigin[0][2] = 3;
            rotCoreMatrixOrigin[1][0] = 4;
            rotCoreMatrixOrigin[1][1] = 5;
            rotCoreMatrixOrigin[1][2] = 6;
            rotCoreMatrixOrigin[2][0] = 7;
            rotCoreMatrixOrigin[2][1] = 8;
            rotCoreMatrixOrigin[2][2] = 9;

            var rotCoreMatrix = factory.getFMatrix3x3D(rotCoreMatrixOrigin);

            var dto = factory.getFRot(rotAxis, rotAngle, rotCoreCode, rotCoreMatrix);

            Assertions.assertAll("Check values",
                    () -> assertEquals(rotAxis, dto.getAxis(), "The rotation axis is incorrect"),
                    () -> assertEquals(rotAngle, dto.getAngle(), "The rotation angle is incorrect"),
                    () -> assertEquals(rotCoreCode, dto.getCoreCode(), "The rotation core is incorrect"),
                    () -> assertEquals(rotCoreMatrix, dto.getCoreMatrix(), "The rotation matrix is incorrect")

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
            var rotAngle = 5d;
            var rotAxis = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var rotCoreCode = factory.getFPos4D(1, 2, 3, 4);

            var rotCoreMatrixOrigin = new double[3][3];

            rotCoreMatrixOrigin[0][0] = 1;
            rotCoreMatrixOrigin[0][1] = 2;
            rotCoreMatrixOrigin[0][2] = 3;
            rotCoreMatrixOrigin[1][0] = 4;
            rotCoreMatrixOrigin[1][1] = 5;
            rotCoreMatrixOrigin[1][2] = 6;
            rotCoreMatrixOrigin[2][0] = 7;
            rotCoreMatrixOrigin[2][1] = 8;
            rotCoreMatrixOrigin[2][2] = 9;

            var rotCoreMatrix = factory.getFMatrix3x3D(rotCoreMatrixOrigin);

            var dtoOrigin = factory.getFRot(rotAxis, rotAngle, rotCoreCode, rotCoreMatrix);

            JSONObject jsonOrigin = dtoOrigin.exportToJSON();

            var dtoCopy = factory.getFRot(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }
    }
}
