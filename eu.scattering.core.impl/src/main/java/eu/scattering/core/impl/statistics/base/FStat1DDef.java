package eu.scattering.core.impl.statistics.base;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.base.FStat1D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FStat1DDef implements FStat1D {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "stat1D";
    private static final String JSON_DATA = "data";

    private final ScatFactory factory;

    private List<Double> data = new ArrayList<>();

    private String name = "";

    private FStat1DDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FStat1D create(ScatFactory factory) {

        return new FStat1DDef(factory);
    }

    public static FStat1D create(ScatFactory factory, int[] values) {
        FStat1D fStat = FStat1DDef.create(factory);

        for (double item : values) {
            fStat.add(item);
        }

        return fStat;
    }

    public static FStat1D create(ScatFactory factory, double[] values) {
        FStat1D fStat = FStat1DDef.create(factory);

        for (double item : values) {
            fStat.add(item);
        }

        return fStat;
    }

    public static FStat1D create(ScatFactory factory, Collection<Double> values) {
        FStat1D fStat = FStat1DDef.create(factory);

        for (Double item : values) {
            fStat.add(item);
        }

        return fStat;
    }

    public static FStat1D create(ScatFactory factory, JSONObject json) {
        FStat1D fStat1D = FStat1DDef.create(factory);

        JSONArray data = json.getJSONArray(JSON_DATA);
        for (int i = 0 ; i < data.length() ; i++) {
            fStat1D.add(data.getDouble(i));
        }

        return fStat1D;
    }

    @Override
    public FStat1D copy() {
        FStat1D results = factory.getFStat1D();

        getData().forEach(results::add);

        return results;
    }

    @Override
    public int size() {

        return getData().size();
    }

    @Override
    public void clear() {

        getData().clear();
    }

    @Override
    public void add(double value) {

        getData().add(value);
    }

    @Override
    public void add(double... value) {

        for (double v : value) {
            add(v);
        }
    }

    @Override
    public void add(Function<Double, Double> collision, double value) {
        boolean isUpdated = false;

        for (int i = 0 ; i < size() ; i++) {

            if (get(i) == value) {
                isUpdated = true;

                set(i, collision.apply(value));
            }
        }

        if (!isUpdated) {
            add(value);
        }
    }

    @Override
    public void add(Function<Double, Double> collision, double... value) {

        for (double v : value) {
            add(collision, v);
        }
    }

    @Override
    public double get(int index) {

        return getData().get(index);
    }

    @Override
    public void set(int index, double value) {

        getData().set(index, value);
    }

    @Override
    public double min() {
        double min = Double.POSITIVE_INFINITY;

        for (Double item : getData()) {

            if (item < min) {
                min = item;
            }
        }

        return min;
    }

    @Override
    public double max() {
        double max = Double.NEGATIVE_INFINITY;

        for (Double item : getData()) {

            if (item > max) {
                max = item;
            }
        }

        return max;
    }

    @Override
    public double range() {

        return max() - min();
    }

    @Override
    public double midrange() {

        return (min() + max()) / 2;
    }

    @Override
    public double sum() {

        return getData().stream().reduce(0d, Double::sum);
    }

    @Override
    public double mean() {

        return sum() / size();
    }

    @Override
    public double percentile(double p) {

        if (p < 0) {
            throw new IllegalArgumentException("The value cannot be lower than zero");
        }

        if (p > 100) {
            throw new IllegalArgumentException("The value cannot be greater than one hundred");
        }

        FStat1D copy = copy();

        copy.sort(true);

        double rank = (p / 100) * (copy.size() - 1);

        int integer =  (int) rank;
        double fractional = rank - integer;

        if (integer == 0) {
            return copy.get(0);
        }

        if (integer >= copy.size() - 1) {
            return copy.get(copy.size() - 1);
        }

        return copy.get(integer) + (fractional * (copy.get(integer + 1) - copy.get(integer)));
    }

    @Override
    public double q1() {

        return percentile(25);
    }

    @Override
    public double q2() {

        return percentile(50);
    }

    @Override
    public double q3() {

        return percentile(75);
    }

    @Override
    public double median() {

        return percentile(50);
    }

    @Override
    public double midspread() {

        return q3() - q1();
    }

    @Override
    public double[] mode() {

        if (size() == 0) {
            return new double[0];
        }

        FPlot2D fPlot = factory.getFPlot2D();

        for (double v : getData()) {
            fPlot.add((y1, y2) -> y1 + 1, v);
        }

        fPlot.sortY(false);

        double ref = fPlot.getY(0);

        int i = 1;
        for (; i < fPlot.size() ; i++) {
            if (fPlot.getY(i) != ref) {
                break;
            }
        }

        FStat1D fStat1D = factory.getFStat1D();

        for (int j = 0 ; j < i ; j++) {
            fStat1D.add(fPlot.getX(j));
        }

        fStat1D.sort(true);

        return fStat1D.toArray();
    }

    @Override
    public double rms() {
        double sum = 0;

        for (double value : getData()) {
            sum += (value * value);
        }

        return Math.sqrt(sum / size());
    }

    @Override
    public double ss() {
        double mean = mean();

        return ss(mean);
    }

    @Override
    public double ss(double mean) {
        double sum = 0;

        for (double value : getData()) {
            sum += Math.pow(value - mean, 2);
        }

        return sum;
    }

    @Override
    public double mad() {
        double mean = mean();

        return mad(mean);
    }

    @Override
    public double mad(double mean) {
        double sum = 0;

        for (double value : getData()) {
            sum += Math.abs(value - mean);
        }

        return sum / size();
    }

    @Override
    public double var(boolean sample) {
        double mean = mean();

        return sample ? varSample(mean) : varPopulation(mean);
    }

    @Override
    public double var(boolean sample, double mean) {

        return sample ? varSample(mean) : varPopulation(mean);
    }

    @Override
    public double std(boolean sample) {

        return Math.sqrt(var(sample));
    }

    @Override
    public double std(boolean sample, double mean) {

        return Math.sqrt(var(sample, mean));
    }

    @Override
    public double skewness(boolean sample) {
        double mean = mean();
        double std = std(sample);

        return sample ? skewnessSample(mean, std) : skewnessPopulation(mean, std);
    }

    @Override
    public double skewness(boolean sample, double mean, double std) {

        return sample ? skewnessSample(mean, std) : skewnessPopulation(mean, std);
    }

    @Override
    public double kurtosis(boolean sample) {
        double mean = mean();
        double std = std(sample);

        return sample ? kurtosisSample(mean, std) : kurtosisPopulation(mean, std);
    }

    @Override
    public double kurtosis(boolean sample, double mean, double std) {

        return sample ? kurtosisSample(mean, std) : kurtosisPopulation(mean, std);
    }

    @Override
    public double kurtosisExcess(boolean sample) {
        double mean = mean();
        double std = std(sample);

        return kurtosisExcess(sample, mean, std);
    }

    @Override
    public double kurtosisExcess(boolean sample, double mean, double std) {
        double kurtosis = kurtosis(sample, mean, std);

        if (sample) {
            double n = size();
            double postfix = (3 * (Math.pow(n - 1, 2))) / ((n - 2) * (n - 3));

            return kurtosis - postfix;
        }

        return kurtosis - 3;
    }

    @Override
    public boolean isUnique() {

        return getData().stream().distinct().toList().size() == size();
    }

    @Override
    public int filter(Function<Double, Boolean> function) {
        int oldSize = size();

        setData(getData().stream().filter(function::apply).collect(Collectors.toList()));

        return oldSize - size();
    }

    @Override
    public int filter(boolean dynamic, BiFunction<Double, Double, Boolean> function) {

        if (size() < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        List<Double> results = dynamic ? filterDynamic(function) : filterStatic(function);
        int count = size() - results.size();

        setData(results);

        return count;
    }

    @Override
    public int removeOutliers(boolean sample, double factor) {

        if (factor < 0) {
            throw new IllegalArgumentException("The multiplication factor cannot be lower then zero");
        }

        double mean = mean();
        double std = std(sample);

        return removeOutliers(mean, std, factor);
    }

    @Override
    public int removeOutliers(double mean, double std, double factor) {

        if (factor < 0) {
            throw new IllegalArgumentException("The multiplication factor cannot be lower then zero");
        }

        double dist = std * factor;

        return filter(val -> Math.abs(val - mean) < dist);
    }

    @Override
    public int removeDuplicates() {
        Set<Double> values = new HashSet<>();

        for (int i = 0 ; i < size() ; i++) {
            if (!values.add(get(i))) {
                set(i, Double.NaN);
            }
        }

        return filter(e -> !Double.isNaN(e));
    }

    @Override
    public FStat1D removeNaN() {

        filter((x) -> !Double.isNaN(x));

        return this;
    }

    @Override
    public FStat1D replaceWithNaN(Function<Double, Boolean> function) {

        for (int i = 0 ; i < size() ; i++) {
            if (!function.apply(get(i))) {
                set(i, Double.NaN);
            }
        }

        return this;
    }

    @Override
    public FStat1D replaceWithNaN(boolean dynamic, BiFunction<Double, Double, Boolean> function) {

        if (size() < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        if (dynamic) {
            replaceWithNaNDynamic(function);
        } else {
            replaceWithNaNStatic(function);
        }

        return this;
    }

    @Override
    public FStat1D replaceOutliersWithNaN(boolean sample, double factor) {

        if (factor < 0) {
            throw new IllegalArgumentException("The multiplication factor cannot be lower then zero");
        }

        double mean = mean();
        double std = std(sample);

        replaceOutliersWithNaN(mean, std, factor);

        return this;
    }

    @Override
    public FStat1D replaceOutliersWithNaN(double mean, double std, double factor) {

        if (factor < 0) {
            throw new IllegalArgumentException("The multiplication factor cannot be lower then zero");
        }

        double dist = std * factor;

        replaceWithNaN(val -> Math.abs(val - mean) < dist);

        return this;
    }

    @Override
    public FStat1D replaceSameWithNaN() {

        replaceWithNaN(true, (x0, x1) -> !Objects.equals(x0, x1));

        return this;
    }

    @Override
    public FStat1D replaceDecreasingWithNaN() {

        replaceWithNaN(true, (x0, x1) -> x1 >= x0);

        return this;
    }

    @Override
    public FStat1D replaceIncreasingWithNaN() {

        replaceWithNaN(true, (x0, x1) -> x1 <= x0);

        return this;
    }

    @Override
    public void mutate(Function<Double, Double> function) {

        for (int i = 0 ; i < size() ; i++) {

            getData().set(i, function.apply(getData().get(i)));
        }
    }

    @Override
    public void mutate(boolean dynamic, BiFunction<Double, Double, Double> function) {

        if (size() < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        if (dynamic) {
            mutateDynamic(function);
        } else {
            mutateStatic(function);
        }
    }

    @Override
    public void sort(boolean ascending) {

        if (ascending) {
            sortAsc();
        } else {
            sortDsc();
        }
    }

    @Override
    public void invert() {

        setData(getData().reversed());
    }

    @Override
    public void mirror() {

        mutate(val -> -val);
    }

    @Override
    public void normalize(boolean sample) {
        double mean = mean();
        double std = std(sample);

        for (int i = 0 ; i < size() ; i++) {
            getData().set(i, (getData().get(i) - mean) / std);
        }
    }

    @Override
    public void normalize(double mean, double std) {

        for (int i = 0 ; i < size() ; i++) {
            getData().set(i, (getData().get(i) - mean) / std);
        }
    }

    @Override
    public void removeBias() {
        double mean = mean();

        removeBias(mean);
    }

    @Override
    public void removeBias(double mean) {

        mutate((val -> val - mean));
    }

    @Override
    public boolean isSimilarAbs(double threshold, FStat1D... comparison) {

        if (threshold < 0) {
            throw new IllegalArgumentException("The threshold value cannot be negative");
        }

        if (comparison.length == 0) {
            throw new IllegalArgumentException("At least one set must be provided for comparison");
        }

        for (FStat1D fStat : comparison) {
            if (size() != fStat.size()) {
                throw new IllegalArgumentException("All sets must have the same size");
            }
        }

        for (int i = 0 ; i < size() ; i++) {
            double value = get(i);

            for (FStat1D fStat : comparison) {
                if (Math.abs(value - fStat.get(i)) > threshold) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isSimilarRel(double threshold, FStat1D... comparison) {

        if (threshold < 0) {
            throw new IllegalArgumentException("The threshold value cannot be negative");
        }

        if (comparison.length == 0) {
            throw new IllegalArgumentException("At least one set must be provided for comparison");
        }

        for (FStat1D fStat : comparison) {
            if (size() != fStat.size()) {
                throw new IllegalArgumentException("All sets must have the same size");
            }
        }

        for (int i = 0 ; i < size() ; i++) {
            double value = get(i);

            for (FStat1D fStat : comparison) {
                if (Math.abs((fStat.get(i) - value) / value) > threshold) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public double[] toArray() {
        double[] values = new double[size()];

        for (int i = 0 ; i < size() ; i++) {
            values[i] = get(i);
        }

        return values;
    }

    @Override
    public FPlot2D toFPlot2DLinear() {

        if (size() == 0) {
            throw new IllegalStateException("The number of elements must be greater than zero");
        }

        FPlot2D fPlot = factory.getFPlot2D();

        for (int i = 0 ; i < size() ; i++) {

            fPlot.add(i, getData().get(i));
        }

        return fPlot;
    }

    @Override
    public FPlot2D toFPlot2DPieChart() {

        if (size() == 0) {
            throw new IllegalStateException("The number of elements must be greater than zero");
        }

        FPlot2D fPlot = factory.getFPlot2D();

        for (double v : getData()) {
            fPlot.add((y1, y2) -> y1 + 1, v, 1);
        }

        fPlot.sortX(true);
        fPlot.sortY(false);

        return fPlot;
    }

    @Override
    public FPlot2D toFPlot2DHistogram(double min, double max, int divisions) {

        if (divisions < 2) {
            throw new IllegalArgumentException("The number of groups must be greater then one");
        }

        if (min >= max) {
            throw new IllegalArgumentException("The minimum value must be smaller than the maximum value");
        }

        FPlot2D fPlot = factory.getFPlot2D();

        double range = max - min;
        double step = range / divisions;

        for (int i = 0; i < divisions; i++) {
            fPlot.add(min + (i * step));
        }

        main:
        for (double value : getData()) {

            if (value < min || value > max) {
                continue;
            }

            for (int i = 0; i < divisions; i++) {
                if (value < fPlot.getX(i) + step) {
                    fPlot.add((y1, y2) -> y1 + 1, fPlot.getX(i));

                    continue main;
                }
            }

            throw new IllegalStateException("The histogram could not be generated");
        }

        return fPlot;
    }

    //--------------------------------------------------

    private void replaceWithNaNDynamic(BiFunction<Double, Double, Boolean> function) {
        double previous = get(0);

        for (int i = 1 ; i < size() ; i++) {
            double current = get(i);

            if (function.apply(previous, current)) {
                previous = current;
            } else {
                set(i, Double.NaN);
            }
        }
    }

    private void replaceWithNaNStatic(BiFunction<Double, Double, Boolean> function) {
        double previous = get(0);

        for (int i = 1 ; i < size() ; i++) {
            double current = get(i);

            boolean results = function.apply(previous, current);

            previous = current;

            if (!results) {
                set(i, Double.NaN);
            }
        }
    }

    private List<Double> filterDynamic(BiFunction<Double, Double, Boolean> function) {
        List<Double> results = new ArrayList<>(size());
        double previous = get(0);

        results.add(previous);

        for (int i = 1 ; i < size() ; i++) {
            double current = get(i);

            if (function.apply(previous, current)) {
                results.add(current);
                previous = current;
            }
        }

        return results;
    }

    private List<Double> filterStatic(BiFunction<Double, Double, Boolean> function) {
        List<Double> results = new ArrayList<>(size());

        results.add(get(0));

        for (int i = 1 ; i < size() ; i++) {
            double previous = get(i - 1);
            double current = get(i);

            if (function.apply(previous, current)) {
                results.add(current);
            }
        }

        return results;
    }

    private void mutateDynamic(BiFunction<Double, Double, Double> function) {

        for (int i = 1 ; i < size() ; i++) {
            set(i, function.apply(get(i - 1), get(i)));
        }
    }

    private void mutateStatic(BiFunction<Double, Double, Double> function) {
        double reference = get(0);

        for (int i = 1 ; i < size() ; i++) {
            double updated = function.apply(reference, get(i));
            reference = get(i);
            set(i, updated);
        }
    }

    private void sortAsc() {

        getData().sort((a, b) -> a - b > 0 ? 1 : a.equals(b) ? 0 : -1);
    }

    private void sortDsc() {

        getData().sort((a, b) -> a - b < 0 ? 1 : a.equals(b) ? 0 : -1);
    }

    private double varSample(double mean) {

        if (size() < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        double sum = 0;

        for (double d : getData()) {
            sum += Math.pow(d - mean, 2);
        }

        return sum / (size() - 1);
    }

    private double varPopulation(double mean) {

        if (size() < 1) {
            throw new IllegalStateException("The set must contain at least one element");
        }

        double sum = 0;

        for (double d : getData()) {
            sum += Math.pow(d - mean, 2);
        }

        return sum / size();
    }

    private double skewnessSample(double mean, double std) {
        double n = size();
        double prefix = n / ((n - 1) * (n - 2));
        double sum = 0;

        for (double value : getData()) {
            sum += Math.pow((value - mean) / std, 3);
        }

        return prefix * sum;
    }

    private double skewnessPopulation(double mean, double std) {
        double sum = 0;

        for (double value : getData()) {
            sum += Math.pow(value - mean, 3);
        }

        return sum / (size() * Math.pow(std, 3));
    }

    private double kurtosisSample(double mean, double std) {
        double n = size();
        double prefix = (n * (n + 1)) / ((n - 1) * (n - 2) * (n - 3));
        double sum = 0;

        for (double value : getData()) {
            sum += Math.pow((value - mean) / std, 4);
        }

        return prefix * sum;
    }

    private double kurtosisPopulation(double mean, double std) {
        double sum = 0;

        for (double value : getData()) {
            sum += Math.pow(value - mean, 4);
        }

        return sum / (size() * Math.pow(std, 4));
    }

    //--------------------------------------------------

    @Override
    public boolean isEqual(FStat1D fStat) {

        if (size() != fStat.size()) {
            return false;
        }

        for (int i = 0 ; i < fStat.size() ; i++) {
            if (!(getData().get(i) == fStat.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEqualData(FStat1D fStat) {

        return isEqual(fStat);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        for (Double item : getData()) {
            json.append(JSON_DATA, item);
        }

        return json;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    //--------------------------------------------------

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public List<Double> getData() {

        return this.data;
    }

    @Override
    public void setData(List<Double> data) {

        this.data = data;
    }

    @Override
    public Iterator<Double> iterator() {

        return new FStat1DIterator();
    }

    class FStat1DIterator implements Iterator<Double> {
        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < FStat1DDef.this.size();
        }

        @Override
        public Double next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return FStat1DDef.this.get(index++);
        }
    }
}

//https://www.calculatorsoup.com/calculators/statistics/descriptivestatistics.php
//https://www.calculatorsoup.com/calculators/statistics/z-score-calculator.php
//https://www.calculatorsoup.com/calculators/statistics/percentile-calculator.php
//https://www.itl.nist.gov/div898/handbook/prc/section2/prc262.htm