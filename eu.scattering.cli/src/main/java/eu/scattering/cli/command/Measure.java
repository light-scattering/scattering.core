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
                case NP -> getNp(aggregate);
                case RP -> getRp(factory, aggregate);
                case RP__AVG -> getRpAvg(aggregate);
                case RP__STD -> getRpStd(aggregate);
                case RP__MAX -> getRpMax(aggregate);
                case RP__MIN -> getRpMin(aggregate);
                case DFB -> getDfBox(aggregate);
                case DFC -> getDfCor(aggregate);
                case LX -> getLengthX(aggregate);
                case LY -> getLengthY(aggregate);
                case LZ -> getLengthZ(aggregate);
                case V_A -> getVolumeAdaptive(aggregate);
                case V_S -> getVolumeSimple(aggregate);
                case V_C -> getVolumeComplex(aggregate);
                case RV_A -> getRadiusVolumeAdaptive(aggregate);
                case RV_S -> getRadiusVolumeSimple(aggregate);
                case RV_C -> getRadiusVolumeComplex(aggregate);
                case S_A -> getSurfaceAdaptive(aggregate);
                case S_S -> getSurfaceSimple(aggregate);
                case S_C -> getSurfaceComplex(aggregate);
                case RS_A -> getRadiusSurfaceAdaptive(aggregate);
                case RS_S -> getRadiusSurfaceSimple(aggregate);
                case RS_C -> getRadiusSurfaceComplex(aggregate);
                case CM_A -> getCmAdaptive(factory, aggregate);
                case CM_SM -> getCmSimpleMono(factory, aggregate);
                case CM_SP -> getCmSimplePoly(factory, aggregate);
                case CM_C -> getCmComplex(factory, aggregate);
                case CB -> getCb(factory, aggregate);
                case CS -> getCs(factory, aggregate);
                case RCM_A -> getRadiusCmAdaptive(factory, aggregate);
                case RCM_SM -> getRadiusCmSimpleMono(factory, aggregate);
                case RCM_SP -> getRadiusCmSimplePoly(factory, aggregate);
                case RCM_C -> getRadiusCmComplex(factory, aggregate);
                case RCB -> getRadiusCb(factory, aggregate);
                case RCS -> getRadiusCs(factory, aggregate);
                case RG_SM -> getRgSimpleMono(aggregate);
                case RG_SM_06R1 -> getRgSimpleMono06R1(aggregate);
                case RG_SM_10R2 -> getRgSimpleMono10R2(aggregate);
                case RG_SP -> getRgSimplePoly(aggregate);
                case RG_SP_06R1 -> getRgSimplePoly06R1(aggregate);
                case RG_SP_10R2 -> getRgSimplePoly10R2(aggregate);
                case RG_C -> getRgComplex(aggregate);
                case RG_D_F -> getRgDedicatedFilippov(aggregate);
                case OF_CV -> getOverlapFactorClusterVolumetric(factory, aggregate);
                case OF_PV -> getOverlapFactorParticleVolumetric(factory, aggregate);
                case OF_PV__AVG -> getOverlapFactorParticleVolumetricAvg(aggregate);
                case OF_PV__STD -> getOverlapFactorParticleVolumetricStd(aggregate);
                case OF_PV__MAX -> getOverlapFactorParticleVolumetricMax(aggregate);
                case OF_PV__MIN -> getOverlapFactorParticleVolumetricMin(aggregate);
                case OF_PQ -> getOverlapFactorParticleQuantitative(factory, aggregate);
                case OF_PQ__AVG -> getOverlapFactorParticleQuantitativeAvg(aggregate);
                case OF_PQ__STD -> getOverlapFactorParticleQuantitativeStd(aggregate);
                case OF_PQ__MAX -> getOverlapFactorParticleQuantitativeMax(aggregate);
                case OF_PQ__MIN -> getOverlapFactorParticleQuantitativeMin(aggregate);
                case OF_PL -> getOverlapFactorParticleLinear(factory, aggregate);
                case OF_PL__AVG -> getOverlapFactorParticleLinearAvg(aggregate);
                case OF_PL__STD -> getOverlapFactorParticleLinearStd(aggregate);
                case OF_PL__MAX -> getOverlapFactorParticleLinearMax(aggregate);
                case OF_PL__MIN -> getOverlapFactorParticleLinearMin(aggregate);
                case IC -> isConnected(aggregate);
                case IPC -> isPointConnected(aggregate);
                case INO -> inNonOverlapping(aggregate);
                case TA -> getTripletAngle(factory, aggregate);
                case TA__F -> getTripletAngleFunction(factory, aggregate);
                case TA__AVG -> getTripletAngleAvg(aggregate);
                case TA__STD -> getTripletAngleStd(aggregate);
                case TA__MAX -> getTripletAngleMax(aggregate);
                case TA__MIN -> getTripletAngleMin(aggregate);
                case PD -> getPairDistance(factory, aggregate);
                case PD__F -> getPairDistanceFunction(factory, aggregate);
                case PD__AVG -> getPairDistanceAvg(aggregate);
                case PD__STD -> getPairDistanceStd(aggregate);
                case PD__MAX -> getPairDistanceMax(aggregate);
                case PD__MIN -> getPairDistanceMin(aggregate);
                case CN -> getCoordinationNumber(factory, aggregate);
                case CN__F -> getCoordinationNumberFunction(factory, aggregate);
                case CN__AVG -> getCoordinationNumberAvg(aggregate);
                case CN__STD -> getCoordinationNumberStd(aggregate);
                case CN__MAX -> getCoordinationNumberMax(aggregate);
                case CN__MIN -> getCoordinationNumberMin(aggregate);
                case BC__F -> getBoxCoverageFunction(factory, aggregate);
                case DC__F -> getDensityCorrelationFunction(factory, aggregate);
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

        return String.valueOf(aggregate.getFractalDimension(FractalDimension.BOX));
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
                .toCLI(aggregate.getBoxCoverageFunction(true));
    }

    private static String getDensityCorrelationFunction(ScatFactory factory, FAggregate aggregate) {

        return factory.getSaveAspect().getStatisticsContext()
                .toCLI(aggregate.getDensityCorrelationFunction(true));
    }
}
