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
    private final FRandGenerator generator;
    private final ScatterFactory factory;

    private final FRandAspectModuleFAggregateDef moduleFAggregate;

    private FRandAspectDef(FRandGenerator generator, ScatterFactory factory) {

        this.generator = generator;
        this.factory = factory;

        this.moduleFAggregate = FRandAspectModuleFAggregateDef.create(generator, factory);
    }

    public static FRandAspect create(FRandGenerator generator, ScatterFactory factory) {

        return new FRandAspectDef(generator, factory);
    }

    //--------------------------------------------------

    @Override
    public FRandGenerator generator() {

        return this.generator;
    }

    @Override
    public FComplex inRange(FComplex in, FPairPos2D range) {

        in.applyStateFrom(generator.nextDouble2D(range));

        return in;
    }

    @Override
    public FComplex inCircle(FComplex in, double radius) {

        in.applyStateFrom(generator.nextDoubleInCircle(radius));

        return in;
    }

    @Override
    public FComplex onCircle(FComplex in, double radius) {

        in.applyStateFrom(generator.nextDoubleOnCircle(radius));

        return in;
    }

    @Override
    public FQuaternion inRange(FQuaternion in, FPairPos4D range) {

        in.applyStateFrom(generator.nextDouble4D(range));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint onSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.set(generator.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint inRange(FPoint in, FPairPos3D range) {

        in.set(generator.nextDouble3D(range));

        return in;
    }

    @Override
    public FPoint inSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.set(generator.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint inSphere(FPoint in, double radius) {

        in.set(generator.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint onSphere(FPoint in, double radius) {

        in.set(generator.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in) {

        in.setMagnitude(generator.nextDouble(EPSILON, in.getMagnitude()));

        return in;
    }

    @Override
    public FPoint onAxis(FPoint in, FPoint axis) {

        in.set(axis);
        in.setMagnitude(generator.nextDouble(EPSILON, axis.getMagnitude()));

        return in;
    }

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FPoint dir, double radius) {

        in.set(generator.nextDoubleInSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint ortToBaseOnCircle(FPoint in, FPoint dir, double radius) {

        in.set(generator.nextDoubleOnSphere(radius));
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
        in.setMagnitude(generator.nextDouble(EPSILON, in.getMagnitude()));
        in.add(axis.getRefBase());

        return in;
    }

    @Override
    public FPoint ortToBaseInCircle(FPoint in, FVector dir, double radius) {

        in.set(generator.nextDoubleInSphere(radius));

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

        in.set(generator.nextDoubleOnSphere(radius));

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

        in.set(generator.nextDoubleInSphere(radius));

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

        in.set(generator.nextDoubleOnSphere(radius));

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

        in.setCenter(this.generator.nextDoubleOnSphere((in.getRadius() + target.getRadius() * 2)));
        in.translate(target.getCenterX(), target.getCenterY(), target.getCenterZ());

        return in.attachLinear(target);
    }

    @Override
    public boolean attachLinear2D(Shape in, Shape target) {

        if (in == target) {
            return false;
        }

        FPos2D position = this.generator.nextDoubleOnCircle((in.getRadius() + target.getRadius() * 2));

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

        in.setCenter(this.generator.nextDoubleOnSphere(dist));
        in.translate(x, y, z);

        return in.attachSpherical(target, x, y, z);
    }

    @Override
    public boolean attachSpherical2D(Shape in, Shape target, double x, double y, double z) {

        if (in == target) {
            return false;
        }

        double dist = in.getDistCenter(x, y, z);

        in.setCenter(this.generator.nextDoubleOnCircle(dist), 0);
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
            Shape candidate = generator().getElement(candidates, false);

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
            FPos3D pos3D = this.factory.random().generator().nextDoubleOnSphere(4 * radius);

            baseRnd.set(0, 0, 0);
            headRnd.set(pos3D);

            vectorRnd.moveBase(center);

            this.factory.random().ortToBaseInCircle(headDir, vectorRnd, radius);

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
            FPos2D pos2D = this.factory.random().generator().nextDoubleOnCircle(4 * radius);
            double pos1D = this.factory.random().generator().nextDouble(-radius, radius);

            baseDir.set(pos2D, 0);
            headDir.set(pos1D, 0, 0);

            this.factory.rotate().setRgAngle(headDir, baseDir, Math.PI * 0.5);

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
