package eu.scattering.core.impl.mutables.number;

import eu.scattering.core.design.mutables.number.complex.FComplex;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FComplexDef implements FComplex {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "cpx";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private double oRe, oIm;

    private FComplexDef(double re, double im) {

        this.oRe = re;
        this.oIm = im;
    }

    public static FComplex create(double re, double im) {

        return new FComplexDef(re, im);
    }

    @Override
    public double getRe() {

        return this.oRe;
    }

    @Override
    public FComplex setRe(double re) {

        this.oRe = re;

        return this;
    }

    @Override
    public double getIm() {

        return this.oIm;
    }

    @Override
    public FComplex setIm(double im) {

        this.oIm = im;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex set(double re, double im) {

        return setRe(re).setIm(im);
    }

    @Override
    public FComplex applyStateFrom(FPos2D position) {

        return set(position.getD0(), position.getD1());
    }

    @Override
    public FComplex applyStateFrom(FComplex arg) {

        return set(arg.getRe(), arg.getIm());
    }

    @Override
    public FComplex applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        double re = structure.getDouble(0);
        double im = structure.getDouble(1);

        return set(re, im);
    }

    @Override
    public FComplex applyStateTo(FComplex arg) {

        arg.applyStateFrom(this);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex self() {

        return this;
    }

    @Override
    public FComplex copy() {

        return copyZero().applyStateFrom(this);
    }

    @Override
    public FComplex copyZero() {

        return FComplexDef.create(0, 0);
    }

    @Override
    public FPos2D toFPos2D() {

        return factory.getFPos2D(getRe(), getIm());
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getRe());
        json.append(JSON_VAL, getIm());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getRe(), getIm());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FComplex) {
            FComplex ref = (FComplex) object;

            return getRe() == ref.getRe() && getIm() == ref.getIm();
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex add(FPos2D arg) {

        return add(arg.getD0(), arg.getD1());
    }

    @Override
    public FComplex add(FComplex arg) {

        return add(arg.getRe(), arg.getIm());
    }

    @Override
    public FComplex add(double re, double im) {

        return addRe(re).addIm(im);
    }

    @Override
    public FComplex addFactor(double factor) {

        return add(factor, factor);
    }

    @Override
    public FComplex addRe(double re) {

        return setRe(getRe() + re);
    }

    @Override
    public FComplex addIm(double im) {

        return setIm(getIm() + im);
    }

    @Override
    public FComplex sub(FPos2D arg) {

        return sub(arg.getD0(), arg.getD1());
    }

    @Override
    public FComplex sub(FComplex arg) {

        return sub(arg.getRe(), arg.getIm());
    }

    @Override
    public FComplex sub(double re, double im) {

        return subRe(re).subIm(im);
    }

    @Override
    public FComplex subFactor(double factor) {

        return sub(factor, factor);
    }

    @Override
    public FComplex subRe(double re) {

        return setRe(getRe() - re);
    }

    @Override
    public FComplex subIm(double re) {

        return setIm(getIm() - re);
    }

    @Override
    public FComplex mul(FComplex arg) {
        double valueRe = getRe() * arg.getRe() - getIm() * arg.getIm();
        double valueIm = getRe() * arg.getIm() + getIm() * arg.getRe();

        return set(valueRe, valueIm);
    }

    @Override
    public FComplex mul(double re, double im) {
        double valueRe = getRe() * re - getIm() * im;
        double valueIm = getRe() * im + getIm() * re;

        return set(valueRe, valueIm);
    }

    @Override
    public FComplex mulFactor(double factor) {

        return mulRe(factor).mulIm(factor);
    }

    @Override
    public FComplex mulRe(double re) {

        return setRe(getRe() * re);
    }

    @Override
    public FComplex mulIm(double im) {

        return setIm(getIm() * im);
    }

    @Override
    public FComplex div(FComplex arg) {

        if (arg.isZero()) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        double nominatorRe = (getRe() * arg.getRe()) + (getIm() * arg.getIm());
        double nominatorIm = (getIm() * arg.getRe()) - (getRe() * arg.getIm());
        double denominator = (arg.getRe() * arg.getRe()) + (arg.getIm() * arg.getIm());

        return set(nominatorRe / denominator, nominatorIm / denominator);
    }

    @Override
    public FComplex div(double re, double im) {

        if (re == 0 && im == 0) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        double nominatorRe = (getRe() * re) + (getIm() * im);
        double nominatorIm = (getIm() * re) - (getRe() * im);
        double denominator = (re * re) + (im * im);

        return set(nominatorRe / denominator, nominatorIm / denominator);
    }

    @Override
    public FComplex divFactor(double factor) {

        return divRe(factor).divIm(factor);
    }

    @Override
    public FComplex divRe(double re) {

        if (re == 0) {
            throw new ArithmeticException("The real part of the FComplex value cannot be zero");
        }

        return setRe(getRe() / re);
    }

    @Override
    public FComplex divIm(double im) {

        if (im == 0) {
            throw new ArithmeticException("The imaginary part of the FComplex value cannot be zero");
        }

        return setIm(getIm() / im);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isZero() {

        return getRe() == 0 && getIm() == 0;
    }

    @Override
    public boolean isExact(double re, double im) {

        return getRe() == re && getIm() == im;
    }

    @Override
    public boolean isExact(FComplex arg) {

        if (arg == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == arg) {
            return true;
        }

        return getRe() == arg.getRe() && getIm() == arg.getIm();
    }

    @Override
    public boolean isExact(FPos2D arg) {

        return isExact(arg.getD0(), arg.getD1());
    }

    @Override
    public boolean isSimilar(double re, double im) {
        double distRe = Math.abs(getRe() - re);
        double distIm = Math.abs(getIm() - im);

        return distRe < EPSILON && distIm < EPSILON;
    }

    @Override
    public boolean isSimilar(FComplex arg) {

        if (arg == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == arg) {
            return true;
        }

        double distanceRe = Math.abs(getRe() - arg.getRe());
        double distanceIm = Math.abs(getIm() - arg.getIm());

        return distanceRe < EPSILON && distanceIm < EPSILON;
    }

    @Override
    public boolean isSimilar(FPos2D arg) {

        return isSimilar(arg.getD0(), arg.getD1());
    }

    @Override
    public double getMagnitudeP2() {

        return (getRe() * getRe()) + (getIm() * getIm());
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt((getRe() * getRe()) + (getIm() * getIm()));
    }

    @Override
    public FComplex setMagnitude(double magnitude) {
        double phase = getPhase();

        return setRe(magnitude * Math.cos(phase)).setIm(magnitude * Math.sin(phase));
    }

    @Override
    public double getDistanceP2(double re, double im) {
        double distRe = Math.abs(getRe() - re);
        double distIm = Math.abs(getIm() - im);

        return (distRe * distRe) + (distIm * distIm);
    }

    @Override
    public double getDistanceP2(FComplex arg) {

        return getDistanceP2(arg.getRe(), arg.getIm());
    }

    @Override
    public double getDistanceP2(FPos2D arg) {
        double distRe = Math.abs(getRe() - arg.getD0());
        double distIm = Math.abs(getIm() - arg.getD1());

        return (distRe * distRe) + (distIm * distIm);
    }

    @Override
    public double getDistance(double re, double im) {
        double distRe = Math.abs(getRe() - re);
        double distIm = Math.abs(getIm() - im);

        return Math.sqrt((distRe * distRe) + (distIm * distIm));
    }

    @Override
    public double getDistance(FComplex arg) {

        return getDistance(arg.getRe(), arg.getIm());
    }

    @Override
    public double getDistance(FPos2D arg) {

        return getDistance(arg.getD0(), arg.getD1());
    }

    // TODO - Not implemented
    @Override
    public FComplex setDistance(double re, double im, double distance) {

        return null;
    }

    // TODO - Not implemented
    @Override
    public FComplex setDistance(FComplex arg, double distance) {

        return setDistance(arg.getRe(), arg.getIm(), distance);
    }

    // TODO - Not implemented
    @Override
    public FComplex setDistance(FPos2D arg, double distance) {

        return setDistance(arg.getD0(), arg.getD1(), distance);
    }

    @Override
    public double getPhase() {

        if (isZero()) {
            throw new IllegalStateException("The direction is not defined");
        }

        double magnitude = getMagnitude();

        if (getIm() >= 0 && magnitude != 0) {
            return Math.acos(getRe() / magnitude);
        }

        if (getIm() < 0) {
            return Math.acos(getRe() / magnitude) * (-1);
        }

        return 0;
    }

    @Override
    public FComplex setPhase(double phase) {
        double magnitude = getMagnitude();

        return setRe(magnitude * Math.cos(phase)).setIm(magnitude * Math.sin(phase));
    }

    @Override
    public FComplex power(int n) {
        double power = Math.pow(getMagnitude(), n);
        double phase = getPhase();

        return setRe(power * Math.cos(n * phase)).setIm(power * Math.sin(n * phase));
    }

    @Override
    public FComplex[] root(int n) {

        if (n <= 0) {
            throw new IllegalArgumentException("The root value must be greater than zero");
        }

        FComplex[] res = new FComplex[n];
        double tmp = Math.pow(getMagnitude(), 1 / (double) n);
        double phase = getPhase();

        for (int i = 0; i < n; i++) {
            double valueRe = tmp * Math.cos((phase + (2 * i * Math.PI)) / n);
            double valueIm = tmp * Math.sin((phase + (2 * i * Math.PI)) / n);

            res[i] = copyZero().set(valueRe, valueIm);
        }

        return res;
    }

    @Override
    public FComplex negate() {

        return mulFactor(-1);
    }

    @Override
    public FComplex inverse() {
        double denominator = (getRe() * getRe()) + (getIm() * getIm());

        return set(getRe() / denominator, -getIm() / denominator);
    }

    @Override
    public FComplex conjugate() {

        return mulIm(-1);
    }

    @Override
    public FComplex normalize() {

        return setMagnitude(1);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FComplex apply(Consumer<FComplex> action) {

        action.accept(this);

        return this;
    }

    @Override
    public double toDouble(Function<FComplex, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FComplex, Boolean> action) {

        return action.apply(this);
    }
}
