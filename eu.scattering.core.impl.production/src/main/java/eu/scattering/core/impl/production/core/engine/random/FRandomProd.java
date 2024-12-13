package eu.scattering.core.impl.production.core.engine.random;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.elements.data.position.FPos3D;
import eu.scattering.core.design.elements.engine.random.FRandom;
import org.json.JSONObject;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class FRandomProd implements FRandom {

    private final Factory factory;
    private final Random random;

    private final Long seed;

    private Double limitMin = null;
    private Double limitMax = null;

    private double jitter = 1E-8;

    //--------------------------------------------------

    private FRandomProd(Factory factory) {

        this.factory = factory;
        this.random = null;
        this.seed = null;
    }

    private FRandomProd(Factory factory, long seed) {

        this.factory = factory;
        this.random = new Random(seed);
        this.seed = seed;
    }

    public static FRandomProd create(Factory factory) {

        return new FRandomProd(factory);
    }

    public static FRandomProd create(Factory factory, long seed) {

        return new FRandomProd(factory, seed);
    }

    //--------------------------------------------------

    @Override
    public Optional<Long> getSeed() {

        return Optional.of(seed);
    }

    @Override
    public double getJitter() {

        return jitter;
    }

    @Override
    public FRandom setJitter(double jitter) {

        this.jitter = jitter;

        return this;
    }

    @Override
    public Optional<Double> getLimitMin() {

        return Optional.of(limitMin);
    }

    @Override
    public FRandom setLimitMin(double limitMin) {

        this.limitMin = limitMin;

        return this;
    }

    @Override
    public FRandom removeLimitMin() {

        this.limitMin = null;

        return this;
    }

    @Override
    public Optional<Double> getLimitMax() {

        return Optional.of(this.limitMax);
    }

    @Override
    public FRandom setLimitMax(double limitMax) {

        this.limitMax = limitMax;

        return this;
    }

    @Override
    public FRandom removeLimitMax() {

        this.limitMax = null;

        return this;
    }

    //--------------------------------------------------

    @Override
    public FPos3D getPositionOnUnitSphere() {
        double x1 = 0, x2 = 0, f = 10;

        while (f >= 1) {
            x1 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
            x2 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
            f = x1 * x1 + x2 * x2;
        }

        double x = 2 * x1 * Math.sqrt(1 - f);
        double y = 2 * x2 * Math.sqrt(1 - f);
        double z = 1 - 2 * f;

        return factory.getFPos3D(x, y, z);
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        return null;
    }
}
