package eu.scattering.core.test.statistics;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FPlot2D")
public class FPlot2DTest {

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
    @Tag("Core")
    @DisplayName("Core")
    class FPlot2DCoreTest {

        @Test
        @DisplayName("Is equal")
        void isEqual() {
            FPlot2D fPlotA = factory.getFPlot2D();

            fPlotA.getInterpolator().setMethod(FPlot2DInterpolator.Method.CUBIC);
            fPlotA.getInterpolator().setHermiteTension(2);
            fPlotA.getInterpolator().setHermiteBias(3);

            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            FPlot2D fPlotB = factory.getFPlot2D();

            fPlotB.getInterpolator().setMethod(FPlot2DInterpolator.Method.CUBIC);
            fPlotB.getInterpolator().setHermiteTension(2);
            fPlotB.getInterpolator().setHermiteBias(3);

            fPlotB.add(0, 0);
            fPlotB.add(1, 1);
            fPlotB.add(2, 2);

            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
            assertTrue(fPlotA.isEqualData(fPlotB));
            assertTrue(fPlotB.isEqualData(fPlotA));

            fPlotA.getInterpolator().setHermiteTension(3);

            assertFalse(fPlotA.isEqual(fPlotB));
            assertFalse(fPlotB.isEqual(fPlotA));
            assertTrue(fPlotA.isEqualData(fPlotB));
            assertTrue(fPlotB.isEqualData(fPlotA));

            fPlotA.getInterpolator().setHermiteTension(2);
            fPlotA.add(10, 10);

            assertFalse(fPlotA.isEqual(fPlotB));
            assertFalse(fPlotB.isEqual(fPlotA));
            assertFalse(fPlotA.isEqualData(fPlotB));
            assertFalse(fPlotB.isEqualData(fPlotA));
        }

        @Test
        @DisplayName("To JSON")
        void toJSON() {
            FPlot2D fPlotA = factory.getFPlot2D();

            fPlotA.getInterpolator().setMethod(FPlot2DInterpolator.Method.CUBIC);
            fPlotA.getInterpolator().setHermiteTension(2);
            fPlotA.getInterpolator().setHermiteBias(3);

            fPlotA.add(-2, 2);
            fPlotA.add(-1, 1);
            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            JSONObject json = fPlotA.toJSON();

            FPlot2D fPlotB = factory.getFPlot2D(json);

            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
        }

        @Test
        @DisplayName("Configure")
        void configure() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.getInterpolator().setHermiteBias(2);

            assertEquals(2, fPlot.getInterpolator().getHermiteBias());

            fPlot.getInterpolator().setHermiteTension(3);

            assertEquals(3, fPlot.getInterpolator().getHermiteTension());

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.COSINE);

            assertEquals(FPlot2DInterpolator.Method.COSINE, fPlot.getInterpolator().getMethod());
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FPlot2D fPlotA = factory.getFPlot2D();

            fPlotA.getInterpolator().setMethod(FPlot2DInterpolator.Method.CUBIC);
            fPlotA.getInterpolator().setHermiteTension(2);
            fPlotA.getInterpolator().setHermiteBias(3);

            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            FPlot2D fPlotB = fPlotA.copy();

            assertNotSame(fPlotA, fPlotB);
            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPlot2DAdvancedTest {

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
                    () -> assertEquals(4, fPlot.getIndex(1.8)),
                    () -> assertEquals(1, fPlot.getIndex(-1.1)),
                    () -> assertEquals(4, fPlot.getIndex(100)),
                    () -> assertEquals(0, fPlot.getIndex(-100))
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
                    () -> assertEquals(3, fPlot.getIndex(1.8)),
                    () -> assertEquals(4, fPlot.getIndex(-1.1)),
                    () -> assertEquals(3, fPlot.getIndex(100)),
                    () -> assertEquals(2, fPlot.getIndex(-100))
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
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -1);
            fPlot.add(-1, 5);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(-2, fPlot.minX()),
                    () -> assertEquals(2, fPlot.maxX()),
                    () -> assertEquals(-1, fPlot.minY()),
                    () -> assertEquals(5, fPlot.maxY())
            );
        }

        @Test
        @DisplayName("Integrate - A")
        void integrateA() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 0);
            fPlot.add(2, 5);

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.LINEAR);
            fPlot.interpolate(100);

            double area = 10;
            double results = fPlot.integrate();

            assertEquals(area, results, 1E-4);
        }

        @Test
        @DisplayName("Integrate - B")
        void integrateB() {
            FPlot2D fPlot = factory.getFPlot2D();

            double step = Math.PI / 10;
            double x = -Math.PI;
            while (x <= Math.PI) {
                fPlot.add(x, Math.sin(x));
                x += step;
            }

            fPlot.interpolate(100);

            double area = 4;
            double results = fPlot.integrate();

            assertEquals(area, results, 1E-1);
        }

        @Test
        @DisplayName("Approximate linear")
        void approxLinear() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.LINEAR);

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

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.COSINE);

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

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.CUBIC);

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

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.CATMULL_ROM);

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

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.HERMITE);

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
        @DisplayName("Regression - Simple linear")
        void regressionSimpleLinear() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPos2D parameters = fPlot.simpleLinearRegression();

            assertEquals(-1.12, parameters.getD0(), 1E-4);
            assertEquals(-0.12, parameters.getD1(), 1E-4);

            assertEquals(2.12, fPlot.getY(0), 1E-4);
            assertEquals(1, fPlot.getY(1), 1E-4);
            assertEquals(-0.12, fPlot.getY(2), 1E-4);
            assertEquals(-1.24, fPlot.getY(3), 1E-4);
            assertEquals(-2.36, fPlot.getY(4), 1E-4);
        }

        @Test
        @DisplayName("Absolute")
        void absolute() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            fPlot.absolute();

            assertEquals(2, fPlot.getY(0), 1E-4);
            assertEquals(1, fPlot.getY(1), 1E-4);
            assertEquals(0, fPlot.getY(2), 1E-4);
            assertEquals(1, fPlot.getY(3), 1E-4);
            assertEquals(2, fPlot.getY(4), 1E-4);
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
        @DisplayName("Mutate with polynomial - A")
        void mutateWithPolynomialA() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 1);
            fPlot.add(2, 1);
            fPlot.add(4, 1);
            fPlot.add(5, 1);

            fPlot.mutateYWithPolynomial((x, p) -> x - p, 1, 1);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(2, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getX(2)),
                    () -> assertEquals(5, fPlot.getX(3)),
                    () -> assertEquals(-1, fPlot.getY(0)),
                    () -> assertEquals(-2, fPlot.getY(1)),
                    () -> assertEquals(-4, fPlot.getY(2)),
                    () -> assertEquals(-5, fPlot.getY(3))
            );
        }

        @Test
        @DisplayName("Mutate with polynomial - B")
        void mutateWithPolynomialB() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -2);
            fPlot.add(2, -1);
            fPlot.add(4, 1);
            fPlot.add(5, 2);

            fPlot.mutateYWithPolynomial((x, p) -> x - p, 1);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(2, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getX(2)),
                    () -> assertEquals(5, fPlot.getX(3)),
                    () -> assertEquals(-3, fPlot.getY(0)),
                    () -> assertEquals(-2, fPlot.getY(1)),
                    () -> assertEquals(0, fPlot.getY(2)),
                    () -> assertEquals(1, fPlot.getY(3))
            );
        }

        @Test
        @DisplayName("Interpolate")
        void interpolate() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.HERMITE);

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

            fPlot.getInterpolator().setMethod(FPlot2DInterpolator.Method.HERMITE);

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
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            fPlot.sortX(true);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(-1, fPlot.getY(0)),
                    () -> assertEquals(2, fPlot.getX(1)),
                    () -> assertEquals(-2, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getX(2)),
                    () -> assertEquals(-3, fPlot.getY(2)),
                    () -> assertEquals(4, fPlot.getX(3)),
                    () -> assertEquals(-4, fPlot.getY(3)),
                    () -> assertEquals(5, fPlot.getX(4)),
                    () -> assertEquals(-5, fPlot.getY(4))
            );
        }

        @Test
        @DisplayName("Sort dsc X")
        void sortDscX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            fPlot.sortX(false);

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, fPlot.getX(0)),
                    () -> assertEquals(-5, fPlot.getY(0)),
                    () -> assertEquals(4, fPlot.getX(1)),
                    () -> assertEquals(-4, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getX(2)),
                    () -> assertEquals(-3, fPlot.getY(2)),
                    () -> assertEquals(2, fPlot.getX(3)),
                    () -> assertEquals(-2, fPlot.getY(3)),
                    () -> assertEquals(1, fPlot.getX(4)),
                    () -> assertEquals(-1, fPlot.getY(4))
            );
        }

        @Test
        @DisplayName("Sort asc Y")
        void sortAscY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            fPlot.sortY(true);

            Assertions.assertAll("Check values",
                    () -> assertEquals(5, fPlot.getX(0)),
                    () -> assertEquals(-5, fPlot.getY(0)),
                    () -> assertEquals(4, fPlot.getX(1)),
                    () -> assertEquals(-4, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getX(2)),
                    () -> assertEquals(-3, fPlot.getY(2)),
                    () -> assertEquals(2, fPlot.getX(3)),
                    () -> assertEquals(-2, fPlot.getY(3)),
                    () -> assertEquals(1, fPlot.getX(4)),
                    () -> assertEquals(-1, fPlot.getY(4))
            );
        }

        @Test
        @DisplayName("Sort dsc Y")
        void sortDsc() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            fPlot.sortY(false);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(-1, fPlot.getY(0)),
                    () -> assertEquals(2, fPlot.getX(1)),
                    () -> assertEquals(-2, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getX(2)),
                    () -> assertEquals(-3, fPlot.getY(2)),
                    () -> assertEquals(4, fPlot.getX(3)),
                    () -> assertEquals(-4, fPlot.getY(3)),
                    () -> assertEquals(5, fPlot.getX(4)),
                    () -> assertEquals(-5, fPlot.getY(4))
            );
        }

        @Test
        @DisplayName("For each")
        void forEach() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            AtomicInteger sumX = new AtomicInteger();
            AtomicInteger sumY = new AtomicInteger();

            fPlot.forEach((x, y) -> {
                sumX.addAndGet((int) Math.round(x));
                sumY.addAndGet((int) Math.round(y));
            });

            assertEquals(15, sumX.get());
            assertEquals(-15, sumY.get());
        }
    }

    @Nested
    @Tag("Meta")
    @DisplayName("Meta")
    class FPlot2DMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.setName("test");

            assertEquals("test", fPlot.getName());
        }
    }
}

//  https://www.statskingdom.com/linear-regression-calculator.html
