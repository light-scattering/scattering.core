package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DRecord;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class FPlot2DDef implements FPlot2D {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "plot2D";
    private static final String JSON_DATA = "records";
    private static final String JSON_INT = "interpolator";

    private final ScatFactory factory;

    private List<FPlot2DRecord> data;
    private FPlot2DInterpolator interpolator;

    private String name = "";

    private FPlot2DDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FPlot2D create(ScatFactory factory) {
        FPlot2DDef fPlot = new FPlot2DDef(factory);

        fPlot.data = new ArrayList<>();
        fPlot.interpolator = FPlot2DInterpolatorDef.create();

        return fPlot;
    }

    public static FPlot2D create(ScatFactory factory, JSONObject json) {
        FPlot2DDef fPlot = new FPlot2DDef(factory);

        fPlot.data = new ArrayList<>();

        JSONArray data = json.getJSONArray(JSON_DATA);
        for (int i = 0 ; i < data.length() ; i++) {
            fPlot.add(FPlot2DRecordDef.create(data.getJSONObject(i)));
        }

        fPlot.interpolator = FPlot2DInterpolatorDef.create(json.getJSONObject(JSON_INT));

        return fPlot;
    }

    @Override
    public FPlot2D copy() {
        FPlot2D fPlot = factory.getFPlot2D();

        fPlot.getInterpolator().setMethod(getInterpolator().getMethod());
        fPlot.getInterpolator().setHermiteBias(getInterpolator().getHermiteBias());
        fPlot.getInterpolator().setHermiteTension(getInterpolator().getHermiteTension());

        for (FPlot2DRecord record : getData()) {
            fPlot.add(record.getX(), record.getY());
        }

        return fPlot;
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
    public void add(double x) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getData().add(FPlot2DRecordDef.create(x, 0));
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x) {

        add(collision, x, 0);
    }

    @Override
    public void add(double x, double y) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getData().add(FPlot2DRecordDef.create(x, y));
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x, double y) {
        int position = position(x);

        if (position < 0) {
            getData().add(FPlot2DRecordDef.create(x, y));
        } else {
            FPlot2DRecord record = getData().get(position);

            record.setY(collision.apply(record.getY(), y));
        }
    }

    @Override
    public double getX(int index) {

        return getData().get(index).getX();
    }

    @Override
    public void setX(int index, double x) {

        getData().get(index).setX(x);

    }

    @Override
    public double getY(int index) {

        return getData().get(index).getY();
    }

    @Override
    public void setY(int index, double y) {

        getData().get(index).setY(y);
    }

    @Override
    public FStat1D getStatX() {
        FStat1D fStat = factory.getFStat1D();

        for (FPlot2DRecord record : getData()) {
            fStat.add(record.getX());
        }

        return fStat;
    }

    @Override
    public void setStatX(FStat1D statX) {

        if (!statX.isUnique()) {
            throw new IllegalArgumentException("X axis values must be unique");
        }

        if (statX.size() != size()) {
            throw new IllegalArgumentException("The number of elements is erroneous");
        }

        List<FPlot2DRecord> results = new ArrayList<>();

        for (int i = 0 ; i < statX.size() ; i++) {

            if (Double.isNaN(statX.get(i))) {
                continue;
            }

            results.add(FPlot2DRecordDef.create(statX.get(i), getY(i)));
        }

        setData(results);
    }

    @Override
    public FStat1D getStatY() {
        FStat1D fStat = factory.getFStat1D();

        for (FPlot2DRecord record : getData()) {
            fStat.add(record.getY());
        }

        return fStat;
    }

    @Override
    public void setStatY(FStat1D statY) {

        if (statY.size() != size()) {
            throw new IllegalArgumentException("The number of elements is erroneous");
        }

        List<FPlot2DRecord> results = new ArrayList<>();

        for (int i = 0 ; i < statY.size() ; i++) {

            if (Double.isNaN(statY.get(i))) {
                continue;
            }

            results.add(FPlot2DRecordDef.create(getX(i), statY.get(i)));
        }

        setData(results);
    }

    @Override
    public int getIndex(double x) {
        double valueMin = Double.POSITIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < size() ; i++) {
            double value = getX(i);

            double abs = Math.abs(x - value);
            if (abs < valueMin) {
                valueMin = abs;
                index = i;
            }
        }

        return index;
    }

    @Override
    public int getIndexCeil(double x) {
        double valueMax = Double.POSITIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < this.data.size() ; i++) {
            double value = this.data.get(i).getX();

            if (value < valueMax && value >= x) {
                valueMax = value;
                index = i;
            }
        }

        return index;
    }

    @Override
    public int getIndexFloor(double x) {
        double valueMin = Double.NEGATIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < this.data.size() ; i++) {
            double value = this.data.get(i).getX();

            if (value > valueMin && value <= x) {
                valueMin = value;
                index = i;
            }
        }

        return index;
    }

    @Override
    public double minX() {
        double valueMin = Double.POSITIVE_INFINITY;

        for (FPlot2DRecord record : getData()) {
            if (record.getX() < valueMin) {
                valueMin = record.getX();
            }
        }

        return valueMin;
    }

    @Override
    public double minY() {
        double valueMin = Double.POSITIVE_INFINITY;

        for (FPlot2DRecord record : getData()) {
            if (record.getY() < valueMin) {
                valueMin = record.getY();
            }
        }

        return valueMin;
    }

    @Override
    public double maxX() {
        double valueMax = Double.NEGATIVE_INFINITY;

        for (FPlot2DRecord record : getData()) {
            if (record.getX() > valueMax) {
                valueMax = record.getX();
            }
        }

        return valueMax;
    }

    @Override
    public double maxY() {
        double valueMax = Double.NEGATIVE_INFINITY;

        for (FPlot2DRecord record : getData()) {
            if (record.getY() > valueMax) {
                valueMax = record.getY();
            }
        }

        return valueMax;
    }

    @Override
    public double integrate() {

        if (size() < 1) {
            throw new IllegalArgumentException("The number of elements must be greater then one");
        }

        double area = 0;
        for (int i = 0 ; i < size() - 1 ; i++) {
            double avgY = (Math.abs(getY(i)) + Math.abs(getY(i + 1))) * 0.5;
            double stepX = getX(i + 1) - getX(i);

            area += (stepX * avgY);
        }

        return area;
    }

    @Override
    public double approximate(double x) {

        return getInterpolator().apx(this, x);
    }

    @Override
    public int filter(BiFunction<Double, Double, Boolean> filter) {
        int oldSize = size();

        setData(getData().stream()
                .filter(e -> filter.apply(e.getX(), e.getY()))
                .collect(Collectors.toList()));

        return oldSize - size();
    }

    @Override
    public void mutateX(BiFunction<Double, Double, Double> function) {

        for (FPlot2DRecord record : this.data) {
            record.setX(function.apply(record.getX(), record.getY()));
        }
    }

    @Override
    public void mutateY(BiFunction<Double, Double, Double> function) {

        for (FPlot2DRecord record : this.data) {
            record.setY(function.apply(record.getX(), record.getY()));
        }
    }

    @Override
    public void mutateYWithPolynomial(BiFunction<Double, Double, Double> function, double... polynomial) {

        for (FPlot2DRecord record : getData()) {
            record.setY(function.apply(record.getY(), getPolynomialValue(record.getX(), polynomial)));
        }
    }

    private double getPolynomialValue(double x, double[] polynomial) {
        double value = 0;

        for (int i = 0 ; i < polynomial.length ; i++) {
            value += polynomial[i] * Math.pow(x, polynomial.length - 1 - i);
        }

        return value;
    }

    @Override
    public void interpolate(double step, boolean overflow) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step value must be greater than zero");
        }

        double minX = minX();
        double maxX = maxX();

        List<FPlot2DRecord> list = new ArrayList<>();

        double value = minX;
        while (value < maxX) {
            list.add(FPlot2DRecordDef.create(value, approximate(value)));
            value += step;
        }

        if (overflow) {
            list.add(FPlot2DRecordDef.create(value, approximate(maxX)));
        }

        setData(list);
    }

    @Override
    public void interpolate(double divisions) {

        if (divisions < 1) {
            throw new IllegalArgumentException("The number of divisions cannot be smaller then one");
        }

        double minX = minX();
        double maxX = maxX();

        double step = (maxX - minX) / divisions;

        List<FPlot2DRecord> list = new ArrayList<>();

        double value = minX;
        while (value < maxX) {
            list.add(FPlot2DRecordDef.create(value, approximate(value)));
            value += step;
        }

        list.add(FPlot2DRecordDef.create(maxX, approximate(maxX)));

        setData(list);
    }

    @Override
    public void sortX(boolean ascending) {

        if (ascending) {
            sortAscX();
        } else {
            sortDscX();
        }
    }

    @Override
    public void sortY(boolean ascending) {

        if (ascending) {
            sortAscY();
        } else {
            sortDscY();
        }
    }

    @Override
    public void absolute() {

        mutateY((x, y) -> Math.abs(y));
    }

    @Override
    public FPos2D simpleLinearRegression() {
        FStat1D sx = getStatX();
        FStat1D sy = getStatY();

        double mx = sx.mean();
        double my = sy.mean();

        double numerator = 0;
        double denominator = 0;

        for (int i = 0 ; i < size() ; i++) {
            numerator += (getX(i) - mx) * (getY(i) - my);
            denominator += Math.pow(getX(i) - mx, 2);
        }

        double a = numerator / denominator;
        double b = my - (a * mx);

        mutateY((x, y) -> (a * x) + b);

        return factory.getFPos2D(a, b);
    }

    @Override
    public void swapXY() {
        double swap;

        for (FPlot2DRecord record : getData()) {
            swap = record.getX();
            record.setX(record.getY());
            record.setY(swap);
        }
    }

    @Override
    public void log(double base, boolean x, boolean y) {
        double denominator = Math.log(base);

        if (x) {
            mutateX((dx, dy) -> Math.log(dx) / denominator);
        }

        if (y) {
            mutateY((dx, dy) -> Math.log(dy) / denominator);
        }
    }

    @Override
    public void forEach(BiConsumer<Double, Double> consumer) {

        for (FPlot2DRecord record : getData()) {
            consumer.accept(record.getX(), record.getY());
        }
    }

    @Override
    public FPlot2DInterpolator getInterpolator() {

        return this.interpolator;
    }

    @Override
    public double[][] toArray() {
        double [][] values = new double[2][size()];

        for (int i = 0 ; i < size() ; i++) {
            values[0][i] = getX(i);
            values[1][i] = getY(i);
        }

        return values;
    }

    // -------------------------------------------------------------------------------------------------

    private void add(FPlot2DRecord record) {

        getData().add(record);
    }

    private int position(double x) {

        for (int i = 0 ; i < this.data.size() ; i++) {
            if (this.data.get(i).getX() == x) {
                return i;
            }
        }

        return -1;
    }

    private void sortAscX() {

        this.data.sort((a, b) -> a.getX() - b.getX() > 0 ? 1 : a.getX() == b.getX() ? 0 : -1);
    }

    private void sortDscX() {

        this.data.sort((a, b) -> a.getX() - b.getX() < 0 ? 1 : a.getX() == b.getX() ? 0 : -1);
    }

    private void sortAscY() {

        this.data.sort((a, b) -> a.getY() - b.getY() > 0 ? 1 : a.getY() == b.getY() ? 0 : -1);
    }

    private void sortDscY() {

        this.data.sort((a, b) -> a.getY() - b.getY() < 0 ? 1 : a.getY() == b.getY() ? 0 : -1);
    }

    //--------------------------------------------------

    @Override
    public boolean isEqual(FPlot2D fPlot2D) {

        if (!getInterpolator().isEqual(fPlot2D.getInterpolator())) {
            return false;
        }

        if (this.size() != fPlot2D.size()) {
            return false;
        }

        for (int i = 0 ; i < this.size() ; i++) {
            if (this.getX(i) != fPlot2D.getX(i) || this.getY(i) != fPlot2D.getY(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEqualData(FPlot2D fPlot2D) {

        if (this.size() != fPlot2D.size()) {
            return false;
        }

        for (int i = 0 ; i < this.size() ; i++) {
            if (this.getX(i) != fPlot2D.getX(i) || this.getY(i) != fPlot2D.getY(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_INT, getInterpolator().toJSON());

        for (FPlot2DRecord record : this.data) {
            json.append(JSON_DATA, record.toJSON());
        }

        return json;
    }

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

    //--------------------------------------------------

    @Override
    public FPlot2DRecord getRecord(int index) {

        return getData().get(index);
    }

    private List<FPlot2DRecord> getData() {

        return this.data;
    }

    private void setData(List<FPlot2DRecord> data) {

        this.data = data;
    }


}

// https://paulbourke.net/miscellaneous/interpolation/