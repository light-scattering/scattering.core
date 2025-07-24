package eu.scattering.core.impl.component.geometry.shape.preset;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import eu.scattering.core.transfer.container.buffer.layer.FLayer;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.*;

public abstract class ShapePresetDef implements Shape {
    private final static double SHIFT_OFFSET = -0.25;

    private final ScatFactory factory;
    private FCache cache;

    private double epsilon = SHAPE_EPSILON;
    private double delta = SHAPE_DELTA;

    private final boolean shift = true;

    private int index = -1;
    private String tag = "";

    public ShapePresetDef(ScatFactory factory) {

        this.factory = factory;
    }

    @Override
    public Shape createCache() {

        this.cache = supplyFCache();

        return this;
    }

    @Override
    public FCache getCache() {

        return this.cache;
    }

    @Override
    public Shape setCache(FCache cache) {

        this.cache = cache;

        return this;
    }

    @Override
    public double getEpsilon() {

        return this.epsilon;
    }

    @Override
    public Shape setEpsilon(double epsilon) {

        this.epsilon = epsilon;

        return this;
    }

    @Override
    public double getDelta() {

        return this.delta;
    }

    @Override
    public Shape setDelta(double delta) {

        this.delta = delta;

        return this;
    }

    @Override
    public String getMeta() {

        return this.tag;
    }

    @Override
    public Shape setMeta(String meta) {

        this.tag = meta;

        return this;
    }

    @Override
    public int getIndex() {

        return this.index;
    }

    @Override
    public Shape setIndex(int index) {

        this.index = index;

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExactCenter(Shape arg) {

        if (getCenterX() != arg.getCenterX()) {
            return false;
        }

        if (getCenterY() != arg.getCenterY()) {
            return false;
        }

        return getCenterZ() == arg.getCenterZ();
    }

    @Override
    public boolean isSimilarCenter(Shape arg) {

        double distanceX = Math.abs(getCenterX() - arg.getCenterX());
        double distanceY = Math.abs(getCenterY() - arg.getCenterY());
        double distanceZ = Math.abs(getCenterZ() - arg.getCenterZ());

        return distanceX < EPSILON && distanceY < EPSILON && distanceZ < EPSILON;
    }

    @Override
    public FPos3D getCenter() {

        return factory.getFPos3D(getCenterX(), getCenterY(), getCenterZ());
    }

    @Override
    public Shape setCenter(double x, double y, double z) {

        return setCenterX(x).setCenterY(y).setCenterZ(z);
    }

    @Override
    public Shape setCenter(FPoint fPoint) {

        setCenter(fPoint.getX(), fPoint.getY(), fPoint.getZ());

        return this;
    }

    @Override
    public Shape setCenter(FPos3D fPos3D) {

        setCenter(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());

        return this;
    }

    @Override
    public Shape scale(double factor) {

        setCenter(
                getCenterX() * factor,
                getCenterY() * factor,
                getCenterZ() * factor
        );

        setRadius(getRadius() * factor);

        return this;
    }

    @Override
    public Shape translate(double x, double y, double z) {

        setCenter(
                getCenterX() + x,
                getCenterY() + y,
                getCenterZ() + z
        );

        return this;
    }

    @Override
    public Shape translate(FPoint fPoint) {

        return translate(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public Shape translate(FPos3D fPos3D) {

        return translate(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
    }

    @Override
    public double getDistCenter(double x, double y, double z) {
        FVector fVector = getCacheFVector();

        fVector.setBase(x, y, z);
        fVector.setHead(getCenterX(), getCenterY(), getCenterZ());

        return fVector.getMagnitude();
    }

    @Override
    public double getDistCenter(FPoint fPoint) {

        return getDistCenter(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public double getDistCenter(FPos3D fPos3D) {

        return getDistCenter(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
    }

    @Override
    public double getDistCenter(Shape shape) {

        return getDistCenter(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());
    }

    @Override
    public double getDistCenterP2(double x, double y, double z) {
        double dimX = getCenterX() - x;
        double dimY = getCenterY() - y;
        double dimZ = getCenterZ() - z;

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

    @Override
    public double getDistCenterP2(FPoint fPoint) {

        return getDistCenterP2(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public double getDistCenterP2(FPos3D fPos3D) {

        return getDistCenterP2(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
    }

    @Override
    public double getDistCenterP2(Shape shape) {

        return getDistCenterP2(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());
    }

    @Override
    public Shape setDistCenter(double x, double y, double z, double dist) {
        FVector fVector = getCacheFVector();

        fVector.setBase(x, y, z);
        fVector.setHead(getCenterX(), getCenterY(), getCenterZ());

        fVector.setMagnitude(dist);

        setCenter(fVector.getRefHead());

        return this;
    }

    @Override
    public Shape setDistCenter(FPoint fPoint, double dist) {

        return setDistCenter(fPoint.getX(), fPoint.getY(), fPoint.getZ(), dist);
    }

    @Override
    public Shape setDistCenter(FPos3D fPos3D, double dist) {

        return setDistCenter(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2(), dist);
    }

    @Override
    public Shape setDistCenter(Shape shape, double dist) {

        return setDistCenter(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ(), dist);
    }

    @Override
    public boolean contains(FPoint fPoint) {

        return contains(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public boolean contains(FPos3D fPos3D) {

        return contains(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
    }

    @Override
    public boolean touches(Shape shape) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = touchesEpsilon(shape, epsilon);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return touchesDelta(shape, delta);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int touches(Iterable<? extends Shape> shapes) {
        int count = 0;

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (touches(shape)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public int touches(Iterable<? extends Shape> shapes, List<Shape> in) {
        in.clear();

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (touches(shape)) {
                in.add(shape);
            }
        }

        return in.size();
    }

    protected Relation touchesEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() + epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getInnerRadius() + shape.getInnerRadius() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.FALSE;
        }

        return this.getRadius() == this.getInnerRadius() ? Relation.TRUE : Relation.UNDEFINED;
    }

    protected boolean touchesDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        boolean ruleTouch = false;

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getBaseX() + offset ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + offset ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + offset ; z < range.getHeadZ() ; z += delta) {
                    boolean containsA = this.contains(x, y, z);
                    boolean containsB = shape.contains(x, y, z);

                    if (containsA && containsB) {
                        return false;
                    }

                    if (!ruleTouch) {
                        if (containsA) {
                            if (shape.contains(x + delta, y, z) ||
                                    shape.contains(x, y + delta, z) ||
                                    shape.contains(x, y, z + delta)) {
                                ruleTouch = true;
                            }
                        }

                        if (containsB) {
                            if (this.contains(x + delta, y, z) ||
                                    this.contains(x, y + delta, z) ||
                                    this.contains(x, y, z + delta)) {
                                ruleTouch = true;
                            }
                        }
                    }
                }
            }
        }

        return ruleTouch;
    }

    @Override
    public boolean overlaps(Shape shape) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = overlapsEpsilon(shape);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return overlapsDelta(shape);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int overlaps(Iterable<? extends Shape> shapes) {
        int count = 0;

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (overlaps(shape)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public int overlaps(Iterable<? extends Shape> shapes, List<Shape> in) {
        in.clear();

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (overlaps(shape)) {
                in.add(shape);
            }
        }

        return in.size();
    }

    protected Relation overlapsEpsilon(Shape shape) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getInnerRadius() + shape.getInnerRadius() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getInnerRadius() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean overlapsDelta(Shape shape) {
        FVector range = getOperationRange(shape);

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getBaseX() + offset ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + offset ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + offset ; z < range.getHeadZ() ; z += delta) {
                    if (this.contains(x, y, z) && shape.contains(x, y, z)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean encloses(Shape shape) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = enclosesEpsilon(shape);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return enclosesDelta(shape);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int encloses(Iterable<? extends Shape> shapes) {
        int count = 0;

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (encloses(shape)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public int encloses(Iterable<? extends Shape> shapes, List<Shape> in) {
        in.clear();

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (encloses(shape)) {
                in.add(shape);
            }
        }

        return in.size();
    }

    protected Relation enclosesEpsilon(Shape shape) {

        if (this.getRadius() < shape.getRadius()) {
            return Relation.FALSE;
        }

        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getInnerRadius() - shape.getInnerRadius() + epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getInnerRadius() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean enclosesDelta(Shape shape) {
        FVector range = getOperationRange(shape);

        if (range.getBaseX() == range.getHeadX()) {
            return false;
        }

        if (range.getBaseY() == range.getHeadY()) {
            return false;
        }

        if (range.getBaseZ() == range.getHeadZ()) {
            return false;
        }

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getBaseX() + offset ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + offset ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + offset ; z < range.getHeadZ() ; z += delta) {
                    if (shape.contains(x, y, z) && !this.contains(x, y, z)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean intersects(Shape shape) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = intersectsEpsilon(shape, epsilon);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return intersectsDelta(shape, delta);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int intersects(Iterable<? extends Shape> shapes) {
        int count = 0;

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (intersects(shape)) {
                count++;
            }
        }

        return count;
    }

    @Override
    public int intersects(Iterable<? extends Shape> shapes, List<Shape> in) {
        in.clear();

        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            if (intersects(shape)) {
                in.add(shape);
            }
        }

        return in.size();
    }

    protected Relation intersectsEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = Math.abs(this.getInnerRadius() - shape.getInnerRadius()) - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getInnerRadius() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean intersectsDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        boolean ruleShapeA = false;
        boolean ruleShapeB = false;
        boolean ruleCommon = false;

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getBaseX() + offset ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + offset ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + offset ; z < range.getHeadZ() ; z += delta) {
                    boolean containsA = this.contains(x, y, z);
                    boolean containsB = shape.contains(x, y, z);

                    if (!ruleShapeA) {
                        if (containsA && !containsB) {
                            ruleShapeA = true;
                        }
                    }

                    if (!ruleShapeB) {
                        if (!containsA && containsB) {
                            ruleShapeB = true;
                        }
                    }

                    if (!ruleCommon) {
                        if (containsA && containsB) {
                            ruleCommon = true;
                        }
                    }

                    if (ruleShapeA && ruleShapeB && ruleCommon) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected FVector getOperationRange(Shape shape) {
        FVector range = getCacheFVector();

        double opA, opB;

        opA = this.getCenterX() - this.getRadius();
        opB = shape.getCenterX() - shape.getRadius();

        range.setBaseX(Math.max(opA, opB));

        opA = this.getCenterX() + this.getRadius();
        opB = shape.getCenterX() + shape.getRadius();

        range.setHeadX(Math.min(opA, opB));

        opA = this.getCenterY() - this.getRadius();
        opB = shape.getCenterY() - shape.getRadius();

        range.setBaseY(Math.max(opA, opB));

        opA = this.getCenterY() + this.getRadius();
        opB = shape.getCenterY() + shape.getRadius();

        range.setHeadY(Math.min(opA, opB));

        opA = this.getCenterZ() - this.getRadius();
        opB = shape.getCenterZ() - shape.getRadius();

        range.setBaseZ(Math.max(opA, opB));

        opA = this.getCenterZ() + this.getRadius();
        opB = shape.getCenterZ() + shape.getRadius();

        range.setHeadZ(Math.min(opA, opB));

        return range;
    }

    @Override
    public void sortByDistCenter(List<? extends Shape> in) {
        CmpDistCenter cmp = getCacheCmpDistCenter();

        cmp.setRef(this);

        in.sort(cmp);
    }

    @Override
    public void fillVolumeLayer(FLayer in) {
        double factor = 1 / delta;

        double radiusParsed = factor * getRadius();

        double cX = factor * getCenterX();
        double cY = factor * getCenterY();
        double cZ = factor * getCenterZ();

        double minX = Math.floor(cX - radiusParsed) * delta;
        double maxX = getCenterX() + getRadius();
        double minY = Math.floor(cY - radiusParsed) * delta;
        double maxY = getCenterY() + getRadius();
        double minZ = Math.floor(cZ - radiusParsed) * delta;
        double maxZ = getCenterZ() + getRadius();

        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    if (contains(x, y, z)) {
                        in.inc();
                    }
                }
            }
        }
    }

    @Override
    public void fillVolumeLayer(FLayer in, Iterable<? extends Shape> shapes) {
        double factor = 1 / delta;

        double radiusParsed = factor * getRadius();

        double cX = factor * getCenterX();
        double cY = factor * getCenterY();
        double cZ = factor * getCenterZ();

        double minX = Math.floor(cX - radiusParsed) * delta;
        double maxX = getCenterX() + getRadius();
        double minY = Math.floor(cY - radiusParsed) * delta;
        double maxY = getCenterY() + getRadius();
        double minZ = Math.floor(cZ - radiusParsed) * delta;
        double maxZ = getCenterZ() + getRadius();

        int layers;
        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    if (contains(x, y, z)) {
                        layers = 0;

                        for (Shape shape : shapes) {
                            if (this == shape) {
                                continue;
                            }

                            if (shape.contains(x, y, z)) {
                                layers++;
                            }
                        }

                        in.inc(layers);
                    }
                }
            }
        }
    }

    @Override
    public void fillVolumeArray(FArray in) {
        double factor = 1 / delta;

        double radiusParsed = factor * getRadius();

        double cX = factor * getCenterX();
        double cY = factor * getCenterY();
        double cZ = factor * getCenterZ();

        double minX = Math.floor(cX - radiusParsed) * delta;
        double maxX = getCenterX() + getRadius();
        double minY = Math.floor(cY - radiusParsed) * delta;
        double maxY = getCenterY() + getRadius();
        double minZ = Math.floor(cZ - radiusParsed) * delta;
        double maxZ = getCenterZ() + getRadius();

        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    if (contains(x, y, z)) {
                        in.add(x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public void fillVolumeArray(FArray in, Iterable<? extends Shape> shapes) {
        double factor = 1 / delta;

        double radiusParsed = factor * getRadius();

        double cX = factor * getCenterX();
        double cY = factor * getCenterY();
        double cZ = factor * getCenterZ();

        double minX = Math.floor(cX - radiusParsed) * delta;
        double maxX = getCenterX() + getRadius();
        double minY = Math.floor(cY - radiusParsed) * delta;
        double maxY = getCenterY() + getRadius();
        double minZ = Math.floor(cZ - radiusParsed) * delta;
        double maxZ = getCenterZ() + getRadius();

        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {

                local:
                for (double z = minZ ; z < maxZ ; z += delta) {
                    if (contains(x, y, z)) {

                        for (Shape shape : shapes) {
                            if (this == shape) {
                                continue;
                            }

                            if (shape.contains(x, y, z)) {
                                continue local;
                            }
                        }

                        in.add(x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public Shape setMinRadius(Iterable<? extends Shape> shapes) {
        double minRadius = getRadius();

        double dist;
        for (Shape shape : shapes) {
            if (this == shape) {
                continue;
            }

            dist = getDistCenter(shape) + shape.getRadius();

            if (dist > minRadius) {
                minRadius = dist;
            }
        }
        
        if (minRadius > getRadius()) {
            setRadius(minRadius + EPSILON);
        }

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPoint getCacheFPoint() {

        if (cache != null) {
            return cache.get("fPoint", FPoint.class, (cache) -> supplyFPoint());
        }

        return supplyFPoint();
    }

    protected FVector getCacheFVector() {

        if (cache != null) {
            return cache.get("fVector", FVector.class, (cache) -> supplyFVector());
        }

        return supplyFVector();
    }

    protected List<Shape> getListShape() {

        if (cache != null) {
            return cache.get("listShape", ListShape.class, (cache) -> supplyListShape()).get();
        }

        return supplyListShape().get();
    }

    protected CmpDistCenter getCacheCmpDistCenter() {

        if (cache != null) {
            return cache.get(CmpDistCenter.class, (cache) -> supplyCmpDistCenter());
        }

        return supplyCmpDistCenter();
    }

    // -------------------------------------------------------------------------------------------------

    protected FCache supplyFCache() {

        return factory.getFCache();
    }

    protected FPoint supplyFPoint() {

        return factory.getFPoint();
    }

    protected FVector supplyFVector() {

        return factory.getFVector();
    }

    protected ListShape supplyListShape() {

        return ListShape.create();
    }

    protected CmpDistCenter supplyCmpDistCenter() {

        return CmpDistCenter.create();
    }

    // -------------------------------------------------------------------------------------------------

    protected enum Relation {
        TRUE, FALSE, UNDEFINED
    }
}

class CmpDistCenter implements Comparator<Shape> {
    private Shape ref;

    private CmpDistCenter() {}

    public static CmpDistCenter create() {

        return new CmpDistCenter();
    }

    public void setRef(Shape ref) {

        this.ref = ref;
    }

    @Override
    public int compare(Shape s1, Shape s2) {
        double distS1 = this.ref.getDistCenterP2(s1);
        double distS2 = this.ref.getDistCenterP2(s2);

        return Double.compare(distS1, distS2);
    }
}

class ListShape {
    private final List<Shape> list = new ArrayList<>(100);

    private ListShape() {}

    public static ListShape create() {

        return new ListShape();
    }

    public List<Shape> get() {

        list.clear();

        return list;
    }
}