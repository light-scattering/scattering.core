package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.impl.component.geometry.shape.preset.ShapePresetDef;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.layer.FLayer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

import java.util.*;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FSphereDef extends ShapePresetDef implements FSphere {
    private static final String JSON_MAIN = "sphere";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_CENTER = "center";
    private static final String JSON_INDEX = "index";
    private static final String JSON_META = "meta";

    private static final double DEF_RADIUS = 1;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final ScatFactory factory;

    private FPoint center;
    private double radius;

    private FSphereDef(ScatFactory factory) {
        super(factory);

        this.factory = factory;
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

        if (json.has(JSON_INDEX)) {
            setIndex(json.getInt(JSON_INDEX));
        }

        if (json.has(JSON_META)) {
            setMeta(json.getString(JSON_META));
        }

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

    @Override
    public Collection<FPoint> toFPoints() {
        Collection<FPoint> units = new ArrayList<>();

        units.add(getRefCenter());

        return units;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(Shape arg) {

        if (arg instanceof FSphere fSphere) {

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

        if (arg instanceof FSphere fSphere) {

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
        json.put(JSON_RADIUS, getRadius());

        if (getIndex() >= 0) {
            json.put(JSON_INDEX, getIndex());
        }

        if (!getMeta().equals("")) {
            json.put(JSON_META, getMeta());
        }

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

        if (object instanceof FSphere ref) {

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
    public double getInnerRadius() {

        return getRadius();
    }

    @Override
    public Shape setInnerRadius(double radius) {

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
        double tX = x - getCenterX();
        double tY = y - getCenterY();
        double tZ = z - getCenterZ();

        double radP2 = getRadius() * getRadius();
        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        return distP2 < radP2;
    }

    @Override
    public void fillSurfaceLayer(FLayer in) {

        in.set(0, in.get(0) + (int) Math.round(getSurface() / (getDelta() * getDelta())));
    }

    @Override
    public void fillSurfaceLayer(FLayer in, Iterable<? extends Shape> shapes) {

        getSurfacePoints((x, y, z) -> {
            int layers = 0;

            for (Shape shape : shapes) {
                if (this == shape) {
                    continue;
                }

                if (shape.contains(x, y, z)) {
                    layers++;
                }
            }

            in.inc(layers);
        });

    }

    @Override
    public void fillSurfaceArray(FArray in) {

       getSurfacePoints(in::add);
    }

    @Override
    public void fillSurfaceArray(FArray in, Iterable<? extends Shape> shapes) {

        getSurfacePoints((x, y, z) -> {
            boolean add = true;

            for (Shape shape : shapes) {
                if (this == shape) {
                    continue;
                }

                if (shape.contains(x, y, z)) {
                    add = false;
                }
            }

            if (add) {
                in.add(x, y, z);
            }
        });
    }

    private void getSurfacePoints(TriConsumer consumer) {
        int points = (int) Math.round(getSurface() / (getDelta() * getDelta()));

        double offset = 2.0 / points;
        double increment = Math.PI * (3.0 - Math.sqrt(5));

        for (int i = 0; i < points; i++) {
            double y = 1 - (i + 0.5) * offset;
            double r = Math.sqrt(1 - y * y);
            double phi = i * increment;

            double x = Math.cos(phi) * r;
            double z = Math.sin(phi) * r;

            consumer.consume(
                    getCenterX() + (x * getRadius()),
                    getCenterY() + (y * getRadius()),
                    getCenterZ() + (z * getRadius())
            );
        }
    }

    @Override
    public boolean attachLinear(Shape target) {

        // The FSphere is already at a correct position.
        if (super.touches(target)) {
            return true;
        }

        // The FSphere is at the same position as the target shape, and therefore, cannot be linearly positioned.
        if (isSimilarCenter(target)) {
            return false;
        }

        setDistCenter(target, getRadius() + target.getRadius());

        return true;
    }

    // TODO - Not optimized
    @Override
    public boolean attachSpherical(Shape target, double x, double y, double z) {

        // The FSphere is already at a correct position.
        if (super.touches(target)) {
            return true;
        }

        FVector fVectorAxis = super.supplyFVector()
                .setBase(x, y, z)
                .setHead(this.getCenterX(), this.getCenterY(), this.getCenterZ());

        // The FSphere is on the rotation axis, and therefore, cannot be linearly positioned.
        if (fVectorAxis.isCollinearBaseCommon(target.getCenter())) {
            return false;
        }

        double sideA = fVectorAxis.getMagnitude();
        double sideB = target.getDistCenter(fVectorAxis.getRefBase());
        double sideC = this.getRadius() + target.getRadius();

        // The FSphere cannot be positioned.
        if (!factory.getFTrigHelper().isValid(sideA, sideB, sideC)) {
            return false;
        }

        double angle = factory.getFTrigHelper().getAngle(sideB, sideA, sideC);

        factory.getFRotEngine().setRgAngleBaseCommon(fVectorAxis, target.getCenter(), angle);

        this.setCenter(fVectorAxis.getRefHead());

        return true;
    }

    @Override
    public boolean attachSpherical(Shape target, FPoint center) {

        return attachSpherical(target, center.getX(), center.getY(), center.getZ());
    }

    @Override
    public boolean attachSpherical(Shape target, FPos3D center) {

        return attachSpherical(target, center.getD0(), center.getD1(), center.getD2());
    }

    @Override
    public boolean attach(Shape target, Iterable<? extends Shape> shapes, int corrections) {
        int repositions = 1;

        if (!attachLinear(target)) {
            return false;
        }

        List<Shape> neighbours = new ArrayList<>();

        Shape closestNeighbour = getClosestNeighbourCenter(neighbours, shapes);

        if (closestNeighbour == null) {
            return true;
        }

        while (closestNeighbour != null && repositions++ < corrections + 1) {

            if (!attachSpherical(closestNeighbour, target.getCenterX(), target.getCenterY(), target.getCenterZ())) {
                return false;
            }

            closestNeighbour = getClosestNeighbourCenter(neighbours, shapes);
        }

        return closestNeighbour == null;
    }

    public Shape getClosestNeighbourCenter(List<Shape> arg, Iterable<? extends Shape> shapes) {
        overlaps(shapes, arg);
        sortByDistCenter(arg);

        return arg.size() > 0 ? arg.get(0) : null;
    }

    // TODO - Not optimized
    @Override
    public boolean project(FRay ray, Iterable<? extends Shape> shapes) {
        List<Shape> candidates = new ArrayList<>();

        setBasePosition(ray);
        getCollisionList(candidates, ray, shapes);

        for (Shape candidate : candidates) {
            if (validateCollision(candidate, ray, shapes)) {
                return true;
            }
        }

        return false;
    }

    private void setBasePosition(FRay ray) {

        this.setCenter(ray.getRefOrigin().getRefBase());
    }

    private void getCollisionList(List<Shape> in, FRay ray, Iterable<? extends Shape> shapes) {

        for (Shape shape : shapes) {

            double dist = ray.getDistance(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());
            if (dist >= 0 && dist < this.getRadius() + shape.getRadius() && !this.overlaps(shape)) {
                in.add(shape);
            }
        }

        sortByDistCenter(in);
    }

    private boolean validateCollision(Shape candidate, FRay ray, Iterable<? extends Shape> shapes) {

        this.setCenter(candidate);

        FPos3D projection = ray.project(this.getCenterX(), this.getCenterY(), this.getCenterZ());

        double sideA = candidate.getDistCenter(projection);
        double sideC = this.getRadius() + candidate.getRadius();
        double sideB = Math.sqrt((sideC * sideC) - (sideA * sideA));

        FPos3D center = getFPointHelper().setDistance(projection, ray.getRefOrigin().getRefBase().toFPos3D(), sideB);

        this.setCenter(center);

        return this.overlaps(shapes) <= 0;
    }

    @Override
    public void sortByDistSpace(List<? extends Shape> in) {
        CmpDistSpace cmp = getCacheCmpDistSpace();

        cmp.setRef(this);

        in.sort(cmp);
    }

    // -------------------------------------------------------------------------------------------------

    private CmpDistSpace getCacheCmpDistSpace() {

        if (super.getCache() != null) {
            return super.getCache().get(CmpDistSpace.class, (cache) -> supplyCmpDistSpace());
        }

        return supplyCmpDistSpace();
    }

    // -------------------------------------------------------------------------------------------------

    private FSphere supplyFSphere() {

        return factory.getFSphere();
    }

    private CmpDistSpace supplyCmpDistSpace() {

        return CmpDistSpace.create();
    }

    // -------------------------------------------------------------------------------------------------

    @FunctionalInterface
    interface TriConsumer {
        void consume(double x, double y, double z);
    }
}

class CmpDistSpace implements Comparator<Shape> {
    private Shape ref;

    private CmpDistSpace() {}

    public static CmpDistSpace create() {

        return new CmpDistSpace();
    }

    public void setRef(Shape ref) {

        this.ref = ref;
    }

    @Override
    public int compare(Shape s1, Shape s2) {
        double distS1 = this.ref.getDistCenter(s1) - this.ref.getRadius() - s1.getRadius();
        double distS2 = this.ref.getDistCenter(s2) - this.ref.getRadius() - s2.getRadius();

        return Double.compare(distS1, distS2);
    }
}