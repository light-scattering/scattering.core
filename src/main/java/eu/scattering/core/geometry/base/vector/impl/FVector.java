package eu.scattering.core.geometry.base.vector.impl;

import eu.scattering.core.CoreObject;
import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.factory.FactoryGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.base.vector.IFVector;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FVector extends CoreObject implements IFVector {

    private IFPoint[] origin = new IFPoint[2];

    private FVector() { }

    public static FVector create() {
        FVector fVector = new FVector();

        fVector.origin[0] = FactoryGeometry.getIFPoint();
        fVector.origin[1] = FactoryGeometry.getIFPoint();

        return fVector;
    }

//--------------------------------------------------

    @Override
    public boolean isExact(IFVector fVector) {
        return origin[0].isExact(fVector.getBase()) && origin[1].isExact(fVector.getHead());
    }

    @Override
    public boolean isSimilar(IFVector fVector) {
        return origin[0].isSimilar(fVector.getBase()) && origin[1].isSimilar(fVector.getHead());
    }

    @Override
    public int getHashCode() {
        int hashCode = 7;

        hashCode = 31 * hashCode + origin[0].getHashCode();
        hashCode = 31 * hashCode + origin[1].getHashCode();

        return hashCode;
    }

    @Override
    public String exportToJSON() {
        JSONObject json = new JSONObject();
        json.put("base", origin[0].exportToJSON());
        json.put("head", origin[1].exportToJSON());

        return json.toString();
    }

    @Override
    public IFVector importFromJSON(String json) {
        JSONObject structure = new JSONObject(json);

        origin[0] = FactoryGeometry.getIFPoint().importFromJSON(structure.getJSONObject("base").toString());
        origin[1] = FactoryGeometry.getIFPoint().importFromJSON(structure.getJSONObject("head").toString());

        return this;
    }

    @Override
    public FVector copy() {
        FVector fVector = new FVector();

        fVector.origin[0] = FactoryGeometry.getIFPoint().set(origin[0]);
        fVector.origin[1] = FactoryGeometry.getIFPoint().set(origin[1]);

        return fVector;
    }

//--------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof IFVector)) {
            return false;
        }

        return isExact((IFVector) object);
    }

    @Override
    public int hashCode() {
        return getHashCode();
    }

    @Override
    public String toString() {
        return
                "FVector { " +
                    "Base [" + origin[0].getX() + "," + origin[0].getY() + "," + origin[0].getZ() + "], " +
                    "Head [" + origin[1].getX() + "," + origin[1].getY() + "," + origin[1].getZ() + "], " +
                "}";
    }

    @Override
    public FVector clone() {
        return copy();
    }

//--------------------------------------------------

    @Override
    public FVector set(IFVector fVector) {
        origin[0].set(fVector.getBase());
        origin[1].set(fVector.getHead());

        return this;
    }

    @Override
    public FVector set(IFPoint base, IFPoint head) {
        origin[0].set(base);
        origin[1].set(head);

        return this;
    }

    @Override
    public IFVector setRef(IFPoint baseRef, IFPoint headRef) {

        if (baseRef == null) {
            throw new NullPointerException(" The base IFPoint must not be null");
        }

        if (headRef == null) {
            throw new NullPointerException(" The head IFPoint must not be null");
        }

        if (baseRef == headRef) {
            throw new IllegalArgumentException("The base/head IFPoints must not be the same instances");
        }

        origin[0] = baseRef;
        origin[1] = headRef;

        return this;
    }


    @Override
    public IFPoint getBase() {
        return origin[0];
    }

    @Override
    public FVector setBase(IFPoint base) {
        origin[0].set(base);

        return this;
    }

    @Override
    public IFVector setBaseRef(IFPoint baseRef) {

        if (baseRef == null) {
            throw new NullPointerException(" The base IFPoint must not be null");
        }

        if (baseRef == origin[1]) {
            throw new IllegalArgumentException("The base/head IFPoints must not be the same instances");
        }

        origin[0] = baseRef;

        return this;
    }

    @Override
    public IFPoint getHead() {
        return origin[1];
    }

    @Override
    public IFVector setHead(IFPoint head) {
        origin[1].set(head);

        return this;
    }

    @Override
    public IFVector setHeadRef(IFPoint headRef) {

        if (headRef == null) {
            throw new NullPointerException(" The head IFPoint must not be null");
        }

        if (headRef == origin[0]) {
            throw new IllegalArgumentException("The base/head IFPoints must not be the same instances");
        }

        origin[1] = headRef;

        return this;
    }

//--------------------------------------------------

    @Override
    public List<IFPoint> getIFPoints() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(origin[0]);
        fPointList.add(origin[1]);

        return fPointList;
    }

    @Override
    public IFVector add(IFPoint fPoint) {
        origin[0].add(fPoint);
        origin[1].add(fPoint);

        return this;
    }

    @Override
    public IFVector add(double x, double y, double z) {
        origin[0].add(x, y, z);
        origin[1].add(x, y, z);

        return this;
    }

    @Override
    public IFVector addX(double x) {
        origin[0].addX(x);
        origin[1].addX(x);

        return null;
    }

    @Override
    public IFVector addY(double y) {
        origin[0].addY(y);
        origin[1].addY(y);

        return this;
    }

    @Override
    public IFVector addZ(double z) {
        origin[0].addZ(z);
        origin[1].addZ(z);

        return this;
    }

    @Override
    public IFVector sub(IFPoint fPoint) {
        origin[0].sub(fPoint);
        origin[1].sub(fPoint);

        return this;
    }

    @Override
    public IFVector sub(double x, double y, double z) {
        origin[0].sub(x, y, z);
        origin[1].sub(x, y, z);

        return this;
    }

    @Override
    public IFVector subX(double x) {
        origin[0].subX(x);
        origin[1].subX(x);

        return this;
    }

    @Override
    public IFVector subY(double y) {
        origin[0].subY(y);
        origin[1].subY(y);

        return this;
    }

    @Override
    public IFVector subZ(double z) {
        origin[0].subZ(z);
        origin[1].subZ(z);

        return this;
    }

    @Override
    public IFVector mul(IFPoint fPoint) {
        origin[0].mul(fPoint);
        origin[1].mul(fPoint);

        return this;
    }

    @Override
    public IFVector mul(double x, double y, double z) {
        origin[0].mul(x, y, z);
        origin[1].mul(x, y, z);

        return this;
    }

    @Override
    public IFVector mulX(double x) {
        origin[0].mulX(x);
        origin[1].mulX(x);

        return this;
    }

    @Override
    public IFVector mulY(double y) {
        origin[0].mulY(y);
        origin[1].mulY(y);

        return this;
    }

    @Override
    public IFVector mulZ(double z) {
        origin[0].mulZ(z);
        origin[1].mulZ(z);

        return this;
    }

    @Override
    public IFVector div(IFPoint fPoint) {
        origin[0].div(fPoint);
        origin[1].div(fPoint);

        return this;
    }

    @Override
    public IFVector div(double x, double y, double z) {
        origin[0].div(x, y, z);
        origin[1].div(x, y, z);

        return this;
    }

    @Override
    public IFVector divX(double x) {
        origin[0].divX(x);
        origin[1].divX(x);

        return this;
    }

    @Override
    public IFVector divY(double y) {
        origin[0].divY(y);
        origin[1].divY(y);

        return this;
    }

    @Override
    public IFVector divZ(double z) {
        origin[0].divZ(z);
        origin[1].divZ(z);

        return this;
    }

    @Override
    public IFVector scale(double scaleFactor) {
        return mul(scaleFactor, scaleFactor, scaleFactor);
    }

//--------------------------------------------------

    @Override
    public IFVector setSphericalCoordinates(double polar, double azimuthal) {

        originShift();
        origin[1].setSphericalCoordinates(polar, azimuthal);
        originRestore();

        return this;
    }

    @Override
    public IFVector setRandom(IFPoint ...exclude) {

        IFPoint[] excludeShift = new IFPoint[exclude.length];

        for (int i = 0 ; i < exclude.length ; i++ ) {
            excludeShift[i] = exclude[i].copy().sub(origin[0]);
        }

        originShift();
        origin[1].setRandom(excludeShift);
        originRestore();

        return this;
    }

    @Override
    public IFVector relocateBase(IFPoint base) {
        IFPoint translation = FactoryGeometry.getIFPoint().set(base).sub(origin[0]);

        origin[0].set(base);
        origin[1].add(translation);

        return this;
    }

    @Override
    public IFVector relocateHead(IFPoint head) {
        return null;
    }

    @Override
    public IFVector add(IFVector fVector) {

        originShift(fVector);
        origin[1].add(fVector.getHead());
        originRestore(fVector);

        return this;
    }

    @Override
    public IFVector sub(IFVector fVector) {

        originShift(fVector);
        origin[1].sub(fVector.getHead());
        originRestore(fVector);

        return this;
    }

    @Override
    public double getDimX() {
        return Math.abs(origin[1].getX() - origin[0].getX());
    }

    @Override
    public double getDimY() {
        return Math.abs(origin[1].getY() - origin[0].getY());
    }

    @Override
    public double getDimZ() {
        return Math.abs(origin[1].getZ() - origin[0].getZ());
    }

    @Override
    public IFVector normalize() {

        originShift();
        origin[1].normalize();
        originRestore();

        return this;
    }

    @Override
    public IFVector reflect() {

        originShift();
        origin[1].reflect();
        originRestore();

        return this;
    }

    @Override
    public IFVector invert() {
        IFPoint container = origin[1];

        origin[1] = origin[0];
        origin[0] = container;

        return this;
    }

    @Override
    public double getMagnitude() {

        double distanceX = origin[1].getX() - origin[0].getX();
        double distanceY = origin[1].getY() - origin[0].getY();
        double distanceZ = origin[1].getZ() - origin[0].getZ();

        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));
    }

    @Override
    public IFVector setMagnitude(double magnitude) throws SamePositionException {

        originShift();
        origin[1].setRadius(magnitude);
        originRestore();

        return this;
    }

    @Override
    public double getInclination() {
        double inclination;

        originShift();
        inclination = origin[1].getInclination();
        originRestore();

        return inclination;
    }

    @Override
    public IFVector setInclination(double inclination) {

        originShift();
        origin[1].setInclination(inclination);
        originRestore();

        return this;
    }

    @Override
    public double getAzimuth() {
        double azimuth;

        originShift();
        azimuth = origin[1].getAzimuth();
        originRestore();

        return azimuth;
    }

    @Override
    public IFVector setAzimuth(double azimuth) {

        originShift();
        origin[1].setAzimuth(azimuth);
        originRestore();

        return this;
    }

    @Override
    public double getAngle(IFVector fVector) {
        double angle, cProd, magAB;

        originShift(fVector);
        cProd = dProd(fVector);
        magAB = getMagnitude() + fVector.getMagnitude();
        angle = Math.acos(cProd / magAB);
        originRestore(fVector);

        return angle;
    }

    @Override
    public double dProd(IFVector fVector) {
        double dProd, dimX, dimY, dimZ;

        originShift(fVector);
        dimX = origin[1].getX() * fVector.getHead().getX();
        dimY = origin[1].getY() * fVector.getHead().getY();
        dimZ = origin[1].getZ() * fVector.getHead().getZ();
        dProd = dimX + dimY + dimZ;
        originRestore(fVector);

        return dProd;
    }

    @Override
    public IFVector cProd(IFVector fVector) {
        double dimX, dimY, dimZ;

        originShift(fVector);
        dimX = (origin[1].getY() * fVector.getHead().getZ()) - (origin[1].getZ() * fVector.getHead().getY());
        dimY = (origin[1].getZ() * fVector.getHead().getX()) - (origin[1].getX() * fVector.getHead().getZ());
        dimZ = (origin[1].getX() * fVector.getHead().getY()) - (origin[1].getY() * fVector.getHead().getX());
        origin[1].set(dimX, dimY, dimZ);
        originRestore(fVector);

        return this;
    }

    @Override
    public double isParallel(IFVector fVector) {
        return 0;
    }

    @Override
    public double isOrthogonal(IFVector fVector) {
        return 0;
    }

    @Override
    public boolean isZero() {
        return false;
    }

//--------------------------------------------------

    private void originShift() {
        origin[1].sub(origin[0]);
    }

    private IFPoint originShift(IFVector fVector) {
        originShift();

        return fVector.getHead().sub(fVector.getBase());
    }

    private void originRestore() {
        origin[1].add(origin[0]);
    }

    private IFPoint originRestore(IFVector fVector) {
        originRestore();

        return fVector.getHead().add(fVector.getBase());
    }

}
