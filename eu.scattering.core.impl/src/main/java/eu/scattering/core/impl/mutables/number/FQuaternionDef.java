package eu.scattering.core.impl.mutables.number;

import eu.scattering.core.design.mutables.number.quaternion.FQuaternion;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPos4D.FPos4D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FQuaternionDef implements FQuaternion {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "qt";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double epsilon;
    private double oRe, oI, oJ, oK;

    private FQuaternionDef(double epsilon, double re, double i, double j, double k) {

        this.epsilon = epsilon;
        this.oRe = re;
        this.oI = i;
        this.oJ = j;
        this.oK = k;
    }

    public static FQuaternion create(double epsilon, double re, double i, double j, double k) {

        return new FQuaternionDef(epsilon, re, i, j, k);
    }

    @Override
    public double getRe() {

        return this.oRe;
    }

    @Override
    public FQuaternion setRe(double re) {

        this.oRe = re;

        return this;
    }

    @Override
    public double getI() {

        return this.oI;
    }

    @Override
    public FQuaternion setI(double i) {

        this.oI = i;

        return this;
    }

    @Override
    public double getJ() {

        return this.oJ;
    }

    @Override
    public FQuaternion setJ(double j) {

        this.oJ = j;

        return this;
    }

    @Override
    public double getK() {

        return this.oK;
    }

    @Override
    public FQuaternion setK(double k) {

        this.oK = k;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion set(double re, double i, double j, double k) {

        return setRe(re).setI(i).setJ(j).setK(k);
    }

    @Override
    public FQuaternion set(FPos4D position) {

        return set(position.getD0(), position.getD1(), position.getD2(), position.getD3());
    }

    @Override
    public FQuaternion applyStateTo(FQuaternion arg) {

        arg.applyStateFrom(this);

        return this;
    }

    @Override
    public FQuaternion applyStateFrom(FQuaternion arg) {

        return set(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
    }

    @Override
    public FQuaternion applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        double re = structure.getDouble(0);
        double i = structure.getDouble(1);
        double j = structure.getDouble(2);
        double k = structure.getDouble(3);

        return set(re, i, j, k);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FQuaternion arg) {

        if (arg == null) {
            throw new NullPointerException("The reference FQuaternion cannot be null");
        }

        if (this == arg) {
            return true;
        }

        return getRe() == arg.getRe() && getI() == arg.getI() && getJ() == arg.getJ() && getK() == arg.getK();
    }

    @Override
    public boolean isSimilar(FQuaternion arg) {

        if (arg == null) {
            throw new NullPointerException("The reference FQuaternion cannot be null");
        }

        if (this == arg) {
            return true;
        }

        double distanceRe = Math.abs(getRe() - arg.getRe());
        double distanceI = Math.abs(getI() - arg.getI());
        double distanceJ = Math.abs(getJ() - arg.getJ());
        double distanceK = Math.abs(getK() - arg.getK());

        return distanceRe < epsilon && distanceI < epsilon && distanceJ < epsilon && distanceK < epsilon;
    }

    @Override
    public FQuaternion self() {

        return this;
    }

    @Override
    public FQuaternion copy() {

        return copyZero().applyStateFrom(this);
    }

    @Override
    public FQuaternion copyZero() {

        return FQuaternionDef.create(epsilon, 0, 0, 0, 0);
    }

    @Override
    public FPos4D toFPos4D() {

        return factory.getFPos4D(getRe(), getI(), getJ(), getK());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getRe());
        json.append(JSON_VAL, getI());
        json.append(JSON_VAL, getJ());
        json.append(JSON_VAL, getK());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getRe(), getI(), getJ(), getK());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FQuaternion) {
            FQuaternion ref = (FQuaternion) object;

            return getRe() == ref.getRe() && getI() == ref.getI() && getJ() == ref.getJ() && getK() == ref.getK();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion add(FQuaternion arg) {

        return add(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
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
    public FQuaternion sub(FQuaternion arg) {

        return sub(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
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
    public FQuaternion mul(FQuaternion arg) {

        double valueRe = (arg.getRe() * getRe()) - (arg.getI() * getI()) -
                (arg.getJ() * getJ()) - (arg.getK() * getK());
        double valueI = (arg.getRe() * getI()) + (arg.getI() * getRe()) -
                (arg.getJ() * getK()) + (arg.getK() * getJ());
        double valueJ = (arg.getRe() * getJ()) + (arg.getI() * getK()) +
                (arg.getJ() * getRe()) - (arg.getK() * getI());
        double valueK = (arg.getRe() * getK()) - (arg.getI() * getJ()) +
                (arg.getJ() * getI()) + (arg.getK() * getRe());

        return set(valueRe, valueI, valueJ, valueK);
    }

    @Override
    public FQuaternion mul(double re, double i, double j, double k) {

        double valueRe = (re * getRe()) - (i * getI()) -
                (j * getJ()) - (k * getK());
        double valueI = (re * getI()) + (i * getRe()) -
                (j * getK()) + (k * getJ());
        double valueJ = (re * getJ()) + (i * getK()) +
                (j * getRe()) - (k * getI());
        double valueK = (re * getK()) - (i * getJ()) +
                (j * getI()) + (k * getRe());

        return set(valueRe, valueI, valueJ, valueK);
    }

    @Override
    public FQuaternion mul(double factor) {

        return mulRe(factor).mulI(factor).mulJ(factor).mulK(factor);
    }

    @Override
    public FQuaternion mulRe(double re) {

        return setRe(getRe() * re);
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
    public FQuaternion div(FQuaternion arg) {

        if (arg.isZero()) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        double factor = (arg.getRe() * arg.getRe()) + (arg.getI() * arg.getI()) +
                (arg.getJ() * arg.getJ()) + (arg.getK() * arg.getK());

        double opRe = arg.getRe() / factor;
        double opI = -arg.getI() / factor;
        double opJ = -arg.getJ() / factor;
        double opK = -arg.getK() / factor;

        return mul(opRe, opI, opJ, opK);
    }

    @Override
    public FQuaternion div(double re, double i, double j, double k) {

        if (isZero()) {
            throw new ArithmeticException("The direction is not defined");
        }

        if (re == 0 && i == 0 && j == 0 && k == 0) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        double factor = (re * re) + (i * i) + (j * j) + (k * k);

        double opRe = re / factor;
        double opI = -i / factor;
        double opJ = -j / factor;
        double opK = -k / factor;

        return mul(opRe, opI, opJ, opK);
    }

    @Override
    public FQuaternion div(double factor) {

        return divRe(factor).divI(factor).divJ(factor).divK(factor);
    }

    @Override
    public FQuaternion divRe(double re) {

        if (re == 0) {
            throw new ArithmeticException("The real part of the FQuaternion value cannot be zero");
        }

        return setRe(getRe() / re);
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
    public boolean isZero() {

        return getRe() == 0 && getI() == 0 && getJ() == 0 && getK() == 0;
    }

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
    public double getMagnitudeP2() {

        return (getRe() * getRe()) + (getI() * getI()) + (getJ() * getJ())+ (getK() * getK());
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt((getRe() * getRe()) + (getI() * getI()) + (getJ() * getJ())+ (getK() * getK()));
    }

    @Override
    public FQuaternion setMagnitude(double magnitude) {

        if (isZero()) {
            throw new IllegalStateException("The direction is not defined");
        }

        double factor = Math.abs(magnitude) / getMagnitude();

        mulRe(factor).mulI(factor).mulJ(factor).mulK(factor);

        return magnitude > 0 ? this : negate();
    }

    @Override
    public double getDistanceP2(FQuaternion arg) {
        double distanceRe = Math.pow(Math.abs(getRe() - arg.getRe()), 2);
        double distanceI = Math.pow(Math.abs(getI() - arg.getI()), 2);
        double distanceJ = Math.pow(Math.abs(getJ() - arg.getI()), 2);
        double distanceK = Math.pow(Math.abs(getK() - arg.getK()), 2);

        return distanceRe + distanceI + distanceJ + distanceK;
    }

    @Override
    public double getDistance(FQuaternion arg) {
        double distanceRe = Math.pow(Math.abs(getRe() - arg.getRe()), 2);
        double distanceI = Math.pow(Math.abs(getI() - arg.getI()), 2);
        double distanceJ = Math.pow(Math.abs(getJ() - arg.getI()), 2);
        double distanceK = Math.pow(Math.abs(getK() - arg.getK()), 2);

        return Math.sqrt(distanceRe + distanceI + distanceJ + distanceK);
    }

    // TODO - Not implemented
    @Override
    public FQuaternion setDistance(FQuaternion arg, double distance) {

        return null;
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

        return mulI(-1).mulJ(-1).mulK(-1);
    }

    @Override
    public FQuaternion normalize() {

        return setMagnitude(1);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FQuaternion apply(Consumer<FQuaternion> action) {

        action.accept(this);

        return this;
    }

    @Override
    public FQuaternion applyWithFixedState(Consumer<FQuaternion> action) {
        double memoRe = this.getRe();
        double memoI = this.getI();
        double memoJ = this.getJ();
        double memoK = this.getK();

        action.accept(this);

        return set(memoRe, memoI, memoJ, memoK);
    }

    @Override
    public double toDouble(Function<FQuaternion, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FQuaternion, Boolean> action) {

        return action.apply(this);
    }

    @Override
    public double toDoubleWithFixedState(Function<FQuaternion, Double> action) {
        double memoRe = this.getRe();
        double memoI = this.getI();
        double memoJ = this.getJ();
        double memoK = this.getK();

        double results = action.apply(this);

        set(memoRe, memoI, memoJ, memoK);

        return results;
    }

    @Override
    public boolean toBooleanWithFixedState(Function<FQuaternion, Boolean> action) {
        double memoRe = this.getRe();
        double memoI = this.getI();
        double memoJ = this.getJ();
        double memoK = this.getK();

        boolean results = action.apply(this);

        set(memoRe, memoI, memoJ, memoK);

        return results;
    }
}

// http://tamivox.org/redbear/qtrn_calc/index.html