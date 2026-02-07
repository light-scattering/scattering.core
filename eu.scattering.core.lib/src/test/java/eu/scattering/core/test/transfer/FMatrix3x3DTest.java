package eu.scattering.core.test.transfer;

import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@Timeout(1)
@DisplayName("FMatrix3x3D")
public class FMatrix3x3DTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FMatrix3x3DBasicTest {

        @Test
        @DisplayName("Values")
        void getValuesTest() {
            var origin = new double[3][3];

            origin[0][0] = 1;
            origin[0][1] = 2;
            origin[0][2] = 3;
            origin[1][0] = 4;
            origin[1][1] = 5;
            origin[1][2] = 6;
            origin[2][0] = 7;
            origin[2][1] = 8;
            origin[2][2] = 9;

            var dto = factory.getFMatrix3x3D(origin);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.get0x0(), "The 0x0 value is incorrect"),
                    () -> assertEquals(2, dto.get0x1(), "The 0x1 value is incorrect"),
                    () -> assertEquals(3, dto.get0x2(), "The 0x2 value is incorrect"),
                    () -> assertEquals(4, dto.get1x0(), "The 1x0 value is incorrect"),
                    () -> assertEquals(5, dto.get1x1(), "The 1x1 value is incorrect"),
                    () -> assertEquals(6, dto.get1x2(), "The 1x2 value is incorrect"),
                    () -> assertEquals(7, dto.get2x0(), "The 2x0 value is incorrect"),
                    () -> assertEquals(8, dto.get2x1(), "The 2x1 value is incorrect"),
                    () -> assertEquals(9, dto.get2x2(), "The 2x2 value is incorrect")

            );
        }

        @Test
        @DisplayName("Values - Array")
        void getValuesArrayTest() {
            var origin = new double[3][3];

            origin[0][0] = 1;
            origin[0][1] = 2;
            origin[0][2] = 3;
            origin[1][0] = 4;
            origin[1][1] = 5;
            origin[1][2] = 6;
            origin[2][0] = 7;
            origin[2][1] = 8;
            origin[2][2] = 9;

            var dto = factory.getFMatrix3x3D(origin);

            var dtoRes = dto.getArray();

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dtoRes[0][0], "The 0x0 value is incorrect"),
                    () -> assertEquals(2, dtoRes[0][1], "The 0x1 value is incorrect"),
                    () -> assertEquals(3, dtoRes[0][2], "The 0x2 value is incorrect"),
                    () -> assertEquals(4, dtoRes[1][0], "The 1x0 value is incorrect"),
                    () -> assertEquals(5, dtoRes[1][1], "The 1x1 value is incorrect"),
                    () -> assertEquals(6, dtoRes[1][2], "The 1x2 value is incorrect"),
                    () -> assertEquals(7, dtoRes[2][0], "The 2x0 value is incorrect"),
                    () -> assertEquals(8, dtoRes[2][1], "The 2x1 value is incorrect"),
                    () -> assertEquals(9, dtoRes[2][2], "The 2x2 value is incorrect")

            );
        }

        @Test
        @DisplayName("Values - Update origin")
        void getValuesUpdateOriginTest() {
            var origin = new double[3][3];

            origin[0][0] = 1;
            origin[0][1] = 2;
            origin[0][2] = 3;
            origin[1][0] = 4;
            origin[1][1] = 5;
            origin[1][2] = 6;
            origin[2][0] = 7;
            origin[2][1] = 8;
            origin[2][2] = 9;

            var dto = factory.getFMatrix3x3D(origin);

            origin[0][0] = 9;
            origin[0][1] = 8;
            origin[0][2] = 7;
            origin[1][0] = 6;
            origin[1][1] = 5;
            origin[1][2] = 4;
            origin[2][0] = 3;
            origin[2][1] = 2;
            origin[2][2] = 1;

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.get0x0(), "The 0x0 value is incorrect"),
                    () -> assertEquals(2, dto.get0x1(), "The 0x1 value is incorrect"),
                    () -> assertEquals(3, dto.get0x2(), "The 0x2 value is incorrect"),
                    () -> assertEquals(4, dto.get1x0(), "The 1x0 value is incorrect"),
                    () -> assertEquals(5, dto.get1x1(), "The 1x1 value is incorrect"),
                    () -> assertEquals(6, dto.get1x2(), "The 1x2 value is incorrect"),
                    () -> assertEquals(7, dto.get2x0(), "The 2x0 value is incorrect"),
                    () -> assertEquals(8, dto.get2x1(), "The 2x1 value is incorrect"),
                    () -> assertEquals(9, dto.get2x2(), "The 2x2 value is incorrect")

            );


        }

        @Test
        @DisplayName("Values - Update copy")
        void getValuesUpdateCopyTest() {
            var origin = new double[3][3];

            origin[0][0] = 1;
            origin[0][1] = 2;
            origin[0][2] = 3;
            origin[1][0] = 4;
            origin[1][1] = 5;
            origin[1][2] = 6;
            origin[2][0] = 7;
            origin[2][1] = 8;
            origin[2][2] = 9;

            var dto = factory.getFMatrix3x3D(origin);

            var dtoRes = dto.getArray();

            dtoRes[0][0] = 9;
            dtoRes[0][1] = 8;
            dtoRes[0][2] = 7;
            dtoRes[1][0] = 6;
            dtoRes[1][1] = 5;
            dtoRes[1][2] = 4;
            dtoRes[2][0] = 3;
            dtoRes[2][1] = 2;
            dtoRes[2][2] = 1;

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, dto.get0x0(), "The 0x0 value is incorrect"),
                    () -> assertEquals(2, dto.get0x1(), "The 0x1 value is incorrect"),
                    () -> assertEquals(3, dto.get0x2(), "The 0x2 value is incorrect"),
                    () -> assertEquals(4, dto.get1x0(), "The 1x0 value is incorrect"),
                    () -> assertEquals(5, dto.get1x1(), "The 1x1 value is incorrect"),
                    () -> assertEquals(6, dto.get1x2(), "The 1x2 value is incorrect"),
                    () -> assertEquals(7, dto.get2x0(), "The 2x0 value is incorrect"),
                    () -> assertEquals(8, dto.get2x1(), "The 2x1 value is incorrect"),
                    () -> assertEquals(9, dto.get2x2(), "The 2x2 value is incorrect")


            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FMatrix3x3DAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            var core = new double[3][3];

            core[0][0] = 1;
            core[0][1] = 2;
            core[0][2] = 3;
            core[1][0] = 4;
            core[1][1] = 5;
            core[1][2] = 6;
            core[2][0] = 7;
            core[2][1] = 8;
            core[2][2] = 9;

            var dtoOrigin = factory.getFMatrix3x3D(core);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            var dtoCopy = factory.getFMatrix3x3D(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }
    }

    @Nested
    @Tag("Java")
    @DisplayName("Java")
    class FMatrix3x3DJavaTest {

        @Test
        @DisplayName("Hash codes")
        void validateHashCodeTest() {
            var origin1 = new double[3][3];

            origin1[0][0] = 1;
            origin1[0][1] = 2;
            origin1[0][2] = 3;
            origin1[1][0] = 4;
            origin1[1][1] = 5;
            origin1[1][2] = 6;
            origin1[2][0] = 7;
            origin1[2][1] = 8;
            origin1[2][2] = 9;

            var dto1 = factory.getFMatrix3x3D(origin1);

            var origin2a = new double[3][3];

            origin2a[0][0] = 1;
            origin2a[0][1] = 2;
            origin2a[0][2] = 3;
            origin2a[1][0] = 4;
            origin2a[1][1] = 5;
            origin2a[1][2] = 6;
            origin2a[2][0] = 7;
            origin2a[2][1] = 8;
            origin2a[2][2] = 9;

            var dto2a = factory.getFMatrix3x3D(origin2a);

            var origin2b = new double[3][3];

            origin2b[0][0] = 9;
            origin2b[0][1] = 8;
            origin2b[0][2] = 7;
            origin2b[1][0] = 6;
            origin2b[1][1] = 5;
            origin2b[1][2] = 4;
            origin2b[2][0] = 3;
            origin2b[2][1] = 2;
            origin2b[2][2] = 1;

            var dto2b = factory.getFMatrix3x3D(origin2b);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1.hashCode(), dto2a.hashCode(), "The hash code should be exact"),
                    () -> assertNotEquals(dto1.hashCode(), dto2b.hashCode(), "The hash code should be different")
            );
        }

        @Test
        @DisplayName("Equality")
        void validateEqualityTest() {
            var origin1 = new double[3][3];

            origin1[0][0] = 1;
            origin1[0][1] = 2;
            origin1[0][2] = 3;
            origin1[1][0] = 4;
            origin1[1][1] = 5;
            origin1[1][2] = 6;
            origin1[2][0] = 7;
            origin1[2][1] = 8;
            origin1[2][2] = 9;

            var dto1 = factory.getFMatrix3x3D(origin1);

            var origin2a = new double[3][3];

            origin2a[0][0] = 1;
            origin2a[0][1] = 2;
            origin2a[0][2] = 3;
            origin2a[1][0] = 4;
            origin2a[1][1] = 5;
            origin2a[1][2] = 6;
            origin2a[2][0] = 7;
            origin2a[2][1] = 8;
            origin2a[2][2] = 9;

            var dto2a = factory.getFMatrix3x3D(origin2a);

            var origin2b = new double[3][3];

            origin2b[0][0] = 9;
            origin2b[0][1] = 8;
            origin2b[0][2] = 7;
            origin2b[1][0] = 6;
            origin2b[1][1] = 5;
            origin2b[1][2] = 4;
            origin2b[2][0] = 3;
            origin2b[2][1] = 2;
            origin2b[2][2] = 1;

            var dto2b = factory.getFMatrix3x3D(origin2b);

            Assertions.assertAll("Check hash codes",
                    () -> assertEquals(dto1, dto2a, "The objects should be exact"),
                    () -> assertNotEquals(dto1, dto2b, "The objects should be different")
            );
        }
    }
}
