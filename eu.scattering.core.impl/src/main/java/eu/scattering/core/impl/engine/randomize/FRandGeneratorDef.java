package eu.scattering.core.impl.engine.randomize;

import eu.scattering.core.design.engine.randomize.generator.FRandGenerator;
import eu.scattering.core.design.engine.randomize.generator.core.FRandCore;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.fixed.FDist1DFixed;
import eu.scattering.core.design.engine.randomize.generator.module.dist1d.uniform.FDist1DUniform;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.composite.FDist2DComposite;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.fixed.FDist2DFixed;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.uniform.FDist2DUniform;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.composite.FDist3DComposite;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.fixed.FDist3DFixed;
import eu.scattering.core.design.engine.randomize.generator.module.dist3d.uniform.FDist3DUniform;
import eu.scattering.core.impl.engine.randomize.core.FRandCoreOptimizedDef;
import eu.scattering.core.impl.engine.randomize.core.FRandCoreSimpleDef;
import eu.scattering.core.impl.engine.randomize.module.*;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPairPos2D.FPairPos2D;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPairPos4D.FPairPos4D;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import eu.scattering.core.transfer.container.storage.FPos4D.FPos4D;

import java.util.Optional;

public class FRandGeneratorDef implements FRandGenerator {
    private static final TransferFactory factory = TransferFactoryConcrete.create();

    private static final FPos2D posZero2D = factory.getFPos2D(0, 0);
    private static final FPos3D posZero3D = factory.getFPos3D(0, 0, 0);

    private final FRandCore core;

    private final int retryLimit = -1;

    //--------------------------------------------------

    private FRandGeneratorDef() {

        this.core = FRandCoreOptimizedDef.create();
    }

    private FRandGeneratorDef(long seed) {

        this.core = FRandCoreSimpleDef.create(seed);
    }

    public static FRandGenerator create() {

        return new FRandGeneratorDef();
    }

    public static FRandGenerator create(long seed) {

        return new FRandGeneratorDef(seed);
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
    public FPos2D nextDouble2D(FPairPos2D range) {
        double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
        double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());

        return factory.getFPos2D(rndD0, rndD1);
    }

    @Override
    public FPos3D nextDouble3D(FPairPos3D range) {
        double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
        double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());
        double rndD2 = nextDouble(range.getPosA().getD2(), range.getPosB().getD2());

        return factory.getFPos3D(rndD0, rndD1, rndD2);
    }

    @Override
    public FPos4D nextDouble4D(FPairPos4D range) {
        double rndD0 = nextDouble(range.getPosA().getD0(), range.getPosB().getD0());
        double rndD1 = nextDouble(range.getPosA().getD1(), range.getPosB().getD1());
        double rndD2 = nextDouble(range.getPosA().getD2(), range.getPosB().getD2());
        double rndD3 = nextDouble(range.getPosA().getD3(), range.getPosB().getD3());

        return factory.getFPos4D(rndD0, rndD1, rndD2, rndD3);
    }

    @Override
    public FPos2D nextDoubleOnCircle(double radius) {
        double rnd = nextDouble(0, 2 * Math.PI);

        double d0 = Math.sin(rnd) * radius;
        double d1 = Math.cos(rnd) * radius;

        return factory.getFPos2D(d0, d1);
    }

    @Override
    public FPos2D nextDoubleInCircle(double radius) {
        double radiusP2 = radius * radius;

        FPos2D posA = factory.getFPos2D(-radius, -radius);
        FPos2D posB = factory.getFPos2D(radius, radius);

        FPairPos2D range = factory.getFPairPos2D(posA, posB);

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

        return factory.getFPos3D(x, y, z);
    }

    @Override
    public FPos3D nextDoubleInSphere(double radius) {
        double radiusP2 = radius * radius;

        FPos3D posA = factory.getFPos3D(-radius, -radius, -radius);
        FPos3D posB = factory.getFPos3D(radius, radius, radius);

        FPairPos3D range = factory.getFPairPos3D(posA, posB);

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

    //--------------------------------------------------

    private double distP2(double val, double ref) {

        return Math.pow(ref - val, 2);
    }

    private double distP22D(FPos2D ref) {

        var d0 = distP2(FRandGeneratorDef.posZero2D.getD0(), ref.getD0());
        var d1 = distP2(FRandGeneratorDef.posZero2D.getD1(), ref.getD1());

        return d0 + d1;
    }

    private double distP23D(FPos3D ref) {

        var d0 = distP2(FRandGeneratorDef.posZero3D.getD0(), ref.getD0());
        var d1 = distP2(FRandGeneratorDef.posZero3D.getD1(), ref.getD1());
        var d2 = distP2(FRandGeneratorDef.posZero3D.getD2(), ref.getD2());

        return d0 + d1 + d2;
    }

    //--------------------------------------------------

    @Override
    public FDist1DFixed getFDist1DFixed(double x) {

        return FDist1DFixedDef.get(x);
    }

    @Override
    public FDist1DUniform getFDist1DUniform(double x1, double x2) {

        return FDist1DUniformDef.get(this, x1, x2);
    }

    @Override
    public FDist2DComposite getFDist2DComposite(FDist1D dX, FDist1D dY) {

        return FDist2DCompositeDef.get(dX, dY);
    }

    @Override
    public FDist2DFixed getFDist2DFixed(double x, double y) {

        return FDist2DFixedDef.get(x, y);
    }

    @Override
    public FDist2DFixed getFDist2DFixed(FPos2D val) {

        return FDist2DFixedDef.get(val);
    }

    @Override
    public FDist2DUniform getFDist2DUniform(double x1, double x2, double y1, double y2) {

        return FDist2DUniformDef.get(this, x1, x2, y1, y2);
    }

    @Override
    public FDist2DUniform getFDist2DUniform(FPairPos2D range) {

        return FDist2DUniformDef.get(this, range);
    }

    @Override
    public FDist3DComposite getFDist3DComposite(FDist1D dX, FDist1D dY, FDist1D dZ) {

        return FDist3DCompositeDef.get(dX, dY, dZ);
    }

    @Override
    public FDist3DFixed getFDist3DFixed(double x, double y, double z) {

        return FDist3DFixedDef.get(x, y, z);
    }

    @Override
    public FDist3DFixed getFDist3DFixed(FPos3D val) {

        return FDist3DFixedDef.get(val);
    }

    @Override
    public FDist3DUniform getFDist3DUniform(double x1, double x2, double y1, double y2, double z1, double z2) {

        return FDist3DUniformDef.get(this, x1, x2, y1, y2, z1, z2);
    }

    @Override
    public FDist3DUniform getFDist3DUniform(FPairPos3D range) {

        return FDist3DUniformDef.get(this, range);
    }
}
