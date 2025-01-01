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
            var rotAxis = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var rotCore = factory.getFPos4D(1, 2, 3, 4);
            var rotAngle = 5d;

            var dto = factory.getFRot(rotAxis, rotAngle, rotCore);

            Assertions.assertAll("Check values",
                    () -> assertEquals(rotAxis, dto.getAxis(), "The rotation axis is incorrect"),
                    () -> assertEquals(rotAngle, dto.getAngle(), "The rotation angle e is incorrect"),
                    () -> assertEquals(rotCore, dto.getQuaternionCore(), "The rotation core is incorrect")

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
            var rotAxis = factory.getFPairPos3D(factory.getFPos3D(1, 2, 3), factory.getFPos3D(4, 5, 6));
            var rotCore = factory.getFPos4D(1, 2, 3, 4);
            var rotAngle = 5d;

            var dtoOrigin = factory.getFRot(rotAxis, rotAngle, rotCore);

            JSONObject jsonOrigin = dtoOrigin.exportToJSON();

            var dtoCopy = factory.getFRot(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }
    }
}
