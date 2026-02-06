package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.ray.FRayHelper;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import org.json.JSONObject;

public class FRayDef extends ConstructPresetDef<FRay> implements FRay {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "ray";
    private static final String JSON_VAL = "val";

    private final ConstructFactory factory;
    private final FRayHelper helper;
    private FVector origin;

    private FRayDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.helper = factory.getFRayHelper();
        this.origin = origin;
    }

    public static FRay create(ConstructFactory factory, FVector origin) {

        return new FRayDef(factory, origin);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FRay setRefOrigin(FVector refOrigin) {

        if (refOrigin == null) {
            throw new NullPointerException("The reference FVector cannot be null");
        }

        origin = refOrigin;

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRay set(FPairPos3D position) {

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FRay set(Construct<?> arg) {

        getRefOrigin().set(arg.getRefOrigin());

        return this;
    }

    @Override
    public FRay set(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = supplyFVector().set(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    @Override
    public FRay applyStateTo(Construct<?> in) {

        getRefOrigin().applyStateTo(in.getRefOrigin());

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FRay self() {

        return this;
    }

    @Override
    public FRay copy() {
        FRay element = supplyFRay();

        element.getRefOrigin().set(getRefOrigin().copy());

        return element;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FRay) {
            return isExact((FRay) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FRay) {
            return isSimilar((FRay) arg);
        }

        return false;
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPairPos3D toFPairPos3D() {

        return getRefOrigin().toFPairPos3D();
    }

    @Override
    public FVector toFVector(double length) {

        return getRefOrigin().copy().setMagnitude(length);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_VAL, getRefOrigin().toJSON());

        return json;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isPartOf(double x, double y, double z) {

        return this.helper.isPartOf(getRefOrigin(), x, y, z);
    }

    @Override
    public boolean isPartOf(double x, double y, double z, double epsilon) {

        return this.helper.isPartOf(getRefOrigin(), x, y, z, epsilon);
    }

    @Override
    public boolean isPartOf(FPoint arg) {

        return this.helper.isPartOf(getRefOrigin(), arg);
    }

    @Override
    public boolean isPartOf(FPoint arg, double epsilon) {

        return this.helper.isPartOf(getRefOrigin(), arg, epsilon);
    }

    @Override
    public boolean isPartOf(FPos3D arg) {

        return this.helper.isPartOf(getRefOrigin(), arg);
    }

    @Override
    public boolean isPartOf(FPos3D arg, double epsilon) {

        return this.helper.isPartOf(getRefOrigin(), arg, epsilon);
    }

    @Override
    public boolean isPartOf(Geometry arg) {

        return this.helper.isPartOf(getRefOrigin(), arg);
    }

    @Override
    public boolean isPartOf(Geometry arg, double epsilon) {

        return this.helper.isPartOf(getRefOrigin(), arg, epsilon);
    }

    @Override
    public boolean isProjectable(double x, double y, double z) {

        return this.helper.isProjectable(getRefOrigin(), x, y, z);
    }

    @Override
    public boolean isProjectable(FPoint arg) {

        return this.helper.isProjectable(getRefOrigin(), arg);
    }

    @Override
    public boolean isProjectable(FPos3D arg) {

        return this.helper.isProjectable(getRefOrigin(), arg);
    }

    @Override
    public FPos3D project(double x, double y, double z) {

        return this.helper.project(getRefOrigin(), x, y, z);
    }

    @Override
    public boolean project(FPoint in) {

        return this.helper.project(getRefOrigin(), in);
    }

    @Override
    public FPos3D project(FPos3D arg) {

        return this.helper.project(getRefOrigin(), arg);
    }

    @Override
    public boolean project(Geometry in) {

        return this.helper.project(getRefOrigin(), in);
    }

    @Override
    public FPos3D reflect(double x, double y, double z) {

        return this.helper.reflect(getRefOrigin(), x, y, z);
    }

    @Override
    public boolean reflect(FPoint in) {

        return this.helper.reflect(getRefOrigin(), in);
    }

    @Override
    public FPos3D reflect(FPos3D arg) {

        return this.helper.reflect(getRefOrigin(), arg);
    }

    @Override
    public boolean reflect(Geometry in) {

        return this.helper.reflect(getRefOrigin(), in);
    }

    @Override
    public double getDistance(double x, double y, double z) {

        return this.helper.getDistance(getRefOrigin(), x, y, z);
    }

    @Override
    public double getDistance(FPoint arg) {

       return this.helper.getDistance(getRefOrigin(), arg);
    }

    @Override
    public double getDistance(FPos3D arg) {

        return this.helper.getDistance(getRefOrigin(), arg);
    }

    @Override
    public FPos3D setDistance(double x, double y, double z, double distance) {

        return this.helper.setDistance(getRefOrigin(), x, y, z, distance);
    }

    @Override
    public FPos3D setDistance(FPos3D arg, double distance) {

        return this.helper.setDistance(getRefOrigin(), arg, distance);
    }

    @Override
    public boolean setDistance(FPoint in, double distance) {

        return this.helper.setDistance(getRefOrigin(), in, distance);
    }

    @Override
    public boolean setDistance(Geometry in, double distance) {

        return this.helper.setDistance(getRefOrigin(), in, distance);
    }

    @Override
    public FPos3D shiftForward(double x, double y, double z, double distance) {

        return this.helper.shiftForward(getRefOrigin(), x, y, z, distance);
    }

    @Override
    public FPos3D shiftForward(FPos3D arg, double distance) {

        return this.helper.shiftForward(getRefOrigin(), arg, distance);
    }

    @Override
    public void shiftForward(FPoint in, double distance) {

        this.helper.shiftForward(getRefOrigin(), in, distance);
    }

    @Override
    public void shiftForward(Geometry in, double distance) {

        this.helper.shiftForward(getRefOrigin(), in, distance);
    }

    @Override
    public FPos3D shiftBackward(double x, double y, double z, double distance) {

        return this.helper.shiftBackward(getRefOrigin(), x, y, z, distance);
    }

    @Override
    public FPos3D shiftBackward(FPos3D arg, double distance) {

        return this.helper.shiftBackward(getRefOrigin(), arg, distance);
    }

    @Override
    public void shiftBackward(FPoint in, double distance) {

        this.helper.shiftBackward(getRefOrigin(), in, distance);
    }

    @Override
    public void shiftBackward(Geometry in, double distance) {

        this.helper.shiftBackward(getRefOrigin(), in, distance);
    }

    @Override
    public FPoint getFPointAtLength(double length) {

        return this.helper.getFPointAtLength(getRefOrigin(), length);
    }

    @Override
    public FPos3D getFPos3DAtLength(double length) {

        return this.helper.getFPos3DAtLength(getRefOrigin(), length);
    }

    // -------------------------------------------------------------------------------------------------

    private FVector supplyFVector() {

        return factory.getFVector();
    }

    private FRay supplyFRay() {

        return factory.getFRay();
    }
}