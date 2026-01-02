package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.annotation.Fragment;
import eu.scattering.core.design.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.FractalDimension;
import eu.scattering.core.design.type.LinearDimension;
import eu.scattering.core.design.type.RadiusOfGyration;

import java.util.function.BiConsumer;

public interface FAggregate extends FAggregateModuleInteraction, Component, Iterable<Shape> {

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
    double getLength(LinearDimension type);

    double getRadius(double x, double y, double z);
    double getRadius(FPoint center);
    double getRadius(FPos3D center);
    double getRadius(Center type);

    //--------------------------------------------------

    FPoint getCenter(FPoint in, Center type);
    FPos3D getCenter(Center type);

    FPoint getMassCenter(FPoint in);
    FPos3D getMassCenter();

    FPoint getSpatialCenter(FPoint in);
    FPos3D getSpatialCenter();

    FPoint getSphericalCenter(FPoint in);
    FPos3D getSphericalCenter();

    void setCenter(Center type, double x, double y, double z);
    void setCenter(Center type, FPoint position);
    void setCenter(Center type, FPos3D position);

    void resetCenter(Center type);

    void resetPosition(FPoint center);
    void resetPosition(FPos3D center);

    //--------------------------------------------------

    double getRadiusOfGyration(RadiusOfGyration type);

    //--------------------------------------------------

    double getFractalDimension(FractalDimension type);

    FPlot getBoxCoverageFunction(boolean log);
    FPlot getDensityCorrelationFunction(boolean log);

    //--------------------------------------------------

    double getVolumetricOverlapFactor();
    double getLinearOverlapFactor();

    //--------------------------------------------------

    int size();

    boolean addParticle(Shape particle);
    boolean removeParticle(Shape particle);

    FStat getTripletAngle();
    FPlot getTripletAngleFunction();

    FStat getPairDistance();
    FPlot getPairDistanceFunction();

    FStat getCoordinationNumber();
    FPlot getCoordinationNumberFunction();

    FStat getFStatParticleRadius();
    FStat getFStatDistance(Center type);

    void setParticleDelta(double delta);
    void setParticleEpsilon(double epsilon);

    boolean isSparse();
    boolean isCompact();

    boolean touches(FAggregate arg);
    boolean overlaps(FAggregate arg);

    void merge(FAggregate arg, boolean removeParticles);

    void translate(double x, double y, double z);
    void translate(FPoint offset);
    void translate(FPos3D offset);

    void translate(double bX, double bY, double bZ, double hX, double hY, double hZ);
    void translate(FVector offset);
    void translate(FPairPos3D offset);

    void index();

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    //--------------------------------------------------

    FAggregate copy();

    boolean isExact(FAggregate aggregate);
    boolean isExactData(FAggregate aggregate);

    //--------------------------------------------------

    FAggregate addFBuffer(int capacity);
    FAggregate addFMaterial();

    @Fragment
    boolean overlapsWithShift(FAggregate arg, FVector shift);
    @Fragment
    boolean overlapsWithRotation(FAggregate arg, FVector axis, double degree);

    @Modificator
    FAssembly<Shape> getRefParticles();

    @Modificator
    FAggregate setRefFBuffer(FBuffer<FBufferData> refFBuffer);
    @Modificator
    FAggregate setRefFMaterial(FMaterial refMaterial);

    @Modificator
    FBuffer<FBufferData> getRefFBuffer();
    @Modificator
    FMaterial getRefFMaterial();
}
