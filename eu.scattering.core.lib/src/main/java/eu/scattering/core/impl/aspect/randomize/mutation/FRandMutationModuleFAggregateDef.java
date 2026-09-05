package eu.scattering.core.impl.aspect.randomize.mutation;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.method.MassCenter;

import java.util.ArrayList;
import java.util.List;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FRandMutationModuleFAggregateDef {
    private final FRandEngine engine;
    private final ScatterFactory factory;

    private FRandMutationModuleFAggregateDef(FRandEngine engine, ScatterFactory factory) {

        this.engine = engine;
        this.factory = factory;
    }

    protected static FRandMutationModuleFAggregateDef create(FRandEngine engine, ScatterFactory factory) {

        return new FRandMutationModuleFAggregateDef(engine, factory);
    }

    //--------------------------------------------------

    public FAggregate moveMassCenter(FAggregate in, FAggregate arg, MassCenter type, double dist) {
        FPoint position = arg.getMassCenter(this.factory.getFPoint(), type)
                .add(this.engine.nextDoubleOnSphere(dist));

        in.getRefParticles().translate(in.getMassCenter(type), position);

        return in;
    }

    public FAggregate moveMassCenterOnPlane(FAggregate in, FAggregate arg, MassCenter type, double dist) {
        isOnSurface(in, arg);

        FPoint position = arg.getMassCenter(factory.getFPoint(), type)
                .add(this.engine.nextDoubleOnCircle(dist), 0);

        in.getRefParticles().translate(in.getMassCenter(type), position);

        return in;
    }

    //--------------------------------------------------

    public FAggregate attach(FAggregate in, FAggregate arg) {

        attachUniversal(in, arg, true);

        return in;
    }

    public FAggregate attachOnPlane(FAggregate in, FAggregate arg) {
        isOnSurface(in, arg);

        attachUniversal(in, arg, false);

        return in;
    }

    private void attachUniversal(FAggregate in, FAggregate arg, boolean is3D) {
        List<Shape> candidates = new ArrayList<>(arg.getRefParticles().asList());

        double minRadius = in.getFStatParticleRadius().min();

        Shape particleA = this.factory.getFSphere();
        Shape particleB = this.factory.getFSphere();
        Shape particleLoc = this.factory.getFSphere(minRadius);

        while (true) {
            particleLoc.setRadius(minRadius);

            boolean progress;

            progress = setPosition(arg, candidates, particleA, particleLoc, is3D);

            if (!progress) {
                continue;
            }

            progress = setCorrectionTrans(in, arg, particleB, particleA, particleLoc);

            if (!progress) {
                continue;
            }

            in.getRefParticles().translate(particleB.getRefCenter(), particleLoc.getRefCenter());

            return;
        }
    }

    //--------------------------------------------------

    public boolean rotate(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections) {

        return rotateUniversal(inA, inB, cA, cB, corrections, true);
    }

    public boolean rotateOnPlane(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections) {
        isOnSurface(inA, inB);

        return rotateUniversal(inA, inB, cA, cB, corrections, false);
    }

    private boolean rotateUniversal(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, int corrections, boolean is3D) {
        double distance = cA.getDistance(cB);

        List<Shape> candidatesA = getRotCandidatesA(inA, inB, cA, cB, distance);
        List<Shape> candidatesB = getRotCandidatesB(inA, inB, cA, cB, distance);

        if (candidatesA.isEmpty()) {
            return false;
        }

        if (candidatesB.isEmpty()) {
            return false;
        }

        double minRadius = inB.getFStatParticleRadius().min();

        int iterations = 0;

        while (iterations++ < (inA.size() + inB.size()) * corrections) {
            FVector axis = this.factory.getFVector();
            Shape particleA = this.factory.getFSphere();
            Shape particleB = this.factory.getFSphere();
            Shape particleLoc = this.factory.getFSphere(minRadius);

            boolean progress;

            progress = setPosition(inA, candidatesA, particleA, particleLoc, is3D);

            if (!progress) {
                continue;
            }

            progress = setCorrectionRot(inA, candidatesB, particleB, particleA, particleLoc);

            if (!progress) {
                continue;
            }

            progress = valRotation(cA, cB, particleB, particleLoc, distance);

            if (!progress) {
                continue;
            }

            double angleRef = getRotAngleA(axis, cA, cB, particleB, particleLoc, distance);
            this.factory.rotate().mutate().aroundRg(inA, axis, angleRef);
            this.factory.rotate().mutate().aroundRg(particleLoc, axis, angleRef);

            double angleArg = getRotAngleB(axis, cB, particleB, particleLoc);
            this.factory.rotate().mutate().aroundRg(inB, axis, angleArg);

            if (inA.overlaps(inB)) {
                continue;
            }

            return true;
        }

        return false;
    }

    private List<Shape> getRotCandidatesA(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, double dist) {
        List<Shape> candidates = new ArrayList<>(inA.size());

        double radiusArg = inB.getRadiusFrom(cB);
        double offset = dist - radiusArg;

        for (Shape particle : inA) {
            if (particle.getDistCenter(cA) > offset - particle.getRadius()) {
                if (!particle.getRefCenter().isSimilar(cA)) {
                    candidates.add(particle);
                }
            }
        }

        this.engine.shuffle(candidates);

        return candidates;
    }

    private List<Shape> getRotCandidatesB(FAggregate inA, FAggregate inB, FPoint cA, FPoint cB, double dist) {
        List<Shape> candidates = new ArrayList<>(inB.size());

        double radiusRef = inA.getRadiusFrom(cA);
        double offset = dist - radiusRef;

        for (Shape particle : inB) {
            if (particle.getDistCenter(cB) > offset - particle.getRadius()) {
                if (!particle.getRefCenter().isSimilar(cB)) {
                    candidates.add(particle);
                }
            }
        }

        this.engine.shuffle(candidates);

        return candidates;
    }

    private double getRotAngleA(FVector axis, FPoint cA, FPoint cB, Shape particleB, Shape particleLoc, double dist) {
        double sideA = cA.getDistance(particleLoc.getRefCenter());
        double sideB = cB.getDistance(particleB.getRefCenter());

        axis.set(cA, particleLoc.getRefCenter());

        double angleFinal = this.factory.getFTrigHelper().getAngle(sideA, dist, sideB);
        double angleCurrent = axis.getAngle(cA, cB);

        axis.setCrossProduct(cA, cB);

        return angleFinal - angleCurrent;
    }

    private double getRotAngleB(FVector axis, FPoint cB, Shape particleB, Shape particleLoc) {

        axis.set(cB, particleB.getRefCenter());

        double angleCurrent = axis.getAngle(cB, particleLoc.getRefCenter());

        axis.setCrossProduct(cB, particleLoc.getRefCenter());

        return -angleCurrent;
    }

    private boolean valRotation(FPoint cA, FPoint cB, Shape particleB, Shape particleLoc, double dist) {
        double sideA = cA.getDistance(particleLoc.getRefCenter());
        double sideB = cB.getDistance(particleB.getRefCenter());

        return this.factory.getFTrigHelper().isValid(sideA, sideB, dist);
    }

    //--------------------------------------------------

    public void project(FAggregate inA, FAggregate inB) {

        projectUniversal(inA, inB, true);
    }

    public void projectOnPlane(FAggregate inA, FAggregate inB) {
        isOnSurface(inB, inA);

        projectUniversal(inA, inB, false);
    }

    private void projectUniversal(FAggregate inA, FAggregate inB, boolean is3D) {
        FPoint base = this.factory.getFPoint();
        FPoint head = this.factory.getFPoint();
        FVector dir = this.factory.getRefFVector(base, head);

        while (true) {
            inA.setPositionAsZero(inA.getBoxCenter());
            inB.setPositionAsZero(inB.getBoxCenter());

            double radiusRef = inA.getRadiusFrom(Center.ORIGIN);
            double radiusArg = inB.getRadiusFrom(Center.ORIGIN);

            if (is3D) {
                base.set(engine.nextDoubleOnSphere(10 * (radiusRef + radiusArg)));
            } else {
                base.set(engine.nextDoubleOnCircle(10 * (radiusRef + radiusArg)), 0);
            }

            FPoint targetRef = this.factory.getFPoint();
            targetRef.set(engine.nextDoubleInCircle(radiusRef), 0);
            this.factory.rotate().mutate().setAngleRg(targetRef, base, 0.5 * Math.PI);

            FPoint targetArg = this.factory.getFPoint();
            targetArg.set(engine.nextDoubleInCircle(radiusArg), 0);
            this.factory.rotate().mutate().setAngleRg(targetArg, base, 0.5 * Math.PI);

            inA.getRefParticles().translate(base.toFPos3D());

            double shift = inA.project(inB, dir);

            if (shift >= 0) {
                return;
            }
        }
    }

    //--------------------------------------------------

    private boolean setPosition(FAggregate arg, List<Shape> candidates, Shape particleIn, Shape particleLoc, boolean is3D) {
        int maxPositions = 100;

        while (true) {

            if (candidates.isEmpty()) {
                throw new IllegalStateException("The particle reference pool is depleted");
            }

            Shape candidate = this.engine.getElement(candidates, false);

            double radius = candidate.getRadius() + particleLoc.getRadius();

            for (int i = 0; i < maxPositions; i++) {

                if (is3D) {
                    particleLoc.setCenter(this.engine.nextDoubleOnSphere(radius));
                } else {
                    particleLoc.setCenter(this.engine.nextDoubleOnCircle(radius), 0);
                }

                particleLoc.getRefCenter().add(candidate.getRefCenter());

                if (particleLoc.overlaps(arg.getRefParticles().asList()) == 0) {
                    particleIn.setRadius(candidate.getRadius());
                    particleIn.setCenter(candidate.getRefCenter());

                    return true;
                }
            }

            candidates.remove(candidate);
        }
    }

    private boolean setCorrectionRot(FAggregate in, List<Shape> candidates, Shape particleIn, Shape particleArg, Shape particleLoc) {

        if (candidates.isEmpty()) {
            throw new IllegalStateException("The particle argument pool is depleted");
        }

        FPos3D initialParticleLoc = particleLoc.getRefCenter().toFPos3D();

        this.engine.shuffle(candidates);

        for (Shape candidate : candidates) {

            particleLoc.setRadius(candidate.getRadius());

            particleLoc.setCenter(initialParticleLoc);
            particleLoc.attachLinear(particleArg);

            if (particleLoc.overlaps(in.getRefParticles().asList()) > 0) {
                continue;
            }

            particleIn.setRadius(candidate.getRadius());
            particleIn.setCenter(candidate.getRefCenter());

            return true;
        }

        return false;
    }

    private boolean setCorrectionTrans(FAggregate in, FAggregate arg, Shape particleIn, Shape particleArg, Shape particleLoc) {
        FPos3D initialParticleLoc = particleLoc.getRefCenter().toFPos3D();

        FVector shift = this.factory.getFVector();

        Shape dummy = this.factory.getFSphere();

        List<Shape> candidates = new ArrayList<>(in.getRefParticles().asList());
        this.engine.shuffle(candidates);

        for (Shape candidate : candidates) {
            particleLoc.setRadius(candidate.getRadius());

            particleLoc.setCenter(initialParticleLoc);
            particleLoc.attachLinear(particleArg);

            if (particleLoc.overlaps(arg.getRefParticles().asList()) > 0) {
                continue;
            }

            shift.setBase(particleLoc.getRefCenter());
            shift.setHead(particleArg.getRefCenter());

            shift.moveBase(candidate.getRefCenter());

            dummy.setRadius(particleArg.getRadius());
            dummy.setCenter(shift.getRefHead());

            if (dummy.overlaps(in.getRefParticles().asList()) > 0) {
                continue;
            }

            particleIn.setRadius(candidate.getRadius());
            particleIn.setCenter(candidate.getRefCenter());

            shift.setBase(particleIn.getRefCenter());
            shift.setHead(particleLoc.getRefCenter());

            if (in.overlapsWithShift(arg, shift)) {
                continue;
            }

            return true;
        }

        return false;
    }

    private void isOnSurface(FAggregate arg) {

        for (Shape particle : arg) {
            if (Math.abs(particle.getCenterZ()) > EPSILON) {
                throw new IllegalStateException("The FAggregate must be two-dimensional");
            }
        }
    }

    private void isOnSurface(FAggregate arg1, FAggregate arg2) {

        isOnSurface(arg2);
        isOnSurface(arg1);
    }
}
