package eu.scattering.core.impl.component.geometry.shape.preset;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

public abstract class ShapePresetDef implements Shape {
    private final static double MESH_OFFSET = -0.5;

    private final ScatFactory factory;
    private FCache cache;

    private int index = -1;
    private String tag = "";

    public ShapePresetDef(ScatFactory factory) {

        this.factory = factory;
    }

    @Override
    public void setFCache(FCache cache) {

        this.cache = cache;
        this.cache.put(ScatFactory.class, this.factory);
    }

    @Override
    public void setIndex(int index) {

        this.index = index;
    }

    @Override
    public int getIndex() {

        return this.index;
    }

    @Override
    public void setTag(String tag) {

        this.tag = tag;
    }

    @Override
    public String getTag() {

        return this.tag;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean contains(FPoint fPoint) {

        return contains(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public boolean contains(FPos3D fPos3D) {

        return contains(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
    }

    @Override
    public boolean touches(Shape shape, double epsilon, double delta) {

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
    public boolean overlaps(Shape shape, double epsilon, double delta) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = overlapsEpsilon(shape, epsilon);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return overlapsDelta(shape, delta);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public boolean encloses(Shape shape, double epsilon, double delta) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = enclosesEpsilon(shape, epsilon);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return enclosesDelta(shape, delta);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public boolean intersects(Shape shape, double epsilon, double delta) {

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

    protected Relation enclosesEpsilon(Shape shape, double epsilon) {

        if (this.getRadius() < shape.getRadius()) {
            return Relation.FALSE;
        }

        double distP2 = getDistanceP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getRadiusInner() - shape.getRadiusInner() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 < reqDistP2 ? Relation.TRUE : Relation.UNDEFINED;
    }

    protected Relation enclosesEpsilonSimplified(Shape shape, double epsilon) {

        if (this.getRadius() < shape.getRadius()) {
            return Relation.FALSE;
        }

        double distP2 = getDistanceP2(shape);
        double reqDist = this.getRadius() - shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 < reqDistP2 ? Relation.TRUE : Relation.FALSE;
    }

    protected boolean enclosesDelta(Shape shape, double delta) {
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

        double step = delta * MESH_OFFSET;
        for (double x = range.getBaseX() + step ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + step ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + step ; z < range.getHeadZ() ; z += delta) {
                    if (shape.contains(x, y, z) && !this.contains(x, y, z)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    protected Relation touchesEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistanceP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() + epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getRadiusInner() + shape.getRadiusInner() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 < reqDistP2 ? Relation.FALSE: Relation.UNDEFINED;
    }

    protected Relation touchesEpsilonSimplified(Shape shape, double epsilon) {
        double distP2 = getDistanceP2(shape);

        double reqDistOuter = this.getRadius() + shape.getRadius() + epsilon;
        double reqDistOuterP2 = reqDistOuter < 0 ? 0 : reqDistOuter * reqDistOuter;

        if (distP2 > reqDistOuterP2) {
            return Relation.FALSE;
        }

        double reqDistInner = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistInnerP2 = reqDistInner < 0 ? 0 : reqDistInner * reqDistInner;

        return distP2 < reqDistInnerP2 ? Relation.FALSE: Relation.TRUE;
    }

    protected boolean touchesDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        boolean ruleTouch = false;

        double step = delta * MESH_OFFSET;
        for (double x = range.getBaseX() + step ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + step ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + step ; z < range.getHeadZ() ; z += delta) {
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

    protected Relation overlapsEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistanceP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = this.getRadiusInner() + shape.getRadiusInner() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 < reqDistP2 ? Relation.TRUE: Relation.UNDEFINED;
    }

    protected Relation overlapsEpsilonSimplified(Shape shape, double epsilon) {
        double distP2 = getDistanceP2(shape);

        double reqDistNone = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistNoneP2 = reqDistNone < 0 ? 0 : reqDistNone * reqDistNone;

        return distP2 < reqDistNoneP2 ? Relation.TRUE : Relation.FALSE;
    }

    protected boolean overlapsDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        double step = delta * MESH_OFFSET;
        for (double x = range.getBaseX() + step ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + step ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + step ; z < range.getHeadZ() ; z += delta) {
                    if (this.contains(x, y, z) && shape.contains(x, y, z)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    protected Relation intersectsEpsilon(Shape shape, double epsilon) {
        double distP2 = getDistanceP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = Math.abs(this.getRadiusInner() - shape.getRadiusInner()) - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 > reqDistP2 ? Relation.TRUE : Relation.UNDEFINED;
    }

    protected Relation intersectsEpsilonSimplified(Shape shape, double epsilon) {
        double distP2 = getDistanceP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.FALSE;
        }

        reqDist = Math.abs(this.getRadius() - shape.getRadius()) - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 > reqDistP2 ? Relation.TRUE : Relation.FALSE;
    }

    protected boolean intersectsDelta(Shape shape, double delta) {
        FVector range = getOperationRange(shape);

        boolean ruleShapeA = false;
        boolean ruleShapeB = false;
        boolean ruleCommon = false;

        double step = delta * MESH_OFFSET;
        for (double x = range.getBaseX() + step ; x < range.getHeadX() ; x += delta) {
            for (double y = range.getBaseY() + step ; y < range.getHeadY() ; y += delta) {
                for (double z = range.getBaseZ() + step ; z < range.getHeadZ() ; z += delta) {
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
        FPoint centerA = getCacheFPointA();
        FPoint centerB = getCacheFPointB();

        this.getCenter(centerA);
        shape.getCenter(centerB);

        FVector range = getCacheFVectorA();

        double opA, opB;

        opA = centerA.getX() - this.getRadius();
        opB = centerB.getX() - shape.getRadius();

        range.setBaseX(Math.max(opA, opB));

        opA = centerA.getX() + this.getRadius();
        opB = centerB.getX() + shape.getRadius();

        range.setHeadX(Math.min(opA, opB));

        opA = centerA.getY() - this.getRadius();
        opB = centerB.getY() - shape.getRadius();

        range.setBaseY(Math.max(opA, opB));

        opA = centerA.getY() + this.getRadius();
        opB = centerB.getY() + shape.getRadius();

        range.setHeadY(Math.min(opA, opB));

        opA = centerA.getZ() - this.getRadius();
        opB = centerB.getZ() - shape.getRadius();

        range.setBaseZ(Math.max(opA, opB));

        opA = centerA.getZ() + this.getRadius();
        opB = centerB.getZ() + shape.getRadius();

        range.setHeadZ(Math.min(opA, opB));

        return range;
    }

    @Override
    public void getVolumeBuffer(FStream3D stream, double delta) {
        FPoint center = getCacheFPointA();

        getCenter(center);

        double radiusParsed = getRadius() + delta;
        double radiusP2 = getRadius() * getRadius();

        double cX = center.getX();
        double cY = center.getY();
        double cZ = center.getZ();

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
        FPoint center = getCacheFPointA();

        getCenter(center);

        double factor = 1 / delta;

        double radiusParsed = factor * (getRadius() + delta);
        double radiusP2 = (factor * getRadius()) * (factor * getRadius());

        double cX = factor * center.getX();
        double cY = factor * center.getY();
        double cZ = factor * center.getZ();

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

    protected FPoint getCacheFPointA() {

        if (cache != null) {
            return cache.get("fPointA", FPoint.class,
                    (core) -> core.get(ScatFactory.class).getFPoint());
        }

        return factory.getFPoint();
    }

    protected FPoint getCacheFPointB() {

        if (cache != null) {
            return cache.get("fPointB", FPoint.class,
                    (core) -> core.get(ScatFactory.class).getFPoint());
        }

        return factory.getFPoint();
    }

    protected FVector getCacheFVectorA() {

        if (cache != null) {
            return cache.get("fVectorA", FVector.class,
                    (core) -> core.get(ScatFactory.class).getFVector());
        }

        return factory.getFVector();
    }

    protected FVector getCacheFVectorB() {

        if (cache != null) {
            return cache.get("fVectorB", FVector.class,
                    (core) -> core.get(ScatFactory.class).getFVector());
        }

        return factory.getFVector();
    }

    protected double getDistanceP2(Shape shape) {
        FPoint centerA = getCacheFPointA();
        FPoint centerB = getCacheFPointB();

        this.getCenter(centerA);
        shape.getCenter(centerB);

        return centerA.getDistanceP2(centerB);
    }

    // -------------------------------------------------------------------------------------------------

    protected enum Relation {
        TRUE, FALSE, UNDEFINED
    }
}
