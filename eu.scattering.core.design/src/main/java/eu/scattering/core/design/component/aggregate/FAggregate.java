package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;

import java.util.function.BiConsumer;

public interface FAggregate extends Component {

    FAssembly<Shape> getParticles();

    FArrayMesh<FMetaData> getVolumeMesh();

    double getVolume();
    double getVolume(double[] layers);

    double getSurface();
    double getSurface(double[] layers);

    double getOverlapFactor();
    double getOverlapFactorLegacy();

    double getRadiusOfGyrationMonodisperse();
    double getRadiusOfGyrationPolydisperse();

    boolean isCompact();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

}
