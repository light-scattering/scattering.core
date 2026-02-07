package eu.scattering.core.impl.aspect.randomize;

import eu.scattering.core.design.aspect.randomize.generator.FRandGenerator;
import eu.scattering.core.design.aspect.randomize.generator.core.FRandCore;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.custom.FDist1DCustom;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.fixed.FDist1DFixed;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.normal.FDist1DNormal;
import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.uniform.FDist1DUniform;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.joint.FDist2DJoint;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.fixed.FDist2DFixed;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.custom.FDist2DCustom;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.uniform.FDist2DUniform;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.joint.FDist3DJoint;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.fixed.FDist3DFixed;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.custom.FDist3DCustom;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.uniform.FDist3DUniform;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos2D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos4D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos4D;
import eu.scattering.core.impl.aspect.randomize.core.FRandCoreOptimizedDef;
import eu.scattering.core.impl.aspect.randomize.core.FRandCoreSimpleDef;
import eu.scattering.core.impl.aspect.randomize.module.*;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

public class FRandGeneratorDef implements FRandGenerator {
    private final TransferFactory factoryExt;

    private final FPos2D posZero2D;
    private final FPos3D posZero3D;

    private final FRandCore core;

    private final int retryLimit = -1;

    //--------------------------------------------------

    private FRandGeneratorDef(TransferFactory factoryExt) {

        this.core = FRandCoreOptimizedDef.create();

        this.factoryExt = factoryExt;

        this.posZero2D = this.factoryExt.getFPos2D(0, 0);
        this.posZero3D = this.factoryExt.getFPos3D(0, 0, 0);
    }

    private FRandGeneratorDef(TransferFactory factoryExt, long seed) {

        this.core = FRandCoreSimpleDef.create(seed);

        this.factoryExt = factoryExt;

        this.posZero2D = this.factoryExt.getFPos2D(0, 0);
        this.posZero3D = this.factoryExt.getFPos3D(0, 0, 0);
    }

    public static FRandGenerator create(TransferFactory factoryExt) {

        return new FRandGeneratorDef(factoryExt);
    }

    public static FRandGenerator create(TransferFactory factoryExt, long seed) {

        return new FRandGeneratorDef(factoryExt, seed);
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

    //--------------------------------------------------

    @Override
    public FDist1DCustom getFDist1DManual(BiConsumer<FRandGenerator, Double[]> consumer) {

        return FDist1DManualDef.get(this, consumer);
    }

    @Override
    public FDist1DFixed getFDist1DFixed(double x) {

        return FDist1DFixedDef.get(x);
    }

    @Override
    public FDist1DNormal getFDist1DNormal(double mean, double std) {

        return FDist1DNormalDef.get(this, mean, std);
    }

    @Override
    public FDist1DUniform getFDist1DUniform(double x1, double x2) {

        return FDist1DUniformDef.get(this, x1, x2);
    }

    @Override
    public FDist2DCustom getFDist2DManual(BiConsumer<FRandGenerator, Double[]> consumer) {

        return FDist2DManualDef.create(this.factoryExt, this, consumer);
    }

    @Override
    public FDist2DJoint getFDist2DJoint(FDist1D dX, FDist1D dY) {

        return FDist2DJointDef.create(this.factoryExt, dX, dY);
    }

    @Override
    public FDist2DFixed getFDist2DFixed(double x, double y) {

        return FDist2DFixedDef.create(this.factoryExt, x, y);
    }

    @Override
    public FDist2DFixed getFDist2DFixed(FPos2D val) {

        return FDist2DFixedDef.create(this.factoryExt, val);
    }

    @Override
    public FDist2DUniform getFDist2DUniform(double x1, double x2, double y1, double y2) {

        return FDist2DUniformDef.create(this.factoryExt, this, x1, x2, y1, y2);
    }

    @Override
    public FDist2DUniform getFDist2DUniform(FPairPos2D range) {

        return FDist2DUniformDef.create(this.factoryExt, this, range);
    }

    @Override
    public FDist3DCustom getFDist3DManual(BiConsumer<FRandGenerator, Double[]> consumer) {

        return FDist3DManualDef.create(this.factoryExt, this, consumer);
    }

    @Override
    public FDist3DJoint getFDist3DJoint(FDist1D dX, FDist1D dY, FDist1D dZ) {

        return FDist3DJointDef.create(this.factoryExt, dX, dY, dZ);
    }

    @Override
    public FDist3DFixed getFDist3DFixed(double x, double y, double z) {

        return FDist3DFixedDef.create(this.factoryExt, x, y, z);
    }

    @Override
    public FDist3DFixed getFDist3DFixed(FPos3D val) {

        return FDist3DFixedDef.create(this.factoryExt, val);
    }

    @Override
    public FDist3DUniform getFDist3DUniform(double x1, double x2, double y1, double y2, double z1, double z2) {

        return FDist3DUniformDef.create(this.factoryExt, this, x1, x2, y1, y2, z1, z2);
    }

    @Override
    public FDist3DUniform getFDist3DUniform(FPairPos3D range) {

        return FDist3DUniformDef.create(this.factoryExt, this, range);
    }
}
