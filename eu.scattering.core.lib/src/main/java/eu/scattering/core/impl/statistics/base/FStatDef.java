package eu.scattering.core.impl.statistics.base;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.base.FStat;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FStatDef implements FStat {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "stat";
    private static final String JSON_DATA = "data";
    private static final String JSON_TOTAL = "total";
    private static final String JSON_INVALID = "invalid";

    private final ScatterFactory factory;

    private final List<Double> data;

    private String name = "";

    private FStatDef(ScatterFactory factory, List<Double> data) {

        this.factory = factory;
        this.data = data == null ? new ArrayList<>() : data;
    }

    public static FStat create(ScatterFactory factory) {

        return new FStatDef(factory, null);
    }

    public static FStat create(ScatterFactory factory, List<Double> data) {

        return new FStatDef(factory, data);
    }

    public static FStat create(ScatterFactory factory, double... data) {
        List<Double> dataList = new ArrayList<>(data.length);

        for (Double element : data) {
            dataList.add(element);
        }

        return new FStatDef(factory, dataList);
    }

    public static FStat create(ScatterFactory factory, JSONObject json) {
        FStat fStat = FStatDef.create(factory);

        JSONArray data = json.getJSONArray(JSON_DATA);
        for (int i = 0 ; i < data.length() ; i++) {

            if (data.isNull(i)) {
                fStat.add(Double.NaN);
            } else {
                fStat.add(data.getDouble(i));
            }
        }

        return fStat;
    }

    @Override
    public FStat copy() {
        FStat results = factory.getFStat();

        getRefCore().forEach(results::add);

        return results;
    }

    @Override
    public int size() {

        return getRefCore().size();
    }

    @Override
    public void clear() {

        getRefCore().clear();
    }

    @Override
    public FStat add(double value) {

        getRefCore().add(value);

        return this;
    }

    @Override
    public FStat add(double... value) {

        for (double v : value) {
            add(v);
        }

        return this;
    }

    @Override
    public double get(int index) {

        return getRefCore().get(index);
    }

    @Override
    public FStat set(int index, double value) {

        getRefCore().set(index, value);

        return this;
    }

    @Override
    public boolean contains(double value) {

        return getRefCore().stream().anyMatch(e -> e == value);
    }

    @Override
    public double min() {
        double min = Double.POSITIVE_INFINITY;

        for (Double item : getRefCore()) {

            if (item < min) {
                min = item;
            }
        }

        return min;
    }

    @Override
    public double max() {
        double max = Double.NEGATIVE_INFINITY;

        for (Double item : getRefCore()) {

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

        return getRefCore().stream().reduce(0d, Double::sum);
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

        FStat copy = copy();

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

        FPlot fPlot = factory.getFPlot();

        for (double v : getRefCore()) {
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

        FStat fStat = factory.getFStat();

        for (int j = 0 ; j < i ; j++) {
            fStat.add(fPlot.getX(j));
        }

        fStat.sort(true);

        return fStat.toArray();
    }

    @Override
    public double rms() {
        double sum = 0;

        for (double value : getRefCore()) {
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

        for (double value : getRefCore()) {
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

        for (double value : getRefCore()) {
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
    public double covariance(FStat arg, boolean sample) {
        int size = size();

        if (size < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        if (size != arg.size()) {
            throw new IllegalStateException("The two sets must consist of exactly the same number of elements");
        }

        double avgA = this.mean();
        double avgB = arg.mean();

        double sum = 0;
        for (int i = 0 ; i < size ; i++) {
            sum += (get(i) - avgA) * (arg.get(i) - avgB);
        }

        return sum / (sample ? (size - 1) : size);
    }

    @Override
    public double correlation(FStat arg) {
        double stdA = this.std(true);
        double stdB = arg.std(true);

        if (stdA == 0 || stdB == 0) {
            throw new IllegalStateException("The standard deviation of any set cannot be zero");
        }

        double covariance = covariance(arg, true);

        return covariance / (stdA * stdB);
    }

    @Override
    public boolean allDistinct() {

        return getRefCore().stream().distinct().toList().size() == size();
    }

    @Override
    public int filter(Function<Double, Boolean> function) {
        int oldSize = size();

        setData(getRefCore().stream().filter(function::apply).collect(Collectors.toList()));

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
    public int deduplicate() {
        Set<Double> values = new HashSet<>();

        for (int i = 0 ; i < size() ; i++) {
            if (!values.add(get(i))) {
                set(i, Double.NaN);
            }
        }

        return filter(e -> !Double.isNaN(e));
    }

    @Override
    public FStat removeNaN() {

        filter((x) -> !Double.isNaN(x));

        return this;
    }

    @Override
    public FStat replaceWithNaN(Function<Double, Boolean> function) {

        for (int i = 0 ; i < size() ; i++) {
            if (!function.apply(get(i))) {
                set(i, Double.NaN);
            }
        }

        return this;
    }

    @Override
    public FStat replaceWithNaN(boolean dynamic, BiFunction<Double, Double, Boolean> function) {

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
    public FStat replaceOutliersWithNaN(boolean sample, double factor) {

        if (factor < 0) {
            throw new IllegalArgumentException("The multiplication factor cannot be lower then zero");
        }

        double mean = mean();
        double std = std(sample);

        replaceOutliersWithNaN(mean, std, factor);

        return this;
    }

    @Override
    public FStat replaceOutliersWithNaN(double mean, double std, double factor) {

        if (factor < 0) {
            throw new IllegalArgumentException("The multiplication factor cannot be lower then zero");
        }

        double dist = std * factor;

        replaceWithNaN(val -> Math.abs(val - mean) < dist);

        return this;
    }

    @Override
    public FStat replaceSameWithNaN() {

        replaceWithNaN(true, (x0, x1) -> !Objects.equals(x0, x1));

        return this;
    }

    @Override
    public FStat replaceDecreasingWithNaN() {

        replaceWithNaN(true, (x0, x1) -> x1 >= x0);

        return this;
    }

    @Override
    public FStat replaceIncreasingWithNaN() {

        replaceWithNaN(true, (x0, x1) -> x1 <= x0);

        return this;
    }

    @Override
    public FStat log(double base) {
        double denominator = Math.log(base);

        mutate((e) -> Math.log(e) / denominator);

        return this;
    }

    @Override
    public FStat mutate(Function<Double, Double> function) {

        for (int i = 0 ; i < size() ; i++) {

            getRefCore().set(i, function.apply(getRefCore().get(i)));
        }

        return this;
    }

    @Override
    public FStat mutate(boolean dynamic, BiFunction<Double, Double, Double> function) {

        if (size() < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        if (dynamic) {
            mutateDynamic(function);
        } else {
            mutateStatic(function);
        }

        return this;
    }

    @Override
    public FStat sort(boolean ascending) {

        if (ascending) {
            sortAsc();
        } else {
            sortDsc();
        }

        return this;
    }

    @Override
    public FStat rescale() {
        double min = min();
        double max = max();

        mutate((x) -> (x - min) / (max - min));

        return this;
    }

    @Override
    public FStat rescale(double min, double max) {

        if (min >= max) {
            throw new IllegalArgumentException("The min value must be greater then the max value");
        }

        double spread = max - min;

        rescale();

        mutate((x) -> (x * spread) + min);

        return this;
    }

    @Override
    public FStat absolute() {

        mutate(Math::abs);

        return this;
    }

    @Override
    public FStat distribute() {
        double sum = sum();

        mutate((x) -> x / sum);

        return this;
    }

    @Override
    public FStat invert() {

        setData(new ArrayList<>(getRefCore().reversed()));

        return this;
    }

    @Override
    public FStat mirror() {

        mutate(val -> -val);

        return this;
    }

    @Override
    public FStat normalize(boolean sample) {
        double mean = mean();
        double std = std(sample);

        for (int i = 0 ; i < size() ; i++) {
            getRefCore().set(i, (getRefCore().get(i) - mean) / std);
        }

        return this;
    }

    @Override
    public FStat normalize(double mean, double std) {

        for (int i = 0 ; i < size() ; i++) {
            getRefCore().set(i, (getRefCore().get(i) - mean) / std);
        }

        return this;
    }

    @Override
    public FStat removeBias() {
        double mean = mean();

        removeBias(mean);

        return this;
    }

    @Override
    public FStat removeBias(double mean) {

        mutate((val -> val - mean));

        return this;
    }

    @Override
    public boolean isSimilarAbs(double threshold, FStat... comparison) {

        if (threshold < 0) {
            throw new IllegalArgumentException("The threshold value cannot be negative");
        }

        if (comparison.length == 0) {
            throw new IllegalArgumentException("At least one set must be provided for comparison");
        }

        for (FStat fStat : comparison) {
            if (size() != fStat.size()) {
                throw new IllegalArgumentException("All sets must have the same size");
            }
        }

        for (int i = 0 ; i < size() ; i++) {
            double value = get(i);

            for (FStat fStat : comparison) {
                if (Math.abs(value - fStat.get(i)) > threshold) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isSimilarRel(double threshold, FStat... comparison) {

        if (threshold < 0) {
            throw new IllegalArgumentException("The threshold value cannot be negative");
        }

        if (comparison.length == 0) {
            throw new IllegalArgumentException("At least one set must be provided for comparison");
        }

        for (FStat fStat : comparison) {
            if (size() != fStat.size()) {
                throw new IllegalArgumentException("All sets must have the same size");
            }
        }

        for (int i = 0 ; i < size() ; i++) {
            double value = get(i);

            for (FStat fStat : comparison) {
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
    public FPlot toFPlotLinear() {

        if (size() == 0) {
            throw new IllegalStateException("The number of elements must be greater than zero");
        }

        FPlot fPlot = factory.getFPlot();

        for (int i = 0 ; i < size() ; i++) {

            fPlot.add(i, getRefCore().get(i));
        }

        return fPlot;
    }

    @Override
    public FPlot toFPlotPieChart() {

        if (size() == 0) {
            throw new IllegalStateException("The number of elements must be greater than zero");
        }

        FPlot fPlot = factory.getFPlot();

        for (double v : getRefCore()) {
            fPlot.add((y1, y2) -> y1 + 1, v, 1);
        }

        fPlot.sortX(true);
        fPlot.sortY(false);

        return fPlot;
    }

    @Override
    public FPlot toFPlotHistogram(double min, double max, int divisions) {

        if (divisions < 2) {
            throw new IllegalArgumentException("The number of groups must be at least two");
        }

        if (min >= max) {
            throw new IllegalArgumentException("The minimum value must be smaller than the maximum value");
        }

        FPlot fPlot = factory.getFPlot();

        double range = max - min;
        double step = range / divisions;

        for (int i = 0; i < divisions + 1; i++) {
            fPlot.add(min + (i * step));
        }

        main:
        for (double value : getRefCore()) {

            if (value < min || value > max) {
                continue;
            }

            for (int i = 0; i < divisions + 1; i++) {
                if (value == fPlot.getX(i) || value < fPlot.getX(i) + step) {
                    fPlot.add((y1, y2) -> y1 + 1, fPlot.getX(i));

                    continue main;
                }
            }

            throw new IllegalStateException("The histogram could not be generated");
        }

        return fPlot;
    }

    @Override
    public FPlot toFPlotHistogram(double step) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step must be greater than zero");
        }

        double min = min();
        double max = max();

        FPlot fPlot = factory.getFPlot();

        double range = max - min;
        int divisions = (int) Math.ceil(range / step);

        for (int i = 0; i < divisions + 1; i++) {
            fPlot.add(min + (i * step));
        }

        main:
        for (double value : getRefCore()) {

            if (value < min || value > max) {
                continue;
            }

            for (int i = 0; i < divisions + 1; i++) {
                if (value == fPlot.getX(i) || value < fPlot.getX(i) + step) {
                    fPlot.add((y1, y2) -> y1 + 1, fPlot.getX(i));

                    continue main;
                }
            }

            throw new IllegalStateException("The histogram could not be generated");
        }

        return fPlot;
    }

    //--------------------------------------------------

    private void setData(Collection<Double> data) {

        getRefCore().clear();
        getRefCore().addAll(data);
    }

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

        getRefCore().sort((a, b) -> a - b > 0 ? 1 : a.equals(b) ? 0 : -1);
    }

    private void sortDsc() {

        getRefCore().sort((a, b) -> a - b < 0 ? 1 : a.equals(b) ? 0 : -1);
    }

    private double varSample(double mean) {

        if (size() < 2) {
            throw new IllegalStateException("The set must contain at least two elements");
        }

        double sum = 0;

        for (double d : getRefCore()) {
            sum += Math.pow(d - mean, 2);
        }

        return sum / (size() - 1);
    }

    private double varPopulation(double mean) {

        if (size() < 1) {
            throw new IllegalStateException("The set must contain at least one element");
        }

        double sum = 0;

        for (double d : getRefCore()) {
            sum += Math.pow(d - mean, 2);
        }

        return sum / size();
    }

    private double skewnessSample(double mean, double std) {
        double n = size();
        double prefix = n / ((n - 1) * (n - 2));
        double sum = 0;

        for (double value : getRefCore()) {
            sum += Math.pow((value - mean) / std, 3);
        }

        return prefix * sum;
    }

    private double skewnessPopulation(double mean, double std) {
        double sum = 0;

        for (double value : getRefCore()) {
            sum += Math.pow(value - mean, 3);
        }

        return sum / (size() * Math.pow(std, 3));
    }

    private double kurtosisSample(double mean, double std) {
        double n = size();
        double prefix = (n * (n + 1)) / ((n - 1) * (n - 2) * (n - 3));
        double sum = 0;

        for (double value : getRefCore()) {
            sum += Math.pow((value - mean) / std, 4);
        }

        return prefix * sum;
    }

    private double kurtosisPopulation(double mean, double std) {
        double sum = 0;

        for (double value : getRefCore()) {
            sum += Math.pow(value - mean, 4);
        }

        return sum / (size() * Math.pow(std, 4));
    }

    //--------------------------------------------------

    @Override
    public boolean isEqual(FStat fStat) {

        if (size() != fStat.size()) {
            return false;
        }

        for (int i = 0 ; i < fStat.size() ; i++) {
            if (!(getRefCore().get(i) == fStat.get(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEqualWithNaN(FStat fStat) {

        if (size() != fStat.size()) {
            return false;
        }

        for (int i = 0 ; i < fStat.size() ; i++) {
            if (!(getRefCore().get(i) == fStat.get(i))) {
                if (getRefCore().get(i).isNaN() && Double.isNaN(fStat.get(i))) {
                    continue;
                }

                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEqualData(FStat fStat) {

        return isEqual(fStat);
    }

    @Override
    public boolean isEqualDataWithNaN(FStat fStat) {

        return isEqualWithNaN(fStat);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);

        for (Double item : getRefCore()) {
            if (item.isNaN()) {
                json.append(JSON_DATA, null);
            } else {
                json.append(JSON_DATA, item);
            }
        }

        return json;
    }

    @Override
    public JSONObject toSimpleJSON() {
        JSONObject json = new JSONObject();

        int invalid = copy().filter((x) -> !Double.isNaN(x));

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_TOTAL, size());
        json.put(JSON_INVALID, invalid);

        return json;
    }

    @Override
    public String toString() {

        return toSimpleJSON().toString();
    }

    //--------------------------------------------------

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public FStat setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public List<Double> getRefCore() {

        return this.data;
    }

    @Override
    public Iterator<Double> iterator() {

        return new FStatIterator();
    }

    class FStatIterator implements Iterator<Double> {
        private int index = 0;

        @Override
        public boolean hasNext() {

            return index < FStatDef.this.size();
        }

        @Override
        public Double next() {

            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return FStatDef.this.get(index++);
        }
    }
}

//https://www.calculatorsoup.com/calculators/statistics/descriptivestatistics.php
//https://www.calculatorsoup.com/calculators/statistics/z-score-calculator.php
//https://www.calculatorsoup.com/calculators/statistics/percentile-calculator.php
//https://www.itl.nist.gov/div898/handbook/prc/section2/prc262.htm