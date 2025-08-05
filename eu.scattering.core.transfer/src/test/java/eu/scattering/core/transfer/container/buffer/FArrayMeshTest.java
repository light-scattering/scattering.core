package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FArrayMesh")
public class FArrayMeshTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FArrayMeshBasicTest {

        @Test
        @DisplayName("Creation")
        void creation() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fArray.size(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Incrementation")
        void inc() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            fArray.add(1, 2, 3);
            fArray.add(factory.getFPos3DI(4, 5, 6));
            fArray.add(factory.getFPos3DI(7, 8, 9));

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1, fArray.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2, fArray.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3, fArray.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4, fArray.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5, fArray.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6, fArray.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7, fArray.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8, fArray.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9, fArray.getD2(2),
                            "The D2 (3) is erroneous"),
                    () -> assertEquals(0, fArray.getValue(0),
                            "The value (0) is erroneous"),
                    () -> assertEquals(0, fArray.getValue(1),
                            "The value (1) is erroneous"),
                    () -> assertEquals(0, fArray.getValue(2),
                            "The value (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Incrementation with value")
        void incWithValue() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            fArray.addWithValue(1, 2, 3, 1.1);
            fArray.addWithValue(factory.getFPos3DI(4, 5, 6), 2.2);
            fArray.addWithValue(factory.getFPos4DI(7, 8, 9, 3));

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1, fArray.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2, fArray.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3, fArray.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4, fArray.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5, fArray.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6, fArray.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7, fArray.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8, fArray.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9, fArray.getD2(2),
                            "The D2 (3) is erroneous"),
                    () -> assertEquals(1.1, fArray.getValue(0),
                            "The value (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getValue(1),
                            "The value (1) is erroneous"),
                    () -> assertEquals(3, fArray.getValue(2),
                            "The value (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Out of bounds exception")
        void outOfBoundsException() {
            FArrayMesh fArray = factory.getFArrayMesh(3);

            fArray.addWithValue(1, 2, 3, 1.1);
            fArray.addWithValue(4, 5, 6, 2.2);
            fArray.addWithValue(7, 8, 9, 3.3);

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(-1),
                    "The index is out of bounds (negative), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(3),
                    "The index is out of bounds (positive), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.add(1, 2, 3),
                    "The index is out of bounds (buffer overflow), an exception should be thrown");
        }

        @Test
        @DisplayName("Reset")
        void reset() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            fArray.addWithValue(1, 2, 3, 1.1);
            fArray.addWithValue(4, 5, 6, 2.2);
            fArray.addWithValue(7, 8, 9, 3.3);

            fArray.reset();

            fArray.addWithValue(7, 8, 9, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(7, fArray.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(8, fArray.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(9, fArray.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(3.3, fArray.getValue(0),
                            "The value (0) is erroneous")
            );

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(1),
                    "The index is out of bounds (positive), an exception should be thrown");
        }

        @Test
        @DisplayName("Get")
        void get() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            fArray.addWithValue(1, 2, 3, 4);

            Assertions.assertAll("Check values",
                    () -> assertEquals(factory.getFPos3DI(1, 2, 3), fArray.getFPos3DI(0),
                            "The values are incorrect"),
                    () -> assertEquals(factory.getFPos4DI(1, 2, 3, 4), fArray.getFPos4DI(0),
                            "The values are incorrect")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core")
    class FArrayMeshCoreTest {

        @Test
        @DisplayName("Equals")
        void equals() {
            FArrayMesh fArray1 = factory.getFArrayMesh(10);
            FArrayMesh fArray2 = factory.getFArrayMesh(15);

            fArray1.addWithValue(4, 5, 6, 2.2);
            fArray1.addWithValue(7, 8, 9, 3.3);

            fArray2.addWithValue(3, 2, 1, 9.9);
            fArray2.addWithValue(6, 5, 4, 8.8);
            fArray2.addWithValue(9, 8, 7, 7.7);

            fArray2.reset();

            fArray2.addWithValue(4, 5, 6, 2.2);
            fArray2.addWithValue(7, 8, 9, 3.3);

            Assertions.assertAll("Check equality",
                    () -> assertEquals(fArray1, fArray2,
                            "Arrays should be equal"),
                    () -> assertEquals(fArray2, fArray1,
                            "Arrays should be equal"),
                    () -> assertEquals(fArray1.hashCode(), fArray2.hashCode(),
                            "Hash codes should be the same")
            );
        }

        @Test
        @DisplayName("Equals (fail)")
        void equalsFail() {
            FArrayMesh fArray1 = factory.getFArrayMesh(10);
            FArrayMesh fArray2 = factory.getFArrayMesh(15);

            fArray1.addWithValue(4, 5, 6, 2.2);
            fArray1.addWithValue(7, 8, 9, 3.3);

            fArray2.addWithValue(3, 2, 1, 9.9);
            fArray2.addWithValue(6, 5, 4, 8.8);
            fArray2.addWithValue(9, 8, 7, 7.7);

            Assertions.assertAll("Check equality",
                    () -> assertNotEquals(fArray1, fArray2,
                            "Arrays should not be equal"),
                    () -> assertNotEquals(fArray2, fArray1,
                            "Arrays should not be equal")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FArrayMeshAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSON() {
            FArrayMesh dtoOrigin = factory.getFArrayMesh(123);

            dtoOrigin.addWithValue(3, 2, 1, 9.9);
            dtoOrigin.addWithValue(6, 5, 4, 8.8);
            dtoOrigin.addWithValue(9, 8, 7, 7.7);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FArrayMesh dtoCopy = factory.getFArrayMesh(jsonOrigin);

            assertEquals(dtoOrigin.capacity(), dtoCopy.capacity(),
                    "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("Iteration")
        void iteration() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            fArray.addWithValue(1, 2, 3, 1.1);
            fArray.addWithValue(4, 5, 6, 2.2);
            fArray.addWithValue(7, 8, 9, 3.3);

            double[] sum = new double[fArray.size()];

            fArray.forEach((index, d0, d1, d2, value) -> sum[index] = d0 + d1 + d2 + value);

            Assertions.assertAll("Check values",
                    () -> assertEquals(7.1, sum[0],
                            "Index 0 value is erroneous"),
                    () -> assertEquals(17.2, sum[1],
                            "Index 1 value is erroneous"),
                    () -> assertEquals(27.3, sum[2],
                            "Index 2 value is erroneous")
            );
        }

        @Test
        @DisplayName("Iterator")
        void iterator() {
            FArrayMesh fArray = factory.getFArrayMesh(100);

            fArray.addWithValue(1, 2, 3, 1.1);
            fArray.addWithValue(4, 5, 6, 2.2);
            fArray.addWithValue(7, 8, 9, 3.3);

            double[] sum = new double[fArray.size()];

            int index = 0;
            for (double[] data : fArray) {
                sum[index++] = data[0] + data[1] + data[2] + data[3];
            }

            Assertions.assertAll("Check values",
                    () -> assertEquals(7.1, sum[0],
                            "Index 0 value is erroneous"),
                    () -> assertEquals(17.2, sum[1],
                            "Index 1 value is erroneous"),
                    () -> assertEquals(27.3, sum[2],
                            "Index 2 value is erroneous")
            );
        }

    }
}
