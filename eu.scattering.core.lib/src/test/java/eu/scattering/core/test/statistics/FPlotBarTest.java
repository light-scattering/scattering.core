package eu.scattering.core.test.statistics;

import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.utility.type.option.RoundMethod;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FPlotBar")
public class FPlotBarTest {

    @Nested
    @DisplayName("Basic")
    class FPlotBarBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            assertEquals(0, fPlotBar.size());
        }

        @Test
        @DisplayName("Create with reference")
        void createWithReference() {
            FStat coreX = factory.getFStat();
            List<FStat> coreY = new ArrayList<>();

            FPlotBar fPlotBar = factory.getRefFPlotBar(coreX, coreY);

            assertEquals(0, fPlotBar.size());
            assertSame(coreX, fPlotBar.getRefCoreX());
            assertSame(coreY, fPlotBar.getRefCoreY());
        }

        @Test
        @DisplayName("Add X")
        void addX() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(2);
            FPlotBar results = fPlotBar.add(3);

            assertSame(fPlotBar, results);
            assertEquals(2, fPlotBar.size());
            assertEquals(0, fPlotBar.getY(0).size());
            assertEquals(0, fPlotBar.getY(1).size());

            assertThrows(IllegalStateException.class, () -> fPlotBar.add(2));
        }

        @Test
        @DisplayName("Add X Y")
        void addXY() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(2, 4);
            fPlotBar.add(3, 5);

            fPlotBar.add(2, 6);
            FPlotBar results = fPlotBar.add(2, 8);

            assertSame(fPlotBar, results);
            assertEquals(2, fPlotBar.size());
            assertEquals(3, fPlotBar.getY(0).size());
            assertEquals(1, fPlotBar.getY(1).size());
            assertEquals(4, fPlotBar.getY(0).get(0));
            assertEquals(6, fPlotBar.getY(0).get(1));
            assertEquals(8, fPlotBar.getY(0).get(2));
        }

        @Test
        @DisplayName("Add FStat")
        void addFStat() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2, 3);
            FStat fStatB = factory.getFStat(4, 5);

            fPlotBar.add(2, fStatA);
            FPlotBar results = fPlotBar.add(3, fStatB);

            assertSame(fPlotBar, results);
            assertEquals(2, fPlotBar.size());
            assertEquals(3, fPlotBar.getY(0).size());
            assertEquals(2, fPlotBar.getY(1).size());
            assertNotSame(fStatA, fPlotBar.getY(0));
            assertNotSame(fStatB, fPlotBar.getY(1));

            FStat fStatC = factory.getFStat(6);

            assertThrows(IllegalStateException.class, () -> fPlotBar.add(2, fStatC));
        }

        @Test
        @DisplayName("Add ref FStat")
        void addRefFStat() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2, 3);
            FStat fStatB = factory.getFStat(4, 5);

            fPlotBar.addRef(2, fStatA);
            FPlotBar results = fPlotBar.addRef(3, fStatB);

            assertSame(fPlotBar, results);
            assertEquals(2, fPlotBar.size());
            assertEquals(3, fPlotBar.getY(0).size());
            assertEquals(2, fPlotBar.getY(1).size());
            assertSame(fStatA, fPlotBar.getRefY(0));
            assertSame(fStatB, fPlotBar.getRefY(1));

            FStat fStatC = factory.getFStat(6);

            assertThrows(IllegalStateException.class, () -> fPlotBar.addRef(2, fStatC));
        }

        @Test
        @DisplayName("Get / Set X")
        void getSetX() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(2, factory.getFStat(1, 2, 3));
            fPlotBar.addRef(3, factory.getFStat(4, 5));

            FPlotBar results = fPlotBar.setX(1, 5);

            assertSame(fPlotBar, results);
            assertEquals(5, fPlotBar.getX(1));

            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.getX(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.getX(3));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.setX(-1, 1));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.setX(3, 1));
        }

        @Test
        @DisplayName("Set Y")
        void setY() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(2, factory.getFStat(1, 2, 3));
            fPlotBar.addRef(3, factory.getFStat(4, 5));

            FStat update = factory.getFStat(6, 7, 8);

            FPlotBar results = fPlotBar.setY(1, update);

            assertSame(fPlotBar, results);
            assertNotSame(update, fPlotBar.getY(1));
            assertNotSame(update, fPlotBar.getRefY(1));
            assertEquals(3, fPlotBar.getY(1).size());
            assertEquals(6, fPlotBar.getY(1).get(0));
            assertEquals(7, fPlotBar.getY(1).get(1));
            assertEquals(8, fPlotBar.getY(1).get(2));

            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.getY(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.getY(3));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.setY(-1, update));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.setY(3, update));
        }

        @Test
        @DisplayName("Set ref Y")
        void setRefY() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(2, factory.getFStat(1, 2, 3));
            fPlotBar.addRef(3, factory.getFStat(4, 5));

            FStat update = factory.getFStat(6, 7, 8);

            FPlotBar results = fPlotBar.setRefY(1, update);

            assertSame(fPlotBar, results);
            assertNotSame(update, fPlotBar.getY(1));
            assertSame(update, fPlotBar.getRefY(1));
            assertEquals(3, fPlotBar.getRefY(1).size());
            assertEquals(6, fPlotBar.getRefY(1).get(0));
            assertEquals(7, fPlotBar.getRefY(1).get(1));
            assertEquals(8, fPlotBar.getRefY(1).get(2));

            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.getRefY(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.getRefY(3));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.setRefY(-1, update));
            assertThrows(IndexOutOfBoundsException.class, () -> fPlotBar.setRefY(3, update));
        }
    }

    @Nested
    @DisplayName("Core")
    class FPlotBarCoreTest {

        @Test
        @DisplayName("Is equal")
        void isEqual() {
            FPlotBar fPlotBarA = factory.getFPlotBar();

            fPlotBarA.addRef(0, factory.getFStat(1, 2));
            fPlotBarA.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBarA.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlotBar fPlotBarB = factory.getFPlotBar();

            fPlotBarB.addRef(0, factory.getFStat(1, 2));
            fPlotBarB.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBarB.addRef(2, factory.getFStat(6, 7, 8, 9));

            assertTrue(fPlotBarA.isEqual(fPlotBarB));
            assertTrue(fPlotBarB.isEqual(fPlotBarA));
            assertTrue(fPlotBarA.isEqualData(fPlotBarB));
            assertTrue(fPlotBarB.isEqualData(fPlotBarA));

            fPlotBarA.add(3, 1);

            assertFalse(fPlotBarA.isEqual(fPlotBarB));
            assertFalse(fPlotBarB.isEqual(fPlotBarA));
            assertFalse(fPlotBarA.isEqualData(fPlotBarB));
            assertFalse(fPlotBarB.isEqualData(fPlotBarA));
        }

        @Test
        @DisplayName("To JSON")
        void toJSON() {
            FPlotBar fPlotBarA = factory.getFPlotBar();

            fPlotBarA.addRef(0, factory.getFStat(1, 2));
            fPlotBarA.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBarA.addRef(2, factory.getFStat(6, 7, 8, 9));

            JSONObject json = fPlotBarA.toJSON();

            FPlotBar fPlotBarB = factory.getFPlotBar(json);

            assertTrue(fPlotBarA.isEqual(fPlotBarB));
            assertTrue(fPlotBarB.isEqual(fPlotBarA));
        }

        @Test
        @DisplayName("To JSON with text")
        void toJSONWithText() {
            FPlotBar fPlotBarA = factory.getFPlotBar();

            fPlotBarA.addRef(0, factory.getFStat(1, 2));
            fPlotBarA.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBarA.addRef(2, factory.getFStat(6, 7, 8, 9));

            JSONObject json = fPlotBarA.toJSON();

            FPlotBar fPlotBarB = factory.getFPlotBar(json.toString());

            assertTrue(fPlotBarA.isEqual(fPlotBarB));
            assertTrue(fPlotBarB.isEqual(fPlotBarA));
        }

        @Test
        @DisplayName("To JSON with NaN")
        void toJSONWithNaN() {
            FPlotBar fPlotBarA = factory.getFPlotBar();

            fPlotBarA.addRef(0, factory.getFStat(1, 2));
            fPlotBarA.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBarA.addRef(2, factory.getFStat(6, 7, 8, 9));

            fPlotBarA.mutateY((y) -> y.get(1).set(1, Double.NaN));

            JSONObject json = fPlotBarA.toJSON();

            FPlotBar fPlotBarB = factory.getFPlotBar(json);

            assertFalse(fPlotBarA.isEqual(fPlotBarB));
            assertFalse(fPlotBarB.isEqual(fPlotBarA));

            assertTrue(fPlotBarA.isEqualWithNaN(fPlotBarB));
            assertTrue(fPlotBarB.isEqualWithNaN(fPlotBarA));
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FPlotBar fPlotBarA = factory.getFPlotBar();

            fPlotBarA.addRef(0, factory.getFStat(1, 2));
            fPlotBarA.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBarA.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlotBar fPlotBarB = fPlotBarA.copy();

            assertNotSame(fPlotBarA, fPlotBarB);
            assertTrue(fPlotBarA.isEqual(fPlotBarB));
            assertTrue(fPlotBarB.isEqual(fPlotBarA));
            assertNotSame(fPlotBarA.getRefCoreY(), fPlotBarB.getRefCoreY());
        }
    }

    @Nested
    @DisplayName("Advanced")
    class FPlotBarAdvancedTest {

        @Test
        @DisplayName("Get index  X round (sorted)")
        void getIndexXRoundSorted() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(-2, 2);
            fPlotBar.add(-1, 1);
            fPlotBar.add(0, 0);
            fPlotBar.add(1, -1);
            fPlotBar.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(4, fPlotBar.getIndexX(RoundMethod.CLOSEST, 1.8)),
                    () -> assertEquals(1, fPlotBar.getIndexX(RoundMethod.CLOSEST, -1.1)),
                    () -> assertEquals(4, fPlotBar.getIndexX(RoundMethod.CLOSEST, 100)),
                    () -> assertEquals(0, fPlotBar.getIndexX(RoundMethod.CLOSEST, -100))
            );
        }

        @Test
        @DisplayName("Get index X round (random)")
        void getIndexXRoundRandom() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(1, -1);
            fPlotBar.add(0, 0);
            fPlotBar.add(-2, 2);
            fPlotBar.add(2, -2);
            fPlotBar.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlotBar.getIndexX(RoundMethod.CLOSEST, 1.8)),
                    () -> assertEquals(4, fPlotBar.getIndexX(RoundMethod.CLOSEST, -1.1)),
                    () -> assertEquals(3, fPlotBar.getIndexX(RoundMethod.CLOSEST, 100)),
                    () -> assertEquals(2, fPlotBar.getIndexX(RoundMethod.CLOSEST, -100))
            );
        }

        @Test
        @DisplayName("Get index X floor (sorted)")
        void getIndexXFloorSorted() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(-2, 2);
            fPlotBar.add(-1, 1);
            fPlotBar.add(0, 0);
            fPlotBar.add(1, -1);
            fPlotBar.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlotBar.getIndexX(RoundMethod.FLOOR, 1.8)),
                    () -> assertEquals(0, fPlotBar.getIndexX(RoundMethod.FLOOR, -1.1)),
                    () -> assertEquals(4, fPlotBar.getIndexX(RoundMethod.FLOOR, 100)),
                    () -> assertEquals(-1, fPlotBar.getIndexX(RoundMethod.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index X floor (random)")
        void getIndexXFloorRandom() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(1, -1);
            fPlotBar.add(0, 0);
            fPlotBar.add(-2, 2);
            fPlotBar.add(2, -2);
            fPlotBar.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlotBar.getIndexX(RoundMethod.FLOOR, 1.8)),
                    () -> assertEquals(2, fPlotBar.getIndexX(RoundMethod.FLOOR, -1.1)),
                    () -> assertEquals(3, fPlotBar.getIndexX(RoundMethod.FLOOR, 100)),
                    () -> assertEquals(-1, fPlotBar.getIndexX(RoundMethod.FLOOR, -100))
            );
        }

        @Test
        @DisplayName("Get index X ceil (sorted)")
        void getIndexXCeilSorted() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(-2, 2);
            fPlotBar.add(-1, 1);
            fPlotBar.add(0, 0);
            fPlotBar.add(1, -1);
            fPlotBar.add(2, -2);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(3, fPlotBar.getIndexX(RoundMethod.CEIL, 0.8)),
                    () -> assertEquals(1, fPlotBar.getIndexX(RoundMethod.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlotBar.getIndexX(RoundMethod.CEIL, 100)),
                    () -> assertEquals(0, fPlotBar.getIndexX(RoundMethod.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Get index X ceil (random)")
        void getIndexXCeilRandom() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.add(1, -1);
            fPlotBar.add(0, 0);
            fPlotBar.add(-2, 2);
            fPlotBar.add(2, -2);
            fPlotBar.add(-1, 1);

            Assertions.assertAll("Check indexes",
                    () -> assertEquals(0, fPlotBar.getIndexX(RoundMethod.CEIL, 0.8)),
                    () -> assertEquals(4, fPlotBar.getIndexX(RoundMethod.CEIL, -1.1)),
                    () -> assertEquals(-1, fPlotBar.getIndexX(RoundMethod.CEIL, 100)),
                    () -> assertEquals(2, fPlotBar.getIndexX(RoundMethod.CEIL, -100))
            );
        }

        @Test
        @DisplayName("Filter")
        void filter() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2);
            FStat fStatB = factory.getFStat(3, 4, 5);
            FStat fStatC = factory.getFStat(6, 7, 8, 9);

            fPlotBar.addRef(0, fStatA);
            fPlotBar.addRef(1, fStatB);
            fPlotBar.addRef(2, fStatC);

            int results = fPlotBar.filter((x, y) -> !y.contains(4));

            assertEquals(1, results);
            assertEquals(2, fPlotBar.size());
            assertSame(fStatA, fPlotBar.getRefY(0));
            assertSame(fStatC, fPlotBar.getRefY(1));
        }

        @Test
        @DisplayName("Mutate X (Consumer)")
        void mutateXConsumer() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlotBar results = fPlotBar.mutateX(a -> a.mutate(b -> b * 2));

            assertSame(fPlotBar, results);
            assertEquals(3, fPlotBar.size());
            assertEquals(0, fPlotBar.getX(0));
            assertEquals(2, fPlotBar.getX(1));
            assertEquals(4, fPlotBar.getX(2));
        }

        @Test
        @DisplayName("Mutate X (BiFunction)")
        void mutateXFunction() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlotBar results = fPlotBar.mutateX((x, y) -> (double) y.size());

            assertSame(fPlotBar, results);
            assertEquals(3, fPlotBar.size());
            assertEquals(2, fPlotBar.getX(0));
            assertEquals(3, fPlotBar.getX(1));
            assertEquals(4, fPlotBar.getX(2));
        }

        @Test
        @DisplayName("Mutate Y (Consumer)")
        void mutateYConsumer() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlotBar results = fPlotBar.mutateY(a -> a.forEach(b -> b.mutate(c -> c * 2)));

            assertSame(fPlotBar, results);
            assertEquals(3, fPlotBar.size());
            assertEquals(6, fPlotBar.getRefY(0).sum());
            assertEquals(24, fPlotBar.getRefY(1).sum());
            assertEquals(60, fPlotBar.getRefY(2).sum());
        }

        @Test
        @DisplayName("Mutate Y (BiFunction)")
        void mutateYFunction() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlotBar results = fPlotBar.mutateY((a, b) -> b.mutate(c -> c * a));

            assertSame(fPlotBar, results);
            assertEquals(3, fPlotBar.size());
            assertEquals(0, fPlotBar.getRefY(0).sum());
            assertEquals(12, fPlotBar.getRefY(1).sum());
            assertEquals(60, fPlotBar.getRefY(2).sum());
        }

        @Test
        @DisplayName("Sort asc X")
        void sortAscX() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2);
            FStat fStatB = factory.getFStat(3, 4, 5);
            FStat fStatC = factory.getFStat(6, 7, 8, 9);

            fPlotBar.addRef(2, fStatA);
            fPlotBar.addRef(0, fStatB);
            fPlotBar.addRef(1, fStatC);

            FPlotBar results = fPlotBar.sortX(true);

            Assertions.assertAll("Check values",
                    () -> assertSame(fPlotBar, results),
                    () -> assertEquals(0, fPlotBar.getX(0)),
                    () -> assertSame(fStatB, fPlotBar.getRefY(0)),
                    () -> assertEquals(1, fPlotBar.getX(1)),
                    () -> assertSame(fStatC, fPlotBar.getRefY(1)),
                    () -> assertEquals(2, fPlotBar.getX(2)),
                    () -> assertSame(fStatA, fPlotBar.getRefY(2))
            );
        }

        @Test
        @DisplayName("Sort dsc X")
        void sortDscX() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2);
            FStat fStatB = factory.getFStat(3, 4, 5);
            FStat fStatC = factory.getFStat(6, 7, 8, 9);

            fPlotBar.addRef(2, fStatA);
            fPlotBar.addRef(0, fStatB);
            fPlotBar.addRef(1, fStatC);

            FPlotBar results = fPlotBar.sortX(false);

            Assertions.assertAll("Check values",
                    () -> assertSame(fPlotBar, results),
                    () -> assertEquals(2, fPlotBar.getX(0)),
                    () -> assertSame(fStatA, fPlotBar.getRefY(0)),
                    () -> assertEquals(1, fPlotBar.getX(1)),
                    () -> assertSame(fStatC, fPlotBar.getRefY(1)),
                    () -> assertEquals(0, fPlotBar.getX(2)),
                    () -> assertSame(fStatB, fPlotBar.getRefY(2))
            );
        }

        @Test
        @DisplayName("For each")
        void forEach() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            FStat fStatA = factory.getFStat(1, 2);
            FStat fStatB = factory.getFStat(3, 4, 5);
            FStat fStatC = factory.getFStat(6, 7, 8, 9);

            fPlotBar.addRef(0, fStatA);
            fPlotBar.addRef(1, fStatB);
            fPlotBar.addRef(2, fStatC);

            AtomicInteger sumX = new AtomicInteger();
            AtomicInteger sumY = new AtomicInteger();

            FPlotBar results = fPlotBar.forEach((x, y, index) -> {
                sumX.addAndGet((int) Math.round(x));
                sumY.addAndGet((int) Math.round(y.sum()));
            });

            assertSame(fPlotBar, results);
            assertEquals(3, sumX.get());
            assertEquals(45, sumY.get());
        }

        @Test
        @DisplayName("To FPlot")
        void toFPlot() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            FPlot fPlot = fPlotBar.toFPlot(FStat::max);

            Assertions.assertAll("Test values",
                    () -> assertEquals(0, fPlot.getX(0)),
                    () -> assertEquals(1, fPlot.getX(1)),
                    () -> assertEquals(2, fPlot.getX(2)),
                    () -> assertEquals(2, fPlot.getY(0)),
                    () -> assertEquals(5, fPlot.getY(1)),
                    () -> assertEquals(9, fPlot.getY(2))
            );
        }

        @Test
        @DisplayName("Remove NaN")
        void removeNaN() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            fPlotBar.setX(1, Double.NaN);
            fPlotBar.getRefY(2).set(1, Double.NaN);
            fPlotBar.getRefY(2).set(2, Double.NaN);

            FPlotBar results = fPlotBar.removeNaN();

            Assertions.assertAll("Check values",
                    () -> assertSame(fPlotBar, results),
                    () -> assertEquals(2, fPlotBar.size()),
                    () -> assertEquals(2, fPlotBar.getRefY(0).size()),
                    () -> assertEquals(2, fPlotBar.getRefY(1).size())
            );
        }

        @Test
        @DisplayName("Get ref core X")
        void getRefCoreX() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            FStat coreX = fPlotBar.getRefCoreX();
            coreX.set(1, 5);

            assertEquals(5, fPlotBar.getX(1));
        }

        @Test
        @DisplayName("Get ref core Y")
        void getRefCoreY() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.addRef(0, factory.getFStat(1, 2));
            fPlotBar.addRef(1, factory.getFStat(3, 4, 5));
            fPlotBar.addRef(2, factory.getFStat(6, 7, 8, 9));

            List<FStat> coreY = fPlotBar.getRefCoreY();
            coreY.get(1).set(1, 0);

            assertEquals(0, fPlotBar.getRefY(1).get(1));
        }
    }

    @Nested
    @DisplayName("Meta")
    class FPlotBarMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            FPlotBar fPlotBar = factory.getFPlotBar();

            fPlotBar.setName("test");

            assertEquals("test", fPlotBar.getName());
        }
    }
}
