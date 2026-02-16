package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.line.FLine;
import eu.scattering.core.design.component.geometry.construct.ray.FRayHelper;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.impl.component.geometry.shape.preset.ShapePresetDef;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static eu.scattering.core.impl.ScatConfigDef.EPSILON;

public class FSphereDef extends ShapePresetDef implements FSphere {
    private static final String JSON_TYPE = "type";
    private static final String JSON_MAIN = "sphere";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_CENTER = "center";
    private static final String JSON_COATS = "coats";
    private static final String JSON_INDEX = "index";
    private static final String JSON_META = "meta";

    private static final double DEF_RADIUS = 1;

    // -------------------------------------------------------------------------------------------------

    private final FSphereHelper helper;

    private FPoint center;
    private double radius;

    private CmpDistSpace cmpDistSpace;

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

    @Override
    public FSphere set(JSONObject json) {

        if (!json.get(JSON_TYPE).equals(JSON_MAIN)) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        getRefCenter().set(json.getJSONObject(JSON_CENTER));
        setRadius(json.getDouble(JSON_RADIUS));

        if (json.has(JSON_COATS)) {
            var coats = json.getJSONArray(JSON_COATS);

            getCoatWidth().clear();
            for (int i = 0 ; i < coats.length() ; i++) {
                addInternalCoat(coats.getDouble(i));
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

        getRefCenter().set(arg.getRefCenter());
        setRadius(arg.getRadius());

        getCoatWidth().clear();
        for (int i = 0; i < arg.getCoatCount() ; i++) {
            this.addInternalCoat(arg.getCoatWidth(i));
        }

        setMeta(arg.getMeta());

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

        super.getAspectRot().setRgAngleBaseCommon(fVectorAxis, target.getCenter(), angle);

        this.setCenter(fVectorAxis.getRefHead());

        return true;
    }

    @Override
    public boolean attachSpherical(Shape target, FPoint anchor) {

        return attachSpherical(target, anchor.getX(), anchor.getY(), anchor.getZ());
    }

    @Override
    public boolean attachSpherical(Shape target, FPos3D anchor) {

        return attachSpherical(target, anchor.getD0(), anchor.getD1(), anchor.getD2());
    }

    @Override
    public boolean attachLinearWithSphericalCorrection(Shape target, Iterable<? extends Shape> field, int corrections) {
        int repositions = 0;

        if (!attachLinear(target)) {
            return false;
        }

        List<Shape> neighbours = new ArrayList<>();

        Shape closestNeighbour = getClosestNeighbourCenter(neighbours, field);

        if (closestNeighbour == null) {
            return true;
        }

        while (closestNeighbour != null && repositions++ < corrections) {

            if (!attachSpherical(closestNeighbour, target.getCenterX(), target.getCenterY(), target.getCenterZ())) {
                return false;
            }

            closestNeighbour = getClosestNeighbourCenter(neighbours, field);
        }

        return closestNeighbour == null;
    }

    protected Shape getClosestNeighbourCenter(List<Shape> arg, Iterable<? extends Shape> shapes) {
        overlaps(shapes, arg);
        sortByDistCenter(arg);

        return arg.size() > 0 ? arg.get(0) : null;
    }

    @Override
    public double project(Shape target, FVector dir) {
        FVector path = dir.copy();

        path.moveBase(getRefCenter());

        return projectFrom(target, path);
    }

    @Override
    public double project(Iterable<? extends Shape> field, FVector dir) {
        FVector path = dir.copy();

        path.moveBase(getRefCenter());

        return projectFrom(field, path);
    }

    @Override
    public double project(Shape target, FVector dir, double distLimit) {
        FVector path = dir.copy();

        path.moveBase(getRefCenter());

        return projectFrom(target, path, distLimit);
    }

    @Override
    public double project(Iterable<? extends Shape> field, FVector dir, double distLimit) {
        FVector path = dir.copy();

        path.moveBase(getRefCenter());

        return projectFrom(field, path, distLimit);
    }

    @Override
    public double projectFrom(Shape target, FVector path) {
        FPos3D projection = getFRayHelper().project(path, target.getCenterX(), target.getCenterY(), target.getCenterZ());

        if (projection == null) {
            return -1;
        }

        if (target.getDistCenter(projection) > this.getRadius() + target.getRadius()) {
            return -1;
        }

        double sideA = target.getDistCenter(projection);
        double sideC = this.getRadius() + target.getRadius();
        double sideB = Math.sqrt((sideC * sideC) - (sideA * sideA));

        FPos3D center = getFPointHelper().setDistance(projection, path.getRefBase().toFPos3D(), sideB);

        double shift = path.getRefBase().getDistance(center);

        this.setCenter(center);

        return shift;
    }

    @Override
    public double projectFrom(Iterable<? extends Shape> field, FVector path) {
        List<Shape> candidates = new ArrayList<>();

        getProjectFromCollisions(candidates, field, path);

        sortByDistCenter(candidates);

        for (Shape candidate : candidates) {
            double shift = projectFrom(candidate, path);

            if (shift >= 0) {
                if (overlaps(field) <= 0) {
                    return shift;
                }
            }
        }

        return -1;
    }

    @Override
    public double projectFrom(Shape target, FVector path, double distLimit) {
        FPos3D projection = getFRayHelper().project(path, target.getCenterX(), target.getCenterY(), target.getCenterZ());

        if (projection == null) {
            return -1;
        }

        if (target.getDistCenter(projection) > this.getRadius() + target.getRadius()) {
            return -1;
        }

        double sideA = target.getDistCenter(projection);
        double sideC = this.getRadius() + target.getRadius();
        double sideB = Math.sqrt((sideC * sideC) - (sideA * sideA));

        FPos3D center = getFPointHelper().setDistance(projection, path.getRefBase().toFPos3D(), sideB);

        double shift = path.getRefBase().getDistance(center);

        if (shift > distLimit) {
            return -1;
        }

        this.setCenter(center);

        return shift;
    }

    @Override
    public double projectFrom(Iterable<? extends Shape> field, FVector path, double distLimit) {
        List<Shape> candidates = new ArrayList<>();

        getProjectFromCollisions(candidates, field, path);

        sortByDistCenter(candidates);

        for (Shape candidate : candidates) {
            double shift = projectFrom(candidate, path, distLimit);

            if (shift >= 0) {
                if (overlaps(field) <= 0) {
                    return shift;
                }
            }
        }

        return -1;
    }

    @Override
    public double projectFromDryRun(Shape target, FVector path) {
        FPos3D projection = getFRayHelper().project(path, target.getCenterX(), target.getCenterY(), target.getCenterZ());

        if (projection == null) {
            return -1;
        }

        if (target.getDistCenter(projection) >= this.getRadius() + target.getRadius()) {
            return -1;
        }

        double sideA = target.getDistCenter(projection);
        double sideC = this.getRadius() + target.getRadius();
        double sideB = Math.sqrt((sideC * sideC) - (sideA * sideA));

        FPos3D center = getFPointHelper().setDistance(projection, path.getRefBase().toFPos3D(), sideB);

        return path.getRefBase().getDistance(center);
    }

    @Override
    public double projectFromDryRun(Iterable<? extends Shape> field, FVector path) {
        List<Shape> candidates = new ArrayList<>();

        getProjectFromCollisions(candidates, field, path);

        sortByDistCenter(candidates);

        double memoX, memoY, memoZ;
        for (Shape candidate : candidates) {
            double shift = projectFromDryRun(candidate, path);

            if (shift >= 0) {
                memoX = getCenterX();
                memoY = getCenterY();
                memoZ = getCenterZ();

                getFRayHelper().shiftForward(path, this, shift);

                boolean stop = overlaps(field) <= 0;

                setCenter(memoX, memoY, memoZ);

                if (stop) {
                    return shift;
                }
            }
        }

        return -1;
    }

    @Override
    public double projectFromDryRun(Shape target, FVector path, double distLimit) {
        FPos3D projection = getFRayHelper().project(path, target.getCenterX(), target.getCenterY(), target.getCenterZ());

        if (projection == null) {
            return -1;
        }

        if (target.getDistCenter(projection) > this.getRadius() + target.getRadius()) {
            return -1;
        }

        double sideA = target.getDistCenter(projection);
        double sideC = this.getRadius() + target.getRadius();
        double sideB = Math.sqrt((sideC * sideC) - (sideA * sideA));

        FPos3D center = getFPointHelper().setDistance(projection, path.getRefBase().toFPos3D(), sideB);

        double shift = path.getRefBase().getDistance(center);

        if (shift > distLimit) {
            return -1;
        }

        return shift;
    }

    @Override
    public double projectFromDryRun(Iterable<? extends Shape> field, FVector path, double distLimit) {
        List<Shape> candidates = new ArrayList<>();

        getProjectFromCollisions(candidates, field, path);

        sortByDistCenter(candidates);

        for (Shape candidate : candidates) {
            double shift = projectFromDryRun(candidate, path, distLimit);

            if (shift >= 0) {
                if (overlaps(field) <= 0) {
                    return shift;
                }
            }
        }

        return -1;
    }

    @Override
    public void getAttachSphericalCollisions(List<Shape> in, Iterable<? extends Shape> field, double x, double y, double z) {
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
    public void getAttachSphericalCollisions(List<Shape> in, Iterable<? extends Shape> field, FPoint anchor) {

        getAttachSphericalCollisions(in, field, anchor.getX(), anchor.getY(), anchor.getZ());
    }

    @Override
    public void getAttachSphericalCollisions(List<Shape> in, Iterable<? extends Shape> field, FPos3D anchor) {

        getAttachSphericalCollisions(in, field, anchor.getD0(), anchor.getD1(), anchor.getD2());
    }

    @Override
    public void getProjectCollisions(List<Shape> in, Iterable<? extends Shape> field, FVector dir) {
        FVector path = dir.copy();

        path.moveBase(getRefCenter());

        getProjectFromCollisions(in, field, path);
    }

    @Override
    public void getProjectFromCollisions(List<Shape> in, Iterable<? extends Shape> field, FVector path) {
        in.clear();

        double distProjection, distShape, distRadius;
        for (Shape shape : field) {

            if (this == shape) {
                continue;
            }

            distRadius = this.getRadius() + shape.getRadius();
            distProjection = getFRayHelper().getDistance(path, shape.getRefCenter());
            distShape = path.getRefBase().getDistance(shape.getRefCenter()) - distRadius;

            if (distProjection >= 0 && distProjection <= distRadius && distShape >= 0) {
                in.add(shape);
            }
        }
    }


    @Override
    public void getAttachCircularCollisions(List<Shape> in, Iterable<? extends Shape> field, FLine axis) {
       FSphere dummy = supplyFSphere();

       FPoint centerRef = supplyFPoint();
       FPoint centerArg = supplyFPoint();

       FVector axisRef = supplyFVector();
       FVector axisArg = supplyFVector();

       double distRef = axis.getDistance(getRefCenter());

       centerRef.set(getRefCenter());
       axis.project(centerRef);

       axisRef.set(centerRef, getRefCenter());

       for (Shape shape : field) {
           double distArg = axis.getDistance(shape.getRefCenter());

           if (Math.abs(distRef - distArg) > getRadius() + shape.getRadius() + EPSILON) {
               continue;
           }

           centerArg.set(shape.getRefCenter());
           axis.project(centerArg);

           if (centerRef.getDistance(centerArg) > getRadius() + shape.getRadius() + EPSILON) {
               continue;
           }

           dummy.setCenter(shape.getRefCenter());
           dummy.setRadius(shape.getRadius());

           axisArg.set(centerArg, dummy.getRefCenter());

           if (!axisRef.isNearZeroLength() && !axisArg.isNearZeroLength() && !axisRef.isParallel(axisArg)) {
               if (axisRef.isAntiParallel(axisArg)) {
                   getFRotAspect().rotRgAround(dummy.getRefCenter(), axis.getRefOrigin(), Math.PI);
               } else {
                   double angle = -axisRef.getAngle(axisArg);

                   getFRotAspect().rotRgAround(dummy.getRefCenter(), axisArg.setCrossProduct(axisRef), angle);
               }
           }

           if (overlaps(dummy)) {
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

    @Override
    public Shape rotate(FMatrix3x3D matrix) {

        getRefCenter().rotate(matrix);

        return this;
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
    public double fillSurfaceLayerOverlap(FLayer in, Iterable<? extends Shape> field) {
        double srfUnit = getDelta() * getDelta();

        getSurfacePoints(getRadius(), (x, y, z) -> {
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
    public double fillSurfaceLayer(FLayer in) {
        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            in.set(i, in.get(i) + (int) Math.round(helper.getSurface(getLayerRadius(i)) / srfUnit));
        }

        return srfUnit;
    }

    @Override
    public double fillSurfaceLayer(FLayer in, List<? extends Shape> structure) {
        int position = structure.indexOf(this);

        if (position == -1) {
            throw new IllegalArgumentException("The shape must be a part of the structure");
        }

        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            int location = i;

            getSurfacePoints(getLayerRadius(location), (x, y, z) -> {
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
    public double fillSurfaceArray(FBuffer<FBufferData> in) {
        List<FBufferData> metaData = getMetaData();

        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            int index = i;

            getSurfacePoints(getLayerRadius(i), (d0, d1, d2) ->
                    in.addWithDataAndMeta(d0, d1, d2, getDelta(), metaData.get(index)));
        }

       return srfUnit;
    }

    @Override
    public double fillSurfaceArray(FBuffer<FBufferData> in, List<? extends Shape> structure) {
        List<FBufferData> metaData = getMetaData();

        int position = structure.indexOf(this);

        if (position == -1) {
            throw new IllegalArgumentException("The shape must be a part of the structure");
        }

        double srfUnit = getDelta() * getDelta();

        for (int i = 0 ; i < getLayerCount() ; i++) {
            int location = i;

            getSurfacePoints(getLayerRadius(location), (x, y, z) -> {
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

    private void getSurfacePoints(double radius, TriConsumer consumer) {
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

        if (this.cmpDistSpace == null) {
            this.cmpDistSpace = CmpDistSpace.create();
        }

        return this.cmpDistSpace;
    }

    // -------------------------------------------------------------------------------------------------

    private FPoint supplyFPoint() {

        return factory.getFPoint();
    }

    private FSphere supplyFSphere() {

        return factory.getFSphere();
    }

    private FRayHelper getFRayHelper() {

        return factory.getFRayHelper();
    }

    private FRotAspect getFRotAspect() {

        return factory.getRotAspect();
    }

    // -------------------------------------------------------------------------------------------------

    @FunctionalInterface
    interface TriConsumer {

        void consume(double x, double y, double z);
    }

    static class CmpDistSpace implements Comparator<Shape> {
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
}
