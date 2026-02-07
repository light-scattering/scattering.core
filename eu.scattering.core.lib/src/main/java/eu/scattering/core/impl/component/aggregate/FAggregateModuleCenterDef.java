package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.storage.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.transfer.single.variants.FPos3D;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.utility.type.Center;
import eu.scattering.core.design.utility.type.MassCenter;

public class FAggregateModuleCenterDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleCenterDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected void getCenter(FPoint in, Center type) {

        switch (type) {
            case ORIGIN -> in.set(0, 0, 0);
            case MASS -> getMassCenter(in, MassCenter.ADAPTIVE);
            case SPATIAL -> getSpatialCenter(in);
            case SPHERICAL -> getSphericalCenter(in, 100);
        }
    }

    protected FPos3D getCenter(Center type) {
        FPoint center = factory.getFPoint();

        getCenter(center, type);

        return center.toFPos3D();
    }

    protected void getSpatialCenter(FPoint in) {

        this.aggregate.getRefParticles().getSpatialCenter(in);
    }

    protected FPos3D getSpatialCenter() {
        FPoint center = factory.getFPoint();

        getSpatialCenter(center);

        return center.toFPos3D();
    }

    protected void getSphericalCenter(FPoint in, int steps) {

        this.aggregate.getRefParticles().getSphericalCenter(in, steps);
    }

    protected FPos3D getSphericalCenter(int steps) {
        FPoint center = this.factory.getFPoint();

        getSphericalCenter(center, steps);

        return center.toFPos3D();
    }

    protected void getMassCenter(FPoint in, MassCenter type) {

        switch (type) {
            case SIMPLE_MONO -> getMassCenterMethodSimpleMono(in);
            case SIMPLE_POLY -> getMassCenterMethodSimplePoly(in);
            case COMPLEX -> getMassCenterMethodComplex(in);
            case ADAPTIVE -> getMassCenterMethodAdaptive(in);
        }
    }

    protected FPos3D getMassCenter(MassCenter type) {
        FPoint center = this.factory.getFPoint();

        getMassCenter(center, type);

        return center.toFPos3D();
    }

    private void getMassCenterMethodSimpleMono(FPoint in) {
        double radius = this.aggregate.getFStatParticleRadius().mean();

        double volume = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            volume += getMassCenterMethodSimpleMonoStep(in, shape, radius);
        }

        in.divFactor(volume);
    }

    private void getMassCenterMethodSimplePoly(FPoint in) {
        double volume = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            volume += getMassCenterMethodSimplePolyStep(in, shape);
        }

        in.divFactor(volume);
    }

    private void getMassCenterMethodAdaptive(FPoint in) {
        double volume = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            volume += getMassCenterMethodAdaptiveStep(in, shape);
        }

        in.divFactor(volume);
    }

    private void getMassCenterMethodComplex(FPoint in) {
        double volume = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            volume += getMassCenterMethodComplexStep(in, shape);
        }

        in.divFactor(volume);
    }

    private double getMassCenterMethodSimpleMonoStep(FPoint center, Shape shape, double radius) {

        if (shape.getCoatCount() > 0) {
            throw new IllegalArgumentException("SIMPLE_MONO option cannot be used with coated particles");
        }

        return getMassCenterMethodSimpleMonoPrecise(center, shape, radius);
    }

    private double getMassCenterMethodSimpleMonoPrecise(FPoint center, Shape shape, double radius) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            return getMassCenterMethodSimpleMonoPreciseMath(center, shape, radius);
        }

        return getMassCenterMethodSimpleMonoPrecisePhys(center, shape, radius);
    }

    private double getMassCenterMethodSimpleMonoPreciseMath(FPoint center, Shape shape, double radius) {
        double volume = this.factory.getFSphereHelper().getVolume(radius);

        center.setX(center.getX() + (shape.getCenterX() * volume));
        center.setY(center.getY() + (shape.getCenterY() * volume));
        center.setZ(center.getZ() + (shape.getCenterZ() * volume));

        return volume;
    }

    private double getMassCenterMethodSimpleMonoPrecisePhys(FPoint center, Shape shape, double radius) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        double volume = this.factory.getFSphereHelper().getVolume(radius);
        double mass = volume * material.getDensity(shape.getMeta());

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterMethodSimplePolyStep(FPoint center, Shape shape) {

        return getMassCenterMethodSimplePolyPrecise(center, shape);
    }

    private double getMassCenterMethodSimplePolyPrecise(FPoint center, Shape shape) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            return getMassCenterMethodSimplePolyPreciseMath(center, shape);
        }

        return getMassCenterMethodSimplePolyPrecisePhys(center, shape);
    }

    private double getMassCenterMethodSimplePolyPreciseMath(FPoint center, Shape shape) {
        double volume = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume += shape.getLayerVolume(i);
        }

        center.setX(center.getX() + (shape.getCenterX() * volume));
        center.setY(center.getY() + (shape.getCenterY() * volume));
        center.setZ(center.getZ() + (shape.getCenterZ() * volume));

        return volume;
    }

    private double getMassCenterMethodSimplePolyPrecisePhys(FPoint center, Shape shape) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            String meta = shape.getMetaData().get(i).getMeta();

            mass += shape.getLayerVolume(i) * material.getDensity(meta);
        }

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterMethodComplexStep(FPoint center, Shape shape) {

        return getMassCenterMethodComplexApprox(center, shape);
    }

    private double getMassCenterMethodComplexApprox(FPoint center, Shape shape) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            return getMassCenterMethodComplexApproxMath(center, shape);
        }

        return getMassCenterMethodComplexApproxPhys(center, shape);
    }

    private double getMassCenterMethodComplexApproxMath(FPoint center, Shape shape) {
        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        buffer.clear();

        double unitVolume = shape.fillVolumeArray(buffer, this.aggregate.getRefParticles().asList());

        FBoxDouble volume = this.factory.getFBoxDouble();

        this.aggregate.getRefFExtension().getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            center.setX(center.getX() + (d0 * unitVolume));
            center.setY(center.getY() + (d1 * unitVolume));
            center.setZ(center.getZ() + (d2 * unitVolume));

            volume.setValue(volume.getValue() + unitVolume);
        });

        return volume.getValue();
    }

    private double getMassCenterMethodComplexApproxPhys(FPoint center, Shape shape) {
        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        buffer.clear();

        double unitVolume = shape.fillVolumeArray(buffer, this.aggregate.getRefParticles().asList());

        FBoxDouble mass = this.factory.getFBoxDouble();

        this.aggregate.getRefFExtension().getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            double unitMass = unitVolume * material.getDensity(meta.getMeta());

            center.setX(center.getX() + (d0 * unitMass));
            center.setY(center.getY() + (d1 * unitMass));
            center.setZ(center.getZ() + (d2 * unitMass));

            mass.setValue(mass.getValue() + unitMass);
        });

        return mass.getValue();
    }

    private double getMassCenterMethodAdaptiveStep(FPoint center, Shape shape) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            return getMassCenterMethodSimplePolyPrecise(center, shape);
        }

        return getMassCenterMethodComplexApprox(center, shape);
    }

    // -------------------------------------------------------------------------------------------------

    protected void setPositionAsZero(FPoint center) {

        this.aggregate.getRefParticles().translate(-center.getX(), -center.getY(), -center.getZ());
    }

    protected void setPositionAsZero(FPos3D center) {

        this.aggregate.getRefParticles().translate(-center.getD0(), -center.getD1(), -center.getD2());
    }

    protected void setCenterAsZero(Center type) {

        switch (type) {
            case ORIGIN -> {}
            case MASS -> setMassCenterAsZero(MassCenter.ADAPTIVE);
            case SPATIAL -> setSpatialCenterAsZero();
            case SPHERICAL -> setSphericalCenterAsZero(100);
        }
    }

    protected void setMassCenterAsZero(MassCenter type) {
        FPoint center = this.factory.getFPoint();

        getMassCenter(center, type);

        setPositionAsZero(center);
    }

    protected void setSpatialCenterAsZero() {
        FPoint center = this.factory.getFPoint();

        getSpatialCenter(center);

        setPositionAsZero(center);
    }

    protected void setSphericalCenterAsZero(int steps) {
        FPoint center = this.factory.getFPoint();

        getSphericalCenter(center, steps);

        setPositionAsZero(center);
    }

    protected void setCenter(Center type, double x, double y, double z) {

        this.aggregate.getRefParticles().translate(getCenter(type), x, y, z);
    }

    protected void setCenter(Center type, FPoint position) {

        setCenter(type, position.getX(), position.getY(), position.getZ());
    }

    protected void setCenter(Center type, FPos3D position) {

        setCenter(type, position.getD0(), position.getD1(), position.getD2());
    }

    protected void setMassCenter(double x, double y, double z, MassCenter type) {

        this.aggregate.getRefParticles().translate(getMassCenter(type), x, y, z);
    }

    protected void setMassCenter(FPoint position, MassCenter type) {

        setMassCenter(position.getX(), position.getY(), position.getZ(), type);
    }

    protected void setMassCenter(FPos3D position, MassCenter type) {

        setMassCenter(position.getD0(), position.getD1(), position.getD2(), type);
    }

    protected void setSpatialCenter(double x, double y, double z) {

        this.aggregate.getRefParticles().translate(getSpatialCenter(), x, y, z);
    }

    protected void setSpatialCenter(FPoint position) {

        setSpatialCenter(position.getX(), position.getY(), position.getZ());
    }

    protected void setSpatialCenter(FPos3D position) {

        setSpatialCenter(position.getD0(), position.getD1(), position.getD2());
    }

    protected void setSphericalCenter(double x, double y, double z, int steps) {

        this.aggregate.getRefParticles().translate(getSphericalCenter(steps), x, y, z);
    }

    protected void setSphericalCenter(FPoint position, int steps) {

        setSphericalCenter(position.getX(), position.getY(), position.getZ(), steps);
    }

    protected void setSphericalCenter(FPos3D position, int steps) {

        setSphericalCenter(position.getD0(), position.getD1(), position.getD2(), steps);
    }
}
