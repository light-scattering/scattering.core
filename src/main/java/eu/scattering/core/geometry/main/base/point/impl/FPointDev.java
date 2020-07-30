package eu.scattering.core.geometry.main.base.point.impl;

import eu.scattering.core.CoreObject;
import eu.scattering.core.debug.IDebug;
import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.geometry.IGeometryBase;
import eu.scattering.core.geometry.main.IBase;
import eu.scattering.core.geometry.main.IBaseExtensionAssembly;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class FPointDev extends CoreObject
        implements IFPoint, IGeometryBase<IFPoint>, IDebug<IFPoint>, IBase<IFPoint>, Cloneable {

    private final IFPoint core;

    private FPointDev(IFPoint core) {

        this.core = core;
    }

    public static IFPoint create(IFPoint core) {

        return new FPointDev(core);
    }

    @Override
    public IFPoint set(double x, double y, double z) {

        String name = "set(double, double, double)";
        long time = System.currentTimeMillis();

        core.set(x, y, z);

        return this;
    }

    @Override
    public double getX() {

        String name = "getX()";
        long time = System.currentTimeMillis();

        var res = core.getX();

        return res;
    }

    @Override
    public IFPoint setX(double x) {

        String name = "setX(double)";
        long time = System.currentTimeMillis();

        core.setX(x);

        return this;
    }

    @Override
    public double getY() {

        String name = "getY()";
        long time = System.currentTimeMillis();

        var res = core.getY();

        return res;
    }

    @Override
    public IFPoint setY(double y) {

        String name = "setY(double)";
        long time = System.currentTimeMillis();

        core.setY(y);

        return this;
    }

    @Override
    public double getZ() {

        String name = "getZ()";
        long time = System.currentTimeMillis();

        var res = core.getZ();

        return res;
    }

    @Override
    public IFPoint setZ(double z) {

        String name = "setZ(double)";
        long time = System.currentTimeMillis();

        core.setZ(z);

        return this;
    }

    @Override
    public IFPoint devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        core.devDescribe();

        return this;
    }

    @Override
    public IFPoint devDescribe(String message) {

        String name = "devDescribe(String)";
        long time = System.currentTimeMillis();

        core.devDescribe(message);

        return this;
    }

    @Override
    public boolean isExact(IFPoint element) {

        String name = "isExact(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        return res;
    }

    @Override
    public boolean isSimilar(IFPoint element) {

        String name = "isSimilar(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(element);

        return res;
    }

    @Override
    public JSONObject exportToJSON() {

        String name = "exportToJSON()";
        long time = System.currentTimeMillis();

        var res = core.exportToJSON();

        return res;
    }

    @Override
    public IFPoint importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        return res;
    }

    @Override
    public IFPoint copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = core.copy();

        return res;
    }

    @Override
    public IFPoint self() {

        String name = "self()";
        long time = System.currentTimeMillis();

         var res = core;

         return res;
    }

    @Override
    public IFPoint add(IFPoint fPoint) {

        String name = "add(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.add(fPoint);

        return res;
    }

    @Override
    public IFPoint add(double x, double y, double z) {

        String name = "add(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.add(x, y, z);

        return res;
    }

    @Override
    public IFPoint add(double factor) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.add(factor);

        return res;
    }

    @Override
    public IFPoint addX(double x) {

        String name = "add(double)";
        long time = System.currentTimeMillis();

        var res = core.addX(x);

        return res;
    }

    @Override
    public IFPoint addY(double y) {

        String name = "addY(double)";
        long time = System.currentTimeMillis();

        var res = core.addY(y);

        return res;
    }

    @Override
    public IFPoint addZ(double z) {

        String name = "addZ(double)";
        long time = System.currentTimeMillis();

        var res = core.addZ(z);

        return res;
    }

    @Override
    public IFPoint sub(IFPoint fPoint) {

        String name = "sub(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.sub(fPoint);

        return res;
    }

    @Override
    public IFPoint sub(double x, double y, double z) {

        String name = "sub(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.sub(x, y, z);

        return res;
    }

    @Override
    public IFPoint sub(double factor) {

        String name = "sub(double)";
        long time = System.currentTimeMillis();

        var res = core.sub(factor);

        return res;
    }

    @Override
    public IFPoint subX(double x) {

        String name = "subX(double)";
        long time = System.currentTimeMillis();

        var res = core.subX(x);

        return res;
    }

    @Override
    public IFPoint subY(double y) {

        String name = "subY(double)";
        long time = System.currentTimeMillis();

        var res = core.subY(y);

        return res;
    }

    @Override
    public IFPoint subZ(double z) {

        String name = "subZ(double)";
        long time = System.currentTimeMillis();

        var res = core.subZ(z);

        return res;
    }

    @Override
    public IFPoint mul(IFPoint fPoint) {

        String name = "mul(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.mul(fPoint);

        return res;
    }

    @Override
    public IFPoint mul(double x, double y, double z) {

        String name = "mul(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.mul(x, y, z);

        return res;
    }

    @Override
    public IFPoint mul(double factor) {

        String name = "mul(double)";
        long time = System.currentTimeMillis();

        var res = core.mul(factor);

        return res;
    }

    @Override
    public IFPoint mulX(double x) {

        String name = "mulX(double)";
        long time = System.currentTimeMillis();

        var res = core.mulX(x);

        return res;
    }

    @Override
    public IFPoint mulY(double y) {

        String name = "mulY(double)";
        long time = System.currentTimeMillis();

        var res = core.mulY(y);

        return res;
    }

    @Override
    public IFPoint mulZ(double z) {

        String name = "mulZ(double)";
        long time = System.currentTimeMillis();

        var res = core.mulZ(z);

        return res;
    }

    @Override
    public IFPoint div(IFPoint fPoint) {

        String name = "div(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.div(fPoint);

        return res;
    }

    @Override
    public IFPoint div(double x, double y, double z) {

        String name = "div(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.div(x, y, z);

        return res;
    }

    @Override
    public IFPoint div(double factor) {

        String name = "div(double)";
        long time = System.currentTimeMillis();

        var res = core.div(factor);

        return res;
    }

    @Override
    public IFPoint divX(double x) {

        String name = "divX(double)";
        long time = System.currentTimeMillis();

        var res = core.divX(x);

        return res;
    }

    @Override
    public IFPoint divY(double y) {

        String name = "divY(double)";
        long time = System.currentTimeMillis();

        var res = core.divY(y);

        return res;
    }

    @Override
    public IFPoint divZ(double z) {

        String name = "divZ(double)";
        long time = System.currentTimeMillis();

        var res = core.divZ(z);

        return res;
    }

    @Override
    public IFPoint set(IFPoint element) {

        String name = "set(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.set(element);

        return res;
    }

    @Override
    public IFPoint swap(IFPoint element) {

        String name = "swap(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.swap(element);

        return res;
    }

    @Override
    public IFPoint imprint(IFPoint element) {

        String name = "imprint(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.imprint(element);

        return res;
    }

    @Override
    public IFPoint fun(Consumer<IFPoint> exp) {

        String name = "fun(Consumer<IFPoint> exp)";
        long time = System.currentTimeMillis();

        var res = core.fun(exp);

        return res;
    }

    @Override
    public double funVal(Function<IFPoint, Double> exp) {

        String name = "funVal(Function<IFPoint, Double>)";
        long time = System.currentTimeMillis();

        var res = core.funVal(exp);

        return res;
    }

    @Override
    public boolean funLog(Predicate<IFPoint> exp) {

        String name = "funLog(Predicate<IFPoint>)";
        long time = System.currentTimeMillis();

        var res = core.funLog(exp);

        return res;
    }

    @Override
    public IFPoint ext(Consumer<IBaseExtensionAssembly> exp) {

        String name = "ext(Consumer<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.ext(exp);

        return res;
    }

    @Override
    public List<Double> extVal(Function<IBaseExtensionAssembly, List<Double>> exp) {

        String name = "extVal(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extVal(exp);

        return res;
    }

    @Override
    public List<Boolean> extLog(Function<IBaseExtensionAssembly, List<Boolean>> exp) {

        String name = "extLog(Function<IBaseExtensionAssembly>)";
        long time = System.currentTimeMillis();

        var res = core.extLog(exp);

        return res;
    }

    @Override
    public List<IFPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = core.disassemble();

        return res;
    }

    @Override
    public IFPoint setSphericalCoordinates(double inclination, double azimuth) {

        String name = "setSphericalCoordinates(double, double)";
        long time = System.currentTimeMillis();

        var res = core.setSphericalCoordinates(inclination, azimuth);

        return res;
    }

    @Override
    public IFPoint setRandomAngle(IFPoint... exclude) {

        String name = "setRandomAngle(IFPoint...)";
        long time = System.currentTimeMillis();

        var res = core.setRandomAngle(exclude);

        return res;
    }

    @Override
    public boolean isExact(double x, double y, double z) {

        String name = "isExact(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isExact(x, y, z);

        return res;
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {

        String name = "isSimilar(double, double, double)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(x, y, z);

        return res;
    }

    @Override
    public IFPoint reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = core.reflect();

        return res;
    }

    @Override
    public IFPoint reflect(IFPoint ref) {

        String name = "reflect(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.reflect(ref);

        return res;
    }

    @Override
    public IFPoint normalize() throws DirectionException {

        String name = "normalize()";
        long time = System.currentTimeMillis();

        var res = core.normalize();

        return res;
    }

    @Override
    public double getLength() {

        String name = "getLength()";
        long time = System.currentTimeMillis();

        var res = core.getLength();

        return res;
    }

    @Override
    public IFPoint setLength(double length) throws DirectionException {

        String name = "setLength(double)";
        long time = System.currentTimeMillis();

        var res = core.setLength(length);

        return res;
    }

    @Override
    public double getInclination() {

        String name = "getInclination()";
        long time = System.currentTimeMillis();

        var res = core.getInclination();

        return res;
    }

    @Override
    public IFPoint setInclination(double inclination) {

        String name = "setInclination(double)";
        long time = System.currentTimeMillis();

        var res = core.setInclination(inclination);

        return res;
    }

    @Override
    public double getAzimuth() {

        String name = "getAzimuth()";
        long time = System.currentTimeMillis();

        var res = core.getAzimuth();

        return res;
    }

    @Override
    public IFPoint setAzimuth(double azimuth) {

        String name = "setAzimuth(double)";
        long time = System.currentTimeMillis();

        var res = core.setAzimuth(azimuth);

        return res;
    }

    @Override
    public double getAngle(IFPoint ref) throws DirectionException {

        String name = "getAngle(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getAngle(ref);

        return res;
    }

    @Override
    public double getDistance(IFPoint ref) {

        String name = "getDistance(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDistance(ref);

        return res;
    }

    @Override
    public IFPoint setDistance(IFPoint ref, double distance) throws DirectionException {

        String name = "setDistance(IFPoint, double)";
        long time = System.currentTimeMillis();

        var res = core.setDistance(ref, distance);

        return res;
    }

    @Override
    public double getDotProduct(IFPoint ref) {

        String name = "getDotProduct(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.getDotProduct(ref);

        return res;
    }

    @Override
    public IFPoint setCrossProduct(IFPoint ref) {

        String name = "setCrossProduct(IFPoint)";
        long time = System.currentTimeMillis();

        var res = core.setCrossProduct(ref);

        return res;
    }

    @Override
    public boolean isZero() {

        String name = "isZero()";
        long time = System.currentTimeMillis();

        var res = core.isZero();

        return res;
    }

    @Override
    public Object clone() {

        String name = "clone()";
        long time = System.currentTimeMillis();

        var res = core.clone();

        return res;
    }

    @Override
    public boolean equals(Object object) {

        String name = "equals()";
        long time = System.currentTimeMillis();

        var res = core.equals(object);

        return res;
    }

    @Override
    public String toString() {

        String name = "toString()";
        long time = System.currentTimeMillis();

        var res = core.toString();

        return res;
    }

    @Override
    public int hashCode() {

        String name = "hashCode()";
        long time = System.currentTimeMillis();

        var res = core.hashCode();

        return res;
    }
}
