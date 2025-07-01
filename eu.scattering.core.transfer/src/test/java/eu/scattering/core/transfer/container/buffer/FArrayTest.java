package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FArray")
public class FArrayTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FArrayBasicTest {

        @Test
        @DisplayName("Creation")
        void creation() {
            FArray fArray = factory.getFArray(100);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fArray.size(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Incrementation")
        void inc() {
            FArray fArray = factory.getFArray(100);

            fArray.add(1.1, 2.2, 3.3);
            fArray.add(4.4, 5.5, 6.6);
            fArray.add(7.7, 8.8, 9.9);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.1, fArray.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3.3, fArray.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4.4, fArray.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fArray.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6.6, fArray.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7.7, fArray.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8.8, fArray.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9.9, fArray.getD2(2),
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
            FArray fArray = factory.getFArray(100);

            fArray.add(1.5, 2.5, 3.5, 1.1);
            fArray.add(4.5, 5.5, 6.5, 2.2);
            fArray.add(7.5, 8.5, 9.5, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.5, fArray.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2.5, fArray.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3.5, fArray.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4.5, fArray.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fArray.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6.5, fArray.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7.5, fArray.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8.5, fArray.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9.5, fArray.getD2(2),
                            "The D2 (3) is erroneous"),
                    () -> assertEquals(1.1, fArray.getValue(0),
                            "The value (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getValue(1),
                            "The value (1) is erroneous"),
                    () -> assertEquals(3.3, fArray.getValue(2),
                            "The value (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Out of bounds exception")
        void outOfBoundsException() {
            FArray fArray = factory.getFArray(3);

            fArray.add(1, 2, 3, 1.1);
            fArray.add(4, 5, 6, 2.2);
            fArray.add(7, 8, 9, 3.3);

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
            FArray fArray = factory.getFArray(100);

            fArray.add(1.5, 2.5, 3.5, 1.1);
            fArray.add(4.5, 5.5, 6.5, 2.2);
            fArray.add(7.5, 8.5, 9.5, 3.3);

            fArray.reset();

            fArray.add(7.5, 8.5, 9.5, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(7.5, fArray.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(8.5, fArray.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(9.5, fArray.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(3.3, fArray.getValue(0),
                            "The value (0) is erroneous")
            );

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(1),
                    "The index is out of bounds (positive), an exception should be thrown");
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FArrayIAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSON() {
            FArray dtoOrigin = factory.getFArray(123);

            dtoOrigin.add(3.5, 2.5, 1.5, 9.9);
            dtoOrigin.add(6.5, 5.5, 4.5, 8.8);
            dtoOrigin.add(9.5, 8.5, 7.5, 7.7);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FArray dtoCopy = factory.getFArray(jsonOrigin);

            assertEquals(dtoOrigin.capacity(), dtoCopy.capacity(),
                    "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("Iteration")
        void iteration() {
            FArray fArray = factory.getFArray(100);

            fArray.add(1.5, 2.5, 3.5, 1.1);
            fArray.add(4.5, 5.5, 6.5, 2.2);
            fArray.add(7.5, 8.5, 9.5, 3.3);

            double[] sum = new double[fArray.size()];

            fArray.iterate((index, d0, d1, d2, value) -> sum[index] = d0 + d1 + d2 + value);

            Assertions.assertAll("Check values",
                    () -> assertEquals(8.6, sum[0],
                            "Index 0 value is erroneous"),
                    () -> assertEquals(18.7, sum[1],
                            "Index 1 value is erroneous"),
                    () -> assertEquals(28.8, sum[2],
                            "Index 2 value is erroneous")
            );
        }

        @Test
        @DisplayName("Iterator")
        void iterator() {
            FArray fArray = factory.getFArray(100);

            fArray.add(1.5, 2.5, 3.5, 1.1);
            fArray.add(4.5, 5.5, 6.5, 2.2);
            fArray.add(7.5, 8.5, 9.5, 3.3);

            double[] sum = new double[fArray.size()];

            int index = 0;
            for (double[] data : fArray) {
                sum[index++] = data[0] + data[1] + data[2] + data[3];
            }

            Assertions.assertAll("Check values",
                    () -> assertEquals(8.6, sum[0],
                            "Index 0 value is erroneous"),
                    () -> assertEquals(18.7, sum[1],
                            "Index 1 value is erroneous"),
                    () -> assertEquals(28.8, sum[2],
                            "Index 2 value is erroneous")
            );
        }
    }
}
