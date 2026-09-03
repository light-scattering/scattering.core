package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.aspect.randomize.engine.FRandEngine;
import eu.scattering.core.design.aspect.randomize.engine.core.FRandEngineCore;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import eu.scattering.core.impl.aspect.randomize.core.FRandEngineCoreOptimizedDef;
import eu.scattering.core.impl.aspect.randomize.core.FRandEngineCoreSimpleDef;

import java.util.List;
import java.util.Optional;

public class FRandEngineDef implements FRandEngine {
    private final TransferFactory factoryExt;

    private final FPos2D posZero2D;
    private final FPos3D posZero3D;

    private final FRandEngineCore core;

    private final int retryLimit = -1;

    //--------------------------------------------------

    private FRandEngineDef(TransferFactory factoryExt) {

        this.core = FRandEngineCoreOptimizedDef.create();

        this.factoryExt = factoryExt;

        this.posZero2D = this.factoryExt.getFPos2D(0, 0);
        this.posZero3D = this.factoryExt.getFPos3D(0, 0, 0);
    }

    private FRandEngineDef(TransferFactory factoryExt, long seed) {

        this.core = FRandEngineCoreSimpleDef.create(seed);

        this.factoryExt = factoryExt;

        this.posZero2D = this.factoryExt.getFPos2D(0, 0);
        this.posZero3D = this.factoryExt.getFPos3D(0, 0, 0);
    }

    public static FRandEngine create(TransferFactory factoryExt) {

        return new FRandEngineDef(factoryExt);
    }

    public static FRandEngine create(TransferFactory factoryExt, long seed) {

        return new FRandEngineDef(factoryExt, seed);
    }

    //--------------------------------------------------

    @Override
    public Optional<Long> getSeed() {

        return this.core.getSeed();
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
    public long nextLong() {

        return this.core.nextLong();
    }

    @Override
    public long nextLong(long origin, long bound) {

        return this.core.nextLong(origin, bound);
    }

    @Override
    public int nextInteger() {

        return this.core.nextInteger();
    }

    @Override
    public int nextInteger(int origin, int bound) {

        return this.core.nextInteger(origin, bound);
    }

    @Override
    public double nextGaussian() {

        return this.core.nextGaussian();
    }

    @Override
    public double nextGaussian(double mean, double std) {

        return this.core.nextGaussian(mean, std);
    }

    @Override
    public FPos2D nextDouble2D(FPairPos2D range) {
        double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
        double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());

        return factoryExt.getFPos2D(rndD0, rndD1);
    }

    @Override
    public FPos3D nextDouble3D(FPairPos3D range) {
        double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
        double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());
        double rndD2 = nextDouble(range.getPosA().getD2(), range.getPosB().getD2());

        return factoryExt.getFPos3D(rndD0, rndD1, rndD2);
    }

    @Override
    public FPos4D nextDouble4D(FPairPos4D range) {
        double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
        double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());
        double rndD2 = nextDouble(range.getPosA().getD2(), range.getPosB().getD2());
        double rndD3 = nextDouble(range.getPosA().getD3(), range.getPosB().getD3());

        return factoryExt.getFPos4D(rndD0, rndD1, rndD2, rndD3);
    }

    @Override
    public FPos2D nextDoubleOnCircle(double radius) {
        double rnd = nextDouble(0, 2 * Math.PI);

        double d0 = Math.sin(rnd) * radius;
        double d1 = Math.cos(rnd) * radius;

        return factoryExt.getFPos2D(d0, d1);
    }

    @Override
    public FPos2D nextDoubleInCircle(double radius) {
        double radiusP2 = radius * radius;

        FPos2D posA = factoryExt.getFPos2D(-radius, -radius);
        FPos2D posB = factoryExt.getFPos2D(radius, radius);

        FPairPos2D range = factoryExt.getFPairPos2D(posA, posB);

        int retries = 0;

        while (true) {
            FPos2D rnd = nextDouble2D(range);

            if (distP22D(rnd) < radiusP2) {
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
    public FPos3D nextDoubleOnSphere(double radius) {
        double x1 = 0, x2 = 0, f = 10;

        while (f >= 1) {
            x1 = 2 * nextDouble() - 1;
            x2 = 2 * nextDouble() - 1;
            f = x1 * x1 + x2 * x2;
        }

        double x = 2 * x1 * Math.sqrt(1 - f) * radius;
        double y = 2 * x2 * Math.sqrt(1 - f) * radius;
        double z = (1 - 2 * f) * radius;

        return factoryExt.getFPos3D(x, y, z);
    }

    @Override
    public FPos3D nextDoubleInSphere(double radius) {
        double radiusP2 = radius * radius;

        FPos3D posA = factoryExt.getFPos3D(-radius, -radius, -radius);
        FPos3D posB = factoryExt.getFPos3D(radius, radius, radius);

        FPairPos3D range = factoryExt.getFPairPos3D(posA, posB);

        int retries = 0;

        while (true) {
            FPos3D rnd = nextDouble3D(range);

            if (distP23D(rnd) < radiusP2) {
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
    public FPos3D nextDoubleInShell(double radiusMin, double radiusMax) {

        if (radiusMin < 0 || radiusMax < 0) {
            throw new IllegalArgumentException("The radius cannot be lower than zero");
        }

        double rMin, rMax;

        if (radiusMin <= radiusMax) {
            rMin = radiusMin;
            rMax = radiusMax;
        } else {
            rMin = radiusMax;
            rMax = radiusMin;
        }

        double radius = Math.cbrt((Math.pow(rMax, 3) - Math.pow(rMin, 3)) * nextDouble() + Math.pow(rMin, 3));

        return nextDoubleOnSphere(radius);
    }

    @Override
    public <T> T getElement(List<T> in, boolean remove) {

        if (in.size() == 0) {
            throw new IllegalArgumentException("The list is empty");
        }

        int index = this.core.nextInteger(0, in.size());

        T element = in.get(index);

        if (remove) {
            in.remove(index);
        }

        return element;
    }

    @Override
    public <T> void shuffle(List<T> in) {

        this.core.shuffle(in);
    }

    //--------------------------------------------------

    private double distP2(double val, double ref) {

        return Math.pow(ref - val, 2);
    }

    private double distP22D(FPos2D ref) {

        var d0 = distP2(this.posZero2D.getD0(), ref.getD0());
        var d1 = distP2(this.posZero2D.getD1(), ref.getD1());

        return d0 + d1;
    }

    private double distP23D(FPos3D ref) {

        var d0 = distP2(this.posZero3D.getD0(), ref.getD0());
        var d1 = distP2(this.posZero3D.getD1(), ref.getD1());
        var d2 = distP2(this.posZero3D.getD2(), ref.getD2());

        return d0 + d1 + d2;
    }
}
