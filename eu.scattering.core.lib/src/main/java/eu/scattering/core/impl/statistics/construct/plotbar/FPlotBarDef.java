package eu.scattering.core.impl.statistics.construct.plotbar;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.utility.lambda.TriConsumer;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.utility.type.option.RoundMethod;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class FPlotBarDef implements FPlotBar {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "bar";
    private static final String JSON_DATA_X = "dataX";
    private static final String JSON_DATA_Y = "dataY";

    private final ScatFactory factory;

    private final FStat dataX;
    private final List<FStat> dataY;

    private String name = "";

    private FPlotBarDef(ScatFactory factory, FStat dataX, List<FStat> dataY) {

        this.factory = factory;

        this.dataX = dataX == null ? factory.getFStat() : dataX;
        this.dataY = dataY == null ? new ArrayList<>() : dataY;

        if (this.dataX.size() != this.dataY.size()) {
            throw new IllegalArgumentException("The data is corrupted");
        }
    }

    public static FPlotBar create(ScatFactory factory) {

        return new FPlotBarDef(factory, null, null);
    }

    public static FPlotBar create(ScatFactory factory, FStat dataX, List<FStat> dataY) {

        return new FPlotBarDef(factory, dataX, dataY);
    }

    public static FPlotBar create(ScatFactory factory, JSONObject json) {
        FStat dataX = factory.getFStat(json.getJSONObject(JSON_DATA_X));

        List<FStat> dataY = new ArrayList<>();

        JSONArray dataRawY = json.getJSONArray(JSON_DATA_Y);
        for (int i = 0 ; i < dataRawY.length() ; i++) {
            dataY.add(factory.getFStat(dataRawY.getJSONObject(i)));
        }

        return new FPlotBarDef(factory, dataX, dataY);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPlotBar add(double x) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefCoreX().add(x);
        getRefCoreY().add(this.factory.getFStat());

        return this;
    }

    @Override
    public FPlotBar add(double x, double y) {
        int position = position(x);

        if (position < 0) {
            FStat dataY = factory.getFStat();
            dataY.add(y);

            getRefCoreX().add(x);
            getRefCoreY().add(dataY);
        } else {
            FStat dataY = getRefY(position);

            dataY.add(y);
        }

        return this;
    }

    @Override
    public FPlotBar add(double x, FStat y) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefCoreX().add(x);
        getRefCoreY().add(y.copy());

        return this;
    }

    @Override
    public FPlotBar addRef(double x, FStat refY) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getRefCoreX().add(x);
        getRefCoreY().add(refY);

        return this;
    }

    @Override
    public double getX(int index) {

        return getRefCoreX().get(index);
    }

    @Override
    public FPlotBar setX(int index, double x) {

        getRefCoreX().set(index, x);

        return this;
    }

    @Override
    public FStat getY(int index) {

        return getRefCoreY().get(index).copy();
    }

    @Override
    public FPlotBar setY(int index, FStat y) {

        getRefCoreY().set(index, y.copy());

        return this;
    }

    @Override
    public FStat getRefY(int index) {

        return getRefCoreY().get(index);
    }

    @Override
    public FPlotBar setRefY(int index, FStat refY) {

        getRefCoreY().set(index, refY);

        return this;
    }

    @Override
    public int getIndexX(RoundMethod type, double x) {

        return switch (type) {
            case CLOSEST -> getIndexXRound(x);
            case FLOOR -> getIndexXFloor(x);
            case CEIL -> getIndexXCeil(x);
        };
    }

    @Override
    public int filter(BiFunction<Double, FStat, Boolean> filter) {
        int oldSize = size();

        List<Double> dataX = new ArrayList<>();
        List<FStat> dataY = new ArrayList<>();

        for (int i = 0 ; i < size() ; i++) {
            if (filter.apply(getX(i), getRefY(i))) {
                dataX.add(getX(i));
                dataY.add(getRefY(i));
            }
        }

        getRefCoreX().clear();
        getRefCoreX().getRefCore().addAll(dataX);
        getRefCoreY().clear();
        getRefCoreY().addAll(dataY);

        return oldSize - size();
    }

    @Override
    public FPlotBar mutateX(Consumer<FStat> consumer) {

        consumer.accept(getRefCoreX());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return this;
    }

    @Override
    public FPlotBar mutateX(BiFunction<Double, FStat, Double> function) {

        for (int i = 0 ; i < size() ; i++) {
            setX(i, function.apply(getX(i), getY(i)));
        }

        return this;
    }

    @Override
    public FPlotBar mutateY(Consumer<List<FStat>> consumer) {

        consumer.accept(getRefCoreY());

        if (getRefCoreX().size() != getRefCoreY().size()) {
            throw new IllegalStateException("The number of elements is erroneous");
        }

        return this;
    }

    @Override
    public FPlotBar mutateY(BiFunction<Double, FStat, FStat> function) {

        for (int i = 0 ; i < size() ; i++) {
            setY(i, function.apply(getX(i), getY(i)));
        }

        return this;
    }

    @Override
    public FPlotBar sortX(boolean ascending) {
        List<Pair> data = new ArrayList<>(size());

        forEach((x, y, index) -> data.add(FPlotBarDef.Pair.create(x, y)));

        if (ascending) {
            sortAscX(data);
        } else {
            sortDscX(data);
        }

        for (int i = 0 ; i < size() ; i++) {
            Pair item = data.get(i);
            setX(i, item.getX());
            setRefY(i, item.getY());
        }

        return this;
    }

    @Override
    public FPlotBar forEach(TriConsumer<Double, FStat, Integer> consumer) {
        Iterator<Double> iteratorX = getRefCoreX().iterator();
        Iterator<FStat> iteratorY = getRefCoreY().iterator();

        for (int i = 0; i < getRefCoreX().size() ; i++) {
            consumer.accept(iteratorX.next(), iteratorY.next(), i);
        }

        return this;
    }

    @Override
    public FPlot toFPlot(Function<FStat, Double> function) {
        FPlot fPlot = this.factory.getFPlot();

        forEach((x, y, index) -> fPlot.add(x, function.apply(y)));

        return fPlot;
    }

    @Override
    public FStat getRefCoreX() {

        return this.dataX;
    }

    @Override
    public List<FStat> getRefCoreY() {

        return this.dataY;
    }

    // -------------------------------------------------------------------------------------------------

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
    public boolean isEqual(FPlotBar fBar) {

        return isEqualData(fBar);
    }

    @Override
    public boolean isEqualWithNaN(FPlotBar fBar) {

        return isEqualDataWithNaN(fBar);
    }

    @Override
    public boolean isEqualData(FPlotBar fBar) {

        if (this.size() != fBar.size()) {
            return false;
        }

        if (!getRefCoreX().isEqualData(fBar.getRefCoreX())) {
            return false;
        }

        for (int i = 0 ; i < size() ; i++) {
            if (!getRefY(i).isEqualData(fBar.getRefY(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isEqualDataWithNaN(FPlotBar fBar) {

        if (this.size() != fBar.size()) {
            return false;
        }

        if (!getRefCoreX().isEqualDataWithNaN(fBar.getRefCoreX())) {
            return false;
        }

        for (int i = 0 ; i < size() ; i++) {
            if (!getRefY(i).isEqualDataWithNaN(fBar.getRefY(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public FPlotBar copy() {
        FPlotBar fBar = factory.getFPlotBar();

        forEach((x, y, index) -> fBar.add(x, y));

        return fBar;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_DATA_X, getRefCoreX().toJSON());

        for (FStat dataY : getRefCoreY()) {
            json.append(JSON_DATA_Y, dataY.toJSON());
        }

        return json;
    }

    @Override
    public JSONObject toSimpleJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_DATA_X, getRefCoreX().toSimpleJSON());

        return json;
    }

    @Override
    public String toString() {

        return toSimpleJSON().toString();
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

    private int position(double x) {

        for (int i = 0 ; i < size() ; i++) {
            if (getX(i) == x) {
                return i;
            }
        }

        return -1;
    }

    private void sortAscX(List<Pair> data) {

        data.sort((a, b) -> a.getX() - b.getX() > 0 ? 1 : a.getX() == b.getX() ? 0 : -1);
    }

    private void sortDscX(List<Pair> data) {

        data.sort((a, b) -> a.getX() - b.getX() < 0 ? 1 : a.getX() == b.getX() ? 0 : -1);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public String getName() {

        return this.name;
    }

    @Override
    public FPlotBar setName(String name) {

        this.name = name;

        return this;
    }

    @Override
    public FPlotBar removeNaN() {

        filter((x, y) -> {

            if (Double.isNaN(x)) {
                return false;
            }

            for (FStat fStat : getRefCoreY()) {
                fStat.removeNaN();

                if (fStat.size() == 0) {
                    return false;
                }
            }

            return true;

        });

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    private static class Pair {
        final double valX;
        final FStat valY;

        private Pair(double valX, FStat valY) {

            this.valX = valX;
            this.valY = valY;
        }

        protected static Pair create(double valX, FStat valY) {

            return new FPlotBarDef.Pair(valX, valY);
        }

        public double getX() {

            return this.valX;
        }

        public FStat getY() {

            return this.valY;
        }
    }
}
