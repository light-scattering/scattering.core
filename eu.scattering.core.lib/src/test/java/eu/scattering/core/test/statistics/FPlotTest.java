package eu.scattering.core.test.statistics;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plot.util.FPlotInterpolator;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.transfer.polynomial.variant.FPoly;
import eu.scattering.core.design.utility.type.option.RoundMethod;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FPlot")
public class FPlotTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FPlotBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FPlot fPlot = factory.getFPlot();

            assertEquals(0, fPlot.size());
        }

        @Test
        @DisplayName("Create with reference")
        void createWithReference() {
            FStat coreX = factory.getFStat();
            FStat coreY = factory.getFStat();

            FPlot fPlot = factory.getRefFPlot(coreX, coreY);

            assertEquals(0, fPlot.size());
            assertSame(coreX, fPlot.getRefCoreX());
            assertSame(coreY, fPlot.getRefCoreY());
        }

        @Test
        @DisplayName("Create with FLayer")
        void createWithFLayer() {
            FLayer fLayer = factory.getFLayer();

            fLayer.incGroup(1, 5);
            fLayer.setGroup(2, 4, 3);

            FPlot fPlot = factory.getFPlot(fLayer);

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
            FPlot fPlot = factory.getFPlot();

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
        @DisplayName("Export to FPlotBar")
        void exportToFPlotBar() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FPlotBar fPlotBar = fPlot.toFPlotBar();

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fPlotBar.size()),
                    () -> assertEquals(1, fPlotBar.getX(0)),
                    () -> assertEquals(3, fPlotBar.getX(1)),
                    () -> assertEquals(5, fPlotBar.getX(2)),
                    () -> assertEquals(2, fPlotBar.getRefY(0).get(0)),
                    () -> assertEquals(4, fPlotBar.getRefY(1).get(0)),
                    () -> assertEquals(6, fPlotBar.getRefY(2).get(0))
            );
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FPlot fPlot = factory.getFPlot();

            assertEquals(0, fPlot.size());

            fPlot.add(0);
            FPlot results = fPlot.add(1);

            assertSame(fPlot, results);
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
            FPlot fPlot = factory.getFPlot();

            assertEquals(0, fPlot.size());

            fPlot.add(1, 3);
            FPlot results = fPlot.add(0, 2);

            assertSame(fPlot, results);
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
            FPlot fPlot = factory.getFPlot();

            assertEquals(0, fPlot.size());

            fPlot.add(1, 3);
            fPlot.add(0, 6);

            FPlot results = fPlot.add((y1, y2) -> (y1 + y2) / 2, 0);

            assertSame(fPlot, results);
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
            FPlot fPlot = factory.getFPlot();

            assertEquals(0, fPlot.size());

            fPlot.add(1, 3);
            fPlot.add(0, 2);

            FPlot results = fPlot.add((y1, y2) -> (y1 + y2) / 2, 0, 6);

            assertSame(fPlot, results);
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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(0);
            fPlot.add(1);

            fPlot.clear();

            assertEquals(0, fPlot.size());
        }

        @Test
        @DisplayName("Set X")
        void setX() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 2);
            fPlot.add(3, 4);

            FPlot results = fPlot.setX(1, 1);

            assertSame(fPlot, results);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 2);
            fPlot.add(3, 4);

            FPlot results = fPlot.setY(1, 1);

            assertSame(fPlot, results);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            FPlot results = fPlot.setY(factory.getFPoly(2, 1));

            assertSame(fPlot, results);

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
    class FPlotCoreTest {

        @Test
        @DisplayName("Is equal")
        void isEqual() {
            FPlot fPlotA = factory.getFPlot();

            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            FPlot fPlotB = factory.getFPlot();

            fPlotB.add(0, 0);
            fPlotB.add(1, 1);
            fPlotB.add(2, 2);

            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
            assertTrue(fPlotA.isEqualData(fPlotB));
            assertTrue(fPlotB.isEqualData(fPlotA));

            fPlotA.add(10, 10);

            assertFalse(fPlotA.isEqual(fPlotB));
            assertFalse(fPlotB.isEqual(fPlotA));
            assertFalse(fPlotA.isEqualData(fPlotB));
            assertFalse(fPlotB.isEqualData(fPlotA));
        }

        @Test
        @DisplayName("To JSON")
        void toJSON() {
            FPlot fPlotA = factory.getFPlot();

            fPlotA.add(-2, 2);
            fPlotA.add(-1, 1);
            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            JSONObject json = fPlotA.toJSON();

            FPlot fPlotB = factory.getFPlot(json);

            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
        }

        @Test
        @DisplayName("To JSON with text")
        void toJSONWithText() {
            FPlot fPlotA = factory.getFPlot();

            fPlotA.add(-2, 2);
            fPlotA.add(-1, 1);
            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            JSONObject json = fPlotA.toJSON();

            FPlot fPlotB = factory.getFPlot(json.toString());

            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
        }

        @Test
        @DisplayName("To JSON with NaN")
        void toJSONWithNaN() {
            FPlot fPlotA = factory.getFPlot();

            fPlotA.add(-2, 2);
            fPlotA.add(Double.NaN, 1);
            fPlotA.add(0, Double.NaN);
            fPlotA.add(Double.NaN, 1);
            fPlotA.add(2, 2);

            JSONObject json = fPlotA.toJSON();

            FPlot fPlotB = factory.getFPlot(json);

            assertFalse(fPlotA.isEqual(fPlotB));
            assertFalse(fPlotB.isEqual(fPlotA));

            assertTrue(fPlotA.isEqualWithNaN(fPlotB));
            assertTrue(fPlotB.isEqualWithNaN(fPlotA));
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FPlot fPlotA = factory.getFPlot();

            fPlotA.add(0, 0);
            fPlotA.add(1, 1);
            fPlotA.add(2, 2);

            FPlot fPlotB = fPlotA.copy();

            assertNotSame(fPlotA, fPlotB);
            assertTrue(fPlotA.isEqual(fPlotB));
            assertTrue(fPlotB.isEqual(fPlotA));
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FPlotAdvancedTest {

        @Test
        @DisplayName("Get ref FStat X")
        void getRefFStatX() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat fStatRef = factory.getFStat();

            fStatRef.add(1, 3, 5);

            FStat fStat = fPlot.getRefCoreX();

            assertTrue(fStatRef.isEqual(fStat));
        }

        @Test
        @DisplayName("Get ref FStat Y")
        void getRefFStatY() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 2);
            fPlot.add(3, 4);
            fPlot.add(5, 6);

            FStat fStatRef = factory.getFStat();

            fStatRef.add(2, 4, 6);

            FStat fStat = fPlot.getRefCoreY();

            assertTrue(fStatRef.isEqual(fStat));
        }

        @Test
        @DisplayName("Get index  X round (sorted)")
        void getIndexXRoundSorted() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexX(RoundMethod.CLOSEST, 1.8)),
                    () -> assertEquals(1, fPlot.getIndexX(RoundMethod.CLOSEST, -1.1)),
                    () -> assertEquals(4, fPlot.getIndexX(RoundMethod.CLOSEST, 100)),
                    () -> assertEquals(0, fPlot.getIndexX(RoundMethod.CLOSEST, -100))
            );
        }

        @Test
        @DisplayName("Get index X round (random)")
        void getIndexXRoundRandom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexX(RoundMethod.CLOSEST, 1.8)),
                    () -> assertEquals(4, fPlot.getIndexX(RoundMethod.CLOSEST, -1.1)),
                    () -> assertEquals(3, fPlot.getIndexX(RoundMethod.CLOSEST, 100)),
                    () -> assertEquals(2, fPlot.getIndexX(RoundMethod.CLOSEST, -100))
            );
        }

        @Test
        @DisplayName("Get index X floor (sorted)")
        void getIndexXFloorSorted() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexX(RoundMethod.FLOOR, 1.8)),
                    () -> assertEquals(0, fPlot.getIndexX(RoundMethod.FLOOR, -1.1)),
                    () -> assertEquals(4, fPlot.getIndexX(RoundMethod.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexX(RoundMethod.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index X floor (random)")
        void getIndexXFloorRandom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexX(RoundMethod.FLOOR, 1.8)),
                    () -> assertEquals(2, fPlot.getIndexX(RoundMethod.FLOOR, -1.1)),
                    () -> assertEquals(3, fPlot.getIndexX(RoundMethod.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexX(RoundMethod.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index X ceil (sorted)")
        void getIndexXCeilSorted() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlot.getIndexX(RoundMethod.CEIL, 0.8)),
                    () -> assertEquals(1, fPlot.getIndexX(RoundMethod.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexX(RoundMethod.CEIL, 100)),
                    () -> assertEquals(0, fPlot.getIndexX(RoundMethod.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index X ceil (random)")
        void getIndexXCeilRandom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexX(RoundMethod.CEIL, 0.8)),
                    () -> assertEquals(4, fPlot.getIndexX(RoundMethod.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexX(RoundMethod.CEIL, 100)),
                    () -> assertEquals(2, fPlot.getIndexX(RoundMethod.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index  Y round (sorted)")
        void getIndexYRoundSorted() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlot.getIndexY(RoundMethod.CLOSEST, 1.8)),
                    () -> assertEquals(3, fPlot.getIndexY(RoundMethod.CLOSEST, -1.1)),
                    () -> assertEquals(0, fPlot.getIndexY(RoundMethod.CLOSEST, 100)),
                    () -> assertEquals(4, fPlot.getIndexY(RoundMethod.CLOSEST, -100))
            );
        }

        @Test
        @DisplayName("Get index Y round (random)")
        void getIndexXYRoundRandom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(2, fPlot.getIndexY(RoundMethod.CLOSEST, 1.8)),
                    () -> assertEquals(0, fPlot.getIndexY(RoundMethod.CLOSEST, -1.1)),
                    () -> assertEquals(2, fPlot.getIndexY(RoundMethod.CLOSEST, 100)),
                    () -> assertEquals(3, fPlot.getIndexY(RoundMethod.CLOSEST, -100))
            );
        }

        @Test
        @DisplayName("Get index Y floor (sorted)")
        void getIndexYFloorSorted() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(1, fPlot.getIndexY(RoundMethod.FLOOR, 1.8)),
                    () -> assertEquals(4, fPlot.getIndexY(RoundMethod.FLOOR, -1.1)),
                    () -> assertEquals(0, fPlot.getIndexY(RoundMethod.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexY(RoundMethod.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index Y floor (random)")
        void getIndexYFloorRandom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexY(RoundMethod.FLOOR, 1.8)),
                    () -> assertEquals(3, fPlot.getIndexY(RoundMethod.FLOOR, -1.1)),
                    () -> assertEquals(2, fPlot.getIndexY(RoundMethod.FLOOR, 100)),
                    () -> assertEquals(-1, fPlot.getIndexY(RoundMethod.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index Y ceil (sorted)")
        void getIndexYCeilSorted() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(1, fPlot.getIndexY(RoundMethod.CEIL, 0.8)),
                    () -> assertEquals(3, fPlot.getIndexY(RoundMethod.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexY(RoundMethod.CEIL, 100)),
                    () -> assertEquals(4, fPlot.getIndexY(RoundMethod.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index Y ceil (random)")
        void getIndexYCeilRandom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -2);
            fPlot.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlot.getIndexY(RoundMethod.CEIL, 0.8)),
                    () -> assertEquals(0, fPlot.getIndexY(RoundMethod.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlot.getIndexY(RoundMethod.CEIL, 100)),
                    () -> assertEquals(3, fPlot.getIndexY(RoundMethod.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get min/max value")
        void getMinMaxValue() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, -1);
            fPlot.add(0, 0);
            fPlot.add(-2, 2);
            fPlot.add(2, -1);
            fPlot.add(-1, 5);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(-2, fPlot.getRefCoreX().min()),
                    () -> assertEquals(2, fPlot.getRefCoreX().max()),
                    () -> assertEquals(-1, fPlot.getRefCoreY().min()),
                    () -> assertEquals(5, fPlot.getRefCoreY().max())
            );
        }

        @Test
        @DisplayName("Integrate - A")
        void integrateA() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 0);
            fPlot.add(2, 5);

            fPlot.apx().sampleDivisions(FPlotInterpolator::linear, 100);

            double area = 10;
            double results = fPlot.integrate();

            assertEquals(area, results, 1E-4);
        }

        @Test
        @DisplayName("Integrate - B")
        void integrateB() {
            FPlot fPlot = factory.getFPlot();

            double step = Math.PI / 10;
            double x = -Math.PI;
            while (x <= Math.PI) {
                fPlot.add(x, Math.sin(x));
                x += step;
            }

            fPlot.apx().sampleDivisions(FPlotInterpolator::hermite, 100);

            double area = 4;
            double results = fPlot.integrate();

            assertEquals(area, results, 1E-1);
        }

        @Test
        @DisplayName("Filter X")
        void filterX() {
            FPlot fPlot = factory.getFPlot();

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
            FPlot fPlot = factory.getFPlot();

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
        @DisplayName("Mutate FStat - Consumer")
        void mutateFStatConsumer() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            FPlot results = fPlot.mutate((a) -> a.mutate((b) -> b * 2));

            assertSame(fPlot, results);
            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(6, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(8, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Mutate FStat - BiConsumer")
        void mutateFStatBiConsumer() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            FPlot results = fPlot.mutate((a, b) -> {
                a.mutate((c) -> c * 2);
                b.mutate((c) -> c * 4);
            });

            assertSame(fPlot, results);
            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(12, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(16, fPlot.getY(1))
            );

            fPlot.mutate((a, b) -> {
                a.add(1);
                b.add(1);
            });

            assertThrows(IllegalStateException.class, () -> fPlot.mutate((a, b) -> a.add(1)));
        }

        @Test
        @DisplayName("Mutate X")
        void mutateX() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            FPlot results = fPlot.mutateX((x, y) -> (x * 2) + y);

            assertSame(fPlot, results);
            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(7, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0)),
                    () -> assertEquals(10, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Mutate FStat X")
        void mutateFStatX() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            FPlot results = fPlot.mutateX((a) -> a.mutate((b) -> b * 2));

            assertSame(fPlot, results);
            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(3, fPlot.getY(0)),
                    () -> assertEquals(6, fPlot.getX(1)),
                    () -> assertEquals(4, fPlot.getY(1))
            );

            assertThrows(IllegalStateException.class, () -> fPlot.mutateX((x) -> x.add(1)));
        }

        @Test
        @DisplayName("Mutate Y")
        void mutateY() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            FPlot results = fPlot.mutateY((x, y) -> (x * 2) + y);

            assertSame(fPlot, results);
            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fPlot.getX(0)),
                    () -> assertEquals(7, fPlot.getY(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(10, fPlot.getY(1))
            );
        }

        @Test
        @DisplayName("Mutate FStat Y")
        void mutateFStatY() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, 3);
            fPlot.add(3, 4);

            FPlot results = fPlot.mutateY((a) -> a.mutate((b) -> b * 2));

            assertSame(fPlot, results);
            assertEquals(2, fPlot.size());

            Assertions.assertAll("Check values",
                    () -> assertEquals(2, fPlot.getX(0)),
                    () -> assertEquals(6, fPlot.getY(0)),
                    () -> assertEquals(3, fPlot.getX(1)),
                    () -> assertEquals(8, fPlot.getY(1))
            );

            assertThrows(IllegalStateException.class, () -> fPlot.mutateY((x) -> x.add(1)));
        }

        @Test
        @DisplayName("Regression with range - Poly 0")
        void regressionWithRangePoly0() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPoly parameters = fPlot.reg().poly(0, 2, 3);

            assertEquals(-0.65, parameters.at(0), 1E-4);
        }

        @Test
        @DisplayName("Regression - Poly 0")
        void regressionPoly0() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPoly parameters = fPlot.reg().poly(0);

            assertEquals(-0.12, parameters.at(0), 1E-4);

            fPlot.setY(parameters);

            assertEquals(-0.12, fPlot.getY(0), 1E-4);
            assertEquals(-0.12, fPlot.getY(1), 1E-4);
            assertEquals(-0.12, fPlot.getY(2), 1E-4);
            assertEquals(-0.12, fPlot.getY(3), 1E-4);
            assertEquals(-0.12, fPlot.getY(4), 1E-4);
        }

        @Test
        @DisplayName("Regression (constant) - Poly 0")
        void regressionConstantPoly0() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPoly parameters = fPlot.reg().fitConstant();

            assertEquals(-0.12, parameters.getRefCore()[0], 1E-4);

            fPlot.setY(parameters);

            assertEquals(-0.12, fPlot.getY(0), 1E-4);
            assertEquals(-0.12, fPlot.getY(1), 1E-4);
            assertEquals(-0.12, fPlot.getY(2), 1E-4);
            assertEquals(-0.12, fPlot.getY(3), 1E-4);
            assertEquals(-0.12, fPlot.getY(4), 1E-4);
        }

        @Test
        @DisplayName("Regression with range - Poly 1")
        void regressionWithRangePoly1() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPoly parameters = fPlot.reg().poly(1, 2, 3);

            assertEquals(-1.7, parameters.at(1), 1E-4);
            assertEquals(0.2, parameters.at(0), 1E-4);
        }

        @Test
        @DisplayName("Regression - Poly 1")
        void regressionPoly1() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPoly parameters = fPlot.reg().poly(1);

            assertEquals(-1.12, parameters.at(1), 1E-4);
            assertEquals(-0.12, parameters.at(0), 1E-4);

            fPlot.setY(parameters);

            assertEquals(2.12, fPlot.getY(0), 1E-4);
            assertEquals(1, fPlot.getY(1), 1E-4);
            assertEquals(-0.12, fPlot.getY(2), 1E-4);
            assertEquals(-1.24, fPlot.getY(3), 1E-4);
            assertEquals(-2.36, fPlot.getY(4), 1E-4);
        }

        @Test
        @DisplayName("Regression (linear) - Poly 1")
        void regressionLinearPoly1() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2.1);
            fPlot.add(-1, 0.9);
            fPlot.add(0, 0.2);
            fPlot.add(1, -1.5);
            fPlot.add(2, -2.3);

            FPoly parameters = fPlot.reg().fitLinear();

            assertEquals(-1.12, parameters.getRefCore()[1], 1E-4);
            assertEquals(-0.12, parameters.getRefCore()[0], 1E-4);

            fPlot.setY(parameters);

            assertEquals(2.12, fPlot.getY(0), 1E-4);
            assertEquals(1, fPlot.getY(1), 1E-4);
            assertEquals(-0.12, fPlot.getY(2), 1E-4);
            assertEquals(-1.24, fPlot.getY(3), 1E-4);
            assertEquals(-2.36, fPlot.getY(4), 1E-4);
        }

        @Test
        @DisplayName("Interpolate with divisions")
        void interpolateWithDivisions() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            FPlot results = fPlot.apx().sampleDivisions(FPlotInterpolator::hermite, 10);

            Assertions.assertAll("Test values",
                    () -> assertEquals(11, results.size())
            );
        }

        @Test
        @DisplayName("Distribute")
        void distribute() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            fPlot.mutateY(FStat::distribute);

            Assertions.assertAll("Test values",
                    () -> assertEquals(1, fPlot.getRefCoreY().sum(), 1E-4),
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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            FPlot results = fPlot.sortX(true);

            assertSame(fPlot, results);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            FPlot results = fPlot.sortX(false);

            assertSame(fPlot, results);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            FPlot results = fPlot.sortY(true);

            assertSame(fPlot, results);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            FPlot results = fPlot.sortY(false);

            assertSame(fPlot, results);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(2, -2);
            fPlot.add(5, -5);
            fPlot.add(1, -1);
            fPlot.add(4, -4);
            fPlot.add(3, -3);

            AtomicInteger sumX = new AtomicInteger();
            AtomicInteger sumY = new AtomicInteger();

            FPlot results = fPlot.forEach((x, y, index) -> {
                sumX.addAndGet((int) Math.round(x));
                sumY.addAndGet((int) Math.round(y));
            });

            assertSame(fPlot, results);
            assertEquals(15, sumX.get());
            assertEquals(-15, sumY.get());
        }

        @Test
        @DisplayName("Log X")
        void logX() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateX((e) -> e.log(Math.E));

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateY((e) -> e.log(Math.E));

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateX((e) -> e.log(Math.E));
            fPlot.mutateY((e) -> e.log(Math.E));

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateX((e) -> e.log(10));

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateY((e) -> e.log(10));

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateX((e) -> e.log(10));
            fPlot.mutateY((e) -> e.log(10));

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateX(FStat::ln);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateY(FStat::ln);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            fPlot.mutateX(FStat::ln);
            fPlot.mutateY(FStat::ln);

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
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            FPlot results = fPlot.swapXY();

            assertSame(fPlot, results);

            Assertions.assertAll("Check values",
                    () -> assertEquals(4, fPlot.getX(0)),
                    () -> assertEquals(5, fPlot.getX(1)),
                    () -> assertEquals(6, fPlot.getX(2)),
                    () -> assertEquals(1, fPlot.getY(0)),
                    () -> assertEquals(2, fPlot.getY(1)),
                    () -> assertEquals(3, fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Remove NaN")
        void removeNaN() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(1, 4);
            fPlot.add(2, 5);
            fPlot.add(3, 6);

            Assertions.assertAll("Check values",
                    () -> assertEquals(3, fPlot.size()),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(2, fPlot.getX(1)),
                    () -> assertEquals(3, fPlot.getX(2)),
                    () -> assertEquals(4, fPlot.getY(0)),
                    () -> assertEquals(5, fPlot.getY(1)),
                    () -> assertEquals(6, fPlot.getY(2))
            );

            fPlot.setX(1, Double.NaN);
            fPlot.setY(2, Double.NaN);

            FPlot results = fPlot.removeNaN();

            Assertions.assertAll("Check values",
                    () -> assertSame(fPlot, results),
                    () -> assertEquals(1, fPlot.size()),
                    () -> assertEquals(1, fPlot.getX(0)),
                    () -> assertEquals(4, fPlot.getY(0))
            );
        }
    }

    @Nested
    @Tag("Regressor")
    @DisplayName("Regressor")
    class FPlotRegressorTest {

        @Test
        @DisplayName("Mean square error A")
        void meanSquareErrorA() {
            FPlot fPlot = factory.getFPlot();
            FPoly fPoly = factory.getFPoly(2, 1);

            fPlot.add(-2, 0);
            fPlot.add(-1, 1);
            fPlot.add(0, 2);
            fPlot.add(1, 3);
            fPlot.add(2, 4);

            assertEquals(0, fPlot.reg().mse(fPoly), 1E-4);
        }

        @Test
        @DisplayName("Mean square error B")
        void meanSquareErrorB() {
            FPlot fPlot = factory.getFPlot();
            FPoly fPoly = factory.getFPoly(2, 1);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            assertEquals(3.2, fPlot.reg().mse(fPoly), 1E-4);
        }

        @Test
        @DisplayName("Mean square error - Range")
        void meanSquareErrorRange() {
            FPlot fPlot = factory.getFPlot();
            FPoly fPoly = factory.getFPoly(2, 1);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            assertEquals(8d / 3, fPlot.reg().mse(fPoly, 0, 2), 1E-4);
        }

        @Test
        @DisplayName("Root mean square error A")
        void rootMeanSquareErrorA() {
            FPlot fPlot = factory.getFPlot();
            FPoly fPoly = factory.getFPoly(2, 1);

            fPlot.add(-2, 0);
            fPlot.add(-1, 1);
            fPlot.add(0, 2);
            fPlot.add(1, 3);
            fPlot.add(2, 4);

            assertEquals(0, fPlot.reg().rmse(fPoly), 1E-4);
        }

        @Test
        @DisplayName("Root mean square error B")
        void rootMeanSquareErrorB() {
            FPlot fPlot = factory.getFPlot();
            FPoly fPoly = factory.getFPoly(2, 1);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            assertEquals(Math.sqrt(3.2), fPlot.reg().rmse(fPoly), 1E-4);
        }

        @Test
        @DisplayName("Root mean square error - Range")
        void rootMeanSquareErrorRange() {
            FPlot fPlot = factory.getFPlot();
            FPoly fPoly = factory.getFPoly(2, 1);

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            assertEquals(Math.sqrt(8d / 3), fPlot.reg().rmse(fPoly, 0, 2), 1E-4);
        }
    }

    @Nested
    @Tag("Interpolator")
    @DisplayName("Interpolator")
    class FPlotInterpolatorTest {

        @Test
        @DisplayName("Approximate linear")
        void approxLinear() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.apx().linear(-2), 1E-6),
                    () -> assertEquals(1, fPlot.apx().linear(-1), 1E-6),
                    () -> assertEquals(0, fPlot.apx().linear(0), 1E-6),
                    () -> assertEquals(-1, fPlot.apx().linear(1), 1E-6),
                    () -> assertEquals(-2, fPlot.apx().linear(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertEquals(1.5, fPlot.apx().linear(-1.5), 1E-6),
                    () -> assertEquals(-1.9, fPlot.apx().linear(1.9), 1E-6)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().linear(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().linear(2.5))
            );
        }

        @Test
        @DisplayName("Approximate cosine")
        void approxCosine() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.apx().cosine(-2), 1E-6),
                    () -> assertEquals(1, fPlot.apx().cosine(-1), 1E-6),
                    () -> assertEquals(0, fPlot.apx().cosine(0), 1E-6),
                    () -> assertEquals(-1, fPlot.apx().cosine(1), 1E-6),
                    () -> assertEquals(-2, fPlot.apx().cosine(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.apx().cosine(-1.5) > 1 && fPlot.apx().cosine(-1.5) < 2),
                    () -> assertTrue(fPlot.apx().cosine(1.9) > -2 && fPlot.apx().cosine(1.9) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().cosine(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().cosine(2.5))
            );
        }

        @Test
        @DisplayName("Approximate cubic")
        void approxCubic() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.apx().cubic(-2), 1E-6),
                    () -> assertEquals(1, fPlot.apx().cubic(-1), 1E-6),
                    () -> assertEquals(0, fPlot.apx().cubic(0), 1E-6),
                    () -> assertEquals(-1, fPlot.apx().cubic(1), 1E-6),
                    () -> assertEquals(-2, fPlot.apx().cubic(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.apx().cubic(-1.5) > 1 && fPlot.apx().cubic(-1.5) < 2),
                    () -> assertTrue(fPlot.apx().cubic(-0.5) > 0 && fPlot.apx().cubic(-0.5) < 1),
                    () -> assertTrue(fPlot.apx().cubic(0.5) > -1 && fPlot.apx().cubic(0.5) < 0),
                    () -> assertTrue(fPlot.apx().cubic(1.5) > -2 && fPlot.apx().cubic(1.5) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().cubic(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().cubic(2.5))
            );
        }

        @Test
        @DisplayName("Approximate Catmull-Rom")
        void approxCatmullRom() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.apx().catmullRom(-2), 1E-6),
                    () -> assertEquals(1, fPlot.apx().catmullRom(-1), 1E-6),
                    () -> assertEquals(0, fPlot.apx().catmullRom(0), 1E-6),
                    () -> assertEquals(-1, fPlot.apx().catmullRom(1), 1E-6),
                    () -> assertEquals(-2, fPlot.apx().catmullRom(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.apx().catmullRom(-1.5) > 1 && fPlot.apx().catmullRom(-1.5) < 2),
                    () -> assertTrue(fPlot.apx().catmullRom(-0.5) > 0 && fPlot.apx().catmullRom(-0.5) < 1),
                    () -> assertTrue(fPlot.apx().catmullRom(0.5) > -1 && fPlot.apx().catmullRom(0.5) < 0),
                    () -> assertTrue(fPlot.apx().catmullRom(1.5) > -2 && fPlot.apx().catmullRom(1.5) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().catmullRom(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().catmullRom(2.5))
            );
        }

        @Test
        @DisplayName("Approximate Hermite")
        void approxHermite() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, -1);
            fPlot.add(2, -2);

            Assertions.assertAll("Integer values",
                    () -> assertEquals(2, fPlot.apx().hermite(-2), 1E-6),
                    () -> assertEquals(1, fPlot.apx().hermite(-1), 1E-6),
                    () -> assertEquals(0, fPlot.apx().hermite(0), 1E-6),
                    () -> assertEquals(-1, fPlot.apx().hermite(1), 1E-6),
                    () -> assertEquals(-2, fPlot.apx().hermite(2), 1E-6)
            );

            Assertions.assertAll("Test values",
                    () -> assertTrue(fPlot.apx().hermite(-1.5) > 1 && fPlot.apx().hermite(-1.5) < 2),
                    () -> assertTrue(fPlot.apx().hermite(-0.5) > 0 && fPlot.apx().hermite(-0.5) < 1),
                    () -> assertTrue(fPlot.apx().hermite(0.5) > -1 && fPlot.apx().hermite(0.5) < 0),
                    () -> assertTrue(fPlot.apx().hermite(1.5) > -2 && fPlot.apx().hermite(1.5) < -1)
            );

            Assertions.assertAll("Erroneous values",
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().hermite(-2.5)),
                    () -> assertThrows(IllegalArgumentException.class, () -> fPlot.apx().hermite(2.5))
            );
        }

        @Test
        @DisplayName("Interpolate with step")
        void interpolate() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            FPlot results = fPlot.apx().sampleStep(FPlotInterpolator::hermite, 0.1);

            Assertions.assertAll("Test values",
                    () -> assertEquals(40, results.size(), 1),
                    () -> assertEquals(-2, results.getRefCoreX().min(), 1E-6),
                    () -> assertEquals(2, results.getRefCoreX().max(), 1E-6),
                    () -> assertEquals(1.5, results.apx().hermite(-1.5), 0.25),
                    () -> assertEquals(0.5, results.apx().hermite(-0.5), 0.25),
                    () -> assertEquals(0, results.apx().hermite(0), 0.25),
                    () -> assertEquals(0.5, results.apx().hermite(0.5), 0.25),
                    () -> assertEquals(1.5, results.apx().hermite(1.5), 0.25)
            );
        }

        @Test
        @DisplayName("Interpolate with divisions")
        void interpolateWithDivisions() {
            FPlot fPlot = factory.getFPlot();

            fPlot.add(-2, 2);
            fPlot.add(-1, 1);
            fPlot.add(0, 0);
            fPlot.add(1, 1);
            fPlot.add(2, 2);

            FPlot results = fPlot.apx().sampleDivisions(FPlotInterpolator::hermite, 39);

            Assertions.assertAll("Test values",
                    () -> assertEquals(40, results.size(), 1),
                    () -> assertEquals(-2, results.getRefCoreX().min(), 1E-6),
                    () -> assertEquals(2, results.getRefCoreX().max(), 1E-6),
                    () -> assertEquals(1.5, results.apx().hermite(-1.5), 0.25),
                    () -> assertEquals(0.5, results.apx().hermite(-0.5), 0.25),
                    () -> assertEquals(0, results.apx().hermite(0), 0.25),
                    () -> assertEquals(0.5, results.apx().hermite(0.5), 0.25),
                    () -> assertEquals(1.5, results.apx().hermite(1.5), 0.25)
            );
        }
    }

    @Nested
    @Tag("Meta")
    @DisplayName("Meta")
    class FPlotMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            FPlot fPlot = factory.getFPlot();

            fPlot.setName("test");

            assertEquals("test", fPlot.getName());
        }
    }
}

//  https://www.statskingdom.com/linear-regression-calculator.html
