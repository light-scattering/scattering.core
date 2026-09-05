package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FAggregateModuleOverlapDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleOverlapDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected boolean isNonOverlapping() {
        Queue<Shape> queue = new LinkedList<>(this.aggregate.getRefParticles().asList());

        while (!queue.isEmpty()) {
            if (queue.poll().overlaps(queue) != 0) {
                return false;
            }
        }

        return true;
    }

    // -------------------------------------------------------------------------------------------------

    protected boolean isPointConnected() {

        return isNonOverlapping() && isConnected();
    }

    protected boolean isConnected() {
        List<Shape> processed = new ArrayList<>();

        isConnectedRecurrence(this.aggregate.getRefParticles().asList().getFirst(), processed);

        return this.aggregate.getRefParticles().asList().size() == processed.size();
    }

    private void isConnectedRecurrence(Shape shape, List<Shape> processed) {

        if (processed.contains(shape)) {
            return;
        }

        processed.add(shape);

        List<Shape> candidates = new ArrayList<>();
        shape.touchesOrOverlaps(this.aggregate.getRefParticles().asList(), candidates);

        for (Shape candidate : candidates) {
            isConnectedRecurrence(candidate, processed);
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected boolean touches(FAggregate arg) {
        FPos3D centerRef = this.aggregate.getBoxCenter();
        FPos3D centerArg = arg.getBoxCenter();

        double radiusRef = this.aggregate.getRadiusFrom(centerRef);
        double radiusArg = arg.getRadiusFrom(centerArg);

        List<Shape> particlesRef = new ArrayList<>(this.aggregate.size());
        List<Shape> particlesArg = new ArrayList<>(arg.size());

        for (Shape shape : this.aggregate) {
            if (shape.getDistCenter(centerArg) <= radiusArg + shape.getRadius()) {
                particlesRef.add(shape);
            }
        }

        for (Shape shape : arg.getRefParticles()) {
            if (shape.getDistCenter(centerRef) <= radiusRef + shape.getRadius()) {
                particlesArg.add(shape);
            }
        }

        boolean touches = false;
        for (Shape shapeRef : particlesRef) {
            for (Shape shapeArg : particlesArg) {
                if (shapeRef.touches(shapeArg)) {
                    touches = true;
                }

                if (shapeRef.overlaps(shapeArg)) {
                    return false;
                }
            }
        }

        return touches;
    }

    protected boolean overlaps(FAggregate arg) {
        FPos3D centerRef = this.aggregate.getBoxCenter();
        FPos3D centerArg = arg.getBoxCenter();

        double radiusRef = this.aggregate.getRadiusFrom(centerRef);
        double radiusArg = arg.getRadiusFrom(centerArg);

        List<Shape> particlesRef = new ArrayList<>(this.aggregate.size());
        List<Shape> particlesArg = new ArrayList<>(arg.size());

        for (Shape shape : this.aggregate) {
            if (shape.getDistCenter(centerArg) < radiusArg + shape.getRadius()) {
                particlesRef.add(shape);
            }
        }

        for (Shape shape : arg.getRefParticles()) {
            if (shape.getDistCenter(centerRef) < radiusRef + shape.getRadius()) {
                particlesArg.add(shape);
            }
        }

        for (Shape shapeRef : particlesRef) {
            for (Shape shapeArg : particlesArg) {
                if (shapeRef.overlaps(shapeArg)) {
                    return true;
                }
            }
        }

        return false;
    }

    protected boolean overlapsWithShift(FAggregate arg, FVector shift) {
        FPos3D centerRef = this.aggregate.getBoxCenter();
        FPos3D centerArg = arg.getBoxCenter();

        double radiusRef = this.aggregate.getRadiusFrom(centerRef);
        double radiusArg = arg.getRadiusFrom(centerArg);

        List<Shape> particlesRef = new ArrayList<>(this.aggregate.size());
        List<Shape> particlesArg = new ArrayList<>(arg.size());

        FVector translator = shift.copy();

        for (Shape shape : this.aggregate) {
            translator.moveBase(shape.getRefCenter());

            if (translator.getRefHead().getDistance(centerArg) < radiusArg + shape.getRadius()) {
                particlesRef.add(shape);
            }
        }

        for (Shape shape : arg.getRefParticles()) {
            translator.moveBase(centerRef);

            if (shape.getDistCenter(translator.getRefHead()) < radiusRef + shape.getRadius()) {
                particlesArg.add(shape);
            }
        }

        double memoX, memoY, memoZ;
        for (Shape shapeRef : particlesRef) {
            memoX = shapeRef.getCenterX();
            memoY = shapeRef.getCenterY();
            memoZ = shapeRef.getCenterZ();

            translator.moveBase(memoX, memoY, memoZ);

            shapeRef.setCenter(translator.getRefHead());

            boolean stop = false;

            for (Shape shapeArg : particlesArg) {
                if (shapeRef.overlaps(shapeArg)) {
                    stop = true;

                    break;
                }
            }

            shapeRef.setCenter(memoX, memoY, memoZ);

            if (stop) {
                return true;
            }
        }

        return false;
    }

    protected boolean overlapsWithRotation(FAggregate arg, FVector axis, double angle) {
        FSphere dummy = this.factory.getFSphere();

        for (Shape shapeRef : this.aggregate) {
            dummy.setRadius(shapeRef.getRadius());
            dummy.setCenter(shapeRef.getRefCenter());

            factory.rotate().mutate().aroundRg(dummy.getRefCenter(), axis, angle);

            for (Shape shapeArg : arg.getRefParticles()) {
                if (dummy.overlaps(shapeArg)) {
                    return true;
                }
            }
        }

        return false;
    }

    // -------------------------------------------------------------------------------------------------

    protected FStat getOverlapFactor(OverlapFactor type) {

        return switch (type) {
            case PARTICLE_LINEAR -> getParticleLinearOF();
            case PARTICLE_VOLUMETRIC -> getParticleVolumetricOF();
            case PARTICLE_QUANTITATIVE -> getParticleQuantitativeOF();
            case CLUSTER_VOLUMETRIC -> getClusterVolumetricOF();
        };
    }

    // -------------------------------------------------------------------------------------------------

    private FStat getParticleQuantitativeOF() {
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

    private FStat getParticleLinearOF() {
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

                if (shapeA.misses(shapeB)) {
                    continue;
                }

                double overlap = getParticleLinearSingle(shapeA, shapeB);

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

    private double getParticleLinearSingle(Shape shapeA, Shape shapeB) {
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

    private FStat getParticleVolumetricOF() {
        FStat results = this.factory.getFStat();

        for (Shape shape : this.aggregate.getRefParticles()) {
            getParticleVolumetricMethod(shape, results);
        }

        return results;
    }

    private void getParticleVolumetricMethod(Shape shape, FStat results) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            results.add(0);
        } else {
            getParticleVolumetricMethodApprox(shape, results);
        }
    }

    private void getParticleVolumetricMethodApprox(Shape shape, FStat results) {
        FLayer fLayer = this.factory.getFLayer();

        shape.fillVolumeLayerOverlap(fLayer, this.aggregate.getRefParticles());

        results.add(1 - (fLayer.get() / fLayer.addSelf()));
    }

    // -------------------------------------------------------------------------------------------------

    private FStat getClusterVolumetricOF() {
        List<Double> volume = new ArrayList<>();

        for (Shape shape : this.aggregate.getRefParticles()) {
            getClusterVolumetricMethod(shape, volume);
        }

        return getClusterTotalVolumetricProcess(volume);
    }

    private void getClusterVolumetricMethod(Shape shape, List<Double> volume) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            getClusterVolumetricMethodPrecise(shape, volume);
        } else {
            getClusterVolumetricMethodApprox(shape, volume);
        }
    }

    private void getClusterVolumetricMethodPrecise(Shape shape, List<Double> volume) {

        if (volume.isEmpty()) {
            volume.add(0d);
        }

        volume.set(0, volume.getFirst() + shape.getVolumeAlgebraic());
    }

    private void getClusterVolumetricMethodApprox(Shape shape, List<Double> volume) {
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

    private FStat getClusterTotalVolumetricProcess(List<Double> volume) {
        FStat results = this.factory.getFStat();

        double volTmp;
        double volTotal = 0;

        for (int i = 0 ; i < volume.size() - 1 ; i++) {
            results.add(0);
        }

        for (int i = 0 ; i < volume.size() ; i++) {
            volTmp = volume.get(i) / (i + 1);

            volTotal += volTmp;

            if (i > 0) {
                results.set(i - 1, results.get(i - 1) + volTmp);
            }
        }

        for (int i = 0 ; i < results.size() ; i++) {
            results.set(i, results.get(i) / volTotal);
        }

        return results;
    }
}
