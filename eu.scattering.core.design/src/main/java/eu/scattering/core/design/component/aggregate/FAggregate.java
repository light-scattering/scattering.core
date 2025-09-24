package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.util.annotation.Modificator;
import eu.scattering.core.design.util.annotation.Legacy;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;

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
    double getSurfaceRadius();
    double getSurfaceRadius(double[] layers);

    FPairPos3D getRange();

    void getMassCenter(FPoint center);
    FPos3D getMassCenter();

    void getSpatialCenter(FPoint center);
    FPos3D getSpatialCenter();

    void getSphericalCenter(FPoint center);
    FPos3D getSphericalCenter();

    void positionCenter(FPoint center);
    void positionCenter(FPos3D center);

    double getRadius(FPoint center);
    double getRadiusFromOrigin();

    double getRadiusOfGyration();

    double getOverlapFactor();

    boolean isCompact();
    boolean isSparse();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    //--------------------------------------------------

    FAggregate setMaterialDensity(String material, double density);

    //--------------------------------------------------

    @Legacy
    double getOverlapFactorLinear();
    @Legacy
    double getRadiusOfGyrationMonodisperse();
    @Legacy
    double getRadiusOfGyrationPolydisperse();

    @Modificator
    FAssembly<Shape> getRefParticles();
    @Modificator
    FAggregate setRefParticles(FAssembly<Shape> particles);

    @Modificator
    FArray<FMetaData> getRefDipoles();
    @Modificator
    FAggregate setRefDipoles(FArray<FMetaData> dipoles);
}
