package eu.scattering.core.impl.aspect.randomize.distribution.dist2D;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist2d.joint.FRandDist2DJoint;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FRandDist2DJointDef implements FRandDist2DJoint {
    private final TransferFactory factoryExt;

    private final FRandDist1D d0, d1;

    private FRandDist2DJointDef(TransferFactory factoryExt, FRandDist1D d0, FRandDist1D d1) {
        this.factoryExt = factoryExt;

        this.d0 = d0;
        this.d1 = d1;
    }

    public static FRandDist2DJoint create(TransferFactory factoryExt, FRandDist1D d0, FRandDist1D d1) {

        return new FRandDist2DJointDef(factoryExt, d0, d1);
    }

    @Override
    public FPos2D produce() {

        return factoryExt.getFPos2D(
                this.d0.produce(),
                this.d1.produce()
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.d0.produce();
        in[1] = this.d1.produce();
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
