package eu.scattering.core.impl.component.geometry.base;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.base.vector.FVectorFactory;
import eu.scattering.core.design.transfer.TransferFactory;
import eu.scattering.core.design.transfer.TransferFactoryConcrete;
import eu.scattering.core.design.transfer.primitive.FMatrix3x3D;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FVectorDef implements FVector {
    private static final TransferFactory factoryExt = TransferFactoryConcrete.create();

    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "vector";
    private static final String JSON_VAL = "position";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FVectorFactory factorySelf;

    private FPoint oBase;
    private FPoint oHead;

    private FVectorDef(FVectorFactory factorySelf) {

        this.factorySelf = factorySelf;
    }

    public static FVector create(FVectorFactory factorySelf, FPoint refBase, FPoint refHead) {

        if (refBase == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        if (refHead == null) {
            throw new NullPointerException("The head FPoint cannot be null");
        }

        var fVector = new FVectorDef(factorySelf);

        fVector.setRefBase(refBase);
        fVector.setRefHead(refHead);

        return fVector;
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FPoint getRefBase() {

        return oBase;
    }

    @Override
    public FVector setRefBase(FPoint refBase) {

        if (refBase == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        oBase = refBase;

        return this;
    }

    @Override
    public FPoint getRefHead() {

        return oHead;
    }

    @Override
    public FVector setRefHead(FPoint refHead) {

        if (refHead == null) {
            throw new NullPointerException("The head FPoint cannot be null");
        }

        oHead = refHead;

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
    public FVector set(FPoint base, FPoint head) {
        setBase(base);
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
    public FVector applyStateFrom(FPairPos3D position) {
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
    public FVector setBase(FPos3D base) {
        setBase(base.getD0(), base.getD1(), base.getD2());

        return this;
    }

    @Override
    public double getBaseX() {

        return getRefBase().getX();
    }

    @Override
    public double getBaseY() {

        return getRefBase().getY();
    }

    @Override
    public double getBaseZ() {

        return getRefBase().getZ();
    }

    @Override
    public FVector setBaseX(double bX) {
        getRefBase().setX(bX);

        return this;
    }

    @Override
    public FVector setBaseY(double bY) {
        getRefBase().setY(bY);

        return this;
    }

    @Override
    public FVector setBaseZ(double bZ) {
        getRefBase().setZ(bZ);

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
    public FVector setHead(FPos3D head) {
        setHead(head.getD0(), head.getD1(), head.getD2());

        return this;
    }

    @Override
    public double getHeadX() {

        return getRefHead().getX();
    }

    @Override
    public double getHeadY() {

        return getRefHead().getY();
    }

    @Override
    public double getHeadZ() {

        return getRefHead().getZ();
    }

    @Override
    public FVector setHeadX(double hX) {
        getRefHead().setX(hX);

        return this;
    }

    @Override
    public FVector setHeadY(double hY) {
        getRefHead().setY(hY);

        return this;
    }

    @Override
    public FVector setHeadZ(double hZ) {
        getRefHead().setZ(hZ);

        return this;
    }

    @Override
    public FVector applyStateFrom(FVector arg) {
        setBase(arg.getRefBase());
        setHead(arg.getRefHead());

        return this;
    }

    @Override
    public FVector set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPoint base = getRefBase().set(structure.getJSONObject(0));
        FPoint head = getRefHead().set(structure.getJSONObject(1));

        return set(base, head);
    }

    @Override
    public FVector applyStateTo(FVector arg) {

        arg.applyStateFrom(this);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getRefBase().isExact(bX, bY, bZ) && getRefHead().isExact(hX, hY, hZ);
    }

    @Override
    public boolean isExact(FPoint base, FPoint head) {

        return getRefBase().isExact(base) && getRefHead().isExact(head);
    }

    @Override
    public boolean isExact(FPos3D base, FPos3D head) {

        return getRefBase().isExact(base) && getRefHead().isExact(head);
    }

    @Override
    public boolean isExact(FVector arg) {

        if (this == arg) {
            return true;
        }

        return getRefBase().isExact(arg.getRefBase()) && getRefHead().isExact(arg.getRefHead());
    }

    @Override
    public boolean isExact(FPairPos3D arg) {
        boolean isBaseExact = getRefBase().isExact(arg.getPosA());
        boolean isHeadExact = getRefHead().isExact(arg.getPosB());

        return isBaseExact && isHeadExact;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FVector) {
            return isExact((FVector) arg);
        }

        return false;
    }

    @Override
    public boolean isExactBaseCommon(double hX, double hY, double hZ) {

        return getRefHead().isExact(hX, hY, hZ);
    }

    @Override
    public boolean isExactBaseCommon(FPoint head) {

        return getRefHead().isExact(head);
    }

    @Override
    public boolean isExactBaseCommon(FPos3D head) {

        return getRefHead().isExact(head);
    }

    @Override
    public boolean isExactBaseZero(double hX, double hY, double hZ) {

        return isExact(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isExactBaseZero(FPoint head) {

        return isExactBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isExactBaseZero(FPos3D head) {

        return isExactBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getRefBase().isSimilar(bX, bY, bZ) && getRefHead().isSimilar(hX, hY, hZ);
    }

    @Override
    public boolean isSimilar(FPoint base, FPoint head) {

        return isSimilar(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isSimilar(FPos3D base, FPos3D head) {

        return isSimilar(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isSimilar(FVector arg) {

        if (this == arg) {
            return true;
        }

        return getRefBase().isSimilar(arg.getRefBase()) && getRefHead().isSimilar(arg.getRefHead());
    }

    @Override
    public boolean isSimilar(FPairPos3D arg) {
        boolean isBaseSimilar = getRefBase().isSimilar(arg.getPosA());
        boolean isHeadSimilar = getRefHead().isSimilar(arg.getPosB());

        return isBaseSimilar && isHeadSimilar;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FVector) {
            return isSimilar((FVector) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilarBaseCommon(double hX, double hY, double hZ) {

        return getRefHead().isSimilar(hX, hY, hZ);
    }

    @Override
    public boolean isSimilarBaseCommon(FPoint head) {

        return getRefHead().isSimilar(head);
    }

    @Override
    public boolean isSimilarBaseCommon(FPos3D head) {

        return getRefHead().isSimilar(head);
    }

    @Override
    public boolean isSimilarBaseZero(double hX, double hY, double hZ) {

        return isSimilar(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isSimilarBaseZero(FPoint head) {

        return isSimilarBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isSimilarBaseZero(FPos3D head) {

        return isSimilarBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector self() {

        return this;
    }

    @Override
    public FVector copy() {

        return supplyFVector().applyStateFrom(this);
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        FPos3D posA = getRefBase().toFPos3D();
        FPos3D posB = getRefHead().toFPos3D();

        return factoryExt.getFPairPos3D(posA, posB);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.append(JSON_VAL, getRefBase().toJSON());
        json.append(JSON_VAL, getRefHead().toJSON());

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector scale(double factor) {

        getRefBase().mulFactor(factor);
        getRefHead().mulFactor(factor);

        return this;
    }

    @Override
    public FVector translate(double x, double y, double z) {

        getRefBase().add(x, y, z);
        getRefHead().add(x, y, z);

        return this;
    }

    @Override
    public FVector translate(FPoint arg) {

        return translate(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FVector translate(FPos3D arg) {

        return translate(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector add(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().addXYZ(hX - bX, hY - bY, hZ - bZ);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector add(FPoint base, FPoint head) {

        return add(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector add(FPos3D base, FPos3D head) {

        return add(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector add(FVector arg) {

        return add(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public FVector add(FPairPos3D arg) {

        return add(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector addBaseCommon(double hX, double hY, double hZ) {

        return add(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector addBaseCommon(FPoint head) {

        return add(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector addBaseCommon(FPos3D head) {

        return add(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector addBaseZero(double hX, double hY, double hZ) {

        return add(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector addBaseZero(FPoint head) {

        return addBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector addBaseZero(FPos3D head) {

        return addBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector sub(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().subXYZ(hX - bX, hY - bY, hZ - bZ);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector sub(FPoint base, FPoint head) {

        return sub(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector sub(FPos3D base, FPos3D head) {

        return sub(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector sub(FVector arg) {

        return sub(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public FVector sub(FPairPos3D arg) {

        return sub(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector subBaseCommon(double hX, double hY, double hZ) {

        return sub(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector subBaseCommon(FPoint head) {

        return sub(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector subBaseCommon(FPos3D head) {

        return sub(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector subBaseZero(double hX, double hY, double hZ) {

        return sub(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector subBaseZero(FPoint head) {

        return subBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector subBaseZero(FPos3D head) {

        return subBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector addXYZ(FPoint arg) {

        return addXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FVector addXYZ(FPos3D arg) {

        return addXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector addXYZ(double x, double y, double z) {

        getRefBase().addXYZ(x, y, z);
        getRefHead().addXYZ(x, y, z);

        return this;
    }

    @Override
    public FVector addFactor(double factor) {

        return addXYZ(factor, factor, factor);
    }

    @Override
    public FVector addX(double x) {

        getRefBase().addX(x);
        getRefHead().addX(x);

        return this;
    }

    @Override
    public FVector addY(double y) {

        getRefBase().addY(y);
        getRefHead().addY(y);

        return this;
    }

    @Override
    public FVector addZ(double z) {

        getRefBase().addZ(z);
        getRefHead().addZ(z);

        return this;
    }

    @Override
    public FVector subXYZ(FPoint arg) {

        return subXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FVector subXYZ(FPos3D arg) {

        return subXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector subXYZ(double x, double y, double z) {

        getRefBase().subXYZ(x, y, z);
        getRefHead().subXYZ(x, y, z);

        return this;
    }

    @Override
    public FVector subFactor(double factor) {

        return subXYZ(factor, factor, factor);
    }

    @Override
    public FVector subX(double x) {

        getRefBase().subX(x);
        getRefHead().subX(x);

        return this;
    }

    @Override
    public FVector subY(double y) {

        getRefBase().subY(y);
        getRefHead().subY(y);

        return this;
    }

    @Override
    public FVector subZ(double z) {

        getRefBase().subZ(z);
        getRefHead().subZ(z);

        return this;
    }

    @Override
    public FVector mulXYZ(FPoint arg) {

        return mulXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FVector mulXYZ(FPos3D arg) {

        return mulXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector mulXYZ(double x, double y, double z) {

        getRefBase().mulXYZ(x, y, z);
        getRefHead().mulXYZ(x, y, z);

        return this;
    }

    @Override
    public FVector mulFactor(double factor) {

        return mulXYZ(factor, factor, factor);
    }

    @Override
    public FVector mulX(double x) {

        getRefBase().mulX(x);
        getRefHead().mulX(x);

        return this;
    }

    @Override
    public FVector mulY(double y) {

        getRefBase().mulY(y);
        getRefHead().mulY(y);

        return this;
    }

    @Override
    public FVector mulZ(double z) {

        getRefBase().mulZ(z);
        getRefHead().mulZ(z);

        return this;
    }

    @Override
    public FVector divXYZ(FPoint arg) {

        return divXYZ(arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public FVector divXYZ(FPos3D arg) {

        return divXYZ(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector divXYZ(double x, double y, double z) {

        getRefBase().divXYZ(x, y, z);
        getRefHead().divXYZ(x, y, z);

        return this;
    }

    @Override
    public FVector divFactor(double factor) {

        return divXYZ(factor, factor, factor);
    }

    @Override
    public FVector divX(double x) {

        getRefBase().divX(x);
        getRefHead().divX(x);

        return this;
    }

    @Override
    public FVector divY(double y) {

        getRefBase().divY(y);
        getRefHead().divY(y);

        return this;
    }

    @Override
    public FVector divZ(double z) {

        getRefBase().divZ(z);
        getRefHead().divZ(z);

        return this;
    }

    @Override
    public FVector mul(FMatrix3x3D arg) {

        getRefBase().mul(arg);
        getRefHead().mul(arg);

        return this;
    }

    //--------------------------------------------------

    @Override
    public List<FPoint> toFPoints() {
        List<FPoint> fPointList = new ArrayList<>();

        fPointList.add(getRefBase());
        fPointList.add(getRefHead());

        return fPointList;
    }

    @Override
    public boolean isZeroLength() {

        return getRefBase().isExact(getRefHead());
    }

    @Override
    public boolean isNearZeroLength() {

        return getRefBase().isSimilar(getRefHead());
    }

    @Override
    public double getMagnitudeP2() {
        double distX = getRefHead().getX() - getRefBase().getX();
        double distY = getRefHead().getY() - getRefBase().getY();
        double distZ = getRefHead().getZ() - getRefBase().getZ();

        return (distX * distX) + (distY * distY) + (distZ * distZ);
    }

    @Override
    public double getLengthX() {

        return Math.abs(getRefHead().getX() - getRefBase().getX());
    }

    @Override
    public double getLengthY() {

        return Math.abs(getRefHead().getY() - getRefBase().getY());
    }

    @Override
    public double getLengthZ() {

        return Math.abs(getRefHead().getZ() - getRefBase().getZ());
    }

    @Override
    public double getMagnitude() {

        return Math.sqrt(getMagnitudeP2());
    }

    @Override
    public FVector setMagnitude(double magnitude) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().setMagnitude(magnitude);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector normalize() {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().normalize();
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector reflectBase() {

        getRefBase().reflect(getRefHead());

        return this;
    }

    @Override
    public FVector reflectHead() {

        getRefHead().reflect(getRefBase());

        return this;
    }

    @Override
    public FVector reflectThroughCenter() {

        getRefBase().reflect(0, 0, 0);
        getRefHead().reflect(0, 0, 0);

        return this;
    }

    @Override
    public FVector reflect(double x, double y, double z) {

        getRefBase().reflect(x, y, z);
        getRefHead().reflect(x, y, z);

        return this;
    }

    @Override
    public FVector reflect(FPoint arg) {

        getRefBase().reflect(arg);
        getRefHead().reflect(arg);

        return this;
    }

    @Override
    public FVector reflect(FPos3D arg) {

        return reflect(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector swapBaseWithHead() {
        double memoOHX = getRefHead().getX();
        double memoOHY = getRefHead().getY();
        double memoOHZ = getRefHead().getZ();

        getRefHead().reflect(getRefBase());
        moveBase(memoOHX, memoOHY, memoOHZ);

        return this;
    }

    @Override
    public FVector moveBase(double bX, double bY, double bZ) {
        double distX = getRefBase().getX() - bX;
        double distY = getRefBase().getY() - bY;
        double distZ = getRefBase().getZ() - bZ;

        getRefBase().subXYZ(distX, distY, distZ);
        getRefHead().subXYZ(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveBase(FPoint base) {
        double distX = getRefBase().getX() - base.getX();
        double distY = getRefBase().getY() - base.getY();
        double distZ = getRefBase().getZ() - base.getZ();

        getRefBase().subXYZ(distX, distY, distZ);
        getRefHead().subXYZ(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveBase(FPos3D base) {

        return moveBase(base.getD0(), base.getD1(), base.getD2());
    }

    @Override
    public FVector moveBaseToCenter() {

        return moveBase(0, 0, 0);
    }

    @Override
    public FVector moveHead(double hX, double hY, double hZ) {
        double distX = getRefHead().getX() - hX;
        double distY = getRefHead().getY() - hY;
        double distZ = getRefHead().getZ() - hZ;

        getRefBase().subXYZ(distX, distY, distZ);
        getRefHead().subXYZ(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveHead(FPoint head) {
        double distX = getRefHead().getX() - head.getX();
        double distY = getRefHead().getY() - head.getY();
        double distZ = getRefHead().getZ() - head.getZ();

        getRefBase().subXYZ(distX, distY, distZ);
        getRefHead().subXYZ(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveHead(FPos3D head) {

        return moveHead(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector moveHeadToCenter() {

        return moveHead(0, 0, 0);
    }

    @Override
    public FVector shiftForward(double distance) {

        if (distance < 0) {
            return shiftBackward(-distance);
        }

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        double memoOMag = getMagnitude();

        setMagnitude(distance);
        moveBase(getRefHead());
        setMagnitude(memoOMag);

        return this;
    }

    @Override
    public FVector shiftBackward(double distance) {

        if (distance < 0) {
            return shiftForward(-distance);
        }

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        double memoOMag = getMagnitude();

        setMagnitude(distance);
        reflectHead();
        moveBase(getRefHead());
        reflectHead();
        setMagnitude(memoOMag);

        return this;
    }

    @Override
    public double getDotProduct(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double distOX = getHeadX() - getBaseX();
        double distOY = getHeadY() - getBaseY();
        double distOZ = getHeadZ() - getBaseZ();
        double distAX = hX - bX;
        double distAY = hY - bY;
        double distAZ = hZ - bZ;

        return (distOX * distAX) + (distOY * distAY) + (distOZ * distAZ);
    }

    @Override
    public double getDotProduct(FPoint base, FPoint head) {

        return getDotProduct(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public double getDotProduct(FPos3D base, FPos3D head) {

        return getDotProduct(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public double getDotProduct(FVector arg) {

        return getDotProduct(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public double getDotProduct(FPairPos3D arg) {

        return getDotProduct(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public double getDotProductBaseCommon(double hX, double hY, double hZ) {

        return getDotProduct(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public double getDotProductBaseCommon(FPoint head) {

        return getDotProduct(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public double getDotProductBaseCommon(FPos3D head) {

        return getDotProduct(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public double getDotProductBaseZero(double hX, double hY, double hZ) {

        return getDotProduct(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public double getDotProductBaseZero(FPoint head) {

        return getDotProductBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public double getDotProductBaseZero(FPos3D head) {

        return getDotProductBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setCrossProduct(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = hX - bX;
        double zeroAY = hY - bY;
        double zeroAZ = hZ - bZ;

        moveBaseToCenter();
        getRefHead().setCrossProduct(zeroAX, zeroAY, zeroAZ);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector setCrossProduct(FPoint base, FPoint head) {

        return setCrossProduct(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setCrossProduct(FPos3D base, FPos3D head) {

        return setCrossProduct(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setCrossProduct(FVector arg) {

        return setCrossProduct(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public FVector setCrossProduct(FPairPos3D arg) {

        return setCrossProduct(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector setCrossProductBaseCommon(double hX, double hY, double hZ) {

        return setCrossProduct(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector setCrossProductBaseCommon(FPoint head) {

        return setCrossProduct(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setCrossProductBaseCommon(FPos3D head) {

        return setCrossProduct(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setCrossProductBaseZero(double hX, double hY, double hZ) {

        return setCrossProduct(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setCrossProductBaseZero(FPoint head) {

        return setCrossProductBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setCrossProductBaseZero(FPos3D head) {

        return setCrossProductBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isParallel(bX, bY, bZ, hX, hY, hZ) || isAntiParallel(bX, bY, bZ, hX, hY, hZ);
    }

    @Override
    public boolean isCollinear(FPoint base, FPoint head) {

        return isCollinear(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isCollinear(FPos3D base, FPos3D head) {

        return isCollinear(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isCollinear(FVector arg) {

        return isCollinear(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public boolean isCollinear(FPairPos3D arg) {

        return isCollinear(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public boolean isCollinearBaseCommon(double hX, double hY, double hZ) {

        return isCollinear(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public boolean isCollinearBaseCommon(FPoint head) {

        return isCollinear(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isCollinearBaseCommon(FPos3D head) {

        return isCollinear(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isCollinearBaseZero(double hX, double hY, double hZ) {

        return isCollinear(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isCollinearBaseZero(FPoint head) {

        return isCollinearBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isCollinearBaseZero(FPos3D head) {

        return isCollinearBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (getAngle(bX, bY, bZ, hX, hY, hZ) < Math.PI / 2) {
            return setParallel(bX, bY, bZ, hX, hY, hZ);
        }

        return setAntiParallel(bX, bY, bZ, hX, hY, hZ);
    }

    @Override
    public FVector setCollinear(FPoint base, FPoint head) {

        return setCollinear(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setCollinear(FPos3D base, FPos3D head) {

        return setCollinear(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setCollinear(FVector arg) {

        return setCollinear(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public FVector setCollinear(FPairPos3D arg) {

        return setCollinear(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector setCollinearBaseCommon(double hX, double hY, double hZ) {

        return setCollinear(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector setCollinearBaseCommon(FPoint head) {

        return setCollinear(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setCollinearBaseCommon(FPos3D head) {

        return setCollinear(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setCollinearBaseZero(double hX, double hY, double hZ) {

        return setCollinear(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setCollinearBaseZero(FPoint head) {

        return setCollinearBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setCollinearBaseZero(FPos3D head) {

        return setCollinearBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isParallel(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double magO = getMagnitude();
        double headOX = (getHeadX() - getBaseX()) / magO;
        double headOY = (getHeadY() - getBaseY()) / magO;
        double headOZ = (getHeadZ() - getBaseZ()) / magO;

        double magA = getMagnitude(bX, bY, bZ, hX, hY, hZ);
        double headAX = (hX - bX) / magA;
        double headAY = (hY - bY) / magA;
        double headAZ = (hZ - bZ) / magA;

        double distX = Math.abs(headOX - headAX);
        double distY = Math.abs(headOY - headAY);
        double distZ = Math.abs(headOZ - headAZ);

        return distX < EPSILON && distY < EPSILON && distZ < EPSILON;
    }

    @Override
    public boolean isParallel(FPoint base, FPoint head) {

        return isParallel(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isParallel(FPos3D base, FPos3D head) {

        return isParallel(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isParallel(FVector arg) {

        return isParallel(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public boolean isParallel(FPairPos3D arg) {

        return isParallel(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public boolean isParallelBaseCommon(double hX, double hY, double hZ) {

        return isParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public boolean isParallelBaseCommon(FPoint head) {

        return isParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isParallelBaseCommon(FPos3D head) {

        return isParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isParallelBaseZero(double hX, double hY, double hZ) {

        return isParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isParallelBaseZero(FPoint head) {

        return isParallel(0, 0, 0, head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isParallelBaseZero(FPos3D head) {

        return isParallel(0, 0, 0, head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setParallel(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double memoMagO = getMagnitude();

        double headAX = hX - bX + getBaseX();
        double headAY = hY - bY + getBaseY();
        double headAZ = hZ - bZ + getBaseZ();

        getRefHead().set(headAX, headAY, headAZ);
        setMagnitude(memoMagO);

        return this;
    }

    @Override
    public FVector setParallel(FPoint base, FPoint head) {

        return setParallel(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setParallel(FPos3D base, FPos3D head) {

        return setParallel(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setParallel(FVector arg) {

       return setParallel(
               arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
               arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
       );
    }

    @Override
    public FVector setParallel(FPairPos3D arg) {

        return setParallel(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector setParallelBaseCommon(double hX, double hY, double hZ) {

        return setParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector setParallelBaseCommon(FPoint head) {

        return setParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setParallelBaseCommon(FPos3D head) {

        return setParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setParallelBaseZero(double hX, double hY, double hZ) {

        return setParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setParallelBaseZero(FPoint head) {

        return setParallelBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setParallelBaseZero(FPos3D head) {

        return setParallelBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double magO = getMagnitude();
        double headOX = (getHeadX() - getBaseX()) / magO;
        double headOY = (getHeadY() - getBaseY()) / magO;
        double headOZ = (getHeadZ() - getBaseZ()) / magO;

        double magA = getMagnitude(bX, bY, bZ, hX, hY, hZ);
        double headAX = (hX - bX) / magA;
        double headAY = (hY - bY) / magA;
        double headAZ = (hZ - bZ) / magA;

        double distX = Math.abs(headOX + headAX);
        double distY = Math.abs(headOY + headAY);
        double distZ = Math.abs(headOZ + headAZ);

        return distX < EPSILON && distY < EPSILON && distZ < EPSILON;
    }

    @Override
    public boolean isAntiParallel(FPoint base, FPoint head) {

        return isAntiParallel(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isAntiParallel(FPos3D base, FPos3D head) {

        return isAntiParallel(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isAntiParallel(FVector arg) {

        return isAntiParallel(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public boolean isAntiParallel(FPairPos3D arg) {

        return isAntiParallel(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public boolean isAntiParallelBaseCommon(double hX, double hY, double hZ) {

        return isAntiParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public boolean isAntiParallelBaseCommon(FPoint head) {

        return isAntiParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isAntiParallelBaseCommon(FPos3D head) {

        return isAntiParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isAntiParallelBaseZero(double hX, double hY, double hZ) {

        return isAntiParallel(0 ,0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isAntiParallelBaseZero(FPoint head) {

        return isAntiParallelBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isAntiParallelBaseZero(FPos3D head) {

        return isAntiParallelBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return setParallel(bX, bY, bZ, hX, hY, hZ).reflectHead();
    }

    @Override
    public FVector setAntiParallel(FPoint base, FPoint head) {

        return setAntiParallel(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setAntiParallel(FPos3D base, FPos3D head) {

        return setAntiParallel(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setAntiParallel(FVector arg) {

        return setAntiParallel(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public FVector setAntiParallel(FPairPos3D arg) {

        return setAntiParallel(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector setAntiParallelBaseCommon(double hX, double hY, double hZ) {

        return setAntiParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector setAntiParallelBaseCommon(FPoint head) {

        return setAntiParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setAntiParallelBaseCommon(FPos3D head) {

        return setAntiParallel(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setAntiParallelBaseZero(double hX, double hY, double hZ) {

        return setAntiParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setAntiParallelBaseZero(FPoint head) {

        return setAntiParallelBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setAntiParallelBaseZero(FPos3D head) {

        return setAntiParallelBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        boolean dotProduct = Math.abs(getDotProduct(bX, bY, bZ, hX, hY, hZ)) < EPSILON;
        boolean angle = Math.abs((Math.PI * 0.5) - getAngle(bX, bY, bZ, hX, hY, hZ)) < EPSILON;

        return  dotProduct || angle;
    }

    @Override
    public boolean isOrthogonal(FPoint base, FPoint head) {

        return isOrthogonal(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isOrthogonal(FPos3D base, FPos3D head) {

        return isOrthogonal(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isOrthogonal(FVector arg) {

        return isOrthogonal(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public boolean isOrthogonal(FPairPos3D arg) {

        return isOrthogonal(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public boolean isOrthogonalBaseCommon(double hX, double hY, double hZ) {

        return isOrthogonal(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public boolean isOrthogonalBaseCommon(FPoint head) {

        return isOrthogonal(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public boolean isOrthogonalBaseCommon(FPos3D head) {

        return isOrthogonal(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public boolean isOrthogonalBaseZero(double hX, double hY, double hZ) {

        return isOrthogonal(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isOrthogonalBaseZero(FPoint head) {

        return isOrthogonalBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isOrthogonalBaseZero(FPos3D head) {

        return isOrthogonalBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isParallel(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalStateException("FVectors are parallel");
        }

        if (isAntiParallel(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalStateException("FVectors are anti-parallel");
        }

        double memoMagO = getMagnitude();
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = hX - bX;
        double zeroAY = hY - bY;
        double zeroAZ = hZ - bZ;

        moveBaseToCenter();
        getRefHead().setCrossProduct(zeroAX, zeroAY, zeroAZ);
        getRefHead().setCrossProduct(-zeroAX, -zeroAY, -zeroAZ);
        moveBase(memoOBX, memoOBY, memoOBZ);
        setMagnitude(memoMagO);

        return this;
    }

    @Override
    public FVector setOrthogonal(FPoint base, FPoint head) {

        return setOrthogonal(
                base.getX(), base.getY(), base.getZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setOrthogonal(FPos3D base, FPos3D head) {

        return setOrthogonal(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setOrthogonal(FVector arg) {

        return setOrthogonal(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public FVector setOrthogonal(FPairPos3D arg) {

        return setOrthogonal(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public FVector setOrthogonalBaseCommon(double hX, double hY, double hZ) {

        return setOrthogonal(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public FVector setOrthogonalBaseCommon(FPoint head) {

        return setOrthogonal(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public FVector setOrthogonalBaseCommon(FPos3D head) {

        return setOrthogonal(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public FVector setOrthogonalBaseZero(double hX, double hY, double hZ) {

        return setOrthogonal(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setOrthogonalBaseZero(FPoint head) {

        return setOrthogonalBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setOrthogonalBaseZero(FPos3D head) {

        return setOrthogonalBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setSphericalCoordinates(double inclination, double azimuth) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().setSphericalCoordinates(inclination, azimuth);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public double getInclination() {
        double memoOHX = getHeadX();
        double memoOHY = getHeadY();
        double memoOHZ = getHeadZ();

        double results = getRefHead().subXYZ(getRefBase()).getInclination();

        getRefHead().set(memoOHX, memoOHY, memoOHZ);

        return results;
    }

    @Override
    public FVector setInclination(double inclination) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().setInclination(inclination);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public double getAzimuth() {
        double memoOHX = getHeadX();
        double memoOHY = getHeadY();
        double memoOHZ = getHeadZ();

        double results = getRefHead().subXYZ(getRefBase()).getAzimuth();

        getRefHead().set(memoOHX, memoOHY, memoOHZ);

        return results;
    }

    @Override
    public FVector setAzimuth(double azimuth) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().setAzimuth(azimuth);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public double getAngle(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double memoOHX = getHeadX();
        double memoOHY = getHeadY();
        double memoOHZ = getHeadZ();
        double zeroAX = hX - bX;
        double zeroAY = hY - bY;
        double zeroAZ = hZ - bZ;

        getRefHead().subXYZ(getRefBase());

        double results = getRefHead().getAngle(zeroAX, zeroAY, zeroAZ);

        getRefHead().set(memoOHX, memoOHY, memoOHZ);

        return results;
    }

    @Override
    public double getAngle(FPoint base, FPoint head) {

        return getAngle(
                base.getX(), base.getY(), getBaseZ(),
                head.getY(), head.getY(), head.getZ()
        );
    }

    @Override
    public double getAngle(FPos3D base, FPos3D head) {

        return getAngle(
                base.getD0(), base.getD1(), base.getD2(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public double getAngle(FVector arg) {

        return getAngle(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ()
        );
    }

    @Override
    public double getAngle(FPairPos3D arg) {

        return getAngle(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2()
        );
    }

    @Override
    public double getAngleBaseCommon(double hX, double hY, double hZ) {

        return getAngle(
                getBaseX(), getBaseY(), getBaseZ(),
                hX, hY, hZ
        );
    }

    @Override
    public double getAngleBaseCommon(FPoint head) {

        return getAngle(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getX(), head.getY(), head.getZ()
        );
    }

    @Override
    public double getAngleBaseCommon(FPos3D head) {

        return getAngle(
                getBaseX(), getBaseY(), getBaseZ(),
                head.getD0(), head.getD1(), head.getD2()
        );
    }

    @Override
    public double getAngleBaseZero(double hX, double hY, double hZ) {

        return getAngle(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public double getAngleBaseZero(FPoint head) {

        return getAngleBaseZero(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public double getAngleBaseZero(FPos3D head) {

        return getAngleBaseZero(head.getD0(), head.getD1(), head.getD2());
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector apply(Consumer<FVector> action) {

        action.accept(this);

        return this;
    }

    @Override
    public double toDouble(Function<FVector, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FVector, Boolean> action) {

        return action.apply(this);
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isNearZeroLength(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        boolean posX = Math.abs(bX - hX) < EPSILON;
        boolean posY = Math.abs(bY - hY) < EPSILON;
        boolean posZ = Math.abs(bZ - hZ) < EPSILON;

        return posX && posY && posZ;
    }

    private double getMagnitude(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double distX = hX - bX;
        double distY = hY - bY;
        double distZ = hZ - bZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }

    // -------------------------------------------------------------------------------------------------

    private FVector supplyFVector() {

        return factorySelf.getFVector();
    }
}
