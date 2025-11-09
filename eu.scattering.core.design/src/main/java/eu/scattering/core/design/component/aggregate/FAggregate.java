package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat1D;
import eu.scattering.core.design.statistics.construct.FPlot2D;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.function.BiConsumer;

public interface FAggregate extends Component {

    int size();

    FAggregate addFBuffer(int capacity);
    FAggregate addFMaterial();

    FMesh<FBufferData> getVolumeMesh();

    double getVolume();
    double getVolume(double[] layers);
    double getVolumeRadius();
    double getVolumeRadius(double[] layers);

    double getSurface();
    double getSurface(double[] layers);
    double getSurfaceRadius();
    double getSurfaceRadius(double[] layers);

    void getMassCenter(FPoint in);
    FPos3D getMassCenter();

    void getSpatialCenter(FPoint in);
    FPos3D getSpatialCenter();

    void getSphericalCenter(FPoint in);
    FPos3D getSphericalCenter();

    void positionCenter(FPoint center);
    void positionCenter(FPos3D center);

    FPairPos3D getBoundary();

    FPos3D getLength();
    double getLengthMax();

    double getRadius(double x, double y, double z);
    double getRadius(FPoint center);
    double getRadius(FPos3D center);
    double getRadiusFromOrigin();

    double getRadiusOfGyration();
    double getRadiusOfGyration(RoG type);

    double getOverlapFactor();
    double getOverlapFactor(OF type);

    double getBoxDimension();

    FStat1D getPairDistance();
    FPlot2D getPairDistanceDistribution();

    FStat1D getTripletAngle(boolean deg);
    FPlot2D getTripletAngleDistribution(boolean deg);

    boolean isCompact();
    boolean isSparse();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    void setEpsilon(double epsilon);
    void setDelta(double delta);

    FStat1D getParticleRadius();

    //--------------------------------------------------

    FAggregate copy();

    boolean isExact(FAggregate aggregate);
    boolean isExactData(FAggregate aggregate);

    //--------------------------------------------------

    @Modificator
    FAssembly<Shape> getRefParticles();
    @Modificator
    FAggregate setRefParticles(FAssembly<Shape> particles);

    @Modificator
    FMaterial getRefFMaterial();
    @Modificator
    FAggregate setRefFMaterial(FMaterial refMaterial);

    @Modificator
    FBuffer<FBufferData> getRefFBuffer();
    @Modificator
    FAggregate setRefFBuffer(FBuffer<FBufferData> refFBuffer);

    //--------------------------------------------------

    enum RoG { COMPLEX, SIMPLE_FILIPPOV, SIMPLE_MONO, SIMPLE_POLY }
    enum OF { VOLUMETRIC, LINEAR }
}
