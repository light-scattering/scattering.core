package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.annotation.Legacy;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FMaterial;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.function.BiConsumer;

public interface FAggregate extends Component {

    FAssembly<Shape> getParticles();
    FAggregate setParticles(FAssembly<Shape> particles);

    FMesh<FBufferData> getVolumeMesh();

    double getVolume();
    double getVolume(double[] layers);
    double getVolumeRadius();
    double getVolumeRadius(double[] layers);

    double getSurface();
    double getSurface(double[] layers);
    double getSurfaceRadius();
    double getSurfaceRadius(double[] layers);

    FPairPos3D getRange();
    FPos3D getLength();

    double getMaxLength();

    void getMassCenter(FPoint center);
    FPos3D getMassCenter();

    void getSpatialCenter(FPoint center);
    FPos3D getSpatialCenter();

    void getSphericalCenter(FPoint center);
    FPos3D getSphericalCenter();

    void positionCenter(FPoint center);
    void positionCenter(FPos3D center);

    double getRadius(double x, double y, double z);
    double getRadius(FPoint center);
    double getRadius(FPos3D center);
    double getRadiusFromOrigin();

    double getRadiusOfGyration();
    double getOverlapFactor();
    double getBoxDimension();

    FStat1D getPairDistance();
    FStat1D getTripletAngle();

    boolean isCompact();
    boolean isSparse();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    FStat1D getStatRadius();

    //--------------------------------------------------

    FAggregate copy();

    boolean isExact(FAggregate aggregate);
    boolean isExactData(FAggregate aggregate);

    //--------------------------------------------------

    @Legacy
    double getOverlapFactorLinear();
    @Legacy
    double getRadiusOfGyrationMonodisperse();
    @Legacy
    double getRadiusOfGyrationPolydisperse();

    @Modificator
    FBuffer<FBufferData> getRefBuffer();
    @Modificator
    FAggregate setRefBuffer(FBuffer<FBufferData> dipoles);

    @Modificator
    FAssembly<Shape> getRefParticles();
    @Modificator
    FAggregate setRefParticles(FAssembly<Shape> particles);

    @Modificator
    FMaterial getRefMaterial();
    @Modificator
    FAggregate setRefMaterial(FMaterial material);
}
