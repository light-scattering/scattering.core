package eu.scattering.core.implementation.main.engine.base.point;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.injection.DevelopmentFactory;
import eu.scattering.core.design.main.engine.base.BaseComposite;
import eu.scattering.core.implementation.main.engine.base.BasePreset;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FPointDevelopment extends BasePreset<FPoint> implements FPoint {

    private static long numberOfInstances = 0;
    private static final Statistics statsClass = DevelopmentFactory.getIStats(true);
    private final Statistics statsObject = DevelopmentFactory.getIStats(false);
    private final FPoint core;

    private FPointDevelopment(FPoint core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FPoint create(FPoint core) {

        return new FPointDevelopment(core);
    }

    @Override
    public FPoint set(double x, double y, double z) {

        String name = "set(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.set(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getX() {

        String name = "getX()";
        long time = System.currentTimeMillis();

        var res = core.getX();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setX(double x) {

        String name = "setX(double)";
        long time = System.currentTimeMillis();

        var res = core.setX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getY() {

        String name = "getY()";
        long time = System.currentTimeMillis();

        var res = core.getY();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setY(double y) {

        String name = "setY(double)";
        long time = System.currentTimeMillis();

        var res = core.setY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getZ() {

        String name = "getZ()";
        long time = System.currentTimeMillis();

        var res = core.getZ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setZ(double z) {

        String name = "setZ(double)";
        long time = System.currentTimeMillis();

        var res = core.setZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(FPoint element) {

        String name = "isExact(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FPoint element) {

        String name = "isSimilar(FPoint)";
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
    public FPoint importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(core.copy());

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint self() {

        return this;
    }

    @Override
    public FPoint add(FPoint fPoint) {

        String name = "add(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.add(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.add(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.add(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint addX(double x) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.addX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        var res = core.addY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        var res = core.addZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint sub(FPoint fPoint) {

        String name = "sub(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.sub(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint sub(double x, double y, double z) {

        String name = "sub(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.sub(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = core.sub(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        var res = core.subX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        var res = core.subY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        var res = core.subZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint mul(FPoint fPoint) {

        String name = "mul(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.mul(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.mul(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = core.mul(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        var res = core.mulX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        var res = core.mulY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        var res = core.mulZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint div(FPoint fPoint) {

        String name = "div(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.div(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.div(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = core.div(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        var res = core.divX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        var res = core.divY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        var res = core.divZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint set(FPoint element) {

        String name = "set(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.set(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint imprint(FPoint element) {

        String name = "imprint(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.imprint(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint cus(Consumer<FPoint> exp) {

        String name = "cus(Consumer<FPoint> exp)";
        long time = System.currentTimeMillis();

        var res = core.cus(exp);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double cusDouble(Function<FPoint, Double> exp) {

        String name = "cusDouble(Function<FPoint, Double>)";
        long time = System.currentTimeMillis();

        var res = core.cusDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean cusBoolean(Predicate<FPoint> exp) {

        String name = "cusBoolean(Predicate<FPoint>)";
        long time = System.currentTimeMillis();

        var res = core.cusBoolean(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint ext(Consumer<BaseComposite> exp) {

        String name = "ext(Consumer<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.ext(exp);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public List<Double> extDouble(Function<BaseComposite, List<Double>> exp) {

        String name = "extDouble(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<Boolean> extBoolean(Function<BaseComposite, List<Boolean>> exp) {

        String name = "extBoolean(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extBoolean(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<FPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = core.disassemble();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        var res = core.setSphericalCoordinates(inclination, azimuth);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint setRandomAngle(FPoint... exclude) {

        String name = "setRandomAngle(FPoint...)";
        long time = System.currentTimeMillis();

        var res = core.setRandomAngle(exclude);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(double x, double y, double z) {

        String name = "isExact(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isExact(x, y, z);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        String name = "isSimilar(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(x, y, z);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = core.reflect();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint reflect(FPoint ref) {

        String name = "reflect(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.reflect(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint normalize() throws IllegalStateException {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = core.normalize();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getLength() {

        String name = "getLength()";
        long time = System.currentTimeMillis();

        var res = core.getLength();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setLength(double length) throws IllegalStateException {

        String name = "setLength(double)";
        long time = System.currentTimeMillis();

        var res = core.setLength(length);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getInclination() {

        String name = "getInclination()";
        long time = System.currentTimeMillis();

        var res = core.getInclination();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setInclination(double inclination) {

        String name = "setInclination(double)";
        long time = System.currentTimeMillis();

        var res = core.setInclination(inclination);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getAzimuth() {

        String name = "getAzimuth()";
        long time = System.currentTimeMillis();

        var res = core.getAzimuth();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setAzimuth(double azimuth) {

        String name = "setAzimuth(double)";
        long time = System.currentTimeMillis();

        var res = core.setAzimuth(azimuth);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getAngle(FPoint ref) throws IllegalStateException {

        String name = "getAngle(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.getAngle(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public double getDistance(FPoint ref) {

        String name = "getDistance(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDistance(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setDistance(FPoint ref, double distance) throws IllegalStateException {

        String name = "setDistance(FPoint, double)";
        long time = System.currentTimeMillis();

        var res = core.setDistance(ref, distance);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getDotProduct(FPoint ref) {

        String name = "getDotProduct(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDotProduct(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setCrossProduct(FPoint ref) {

        String name = "setCrossProduct(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setCrossProduct(ref);

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

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        return create((FPoint) core.clone());

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

        if (object instanceof FPoint) {
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
    public FPoint devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public FPoint devDescribeStatistics() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FPoint devDescribeClassStatistics() {

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

    // -------------------------------------------------------------------------------------------------

    private void updateStats(String name, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        statsClass.recordEvent(name, time);

        if (statsObject.isSuspended()) {
            return;
        }

        statsObject.recordEvent(name, time);
    }

}
