package eu.scattering.core.impl.aspect.randomize.distribution.dist3D;

import eu.scattering.core.design.aspect.randomize.distribution.dist1d.FRandDist1D;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.joint.FRandDist3DJoint;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public class FRandDist3DJointDef implements FRandDist3DJoint {
    private final TransferFactory factoryExt;

    private final FRandDist1D d0, d1, d2;

    private FRandDist3DJointDef(TransferFactory factoryExt, FRandDist1D d0, FRandDist1D d1, FRandDist1D d2) {
        this.factoryExt = factoryExt;

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    public static FRandDist3DJoint create(TransferFactory factoryExt, FRandDist1D d0, FRandDist1D d1, FRandDist1D d2) {

        return new FRandDist3DJointDef(factoryExt, d0, d1, d2);
    }

    @Override
    public FPos3D produce() {

        return factoryExt.getFPos3D(
                this.d0.produce(),
                this.d1.produce(),
                this.d2.produce()
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.d0.produce();
        in[1] = this.d1.produce();
        in[2] = this.d2.produce();
    }

    private void validate(double[] in) {

        if (in == null) {
            throw new NullPointerException("The input array is null");
        }

        if (in.length < 3) {
            throw new IllegalArgumentException("The input array does not contain the required number of elements");
        }
    }
}
