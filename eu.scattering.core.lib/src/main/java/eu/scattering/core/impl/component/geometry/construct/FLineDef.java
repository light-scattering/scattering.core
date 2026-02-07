package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.line.FLineHelper;
import eu.scattering.core.design.storage.transfer.pair.variants.FPairPos3D;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import org.json.JSONObject;

import java.util.Optional;

public class FLineDef extends ConstructPresetDef<FLine> implements FLine {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "line";
    private static final String JSON_VAL = "val";

    private final ConstructFactory factory;
    private final FLineHelper helper;
    private FVector origin;

    private FLineDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.helper = factory.getFLineHelper();
        this.origin = origin;
    }

    public static FLine create(ConstructFactory factory, FVector origin) {

        return new FLineDef(factory, origin);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FLine setRefOrigin(FVector refOrigin) {

        if (refOrigin == null) {
            throw new NullPointerException("The reference FVector cannot be null");
        }

        origin = refOrigin;

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FLine set(FPairPos3D position) {

        getRefOrigin().set(position);

        return this;
    }

    @Override
    public FLine set(Construct<?> arg) {

        getRefOrigin().set(arg.getRefOrigin());

        return this;
    }

    @Override
    public FLine set(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = supplyFVector().set(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    @Override
    public FLine applyStateTo(Construct<?> in) {

        getRefOrigin().applyStateTo(in.getRefOrigin());

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FLine self() {

        return this;
    }

    @Override
    public FLine copy() {
        FLine element = supplyFLine();

        element.getRefOrigin().set(getRefOrigin().copy());

        return element;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FLine) {
            return isExact((FLine) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FLine) {
            return isSimilar((FLine) arg);
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
    public FPos3D project(double x, double y, double z) {

        return this.helper.project(getRefOrigin(), x, y, z);
    }

    @Override
    public boolean project(FPoint in) {

        this.helper.project(getRefOrigin(), in);

        return true;
    }

    @Override
    public FPos3D project(FPos3D arg) {

        return this.helper.project(getRefOrigin(), arg);
    }

    @Override
    public boolean project(Geometry in) {

        this.helper.project(getRefOrigin(), in);

        return true;
    }

    @Override
    public FPos3D reflect(double x, double y, double z) {

        return this.helper.reflect(getRefOrigin(), x, y, z);
    }

    @Override
    public boolean reflect(FPoint in) {

        this.helper.reflect(getRefOrigin(), in);

        return true;
    }

    @Override
    public FPos3D reflect(FPos3D arg) {

        return this.helper.reflect(getRefOrigin(), arg);
    }

    @Override
    public boolean reflect(Geometry in) {

        this.helper.reflect(getRefOrigin(), in);

        return true;
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

        this.helper.setDistance(getRefOrigin(), in, distance);

        return true;
    }

    @Override
    public boolean setDistance(Geometry in, double distance) {

        this.helper.setDistance(getRefOrigin(), in, distance);

        return true;
    }

    @Override
    public boolean isSameLine(FLine arg) {

        return this.helper.isSameLine(getRefOrigin(), arg.getRefOrigin());
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {

        return this.helper.getFPointAtX(getRefOrigin(), x);
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {

        return this.helper.getFPointAtY(getRefOrigin(), y);
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {

        return this.helper.getFPointAtZ(getRefOrigin(), z);
    }

    @Override
    public Optional<FPoint> getFPointAtIntersection(FLine arg) {

        return this.helper.getFPointAtIntersection(getRefOrigin(), arg.getRefOrigin());
    }

    // -------------------------------------------------------------------------------------------------

    private FVector supplyFVector() {

        return factory.getFVector();
    }

    private FLine supplyFLine() {

        return factory.getFLine();
    }
}
