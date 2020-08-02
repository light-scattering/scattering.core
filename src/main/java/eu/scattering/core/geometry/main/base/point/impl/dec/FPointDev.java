package eu.scattering.core.geometry.main.base.point.impl.dec;

import eu.scattering.core.debug.dao.DevStats;
import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.PresetBase;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static eu.scattering.core.Configuration.debugPrintStream;

public class FPointDev extends PresetBase<IFPoint> implements IFPoint  {

    private static long numberOfInstances = 0;

    private final IFPoint core;
    private final DevStats stats;

    private FPointDev(IFPoint core) {

        numberOfInstances++;

        this.core = core;
        this.stats = new DevStats();
    }

    public static IFPoint create(IFPoint core) {

        return new FPointDev(core);
    }

    @Override
    public IFPoint set(double x, double y, double z) {

        String name = "set(double, double, double)";
        long time = System.currentTimeMillis();

        core.set(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getX() {

        String name = "getX()";
        long time = System.currentTimeMillis();

        var res = core.getX();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setX(double x) {

        String name = "setX(double)";
        long time = System.currentTimeMillis();

        core.setX(x);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getY() {

        String name = "getY()";
        long time = System.currentTimeMillis();

        var res = core.getY();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setY(double y) {

        String name = "setY(double)";
        long time = System.currentTimeMillis();

        core.setY(y);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getZ() {

        String name = "getZ()";
        long time = System.currentTimeMillis();

        var res = core.getZ();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setZ(double z) {

        String name = "setZ(double)";
        long time = System.currentTimeMillis();

        core.setZ(z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        core.devDescribe();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint devDescribe(String message) {

        String name = "devDescribe(String)";
        long time = System.currentTimeMillis();

        core.devDescribe(message);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public boolean isExact(IFPoint element) {

        String name = "isExact(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public boolean isSimilar(IFPoint element) {

        String name = "isSimilar(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(element);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public JSONObject exportToJSON() {

        String name = "exportToJSON()";
        long time = System.currentTimeMillis();

        var res = core.exportToJSON();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        core.importFromJSON(json);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = create(core.copy());

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint self() {

        String name = "self()";
        long time = System.currentTimeMillis();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint add(IFPoint fPoint) {

        String name = "add(IFPoint)";
        long time = System.currentTimeMillis();

        core.add(fPoint);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        core.add(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        core.add(factor);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint addX(double x) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        core.addX(x);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        core.addY(y);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        core.addZ(z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint sub(IFPoint fPoint) {

        String name = "sub(IFPoint)";
        long time = System.currentTimeMillis();

        core.sub(fPoint);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint sub(double x, double y, double z) {

        String name = "sub(double, double, double)";
        long time = System.currentTimeMillis();

        core.sub(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        core.sub(factor);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        core.subX(x);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        core.subY(y);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        core.subZ(z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint mul(IFPoint fPoint) {

        String name = "mul(IFPoint)";
        long time = System.currentTimeMillis();

        core.mul(fPoint);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        core.mul(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        core.mul(factor);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        core.mulX(x);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        core.mulY(y);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        core.mulZ(z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint div(IFPoint fPoint) {

        String name = "div(IFPoint)";
        long time = System.currentTimeMillis();

        core.div(fPoint);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        core.div(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        core.div(factor);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        core.divX(x);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        core.divY(y);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        core.divZ(z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint set(IFPoint element) {

        String name = "set(IFPoint)";
        long time = System.currentTimeMillis();

        core.set(element);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint imprint(IFPoint element) {

        String name = "imprint(IFPoint)";
        long time = System.currentTimeMillis();

        core.imprint(element);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint fun(Consumer<IFPoint> exp) {

        String name = "fun(Consumer<IFPoint> exp)";
        long time = System.currentTimeMillis();

        core.fun(exp);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double funVal(Function<IFPoint, Double> exp) {

        String name = "funVal(Function<IFPoint, Double>)";
        long time = System.currentTimeMillis();

        var res = core.funVal(exp);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public boolean funLog(Predicate<IFPoint> exp) {

        String name = "funLog(Predicate<IFPoint>)";
        long time = System.currentTimeMillis();

        var res = core.funLog(exp);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint ext(Consumer<IBaseExtensionAssembly> exp) {

        String name = "ext(Consumer<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        core.ext(exp);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public List<Double> extVal(Function<IBaseExtensionAssembly, List<Double>> exp) {

        String name = "extVal(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extVal(exp);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public List<Boolean> extLog(Function<IBaseExtensionAssembly, List<Boolean>> exp) {

        String name = "extLog(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extLog(exp);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public List<IFPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = core.disassemble();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        core.setSphericalCoordinates(inclination, azimuth);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint setRandomAngle(IFPoint... exclude) {

        String name = "setRandomAngle(IFPoint...)";
        long time = System.currentTimeMillis();

        core.setRandomAngle(exclude);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public boolean isExact(double x, double y, double z) {

        String name = "isExact(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isExact(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        String name = "isSimilar(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(x, y, z);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        core.reflect();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint reflect(IFPoint ref) {

        String name = "reflect(IFPoint)";
        long time = System.currentTimeMillis();

        core.reflect(ref);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public IFPoint normalize() throws DirectionException {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        core.normalize();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getLength() {

        String name = "getLength()";
        long time = System.currentTimeMillis();

        var res = core.getLength();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setLength(double length) throws DirectionException {

        String name = "setLength(double)";
        long time = System.currentTimeMillis();

        core.setLength(length);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getInclination() {

        String name = "getInclination()";
        long time = System.currentTimeMillis();

        var res = core.getInclination();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setInclination(double inclination) {

        String name = "setInclination(double)";
        long time = System.currentTimeMillis();

        core.setInclination(inclination);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getAzimuth() {

        String name = "getAzimuth()";
        long time = System.currentTimeMillis();

        var res = core.getAzimuth();

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setAzimuth(double azimuth) {

        String name = "setAzimuth(double)";
        long time = System.currentTimeMillis();

        core.setAzimuth(azimuth);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getAngle(IFPoint ref) throws DirectionException {

        String name = "getAngle(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getAngle(ref);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public double getDistance(IFPoint ref) {

        String name = "getDistance(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDistance(ref);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setDistance(IFPoint ref, double distance) throws DirectionException {

        String name = "setDistance(IFPoint, double)";
        long time = System.currentTimeMillis();

        core.setDistance(ref, distance);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public double getDotProduct(IFPoint ref) {

        String name = "getDotProduct(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDotProduct(ref);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return res;
    }

    @Override
    public IFPoint setCrossProduct(IFPoint ref) {

        String name = "setCrossProduct(IFPoint)";
        long time = System.currentTimeMillis();

        core.setCrossProduct(ref);

        stats.recordEvent(name, System.currentTimeMillis() - time);

        return this;
    }

    @Override
    public boolean isZero() {

        String name = "isZero()";
        long time = System.currentTimeMillis();

        var res = core.isZero();

        stats.recordEvent(name, System.currentTimeMillis() - time);

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
    public IFPoint devDescribeStats() {

        debugPrintStream.println(stats.toString());

        return self();
    }

    @Override
    public Optional<DevStats> devGetStats() {

        return Optional.of(stats);
    }

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.of(numberOfInstances);
    }

}
