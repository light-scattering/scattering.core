package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.container.DipoleData;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.BiConsumer;

public interface FAggregate extends Component {

    FAssembly<Shape> getParticles();
    FAggregate setParticles(FAssembly<Shape> particles);

    double getVolume();
    double getVolume(double[] layers);

    void getVolumeMesh(FArray<DipoleData> mesh);

    double getSurface();
    double getSurface(double[] layers);

    void getSurfaceMesh(FArray<DipoleData> mesh);

    double getOverlapFactor();
    double getOverlapFactorLegacy();

    boolean isCompact();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

}
