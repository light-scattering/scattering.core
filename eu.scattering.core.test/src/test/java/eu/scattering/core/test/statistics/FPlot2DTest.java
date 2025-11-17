package eu.scattering.core.test.statistics;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.transfer.primitive.FPoly;
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
        @DisplayName("Create with FLayer")
        void createWithFLayer() {
            FLayer fLayer = factory.getFLayer();

            fLayer.incGroup(1, 5);
            fLayer.setGroup(2, 4, 3);

            FPlot2D fPlot = factory.getFPlot2D(fLayer);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(6, fPlot.size()),
                    () -> assertEquals(0, fPlot.getX(0)),
                    () -> assertEquals(1, fPlot.getX(1)),
                    () -> assertEquals(2, fPlot.getX(2)),
                    () -> assertEquals(3, fPlot.getX(3)),
                    () -> assertEquals(4, fPlot.getX(4)),
                    () -> assertEquals(5, fPlot.getX(5)),
                    () -> assertEquals(0, fPlot.getY(0)),
                    () -> assertEquals(1, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getY(2)),
                    () -> assertEquals(3, fPlot.getY(3)),
                    () -> assertEquals(3, fPlot.getY(4)),
                    () -> assertEquals(1, fPlot.getY(5))
            );
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

        @Test
        @DisplayName("Set Y with polynomial")
        void setYWithPolynomial() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            fPlot.setY(factory.getFPoly(1, 2));

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fPlot.getX(0)),
                    () -> assertEquals(4, fPlot.getY(0)),
                    () -> assertEquals(5, fPlot.getX(1)),
                    () -> assertEquals(7, fPlot.getY(1)),
                    () -> assertEquals(1, fPlot.getX(2)),
                    () -> assertEquals(3, fPlot.getY(2)),
                    () -> assertEquals(4, fPlot.getX(3)),
                    () -> assertEquals(6, fPlot.getY(3)),
                    () -> assertEquals(3, fPlot.getX(4)),
                    () -> assertEquals(5, fPlot.getY(4))
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
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPlot2DAdvancedTest {

        @Test
        @DisplayName("Get with FStat1D")
        void getWithFStat1D() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            assertEquals(7d, fPlot.getWithFStat((x, y) -> x.max() + y.min()));
            assertEquals(2d, fPlot.getWithFStat((x, y) -> {
                x.add(1);
                y.add(1);
                return x.min() + y.min();
            }));

            assertThrows(IllegalStateException.class, () -> fPlot.getWithFStat((x, y) -> {
                x.add(1);
                return x.min() + y.min();
            }));
        }

        @Test
        @DisplayName("Get with FStat1D X")
        void getWithFStat1DX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            assertEquals(1d, fPlot.getWithFStatX(FStat1D::min));
            assertEquals(5d, fPlot.getWithFStatX(FStat1D::max));

            assertThrows(IllegalStateException.class, () -> fPlot.getWithFStatX((data) -> {
                data.add(1);
                return data.min();
            }));
        }

        @Test
        @DisplayName("Get with FStat1D Y")
        void getWithFStat1DY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            assertEquals(2d, fPlot.getWithFStatY(FStat1D::min));
            assertEquals(6d, fPlot.getWithFStatY(FStat1D::max));

            assertThrows(IllegalStateException.class, () -> fPlot.getWithFStatY((data) -> {
                data.add(1);
                return data.min();
            }));
        }

        @Test
        @DisplayName("Get ref FStat1D X")
        void getRefFStat1DX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStatRef = factory.getFStat1D();

            fStatRef.add(1, 3, 5);

            FStat1D fStat = fPlot.getRefFStatX();

            assertTrue(fStatRef.isEqual(fStat));
        }

        @Test
        @DisplayName("Set ref FStat1D X")
        void setRefFStat1DX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStat = factory.getFStat1D();

            fStat.add(-1, -3, -5);

            fPlot.setRefFStatX(fStat);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fPlot.size()),
                    () -> assertEquals(-1, fPlot.getX(0)),
                    () -> assertEquals(-3, fPlot.getX(1)),
                    () -> assertEquals(-5, fPlot.getX(2)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(4, fPlot.getY(1)),
                    () -> assertEquals(6, fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Get ref FStat1D Y")
        void getRefFStat1DY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStatRef = factory.getFStat1D();

            fStatRef.add(2, 4, 6);

            FStat1D fStat = fPlot.getRefFStatY();

            assertTrue(fStatRef.isEqual(fStat));
        }

        @Test
        @DisplayName("Set ref FStat1D Y")
        void setRefFStat1DY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat1D fStat = factory.getFStat1D();

            fStat.add(-2, -4, -6);

            fPlot.setRefFStatY(fStat);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fPlot.size()),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(5, fPlot.getX(2)),
                    () -> assertEquals(-2, fPlot.getY(0)),
                    () -> assertEquals(-4, fPlot.getY(1)),
                    () -> assertEquals(-6, fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Get index  X round (sorted)")
        void getIndexXRoundSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexX(FPlot2D.Index.ROUND, 1.8)),
                    () -> assertEquals(1, fPlot.getIndexX(FPlot2D.Index.ROUND, -1.1)),
                    () -> assertEquals(4, fPlot.getIndexX(FPlot2D.Index.ROUND, 100)),
                    () -> assertEquals(0, fPlot.getIndexX(FPlot2D.Index.ROUND, -100))
            );
        }

        @Test
        @DisplayName("Get index X round (random)")
        void getIndexXRoundRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexX(FPlot2D.Index.ROUND, 1.8)),
                    () -> assertEquals(4, fPlot.getIndexX(FPlot2D.Index.ROUND, -1.1)),
                    () -> assertEquals(3, fPlot.getIndexX(FPlot2D.Index.ROUND, 100)),
                    () -> assertEquals(2, fPlot.getIndexX(FPlot2D.Index.ROUND, -100))
            );
        }

        @Test
        @DisplayName("Get index X floor (sorted)")
        void getIndexXFloorSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexX(FPlot2D.Index.FLOOR, 1.8)),
                    () -> assertEquals(0, fPlot.getIndexX(FPlot2D.Index.FLOOR, -1.1)),
                    () -> assertEquals(4, fPlot.getIndexX(FPlot2D.Index.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexX(FPlot2D.Index.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index X floor (random)")
        void getIndexXFloorRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexX(FPlot2D.Index.FLOOR, 1.8)),
                    () -> assertEquals(2, fPlot.getIndexX(FPlot2D.Index.FLOOR, -1.1)),
                    () -> assertEquals(3, fPlot.getIndexX(FPlot2D.Index.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexX(FPlot2D.Index.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index X ceil (sorted)")
        void getIndexXCeilSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexX(FPlot2D.Index.CEIL, 0.8)),
                    () -> assertEquals(1, fPlot.getIndexX(FPlot2D.Index.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexX(FPlot2D.Index.CEIL, 100)),
                    () -> assertEquals(0, fPlot.getIndexX(FPlot2D.Index.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index X ceil (random)")
        void getIndexXCeilRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexX(FPlot2D.Index.CEIL, 0.8)),
                    () -> assertEquals(4, fPlot.getIndexX(FPlot2D.Index.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexX(FPlot2D.Index.CEIL, 100)),
                    () -> assertEquals(2, fPlot.getIndexX(FPlot2D.Index.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index  Y round (sorted)")
        void getIndexYRoundSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexY(FPlot2D.Index.ROUND, 1.8)),
                    () -> assertEquals(3, fPlot.getIndexY(FPlot2D.Index.ROUND, -1.1)),
                    () -> assertEquals(0, fPlot.getIndexY(FPlot2D.Index.ROUND, 100)),
                    () -> assertEquals(4, fPlot.getIndexY(FPlot2D.Index.ROUND, -100))
            );
        }

        @Test
        @DisplayName("Get index Y round (random)")
        void getIndexXYRoundRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(2, fPlot.getIndexY(FPlot2D.Index.ROUND, 1.8)),
                    () -> assertEquals(0, fPlot.getIndexY(FPlot2D.Index.ROUND, -1.1)),
                    () -> assertEquals(2, fPlot.getIndexY(FPlot2D.Index.ROUND, 100)),
                    () -> assertEquals(3, fPlot.getIndexY(FPlot2D.Index.ROUND, -100))
            );
        }

        @Test
        @DisplayName("Get index Y floor (sorted)")
        void getIndexYFloorSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(1, fPlot.getIndexY(FPlot2D.Index.FLOOR, 1.8)),
                    () -> assertEquals(4, fPlot.getIndexY(FPlot2D.Index.FLOOR, -1.1)),
                    () -> assertEquals(0, fPlot.getIndexY(FPlot2D.Index.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexY(FPlot2D.Index.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index Y floor (random)")
        void getIndexYFloorRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexY(FPlot2D.Index.FLOOR, 1.8)),
                    () -> assertEquals(3, fPlot.getIndexY(FPlot2D.Index.FLOOR, -1.1)),
                    () -> assertEquals(2, fPlot.getIndexY(FPlot2D.Index.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexY(FPlot2D.Index.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index Y ceil (sorted)")
        void getIndexYCeilSorted() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(1, fPlot.getIndexY(FPlot2D.Index.CEIL, 0.8)),
                    () -> assertEquals(3, fPlot.getIndexY(FPlot2D.Index.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexY(FPlot2D.Index.CEIL, 100)),
                    () -> assertEquals(4, fPlot.getIndexY(FPlot2D.Index.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index Y ceil (random)")
        void getIndexYCeilRandom() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexY(FPlot2D.Index.CEIL, 0.8)),
                    () -> assertEquals(0, fPlot.getIndexY(FPlot2D.Index.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexY(FPlot2D.Index.CEIL, 100)),
                    () -> assertEquals(3, fPlot.getIndexY(FPlot2D.Index.CEIL, -100))
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
                    () -> assertEquals(-2, fPlot.getRefFStatX().min()),
                    () -> assertEquals(2, fPlot.getRefFStatX().max()),
                    () -> assertEquals(-1, fPlot.getRefFStatY().min()),
                    () -> assertEquals(5, fPlot.getRefFStatY().max())
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
        @DisplayName("Mean square error A")
        void MeanSquareErrorA() {
            FPlot2D fPlot = factory.getFPlot2D();
            FPoly fPoly = factory.getFPoly(1, 2);

            fPlot.add(-2, 0);
            fPlot.add(-1, 1);
            fPlot.add(0, 2);
            fPlot.add(1, 3);
            fPlot.add(2, 4);

            assertEquals(0, fPlot.mse(fPoly), 1E-4);
        }

        @Test
        @DisplayName("Mean square error B")
        void MeanSquareErrorB() {
            FPlot2D fPlot = factory.getFPlot2D();
            FPoly fPoly = factory.getFPoly(1, 2);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            assertEquals(3.2, fPlot.mse(fPoly), 1E-4);
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
        @DisplayName("Mutate FStat1D - Consumer")
        void mutateFStat1DConsumer() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            fPlot.mutateFStat((a) -> {
                a.mutate((b) -> b * 2);
            });

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(6, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(8, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Mutate FStat1D - BiConsumer")
        void mutateFStat1DBiConsumer() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            fPlot.mutateFStat((a, b) -> {
                a.mutate((c) -> c * 2);
                b.mutate((c) -> c * 4);
            });

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(12, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(16, fPlot.getY(1))
            );

            fPlot.mutateFStat((a, b) -> {
                a.add(1);
                b.add(1);
            });

            assertThrows(IllegalStateException.class, () -> fPlot.mutateFStat((a, b) -> a.add(1)));
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
        @DisplayName("Mutate FStat1D X")
        void mutateFStat1DX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            fPlot.mutateFStatX((a) -> a.mutate((b) -> b * 2));

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getY(1))
            );

            assertThrows(IllegalStateException.class, () -> fPlot.mutateFStatX((x) -> x.add(1)));
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
        @DisplayName("Mutate FStat1D Y")
        void mutateFStat1DY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            fPlot.mutateFStatY((a) -> a.mutate((b) -> b * 2));

            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fPlot.getX(0)),
                    () -> assertEquals(6, fPlot.getY(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(8, fPlot.getY(1))
            );

            assertThrows(IllegalStateException.class, () -> fPlot.mutateFStatY((x) -> x.add(1)));
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
                    () -> assertEquals(-2, fPlot.getRefFStatX().min(), 1E-6),
                    () -> assertEquals(2, fPlot.getRefFStatX().max(), 1E-6),
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
        @DisplayName("Distribute")
        void distribute() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            fPlot.mutateFStatY(FStat1D::distribute);

            Assertions.assertAll("Test values",
                    () -> assertEquals(1, fPlot.getRefFStatY().sum(), 1E-4),
                    () -> assertEquals(-2, fPlot.getX(0)),
                    () -> assertEquals(-1, fPlot.getX(1)),
                    () -> assertEquals(0, fPlot.getX(2)),
                    () -> assertEquals(1, fPlot.getX(3)),
                    () -> assertEquals(2, fPlot.getX(4)),
                    () -> assertEquals(2d / 6, fPlot.getY(0), 1E-4),
                    () -> assertEquals(1d / 6, fPlot.getY(1), 1E-4),
                    () -> assertEquals(0d / 6, fPlot.getY(2), 1E-4),
                    () -> assertEquals(1d / 6, fPlot.getY(3), 1E-4),
                    () -> assertEquals(2d / 6, fPlot.getY(4), 1E-4)
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

            fPlot.forEach((x, y, index) -> {
                sumX.addAndGet((int) Math.round(x));
                sumY.addAndGet((int) Math.round(y));
            });

            assertEquals(15, sumX.get());
            assertEquals(-15, sumY.get());
        }

        @Test
        @DisplayName("Log X")
        void logX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatX((e) -> e.log(Math.E));

            Assertions.assertAll("Check values",
                    () -> assertEquals(Math.log(1), fPlot.getX(0), 1E-4),
                    () -> assertEquals(Math.log(2), fPlot.getX(1), 1E-4),
                    () -> assertEquals(Math.log(3), fPlot.getX(2), 1E-4),
                    () -> assertEquals(4, fPlot.getY(0), 1E-4),
                    () -> assertEquals(5, fPlot.getY(1), 1E-4),
                    () -> assertEquals(6, fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Log Y")
        void logY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatY((e) -> e.log(Math.E));

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0), 1E-4),
                    () -> assertEquals(2, fPlot.getX(1), 1E-4),
                    () -> assertEquals(3, fPlot.getX(2), 1E-4),
                    () -> assertEquals(Math.log(4), fPlot.getY(0), 1E-4),
                    () -> assertEquals(Math.log(5), fPlot.getY(1), 1E-4),
                    () -> assertEquals(Math.log(6), fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Log XY")
        void logXY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatX((e) -> e.log(Math.E));
            fPlot.mutateFStatY((e) -> e.log(Math.E));

            Assertions.assertAll("Check values",
                    () -> assertEquals(Math.log(1), fPlot.getX(0), 1E-4),
                    () -> assertEquals(Math.log(2), fPlot.getX(1), 1E-4),
                    () -> assertEquals(Math.log(3), fPlot.getX(2), 1E-4),
                    () -> assertEquals(Math.log(4), fPlot.getY(0), 1E-4),
                    () -> assertEquals(Math.log(5), fPlot.getY(1), 1E-4),
                    () -> assertEquals(Math.log(6), fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Log10 X")
        void log10X() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatX((e) -> e.log(10));

            Assertions.assertAll("Check values",
                    () -> assertEquals(Math.log10(1), fPlot.getX(0), 1E-4),
                    () -> assertEquals(Math.log10(2), fPlot.getX(1), 1E-4),
                    () -> assertEquals(Math.log10(3), fPlot.getX(2), 1E-4),
                    () -> assertEquals(4, fPlot.getY(0), 1E-4),
                    () -> assertEquals(5, fPlot.getY(1), 1E-4),
                    () -> assertEquals(6, fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Log10 Y")
        void log10Y() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatY((e) -> e.log(10));

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0), 1E-4),
                    () -> assertEquals(2, fPlot.getX(1), 1E-4),
                    () -> assertEquals(3, fPlot.getX(2), 1E-4),
                    () -> assertEquals(Math.log10(4), fPlot.getY(0), 1E-4),
                    () -> assertEquals(Math.log10(5), fPlot.getY(1), 1E-4),
                    () -> assertEquals(Math.log10(6), fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Log10 XY")
        void log10XY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatX((e) -> e.log(10));
            fPlot.mutateFStatY((e) -> e.log(10));

            Assertions.assertAll("Check values",
                    () -> assertEquals(Math.log10(1), fPlot.getX(0), 1E-4),
                    () -> assertEquals(Math.log10(2), fPlot.getX(1), 1E-4),
                    () -> assertEquals(Math.log10(3), fPlot.getX(2), 1E-4),
                    () -> assertEquals(Math.log10(4), fPlot.getY(0), 1E-4),
                    () -> assertEquals(Math.log10(5), fPlot.getY(1), 1E-4),
                    () -> assertEquals(Math.log10(6), fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Ln X")
        void lnX() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatX(FStat1D::ln);

            Assertions.assertAll("Check values",
                    () -> assertEquals(Math.log(1), fPlot.getX(0), 1E-4),
                    () -> assertEquals(Math.log(2), fPlot.getX(1), 1E-4),
                    () -> assertEquals(Math.log(3), fPlot.getX(2), 1E-4),
                    () -> assertEquals(4, fPlot.getY(0), 1E-4),
                    () -> assertEquals(5, fPlot.getY(1), 1E-4),
                    () -> assertEquals(6, fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Ln Y")
        void lnY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatY(FStat1D::ln);

            Assertions.assertAll("Check values",
                    () -> assertEquals(1, fPlot.getX(0), 1E-4),
                    () -> assertEquals(2, fPlot.getX(1), 1E-4),
                    () -> assertEquals(3, fPlot.getX(2), 1E-4),
                    () -> assertEquals(Math.log(4), fPlot.getY(0), 1E-4),
                    () -> assertEquals(Math.log(5), fPlot.getY(1), 1E-4),
                    () -> assertEquals(Math.log(6), fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Ln XY")
        void lnXY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateFStatX(FStat1D::ln);
            fPlot.mutateFStatY(FStat1D::ln);

            Assertions.assertAll("Check values",
                    () -> assertEquals(Math.log(1), fPlot.getX(0), 1E-4),
                    () -> assertEquals(Math.log(2), fPlot.getX(1), 1E-4),
                    () -> assertEquals(Math.log(3), fPlot.getX(2), 1E-4),
                    () -> assertEquals(Math.log(4), fPlot.getY(0), 1E-4),
                    () -> assertEquals(Math.log(5), fPlot.getY(1), 1E-4),
                    () -> assertEquals(Math.log(6), fPlot.getY(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Swap XY")
        void swapXY() {
            FPlot2D fPlot = factory.getFPlot2D();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.swapXY();

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(5, fPlot.getX(1)),
                    () -> assertEquals(6, fPlot.getX(2)),
                    () -> assertEquals(1, fPlot.getY(0)),
                    () -> assertEquals(2, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getY(2))
            );
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
