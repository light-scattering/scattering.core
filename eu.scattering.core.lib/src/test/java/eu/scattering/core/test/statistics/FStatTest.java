package eu.scattering.core.test.statistics;

import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.base.FStat;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FStat")
public class FStatTest {

    @Nested
    @DisplayName("Basic")
    class FStatBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FStat fStat = factory.getFStat();

            assertEquals(0, fStat.size());
        }

        @Test
        @DisplayName("Create with reference")
        void createWithReference() {
            List<Double> data = Arrays.asList(1d, 2d, 3d, 4d, 5d);

            FStat fStat = factory.getRefFStat(data);

            assertSame(data, fStat.getRefCore());
        }

        @Test
        @DisplayName("Create with collection")
        void createWithCollection() {
            List<Double> values = new ArrayList<>();

            values.add(1d);
            values.add(2d);
            values.add(3d);
            values.add(4d);
            values.add(5d);

            FStat fStat = factory.getRefFStat(values);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(values.size(), fStat.size()),
                    () -> assertEquals(values.get(0), fStat.get(0)),
                    () -> assertEquals(values.get(1), fStat.get(1)),
                    () -> assertEquals(values.get(2), fStat.get(2)),
                    () -> assertEquals(values.get(3), fStat.get(3)),
                    () -> assertEquals(values.get(4), fStat.get(4))
            );
        }

        @Test
        @DisplayName("Create with array")
        void createWithArray() {
            FStat fStat = factory.getFStat(1, 2, 3, 4, 5);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(5, fStat.size()),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(2, fStat.get(1)),
                    () -> assertEquals(3, fStat.get(2)),
                    () -> assertEquals(4, fStat.get(3)),
                    () -> assertEquals(5, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Export to array")
        void exportToArray() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            double[] results = fStat.toArray();

            Assertions.assertAll("Validate results",
                    () -> assertEquals(results.length, fStat.size()),
                    () -> assertEquals(results[0], fStat.get(0)),
                    () -> assertEquals(results[1], fStat.get(1)),
                    () -> assertEquals(results[2], fStat.get(2)),
                    () -> assertEquals(results[3], fStat.get(3)),
                    () -> assertEquals(results[4], fStat.get(4))
            );
        }

        @Test
        @DisplayName("Add")
        void add() {
            FStat fStat = factory.getFStat();

            fStat.add(5);
            fStat.add(7);

            FStat results = fStat.add(9);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(7, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Add vararg")
        void addVararg() {
            FStat fStat = factory.getFStat();

            FStat results = fStat.add(5, 7, 9);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(7, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Clear")
        void clear() {
            FStat fStat = factory.getFStat();

            fStat.add(5);
            fStat.add(7);
            fStat.add(9);

            fStat.clear();

            assertEquals(0, fStat.size());
        }

        @Test
        @DisplayName("Set")
        void set() {
            FStat fStat = factory.getFStat();

            fStat.add(5);
            fStat.add(7);
            fStat.add(9);

            FStat results = fStat.set(1, 1);

            assertThrows(IndexOutOfBoundsException.class, () -> fStat.set(-1, 0));
            assertThrows(IndexOutOfBoundsException.class, () -> fStat.set(3, 0));

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(1, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Contains")
        void contains() {
            FStat fStat = factory.getFStat(1, 2, 3);

            Assertions.assertAll("Validate results",
                    () -> assertTrue(fStat.contains(2)),
                    () -> assertFalse(fStat.contains(4))
            );
        }
    }

    @Nested
    @DisplayName("Core")
    class FStatCoreTest {

        @Test
        @DisplayName("Is equal")
        void isEqual() {
            FStat fStat1 = factory.getFStat();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            FStat fStat2 = factory.getFStat();

            fStat2.add(1);
            fStat2.add(-2);
            fStat2.add(5);
            fStat2.add(3);
            fStat2.add(-2);

            assertTrue(fStat1.isEqual(fStat2));
            assertTrue(fStat2.isEqual(fStat1));
        }

        @Test
        @DisplayName("Equals (fail)")
        void equalsFail() {
            FStat fStat1 = factory.getFStat();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            FStat fStat2 = factory.getFStat();

            fStat2.add(1);
            fStat2.add(-2);
            fStat2.add(5);
            fStat2.add(3);
            fStat2.add(-1);

            assertFalse(fStat1.isEqual(fStat2));
            assertFalse(fStat2.isEqual(fStat1));
        }

        @Test
        @DisplayName("To JSON")
        void toJSON() {
            FStat fStat1 = factory.getFStat();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            JSONObject json = fStat1.toJSON();

            FStat fStat2 = factory.getFStat(json);

            assertTrue(fStat1.isEqual(fStat2));
            assertTrue(fStat2.isEqual(fStat1));
        }

        @Test
        @DisplayName("To JSON with text")
        void toJSONWithText() {
            FStat fStat1 = factory.getFStat();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            JSONObject json = fStat1.toJSON();

            FStat fStat2 = factory.getFStat(json.toString());

            assertTrue(fStat1.isEqual(fStat2));
            assertTrue(fStat2.isEqual(fStat1));
        }

        @Test
        @DisplayName("To JSON with NaN")
        void toJSONWithNaN() {
            FStat fStat1 = factory.getFStat();

            fStat1.add(1);
            fStat1.add(2);
            fStat1.add(Double.NaN);
            fStat1.add(4);
            fStat1.add(5);

            JSONObject json = fStat1.toJSON();

            FStat fStat2 = factory.getFStat(json);

            assertFalse(fStat1.isEqual(fStat2));
            assertFalse(fStat2.isEqual(fStat1));

            assertTrue(fStat1.isEqualWithNaN(fStat2));
            assertTrue(fStat2.isEqualWithNaN(fStat1));
        }

        @Test
        @DisplayName("Iterate")
        void iterate() {
            FStat fStat = factory.getFStat();

            fStat.add(1, -2, 5, 3, -2);

            FStat results = factory.getFStat();

            for (double value : fStat) {
                results.add(value);
            }

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, results.get(0)),
                    () -> assertEquals(-2, results.get(1)),
                    () -> assertEquals(5, results.get(2)),
                    () -> assertEquals(3, results.get(3)),
                    () -> assertEquals(-2, results.get(4))
            );
        }
    }

    @Nested
    @DisplayName("Advanced")
    class FStatAdvancedTest {

        @Test
        @DisplayName("Sort ascending")
        void sortAsc() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            FStat results = fStat.sort(true);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(-2, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(1, fStat.get(2)),
                    () -> assertEquals(3, fStat.get(3)),
                    () -> assertEquals(5, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Sort descending")
        void sortDsc() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            FStat results = fStat.sort(false);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(3, fStat.get(1)),
                    () -> assertEquals(1, fStat.get(2)),
                    () -> assertEquals(-2, fStat.get(3)),
                    () -> assertEquals(-2, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Is unique")
        void isUnique() {
            FStat fStat = factory.getFStat();

            fStat.add(1.1, 1.2, 1.3, 1.4, 1.5, 2.1, 2.2, 2.3, 2.4, 2.5);

            assertTrue(fStat.allDistinct());

            fStat.add(2.5);

            assertFalse(fStat.allDistinct());
        }

        @Test
        @DisplayName("Filter")
        void filter() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            int count = fStat.filter(e -> e != 3);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, count),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(5, fStat.get(2)),
                    () -> assertEquals(-2, fStat.get(3))
            );
        }

        @Test
        @DisplayName("Filter - History (static)")
        void filterHistoryStatic() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(3);
            fStat.add(-4);
            fStat.add(5);

            int count = fStat.filter(false, (x0, x1) -> x0 > 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(2, count),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(-4, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Filter - History (dynamic)")
        void filterHistoryDynamic() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(3);
            fStat.add(-4);
            fStat.add(5);

            int count = fStat.filter(true, (x0, x1) -> x0 > 0);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, count),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1))
            );
        }

        @Test
        @DisplayName("Remove NaN")
        void removeNaN() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(3);
            fStat.add(-4);
            fStat.add(5);

            fStat.set(1, Double.NaN);
            fStat.set(4, Double.NaN);

            var results = fStat.removeNaN();

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(3, fStat.get(1)),
                    () -> assertEquals(-4, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Replace with NaN")
        void replaceWithNaN() {
            FStat fStat = factory.getFStat();

            fStat.add(1, -2, 5, 3, -2);

            var results = fStat.replaceWithNaN(e -> e != 3);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(5, fStat.get(2)),
                    () -> assertEquals(Double.NaN, fStat.get(3)),
                    () -> assertEquals(-2, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Replace with NaN - History (static)")
        void replaceWithNaNHistoryStatic() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(3);
            fStat.add(-4);
            fStat.add(5);

            var results = fStat.replaceWithNaN(false, (x0, x1) -> x0 > 0);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(Double.NaN, fStat.get(2)),
                    () -> assertEquals(-4, fStat.get(3)),
                    () -> assertEquals(Double.NaN, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Replace with NaN - History (dynamic)")
        void replaceWithNaNHistoryDynamic() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(3);
            fStat.add(-4);
            fStat.add(5);

            var results = fStat.replaceWithNaN(true, (x0, x1) -> x0 > 0);

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(Double.NaN, fStat.get(2)),
                    () -> assertEquals(Double.NaN, fStat.get(3)),
                    () -> assertEquals(Double.NaN, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Replace same with NaN")
        void replaceSameWithNaN() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(-2);
            fStat.add(-2);
            fStat.add(3);
            fStat.add(4);
            fStat.add(1);
            fStat.add(1);

            var results = fStat.replaceSameWithNaN();

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(Double.NaN, fStat.get(2)),
                    () -> assertEquals(Double.NaN, fStat.get(3)),
                    () -> assertEquals(3, fStat.get(4)),
                    () -> assertEquals(4, fStat.get(5)),
                    () -> assertEquals(1, fStat.get(6)),
                    () -> assertEquals(Double.NaN, fStat.get(7))
            );
        }

        @Test
        @DisplayName("Replace decreasing with NaN")
        void replaceDecreasingWithNaN() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-4);
            fStat.add(-2);
            fStat.add(-2);
            fStat.add(1);
            fStat.add(4);
            fStat.add(3);
            fStat.add(5);

            var results = fStat.replaceDecreasingWithNaN();

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(Double.NaN, fStat.get(1)),
                    () -> assertEquals(Double.NaN, fStat.get(2)),
                    () -> assertEquals(Double.NaN, fStat.get(3)),
                    () -> assertEquals(1, fStat.get(4)),
                    () -> assertEquals(4, fStat.get(5)),
                    () -> assertEquals(Double.NaN, fStat.get(6)),
                    () -> assertEquals(5, fStat.get(7))
            );
        }

        @Test
        @DisplayName("Replace increasing with NaN")
        void replaceIncreasingWithNaN() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(-4);
            fStat.add(-2);
            fStat.add(-5);
            fStat.add(4);
            fStat.add(-5);
            fStat.add(-6);

            var results = fStat.replaceIncreasingWithNaN();

            Assertions.assertAll("Validate results",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(-2, fStat.get(1)),
                    () -> assertEquals(-4, fStat.get(2)),
                    () -> assertEquals(Double.NaN, fStat.get(3)),
                    () -> assertEquals(-5, fStat.get(4)),
                    () -> assertEquals(Double.NaN, fStat.get(5)),
                    () -> assertEquals(-5, fStat.get(6)),
                    () -> assertEquals(-6, fStat.get(7))
            );
        }

        @Test
        @DisplayName("Mutate")
        void mutate() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            fStat.mutate(e -> e * 2);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(2, fStat.get(0)),
                    () -> assertEquals(-4, fStat.get(1)),
                    () -> assertEquals(10, fStat.get(2)),
                    () -> assertEquals(6, fStat.get(3)),
                    () -> assertEquals(-4, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Mutate - History (static)")
        void mutateHistoryStatic() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(2);
            fStat.add(3);
            fStat.add(4);
            fStat.add(5);

            fStat.mutate(false, Double::sum);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(3, fStat.get(1)),
                    () -> assertEquals(5, fStat.get(2)),
                    () -> assertEquals(7, fStat.get(3)),
                    () -> assertEquals(9, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Mutate - History (dynamic)")
        void mutateHistoryDynamic() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(2);
            fStat.add(3);
            fStat.add(4);
            fStat.add(5);

            fStat.mutate(true, Double::sum);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(1, fStat.get(0)),
                    () -> assertEquals(3, fStat.get(1)),
                    () -> assertEquals(6, fStat.get(2)),
                    () -> assertEquals(10, fStat.get(3)),
                    () -> assertEquals(15, fStat.get(4))
            );
        }

        @Test
        @DisplayName("Get min")
        void getMin() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            assertEquals(-2, fStat.min());
        }

        @Test
        @DisplayName("Get max")
        void getMax() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            assertEquals(5, fStat.max());
        }

        @Test
        @DisplayName("Get range")
        void getRange() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double range = fStat.range();

            assertEquals(15, range, 1E-4);
        }

        @Test
        @DisplayName("Get midrange")
        void getMidrange() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double range = fStat.midrange();

            assertEquals(2.5, range, 1E-4);
        }

        @Test
        @DisplayName("Sum")
        void sum() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            assertEquals(5, fStat.sum());
        }

        @Test
        @DisplayName("Sum or squares")
        void sumOfSquares() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double ss = fStat.ss();

            assertEquals(135.428571, ss, 1E-4);
        }

        @Test
        @DisplayName("Sum or squares (provided)")
        void sumOfSquaresProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double ss = fStat.ss(3.28571429);

            assertEquals(135.428571, ss, 1E-4);
        }

        @Test
        @DisplayName("Mean")
        void mean() {
            FStat fStat = factory.getFStat();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            assertEquals(1, fStat.mean());
        }

        @Test
        @DisplayName("Mean absolute deviation")
        void meanAbsoluteDeviation() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double mad = fStat.mad();

            assertEquals(3.3877551, mad, 1E-4);
        }

        @Test
        @DisplayName("Mean absolute deviation (provided)")
        void meanAbsoluteDeviationProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double mad = fStat.mad(3.28571429);

            assertEquals(3.3877551, mad, 1E-4);
        }

        @Test
        @DisplayName("Root mean square")
        void rootMeanSquare() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double rms = fStat.rms();

            assertEquals(5.4902511, rms, 1E-4);
        }

        @Test
        @DisplayName("Percentile - Even")
        void percentileEven() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertThrows(IllegalArgumentException.class, () -> fStat.percentile(-1));
            assertThrows(IllegalArgumentException.class, () -> fStat.percentile(101));
            assertEquals(0, fStat.percentile(0));
            assertEquals(2.25, fStat.percentile(25), 1E-6);
            assertEquals(4.5, fStat.percentile(50), 1E-6);
            assertEquals(6.75, fStat.percentile(75), 1E-6);
            assertEquals(9, fStat.percentile(100));
        }

        @Test
        @DisplayName("Percentile - Odd")
        void percentileOdd() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 10, 8, 3, 1, 4, 9, 7, 0, 2, 6);

            assertThrows(IllegalArgumentException.class, () -> fStat.percentile(-1));
            assertThrows(IllegalArgumentException.class, () -> fStat.percentile(101));
            assertEquals(0, fStat.percentile(0));
            assertEquals(2.5, fStat.percentile(25), 1E-6);
            assertEquals(5, fStat.percentile(50), 1E-6);
            assertEquals(7.5, fStat.percentile(75), 1E-6);
            assertEquals(10, fStat.percentile(100));
        }

        @Test
        @DisplayName("Median")
        void median() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(50), fStat.median(), 1E-6);
        }

        @Test
        @DisplayName("Q1")
        void q1() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(25), fStat.q1(), 1E-6);
        }

        @Test
        @DisplayName("Q2")
        void q2() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(50), fStat.q2(), 1E-6);
        }

        @Test
        @DisplayName("Q3")
        void q3() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(75), fStat.q3(), 1E-6);
        }

        @Test
        @DisplayName("Mode")
        void mode() {
            FStat fStat = factory.getFStat();

            double[] resA = fStat.mode();
            assertEquals(0, resA.length);

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            double[] resB = fStat.mode();
            assertEquals(10, resB.length);

            fStat.clear();
            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 7);

            double[] resC = fStat.mode();
            assertEquals(1, resC.length);
            assertEquals(7, resC[0]);

            fStat.clear();
            fStat.add(5, 2, 3, 2, 8, 7, 1, 4, 6, 7);

            double[] resD = fStat.mode();
            assertEquals(2, resD.length);
            assertEquals(2, resD[0]);
            assertEquals(7, resD[1]);

            fStat.clear();
            fStat.add(1, 9, 8, 2, 8, 7, 1, 4, 6, 8);

            double[] resE = fStat.mode();
            assertEquals(1, resE.length);
            assertEquals(8, resE[0]);
        }

        @Test
        @DisplayName("Mode")
        void iqr() {
            FStat fStat = factory.getFStat();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            double iqr = fStat.midspread();

            assertEquals(fStat.q3() - fStat.q1(), iqr, 1E-6);
        }

        @Test
        @DisplayName("Variance - Sample")
        void varianceSample() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.var(true));

            fStat.add(1);

            assertThrows(IllegalStateException.class, () -> fStat.var(true));

            fStat.add(7, 4, 10, -5, 2, 4);

            double vars = fStat.var(true);

            assertEquals(22.571429, vars, 1E-4);
        }

        @Test
        @DisplayName("Variance - Sample (provided)")
        void varianceSampleProvided() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.var(true, 3.2857143));

            fStat.add(1);

            assertThrows(IllegalStateException.class, () -> fStat.var(true, 3.2857143));

            fStat.add(7, 4, 10, -5, 2, 4);

            double vars = fStat.var(true, 3.2857143);

            assertEquals(22.571429, vars, 1E-4);
        }

        @Test
        @DisplayName("Variance - Population")
        void variancePopulation() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.var(false));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double varp = fStat.var(false);

            assertEquals(19.346939, varp, 1E-4);
        }

        @Test
        @DisplayName("Variance - Population (provided)")
        void variancePopulationProvided() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.var(false, 3.2857143));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double varp = fStat.var(false, 3.2857143);

            assertEquals(19.346939, varp, 1E-4);
        }

        @Test
        @DisplayName("Standard deviation - Sample")
        void standardDeviationSample() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.std(true));

            fStat.add(1);

            assertThrows(IllegalStateException.class, () -> fStat.std(true));

            fStat.add(7, 4, 10, -5, 2, 4);

            double stds = fStat.std(true);

            assertEquals(4.7509398, stds, 1E-4);
        }

        @Test
        @DisplayName("Standard deviation - Sample (provided)")
        void standardDeviationSampleProvided() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.std(true, 3.2857143));

            fStat.add(1);

            assertThrows(IllegalStateException.class, () -> fStat.std(true, 3.2857143));

            fStat.add(7, 4, 10, -5, 2, 4);

            double stds = fStat.std(true, 3.2857143);

            assertEquals(4.7509398, stds, 1E-4);
        }

        @Test
        @DisplayName("Standard deviation - Population")
        void standardDeviationPopulation() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.std(false));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double stdp = fStat.std(false);

            assertEquals(4.3985155, stdp, 1E-4);
        }

        @Test
        @DisplayName("Standard deviation - Population (provided)")
        void standardDeviationPopulationProvided() {
            FStat fStat = factory.getFStat();

            assertThrows(IllegalStateException.class, () -> fStat.std(false, 3.2857143));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double stdp = fStat.std(false, 3.2857143);

            assertEquals(4.3985155, stdp, 1E-4);
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.normalize(3.2857143, 4.3985155);

            assertEquals(-0.51965584752401, fStat.get(0), 1E-6);
            assertEquals(0.84444074370091, fStat.get(1), 1E-6);
            assertEquals(0.16239244808845, fStat.get(2), 1E-6);
            assertEquals(1.5264890393134, fStat.get(3), 1E-6);
            assertEquals(-1.8837524387489, fStat.get(4), 1E-6);
            assertEquals(-0.29230641565319, fStat.get(5), 1E-6);
            assertEquals(0.16239244808845, fStat.get(6), 1E-6);

            double mean = fStat.mean();
            double std = fStat.std(false);

            assertSame(fStat, results);
            assertEquals(0, mean, 1E-6);
            assertEquals(1, std, 1E-6);
        }

        @Test
        @DisplayName("Normalize - Sample")
        void normalizeSample() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.normalize(true);

            assertEquals(-0.48110782207764, fStat.get(0), 1E-6);
            assertEquals(0.781800202983, fStat.get(1), 1E-6);
            assertEquals(0.15034619045268, fStat.get(2), 1E-6);
            assertEquals(1.4132542155133, fStat.get(3), 1E-6);
            assertEquals(-1.7440158471383, fStat.get(4), 1E-6);
            assertEquals(-0.2706231512342, fStat.get(5), 1E-6);
            assertEquals(0.15034619045268, fStat.get(6), 1E-6);

            double mean = fStat.mean();
            double std = fStat.std(true);

            assertSame(fStat, results);
            assertEquals(0, mean, 1E-6);
            assertEquals(1, std, 1E-6);
        }

        @Test
        @DisplayName("Rescale")
        void rescale() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.rescale();

            assertSame(fStat, results);
            assertEquals(1, fStat.max(), 1E-6);
            assertEquals(0, fStat.min(), 1E-6);
        }

        @Test
        @DisplayName("Rescale with range")
        void rescaleWithRange() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.rescale(-1, 5);

            assertSame(fStat, results);
            assertEquals(5, fStat.max(), 1E-6);
            assertEquals(-1, fStat.min(), 1E-6);
        }

        @Test
        @DisplayName("Rescale with range (fail)")
        void rescaleWithRangeFail() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            assertThrows(IllegalArgumentException.class, () -> fStat.rescale(1, -2));
        }

        @Test
        @DisplayName("Absolute")
        void absolute() {
            FStat fStat = factory.getFStat();

            fStat.add(1, -7, -4, 10, -5, 2, 4);

            FStat results = fStat.absolute();

            assertSame(fStat, results);
            assertEquals(1, fStat.get(0), 1E-6);
            assertEquals(7, fStat.get(1), 1E-6);
            assertEquals(4, fStat.get(2), 1E-6);
            assertEquals(10, fStat.get(3), 1E-6);
            assertEquals(5, fStat.get(4), 1E-6);
            assertEquals(2, fStat.get(5), 1E-6);
            assertEquals(4, fStat.get(6), 1E-6);
        }

        @Test
        @DisplayName("Distribute")
        void distribute() {
            FStat fStat = factory.getFStat(2, 1, 0, 1, 2);

            FStat results = fStat.distribute();

            Assertions.assertAll("Test values",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(2d / 6, fStat.get(0), 1E-4),
                    () -> assertEquals(1d / 6, fStat.get(1), 1E-4),
                    () -> assertEquals(0d / 6, fStat.get(2), 1E-4),
                    () -> assertEquals(1d / 6, fStat.get(3), 1E-4),
                    () -> assertEquals(2d / 6, fStat.get(4), 1E-4)
            );
        }

        @Test
        @DisplayName("Log")
        void log() {
            FStat fStat = factory.getFStat(4, 5, 6);

            FStat results = fStat.log(Math.E);

            Assertions.assertAll("Check values",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(Math.log(4), fStat.get(0), 1E-4),
                    () -> assertEquals(Math.log(5), fStat.get(1), 1E-4),
                    () -> assertEquals(Math.log(6), fStat.get(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Ln")
        void ln() {
            FStat fStat = factory.getFStat(4, 5, 6);

            FStat results = fStat.ln();

            Assertions.assertAll("Check values",
                    () -> assertSame(fStat, results),
                    () -> assertEquals(Math.log(4), fStat.get(0), 1E-4),
                    () -> assertEquals(Math.log(5), fStat.get(1), 1E-4),
                    () -> assertEquals(Math.log(6), fStat.get(2), 1E-4)
            );
        }

        @Test
        @DisplayName("Invert order")
        void invertOrder() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.invert();

            assertSame(fStat, results);
            assertEquals(4, fStat.get(0), 1E-6);
            assertEquals(2, fStat.get(1), 1E-6);
            assertEquals(-5, fStat.get(2), 1E-6);
            assertEquals(10, fStat.get(3), 1E-6);
            assertEquals(4, fStat.get(4), 1E-6);
            assertEquals(7, fStat.get(5), 1E-6);
            assertEquals(1, fStat.get(6), 1E-6);
        }

        @Test
        @DisplayName("Invert values")
        void invertValues() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.mirror();

            assertSame(fStat, results);
            assertEquals(-1, fStat.get(0), 1E-6);
            assertEquals(-7, fStat.get(1), 1E-6);
            assertEquals(-4, fStat.get(2), 1E-6);
            assertEquals(-10, fStat.get(3), 1E-6);
            assertEquals(5, fStat.get(4), 1E-6);
            assertEquals(-2, fStat.get(5), 1E-6);
            assertEquals(-4, fStat.get(6), 1E-6);
        }

        @Test
        @DisplayName("Remove bias")
        void removeBias() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.removeBias();
            assertSame(fStat, results);

            assertEquals(0, fStat.mean(), 1E-6);
        }

        @Test
        @DisplayName("Remove bias (provided)")
        void removeBiasProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.removeBias(3.28571429);

            assertSame(fStat, results);
            assertEquals(0, fStat.mean(), 1E-6);
        }

        @Test
        @DisplayName("Remove outliers - Sample")
        void removeOutliersSample() {
            FStat fStat = factory.getFStat();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            int count = fStat.removeOutliers(true, 1.5);

            assertEquals(2, count);
            assertEquals(7, fStat.size());
        }

        @Test
        @DisplayName("Remove outliers - Population")
        void removeOutliersPopulation() {
            FStat fStat = factory.getFStat();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            int count = fStat.removeOutliers(2.55555556, 10.9099852, 1.5);

            assertEquals(2, count);
            assertEquals(7, fStat.size());
        }

        @Test
        @DisplayName("Remove outliers (provided)")
        void removeOutliersProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            int count = fStat.removeOutliers(false, 1.5);

            assertEquals(2, count);
            assertEquals(7, fStat.size());
        }

        @Test
        @DisplayName("Replace outliers with NaN - Sample")
        void replaceOutliersWithNaNSample() {
            FStat fStat = factory.getFStat();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            var results = fStat.replaceOutliersWithNaN(true, 1.5);

            long count = fStat.getRefCore().stream().filter(e -> Double.isNaN(e)).toList().size();

            assertSame(fStat, results);
            assertEquals(2, count);
            assertEquals(9, fStat.size());
        }

        @Test
        @DisplayName("Replace outliers with NaN - Population")
        void replaceOutliersWithNaNPopulation() {
            FStat fStat = factory.getFStat();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            var results = fStat.replaceOutliersWithNaN(2.55555556, 10.9099852, 1.5);

            long count = fStat.getRefCore().stream().filter(e -> Double.isNaN(e)).toList().size();

            assertSame(fStat, results);
            assertEquals(2, count);
            assertEquals(9, fStat.size());
        }

        @Test
        @DisplayName("Replace outliers with NaN (provided)")
        void replaceOutliersWithNaNProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            fStat.replaceOutliersWithNaN(false, 1.5);

            long count = fStat.getRefCore().stream().filter(e -> Double.isNaN(e)).toList().size();

            assertEquals(2, count);
            assertEquals(9, fStat.size());
        }

        @Test
        @DisplayName("Normalize - Population")
        void normalizePopulation() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FStat results = fStat.normalize(false);

            assertSame(fStat, results);
            assertEquals(-0.51965584752401, fStat.get(0), 1E-6);
            assertEquals(0.84444074370091, fStat.get(1), 1E-6);
            assertEquals(0.16239244808845, fStat.get(2), 1E-6);
            assertEquals(1.5264890393134, fStat.get(3), 1E-6);
            assertEquals(-1.8837524387489, fStat.get(4), 1E-6);
            assertEquals(-0.29230641565319, fStat.get(5), 1E-6);
            assertEquals(0.16239244808845, fStat.get(6), 1E-6);

            double mean = fStat.mean();
            double std = fStat.std(false);

            assertEquals(0, mean, 1E-6);
            assertEquals(1, std, 1E-6);
        }

        @Test
        @DisplayName("Skewness - Sample")
        void skewnessSample() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(true);

            assertEquals(-0.496637253, skewness, 1E-4);
        }

        @Test
        @DisplayName("Skewness - Sample (provided)")
        void skewnessSampleProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(true, 3.28571429, 4.75093976);

            assertEquals(-0.496637253, skewness, 1E-4);
        }

        @Test
        @DisplayName("Skewness - Population")
        void skewnessPopulation() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(false);

            assertEquals(-0.383163959, skewness, 1E-4);
        }

        @Test
        @DisplayName("Skewness - Population (provided)")
        void skewnessPopulationProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(false, 3.28571429,  4.39851552);

            assertEquals(-0.383163959, skewness, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Sample")
        void kurtosisSample() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(true);

            assertEquals(6.38118891, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Sample (provided)")
        void kurtosisSampleProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(true, 3.28571429, 4.75093976);

            assertEquals(6.38118891, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Population")
        void kurtosisPopulation() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(false);

            assertEquals(2.65882871, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Population (provided)")
        void kurtosisPopulationProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(false, 3.28571429, 4.39851552);

            assertEquals(2.65882871, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Sample")
        void kurtosisExcessSample() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(true);

            assertEquals(0.981188912, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Sample (provided)")
        void kurtosisExcessSampleProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(true, 3.28571429, 4.75093976);

            assertEquals(0.981188912, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Population")
        void kurtosisExcessPopulation() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(false);

            assertEquals(-0.341171287, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Population (provided)")
        void kurtosisExcessPopulationProvided() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(false, 3.28571429, 4.39851552);

            assertEquals(-0.341171287, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("To FPlot linear")
        void toFPlotLinear() {
            FStat fStat = factory.getFStat();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FPlot fPlot = fStat.toFPlotLinear();

            assertEquals(0, fPlot.getX(0));
            assertEquals(1, fPlot.getX(1));
            assertEquals(2, fPlot.getX(2));
            assertEquals(3, fPlot.getX(3));
            assertEquals(4, fPlot.getX(4));
            assertEquals(5, fPlot.getX(5));
            assertEquals(6, fPlot.getX(6));

            assertEquals(1, fPlot.getY(0));
            assertEquals(7, fPlot.getY(1));
            assertEquals(4, fPlot.getY(2));
            assertEquals(10, fPlot.getY(3));
            assertEquals(-5, fPlot.getY(4));
            assertEquals(2, fPlot.getY(5));
            assertEquals(4, fPlot.getY(6));
        }

        @Test
        @DisplayName("To FPlot pie chart")
        void toFPlotPieChart() {
            FStat fStat = factory.getFStat();

            fStat.add(-1, 0, 1, 2, 2, 3, 4, 5, 5, 5, 8);

            FPlot fPlot = fStat.toFPlotPieChart();

            assertEquals(5, fPlot.getX(0));
            assertEquals(2, fPlot.getX(1));
            assertEquals(-1, fPlot.getX(2));
            assertEquals(0, fPlot.getX(3));
            assertEquals(1, fPlot.getX(4));
            assertEquals(3, fPlot.getX(5));
            assertEquals(4, fPlot.getX(6));
            assertEquals(8, fPlot.getX(7));

            assertEquals(3, fPlot.getY(0));
            assertEquals(2, fPlot.getY(1));
            assertEquals(1, fPlot.getY(2));
            assertEquals(1, fPlot.getY(3));
            assertEquals(1, fPlot.getY(4));
            assertEquals(1, fPlot.getY(5));
            assertEquals(1, fPlot.getY(6));
            assertEquals(1, fPlot.getY(7));
        }

        @Test
        @DisplayName("To FPlot histogram with division")
        void toFPlotHistogramWithDivision() {
            FStat fStat = factory.getFStat();

            fStat.add(0, 0.2, 0.5, 0.9, 1.4, 2.3, 2.6, 3.5);

            FPlot fPlot = fStat.toFPlotHistogram(0, 5, 5);

            assertEquals(0, fPlot.getX(0));
            assertEquals(1, fPlot.getX(1));
            assertEquals(2, fPlot.getX(2));
            assertEquals(3, fPlot.getX(3));
            assertEquals(4, fPlot.getX(4));

            assertEquals(4, fPlot.getY(0));
            assertEquals(1, fPlot.getY(1));
            assertEquals(2, fPlot.getY(2));
            assertEquals(1, fPlot.getY(3));
            assertEquals(0, fPlot.getY(4));
        }

        @Test
        @DisplayName("To FPlot histogram with step")
        void toFPlotHistogramWithStep() {
            FStat fStat = factory.getFStat();

            fStat.add(0, 0.2, 0.5, 0.9, 1.4, 2.3, 2.6, 3.5);

            FPlot fPlot = fStat.toFPlotHistogram(1);

            assertEquals(0, fPlot.getX(0));
            assertEquals(1, fPlot.getX(1));
            assertEquals(2, fPlot.getX(2));
            assertEquals(3, fPlot.getX(3));
            assertEquals(4, fPlot.getX(4));

            assertEquals(4, fPlot.getY(0));
            assertEquals(1, fPlot.getY(1));
            assertEquals(2, fPlot.getY(2));
            assertEquals(1, fPlot.getY(3));
            assertEquals(0, fPlot.getY(4));
        }

        @Test
        @DisplayName("Is similar (absolute)")
        void isSimilarAbs() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2, 3.2, 3.9, 5);

            FStat fStatB = factory.getFStat();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertTrue(fStat.isSimilarAbs(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Fail")
        void isSimilarAbsFail() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2, 3.3, 3.9, 5);

            FStat fStatB = factory.getFStat();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertFalse(fStat.isSimilarAbs(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Wrong length")
        void isSimilarAbsWrongLength() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2, 3.3, 3.9, 5, 6);

            FStat fStatB = factory.getFStat();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarAbs(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Negative threshold")
        void isSimilarAbsNegativeThreshold() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2, 3.2, 3.9, 5);

            FStat fStatB = factory.getFStat();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarAbs(-1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Empty set")
        void isSimilarAbsEmptySet() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarAbs(1));
        }

        @Test
        @DisplayName("Is similar (relative)")
        void isSimilarRel() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2.1, 3.2, 3.9, 5);

            FStat fStatB = factory.getFStat();
            fStatB.add(1, 2, 3, 4.1, 5.4);

            assertTrue(fStat.isSimilarRel(0.1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Fail")
        void isSimilarRelFail() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2.1, 3.2, 3.9, 5);

            FStat fStatB = factory.getFStat();
            fStatB.add(1.2, 2, 3, 4.1, 5.4);

            assertFalse(fStat.isSimilarRel(0.1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Wrong length")
        void isSimilarRelWrongLength() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2, 3.3, 3.9, 5, 6);

            FStat fStatB = factory.getFStat();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarRel(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Negative threshold")
        void isSimilarRelNegativeThreshold() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            FStat fStatA = factory.getFStat();
            fStatA.add(1, 2, 3.2, 3.9, 5);

            FStat fStatB = factory.getFStat();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarRel(-1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Empty set")
        void isSimilarRelEmptySet() {
            FStat fStat = factory.getFStat();
            fStat.add(1, 2, 3, 4, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarRel(1));
        }

        @Test
        @DisplayName("Deduplicate")
        void deduplicate() {
            FStat data = factory.getFStat();

            data.add(1, 2, 3, 1, 1, 5, 2, 3);

            int count = data.deduplicate();

            data.sort(false);

            assertEquals(4, count);
            assertEquals(4, data.size());
            assertEquals(5, data.get(0));
            assertEquals(3, data.get(1));
            assertEquals(2, data.get(2));
            assertEquals(1, data.get(3));
        }
    }

    @Nested
    @DisplayName("Meta")
    class FStatMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            FStat fStat = factory.getFStat();

            FStat results = fStat.setName("test");

            assertSame(fStat, results);
            assertEquals("test", fStat.getName());
        }
    }
}
