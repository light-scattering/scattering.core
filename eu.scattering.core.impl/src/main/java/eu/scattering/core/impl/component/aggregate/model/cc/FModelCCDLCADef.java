package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.dlca.FModelCCDLCA;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.lambda.TriConsumer;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.Dimension;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class FModelCCDLCADef implements FModelCCDLCA {
    private static final int AGGREGATE_SIZE = 6;
    private static final int FRAGMENT_SIZE = 3;
    private static final int MAX_IT_GLOBAL = 10;

    private final Dimension dimension;

    private final List<BiConsumer<FAggregate, FAggregate>> monitors;
    private final List<BiFunction<FAggregate, FAggregate, Boolean>> acceptors;
    private final List<BiFunction<FAggregate, Integer, Boolean>> validators;

    private TriConsumer<FAggregate, FRandAspect, FPoint> movement;

    private final ScatFactory factory;
    private final FRandAspect random;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private final FPoint cAggA, cAggB;

    private final FVector path;

    private final FPoint tmpFPoint;
    private final FVector tmpFVector;

    private double rAggA, rAggB;

    private double rSpawn, rExile;
    private double fSpawn, fExile, fStep;

    private boolean internal;

    private double rp;

    private boolean symmetry;

    private FModelCCDLCADef(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

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

        this.factory = factory;
        this.random = this.factory.getRandAspect();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();

        this.cAggA = factory.getFPoint();
        this.cAggB = factory.getFPoint();

        this.path = factory.getFVector();

        this.tmpFPoint = factory.getFPoint();
        this.tmpFVector = factory.getFVector();

        this.fExile = 4;
        this.fSpawn = 4;
        this.fStep = 1;

        this.symmetry = true;

        setMovementVariantDimension();
    }

    public static FModelCCDLCA create(Dimension dimension, FAggregate aggregate, ScatFactory factory) {

        return new FModelCCDLCADef(dimension, aggregate, factory);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < AGGREGATE_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + AGGREGATE_SIZE + " particles");
        }

        int iteration = 0;
        int validation = 0;

        generation:
        while (iteration ++ < MAX_IT_GLOBAL) {

            init();

            while (this.fragments.size() > 1) {
                buildStepVariantSymmetry();
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
        this.rp = this.aggregate.getFStatParticleRadius().mean();

        distributeFragments();
        buildFragments();

        for (FAggregate fragment : this.fragments) {
            this.monitors.forEach(e -> e.accept(null, fragment));
        }

        shuffleFragments();
    }

    private void buildStepVariantSymmetry() {

        if (this.symmetry) {
            buildStepSymmetric();
        } else {
            buildStepRandom();
        }
    }

    private void buildStepSymmetric() {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            adjustParameters(aggA, aggB);

            main:
            while(true) {

                positionVariantDimension(aggA, aggB);

                while (true) {
                    this.path.set(0, 0, 0, 0, 0, 0);

                    this.movement.accept(aggB, this.random, this.path.getRefHead());

                    buildStepValidationVersionDimension();

                    boolean isPositioned = move(aggA, aggB);

                    if (this.cAggA.getDistance(this.cAggB) > this.rExile) {
                        continue main;
                    }

                    if (!isPositioned) {
                        continue;
                    }

                    for (var acceptor : this.acceptors) {
                        if (!acceptor.apply(aggA, aggB)) {

                            continue main;
                        }
                    }

                    this.monitors.forEach(e -> e.accept(aggA, aggB));

                    aggA.merge(aggB, true);

                    break main;
                }
            }
        }

        buildStepCleanup();
    }

    private void buildStepRandom() {
        FAggregate aggA;
        FAggregate aggB;

        do {
            aggA = this.random.getFRand().getElement(this.fragments, false);
            aggB = this.random.getFRand().getElement(this.fragments, false);
        } while (aggA == aggB);

        buildStepCore(aggA, aggB);

        buildStepCleanup();
    }

    private void buildStepCore(FAggregate aggA, FAggregate aggB) {

        adjustParameters(aggA, aggB);

        main:
        while(true) {

            positionVariantDimension(aggA, aggB);

            while (true) {
                this.path.set(0, 0, 0, 0, 0, 0);

                this.movement.accept(aggB, this.random, this.path.getRefHead());

                buildStepValidationVersionDimension();

                boolean isPositioned = move(aggA, aggB);

                if (this.cAggA.getDistance(this.cAggB) > this.rExile) {
                    continue main;
                }

                if (!isPositioned) {
                    continue;
                }

                for (var acceptor : this.acceptors) {
                    if (!acceptor.apply(aggA, aggB)) {

                        continue main;
                    }
                }

                this.monitors.forEach(e -> e.accept(aggA, aggB));

                aggA.merge(aggB, true);

                break main;
            }
        }
    }

    private void buildStepCleanup() {

        removeFragments();
        shuffleFragments();
    }

    private void buildStepValidationVersionDimension() {

        if (dimension.equals(Dimension.D2)) {
            if (this.path.getRefHead().getZ() < 0 || this.path.getRefHead().getZ() > 0) {
                throw new IllegalStateException("The position of at least one particle is not 2D");
            }
        }
    }

    private void distributeFragments() {

        this.fragments.clear();

        for (int i = 0; i < this.aggregate.size() / FRAGMENT_SIZE; i++) {
            this.fragments.add(this.factory.getFAggregate());
        }

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            this.fragments.get(i % this.fragments.size()).addRefParticle(this.aggregate.getRefParticles().asList().get(i));
        }
    }

    private void buildFragments() {

        for (FAggregate fragment : this.fragments) {
            factory.getFModelContext().pc().dla(dimension, fragment).build();
        }

        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer != null) {
            for (FAggregate fragment : this.fragments) {
                fragment.setRefFBuffer(buffer);
            }
        }
    }

    private void shuffleFragments() {

        this.random.getFRand().shuffle(this.fragments);
    }

    private void removeFragments() {
        List<FAggregate> elements = this.fragments.stream().filter((fragment) -> fragment.size() > 0).toList();

        this.fragments.clear();
        this.fragments.addAll(elements);
    }

    //--------------------------------------------------

    private void setMovementVariantDimension() {

        switch (this.dimension) {
            case D3 -> setMovement3D();
            case D2 -> setMovement2D();
        }
    }

    private void setMovement2D() {

        this.movement = (aggregate, random, position) ->
                position.set(this.random.getFRand().nextDoubleOnCircle(this.rp * this.fStep), 0);
    }

    private void setMovement3D() {

        this.movement = (aggregate, random, position) ->
                position.set(this.random.getFRand().nextDoubleOnSphere(this.rp * this.fStep));
    }

    private void positionVariantDimension(FAggregate aggA, FAggregate aggB) {

        if (this.internal) {
            switch (this.dimension) {
                case D3 -> positionInternal3D(aggA, aggB);
                case D2 -> positionInternal2D(aggA, aggB);
            }
        } else {
            switch (this.dimension) {
                case D3 -> positionExternal3D(aggB);
                case D2 -> positionExternal2D(aggB);
            }
        }
    }

    private void positionExternal2D(FAggregate aggB) {

        tmpFPoint.set(this.random.getFRand().nextDoubleOnCircle(this.rSpawn), 0);
        tmpFPoint.add(this.cAggA);

        aggB.getRefParticles().translate(this.cAggB, this.tmpFPoint);

        this.cAggB.set(this.tmpFPoint);
    }

    private void positionExternal3D(FAggregate aggB) {

        tmpFPoint.set(this.random.getFRand().nextDoubleOnSphere(this.rSpawn));
        tmpFPoint.add(this.cAggA);

        aggB.getRefParticles().translate(this.cAggB, this.tmpFPoint);

        this.cAggB.set(this.tmpFPoint);
    }

    private void positionInternal2D(FAggregate aggA, FAggregate aggB) {

        do {
            tmpFPoint.set(this.random.getFRand().nextDoubleInCircle(this.rSpawn), 0);
            tmpFPoint.add(this.cAggA);

            aggB.getRefParticles().translate(this.cAggB, this.tmpFPoint);

            this.cAggB.set(this.tmpFPoint);

        } while (aggA.overlaps(aggB));
    }

    private void positionInternal3D(FAggregate aggA, FAggregate aggB) {

        do {
            tmpFPoint.set(this.random.getFRand().nextDoubleInSphere(this.rSpawn));
            tmpFPoint.add(this.cAggA);

            aggB.getRefParticles().translate(this.cAggB, this.tmpFPoint);

            this.cAggB.set(this.tmpFPoint);

        } while (aggA.overlaps(aggB));
    }

    private void adjustParameters(FAggregate aggA, FAggregate aggB) {
        aggA.getCenter(this.cAggA, Center.SPATIAL);
        aggB.getCenter(this.cAggB, Center.SPATIAL);

        this.rAggA = this.aggregate.getRadiusFrom(this.cAggA);
        this.rAggB = this.aggregate.getRadiusFrom(this.cAggB);

        this.rSpawn = this.rAggA + (this.rp * this.fSpawn) + this.rAggB;
        this.rExile = this.rSpawn + (this.rp * this.fExile) + this.rAggB;
    }

    private boolean move(FAggregate aggA, FAggregate aggB) {
        double maxShift = this.path.getMagnitude();

        if (this.cAggA.getDistance(this.cAggB) > this.rAggA + this.rAggB + maxShift) {
            shiftGeometry(aggB, maxShift);

            return false;
        }

        if (!aggB.overlapsWithShift(aggA, this.path)) {
            shiftGeometry(aggB, maxShift);

            return false;
        }

        return project(aggA, aggB, maxShift);
    }

    private boolean project(FAggregate aggA, FAggregate aggB, double maxShift) {
        List<Shape> candidates = new ArrayList<>(aggB.getRefParticles().asList());

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(this.cAggA)));

        for (Shape candidate : candidates) {
            this.path.moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(aggA, this.path);

            if (shift >= 0 && shift <= maxShift) {
                this.tmpFVector.set(this.path);
                this.tmpFVector.setMagnitude(shift);

                boolean overlaps = aggB.overlapsWithShift(aggA, this.tmpFVector);

                if (!overlaps) {
                    shiftGeometry(aggB, shift);

                    return true;
                }
            }

        }

        shiftGeometry(aggB, maxShift);

        return false;
    }

    private void shiftGeometry(FAggregate aggB, double shift) {

        for (Shape particle : aggB) {
            this.factory.getFRayHelper().shiftForward(this.path, particle, shift);
        }

        this.factory.getFRayHelper().shiftForward(path, this.cAggB, shift);
    }

    //--------------------------------------------------

    @Override
    public boolean getSymmetry() {

        return this.symmetry;
    }

    @Override
    public void setSymmetry(boolean symmetry) {

        this.symmetry = symmetry;
    }

    @Override
    public boolean getInternalSpawn() {

        return this.internal;
    }

    @Override
    public void setInternalSpawn(boolean internal) {

        this.internal = internal;
    }

    @Override
    public double getStepFactor() {

        return this.fStep;
    }

    @Override
    public void setStepFactor(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The step factor must be greater than zero");
        }

        this.fStep = factor;
    }

    @Override
    public double getSpawnFactor() {

        return this.fSpawn;
    }

    @Override
    public void setSpawnFactor(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The spawn factor must be greater than zero");
        }

        this.fSpawn = factor;
    }

    @Override
    public double getExileFactor() {

        return this.fExile;
    }

    @Override
    public void setExileFactor(double factor) {

        if (factor <= 0) {
            throw new IllegalArgumentException("The exile factor must be greater than zero");
        }

        this.fExile = factor;
    }

    @Override
    public TriConsumer<FAggregate, FRandAspect, FPoint> getMovement() {

        return this.movement;
    }

    @Override
    public void setMovement(TriConsumer<FAggregate, FRandAspect, FPoint> movement) {

        this.movement = movement;
    }

    @Override
    public void addStepMonitor(BiConsumer<FAggregate, FAggregate> monitor) {

        this.monitors.add(monitor);
    }

    @Override
    public void addStepAcceptor(BiFunction<FAggregate, FAggregate, Boolean> acceptor) {

        this.acceptors.add(acceptor);
    }

    @Override
    public void addCompletionValidator(BiFunction<FAggregate, Integer, Boolean> validator) {

        this.validators.add(validator);
    }
}
