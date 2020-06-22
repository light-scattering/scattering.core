package eu.scattering.core.geometry.base.vector.impl;

import eu.scattering.core.geometry.PresetGeometry;
import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.Configuration.jitter;

public class FVector extends PresetGeometry<IFVector> implements IFVector {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private IFPoint[] origin = new IFPoint[2];

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
        json.append("assembly", getBase().exportToJSON());
        json.append("assembly", getHead().exportToJSON());

        return json;
    }

    @Override
    public IFVector importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("assembly");

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
    public IFVector setSphericalCoordinates(double polar, double azimuthal) {

        originShift();
        getHead().setSphericalCoordinates(polar, azimuthal);
        originRestore();

        return this;
    }

    @Override
    public IFVector setRandom(IFPoint ...exclude) {

        IFPoint[] excludeShift = new IFPoint[exclude.length];

        for (int i = 0 ; i < exclude.length ; i++ ) {
            excludeShift[i] = exclude[i].copy().sub(getBase());
        }

        originShift();
        getHead().setRandom(excludeShift);
        originRestore();

        return this;
    }

    @Override
    public IFVector relocateBase(double x, double y, double z) {
        return relocateBase(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public IFVector relocateBase(IFPoint base) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(base).sub(getBase());

        getBase().set(base);
        getHead().add(translation);

        return this;
    }

    @Override
    public IFVector relocateHead(double x, double y, double z) {
        return relocateHead(FactoryGeometry.getIFPoint(x, y, z));
    }

    @Override
    public IFVector relocateHead(IFPoint head) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(head).sub(getHead());

        getBase().add(translation);
        getHead().set(head);

        return this;
    }

    @Override
    public IFVector add(IFVector fVector) {

        originShift(fVector);
        getHead().add(fVector.getHead());
        originRestore(fVector);

        return this;
    }

    @Override
    public IFVector sub(IFVector fVector) {

        originShift(fVector);
        getHead().sub(fVector.getHead());
        originRestore(fVector);

        return this;
    }

    @Override
    public double getDimX() {
        return Math.abs(getHead().getX() - getBase().getX());
    }

    @Override
    public double getDimY() {
        return Math.abs(getHead().getY() - getBase().getY());
    }

    @Override
    public double getDimZ() {
        return Math.abs(getHead().getZ() - getBase().getZ());
    }

    @Override
    public IFVector normalize() {

        originShift();
        getHead().normalize();
        originRestore();

        return this;
    }

    @Override
    public IFVector reflect() {

        originShift();
        getHead().reflect();
        originRestore();

        return this;
    }

    @Override
    public IFVector invert() {
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

        originShift();
        getHead().setRadius(magnitude);
        originRestore();

        return this;
    }

    @Override
    public double getInclination() {
        double inclination;

        originShift();
        inclination = getHead().getInclination();
        originRestore();

        return inclination;
    }

    @Override
    public IFVector setInclination(double inclination) {

        originShift();
        getHead().setInclination(inclination);
        originRestore();

        return this;
    }

    @Override
    public double getAzimuth() {
        double azimuth;

        originShift();
        azimuth = getHead().getAzimuth();
        originRestore();

        return azimuth;
    }

    @Override
    public IFVector setAzimuth(double azimuth) {

        originShift();
        getHead().setAzimuth(azimuth);
        originRestore();

        return this;
    }

    @Override
    public double getAngle(IFVector fVector) {
        double angle, dProd, magAB;

        dProd = dProd(fVector);
        originShift(fVector);
        magAB = getHead().getRadius() * fVector.getHead().getRadius();
        angle = Math.acos(dProd / magAB);
        originRestore(fVector);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double dProd(IFVector fVector) {
        double dProd;

        originShift(fVector);
        dProd = getHead().dProd(fVector.getHead());
        originRestore(fVector);

        return dProd;
    }

    @Override
    public IFVector cProd(IFVector fVector) {

        originShift(fVector);
        getHead().cProd(fVector.getHead());
        originRestore(fVector);

        return this;
    }

    @Override
    public boolean isParallel(IFVector fVector) {
        double conX, conY, conZ;

        originShift(fVector);
        conX = getHead().getX() / fVector.getHead().getX();
        conY = getHead().getY() / fVector.getHead().getY();
        conZ = getHead().getZ() / fVector.getHead().getZ();
        originRestore(fVector);

        return Math.abs(conX - conY) < jitter && Math.abs(conX - conZ) < jitter;
    }

    @Override
    public boolean isOrthogonal(IFVector fVector) {
        return Math.abs(dProd(fVector)) < jitter;
    }

    @Override
    public boolean isZero() {
        return getBase().equals(getHead());
    }

//--------------------------------------------------

    private void originShift() {
        getHead().sub(getBase());
    }

    private IFPoint originShift(IFVector fVector) {
        originShift();

        return fVector.getHead().sub(fVector.getBase());
    }

    private void originRestore() {
        getHead().add(getBase());
    }

    private IFPoint originRestore(IFVector fVector) {
        originRestore();

        return fVector.getHead().add(fVector.getBase());
    }

}
