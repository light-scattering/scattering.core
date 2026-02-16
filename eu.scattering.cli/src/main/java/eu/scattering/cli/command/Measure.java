package eu.scattering.cli.command;

import eu.scattering.cli.type.TYPE_METRIC;
import eu.scattering.core.design.ScatFactory;
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

    public static String measure(ScatFactory factory, FAggregate aggregate, List<TYPE_METRIC> metrics) {
        List<String> results = new ArrayList<>(metrics.size());

        for (TYPE_METRIC metric : metrics) {

            results.add(switch (metric) {
                case Np -> getNp(aggregate);
                case Rp -> getRp(factory, aggregate);
                case Rp__avg -> getRpAvg(aggregate);
                case Rp__std -> getRpStd(aggregate);
                case Rp__max -> getRpMax(aggregate);
                case Rp__min -> getRpMin(aggregate);
                case Df_box -> getDfBox(aggregate);
                case Df_density -> getDfCor(aggregate);
                case Length_x -> getLengthX(aggregate);
                case Length_y -> getLengthY(aggregate);
                case Length_z -> getLengthZ(aggregate);
                case Volume -> getVolumeAdaptive(aggregate);
                case Volume_simple -> getVolumeSimple(aggregate);
                case Volume_complex -> getVolumeComplex(aggregate);
                case VolumeRadius -> getRadiusVolumeAdaptive(aggregate);
                case VolumeRadius_simple -> getRadiusVolumeSimple(aggregate);
                case VolumeRadius_complex -> getRadiusVolumeComplex(aggregate);
                case Surface -> getSurfaceAdaptive(aggregate);
                case Surface_simple -> getSurfaceSimple(aggregate);
                case Surface_complex -> getSurfaceComplex(aggregate);
                case SurfaceRadius -> getRadiusSurfaceAdaptive(aggregate);
                case SurfaceRadius_simple -> getRadiusSurfaceSimple(aggregate);
                case SurfaceRadius_complex -> getRadiusSurfaceComplex(aggregate);
                case MassCenter -> getCmAdaptive(factory, aggregate);
                case MassCenter_mono -> getCmSimpleMono(factory, aggregate);
                case MassCenter_poly -> getCmSimplePoly(factory, aggregate);
                case MassCenter_mesh -> getCmComplex(factory, aggregate);
                case BoxCenter -> getCb(factory, aggregate);
                case SphericalCenter -> getCs(factory, aggregate);
                case RadiusFromMassCenter -> getRadiusCmAdaptive(factory, aggregate);
                case RadiusFromMassCenter_mono -> getRadiusCmSimpleMono(factory, aggregate);
                case RadiusFromMassCenter_poly -> getRadiusCmSimplePoly(factory, aggregate);
                case RadiusFromMassCenter_mesh -> getRadiusCmComplex(factory, aggregate);
                case RadiusFromSphericalCenter -> getRadiusCs(factory, aggregate);
                case RadiusFromBoxCenter -> getRadiusCb(factory, aggregate);
                case Rg_mono -> getRgSimpleMono(aggregate);
                case Rg_mono_06R1 -> getRgSimpleMono06R1(aggregate);
                case Rg_mono_10R2 -> getRgSimpleMono10R2(aggregate);
                case Rg_poly -> getRgSimplePoly(aggregate);
                case Rg_poly_06R1 -> getRgSimplePoly06R1(aggregate);
                case Rg_poly_10R2 -> getRgSimplePoly10R2(aggregate);
                case Rg_mesh -> getRgComplex(aggregate);
                case RG_filippov -> getRgDedicatedFilippov(aggregate);
                case OverlapFactor_c_vol -> getOverlapFactorClusterVolumetric(factory, aggregate);
                case OverlapFactor_p_vol -> getOverlapFactorParticleVolumetric(factory, aggregate);
                case OverlapFactor_p_vol__avg -> getOverlapFactorParticleVolumetricAvg(aggregate);
                case OverlapFactor_p_vol__std -> getOverlapFactorParticleVolumetricStd(aggregate);
                case OverlapFactor_p_vol__max -> getOverlapFactorParticleVolumetricMax(aggregate);
                case OverlapFactor_p_vol__min -> getOverlapFactorParticleVolumetricMin(aggregate);
                case OverlapFactor_p_count -> getOverlapFactorParticleQuantitative(factory, aggregate);
                case OverlapFactor_p_count__avg -> getOverlapFactorParticleQuantitativeAvg(aggregate);
                case OverlapFactor_p_count__std -> getOverlapFactorParticleQuantitativeStd(aggregate);
                case OverlapFactor_p_count__max -> getOverlapFactorParticleQuantitativeMax(aggregate);
                case OverlapFactor_p_count__min -> getOverlapFactorParticleQuantitativeMin(aggregate);
                case OverlapFactor_p_lin -> getOverlapFactorParticleLinear(factory, aggregate);
                case OverlapFactor_p_lin__avg -> getOverlapFactorParticleLinearAvg(aggregate);
                case OverlapFactor_p_lin__std -> getOverlapFactorParticleLinearStd(aggregate);
                case OverlapFactor_p_lin__max -> getOverlapFactorParticleLinearMax(aggregate);
                case OverlapFactor_p_lin__min -> getOverlapFactorParticleLinearMin(aggregate);
                case IsConnected -> isConnected(aggregate);
                case IsPointConnected -> isPointConnected(aggregate);
                case IsNonOverlapping -> inNonOverlapping(aggregate);
                case TripletAngle -> getTripletAngle(factory, aggregate);
                case TripletAngle__fun -> getTripletAngleFunction(factory, aggregate);
                case TripletAngle__avg -> getTripletAngleAvg(aggregate);
                case TripletAngle__std -> getTripletAngleStd(aggregate);
                case TripletAngle__max -> getTripletAngleMax(aggregate);
                case TripletAngle__min -> getTripletAngleMin(aggregate);
                case PairDistance -> getPairDistance(factory, aggregate);
                case PairDistance__fun -> getPairDistanceFunction(factory, aggregate);
                case PairDistance__avg -> getPairDistanceAvg(aggregate);
                case PairDistance__std -> getPairDistanceStd(aggregate);
                case PairDistance__max -> getPairDistanceMax(aggregate);
                case PairDistance__min -> getPairDistanceMin(aggregate);
                case CoordinationNumber -> getCoordinationNumber(factory, aggregate);
                case CoordinationNumber__fun -> getCoordinationNumberFunction(factory, aggregate);
                case CoordinationNumber__avg -> getCoordinationNumberAvg(aggregate);
                case CoordinationNumber__std -> getCoordinationNumberStd(aggregate);
                case CoordinationNumber__max -> getCoordinationNumberMax(aggregate);
                case CoordinationNumber__min -> getCoordinationNumberMin(aggregate);
                case BoxCoverage__fun -> getBoxCoverageFunction(factory, aggregate);
                case DensityCorrelation__fun -> getDensityCorrelationFunction(factory, aggregate);
            });
        }

        return String.join(" ", results);
    }

    private static String getNp(FAggregate aggregate) {

        return String.valueOf(aggregate.size());
    }

    private static String getRp(ScatFactory factory, FAggregate aggregate) {

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

        return String.valueOf(aggregate.getVolume(Volume.COMPLEX));
    }

    private static String getRadiusVolumeAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolumeRadius(Volume.ADAPTIVE));
    }

    private static String getRadiusVolumeSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolumeRadius(Volume.SIMPLE));
    }

    private static String getRadiusVolumeComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getVolumeRadius(Volume.COMPLEX));
    }

    private static String getSurfaceAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.ADAPTIVE));
    }

    private static String getSurfaceSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.SIMPLE));
    }

    private static String getSurfaceComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurface(Surface.COMPLEX));
    }

    private static String getRadiusSurfaceAdaptive(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurfaceRadius(Surface.ADAPTIVE));
    }

    private static String getRadiusSurfaceSimple(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurfaceRadius(Surface.SIMPLE));
    }

    private static String getRadiusSurfaceComplex(FAggregate aggregate) {

        return String.valueOf(aggregate.getSurfaceRadius(Surface.COMPLEX));
    }

    private static String getDfBox(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.BOX_FAST));
    }

    private static String getDfCor(FAggregate aggregate) {

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.CORRELATION));
    }

    private static String getCmAdaptive(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.ADAPTIVE));
    }

    private static String getCmSimpleMono(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_MONO));
    }

    private static String getCmSimplePoly(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY));
    }

    private static String getCmComplex(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getMassCenter(factory.getFPoint(), MassCenter.COMPLEX));
    }

    private static String getCs(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getCenter(factory.getFPoint(), Center.SPHERICAL));
    }

    private static String getCb(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getComponentContext()
                .toCLI(aggregate.getCenter(factory.getFPoint(), Center.SPATIAL));
    }

    private static String getRadiusCmAdaptive(ScatFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.ADAPTIVE);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCmSimpleMono(ScatFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_MONO);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCmSimplePoly(ScatFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCmComplex(ScatFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getMassCenter(factory.getFPoint(), MassCenter.COMPLEX);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCb(ScatFactory factory, FAggregate aggregate) {
        FPoint center = aggregate.getCenter(factory.getFPoint(), Center.SPATIAL);

        return String.valueOf(aggregate.getRadiusFrom(center));
    }

    private static String getRadiusCs(ScatFactory factory, FAggregate aggregate) {
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

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.COMPLEX));
    }

    private static String getRgDedicatedFilippov(FAggregate aggregate) {

        return String.valueOf(aggregate.getRadiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV));
    }

    private static String getOverlapFactorParticleVolumetric(ScatFactory factory, FAggregate aggregate) {

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

    private static String getOverlapFactorParticleQuantitative(ScatFactory factory, FAggregate aggregate) {

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

    private static String getOverlapFactorParticleLinear(ScatFactory factory, FAggregate aggregate) {

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

    private static String getOverlapFactorClusterVolumetric(ScatFactory factory, FAggregate aggregate) {

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

    private static String getTripletAngle(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getTripletAngle());
    }

    private static String getTripletAngleFunction(ScatFactory factory, FAggregate aggregate) {

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

    private static String getPairDistance(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getPairDistance());
    }

    private static String getPairDistanceFunction(ScatFactory factory, FAggregate aggregate) {

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

    private static String getCoordinationNumber(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getCoordinationNumber());
    }

    private static String getCoordinationNumberFunction(ScatFactory factory, FAggregate aggregate) {

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

    private static String getBoxCoverageFunction(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getBoxCoverageFunction(1.3, 3, false, false, false));
    }

    private static String getDensityCorrelationFunction(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getDensityCorrelationFunction(1.1));
    }
}
