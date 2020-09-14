package eu.scattering.core.implementation.main.algebra.type.quaternion;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.implementation.main.algebra.AlgebraPresetDevelopment;
import org.json.JSONObject;

import static eu.scattering.core.Config.factory;

public class FQuaternionDevelopment extends AlgebraPresetDevelopment<FQuaternion> implements FQuaternion {

    private static final Statistics classStatistics = factory.getStatistics().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FQuaternion create(FQuaternion core) {

        numberOfInstances++;
        return new FQuaternionDevelopment(core);
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

        FQuaternionDevelopment.numberOfInstances = numberOfInstances;
    }

    private FQuaternionDevelopment(FQuaternion core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion set(FQuaternion fQuaternion) {

        String name = "set(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = getCore().set(fQuaternion);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion set(double re, double i, double j, double k) {

        String name = "set(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().set(re, i, j, k);

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
    public FQuaternion setRe(double re) {

        String name = "setRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getI() {

        String name = "getI()";
        long time = System.currentTimeMillis();

        var res = getCore().getI();

        updateStats(name, time);

        return res;
    }

    @Override
    public FQuaternion setI(double i) {

        String name = "setI(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setI(i);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getJ() {

        String name = "getJ()";
        long time = System.currentTimeMillis();

        var res = getCore().getJ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FQuaternion setJ(double j) {

        String name = "setJ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setJ(j);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getK() {

        String name = "getK()";
        long time = System.currentTimeMillis();

        var res = getCore().getK();

        updateStats(name, time);

        return res;
    }

    @Override
    public FQuaternion setK(double k) {

        String name = "setK(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setK(k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isExact(FQuaternion element) {

        String name = "isExact(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FQuaternion element) {

        String name = "isSimilar(FQuaternion)";
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
    public FQuaternion importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = getCore().importFromJSON(json);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(getCore().copy());

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion self() {

        return this;
    }


    @Override
    public FQuaternion add(FQuaternion element) {

        String name = "add(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = getCore().add(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion sub(FQuaternion element) {

        String name = "sub(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mul(FQuaternion element) {

        String name = "mul(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion div(FQuaternion element) {

        String name = "div(FQuaternion)";
        long time = System.currentTimeMillis();

        var res = getCore().div(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }


    @Override
    public FQuaternion add(double re, double i, double j, double k) {

        String name = "add(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(re, i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion addRe(double re) {

        String name = "addRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion addIm(double i, double j, double k) {

        String name = "addIm(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().addIm(i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion addI(double i) {

        String name = "addI(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addI(i);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion addJ(double j) {

        String name = "addJ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addJ(j);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion addK(double k) {

        String name = "addK(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addK(k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion sub(double re, double i, double j, double k) {

        String name = "sub(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(re, i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion subRe(double re) {

        String name = "subRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion subIm(double i, double j, double k) {

        String name = "subIm(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().subIm(i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion subI(double i) {

        String name = "subI(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subI(i);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion subJ(double j) {

        String name = "subJ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subJ(j);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion subK(double k) {

        String name = "subK(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subK(k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mul(double re, double i, double j, double k) {

        String name = "mul(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(re, i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mulRe(double re) {

        String name = "mulRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mulIm(double i, double j, double k) {

        String name = "mulIm(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulIm(i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mulI(double i) {

        String name = "mulI(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulI(i);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mulJ(double j) {

        String name = "mulJ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulJ(j);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion mulK(double k) {

        String name = "mulK(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulK(k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion div(double re, double i, double j, double k) {

        String name = "div(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(re, i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion divRe(double re) {

        String name = "divRe(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divRe(re);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion divIm(double i, double j, double k) {

        String name = "divIm(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().divIm(i, j, k);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion divI(double i) {

        String name = "divI(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divI(i);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion divJ(double j) {

        String name = "divJ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divJ(j);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion divK(double k) {

        String name = "divK(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divK(k);

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
    public FQuaternion setMagnitude(double magnitude) {

        String name = "setMagnitude(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setMagnitude(magnitude);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion pow(int n) {

        String name = "pow(int)";
        long time = System.currentTimeMillis();

        var res = getCore().pow(n);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion negate() {

        String name = "negate()";
        long time = System.currentTimeMillis();

        var res = getCore().negate();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion inverse() {

        String name = "inverse()";
        long time = System.currentTimeMillis();

        var res = getCore().inverse();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion conjugate() {

        String name = "conjugate()";
        long time = System.currentTimeMillis();

        var res = getCore().conjugate();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion normalize() {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = getCore().normalize();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FQuaternion imprint(FQuaternion element) {

        String name = "imprint(FQuaternion)";
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
    public boolean isExact(double re, double i, double j, double k) {

        String name = "isExact(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(re, i, j, k);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double re, double i, double j, double k) {

        String name = "isSimilar(double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isSimilar(re, i, j, k);

        updateStats(name, time);

        return res;
    }
}