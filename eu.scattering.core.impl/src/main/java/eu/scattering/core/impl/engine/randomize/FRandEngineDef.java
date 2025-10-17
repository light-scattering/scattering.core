package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.component.geometry.GeometryFactory;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.engine.randomize.FRandEngine;
import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.transfer.primitive.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRandEngineDef implements FRandEngine {
    private final FRandGenerator core;
    private final GeometryFactory factory;

    private FRandEngineDef(FRandGenerator core, GeometryFactory factory) {

        this.core = core;
        this.factory = factory;
    }

    public static FRandEngine create(FRandGenerator core, GeometryFactory factory) {

        return new FRandEngineDef(core, factory);
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator getFRand() {

        return this.core;
    }

    @Override
    public FComplex inRange(FComplex in, FPairPos2D range) {

        in.applyStateFrom(core.nextDouble2D(range));

        return in;
    }

    @Override
    public FComplex inCircle(FComplex in, double radius) {

        in.applyStateFrom(core.nextDoubleInCircle(radius));

        return in;
    }

    @Override
    public FComplex onCircle(FComplex in, double radius) {

        in.applyStateFrom(core.nextDoubleOnCircle(radius));

        return in;
    }

    @Override
    public FQuaternion inRange(FQuaternion in, FPairPos4D range) {

        in.applyStateFrom(core.nextDouble4D(range));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint onSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint inRange(FPoint in, FPairPos3D range) {

        in.applyStateFrom(core.nextDouble3D(range));

        return in;
    }

    @Override
    public FPoint inSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint inSphere(FPoint in, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint onSphere(FPoint in, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in) {

        in.setMagnitude(core.nextDouble(EPSILON, in.getMagnitude()));

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in, FPoint axis) {

        in.applyStateFrom(axis);
        in.setMagnitude(core.nextDouble(EPSILON, axis.getMagnitude()));

        return in;
    }

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FPoint dir, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint ortToBaseOnCircle(FPoint in, FPoint dir, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint ortToHeadInCircle(FPoint in, FPoint dir, double radius) {

        ortToBaseInCircle(in, dir, radius);

        in.add(dir);

        return in;
    }

    @Override
    public FPoint ortToHeadOnCircle(FPoint in, FPoint dir, double radius) {

        ortToBaseOnCircle(in, dir, radius);

        in.add(dir);

        return in;
    }

    @Override
    public FVector onSphere(FVector in) {
        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();

        in.moveBaseToCenter();
        onSphere(in.getRefHead());
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FVector inSphere(FVector in) {
        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();

        in.moveBaseToCenter();
        inSphere(in.getRefHead());
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in, FVector axis) {

        in.applyStateFrom(axis.getRefHead());
        in.sub(axis.getRefBase());
        in.setMagnitude(core.nextDouble(EPSILON, in.getMagnitude()));
        in.add(axis.getRefBase());

        return in;
    }

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint ortToBaseOnCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint ortToHeadInCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleInSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefHead());

        return in;
    }

    @Override
    public FPoint ortToHeadOnCircle(FPoint in, FVector dir, double radius) {

        in.applyStateFrom(core.nextDoubleOnSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefHead());

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FRay dir, double radius) {

        return ortToBaseInCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint ortToBaseOnCircle(FPoint in, FRay dir, double radius) {

        return ortToBaseOnCircle(in, dir.getRefOrigin(), radius);
    }

    @Override
    public FPoint onSegment(FPoint in, FSegment ref) {

        return onAxis(in, ref.getRefOrigin());
    }

    @Override
    public FPoint ortToPosAInCircle(FPoint in, FSegment ref, double radius) {

        return ortToBaseInCircle(in, ref.getRefOrigin(), radius);
    }

    @Override
    public FPoint ortToPosAOnCircle(FPoint in, FSegment ref, double radius) {

        return ortToBaseOnCircle(in, ref.getRefOrigin(), radius);
    }

    @Override
    public FPoint ortToPosBInCircle(FPoint in, FSegment ref, double radius) {

        return ortToHeadInCircle(in, ref.getRefOrigin(), radius);
    }

    @Override
    public FPoint ortToPosBOnCircle(FPoint in, FSegment ref, double radius) {

        return ortToHeadOnCircle(in, ref.getRefOrigin(), radius);
    }

    //--------------------------------------------------

    @Override
    public boolean attachLinear(Shape in, Shape target) {

        if (in == target) {
            return false;
        }

        in.setCenter(this.core.nextDoubleOnSphere((in.getRadius() + target.getRadius() * 2)));
        in.translate(target.getCenterX(), target.getCenterY(), target.getCenterZ());

        return in.attachLinear(target);
    }

    @Override
    public boolean attachLinear2D(Shape in, Shape target) {

        if (in == target) {
            return false;
        }

        FPos2D position = this.core.nextDoubleOnCircle((in.getRadius() + target.getRadius() * 2));

        in.setCenter(position.getD0(), position.getD1(), 0);
        in.translate(target.getCenterX(), target.getCenterY(), target.getCenterZ());

        return in.attachLinear(target);
    }

    @Override
    public boolean attachLinear(Shape in, Shape target, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            boolean results = attachLinear(in, target);

            if (!results) {
                continue;
            }

            if (in.overlaps(field) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean attachLinear2D(Shape in, Shape target, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            boolean results = attachLinear2D(in, target);

            if (!results) {
                continue;
            }

            if (in.overlaps(field) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean attachSpherical(Shape in, Shape target, double x, double y, double z) {

        if (in == target) {
            return false;
        }

        double dist = in.getDistCenter(x, y, z);

        in.setCenter(this.core.nextDoubleOnSphere(dist));
        in.translate(x, y, z);

        return in.attachSpherical(target, x, y, z);
    }

    @Override
    public boolean attachSpherical2D(Shape in, Shape target, double x, double y, double z) {

        if (in == target) {
            return false;
        }

        double dist = in.getDistCenter(x, y, z);

        FPos2D position = this.core.nextDoubleOnCircle(dist);

        in.setCenter(position.getD0(), position.getD1(), 0);
        in.translate(x, y, z);

        return in.attachSpherical(target, x, y, z);
    }

    @Override
    public boolean attachSpherical(Shape in, Shape target, FPoint center) {

        return attachSpherical(in, target, center.getX(), center.getY(), center.getZ());
    }

    @Override
    public boolean attachSpherical(Shape in, Shape target, FPos3D center) {

        return attachSpherical(in, target, center.getD0(), center.getD1(), center.getD2());
    }

    @Override
    public boolean attachSpherical(Shape in, Shape target, double x, double y, double z, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            boolean results = attachSpherical(in, target, x, y, z);

            if (!results) {
                continue;
            }

            if (in.overlaps(field) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean attachSpherical2D(Shape in, Shape target, double x, double y, double z, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            boolean results = attachSpherical2D(in, target, x, y, z);

            if (!results) {
                continue;
            }

            if (in.overlaps(field) == 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean attachSpherical2D(Shape in, Shape target, FPoint center, Iterable<? extends Shape> field, int corrections) {

        return attachSpherical2D(in, target, center.getX(), center.getY(), center.getZ(), field, corrections);
    }

    @Override
    public boolean attachSpherical(Shape in, Shape target, FPoint center, Iterable<? extends Shape> field, int corrections) {

        return attachSpherical(in, target, center.getX(), center.getY(), center.getZ(), field, corrections);
    }

    @Override
    public boolean attachSpherical(Shape in, Shape target, FPos3D center, Iterable<? extends Shape> field, int corrections) {

        return attachSpherical(in, target, center.getD0(), center.getD1(), center.getD2(), field, corrections);
    }

    @Override
    public boolean attachLinearAndSpherical(Shape in, Shape target, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        boolean isLinear = attachLinear(in, target, field, corrections);

        if (isLinear) {
            return true;
        }

        List<Shape> candidates = new ArrayList<>();
        in.getCollisionListSpherical(candidates, field, target.getCenter());

        if (candidates.size() == 0) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            Shape candidate = getFRand().getElement(candidates, false);

            if (in == candidate) {
                continue;
            }

            boolean isSpherical = attachSpherical(in, candidate, target.getCenterX(), target.getCenterY(), target.getCenterZ(), field, corrections);

            if (isSpherical) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean project(Shape in, Shape range, Iterable<? extends Shape> field, int corrections) {
        FRay ray = factory.getFRay();

        int iterations = 0;

        while (iterations++ <= corrections) {
            ray.getRefOrigin()
                    .setBase(core.nextDoubleOnSphere(10 * range.getRadius()))
                    .setHead(core.nextDoubleInSphere(range.getRadius()))
                    .translate(range.getCenter());

            boolean isPositioned = in.project(field, ray);

            if (isPositioned) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean project2D(Shape in, Shape range, Iterable<? extends Shape> field, int corrections) {
        FRay ray = factory.getFRay();

        int iterations = 0;

        while (iterations++ <= corrections) {
            FPos2D posBase = core.nextDoubleOnCircle(10 * range.getRadius());
            FPos2D posHead = core.nextDoubleInCircle(range.getRadius());

            ray.getRefOrigin()
                    .setBase(posBase.getD0(), posBase.getD1(), 0)
                    .setHead(posHead.getD0(), posHead.getD1(), 0)
                    .translate(range.getCenter());

            boolean isPositioned = in.project(field, ray);

            if (isPositioned) {
                return true;
            }
        }

        return false;
    }
}
