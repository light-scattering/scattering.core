package eu.scattering.core.impl.component.geometry.construct;

import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.Construct;
import eu.scattering.core.design.component.geometry.construct.ConstructFactory;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.draft.FDraft;
import eu.scattering.core.design.component.geometry.construct.plane.FPlane;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.impl.component.geometry.construct.preset.ConstructPresetDef;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FDraftDef extends ConstructPresetDef<FDraft> implements FDraft {
    private static final String JSON_MAIN = "draft";
    private static final String JSON_VAL = "val";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ConstructFactory factory;

    private FVector origin;

    private FRay fRay;
    private FLine fLine;
    private FPlane fPlane;
    private FSegment fSegment;

    private FDraftDef(ConstructFactory factory, FVector origin) {

        this.factory = factory;
        this.origin = origin;
    }

    public static FDraft create(ConstructFactory factory, FVector origin) {

        return new FDraftDef(factory, origin);
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FVector getRefOrigin() {

        return origin;
    }

    @Override
    public FDraft setRefOrigin(FVector refOrigin) {

        if (refOrigin == null) {
            throw new NullPointerException("The reference FVector cannot be null");
        }

        origin = refOrigin;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------


    @Override
    public FDraft set(FPairPos3D position) {

        getRefOrigin().applyStateFrom(position);

        return this;
    }

    @Override
    public FDraft applyStateTo(Construct<?> in) {

        getRefOrigin().applyStateTo(in.getRefOrigin());

        return this;
    }

    @Override
    public FDraft applyStateFrom(Construct<?> arg) {

        getRefOrigin().applyStateFrom(arg.getRefOrigin());

        return this;
    }

    @Override
    public FDraft set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FVector origin = supplyFVector().set(json.getJSONObject(JSON_VAL));

        return setRefOrigin(origin);
    }

    // -------------------------------------------------------------------------------------------------


    @Override
    public FDraft self() {

        return this;
    }

    @Override
    public FDraft copy() {

        FDraft element = supplyFDraft();

        element.getRefOrigin().applyStateFrom(getRefOrigin().copy());

        return element;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FDraft) {
            return isExact((FDraft) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FDraft) {
            return isSimilar((FDraft) arg);
        }

        return false;
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
    }

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

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return getRefOrigin().hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FDraft fDraft) {

            return getRefOrigin().equals(fDraft.getRefOrigin());
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public FPos3D project(double x, double y, double z) {

        throw new RuntimeException("The 'project' method cannot be used with FDraft");
    }

    @Override
    public boolean project(FPoint in) {

        throw new RuntimeException("The 'project' method cannot be used with FDraft");
    }

    @Override
    public boolean project(Geometry in) {

        throw new RuntimeException("The 'project' method cannot be used with FDraft");
    }

    @Override
    public FPos3D reflect(double x, double y, double z) {

        throw new RuntimeException("The 'reflect' method cannot be used with FDraft");
    }

    @Override
    public boolean reflect(FPoint in) {

        throw new RuntimeException("The 'reflect' method cannot be used with FDraft");
    }

    @Override
    public boolean reflect(Geometry in) {

        throw new RuntimeException("The 'reflect' method cannot be used with FDraft");
    }

    @Override
    public boolean isPartOf(FPoint arg) {

        throw new RuntimeException("The 'isPartOf' method cannot be used with FDraft");
    }

    @Override
    public boolean isPartOf(FPoint arg, double epsilon) {

        throw new RuntimeException("The 'isPartOf' method cannot be used with FDraft");
    }

    @Override
    public boolean isPartOf(Geometry arg) {

        throw new RuntimeException("The 'isPartOf' method cannot be used with FDraft");
    }

    @Override
    public boolean isPartOf(Geometry arg, double epsilon) {

        throw new RuntimeException("The 'isPartOf' method cannot be used with FDraft");
    }

    @Override
    public FRay asFRay() {

        if (this.fRay == null) {
            this.fRay = supplyRefFRay();
        }

        return this.fRay;
    }

    @Override
    public FLine asFLine() {

        if (this.fLine == null) {
            this.fLine = supplyRefFLine();
        }

        return this.fLine;
    }

    @Override
    public FPlane asFPlane() {

        if (this.fPlane == null) {
            this.fPlane = supplyRefFPlane();
        }

        return this.fPlane;
    }

    @Override
    public FSegment asFSegment() {

        if (this.fSegment == null) {
            this.fSegment = supplyRefFSegment();
        }

        return this.fSegment;
    }

    // -------------------------------------------------------------------------------------------------

    private FVector supplyFVector() {

        return factory.getFVector();
    }

    private FDraft supplyFDraft() {

        return factory.getFDraft();
    }

    private FRay supplyRefFRay() {

        return factory.getRefFRay(this.getRefOrigin());
    }

    private FLine supplyRefFLine() {

        return factory.getRefFLine(this.getRefOrigin());
    }

    private FPlane supplyRefFPlane() {

        return factory.getRefFPlane(this.getRefOrigin());
    }

    private FSegment supplyRefFSegment() {

        return factory.getRefFSegment(this.getRefOrigin());
    }
}
