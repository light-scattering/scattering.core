package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.MassCenter;
import eu.scattering.core.design.utility.type.RadiusOfGyration;

public class FAggregateModuleRadiusOfGyrationDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleRadiusOfGyrationDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getRadiusOfGyration(RadiusOfGyration type) {

        return switch (type) {
            case SIMPLE_MONO -> getRadiusOfGyrationSimpleMono(Correction.NONE);
            case SIMPLE_MONO_06R1 -> getRadiusOfGyrationSimpleMono(Correction._06R1);
            case SIMPLE_MONO_10R2, DEDICATED_FILIPPOV -> getRadiusOfGyrationSimpleMono(Correction._10R2);
            case SIMPLE_POLY -> getRadiusOfGyrationSimplePoly(Correction.NONE);
            case SIMPLE_POLY_06R1 -> getRadiusOfGyrationSimplePoly(Correction._06R1);
            case SIMPLE_POLY_10P2 -> getRadiusOfGyrationSimplePoly(Correction._10R2);
            case COMPLEX -> getRadiusOfGyrationComplex();
        };
    }

    private double getRadiusOfGyrationSimpleMono(Correction type) {
        FComplex data = this.factory.getFComplex();
        double radius = this.aggregate.getFStatParticleRadius().mean();

        getRadiusOfGyrationSimpleMonoPrecise(data, radius);

        return correction(data, type, radius);
    }

    private void getRadiusOfGyrationSimpleMonoPrecise(FComplex data, double radius) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            getRadiusOfGyrationSimpleMonoPreciseMath(data);
        } else {
            getRadiusOfGyrationSimpleMonoPrecisePhys(data, radius);
        }
    }

    private void getRadiusOfGyrationSimpleMonoPreciseMath(FComplex data) {
        FPos3D center = this.aggregate.getMassCenter(MassCenter.SIMPLE_MONO);

        for (Shape shape : this.aggregate) {
            data.setRe(data.getRe() + shape.getDistCenterP2(center));
        }

        data.setIm(this.aggregate.size());
    }

    private void getRadiusOfGyrationSimpleMonoPrecisePhys(FComplex data, double radius) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();
        FPos3D center = this.aggregate.getMassCenter(MassCenter.SIMPLE_MONO);

        for (Shape shape : this.aggregate) {
            double mass = getParticleMassMono(shape, radius, material);

            data.setRe(data.getRe() + shape.getDistCenterP2(center) * mass);
            data.setIm(data.getIm() + mass);
        }
    }

    private double getRadiusOfGyrationSimplePoly(Correction type) {
        FComplex data = this.factory.getFComplex();
        FPoint center = this.aggregate.getMassCenter(this.factory.getFPoint(), MassCenter.SIMPLE_POLY);

        for (Shape shape : this.aggregate) {
            getRadiusOfGyrationSimplePolyPrecise(data, center, shape);
        }

        return correction(data, type);
    }

    private void getRadiusOfGyrationSimplePolyPrecise(FComplex data, FPoint center, Shape shape) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            getRadiusOfGyrationSimplePolyPreciseMath(data, center, shape);
        } else {
            getRadiusOfGyrationSimplePolyPrecisePhys(data, center, shape);
        }
    }

    private void getRadiusOfGyrationSimplePolyPreciseMath(FComplex data, FPoint center, Shape shape) {
        double volume = shape.getVolumeAlgebraic();

        data.setRe(data.getRe() + shape.getDistCenterP2(center) * volume);
        data.setIm(data.getIm() + volume);
    }

    private void getRadiusOfGyrationSimplePolyPrecisePhys(FComplex data, FPoint center, Shape shape) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        double mass = getParticleMass(shape, material);

        data.setRe(data.getRe() + shape.getDistCenterP2(center) * mass);
        data.setIm(data.getIm() + mass);
    }

    private double getRadiusOfGyrationComplex() {
        FComplex data = this.factory.getFComplex();
        FPoint center = this.aggregate.getMassCenter(this.factory.getFPoint(), MassCenter.COMPLEX);

        for (Shape shape : this.aggregate) {
            getRadiusOfGyrationComplexApprox(data, center, shape);
        }

        return correction(data, Correction.NONE);
    }

    private void getRadiusOfGyrationComplexApprox(FComplex data, FPoint center, Shape shape) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            getRadiusOfGyrationComplexApproxMath(data, center, shape);
        } else {
            getRadiusOfGyrationComplexApproxPhys(data, center, shape);
        }
    }

    private void getRadiusOfGyrationComplexApproxMath(FComplex data, FPoint center, Shape shape) {
        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        buffer.clear();

        double unitVolume = shape.fillVolumeArray(buffer, this.aggregate.getRefParticles().asList());

        this.aggregate.getRefFExtension().getRefFBuffer().forEach((index, d0, d1, d2, dummy, meta) -> {
            data.setRe(data.getRe() + (unitVolume * center.getDistanceP2(d0, d1, d2)));
            data.setIm(data.getIm() + unitVolume);
        });
    }

    private void getRadiusOfGyrationComplexApproxPhys(FComplex data, FPoint center, Shape shape) {
        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        buffer.clear();

        double unitVolume = shape.fillVolumeArray(buffer, this.aggregate.getRefParticles().asList());

        this.aggregate.getRefFExtension().getRefFBuffer().forEach((index, d0, d1, d2, dummy, meta) -> {
            double mass = unitVolume * material.getDensity(meta.getMeta());

            data.setRe(data.getRe() + (mass * center.getDistanceP2(d0, d1, d2)));
            data.setIm(data.getIm() + mass);
        });
    }

    private double correction(FComplex data, Correction type) {

        return correction(data, type, -1);
    }

    private double correction(FComplex data, Correction type, double radius) {

        switch (type) {
            case NONE -> {

                return Math.sqrt((data.getRe() / data.getIm()));
            }
            case _06R1 -> {

                if (radius < 0) {
                    radius = this.aggregate.getFStatParticleRadius().mean();
                }

                return Math.sqrt((data.getRe() / data.getIm()) + (0.6 * radius));
            }
            case _10R2 -> {

                if (radius < 0) {
                    radius = this.aggregate.getFStatParticleRadius().mean();
                }

                return Math.sqrt((data.getRe() / data.getIm()) + (radius * radius));
            }
        }

        throw new IllegalStateException("Unknown RoG correction");
    }

    // -------------------------------------------------------------------------------------------------

    private double getParticleMass(Shape shape, FMaterial material) {
        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            mass += shape.getLayerVolume(i) * material.getDensity(shape.getMeta(i));
        }

        return mass;
    }

    private double getParticleMassMono(Shape shape, double radius, FMaterial material) {

        if (shape.getCoatCount() > 0) {
            throw new IllegalArgumentException("SIMPLE_MONO option cannot be used with coated particles");
        }

        return  this.factory.getFSphereHelper().getVolume(radius) * material.getDensity(shape.getMeta());
    }

    // -------------------------------------------------------------------------------------------------

    private enum Correction {
        NONE, _06R1, _10R2,
    }
}
