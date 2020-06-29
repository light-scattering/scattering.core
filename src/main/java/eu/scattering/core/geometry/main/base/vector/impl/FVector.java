package eu.scattering.core.geometry.main.base.vector.impl;

import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.main.PresetBase;
import eu.scattering.core.geometry.main.base.point.IFPoint;
import eu.scattering.core.geometry.main.base.vector.IFVector;
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
            throw new NullPointerException(" The base IFPoint must not be null");
        }

        if (baseRef == getHead()) {
            throw new IllegalArgumentException("The base/head IFPoints must not point to the same instance");
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
            throw new NullPointerException(" The head IFPoint must not be null");
        }

        if (headRef == getBase()) {
            throw new IllegalArgumentException("The base/head IFPoints must not point to the same instance");
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

    @Override
    public IFVector swap(IFVector element) {

        getBase().swap(element.getBase());
        getHead().swap(element.getHead());

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
        IFVector fCopyLocal = copy().relocateBase();

        fCopyLocal.getHead().setSphericalCoordinates(inclination, azimuth);
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector setRandom(IFPoint ... exclusion) {
        IFVector fCopyLocal = copy().relocateBase();

        IFPoint[] excludeShift = new IFPoint[exclusion.length];

        for (int i = 0; i < exclusion.length ; i++ ) {
            excludeShift[i] = exclusion[i].copy().sub(getBase());
        }

        fCopyLocal.getHead().setRandom(excludeShift);
        fCopyLocal.relocateBase(getBase());

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
    public IFVector relocateBase() {

        return relocateBase(FactoryGeometry.getIFPoint());
    }

    @Override
    public IFVector relocateBase(double bX, double bY, double bZ) {

        return relocateBase(FactoryGeometry.getIFPoint(bX, bY, bZ));
    }

    @Override
    public IFVector relocateBase(IFPoint base) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(base).sub(getBase());

        getBase().set(base);
        getHead().add(translation);

        return this;
    }

    @Override
    public IFVector relocateHead() {

        return relocateHead(FactoryGeometry.getIFPoint());
    }

    @Override
    public IFVector relocateHead(double hX, double hY, double hZ) {

        return relocateHead(FactoryGeometry.getIFPoint(hX, hY, hZ));
    }

    @Override
    public IFVector relocateHead(IFPoint head) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(head).sub(getHead());

        getBase().add(translation);
        getHead().set(head);

        return this;
    }

    @Override
    public IFVector moveForward(double distance) {

        if (distance < 0) {
            return moveBackward(-distance);
        }

        IFVector fCopyLocal = copy();
        fCopyLocal.setMagnitude(distance);

        relocateBase(fCopyLocal.getHead());

        return this;
    }

    @Override
    public IFVector moveBackward(double distance) {

        if (distance < 0) {
            return moveForward(-distance);
        }

        IFVector fCopyLocal = copy().reflectHead();
        fCopyLocal.setMagnitude(distance);

        relocateBase(fCopyLocal.getHead());

        return this;
    }

    @Override
    public IFVector add(IFVector vector) {
        IFVector fCopyLocal = copy().relocateBase();
        IFVector fCopyExternal = vector.copy().relocateBase();

        fCopyLocal.getHead().add(fCopyExternal.getHead());
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector sub(IFVector vector) {
        IFVector fCopyLocal = copy().relocateBase();
        IFVector fCopyExternal = vector.copy().relocateBase();

        fCopyLocal.getHead().sub(fCopyExternal.getHead());
        fCopyLocal.relocateBase(getBase());

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
    public IFPoint getCenter() {

        double valX = 0.5 * (getHead().getX() + getBase().getX());
        double valY = 0.5 * (getHead().getY() + getBase().getY());
        double valZ = 0.5 * (getHead().getZ() + getBase().getZ());

        return FactoryGeometry.getIFPoint(valX, valY, valZ);
    }

    @Override
    public IFVector normalize() {
        IFVector fCopyLocal = copy().relocateBase();

        fCopyLocal.getHead().normalize();
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector reflectBase() {
        IFVector fCopyLocal = copy().relocateHead();

        fCopyLocal.getBase().reflect();
        fCopyLocal.relocateHead(getHead());

        return set(fCopyLocal);
    }

    @Override
    public IFVector reflectHead() {
        IFVector fCopyLocal = copy().relocateBase();

        fCopyLocal.getHead().reflect();
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public IFVector reflect(IFPoint ref) {

        getBase().reflect(ref);
        getHead().reflect(ref);

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
    public double getMagnitude() {

        double distanceX = getHead().getX() - getBase().getX();
        double distanceY = getHead().getY() - getBase().getY();
        double distanceZ = getHead().getZ() - getBase().getZ();

        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));
    }

    @Override
    public IFVector setMagnitude(double magnitude) throws SamePositionException {
        IFVector fCopyLocal = copy().relocateBase();

        fCopyLocal.getHead().setRadius(magnitude);
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getInclination() {
        IFVector fCopyLocal = copy().relocateBase();

        return fCopyLocal.getHead().getInclination();
    }

    @Override
    public IFVector setInclination(double inclination) {
        IFVector fCopyLocal = copy().relocateBase();

        fCopyLocal.getHead().setInclination(inclination);
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getAzimuth() {
        IFVector fCopyLocal = copy().relocateBase();

        return fCopyLocal.getHead().getAzimuth();
    }

    @Override
    public IFVector setAzimuth(double azimuth) {
        IFVector fCopyLocal = copy().relocateBase();

        fCopyLocal.getHead().setAzimuth(azimuth);
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public double getAngle(IFPoint ref) {

        return getAngle(FactoryGeometry.getIFVector(getBase(), ref));
    }

    @Override
    public double getAngle(IFVector ref) {
        double angle, dProd, magAB;
        IFVector fCopyLocal = copy().relocateBase();
        IFVector fCopyExternal = ref.copy().relocateBase();

        dProd = fCopyLocal.getDotProduct(fCopyExternal);
        magAB = fCopyLocal.getHead().getRadius() * fCopyExternal.getHead().getRadius();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getDotProduct(IFPoint ref) {

        return getDotProduct(FactoryGeometry.getIFVector(getBase(), ref));
    }

    @Override
    public double getDotProduct(IFVector ref) {
        IFVector fCopyLocal = copy().relocateBase();
        IFVector fCopyExternal = ref.copy().relocateBase();

        return fCopyLocal.getHead().getDotProduct(fCopyExternal.getHead());
    }

    @Override
    public IFVector getCrossProduct(IFPoint ref) {

        return getCrossProduct(FactoryGeometry.getIFVector(getBase(), ref));
    }

    @Override
    public IFVector getCrossProduct(IFVector ref) {
        IFVector fCopyLocal = copy().relocateBase();
        IFVector fCopyExternal = ref.copy().relocateBase();

        fCopyLocal.getHead().getCrossProduct(fCopyExternal.getHead());
        fCopyLocal.relocateBase(getBase());

        return set(fCopyLocal);
    }

    @Override
    public boolean isParallel(IFVector ref) {
        double conX, conY, conZ;
        IFVector fCopyLocal = copy().relocateBase();
        IFVector fCopyExternal = ref.copy().relocateBase();

        conX = fCopyLocal.getHead().getX() / fCopyExternal.getHead().getX();
        conY = fCopyLocal.getHead().getY() / fCopyExternal.getHead().getY();
        conZ = fCopyLocal.getHead().getZ() / fCopyExternal.getHead().getZ();

        return Math.abs(conX - conY) < jitter && Math.abs(conX - conZ) < jitter;
    }

    @Override
    public IFVector setParallel(IFPoint base, IFPoint head) {
        double magnitude = getMagnitude();
        IFPoint baseCopy = getBase().copy();

        return set(base, head).setMagnitude(magnitude).relocateBase(baseCopy);
    }

    @Override
    public IFVector setParallel(IFVector ref) {
        double magnitude = getMagnitude();
        IFPoint baseCopy = getBase().copy();

        return set(ref).setMagnitude(magnitude).relocateBase(baseCopy);
    }

    @Override
    public boolean isOrthogonal(IFVector ref) {

        return Math.abs((Math.PI * 0.5) - getAngle(ref)) < jitter;
    }

    @Override
    public IFVector setOrthogonal(IFPoint headA, IFPoint headB) {
        double magnitude = getMagnitude();
        IFVector fVectorRef = FactoryGeometry.getIFVector(getBase().copy(), headB.copy());

        setHead(headA);

        return getCrossProduct(fVectorRef).setMagnitude(magnitude);
    }

    @Override
    public IFVector setOrthogonal(IFVector ref) {
        double magnitude = getMagnitude();
        IFVector fVectorRef = FactoryGeometry.getIFVector(getBase().copy(), ref.getHead().copy());

        setHead(ref.getBase());

        return getCrossProduct(fVectorRef.copy()).setMagnitude(magnitude);
    }

    @Override
    public boolean isZero() {

        return getBase().equals(getHead());
    }

}
