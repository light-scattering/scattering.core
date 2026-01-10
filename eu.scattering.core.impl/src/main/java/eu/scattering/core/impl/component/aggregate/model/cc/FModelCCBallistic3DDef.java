package eu.scattering.core.impl.component.aggregate.model.cc;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.aspect.randomize.FRandAspect;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.Center;

import java.util.ArrayList;
import java.util.List;

public class FModelCCBallistic3DDef implements FModelCCBallistic {
    private static final int AGGREGATE_SIZE = 5;
    private static final int FRAGMENT_SIZE = 3;

    private final ScatFactory factory;

    private final FRandAspect rndEng;

    private final FAggregate aggregate;

    private final List<FAggregate> fragments;

    private FModelCCBallistic3DDef(FAggregate aggregate, ScatFactory factory) {

        if (aggregate == null) {
            throw new IllegalArgumentException("The base aggregate is not defined");
        }

        if (factory == null) {
            throw new IllegalArgumentException("The factory is not defined");
        }

        this.factory = factory;

        this.rndEng = factory.getRandAspect();

        this.aggregate = aggregate;

        this.fragments = new ArrayList<>();
    }

    public static FModelCCBallistic create(FAggregate aggregate, ScatFactory factory) {

        return new FModelCCBallistic3DDef(aggregate, factory);
    }

    @Override
    public void build() {

        if (this.aggregate.getRefParticles().size() < AGGREGATE_SIZE) {
            throw new IllegalStateException("The aggregate should consist of at least " + AGGREGATE_SIZE + " particles");
        }

        init();

        while (this.fragments.size() > 1) {
            buildStep();
        }
    }

    private void init() {

        distributeFragments();
        buildFragments();
    }

    private void buildStep() {

        for (int i = 0 ; i < this.fragments.size() - 1 ; i += 2) {
            FAggregate aggA = this.fragments.get(i);
            FAggregate aggB = this.fragments.get(i + 1);

            double shift = -1;

            FPoint cAggA = aggA.getCenter(factory.getFPoint(), Center.SPATIAL);
            double rAggA = aggA.getRadius(cAggA);

            while (shift < 0) {
                FPoint cAggB = aggB.getCenter(factory.getFPoint(), Center.SPATIAL);
                double rAggB = aggB.getRadius(cAggB);

                FPos3D dist = this.rndEng.getFRand().nextDoubleOnSphere((rAggA + rAggB) * 10);

                aggB.getRefParticles().translate(cAggB, dist);

                FPos3D posA = this.rndEng.getFRand().nextDoubleInSphere(rAggA);
                FPos3D posB = this.rndEng.getFRand().nextDoubleInSphere(rAggB);

                FVector dir = factory.getFVector();

                dir.getRefHead().set(posA).add(cAggA);
                dir.getRefBase().set(posB).add(dist);

                shift = aggB.project(aggA, dir);
            }

            aggA.merge(aggB, true);
        }

        removeFragments();
        shuffleFragments();
    }

    private void distributeFragments() {

        this.fragments.clear();

        for (int i = 0; i < this.aggregate.size() / FRAGMENT_SIZE; i++) {
            this.fragments.add(this.factory.getFAggregate());
        }

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            this.fragments.get(i % this.fragments.size()).addParticle(this.aggregate.getRefParticles().asList().get(i));
        }
    }

    private void buildFragments() {

        for (FAggregate fragment : this.fragments) {
            factory.getFModelContext().pc().ballistic(fragment).build();
        }
    }

    private void shuffleFragments() {

        this.rndEng.getFRand().shuffle(this.fragments);
    }

    private void removeFragments() {
        List<FAggregate> elements = this.fragments.stream().filter((fragment) -> fragment.size() > 0).toList();

        this.fragments.clear();
        this.fragments.addAll(elements);
    }
}
