package eu.scattering.core.test.storage;

import eu.scattering.core.design.storage.buffer.FBuffer;
import org.junit.jupiter.api.*;

import java.util.function.BiFunction;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FArray")
public class FBufferTest {

    @Nested
    @DisplayName("Basic")
    class FArrayBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fArray.size(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Increment")
        void increment() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.add(1.1, 2.2, 3.3);
            fArray.add(factory.getFPos3D(4.4, 5.5, 6.6));
            fArray.add(factory.getFPos3D(7.7, 8.8, 9.9));

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.1, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(3.3, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(4.4, fArray.getD0(1),
                            "D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fArray.getD1(1),
                            "D1 (1) is erroneous"),
                    () -> assertEquals(6.6, fArray.getD2(1),
                            "D2 (1) is erroneous"),
                    () -> assertEquals(7.7, fArray.getD0(2),
                            "D0 (2) is erroneous"),
                    () -> assertEquals(8.8, fArray.getD1(2),
                            "D1 (2) is erroneous"),
                    () -> assertEquals(9.9, fArray.getD2(2),
                            "D2 (2) is erroneous"),
                    () -> assertEquals(0, fArray.getData(0),
                            "Data (0) is erroneous"),
                    () -> assertEquals(0, fArray.getData(1),
                            "Data (0) is erroneous"),
                    () -> assertEquals(0, fArray.getData(2),
                            "Data (0) is erroneous"),
                    () -> assertNull(fArray.getMeta(0),
                            "Meta (0) is erroneous"),
                    () -> assertNull(fArray.getMeta(1),
                            "Meta (1) is erroneous"),
                    () -> assertNull(fArray.getMeta(2),
                            "Meta (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Increment with meta")
        void incrementWithData() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.addWithData(1.5, 2.5, 3.5, 1.1);
            fArray.addWithData(factory.getFPos3D(4.5, 5.5, 6.5), 2.2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.5, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(2.5, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(3.5, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(4.5, fArray.getD0(1),
                            "D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fArray.getD1(1),
                            "D1 (1) is erroneous"),
                    () -> assertEquals(6.5, fArray.getD2(1),
                            "D2 (1) is erroneous"),
                    () -> assertEquals(1.1, fArray.getData(0),
                            "Data (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getData(1),
                            "Data (1) is erroneous"),
                    () -> assertNull(fArray.getMeta(0),
                            "Meta (0) is erroneous"),
                    () -> assertNull(fArray.getMeta(1),
                            "Meta (1) is erroneous")
            );
        }

        @Test
        @DisplayName("Increment with meta")
        void incrementWithMeta() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.addWithMeta(1.5, 2.5, 3.5, 1.1);
            fArray.addWithMeta(factory.getFPos3D(4.5, 5.5, 6.5), 2.2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.5, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(2.5, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(3.5, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(4.5, fArray.getD0(1),
                            "D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fArray.getD1(1),
                            "D1 (1) is erroneous"),
                    () -> assertEquals(6.5, fArray.getD2(1),
                            "D2 (1) is erroneous"),
                    () -> assertEquals(0, fArray.getData(0),
                            "Data (0) is erroneous"),
                    () -> assertEquals(0, fArray.getData(1),
                            "Data (1) is erroneous"),
                    () -> assertEquals(1.1, fArray.getMeta(0),
                            "Meta (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getMeta(1),
                            "Meta (1) is erroneous")
            );
        }

        @Test
        @DisplayName("Increment with data and meta")
        void incrementWithDataMeta() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.addWithDataAndMeta(1.5, 2.5, 3.5, 1.1, 1.2);
            fArray.addWithDataAndMeta(factory.getFPos3D(4.5, 5.5, 6.5), 2.1, 2.2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.5, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(2.5, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(3.5, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(4.5, fArray.getD0(1),
                            "D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fArray.getD1(1),
                            "D1 (1) is erroneous"),
                    () -> assertEquals(6.5, fArray.getD2(1),
                            "D2 (1) is erroneous"),
                    () -> assertEquals(1.1, fArray.getData(0),
                            "Data (0) is erroneous"),
                    () -> assertEquals(2.1, fArray.getData(1),
                            "Data (1) is erroneous"),
                    () -> assertEquals(1.2, fArray.getMeta(0),
                            "Meta (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getMeta(1),
                            "Meta (1) is erroneous")
            );
        }

        @Test
        @DisplayName("Get FPos3D")
        void getFPos3D() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.addWithMeta(1, 2, 3, 1.1);

            Assertions.assertAll("Check values",
                    () -> assertEquals(factory.getFPos3D(1, 2, 3), fArray.getFPos3D(0),
                            "The values are incorrect")
            );
        }

        @Test
        @DisplayName("Clear")
        void clear() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.addWithMeta(1.5, 2.5, 3.5, 1.1);
            fArray.addWithMeta(4.5, 5.5, 6.5, 2.2);
            fArray.addWithMeta(7.5, 8.5, 9.5, 3.3);

            fArray.clear();

            fArray.addWithMeta(7.5, 8.5, 9.5, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(7.5, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(8.5, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(9.5, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(3.3, fArray.getMeta(0),
                            "Value (0) is erroneous")
            );

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(1),
                    "The index is out of bounds (positive), an exception should be thrown");
        }

        @Test
        @DisplayName("Out of bounds exception")
        void outOfBoundsException() {
            FBuffer<Double> fArray = factory.getFBuffer(2);

            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(factory.getFPos3D(4, 5, 6), 2.2);

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(-1),
                    "The index is out of bounds (negative), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(2),
                    "The index is out of bounds (positive), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.add(1, 2, 3),
                    "The index is out of bounds (buffer overflow), an exception should be thrown");
        }
    }

    @Nested
    @DisplayName("Core")
    class FArrayCoreTest {

        @Test
        @DisplayName("Equals")
        void equals() {
            FBuffer<Double> fArray1 = factory.getFBuffer(10);
            FBuffer<Double> fArray2 = factory.getFBuffer(15);

            fArray1.addWithDataAndMeta(4.1, 5.1, 6.1, -2.2, 2.2);
            fArray1.addWithDataAndMeta(7.1, 8.1, 9.1, -3.3, 3.3);

            fArray2.addWithDataAndMeta(3.1, 2.1, 1.1, -9.9, 9.9);
            fArray2.addWithDataAndMeta(6.1, 5.1, 4.1, -8.8, 8.8);
            fArray2.addWithDataAndMeta(9.1, 8.1, 7.1, -7.7, 7.7);

            fArray2.clear();

            fArray2.addWithDataAndMeta(4.1, 5.1, 6.1, -2.2, 4.4);
            fArray2.addWithDataAndMeta(7.1, 8.1, 9.1, -3.3, 5.5);

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
            FBuffer<Double> fArray1 = factory.getFBuffer(10);
            FBuffer<Double> fArray2 = factory.getFBuffer(15);

            fArray1.addWithDataAndMeta(4.1, 5.1, 6.1, -2.2, 2.2);
            fArray1.addWithDataAndMeta(7.1, 8.1, 9.1, -3.3, 3.3);

            fArray2.addWithDataAndMeta(4.0, 5.0, 6.0, -2.2, 2.2);
            fArray2.addWithDataAndMeta(7.0, 8.0, 9.0, -3.3, 3.3);

            Assertions.assertAll("Check equality",
                    () -> assertNotEquals(fArray1, fArray2,
                            "Arrays should not be equal"),
                    () -> assertNotEquals(fArray2, fArray1,
                            "Arrays should not be equal")
            );
        }
    }

    @Nested
    @DisplayName("Advanced")
    class FArrayAdvancedTest {

        @Test
        @DisplayName("Iterate")
        void iterate() {
            FBuffer<Double> fArray = factory.getFBuffer(100);

            fArray.addWithDataAndMeta(1.5, 2.5, 3.5, 10, 1.1);
            fArray.addWithDataAndMeta(4.5, 5.5, 6.5, 20, 2.2);
            fArray.addWithDataAndMeta(7.5, 8.5, 9.5, 30, 3.3);

            double[] sum = new double[fArray.size()];

            fArray.forEach((index, d0, d1, d2, data, meta) -> sum[index] = d0 + d1 + d2 + data + meta);

            Assertions.assertAll("Check values",
                    () -> assertEquals(18.6, sum[0],
                            "Index 0 value is erroneous"),
                    () -> assertEquals(38.7, sum[1],
                            "Index 1 value is erroneous"),
                    () -> assertEquals(58.8, sum[2],
                            "Index 2 value is erroneous")
            );
        }

        @Test
        @DisplayName("Deduplicate - Single")
        void deduplicateSingle() {
            int size = 10;

            FBuffer<Double> fArray = factory.getFBuffer(size);

            for (int i = 0 ; i < fArray.capacity() ; i++) {
                fArray.addWithMeta(i, i, i, i * 1.1);
            }

            int removed = fArray.deduplicate();

            Assertions.assertAll("Check results",
                    () -> assertEquals(10, fArray.size(),
                            "The number of elements is erroneous"),
                    () -> assertEquals(0, removed,
                            "The number of removed elements is erroneous")
            );
        }

        @Test
        @DisplayName("Deduplicate - Multiple")
        void deduplicateMultiple() {
            FBuffer<Double> fArray = factory.getFBuffer(20);

            fArray.addWithMeta(1.1, 1.1, 1.1, -1.1);
            fArray.addWithMeta(1.1, 2.1, 3.1, 1.1);
            fArray.addWithMeta(4.1, 5.1, 6.1, 2.2);
            fArray.addWithMeta(7.1, 8.1, 9.1, 3.3);
            fArray.addWithMeta(2.1, 2.1, 2.1, -2.2);
            fArray.addWithMeta(7.1, 8.1, 9.1, 4.4);
            fArray.addWithMeta(4.1, 5.1, 6.1, 5.5);
            fArray.addWithMeta(1.1, 2.1, 3.1, 6.6);
            fArray.addWithMeta(3.1, 3.1, 3.1, -3.3);
            fArray.addWithMeta(1.1, 2.1, 3.1, 7.7);
            fArray.addWithMeta(4.1, 5.1, 6.1, 8.8);
            fArray.addWithMeta(7.1, 8.1, 9.1, 9.9);
            fArray.addWithMeta(1.1, 1.1, 1.1, -4.4);

            int removed = fArray.deduplicate();

            Assertions.assertAll("Check results",
                    () -> assertEquals(6, fArray.size(),
                            "The number of elements is erroneous"),
                    () -> assertEquals(7, removed,
                            "The number of removed elements is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(0), factory.getFPos3D(1.1, 1.1, 1.1),
                            "Index 0 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(0), -1.1,
                            "Index 0 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(1), factory.getFPos3D(1.1, 2.1, 3.1),
                            "Index 1 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(1), 1.1,
                            "Index 1 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(2), factory.getFPos3D(4.1, 5.1, 6.1),
                            "Index 2 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(2), 2.2,
                            "Index 2 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(3), factory.getFPos3D(7.1, 8.1, 9.1),
                            "Index 3 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(3), 3.3,
                            "Index 3 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(4), factory.getFPos3D(2.1, 2.1, 2.1),
                            "Index 4 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(4), -2.2,
                            "Index 4 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(5), factory.getFPos3D(3.1, 3.1, 3.1),
                            "Index 5 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(5), -3.3,
                            "Index 5 meta is erroneous")
            );
        }

        @Test
        @DisplayName("Deduplicate with collision - Multiple")
        void deduplicateWithCollisionMultiple() {
            FBuffer<Double> fArray = factory.getFBuffer(20);

            fArray.addWithMeta(1.1, 1.1, 1.1, -1.1);
            fArray.addWithMeta(1.1, 2.1, 3.1, 1.1);
            fArray.addWithMeta(4.1, 5.1, 6.1, 2.2);
            fArray.addWithMeta(7.1, 8.1, 9.1, 3.3);
            fArray.addWithMeta(2.1, 2.1, 2.1, -2.2);
            fArray.addWithMeta(7.1, 8.1, 9.1, 4.4);
            fArray.addWithMeta(4.1, 5.1, 6.1, 5.5);
            fArray.addWithMeta(1.1, 2.1, 3.1, 6.6);
            fArray.addWithMeta(3.1, 3.1, 3.1, -3.3);
            fArray.addWithMeta(1.1, 2.1, 3.1, 7.7);
            fArray.addWithMeta(4.1, 5.1, 6.1, 8.8);
            fArray.addWithMeta(7.1, 8.1, 9.1, 9.9);
            fArray.addWithMeta(1.1, 1.1, 1.1, -4.4);

            BiFunction<Double, Double, Boolean> collision = (metaOld, metaNew) -> metaNew > metaOld;

            int removed = fArray.deduplicate(collision);

            Assertions.assertAll("Check results",
                    () -> assertEquals(6, fArray.size(),
                            "The number of elements is erroneous"),
                    () -> assertEquals(7, removed,
                            "The number of removed elements is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(0), factory.getFPos3D(1.1, 1.1, 1.1),
                            "Index 0 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(0), -1.1,
                            "Index 0 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(1), factory.getFPos3D(1.1, 2.1, 3.1),
                            "Index 1 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(1), 7.7,
                            "Index 1 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(2), factory.getFPos3D(4.1, 5.1, 6.1),
                            "Index 2 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(2), 8.8,
                            "Index 2 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(3), factory.getFPos3D(7.1, 8.1, 9.1),
                            "Index 3 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(3), 9.9,
                            "Index 3 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(4), factory.getFPos3D(2.1, 2.1, 2.1),
                            "Index 4 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(4), -2.2,
                            "Index 4 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3D(5), factory.getFPos3D(3.1, 3.1, 3.1),
                            "Index 5 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(5), -3.3,
                            "Index 5 meta is erroneous")
            );
        }
    }
}
