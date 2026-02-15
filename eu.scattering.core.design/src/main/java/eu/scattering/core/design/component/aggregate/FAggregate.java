package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.utility.annotation.Modificator;
import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.aggregate.extension.FExtension;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.utility.type.method.MassCenter;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.method.Surface;
import eu.scattering.core.design.utility.type.method.Volume;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;

import java.util.function.BiConsumer;

public interface FAggregate extends Component, Iterable<Shape> {

    int size();

    double getSurface(Surface type);
    double getSurface(double[] layers, Surface type);

    double getSurfaceRadius(Surface type);
    double getSurfaceRadius(double[] layers, Surface type);

    double getVolume(Volume type);
    double getVolume(double[] layers, Volume type);

    double getVolumeRadius(Volume type);
    double getVolumeRadius(double[] layers, Volume type);

    FMesh<FBufferData> getVolumeMesh();

    FPairPos3D getBoundary();

    FPos3D getLength();
    double getLength(Length type);

    double getRadiusFrom(double x, double y, double z);
    double getRadiusFrom(FPoint center);
    double getRadiusFrom(FPos3D center);
    double getRadiusFrom(Center type);

    void setRadiusFrom(double x, double y, double z, double radius);
    void setRadiusFrom(FPoint center, double radius);
    void setRadiusFrom(FPos3D center, double radius);
    void setRadiusFrom(Center type, double radius);

    FStat getFStatDistance(Center type);
    FStat getFStatParticleRadius();

    //--------------------------------------------------

    FPoint getCenter(FPoint in, Center type);
    FPos3D getCenter(Center type);

    FPoint getMassCenter(FPoint in, MassCenter type);
    FPos3D getMassCenter(MassCenter type);

    FPoint getSpatialCenter(FPoint in);
    FPos3D getSpatialCenter();

    FPoint getSphericalCenter(FPoint in, int steps);
    FPos3D getSphericalCenter(int steps);

    void setCenter(Center type, double x, double y, double z);
    void setCenter(Center type, FPoint position);
    void setCenter(Center type, FPos3D position);
    void setCenterAsZero(Center type);

    void setMassCenter(double x, double y, double z, MassCenter type);
    void setMassCenter(FPoint position, MassCenter type);
    void setMassCenter(FPos3D position, MassCenter type);
    void setMassCenterAsZero(MassCenter type);

    void setSpatialCenter(double x, double y, double z);
    void setSpatialCenter(FPoint position);
    void setSpatialCenter(FPos3D position);
    void setSpatialCenterAsZero();

    void setSphericalCenter(double x, double y, double z, int steps);
    void setSphericalCenter(FPoint position, int steps);
    void setSphericalCenter(FPos3D position, int steps);
    void setSphericalCenterAsZero(int steps);

    void setPositionAsZero(FPoint center);
    void setPositionAsZero(FPos3D center);

//    FMatrix3x3D getGyrationTensor()

    //--------------------------------------------------

    double getRadiusOfGyration(RadiusOfGyration type);

    //--------------------------------------------------

    double getFractalDimension(FractalDimension type);

    FPlot getBoxCoverageFunction(boolean log);
    FPlot getDensityCorrelationFunction(boolean log);

    //--------------------------------------------------

    FStat getOverlapFactor(OverlapFactor type);

    boolean isNonOverlapping();

    boolean isConnected();
    boolean isPointConnected();

    boolean touches(FAggregate arg);
    boolean overlaps(FAggregate arg);

    @Fragment
    boolean overlapsWithShift(FAggregate arg, FVector shift);
    @Fragment
    boolean overlapsWithRotation(FAggregate arg, FVector axis, double degree);

    //--------------------------------------------------

    FStat getTripletAngle();
    FPlot getTripletAngleFunction();

    FStat getPairDistance();
    FPlot getPairDistanceFunction();

    FStat getCoordinationNumber();
    FPlot getCoordinationNumberFunction();

    //--------------------------------------------------

    void addParticles(Shape particle, double quantity);

    void setParticleDelta(double delta);
    void setParticleEpsilon(double epsilon);

    void index();

    void merge(FAggregate arg, boolean removeParticles);

    void translate(double x, double y, double z);
    void translate(FPoint offset);
    void translate(FPos3D offset);

    void translate(double bX, double bY, double bZ, double hX, double hY, double hZ);
    void translate(FVector offset);
    void translate(FPairPos3D offset);

    void forEachPairInContact(BiConsumer<Shape, Shape> consumer);

    double project(FAggregate arg, FVector dir);
    double project(FAggregate arg, FVector dir, double distLimit);

    @Modificator
    boolean addRefParticle(Shape particle);
    @Modificator
    boolean deleteRefParticle(Shape particle);

    //--------------------------------------------------

    FAggregate copy(boolean deep);

    boolean isExact(FAggregate aggregate);
    boolean isExactData(FAggregate aggregate);

    //--------------------------------------------------

    @Modificator
    FAssembly<Shape> getRefParticles();

    FAggregate addFBuffer(int capacity);
    FAggregate addFMaterial();

    @Modificator
    FAggregate setRefFBuffer(FBuffer<FBufferData> buffer);
    @Modificator
    FAggregate setRefFMaterial(FMaterial material);

    @Modificator
    FExtension getRefFExtension();
}
