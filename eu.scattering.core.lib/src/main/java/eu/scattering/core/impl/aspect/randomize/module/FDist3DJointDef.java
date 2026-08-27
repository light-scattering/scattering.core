package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.joint.FDist3DJoint;
import eu.scattering.core.design.storage.transfer.TransferFactory;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;

public class FDist3DJointDef implements FDist3DJoint {
    private final TransferFactory factoryExt;

    private final FDist1D d0, d1, d2;

    private FDist3DJointDef(TransferFactory factoryExt, FDist1D d0, FDist1D d1, FDist1D d2) {
        this.factoryExt = factoryExt;

        this.d0 = d0;
        this.d1 = d1;
        this.d2 = d2;
    }

    public static FDist3DJoint create(TransferFactory factoryExt, FDist1D d0, FDist1D d1, FDist1D d2) {

        return new FDist3DJointDef(factoryExt, d0, d1, d2);
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
