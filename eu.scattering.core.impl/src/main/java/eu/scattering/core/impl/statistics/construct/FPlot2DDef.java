package eu.scattering.core.impl.statistics.construct;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.statistics.construct.utils.FPlot2DInterpolator;
import eu.scattering.core.design.statistics.base.FStat1D;
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

public class FPlot2DDef implements FPlot2D {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "plot2D";
    private static final String JSON_DATA_X = "dataX";
    private static final String JSON_DATA_Y = "dataY";
    private static final String JSON_INT = "interpolator";

    private final ScatFactory factory;

    private FPlot2DInterpolator interpolator;
    private FStat1D dataX;
    private FStat1D dataY;

    private String name = "";

    private FPlot2DDef(ScatFactory factory) {

        this.factory = factory;
    }

    public static FPlot2D create(ScatFactory factory) {
        FPlot2DDef fPlot = new FPlot2DDef(factory);

        fPlot.interpolator = FPlot2DInterpolatorDef.create();

        fPlot.dataX = factory.getFStat1D();
        fPlot.dataY = factory.getFStat1D();

        return fPlot;
    }

    public static FPlot2D create(ScatFactory factory, FLayer fLayer) {
        FPlot2DDef fPlot = new FPlot2DDef(factory);

        fPlot.interpolator = FPlot2DInterpolatorDef.create();

        fPlot.dataX = factory.getFStat1D();
        fPlot.dataY = factory.getFStat1D();

        for (int i = 0 ; i < fLayer.size() ; i++) {
            fPlot.dataX.add(i);
            fPlot.dataY.add(fLayer.get(i));
        }

        return fPlot;
    }

    public static FPlot2D create(ScatFactory factory, JSONObject json) {
        FPlot2DDef fPlot = new FPlot2DDef(factory);

        fPlot.interpolator = FPlot2DInterpolatorDef.create(json.getJSONObject(JSON_INT));

        fPlot.dataX = factory.getFStat1D(json.getJSONObject(JSON_DATA_X));
        fPlot.dataY = factory.getFStat1D(json.getJSONObject(JSON_DATA_Y));

        if (fPlot.dataX.size() != fPlot.dataY.size()) {
            throw new IllegalArgumentException("The data is corrupted");
        }

        return fPlot;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public void add(double x) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefFStatX().add(x);
        getRefFStatY().add(0);
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x) {
        int position = position(x);

        if (position < 0) {
            getRefFStatX().add(x);
            getRefFStatY().add(0);
        } else {
            getRefFStatY().set(position, collision.apply(getRefFStatY().get(position), 0d));
        }
    }

    @Override
    public void add(double x, double y) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefFStatX().add(x);
        getRefFStatY().add(y);
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x, double y) {
        int position = position(x);

        if (position < 0) {
            getRefFStatX().add(x);
            getRefFStatY().add(y);
        } else {
            getRefFStatY().set(position, collision.apply(getRefFStatY().get(position), y));
        }
    }

    @Override
    public double getX(int index) {

        return getRefFStatX().get(index);
    }

    @Override
    public void setX(int index, double x) {

        getRefFStatX().set(index, x);
    }

    @Override
    public double getY(int index) {

        return getRefFStatY().get(index);
    }

    @Override
    public void setY(int index, double y) {

        getRefFStatY().set(index, y);
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
    public <T> T getWithFStat(BiFunction<FStat1D, FStat1D, T> function) {
        T value = function.apply(getRefFStatX(), getRefFStatY());

        if (getRefFStatX().size() != getRefFStatY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return value;
    }

    @Override
    public <T> T getWithFStatX(Function<FStat1D, T> function) {
        T value = function.apply(this.dataX);

        if (getRefFStatX().size() != getRefFStatY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return value;
    }

    @Override
    public <T> T getWithFStatY(Function<FStat1D, T> function) {
        T value = function.apply(this.dataY);

        if (getRefFStatX().size() != getRefFStatY().size()) {
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
        FStat1D sx = getRefFStatX();
        FStat1D sy = getRefFStatY();

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

        FStat1D fStatX = factory.getFStat1D();
        FStat1D fStatY = factory.getFStat1D();

        for (int i = 0 ; i < size() ; i++) {
            if (filter.apply(getX(i), getY(i))) {
                fStatX.add(getX(i));
                fStatY.add(getY(i));
            }
        }

        setRefFStatX(fStatX);
        setRefFStatY(fStatY);

        return oldSize - size();
    }

    @Override
    public void setY(FPoly est) {

        mutateY((x, y) -> est.getValue(x));
    }

    @Override
    public void mutateFStat(Consumer<FStat1D> consumer) {

        consumer.accept(getRefFStatX());
        consumer.accept(getRefFStatY());

        if (getRefFStatX().size() != getRefFStatY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }
    }

    @Override
    public void mutateFStat(BiConsumer<FStat1D, FStat1D> consumer) {

        consumer.accept(getRefFStatX(), getRefFStatY());

        if (getRefFStatX().size() != getRefFStatY().size()) {
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
    public void mutateFStatX(Consumer<FStat1D> consumer) {

        consumer.accept(getRefFStatX());

        if (getRefFStatX().size() != getRefFStatY().size()) {
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
    public void mutateFStatY(Consumer<FStat1D> consumer) {

        consumer.accept(getRefFStatY());

        if (getRefFStatX().size() != getRefFStatY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }
    }

    @Override
    public void interpolate(double step, boolean overflow) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step value must be greater than zero");
        }

        double minX = getRefFStatX().min();
        double maxX = getRefFStatX().max();

        FStat1D fStatX = factory.getFStat1D();
        FStat1D fStatY = factory.getFStat1D();

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

        setRefFStatX(fStatX);
        setRefFStatY(fStatY);
    }

    @Override
    public void interpolate(double divisions) {

        if (divisions < 1) {
            throw new IllegalArgumentException("The number of divisions cannot be smaller then one");
        }

        double minX = getRefFStatX().min();
        double maxX = getRefFStatX().max();

        double step = (maxX - minX) / divisions;

        FStat1D fStatX = factory.getFStat1D();
        FStat1D fStatY = factory.getFStat1D();

        double value = minX;
        while (value < maxX) {
            fStatX.add(value);
            fStatY.add(approximate(value));

            value += step;
        }

        fStatX.add(maxX);
        fStatY.add(approximate(maxX));

        setRefFStatX(fStatX);
        setRefFStatY(fStatY);
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

//    @Override
//    public void log(double base, boolean x, boolean y) {
//        double denominator = Math.log(base);
//
//        if (x) {
//            mutateX((dx, dy) -> Math.log(dx) / denominator);
//        }
//
//        if (y) {
//            mutateY((dx, dy) -> Math.log(dy) / denominator);
//        }
//    }

    @Override
    public void forEach(TriConsumer<Double, Double, Integer> consumer) {
        Iterator<Double> iteratorX = getRefFStatX().iterator();
        Iterator<Double> iteratorY = getRefFStatY().iterator();

        for (int i = 0; i < getRefFStatX().size() ; i++) {
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
    public FPlot2DInterpolator getInterpolator() {

        return this.interpolator;
    }

    @Override
    public FStat1D getRefFStatX() {

        return this.dataX;
    }

    @Override
    public void setRefFStatX(FStat1D fStat1DX) {

        this.dataX = fStat1DX;
    }

    @Override
    public FStat1D getRefFStatY() {

        return this.dataY;
    }

    @Override
    public void setRefFStatY(FStat1D fStat1DY) {

        this.dataY = fStat1DY;
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

        return getRefFStatX().size();
    }

    @Override
    public void clear() {

        getRefFStatX().clear();
        getRefFStatY().clear();
    }

    @Override
    public FPlot2D copy() {
        FPlot2D fPlot = factory.getFPlot2D();

        fPlot.getInterpolator().setMethod(getInterpolator().getMethod());
        fPlot.getInterpolator().setHermiteBias(getInterpolator().getHermiteBias());
        fPlot.getInterpolator().setHermiteTension(getInterpolator().getHermiteTension());

        forEach((x, y, index) -> fPlot.add(x, y));

        return fPlot;
    }

    @Override
    public boolean isEqual(FPlot2D fPlot2D) {

        if (!getInterpolator().isEqual(fPlot2D.getInterpolator())) {
            return false;
        }

        return isEqualData(fPlot2D);
    }

    @Override
    public boolean isEqualData(FPlot2D fPlot2D) {

        if (this.size() != fPlot2D.size()) {
            return false;
        }

        return this.getRefFStatX().isEqualData(fPlot2D.getRefFStatX()) && this.getRefFStatY().isEqualData(fPlot2D.getRefFStatY());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_DATA_X, getRefFStatX().toJSON());
        json.put(JSON_DATA_Y, getRefFStatY().toJSON());
        json.put(JSON_INT, getInterpolator().toJSON());

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
    public FPos2D getRecord(int index) {

        return factory.getFPos2D(getX(index), getY(index));
    }
}

// https://paulbourke.net/miscellaneous/interpolation/