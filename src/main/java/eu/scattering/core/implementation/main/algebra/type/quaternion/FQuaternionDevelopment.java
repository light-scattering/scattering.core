package eu.scattering.core.implementation.main.algebra.type.quaternion;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.implementation.main.algebra.AlgebraPresetDefault;
import org.json.JSONObject;

import java.util.Optional;

import static eu.scattering.core.Config.factory;

public class FQuaternionDevelopment extends AlgebraPresetDefault<FQuaternion> implements FQuaternion {

    private static long numberOfInstances = 0;

    private static final Statistics statsClass = factory.getStatistics().setEnabled(true);
    private final Statistics statsObject = factory.getStatistics();

    private final FQuaternion core;

    private FQuaternionDevelopment(FQuaternion core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FQuaternion create(FQuaternion core) {

        return new FQuaternionDevelopment(core);
    }

    @Override
    public FQuaternion devSetStatisticsEnabled(boolean enabled) {

        statsObject.setEnabled(enabled);

        return this;
    }

    @Override
    public FQuaternion set(FQuaternion fQuaternion) {

        String name = "set(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = core.set(fQuaternion);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion set(double re, double i, double j, double k) {

        String name = "set(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.set(re, i, j, k);

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
    public FQuaternion setRe(double re) {

        String name = "setRe(double)";
        long time = System.currentTimeMillis();

        var res = core.setRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getI() {

        String name = "getI()";
        long time = System.currentTimeMillis();

        var res = core.getI();

        updateStats(name, time);

        return res;
    }

    @Override
    public FQuaternion setI(double i) {

        String name = "setI(double)";
        long time = System.currentTimeMillis();

        var res = core.setI(i);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getJ() {

        String name = "getJ()";
        long time = System.currentTimeMillis();

        var res = core.getJ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FQuaternion setJ(double j) {

        String name = "setJ(double)";
        long time = System.currentTimeMillis();

        var res = core.setJ(j);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getK() {

        String name = "getK()";
        long time = System.currentTimeMillis();

        var res = core.getK();

        updateStats(name, time);

        return res;
    }

    @Override
    public FQuaternion setK(double k) {

        String name = "setK(double)";
        long time = System.currentTimeMillis();

        var res = core.setK(k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(FQuaternion element) {

        String name = "isExact(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FQuaternion element) {

        String name = "isSimilar(FQuaternion)";
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
    public FQuaternion importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(core.copy());

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion self() {

        return this;
    }


    @Override
    public FQuaternion add(FQuaternion element) {

        String name = "add(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = core.add(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion sub(FQuaternion element) {

        String name = "sub(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = core.sub(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mul(FQuaternion element) {

        String name = "mul(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = core.mul(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion div(FQuaternion element) {

        String name = "div(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = core.div(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.add(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = core.sub(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = core.mul(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = core.div(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }


    @Override
    public FQuaternion add(double re, double i, double j, double k) {

        String name = "add(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.add(re, i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion addRe(double re) {

        String name = "addRe(double)";
        long time = System.currentTimeMillis();

        var res = core.addRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion addIm(double i, double j, double k) {

        String name = "addIm(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.addIm(i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion addI(double i) {

        String name = "addI(double)";
        long time = System.currentTimeMillis();

        var res = core.addI(i);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion addJ(double j) {

        String name = "addJ(double)";
        long time = System.currentTimeMillis();

        var res = core.addJ(j);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion addK(double k) {

        String name = "addK(double)";
        long time = System.currentTimeMillis();

        var res = core.addK(k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion sub(double re, double i, double j, double k) {

        String name = "sub(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.sub(re, i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion subRe(double re) {

        String name = "subRe(double)";
        long time = System.currentTimeMillis();

        var res = core.subRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion subIm(double i, double j, double k) {

        String name = "subIm(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.subIm(i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion subI(double i) {

        String name = "subI(double)";
        long time = System.currentTimeMillis();

        var res = core.subI(i);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion subJ(double j) {

        String name = "subJ(double)";
        long time = System.currentTimeMillis();

        var res = core.subJ(j);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion subK(double k) {

        String name = "subK(double)";
        long time = System.currentTimeMillis();

        var res = core.subK(k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mul(double re, double i, double j, double k) {

        String name = "mul(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.mul(re, i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mulRe(double re) {

        String name = "mulRe(double)";
        long time = System.currentTimeMillis();

        var res = core.mulRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mulIm(double i, double j, double k) {

        String name = "mulIm(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.mulIm(i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mulI(double i) {

        String name = "mulI(double)";
        long time = System.currentTimeMillis();

        var res = core.mulI(i);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mulJ(double j) {

        String name = "mulJ(double)";
        long time = System.currentTimeMillis();

        var res = core.mulJ(j);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion mulK(double k) {

        String name = "mulK(double)";
        long time = System.currentTimeMillis();

        var res = core.mulK(k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion div(double re, double i, double j, double k) {

        String name = "div(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.div(re, i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion divRe(double re) {

        String name = "divRe(double)";
        long time = System.currentTimeMillis();

        var res = core.divRe(re);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion divIm(double i, double j, double k) {

        String name = "divIm(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.divIm(i, j, k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion divI(double i) {

        String name = "divI(double)";
        long time = System.currentTimeMillis();

        var res = core.divI(i);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion divJ(double j) {

        String name = "divJ(double)";
        long time = System.currentTimeMillis();

        var res = core.divJ(j);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion divK(double k) {

        String name = "divK(double)";
        long time = System.currentTimeMillis();

        var res = core.divK(k);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion devDesc() {

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
    public FQuaternion devSetLabel(String label) {

        String name = "devSetLabel(String)";
        long time = System.currentTimeMillis();

        var res = core.devSetLabel(label);

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
    public FQuaternion setMagnitude(double magnitude) {

        String name = "setMagnitude(double)";
        long time = System.currentTimeMillis();

        var res = core.setMagnitude(magnitude);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion pow(int n) {

        String name = "pow(int)";
        long time = System.currentTimeMillis();

        var res = core.pow(n);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion negate() {

        String name = "negate()";
        long time = System.currentTimeMillis();

        var res = core.negate();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion inverse() {

        String name = "inverse()";
        long time = System.currentTimeMillis();

        var res = core.inverse();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion conjugate() {

        String name = "conjugate()";
        long time = System.currentTimeMillis();

        var res = core.conjugate();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion normalize() {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = core.normalize();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FQuaternion imprint(FQuaternion element) {

        String name = "imprint(FQuaternion)";
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
    public boolean isExact(double re, double i, double j, double k) {

        String name = "isExact(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isExact(re, i, j, k);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double re, double i, double j, double k) {

        String name = "isSimilar(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(re, i, j, k);

        updateStats(name, time);

        return res;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        return create((FQuaternion) core.clone());
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

        if (object instanceof FQuaternion) {
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
    public FQuaternion devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public FQuaternion devDescStatistics() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FQuaternion devDescClassStatistics() {

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
    public FQuaternion devDescNumberOfInstances() {

        String data = "Number of instances for FQuaternionDevelopment: " + numberOfInstances + "\n";

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