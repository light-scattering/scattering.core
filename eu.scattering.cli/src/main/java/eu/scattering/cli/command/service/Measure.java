package eu.scattering.cli.command.service;

import eu.scattering.cli.type.TYPE_METRIC;
import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.utility.type.method.MassCenter;
import eu.scattering.core.design.utility.type.method.RadiusOfGyration;
import eu.scattering.core.design.utility.type.method.Surface;
import eu.scattering.core.design.utility.type.method.Volume;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class Measure {

    private static final Map<TYPE_METRIC, BiFunction<ScatterFactory, FAggregate, String>> REGISTRY = new EnumMap<>(TYPE_METRIC.class);

    static {
        REGISTRY.put(TYPE_METRIC.np, (factory, agg) -> getNp(agg));
        REGISTRY.put(TYPE_METRIC.rp, Measure::getRp);
        REGISTRY.put(TYPE_METRIC.rp__avg, (factory, agg) -> getRpAvg(agg));
        REGISTRY.put(TYPE_METRIC.rp__std, (factory, agg) -> getRpStd(agg));
        REGISTRY.put(TYPE_METRIC.rp__max, (factory, agg) -> getRpMax(agg));
        REGISTRY.put(TYPE_METRIC.rp__min, (factory, agg) -> getRpMin(agg));
        REGISTRY.put(TYPE_METRIC.conn, (factory, agg) -> isConnected(agg));
        REGISTRY.put(TYPE_METRIC.conn_pt, (factory, agg) -> isPointConnected(agg));
        REGISTRY.put(TYPE_METRIC.conn_non_ov, (factory, agg) -> inNonOverlapping(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_vol, Measure::getOverlapFactorParticleVolumetric);
        REGISTRY.put(TYPE_METRIC.ov_p_vol__avg, (factory, agg) -> getOverlapFactorParticleVolumetricAvg(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_vol__std, (factory, agg) -> getOverlapFactorParticleVolumetricStd(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_vol__max, (factory, agg) -> getOverlapFactorParticleVolumetricMax(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_vol__min, (factory, agg) -> getOverlapFactorParticleVolumetricMin(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_num, Measure::getOverlapFactorParticleQuantitative);
        REGISTRY.put(TYPE_METRIC.ov_p_num__avg, (factory, agg) -> getOverlapFactorParticleQuantitativeAvg(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_num__std, (factory, agg) -> getOverlapFactorParticleQuantitativeStd(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_num__max, (factory, agg) -> getOverlapFactorParticleQuantitativeMax(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_num__min, (factory, agg) -> getOverlapFactorParticleQuantitativeMin(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_lin, Measure::getOverlapFactorParticleLinear);
        REGISTRY.put(TYPE_METRIC.ov_p_lin__avg, (factory, agg) -> getOverlapFactorParticleLinearAvg(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_lin__std, (factory, agg) -> getOverlapFactorParticleLinearStd(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_lin__max, (factory, agg) -> getOverlapFactorParticleLinearMax(agg));
        REGISTRY.put(TYPE_METRIC.ov_p_lin__min, (factory, agg) -> getOverlapFactorParticleLinearMin(agg));
        REGISTRY.put(TYPE_METRIC.ov_c_vol, Measure::getOverlapFactorClusterVolumetric);
        REGISTRY.put(TYPE_METRIC.len, Measure::getLength);
        REGISTRY.put(TYPE_METRIC.box, Measure::getBoundary);
        REGISTRY.put(TYPE_METRIC.diam, (factory, agg) -> getDiameter(agg));
        REGISTRY.put(TYPE_METRIC.len_x, (factory, agg) -> getLengthX(agg));
        REGISTRY.put(TYPE_METRIC.len_y, (factory, agg) -> getLengthY(agg));
        REGISTRY.put(TYPE_METRIC.len_z, (factory, agg) -> getLengthZ(agg));
        REGISTRY.put(TYPE_METRIC.r_cm_adapt, Measure::getRadiusCmAdaptive);
        REGISTRY.put(TYPE_METRIC.r_cm_mono, Measure::getRadiusCmSimpleMono);
        REGISTRY.put(TYPE_METRIC.r_cm_poly, Measure::getRadiusCmSimplePoly);
        REGISTRY.put(TYPE_METRIC.r_cm_mesh, Measure::getRadiusCmComplex);
        REGISTRY.put(TYPE_METRIC.r_cs, Measure::getRadiusCs);
        REGISTRY.put(TYPE_METRIC.r_cb, Measure::getRadiusCb);
        REGISTRY.put(TYPE_METRIC.cm_adapt, Measure::getCmAdaptive);
        REGISTRY.put(TYPE_METRIC.cm_mono, Measure::getCmSimpleMono);
        REGISTRY.put(TYPE_METRIC.cm_poly, Measure::getCmSimplePoly);
        REGISTRY.put(TYPE_METRIC.cm_mesh, Measure::getCmComplex);
        REGISTRY.put(TYPE_METRIC.cs, Measure::getCs);
        REGISTRY.put(TYPE_METRIC.cb, Measure::getCb);
        REGISTRY.put(TYPE_METRIC.vol_adapt, (factory, agg) -> getVolumeAdaptive(agg));
        REGISTRY.put(TYPE_METRIC.vol_sum, (factory, agg) -> getVolumeSimple(agg));
        REGISTRY.put(TYPE_METRIC.vol_mesh, (factory, agg) -> getVolumeComplex(agg));
        REGISTRY.put(TYPE_METRIC.srf_adapt, (factory, agg) -> getSurfaceAdaptive(agg));
        REGISTRY.put(TYPE_METRIC.srf_sum, (factory, agg) -> getSurfaceSimple(agg));
        REGISTRY.put(TYPE_METRIC.srf_mesh, (factory, agg) -> getSurfaceComplex(agg));
        REGISTRY.put(TYPE_METRIC.r_vol_adapt, (factory, agg) -> getRadiusVolumeAdaptive(agg));
        REGISTRY.put(TYPE_METRIC.r_vol_sum, (factory, agg) -> getRadiusVolumeSimple(agg));
        REGISTRY.put(TYPE_METRIC.r_vol_mesh, (factory, agg) -> getRadiusVolumeComplex(agg));
        REGISTRY.put(TYPE_METRIC.r_srf_adapt, (factory, agg) -> getRadiusSurfaceAdaptive(agg));
        REGISTRY.put(TYPE_METRIC.r_srf_sum, (factory, agg) -> getRadiusSurfaceSimple(agg));
        REGISTRY.put(TYPE_METRIC.r_srf_mesh, (factory, agg) -> getRadiusSurfaceComplex(agg));
        REGISTRY.put(TYPE_METRIC.rg_mono, (factory, agg) -> getRgSimpleMono(agg));
        REGISTRY.put(TYPE_METRIC.rg_mono_06r1, (factory, agg) -> getRgSimpleMono06R1(agg));
        REGISTRY.put(TYPE_METRIC.rg_mono_10r2, (factory, agg) -> getRgSimpleMono10R2(agg));
        REGISTRY.put(TYPE_METRIC.rg_poly, (factory, agg) -> getRgSimplePoly(agg));
        REGISTRY.put(TYPE_METRIC.rg_poly_06r1, (factory, agg) -> getRgSimplePoly06R1(agg));
        REGISTRY.put(TYPE_METRIC.rg_poly_10r2, (factory, agg) -> getRgSimplePoly10R2(agg));
        REGISTRY.put(TYPE_METRIC.rg_mesh, (factory, agg) -> getRgComplex(agg));
        REGISTRY.put(TYPE_METRIC.coord, Measure::getCoordinationNumber);
        REGISTRY.put(TYPE_METRIC.coord__fun, Measure::getCoordinationNumberFunction);
        REGISTRY.put(TYPE_METRIC.coord__avg, (factory, agg) -> getCoordinationNumberAvg(agg));
        REGISTRY.put(TYPE_METRIC.coord__std, (factory, agg) -> getCoordinationNumberStd(agg));
        REGISTRY.put(TYPE_METRIC.coord__max, (factory, agg) -> getCoordinationNumberMax(agg));
        REGISTRY.put(TYPE_METRIC.coord__min, (factory, agg) -> getCoordinationNumberMin(agg));
        REGISTRY.put(TYPE_METRIC.angle, Measure::getTripletAngle);
        REGISTRY.put(TYPE_METRIC.angle__fun, Measure::getTripletAngleFunction);
        REGISTRY.put(TYPE_METRIC.angle__avg, (factory, agg) -> getTripletAngleAvg(agg));
        REGISTRY.put(TYPE_METRIC.angle__std, (factory, agg) -> getTripletAngleStd(agg));
        REGISTRY.put(TYPE_METRIC.angle__max, (factory, agg) -> getTripletAngleMax(agg));
        REGISTRY.put(TYPE_METRIC.angle__min, (factory, agg) -> getTripletAngleMin(agg));
        REGISTRY.put(TYPE_METRIC.dist, Measure::getPairDistance);
        REGISTRY.put(TYPE_METRIC.dist__fun, Measure::getPairDistanceFunction);
        REGISTRY.put(TYPE_METRIC.dist__avg, (factory, agg) -> getPairDistanceAvg(agg));
        REGISTRY.put(TYPE_METRIC.dist__std, (factory, agg) -> getPairDistanceStd(agg));
        REGISTRY.put(TYPE_METRIC.dist__max, (factory, agg) -> getPairDistanceMax(agg));
        REGISTRY.put(TYPE_METRIC.dist__min, (factory, agg) -> getPairDistanceMin(agg));
        REGISTRY.put(TYPE_METRIC.df_bc, (factory, agg) -> getDfBoxCountingOptimized(agg));
        REGISTRY.put(TYPE_METRIC.df_mr, (factory, agg) -> getDfMassRadiusRestricted(agg));
        REGISTRY.put(TYPE_METRIC.df_dc, (factory, agg) -> getDfDensityCorrelationRestricted(agg));

        if (REGISTRY.size() != TYPE_METRIC.values().length) {
            throw new IllegalStateException("Not all TYPE_METRIC values are mapped in the Measure registry!");
        }
    }

    public static String measure(ScatterFactory factory, FAggregate aggregate, List<TYPE_METRIC> metrics) {

        return metrics.stream()
                .map(metric -> REGISTRY.get(metric).apply(factory, aggregate))
                .collect(Collectors.joining(" "));
    }

    private static String getNp(FAggregate aggregate) {

        return String.valueOf(aggregate.size());
    }

    private static String getRp(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getFStatParticleRadius());
    }

    private static String getRpAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius().mean());
    }

    private static String getRpStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius().std(true));
    }

    private static String getRpMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius().max());
    }

    private static String getRpMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getFStatParticleRadius().min());
    }

    private static String isConnected(FAggregate aggregate) {

        return String.valueOf(aggregate.isConnected());
    }

    private static String isPointConnected(FAggregate aggregate) {

        return String.valueOf(aggregate.isPointConnected());
    }

    private static String inNonOverlapping(FAggregate aggregate) {

        return String.valueOf(aggregate.isNonOverlapping());
    }

    private static String getOverlapFactorParticleVolumetric(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC));
    }

    private static String getOverlapFactorParticleVolumetricAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC).mean());
    }

    private static String getOverlapFactorParticleVolumetricStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC).std(true));
    }

    private static String getOverlapFactorParticleVolumetricMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC).max());
    }

    private static String getOverlapFactorParticleVolumetricMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC).min());
    }

    private static String getOverlapFactorParticleQuantitative(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE));
    }

    private static String getOverlapFactorParticleQuantitativeAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE).mean());
    }

    private static String getOverlapFactorParticleQuantitativeStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE).std(true));
    }

    private static String getOverlapFactorParticleQuantitativeMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE).max());
    }

    private static String getOverlapFactorParticleQuantitativeMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE).min());
    }

    private static String getOverlapFactorParticleLinear(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR));
    }

    private static String getOverlapFactorParticleLinearAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).mean());
    }

    private static String getOverlapFactorParticleLinearStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).std(true));
    }

    private static String getOverlapFactorParticleLinearMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max());
    }

    private static String getOverlapFactorParticleLinearMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).min());
    }

    private static String getOverlapFactorClusterVolumetric(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC));
    }

    private static String getLength(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().storage()
                .toCLI(aggregate.getLength());
    }

    private static String getBoundary(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().storage()
                .toCLI(aggregate.getBoundary());
    }

    private static String getDiameter(FAggregate aggregate) {

        return String.valueOf(aggregate.getDiameter());
    }

    private static String getLengthX(FAggregate aggregate) {

        return String.valueOf(aggregate.getLength(Length.X));
    }

    private static String getLengthY(FAggregate aggregate) {

        return String.valueOf(aggregate.getLength(Length.Y));
    }

    private static String getLengthZ(FAggregate aggregate) {

        return String.valueOf(aggregate.getLength(Length.Z));
    }

    private static String getRadiusCmAdaptive(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.ADAPTIVE);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCmSimpleMono(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_MONO);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCmSimplePoly(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCmComplex(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.VOLUMETRIC);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCb(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getCenter(factory.getFPoint(), Center.BOX);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCs(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getCenter(factory.getFPoint(), Center.SPHERE);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getCmAdaptive(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().components()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.ADAPTIVE));
    }

    private static String getCmSimpleMono(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().components()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_MONO));
    }

    private static String getCmSimplePoly(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().components()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY));
    }

    private static String getCmComplex(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().components()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.VOLUMETRIC));
    }

    private static String getCb(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().components()
                .toCLI(aggregate.getCenter(factory.getFPoint(), Center.BOX));
    }

    private static String getCs(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().components()
                .toCLI(aggregate.getCenter(factory.getFPoint(), Center.SPHERE));
    }

    private static String getVolumeAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolume(Volume.ADAPTIVE));
    }

    private static String getVolumeSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolume(Volume.SIMPLE));
    }

    private static String getVolumeComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolume(Volume.DISCRETE));
    }

    private static String getSurfaceAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.ADAPTIVE));
    }

    private static String getSurfaceSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.SIMPLE));
    }

    private static String getSurfaceComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.DISCRETE));
    }

    private static String getRadiusVolumeAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolumeRadius(Volume.ADAPTIVE));
    }

    private static String getRadiusVolumeSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolumeRadius(Volume.SIMPLE));
    }

    private static String getRadiusVolumeComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolumeRadius(Volume.DISCRETE));
    }

    private static String getRadiusSurfaceAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurfaceRadius(Surface.ADAPTIVE));
    }

    private static String getRadiusSurfaceSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurfaceRadius(Surface.SIMPLE));
    }

    private static String getRadiusSurfaceComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurfaceRadius(Surface.DISCRETE));
    }

    private static String getRgSimpleMono(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO));
    }

    private static String getRgSimpleMono06R1(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_06R1));
    }

    private static String getRgSimpleMono10R2(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_10R2));
    }

    private static String getRgSimplePoly(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY));
    }

    private static String getRgSimplePoly06R1(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1));
    }

    private static String getRgSimplePoly10R2(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_10R2));
    }

    private static String getRgComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.VOLUMETRIC));
    }

    private static String getCoordinationNumber(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getCoordinationNumber());
    }

    private static String getCoordinationNumberFunction(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getCoordinationNumberFunction());
    }

    private static String getCoordinationNumberAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getCoordinationNumber().mean());
    }

    private static String getCoordinationNumberStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getCoordinationNumber().std(true));
    }

    private static String getCoordinationNumberMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getCoordinationNumber().max());
    }

    private static String getCoordinationNumberMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getCoordinationNumber().min());
    }

    private static String getTripletAngle(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getTripletAngle());
    }

    private static String getTripletAngleFunction(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getTripletAngleFunction());
    }

    private static String getTripletAngleAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getTripletAngle().mean());
    }

    private static String getTripletAngleStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getTripletAngle().std(true));
    }

    private static String getTripletAngleMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getTripletAngle().max());
    }

    private static String getTripletAngleMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getTripletAngle().min());
    }

    private static String getPairDistance(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getPairDistance());
    }

    private static String getPairDistanceFunction(ScatterFactory factory, FAggregate aggregate) {

        return factory.save().statistics()
                .toCLI(aggregate.getPairDistanceFunction());
    }

    private static String getPairDistanceAvg(FAggregate aggregate) {

        return String.valueOf(aggregate.getPairDistance().mean());
    }

    private static String getPairDistanceStd(FAggregate aggregate) {

        return String.valueOf(aggregate.getPairDistance().std(true));
    }

    private static String getPairDistanceMax(FAggregate aggregate) {

        return String.valueOf(aggregate.getPairDistance().max());
    }

    private static String getPairDistanceMin(FAggregate aggregate) {

        return String.valueOf(aggregate.getPairDistance().min());
    }

    private static String getDfBoxCountingOptimized(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED));
    }

    private static String getDfMassRadiusRestricted(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.MR_RESTRICTED));
    }

    private static String getDfDensityCorrelationRestricted(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.DC_RESTRICTED));
    }
}
