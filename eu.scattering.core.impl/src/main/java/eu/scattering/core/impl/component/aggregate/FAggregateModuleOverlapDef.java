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
        List<Shape> particles = this.aggregate.getRefParticles().asList();

        int quantity = 0;
        for (int i = 0 ; i < particles.size() - 1 ; i++) {
            for (int j = i + 1 ; j < particles.size() ; j++) {
                if (particles.get(i).overlaps(particles.get(j))) {
                    quantity++;
                }
            }
        }

        return quantity;
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
        int oFacCount = 0;
        double oFacTotal = 0;
        Shape shapeA, shapeB;
        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            shapeA = this.aggregate.getRefParticles().asList().get(i);

            for (int j = i + 1 ; j < this.aggregate.size() ; j++) {
                shapeB = this.aggregate.getRefParticles().asList().get(j);

                if (shapeA == shapeB) {
                    continue;
                }

                if (shapeA.repels(shapeB)) {
                    continue;
                }

                oFacTotal += geLinearSinglePair(shapeA, shapeB);
                oFacCount += 1;
            }
        }

        if (oFacCount == 0) {
            return 0;
        }

        return oFacTotal / oFacCount;
    }

    private double geLinearSinglePair(Shape shapeA, Shape shapeB) {

        double dist = shapeA.getDistCenter(shapeB);
        double oFacRaw = 1 - (dist / (shapeA.getRadius() + shapeB.getRadius()));

        if (oFacRaw > 1) {
            return 1;
        }

        if (oFacRaw < 0) {
            return 0;
        }

        return oFacRaw;
    }

    // -------------------------------------------------------------------------------------------------

    protected double getVolumetricOverlapFactor() {
        List<Double> layer = new ArrayList<>();

        for (Shape shape : this.aggregate.getRefParticles()) {
            getVolumetricMethod(shape, layer);
        }

        return getVolumetricProcess(layer);
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
