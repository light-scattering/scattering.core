package eu.scattering.core.impl.component.geometry.shape.preset;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointHelper;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.transfer.complex.FMetaData;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.*;

public abstract class ShapePresetDef implements Shape {
    private final static boolean SHIFT_GEOMETRY = true;
    private final static double SHIFT_OFFSET = -1d / 3;
    private final static int LAYER_LIMIT = 6;

    protected final ScatFactory factory;

    private final List<FMetaData> metaData = new ArrayList<>();
    private final List<Double> coatData = new ArrayList<>();

    private double epsilon = SHAPE_EPSILON;
    private double delta = SHAPE_DELTA;

    private double index = -1;

    private CmpDistCenter cmpDistCenter;

    public ShapePresetDef(ScatFactory factory) {

        this.factory = factory;

        for (int i = 0 ; i < LAYER_LIMIT ; i++) {
            metaData.add(FMetaData.crete("", i));
        }
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
    public String getMeta(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower then zero");
        }

        if (index > LAYER_LIMIT) {
            throw new IllegalArgumentException("The index cannot be greater than the number of layers");
        }

        return this.metaData.get(index).getMeta();
    }

    @Override
    public Shape setMeta(String... meta) {

        if (meta.length > this.metaData.size()) {
            throw new IllegalArgumentException("The number of layers cannot exceed the limit (" + LAYER_LIMIT + ")");
        }

        for (int i = 0; i < meta.length ; i++) {
            this.metaData.get(i).setMeta(meta[i]);
        }

        return this;
    }

    @Override
    public double getIndex() {

        return this.index;
    }

    @Override
    public Shape setIndex(double index) {

        this.index = index;

        return this;
    }

    //--- Module - Interaction

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
            double coatWidth = getLayerWidthRemaining(0);

            if (minRadius + EPSILON <= coatWidth) {
                setRadius(coatWidth + EPSILON);
            } else {
                setRadius(minRadius + EPSILON);
            }
        }

        return this;
    }

    //--- Module - Composition

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
    public List<FMetaData> getMetaData() {

        return this.metaData;
    }

    //--- Module - Position

    @Override
    public FPos3D getCenter() {

        return getRefCenter().toFPos3D();
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
    public void sortByDistCenter(List<? extends Shape> in) {
        CmpDistCenter cmp = getCacheCmpDistCenter();

        cmp.setRef(this);

        in.sort(cmp);
    }

    @Override
    public Shape scalePosition(double factor) {

        setCenter(
                getCenterX() * factor,
                getCenterY() * factor,
                getCenterZ() * factor
        );

        return this;
    }

    //--- Module - Relation

    @Override
    public boolean repels(Shape shape) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = repelsEpsilon(shape);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return repelsDelta(shape);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int repels(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (repels(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
    }

    protected Relation repelsEpsilon(Shape shape) {
        double distP2 = getDistCenterP2(shape);

        double reqDist = this.getRadius() + shape.getRadius() + epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return Relation.TRUE;
        }

        reqDist = this.getInnerRadius() + shape.getInnerRadius() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 < reqDistP2) {
            return Relation.FALSE;
        }

        return this.getRadius() == this.getInnerRadius() ? Relation.FALSE : Relation.UNDEFINED;
    }

    protected boolean repelsDelta(Shape shape) {
        FPairPos3D range = getOperationRange(shape);

        double offset = SHIFT_GEOMETRY ? delta * SHIFT_OFFSET : 0;
        for (double x = range.getPosA().getD0() + offset ; x < range.getPosB().getD0() ; x += delta) {
            for (double y = range.getPosA().getD1() + offset ; y < range.getPosB().getD1() ; y += delta) {
                for (double z = range.getPosA().getD2() + offset ; z < range.getPosB().getD2() ; z += delta) {
                    boolean containsA = this.contains(x, y, z);
                    boolean containsB = shape.contains(x, y, z);

                    if (containsA && containsB) {
                        return false;
                    }

                    if (containsA) {
                        if (shape.contains(x + delta, y, z) ||
                                shape.contains(x, y + delta, z) ||
                                shape.contains(x, y, z + delta)) {
                            return false;
                        }
                    }

                    if (containsB) {
                        if (this.contains(x + delta, y, z) ||
                                this.contains(x, y + delta, z) ||
                                this.contains(x, y, z + delta)) {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean touches(Shape shape) {

        if (epsilon <= 0 && delta <= 0) {
            throw new IllegalArgumentException("At least one precision parameter must be defined");
        }

        if (epsilon > 0) {
            Relation relation = touchesEpsilon(shape);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return touchesDelta(shape);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int touches(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (touches(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
    }

    protected Relation touchesEpsilon(Shape shape) {
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

    protected boolean touchesDelta(Shape shape) {
        FPairPos3D range = getOperationRange(shape);

        boolean ruleTouch = false;

        double offset = SHIFT_GEOMETRY ? delta * SHIFT_OFFSET : 0;
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
    public int overlaps(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (overlaps(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
    }

    protected Relation overlapsEpsilon(Shape shape) {
        double distMax = this.getRadius() + shape.getRadius();

        double distX = Math.abs(this.getCenterX() - shape.getCenterX());

        if (distX > distMax + epsilon) {
            return Relation.FALSE;
        }

        double distY = Math.abs(this.getCenterY() - shape.getCenterY());

        if (distY > distMax + epsilon) {
            return Relation.FALSE;
        }

        double distZ = Math.abs(this.getCenterZ() - shape.getCenterZ());

        if (distZ > distMax + epsilon) {
            return Relation.FALSE;
        }

        double distP2 = (distX * distX) + (distY * distY) + (distZ * distZ);

        double reqDist = distMax - epsilon;
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
        FPairPos3D range = getOperationRange(shape);

        double offset = SHIFT_GEOMETRY ? delta * SHIFT_OFFSET : 0;
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
    public int encloses(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (encloses(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
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

        double offset = SHIFT_GEOMETRY ? delta * SHIFT_OFFSET : 0;
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
            Relation relation = intersectsEpsilon(shape);

            if (relation == Relation.TRUE) {
                return true;
            }

            if (relation == Relation.FALSE) {
                return false;
            }
        }

        if (delta > 0) {
            return intersectsDelta(shape);
        }

        throw new IllegalStateException("The problem cannot be solved with direct equations");
    }

    @Override
    public int intersects(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (intersects(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
    }

    protected Relation intersectsEpsilon(Shape shape) {
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

    protected boolean intersectsDelta(Shape shape) {
        FPairPos3D range = getOperationRange(shape);

        boolean ruleShapeA = false;
        boolean ruleShapeB = false;
        boolean ruleCommon = false;

        double offset = SHIFT_GEOMETRY ? delta * SHIFT_OFFSET : 0;
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

    @Override
    public boolean touchesOrRepels(Shape shape) {

        return !overlaps(shape);
    }

    @Override
    public int touchesOrRepels(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (!overlaps(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
    }

    @Override
    public boolean touchesOrOverlaps(Shape shape) {

        return !repels(shape);
    }

    @Override
    public int touchesOrOverlaps(Iterable<? extends Shape> shapes, List<Shape> in) {
        int count = 0;

        if (in != null) {
            in.clear();
        }

        for (Shape shape : shapes) {

            if (this == shape) {
                continue;
            }

            if (!repels(shape)) {
                count++;

                if (in != null) {
                    in.add(shape);
                }
            }
        }

        return count;
    }

    //--- Module - Dimension

    @Override
    public double getCoatWidth(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The coat index cannot be lower than zero");
        }

        if (index >= getCoatCount()) {
            throw new IllegalArgumentException("The coat index is invalid");
        }

        return getCoatWidth().get(index);
    }

    @Override
    public Shape setCoatWidth(int index, double width) {

        if (index < 0) {
            throw new IllegalArgumentException("The coat index cannot be lower than zero");
        }

        if (index >= getCoatWidth().size()) {
            throw new IllegalArgumentException("the coat index is invalid");
        }

        getCoatWidth().set(index, width);

        return this;
    }

    @Override
    public int getLayerCount() {

        return getCoatWidth().size() + 1;
    }

    @Override
    public int getCoatCount() {

        return getCoatWidth().size();
    }

    @Override
    public Shape applyCoatsFrom(Shape shape) {

        removeCoats();

        for (int i = 0; i < shape.getLayerCount() - 1 ; i++) {
            addCoat(shape.getCoatWidth(i));
        }

        return this;
    }

    @Override
    public Shape addCoat(double width) {

        if (width < 0) {
            throw new IllegalArgumentException("The coat width cannot be lower than zero");
        }

        if (getLayerCount() >= LAYER_LIMIT) {
            throw new IllegalStateException("The number of layers cannot exceed the limit (" + LAYER_LIMIT + ")");
        }

        getCoatWidth().add(width);

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

        if (getLayerCount() >= LAYER_LIMIT) {
            throw new IllegalStateException("The number of layers cannot exceed the limit (" + LAYER_LIMIT + ")");
        }

        if (getLayerWidthRemaining(0) + width >= getRadius()) {
            throw new IllegalStateException("The total coat width cannot be larger than the radius");
        }

        getCoatWidth().add(width);

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
    public Shape removeCoats() {

        getCoatWidth().clear();

        return this;
    }

    @Override
    public Shape scaleSize(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The factor must be a positive value");
        }

        setRadius(getRadius() * factor);

        for (int i = 0; i < getCoatCount() ; i++) {
            setCoatWidth(i, getCoatWidth(i) * factor);
        }

        return this;
    }

    @Override
    public double getLayerWidthRemaining(int index) {

        if (index < 0) {
            throw new IllegalArgumentException("The index cannot be lower than zero");
        }

        if (index >= getLayerCount()) {
            throw new IllegalArgumentException("the layer index is erroneous");
        }

        if (index == 0) {
            return getCoatWidth().stream().mapToDouble(Double::doubleValue).sum();
        }

        double width = 0;
        for (int i = getLayerCount() - 1 ; i > index ; i--) {
            width += getCoatWidth(i - 1);
        }

        return width;
    }

    @Override
    public double fillVolumeLayerOverlap(FLayer in, Iterable<? extends Shape> field) {
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

                        for (Shape shape : field) {

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

        return delta * delta * delta;
    }

    @Override
    public double fillVolumeLayer(FLayer in) {
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

        return delta * delta * delta;
    }

    @Override
    public double fillVolumeLayer(FLayer in, List<? extends Shape> structure) {
        int position = structure.indexOf(this);

        if (position == -1) {
            throw new IllegalArgumentException("The shape must be a part of the structure");
        }

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

        for (int i = 0 ; i < position ; i++) {
            prefix.add(structure.get(i));
        }

        for (int i = position + 1 ; i < structure.size() ; i++) {
            suffix.add(structure.get(i));
        }

        int location;
        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    location = locate(x, y, z);

                    if (location < 0) {
                        continue;
                    }

                    if (location > locateInStructure(prefix, x, y, z)) {
                        continue;
                    }

                    if (location < locateInStructure(suffix, x, y, z)) {
                        in.inc(location);
                    }
                }
            }
        }

        return delta * delta * delta;
    }

    @Override
    public double fillVolumeArray(FBuffer<FMetaData> in) {
        List<FMetaData> metaData = getMetaData();

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
                        in.addWithDataAndMeta(x, y, z, delta, metaData.get(location));
                    }
                }
            }
        }

        return delta * delta * delta;
    }

    @Override
    public double fillVolumeArray(FBuffer<FMetaData> in, List<? extends Shape> structure) {
        int position = structure.indexOf(this);

        if (position == -1) {
            throw new IllegalArgumentException("The shape must be a part of the structure");
        }

        List<FMetaData> metaData = getMetaData();

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

        for (int i = 0 ; i < position ; i++) {
            prefix.add(structure.get(i));
        }

        for (int i = position + 1 ; i < structure.size() ; i++) {
            suffix.add(structure.get(i));
        }

        int location;
        for (double x = minX ; x < maxX ; x += delta) {
            for (double y = minY ; y < maxY ; y += delta) {
                for (double z = minZ ; z < maxZ ; z += delta) {
                    location = locate(x, y, z);

                    if (location < 0) {
                        continue;
                    }

                    if (location > locateInStructure(prefix, x, y, z)) {
                        continue;
                    }

                    if (location < locateInStructure(suffix, x, y, z)) {
                        in.addWithDataAndMeta(x, y, z, delta, metaData.get(location));
                    }
                }
            }
        }

        return delta * delta * delta;
    }

    int locateInStructure(List<Shape> structure, double x, double y, double z) {
        int locArgMin = Integer.MAX_VALUE;

        int locArg;
        for (Shape shape : structure) {

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

    // -------------------------------------------------------------------------------------------------

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

    protected List<Double> getCoatWidth() {

        return this.coatData;
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

        if (this.cmpDistCenter == null) {
            this.cmpDistCenter = CmpDistCenter.create();
        }

        return this.cmpDistCenter;
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