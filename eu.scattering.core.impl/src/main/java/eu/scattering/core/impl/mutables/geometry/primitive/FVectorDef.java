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
    public boolean isExactSimple(double hX, double hY, double hZ) {

        return isExact(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isExactSimple(FPoint head) {

        return isExactSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isExactSimple(FPos3D head) {

        return isExactSimple(head.getD0(), head.getD1(), head.getD2());
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
    public boolean isSimilarSimple(double hX, double hY, double hZ) {

        return isSimilar(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isSimilarSimple(FPoint head) {

        return isSimilarSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isSimilarSimple(FPos3D head) {

        return isSimilarSimple(head.getD0(), head.getD1(), head.getD2());
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
    public FVector add(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().add(hX - bX, hY - bY, hZ - bZ);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
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
    public FVector addSimple(double hX, double hY, double hZ) {

        return add(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector addSimple(FPoint head) {

        return addSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector addSimple(FPos3D head) {

        return addSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector sub(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();

        moveBaseToCenter();
        getRefHead().sub(hX - bX, hY - bY, hZ - bZ);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
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
    public FVector subSimple(double hX, double hY, double hZ) {

        return sub(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector subSimple(FPoint head) {

        return subSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector subSimple(FPos3D head) {

        return subSimple(head.getD0(), head.getD1(), head.getD2());
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
    public double getDotProductSimple(double hX, double hY, double hZ) {

        return getDotProduct(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public double getDotProductSimple(FPoint head) {

        return getDotProductSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public double getDotProductSimple(FPos3D head) {

        return getDotProductSimple(head.getD0(), head.getD1(), head.getD2());
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
    public FVector setCrossProductSimple(double hX, double hY, double hZ) {

        return setCrossProduct(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setCrossProductSimple(FPoint head) {

        return setCrossProductSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setCrossProductSimple(FPos3D head) {

        return setCrossProductSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return isParallel(bX, bY, bZ, hX, hY, hZ) || isAntiParallel(bX, bY, bZ, hX, hY, hZ);
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
    public boolean isCollinearSimple(double hX, double hY, double hZ) {

        return isCollinear(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isCollinearSimple(FPoint head) {

        return isCollinearSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isCollinearSimple(FPos3D head) {

        return isCollinearSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setCollinear(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (getAngle(bX, bY, bZ, hX, hY, hZ) < Math.PI / 2) {
            return setParallel(bX, bY, bZ, hX, hY, hZ);
        }

        return setAntiParallel(bX, bY, bZ, hX, hY, hZ);
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
    public FVector setCollinearSimple(double hX, double hY, double hZ) {

        return setCollinear(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setCollinearSimple(FPoint head) {

        return setCollinearSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setCollinearSimple(FPos3D head) {

        return setCollinearSimple(head.getD0(), head.getD1(), head.getD2());
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
    public boolean isParallelSimple(double hX, double hY, double hZ) {

        return isParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isParallelSimple(FPoint head) {

        return isParallel(0, 0, 0, head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isParallelSimple(FPos3D head) {

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
    public FVector setParallelSimple(double hX, double hY, double hZ) {

        return setParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setParallelSimple(FPoint head) {

        return setParallelSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setParallelSimple(FPos3D head) {

        return setParallelSimple(head.getD0(), head.getD1(), head.getD2());
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

        return distX < epsilon && distY < epsilon && distZ < epsilon;
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
    public boolean isAntiParallelSimple(double hX, double hY, double hZ) {

        return isAntiParallel(0 ,0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isAntiParallelSimple(FPoint head) {

        return isAntiParallelSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isAntiParallelSimple(FPos3D head) {

        return isAntiParallelSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setAntiParallel(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return setParallel(bX, bY, bZ, hX, hY, hZ).reflectHead();
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
    public FVector setAntiParallelSimple(double hX, double hY, double hZ) {

        return setAntiParallel(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setAntiParallelSimple(FPoint head) {

        return setAntiParallelSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setAntiParallelSimple(FPos3D head) {

        return setAntiParallelSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public boolean isOrthogonal(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        boolean dotProduct = Math.abs(getDotProduct(bX, bY, bZ, hX, hY, hZ)) < epsilon;
        boolean angle = Math.abs((Math.PI * 0.5) - getAngle(bX, bY, bZ, hX, hY, hZ)) < epsilon;

        return  dotProduct || angle;
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
    public boolean isOrthogonalSimple(double hX, double hY, double hZ) {

        return isOrthogonal(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public boolean isOrthogonalSimple(FPoint head) {

        return isOrthogonalSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public boolean isOrthogonalSimple(FPos3D head) {

        return isOrthogonalSimple(head.getD0(), head.getD1(), head.getD2());
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
    public FVector setOrthogonalSimple(double hX, double hY, double hZ) {

        return setOrthogonal(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public FVector setOrthogonalSimple(FPoint head) {

        return setOrthogonalSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public FVector setOrthogonalSimple(FPos3D head) {

        return setOrthogonalSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector rotateAround(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        sub(bX, bY, bZ);

        getRefHead().rotateAround(hX - bX, hY - bY, hZ - bZ, angle);
        getRefBase().rotateAround(hX - bX, hY - bY, hZ - bZ, angle);

        add(bX, bY, bZ);

        return this;
    }

    @Override
    public FVector rotateAround(FVector arg, double angle) {

        return rotateAround(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector rotateAround(FPairPos3D arg, double angle) {

        return rotateAround(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector rotateAroundSimple(double hX, double hY, double hZ, double angle) {

        return rotateAround(0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector rotateAroundSimple(FPoint head, double angle) {

        return rotateAroundSimple(head.getX(), head.getY(), head.getZ(), angle);
    }

    @Override
    public FVector rotateAroundSimple(FPos3D head, double angle) {

        return rotateAroundSimple(head.getD0(), head.getD1(), head.getD2(), angle);
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

        getRefHead().sub(getRefBase());

        double results = getRefHead().getAngle(zeroAX, zeroAY, zeroAZ);

        getRefHead().set(memoOHX, memoOHY, memoOHZ);

        return results;
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
    public double getAngleSimple(double hX, double hY, double hZ) {

        return getAngle(0, 0, 0, hX, hY, hZ);
    }

    @Override
    public double getAngleSimple(FPoint head) {

        return getAngleSimple(head.getX(), head.getY(), head.getZ());
    }

    @Override
    public double getAngleSimple(FPos3D head) {

        return getAngleSimple(head.getD0(), head.getD1(), head.getD2());
    }

    @Override
    public FVector setAngle(double bX, double bY, double bZ, double hX, double hY, double hZ, double angle) {

        if (isNearZeroLength()) {
            throw new IllegalStateException("The direction of the FVector is not defined");
        }

        if (isNearZeroLength(bX, bY, bZ, hX, hY, hZ)) {
            throw new IllegalArgumentException("The direction of the argument FVector is not defined");
        }

        double memoOBX = getBaseX();
        double memoOBY = getBaseY();
        double memoOBZ = getBaseZ();
        double zeroAX = hX - bX;
        double zeroAY = hY - bY;
        double zeroAZ = hZ - bZ;

        moveBaseToCenter();
        getRefHead().setAngle(zeroAX, zeroAY, zeroAZ, angle);
        moveBase(memoOBX, memoOBY, memoOBZ);

        return this;
    }

    @Override
    public FVector setAngle(FVector arg, double angle) {

        return setAngle(
                arg.getBaseX(), arg.getBaseY(), arg.getBaseZ(),
                arg.getHeadX(), arg.getHeadY(), arg.getHeadZ(),
                angle
        );
    }

    @Override
    public FVector setAngle(FPairPos3D arg, double angle) {

        return setAngle(
                arg.getPosA().getD0(), arg.getPosA().getD1(), arg.getPosA().getD2(),
                arg.getPosB().getD0(), arg.getPosB().getD1(), arg.getPosB().getD2(),
                angle
        );
    }

    @Override
    public FVector setAngleSimple(double hX, double hY, double hZ, double angle) {

        return setAngle(0, 0, 0, hX, hY, hZ, angle);
    }

    @Override
    public FVector setAngleSimple(FPoint head, double angle) {

        return setAngleSimple(head.getX(), head.getY(), head.getZ(), angle);
    }

    @Override
    public FVector setAngleSimple(FPos3D head, double angle) {

        return setAngleSimple(head.getD0(), head.getD1(), head.getD2(), angle);
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

    private double getMagnitude(double bX, double bY, double bZ, double hX, double hY, double hZ) {
        double distX = hX - bX;
        double distY = hY - bY;
        double distZ = hZ - bZ;

        return Math.sqrt((distX * distX) + (distY * distY) + (distZ * distZ));
    }
}
