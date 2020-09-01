package eu.scattering.core.implementation.main.algebra.type.quaternion;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.Algebra;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDevelopment;
import org.json.JSONObject;

import java.util.Optional;

import static eu.scattering.core.Config.developmentFactory;

public class FQuaternionDevelopment implements FQuaternion {

    private static long numberOfInstances = 0;

    private static final Statistics statsClass = developmentFactory.getStatistics().setEnabled();
    private final Statistics statsObject = developmentFactory.getStatistics();

    private final FQuaternion core;

    private FQuaternionDevelopment(FQuaternion core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FQuaternion create(FQuaternion core) {

        return new FQuaternionDevelopment(core);
    }

    @Override
    public FQuaternion objectStatisticsEnable() {

        statsObject.setEnabled();

        return this;
    }

    @Override
    public FQuaternion objectStatisticsDisable() {

        statsObject.setDisabled();

        return this;
    }

    @Override
    public FQuaternion importFromJSON(JSONObject json) {
        return null;
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
    public FQuaternion copy() {
        return null;
    }

    @Override
    public FQuaternion self() {
        return null;
    }

    @Override
    public FQuaternion devDescribe() {
        return null;
    }

    @Override
    public FQuaternion devDescribeStatistics() {
        return null;
    }

    @Override
    public FQuaternion devDescribeClassStatistics() {
        return null;
    }

    @Override
    public Optional<Long> devGetNumberOfInstances() {
        return Optional.empty();
    }

    @Override
    public FQuaternion devResetNumberOfInstances() {
        return null;
    }

    @Override
    public Optional<Statistics> devGetStatistics() {
        return Optional.empty();
    }

    @Override
    public Optional<Statistics> devGetClassStatistics() {
        return Optional.empty();
    }

    @Override
    public String devGetLabel() {
        return null;
    }

    @Override
    public FQuaternion devSetLabel(String label) {
        return null;
    }

    @Override
    public JSONObject exportToJSON() {
        return null;
    }



    @Override
    public double getRe() {
        return 0;
    }

    @Override
    public FQuaternion setRe(double re) {
        return null;
    }

    @Override
    public double getI() {
        return 0;
    }

    @Override
    public FQuaternion setI(double i) {
        return null;
    }

    @Override
    public double getJ() {
        return 0;
    }

    @Override
    public FQuaternion setJ(double j) {
        return null;
    }

    @Override
    public double getK() {
        return 0;
    }

    @Override
    public FQuaternion setK(double k) {
        return null;
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public FQuaternion add(FQuaternion element) {
        return null;
    }

    @Override
    public FQuaternion sub(FQuaternion element) {
        return null;
    }

    @Override
    public FQuaternion mul(FQuaternion element) {
        return null;
    }

    @Override
    public FQuaternion div(FQuaternion element) {
        return null;
    }

    @Override
    public FQuaternion add(double factor) {
        return null;
    }

    @Override
    public FQuaternion sub(double factor) {
        return null;
    }

    @Override
    public FQuaternion mul(double factor) {
        return null;
    }

    @Override
    public FQuaternion div(double factor) {
        return null;
    }

    @Override
    public double getMagnitude() {
        return 0;
    }

    @Override
    public FQuaternion setMagnitude(double magnitude) {
        return null;
    }

    @Override
    public FQuaternion pow(int n) {
        return null;
    }

    @Override
    public FQuaternion negate() {
        return null;
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
    public FQuaternion imprint(FQuaternion element) {
        return null;
    }

    @Override
    public boolean isZero() {
        return false;
    }

    @Override
    public boolean isExact(double re, double i, double j, double k) {
        return false;
    }

    @Override
    public boolean isSimilar(double re, double i, double j, double k) {
        return false;
    }

    @Override
    public FQuaternion add(double re, double i, double j, double k) {
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
    public FQuaternion addI(double i) {
        return null;
    }

    @Override
    public FQuaternion addJ(double j) {
        return null;
    }

    @Override
    public FQuaternion addK(double k) {
        return null;
    }

    @Override
    public FQuaternion sub(double re, double i, double j, double k) {
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
    public FQuaternion subI(double i) {
        return null;
    }

    @Override
    public FQuaternion subJ(double j) {
        return null;
    }

    @Override
    public FQuaternion subK(double k) {
        return null;
    }

    @Override
    public FQuaternion mul(double re, double i, double j, double k) {
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
    public FQuaternion mulI(double i) {
        return null;
    }

    @Override
    public FQuaternion mulJ(double j) {
        return null;
    }

    @Override
    public FQuaternion mulK(double k) {
        return null;
    }

    @Override
    public FQuaternion div(double re, double i, double j, double k) {
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
    public FQuaternion divI(double i) {
        return null;
    }

    @Override
    public FQuaternion divJ(double j) {
        return null;
    }

    @Override
    public FQuaternion divK(double k) {
        return null;
    }

    // -------------------------------------------------------------------------------------------------

    private void updateStats(String name, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        statsClass.recordEvent(name, time);
        statsObject.recordEvent(name, time);
    }
}
