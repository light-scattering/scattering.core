package eu.scattering.core.impl.engine.randomize.module;

import eu.scattering.core.design.engine.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.engine.randomize.generator.module.dist2d.joint.FDist2DJoint;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.storage.FPos2D.FPos2D;

public class FDist2DJointDef implements FDist2DJoint {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private final FDist1D dX, dY;

    private FDist2DJointDef(FDist1D dX, FDist1D dY) {

        this.dX = dX;
        this.dY = dY;
    }

    public static FDist2DJoint get(FDist1D dX, FDist1D dY) {

        return new FDist2DJointDef(dX, dY);
    }

    @Override
    public FPos2D produce() {

        return factory.getFPos2D(
                this.dX.produce(),
                this.dY.produce()
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.dX.produce();
        in[1] = this.dY.produce();
    }

    private void validate(double[] in) {

        if (in == null) {
            throw new NullPointerException("The input array is null");
        }

        if (in.length < 2) {
            throw new IllegalArgumentException("The input array does not contain the required number of elements");
        }
    }
}
