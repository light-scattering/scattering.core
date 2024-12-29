package eu.scattering.core.impl.production.core.mutable.number.quaternion;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.elements.engine.random.FRandom;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONArray;
import org.json.JSONObject;

public class FQuaternionProd implements FQuaternion {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0, 0.0, 0.0 };
    private final FactoryDesignConcrete factory;
    private final double epsilon;

    private FQuaternionProd(FactoryDesignConcrete factory, double epsilon) {

        this.factory = factory;
        this.epsilon = epsilon;
    }

    public static FQuaternion create(FactoryDesignConcrete factory, double epsilon) {

        return new FQuaternionProd(factory, epsilon);
    }

    @Override
    public double getRe() {

        return origin[0];
    }

    @Override
    public FQuaternion setRe(double re) {

        origin[0] = re;

        return this;
    }

    @Override
    public double getI() {

        return origin[1];
    }

    @Override
    public FQuaternion setI(double i) {

        origin[1] = i;

        return this;
    }

    @Override
    public double getJ() {

        return origin[2];
    }

    @Override
    public FQuaternion setJ(double j) {

        origin[2] = j;

        return this;
    }

    @Override
    public double getK() {

        return origin[3];
    }

    @Override
    public FQuaternion setK(double k) {

        origin[3] = k;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion applyStateFrom(FQuaternion ref) {

        return set(ref.getRe(), ref.getI(), ref.getJ(), ref.getK());
    }

    @Override
    public FQuaternion set(FPos4D position) {

        return set(position.getD0(), position.getD1(), position.getD2(), position.getD3());
    }

    @Override
    public FQuaternion set(double re, double i, double j, double k) {

        return setRe(re).setI(i).setJ(j).setK(k);
    }

    @Override
    public FPos4D toFPos4D() {

        return factory.getFPos4D(getRe(), getI(), getJ(), getK());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FQuaternion ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FQuaternion cannot be null");
        }

        if (this == ref) {
            return true;
        }

        return getRe() == ref.getRe() && getI() == ref.getI() &&
                getJ() == ref.getJ() && getK() == ref.getK();
    }

    @Override
    public boolean isSimilar(FQuaternion ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FQuaternion cannot be null");
        }

        if (this == ref) {
            return true;
        }

        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceI = Math.abs(getI() - ref.getI());
        double distanceJ = Math.abs(getJ() - ref.getJ());
        double distanceK = Math.abs(getK() - ref.getK());

        return distanceRe < epsilon && distanceI < epsilon && distanceJ < epsilon && distanceK < epsilon;
    }

    @Override
    public JSONObject exportToJSON() {

        JSONObject json = new JSONObject();

        json.append("quaternion", getRe());
        json.append("quaternion", getI());
        json.append("quaternion", getJ());
        json.append("quaternion", getK());

        return json;
    }

    @Override
    public FQuaternion importFromJSON(JSONObject json) {

        JSONArray structure = json.getJSONArray("quaternion");

        setRe(structure.getDouble(0));
        setI(structure.getDouble(1));
        setJ(structure.getDouble(2));
        setK(structure.getDouble(3));

        return this;
    }

    @Override
    public FQuaternion copy() {

        return FQuaternionProd.create(factory, epsilon).applyStateFrom(this);
    }

    @Override
    public FQuaternion self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + (int) (getRe() * 100);
        hashCode = 31 * hashCode + (int) (getI() * 100);
        hashCode = 31 * hashCode + (int) (getJ() * 100);
        hashCode = 31 * hashCode + (int) (getK() * 100);

        return hashCode;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion add(FQuaternion ref) {

        return add(ref.getRe(), ref.getI(), ref.getJ(), ref.getK());
    }

    @Override
    public FQuaternion add(double re, double i, double j, double k) {

        return addRe(re).addIm(i, j, k);
    }

    @Override
    public FQuaternion add(double factor) {

        return add(factor, factor, factor, factor);
    }

    @Override
    public FQuaternion addRe(double re) {

        return setRe(getRe() + re);
    }

    @Override
    public FQuaternion addIm(double i, double j, double k) {

        return addI(i).addJ(j).addK(k);
    }

    @Override
    public FQuaternion addI(double i) {

        return setI(getI() + i);
    }

    @Override
    public FQuaternion addJ(double j) {

        return setJ(getJ() + j);
    }

    @Override
    public FQuaternion addK(double k) {

        return setK(getK() + k);
    }

    @Override
    public FQuaternion sub(FQuaternion ref) {

        return sub(ref.getRe(), ref.getI(), ref.getJ(), ref.getK());
    }

    @Override
    public FQuaternion sub(double re, double i, double j, double k) {

        return subRe(re).subIm(i, j, k);
    }

    @Override
    public FQuaternion sub(double factor) {

        return sub(factor, factor, factor, factor);
    }

    @Override
    public FQuaternion subRe(double re) {

        return setRe(getRe() - re);
    }

    @Override
    public FQuaternion subIm(double i, double j, double k) {

        return subI(i).subJ(j).subK(k);
    }

    @Override
    public FQuaternion subI(double i) {

        return setI(getI() - i);
    }

    @Override
    public FQuaternion subJ(double j) {

        return setJ(getJ() - j);
    }

    @Override
    public FQuaternion subK(double k) {

        return setK(getK() - k);
    }

    @Override
    public FQuaternion mul(FQuaternion ref) {

        double valueRe = (ref.getRe() * getRe()) - (ref.getI() * getI()) -
                (ref.getJ() * getJ()) - (ref.getK() * getK());
        double valueI = (ref.getRe() * getI()) + (ref.getI() * getRe()) -
                (ref.getJ() * getK()) + (ref.getK() * getJ());
        double valueJ = (ref.getRe() * getJ()) + (ref.getI() * getK()) +
                (ref.getJ() * getRe()) - (ref.getK() * getI());
        double valueK = (ref.getRe() * getK()) - (ref.getI() * getJ()) +
                (ref.getJ() * getI()) + (ref.getK() * getRe());

        return set(valueRe, valueI, valueJ, valueK);
    }

    @Override
    public FQuaternion mul(double re, double i, double j, double k) {

        return mulRe(re).mulIm(i, j, k);
    }

    @Override
    public FQuaternion mul(double factor) {

        return mul(factor, factor, factor, factor);
    }

    @Override
    public FQuaternion mulRe(double re) {

        return setRe(getRe() * re);
    }

    @Override
    public FQuaternion mulIm(double i, double j, double k) {

        return mulI(i).mulJ(j).mulK(k);
    }

    @Override
    public FQuaternion mulI(double i) {

        return setI(getI() * i);
    }

    @Override
    public FQuaternion mulJ(double j) {

        return setJ(getJ() * j);
    }

    @Override
    public FQuaternion mulK(double k) {

        return setK(getK() * k);
    }

    @Override
    public FQuaternion div(FQuaternion ref) {

        if (ref.isZero()) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        return mul(ref.copy().inverse());
    }

    @Override
    public FQuaternion div(double re, double i, double j, double k) {

        return divRe(re).divI(i).divJ(j).divK(k);
    }

    @Override
    public FQuaternion div(double factor) {

        return div(factor, factor, factor, factor);
    }

    @Override
    public FQuaternion divRe(double re) {

        if (re == 0) {
            throw new ArithmeticException("The real part of the FQuaternion value cannot be zero");
        }

        return setRe(getRe() / re);
    }

    @Override
    public FQuaternion divIm(double i, double j, double k) {

        return divI(i).divJ(j).divK(k);
    }

    @Override
    public FQuaternion divI(double i) {

        if (i == 0) {
            throw new ArithmeticException("The imaginary part (I) of the FQuaternion value cannot be zero");
        }

        return setI(getI() / i);
    }

    @Override
    public FQuaternion divJ(double j) {

        if (j == 0) {
            throw new ArithmeticException("The imaginary part (J) of the FQuaternion value cannot be zero");
        }

        return setJ(getJ() / j);
    }

    @Override
    public FQuaternion divK(double k) {

        if (k == 0) {
            throw new ArithmeticException("The imaginary part (K) of the FQuaternion value cannot be zero");
        }

        return setK(getK() / k);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(double re, double i, double j, double k) {

        return getRe() == re && getI() == i && getJ() == j && getK() == k;
    }

    @Override
    public boolean isSimilar(double re, double i, double j, double k) {
        double distanceRe = Math.abs(getRe() - re);
        double distanceI = Math.abs(getI() - i);
        double distanceJ = Math.abs(getJ() - j);
        double distanceK = Math.abs(getK() - k);

        return distanceRe < epsilon && distanceI < epsilon && distanceJ < epsilon && distanceK < epsilon;
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt((getRe() * getRe()) + (getI() * getI()) + (getJ() * getJ())+ (getK() * getK()));
    }

    @Override
    public double getMagnitudeP2() {

        return (getRe() * getRe()) + (getI() * getI()) + (getJ() * getJ())+ (getK() * getK());
    }

    @Override
    public FQuaternion setMagnitude(double magnitude) {

        if (isZero()) {
            throw new IllegalStateException("The direction is not defined");
        }

        mul(Math.abs(magnitude) / getMagnitude());

        return magnitude > 0 ? this : negate();
    }

    @Override
    public double getDistance(FQuaternion ref) {
        double distanceRe = Math.pow(Math.abs(getRe() - ref.getRe()), 2);
        double distanceI = Math.pow(Math.abs(getI() - ref.getI()), 2);
        double distanceJ = Math.pow(Math.abs(getJ() - ref.getI()), 2);
        double distanceK = Math.pow(Math.abs(getK() - ref.getK()), 2);

        return Math.sqrt(distanceRe + distanceI + distanceJ + distanceK);
    }

    // TODO - Not implemented
    @Override
    public FQuaternion setDistance(FQuaternion ref, double distance) {
        return null;
    }

    @Override
    public double getDistanceP2(FQuaternion ref) {
        double distanceRe = Math.pow(Math.abs(getRe() - ref.getRe()), 2);
        double distanceI = Math.pow(Math.abs(getI() - ref.getI()), 2);
        double distanceJ = Math.pow(Math.abs(getJ() - ref.getI()), 2);
        double distanceK = Math.pow(Math.abs(getK() - ref.getK()), 2);

        return distanceRe + distanceI + distanceJ + distanceK;
    }

    @Override
    public FQuaternion power(int n) {

        if (n == 0) {
            return set(1, 0, 0, 0);
        }

        FQuaternion factor = copy();

        for (int i = 1 ; i < Math.abs(n) ; i++) {
            mul(factor);
        }

        return n > 0 ? this : inverse();
    }

    // TODO - Not implemented
    @Override
    public FQuaternion[] root(int n) {
        return new FQuaternion[0];
    }

    @Override
    public FQuaternion negate() {

        return mul(-1);
    }

    @Override
    public FQuaternion inverse() {

        if (isZero()) {
            throw new ArithmeticException("The direction is not defined");
        }

        double factor = (getRe() * getRe()) + (getI() * getI()) +
                (getJ() * getJ()) + (getK() * getK());

        return set(getRe() / factor, -getI() / factor, -getJ() / factor, -getK() / factor);
    }

    @Override
    public FQuaternion conjugate() {

        return mulIm(-1, -1, -1);
    }

    @Override
    public FQuaternion normalize() {

        return setMagnitude(1);
    }

    @Override
    public FQuaternion applyStateTo(FQuaternion ref) {

        ref.applyStateFrom(this);

        return this;
    }

    @Override
    public boolean isZero() {

        return getRe() == 0 && getI() == 0 && getJ() == 0 && getK() == 0;
    }
}

// http://tamivox.org/redbear/qtrn_calc/index.html
