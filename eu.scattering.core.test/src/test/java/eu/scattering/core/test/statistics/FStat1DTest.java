package eu.scattering.core.test.statistics;

import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.base.FStat1D;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FStat1D")
public class FStat1DTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Basic")
    class FStat1DBasicTest {

        @Test
        @DisplayName("Create")
        void create() {
            FStat1D fStat = factory.getFStat1D();

            assertEquals(0, fStat.size());
        }

        @Test
        @DisplayName("Create with array - int")
        void createWithArrayInt() {
            int[] values = new int[]{1, 2, 3, 4, 5};

            FStat1D fStat = factory.getFStat1D(values);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(values.length, fStat.size()),
                    () -> assertEquals(values[0], fStat.get(0)),
                    () -> assertEquals(values[1], fStat.get(1)),
                    () -> assertEquals(values[2], fStat.get(2)),
                    () -> assertEquals(values[3], fStat.get(3)),
                    () -> assertEquals(values[4], fStat.get(4))
            );
        }

        @Test
        @DisplayName("Create with array - double")
        void createWithArrayDouble() {
            double[] values = new double[]{1, 2, 3, 4, 5};

            FStat1D fStat = factory.getFStat1D(values);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(values.length, fStat.size()),
                    () -> assertEquals(values[0], fStat.get(0)),
                    () -> assertEquals(values[1], fStat.get(1)),
                    () -> assertEquals(values[2], fStat.get(2)),
                    () -> assertEquals(values[3], fStat.get(3)),
                    () -> assertEquals(values[4], fStat.get(4))
            );
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

            FStat1D fStat = factory.getFStat1D(values);

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
        @DisplayName("Export to array")
        void exportToArray() {
            double[] values = new double[]{1, 2, 3, 4, 5};

            FStat1D fStat = factory.getFStat1D(values);

            double[] results = fStat.toArray();

            Assertions.assertAll("Validate results",
                    () -> assertNotSame(values, results),
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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5);
            fStat.add(7);
            fStat.add(9);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(7, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Add vararg")
        void addVararg() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5, 7, 9);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(7, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Add with collision")
        void addWithCollision() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add((x) -> x + 1, 5);
            fStat.add((x) -> x + 1, 7);
            fStat.add((x) -> x + 1, 9);

            fStat.add((x) -> x + 1, 5);
            fStat.add((x) -> x + 1, 7);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(6, fStat.get(0)),
                    () -> assertEquals(8, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Add vararg with collision")
        void addVarargWithCollision() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add((x) -> x + 1, 5, 7, 9);
            fStat.add((x) -> x + 1, 5, 7);

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(6, fStat.get(0)),
                    () -> assertEquals(8, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }

        @Test
        @DisplayName("Clear")
        void clear() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5);
            fStat.add(7);
            fStat.add(9);

            fStat.clear();

            assertEquals(0, fStat.size());
        }

        @Test
        @DisplayName("Set")
        void set() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add((x) -> x + 1, 5);
            fStat.add((x) -> x + 1, 7);
            fStat.add((x) -> x + 1, 9);

            fStat.set(1, 1);

            assertThrows(IndexOutOfBoundsException.class, () -> fStat.set(-1, 0));
            assertThrows(IndexOutOfBoundsException.class, () -> fStat.set(3, 0));

            Assertions.assertAll("Validate results",
                    () -> assertEquals(3, fStat.size()),
                    () -> assertEquals(5, fStat.get(0)),
                    () -> assertEquals(1, fStat.get(1)),
                    () -> assertEquals(9, fStat.get(2))
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core")
    class FStat1DCoreTest {

        @Test
        @DisplayName("Is equal")
        void isEqual() {
            FStat1D fStat1 = factory.getFStat1D();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            FStat1D fStat2 = factory.getFStat1D();

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
            FStat1D fStat1 = factory.getFStat1D();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            FStat1D fStat2 = factory.getFStat1D();

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
            FStat1D fStat1 = factory.getFStat1D();

            fStat1.add(1);
            fStat1.add(-2);
            fStat1.add(5);
            fStat1.add(3);
            fStat1.add(-2);

            JSONObject json = fStat1.toJSON();

            FStat1D fStat2 = factory.getFStat1D(json);

            assertTrue(fStat1.isEqual(fStat2));
            assertTrue(fStat2.isEqual(fStat1));
        }

        @Test
        @DisplayName("Iterate")
        void iterate() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, -2, 5, 3, -2);

            FStat1D results = factory.getFStat1D();

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
    @Tag("Advanced")
    @DisplayName("Advanced")
    class FStat1DAdvancedTest {

        @Test
        @DisplayName("Sort ascending")
        void sortAsc() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            fStat.sort(true);

            Assertions.assertAll("Validate results",
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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1);
            fStat.add(-2);
            fStat.add(5);
            fStat.add(3);
            fStat.add(-2);

            fStat.sort(false);

            Assertions.assertAll("Validate results",
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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1.1, 1.2, 1.3, 1.4, 1.5, 2.1, 2.2, 2.3, 2.4, 2.5);

            assertTrue(fStat.isUnique());

            fStat.add(2.5);

            assertFalse(fStat.isUnique());
        }

        @Test
        @DisplayName("Filter")
        void filter() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double range = fStat.range();

            assertEquals(15, range, 1E-4);
        }

        @Test
        @DisplayName("Get midrange")
        void getMidrange() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double range = fStat.midrange();

            assertEquals(2.5, range, 1E-4);
        }

        @Test
        @DisplayName("Sum")
        void sum() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double ss = fStat.ss();

            assertEquals(135.428571, ss, 1E-4);
        }

        @Test
        @DisplayName("Sum or squares (provided)")
        void sumOfSquaresProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double ss = fStat.ss(3.28571429);

            assertEquals(135.428571, ss, 1E-4);
        }

        @Test
        @DisplayName("Mean")
        void mean() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double mad = fStat.mad();

            assertEquals(3.3877551, mad, 1E-4);
        }

        @Test
        @DisplayName("Mean absolute deviation (provided)")
        void meanAbsoluteDeviationProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double mad = fStat.mad(3.28571429);

            assertEquals(3.3877551, mad, 1E-4);
        }

        @Test
        @DisplayName("Root mean square")
        void rootMeanSquare() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double rms = fStat.rms();

            assertEquals(5.4902511, rms, 1E-4);
        }

        @Test
        @DisplayName("Percentile - Even")
        void percentileEven() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(50), fStat.median(), 1E-6);
        }

        @Test
        @DisplayName("Q1")
        void q1() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(25), fStat.q1(), 1E-6);
        }

        @Test
        @DisplayName("Q2")
        void q2() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(50), fStat.q2(), 1E-6);
        }

        @Test
        @DisplayName("Q3")
        void q3() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            assertEquals(fStat.percentile(75), fStat.q3(), 1E-6);
        }

        @Test
        @DisplayName("Mode")
        void mode() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(5, 9, 3, 2, 8, 7, 1, 4, 6, 0);

            double iqr = fStat.midspread();

            assertEquals(fStat.q3() - fStat.q1(), iqr, 1E-6);
        }

        @Test
        @DisplayName("Variance - Sample")
        void varianceSample() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            assertThrows(IllegalStateException.class, () -> fStat.var(false));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double varp = fStat.var(false);

            assertEquals(19.346939, varp, 1E-4);
        }

        @Test
        @DisplayName("Variance - Population (provided)")
        void variancePopulationProvided() {
            FStat1D fStat = factory.getFStat1D();

            assertThrows(IllegalStateException.class, () -> fStat.var(false, 3.2857143));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double varp = fStat.var(false, 3.2857143);

            assertEquals(19.346939, varp, 1E-4);
        }

        @Test
        @DisplayName("Standard deviation - Sample")
        void standardDeviationSample() {
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

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
            FStat1D fStat = factory.getFStat1D();

            assertThrows(IllegalStateException.class, () -> fStat.std(false));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double stdp = fStat.std(false);

            assertEquals(4.3985155, stdp, 1E-4);
        }

        @Test
        @DisplayName("Standard deviation - Population (provided)")
        void standardDeviationPopulationProvided() {
            FStat1D fStat = factory.getFStat1D();

            assertThrows(IllegalStateException.class, () -> fStat.std(false, 3.2857143));

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double stdp = fStat.std(false, 3.2857143);

            assertEquals(4.3985155, stdp, 1E-4);
        }

        @Test
        @DisplayName("Normalize")
        void normalize() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.normalize(3.2857143, 4.3985155);

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
        @DisplayName("Normalize - Sample")
        void normalizeSample() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.normalize(true);

            assertEquals(-0.48110782207764, fStat.get(0), 1E-6);
            assertEquals(0.781800202983, fStat.get(1), 1E-6);
            assertEquals(0.15034619045268, fStat.get(2), 1E-6);
            assertEquals(1.4132542155133, fStat.get(3), 1E-6);
            assertEquals(-1.7440158471383, fStat.get(4), 1E-6);
            assertEquals(-0.2706231512342, fStat.get(5), 1E-6);
            assertEquals(0.15034619045268, fStat.get(6), 1E-6);

            double mean = fStat.mean();
            double std = fStat.std(true);

            assertEquals(0, mean, 1E-6);
            assertEquals(1, std, 1E-6);
        }

        @Test
        @DisplayName("Invert order")
        void invertOrder() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.invert();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.mirror();

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.removeBias();

            assertEquals(0, fStat.mean(), 1E-6);
        }

        @Test
        @DisplayName("Remove bias (provided)")
        void removeBiasProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.removeBias(3.28571429);

            assertEquals(0, fStat.mean(), 1E-6);
        }

        @Test
        @DisplayName("Remove outliers - Sample")
        void removeOutliersSample() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            int count = fStat.removeOutliers(true, 1.5);

            assertEquals(2, count);
            assertEquals(7, fStat.size());
        }

        @Test
        @DisplayName("Remove outliers - Population")
        void removeOutliersPopulation() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            int count = fStat.removeOutliers(2.55555556, 10.9099852, 1.5);

            assertEquals(2, count);
            assertEquals(7, fStat.size());
        }

        @Test
        @DisplayName("Remove outliers (provided)")
        void removeOutliersProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            int count = fStat.removeOutliers(false, 1.5);

            assertEquals(2, count);
            assertEquals(7, fStat.size());
        }

        @Test
        @DisplayName("Replace outliers with NaN - Sample")
        void replaceOutliersWithNaNSample() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            var results = fStat.replaceOutliersWithNaN(true, 1.5);

            long count = fStat.getData().stream().filter(e -> Double.isNaN(e)).toList().size();

            assertSame(fStat, results);
            assertEquals(2, count);
            assertEquals(9, fStat.size());
        }

        @Test
        @DisplayName("Replace outliers with NaN - Population")
        void replaceOutliersWithNaNPopulation() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            var results = fStat.replaceOutliersWithNaN(2.55555556, 10.9099852, 1.5);

            long count = fStat.getData().stream().filter(e -> Double.isNaN(e)).toList().size();

            assertSame(fStat, results);
            assertEquals(2, count);
            assertEquals(9, fStat.size());
        }

        @Test
        @DisplayName("Replace outliers with NaN (provided)")
        void replaceOutliersWithNaNProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-20, 1, 7, 4, 10, -5, 2, 4, 20);

            fStat.replaceOutliersWithNaN(false, 1.5);

            long count = fStat.getData().stream().filter(e -> Double.isNaN(e)).toList().size();

            assertEquals(2, count);
            assertEquals(9, fStat.size());
        }

        @Test
        @DisplayName("Normalize - Population")
        void normalizePopulation() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            fStat.normalize(false);

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
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(true);

            assertEquals(-0.496637253, skewness, 1E-4);
        }

        @Test
        @DisplayName("Skewness - Sample (provided)")
        void skewnessSampleProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(true, 3.28571429, 4.75093976);

            assertEquals(-0.496637253, skewness, 1E-4);
        }

        @Test
        @DisplayName("Skewness - Population")
        void skewnessPopulation() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(false);

            assertEquals(-0.383163959, skewness, 1E-4);
        }

        @Test
        @DisplayName("Skewness - Population (provided)")
        void skewnessPopulationProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double skewness = fStat.skewness(false, 3.28571429,  4.39851552);

            assertEquals(-0.383163959, skewness, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Sample")
        void kurtosisSample() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(true);

            assertEquals(6.38118891, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Sample (provided)")
        void kurtosisSampleProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(true, 3.28571429, 4.75093976);

            assertEquals(6.38118891, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Population")
        void kurtosisPopulation() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(false);

            assertEquals(2.65882871, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis - Population (provided)")
        void kurtosisPopulationProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosis = fStat.kurtosis(false, 3.28571429, 4.39851552);

            assertEquals(2.65882871, kurtosis, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Sample")
        void kurtosisExcessSample() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(true);

            assertEquals(0.981188912, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Sample (provided)")
        void kurtosisExcessSampleProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(true, 3.28571429, 4.75093976);

            assertEquals(0.981188912, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Population")
        void kurtosisExcessPopulation() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(false);

            assertEquals(-0.341171287, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("Kurtosis excess - Population (provided)")
        void kurtosisExcessPopulationProvided() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            double kurtosisExcess = fStat.kurtosisExcess(false, 3.28571429, 4.39851552);

            assertEquals(-0.341171287, kurtosisExcess, 1E-4);
        }

        @Test
        @DisplayName("To FPlot2D linear")
        void toFPlot2DLinear() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(1, 7, 4, 10, -5, 2, 4);

            FPlot2D fPlot = fStat.toFPlot2DLinear();

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
        @DisplayName("To FPlot2D pie chart")
        void toFPlot2DPieChart() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(-1, 0, 1, 2, 2, 3, 4, 5, 5, 5, 8);

            FPlot2D fPlot = fStat.toFPlot2DPieChart();

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
        @DisplayName("To FPlot2D histogram")
        void toFPlot2DHistogram() {
            FStat1D fStat = factory.getFStat1D();

            fStat.add(0, 0.2, 0.5, 0.9, 1.4, 2.3, 2.6, 3.5);

            FPlot2D fPlot = fStat.toFPlot2DHistogram(0, 5, 5);

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
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2, 3.2, 3.9, 5);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertTrue(fStat.isSimilarAbs(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Fail")
        void isSimilarAbsFail() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2, 3.3, 3.9, 5);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertFalse(fStat.isSimilarAbs(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Wrong length")
        void isSimilarAbsWrongLength() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2, 3.3, 3.9, 5, 6);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarAbs(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Negative threshold")
        void isSimilarAbsNegativeThreshold() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2, 3.2, 3.9, 5);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarAbs(-1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (absolute) - Empty set")
        void isSimilarAbsEmptySet() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarAbs(1));
        }

        @Test
        @DisplayName("Is similar (relative)")
        void isSimilarRel() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2.1, 3.2, 3.9, 5);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(1, 2, 3, 4.1, 5.4);

            assertTrue(fStat.isSimilarRel(0.1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Fail")
        void isSimilarRelFail() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2.1, 3.2, 3.9, 5);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(1.2, 2, 3, 4.1, 5.4);

            assertFalse(fStat.isSimilarRel(0.1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Wrong length")
        void isSimilarRelWrongLength() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2, 3.3, 3.9, 5, 6);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarRel(0.25, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Negative threshold")
        void isSimilarRelNegativeThreshold() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            FStat1D fStatA = factory.getFStat1D();
            fStatA.add(1, 2, 3.2, 3.9, 5);

            FStat1D fStatB = factory.getFStat1D();
            fStatB.add(0.9, 2, 3, 4.1, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarRel(-1, fStatA, fStatB));
        }

        @Test
        @DisplayName("Is similar (relative) - Empty set")
        void isSimilarRelEmptySet() {
            FStat1D fStat = factory.getFStat1D();
            fStat.add(1, 2, 3, 4, 5);

            assertThrows(IllegalArgumentException.class, () -> fStat.isSimilarRel(1));
        }

        @Test
        @DisplayName("Deduplicate")
        void deduplicate() {
            FStat1D data = factory.getFStat1D();

            data.add(1, 2, 3, 1, 1, 5, 2, 3);

            int count = data.removeDuplicates();

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
    @Tag("Meta")
    @DisplayName("Meta")
    class FStat1DMetaTest {

        @Test
        @DisplayName("Set name")
        void setName() {
            FStat1D fStat = factory.getFStat1D();

            fStat.setName("test");

            assertEquals("test", fStat.getName());
        }
    }
}
