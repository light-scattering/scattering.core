package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.statistics.construct.utils.FPlotInterpolator;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.transfer.primitive.FPoly;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FPlotDef implements FPlot {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "plot";
    private static final String JSON_DATA_X = "dataX";
    private static final String JSON_DATA_Y = "dataY";
    private static final String JSON_INT = "interpolator";

    private final ScatFactory factory;

    private final FStat dataX;
    private final FStat dataY;

    private FPlotInterpolator interpolator;

    private String name = "";

    private FPlotDef(ScatFactory factory, FStat dataX, FStat dataY) {

        this.factory = factory;

        this.dataX = dataX == null ? factory.getFStat() : dataX;
        this.dataY = dataY == null ? factory.getFStat() : dataY;

        if (this.dataX.size() != this.dataY.size()) {
            throw new IllegalArgumentException("The data is corrupted");
        }

        this.interpolator = FPlotInterpolatorDef.create();
    }

    public static FPlot create(ScatFactory factory) {

        return new FPlotDef(factory, null, null);
    }

    public static FPlot create(ScatFactory factory, FStat dataX, FStat dataY) {

        return new FPlotDef(factory, dataX, dataY);
    }

    public static FPlot create(ScatFactory factory, FLayer fLayer) {
        FPlotDef fPlot = new FPlotDef(factory, null, null);

        for (int i = 0 ; i < fLayer.size() ; i++) {
            fPlot.dataX.add(i);
            fPlot.dataY.add(fLayer.get(i));
        }

        return fPlot;
    }

    public static FPlot create(ScatFactory factory, JSONObject json) {
        FStat dataX = factory.getFStat(json.getJSONObject(JSON_DATA_X));
        FStat dataY = factory.getFStat(json.getJSONObject(JSON_DATA_Y));

        FPlotDef fPlot = new FPlotDef(factory, dataX, dataY);

        fPlot.interpolator = FPlotInterpolatorDef.create(json.getJSONObject(JSON_INT));

        return fPlot;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void add(double x) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefCoreX().add(x);
        getRefCoreY().add(0);
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x) {
        int position = position(x);

        if (position < 0) {
            getRefCoreX().add(x);
            getRefCoreY().add(0);
        } else {
            getRefCoreY().set(position, collision.apply(getRefCoreY().get(position), 0d));
        }
    }

    @Override
    public void add(double x, double y) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefCoreX().add(x);
        getRefCoreY().add(y);
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x, double y) {
        int position = position(x);

        if (position < 0) {
            getRefCoreX().add(x);
            getRefCoreY().add(y);
        } else {
            getRefCoreY().set(position, collision.apply(getRefCoreY().get(position), y));
        }
    }

    @Override
    public double getX(int index) {

        return getRefCoreX().get(index);
    }

    @Override
    public void setX(int index, double x) {

        getRefCoreX().set(index, x);
    }

    @Override
    public double getY(int index) {

        return getRefCoreY().get(index);
    }

    @Override
    public void setY(int index, double y) {

        getRefCoreY().set(index, y);
    }

    @Override
    public int getIndexX(Index type, double x) {

        return switch (type) {
            case ROUND -> getIndexXRound(x);
            case FLOOR -> getIndexXFloor(x);
            case CEIL -> getIndexXCeil(x);
        };
    }

    @Override
    public int getIndexY(Index type, double y) {

        return switch (type) {
            case ROUND -> getIndexYRound(y);
            case FLOOR -> getIndexYFloor(y);
            case CEIL -> getIndexYCeil(y);
        };
    }

    @Override
    public <T> T getWithFStat(BiFunction<FStat, FStat, T> function) {
        T value = function.apply(getRefCoreX(), getRefCoreY());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return value;
    }

    @Override
    public <T> T getWithFStatX(Function<FStat, T> function) {
        T value = function.apply(this.dataX);

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return value;
    }

    @Override
    public <T> T getWithFStatY(Function<FStat, T> function) {
        T value = function.apply(this.dataY);

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return value;
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
    public FPos2D simpleLinearRegression() {
        FStat sx = getRefCoreX();
        FStat sy = getRefCoreY();

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
    public double mse(FPoly est) {
        double mse = 0;

        for (int i = 0 ; i < size() ; i++) {
            mse += Math.pow(getY(i) - est.getValue(getX(i)), 2);
        }

        return mse / size();
    }

    @Override
    public int filter(BiFunction<Double, Double, Boolean> filter) {
        int oldSize = size();

        List<Double> fStatX = new ArrayList<>();
        List<Double> fStatY = new ArrayList<>();

        for (int i = 0 ; i < size() ; i++) {
            if (filter.apply(getX(i), getY(i))) {
                fStatX.add(getX(i));
                fStatY.add(getY(i));
            }
        }

        getRefCoreX().clear();
        getRefCoreX().getRefCore().addAll(fStatX);
        getRefCoreY().clear();
        getRefCoreY().getRefCore().addAll(fStatY);

        return oldSize - size();
    }

    @Override
    public void setY(FPoly est) {

        mutateY((x, y) -> est.getValue(x));
    }

    @Override
    public void mutateFStat(Consumer<FStat> consumer) {

        consumer.accept(getRefCoreX());
        consumer.accept(getRefCoreY());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }
    }

    @Override
    public void mutateFStat(BiConsumer<FStat, FStat> consumer) {

        consumer.accept(getRefCoreX(), getRefCoreY());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }
    }

    @Override
    public void mutateX(BiFunction<Double, Double, Double> function) {

        for (int i = 0 ; i < size() ; i++) {
            setX(i, function.apply(getX(i), getY(i)));
        }
    }

    @Override
    public void mutateFStatX(Consumer<FStat> consumer) {

        consumer.accept(getRefCoreX());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }
    }

    @Override
    public void mutateY(BiFunction<Double, Double, Double> function) {

        for (int i = 0 ; i < size() ; i++) {
            setY(i, function.apply(getX(i), getY(i)));
        }
    }

    @Override
    public void mutateFStatY(Consumer<FStat> consumer) {

        consumer.accept(getRefCoreY());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }
    }

    @Override
    public void interpolate(double step, boolean overflow) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step value must be greater than zero");
        }

        double minX = getRefCoreX().min();
        double maxX = getRefCoreX().max();

        List<Double> fStatX = new ArrayList<>();
        List<Double> fStatY = new ArrayList<>();

        double value = minX;
        while (value < maxX) {
            fStatX.add(value);
            fStatY.add(approximate(value));

            value += step;
        }

        if (overflow) {
            fStatX.add(value);
            fStatY.add(approximate(maxX));
        }

        getRefCoreX().clear();
        getRefCoreX().getRefCore().addAll(fStatX);
        getRefCoreY().clear();
        getRefCoreY().getRefCore().addAll(fStatY);
    }

    @Override
    public void interpolate(double divisions) {

        if (divisions < 1) {
            throw new IllegalArgumentException("The number of divisions cannot be smaller then one");
        }

        double minX = getRefCoreX().min();
        double maxX = getRefCoreX().max();

        double step = (maxX - minX) / divisions;

        List<Double> fStatX = new ArrayList<>();
        List<Double> fStatY = new ArrayList<>();

        double value = minX;
        while (value < maxX) {
            fStatX.add(value);
            fStatY.add(approximate(value));

            value += step;
        }

        fStatX.add(maxX);
        fStatY.add(approximate(maxX));

        getRefCoreX().clear();
        getRefCoreX().getRefCore().addAll(fStatX);
        getRefCoreY().clear();
        getRefCoreY().getRefCore().addAll(fStatY);
    }

    @Override
    public void sortX(boolean ascending) {
        List<FPos2D> data = new ArrayList<>(size());

        forEach((x, y, index) -> data.add(factory.getFPos2D(x, y)));

        if (ascending) {
            sortAscX(data);
        } else {
            sortDscX(data);
        }

        for (int i = 0 ; i < size() ; i++) {
            FPos2D item = data.get(i);
            setX(i, item.getD0());
            setY(i, item.getD1());
        }
    }

    @Override
    public void sortY(boolean ascending) {
        List<FPos2D> data = new ArrayList<>(size());

        forEach((x, y, index) -> data.add(factory.getFPos2D(x, y)));

        if (ascending) {
            sortAscY(data);
        } else {
            sortDscY(data);
        }

        for (int i = 0 ; i < size() ; i++) {
            FPos2D item = data.get(i);
            setX(i, item.getD0());
            setY(i, item.getD1());
        }
    }

    @Override
    public void swapXY() {
        double swap;

        for (int i = 0 ; i < size() ; i++) {
            swap = getX(i);
            setX(i, getY(i));
            setY(i, swap);
        }
    }

    @Override
    public void forEach(TriConsumer<Double, Double, Integer> consumer) {
        Iterator<Double> iteratorX = getRefCoreX().iterator();
        Iterator<Double> iteratorY = getRefCoreY().iterator();

        for (int i = 0; i < getRefCoreX().size() ; i++) {
            consumer.accept(iteratorX.next(), iteratorY.next(), i);
        }
    }

    @Override
    public double[][] toArray() {
        double [][] values = new double[2][size()];

        forEach((x, y, i) -> {
            values[0][i] = x;
            values[1][i] = y;
        });

        return values;
    }

    @Override
    public FPlotInterpolator getInterpolator() {

        return this.interpolator;
    }

    @Override
    public FStat getRefCoreX() {

        return this.dataX;
    }

    @Override
    public FStat getRefCoreY() {

        return this.dataY;
    }

    // -------------------------------------------------------------------------------------------------

    private int getIndexXRound(double x) {
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

    private int getIndexYRound(double y) {
        double valueMin = Double.POSITIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < size() ; i++) {
            double value = getY(i);

            double abs = Math.abs(y - value);
            if (abs < valueMin) {
                valueMin = abs;
                index = i;
            }
        }

        return index;
    }

    private int getIndexXCeil(double x) {
        double valueMax = Double.POSITIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < size() ; i++) {
            double value = getX(i);

            if (value < valueMax && value >= x) {
                valueMax = value;
                index = i;
            }
        }

        return index;
    }

    private int getIndexYCeil(double y) {
        double valueMax = Double.POSITIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < size() ; i++) {
            double value = getY(i);

            if (value < valueMax && value >= y) {
                valueMax = value;
                index = i;
            }
        }

        return index;
    }

    private int getIndexXFloor(double x) {
        double valueMin = Double.NEGATIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < size() ; i++) {
            double value = getX(i);

            if (value > valueMin && value <= x) {
                valueMin = value;
                index = i;
            }
        }

        return index;
    }

    private int getIndexYFloor(double y) {
        double valueMin = Double.NEGATIVE_INFINITY;
        int index = -1;

        for (int i = 0 ; i < size() ; i++) {
            double value = getY(i);

            if (value > valueMin && value <= y) {
                valueMin = value;
                index = i;
            }
        }

        return index;
    }

    private int position(double x) {

        for (int i = 0 ; i < size() ; i++) {
            if (getX(i) == x) {
                return i;
            }
        }

        return -1;
    }

    private void sortAscX(List<FPos2D> data) {

        data.sort((a, b) -> a.getD0() - b.getD0() > 0 ? 1 : a.getD0() == b.getD0() ? 0 : -1);
    }

    private void sortDscX(List<FPos2D> data) {

        data.sort((a, b) -> a.getD0() - b.getD0() < 0 ? 1 : a.getD0() == b.getD0() ? 0 : -1);
    }

    private void sortAscY(List<FPos2D> data) {

        data.sort((a, b) -> a.getD1() - b.getD1() > 0 ? 1 : a.getD1() == b.getD1() ? 0 : -1);
    }

    private void sortDscY(List<FPos2D> data) {

        data.sort((a, b) -> a.getD1() - b.getD1() < 0 ? 1 : a.getD1() == b.getD1() ? 0 : -1);
    }

    //--------------------------------------------------

    @Override
    public int size() {

        return getRefCoreX().size();
    }

    @Override
    public void clear() {

        getRefCoreX().clear();
        getRefCoreY().clear();
    }

    @Override
    public FPlot copy() {
        FPlot fPlot = factory.getFPlot();

        fPlot.getInterpolator().setMethod(getInterpolator().getMethod());
        fPlot.getInterpolator().setHermiteBias(getInterpolator().getHermiteBias());
        fPlot.getInterpolator().setHermiteTension(getInterpolator().getHermiteTension());

        forEach((x, y, index) -> fPlot.add(x, y));

        return fPlot;
    }

    @Override
    public boolean isEqual(FPlot fPlot) {

        if (!getInterpolator().isEqual(fPlot.getInterpolator())) {
            return false;
        }

        return isEqualData(fPlot);
    }

    @Override
    public boolean isEqualWithNaN(FPlot fPlot) {

        if (!getInterpolator().isEqual(fPlot.getInterpolator())) {
            return false;
        }

        return isEqualDataWithNaN(fPlot);
    }

    @Override
    public boolean isEqualData(FPlot fPlot) {

        if (this.size() != fPlot.size()) {
            return false;
        }

        return this.getRefCoreX().isEqualData(fPlot.getRefCoreX()) && this.getRefCoreY().isEqualData(fPlot.getRefCoreY());
    }

    @Override
    public boolean isEqualDataWithNaN(FPlot fPlot) {

        if (this.size() != fPlot.size()) {
            return false;
        }

        return this.getRefCoreX().isEqualDataWithNaN(fPlot.getRefCoreX()) && this.getRefCoreY().isEqualDataWithNaN(fPlot.getRefCoreY());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_DATA_X, getRefCoreX().toJSON());
        json.put(JSON_DATA_Y, getRefCoreY().toJSON());
        json.put(JSON_INT, getInterpolator().toJSON());

        return json;
    }

    @Override
    public JSONObject toSimpleJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_DATA_X, getRefCoreX().toSimpleJSON());
        json.put(JSON_DATA_Y, getRefCoreY().toSimpleJSON());
        json.put(JSON_INT, getInterpolator().toJSON());

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
    public void setName(String name) {

        this.name = name;
    }

    @Override
    public FPos2D getFPos2D(int index) {

        return factory.getFPos2D(getX(index), getY(index));
    }
}

// https://paulbourke.net/miscellaneous/interpolation/