package eu.scattering.core.impl.development.core.mutable.geometry.simple.vector;

import eu.scattering.core.design.core.data.position.FTuplePos3D;
import eu.scattering.core.design.debug.stats.Stats;
import eu.scattering.core.design.core.algebra.geometry.Geometry;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.production.debug.stats.StatsProd;
import eu.scattering.core.impl.development.core.mutable.MutablePresetDev;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FVectorDev extends MutablePresetDev<FVector> implements FVector {

    private static final Stats classStatistics = StatsProd.create().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FVector create(FVector core) {

        numberOfInstances++;
        return new FVectorDev(core);
    }


    @Override
    protected Stats getClassStatistics() {

        return classStatistics;
    }

    @Override
    protected long getNumberOfInstances() {

        return numberOfInstances;
    }

    @Override
    protected void setNumberOfInstances(long numberOfInstances) {

        FVectorDev.numberOfInstances = numberOfInstances;
    }


    private FVectorDev(FVector core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FVector element) {

        String name = "isExact(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FVector element) {

        String name = "isSimilar(FVector)";
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
    public FVector importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = getCore().importFromJSON(json);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = getCore().copy();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector self() {

        return this;
    }

    @Override
    public FVector devDesc() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = getCore().devDesc();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector add(FPoint fPoint) {

        String name = "add(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().add(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = getCore().add(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector addX(double x) {

        String name = "addX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().addZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector sub(FPoint fPoint) {

        String name = "sub(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector sub(double x, double y, double z) {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = getCore().sub(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().subZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector mul(FPoint fPoint) {

        String name = "mul(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mul(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().mulZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector div(FPoint fPoint) {

        String name = "div(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().div(fPoint);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(x, y, z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = getCore().div(factor);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divX(x);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divY(y);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().divZ(z);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector set(FVector element) {

        String name = "set(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().set(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector imprint(FVector element) {

        String name = "imprint(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().imprint(element);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector trans(Consumer<FVector> exp) {

        String name = "trans(Consumer<FVector>)";
        long time = System.currentTimeMillis();

        var res = getCore().trans(exp);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double transDouble(Function<FVector, Double> exp) {

        String name = "transDouble(Function<FVector, Double>)";
        long time = System.currentTimeMillis();

        var res = getCore().transDouble(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean transBoolean(Predicate<FVector> exp) {

        String name = "transBoolean(Predicate<FVector>)";
        long time = System.currentTimeMillis();

        var res = getCore().transBoolean(exp);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector ext(Consumer<Geometry> exp) {

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
    public FVector setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().setSphericalCoordinates(inclination, azimuth);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setRandomAngle(FPoint... exclude) {

        String name = "setRandomAngle(FPoint...)";
        long time = System.currentTimeMillis();

        var res = getCore().setRandomAngle(exclude);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        String name = "isExact(double, double, double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(bX, bY, bZ, hX, hY, hZ);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        String name = "isSimilar(double, double, double, double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().isSimilar(bX, bY, bZ, hX, hY, hZ);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector moveBase() {

        String name = "moveBase()";
        long time = System.currentTimeMillis();

        var res = getCore().moveBase();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {

        String name = "moveBase(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().moveBase(bX, bY, bZ);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveBase(FPoint base) {

        String name = "moveBase(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().moveBase(base);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveHead() {

        String name = "moveHead()";
        long time = System.currentTimeMillis();

        var res = getCore().moveHead();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {

        String name = "moveHead(double, double, double)";
        long time = System.currentTimeMillis();

        var res = getCore().moveHead(hX, hY, hZ);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveHead(FPoint head) {

        String name = "moveHead(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().moveHead(head);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveForward(double distance) {

        String name = "moveForward(double)";
        long time = System.currentTimeMillis();

        var res = getCore().moveForward(distance);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector moveBackward(double distance) {

        String name = "moveBackward(double)";
        long time = System.currentTimeMillis();

        var res = getCore().moveBackward(distance);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector add(FVector vector) {

        String name = "add(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().add(vector);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector sub(FVector vector) {

        String name = "sub(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().sub(vector);

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
    public FVector setLength(double length) {

        String name = "setLength(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setLength(length);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getLengthX() {

        String name = "getLengthX()";
        long time = System.currentTimeMillis();

        var res = getCore().getLengthX();

        updateStats(name, time);

        return res;
    }

    @Override
    public double getLengthY() {

        String name = "getLengthY()";
        long time = System.currentTimeMillis();

        var res = getCore().getLengthY();

        updateStats(name, time);

        return res;
    }

    @Override
    public double getLengthZ() {

        String name = "getLengthZ()";
        long time = System.currentTimeMillis();

        var res = getCore().getLengthZ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector normalize() {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = getCore().normalize();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector reflect(FPoint center) {

        String name = "reflect(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().reflect(center);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector reflectBase() {

        String name = "reflectBase()";
        long time = System.currentTimeMillis();

        var res = getCore().reflectBase();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector reflectHead() {

        String name = "reflectHead()";
        long time = System.currentTimeMillis();

        var res = getCore().reflectHead();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector invertDirection() {

        String name = "invertDirection()";
        long time = System.currentTimeMillis();

        var res = getCore().invertDirection();

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
    public FVector setInclination(double inclination) {

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
    public FVector setAzimuth(double azimuth) {

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
    public double getAngle(FVector ref) {

        String name = "getAngle(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().getAngle(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setAngle(FPoint ref, double angle) {

        String name = "setAngle(FPoint, angle)";
        long time = System.currentTimeMillis();

        var res = getCore().setAngle(ref, angle);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setAngle(FVector ref, double angle) {

        String name = "setAngle(FVector, angle)";
        long time = System.currentTimeMillis();

        var res = getCore().setAngle(ref, angle);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector rotate(FPoint ref, double angle) {

        String name = "rotate(FPoint, angle)";
        long time = System.currentTimeMillis();

        var res = getCore().rotate(ref, angle);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector rotate(FVector ref, double angle) {

        String name = "rotate(FVector, angle)";
        long time = System.currentTimeMillis();

        var res = getCore().rotate(ref, angle);

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
    public double getDotProduct(FVector ref) {

        String name = "getDotProduct(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().getDotProduct(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setCrossProduct(FPoint ref) {

        String name = "setCrossProduct(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setCrossProduct(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setCrossProduct(FVector ref) {

        String name = "setCrossProduct(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().setCrossProduct(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isCollinear(FVector ref) {

        String name = "isCollinear(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().isCollinear(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isParallel(FVector ref) {

        String name = "isParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().isParallel(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setParallel(FVector ref) {

        String name = "setParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().setParallel(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isAntiParallel(FVector ref) {

        String name = "isAntiParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().isAntiParallel(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setAntiParallel(FVector ref) {

        String name = "setAntiParallel(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().setAntiParallel(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public boolean isOrthogonal(FVector ref) {

        String name = "isOrthogonal(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().isOrthogonal(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setOrthogonal(FVector ref) {

        String name = "setOrthogonal(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().setOrthogonal(ref);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FTuplePos3D toTuplePos3D() {

        String name = "toTuplePos3D()";
        long time = System.currentTimeMillis();

        var res = getCore().toTuplePos3D();

        updateStats(name, time);

        return res;
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

    @Override
    public FVector set(FPoint base, FPoint head) {

        String name = "set(FPoint, FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().set(base, head);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector set(FTuplePos3D tuple) {

        String name = "set(FTuplePos3D)";
        long time = System.currentTimeMillis();

        var res = getCore().set(tuple);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setBase(double bX, double bY, double bZ) {

        String name = "setBase(double,double,double)";
        long time = System.currentTimeMillis();

        var res = getCore().setBase(bX, bY, bZ);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setRef(FPoint baseRef, FPoint headRef) {

        String name = "setRef(FPoint, FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setRef(baseRef, headRef);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint getBaseRef() {

        String name = "getBase()";
        long time = System.currentTimeMillis();

        var res = getCore().getBaseRef();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setBase(FPoint base) {

        String name = "setBase(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setBase(base);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setHead(double hX, double hY, double hZ) {

        String name = "setHead(double,double,double)";
        long time = System.currentTimeMillis();

        var res = getCore().setHead(hX, hY, hZ);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setBaseRef(FPoint baseRef) {

        String name = "setBaseRef(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setBaseRef(baseRef);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint getHeadRef() {

        String name = "getHead()";
        long time = System.currentTimeMillis();

        var res = getCore().getHeadRef();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setHead(FPoint head) {

        String name = "setHead(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setHead(head);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getBaseX() {

        String name = "getBaseX()";
        long time = System.currentTimeMillis();

        var res = getCore().getBaseX();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setBaseX(double bX) {

        String name = "setBaseX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setBaseX(bX);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getBaseY() {

        String name = "getBaseY()";
        long time = System.currentTimeMillis();

        var res = getCore().getBaseY();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setBaseY(double bY) {

        String name = "setBaseY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setBaseY(bY);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getBaseZ() {

        String name = "getBaseZ()";
        long time = System.currentTimeMillis();

        var res = getCore().getBaseZ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setBaseZ(double bZ) {

        String name = "setBaseZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setBaseZ(bZ);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getHeadX() {

        String name = "getHeadX()";
        long time = System.currentTimeMillis();

        var res = getCore().getHeadX();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setHeadX(double hX) {

        String name = "setHeadX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setHeadX(hX);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getHeadY() {

        String name = "getHeadY()";
        long time = System.currentTimeMillis();

        var res = getCore().getHeadY();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setHeadY(double hY) {

        String name = "setHeadY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setHeadY(hY);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public double getHeadZ() {

        String name = "getHeadZ()";
        long time = System.currentTimeMillis();

        var res = getCore().getHeadZ();

        updateStats(name, time);

        return res;
    }

    @Override
    public FVector setHeadZ(double hZ) {

        String name = "setHeadZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setHeadZ(hZ);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector setHeadRef(FPoint headRef) {

        String name = "setHeadRef(FPoint)";
        long time = System.currentTimeMillis();

        var res = getCore().setHeadRef(headRef);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }
}
