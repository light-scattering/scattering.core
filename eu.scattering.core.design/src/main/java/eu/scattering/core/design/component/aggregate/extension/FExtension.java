package eu.scattering.core.design.component.aggregate.extension;

import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.transfer.complex.FBufferData;

public interface FExtension extends Component {

    void addFBuffer(int capacity);
    void addFMaterial();

    //--------------------------------------------------

    FExtension copy();

    boolean isExact(FExtension arg);

    //--------------------------------------------------

    @Modificator
    FBuffer<FBufferData> getRefFBuffer();
    @Modificator
    void setRefFBuffer(FBuffer<FBufferData> buffer);

    @Modificator
    FMaterial getRefFMaterial();
    @Modificator
    void setRefFMaterial(FMaterial material);
}
