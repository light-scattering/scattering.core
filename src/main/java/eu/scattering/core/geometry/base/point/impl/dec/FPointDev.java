package eu.scattering.core.geometry.base.point.impl.dec;

import eu.scattering.core.debug.stats.IStats;
import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.factory.FactoryDebug;
import eu.scattering.core.geometry.base.IBaseExtensionAssembly;
import eu.scattering.core.geometry.base.PresetBase;
import eu.scattering.core.geometry.base.point.IFPoint;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static eu.scattering.core.Configuration.debugPrintStream;

public class FPointDev extends PresetBase<IFPoint> implements IFPoint  {

    private static long numberOfInstances = 0;
    private static final IStats statsClass = FactoryDebug.getIStats(true);
    private final IStats statsObject = FactoryDebug.getIStats(false);
    private final IFPoint core;

    private FPointDev(IFPoint core) {

        numberOfInstances++;

        this.core = core;
    }

    public static IFPoint create(IFPoint core) {

        return new FPointDev(core);
    }

    @Override
    public IFPoint set(double x, double y, double z) {

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
    public IFPoint setX(double x) {

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
    public IFPoint setY(double y) {

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
    public IFPoint setZ(double z) {

        String name = "setZ(double)";
        long time = System.currentTimeMillis();

        var res = core.setZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(IFPoint element) {

        String name = "isExact(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(IFPoint element) {

        String name = "isSimilar(IFPoint)";
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
    public IFPoint importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(core.copy());

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint self() {

        String name = "self()";
        long time = System.currentTimeMillis();

        updateStats(name, time);

        return this;
    }

    @Override
    public IFPoint add(IFPoint fPoint) {

        String name = "add(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.add(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.add(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.add(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint addX(double x) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.addX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        var res = core.addY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        var res = core.addZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint sub(IFPoint fPoint) {

        String name = "sub(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.sub(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint sub(double x, double y, double z) {

        String name = "sub(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.sub(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = core.sub(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        var res = core.subX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        var res = core.subY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        var res = core.subZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint mul(IFPoint fPoint) {

        String name = "mul(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.mul(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.mul(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = core.mul(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        var res = core.mulX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        var res = core.mulY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        var res = core.mulZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint div(IFPoint fPoint) {

        String name = "div(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.div(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.div(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = core.div(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        var res = core.divX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        var res = core.divY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        var res = core.divZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint set(IFPoint element) {

        String name = "set(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.set(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint imprint(IFPoint element) {

        String name = "imprint(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.imprint(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint fun(Consumer<IFPoint> exp) {

        String name = "fun(Consumer<IFPoint> exp)";
        long time = System.currentTimeMillis();

        var res = core.fun(exp);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double funVal(Function<IFPoint, Double> exp) {

        String name = "funVal(Function<IFPoint, Double>)";
        long time = System.currentTimeMillis();

        var res = core.funVal(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean funLog(Predicate<IFPoint> exp) {

        String name = "funLog(Predicate<IFPoint>)";
        long time = System.currentTimeMillis();

        var res = core.funLog(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint ext(Consumer<IBaseExtensionAssembly> exp) {

        String name = "ext(Consumer<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.ext(exp);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public List<Double> extVal(Function<IBaseExtensionAssembly, List<Double>> exp) {

        String name = "extVal(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extVal(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<Boolean> extLog(Function<IBaseExtensionAssembly, List<Boolean>> exp) {

        String name = "extLog(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extLog(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<IFPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = core.disassemble();

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        var res = core.setSphericalCoordinates(inclination, azimuth);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint setRandomAngle(IFPoint... exclude) {

        String name = "setRandomAngle(IFPoint...)";
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
    public IFPoint reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = core.reflect();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint reflect(IFPoint ref) {

        String name = "reflect(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.reflect(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint normalize() throws DirectionException {

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
    public IFPoint setLength(double length) throws DirectionException {

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
    public IFPoint setInclination(double inclination) {

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
    public IFPoint setAzimuth(double azimuth) {

        String name = "setAzimuth(double)";
        long time = System.currentTimeMillis();

        var res = core.setAzimuth(azimuth);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getAngle(IFPoint ref) throws DirectionException {

        String name = "getAngle(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getAngle(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public double getDistance(IFPoint ref) {

        String name = "getDistance(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDistance(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint setDistance(IFPoint ref, double distance) throws DirectionException {

        String name = "setDistance(IFPoint, double)";
        long time = System.currentTimeMillis();

        var res = core.setDistance(ref, distance);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getDotProduct(IFPoint ref) {

        String name = "getDotProduct(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDotProduct(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint setCrossProduct(IFPoint ref) {

        String name = "setCrossProduct(IFPoint)";
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

        return create((IFPoint) core.clone());

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

        if (object instanceof IFPoint) {
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
    public IFPoint devDescribeStats() {

        debugPrintStream.println(statsObject.toString());

        return self();
    }

    @Override
    public IFPoint devDescribeClassStats() {

        debugPrintStream.println(statsClass.toString());

        return self();
    }

    @Override
    public Optional<IStats> devGetStats() {

        return Optional.of(statsObject);
    }

    @Override
    public Optional<IStats> devGetClassStats() {

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
