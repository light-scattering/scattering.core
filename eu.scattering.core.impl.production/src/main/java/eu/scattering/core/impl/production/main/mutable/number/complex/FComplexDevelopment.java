package eu.scattering.core.impl.production.main.mutable.number.complex;

import eu.scattering.core.test.design.development.statistics.Statistics;
import eu.scattering.core.test.design.main.mutable.number.complex.FComplex;
import eu.scattering.core.impl.production.development.statistics.StatisticsDefault;
import eu.scattering.core.impl.production.main.mutable.MutablePresetDevelopment;
import org.json.JSONObject;

public class FComplexDevelopment extends MutablePresetDevelopment<FComplex> implements FComplex {

    private static final Statistics classStatistics = StatisticsDefault.create().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FComplex create(FComplex core) {

        numberOfInstances++;
        return new FComplexDevelopment(core);
    }

    @Override
    protected Statistics getClassStatistics() {

        return classStatistics;
    }

    @Override
    protected long getNumberOfInstances() {

        return numberOfInstances;
    }

    @Override
    protected void setNumberOfInstances(long numberOfInstances) {

        FComplexDevelopment.numberOfInstances = numberOfInstances;
    }

    private FComplexDevelopment(FComplex core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex set(FComplex fComplex) {

        String name = "set(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().set(fComplex);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex set(double re, double im) {

        String name = "set(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().set(re, im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getRe() {

        String name = "getRe()";
        long time = System.currentTimeMillis();

        var res = getCore().getRe();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setRe(double re) {

        String name = "setRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getIm() {

        String name = "getIm()";
        long time = System.currentTimeMillis();

        var res = getCore().getIm();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setIm(double im) {

        String name = "setIm(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setIm(im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isExact(FComplex element) {

        String name = "isExact(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FComplex element) {

        String name = "isSimilar(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().isSimilar(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public JSONObject exportToJSON() {

        String name = "exportToJSON()";
        long time = System.currentTimeMillis();

        var res = getCore().exportToJSON();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = getCore().importFromJSON(json);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(getCore().copy());

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex self() {

        return this;
    }

    @Override
    public FComplex add(FComplex element) {

        String name = "add(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().add(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex sub(FComplex element) {

        String name = "sub(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex mul(FComplex element) {

        String name = "mul(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex div(FComplex element) {

        String name = "div(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().div(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getMagnitude() {

        String name = "getMagnitude()";
        long time = System.currentTimeMillis();

        var res = getCore().getMagnitude();

        updateStats(name, time);

        return res;
    }

    @Override
    public double getMagnitudeP2() {

        String name = "getMagnitudeP2()";
        long time = System.currentTimeMillis();

        var res = getCore().getMagnitudeP2();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setMagnitude(double magnitude) {

        String name = "setMagnitude(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setMagnitude(magnitude);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getDistance(FComplex element) {

        String name = "getDistance(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().getDistance(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public double getDistanceP2(FComplex element) {

        String name = "getDistanceP2(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().getDistanceP2(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex pow(int n) {

        String name = "pow(int)";
        long time = System.currentTimeMillis();

        var res = getCore().pow(n);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex[] root(int n) {

        String name = "root(int)";
        long time = System.currentTimeMillis();

        var res = getCore().root(n);

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex negate() {

        String name = "negate()";
        long time = System.currentTimeMillis();

        var res = getCore().negate();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex inverse() {

        String name = "inverse()";
        long time = System.currentTimeMillis();

        var res = getCore().inverse();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex conjugate() {

        String name = "conjugate()";
        long time = System.currentTimeMillis();

        var res = getCore().conjugate();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex normalize() {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = getCore().normalize();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex imprint(FComplex element) {

        String name = "imprint(FComplex)";
        long time = System.currentTimeMillis();

        var res = getCore().imprint(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isZero() {

        String name = "isZero()";
        long time = System.currentTimeMillis();

        var res = getCore().isZero();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isExact(double re, double im) {

        String name = "isExact(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(re, im);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double re, double im) {

        String name = "isSimilar(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isSimilar(re, im);

        updateStats(name, time);

        return res;
    }

    @Override
    public double getPhase() {

        String name = "getPhase()";
        long time = System.currentTimeMillis();

        var res = getCore().getPhase();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setPhase(double phase) {

        String name = "setPhase(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setPhase(phase);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex add(double re, double im) {

        String name = "add(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(re, im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex addRe(double re) {

        String name = "addRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex addIm(double im) {

        String name = "addIm(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addIm(im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex sub(double re, double im) {

        String name = "sub(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(re, im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex subRe(double re) {

        String name = "subRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex subIm(double re) {

        String name = "subIm(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subIm(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex mul(double re, double im) {

        String name = "mul(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(re, im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex mulRe(double re) {

        String name = "mulRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex mulIm(double im) {

        String name = "mulIm(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulIm(im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex div(double re, double im) {

        String name = "div(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(re, im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex divRe(double re) {

        String name = "divRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FComplex divIm(double im) {

        String name = "divIm(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divIm(im);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }
}