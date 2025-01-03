package eu.scattering.core.impl.production.core.mutable.geometry.simple.vector;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.production.core.mutable.geometry.simple.SimplePresetProd;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FVectorProd extends SimplePresetProd<FVector> implements FVector {

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FPoint[] origin = new FPoint[2];
    private final FactoryDesignConcrete factory;
    private final double epsilon;

    private FVectorProd(FactoryDesignConcrete factory, double epsilon) {

        this.factory = factory;
        this.epsilon = epsilon;
    }

    public static FVector create(FactoryDesignConcrete factory, double epsilon) {
        FVectorProd fVector = new FVectorProd(factory, epsilon);

        fVector.origin[0] = factory.getFPoint();
        fVector.origin[1] = factory.getFPoint();

        return fVector;
    }

    public static FVector createRef(FactoryDesignConcrete factory, double epsilon) {

        return new FVectorProd(factory, epsilon);
    }

    @Override
    public FPoint getRefBase() {

        return origin[0];
    }

    @Override
    public FVector setRefBase(FPoint refBase) {

        if (refBase == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        if (refBase == getRefHead()) {
            throw new IllegalArgumentException("The base/head FPoints cannot be the same");
        }

        origin[0] = refBase;

        return this;
    }

    @Override
    public FPoint getRefHead() {

        return origin[1];
    }

    @Override
    public FVector setRefHead(FPoint refHead) {

        if (refHead == null) {
            throw new NullPointerException(" The head FPoint cannot be null");
        }

        if (refHead == getRefBase()) {
            throw new IllegalArgumentException("The base/head FPoints cannot be the same");
        }

        origin[1] = refHead;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector setRef(FPoint refBase, FPoint refHead) {
        setRefBase(refBase);
        setRefHead(refHead);

        return this;
    }

    @Override
    public FVector set(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        setBase(bX, bY, bZ);
        setHead(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector set(FPoint base, double hX, double hY, double hZ) {
        setBase(base);
        setHead(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector set(double bX, double bY, double bZ, FPoint head) {
        setBase(bX, bY, bZ);
        setHead(head);

        return this;
    }

    @Override
    public FVector applyStateFrom(FVector ref) {
        setBase(ref.getRefBase());
        setHead(ref.getRefHead());

        return this;
    }

    @Override
    public FVector set(FPoint base, FPoint head) {
        setBase(base);
        setHead(head);

        return this;
    }

    @Override
    public FVector set(FPairPos3D position) {

        setBase(position.getPosA());
        setHead(position.getPosB());

        return this;
    }

    @Override
    public FVector setBase(double bX, double bY, double bZ) {
        getRefBase().set(bX, bY, bZ);

        return this;
    }

    @Override
    public FVector setBase(FPoint base) {
        getRefBase().applyStateFrom(base);

        return this;
    }

    @Override
    public FVector setHead(double hX, double hY, double hZ) {
        getRefHead().set(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector setHead(FPoint head) {
        getRefHead().applyStateFrom(head);

        return this;
    }

    @Override
    public double getBaseX() {

        return getRefBase().getX();
    }

    @Override
    public FVector setBaseX(double bX) {
        getRefBase().setX(bX);

        return this;
    }

    @Override
    public double getBaseY() {

        return getRefBase().getY();
    }

    @Override
    public FVector setBaseY(double bY) {
        getRefBase().setY(bY);

        return this;
    }

    @Override
    public double getBaseZ() {

        return getRefBase().getZ();
    }

    @Override
    public FVector setBaseZ(double bZ) {
        getRefBase().setZ(bZ);

        return this;
    }

    @Override
    public double getHeadX() {

        return getRefHead().getX();
    }

    @Override
    public FVector setHeadX(double hX) {
        getRefHead().setX(hX);

        return this;
    }

    @Override
    public double getHeadY() {

        return getRefHead().getY();
    }

    @Override
    public FVector setHeadY(double hY) {
        getRefHead().setY(hY);

        return this;
    }

    @Override
    public double getHeadZ() {

        return getRefHead().getZ();
    }

    @Override
    public FVector setHeadZ(double hZ) {
        getRefHead().setZ(hZ);

        return this;
    }

    @Override
    public FVector set(FPos3D base, double hX, double hY, double hZ) {
        setBase(base);
        setHead(hX, hY, hZ);

        return this;
    }

    @Override
    public FVector set(double bX, double bY, double bZ, FPos3D head) {
        setBase(bX, bY, bZ);
        setHead(head);

        return this;
    }

    @Override
    public FVector set(FPos3D base, FPos3D head) {
        setBase(base);
        setHead(head);

        return this;
    }

    @Override
    public FVector setBase(FPos3D base) {
        setBase(base.getD0(), base.getD1(), base.getD2());

        return this;
    }

    @Override
    public FVector setHead(FPos3D head) {
        setHead(head.getD0(), head.getD1(), head.getD2());

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FVector ref) {

        if (this == ref) {
            return true;
        }

        return getRefBase().isExact(ref.getRefBase()) && getRefHead().isExact(ref.getRefHead());
    }

    @Override
    public boolean isSimilar(FVector ref) {

        if (this == ref) {
            return true;
        }

        return getRefBase().isSimilar(ref.getRefBase()) && getRefHead().isSimilar(ref.getRefHead());
    }

    @Override
    public JSONObject exportToJSON() {
        JSONObject json = new JSONObject();

        json.append("vector", getRefBase().exportToJSON());
        json.append("vector", getRefHead().exportToJSON());

        return json;
    }

    @Override
    public FVector importFromJSON(JSONObject json) {
        JSONArray structure = json.getJSONArray("vector");

        setRefBase(factory.getFPoint().importFromJSON(structure.getJSONObject(0)));
        setRefHead(factory.getFPoint().importFromJSON(structure.getJSONObject(1)));

        return this;
    }

    @Override
    public FVector copy() {
        FVector fVector = factory.getFVector();

        fVector.setRefBase(factory.getFPoint(getRefBase()));
        fVector.setRefHead(factory.getFPoint(getRefHead()));

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

        hashCode = 31 * hashCode + getRefBase().hashCode();
        hashCode = 31 * hashCode + getRefHead().hashCode();

        return hashCode;
    }

//--------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();
        fPointList.add(getRefBase());
        fPointList.add(getRefHead());

        return fPointList;
    }

//--------------------------------------------------

    @Override
    public FVector setSphericalCoordinates(double inclination, double azimuth) {
        FVector fCopyLocal = copy().moveBaseToCenter();

        fCopyLocal.getRefHead().setSphericalCoordinates(inclination, azimuth);
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
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
    public FVector moveBaseToCenter() {

        return moveBase(factory.getFPoint());
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {

        return moveBase(factory.getFPoint(bX, bY, bZ));
    }

    @Override
    public FVector moveBase(FPoint base) {
        FPoint translation = factory.getFPoint(base).sub(getRefBase());

        getRefBase().applyStateFrom(base);
        getRefHead().add(translation);

        return this;
    }

    @Override
    public FVector moveHeadToCenter() {

        return moveHead(factory.getFPoint());
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {

        return moveHead(factory.getFPoint(hX, hY, hZ));
    }

    @Override
    public FVector moveHead(FPoint head) {
        FPoint translation = factory.getFPoint().applyStateFrom(head).sub(getRefHead());

        getRefBase().add(translation);
        getRefHead().applyStateFrom(head);

        return this;
    }

    @Override
    public FVector shiftForward(double distance) {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the IFVector is not defined");
        }

        if (distance < 0) {
            return shiftBackward(-distance);
        }

        FVector fCopyLocal = copy();
        fCopyLocal.setLength(distance);

        moveBase(fCopyLocal.getRefHead());

        return this;
    }

    @Override
    public FVector shiftBackward(double distance) {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the IFVector is not defined");
        }

        if (distance < 0) {
            return shiftForward(-distance);
        }

        FVector fCopyLocal = copy().reflectHead();
        fCopyLocal.setLength(distance);

        moveBase(fCopyLocal.getRefHead());

        return this;
    }

    @Override
    public FVector add(FVector vector) {
        FVector fCopyLocal = copy().moveBaseToCenter();
        FVector fCopyExternal = vector.copy().moveBaseToCenter();

        fCopyLocal.getRefHead().add(fCopyExternal.getRefHead());
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector sub(FVector vector) {
        FVector fCopyLocal = copy().moveBaseToCenter();
        FVector fCopyExternal = vector.copy().moveBaseToCenter();

        fCopyLocal.getRefHead().sub(fCopyExternal.getRefHead());
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public double getLengthAxisX() {

        return Math.abs(getRefHead().getX() - getRefBase().getX());
    }

    @Override
    public double getLengthAxisY() {

        return Math.abs(getRefHead().getY() - getRefBase().getY());
    }

    @Override
    public double getLengthAxisZ() {

        return Math.abs(getRefHead().getZ() - getRefBase().getZ());
    }

    @Override
    public FVector normalize() {
        FVector fCopyLocal = copy().moveBaseToCenter();

        fCopyLocal.getRefHead().normalize();
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector reflectBase() {
        FVector fCopyLocal = copy().moveHeadToCenter();

        fCopyLocal.getRefBase().reflect();
        fCopyLocal.moveHead(getRefHead());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector reflectHead() {
        FVector fCopyLocal = copy().moveBaseToCenter();

        fCopyLocal.getRefHead().reflect();
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public FVector reflect(FPoint center) {

        getRefBase().reflect(center);
        getRefHead().reflect(center);

        return this;
    }

    @Override
    public FVector invertDirection() {
        FPoint container = getRefHead().copy();

        getRefHead().applyStateFrom(getRefBase());
        getRefBase().applyStateFrom(container);

        return this;
    }

    @Override
    public double getLength() {

        return Math.sqrt(getLengthP2());
    }

    @Override
    public double getLengthP2() {
        double distanceX = getRefHead().getX() - getRefBase().getX();
        double distanceY = getRefHead().getY() - getRefBase().getY();
        double distanceZ = getRefHead().getZ() - getRefBase().getZ();

        return (distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ);
    }

    @Override
    public FVector setLength(double length) {
        FVector fCopyLocal = copy().moveBaseToCenter();

        fCopyLocal.getRefHead().setLength(length);
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public double getInclination() {
        FVector fCopyLocal = copy().moveBaseToCenter();

        return fCopyLocal.getRefHead().getInclination();
    }

    @Override
    public FVector setInclination(double inclination) {
        FVector fCopyLocal = copy().moveBaseToCenter();

        fCopyLocal.getRefHead().setInclination(inclination);
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
    }

    @Override
    public double getAzimuth() {
        FVector fCopyLocal = copy().moveBaseToCenter();

        return fCopyLocal.getRefHead().getAzimuth();
    }

    @Override
    public FVector setAzimuth(double azimuth) {
        FVector fCopyLocal = copy().moveBaseToCenter();

        fCopyLocal.getRefHead().setAzimuth(azimuth);
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
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
        FVector fCopyLocal = copy().moveBaseToCenter();
        FVector fCopyExternal = ref.copy().moveBaseToCenter();

        dProd = fCopyLocal.getDotProduct(fCopyExternal);
        magAB = fCopyLocal.getRefHead().getLength() * fCopyExternal.getRefHead().getLength();
        angle = Math.acos(dProd / magAB);

        return Double.isNaN(angle) ? 0 : angle;
    }

    @Override
    public double getDotProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBaseToCenter();
        FVector fCopyExternal = ref.copy().moveBaseToCenter();

        return fCopyLocal.getRefHead().getDotProduct(fCopyExternal.getRefHead());
    }

    @Override
    public FVector setCrossProduct(FVector ref) {
        FVector fCopyLocal = copy().moveBaseToCenter();
        FVector fCopyExternal = ref.copy().moveBaseToCenter();

        fCopyLocal.getRefHead().setCrossProduct(fCopyExternal.getRefHead());
        fCopyLocal.moveBase(getRefBase());

        return applyStateFrom(fCopyLocal);
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

        FPoint fCopyLocal = copy().moveBaseToCenter().normalize().getRefHead();
        FPoint fCopyExternal = ref.copy().moveBaseToCenter().normalize().getRefHead();

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
        FPoint baseCopy = getRefBase().copy();

        return applyStateFrom(ref).setLength(magnitude).moveBase(baseCopy);
    }

    @Override
    public boolean isAntiParallel(FVector ref) {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        FPoint fCopyLocal = copy().moveBaseToCenter().normalize().getRefHead();
        FPoint fCopyExternal = ref.copy().moveBaseToCenter().normalize().getRefHead();

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
        FPoint baseCopy = getRefBase().copy();

        return applyStateFrom(ref).setLength(magnitude).moveBase(baseCopy).reflectHead();
    }

    @Override
    public boolean isOrthogonal(FVector ref) {

        if (isNonDirectional()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (ref.isNonDirectional()) {
            throw new IllegalStateException("The direction of the provided FVector is not defined");
        }

        return (Math.abs(getDotProduct(ref)) < epsilon) || (Math.abs((Math.PI * 0.5) - getAngle(ref)) < epsilon);
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
        FVector fVectorRef = factory.getFVector(ref.getRefBase().copy(), ref.getRefHead().copy());
        FVector fVectorRot = copy().setCrossProduct(fVectorRef);

        fVectorRef.setCrossProduct(fVectorRot).setLength(magnitude);
        fVectorRef.moveBase(getRefBase());

        applyStateFrom(fVectorRef);

        return this;
    }

    @Override
    public boolean isZero() {

        return getRefBase().isZero() && getRefHead().isZero();
    }

    @Override
    public boolean isNonDirectional() {

        return getRefBase().isSimilar(getRefHead());
    }

    @Override
    public FPairPos3D toTuplePos3D() {

        var posA = factory.getFPos3D(getBaseX(), getBaseY(), getBaseZ());
        var posB = factory.getFPos3D(getHeadX(), getHeadY(), getHeadZ());

        return factory.getFPairPos3D(posA, posB);
    }

}
