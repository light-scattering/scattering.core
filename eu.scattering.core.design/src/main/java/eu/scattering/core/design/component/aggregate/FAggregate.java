package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;

import java.util.function.BiConsumer;

public interface FAggregate extends Component, Iterable<Shape> {

    double getSurface();
    double getSurface(double[] layers);

    double getSurfaceRadius();
    double getSurfaceRadius(double[] layers);

    double getVolume();
    double getVolume(double[] layers);

    double getVolumeRadius();
    double getVolumeRadius(double[] layers);

    FMesh<FBufferData> getVolumeMesh();

    FPairPos3D getBoundary();

    FPos3D getLength();
    double getLength(Axis type);

    double getRadius(double x, double y, double z);
    double getRadius(FPoint center);
    double getRadius(FPos3D center);
    double getRadiusFromOrigin();

    //--------------------------------------------------

    void getMassCenter(FPoint in);
    FPos3D getMassCenter();

    void getSpatialCenter(FPoint in);
    FPos3D getSpatialCenter();

    void getSphericalCenter(FPoint in);
    FPos3D getSphericalCenter();

    void setCenter(FPoint center);
    void setCenter(FPos3D center);

    //--------------------------------------------------

    double getRadiusOfGyration(RadiusOfGyration type);

    //--------------------------------------------------

    double getFractalDimension(Dimension type);

    FPlot getBoxCoverageFunction(boolean log);
    FPlot getDensityCorrelationFunction(boolean log);

    //--------------------------------------------------

    double getVolumetricOverlapFactor();
    double getLinearOverlapFactor();

    //--------------------------------------------------

    int size();

    FStat getTripletAngle();
    FPlot getTripletAngleFunction();

    FStat getPairDistance();
    FPlot getPairDistanceFunction();

    FStat getCoordinationNumber();
    FPlot getCoordinationNumberFunction();

    FStat getParticleRadius();

    void setParticleDelta(double delta);
    void setParticleEpsilon(double epsilon);

    boolean isSparse();
    boolean isCompact();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    //--------------------------------------------------

    FAggregate copy();

    boolean isExact(FAggregate aggregate);
    boolean isExactData(FAggregate aggregate);

    //--------------------------------------------------

    @Modificator
    FAssembly<Shape> getRefParticles();

    FAggregate addFBuffer(int capacity);
    FAggregate addFMaterial();

    @Modificator
    FAggregate setRefFMaterial(FMaterial refMaterial);
    @Modificator
    FAggregate setRefFBuffer(FBuffer<FBufferData> refFBuffer);

    @Modificator
    FMaterial getRefFMaterial();
    @Modificator
    FBuffer<FBufferData> getRefFBuffer();

    //--------------------------------------------------
    
    enum Axis { X, Y, Z, MIN, MAX}
    enum Overlap { VOLUMETRIC, LINEAR }
    enum Dimension { BOX, CORRELATION }
    enum RadiusOfGyration { COMPLEX, SIMPLE_FILIPPOV, SIMPLE_MONO, SIMPLE_POLY }
}
