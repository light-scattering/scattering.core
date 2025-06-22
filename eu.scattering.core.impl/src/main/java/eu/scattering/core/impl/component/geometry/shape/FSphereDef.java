package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.impl.component.geometry.shape.preset.ShapePresetDef;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FSphereDef extends ShapePresetDef implements FSphere {
    private static final String JSON_MAIN = "sphere";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_CENTER = "center";
    private static final String JSON_INDEX = "index";
    private static final String JSON_TAG = "tag";

    private static final double DEF_RADIUS = 1;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ScatFactory factory;
    private final FCache cache;

    private FPoint center;
    private double radius;

    private FSphereDef(ScatFactory factory) {
        super(factory);

        this.factory = factory;
        this.cache = factory.getFCache();
    }

    public static FSphere create(ScatFactory factory, FPoint refCenter) {

        var fSphere = new FSphereDef(factory);

        fSphere.setRefCenter(refCenter);
        fSphere.setRadius(DEF_RADIUS);

        return fSphere;
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
    }

    @Override
    public FPoint getRefCenter() {

        return this.center;
    }

    @Override
    public FSphere setRefCenter(FPoint refCenter) {

        if (refCenter == null) {
            throw new NullPointerException("The FPoint center cannot be null");
        }

        this.center = refCenter;

        return this;
    }

    @Override
    public double getRadius() {

        return this.radius;
    }

    @Override
    public double getCenterX() {

        return getRefCenter().getX();
    }

    @Override
    public double getCenterY() {

        return getRefCenter().getY();
    }

    @Override
    public double getCenterZ() {

        return getRefCenter().getZ();
    }

    @Override
    public Shape setCenterX(double x) {

        getRefCenter().setX(x);

        return this;
    }

    @Override
    public Shape setCenterY(double y) {

        getRefCenter().setY(y);

        return this;
    }

    @Override
    public Shape setCenterZ(double z) {

        getRefCenter().setZ(z);

        return this;
    }

    @Override
    public Shape setRadius(double radius) {

        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be greater than zero");
        }

        this.radius = radius;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public FSphere set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        getRefCenter().set(json.getJSONObject(JSON_CENTER));
        setRadius(json.getDouble(JSON_RADIUS));
        setIndex(json.getInt(JSON_INDEX));
        setTag(json.getString(JSON_TAG));

        return this;
    }

    @Override
    public FSphere applyStateFrom(FSphere arg) {

        getRefCenter().applyStateFrom(arg.getRefCenter());
        setRadius(arg.getRadius());

        return this;
    }

    @Override
    public FSphere applyStateTo(FSphere in) {

        in.getRefCenter().applyStateFrom(this.getRefCenter());
        in.setRadius(getRadius());

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(Shape arg) {

        if (arg instanceof FSphere) {
            FSphere fSphere = (FSphere) arg;

            return getRefCenter().isExact(fSphere.getRefCenter()) && getRadius() == fSphere.getRadius();
        }

       return false;
    }

    @Override
    public boolean isExact(Geometry arg) {

        if (arg instanceof FSphere) {
            return isExact((FSphere) arg);
        }

        return false;
    }

    @Override
    public boolean isSimilar(Shape arg) {

        if (arg instanceof FSphere) {
            FSphere fSphere = (FSphere) arg;

            if (Math.abs(getRadius() - fSphere.getRadius()) > EPSILON) {
                return false;
            }

            return getRefCenter().isSimilar(fSphere.getRefCenter());
        }

        return false;
    }

    @Override
    public boolean isSimilar(Geometry arg) {

        if (arg instanceof FSphere) {
            return isSimilar((FSphere) arg);
        }

        return false;
    }

    @Override
    public FSphere copy() {

        return supplyFSphere().applyStateFrom(this);
    }

    @Override
    public Geometry copyGeometry() {

        return copy();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_CENTER, getRefCenter().toJSON());
        json.put(JSON_RADIUS, radius);
        json.put(JSON_INDEX, getIndex());
        json.put(JSON_TAG, getTag());

        return json;
    }

    @Override
    public FSphere self() {

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getRefCenter(), getRadius());
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FSphere) {
            FSphere ref = (FSphere) object;

            return isExact(ref);
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public double getRadiusInner() {

        return getRadius();
    }

    @Override
    public Shape setRadiusInner(double radius) {

        return setRadius(radius);
    }

    @Override
    public double getVolume() {
        double r = getRadius();

        return 4 * Math.PI * r * r * r / 3;
    }

    @Override
    public FSphere setVolume(double volume) {

        setRadius(Math.pow(0.75 * volume / Math.PI, 1.0 / 3));

        return this;
    }

    @Override
    public double getSurface() {
        double r = getRadius();

        return 4 * Math.PI * r * r;
    }

    @Override
    public FSphere setSurface(double surface) {

        setRadius(Math.pow(0.25 * surface / Math.PI, 0.5));

        return this;
    }

    @Override
    public boolean contains(double x, double y, double z) {
        double tX = x - center.getX();
        double tY = y - center.getY();
        double tZ = z - center.getZ();

        double radP2 = radius * radius;
        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        return distP2 < radP2 + EPSILON;
    }

    @Override
    public boolean touches(Shape shape, double epsilon, double delta) {

        if (epsilon >= 0) {
            return super.touchesEpsilonSimplified(shape, epsilon) == Relation.TRUE;
        }

        return super.touches(shape, epsilon, delta);
    }

    @Override
    public boolean overlaps(Shape shape, double epsilon, double delta) {

        if (epsilon >= 0) {
            return super.overlapsEpsilonSimplified(shape, epsilon) == Relation.TRUE;
        }

        return super.overlaps(shape, epsilon, delta);
    }

    @Override
    public boolean encloses(Shape shape, double epsilon, double delta) {

        if (epsilon >= 0) {
            return super.enclosesEpsilonSimplified(shape, epsilon) == Relation.TRUE;
        }

        return super.encloses(shape, epsilon, delta);
    }

    @Override
    public boolean intersects(Shape shape, double epsilon, double delta) {

        if (epsilon >= 0) {
            return super.intersectsEpsilonSimplified(shape, epsilon) == Relation.TRUE;
        }

        return super.intersects(shape, epsilon, delta);
    }










    @Override
    public boolean attachLinear(Shape target, double epsilon, double delta) {

        if (!(target instanceof FSphere)) {
            throw new UnsupportedOperationException("The operation is not implemented");
        }

        // The FSphere is already at a correct position.
        if (super.touchesEpsilonSimplified(target, epsilon) == Relation.TRUE) {
            return true;
        }

        // The FSphere is at the same position as the target shape, and therefore, cannot be linearly positioned.
        if (isSimilarCenter(target)) {
            return false;
        }

        setDistCenter(target, getRadius() + target.getRadius());

        return true;
    }

    @Override
    public boolean attachLinear(Shape target, double epsilon, double delta, FAssembly<? extends Shape> field, int corrections) {

        if (!(target instanceof FSphere)) {
            throw new UnsupportedOperationException("The operation is not implemented");
        }

        int repositions = 1;

        if (!attachLinear(target, epsilon, delta)) {
            return false;
        }

        Shape closestNeighbour = getClosestNeighbour(epsilon, field);

        if (closestNeighbour == null) {
            return true;
        }

        while (closestNeighbour != null && repositions++ < corrections + 1) {

            if (!attachSpherical(closestNeighbour, target, epsilon)) {
                return false;
            }

            closestNeighbour = getClosestNeighbour(epsilon, field);
        }

        return closestNeighbour == null;
    }

    @Override
    public boolean attachSpherical(Shape target, Shape center, double epsilon) {

        if (!(target instanceof FSphere)) {
            throw new UnsupportedOperationException("The operation is not implemented");
        }

        // The FSphere is already at a correct position.
        if (super.touchesEpsilonSimplified(target, epsilon) == Relation.TRUE) {
            return true;
        }

        FPoint fPointTarget = getCacheFPoint()
                .set(target.getCenterX(), target.getCenterY(), target.getCenterZ());
        FVector fVectorAxis = getCacheFVector()
                .setBase(center.getCenterX(), center.getCenterY(), center.getCenterZ())
                .setHead(this.getCenterX(), this.getCenterY(), this.getCenterZ());

        // The FSphere is on the rotation axis, and therefore, cannot be linearly positioned.
        if (fVectorAxis.isCollinearBaseCommon(fPointTarget)) {
            return false;
        }

        double sideA = fVectorAxis.getMagnitude();
        double sideB = fPointTarget.getDistance(fVectorAxis.getRefBase());
        double sideC = this.getRadius() + target.getRadius();

        double angle = factory.getFTrigHelper().getAngle(sideB, sideA, sideC);

        factory.getFRotEngine().setRgAngleBaseCommon(fVectorAxis, fPointTarget, angle);

        this.setCenter(fVectorAxis.getRefHead());

        return true;
    }

    private Shape getClosestNeighbour(double epsilon, FAssembly<? extends Shape> field) {
        double distCenterMin = Double.MAX_VALUE;
        Shape candidate = null;

        for (Shape element : field) {

            if (element == this) {
                continue;
            }

            if (overlaps(element, epsilon, -1)) {
                double distCenter = getDistCenterP2(element);

                if (distCenter < distCenterMin) {
                    distCenterMin = distCenter;
                    candidate = element;
                }
            }
        }

        return candidate;
    }

    @Override
    public Collection<FPoint> toFPoints() {
        Collection<FPoint> units = new ArrayList<>();

        units.add(getRefCenter());

        return units;
    }

    @Override
    public void getSurfaceBuffer(FStream3DI stream, double delta) {

    }

    @Override
    public void getSurfaceBuffer(FStream3D stream, double delta) {

    }

    @Override
    public Shape setRadiusMin(FAssembly<? extends Shape> field, double minCutoff) {
        return null;
    }

    @Override
    public Shape setRadiusMax(FAssembly<? extends Shape> field, double maxCutoff) {
        return null;
    }


    @Override
    public boolean project(FPoint aim, List<FSphere> field) {
        return false;
    }




    // -------------------------------------------------------------------------------------------------

    private FSphere supplyFSphere() {

        return factory.getFSphere();
    }
}
