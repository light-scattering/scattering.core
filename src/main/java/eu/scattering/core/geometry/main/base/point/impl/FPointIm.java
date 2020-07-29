package eu.scattering.core.geometry.main.base.point.impl;

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

public class FPointIm implements IFPoint, IGeometryBase<IFPoint>, IDebug<IFPoint>, IBase<IFPoint> {

    private final IFPoint core;

    private FPointIm() {

        this.core = null;
    }

    public FPointIm(IFPoint core) {

        this.core = core;
    }

    @Override
    public IFPoint set(double x, double y, double z) {

        return core.copy().set(x, y, z);
    }

    @Override
    public double getX() {

        return core.getX();
    }

    @Override
    public IFPoint setX(double x) {

        return core.copy().setX(x);
    }

    @Override
    public double getY() {

        return core.getY();
    }

    @Override
    public IFPoint setY(double y) {

        return core.copy().setY(y);
    }

    @Override
    public double getZ() {

        return core.getZ();
    }

    @Override
    public IFPoint setZ(double z) {

        return core.copy().setZ(z);
    }

    @Override
    public IFPoint devDescribe() {

        return core.copy().devDescribe();
    }

    @Override
    public IFPoint devDescribe(String message) {

        return core.copy().devDescribe(message);
    }

    @Override
    public boolean isExact(IFPoint element) {

        return core.isExact(element);
    }

    @Override
    public boolean isSimilar(IFPoint element) {

        return core.isSimilar(element);
    }

    @Override
    public JSONObject exportToJSON() {

        return core.exportToJSON();
    }

    @Override
    public IFPoint importFromJSON(JSONObject json) {

        return core.copy().importFromJSON(json);
    }

    @Override
    public IFPoint copy() {

        return core.copy();
    }

    @Override
    public IFPoint self() {

        return core.copy();
    }

    @Override
    public IFPoint add(IFPoint fPoint) {

        return core.copy().add(fPoint);
    }

    @Override
    public IFPoint add(double x, double y, double z) {

        return core.copy().add(x, y, z);
    }

    @Override
    public IFPoint add(double factor) {

        return core.copy().add(factor);
    }

    @Override
    public IFPoint addX(double x) {

        return core.copy().addX(x);
    }

    @Override
    public IFPoint addY(double y) {

        return core.copy().addY(y);
    }

    @Override
    public IFPoint addZ(double z) {

        return core.copy().addZ(z);
    }

    @Override
    public IFPoint sub(IFPoint fPoint) {

        return core.copy().sub(fPoint);
    }

    @Override
    public IFPoint sub(double x, double y, double z) {

        return core.copy().sub(x, y, z);
    }

    @Override
    public IFPoint sub(double factor) {

        return core.copy().sub(factor);
    }

    @Override
    public IFPoint subX(double x) {
        return null;
    }

    @Override
    public IFPoint subY(double y) {
        return null;
    }

    @Override
    public IFPoint subZ(double z) {
        return null;
    }

    @Override
    public IFPoint mul(IFPoint fPoint) {
        return null;
    }

    @Override
    public IFPoint mul(double x, double y, double z) {
        return null;
    }

    @Override
    public IFPoint mul(double factor) {
        return null;
    }

    @Override
    public IFPoint mulX(double x) {
        return null;
    }

    @Override
    public IFPoint mulY(double y) {
        return null;
    }

    @Override
    public IFPoint mulZ(double z) {
        return null;
    }

    @Override
    public IFPoint div(IFPoint fPoint) {
        return null;
    }

    @Override
    public IFPoint div(double x, double y, double z) {
        return null;
    }

    @Override
    public IFPoint div(double factor) {
        return null;
    }

    @Override
    public IFPoint divX(double x) {
        return null;
    }

    @Override
    public IFPoint divY(double y) {
        return null;
    }

    @Override
    public IFPoint divZ(double z) {
        return null;
    }

    @Override
    public IFPoint set(IFPoint element) {
        return null;
    }

    @Override
    public IFPoint swap(IFPoint element) {
        return null;
    }

    @Override
    public IFPoint imprint(IFPoint element) {
        return null;
    }

    @Override
    public IFPoint fun(Consumer<IFPoint> exp) {
        return null;
    }

    @Override
    public double funVal(Function<IFPoint, Double> exp) {
        return 0;
    }

    @Override
    public boolean funLog(Predicate<IFPoint> exp) {
        return false;
    }

    @Override
    public IFPoint ext(Consumer<IBaseExtensionAssembly> exp) {
        return null;
    }

    @Override
    public List<Double> extVal(Function<IBaseExtensionAssembly, List<Double>> exp) {
        return null;
    }

    @Override
    public List<Boolean> extLog(Function<IBaseExtensionAssembly, List<Boolean>> exp) {
        return null;
    }

    @Override
    public List<IFPoint> disassemble() {
        return null;
    }

    @Override
    public IFPoint setSphericalCoordinates(double inclination, double azimuth) {
        return null;
    }

    @Override
    public IFPoint setRandomAngle(IFPoint... exclude) {
        return null;
    }

    @Override
    public boolean isExact(double x, double y, double z) {
        return false;
    }

    @Override
    public boolean isSimilar(double x, double y, double z) {
        return false;
    }

    @Override
    public IFPoint reflect() {
        return null;
    }

    @Override
    public IFPoint reflect(IFPoint ref) {
        return null;
    }

    @Override
    public IFPoint normalize() throws DirectionException {
        return null;
    }

    @Override
    public double getLength() {
        return 0;
    }

    @Override
    public IFPoint setLength(double length) throws DirectionException {
        return null;
    }

    @Override
    public double getInclination() {
        return 0;
    }

    @Override
    public IFPoint setInclination(double inclination) {
        return null;
    }

    @Override
    public double getAzimuth() {
        return 0;
    }

    @Override
    public IFPoint setAzimuth(double azimuth) {
        return null;
    }

    @Override
    public double getAngle(IFPoint ref) throws DirectionException {
        return 0;
    }

    @Override
    public double getDistance(IFPoint ref) {
        return 0;
    }

    @Override
    public IFPoint setDistance(IFPoint ref, double distance) throws DirectionException {
        return null;
    }

    @Override
    public double getDotProduct(IFPoint ref) {
        return 0;
    }

    @Override
    public IFPoint setCrossProduct(IFPoint ref) {
        return null;
    }

    @Override
    public boolean isZero() {
        return false;
    }
}
