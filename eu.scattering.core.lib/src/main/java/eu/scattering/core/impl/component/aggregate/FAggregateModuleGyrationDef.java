package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.number.complex.FComplex;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.GyrationTensor;
import eu.scattering.core.design.utility.type.method.MassCenter;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;

import java.util.ArrayList;
import java.util.List;

public class FAggregateModuleGyrationDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleGyrationDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getRadiusOfGyration(RadiusOfGyration type) {

        return getRadiusOfGyration(type, null, null, null);
    }

    protected double getRadiusOfGyration(RadiusOfGyration type, FPoint massCenter, List<Double> massFragments, List<FPos3D> centerFragments) {
        Meta meta = new Meta(massCenter, massFragments, centerFragments);

        return switch (type) {
            case SIMPLE_MONO -> getRadiusOfGyrationSimpleMono(Correction.NONE, meta);
            case SIMPLE_MONO_06R1 -> getRadiusOfGyrationSimpleMono(Correction._06R1, meta);
            case SIMPLE_MONO_10R2 -> getRadiusOfGyrationSimpleMono(Correction._10R2, meta);
            case SIMPLE_POLY -> getRadiusOfGyrationSimplePoly(Correction.NONE, meta);
            case SIMPLE_POLY_06R1 -> getRadiusOfGyrationSimplePoly(Correction._06R1, meta);
            case SIMPLE_POLY_10R2 -> getRadiusOfGyrationSimplePoly(Correction._10R2, meta);
            case VOLUMETRIC -> getRadiusOfGyrationComplex(meta);
        };
    }

    private double getRadiusOfGyrationSimpleMono(Correction type, Meta meta) {
        FComplex data = this.factory.getFComplex();
        double radius = this.aggregate.getFStatParticleRadius().mean();

        getRadiusOfGyrationSimpleMonoPrecise(data, radius, meta);

        return correction(data, type, radius);
    }

    private void getRadiusOfGyrationSimpleMonoPrecise(FComplex data, double radius, Meta meta) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            getRadiusOfGyrationSimpleMonoPreciseMath(data, meta);
        } else {
            getRadiusOfGyrationSimpleMonoPrecisePhys(data, radius, meta);
        }
    }

    private void getRadiusOfGyrationSimpleMonoPreciseMath(FComplex data, Meta meta) {
        FPos3D center = this.aggregate.getMassCenter(MassCenter.SIMPLE_MONO, meta.massFragments(), meta.centerFragments());

        if (meta.massCenter() != null) {
            meta.massCenter().set(center);
        }

        for (Shape shape : this.aggregate) {
            data.setRe(data.getRe() + shape.getDistCenterP2(center));
        }

        data.setIm(this.aggregate.size());
    }

    private void getRadiusOfGyrationSimpleMonoPrecisePhys(FComplex data, double radius, Meta meta) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();
        FPos3D center = this.aggregate.getMassCenter(MassCenter.SIMPLE_MONO, meta.massFragments(), meta.centerFragments());

        if (meta.massCenter() != null) {
            meta.massCenter().set(center);
        }

        for (Shape shape : this.aggregate) {
            double mass = getParticleMassMono(shape, radius, material);

            data.setRe(data.getRe() + shape.getDistCenterP2(center) * mass);
            data.setIm(data.getIm() + mass);
        }
    }

    private double getRadiusOfGyrationSimplePoly(Correction type, Meta meta) {
        FComplex data = this.factory.getFComplex();

        FPos3D centerFixed = this.aggregate.getMassCenter(MassCenter.SIMPLE_POLY, meta.massFragments(), meta.centerFragments());
        FPoint center = this.factory.getFPoint(centerFixed);

        if (meta.massCenter() != null) {
            meta.massCenter().set(center);
        }

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

    private double getRadiusOfGyrationComplex(Meta meta) {
        FComplex data = this.factory.getFComplex();

        FPos3D centerFixed = this.aggregate.getMassCenter(MassCenter.VOLUMETRIC, meta.massFragments(), meta.centerFragments());
        FPoint center = this.factory.getFPoint(centerFixed);

        if (meta.massCenter() != null) {
            meta.massCenter().set(center);
        }

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

    protected FMatrix3x3D getGyrationTensor(GyrationTensor type) {
        double[][] tensor = new double[3][3];

        List<Double> massFragments = new ArrayList<>(this.aggregate.size());
        List<FPos3D> massCenters = new ArrayList<>(this.aggregate.size());

        MassCenter massCenterType = switch (type) {
            case ADAPTIVE -> MassCenter.ADAPTIVE;
            case SIMPLE_MONO -> MassCenter.SIMPLE_MONO;
            case SIMPLE_POLY -> MassCenter.SIMPLE_POLY;
            case VOLUMETRIC -> MassCenter.VOLUMETRIC;
        };

        FPos3D massCenter = this.aggregate.getMassCenter(massCenterType, massFragments, massCenters);

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            double massFragment = massFragments.get(i);
            FPos3D centerFragment = massCenters.get(i);

            double dx = centerFragment.getD0() - massCenter.getD0();
            double dy = centerFragment.getD1() - massCenter.getD1();
            double dz = centerFragment.getD2() - massCenter.getD2();

            tensor[0][0] += massFragment * dx * dx;
            tensor[0][1] += massFragment * dx * dy;
            tensor[0][2] += massFragment * dx * dz;

            tensor[1][1] += massFragment * dy * dy;
            tensor[1][2] += massFragment * dy * dz;

            tensor[2][2] += massFragment * dz * dz;
        }

        tensor[1][0] = tensor[0][1];
        tensor[2][0] = tensor[0][2];
        tensor[2][1] = tensor[1][2];

        return this.factory.getFMatrix3x3D(tensor);
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

    private record Meta(FPoint massCenter, List<Double> massFragments, List<FPos3D> centerFragments) {}
}
