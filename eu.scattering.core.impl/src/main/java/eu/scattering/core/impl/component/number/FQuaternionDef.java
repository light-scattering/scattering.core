package eu.scattering.core.impl.component.number;

import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.component.number.quaternion.FQuaternionFactory;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FQuaternionDef implements FQuaternion {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_MAIN = "qt";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FQuaternionFactory factorySelf;

    private double oRe, oI, oJ, oK;

    private FQuaternionDef(FQuaternionFactory factorySelf) {

        this.factorySelf = factorySelf;
    }

    public static FQuaternion create(FQuaternionFactory factorySelf) {

        return new FQuaternionDef(factorySelf);
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
    public FQuaternion applyStateFrom(FPos4D position) {

        return set(position.getD0(), position.getD1(), position.getD2(), position.getD3());
    }

    @Override
    public FQuaternion applyStateTo(FQuaternion in) {

        in.applyStateFrom(this);

        return this;
    }

    @Override
    public FQuaternion applyStateFrom(FQuaternion arg) {

        return set(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
    }

    @Override
    public FQuaternion set(JSONObject json) {

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
    public FQuaternion self() {

        return this;
    }

    @Override
    public FQuaternion copy() {

        return supplyFQuaternion().applyStateFrom(this);
    }

    @Override
    public FPos4D toFPos4D() {

        return factoryExt.getFPos4D(getRe(), getI(), getJ(), getK());
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
    public FQuaternion add(FPos4D arg) {

        return add(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3());
    }

    @Override
    public FQuaternion add(double re, double i, double j, double k) {

        return addRe(re).addIm(i, j, k);
    }

    @Override
    public FQuaternion addFactor(double factor) {

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
    public FQuaternion sub(FPos4D arg) {

        return sub(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3());
    }

    @Override
    public FQuaternion sub(double re, double i, double j, double k) {

        return subRe(re).subIm(i, j, k);
    }

    @Override
    public FQuaternion subFactor(double factor) {

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
    public FQuaternion mulFactor(double factor) {

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
    public FQuaternion divFactor(double factor) {

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
    public boolean isExact(FQuaternion arg) {

        return isExact(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
    }

    @Override
    public boolean isExact(FPos4D arg) {

        return isExact(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3());
    }

    @Override
    public boolean isSimilar(double re, double i, double j, double k) {
        double distanceRe = Math.abs(getRe() - re);
        double distanceI = Math.abs(getI() - i);
        double distanceJ = Math.abs(getJ() - j);
        double distanceK = Math.abs(getK() - k);

        return distanceRe < EPSILON && distanceI < EPSILON && distanceJ < EPSILON && distanceK < EPSILON;
    }

    @Override
    public boolean isSimilar(FQuaternion arg) {

        return isSimilar(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
    }

    @Override
    public boolean isSimilar(FPos4D arg) {

        return isSimilar(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3());
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
    public double getDistanceP2(double re, double i, double j, double k) {
        double distRe = Math.pow(Math.abs(getRe() - re), 2);
        double distI = Math.pow(Math.abs(getI() - i), 2);
        double distJ = Math.pow(Math.abs(getJ() - j), 2);
        double distK = Math.pow(Math.abs(getK() - k), 2);

        return distRe + distI + distJ + distK;
    }

    @Override
    public double getDistanceP2(FQuaternion arg) {

        return getDistanceP2(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
    }

    @Override
    public double getDistanceP2(FPos4D arg) {

        return getDistanceP2(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3());
    }

    @Override
    public double getDistance(double re, double i, double j, double k) {

        double distRe = Math.pow(Math.abs(getRe() - re), 2);
        double distI = Math.pow(Math.abs(getI() - i), 2);
        double distJ = Math.pow(Math.abs(getJ() - j), 2);
        double distK = Math.pow(Math.abs(getK() - k), 2);

        return Math.sqrt(distRe + distI + distJ + distK);
    }

    @Override
    public double getDistance(FPos4D arg) {

        return getDistance(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3());
    }

    @Override
    public double getDistance(FQuaternion arg) {

        return getDistance(arg.getRe(), arg.getI(), arg.getJ(), arg.getK());
    }

    // TODO - Not implemented
    @Override
    public FQuaternion setDistance(double re, double i, double j, double k, double distance) {

        return null;
    }

    // TODO - Not implemented
    @Override
    public FQuaternion setDistance(FQuaternion arg, double distance) {

        return setDistance(arg.getRe(), arg.getI(), arg.getJ(), arg.getK(), distance);
    }

    // TODO - Not implemented
    @Override
    public FQuaternion setDistance(FPos4D arg, double distance) {

        return setDistance(arg.getD0(), arg.getD1(), arg.getD2(), arg.getD3(), distance);
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

        return mulFactor(-1);
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
    public double toDouble(Function<FQuaternion, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FQuaternion, Boolean> action) {

        return action.apply(this);
    }

    // -------------------------------------------------------------------------------------------------

    private FQuaternion supplyFQuaternion() {

        return factorySelf.getFQuaternion();
    }
}

// http://tamivox.org/redbear/qtrn_calc/index.html