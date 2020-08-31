package eu.scattering.core.implementation.main.algebra.engine.base.vector;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.implementation.main.algebra.engine.base.BasePreset;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static eu.scattering.core.Config.developmentFactory;

public class FVectorDevelopment extends BasePreset<FVector> implements FVector {

    private static long numberOfInstances = 0;

    private static final Statistics statsClass = developmentFactory.getStatistics().setEnabled();
    private final Statistics statsObject = developmentFactory.getStatistics();

    private final FVector core;

    private FVectorDevelopment(FVector core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FVector create(FVector core) {

        return new FVectorDevelopment(core);
    }

    public FVector objectStatisticsEnable() {

        statsObject.setEnabled();

        return this;
    }

    public FVector objectStatisticsDisable() {

        statsObject.setDisabled();

        return this;
    }

    @Override
    public boolean isExact(FVector element) {

        String name = "isExact(FVector)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FVector element) {

        String name = "isSimilar(FVector)";
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
    public FVector importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = core.copy();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector self() {

        return this;
    }

    @Override
    public FVector devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector add(FPoint fPoint) {

        String name = "add(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.add(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.add(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.add(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector addX(double x) {

        String name = "addX(double)";
        long time = System.currentTimeMillis();

        var res = core.addX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        var res = core.addY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        var res = core.addZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector sub(FPoint fPoint) {

        String name = "sub(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.sub(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector sub(double x, double y, double z) {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.sub(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = core.sub(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        var res = core.subX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        var res = core.subY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        var res = core.subZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector mul(FPoint fPoint) {

        String name = "mul(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.mul(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.mul(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = core.mul(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        var res = core.mulX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        var res = core.mulY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        var res = core.mulZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector div(FPoint fPoint) {

        String name = "div(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.div(fPoint);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.div(x, y, z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = core.div(factor);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        var res = core.divX(x);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        var res = core.divY(y);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        var res = core.divZ(z);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector set(FVector element) {

        String name = "set(FVector)";
        long time = System.currentTimeMillis();

        var res = core.set(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector imprint(FVector element) {

        String name = "imprint(FVector)";
        long time = System.currentTimeMillis();

        var res = core.imprint(element);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector trans(Consumer<FVector> exp) {

        String name = "trans(Consumer<FVector>)";
        long time = System.currentTimeMillis();

        var res = core.trans(exp);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double transDouble(Function<FVector, Double> exp) {

        String name = "transDouble(Function<FVector, Double>)";
        long time = System.currentTimeMillis();

        var res = core.transDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean transBoolean(Predicate<FVector> exp) {

        String name = "transBoolean(Predicate<FVector>)";
        long time = System.currentTimeMillis();

        var res = core.transBoolean(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector ext(Consumer<Engine> exp) {

        String name = "ext(Consumer<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.ext(exp);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public List<Double> extDouble(Function<Engine, List<Double>> exp) {

        String name = "extDouble(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public List<Boolean> extBoolean(Function<Engine, List<Boolean>> exp) {

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
    public FVector setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        var res = core.setSphericalCoordinates(inclination, azimuth);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector setRandomAngle(FPoint... exclusion) {

        String name = "setRandomAngle(FPoint...)";
        long time = System.currentTimeMillis();

        var res = core.setRandomAngle(exclusion);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        String name = "isExact(double, double, double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isExact(bX, bY, bZ, hX, hY, hZ);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        String name = "isSimilar(double, double, double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(bX, bY, bZ, hX, hY, hZ);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector moveBase() {

        String name = "moveBase()";
        long time = System.currentTimeMillis();

        var res = core.moveBase();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {

        String name = "moveBase(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.moveBase(bX, bY, bZ);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveBase(FPoint base) {

        String name = "moveBase(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.moveBase(base);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveHead() {

        String name = "moveHead()";
        long time = System.currentTimeMillis();

        var res = core.moveHead();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {

        String name = "moveHead(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.moveHead(hX, hY, hZ);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveHead(FPoint head) {

        String name = "moveHead(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.moveHead(head);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveForward(double distance) throws IllegalStateException {

        String name = "moveForward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveForward(distance);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector moveBackward(double distance) throws IllegalStateException {

        String name = "moveBackward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveBackward(distance);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector add(FVector vector) {

        String name = "add(FVector)";
        long time = System.currentTimeMillis();

        var res = core.add(vector);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector sub(FVector vector) {

        String name = "sub(FVector)";
        long time = System.currentTimeMillis();

        var res = core.sub(vector);

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
    public FVector setLength(double length) throws IllegalStateException {

        String name = "setLength(double)";
        long time = System.currentTimeMillis();

        var res = core.setLength(length);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public double getLengthX() {

        String name = "getLengthX()";
        long time = System.currentTimeMillis();

        var res = core.getLengthX();

        updateStats(name, time);

        return res;
    }

    @Override
    public double getLengthY() {

        String name = "getLengthY()";
        long time = System.currentTimeMillis();

        var res = core.getLengthY();

        updateStats(name, time);

        return res;
    }

    @Override
    public double getLengthZ() {

        String name = "getLengthZ()";
        long time = System.currentTimeMillis();

        var res = core.getLengthZ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector normalize() throws IllegalStateException {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = core.normalize();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector reflect(FPoint center) {

        String name = "reflect(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.reflect(center);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector reflectBase() {

        String name = "reflectBase()";
        long time = System.currentTimeMillis();

        var res = core.reflectBase();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector reflectHead() {

        String name = "reflectHead()";
        long time = System.currentTimeMillis();

        var res = core.reflectHead();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector invertDirection() {

        String name = "invertDirection()";
        long time = System.currentTimeMillis();

        var res = core.invertDirection();

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
    public FVector setInclination(double inclination) {

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
    public FVector setAzimuth(double azimuth) {

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
    public double getAngle(FVector ref) throws IllegalStateException {

        String name = "getAngle(FVector)";
        long time = System.currentTimeMillis();

        var res = core.getAngle(ref);

        updateStats(name, time);

        return res;
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
    public double getDotProduct(FVector ref) {

        String name = "getDotProduct(FVector)";
        long time = System.currentTimeMillis();

        var res = core.getDotProduct(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setCrossProduct(FPoint ref) {

        String name = "setCrossProduct(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setCrossProduct(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector setCrossProduct(FVector ref) {

        String name = "setCrossProduct(FVector)";
        long time = System.currentTimeMillis();

        var res = core.setCrossProduct(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isParallel(FVector ref) throws IllegalStateException {

        String name = "isParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = core.isParallel(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setParallel(FVector ref) throws IllegalStateException {

        String name = "setParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = core.setParallel(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isAntiParallel(FVector ref) throws IllegalStateException {

        String name = "isAntiParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = core.isAntiParallel(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setAntiParallel(FVector ref) throws IllegalStateException {

        String name = "setAntiParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = core.setAntiParallel(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isOrthogonal(FVector ref) throws IllegalStateException {

        String name = "isOrthogonal(FVector)";
        long time = System.currentTimeMillis();

        var res = core.isOrthogonal(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setOrthogonal(FVector ref) throws IllegalStateException {

        String name = "setOrthogonal(FVector)";
        long time = System.currentTimeMillis();

        var res = core.setOrthogonal(ref);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isNonDirectional() {

        String name = "isNonDirectional()";
        long time = System.currentTimeMillis();

        var res = core.isNonDirectional();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector set(FPoint base, FPoint head) {

        String name = "set(FPoint, FPoint)";
        long time = System.currentTimeMillis();

        var res = core.set(base, head);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector setRef(FPoint baseRef, FPoint headRef) {

        String name = "setRef(FPoint, FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setRef(baseRef, headRef);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint getBase() {

        String name = "getBase()";
        long time = System.currentTimeMillis();

        var res = core.getBase();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setBase(FPoint base) {

        String name = "setBase(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setBase(base);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector setBaseRef(FPoint baseRef) {

        String name = "setBaseRef(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setBaseRef(baseRef);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint getHead() {

        String name = "getHead()";
        long time = System.currentTimeMillis();

        var res = core.getHead();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setHead(FPoint head) {

        String name = "setHead(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setHead(head);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FVector setHeadRef(FPoint headRef) {

        String name = "setHeadRef(FPoint)";
        long time = System.currentTimeMillis();

        var res = core.setHeadRef(headRef);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        return create((FVector) core.clone());

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

        if (object instanceof FVector) {
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
    public FVector devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public FVector devDescribeStatistics() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FVector devDescribeClassStatistics() {

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

        if (statsObject.isEnabled()) {
            return;
        }

        statsObject.recordEvent(name, time);
    }
}
