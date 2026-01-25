package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
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

    protected boolean delRefParticle(Shape particle) {

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
}
