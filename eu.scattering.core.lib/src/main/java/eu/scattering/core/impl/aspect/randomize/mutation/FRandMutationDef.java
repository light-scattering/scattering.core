package eu.scattering.core.impl.aspect.randomize.mutation;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.mutation.FRandMutation;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;
import eu.scattering.core.design.utility.type.method.MassCenter;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FRandMutationDef implements FRandMutation {
    private final ScatterFactory factory;

    private final FRandEngine engine;

    private final FRandMutationModuleFAggregateDef moduleFAggregate;

    private FRandMutationDef(FRandEngine engine, ScatterFactory factory) {

        this.factory = factory;
        this.engine = engine;

        this.moduleFAggregate = FRandMutationModuleFAggregateDef.create(engine, factory);
    }

    public static FRandMutation create(FRandEngine engine, ScatterFactory factory) {

        return new FRandMutationDef(engine, factory);
    }

    //--------------------------------------------------

    @Override
    public FComplex withinRange(FComplex in, FPairPos2D range) {

        in.applyStateFrom(engine.nextDouble2D(range));

        return in;
    }

    @Override
    public FComplex intoCircle(FComplex in, double radius) {

        in.applyStateFrom(engine.nextDoubleInCircle(radius));

        return in;
    }

    @Override
    public FComplex ontoCircle(FComplex in, double radius) {

        in.applyStateFrom(engine.nextDoubleOnCircle(radius));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FQuaternion withinRange(FQuaternion in, FPairPos4D range) {

        in.applyStateFrom(engine.nextDouble4D(range));

        return in;
    }

    //--------------------------------------------------

    @Override
    public FPoint ontoSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.set(engine.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint withinRange(FPoint in, FPairPos3D range) {

        in.set(engine.nextDouble3D(range));

        return in;
    }

    @Override
    public FPoint intoSphere(FPoint in) {
        double radius = in.getMagnitude();

        in.set(engine.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint intoSphere(FPoint in, double radius) {

        in.set(engine.nextDoubleInSphere(radius));

        return in;
    }

    @Override
    public FPoint ontoSphere(FPoint in, double radius) {

        in.set(engine.nextDoubleOnSphere(radius));

        return in;
    }

    @Override
    public FPoint ontoAxis(FPoint in) {

        in.setMagnitude(engine.nextDouble(EPSILON, in.getMagnitude()));

        return in;
    }

    @Override
    public FPoint ontoAxis(FPoint in, FPoint axis) {

        in.set(axis);
        in.setMagnitude(engine.nextDouble(EPSILON, axis.getMagnitude()));

        return in;
    }

    @Override
    public FPoint intoCircleOrthogonalToBase(FPoint in, FPoint dir, double radius) {

        in.set(engine.nextDoubleInSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint ontoCircleOrthogonalToBase(FPoint in, FPoint dir, double radius) {

        in.set(engine.nextDoubleOnSphere(radius));
        in.setOrthogonal(dir);

        return in;
    }

    @Override
    public FPoint intoCircleOrthogonalToHead(FPoint in, FPoint dir, double radius) {

        intoCircleOrthogonalToBase(in, dir, radius);

        in.add(dir);

        return in;
    }

    @Override
    public FPoint ontoCircleOrthogonalToHead(FPoint in, FPoint dir, double radius) {

        ontoCircleOrthogonalToBase(in, dir, radius);

        in.add(dir);

        return in;
    }

    @Override
    public FVector ontoSphere(FVector in) {
        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();

        in.moveBaseToCenter();
        ontoSphere(in.getRefHead());
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FVector intoSphere(FVector in) {
        double memoOBX = in.getBaseX();
        double memoOBY = in.getBaseY();
        double memoOBZ = in.getBaseZ();

        in.moveBaseToCenter();
        intoSphere(in.getRefHead());
        in.moveBase(memoOBX, memoOBY, memoOBZ);

        return in;
    }

    @Override
    public FPoint ontoAxis(FPoint in, FVector axis) {

        in.set(axis.getRefHead());
        in.sub(axis.getRefBase());
        in.setMagnitude(engine.nextDouble(EPSILON, in.getMagnitude()));
        in.add(axis.getRefBase());

        return in;
    }

    @Override
    public FPoint intoCircleOrthogonalToBase(FPoint in, FVector dir, double radius) {

        in.set(engine.nextDoubleInSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint ontoCircleOrthogonalToBase(FPoint in, FVector dir, double radius) {

        in.set(engine.nextDoubleOnSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefBase());

        return in;
    }

    @Override
    public FPoint intoCircleOrthogonalToHead(FPoint in, FVector dir, double radius) {

        in.set(engine.nextDoubleInSphere(radius));

        in.setOrthogonal(
                dir.getHeadX() - dir.getBaseX(),
                dir.getHeadY() - dir.getBaseY(),
                dir.getHeadZ() - dir.getBaseZ()
        );

        in.add(dir.getRefHead());

        return in;
    }

    @Override
    public FPoint ontoCircleOrthogonalToHead(FPoint in, FVector dir, double radius) {

        in.set(engine.nextDoubleOnSphere(radius));

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
    public double project(Shape in, FPos3D center, double radius, Iterable<? extends Shape> field, int corrections) {
        FVector vectorRnd = this.factory.getFVector();
        FPoint baseRnd = vectorRnd.getRefBase();
        FPoint headRnd = vectorRnd.getRefHead();
        FVector vectorDir = this.factory.getFVector();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        for (int i = 0 ; i < corrections ; i++) {
            FPos3D pos3D = this.factory.random().engine().nextDoubleOnSphere(4 * radius);

            baseRnd.set(0, 0, 0);
            headRnd.set(pos3D);

            vectorRnd.moveBase(center);

            intoCircleOrthogonalToBase(headDir, vectorRnd, radius);

            baseDir.set(headRnd);

            double distance = in.projectFrom(field, vectorDir);

            if (distance >= 0) {
                return distance;
            }
        }

        return -1;
    }

    @Override
    public boolean attachLinear(Shape in, Shape target) {

        if (in == target) {
            return false;
        }

        in.setCenter(this.engine.nextDoubleOnSphere((in.getRadius() + target.getRadius() * 2)));
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
    public boolean attachSpherical(Shape in, Shape target, double x, double y, double z) {

        if (in == target) {
            return false;
        }

        double dist = in.getDistCenter(x, y, z);

        in.setCenter(this.engine.nextDoubleOnSphere(dist));
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

        if (candidates.isEmpty()) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            Shape candidate = this.engine.getElement(candidates, false);

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

    //--------------------------------------------------

    @Override
    public double projectOnPlane(Shape in, FPos3D center, double radius, Iterable<? extends Shape> field, int corrections) {

        if (center.getD2() > EPSILON || center.getD2() < -EPSILON) {
            throw new IllegalArgumentException("The center should be two dimensional");
        }

        FVector vectorDir = this.factory.getFVector();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        for (int i = 0 ; i < corrections ; i++) {
            FPos2D pos2D = this.factory.random().engine().nextDoubleOnCircle(4 * radius);
            double pos1D = this.factory.random().engine().nextDouble(-radius, radius);

            baseDir.set(pos2D, 0);
            headDir.set(pos1D, 0, 0);

            this.factory.rotate().mutate().setRgAngle(headDir, baseDir, Math.PI * 0.5);

            vectorDir.translate(center);

            double distance = in.projectFrom(field, vectorDir);

            if (distance >= 0) {
                return distance;
            }
        }

        return -1;
    }

    @Override
    public boolean attachLinearOnPlane(Shape in, Shape target) {

        if (in == target) {
            return false;
        }

        FPos2D position = this.engine.nextDoubleOnCircle((in.getRadius() + target.getRadius() * 2));

        in.setCenter(position.getD0(), position.getD1(), 0);
        in.translate(target.getCenterX(), target.getCenterY(), target.getCenterZ());

        return in.attachLinear(target);
    }

    @Override
    public boolean attachLinearOnPlane(Shape in, Shape target, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            boolean results = attachLinearOnPlane(in, target);

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
    public boolean attachSphericalOnPlane(Shape in, Shape target, double x, double y, double z) {

        if (in == target) {
            return false;
        }

        double dist = in.getDistCenter(x, y, z);

        in.setCenter(this.engine.nextDoubleOnCircle(dist), 0);
        in.translate(x, y, z);

        return in.attachSpherical(target, x, y, z);
    }

    @Override
    public boolean attachSphericalOnPlane(Shape in, Shape target, double x, double y, double z, Iterable<? extends Shape> field, int corrections) {

        if (in == target) {
            return false;
        }

        int iterations = 0;

        while (iterations++ <= corrections) {
            boolean results = attachSphericalOnPlane(in, target, x, y, z);

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
    public boolean attachSphericalOnPlane(Shape in, Shape target, FPoint center, Iterable<? extends Shape> field, int corrections) {

        return attachSphericalOnPlane(in, target, center.getX(), center.getY(), center.getZ(), field, corrections);
    }

    //--------------------------------------------------

    @Override
    public FAggregate moveMassCenter(FAggregate in, FAggregate arg, MassCenter type, double distance) {

        return moduleFAggregate.moveMassCenter(in, arg, type, distance);
    }

    @Override
    public FAggregate moveMassCenterOnPlane(FAggregate in, FAggregate arg, MassCenter type, double distance) {

        return moduleFAggregate.moveMassCenterOnPlane(in, arg, type, distance);
    }

    @Override
    public FAggregate attach(FAggregate in, FAggregate arg) {

        return moduleFAggregate.attach(in, arg);
    }

    @Override
    public FAggregate attachOnPlane(FAggregate in, FAggregate arg) {

        return moduleFAggregate.attachOnPlane(in, arg);
    }

    @Override
    public void project(FAggregate inA, FAggregate inB) {

        moduleFAggregate.project(inA, inB);
    }

    @Override
    public void projectOnPlane(FAggregate inA, FAggregate inB) {

        moduleFAggregate.projectOnPlane(inA, inB);
    }

    @Override
    public boolean rotate(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections) {

        return moduleFAggregate.rotate(inA, inB, cA, cB, corrections);
    }

    @Override
    public boolean rotateOnPlane(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections) {

        return moduleFAggregate.rotateOnPlane(inA, inB, cA, cB, corrections);
    }
}
