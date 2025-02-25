package eu.scattering.core.transfer.containers.buffer;

import eu.scattering.core.transfer.container.ContainerFactory;
import eu.scattering.core.transfer.container.ContainerFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Timeout(1)
@DisplayName("FStream3DI")
public class FStream3DITest {
    private final ContainerFactory factory = ContainerFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FStream3DIBasicTest {

        @Test
        @DisplayName("Creation")
        void creationTest() {
            FStream3DI fStream = factory.getFStream3DI(100);

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fStream.getNumberOfElements(),
                            "The number of elements is incorrect")
            );
        }

        @Test
        @DisplayName("Incrementation")
        void incTest() {
            FStream3DI fStream = factory.getFStream3DI(100);

            fStream.add(1, 2, 3);
            fStream.add(4, 5, 6);
            fStream.add(7, 8, 9);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fStream.getNumberOfElements(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1, fStream.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2, fStream.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3, fStream.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4, fStream.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5, fStream.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6, fStream.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7, fStream.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8, fStream.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9, fStream.getD2(2),
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
            FStream3DI fStream = factory.getFStream3DI(100);

            fStream.add(1, 2, 3, 1.1);
            fStream.add(4, 5, 6, 2.2);
            fStream.add(7, 8, 9, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fStream.getNumberOfElements(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(1, fStream.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(2, fStream.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(3, fStream.getD2(0),
                            "The D2 (0) is erroneous"),
                    () -> assertEquals(4, fStream.getD0(1),
                            "The D0 (1) is erroneous"),
                    () -> assertEquals(5, fStream.getD1(1),
                            "The D1 (1) is erroneous"),
                    () -> assertEquals(6, fStream.getD2(1),
                            "The D2 (2) is erroneous"),
                    () -> assertEquals(7, fStream.getD0(2),
                            "The D0 (3) is erroneous"),
                    () -> assertEquals(8, fStream.getD1(2),
                            "The D1 (3) is erroneous"),
                    () -> assertEquals(9, fStream.getD2(2),
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
            FStream3DI fStream = factory.getFStream3DI(3);

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
            FStream3DI fStream = factory.getFStream3DI(100);

            fStream.add(1, 2, 3, 1.1);
            fStream.add(4, 5, 6, 2.2);
            fStream.add(7, 8, 9, 3.3);

            fStream.reset();

            fStream.add(7, 8, 9, 3.3);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fStream.getNumberOfElements(),
                            "The number of elements is incorrect"),
                    () -> assertEquals(7, fStream.getD0(0),
                            "The D0 (0) is erroneous"),
                    () -> assertEquals(8, fStream.getD1(0),
                            "The D1 (0) is erroneous"),
                    () -> assertEquals(9, fStream.getD2(0),
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
    class FStream3DICoreTest {

        @Test
        @DisplayName("Equals")
        void equalsTest() {
            FStream3DI fStream1 = factory.getFStream3DI(10);
            FStream3DI fStream2 = factory.getFStream3DI(15);

            fStream1.add(4, 5, 6, 2.2);
            fStream1.add(7, 8, 9, 3.3);

            fStream2.add(3, 2, 1, 9.9);
            fStream2.add(6, 5, 4, 8.8);
            fStream2.add(9, 8, 7, 7.7);

            fStream2.reset();

            fStream2.add(4, 5, 6, 2.2);
            fStream2.add(7, 8, 9, 3.3);

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
            FStream3DI fStream1 = factory.getFStream3DI(10);
            FStream3DI fStream2 = factory.getFStream3DI(15);

            fStream1.add(4, 5, 6, 2.2);
            fStream1.add(7, 8, 9, 3.3);

            fStream2.add(3, 2, 1, 9.9);
            fStream2.add(6, 5, 4, 8.8);
            fStream2.add(9, 8, 7, 7.7);

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
            FStream3DI dtoOrigin = factory.getFStream3DI(10);

            dtoOrigin.add(3, 2, 1, 9.9);
            dtoOrigin.add(6, 5, 4, 8.8);
            dtoOrigin.add(9, 8, 7, 7.7);

            dtoOrigin.reset();

            dtoOrigin.add(4, 5, 6, 2.2);
            dtoOrigin.add(7, 8, 9, 3.3);

            JSONObject jsonOrigin = dtoOrigin.toJSON();

            FStream3DI dtoCopy = factory.getFStream3DI(jsonOrigin);

            assertEquals(dtoOrigin, dtoCopy, "The parsed JSON object is erroneous");
        }

        @Test
        @DisplayName("Iteration")
        void iterationTest() {
            FStream3DI fStream = factory.getFStream3DI(100);

            fStream.add(1, 2, 3, 1.1);
            fStream.add(4, 5, 6, 2.2);
            fStream.add(7, 8, 9, 3.3);

            double[] sum = new double[fStream.getNumberOfElements()];

            fStream.iterate((index, d0, d1, d2, value) -> {
                sum[index] = d0 + d1 + d2 + value;
            });

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
