package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.util.annotation.Outdated;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.BiConsumer;

public interface FAggregate extends Component {

    FAssembly<Shape> getParticles();
    FAggregate setParticles(FAssembly<Shape> particles);

    FArrayMesh<FMetaData> getVolumeMesh();

    double getVolume();
    double getVolume(double[] layers);
    double getVolumeRadius();
    double getVolumeRadius(double[] layers);

    double getSurface();
    double getSurface(double[] layers);
    double getSurfaceRadius(double[] layers);

    FPairPos3D getRange();

    void getMassCenter(FPoint center);
    void getSpatialCenter(FPoint center);
    void getSphericalCenter(FPoint center);

    void positionCenter(FPoint center);

    double getRadiusFrom(FPoint center);
    double getRadiusFromZero();

    double getRadiusOfGyration();

    double getOverlapFactor();

    boolean isCompact();
    boolean isSparse();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    //--------------------------------------------------

    FAggregate setMaterialDensity(String material, double density);

    //--------------------------------------------------

    @Outdated
    double getOverlapFactorLegacy();

    @Modificator
    FAssembly<Shape> getRefParticles();
    @Modificator
    FAggregate setRefParticles(FAssembly<Shape> particles);

    @Modificator
    FArray<FMetaData> getRefDipoles();
    @Modificator
    FAggregate setRefDipoles(FArray<FMetaData> dipoles);
}
