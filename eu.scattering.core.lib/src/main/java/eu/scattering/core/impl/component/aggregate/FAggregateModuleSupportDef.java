package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.variant.Center;

import java.util.*;
import java.util.function.BiConsumer;

public class FAggregateModuleSupportDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleSupportDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected void addParticles(Shape particle, double quantity) {

        for (int i = 0 ; i < quantity ; i++) {
            this.aggregate.getRefParticles().register(particle.copy());
        }
    }

    protected boolean addRefParticle(Shape particle) {

        return this.aggregate.getRefParticles().registerWithCheck(particle);
    }

    protected boolean deleteRefParticle(Shape particle) {

        return this.aggregate.getRefParticles().deregisterWithCheck(particle);
    }

    // -------------------------------------------------------------------------------------------------

    protected void setParticleDelta(double delta) {

        this.aggregate.getRefParticles().forEach(e -> e.setDelta(delta));
    }

    protected void setParticleEpsilon(double epsilon) {

        this.aggregate.getRefParticles().forEach(e -> e.setEpsilon(epsilon));
    }

    // -------------------------------------------------------------------------------------------------

    protected void index() {

        int i = 0;
        for (Shape shape : this.aggregate.getRefParticles()) {
            shape.setIndex(i++);
        }
    }

    public void merge(FAggregate arg, boolean removeParticles) {

        for (Shape shape : arg.getRefParticles()) {
            this.aggregate.getRefParticles().register(shape);
        }

        if (removeParticles) {
            arg.getRefParticles().clear();
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected void translate(double x, double y, double z) {

        this.aggregate.getRefParticles().translate(x, y, z);
    }

    protected void translate(FPoint offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void translate(FPos3D offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void translate(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        this.aggregate.getRefParticles().translate(bX, bY, bZ, hX, hY, hZ);
    }

    protected void translate(FVector offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void translate(FPairPos3D offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    // -------------------------------------------------------------------------------------------------

    protected double project(FAggregate target, FVector dir) {
        FVector translator = dir.copy();
        List<Shape> candidates = new ArrayList<>(this.aggregate.getRefParticles().asList());

        FPoint centerArg = target.getCenter(this.factory.getFPoint(), Center.SPATIAL);

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(centerArg)));

        for (Shape candidate : candidates) {
            translator.moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(target, translator);

            if (shift >= 0) {
                translator.setMagnitude(shift);

                boolean overlaps = this.aggregate.overlapsWithShift(target, translator);

                if (!overlaps) {
                    for (Shape particle : this.aggregate) {
                        translator.set(dir);
                        this.factory.getFRayHelper().shiftForward(translator, particle, shift);
                    }

                    return shift;
                }
            }
        }

        return -1;
    }

    protected double project(FAggregate target, FVector dir, double distLimit) {
        FPoint centerRef = this.aggregate.getCenter(this.factory.getFPoint(), Center.SPATIAL);
        FPoint centerArg = target.getCenter(this.factory.getFPoint(), Center.SPATIAL);

        if (centerRef.getDistance(centerArg) > this.aggregate.getRadiusFrom(centerRef) + this.aggregate.getRadiusFrom(centerArg) + distLimit) {
            return -1;
        }

        FVector translator = dir.copy();
        List<Shape> candidates = new ArrayList<>(this.aggregate.getRefParticles().asList());

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(centerArg)));

        for (Shape candidate : candidates) {
            translator.moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(target, translator);

            if (shift >= 0 && shift <= distLimit) {
                translator.setMagnitude(shift);

                boolean overlaps = this.aggregate.overlapsWithShift(target, translator);

                if (!overlaps) {
                    for (Shape particle : this.aggregate) {
                        translator.set(dir);
                        this.factory.getFRayHelper().shiftForward(translator, particle, shift);
                    }

                    return shift;
                }
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------------------------------

    protected void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {
        List<Shape> candidates = new ArrayList<>();

        Queue<Shape> queue = new LinkedList<>(this.aggregate.getRefParticles().asList());

        queue.poll();

        for (Shape shape : this.aggregate) {
            candidates.clear();

            shape.touchesOrOverlaps(queue, candidates);

            candidates.forEach(e -> consumer.accept(shape, e));

            queue.poll();
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected void shiftBoundaryToZero() {
        FPos3D boundary = this.aggregate.getBoundary().getPosA();

        this.aggregate.forEach(e -> e.translate(-boundary.getD0(), -boundary.getD1(), -boundary.getD2()));
    }

    protected void rotate(FMatrix3x3D matrix) {

        this.aggregate.forEach(e -> e.rotate(matrix));
    }

    protected void pca() {

        this.aggregate.rotate(this.aggregate.getRotationPCA());
    }
}
