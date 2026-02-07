package eu.scattering.core.impl.aspect.randomize.module;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.joint.FDist3DJoint;
import eu.scattering.core.design.storage.StorageFactory;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;

public class FDist3DJointDef implements FDist3DJoint {
    private final StorageFactory factory;

    private final FDist1D dX, dY, dZ;

    private FDist3DJointDef(StorageFactory factory, FDist1D dX, FDist1D dY, FDist1D dZ) {

        this.factory = factory;

        this.dX = dX;
        this.dY = dY;
        this.dZ = dZ;
    }

    public static FDist3DJoint get(StorageFactory factory, FDist1D dX, FDist1D dY, FDist1D dZ) {

        return new FDist3DJointDef(factory, dX, dY, dZ);
    }

    @Override
    public FPos3D produce() {

        return factory.getFPos3D(
                this.dX.produce(),
                this.dY.produce(),
                this.dZ.produce()
        );
    }

    @Override
    public void produce(double[] in) {

        validate(in);

        in[0] = this.dX.produce();
        in[1] = this.dY.produce();
        in[2] = this.dZ.produce();
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
