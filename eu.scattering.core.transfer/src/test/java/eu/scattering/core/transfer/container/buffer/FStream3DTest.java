package eu.scattering.core.transfer.container.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FStream3D")
public class FStream3DTest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FStream3DBasicTest {

        @Test
        @DisplayName("Creation")
        void creationTest() {
            FStream3D fStream = factory.getFStream3D(100);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fStream.getNumberOfElements(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Incrementation")
        void incTest() {
            FStream3D fStream = factory.getFStream3D(100);

            fStream.add(1.1, 2.2, 3.3);
            fStream.add(4.4, 5.5, 6.6);
            fStream.add(7.7, 8.8, 9.9);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fStream.getNumberOfElements(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.1, fStream.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2.2, fStream.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3.3, fStream.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4.4, fStream.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fStream.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6.6, fStream.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7.7, fStream.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8.8, fStream.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9.9, fStream.getD2(2),
                            "The D2 (3) is erroneous"),
                    () -> assertEquals(0, fStream.getValue(0),
                            "The value (0) is erroneous"),
                    () -> assertEquals(0, fStream.getValue(1),
                            "The value (1) is erroneous"),
                    () -> assertEquals(0, fStream.getValue(2),
                            "The value (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Incrementation with value")
        void incWithValueTest() {
            FStream3D fStream = factory.getFStream3D(100);

            fStream.add(1.5, 2.5, 3.5, 1.1);
            fStream.add(4.5, 5.5, 6.5, 2.2);
            fStream.add(7.5, 8.5, 9.5, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fStream.getNumberOfElements(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1.5, fStream.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2.5, fStream.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3.5, fStream.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4.5, fStream.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5.5, fStream.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6.5, fStream.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7.5, fStream.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8.5, fStream.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9.5, fStream.getD2(2),
                            "The D2 (3) is erroneous"),
                    () -> assertEquals(1.1, fStream.getValue(0),
                            "The value (0) is erroneous"),
                    () -> assertEquals(2.2, fStream.getValue(1),
                            "The value (1) is erroneous"),
                    () -> assertEquals(3.3, fStream.getValue(2),
                            "The value (2) is erroneous")
            );
        }

        @Test
        @DisplayName("Out of bounds exception")
        void outOfBoundsExceptionTest() {
            FStream3D fStream = factory.getFStream3D(3);

            fStream.add(1, 2, 3, 1.1);
            fStream.add(4, 5, 6, 2.2);
            fStream.add(7, 8, 9, 3.3);

            assertThrows(IndexOutOfBoundsException.class, () -> fStream.getD0(-1),
                    "The index is out of bounds (negative), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fStream.getD0(3),
                    "The index is out of bounds (positive), an exception should be thrown");

            assertThrows(IndexOutOfBoundsException.class, () -> fStream.add(1, 2, 3),
                    "The index is out of bounds (buffer overflow), an exception should be thrown");
        }

        @Test
        @DisplayName("Reset")
        void resetTest() {
            FStream3D fStream = factory.getFStream3D(100);

            fStream.add(1.5, 2.5, 3.5, 1.1);
            fStream.add(4.5, 5.5, 6.5, 2.2);
            fStream.add(7.5, 8.5, 9.5, 3.3);

            fStream.reset();

            fStream.add(7.5, 8.5, 9.5, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fStream.getNumberOfElements(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(7.5, fStream.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(8.5, fStream.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(9.5, fStream.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(3.3, fStream.getValue(0),
                            "The value (0) is erroneous")
            );

            assertThrows(IndexOutOfBoundsException.class, () -> fStream.getD0(1),
                    "The index is out of bounds (positive), an exception should be thrown");
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core")
    class FStream3DCoreTest {

        @Test
        @DisplayName("Equals")
        void equalsTest() {
            FStream3D fStream1 = factory.getFStream3D(10);
            FStream3D fStream2 = factory.getFStream3D(15);

            fStream1.add(4.5, 5.5, 6.5, 2.2);
            fStream1.add(7.5, 8.5, 9.5, 3.3);

            fStream2.add(3.5, 2.5, 1.5, 9.9);
            fStream2.add(6.5, 5.5, 4.5, 8.8);
            fStream2.add(9.5, 8.5, 7.5, 7.7);

            fStream2.reset();

            fStream2.add(4.5, 5.5, 6.5, 2.2);
            fStream2.add(7.5, 8.5, 9.5, 3.3);

            Assertions.assertAll("Check equality",
                    () -> assertEquals(fStream1, fStream2,
                            "Streams should be equal"),
                    () -> assertEquals(fStream2, fStream1,
                            "Streams should be equal"),
                    () -> assertEquals(fStream1.hashCode(), fStream2.hashCode(),
                            "Hash codes should be the same")
            );
        }

        @Test
        @DisplayName("Equals (fail)")
        void equalsFailTest() {
            FStream3D fStream1 = factory.getFStream3D(10);
            FStream3D fStream2 = factory.getFStream3D(15);

            fStream1.add(4.5, 5.5, 6.5, 2.2);
            fStream1.add(7.5, 8.5, 9.5, 3.3);

            fStream2.add(3.5, 2.5, 1.5, 9.9);
            fStream2.add(6.5, 5.5, 4.5, 8.8);
            fStream2.add(9.5, 8.5, 7.5, 7.7);

            Assertions.assertAll("Check equality",
                    () -> assertNotEquals(fStream1, fStream2,
                            "Streams should not be equal"),
                    () -> assertNotEquals(fStream2, fStream1,
                            "Streams should not be equal")
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FStream3DIAdvancedTest {

        @Test
        @DisplayName("JSON")
        void parseJSONTest() {
            FStream3D dtoOrigin = factory.getFStream3D(10);

            dtoOrigin.add(3.5, 2.5, 1.5, 9.9);
            dtoOrigin.add(6.5, 5.5, 4.5, 8.8);
            dtoOrigin.add(9.5, 8.5, 7.5, 7.7);

            dtoOrigin.reset();

            dtoOrigin.add(4.5, 5.5, 6.5, 2.2);
            dtoOrigin.add(7.5, 8.5, 9.5, 3.3);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FStream3D dtoCopy = factory.getFStream3D(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("Iteration")
        void iterationTest() {
            FStream3D fStream = factory.getFStream3D(100);

            fStream.add(1.5, 2.5, 3.5, 1.1);
            fStream.add(4.5, 5.5, 6.5, 2.2);
            fStream.add(7.5, 8.5, 9.5, 3.3);

            double[] sum = new double[fStream.getNumberOfElements()];

            fStream.iterate((index, d0, d1, d2, value) -> sum[index] = d0 + d1 + d2 + value);

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
