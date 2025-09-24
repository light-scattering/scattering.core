package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.impl.component.geometry.shape.preset.ShapePresetDef;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

import java.util.*;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FSphereDef extends ShapePresetDef implements FSphere {
    private static final String JSON_MAIN = "sphere";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_CENTER = "center";
    private static final String JSON_COATS = "coats";
    private static final String JSON_INDEX = "index";
    private static final String JSON_META = "meta";

    private static final double DEF_RADIUS = 1;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FSphereHelper helper;

    private FPoint center;
    private double radius;

    private FSphereDef(ScatFactory factory) {
        super(factory);

        this.helper = factory.getFSphereHelper();
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

        if (json.has(JSON_COATS)) {
            var coats = json.getJSONArray(JSON_COATS);

            getCoatWidth().clear();
            for (int i = 0 ; i < coats.length() ; i++) {
                addCoatInternal(coats.getDouble(i));
            }

        }

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

        getCoatWidth().clear();
        for (int i = 0; i < arg.getCoatCount() ; i++) {
            this.addCoatInternal(arg.getCoatWidth(i));
        }

        return this;
    }

    @Override
    public FSphere applyStateTo(FSphere in) {

        in.applyStateFrom(this);

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

            if (!this.getRefCenter().isExact(fSphere.getRefCenter())) {
                return false;
            }

            if (this.getRadius() != fSphere.getRadius()) {
                return false;
            }

            int coats = this.getCoatCount();

            if (arg.getCoatCount() != coats) {
                return false;
            }

            for (int i = 0 ; i < coats ; i++) {
                if (this.getCoatWidth(i) != arg.getCoatWidth(i)) {
                    return false;
                }
            }

            return true;
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

            if (!this.getRefCenter().isSimilar(fSphere.getRefCenter())) {
                return false;
            }

            if (Math.abs(this.getRadius() - fSphere.getRadius()) > EPSILON) {
                return false;
            }

            int coats = this.getCoatCount();

            if (arg.getLayerCount() - 1 != coats) {
                return false;
            }

            for (int i = 0 ; i < coats ; i++) {
                if (Math.abs(this.getCoatWidth(i) - arg.getCoatWidth(i)) > EPSILON) {
                    return false;
                }
            }

            return true;
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

        if (getCoatWidth().size() > 0) {
            for (int i = 0; i < getCoatWidth().size() ; i++) {
                json.append(JSON_COATS, this.getCoatWidth(i));
            }
        }

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
    public String toString() {

        return toJSON().toString();
    }

    //--- Module - Interaction

    @Override
    public boolean attachLinear(Shape target) {

        if (this == target) {
            throw new IllegalArgumentException("Cannot attach to itself");
        }

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

        if (this == target) {
            throw new IllegalArgumentException("Cannot attach to itself");
        }

        // The FSphere is already at a correct position.
        if (super.touches(target)) {
            return true;
        }

        FVector fVectorAxis = super.supplyFVector()
                .setBase(x, y, z)
                .setHead(getCenter());

        // The FSphere is on the rotation axis, and therefore, cannot be linearly positioned.
        if (fVectorAxis.isCollinearBaseCommon(target.getCenter())) {
            return false;
        }

        double sideA = fVectorAxis.getMagnitude();
        double sideB = target.getDistCenter(fVectorAxis.getRefBase());
        double sideC = this.getRadius() + target.getRadius();

        // The FSphere cannot be positioned.
        if (!super.getFTrigHelper().isValid(sideA, sideB, sideC)) {
            return false;
        }

        double angle = super.getFTrigHelper().getAngle(sideB, sideA, sideC);

        super.getFRotEngine().setRgAngleBaseCommon(fVectorAxis, target.getCenter(), angle);

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
    public boolean attachLinearWithSphericalCorrection(Shape target, Iterable<? extends Shape> field, int corrections) {
        int repositions = 1;

        if (!attachLinear(target)) {
            return false;
        }

        List<Shape> neighbours = new ArrayList<>();

        Shape closestNeighbour = getClosestNeighbourCenter(neighbours, field);

        if (closestNeighbour == null) {
            return true;
        }

        while (closestNeighbour != null && repositions++ < corrections + 1) {

            if (!attachSpherical(closestNeighbour, target.getCenterX(), target.getCenterY(), target.getCenterZ())) {
                return false;
            }

            closestNeighbour = getClosestNeighbourCenter(neighbours, field);
        }

        return closestNeighbour == null;
    }

    public Shape getClosestNeighbourCenter(List<Shape> arg, Iterable<? extends Shape> shapes) {
        overlaps(shapes, arg);
        sortByDistCenter(arg);

        return arg.size() > 0 ? arg.get(0) : null;
    }

    @Override
    public boolean project(Shape target, FRay ray) {

        FPos3D projection = ray.project(target.getCenterX(), target.getCenterY(), target.getCenterZ());

        if (projection == null) {
            return false;
        }

        if (target.getDistCenter(projection) > this.getRadius() + target.getRadius()) {
            return false;
        }

        double sideA = target.getDistCenter(projection);
        double sideC = this.getRadius() + target.getRadius();
        double sideB = Math.sqrt((sideC * sideC) - (sideA * sideA));

        FPos3D center = getFPointHelper().setDistance(projection, ray.getRefOrigin().getRefBase().toFPos3D(), sideB);

        this.setCenter(center);

        return true;
    }

    @Override
    public boolean project(Iterable<? extends Shape> field, FRay ray) {
        List<Shape> candidates = new ArrayList<>();

        setCenter(ray.getRefOrigin().getRefBase());
        getCollisionListLinear(candidates, field, ray);

        sortByDistCenter(candidates);

        for (Shape candidate : candidates) {
            if (project(candidate, ray)) {
                if (overlaps(field) <= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z) {
        in.clear();

        double dist = getDistCenter(x, y, z);
        double distMin = dist - getRadius();
        double distMax = dist + getRadius();

        double distShape;
        for (Shape shape : field) {

            if (this == shape) {
                continue;
            }

            distShape = shape.getDistCenter(x, y, z);

            if (distShape + shape.getRadius() < distMin) {
                continue;
            }

            if (distShape - shape.getRadius() > distMax) {
                continue;
            }

            in.add(shape);
        }
    }

    @Override
    public void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPoint center) {

        getCollisionListSpherical(in, field, center.getX(), center.getY(), center.getZ());
    }

    @Override
    public void getCollisionListSpherical(List<Shape> in, Iterable<? extends Shape> field, FPos3D center) {

        getCollisionListSpherical(in, field, center.getD0(), center.getD1(), center.getD2());
    }

    @Override
    public void getCollisionListLinear(List<Shape> in, Iterable<? extends Shape> field, FRay ray) {
        in.clear();

        this.setCenter(ray.getRefOrigin().getRefBase());

        double distShape, distTarget, distRadius;
        for (Shape shape : field) {

            if (this == shape) {
                continue;
            }

            distRadius = this.getRadius() + shape.getRadius();
            distShape = ray.getDistance(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());
            distTarget = shape.getDistCenter(ray.getRefOrigin().getRefBase()) - distRadius;

            if (distShape >= 0 && distShape < distRadius && distTarget > distRadius) {
                in.add(shape);
            }
        }
    }

    //--- Module - Composition

    @Override
    public int locate(double x, double y, double z) {

        if (super.getCoatCount() == 0) {
            return contains(x, y, z) ? 0 : -1;
        }

        double tX = x - getCenterX();
        double tY = y - getCenterY();
        double tZ = z - getCenterZ();

        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);
        double radius = getInnerRadius() - getLayerWidthRemaining(0);

        if (distP2 < radius * radius) {
            return 0;
        }

        for (int i = 0; i < getCoatCount() ; i++) {
            radius += getCoatWidth(i);

            if (distP2 < radius * radius) {
                return i + 1;
            }
        }

        return -1;
    }

    @Override
    public boolean contains(double x, double y, double z) {
        double tX = x - getCenterX();
        double tY = y - getCenterY();
        double tZ = z - getCenterZ();

        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);
        double radP2 = getRadius() * getRadius();

        return distP2 < radP2;
    }

    @Override
    public boolean containsWithSurface(double x, double y, double z, int layer) {

        if (layer < 0) {
            throw new IllegalArgumentException("The layer index cannot be lower than zero");
        }

        if (layer > getLayerCount()) {
            throw new IllegalArgumentException("The layer index is erroneous");
        }

        double tX = x - getCenterX();
        double tY = y - getCenterY();
        double tZ = z - getCenterZ();

        double radius = getRadius();
        for (int i = getLayerCount() - 1 ; i > layer ; i--) {
            radius -= getCoatWidth(i - 1);
        }

        double radP2 = radius * radius;
        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        return distP2 <= radP2 + getEpsilon();
    }

    @Override
    public boolean containsWithSurface(double x, double y, double z) {
        double tX = x - getCenterX();
        double tY = y - getCenterY();
        double tZ = z - getCenterZ();

        double radP2 = getRadius() * getRadius();
        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        return distP2 <= radP2 + getEpsilon();
    }

    //--- Module - Position

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
    public void sortByDistSpace(List<? extends Shape> in) {
        CmpDistSpace cmp = getCacheCmpDistSpace();

        cmp.setRef(this);

        in.sort(cmp);
    }

    //--- Module - Dimension

    @Override
    public double getRadius() {

        return this.radius;
    }

    @Override
    public Shape setRadius(double radius) {

        if (radius <= 0) {
            throw new IllegalArgumentException("The radius must be greater than zero");
        }

        if (radius <= getLayerWidthRemaining(0)) {
            throw new IllegalArgumentException("The radius cannot be smaller than the width of the coating");
        }

        this.radius = radius;

        return this;
    }

    @Override
    public double getInnerRadius() {

        return getRadius();
    }

    @Override
    public Shape setInnerRadius(double radius) {

        return setRadius(radius);
    }

    @Override
    public double getLayerVolume(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getLayerCount()) {
            throw new IllegalArgumentException("The layer index is erroneous");
        }

        if (index == 0) {
            return helper.getVolume(getRadius() - getLayerWidthRemaining(0));
        }

        double radiusMin = getRadius() - getLayerWidthRemaining(0);

        for (int i = 0 ; i < index - 1 ; i++) {
            radiusMin += getCoatWidth(i);
        }

        double radiusMax = radiusMin + getCoatWidth(index - 1);

        return helper.getVolumeRing(radiusMin, radiusMax);
    }

    @Override
    public double getCoatVolume(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getCoatCount()) {
            throw new IllegalArgumentException("The coat index is erroneous");
        }

        return getLayerVolume(index + 1);
    }

    @Override
    public double getCoatVolume() {
        double radiusMin = getRadius() - getLayerWidthRemaining(0);

        return helper.getVolumeRing(radiusMin, getRadius());
    }

    @Override
    public double getLayerSurface(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getLayerCount()) {
            throw new IllegalArgumentException("The layer index is erroneous");
        }

        if (index == 0) {
            return helper.getSurface(getRadius() - getLayerWidthRemaining(0));
        }

        double radiusMin = getRadius() - getLayerWidthRemaining(0);

        for (int i = 0 ; i < index - 1 ; i++) {
            radiusMin += getCoatWidth(i);
        }

        double radiusMax = radiusMin + getCoatWidth(index - 1);

        return helper.getSurface(radiusMax);
    }

    @Override
    public double getCoatSurface(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getCoatCount()) {
            throw new IllegalArgumentException("The coat index is erroneous");
        }

        return getLayerSurface(index + 1);
    }

    @Override
    public double getCoatSurface() {
        double surface = 0;

        for (int i = 0 ; i < getCoatCount() ; i++) {
            surface += getCoatSurface(i);
        }

        return surface;
    }

    @Override
    public double getVolumeAlgebraic() {

        return helper.getVolume(getRadius());
    }

    @Override
    public double getSurfaceAlgebraic() {

        return helper.getSurface(getRadius());
    }

    @Override
    public double fillSurfaceLayerOverlap(FLayerCounter in, Iterable<? extends Shape> field) {
        double srfUnit = getDelta() * getDelta();

        getSurfaceElements(getRadius(), (x, y, z) -> {
            int layers = 0;

            for (Shape shape : field) {

                if (this == shape) {
                    continue;
                }

                if (shape.containsWithSurface(x, y, z)) {
                    layers++;
                }
            }

            in.inc(layers);
        });

        return srfUnit;
    }

    @Override
    public double fillSurfaceLayer(FLayerCounter in) {
        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            in.set(i, in.get(i) + (int) Math.round(helper.getSurface(getLayerRadius(i)) / srfUnit));
        }

        return srfUnit;
    }

    @Override
    public double fillSurfaceLayer(FLayerCounter in, List<? extends Shape> structure) {
        int position = structure.indexOf(this);

        if (position == -1) {
            throw new IllegalArgumentException("The shape must be a part of the structure");
        }

        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            int location = i;

            getSurfaceElements(getLayerRadius(location), (x, y, z) -> {
                boolean isPartOf = true;

                for (Shape shape : structure) {

                    if (this == shape) {
                        continue;
                    }

                    if (shape.containsWithSurface(x, y, z, location)) {
                        isPartOf = false;

                        break;
                    }
                }

                if (isPartOf) {
                    in.inc(location);
                }
            });
        }

        return srfUnit;
    }

    @Override
    public double fillSurfaceArray(FArray<FMetaData> in) {
        List<FMetaData> metaData = getMetaData();

        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            int index = i;

            getSurfaceElements(getLayerRadius(i), (d0, d1, d2) ->
                    in.addWithDataAndMeta(d0, d1, d2, getDelta(), metaData.get(index)));
        }

       return srfUnit;
    }

    @Override
    public double fillSurfaceArray(FArray<FMetaData> in, List<? extends Shape> structure) {
        List<FMetaData> metaData = getMetaData();

        int position = structure.indexOf(this);

        if (position == -1) {
            throw new IllegalArgumentException("The shape must be a part of the structure");
        }

        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            int location = i;

            getSurfaceElements(getLayerRadius(location), (x, y, z) -> {
                boolean isPartOf = true;

                for (Shape shape : structure) {

                    if (this == shape) {
                        continue;
                    }

                    if (shape.containsWithSurface(x, y, z, location)) {
                        isPartOf = false;

                        break;
                    }
                }

                if (isPartOf) {
                    in.addWithDataAndMeta(x, y, z, getDelta(), metaData.get(location));
                }
            });
        }

        return srfUnit;
    }

    private void getSurfaceElements(double radius, TriConsumer consumer) {
        int points = (int) Math.round(helper.getSurface(radius) / (getDelta() * getDelta()));

        double offset = 2.0 / points;
        double increment = Math.PI * (3.0 - Math.sqrt(5));

        for (int i = 0; i < points; i++) {
            double y = 1 - (i + 0.5) * offset;
            double r = Math.sqrt(1 - y * y);
            double phi = i * increment;

            double x = Math.cos(phi) * r;
            double z = Math.sin(phi) * r;

            consumer.consume(
                    getCenterX() + (x * radius),
                    getCenterY() + (y * radius),
                    getCenterZ() + (z * radius)
            );
        }
    }

    // -------------------------------------------------------------------------------------------------

    private double getLayerRadius(int layer) {

        return getRadius() - getLayerWidthRemaining(layer);
    }

    // -------------------------------------------------------------------------------------------------

    private CmpDistSpace getCacheCmpDistSpace() {

        if (super.getCache() != null) {
            return super.getCache().get(CmpDistSpace.class, (cache) -> CmpDistSpace.create());
        }

        return CmpDistSpace.create();
    }

    // -------------------------------------------------------------------------------------------------

    private FSphere supplyFSphere() {

        return factory.getFSphere();
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