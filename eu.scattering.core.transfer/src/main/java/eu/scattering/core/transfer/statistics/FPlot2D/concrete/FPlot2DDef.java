package eu.scattering.core.transfer.statistics.FPlot2D.concrete;

import eu.scattering.core.transfer.statistics.FPlot2D.FPlot2D;
import eu.scattering.core.transfer.statistics.FStat1D.FStat1D;
import eu.scattering.core.transfer.statistics.StatisticsFactory;
import eu.scattering.core.transfer.statistics.StatisticsFactoryConcrete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static eu.scattering.core.transfer.configuration.NameConfig.JSON_TYPE;

public class FPlot2DDef implements FPlot2D {
    private static final StatisticsFactory factory = StatisticsFactoryConcrete.create();
    private static final String JSON_MAIN = "chart2D";
    private static final String JSON_DATA = "data";
    private static final String JSON_APX = "apx_method";
    private static final String JSON_H_BIAS = "h_bias";
    private static final String JSON_H_TENSION = "h_tension";

    private List<Record> data = new ArrayList<>();
    private Approx apx = Approx.HERMITE;

    private double hTension = 0;
    private double hBias = 0;

    private FPlot2DDef() {}

    public static FPlot2D create() {

        return new FPlot2DDef();
    }

    public static FPlot2D create(JSONObject json) {
        FPlot2DDef chart = new FPlot2DDef();

        JSONArray data = json.getJSONArray(JSON_DATA);
        for (int i = 0 ; i < data.length() ; i++) {
            chart.add(Record.create(data.getJSONObject(i)));
        }

        chart.setApproxHermiteTension(json.getDouble(JSON_H_TENSION));
        chart.setApproxHermiteBias(json.getDouble(JSON_H_BIAS));
        chart.setApproxMethod(json.getEnum(Approx.class, JSON_APX));

        return chart;
    }

    @Override
    public FPlot2D copy() {
        FPlot2D results = factory.getFPlot2D();

        results.setApproxMethod(this.getApproxMethod());
        results.setApproxHermiteBias(this.getApproxHermiteBias());
        results.setApproxHermiteTension(this.getApproxHermiteTension());

        for (Record record : getData()) {
            results.add(record.getX(), record.getY());
        }

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
    public void add(double x) {

        if (position(x) >= 0) {
            throw new IllegalStateException("The x value already exists");
        }

        getData().add(Record.create(x, 0));
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

        getData().add(Record.create(x, y));
    }

    @Override
    public void add(BiFunction<Double, Double, Double> collision, double x, double y) {
        int position = position(x);

        if (position < 0) {
            getData().add(Record.create(x, y));
        } else {
            Record record = getData().get(position);

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

        for (Record record : getData()) {
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

        List<Record> results = new ArrayList<>();

        for (int i = 0 ; i < statX.size() ; i++) {

            if (Double.isNaN(statX.get(i))) {
                continue;
            }

            results.add(Record.create(statX.get(i), getY(i)));
        }

        setData(results);
    }

    @Override
    public FStat1D getStatY() {
        FStat1D fStat = factory.getFStat1D();

        for (Record record : getData()) {
            fStat.add(record.getY());
        }

        return fStat;
    }

    @Override
    public void setStatY(FStat1D statY) {

        if (statY.size() != size()) {
            throw new IllegalArgumentException("The number of elements is erroneous");
        }

        List<Record> results = new ArrayList<>();

        for (int i = 0 ; i < statY.size() ; i++) {

            if (Double.isNaN(statY.get(i))) {
                continue;
            }

            results.add(Record.create(getX(i), statY.get(i)));
        }

        setData(results);
    }

    @Override
    public int getIndexRound(double x) {
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

        for (Record record : getData()) {
            if (record.getX() < valueMin) {
                valueMin = record.getX();
            }
        }

        return valueMin;
    }

    @Override
    public double minY() {
        double valueMin = Double.POSITIVE_INFINITY;

        for (Record record : getData()) {
            if (record.getY() < valueMin) {
                valueMin = record.getY();
            }
        }

        return valueMin;
    }

    @Override
    public double maxX() {
        double valueMax = Double.NEGATIVE_INFINITY;

        for (Record record : getData()) {
            if (record.getX() > valueMax) {
                valueMax = record.getX();
            }
        }

        return valueMax;
    }

    @Override
    public double maxY() {
        double valueMax = Double.NEGATIVE_INFINITY;

        for (Record record : getData()) {
            if (record.getY() > valueMax) {
                valueMax = record.getY();
            }
        }

        return valueMax;
    }

    @Override
    public double approximate(double x) {

        return switch (this.apx) {
            case LINEAR -> apxLinear(x);
            case COSINE -> apxCosine(x);
            case CUBIC -> apxCubic(x);
            case CATMULL_ROM -> apxCatmullRom(x);
            case HERMITE -> apxHermite(x);
        };
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

        for (Record record : this.data) {
            record.setX(function.apply(record.getX(), record.getY()));
        }
    }

    @Override
    public void mutateY(BiFunction<Double, Double, Double> function) {

        for (Record record : this.data) {
            record.setY(function.apply(record.getX(), record.getY()));
        }
    }

    @Override
    public void interpolate(double step, boolean overflow) {

        if (step <= 0) {
            throw new IllegalArgumentException("The step value must be greater than zero");
        }

        double minX = minX();
        double maxX = maxX();

        List<Record> list = new ArrayList<>();

        double value = minX;
        while (value < maxX) {
            list.add(Record.create(value, approximate(value)));
            value += step;
        }

        if (overflow) {
            list.add(Record.create(value, approximate(maxX)));
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

        List<Record> list = new ArrayList<>();

        double value = minX;
        while (value < maxX) {
            list.add(Record.create(value, approximate(value)));
            value += step;
        }

        list.add(Record.create(value, approximate(value)));

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
    public Approx getApproxMethod() {

        return this.apx;
    }

    @Override
    public void setApproxMethod(Approx approx) {

        this.apx = approx;
    }

    @Override
    public double getApproxHermiteBias() {

        return this.hBias;
    }

    @Override
    public void setApproxHermiteBias(double bias) {

        this.hBias = bias;
    }

    @Override
    public double getApproxHermiteTension() {

        return this.hTension;
    }

    @Override
    public void setApproxHermiteTension(double tension) {

        this.hTension = tension;
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

    private void add(Record record) {

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

    private double apxLinear(double x) {
        int indexL = getIndexFloor(x);

        if (indexL == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        int indexR = getIndexCeil(x);

        if (indexR == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        if (indexL == indexR) {
            return this.data.get(indexL).getY();
        }

        Record recordL = this.data.get(indexL);
        Record recordR = this.data.get(indexR);

        double tmp = (x - recordL.getX()) / (recordR.getX() - recordL.getX());

        return recordL.getY() * (1 - tmp) + (recordR.getY() * tmp);
    }

    private double apxCosine(double x) {
        int indexL = getIndexFloor(x);

        if (indexL == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        int indexR = getIndexCeil(x);

        if (indexR == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        if (indexL == indexR) {
            return this.data.get(indexL).getY();
        }

        Record record = this.data.get(indexL);
        Record recordR = this.data.get(indexR);

        double tmp1 = (x - record.getX()) / (recordR.getX() - record.getX());
        double tmp2 = (1 - Math.cos(tmp1 * Math.PI)) / 2;

        return record.getY() * (1 - tmp2) + (recordR.getY() * tmp2);
    }

    private double apxCubic(double x) {
        int indexL1 = getIndexFloor(x);

        if (indexL1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        int indexR1 = getIndexCeil(x);

        if (indexR1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        if (indexL1 == indexR1) {
            return this.data.get(indexL1).getY();
        }

        int indexL2 = indexL1 - 1;

        if (indexL2 < 0) {
            indexL2 = 0;
        }

        int indexR2 = indexR1 + 1;

        if (indexR2 > this.data.size() - 1) {
            indexR2 = this.data.size() - 1;
        }

        Record recordL2 = this.data.get(indexL2);
        Record recordL1 = this.data.get(indexL1);
        Record recordR1 = this.data.get(indexR1);
        Record recordR2 = this.data.get(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = tmp1 * tmp1;

        double a0 = recordR2.getY() - recordR1.getY() - recordL2.getY() + recordR1.getY();
        double a1 = recordL2.getY() - recordL1.getY() - a0;
        double a2 = recordR1.getY() - recordL2.getY();
        double a3 = recordL1.getY();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    private double apxCatmullRom(double x) {
        int indexL1 = getIndexFloor(x);

        if (indexL1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        int indexR1 = getIndexCeil(x);

        if (indexR1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        if (indexL1 == indexR1) {
            return this.data.get(indexL1).getY();
        }

        int indexL2 = indexL1 - 1;

        if (indexL2 < 0) {
            indexL2 = 0;
        }

        int indexR2 = indexR1 + 1;

        if (indexR2 > this.data.size() - 1) {
            indexR2 = this.data.size() - 1;
        }

        Record recordL2 = this.data.get(indexL2);
        Record recordL1 = this.data.get(indexL1);
        Record recordR1 = this.data.get(indexR1);
        Record recordR2 = this.data.get(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = tmp1 * tmp1;

        double a0 = (-0.5 * recordL2.getY()) + (1.5 * recordL1.getY()) - (1.5 * recordR1.getY()) + (0.5 * recordR2.getY());
        double a1 = recordL2.getY() - (2.5 * recordL1.getY()) + (2 * recordR1.getY()) - (0.5 * recordR2.getY());
        double a2 = (-0.5 * recordL2.getY()) + (0.5 * recordR1.getY());
        double a3 = recordL1.getY();

        return (a0 * tmp1 * tmp2) + (a1 * tmp2) + (a2 * tmp1) + a3;
    }

    private double apxHermite(double x) {
        int indexL1 = getIndexFloor(x);

        if (indexL1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        int indexR1 = getIndexCeil(x);

        if (indexR1 == -1) {
            throw new IllegalArgumentException("The provided value is out of range");
        }

        if (indexL1 == indexR1) {
            return this.data.get(indexL1).getY();
        }

        int indexL2 = indexL1 - 1;

        if (indexL2 < 0) {
            indexL2 = 0;
        }

        int indexR2 = indexR1 + 1;

        if (indexR2 > this.data.size() - 1) {
            indexR2 = this.data.size() - 1;
        }

        Record recordL2 = this.data.get(indexL2);
        Record recordL1 = this.data.get(indexL1);
        Record recordR1 = this.data.get(indexR1);
        Record recordR2 = this.data.get(indexR2);

        double tmp1 = (x - recordL1.getX()) / (recordR1.getX() - recordL1.getX());
        double tmp2 = tmp1 * tmp1;
        double tmp3 = tmp2 * tmp1;

        double m0 = (recordL1.getY() - recordL2.getY()) * (1 + this.hBias) * ((1 - this.hTension) / 2);
        m0 += (recordR1.getY() - recordL1.getY()) * (1 - this.hBias) * ((1 - this.hTension) / 2);
        double m1 = (recordR1.getY() - recordL1.getY()) * (1 + this.hBias) * ((1 - this.hTension) / 2);
        m1 += (recordR2.getY() - recordR1.getY()) * ( 1 - this.hBias) * ((1 - this.hTension) / 2);


        double a0 = (2 * tmp3) - (3 * tmp2) + 1;
        double a1 = tmp3 - (2 * tmp2) + tmp1;
        double a2 = tmp3 - tmp2;
        double a3 = (-2 * tmp3) + (3 * tmp2);

        return (a0 * recordL1.getY()) + (a1 * m0) + (a2 * m1) + (a3 * recordR1.getY());
    }

    //--------------------------------------------------

    @Override
    public boolean isEqual(FPlot2D fPlot2D) {

        if (this.getApproxMethod() != fPlot2D.getApproxMethod()) {
            return false;
        }

        if (this.getApproxHermiteBias() != fPlot2D.getApproxHermiteBias()) {
            return false;
        }

        if (this.getApproxHermiteTension() != fPlot2D.getApproxHermiteTension()) {
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

        json.put(JSON_H_TENSION, this.hTension);
        json.put(JSON_H_BIAS, this.hBias);
        json.put(JSON_APX, this.apx);

        for (Record record : this.data) {
            json.append(JSON_DATA, record.toJSON());
        }

        return json;
    }

    public String toString() {

        return toJSON().toString();
    }

    //--------------------------------------------------

    private List<Record> getData() {

        return this.data;
    }

    private void setData(List<Record> data) {

        this.data = data;
    }

    private static class Record {
        private static final String JSON_MAIN = "rec";
        private static final String JSON_X = "x";
        private static final String JSON_Y = "y";

        private double x;
        private double y;

        private Record(double x, double y) {

            this.x = x;
            this.y = y;
        }

        public static Record create(double x, double y) {

            return new Record(x, y);
        }

        public static Record create(JSONObject json) {

            return new Record(json.getDouble(JSON_X), json.getDouble(JSON_Y));
        }

        public double getX() {

            return this.x;
        }

        public void setX(double x) {

            this.x = x;
        }

        public double getY() {

            return this.y;
        }

        public void setY(double y) {

            this.y = y;
        }

        //--------------------------------------------------

        public JSONObject toJSON() {
            JSONObject json = new JSONObject();

            json.put(JSON_TYPE, JSON_MAIN);
            json.put(JSON_X, this.x);
            json.put(JSON_Y, this.y);

            return json;
        }

        @Override
        public String toString() {

            return toJSON().toString();
        }
    }
}

// https://paulbourke.net/miscellaneous/interpolation/