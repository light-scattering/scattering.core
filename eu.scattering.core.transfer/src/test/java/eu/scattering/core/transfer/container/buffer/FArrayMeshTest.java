package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import org.junit.jupiter.api.*;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FArrayMesh")
public class FArrayMeshTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FArrayMeshBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(10);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fArray.size(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Increment")
        void increment() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(100);

            fArray.add(1, 2, 3);
            fArray.add(factory.getFPos3DI(4, 5, 6));
            fArray.add(factory.getFPos3DI(7, 8, 9));

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(2, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(3, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(4, fArray.getD0(1),
                            "D0 (1) is erroneous"),
                    () -> assertEquals(5, fArray.getD1(1),
                            "D1 (1) is erroneous"),
                    () -> assertEquals(6, fArray.getD2(1),
                            "D2 (2) is erroneous"),
                    () -> assertEquals(7, fArray.getD0(2),
                            "D0 (3) is erroneous"),
                    () -> assertEquals(8, fArray.getD1(2),
                            "D1 (3) is erroneous"),
                    () -> assertEquals(9, fArray.getD2(2),
                            "D2 (3) is erroneous"),
                    () -> assertNull(fArray.getMeta(0),
                            "Value (0) is erroneous"),
                    () -> assertNull(fArray.getMeta(1),
                            "Value (1) is erroneous"),
                    () -> assertNull(fArray.getMeta(2),
                            "Value (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Increment with meta")
        void incrementWithMeta() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(100);

            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(factory.getFPos3DI(4, 5, 6), 2.2);

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fArray.size(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(2, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(3, fArray.getD2(0),
                            "D2 (0) is erroneous"),
                    () -> assertEquals(4, fArray.getD0(1),
                            "D0 (1) is erroneous"),
                    () -> assertEquals(5, fArray.getD1(1),
                            "D1 (1) is erroneous"),
                    () -> assertEquals(6, fArray.getD2(1),
                            "D2 (1) is erroneous"),
                    () -> assertEquals(1.1, fArray.getMeta(0),
                            "Value (0) is erroneous"),
                    () -> assertEquals(2.2, fArray.getMeta(1),
                            "Value (1) is erroneous")
            );
        }

        @Test
        @DisplayName("Get FPos3DI")
        void getFPos3DI() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(100);

            fArray.addWithMeta(1, 2, 3, 1.1);

            Assertions.assertAll("Check values",
                    () -> assertEquals(factory.getFPos3DI(1, 2, 3), fArray.getFPos3DI(0),
                            "The values are incorrect")
            );
        }

        @Test
        @DisplayName("Clear")
        void clear() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(100);

            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(4, 5, 6, 2.2);
            fArray.addWithMeta(7, 8, 9, 3.3);

            fArray.clear();

            fArray.addWithMeta(7, 8, 9, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fArray.size(),
                            "number of elements is incorrect"),
                    () -> assertEquals(7, fArray.getD0(0),
                            "D0 (0) is erroneous"),
                    () -> assertEquals(8, fArray.getD1(0),
                            "D1 (0) is erroneous"),
                    () -> assertEquals(9, fArray.getD2(0),
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
            FArrayMesh<Double> fArray = factory.getFArrayMesh(3);

            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(4, 5, 6, 2.2);
            fArray.addWithMeta(7, 8, 9, 3.3);

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(-1),
                    "The index is out of bounds (negative), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.getD0(3),
                    "The index is out of bounds (positive), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fArray.add(1, 2, 3),
                    "The index is out of bounds (buffer overflow), an exception should be thrown");
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core")
    class FArrayMeshCoreTest {

        @Test
        @DisplayName("Equals")
        void equals() {
            FArrayMesh<Double> fArray1 = factory.getFArrayMesh(10);
            FArrayMesh<Double> fArray2 = factory.getFArrayMesh(15);

            fArray1.addWithMeta(4, 5, 6, 2.2);
            fArray1.addWithMeta(7, 8, 9, 3.3);

            fArray2.addWithMeta(3, 2, 1, 9.9);
            fArray2.addWithMeta(6, 5, 4, 8.8);
            fArray2.addWithMeta(9, 8, 7, 7.7);

            fArray2.clear();

            fArray2.addWithMeta(4, 5, 6, 4.4);
            fArray2.addWithMeta(7, 8, 9, 5.5);

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
            FArrayMesh<Double> fArray1 = factory.getFArrayMesh(10);
            FArrayMesh<Double> fArray2 = factory.getFArrayMesh(15);

            fArray1.addWithMeta(4, 5, 6, 2.2);
            fArray1.addWithMeta(7, 8, 9, 3.3);

            fArray2.addWithMeta(3, 2, 1, 9.9);
            fArray2.addWithMeta(6, 5, 4, 8.8);
            fArray2.addWithMeta(9, 8, 7, 7.7);

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
        @DisplayName("Iterate")
        void iterate() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(100);

            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(4, 5, 6, 2.2);
            fArray.addWithMeta(7, 8, 9, 3.3);

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
        @DisplayName("Deduplicate - Single")
        void deduplicateSingle() {
            int size = 10;

            FArrayMesh<Double> fArray = factory.getFArrayMesh(size);

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
            FArrayMesh<Double> fArray = factory.getFArrayMesh(20);

            fArray.addWithMeta(1, 1, 1, -1.1);
            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(4, 5, 6, 2.2);
            fArray.addWithMeta(7, 8, 9, 3.3);
            fArray.addWithMeta(2, 2, 2, -2.2);
            fArray.addWithMeta(7, 8, 9, 4.4);
            fArray.addWithMeta(4, 5, 6, 5.5);
            fArray.addWithMeta(1, 2, 3, 6.6);
            fArray.addWithMeta(3, 3, 3, -3.3);
            fArray.addWithMeta(1, 2, 3, 7.7);
            fArray.addWithMeta(4, 5, 6, 8.8);
            fArray.addWithMeta(7, 8, 9, 9.9);
            fArray.addWithMeta(1, 1, 1, -4.4);

            int removed = fArray.deduplicate();

            Assertions.assertAll("Check results",
                    () -> assertEquals(6, fArray.size(),
                            "The number of elements is erroneous"),
                    () -> assertEquals(7, removed,
                            "The number of removed elements is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(0), factory.getFPos3DI(1, 1, 1),
                            "Index 0 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(0), -1.1,
                            "Index 0 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(1), factory.getFPos3DI(1, 2, 3),
                            "Index 1 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(1), 1.1,
                            "Index 1 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(2), factory.getFPos3DI(4, 5, 6),
                            "Index 2 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(2), 2.2,
                            "Index 2 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(3), factory.getFPos3DI(7, 8, 9),
                            "Index 3 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(3), 3.3,
                            "Index 3 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(4), factory.getFPos3DI(2, 2, 2),
                            "Index 4 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(4), -2.2,
                            "Index 4 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(5), factory.getFPos3DI(3, 3, 3),
                            "Index 5 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(5), -3.3,
                            "Index 5 meta is erroneous")
            );
        }

        @Test
        @DisplayName("Deduplicate with collision - Multiple")
        void deduplicateWithCollisionMultiple() {
            FArrayMesh<Double> fArray = factory.getFArrayMesh(20);

            fArray.addWithMeta(1, 1, 1, -1.1);
            fArray.addWithMeta(1, 2, 3, 1.1);
            fArray.addWithMeta(4, 5, 6, 2.2);
            fArray.addWithMeta(7, 8, 9, 3.3);
            fArray.addWithMeta(2, 2, 2, -2.2);
            fArray.addWithMeta(7, 8, 9, 4.4);
            fArray.addWithMeta(4, 5, 6, 5.5);
            fArray.addWithMeta(1, 2, 3, 6.6);
            fArray.addWithMeta(3, 3, 3, -3.3);
            fArray.addWithMeta(1, 2, 3, 7.7);
            fArray.addWithMeta(4, 5, 6, 8.8);
            fArray.addWithMeta(7, 8, 9, 9.9);
            fArray.addWithMeta(1, 1, 1, -4.4);

            BiFunction<Double, Double, Boolean> collision = (metaOld, metaNew) -> metaNew > metaOld;

            int removed = fArray.deduplicate(collision);

            Assertions.assertAll("Check results",
                    () -> assertEquals(6, fArray.size(),
                            "The number of elements is erroneous"),
                    () -> assertEquals(7, removed,
                            "The number of removed elements is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(0), factory.getFPos3DI(1, 1, 1),
                            "Index 0 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(0), -1.1,
                            "Index 0 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(1), factory.getFPos3DI(1, 2, 3),
                            "Index 1 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(1), 7.7,
                            "Index 1 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(2), factory.getFPos3DI(4, 5, 6),
                            "Index 2 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(2), 8.8,
                            "Index 2 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(3), factory.getFPos3DI(7, 8, 9),
                            "Index 3 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(3), 9.9,
                            "Index 3 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(4), factory.getFPos3DI(2, 2, 2),
                            "Index 4 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(4), -2.2,
                            "Index 4 meta is erroneous"),
                    () -> assertEquals(fArray.getFPos3DI(5), factory.getFPos3DI(3, 3, 3),
                            "Index 5 position is erroneous"),
                    () -> assertEquals(fArray.getMeta(5), -3.3,
                            "Index 5 meta is erroneous")
            );
        }
    }
}
