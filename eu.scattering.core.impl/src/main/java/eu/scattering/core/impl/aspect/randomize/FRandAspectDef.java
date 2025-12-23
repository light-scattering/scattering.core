package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.construct.segment.FSegment;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.component.number.quaternion.FQuaternion;
import eu.scattering.core.design.transfer.primitive.*;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRandAspectDef implements FRandAspect {
    private final FRandGenerator core;
    private final ScatFactory factory;

    private FRandAspectDef(FRandGenerator core, ScatFactory factory) {

        this.core = core;
        this.factory = factory;
    }

    public static FRandAspect create(FRandGenerator core, ScatFactory factory) {

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
    public double project(Shape in, Shape range, Iterable<? extends Shape> field, int corrections) {
        FPoint base = factory.getFPoint();
        FPoint head = factory.getFPoint();
        FRay ray = factory.getRefFRay(factory.getRefFVector(base, head));

        int iterations = 0;

        while (iterations++ <= corrections) {
            base.applyStateFrom(core.nextDoubleOnSphere(10 * range.getRadius()));
            head.set(core.nextDoubleInCircle(range.getRadius()), 0);

            factory.getRotAspect().setRgAngle(head, base, Math.PI * 0.5);

            ray.getRefOrigin().translate(range.getCenter());

            double distance = in.project(field, ray);

            if (distance >= 0) {
                return distance;
            }
        }

        return -1;
    }

    @Override
    public double project2D(Shape in, Shape range, Iterable<? extends Shape> field, int corrections) {
        FPoint base = factory.getFPoint();
        FPoint head = factory.getFPoint();
        FRay ray = factory.getRefFRay(factory.getRefFVector(base, head));

        int iterations = 0;

        while (iterations++ <= corrections) {
            double radius = range.getRadius();

            base.set(core.nextDoubleOnCircle(10 * radius), 0);
            head.set(core.nextDouble(-radius, radius), 0, 0);

            factory.getRotAspect().setRgAngle(head, base, Math.PI * 0.5);

            ray.getRefOrigin().translate(range.getCenter());

            double distance = in.project(field, ray);

            if (distance >= 0) {
                return distance;
            }
        }

        return -1;
    }

    @Override
    public boolean attach(FAggregate ref, FAggregate arg) {

        return attach(ref, arg, Integer.MAX_VALUE);
    }

    @Override
    public boolean attach(FAggregate ref, FAggregate arg, int corrections) {

        return attach(ref, arg, corrections, true);
    }

    @Override
    public boolean attach2D(FAggregate ref, FAggregate arg) {

        return attach2D(ref, arg, Integer.MAX_VALUE);
    }

    @Override
    public boolean attach2D(FAggregate ref, FAggregate arg, int corrections) {

        return attach(ref, arg, corrections, false);
    }

    public boolean attach(FAggregate ref, FAggregate arg, int corrections, boolean is3D) {
        List<Shape> particlesRef = new ArrayList<>(ref.getRefParticles().asList());

        double minRadius = arg.getParticleRadius().min();

        for (int i = 0 ; i < corrections ; i++) {
            Shape particleRef = factory.getFSphere();
            Shape particleArg = factory.getFSphere();
            Shape particleLoc = factory.getFSphere(minRadius);

            boolean progress;

            progress = setRefPosition(ref, particlesRef, particleRef, particleLoc, is3D);

            if (!progress) {
                continue;
            }

            progress = setArgPosition(ref, arg, particleRef, particleArg, particleLoc);

            if (!progress) {
                continue;
            }

            FVector shift = factory.getRefFVector(particleArg.getRefCenter(), particleLoc.getRefCenter());
            shift.moveBaseToCenter();

            arg.getRefParticles().translate(shift.getRefHead().toFPos3D());

            return true;

        }

        return false;
    }

    private boolean setRefPosition(FAggregate ref, List<Shape> particles, Shape particleRef, Shape particleLoc, boolean is3D) {
        int maxPositions = 100;

        while (true) {

            if (particles.size() == 0) {
                throw new IllegalStateException("The particle reference pool is depleted");
            }

            Shape candidate = factory.getFRand().getElement(particles, false);

            double radius = candidate.getRadius() + particleLoc.getRadius();

            for (int i = 0; i < maxPositions; i++) {

                if (is3D) {
                    FPos3D position = factory.getFRand().nextDoubleOnSphere(radius);
                    particleLoc.setCenter(position.getD0(), position.getD1(), position.getD2());
                } else {
                    FPos2D position = factory.getFRand().nextDoubleOnCircle(radius);
                    particleLoc.setCenter(position.getD0(), position.getD1(), 0);
                }

                particleLoc.getRefCenter().add(candidate.getRefCenter());

                if (particleLoc.overlaps(ref.getRefParticles().asList()) == 0) {
                    particleRef.setRadius(candidate.getRadius());
                    particleRef.setCenter(candidate.getRefCenter());

                    return true;
                }
            }

            particles.remove(candidate);
        }
    }

    private boolean setArgPosition(FAggregate ref, FAggregate arg, Shape particleRef, Shape particleArg, Shape particleLoc) {
        FPos3D initialParticleLoc = particleLoc.getRefCenter().toFPos3D();

        FVector shift = factory.getFVector();

        Shape dummy = factory.getFSphere();

        List<Shape> candidates = new ArrayList<>(arg.getRefParticles().asList());
        factory.getFRand().shuffle(candidates);

        for (Shape candidate : candidates) {
            particleLoc.setRadius(candidate.getRadius());

            particleLoc.setCenter(initialParticleLoc);
            particleLoc.attachLinear(particleRef);

            if (particleLoc.overlaps(ref.getRefParticles().asList()) > 0) {
                continue;
            }

            shift.setBase(particleLoc.getRefCenter());
            shift.setHead(particleRef.getRefCenter());

            shift.moveBase(candidate.getRefCenter());

            dummy.setRadius(particleLoc.getRadius());
            dummy.setCenter(shift.getRefHead());

            if (dummy.overlaps(arg.getRefParticles().asList()) > 0) {
                continue;
            }

            particleArg.setRadius(candidate.getRadius());
            particleArg.setCenter(candidate.getRefCenter());

            shift.setBase(particleArg.getRefCenter());
            shift.setHead(particleLoc.getRefCenter());

            if (arg.overlapsWithShift(ref, shift)) {
                continue;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean project(FAggregate ref, FAggregate arg) {

        return project(ref, arg, Integer.MAX_VALUE);
    }

    @Override
    public boolean project(FAggregate ref, FAggregate arg, int corrections) {
        FPoint base = factory.getFPoint();
        FPoint head = factory.getFPoint();
        FRay ray = factory.getRefFRay(factory.getRefFVector(base, head));

        int iterations = 0;

        while (iterations++ <= corrections) {
            FPoint centerRef = factory.getFPoint();
            ref.getSpatialCenter(centerRef);
            ref.setCenter(centerRef);

            FPoint centerArg = factory.getFPoint();
            arg.getSpatialCenter(centerArg);
            arg.setCenter(centerArg);

            double radiusRef = ref.getRadiusFromOrigin();
            double radiusArg = arg.getRadiusFromOrigin();

            base.applyStateFrom(core.nextDoubleOnSphere(10 * (radiusRef + radiusArg)));

            FPoint targetRef = factory.getFPoint();
            targetRef.set(core.nextDoubleInCircle(radiusRef), 0);
            factory.getRotAspect().setRgAngle(targetRef, base, 0.5 * Math.PI);

            FPoint targetArg = factory.getFPoint();
            targetArg.set(core.nextDoubleInCircle(radiusArg), 0);
            factory.getRotAspect().setRgAngle(targetArg, base, 0.5 * Math.PI);

            ref.getRefParticles().translate(base.toFPos3D());

            double shift = ref.project(arg, ray);

            if (shift >= 0) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean project2D(FAggregate ref, FAggregate arg) {

        return project2D(ref, arg, Integer.MAX_VALUE);
    }

    @Override
    public boolean project2D(FAggregate ref, FAggregate arg, int corrections) {
        FPoint base = factory.getFPoint();
        FPoint head = factory.getFPoint();
        FRay ray = factory.getRefFRay(factory.getRefFVector(base, head));

        int iterations = 0;

        while (iterations++ <= corrections) {
            FPoint centerRef = factory.getFPoint();
            ref.getSpatialCenter(centerRef);
            ref.setCenter(centerRef);

            FPoint centerArg = factory.getFPoint();
            arg.getSpatialCenter(centerArg);
            arg.setCenter(centerArg);

            double radiusRef = ref.getRadiusFromOrigin();
            double radiusArg = arg.getRadiusFromOrigin();

            base.set(core.nextDoubleOnCircle(10 * (radiusRef + radiusArg)), 0);

            FPoint targetRef = factory.getFPoint();
            targetRef.set(core.nextDouble(-radiusRef, radiusRef), 0, 0);
            factory.getRotAspect().setRgAngle(targetRef, base, 0.5 * Math.PI);

            FPoint targetArg = factory.getFPoint();
            targetArg.set(core.nextDouble(-radiusArg, radiusArg), 0, 0);
            factory.getRotAspect().setRgAngle(targetArg, base, 0.5 * Math.PI);

            ref.getRefParticles().translate(base.toFPos3D());

            double shift = ref.project(arg, ray);

            if (shift >= 0) {
                return true;
            }
        }

        return false;
    }

    private boolean project(FAggregate ref, FAggregate arg, int corrections, boolean is3D) {
        FPoint base = factory.getFPoint();
        FPoint head = factory.getFPoint();
        FRay ray = factory.getRefFRay(factory.getRefFVector(base, head));

        int iterations = 0;

        while (iterations++ <= corrections) {
            FPoint centerRef = factory.getFPoint();
            ref.getSpatialCenter(centerRef);
            ref.setCenter(centerRef);

            FPoint centerArg = factory.getFPoint();
            arg.getSpatialCenter(centerArg);
            arg.setCenter(centerArg);

            double radiusRef = ref.getRadiusFromOrigin();
            double radiusArg = arg.getRadiusFromOrigin();

            if (is3D) {
                base.applyStateFrom(core.nextDoubleOnSphere(10 * (radiusRef + radiusArg)));
            } else {
                base.set(core.nextDoubleOnCircle(10 * (radiusRef + radiusArg)), 0);
            }

            FPoint targetRef = factory.getFPoint();
            targetRef.set(core.nextDoubleInCircle(radiusRef), 0);
            factory.getRotAspect().setRgAngle(targetRef, base, 0.5 * Math.PI);

            FPoint targetArg = factory.getFPoint();
            targetArg.set(core.nextDoubleInCircle(radiusArg), 0);
            factory.getRotAspect().setRgAngle(targetArg, base, 0.5 * Math.PI);

            ref.getRefParticles().translate(base.toFPos3D());

            double shift = ref.project(arg, ray);

            if (shift >= 0) {
                return true;
            }
        }

        return false;
    }
}
