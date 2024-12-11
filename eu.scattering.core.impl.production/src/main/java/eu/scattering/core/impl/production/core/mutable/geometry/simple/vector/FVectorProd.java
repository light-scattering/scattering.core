package eu.scattering.core.impl.production.core.mutable.geometry.simple.vector;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.core.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.core.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.design.core.data.position.FTuplePos3D;
import eu.scattering.core.design.core.engine.rotation.FRotation;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.SimplePresetProd;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FVectorProd extends SimplePresetProd<FVector> implements FVector {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FPoint[] origin = new FPoint[2];
    private final Factory factory;

    private FVectorProd(Factory factory) {

        this.factory = factory;
    }

    public static FVector create(Factory factory) {
        FVectorProd fVector = new FVectorProd(factory);

        fVector.origin[0] = factory.getFPoint();
        fVector.origin[1] = factory.getFPoint();

        return fVector;
    }

    @Override
    public FPoint getBaseRef() {

        return origin[0];
    }

    @Override
    public FVector setBaseRef(FPoint baseRef) {

        if (baseRef == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        if (baseRef == getHeadRef()) {
            throw new IllegalArgumentException("The base/head FPoints cannot be the same");
        }

        origin[0] = baseRef;

        return this;
    }

    @Override
    public FPoint getHeadRef() {

        return origin[1];
    }

    @Override
    public FVector setHeadRef(FPoint headRef) {

        if (headRef == null) {
            throw new NullPointerException(" The head FPoint cannot be null");
        }

        if (headRef == getBaseRef()) {
            throw new IllegalArgumentException("The base/head FPoints cannot be the same");
        }

        origin[1] = headRef;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector setRef(FPoint baseRef, FPoint headRef) {
        setBaseRef(baseRef);
        setHeadRef(headRef);

        return this;
    }

    @Override
    public FVector set(FVector fVector) {
        setBase(fVector.getBaseRef());
        setHead(fVector.getHeadRef());

        return this;
    }

    @Override
    public FVector set(FPoint base, FPoint head) {
        setBase(base);
        setHead(head);

        return this;
    }

    @Override
    public FVector set(FTuplePos3D tuple) {

        setBase(tuple.getPosA().getD0(), tuple.getPosA().getD1(), tuple.getPosA().getD2());
        setHead(tuple.getPosB().getD0(), tuple.getPosB().getD1(), tuple.getPosB().getD2());

        return this;
    }

    @Override
    public FVector setBase(double bX, double bY, double bZ) {
        getBaseRef().set(bX, bY, bZ);

        return this;
    }

    @Override
    public FVector setBase(FPoint base) {
        getBaseRef().set(base);

        return this;
    }

    @Override
    public FVector setHead(double hX, double hY, double hZ) {
        getHeadRef().set(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector setHead(FPoint head) {
        getHeadRef().set(head);

        return this;
    }

    @Override
    public double getBaseX() {

        return getBaseRef().getX();
    }

    @Override
    public FVector setBaseX(double bX) {
        getBaseRef().setX(bX);

        return this;
    }

    @Override
    public double getBaseY() {

        return getBaseRef().getY();
    }

    @Override
    public FVector setBaseY(double bY) {
        getBaseRef().setY(bY);

        return this;
    }

    @Override
    public double getBaseZ() {

        return getBaseRef().getZ();
    }

    @Override
    public FVector setBaseZ(double bZ) {
        getBaseRef().setZ(bZ);

        return this;
    }

    @Override
    public double getHeadX() {

        return getHeadRef().getX();
    }

    @Override
    public FVector setHeadX(double hX) {
        getHeadRef().setX(hX);

        return this;
    }

    @Override
    public double getHeadY() {

        return getHeadRef().getY();
    }

    @Override
    public FVector setHeadY(double hY) {
        getHeadRef().setY(hY);

        return this;
    }

    @Override
    public double getHeadZ() {

        return getHeadRef().getZ();
    }

    @Override
    public FVector setHeadZ(double hZ) {
        getHeadRef().setZ(hZ);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FVector fVector) {

        if (this == fVector) {
            return true;
        }

        return getBaseRef().isExact(fVector.getBaseRef()) && getHeadRef().isExact(fVector.getHeadRef());
    }

    @Override
    public boolean isSimilar(FVector fVector) {

        if (this == fVector) {
            return true;
        }

        return getBaseRef().isSimilar(fVector.getBaseRef()) && getHeadRef().isSimilar(fVector.getHeadRef());
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();
        json.append("vector", getBaseRef().exportToJSON());
        json.append("vector", getHeadRef().exportToJSON());

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
    public FVector copy() {
        FVector fVector = factory.getFVector();

        fVector.setBaseRef(factory.getFPoint(getBaseRef()));
        fVector.setHeadRef(factory.getFPoint(getHeadRef()));

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

        hashCode = 31 * hashCode + getBaseRef().hashCode();
        hashCode = 31 * hashCode + getHeadRef().hashCode();

        return hashCode;
    }

//--------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();
        fPointList.add(getBaseRef());
        fPointList.add(getHeadRef());

        return fPointList;
    }

//--------------------------------------------------

    @Override
    public FVector setSphericalCoordinates(double inclination, double azimuth) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHeadRef().setSphericalCoordinates(inclination, azimuth);
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public FVector setRandomAngle(FPoint... exclude) {
        FVector fCopyLocal = copy().moveBase();

        FPoint[] excludeShift = new FPoint[exclude.length];

        for (int i = 0; i < exclude.length ; i++ ) {
            excludeShift[i] = exclude[i].copy().sub(getBaseRef());
        }

        fCopyLocal.getHeadRef().setRandomAngle(excludeShift);
        fCopyLocal.moveBase(getBaseRef());

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
        FPoint translation = factory.getFPoint().set(base).sub(getBaseRef());

        getBaseRef().set(base);
        getHeadRef().add(translation);

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
        FPoint translation = factory.getFPoint().set(head).sub(getHeadRef());

        getBaseRef().add(translation);
        getHeadRef().set(head);

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

        moveBase(fCopyLocal.getHeadRef());

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

        moveBase(fCopyLocal.getHeadRef());

        return this;
    }

    @Override
    public FVector add(FVector vector) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = vector.copy().moveBase();

        fCopyLocal.getHeadRef().add(fCopyExternal.getHeadRef());
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public FVector sub(FVector vector) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = vector.copy().moveBase();

        fCopyLocal.getHeadRef().sub(fCopyExternal.getHeadRef());
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public double getLengthX() {

        return Math.abs(getHeadRef().getX() - getBaseRef().getX());
    }

    @Override
    public double getLengthY() {

        return Math.abs(getHeadRef().getY() - getBaseRef().getY());
    }

    @Override
    public double getLengthZ() {

        return Math.abs(getHeadRef().getZ() - getBaseRef().getZ());
    }

    @Override
    public FVector normalize() {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHeadRef().normalize();
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public FVector reflectBase() {
        FVector fCopyLocal = copy().moveHead();

        fCopyLocal.getBaseRef().reflect();
        fCopyLocal.moveHead(getHeadRef());

        return set(fCopyLocal);
    }

    @Override
    public FVector reflectHead() {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHeadRef().reflect();
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public FVector reflect(FPoint center) {

        getBaseRef().reflect(center);
        getHeadRef().reflect(center);

        return this;
    }

    @Override
    public FVector invertDirection() {
        FPoint container = getHeadRef().copy();

        getHeadRef().set(getBaseRef());
        getBaseRef().set(container);

        return this;
    }

    @Override
    public double getLength() {

        return Math.sqrt(getLengthP2());
    }

    @Override
    public double getLengthP2() {
        double distanceX = getHeadRef().getX() - getBaseRef().getX();
        double distanceY = getHeadRef().getY() - getBaseRef().getY();
        double distanceZ = getHeadRef().getZ() - getBaseRef().getZ();

        return (distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ);
    }

    @Override
    public FVector setLength(double length) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHeadRef().setLength(length);
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public double getInclination() {
        FVector fCopyLocal = copy().moveBase();

        return fCopyLocal.getHeadRef().getInclination();
    }

    @Override
    public FVector setInclination(double inclination) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHeadRef().setInclination(inclination);
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public double getAzimuth() {
        FVector fCopyLocal = copy().moveBase();

        return fCopyLocal.getHeadRef().getAzimuth();
    }

    @Override
    public FVector setAzimuth(double azimuth) {
        FVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHeadRef().setAzimuth(azimuth);
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public double getAngle(FPoint ref) {

        if (getBaseRef().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        return getAngle(factory.getFVector(getBaseRef(), ref));
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
        magAB = fCopyLocal.getHeadRef().getLength() * fCopyExternal.getHeadRef().getLength();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public FVector setAngle(FPoint ref, double angle) {

        if (getBaseRef().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        if (getHeadRef().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the head FPoint");
        }

        FVector fCopyLocal = copy().moveBase();
        FPoint fCopyExternal = ref.copy().sub(getBaseRef());

        fCopyLocal.getHeadRef().setAngle(fCopyExternal, angle);
        fCopyLocal.moveBase(getBaseRef());

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

        fCopyLocal.getHeadRef().setAngle(fCopyExternal.getHeadRef(), angle);
        fCopyLocal.moveBase(getBaseRef());

        return set(fCopyLocal);
    }

    @Override
    public FVector rotate(FPoint ref, double angle) {

        if (getBaseRef().isSimilar(ref)) {
            throw new IllegalStateException("The provided FPoint is at the same position as the base FPoint");
        }

        FRotation rotor = factory.getFRotation(factory.getFVector(getBaseRef(), ref), angle);

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

        return getDotProduct(factory.getFVector(getBaseRef(), ref));
    }

    @Override
    public double getDotProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        return fCopyLocal.getHeadRef().getDotProduct(fCopyExternal.getHeadRef());
    }

    @Override
    public FVector setCrossProduct(FPoint ref) {

        return setCrossProduct(factory.getFVector(getBaseRef(), ref));
    }

    @Override
    public FVector setCrossProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBase();
        FVector fCopyExternal = ref.copy().moveBase();

        fCopyLocal.getHeadRef().setCrossProduct(fCopyExternal.getHeadRef());
        fCopyLocal.moveBase(getBaseRef());

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

        FPoint fCopyLocal = copy().moveBase().normalize().getHeadRef();
        FPoint fCopyExternal = ref.copy().moveBase().normalize().getHeadRef();

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
        FPoint baseCopy = getBaseRef().copy();

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

        FPoint fCopyLocal = copy().moveBase().normalize().getHeadRef();
        FPoint fCopyExternal = ref.copy().moveBase().normalize().getHeadRef();

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
        FPoint baseCopy = getBaseRef().copy();

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

        double jitter = factory.getJitter();

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
        FVector fVectorRef = factory.getFVector(ref.getBaseRef().copy(), ref.getHeadRef().copy());
        FVector fVectorRot = copy().setCrossProduct(fVectorRef);

        fVectorRef.setCrossProduct(fVectorRot).setLength(magnitude);
        fVectorRef.moveBase(getBaseRef());

        set(fVectorRef);

        return this;
    }

    @Override
    public boolean isNonDirectional() {

        return getBaseRef().isSimilar(getHeadRef());
    }

    @Override
    public boolean isZero() {

        return getBaseRef().isZero() && getHeadRef().isZero();
    }

    @Override
    public FTuplePos3D toTuplePos3D() {

        var posA = factory.getFPos3D(getBaseX(), getBaseY(), getBaseZ());
        var posB = factory.getFPos3D(getHeadX(), getHeadY(), getHeadZ());

        return factory.getFTuplePos3D(posA, posB);
    }

}
