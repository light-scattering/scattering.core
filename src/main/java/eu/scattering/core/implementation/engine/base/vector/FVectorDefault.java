package eu.scattering.core.implementation.engine.base.vector;

import eu.scattering.core.Config;
import eu.scattering.core.injection.EngineFactory;
import eu.scattering.core.implementation.engine.base.BasePreset;
import eu.scattering.core.design.engine.base.point.FPoint;
import eu.scattering.core.design.engine.base.vector.FVector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FVectorDefault extends BasePreset<FVector> implements FVector {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FPoint[] origin = new FPoint[2];

    private FVectorDefault() { }

    public static FVectorDefault create() {
        FVectorDefault fVector = new FVectorDefault();

        fVector.origin[0] = EngineFactory.getFPoint();
        fVector.origin[1] = EngineFactory.getFPoint();

        return fVector;
    }

    @Override
    public FPoint getBase() {

        return origin[0];
    }

    @Override
    public FVector setBaseRef(FPoint baseRef) {

        if (baseRef == null) {
            throw new NullPointerException(" The base FPoint cannot be null");
        }

        if (baseRef == getHead()) {
            throw new IllegalArgumentException("The base/head FPoints cannot point at the same instance");
        }

        origin[0] = baseRef;

        return this;
    }

    @Override
    public FPoint getHead() {

        return origin[1];
    }

    @Override
    public FVector setHeadRef(FPoint headRef) {

        if (headRef == null) {
            throw new NullPointerException(" The head FPoint cannot be null");
        }

        if (headRef == getBase()) {
            throw new IllegalArgumentException("The base/head FPoints cannot point to the same instance");
        }

        origin[1] = headRef;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FVectorDefault setBase(FPoint base) {
        getBase().set(base);

        return this;
    }

    @Override
    public FVector setHead(FPoint head) {
        getHead().set(head);

        return this;
    }

    @Override
    public FVectorDefault set(FVector fVector) {
        setBase(fVector.getBase());
        setHead(fVector.getHead());

        return this;
    }

    @Override
    public FVectorDefault set(FPoint base, FPoint head) {
        setBase(base);
        setHead(head);

        return this;
    }

    @Override
    public FVector setRef(FPoint baseRef, FPoint headRef) {
        setBaseRef(baseRef);
        setHeadRef(headRef);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FVector fVector) {

        if (this == fVector) {
            return true;
        }

        return getBase().isExact(fVector.getBase()) && getHead().isExact(fVector.getHead());
    }

    @Override
    public boolean isSimilar(FVector fVector) {

        if (this == fVector) {
            return true;
        }

        return getBase().isSimilar(fVector.getBase()) && getHead().isSimilar(fVector.getHead());
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();
        json.append("vector", getBase().exportToJSON());
        json.append("vector", getHead().exportToJSON());

        return json;
    }

    @Override
    public FVector importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("vector");

        setBaseRef(EngineFactory.getFPoint().importFromJSON(structure.getJSONObject(0)));
        setHeadRef(EngineFactory.getFPoint().importFromJSON(structure.getJSONObject(1)));

        return this;
    }

    @Override
    public FVectorDefault copy() {
        FVectorDefault fVector = new FVectorDefault();

        fVector.setBaseRef(EngineFactory.getFPoint(getBase()));
        fVector.setHeadRef(EngineFactory.getFPoint(getHead()));

        return fVector;
    }

    @Override
    public FVector self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + getBase().hashCode();
        hashCode = 31 * hashCode + getHead().hashCode();

        return hashCode;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FVector) {
            return isExact((FVector) object);
        }

        return false;
    }

    @Override
    public Object clone() {

        return copy();
    }

//--------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();
        fPointList.add(getBase());
        fPointList.add(getHead());

        return fPointList;
    }

//--------------------------------------------------

    @Override
    public FVector setSphericalCoordinates(double inclination, double azimuth) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setSphericalCoordinates(inclination, azimuth);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public FVector setRandomAngle(FPoint... exclusion) {
        FVector fCopyLocal = copy().moveBase();

        FPoint[] excludeShift = new FPoint[exclusion.length];

        for (int i = 0; i < exclusion.length ; i++ ) {
            excludeShift[i] = exclusion[i].copy().sub(getBase());
        }

        fCopyLocal.getHead().setRandomAngle(excludeShift);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isExact(EngineFactory.getFVector(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isSimilar(EngineFactory.getFVector(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public FVector moveBase() {

        return moveBase(EngineFactory.getFPoint());
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {

        return moveBase(EngineFactory.getFPoint(bX, bY, bZ));
    }

    @Override
    public FVector moveBase(FPoint base) {
        FPoint translation = EngineFactory.getFPoint().set(base).sub(getBase());

        getBase().set(base);
        getHead().add(translation);

        return this;
    }

    @Override
    public FVector moveHead() {

        return moveHead(EngineFactory.getFPoint());
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {

        return moveHead(EngineFactory.getFPoint(hX, hY, hZ));
    }

    @Override
    public FVector moveHead(FPoint head) {
        FPoint translation = EngineFactory.getFPoint().set(head).sub(getHead());

        getBase().add(translation);
        getHead().set(head);

        return this;
    }

    @Override
    public FVector moveForward(double distance) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the IFVector is not defined");
        }

        if (distance < 0) {
            return moveBackward(-distance);
        }

        FVector fCopyLocal = copy();
        fCopyLocal.setLength(distance);

        moveBase(fCopyLocal.getHead());

        return this;
    }

    @Override
    public FVector moveBackward(double distance) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the IFVector is not defined");
        }

        if (distance < 0) {
            return moveForward(-distance);
        }

        FVector fCopyLocal = copy().reflectHead();
        fCopyLocal.setLength(distance);

        moveBase(fCopyLocal.getHead());

        return this;
    }

    @Override
    public FVector add(FVector vector) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = vector.copy().moveBase();

        fCopyLocal.getHead().add(fCopyExternal.getHead());
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public FVector sub(FVector vector) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = vector.copy().moveBase();

        fCopyLocal.getHead().sub(fCopyExternal.getHead());
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getLengthX() {

        return Math.abs(getHead().getX() - getBase().getX());
    }

    @Override
    public double getLengthY() {

        return Math.abs(getHead().getY() - getBase().getY());
    }

    @Override
    public double getLengthZ() {

        return Math.abs(getHead().getZ() - getBase().getZ());
    }

    @Override
    public FVector normalize() throws IllegalStateException {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().normalize();
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public FVector reflectBase() {
        FVector fCopyLocal = copy().moveHead();

        fCopyLocal.getBase().reflect();
        fCopyLocal.moveHead(getHead());

        return set(fCopyLocal);
    }

    @Override
    public FVector reflectHead() {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().reflect();
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public FVector reflect(FPoint center) {

        getBase().reflect(center);
        getHead().reflect(center);

        return this;
    }

    @Override
    public FVector invertDirection() {
        FPoint container = getHead().copy();

        getHead().set(getBase());
        getBase().set(container);

        return this;
    }

    @Override
    public double getLength() {

        double distanceX = getHead().getX() - getBase().getX();
        double distanceY = getHead().getY() - getBase().getY();
        double distanceZ = getHead().getZ() - getBase().getZ();

        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));
    }

    @Override
    public FVector setLength(double length) throws IllegalStateException {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setLength(length);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getInclination() {
        FVector fCopyLocal = copy().moveBase();

        return fCopyLocal.getHead().getInclination();
    }

    @Override
    public FVector setInclination(double inclination) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setInclination(inclination);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getAzimuth() {
        FVector fCopyLocal = copy().moveBase();

        return fCopyLocal.getHead().getAzimuth();
    }

    @Override
    public FVector setAzimuth(double azimuth) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setAzimuth(azimuth);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getAngle(FPoint ref) throws IllegalStateException {

        if (getBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        return getAngle(EngineFactory.getFVector(getBase(), ref));
    }

    @Override
    public double getAngle(FVector ref) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        double angle, dProd, magAB;
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        dProd = fCopyLocal.getDotProduct(fCopyExternal);
        magAB = fCopyLocal.getHead().getLength() * fCopyExternal.getHead().getLength();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getDotProduct(FPoint ref) {

        return getDotProduct(EngineFactory.getFVector(getBase(), ref));
    }

    @Override
    public double getDotProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        return fCopyLocal.getHead().getDotProduct(fCopyExternal.getHead());
    }

    @Override
    public FVector setCrossProduct(FPoint ref) {

        return setCrossProduct(EngineFactory.getFVector(getBase(), ref));
    }

    @Override
    public FVector setCrossProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        fCopyLocal.getHead().setCrossProduct(fCopyExternal.getHead());
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public boolean isParallel(FVector ref) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        FPoint fCopyLocal = copy().moveBase().normalize().getHead();
        FPoint fCopyExternal = ref.copy().moveBase().normalize().getHead();

        return fCopyLocal.isSimilar(fCopyExternal);
    }

    @Override
    public FVector setParallel(FVector ref) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        double magnitude = getLength();
        FPoint baseCopy = getBase().copy();

        return set(ref).setLength(magnitude).moveBase(baseCopy);
    }

    @Override
    public boolean isAntiParallel(FVector ref) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        FPoint fCopyLocal = copy().moveBase().normalize().getHead();
        FPoint fCopyExternal = ref.copy().moveBase().normalize().getHead();

        return fCopyLocal.isSimilar(fCopyExternal.reflect());
    }

    @Override
    public FVector setAntiParallel(FVector ref) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        double magnitude = getLength();
        FPoint baseCopy = getBase().copy();

        return set(ref).setLength(magnitude).moveBase(baseCopy).reflectHead();
    }

    @Override
    public boolean isOrthogonal(FVector ref) throws IllegalStateException {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        return (Math.abs(getDotProduct(ref)) < Config.getJitter())
                || (Math.abs((Math.PI * 0.5) - getAngle(ref)) < Config.getJitter());
    }

    @Override
    public FVector setOrthogonal(FVector ref) throws IllegalStateException {

        if (isParallel(ref)) {
            throw new IllegalStateException("FVectors are parallel");
        }

        if (isAntiParallel(ref)) {
            throw new IllegalStateException("FVectors are anti-parallel");
        }

        double magnitude = getLength();
        FVector fVectorRef = EngineFactory.getFVector(ref.getBase().copy(), ref.getHead().copy());
        FVector fVectorRot = copy().setCrossProduct(fVectorRef);

        fVectorRef.setCrossProduct(fVectorRot).setLength(magnitude);
        fVectorRef.moveBase(getBase());

        set(fVectorRef);

        return this;
    }

    @Override
    public boolean isNonDirectional() {

        return getBase().equals(getHead());
    }

}
