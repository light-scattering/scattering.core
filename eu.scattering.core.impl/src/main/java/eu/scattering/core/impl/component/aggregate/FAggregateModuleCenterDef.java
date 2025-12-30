package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.box.FBoxDouble;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.design.type.Center;

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
            case MASS -> getMassCenter(in);
            case SPATIAL -> getSpatialCenter(in);
            case SPHERICAL -> getSphericalCenter(in);
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

    protected void getSphericalCenter(FPoint in) {

        this.aggregate.getRefParticles().getSphericalCenter(in);
    }

    protected FPos3D getSphericalCenter() {
        FPoint center = this.factory.getFPoint();

        getSphericalCenter(center);

        return center.toFPos3D();
    }

    protected void getMassCenter(FPoint in) {
        double volume = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            volume += getMassCenterMethod(in, shape);
        }

        in.setX(in.getX() / volume);
        in.setY(in.getY() / volume);
        in.setZ(in.getZ() / volume);
    }

    protected FPos3D getMassCenter() {
        FPoint center = this.factory.getFPoint();

        getMassCenter(center);

        return center.toFPos3D();
    }

    private double getMassCenterMethod(FPoint center, Shape shape) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            return getMassCenterMethodPrecise(center, shape);
        }

        return getMassCenterMethodApprox(center, shape);
    }

    private double getMassCenterMethodPrecise(FPoint center, Shape shape) {

        if (this.aggregate.getRefFMaterial() == null) {
            return getMassCenterMethodPreciseMath(center, shape);
        }

        return getMassCenterMethodPrecisePhys(center, shape);
    }

    private double getMassCenterMethodPreciseMath(FPoint center, Shape shape) {
        double volume = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume += shape.getLayerVolume(i);
        }

        center.setX(center.getX() + (shape.getCenterX() * volume));
        center.setY(center.getY() + (shape.getCenterY() * volume));
        center.setZ(center.getZ() + (shape.getCenterZ() * volume));

        return volume;
    }

    private double getMassCenterMethodPrecisePhys(FPoint center, Shape shape) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            String meta = shape.getMetaData().get(i).getMeta();

            mass += shape.getLayerVolume(i) * this.aggregate.getRefFMaterial().getDensity(meta);
        }

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterMethodApprox(FPoint center, Shape shape) {

        if (this.aggregate.getRefFMaterial() == null) {
            return getMassCenterMethodApproxMath(center, shape);
        }

        return getMassCenterMethodApproxPhys(center, shape);
    }

    private double getMassCenterMethodApproxMath(FPoint center, Shape shape) {
        this.aggregate.getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(this.aggregate.getRefFBuffer(), this.aggregate.getRefParticles().asList());

        FBoxDouble volume = this.factory.getFBoxDouble();

        this.aggregate.getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            center.setX(center.getX() + (d0 * unitVolume));
            center.setY(center.getY() + (d1 * unitVolume));
            center.setZ(center.getZ() + (d2 * unitVolume));

            volume.setValue(volume.getValue() + unitVolume);
        });

        return volume.getValue();
    }

    private double getMassCenterMethodApproxPhys(FPoint center, Shape shape) {
        this.aggregate.getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(this.aggregate.getRefFBuffer(), this.aggregate.getRefParticles().asList());

        FBoxDouble mass = this.factory.getFBoxDouble();

        this.aggregate.getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            double unitMass = unitVolume * this.aggregate.getRefFMaterial().getDensity(meta.getMeta());

            center.setX(center.getX() + (d0 * unitMass));
            center.setY(center.getY() + (d1 * unitMass));
            center.setZ(center.getZ() + (d2 * unitMass));

            mass.setValue(mass.getValue() + unitMass);
        });

        return mass.getValue();
    }

    // -------------------------------------------------------------------------------------------------

    protected void positionCenter(FPoint center) {

        this.aggregate.getRefParticles().translate(-center.getX(), -center.getY(), -center.getZ());
    }

    protected void positionCenter(FPos3D center) {

        this.aggregate.getRefParticles().translate(-center.getD0(), -center.getD1(), -center.getD2());
    }

    protected void resetCenter(Center type) {

        switch (type) {
            case ORIGIN -> resetCenterOrigin();
            case MASS -> resetCenterMass();
            case SPATIAL -> resetCenterSpatial();
            case SPHERICAL -> resetCenterSpherical();
        }
    }

    private void resetCenterOrigin() {
    }

    private void resetCenterMass() {
        FPoint center = this.factory.getFPoint();

        getMassCenter(center);

        positionCenter(center);
    }

    private void resetCenterSpatial() {
        FPoint center = this.factory.getFPoint();

        getSpatialCenter(center);

        positionCenter(center);
    }

    private void resetCenterSpherical() {
        FPoint center = this.factory.getFPoint();

        getSphericalCenter(center);

        positionCenter(center);
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
}
