package eu.scattering.core.impl.mutables.geometry.primitive;

import eu.scattering.core.design.mutables.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutables.geometry.primitive.vector.FVector;
import eu.scattering.core.impl.mutables.geometry.primitive.support.PrimitivePresetDef;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.containers.grid.FMatrix3x3D.FMatrix3x3D;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

import static eu.scattering.core.impl.configurations.NameConfigDef.JSON_TYPE;

public class FVectorDef extends PrimitivePresetDef<FVector> implements FVector {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "vector";
    private static final String JSON_VAL = "val";

    private static double epsilon = 0;

    public static void initialize(double epsilon) {

        FVectorDef.epsilon = epsilon;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FPoint oBase;
    private FPoint oHead;

    private FVectorDef() {}

    public static FVector create( FPoint refBase, FPoint refHead) {

        if (refBase == null) {
            throw new NullPointerException("The base FPoint cannot be null");
        }

        if (refHead == null) {
            throw new NullPointerException("The head FPoint cannot be null");
        }

        var fVector = new FVectorDef();

        fVector.setRefBase(refBase);
        fVector.setRefHead(refHead);

        return fVector;
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
    public FVector applyStateFrom(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        JSONArray structure = json.getJSONArray(JSON_VAL);
        FPoint base = getRefBase().applyStateFrom(structure.getJSONObject(0));
        FPoint head = getRefHead().applyStateFrom(structure.getJSONObject(1));

        return set(base, head);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getRefBase().isExact(bX, bY, bZ) && getRefHead().isExact(hX, hY, hZ);
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
    public boolean isSimilar(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getRefBase().isSimilar(bX, bY, bZ) && getRefHead().isSimilar(hX, hY, hZ);
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
    public FVector self() {

        return this;
    }

    @Override
    public FVector copy() {

        return create(getRefBase().copy(), getRefHead().copy());
    }

    @Override
    public FVector copyZero() {

        return create(getRefBase().copyZero(), getRefHead().copyZero());
    }

    @Override
    public FPairPos3D toFPairPos3D() {

        FPos3D posA = getRefBase().toFPos3D();
        FPos3D posB = getRefHead().toFPos3D();

        return factory.getFPairPos3D(posA, posB);
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
    public int hashCode() {

        return Objects.hash(getBaseX(), getBaseY(), getBaseZ(), getHeadX(), getHeadY(), getHeadZ());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FVector) {
            FVector ref = (FVector) object;

            return getRefBase().equals(ref.getRefBase()) && getRefHead().equals(ref.getRefHead());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector add(FVector arg) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        FPoint aBase = arg.getRefBase();
        FPoint aHead = arg.getRefHead();

        moveBaseToCenter();
        getRefHead().add(
                aHead.getX() - aBase.getX(),
                aHead.getY() - aBase.getY(),
                aHead.getZ() - aBase.getZ());
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector add(FPairPos3D arg) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        FPos3D aBase = arg.getPosA();
        FPos3D aHead = arg.getPosB();

        moveBaseToCenter();
        getRefHead().add(
                aHead.getD0() - aBase.getD0(),
                aHead.getD1() - aBase.getD1(),
                aHead.getD2() - aBase.getD2());
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector sub(FVector arg) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        FPoint aBase = arg.getRefBase();
        FPoint aHead = arg.getRefHead();

        moveBaseToCenter();
        getRefHead().sub(
                aHead.getX() - aBase.getX(),
                aHead.getY() - aBase.getY(),
                aHead.getZ() - aBase.getZ());
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector sub(FPairPos3D arg) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        FPos3D aBase = arg.getPosA();
        FPos3D aHead = arg.getPosB();

        moveBaseToCenter();
        getRefHead().sub(
                aHead.getD0() - aBase.getD0(),
                aHead.getD1() - aBase.getD1(),
                aHead.getD2() - aBase.getD2());
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector add(FPos3D arg) {

        return add(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector sub(FPos3D arg) {

        return sub(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector mul(FPos3D arg) {

        return mul(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector div(FPos3D arg) {

        return div(arg.getD0(), arg.getD1(), arg.getD2());
    }

    @Override
    public FVector mul(FMatrix3x3D arg) {

        getRefBase().mul(arg);
        getRefHead().mul(arg);

        return this;
    }

    //--------------------------------------------------

    @Override
    public List<FPoint> disassemble() {
        List<FPoint> fPointList = new ArrayList<>();

        fPointList.add(getRefBase());
        fPointList.add(getRefHead());

        return fPointList;
    }

    @Override
    public boolean isZeroLength() {

        return getRefBase().equals(getRefHead());
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

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveBase(FPoint base) {
        double distX = getRefBase().getX() - base.getX();
        double distY = getRefBase().getY() - base.getY();
        double distZ = getRefBase().getZ() - base.getZ();

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

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

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

        return this;
    }

    @Override
    public FVector moveHead(FPoint head) {
        double distX = getRefHead().getX() - head.getX();
        double distY = getRefHead().getY() - head.getY();
        double distZ = getRefHead().getZ() - head.getZ();

        getRefBase().sub(distX, distY, distZ);
        getRefHead().sub(distX, distY, distZ);

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
    public double getDotProduct(FVector arg) {
        double distOX = getHeadX() - getBaseX();
        double distOY = getHeadY() - getBaseY();
        double distOZ = getHeadZ() - getBaseZ();
        double distAX = arg.getHeadX() - arg.getBaseX();
        double distAY = arg.getHeadY() - arg.getBaseY();
        double distAZ = arg.getHeadZ() - arg.getBaseZ();

        return (distOX * distAX) + (distOY * distAY) + (distOZ * distAZ);
    }

    @Override
    public FVector setCrossProduct(FVector arg) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = arg.getHeadX() - arg.getBaseX();
        double zeroAY = arg.getHeadY() - arg.getBaseY();
        double zeroAZ = arg.getHeadZ() - arg.getBaseZ();

        moveBaseToCenter();
        getRefHead().setCrossProduct(zeroAX, zeroAY, zeroAZ);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public boolean isCollinear(FVector arg) {

        return isParallel(arg) || isAntiParallel(arg);
    }

    @Override
    public boolean isCollinear(FPairPos3D arg) {

        return isParallel(arg) || isAntiParallel(arg);
    }

    @Override
    public FVector setCollinear(FVector arg) {

        if (getAngle(arg) < Math.PI / 2) {
            return setParallel(arg);
        }

        return setAntiParallel(arg);
    }

    @Override
    public FVector setCollinear(FPairPos3D arg) {

        if (getAngle(arg) < Math.PI / 2) {
            return setParallel(arg);
        }

        return setAntiParallel(arg);
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

        return distX < epsilon && distY < epsilon && distZ < epsilon;
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
    public boolean isParallel(double hX, double hY, double hZ) {

        return isParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isParallel(FPoint arg) {

        return isParallel(0, 0, 0, arg.getX(), arg.getY(), arg.getZ());
    }

    @Override
    public boolean isParallel(FPos3D arg) {

        return isParallel(0, 0, 0, arg.getD0(), arg.getD1(), arg.getD2());
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
    public FVector setParallel(double hX, double hY, double hZ) {

        return setParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setParallel(FPoint head) {

        return setParallel(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setParallel(FPos3D head) {

        return setParallel(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isAntiParallel(FVector arg) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (arg.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double magO = getMagnitude();
        double headOX = (getHeadX() - getBaseX()) / magO;
        double headOY = (getHeadY() - getBaseY()) / magO;
        double headOZ = (getHeadZ() - getBaseZ()) / magO;

        double magA = arg.getMagnitude();
        double headAX = (arg.getHeadX() - arg.getBaseX()) / magA;
        double headAY = (arg.getHeadY() - arg.getBaseY()) / magA;
        double headAZ = (arg.getHeadZ() - arg.getBaseZ()) / magA;

        double distX = Math.abs(headOX + headAX);
        double distY = Math.abs(headOY + headAY);
        double distZ = Math.abs(headOZ + headAZ);

        return distX < epsilon && distY < epsilon && distZ < epsilon;
    }

    @Override
    public boolean isAntiParallel(FPairPos3D arg) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the input FVector is not defined");
        }

        if (isNearZeroLength(arg)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double magO = getMagnitude();
        double headOX = (getHeadX() - getBaseX()) / magO;
        double headOY = (getHeadY() - getBaseY()) / magO;
        double headOZ = (getHeadZ() - getBaseZ()) / magO;

        double magA = getMagnitude(arg);
        double headAX = (arg.getPosB().getD0() - arg.getPosA().getD0()) / magA;
        double headAY = (arg.getPosB().getD1() - arg.getPosA().getD1()) / magA;
        double headAZ = (arg.getPosB().getD2() - arg.getPosA().getD2()) / magA;

        double distX = Math.abs(headOX + headAX);
        double distY = Math.abs(headOY + headAY);
        double distZ = Math.abs(headOZ + headAZ);

        return distX < epsilon && distY < epsilon && distZ < epsilon;
    }

    @Override
    public FVector setAntiParallel(FVector arg) {

       return setParallel(arg).reflectHead();
    }

    @Override
    public FVector setAntiParallel(FPairPos3D arg) {

        return setParallel(arg).reflectHead();
    }

    @Override
    public boolean isOrthogonal(FVector arg) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (arg.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        return (Math.abs(getDotProduct(arg)) < epsilon) || (Math.abs((Math.PI * 0.5) - getAngle(arg)) < epsilon);
    }

    @Override
    public FVector setOrthogonal(FVector arg) {

        if (isParallel(arg)) {
            throw new IllegalStateException("FVectors are parallel");
        }

        if (isAntiParallel(arg)) {
            throw new IllegalStateException("FVectors are anti-parallel");
        }

        double memoMagO = getMagnitude();
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = arg.getHeadX() - arg.getBaseX();
        double zeroAY = arg.getHeadY() - arg.getBaseY();
        double zeroAZ = arg.getHeadZ() - arg.getBaseZ();

        moveBaseToCenter();
        getRefHead().setCrossProduct(zeroAX, zeroAY, zeroAZ);
        getRefHead().setCrossProduct(-zeroAX, -zeroAY, -zeroAZ);
        moveBase(memoOBX, memoOBY, memoOBZ);
        setMagnitude(memoMagO);

        return this;
    }

    @Override
    public FVector rotateAround(FVector arg, double angle) {
        double memoABX = arg.getBaseX();
        double memoABY = arg.getBaseY();
        double memoABZ = arg.getBaseZ();
        double memoAHX = arg.getHeadX();
        double memoAHY = arg.getHeadY();
        double memoAHZ = arg.getHeadZ();

        sub(memoABX, memoABY, memoABZ);

        getRefHead().rotateAround(memoAHX - memoABX, memoAHY - memoABY, memoAHZ - memoABZ, angle);
        getRefBase().rotateAround(memoAHX - memoABX, memoAHY - memoABY, memoAHZ - memoABZ, angle);

        add(memoABX, memoABY, memoABZ);

        return this;
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

        double results = getRefHead().sub(getRefBase()).getInclination();

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

        double results = getRefHead().sub(getRefBase()).getAzimuth();

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
    public double getAngle(FVector arg) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (arg.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double memoOHX = getHeadX();
        double memoOHY = getHeadY();
        double memoOHZ = getHeadZ();
        double zeroAX = arg.getHeadX() - arg.getBaseX();
        double zeroAY = arg.getHeadY() - arg.getBaseY();
        double zeroAZ = arg.getHeadZ() - arg.getBaseZ();

        getRefHead().sub(getRefBase());

        double results = getRefHead().getAngle(zeroAX, zeroAY, zeroAZ);

        getRefHead().set(memoOHX, memoOHY, memoOHZ);

        return results;
    }

    @Override
    public double getAngle(FPairPos3D arg) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(arg)) {
            throw new IllegalArgumentException("The direction of the argument FPairPos3D is not defined");
        }

        double memoOHX = getHeadX();
        double memoOHY = getHeadY();
        double memoOHZ = getHeadZ();
        double zeroAX = arg.getPosB().getD0() - arg.getPosA().getD0();
        double zeroAY = arg.getPosB().getD1() - arg.getPosA().getD1();
        double zeroAZ = arg.getPosB().getD2() - arg.getPosA().getD2();

        getRefHead().sub(getRefBase());

        double results = getRefHead().getAngle(zeroAX, zeroAY, zeroAZ);

        getRefHead().set(memoOHX, memoOHY, memoOHZ);

        return results;
    }

    @Override
    public FVector setAngle(FVector arg, double angle) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (arg.isNearZeroLength()) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = arg.getHeadX() - arg.getBaseX();
        double zeroAY = arg.getHeadY() - arg.getBaseY();
        double zeroAZ = arg.getHeadZ() - arg.getBaseZ();

        moveBaseToCenter();
        getRefHead().setAngle(zeroAX, zeroAY, zeroAZ, angle);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector setAngle(FPairPos3D arg, double angle) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(arg)) {
            throw new IllegalArgumentException("The direction of the argument FPairPos3D is not defined");
        }

        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = arg.getPosB().getD0() - arg.getPosA().getD0();
        double zeroAY = arg.getPosB().getD1() - arg.getPosA().getD1();
        double zeroAZ = arg.getPosB().getD2() - arg.getPosA().getD2();

        moveBaseToCenter();
        getRefHead().setAngle(zeroAX, zeroAY, zeroAZ, angle);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FVector apply(Consumer<FVector> action) {

        action.accept(this);

        return this;
    }

    @Override
    public FVector applyWithFixedState(Consumer<FVector> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();
        double memoHX = this.getHeadX();
        double memoHY = this.getHeadY();
        double memoHZ = this.getHeadZ();

        action.accept(this);

        return this.set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);
    }

    @Override
    public FVector applyWithFixedMagnitude(Consumer<FVector> action) {
        double magnitude = getMagnitude();

        action.accept(this);

        return setMagnitude(magnitude);
    }

    @Override
    public FVector applyWithCenteredPosition(Consumer<FVector> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();

        moveBaseToCenter();

        action.accept(this);

        return moveBase(memoBX, memoBY, memoBZ);
    }

    @Override
    public double toDouble(Function<FVector, Double> action) {

        return action.apply(this);
    }

    @Override
    public boolean toBoolean(Function<FVector, Boolean> action) {

        return action.apply(this);
    }

    @Override
    public double toDoubleWithFixedState(Function<FVector, Double> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();
        double memoHX = this.getHeadX();
        double memoHY = this.getHeadY();
        double memoHZ = this.getHeadZ();

        double res = action.apply(this);

        set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);

        return res;
    }

    @Override
    public boolean toBooleanWithFixedState(Function<FVector, Boolean> action) {
        double memoBX = this.getBaseX();
        double memoBY = this.getBaseY();
        double memoBZ = this.getBaseZ();
        double memoHX = this.getHeadX();
        double memoHY = this.getHeadY();
        double memoHZ = this.getHeadZ();

        boolean res = action.apply(this);

        set(memoBX, memoBY, memoBZ, memoHX, memoHY, memoHZ);

        return res;
    }

    // -------------------------------------------------------------------------------------------------

    private boolean isNearZeroLength(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        boolean posX = Math.abs(bX - hX) < epsilon;
        boolean posY = Math.abs(bY - hY) < epsilon;
        boolean posZ = Math.abs(bZ - hZ) < epsilon;

        return posX && posY && posZ;
    }

    private boolean isNearZeroLength(FPairPos3D arg) {
        boolean posX = Math.abs(arg.getPosA().getD0() - arg.getPosB().getD0()) < epsilon;
        boolean posY = Math.abs(arg.getPosA().getD1() - arg.getPosB().getD1()) < epsilon;
        boolean posZ = Math.abs(arg.getPosA().getD2() - arg.getPosB().getD2()) < epsilon;

        return posX && posY && posZ;
    }

    private double getMagnitude(FPairPos3D arg) {
        double distX = arg.getPosB().getD0() - arg.getPosA().getD0();
        double distY = arg.getPosB().getD1() - arg.getPosA().getD1();
        double distZ = arg.getPosB().getD2() - arg.getPosA().getD2();

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }

    private double getMagnitude(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double distX = hX - bX;
        double distY = hY - bY;
        double distZ = hZ - bZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }
}
