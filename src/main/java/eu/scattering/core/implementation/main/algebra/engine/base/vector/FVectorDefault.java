package eu.scattering.core.implementation.main.algebra.engine.base.vector;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.box.rotation.FRotation;
import eu.scattering.core.implementation.FactoryDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.BasePresetDefault;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FVectorDefault extends BasePresetDefault<FVector> implements FVector {

    private static Factory factory = new FactoryDefault();
    private static double jitter = 1E-8;

    public static void setFactory(Factory factory) {

        FVectorDefault.factory = factory;
    }

    public static void setJitter(double jitter) {

        FVectorDefault.jitter = jitter;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FPoint[] origin = new FPoint[2];

    private FVectorDefault() { }

    public static FVectorDefault create() {
        FVectorDefault fVector = new FVectorDefault();

        fVector.origin[0] = factory.getFPoint();
        fVector.origin[1] = factory.getFPoint();

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

        setBaseRef(factory.getFPoint().importFromJSON(structure.getJSONObject(0)));
        setHeadRef(factory.getFPoint().importFromJSON(structure.getJSONObject(1)));

        return this;
    }

    @Override
    public FVectorDefault copy() {
        FVectorDefault fVector = new FVectorDefault();

        fVector.setBaseRef(factory.getFPoint(getBase()));
        fVector.setHeadRef(factory.getFPoint(getHead()));

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

        return isExact(factory.getFVector(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isSimilar(factory.getFVector(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public FVector moveBase() {

        return moveBase(factory.getFPoint());
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {

        return moveBase(factory.getFPoint(bX, bY, bZ));
    }

    @Override
    public FVector moveBase(FPoint base) {
        FPoint translation = factory.getFPoint().set(base).sub(getBase());

        getBase().set(base);
        getHead().add(translation);

        return this;
    }

    @Override
    public FVector moveHead() {

        return moveHead(factory.getFPoint());
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {

        return moveHead(factory.getFPoint(hX, hY, hZ));
    }

    @Override
    public FVector moveHead(FPoint head) {
        FPoint translation = factory.getFPoint().set(head).sub(getHead());

        getBase().add(translation);
        getHead().set(head);

        return this;
    }

    @Override
    public FVector moveForward(double distance) {

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
    public FVector moveBackward(double distance) {

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
    public FVector normalize() {
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

        return Math.sqrt(getLengthP2());
    }

    @Override
    public double getLengthP2() {
        double distanceX = getHead().getX() - getBase().getX();
        double distanceY = getHead().getY() - getBase().getY();
        double distanceZ = getHead().getZ() - getBase().getZ();

        return (distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ);
    }

    @Override
    public FVector setLength(double length) {
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
    public double getAngle(FPoint ref) {

        if (getBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        return getAngle(factory.getFVector(getBase(), ref));
    }

    @Override
    public double getAngle(FVector ref) {

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
    public FVector setAngle(FPoint ref, double angle) {

        if (getBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        if (getHead().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the head FPoint");
        }

        FVector fCopyLocal = copy().moveBase();
        FPoint fCopyExternal = ref.copy().sub(getBase());

        fCopyLocal.getHead().setAngle(fCopyExternal, angle);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public FVector setAngle(FVector ref, double angle) {

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        if (isSimilar(ref)) {
            throw new IllegalStateException("The two FVectors are similar");
        }

        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        fCopyLocal.getHead().setAngle(fCopyExternal.getHead(), angle);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public FVector rotate(FPoint ref, double angle) {

        if (getBase().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        FRotation rotor = factory.getFRotation(factory.getFVector(getBase(), ref), angle);

        return ext(rotor.rotate());
    }

    @Override
    public FVector rotate(FVector ref, double angle) {

        if (ref.isNonDirectional()) {
            throw new IllegalArgumentException("The direction of the provided FVector is not defined");
        }

        FRotation rotor = factory.getFRotation(ref, angle);

        return ext(rotor.rotate());
    }

    @Override
    public double getDotProduct(FPoint ref) {

        return getDotProduct(factory.getFVector(getBase(), ref));
    }

    @Override
    public double getDotProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        return fCopyLocal.getHead().getDotProduct(fCopyExternal.getHead());
    }

    @Override
    public FVector setCrossProduct(FPoint ref) {

        return setCrossProduct(factory.getFVector(getBase(), ref));
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
    public boolean isCollinear(FVector ref) {

        return isParallel(ref) || isAntiParallel(ref);
    }

    @Override
    public boolean isParallel(FVector ref) {

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
    public FVector setParallel(FVector ref) {

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
    public boolean isAntiParallel(FVector ref) {

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
    public FVector setAntiParallel(FVector ref) {

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
    public boolean isOrthogonal(FVector ref) {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        return (Math.abs(getDotProduct(ref)) < jitter) || (Math.abs((Math.PI * 0.5) - getAngle(ref)) < jitter);
    }

    @Override
    public FVector setOrthogonal(FVector ref) {

        if (isParallel(ref)) {
            throw new IllegalStateException("FVectors are parallel");
        }

        if (isAntiParallel(ref)) {
            throw new IllegalStateException("FVectors are anti-parallel");
        }

        double magnitude = getLength();
        FVector fVectorRef = factory.getFVector(ref.getBase().copy(), ref.getHead().copy());
        FVector fVectorRot = copy().setCrossProduct(fVectorRef);

        fVectorRef.setCrossProduct(fVectorRot).setLength(magnitude);
        fVectorRef.moveBase(getBase());

        set(fVectorRef);

        return this;
    }

    @Override
    public boolean isNonDirectional() {

        return getBase().isSimilar(getHead());
    }

    @Override
    public boolean isZero() {

        return getBase().isZero() && getHead().isZero();
    }

}
