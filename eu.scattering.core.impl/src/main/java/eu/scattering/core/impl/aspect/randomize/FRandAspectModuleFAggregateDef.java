package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FRandAspectModuleFAggregateDef {
    private final FRandGenerator core;
    private final ScatFactory factory;

    private FRandAspectModuleFAggregateDef(ScatFactory factory) {

        this.factory = factory;
        this.core = factory.getFRand();
    }

    protected static FRandAspectModuleFAggregateDef create(ScatFactory factory) {

        return new FRandAspectModuleFAggregateDef(factory);
    }

    //--------------------------------------------------

    public void moveMassCenter(FAggregate ref, FAggregate arg, double dist) {
        FPoint position = ref.getMassCenter(factory.getFPoint())
                .add(core.nextDoubleOnSphere(dist));

        arg.getRefParticles().translate(arg.getMassCenter(), position);
    }

    public void moveMassCenterOnSurface(FAggregate ref, FAggregate arg, double dist) {
        isOnSurface(ref, arg);

        FPoint position = ref.getMassCenter(factory.getFPoint())
                .add(core.nextDoubleOnCircle(dist), 0);

        arg.getRefParticles().translate(arg.getMassCenter(), position);
    }

    //--------------------------------------------------

    public void rotate(FAggregate ref, FAggregate arg) {

        rotateUniversal(ref, arg, true);
    }

    public void rotateOnSurface(FAggregate ref, FAggregate arg) {
        isOnSurface(ref, arg);

        rotateUniversal(ref, arg, false);
    }

    private void rotateUniversal(FAggregate ref, FAggregate arg, boolean is3D) {
        FPoint centerRef = ref.getMassCenter(factory.getFPoint());
        FPoint centerArg = arg.getMassCenter(factory.getFPoint());

        double distance = centerRef.getDistance(centerArg);

        List<Shape> candidatesRef = getRotRefCandidates(ref, arg, centerRef, centerArg, distance);
        List<Shape> candidatesArg = getRotArgCandidates(ref, arg, centerRef, centerArg, distance);

        double minRadius = arg.getParticleRadius().min();

        while (true) {
            FVector axis = factory.getFVector();
            Shape particleRef = factory.getFSphere();
            Shape particleArg = factory.getFSphere();
            Shape particleLoc = factory.getFSphere(minRadius);

            boolean progress;

            progress = setRefPosition(ref, candidatesRef, particleRef, particleLoc, is3D);

            if (!progress) {
                continue;
            }

            progress = setRotArgCorrection(ref, candidatesArg, particleRef, particleArg, particleLoc);

            if (!progress) {
                continue;
            }

            progress = valRotation(centerRef, centerArg, particleArg, particleLoc, distance);

            if (!progress) {
                continue;
            }

            double angleRef = getRotRefAngle(axis, centerRef, centerArg, particleArg, particleLoc, distance);
            factory.getRotAspect().rotRgAround(ref, axis, angleRef);
            factory.getRotAspect().rotRgAround(particleLoc, axis, angleRef);

            double angleArg = getRotArgAngle(axis, centerArg, particleArg, particleLoc);
            factory.getRotAspect().rotRgAround(arg, axis, angleArg);

            if (ref.overlaps(arg)) {
                continue;
            }

            return;
        }
    }

    private List<Shape> getRotRefCandidates(FAggregate ref, FAggregate arg, FPoint centerRef, FPoint centerArg, double dist) {
        List<Shape> candidates = new ArrayList<>(ref.size());

        double radiusArg = arg.getRadius(centerArg);
        double offset = dist - radiusArg;

        for (Shape particle : ref) {
            if (particle.getDistCenter(centerRef) > offset - particle.getRadius()) {
                if (!particle.getRefCenter().isSimilar(centerRef)) {
                    candidates.add(particle);
                }
            }
        }

        if (candidates.isEmpty()) {
            throw new IllegalStateException("The aggregate cannot be built");
        }

        factory.getFRand().shuffle(candidates);

        return candidates;
    }

    private List<Shape> getRotArgCandidates(FAggregate ref, FAggregate arg, FPoint centerRef, FPoint centerArg, double dist) {
        List<Shape> candidates = new ArrayList<>(arg.size());

        double radiusRef = ref.getRadius(centerRef);
        double offset = dist - radiusRef;

        for (Shape particle : arg) {
            if (particle.getDistCenter(centerArg) > offset - particle.getRadius()) {
                if (!particle.getRefCenter().isSimilar(centerArg)) {
                    candidates.add(particle);
                }
            }
        }

        if (candidates.isEmpty()) {
            throw new IllegalStateException("The aggregate cannot be built");
        }

        factory.getFRand().shuffle(candidates);

        return candidates;
    }

    private double getRotRefAngle(FVector axis, FPoint centerRef, FPoint centerArg, Shape particleArg, Shape particleLoc, double dist) {
        double sideA = centerRef.getDistance(particleLoc.getRefCenter());
        double sideB = centerArg.getDistance(particleArg.getRefCenter());

        axis.set(centerRef, particleLoc.getRefCenter());

        double angleFinal = factory.getFTrigHelper().getAngle(sideA, dist, sideB);
        double angleCurrent = axis.getAngle(centerRef, centerArg);

        axis.setCrossProduct(centerRef, centerArg);

        return angleFinal - angleCurrent;
    }

    private double getRotArgAngle(FVector axis, FPoint centerArg, Shape particleArg, Shape particleLoc) {

        axis.set(centerArg, particleArg.getRefCenter());

        double angleCurrent = axis.getAngle(centerArg, particleLoc.getRefCenter());

        axis.setCrossProduct(centerArg, particleLoc.getRefCenter());

        return -angleCurrent;
    }

    private boolean valRotation(FPoint centerRef, FPoint centerArg, Shape particleArg, Shape particleLoc, double dist) {
        double sideA = centerRef.getDistance(particleLoc.getRefCenter());
        double sideB = centerArg.getDistance(particleArg.getRefCenter());

        return factory.getFTrigHelper().isValid(sideA, sideB, dist);
    }

    //--------------------------------------------------

    public void attach(FAggregate ref, FAggregate arg) {

        attachUniversal(ref, arg, true);
    }

    public void attachOnSurface(FAggregate ref, FAggregate arg) {
        isOnSurface(ref, arg);

        attachUniversal(ref, arg, false);
    }

    private void attachUniversal(FAggregate ref, FAggregate arg, boolean is3D) {
        List<Shape> particlesRef = new ArrayList<>(ref.getRefParticles().asList());

        double minRadius = arg.getParticleRadius().min();

        Shape particleRef = factory.getFSphere();
        Shape particleArg = factory.getFSphere();
        Shape particleLoc = factory.getFSphere(minRadius);
        while (true) {
            particleLoc.setRadius(minRadius);

            boolean progress;

            progress = setRefPosition(ref, particlesRef, particleRef, particleLoc, is3D);

            if (!progress) {
                continue;
            }

            progress = setTransArgCorrection(ref, arg, particleRef, particleArg, particleLoc);

            if (!progress) {
                continue;
            }

            arg.getRefParticles().translate(particleArg.getRefCenter(), particleLoc.getRefCenter());

            return;
        }
    }

    //--------------------------------------------------

    public void project(FAggregate ref, FAggregate arg) {

        projectUniversal(ref, arg, true);
    }

    public void projectOnSurface(FAggregate ref, FAggregate arg) {
        isOnSurface(ref, arg);

        projectUniversal(ref, arg, false);
    }

    private void projectUniversal(FAggregate ref, FAggregate arg, boolean is3D) {
        FPoint base = factory.getFPoint();
        FPoint head = factory.getFPoint();
        FRay ray = factory.getRefFRay(factory.getRefFVector(base, head));

        while (true) {
            ref.resetCenter(ref.getSpatialCenter());
            arg.resetCenter(arg.getSpatialCenter());

            double radiusRef = ref.getRadiusFromOrigin();
            double radiusArg = arg.getRadiusFromOrigin();

            if (is3D) {
                base.set(core.nextDoubleOnSphere(10 * (radiusRef + radiusArg)));
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
                return;
            }
        }
    }

    //--------------------------------------------------

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
                    particleLoc.setCenter(factory.getFRand().nextDoubleOnSphere(radius));
                } else {
                    particleLoc.setCenter(factory.getFRand().nextDoubleOnCircle(radius), 0);
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

    private boolean setTransArgCorrection(FAggregate ref, FAggregate arg, Shape particleRef, Shape particleArg, Shape particleLoc) {
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

    private boolean setRotArgCorrection(FAggregate ref, List<Shape> particles, Shape particleRef, Shape particleArg, Shape particleLoc) {
        FPos3D initialParticleLoc = particleLoc.getRefCenter().toFPos3D();

        for (Shape candidate : particles) {
            particleLoc.setRadius(candidate.getRadius());

            particleLoc.setCenter(initialParticleLoc);
            particleLoc.attachLinear(particleRef);

            if (particleLoc.overlaps(ref.getRefParticles().asList()) > 0) {
                continue;
            }

            particleArg.setRadius(candidate.getRadius());
            particleArg.setCenter(candidate.getRefCenter());

            return true;
        }

        return false;
    }

    private void isOnSurface(FAggregate ref) {

        for (Shape particle : ref) {
            if (Math.abs(particle.getCenterZ()) > EPSILON) {
                throw new IllegalStateException("The FAggregate must be two-dimensional");
            }
        }
    }

    private void isOnSurface(FAggregate ref, FAggregate arg) {

        isOnSurface(ref);
        isOnSurface(arg);
    }
}
