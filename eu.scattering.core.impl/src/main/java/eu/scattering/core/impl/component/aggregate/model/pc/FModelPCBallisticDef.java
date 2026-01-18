package eu.scattering.core.impl.component.aggregate.model.pc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.aspect.rotate.FRotAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.construct.ray.FRay;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.primitive.FPos2D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.Dimension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelPCBallisticDef implements FModelPCBallistic {
    private static final int MAX_IT_GLOBAL = 10;
    private static final int MIN_SIZE = 3;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, Shape>> monitors;
    private final List<BiFunction<FAggregate, Shape, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private final FRandAspect random;
    private final FRotAspect rotation;

    private final FAggregate aggregate;

    private final FPoint center;
    private final FRay pathRnd, pathDir;

    private final FAssembly<Shape> attached;
    private final List<Shape> detached;

    private double distance;

    private FModelPCBallisticDef(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.dimension = dimension;

        this.monitors = new ArrayList<>();
        this.acceptors = new ArrayList<>();
        this.validators = new ArrayList<>();

        this.random = factory.getRandAspect();
        this.rotation = factory.getRotAspect();

        this.aggregate = aggregate;

        this.center = factory.getFPoint();
        this.pathRnd = factory.getFRay();
        this.pathDir = factory.getFRay();

        this.attached = this.aggregate.getRefParticles();
        this.detached = new ArrayList<>(this.aggregate.size());
    }

    public static FModelPCBallistic create(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        return new FModelPCBallisticDef(dimension, aggregate, factory);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < MIN_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + MIN_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            while (this.detached.size() != 0) {
                if (!buildStep()) {
                    continue generation;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, null));

            for (var validator : this.validators) {
                if (validator.apply(this.aggregate, validation)) {
                    continue;
                }

                validation++;

                continue generation;
            }

            return;
        }

        throw new RuntimeException("The aggregate could not be built");
    }

    private void init() {
        this.attached.register(this.detached);

        this.detached.clear();
        this.detached.addAll(this.attached.asList());

        this.attached.clear();

        initParticleA();
    }

    private void initParticleA() {
        Shape particle = this.random.getFRand().getElement(this.detached, true);

        particle.setCenter(0, 0, 0);

        this.monitors.forEach(e -> e.accept(null, particle));
        this.attached.register(particle);
    }

    private boolean buildStep() {

        adjustParameters();

        step:
        while (true) {
            Shape particle = this.random.getFRand().getElement(this.detached, false);

            projectVariantDimension(particle);

            for (var acceptor : this.acceptors) {
                if (!acceptor.apply(this.aggregate, particle)) {

                    continue step;
                }
            }

            this.monitors.forEach(e -> e.accept(this.aggregate, particle));
            this.attached.register(particle);
            this.detached.remove(particle);

            return true;
        }
    }

    private void projectVariantDimension(Shape particle) {

        switch (this.dimension) {
            case D3 -> project3D(particle);
            case D2 -> project2D(particle);
        }
    }

    private void project3D(Shape particle) {
        FVector vectorRnd = this.pathRnd.getRefOrigin();
        FPoint baseRnd = vectorRnd.getRefBase();
        FPoint headRnd = vectorRnd.getRefHead();
        FVector vectorDir = this.pathDir.getRefOrigin();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        while (true) {
            FPos3D pos3D = this.random.getFRand().nextDoubleOnSphere(4 * this.distance);

            baseRnd.set(0, 0, 0);
            headRnd.set(pos3D);

            vectorRnd.moveBase(this.center);

            this.random.ortToBaseInCircle(headDir, this.pathRnd, this.distance);

            baseDir.set(headRnd);

            double distance = particle.projectFrom(this.attached, this.pathDir);

            if (distance >= 0) {
                break;
            }
        }
    }

    private void project2D(Shape particle) {
        FVector vectorDir = this.pathDir.getRefOrigin();
        FPoint baseDir = vectorDir.getRefBase();
        FPoint headDir = vectorDir.getRefHead();

        while (true) {
            FPos2D pos2D = this.random.getFRand().nextDoubleOnCircle(4 * this.distance);
            double pos1D = this.random.getFRand().nextDouble(-this.distance, this.distance);

            baseDir.set(pos2D, 0);
            headDir.set(pos1D, 0, 0);

            this.rotation.setRgAngle(headDir, baseDir, Math.PI * 0.5);

            vectorDir.translate(this.center);

            double distance = particle.projectFrom(this.attached, this.pathDir);

            if (distance >= 0) {
                break;
            }
        }
    }

    private void adjustParameters() {
        this.aggregate.getCenter(this.center, Center.SPATIAL);
        this.distance = this.aggregate.getRadius(this.center);
    }

    //--------------------------------------------------

    @Override
    public void addStepMonitor(BiConsumer<FAggregate, Shape> monitor) {

        this.monitors.add(monitor);
    }

    @Override
    public void addStepAcceptor(BiFunction<FAggregate, Shape, Boolean> acceptor) {

        this.acceptors.add(acceptor);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.validators.add(validator);
    }
}
