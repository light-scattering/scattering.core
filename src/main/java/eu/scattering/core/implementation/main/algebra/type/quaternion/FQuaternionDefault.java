package eu.scattering.core.implementation.main.algebra.type.quaternion;

import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.implementation.main.algebra.EnginePreset;
import org.json.JSONObject;

public class FQuaternionDefault extends EnginePreset<FQuaternion> implements FQuaternion {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final double[] origin = { 0.0, 0.0, 0.0, 0.0 };

    private FQuaternionDefault() { }

    public static FQuaternion create() {

        return new FQuaternionDefault();
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
    public FQuaternion set(FQuaternion fQuaternion) {

        return set(fQuaternion.getRe(), fQuaternion.getI(), fQuaternion.getJ(), fQuaternion.getK());
    }

    @Override
    public FQuaternion set(double re, double i, double j, double k) {

        return setRe(re).setI(i).setJ(j).setK(k);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @Override
    public FQuaternion importFromJSON(JSONObject json) {
        return null;
    }

    @Override
    public FQuaternion copy() {
        return null;
    }

    @Override
    public FQuaternion self() {
        return null;
    }

    @Override
    public boolean isSimilar(FQuaternion element) {
        return false;
    }

    @Override
    public boolean isExact(FQuaternion element) {
        return false;
    }

    @Override
    public JSONObject exportToJSON() {
        return null;
    }

    @Override
    public double getMagnitude() {
        return 0;
    }

    @Override
    public double setMagnitude(double magnitude) {
        return 0;
    }

    @Override
    public FQuaternion add(FQuaternion fQuaternion) {
        return null;
    }

    @Override
    public FQuaternion add(double re, double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion add(double factor) {
        return null;
    }

    @Override
    public FQuaternion addRe(double re) {
        return null;
    }

    @Override
    public FQuaternion addIm(double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion sub(FQuaternion fQuaternion) {
        return null;
    }

    @Override
    public FQuaternion sub(double re, double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion sub(double factor) {
        return null;
    }

    @Override
    public FQuaternion subRe(double re) {
        return null;
    }

    @Override
    public FQuaternion subIm(double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion mul(FQuaternion fQuaternion) {
        return null;
    }

    @Override
    public FQuaternion mul(double re, double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion mul(double factor) {
        return null;
    }

    @Override
    public FQuaternion mulRe(double re) {
        return null;
    }

    @Override
    public FQuaternion mulIm(double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion divL(FQuaternion fQuaternion) {
        return null;
    }

    @Override
    public FQuaternion devR(FQuaternion fQuaternion) {
        return null;
    }

    @Override
    public FQuaternion div(double re, double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion div(double factor) {
        return null;
    }

    @Override
    public FQuaternion divRe(double re) {
        return null;
    }

    @Override
    public FQuaternion divIm(double i, double j, double k) {
        return null;
    }

    @Override
    public FQuaternion pow(int n) {
        return null;
    }

    @Override
    public FQuaternion[] root(int n) {
        return new FQuaternion[0];
    }

    @Override
    public FQuaternion inverse() {
        return null;
    }

    @Override
    public FQuaternion conjugate() {
        return null;
    }

    @Override
    public FQuaternion normalize() {
        return null;
    }

    @Override
    public FQuaternion imprint(FQuaternion fQuaternion) {
        return null;
    }

    @Override
    public boolean isZero() {
        return false;
    }
}
