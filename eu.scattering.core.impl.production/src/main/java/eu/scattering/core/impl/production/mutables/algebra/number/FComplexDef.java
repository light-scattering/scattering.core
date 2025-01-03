package eu.scattering.core.impl.production.mutables.algebra.number;

import eu.scattering.core.design.mutables.algebra.number.complex.FComplex;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.position.FPos2D.FPos2D;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Objects;

import static eu.scattering.core.impl.production.configurations.NameConfig.JSON_TYPE;

public class FComplexDef implements FComplex {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "cpx";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0 };
    private final double epsilon;

    private FComplexDef(double epsilon) {

        this.epsilon = epsilon;
    }

    public static FComplex create(double epsilon) {

        return new FComplexDef(epsilon);
    }

    @Override
    public double getRe() {

        return origin[0];
    }

    @Override
    public FComplex setRe(double re) {

        origin[0] = re;

        return this;
    }

    @Override
    public double getIm() {

        return origin[1];
    }

    @Override
    public FComplex setIm(double im) {

        origin[1] = im;

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
    public FComplex set(FPos2D position) {

        return set(position.getD0(), position.getD1());
    }

    @Override
    public FComplex applyStateTo(FComplex ref) {

        ref.applyStateFrom(this);

        return this;
    }

    @Override
    public FComplex applyStateFrom(FComplex ref) {

        return set(ref.getRe(), ref.getIm());
    }

    @Override
    public FComplex applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        var re = structure.getDouble(0);
        var im = structure.getDouble(1);

        return set(re, im);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FComplex ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == ref) {
            return true;
        }

        return getRe() == ref.getRe() && getIm() == ref.getIm();
    }

    @Override
    public boolean isSimilar(FComplex ref) {

        if (ref == null) {
            throw new NullPointerException("The reference FComplex cannot be null");
        }

        if (this == ref) {
            return true;
        }

        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceIm = Math.abs(getIm() - ref.getIm());

        return distanceRe < epsilon && distanceIm < epsilon;
    }

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

        return FComplexDef.create(epsilon);
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
    public FComplex add(FComplex ref) {

        return add(ref.getRe(), ref.getIm());
    }

    @Override
    public FComplex add(double re, double im) {

        return addRe(re).addIm(im);
    }

    @Override
    public FComplex add(double factor) {

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
    public FComplex sub(FComplex ref) {

        return sub(ref.getRe(), ref.getIm());
    }

    @Override
    public FComplex sub(double re, double im) {

        return subRe(re).subIm(im);
    }

    @Override
    public FComplex sub(double factor) {

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
    public FComplex mul(FComplex ref) {

        double valueRe = getRe() * ref.getRe() - getIm() * ref.getIm();
        double valueIm = getRe() * ref.getIm() + getIm() * ref.getRe();

        return set(valueRe, valueIm);
    }

    @Override
    public FComplex mul(double re, double im) {

        return mulRe(re).mulIm(im);
    }

    @Override
    public FComplex mul(double factor) {

        return mul(factor, factor);
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
    public FComplex div(FComplex ref) {

        if (ref.isZero()) {
            throw new ArithmeticException("The divisor cannot be zero");
        }

        double nominatorRe = (getRe() * ref.getRe()) + (getIm() * ref.getIm());
        double nominatorIm = (getIm() * ref.getRe()) - (getRe() * ref.getIm());
        double denominator = (ref.getRe() * ref.getRe()) + (ref.getIm() * ref.getIm());

        return set(nominatorRe / denominator, nominatorIm / denominator);
    }

    @Override
    public FComplex div(double re, double im) {

        return divRe(re).divIm(im);
    }

    @Override
    public FComplex div(double factor) {

        return div(factor, factor);
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
    public boolean isSimilar(double re, double im) {
        double distanceRe = Math.abs(getRe() - re);
        double distanceIm = Math.abs(getIm() - im);

        return distanceRe < epsilon && distanceIm < epsilon;
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
    public double getDistanceP2(FComplex ref) {
        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceIm = Math.abs(getIm() - ref.getIm());

        return (distanceRe * distanceRe) + (distanceIm * distanceIm);
    }

    @Override
    public double getDistance(FComplex ref) {
        double distanceRe = Math.abs(getRe() - ref.getRe());
        double distanceIm = Math.abs(getIm() - ref.getIm());

        return Math.sqrt((distanceRe * distanceRe) + (distanceIm * distanceIm));
    }

    // TODO - Not implemented
    @Override
    public FComplex setDistance(FComplex ref, double distance) {

        return null;
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

        return mul(-1);
    }

    @Override
    public FComplex inverse() {

        copyZero().set(1, 0).div(this).applyStateTo(this);

        return this;
    }

    @Override
    public FComplex conjugate() {

        return mulIm(-1);
    }

    @Override
    public FComplex normalize() {

        return setMagnitude(1);
    }
}
