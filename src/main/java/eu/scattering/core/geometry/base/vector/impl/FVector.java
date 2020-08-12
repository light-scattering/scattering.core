package eu.scattering.core.geometry.base.vector.impl;

import eu.scattering.core.exception.DirectionException;
import eu.scattering.core.exception.PositionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.PresetBase;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.Configuration.jitter;

public class FVector extends PresetBase<IFVector> implements IFVector {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final IFPoint[] origin = new IFPoint[2];

    private FVector() { }

    public static FVector create() {
        FVector fVector = new FVector();

        fVector.origin[0] = FactoryGeometry.getIFPoint();
        fVector.origin[1] = FactoryGeometry.getIFPoint();

        return fVector;
    }

    @Override
    public IFPoint getBase() {

        return origin[0];
    }

    @Override
    public IFVector setBaseRef(IFPoint baseRef) {

        if (baseRef == null) {
            throw new NullPointerException(" The base IFPoint cannot be null");
        }

        if (baseRef == getHead()) {
            throw new IllegalArgumentException("The base/head IFPoints cannot point at the same instance");
        }

        origin[0] = baseRef;

        return this;
    }

    @Override
    public IFPoint getHead() {

        return origin[1];
    }

    @Override
    public IFVector setHeadRef(IFPoint headRef) {

        if (headRef == null) {
            throw new NullPointerException(" The head IFPoint cannot be null");
        }

        if (headRef == getBase()) {
            throw new IllegalArgumentException("The base/head IFPoints cannot point to the same instance");
        }

        origin[1] = headRef;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector setBase(IFPoint base) {
        getBase().set(base);

        return this;
    }

    @Override
    public IFVector setHead(IFPoint head) {
        getHead().set(head);

        return this;
    }

    @Override
    public FVector set(IFVector fVector) {
        setBase(fVector.getBase());
        setHead(fVector.getHead());

        return this;
    }

    @Override
    public FVector set(IFPoint base, IFPoint head) {
        setBase(base);
        setHead(head);

        return this;
    }

    @Override
    public IFVector setRef(IFPoint baseRef, IFPoint headRef) {
        setBaseRef(baseRef);
        setHeadRef(headRef);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(IFVector fVector) {

        if (this == fVector) {
            return true;
        }

        return getBase().isExact(fVector.getBase()) && getHead().isExact(fVector.getHead());
    }

    @Override
    public boolean isSimilar(IFVector fVector) {

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
    public IFVector importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("vector");

        setBaseRef(FactoryGeometry.getIFPoint().importFromJSON(structure.getJSONObject(0)));
        setHeadRef(FactoryGeometry.getIFPoint().importFromJSON(structure.getJSONObject(1)));

        return this;
    }

    @Override
    public FVector copy() {
        FVector fVector = new FVector();

        fVector.setBaseRef(FactoryGeometry.getIFPoint(getBase()));
        fVector.setHeadRef(FactoryGeometry.getIFPoint(getHead()));

        return fVector;
    }

    @Override
    public IFVector self() {

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

        if (object instanceof IFVector) {
            return isExact((IFVector) object);
        }

        return false;
    }

    @Override
    public Object clone() {

        return copy();
    }

//--------------------------------------------------

    @Override
    public List<IFPoint> disassemble() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(getBase());
        fPointList.add(getHead());

        return fPointList;
    }

//--------------------------------------------------

    @Override
    public IFVector setSphericalCoordinates(double inclination, double azimuth) {
        IFVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setSphericalCoordinates(inclination, azimuth);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector setRandomAngle(IFPoint ... exclusion) {
        IFVector fCopyLocal = copy().moveBase();

        IFPoint[] excludeShift = new IFPoint[exclusion.length];

        for (int i = 0; i < exclusion.length ; i++ ) {
            excludeShift[i] = exclusion[i].copy().sub(getBase());
        }

        fCopyLocal.getHead().setRandomAngle(excludeShift);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isExact(FactoryGeometry.getIFVector(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isSimilar(FactoryGeometry.getIFVector(bX, bY, bZ, hX, hY, hZ));
    }

    @Override
    public IFVector moveBase() {

        return moveBase(FactoryGeometry.getIFPoint());
    }

    @Override
    public IFVector moveBase(double bX, double bY, double bZ) {

        return moveBase(FactoryGeometry.getIFPoint(bX, bY, bZ));
    }

    @Override
    public IFVector moveBase(IFPoint base) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(base).sub(getBase());

        getBase().set(base);
        getHead().add(translation);

        return this;
    }

    @Override
    public IFVector moveHead() {

        return moveHead(FactoryGeometry.getIFPoint());
    }

    @Override
    public IFVector moveHead(double hX, double hY, double hZ) {

        return moveHead(FactoryGeometry.getIFPoint(hX, hY, hZ));
    }

    @Override
    public IFVector moveHead(IFPoint head) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(head).sub(getHead());

        getBase().add(translation);
        getHead().set(head);

        return this;
    }

    @Override
    public IFVector moveForward(double distance) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the IFVector is not defined");
        }

        if (distance < 0) {
            return moveBackward(-distance);
        }

        IFVector fCopyLocal = copy();
        fCopyLocal.setLength(distance);

        moveBase(fCopyLocal.getHead());

        return this;
    }

    @Override
    public IFVector moveBackward(double distance) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the IFVector is not defined");
        }

        if (distance < 0) {
            return moveForward(-distance);
        }

        IFVector fCopyLocal = copy().reflectHead();
        fCopyLocal.setLength(distance);

        moveBase(fCopyLocal.getHead());

        return this;
    }

    @Override
    public IFVector add(IFVector vector) {
        IFVector fCopyLocal = copy().moveBase();
        IFVector fCopyExternal = vector.copy().moveBase();

        fCopyLocal.getHead().add(fCopyExternal.getHead());
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector sub(IFVector vector) {
        IFVector fCopyLocal = copy().moveBase();
        IFVector fCopyExternal = vector.copy().moveBase();

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
    public IFVector normalize() throws DirectionException {
        IFVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().normalize();
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector reflectBase() {
        IFVector fCopyLocal = copy().moveHead();

        fCopyLocal.getBase().reflect();
        fCopyLocal.moveHead(getHead());

        return set(fCopyLocal);
    }

    @Override
    public IFVector reflectHead() {
        IFVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().reflect();
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector reflect(IFPoint center) {

        getBase().reflect(center);
        getHead().reflect(center);

        return this;
    }

    @Override
    public IFVector invertDirection() {
        IFPoint container = getHead().copy();

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
    public IFVector setLength(double length) throws DirectionException {
        IFVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setLength(length);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getInclination() {
        IFVector fCopyLocal = copy().moveBase();

        return fCopyLocal.getHead().getInclination();
    }

    @Override
    public IFVector setInclination(double inclination) {
        IFVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setInclination(inclination);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getAzimuth() {
        IFVector fCopyLocal = copy().moveBase();

        return fCopyLocal.getHead().getAzimuth();
    }

    @Override
    public IFVector setAzimuth(double azimuth) {
        IFVector fCopyLocal = copy().moveBase();

        fCopyLocal.getHead().setAzimuth(azimuth);
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getAngle(IFPoint ref) throws PositionException, DirectionException {

        if (getBase().isSimilar(ref)) {
            throw new PositionException("The provided IFPoint is at the same position as the base IFPoint");
        }

        return getAngle(FactoryGeometry.getIFVector(getBase(), ref));
    }

    @Override
    public double getAngle(IFVector ref) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new DirectionException("The direction of the provided IFVector is not defined");
        }

        double angle, dProd, magAB;
        IFVector fCopyLocal = copy().moveBase();
        IFVector fCopyExternal = ref.copy().moveBase();

        dProd = fCopyLocal.getDotProduct(fCopyExternal);
        magAB = fCopyLocal.getHead().getLength() * fCopyExternal.getHead().getLength();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getDotProduct(IFPoint ref) {

        return getDotProduct(FactoryGeometry.getIFVector(getBase(), ref));
    }

    @Override
    public double getDotProduct(IFVector ref) {
        IFVector fCopyLocal = copy().moveBase();
        IFVector fCopyExternal = ref.copy().moveBase();

        return fCopyLocal.getHead().getDotProduct(fCopyExternal.getHead());
    }

    @Override
    public IFVector setCrossProduct(IFPoint ref) {

        return setCrossProduct(FactoryGeometry.getIFVector(getBase(), ref));
    }

    @Override
    public IFVector setCrossProduct(IFVector ref) {
        IFVector fCopyLocal = copy().moveBase();
        IFVector fCopyExternal = ref.copy().moveBase();

        fCopyLocal.getHead().setCrossProduct(fCopyExternal.getHead());
        fCopyLocal.moveBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public boolean isParallel(IFVector ref) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new DirectionException("The direction of the provided IFVector is not defined");
        }

        IFPoint fCopyLocal = copy().moveBase().normalize().getHead();
        IFPoint fCopyExternal = ref.copy().moveBase().normalize().getHead();

        return fCopyLocal.isSimilar(fCopyExternal);
    }

    @Override
    public IFVector setParallel(IFVector ref) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new DirectionException("The direction of the provided IFVector is not defined");
        }

        double magnitude = getLength();
        IFPoint baseCopy = getBase().copy();

        return set(ref).setLength(magnitude).moveBase(baseCopy);
    }

    @Override
    public boolean isAntiParallel(IFVector ref) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new DirectionException("The direction of the provided IFVector is not defined");
        }

        IFPoint fCopyLocal = copy().moveBase().normalize().getHead();
        IFPoint fCopyExternal = ref.copy().moveBase().normalize().getHead();

        return fCopyLocal.isSimilar(fCopyExternal.reflect());
    }

    @Override
    public IFVector setAntiParallel(IFVector ref) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new DirectionException("The direction of the provided IFVector is not defined");
        }

        double magnitude = getLength();
        IFPoint baseCopy = getBase().copy();

        return set(ref).setLength(magnitude).moveBase(baseCopy).reflectHead();
    }

    @Override
    public boolean isOrthogonal(IFVector ref) throws DirectionException {

        if (isNonDirectional()) {
            throw new DirectionException("The direction of the input IFVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new DirectionException("The direction of the provided IFVector is not defined");
        }

        return (Math.abs(getDotProduct(ref)) < jitter) || (Math.abs((Math.PI * 0.5) - getAngle(ref)) < jitter);
    }

    @Override
    public IFVector setOrthogonal(IFVector ref) throws PositionException, DirectionException {

        if (isParallel(ref)) {
            throw new PositionException("IFVectors are parallel");
        }

        if (isAntiParallel(ref)) {
            throw new PositionException("IFVectors are anti-parallel");
        }

        double magnitude = getLength();
        IFVector fVectorRef = FactoryGeometry.getIFVector(ref.getBase().copy(), ref.getHead().copy());
        IFVector fVectorRot = copy().setCrossProduct(fVectorRef);

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
