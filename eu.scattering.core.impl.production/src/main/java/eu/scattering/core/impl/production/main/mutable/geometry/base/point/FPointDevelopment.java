package eu.scattering.core.impl.production.main.mutable.geometry.base.point;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.mutable.geometry.Geometry;
import eu.scattering.core.design.main.mutable.geometry.base.point.FPoint;
import eu.scattering.core.impl.production.development.statistics.StatisticsDefault;
import eu.scattering.core.impl.production.main.mutable.MutablePresetDevelopment;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FPointDevelopment extends MutablePresetDevelopment<FPoint> implements FPoint {

    private static final Statistics classStatistics = StatisticsDefault.create().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FPoint create(FPoint core) {

        numberOfInstances++;
        return new FPointDevelopment(core);
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

        FPointDevelopment.numberOfInstances = numberOfInstances;
    }

    private FPointDevelopment(FPoint core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPoint set(double x, double y, double z) {

        String name = "set(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().set(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getX() {

        String name = "getX()";
        long time = System.currentTimeMillis();

        var res = getCore().getX();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setX(double x) {

        String name = "setX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getY() {

        String name = "getY()";
        long time = System.currentTimeMillis();

        var res = getCore().getY();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setY(double y) {

        String name = "setY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getZ() {

        String name = "getZ()";
        long time = System.currentTimeMillis();

        var res = getCore().getZ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setZ(double z) {

        String name = "setZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isExact(FPoint element) {

        String name = "isExact(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FPoint element) {

        String name = "isSimilar(FPoint)";
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
    public FPoint importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = getCore().importFromJSON(json);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(getCore().copy());

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

        var res = getCore().add(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint addX(double x) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint sub(FPoint fPoint) {

        String name = "sub(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint sub(double x, double y, double z) {

        String name = "sub(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint mul(FPoint fPoint) {

        String name = "mul(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint div(FPoint fPoint) {

        String name = "div(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().div(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint set(FPoint element) {

        String name = "set(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().set(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint imprint(FPoint element) {

        String name = "imprint(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().imprint(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint trans(Consumer<FPoint> exp) {

        String name = "trans(Consumer<FPoint> exp)";
        long time = System.currentTimeMillis();

        var res = getCore().trans(exp);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double transDouble(Function<FPoint, Double> exp) {

        String name = "transDouble(Function<FPoint, Double>)";
        long time = System.currentTimeMillis();

        var res = getCore().transDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean transBoolean(Predicate<FPoint> exp) {

        String name = "transBoolean(Predicate<FPoint>)";
        long time = System.currentTimeMillis();

        var res = getCore().transBoolean(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint ext(Consumer<Geometry> exp) {

        String name = "ext(Consumer<BaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = getCore().ext(exp);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public List<Double> extDouble(Function<Geometry, List<Double>> exp) {

        String name = "extDouble(Function<BaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = getCore().extDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<Boolean> extBoolean(Function<Geometry, List<Boolean>> exp) {

        String name = "extBoolean(Function<BaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = getCore().extBoolean(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<FPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = getCore().disassemble();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().setSphericalCoordinates(inclination, azimuth);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint setRandomAngle(FPoint... exclude) {

        String name = "setRandomAngle(FPoint...)";
        long time = System.currentTimeMillis();

        var res = getCore().setRandomAngle(exclude);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isExact(double x, double y, double z) {

        String name = "isExact(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(x, y, z);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        String name = "isSimilar(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isSimilar(x, y, z);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = getCore().reflect();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint reflect(FPoint ref) {

        String name = "reflect(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().reflect(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint normalize() {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = getCore().normalize();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getLength() {

        String name = "getLength()";
        long time = System.currentTimeMillis();

        var res = getCore().getLength();

        updateStats(name, time);

        return res;
    }

    @Override
    public double getLengthP2() {

        String name = "getLengthP2()";
        long time = System.currentTimeMillis();

        var res = getCore().getLengthP2();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setLength(double length) {

        String name = "setLength(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setLength(length);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getInclination() {

        String name = "getInclination()";
        long time = System.currentTimeMillis();

        var res = getCore().getInclination();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setInclination(double inclination) {

        String name = "setInclination(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setInclination(inclination);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getAzimuth() {

        String name = "getAzimuth()";
        long time = System.currentTimeMillis();

        var res = getCore().getAzimuth();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setAzimuth(double azimuth) {

        String name = "setAzimuth(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setAzimuth(azimuth);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getAngle(FPoint ref) {

        String name = "getAngle(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().getAngle(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setAngle(FPoint ref, double angle) {

        String name = "setAngle(FPoint, double)";
        long time = System.currentTimeMillis();

        var res = getCore().setAngle(ref, angle);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint rotate(FPoint ref, double angle) {

        String name = "rotate(FPoint, double)";
        long time = System.currentTimeMillis();

        var res = getCore().rotate(ref, angle);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getDistance(FPoint ref) {

        String name = "getDistance(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().getDistance(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public double getDistanceP2(FPoint ref) {

        String name = "getDistanceP2(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().getDistanceP2(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setDistance(FPoint ref, double distance) {

        String name = "setDistance(FPoint, double)";
        long time = System.currentTimeMillis();

        var res = getCore().setDistance(ref, distance);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getDotProduct(FPoint ref) {

        String name = "getDotProduct(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().getDotProduct(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint setCrossProduct(FPoint ref) {

        String name = "setCrossProduct(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setCrossProduct(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isNonDirectional() {

        String name = "isNonDirectional()";
        long time = System.currentTimeMillis();

        var res = getCore().isNonDirectional();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isZero() {

        String name = "isZero()";
        long time = System.currentTimeMillis();

        var res = getCore().isZero();

        updateStats(name, time);

        return res;
    }
}
