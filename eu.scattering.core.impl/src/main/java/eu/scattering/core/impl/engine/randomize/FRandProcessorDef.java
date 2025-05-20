package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.core.FRandProcessorCore;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.Optional;

public class FRandProcessorDef implements FRandGenerator {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final FPos2D posZero2D = factory.getFPos2D(0, 0);
    private static final FPos3D posZero3D = factory.getFPos3D(0, 0, 0);

    private final FRandProcessorCore core;

    private int retryLimit = -1;

    private double proximityLimit = -1;
    private double proximityLimitP2 = -1;

    //--------------------------------------------------

    private FRandProcessorDef() {

        this.core = FRandCoreOptimizedDef.create();
    }

    private FRandProcessorDef(long seed) {

        this.core = FRandCoreSimpleDef.create(seed);
    }

    public static FRandGenerator create() {

        return new FRandProcessorDef();
    }

    public static FRandGenerator create(long seed) {

        return new FRandProcessorDef(seed);
    }

    //--------------------------------------------------

    @Override
    public Optional<Long> getSeed() {

        return this.core.getSeed();
    }

    @Override
    public Optional<Double> getProximityLimit() {

        if (this.proximityLimit >= 0) {
            return Optional.of(this.proximityLimit);
        }
        return Optional.empty();
    }

    @Override
    public void setProximityLimit(double proximityLimit) {

        if (proximityLimit < 0) {
            throw new IllegalArgumentException("The proximity limit cannot be lower than zero");
        }

        this.proximityLimit = proximityLimit;
        this.proximityLimitP2 = proximityLimit * proximityLimit;
    }

    @Override
    public void clearProximityLimit() {

        this.proximityLimit = -1;
        this.proximityLimitP2 = -1;
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
    public FPos4D nextDouble4D(FPairPos4D range, FPos4D... exclude) {
        int retries = 0;

        while (true) {
            double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
            double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());
            double rndD2 = nextDouble(range.getPosA().getD2(), range.getPosB().getD2());
            double rndD3 = nextDouble(range.getPosA().getD3(), range.getPosB().getD3());

            FPos4D rnd = factory.getFPos4D(rndD0, rndD1, rndD2, rndD3);

            if (valExc4D(rnd, exclude)) {
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

        double d0 = Math.sin(rnd) * radius;
        double d1 = Math.cos(rnd) * radius;

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

            if (distP22D(posZero2D, rnd) < radiusP2) {
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

        double x = 2 * x1 * Math.sqrt(1 - f) * radius;
        double y = 2 * x2 * Math.sqrt(1 - f) * radius;
        double z = (1 - 2 * f) * radius;

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
            if (dist(val, point) < this.proximityLimit) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean valExc2D(FPos2D val, FPos2D... exc) {

        for (FPos2D point : exc) {
            if (distP22D(val, point) < this.proximityLimitP2) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean valExc3D(FPos3D val, FPos3D... exc) {

        for (FPos3D point : exc) {
            if (distP23D(val, point) < this.proximityLimitP2) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean valExc4D(FPos4D val, FPos4D... exc) {

        for (FPos4D point : exc) {
            if (distP24D(val, point) < this.proximityLimitP2) {
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

        var d0 = distP2(val.getD0(), ref.getD0());
        var d1 = distP2(val.getD1(), ref.getD1());

        return d0 + d1;
    }

    private double distP23D(FPos3D val, FPos3D ref) {

        var d0 = distP2(val.getD0(), ref.getD0());
        var d1 = distP2(val.getD1(), ref.getD1());
        var d2 = distP2(val.getD2(), ref.getD2());

        return d0 + d1 + d2;
    }

    private double distP24D(FPos4D val, FPos4D ref) {

        var d0 = distP2(val.getD0(), ref.getD0());
        var d1 = distP2(val.getD1(), ref.getD1());
        var d2 = distP2(val.getD2(), ref.getD2());
        var d3 = distP2(val.getD3(), ref.getD3());

        return d0 + d1 + d2 + d3;
    }
}
