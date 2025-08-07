package eu.scattering.core.impl.component.geometry.shape.preset;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.cache.FCache;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static eu.scattering.core.impl.ConfigDef.*;

public abstract class ShapePresetDef implements Shape {
    private final static double SHIFT_OFFSET = -0.25;

    private final ScatFactory factory;
    private FCache cache;

    private double epsilon = SHAPE_EPSILON;
    private double delta = SHAPE_DELTA;

    private final boolean shift = true;

    private int index = -1;
    private String meta = "";

    protected List<Double> coating = new ArrayList<>();

    public ShapePresetDef(ScatFactory factory) {

        this.factory = factory;
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

        return this.meta;
    }

    @Override
    public Shape setMeta(String meta) {

        this.meta = meta;

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

    protected List<Double> getCoating() {

        return this.coating;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Shape removeCoat() {

        getCoating().clear();

        return this;
    }

    @Override
    public Shape addCoat(double width) {

        if (width < 0) {
            throw new IllegalArgumentException("The coat width cannot be lower than zero");
        }

        getCoating().add(width);

        setRadius(getRadius() + width);

        return this;
    }

    @Override
    public Shape addCoat(double... width) {

        for (double v : width) {
            addCoat(v);
        }

        return this;
    }

    @Override
    public Shape addCoatInternal(double width) {

        if (width < 0) {
            throw new IllegalArgumentException("The coat width cannot be lower than zero");
        }

        if (getCoatWidthTotal() + width >= getRadius()) {
            throw new IllegalArgumentException("The total coat width cannot be larger than the radius");
        }

        getCoating().add(width);

        return this;
    }

    @Override
    public Shape addCoatInternal(double... width) {

        for (double v : width) {
            addCoatInternal(v);
        }

        return this;
    }

    @Override
    public Shape applyCoatFrom(Shape shape) {

        removeCoat();

        for (int i = 0; i < shape.getCoatCount() ; i++) {
            addCoat(shape.getCoatWidth(i));
        }

        return this;
    }

    @Override
    public int getCoatCount() {

        return this.coating.size();
    }

    @Override
    public int getLayerCount() {

        return getCoatCount() + 1;
    }

    @Override
    public double getCoatWidth(int index) {

        if (getCoating().size() == 0) {
            throw new IllegalArgumentException("The shape is not coated");
        }

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getCoating().size()) {
            throw new IllegalArgumentException("the coat index is erroneous");
        }

        return this.coating.get(index);
    }

    @Override
    public double getCoatWidthTotal() {

        return getCoating().stream().mapToDouble(Double::doubleValue).sum();
    }

    @Override
    public void setCoatWidth(int index, double width) {

        if (getCoating().size() == 0) {
            throw new IllegalArgumentException("The shape is not coated");
        }

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getCoating().size()) {
            throw new IllegalArgumentException("the coat index is erroneous");
        }

         this.coating.set(index, width);
    }

    @Override
    public boolean isExactCenter(Shape arg) {

        if (this.getCenterX() != arg.getCenterX()) {
            return false;
        }

        if (this.getCenterY() != arg.getCenterY()) {
            return false;
        }

        return this.getCenterZ() == arg.getCenterZ();
    }

    @Override
    public boolean isSimilarCenter(Shape arg) {

        double distanceX = Math.abs(this.getCenterX() - arg.getCenterX());
        double distanceY = Math.abs(this.getCenterY() - arg.getCenterY());
        double distanceZ = Math.abs(this.getCenterZ() - arg.getCenterZ());

        return distanceX < EPSILON && distanceY < EPSILON && distanceZ < EPSILON;
    }

    @Override
    public FPos3D getCenter() {

        return factory.getFPos3D(getCenterX(), getCenterY(), getCenterZ());
    }

    @Override
    public Shape setCenter(double x, double y, double z) {

        setCenterX(x);
        setCenterY(y);
        setCenterZ(z);

        return this;
    }

    @Override
    public Shape setCenter(Shape shape) {

        setCenter(shape.getCenterX(), shape.getCenterY(), shape.getCenterZ());

        return this;
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

        for (int i = 0; i < getCoatCount() ; i++) {
            setCoatWidth(i, getCoatWidth(i) * factor);
        }

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

        return getFPointHelper().getDistance(x, y, z, getCenterX(), getCenterY(), getCenterZ());
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
        double distanceX = getCenterX() - x;
        double distanceY = getCenterY() - y;
        double distanceZ = getCenterZ() - z;

        return (distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ);
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
        FPos3D center = getFPointHelper().setDistance(x, y, z, getCenterX(), getCenterY(), getCenterZ(), dist);

        setCenter(center);

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
    public int locate(FPoint fPoint) {

        return locate(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public int locate(FPos3D fPos3D) {

        return locate(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
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

        reqDist = this.getRadiusInternal() + shape.getRadiusInternal() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.FALSE;
        }

        return this.getRadius() == this.getRadiusInternal() ? Relation.TRUE : Relation.UNDEFINED;
    }

    protected boolean touchesDelta(Shape shape, double delta) {
        FPairPos3D range = getOperationRange(shape);

        boolean ruleTouch = false;

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getPosA().getD0() + offset ; x < range.getPosB().getD0() ; x += delta) {
            for (double y = range.getPosA().getD1() + offset ; y < range.getPosB().getD1() ; y += delta) {
                for (double z = range.getPosA().getD2() + offset ; z < range.getPosB().getD2() ; z += delta) {
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

        reqDist = this.getRadiusInternal() + shape.getRadiusInternal() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getRadiusInternal() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean overlapsDelta(Shape shape) {
        FPairPos3D range = getOperationRange(shape);

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getPosA().getD0() + offset ; x < range.getPosB().getD0() ; x += delta) {
            for (double y = range.getPosA().getD1() + offset ; y < range.getPosB().getD1() ; y += delta) {
                for (double z = range.getPosA().getD2() + offset ; z < range.getPosB().getD2() ; z += delta) {
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

        reqDist = this.getRadiusInternal() - shape.getRadiusInternal() + epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getRadiusInternal() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean enclosesDelta(Shape shape) {
        FPairPos3D range = getOperationRange(shape);

        if (range.getPosA().getD0() == range.getPosB().getD0()) {
            return false;
        }

        if (range.getPosA().getD1() == range.getPosB().getD1()) {
            return false;
        }

        if (range.getPosA().getD2() == range.getPosB().getD2()) {
            return false;
        }

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getPosA().getD0() + offset ; x < range.getPosB().getD0() ; x += delta) {
            for (double y = range.getPosA().getD1() + offset ; y < range.getPosB().getD1() ; y += delta) {
                for (double z = range.getPosA().getD2() + offset ; z < range.getPosB().getD2() ; z += delta) {
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

        reqDist = Math.abs(this.getRadiusInternal() - shape.getRadiusInternal()) - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.TRUE;
        }

        return this.getRadius() == this.getRadiusInternal() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean intersectsDelta(Shape shape, double delta) {
        FPairPos3D range = getOperationRange(shape);

        boolean ruleShapeA = false;
        boolean ruleShapeB = false;
        boolean ruleCommon = false;

        double offset = shift ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getPosA().getD0() + offset ; x < range.getPosB().getD0() ; x += delta) {
            for (double y = range.getPosA().getD1() + offset ; y < range.getPosB().getD1() ; y += delta) {
                for (double z = range.getPosA().getD2() + offset ; z < range.getPosB().getD2() ; z += delta) {
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

    protected FPairPos3D getOperationRange(Shape shape) {
        double opA, opB;

        opA = this.getCenterX() - this.getRadius();
        opB = shape.getCenterX() - shape.getRadius();

        double bX = Math.max(opA, opB);

        opA = this.getCenterX() + this.getRadius();
        opB = shape.getCenterX() + shape.getRadius();

        double hX = Math.min(opA, opB);

        opA = this.getCenterY() - this.getRadius();
        opB = shape.getCenterY() - shape.getRadius();

        double bY = Math.max(opA, opB);

        opA = this.getCenterY() + this.getRadius();
        opB = shape.getCenterY() + shape.getRadius();

        double hY = Math.min(opA, opB);

        opA = this.getCenterZ() - this.getRadius();
        opB = shape.getCenterZ() - shape.getRadius();

        double bZ = Math.max(opA, opB);

        opA = this.getCenterZ() + this.getRadius();
        opB = shape.getCenterZ() + shape.getRadius();

        double hZ = Math.min(opA, opB);

        return factory.getFPairPos3D(bX, bY, bZ, hX, hY, hZ);
    }

    @Override
    public void sortByDistCenter(List<? extends Shape> in) {
        CmpDistCenter cmp = getCacheCmpDistCenter();

        cmp.setRef(this);

        in.sort(cmp);
    }

    @Override
    public void fillOverlapLayer(FLayerCounter in) {
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
    public void fillOverlapLayer(FLayerCounter in, Iterable<? extends Shape> shapes) {
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
    public void fillVolumeLayer(FLayerCounter in) {
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

        int location;
        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    location = locate(x, y, z);

                    if (location >= 0) {
                        in.inc(location);
                    }
                }
            }
        }
    }

    @Override
    public void fillVolumeLayer(FLayerCounter in, Iterable<? extends Shape> shapes) {
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

        List<Shape> prefix = new ArrayList<>();
        List<Shape> suffix = new ArrayList<>();

        catItems(shapes, prefix, suffix);

        int locRef;
        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    locRef = locate(x, y, z);

                    if (locRef < 0) {
                        continue;
                    }

                    if (locRef > getLocArg(prefix, x, y, z)) {
                        continue;
                    }

                    if (locRef < getLocArg(suffix, x, y, z)) {
                        in.inc(locRef);
                    }
                }
            }
        }
    }

    void catItems(Iterable<? extends Shape> list, List<Shape> prefix, List<Shape> suffix) {

        var isFound = new AtomicBoolean(false);
        list.forEach(e -> {
            if (this == e) {
                isFound.set(true);
            } else {
                if (isFound.get()) {
                    suffix.add(e);
                } else {
                    prefix.add(e);
                }
            }
        });

        if (!isFound.get()) {
            throw new IllegalArgumentException("The shape must be a part of the list");
        }
    }

    int getLocArg(List<Shape> shapes, double x, double y, double z) {
        int locArgMin = Integer.MAX_VALUE;

        int locArg;
        for (Shape shape : shapes) {

            if (!overlaps(shape)) {
                continue;
            }

            locArg = shape.locate(x, y, z);

            if (locArg < 0) {
                continue;
            }

            if (locArg < locArgMin) {
                locArgMin = locArg;
            }
        }

        return locArgMin;
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
    public Shape setRadiusMin(Iterable<? extends Shape> shapes) {
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
            double coatWidth = getCoatWidthTotal();

            if (minRadius + EPSILON <= coatWidth) {
                setRadius(coatWidth + EPSILON);
            } else {
                setRadius(minRadius + EPSILON);
            }
        }

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    protected FPointHelper getFPointHelper() {

        return factory.getFPointHelper();
    }

    protected FTrigHelper getFTrigHelper() {

        return factory.getFTrigHelper();
    }

    protected FRotEngine getFRotEngine() {

        return factory.getFRotEngine();
    }

    protected CmpDistCenter getCacheCmpDistCenter() {

        if (cache != null) {
            return cache.get(CmpDistCenter.class, (cache) -> CmpDistCenter.create());
        }

        return CmpDistCenter.create();
    }

    // -------------------------------------------------------------------------------------------------

    protected FVector supplyFVector() {

        return factory.getFVector();
    }

    // -------------------------------------------------------------------------------------------------

    protected enum Relation {
        TRUE, FALSE, UNDEFINED
    }

    static class CmpDistCenter implements Comparator<Shape> {
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
}