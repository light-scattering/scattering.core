package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.transfer.box.FBoxDouble;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.type.RadiusOfGyration;

public class FAggregateModuleRadiusOfGyrationDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleRadiusOfGyrationDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected double get(RadiusOfGyration type) {

        return switch (type) {
            case COMPLEX -> getComplex();
            case SIMPLE_MONO -> getSimpleMono();
            case SIMPLE_POLY -> getSimplePoly();
            case SIMPLE_FILIPPOV -> getSimpleFilippov();
        };
    }

    private double getComplex() {

        if (this.aggregate.getRefFBuffer() == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        FBoxDouble numerator = this.factory.getFBoxDouble();
        FBoxDouble denominator = this.factory.getFBoxDouble();

        FPoint center = this.factory.getFPoint();

        this.aggregate.getMassCenter(center);

        if (this.aggregate.getRefFMaterial() == null) {
            for (Shape shape : this.aggregate) {
                getComplexShapeMath(numerator, denominator, center, shape);
            }
        } else {
            for (Shape shape : this.aggregate) {
                getComplexShapePhys(numerator, denominator, center, shape);
            }
        }

        return Math.sqrt(numerator.getValue() / denominator.getValue());
    }

    private void getComplexShapeMath(FBoxDouble numerator, FBoxDouble denominator, FPoint center, Shape shape) {
        this.aggregate.getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(this.aggregate.getRefFBuffer(), this.aggregate.getRefParticles().asList());

        this.aggregate.getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            numerator.setValue(numerator.getValue() + (unitVolume * Math.pow(center.getDistance(d0, d1, d2), 2)));
            denominator.setValue(denominator.getValue() + unitVolume);
        });
    }

    private void getComplexShapePhys(FBoxDouble numerator, FBoxDouble denominator, FPoint center, Shape shape) {
        this.aggregate.getRefFBuffer().clear();

        double unitVolume = shape.fillVolumeArray(this.aggregate.getRefFBuffer(), this.aggregate.getRefParticles().asList());

        this.aggregate.getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            double mass = unitVolume * this.aggregate.getRefFMaterial().getDensity(meta.getMeta());

            numerator.setValue(numerator.getValue() + (mass * Math.pow(center.getDistance(d0, d1, d2), 2)));
            denominator.setValue(denominator.getValue() + mass);
        });
    }

    private double getSimpleFilippov() {
        double avgRadius = 0;
        int size = this.aggregate.size();

        for (Shape shape: this.aggregate) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / size;

        FPoint massCenter = factory.getFPoint();

        for (Shape shape: this.aggregate) {
            massCenter.setX(massCenter.getX() + shape.getCenterX());
            massCenter.setY(massCenter.getY() + shape.getCenterY());
            massCenter.setZ(massCenter.getZ() + shape.getCenterZ());
        }

        massCenter.setX(massCenter.getX() / size);
        massCenter.setY(massCenter.getY() / size);
        massCenter.setZ(massCenter.getZ() / size);

        double numerator = 0;

        for (Shape shape: this.aggregate) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / size) + Math.pow(avgRadius, 2));
    }

    private double getSimpleMono() {
        double avgRadius = 0;
        int size = this.aggregate.size();

        for (Shape shape: this.aggregate) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / size;

        FPoint massCenter = factory.getFPoint();

        for (Shape shape: this.aggregate) {
            massCenter.setX(massCenter.getX() + shape.getCenterX());
            massCenter.setY(massCenter.getY() + shape.getCenterY());
            massCenter.setZ(massCenter.getZ() + shape.getCenterZ());
        }

        massCenter.setX(massCenter.getX() / size);
        massCenter.setY(massCenter.getY() / size);
        massCenter.setZ(massCenter.getZ() / size);

        double numerator = 0;

        for (Shape shape: this.aggregate) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / size) + (0.6 * avgRadius));
    }

    private double getSimplePoly() {
        double avgRadius = 0;
        int size = this.aggregate.size();

        for (Shape shape: this.aggregate) {
            avgRadius += shape.getRadius();
        }

        avgRadius = avgRadius / size;

        FPoint massCenter = factory.getFPoint();

        double massTotal = this.aggregate.getRefFMaterial() == null ?
                getSimplePolyMath(massCenter) : getSimplePolyPhys(massCenter);

        massCenter.setX(massCenter.getX() / massTotal);
        massCenter.setY(massCenter.getY() / massTotal);
        massCenter.setZ(massCenter.getZ() / massTotal);

        double numerator = 0;

        for (Shape shape: this.aggregate) {
            numerator += Math.pow(shape.getDistCenter(massCenter), 2);
        }

        return Math.sqrt((numerator / size) + (0.6 * avgRadius));
    }

    private double getSimplePolyMath(FPoint massCenter) {
        double volumeTotal = 0;

        for (Shape shape: this.aggregate) {
            double volumeParticle = shape.getVolumeAlgebraic();

            massCenter.setX(massCenter.getX() + (volumeParticle * shape.getCenterX()));
            massCenter.setY(massCenter.getY() + (volumeParticle * shape.getCenterY()));
            massCenter.setZ(massCenter.getZ() + (volumeParticle * shape.getCenterZ()));

            volumeTotal += volumeParticle;
        }

        return volumeTotal;
    }

    private double getSimplePolyPhys(FPoint massCenter) {
        double massTotal = 0;

        for (Shape shape: this.aggregate) {
            double massParticle = getParticleMass(shape);

            massCenter.setX(massCenter.getX() + (massParticle * shape.getCenterX()));
            massCenter.setY(massCenter.getY() + (massParticle * shape.getCenterY()));
            massCenter.setZ(massCenter.getZ() + (massParticle * shape.getCenterZ()));

            massTotal += massParticle;
        }

        return massTotal;
    }

    // -------------------------------------------------------------------------------------------------

    private double getParticleMass(Shape shape) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            FBufferData meta = shape.getMetaData().get(i);

            mass += shape.getLayerVolume(i) * this.aggregate.getRefFMaterial().getDensity(meta.getMeta());
        }

        return mass;
    }
}
