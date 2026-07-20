package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.MassCenter;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FRandAspectDef implements FRandAspect {
    private final FRandGenerator core;
    private final ScatterFactory factory;

    private final FRandAspectModuleFAggregateDef moduleFAggregate;

    private FRandAspectDef(FRandGenerator core, ScatterFactory factory) {

        this.core = core;
        this.factory = factory;

        this.moduleFAggregate = FRandAspectModuleFAggregateDef.create(factory);
    }

    public static FRandAspect create(FRandGenerator core, ScatterFactory factory) {

        return new FRandAspectDef(core, factory);
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

        in.set(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint inRange(FPoint in, FPairPos3D range) {

        in.set(core.nextDouble3D(range));

        return in;
    }

    @Override
    public FPoint inSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.set(core.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint inSphere(FPoint in, double radius) {

        in.set(core.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint onSphere(FPoint in, double radius) {

        in.set(core.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in) {

        in.setMagnitude(core.nextDouble(EPSILON, in.getMagnitude()));

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in, FPoint axis) {

        in.set(axis);
        in.setMagnitude(core.nextDouble(EPSILON, axis.getMagnitude()));

        return in;
    }

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FPoint dir, double radius) {

        in.set(core.nextDoubleInSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint ortToBaseOnCircle(FPoint in, FPoint dir, double radius) {

        in.set(core.nextDoubleOnSphere(radius));
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

        in.set(axis.getRefHead());
        in.sub(axis.getRefBase());
        in.setMagnitude(core.nextDouble(EPSILON, in.getMagnitude()));
        in.add(axis.getRefBase());

        return in;
    }

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FVector dir, double radius) {

        in.set(core.nextDoubleInSphere(radius));

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

        in.set(core.nextDoubleOnSphere(radius));

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

        in.set(core.nextDoubleInSphere(radius));

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

        in.set(core.nextDoubleOnSphere(radius));

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

        in.setCenter(this.core.nextDoubleOnCircle(dist), 0);
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
        in.getAttachSphericalCollisions(candidates, field, target.getCenter());

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
    public double project(Shape in, FPos3D center, double radius, Iterable<? extends Shape> field, int corrections) {
        FVector vectorRnd = this.factory.getFVector();
        FPoint baseRnd = vectorRnd.getRefBase();
        FPoint headRnd = vectorRnd.getRefHead();
        FVector vectorDir = this.factory.getFVector();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        for (int i = 0 ; i < corrections ; i++) {
            FPos3D pos3D = this.factory.getFRand().nextDoubleOnSphere(4 * radius);

            baseRnd.set(0, 0, 0);
            headRnd.set(pos3D);

            vectorRnd.moveBase(center);

            this.factory.getRandAspect().ortToBaseInCircle(headDir, vectorRnd, radius);

            baseDir.set(headRnd);

            double distance = in.projectFrom(field, vectorDir);

            if (distance >= 0) {
                return distance;
            }
        }

        return -1;
    }

    @Override
    public double project2D(Shape in, FPos3D center, double radius, Iterable<? extends Shape> field, int corrections) {

        if (center.getD2() > EPSILON || center.getD2() < -EPSILON) {
            throw new IllegalArgumentException("The center should be two dimensional");
        }

        FVector vectorDir = this.factory.getFVector();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        for (int i = 0 ; i < corrections ; i++) {
            FPos2D pos2D = this.factory.getFRand().nextDoubleOnCircle(4 * radius);
            double pos1D = this.factory.getFRand().nextDouble(-radius, radius);

            baseDir.set(pos2D, 0);
            headDir.set(pos1D, 0, 0);

            this.factory.getRotAspect().setRgAngle(headDir, baseDir, Math.PI * 0.5);

            vectorDir.translate(center);

            double distance = in.projectFrom(field, vectorDir);

            if (distance >= 0) {
                return distance;
            }
        }

        return -1;
    }

    //--------------------------------------------------

    @Override
    public void moveMassCenter(FAggregate ref, FAggregate arg, MassCenter type, double distance) {

        moduleFAggregate.moveMassCenter(ref, arg, type, distance);
    }

    @Override
    public void moveMassCenterOnSurface(FAggregate ref, FAggregate arg, MassCenter type, double distance) {

        moduleFAggregate.moveMassCenterOnSurface(ref, arg, type, distance);
    }

    @Override
    public boolean rotate(FAggregate ref, FAggregate arg, FPoint cRef, FPoint cArg, int corrections) {

        return moduleFAggregate.rotate(ref, arg, cRef, cArg, corrections);
    }

    @Override
    public void attach(FAggregate ref, FAggregate arg) {

        moduleFAggregate.attach(ref, arg);
    }

    @Override
    public void project(FAggregate ref, FAggregate arg) {

        moduleFAggregate.project(ref, arg);
    }

    @Override
    public boolean rotateOnSurface(FAggregate ref, FAggregate arg, FPoint cRef, FPoint cArg, int corrections) {

        return moduleFAggregate.rotateOnSurface(ref, arg, cRef, cArg, corrections);
    }

    @Override
    public void attachOnSurface(FAggregate ref, FAggregate arg) {

        moduleFAggregate.attachOnSurface(ref, arg);
    }

    @Override
    public void projectOnSurface(FAggregate ref, FAggregate arg) {

        moduleFAggregate.projectOnSurface(ref, arg);
    }
}
