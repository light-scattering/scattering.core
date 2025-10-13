package eu.scattering.core.transfer.container.statistics;

import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2D;
import eu.scattering.core.transfer.statistics.FStat1D.FStat1D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FPlot2D")
public class FPlot2DTest {
    private final TransferFactory factory = TransferFactoryConcrete.create();

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPlot2DDBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FPlot2D fPlot = factory.getFPlot2D();

            assertEquals(0, fPlot.size());
        }

        @Test
        @DisplayName("Export to array")
        void exportToArray() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            double[][] results = fPlot.toArray();

            Assertions.assertAll("Validate results",
                    () -> assertEquals(results[0].length, fPlot.size()),
                    () -> assertEquals(results[0][0], fPlot.getX(0)),
                    () -> assertEquals(results[0][1], fPlot.getX(1)),
                    () -> assertEquals(results[0][2], fPlot.getX(2)),
                    () -> assertEquals(results[1][0], fPlot.getY(0)),
                    () -> assertEquals(results[1][1], fPlot.getY(1)),
                    () -> assertEquals(results[1][2], fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FPlot2D fPlot = factory.getFPlot2D();

            assertEquals(0, fPlot.size());

            fPlot.add(0);
            fPlot.add(1);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fPlot.getX(0)),
                    () -> assertEquals(0, fPlot.getY(0)),
                    () -> assertEquals(1, fPlot.getX(1)),
                    () -> assertEquals(0, fPlot.getY(1))
            );

            Assertions.assertAll("Check exceptions",
                    () -> assertThrows(IllegalStateException.class, () -> fPlot.add(1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(2)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Add XY")
        void addXY() {
            FPlot2D fPlot = factory.getFPlot2D();

            assertEquals(0, fPlot.size());

            fPlot.add(1, 3);
            fPlot.add(0, 2);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fPlot.getX(1)),
                    () -> assertEquals(2, fPlot.getY(1)),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0))
            );

            Assertions.assertAll("Check exceptions",
                    () -> assertThrows(IllegalStateException.class, () -> fPlot.add(1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(2)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Add X with collision")
        void addXWithCollision() {
            FPlot2D fPlot = factory.getFPlot2D();

            assertEquals(0, fPlot.size());

            fPlot.add(1, 3);
            fPlot.add(0, 6);

            fPlot.add((y1, y2) -> (y1 + y2) / 2, 0);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fPlot.getX(1)),
                    () -> assertEquals(3, fPlot.getY(1)),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0))
            );

            Assertions.assertAll("Check exceptions",
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(2)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Add XY with collision")
        void addXYWithCollision() {
            FPlot2D fPlot = factory.getFPlot2D();

            assertEquals(0, fPlot.size());

            fPlot.add(1, 3);
            fPlot.add(0, 2);

            fPlot.add((y1, y2) -> (y1 + y2) / 2, 0, 6);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(0, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getY(1)),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0))
            );

            Assertions.assertAll("Check exceptions",
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getX(2)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(-1)),
                    () -> assertThrows(IndexOutOfBoundsException.class, () -> fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Clear")
        void clear() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(0);
            fPlot.add(1);

            fPlot.clear();

            assertEquals(0, fPlot.size());
        }

        @Test
        @DisplayName("Set X")
        void setX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);

            fPlot.setX(1, 1);

            assertThrows(IndexOutOfBoundsException.class, () -> fPlot.setX(-1, 0));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlot.setX(2, 0));

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(1, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Set Y")
        void setY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);

            fPlot.setY(1, 1);

            assertThrows(IndexOutOfBoundsException.class, () -> fPlot.setY(-1, 0));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlot.setY(2, 0));

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(1, fPlot.getY(1))
            );
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FStat1DAdvancedTest {

        @Test
        @DisplayName("Get stat X")
        void getStatX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStatRef = factory.getFStat1D();

            fStatRef.add(1, 3, 5);

            FStat1D fStat = fPlot.getStatX();

            assertTrue(fStatRef.isEqual(fStat));
        }

        @Test
        @DisplayName("Set stat X")
        void setStatX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 3, 5, 7);

            assertThrows(IllegalArgumentException.class, () -> fPlot.setStatX(fStat));

            fStat.clear();
            fStat.add(1, 1, 5);

            assertThrows(IllegalArgumentException.class, () -> fPlot.setStatX(fStat));

            fStat.clear();
            fStat.add(-1, -3, -5);

            fPlot.setStatX(fStat);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fPlot.size()),
                    () -> assertEquals(-1, fPlot.getX(0)),
                    () -> assertEquals(-3, fPlot.getX(1)),
                    () -> assertEquals(-5, fPlot.getX(2)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(4, fPlot.getY(1)),
                    () -> assertEquals(6, fPlot.getY(2))
            );

            fStat.clear();
            fStat.add(1, Double.NaN, 5);

            fPlot.setStatX(fStat);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(2, fPlot.size()),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(5, fPlot.getX(1)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Get stat Y")
        void getStatY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStatRef = factory.getFStat1D();

            fStatRef.add(2, 4, 6);

            FStat1D fStat = fPlot.getStatY();

            assertTrue(fStatRef.isEqual(fStat));
        }

        @Test
        @DisplayName("Set stat Y")
        void setStatY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 3, 5, 7);

            assertThrows(IllegalArgumentException.class, () -> fPlot.setStatY(fStat));

            fStat.clear();
            fStat.add(-2, -4, -6);

            fPlot.setStatY(fStat);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fPlot.size()),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(5, fPlot.getX(2)),
                    () -> assertEquals(-2, fPlot.getY(0)),
                    () -> assertEquals(-4, fPlot.getY(1)),
                    () -> assertEquals(-6, fPlot.getY(2))
            );

            fStat.clear();
            fStat.add(2, Double.NaN, 6);

            fPlot.setStatY(fStat);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(2, fPlot.size()),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(5, fPlot.getX(1)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Get index round (sorted)")
        void getIndexRoundSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexRound(1.8)),
                    () -> assertEquals(1, fPlot.getIndexRound(-1.1)),
                    () -> assertEquals(4, fPlot.getIndexRound(100)),
                    () -> assertEquals(0, fPlot.getIndexRound(-100))
            );
        }

        @Test
        @DisplayName("Get index round (random)")
        void getIndexRoundRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -1);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexRound(1.8)),
                    () -> assertEquals(4, fPlot.getIndexRound(-1.1)),
                    () -> assertEquals(3, fPlot.getIndexRound(100)),
                    () -> assertEquals(2, fPlot.getIndexRound(-100))
            );
        }

        @Test
        @DisplayName("Get index floor (sorted)")
        void getIndexFloorSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexFloor(1.8)),
                    () -> assertEquals(0, fPlot.getIndexFloor(-1.1)),
                    () -> assertEquals(4, fPlot.getIndexFloor(100)),
                    () -> assertEquals(-1, fPlot.getIndexFloor(-100))
            );
        }

        @Test
        @DisplayName("Get index floor (random)")
        void getIndexFloorRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -1);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexFloor(1.8)),
                    () -> assertEquals(2, fPlot.getIndexFloor(-1.1)),
                    () -> assertEquals(3, fPlot.getIndexFloor(100)),
                    () -> assertEquals(-1, fPlot.getIndexFloor(-100))
            );
        }

        @Test
        @DisplayName("Get index ceil (sorted)")
        void getIndexCeilSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexCeil(0.8)),
                    () -> assertEquals(1, fPlot.getIndexCeil(-1.1)),
                    () -> assertEquals(-1, fPlot.getIndexCeil(100)),
                    () -> assertEquals(0, fPlot.getIndexCeil(-100))
            );
        }

        @Test
        @DisplayName("Get index ceil (random)")
        void getIndexCeilRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -1);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexCeil(0.8)),
                    () -> assertEquals(4, fPlot.getIndexCeil(-1.1)),
                    () -> assertEquals(-1, fPlot.getIndexCeil(100)),
                    () -> assertEquals(2, fPlot.getIndexCeil(-100))
            );
        }

        @Test
        @DisplayName("Get min/max value")
        void getMinMaxValue() {
            FPlot2D chart = factory.getFPlot2D();

            chart.add(1, -1);
            chart.add(0, 0);
            chart.add(-2, 2);
            chart.add(2, -1);
            chart.add(-1, 5);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(-2, chart.minX()),
                    () -> assertEquals(2, chart.maxX()),
                    () -> assertEquals(-1, chart.minY()),
                    () -> assertEquals(5, chart.maxY())
            );
        }

        @Test
        @DisplayName("Approximate linear")
        void approxLinear() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.LINEAR);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.approximate(-2), 1E-6),
                    () -> assertEquals(1, fPlot.approximate(-1), 1E-6),
                    () -> assertEquals(0, fPlot.approximate(0), 1E-6),
                    () -> assertEquals(-1, fPlot.approximate(1), 1E-6),
                    () -> assertEquals(-2, fPlot.approximate(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertEquals(1.5, fPlot.approximate(-1.5), 1E-6),
                    () -> assertEquals(-1.9, fPlot.approximate(1.9), 1E-6)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(2.5))
            );
        }

        @Test
        @DisplayName("Approximate cosine")
        void approxCosine() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.COSINE);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.approximate(-2), 1E-6),
                    () -> assertEquals(1, fPlot.approximate(-1), 1E-6),
                    () -> assertEquals(0, fPlot.approximate(0), 1E-6),
                    () -> assertEquals(-1, fPlot.approximate(1), 1E-6),
                    () -> assertEquals(-2, fPlot.approximate(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.approximate(-1.5) > 1 && fPlot.approximate(-1.5) < 2),
                    () -> assertTrue(fPlot.approximate(1.9) > -2 && fPlot.approximate(1.9) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(2.5))
            );
        }

        @Test
        @DisplayName("Approximate cubic")
        void approxCubic() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.CUBIC);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.approximate(-2), 1E-6),
                    () -> assertEquals(1, fPlot.approximate(-1), 1E-6),
                    () -> assertEquals(0, fPlot.approximate(0), 1E-6),
                    () -> assertEquals(-1, fPlot.approximate(1), 1E-6),
                    () -> assertEquals(-2, fPlot.approximate(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.approximate(-1.5) > 1 && fPlot.approximate(-1.5) < 2),
                    () -> assertTrue(fPlot.approximate(-0.5) > 0 && fPlot.approximate(-0.5) < 1),
                    () -> assertTrue(fPlot.approximate(0.5) > -1 && fPlot.approximate(0.5) < 0),
                    () -> assertTrue(fPlot.approximate(1.5) > -2 && fPlot.approximate(1.5) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(2.5))
            );
        }

        @Test
        @DisplayName("Approximate Catmull-Rom")
        void approxCatmullRom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.CATMULL_ROM);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.approximate(-2), 1E-6),
                    () -> assertEquals(1, fPlot.approximate(-1), 1E-6),
                    () -> assertEquals(0, fPlot.approximate(0), 1E-6),
                    () -> assertEquals(-1, fPlot.approximate(1), 1E-6),
                    () -> assertEquals(-2, fPlot.approximate(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.approximate(-1.5) > 1 && fPlot.approximate(-1.5) < 2),
                    () -> assertTrue(fPlot.approximate(-0.5) > 0 && fPlot.approximate(-0.5) < 1),
                    () -> assertTrue(fPlot.approximate(0.5) > -1 && fPlot.approximate(0.5) < 0),
                    () -> assertTrue(fPlot.approximate(1.5) > -2 && fPlot.approximate(1.5) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(2.5))
            );
        }

        @Test
        @DisplayName("Approximate Hermite")
        void approxHermite() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.HERMITE);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.approximate(-2), 1E-6),
                    () -> assertEquals(1, fPlot.approximate(-1), 1E-6),
                    () -> assertEquals(0, fPlot.approximate(0), 1E-6),
                    () -> assertEquals(-1, fPlot.approximate(1), 1E-6),
                    () -> assertEquals(-2, fPlot.approximate(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.approximate(-1.5) > 1 && fPlot.approximate(-1.5) < 2),
                    () -> assertTrue(fPlot.approximate(-0.5) > 0 && fPlot.approximate(-0.5) < 1),
                    () -> assertTrue(fPlot.approximate(0.5) > -1 && fPlot.approximate(0.5) < 0),
                    () -> assertTrue(fPlot.approximate(1.5) > -2 && fPlot.approximate(1.5) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.approximate(2.5))
            );
        }

        @Test
        @DisplayName("Filter X")
        void filterX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(2, -2);
            fPlot.add(3, -3);
            fPlot.add(4, -4);
            fPlot.add(5, -5);
            fPlot.add(6, -6);

            int count = fPlot.filter((x, y) -> x > 2 && x < 5);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, count),
                    () -> assertEquals(3, fPlot.getX(0)),
                    () -> assertEquals(-3, fPlot.getY(0)),
                    () -> assertEquals(4, fPlot.getX(1)),
                    () -> assertEquals(-4, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Filter Y")
        void filterY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(2, -2);
            fPlot.add(3, -3);
            fPlot.add(4, -4);
            fPlot.add(5, -5);
            fPlot.add(6, -6);

            int count = fPlot.filter((x, y) -> y < -4);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, count),
                    () -> assertEquals(5, fPlot.getX(0)),
                    () -> assertEquals(-5, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(-6, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Mutate X")
        void mutateX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            fPlot.mutateX((x, y) -> (x * 2) + y);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(7, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0)),
                    () -> assertEquals(10, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Mutate Y")
        void mutateY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            fPlot.mutateY((x, y) -> (x * 2) + y);

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fPlot.getX(0)),
                    () -> assertEquals(7, fPlot.getY(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(10, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Interpolate")
        void interpolate() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.HERMITE);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            fPlot.interpolate(0.1, true);

            Assertions.assertAll("Test values",
                    () -> assertEquals(40, fPlot.size(), 1),
                    () -> assertEquals(-2, fPlot.minX(), 1E-6),
                    () -> assertEquals(2, fPlot.maxX(), 1E-6),
                    () -> assertEquals(1.5, fPlot.approximate(-1.5), 0.25),
                    () -> assertEquals(0.5, fPlot.approximate(-0.5), 0.25),
                    () -> assertEquals(0, fPlot.approximate(0), 0.25),
                    () -> assertEquals(0.5, fPlot.approximate(0.5), 0.25),
                    () -> assertEquals(1.5, fPlot.approximate(1.5), 0.25)
            );
        }

        @Test
        @DisplayName("Interpolate with divisions")
        void interpolateWithDivisions() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setApproxMethod(FPlot2D.Approx.HERMITE);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            fPlot.interpolate(10);

            Assertions.assertAll("Test values",
                    () -> assertEquals(11, fPlot.size())
            );
        }

        @Test
        @DisplayName("Sort asc X")
        void sortAscX() {
            FPlot2D chart = factory.getFPlot2D();

            chart.add(2, -2);
            chart.add(5, -5);
            chart.add(1, -1);
            chart.add(4, -4);
            chart.add(3, -3);

            chart.sortX(true);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, chart.getX(0)),
                    () -> assertEquals(-1, chart.getY(0)),
                    () -> assertEquals(2, chart.getX(1)),
                    () -> assertEquals(-2, chart.getY(1)),
                    () -> assertEquals(3, chart.getX(2)),
                    () -> assertEquals(-3, chart.getY(2)),
                    () -> assertEquals(4, chart.getX(3)),
                    () -> assertEquals(-4, chart.getY(3)),
                    () -> assertEquals(5, chart.getX(4)),
                    () -> assertEquals(-5, chart.getY(4))
            );
        }

        @Test
        @DisplayName("Sort dsc X")
        void sortDscX() {
            FPlot2D chart = factory.getFPlot2D();

            chart.add(2, -2);
            chart.add(5, -5);
            chart.add(1, -1);
            chart.add(4, -4);
            chart.add(3, -3);

            chart.sortX(false);

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, chart.getX(0)),
                    () -> assertEquals(-5, chart.getY(0)),
                    () -> assertEquals(4, chart.getX(1)),
                    () -> assertEquals(-4, chart.getY(1)),
                    () -> assertEquals(3, chart.getX(2)),
                    () -> assertEquals(-3, chart.getY(2)),
                    () -> assertEquals(2, chart.getX(3)),
                    () -> assertEquals(-2, chart.getY(3)),
                    () -> assertEquals(1, chart.getX(4)),
                    () -> assertEquals(-1, chart.getY(4))
            );
        }

        @Test
        @DisplayName("Sort asc Y")
        void sortAscY() {
            FPlot2D chart = factory.getFPlot2D();

            chart.add(2, -2);
            chart.add(5, -5);
            chart.add(1, -1);
            chart.add(4, -4);
            chart.add(3, -3);

            chart.sortY(true);

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, chart.getX(0)),
                    () -> assertEquals(-5, chart.getY(0)),
                    () -> assertEquals(4, chart.getX(1)),
                    () -> assertEquals(-4, chart.getY(1)),
                    () -> assertEquals(3, chart.getX(2)),
                    () -> assertEquals(-3, chart.getY(2)),
                    () -> assertEquals(2, chart.getX(3)),
                    () -> assertEquals(-2, chart.getY(3)),
                    () -> assertEquals(1, chart.getX(4)),
                    () -> assertEquals(-1, chart.getY(4))
            );
        }

        @Test
        @DisplayName("Sort dsc Y")
        void sortDsc() {
            FPlot2D chart = factory.getFPlot2D();

            chart.add(2, -2);
            chart.add(5, -5);
            chart.add(1, -1);
            chart.add(4, -4);
            chart.add(3, -3);

            chart.sortY(false);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, chart.getX(0)),
                    () -> assertEquals(-1, chart.getY(0)),
                    () -> assertEquals(2, chart.getX(1)),
                    () -> assertEquals(-2, chart.getY(1)),
                    () -> assertEquals(3, chart.getX(2)),
                    () -> assertEquals(-3, chart.getY(2)),
                    () -> assertEquals(4, chart.getX(3)),
                    () -> assertEquals(-4, chart.getY(3)),
                    () -> assertEquals(5, chart.getX(4)),
                    () -> assertEquals(-5, chart.getY(4))
            );
        }
    }















    @Test
    @DisplayName("Configure")
    void configure() {
        FPlot2D chart = factory.getFPlot2D();

        chart.setApproxHermiteBias(2);

        assertEquals(2, chart.getApproxHermiteBias());

        chart.setApproxHermiteTension(3);

        assertEquals(3, chart.getApproxHermiteTension());

        chart.setApproxMethod(FPlot2D.Approx.COSINE);

        assertEquals(FPlot2D.Approx.COSINE, chart.getApproxMethod());
    }

    @Test
    @DisplayName("Compare")
    void compare() {
        FPlot2D chartA = factory.getFPlot2D();

        chartA.setApproxMethod(FPlot2D.Approx.CUBIC);
        chartA.setApproxHermiteTension(2);
        chartA.setApproxHermiteBias(3);

        chartA.add(0, 0);
        chartA.add(1, 1);
        chartA.add(2, 2);

        FPlot2D chartB = factory.getFPlot2D();

        chartB.setApproxMethod(FPlot2D.Approx.CUBIC);
        chartB.setApproxHermiteTension(2);
        chartB.setApproxHermiteBias(3);

        chartB.add(0, 0);
        chartB.add(1, 1);
        chartB.add(2, 2);

        assertTrue(chartA.isEqual(chartB));
        assertTrue(chartB.isEqual(chartA));
        assertTrue(chartA.isEqualData(chartB));
        assertTrue(chartB.isEqualData(chartA));

        chartA.setApproxHermiteTension(3);

        assertFalse(chartA.isEqual(chartB));
        assertFalse(chartB.isEqual(chartA));
        assertTrue(chartA.isEqualData(chartB));
        assertTrue(chartB.isEqualData(chartA));

        chartA.setApproxHermiteTension(2);
        chartA.add(10, 10);

        assertFalse(chartA.isEqual(chartB));
        assertFalse(chartB.isEqual(chartA));
        assertFalse(chartA.isEqualData(chartB));
        assertFalse(chartB.isEqualData(chartA));
    }

    @Test
    @DisplayName("Copy")
    void copy() {
        FPlot2D chartA = factory.getFPlot2D();

        chartA.setApproxMethod(FPlot2D.Approx.CUBIC);
        chartA.setApproxHermiteTension(2);
        chartA.setApproxHermiteBias(3);

        chartA.add(0, 0);
        chartA.add(1, 1);
        chartA.add(2, 2);

        FPlot2D chartB = chartA.copy();

        assertNotSame(chartA, chartB);
        assertTrue(chartA.isEqual(chartB));
        assertTrue(chartB.isEqual(chartA));
    }

    @Test
    @DisplayName("To JSON")
    void toJSON() {
        FPlot2D chartA = factory.getFPlot2D();

        chartA.setApproxMethod(FPlot2D.Approx.CUBIC);
        chartA.setApproxHermiteTension(2);
        chartA.setApproxHermiteBias(3);

        chartA.add(-2, 2);
        chartA.add(-1, 1);
        chartA.add(0, 0);
        chartA.add(1, 1);
        chartA.add(2, 2);

        JSONObject json = chartA.toJSON();

        FPlot2D chartB = factory.getFPlot2D(json);

        assertTrue(chartA.isEqual(chartB));
        assertTrue(chartB.isEqual(chartA));
    }
}
