package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist2d.joint.FDist2DJoint;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos2D;

public class FDist2DJointDef implements FDist2DJoint {
    private final TransferFactory factoryExt;

    private final FDist1D d0, d1;

    private FDist2DJointDef(TransferFactory factoryExt, FDist1D d0, FDist1D d1) {
        this.factoryExt = factoryExt;

        this.d0 = d0;
        this.d1 = d1;
    }

    public static FDist2DJoint create(TransferFactory factoryExt, FDist1D d0, FDist1D d1) {

        return new FDist2DJointDef(factoryExt, d0, d1);
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
