package eu.scattering.core.impl.production.core.engine.random;

import eu.scattering.core.design.FactoryPrimitive;
import eu.scattering.core.design.elements.data.position.FPairPos2D;
import eu.scattering.core.design.elements.data.position.FPairPos3D;
import eu.scattering.core.design.elements.data.position.FPos2D;
import eu.scattering.core.design.elements.data.position.FPos3D;
import eu.scattering.core.design.elements.engine.random.FRandom;
import eu.scattering.core.design.elements.engine.random.FRandomCore;
import org.json.JSONObject;

import java.util.Optional;

public class FRandomProd implements FRandom {

    private static final FactoryPrimitive factory = FactoryPrimitive.create();

    private static final FPos2D posZero2D = factory.getFPos2D(0, 0);
    private static final FPos3D posZero3D = factory.getFPos3D(0, 0, 0);

    private final FRandomCore core;

    private int retryLimit = -1;

    private double separationDistance = -1;
    private double separationDistanceP2 = -1;

    //--------------------------------------------------

    private FRandomProd() {
        this.core = FRandomCoreOptimized.create();
    }

    private FRandomProd(long seed) {

        this.core = FRandomCoreSimple.create(seed);
    }

    public static FRandom create() {

        return new FRandomProd();
    }

    public static FRandom create(long seed) {

        return new FRandomProd(seed);
    }

    //--------------------------------------------------

    @Override
    public Optional<Long> getSeed() {

        return this.core.getSeed();
    }

    @Override
    public Optional<Double> getSeparationDistance() {

        if (this.separationDistance >= 0) {
            return Optional.of(this.separationDistance);
        }
        return Optional.empty();
    }

    @Override
    public void setSeparationDistance(double separationDistance) {

        if (separationDistance < 0) {
            throw new IllegalArgumentException("The separation distance cannot be lower than zero");
        }

        this.separationDistance = separationDistance;
        this.separationDistanceP2 = separationDistance * separationDistance;
    }

    @Override
    public void clearSeparationDistance() {

        this.separationDistance = -1;
    }

    @Override
    public Optional<Integer> getRetryLimit() {

        if (this.retryLimit >= 0) {
            return Optional.of(this.retryLimit);
        }

        return Optional.empty();
    }

    @Override
    public void setRetryLimit(int retryLimit) {

        if (retryLimit < 0) {
            throw new IllegalArgumentException("The retry limit cannot be lower than zero");
        }

        this.retryLimit = retryLimit;
    }

    @Override
    public void clearRetryLimit() {

        this.retryLimit = -1;
    }

    @Override
    public boolean nextBoolean() {

        return this.core.nextBoolean();
    }

    @Override
    public double nextDouble() {

        return this.core.nextDouble();
    }

    @Override
    public double nextDouble(double origin, double bound) {

        return this.core.nextDouble(origin, bound);
    }

    @Override
    public double nextDouble(double origin, double bound, double... exclude) {
        int retries = 0;

        while (true) {
            double rnd = nextDouble(origin, bound);

            if (valExc1D(rnd, exclude)) {
                return rnd;
            }

            if (this.retryLimit > 0) {
                if (retries > this.retryLimit) {
                    throw new ArithmeticException("The retry limit has been reached");
                }
            }

            retries++;
        }
    }

    @Override
    public FPos2D nextDouble2D(FPairPos2D range, FPos2D... exclude) {
        int retries = 0;

        while (true) {
            double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
            double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());

            FPos2D rnd = factory.getFPos2D(rndD0, rndD1);

            if (valExc2D(rnd, exclude)) {
                return rnd;
            }

            if (this.retryLimit > 0) {
                if (retries > this.retryLimit) {
                    throw new ArithmeticException("The retry limit has been reached");
                }
            }

            retries++;
        }
    }

    @Override
    public FPos3D nextDouble3D(FPairPos3D range, FPos3D... exclude) {
        int retries = 0;

        while (true) {
            double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
            double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());
            double rndD2 = nextDouble(range.getPosA().getD2(), range.getPosB().getD2());

            FPos3D rnd = factory.getFPos3D(rndD0, rndD1, rndD2);

            if (valExc3D(rnd, exclude)) {
                return rnd;
            }

            if (this.retryLimit > 0) {
                if (retries > this.retryLimit) {
                    throw new ArithmeticException("The retry limit has been reached");
                }
            }

            retries++;
        }
    }

    @Override
    public FPos2D nextDoubleOnCircle(double radius, FPos2D... exclude) {
        double rnd = nextDouble(0, 2 * Math.PI);

        double d0 = Math.sin(rnd);
        double d1 = Math.cos(rnd);

        return factory.getFPos2D(d0, d1);
    }

    @Override
    public FPos2D nextDoubleInCircle(double radius, FPos2D... exclude) {
        double radiusP2 = radius * radius;

        FPos2D posA = factory.getFPos2D(-radius, -radius);
        FPos2D posB = factory.getFPos2D(radius, radius);

        FPairPos2D range = factory.getFPairPos2D(posA, posB);

        int retries = 0;

        while (true) {
            FPos2D rnd = nextDouble2D(range, exclude);

            if (distP22D(this.posZero2D, rnd) < radiusP2) {
                if (this.retryLimit > 0) {
                    if (retries > this.retryLimit) {
                        throw new ArithmeticException("The retry limit has been reached");
                    } else {
                        return rnd;
                    }
                } else {
                    return rnd;
                }
            }

            retries++;
        }
    }

    @Override
    public FPos3D nextDoubleOnSphere(double radius, FPos3D... exclude) {
        double x1 = 0, x2 = 0, f = 10;

        while (f >= 1) {
            x1 = 2 * nextDouble() - 1;
            x2 = 2 * nextDouble() - 1;
            f = x1 * x1 + x2 * x2;
        }

        double x = 2 * x1 * Math.sqrt(1 - f);
        double y = 2 * x2 * Math.sqrt(1 - f);
        double z = 1 - 2 * f;

        return factory.getFPos3D(x, y, z);
    }

    @Override
    public FPos3D nextDoubleInSphere(double radius, FPos3D... exclude) {
        double radiusP2 = radius * radius;

        FPos3D posA = factory.getFPos3D(-radius, -radius, -radius);
        FPos3D posB = factory.getFPos3D(radius, radius, radius);

        FPairPos3D range = factory.getFPairPos3D(posA, posB);

        int retries = 0;

        while (true) {
            FPos3D rnd = nextDouble3D(range, exclude);

            if (distP23D(posZero3D, rnd) < radiusP2) {
                if (this.retryLimit > 0) {
                    if (retries > this.retryLimit) {
                        throw new ArithmeticException("The retry limit has been reached");
                    } else {
                        return rnd;
                    }
                } else {
                    return rnd;
                }
            }

            retries++;
        }
    }

    //--------------------------------------------------

    @Override
    public boolean valExc1D(double val, double... exc) {

        for (double point : exc) {
            if (dist(val, point) < this.separationDistance) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean valExc2D(FPos2D val, FPos2D... exc) {

        for (FPos2D point : exc) {
            if (distP22D(val, point) < this.separationDistanceP2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean valExc3D(FPos3D val, FPos3D... exc) {

        for (FPos3D point : exc) {
            if (distP23D(val, point) < this.separationDistanceP2) {
                return false;
            }
        }

        return true;
    }

    //--------------------------------------------------

    private double dist(double val, double ref) {

        return Math.abs(ref - val);
    }

    private double distP2(double val, double ref) {

        return Math.pow(ref - val, 2);
    }

    private double distP22D(FPos2D val, FPos2D ref) {

        return distP2(val.getD0(), ref.getD0()) + distP2(val.getD1(), ref.getD1());
    }

    private double distP23D(FPos3D val, FPos3D ref) {

        return distP2(val.getD0(), ref.getD0()) + distP2(val.getD1(), ref.getD1()) + distP2(val.getD2(), ref.getD2());
    }

    //--------------------------------------------------

    @Override
    public JSONObject exportToJSON() {
        return null;
    }
}
