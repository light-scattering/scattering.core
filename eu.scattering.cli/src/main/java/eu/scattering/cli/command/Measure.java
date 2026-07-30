package eu.scattering.cli.command;

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

import java.util.ArrayList;
import java.util.List;

public class Measure {

    public static String measure(ScatterFactory factory, FAggregate aggregate, List<TYPE_METRIC> metrics) {
        List<String> results = new ArrayList<>(metrics.size());

        for (TYPE_METRIC metric : metrics) {

            results.add(switch (metric) {
                case np -> getNp(aggregate);
                case rp -> getRp(factory, aggregate);
                case rp__avg -> getRpAvg(aggregate);
                case rp__std -> getRpStd(aggregate);
                case rp__max -> getRpMax(aggregate);
                case rp__min -> getRpMin(aggregate);
                case conn -> isConnected(aggregate);
                case conn_pt -> isPointConnected(aggregate);
                case conn_non_ov -> inNonOverlapping(aggregate);
                case ov_p_vol -> getOverlapFactorParticleVolumetric(factory, aggregate);
                case ov_p_vol__avg -> getOverlapFactorParticleVolumetricAvg(aggregate);
                case ov_p_vol__std -> getOverlapFactorParticleVolumetricStd(aggregate);
                case ov_p_vol__max -> getOverlapFactorParticleVolumetricMax(aggregate);
                case ov_p_vol__min -> getOverlapFactorParticleVolumetricMin(aggregate);
                case ov_p_num -> getOverlapFactorParticleQuantitative(factory, aggregate);
                case ov_p_num__avg -> getOverlapFactorParticleQuantitativeAvg(aggregate);
                case ov_p_num__std -> getOverlapFactorParticleQuantitativeStd(aggregate);
                case ov_p_num__max -> getOverlapFactorParticleQuantitativeMax(aggregate);
                case ov_p_num__min -> getOverlapFactorParticleQuantitativeMin(aggregate);
                case ov_p_lin -> getOverlapFactorParticleLinear(factory, aggregate);
                case ov_p_lin__avg -> getOverlapFactorParticleLinearAvg(aggregate);
                case ov_p_lin__std -> getOverlapFactorParticleLinearStd(aggregate);
                case ov_p_lin__max -> getOverlapFactorParticleLinearMax(aggregate);
                case ov_p_lin__min -> getOverlapFactorParticleLinearMin(aggregate);
                case ov_c_vol -> getOverlapFactorClusterVolumetric(factory, aggregate);
                case box -> getBoundary(aggregate);
                case diam -> getDiameter(aggregate);
                case len_x -> getLengthX(aggregate);
                case len_y -> getLengthY(aggregate);
                case len_z -> getLengthZ(aggregate);


                case df_bc -> getDfBoxCountingOptimized(aggregate);
                case df_mr -> getDfMassRadiusRestricted(aggregate);
                case df_dc -> getDfDensityCorrelationRestricted(aggregate);
                case vol_adapt -> getVolumeAdaptive(aggregate);
                case vol_sum -> getVolumeSimple(aggregate);
                case vol_mesh -> getVolumeComplex(aggregate);
                case r_vol_adapt -> getRadiusVolumeAdaptive(aggregate);
                case r_vol_sum -> getRadiusVolumeSimple(aggregate);
                case r_vol_mesh -> getRadiusVolumeComplex(aggregate);
                case srf_adapt -> getSurfaceAdaptive(aggregate);
                case srf_sum -> getSurfaceSimple(aggregate);
                case srf_mesh -> getSurfaceComplex(aggregate);
                case r_srf_adapt -> getRadiusSurfaceAdaptive(aggregate);
                case r_srf_sum -> getRadiusSurfaceSimple(aggregate);
                case r_srf_mesh -> getRadiusSurfaceComplex(aggregate);
                case cm_adapt -> getCmAdaptive(factory, aggregate);
                case cm_mono -> getCmSimpleMono(factory, aggregate);
                case cm_poly -> getCmSimplePoly(factory, aggregate);
                case cm_mesh -> getCmComplex(factory, aggregate);
                case cb -> getCb(factory, aggregate);
                case cs -> getCs(factory, aggregate);
                case r_cm_adapt -> getRadiusCmAdaptive(factory, aggregate);
                case r_cm_mono -> getRadiusCmSimpleMono(factory, aggregate);
                case r_cm_poly -> getRadiusCmSimplePoly(factory, aggregate);
                case r_cm_mesh -> getRadiusCmComplex(factory, aggregate);
                case r_cs -> getRadiusCs(factory, aggregate);
                case r_cb -> getRadiusCb(factory, aggregate);
                case rg_mono -> getRgSimpleMono(aggregate);
                case rg_mono_06r1 -> getRgSimpleMono06R1(aggregate);
                case rg_mono_10r2 -> getRgSimpleMono10R2(aggregate);
                case rg_poly -> getRgSimplePoly(aggregate);
                case rg_poly_06r1 -> getRgSimplePoly06R1(aggregate);
                case rg_poly_10r2 -> getRgSimplePoly10R2(aggregate);
                case rg_mesh -> getRgComplex(aggregate);
                case angle -> getTripletAngle(factory, aggregate);
                case angle__fun -> getTripletAngleFunction(factory, aggregate);
                case angle__avg -> getTripletAngleAvg(aggregate);
                case angle__std -> getTripletAngleStd(aggregate);
                case angle__max -> getTripletAngleMax(aggregate);
                case angle__min -> getTripletAngleMin(aggregate);
                case dist -> getPairDistance(factory, aggregate);
                case dist__fun -> getPairDistanceFunction(factory, aggregate);
                case dist__avg -> getPairDistanceAvg(aggregate);
                case dist__std -> getPairDistanceStd(aggregate);
                case dist__max -> getPairDistanceMax(aggregate);
                case dist__min -> getPairDistanceMin(aggregate);
                case coord -> getCoordinationNumber(factory, aggregate);
                case coord__fun -> getCoordinationNumberFunction(factory, aggregate);
                case coord__avg -> getCoordinationNumberAvg(aggregate);
                case coord__std -> getCoordinationNumberStd(aggregate);
                case coord__max -> getCoordinationNumberMax(aggregate);
                case coord__min -> getCoordinationNumberMin(aggregate);
            });
        }

        return String.join(" ", results);
    }

    private static String getNp(FAggregate aggregate) {

        return String.valueOf(aggregate.size());
    }

    private static String getRp(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
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

    private static String getBoundary(FAggregate aggregate) {

        return String.valueOf(aggregate.getBoundary());
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

    private static String getVolumeAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolume(Volume.ADAPTIVE));
    }

    private static String getVolumeSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolume(Volume.SIMPLE));
    }

    private static String getVolumeComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolume(Volume.DISCRETE));
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

    private static String getSurfaceAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.ADAPTIVE));
    }

    private static String getSurfaceSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.SIMPLE));
    }

    private static String getSurfaceComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.DISCRETE));
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

    private static String getDfBoxCountingOptimized(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.BC_OPTIMIZED));
    }

    private static String getDfMassRadiusRestricted(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.MR_RESTRICTED));
    }

    private static String getDfDensityCorrelationRestricted(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.DC_RESTRICTED));
    }

    private static String getCmAdaptive(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.ADAPTIVE));
    }

    private static String getCmSimpleMono(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_MONO));
    }

    private static String getCmSimplePoly(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY));
    }

    private static String getCmComplex(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.VOLUMETRIC));
    }

    private static String getCs(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getCenter(factory.getFPoint(), Center.SPHERICAL));
    }

    private static String getCb(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getCenter(factory.getFPoint(), Center.SPATIAL));
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
        FPoint center = aggregate.getCenter(factory.getFPoint(), Center.SPATIAL);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCs(ScatterFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getCenter(factory.getFPoint(), Center.SPHERICAL);

        return String.valueOf(aggregate.getRadiusFrom(center));
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

    private static String getOverlapFactorParticleVolumetric(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
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

        return factory.getSaveAspect().getStatisticsContext()
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

        return factory.getSaveAspect().getStatisticsContext()
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

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC));
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

    private static String getTripletAngle(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getTripletAngle());
    }

    private static String getTripletAngleFunction(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
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

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getPairDistance());
    }

    private static String getPairDistanceFunction(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
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

    private static String getCoordinationNumber(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getCoordinationNumber());
    }

    private static String getCoordinationNumberFunction(ScatterFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getCoordinationNumber());
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
}
