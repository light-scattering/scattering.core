package eu.scattering.core.impl.component.geometry.shape.preset;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.Comparator;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.*;

public abstract class ShapePresetDef implements Shape {
    private final static double MESH_OFFSET = -0.5;

    private final ScatFactory factory;
    private FCache cache;

    private double epsilon = SHAPE_EPSILON;
    private double delta = SHAPE_DELTA;

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
    public Shape setIndex(int index) {

        this.index = index;

        return this;
    }

    @Override
    public int getIndex() {

        return this.index;
    }

    @Override
    public Shape setTag(String tag) {

        this.tag = tag;

        return this;
    }

    @Override
    public String getTag() {

        return this.tag;
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

        if (getCenterZ() != arg.getCenterZ()) {
            return false;
        }

        return true;
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
    public double getDistCenter(Shape shape) {
        FVector fVector = getCacheFVector();

        fVector.setBase(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());
        fVector.setHead(getCenterX(), getCenterY(), getCenterZ());

        return fVector.getMagnitude();
    }

    @Override
    public double getDistCenterP2(Shape shape) {
        double dimX = getCenterX() - shape.getCenterX();
        double dimY = getCenterY() - shape.getCenterY();
        double dimZ = getCenterZ() - shape.getCenterZ();

        return (dimX * dimX) + (dimY * dimY) + (dimZ * dimZ);
    }

    @Override
    public Shape setDistCenter(Shape shape, double dist) {
        FVector fVector = getCacheFVector();

        fVector.setBase(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());
        fVector.setHead(getCenterX(), getCenterY(), getCenterZ());

        fVector.setMagnitude(dist);

        setCenter(fVector.getRefHead());

        return this;
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

        reqDist = this.getRadiusInner() - shape.getRadiusInner() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getRadiusInner() ? Relation.FALSE : Relation.UNDEFINED;
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

        double offset = delta * MESH_OFFSET;
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

    protected Relation touchesEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() + epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getRadiusInner() + shape.getRadiusInner() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.FALSE;
        }

        return this.getRadius() == this.getRadiusInner() ? Relation.TRUE : Relation.UNDEFINED;
    }

    protected boolean touchesDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        boolean ruleTouch = false;

        double offset = -delta + (delta * MESH_OFFSET);
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

    protected Relation overlapsEpsilon(Shape shape) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getRadiusInner() + shape.getRadiusInner() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getRadiusInner() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean overlapsDelta(Shape shape) {
        FVector range = getOperationRange(shape);

        double offset = delta * MESH_OFFSET;
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

    protected Relation intersectsEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = Math.abs(this.getRadiusInner() - shape.getRadiusInner()) - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getRadiusInner() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean intersectsDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        boolean ruleShapeA = false;
        boolean ruleShapeB = false;
        boolean ruleCommon = false;

        double offset = delta * MESH_OFFSET;
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

    @Override
    public void  getOverlappingShapes(List<Shape> in, List<? extends Shape> field) {
        in.clear();

        for (Shape shape : field) {

            if (this == shape) {
                continue;
            }

            if (overlaps(shape)) {
                in.add(shape);
            }
        }
    }

    public void sortByDistance(List<? extends Shape> in) {
        CmpDistRef cmp = getCacheCmpDistRef();

        cmp.setRef(this);

        in.sort(cmp);
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
    public void getVolumeBuffer(FStream3D stream, double delta) {
        double radiusParsed = getRadius() + delta;
        double radiusP2 = getRadius() * getRadius();

        double cX = getCenterX();
        double cY = getCenterY();
        double cZ = getCenterZ();

        double minX = cX - radiusParsed;
        double maxX = cX + radiusParsed;
        double minY = cY - radiusParsed;
        double maxY = cY + radiusParsed;
        double minZ = cZ - radiusParsed;
        double maxZ = cZ + radiusParsed;

        double tX, tXP2;
        double tY, tYP2;
        double tZ, tZP2;

        stream.reset();

        for (double x = minX ; x <= maxX ; x += delta) {
            tX = x - cX;
            tXP2 = tX * tX;

            for (double y = minY ; y <= maxY ; y += delta) {
                tY = y - cY;
                tYP2 = tY * tY;

                for (double z = minZ ; z <= maxZ ; z += delta) {
                    tZ = z - cZ;
                    tZP2 = tZ * tZ;

                    if (tXP2 + tYP2 + tZP2 <= radiusP2) {
                        stream.add(x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public void getVolumeBuffer(FStream3DI stream, double delta) {
        double factor = 1 / delta;

        double radiusParsed = factor * (getRadius() + delta);
        double radiusP2 = (factor * getRadius()) * (factor * getRadius());

        double cX = factor * getCenterX();
        double cY = factor * getCenterY();
        double cZ = factor * getCenterZ();

        int minX = (int) Math.floor(cX - radiusParsed);
        int maxX = (int) Math.ceil(cX + radiusParsed);
        int minY = (int) Math.floor(cY - radiusParsed);
        int maxY = (int) Math.ceil(cY + radiusParsed);
        int minZ = (int) Math.floor(cZ - radiusParsed);
        int maxZ = (int) Math.ceil(cZ + radiusParsed);

        int tX, tXP2;
        int tY, tYP2;
        int tZ, tZP2;

        stream.reset();

        for (int x = minX ; x <= maxX ; x++) {
            tX = (int) (x - cX);
            tXP2 = tX * tX;

            for (int y = minY ; y <= maxY ; y++) {
                tY = (int) (y - cY);
                tYP2 = tY * tY;

                for (int z = minZ ; z <= maxZ ; z++) {
                    tZ = (int) (z - cZ);
                    tZP2 = tZ * tZ;

                    if (tXP2 + tYP2 + tZP2 <= radiusP2) {
                        stream.add(x, y, z);
                    }
                }
            }
        }
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

    protected CmpDistRef getCacheCmpDistRef() {

        if (cache != null) {
            return cache.get("cmpDistRef", CmpDistRef.class, (cache) -> supplyCmpDistRef());
        }

        return supplyCmpDistRef();
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

    protected CmpDistRef supplyCmpDistRef() {

        return CmpDistRef.create();
    }

    // -------------------------------------------------------------------------------------------------

    protected enum Relation {
        TRUE, FALSE, UNDEFINED
    }
}

class CmpDistRef implements Comparator<Shape> {
    private Shape ref;

    private CmpDistRef() {}

    public static CmpDistRef create() {

        return new CmpDistRef();
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