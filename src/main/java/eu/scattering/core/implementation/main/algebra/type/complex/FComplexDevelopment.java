package eu.scattering.core.implementation.main.algebra.type.complex;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import org.json.JSONObject;

import java.util.Optional;

import static eu.scattering.core.Config.factory;

public class FComplexDevelopment implements FComplex {

    private static long numberOfInstances = 0;

    private static final Statistics statsClass = factory.getStatistics().setEnabled(true);
    private final Statistics statsObject = factory.getStatistics();

    private final FComplex core;

    private FComplexDevelopment(FComplex core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FComplex create(FComplex core) {

        return new FComplexDevelopment(core);
    }

    @Override
    public FComplex devSetStatisticsEnabled(boolean enabled) {

        statsObject.setEnabled(enabled);

        return this;
    }

    @Override
    public FComplex set(FComplex fComplex) {

        String name = "set(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.set(fComplex);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex set(double re, double im) {

        String name = "set(double, double)";
        long time = System.currentTimeMillis();

        var res = core.set(re, im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getRe() {

        String name = "getRe()";
        long time = System.currentTimeMillis();

        var res = core.getRe();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setRe(double re) {

        String name = "setRe(double)";
        long time = System.currentTimeMillis();

        var res = core.setRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getIm() {

        String name = "getIm()";
        long time = System.currentTimeMillis();

        var res = core.getIm();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setIm(double im) {

        String name = "setIm(double)";
        long time = System.currentTimeMillis();

        var res = core.setIm(im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(FComplex element) {

        String name = "isExact(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FComplex element) {

        String name = "isSimilar(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public JSONObject exportToJSON() {

        String name = "exportToJSON()";
        long time = System.currentTimeMillis();

        var res = core.exportToJSON();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(core.copy());

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

        var res = core.add(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex sub(FComplex element) {

        String name = "sub(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.sub(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex mul(FComplex element) {

        String name = "mul(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.mul(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex div(FComplex element) {

        String name = "div(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.div(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.add(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = core.sub(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = core.mul(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = core.div(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getMagnitude() {

        String name = "getMagnitude()";
        long time = System.currentTimeMillis();

        var res = core.getMagnitude();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setMagnitude(double magnitude) {

        String name = "setMagnitude(double)";
        long time = System.currentTimeMillis();

        var res = core.setMagnitude(magnitude);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex pow(int n) {

        String name = "pow(int)";
        long time = System.currentTimeMillis();

        var res = core.pow(n);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex[] root(int n) {

        String name = "root(int)";
        long time = System.currentTimeMillis();

        var res = core.root(n);

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex negate() {

        String name = "negate()";
        long time = System.currentTimeMillis();

        var res = core.negate();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex inverse() {

        String name = "inverse()";
        long time = System.currentTimeMillis();

        var res = core.inverse();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex conjugate() {

        String name = "conjugate()";
        long time = System.currentTimeMillis();

        var res = core.conjugate();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex normalize() {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = core.normalize();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex imprint(FComplex element) {

        String name = "imprint(FComplex)";
        long time = System.currentTimeMillis();

        var res = core.imprint(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isZero() {

        String name = "isZero()";
        long time = System.currentTimeMillis();

        var res = core.isZero();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex devDesc() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDesc();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public String devGetLabel() {

        String name = "devGetLabel()";
        long time = System.currentTimeMillis();

        var res = core.devGetLabel();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex devSetLabel(String label) {

        String name = "devSetLabel(String)";
        long time = System.currentTimeMillis();

        var res = core.devSetLabel(label);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(double re, double im) {

        String name = "isExact(double, double)";
        long time = System.currentTimeMillis();

        var res = core.isExact(re, im);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double re, double im) {

        String name = "isSimilar(double, double)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(re, im);

        updateStats(name, time);

        return res;
    }

    @Override
    public double getPhase() {

        String name = "getPhase()";
        long time = System.currentTimeMillis();

        var res = core.getPhase();

        updateStats(name, time);

        return res;
    }

    @Override
    public FComplex setPhase(double phase) {

        String name = "setPhase(double)";
        long time = System.currentTimeMillis();

        var res = core.setPhase(phase);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex add(double re, double im) {

        String name = "add(double, double)";
        long time = System.currentTimeMillis();

        var res = core.add(re, im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex addRe(double re) {

        String name = "addRe(double)";
        long time = System.currentTimeMillis();

        var res = core.addRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex addIm(double im) {

        String name = "addIm(double)";
        long time = System.currentTimeMillis();

        var res = core.addIm(im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex sub(double re, double im) {

        String name = "sub(double, double)";
        long time = System.currentTimeMillis();

        var res = core.sub(re, im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex subRe(double re) {

        String name = "subRe(double)";
        long time = System.currentTimeMillis();

        var res = core.subRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex subIm(double re) {

        String name = "subIm(double)";
        long time = System.currentTimeMillis();

        var res = core.subIm(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex mul(double re, double im) {

        String name = "mul(double, double)";
        long time = System.currentTimeMillis();

        var res = core.mul(re, im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex mulRe(double re) {

        String name = "mulRe(double)";
        long time = System.currentTimeMillis();

        var res = core.mulRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex mulIm(double im) {

        String name = "mulIm(double)";
        long time = System.currentTimeMillis();

        var res = core.mulIm(im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex div(double re, double im) {

        String name = "div(double, double)";
        long time = System.currentTimeMillis();

        var res = core.div(re, im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex divRe(double re) {

        String name = "divRe(double)";
        long time = System.currentTimeMillis();

        var res = core.divRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FComplex divIm(double im) {

        String name = "divIm(double)";
        long time = System.currentTimeMillis();

        var res = core.divIm(im);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        return create((FComplex) core.clone());
    }

    @Override
    public String toString() {

        return core.toString();
    }

    @Override
    public int hashCode() {

        return core.hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FComplex) {
            return core.equals(object);
        }

        return false;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.of(numberOfInstances);
    }

    @Override
    public FComplex devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public FComplex devDescStatistics() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FComplex devDescClassStatistics() {

        Config.getDebugPrintStream().println(statsClass.toString());

        return self();
    }

    @Override
    public Optional<Statistics> devGetStatistics() {

        return Optional.of(statsObject);
    }

    @Override
    public Optional<Statistics> devGetClassStatistics() {

        return Optional.of(statsClass);
    }

    @Override
    public FComplex devDescNumberOfInstances() {

        String data = "Number of instances for FComplexDevelopment: " + numberOfInstances + "\n";

        Config.getDebugPrintStream().println(data);

        return self();
    };

    // -------------------------------------------------------------------------------------------------

    private void updateStats(String name, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        statsClass.recordEvent(name, time);
        statsObject.recordEvent(name, time);
    }

}