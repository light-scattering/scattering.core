package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.type.OverlapFactor;

import java.util.ArrayList;
import java.util.List;

public class FAggregateModuleOverlapDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleOverlapDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getOverlapFactor(OverlapFactor type) {
        return 0;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getQuantitativeOverlapFactor() {

        return getQuantitativeOverlapFactorData().mean();
    }

    protected FStat getQuantitativeOverlapFactorData() {
        FStat results = this.factory.getFStat();

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            results.add(0);
        }

        List<Shape> particles = this.aggregate.getRefParticles().asList();

        for (int i = 0 ; i < particles.size() - 1 ; i++) {
            for (int j = i + 1 ; j < particles.size() ; j++) {
                if (particles.get(i).overlaps(particles.get(j))) {
                    results.set(i, results.get(i) + 1);
                    results.set(j, results.get(j) + 1);
                }
            }
        }

        return results;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getLinearOverlapFactor() {

        return getLinearOverlapFactorData().mean();
    }

    protected FStat getLinearOverlapFactorData() {
        FStat results = this.factory.getFStat();

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            results.add(0);
        }

        for (int i = 0 ; i < this.aggregate.size() - 1 ; i++) {
            Shape shapeA = this.aggregate.getRefParticles().asList().get(i);

            for (int j = i + 1 ; j < this.aggregate.size() ; j++) {
                Shape shapeB = this.aggregate.getRefParticles().asList().get(j);

                if (shapeA == shapeB) {
                    continue;
                }

                if (shapeA.repels(shapeB)) {
                    continue;
                }

                double overlap = getLinearOverlapFactorSingle(shapeA, shapeB);

                if (overlap > results.get(i)) {
                    results.set(i, overlap);
                }

                if (overlap > results.get(j)) {
                    results.set(j, overlap);
                }
            }
        }

        return results;
    }

    private double getLinearOverlapFactorSingle(Shape shapeA, Shape shapeB) {
        double distance = shapeA.getDistCenter(shapeB);
        double overlap = 1 - (distance / (shapeA.getRadius() + shapeB.getRadius()));

        if (overlap > 1) {
            return 1;
        }

        if (overlap < 0) {
            return 0;
        }

        return overlap;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getTotalVolumetricOverlapFactor() {
        List<Double> volume = new ArrayList<>();

        for (Shape shape : this.aggregate.getRefParticles()) {
            getVolumetricMethod(shape, volume);
        }

        return getVolumetricProcess(volume);
    }

    protected double getVolumetricOverlapFactor() {

        return getVolumetricOverlapFactorData().mean();
    }

    protected FStat getVolumetricOverlapFactorData() {
        FStat results = this.factory.getFStat();

        for (Shape shape : this.aggregate.getRefParticles()) {
            getVolumetricMethodData(shape, results);
        }

        return results;
    }

    private void getVolumetricMethodData(Shape shape, FStat results) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            results.add(0);
        } else {
            getVolumetricMethodApproxData(shape, results);
        }
    }

    private void getVolumetricMethodApproxData(Shape shape, FStat results) {
        FLayer fLayer = this.factory.getFLayer();

        shape.fillVolumeLayerOverlap(fLayer, this.aggregate.getRefParticles());

        results.add(1 - (fLayer.get() / fLayer.addSelf()));
    }

    private void getVolumetricMethod(Shape shape, List<Double> volume) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            getVolumetricMethodPrecise(shape, volume);
        } else {
            getVolumetricMethodApprox(shape, volume);
        }
    }

    private void getVolumetricMethodPrecise(Shape shape, List<Double> volume) {

        if (volume.size() < 1) {
            volume.add(0d);
        }

        volume.set(0, volume.get(0) + shape.getVolumeAlgebraic());
    }

    private void getVolumetricMethodApprox(Shape shape, List<Double> volume) {
        FLayer fLayer = this.factory.getFLayer();

        shape.fillVolumeLayerOverlap(fLayer, this.aggregate.getRefParticles());

        double volUnit = Math.pow(shape.getDelta(), 3);

        while (fLayer.size() > volume.size()) {
            volume.add(0d);
        }

        for (int i = 0; i < fLayer.size() ; i++) {
            volume.set(i, volume.get(i) + (fLayer.get(i) * volUnit));
        }
    }

    private double getVolumetricProcess(List<Double> volume) {
        double volTmp;
        double volTotal = 0;
        double volOverlap = 0;

        for (int i = 0 ; i < volume.size() ; i++) {
            volTmp = volume.get(i) / (i + 1);

            volTotal += volTmp;

            if (i > 0) {
                volOverlap += volTmp;
            }
        }

        return volOverlap / volTotal;
    }
}
