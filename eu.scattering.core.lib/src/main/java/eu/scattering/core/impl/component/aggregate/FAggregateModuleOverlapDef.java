package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;

import java.util.*;
import java.util.function.BiConsumer;

import static eu.scattering.core.impl.ScatterCoreConfig.EPSILON;

public class FAggregateModuleOverlapDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleOverlapDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected boolean isNonOverlapping() {
        boolean[] hasOverlap = {false};

        FCellList core = FCellList.create(this.aggregate, (shapeA, shapeB, indexA, indexB) -> {

            if (shapeA.overlaps(shapeB)) {
                hasOverlap[0] = true;
                return false;
            }

            return true;
        });

        core.execute();

        return !hasOverlap[0];
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

    // -------------------------------------------------------------------------------------------------

    protected int forEachPairInContact(BiConsumer<Shape, Shape> consumer) {
        int[] particlePairs = {0};

        FCellList core = FCellList.create(this.aggregate, (shapeA, shapeB, indexA, indexB) -> {

            if (shapeA.touchesOrOverlaps(shapeB)) {
                consumer.accept(shapeA, shapeB);
                particlePairs[0]++;
            }

            return true;
        });

        core.execute();

        return particlePairs[0];
    }

    protected int forEachPairOverlapping(BiConsumer<Shape, Shape> consumer) {
        int[] particlePairs = {0};

        FCellList core = FCellList.create(this.aggregate, (shapeA, shapeB, indexA, indexB) -> {

            if (shapeA.overlaps(shapeB)) {
                consumer.accept(shapeA, shapeB);
                particlePairs[0]++;
            }

            return true;
        });

        core.execute();

        return particlePairs[0];
    }

    // -------------------------------------------------------------------------------------------------

    protected void relaxOverlap() {
        int[] particlePairs = {0};

        FPoint separationVector = this.factory.getFPoint();
        FPoint[] particleShifts = new FPoint[this.aggregate.size()];

        for (int i = 0; i < particleShifts.length; i++) {
            particleShifts[i] = this.factory.getFPoint();
        }

        FCellList core = FCellList.create(this.aggregate, (shapeA, shapeB, indexA, indexB) -> {

            if (shapeA.overlaps(shapeB)) {
                updateShifts(particleShifts, separationVector, shapeA, shapeB, indexA, indexB);
                particlePairs[0]++;
            }

            return true;
        });


        int iteration = 0;
        while (true) {

            if (iteration++ > 0) {
                for (FPoint shift : particleShifts) {
                    shift.set(0, 0, 0);
                }
            }

            particlePairs[0] = 0;

            core.execute();

            if (particlePairs[0] == 0) {
                break;
            }

            updateParticles(particleShifts);
        }
    }

    private void updateShifts(FPoint[] shifts, FPoint vec, Shape shapeA, Shape shapeB, int indexA, int indexB) {

        if (shapeA.getRefCenter().isSimilar(shapeB.getRefCenter())) {
            breakCenterSymmetry(shapeA, shapeB);
        }

        setSeparationVector(vec, shapeA, shapeB);

        shifts[indexB].add(vec);
        shifts[indexA].sub(vec);
    }

    private void updateParticles(FPoint[] shifts) {

        int i = 0;
        for (Shape shape : this.aggregate) {
            shape.getRefCenter().add(shifts[i++]);
        }
    }

    private void breakCenterSymmetry(Shape shapeA, Shape shapeB) {
        double shift = Math.min(shapeA.getRadius() * 1E-4, shapeB.getRadius() * 1E-4);
        FPos3D trans = this.factory.random().engine().nextDoubleOnSphere(shift);

        shapeA.getRefCenter().add(trans);
    }

    private void setSeparationVector(FPoint vec, Shape shapeA, Shape shapeB) {
        double dx = shapeB.getCenterX() - shapeA.getCenterX();
        double dy = shapeB.getCenterY() - shapeA.getCenterY();
        double dz = shapeB.getCenterZ() - shapeA.getCenterZ();

        double distRequired = shapeA.getRadius() + shapeB.getRadius() + EPSILON;
        double distCurrent = Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));

        double shift = (distRequired - distCurrent) * 0.5 * 0.25;
        double shiftFactor = shift / distCurrent;

        vec.set(dx * shiftFactor, dy * shiftFactor, dz * shiftFactor);
    }

    // -------------------------------------------------------------------------------------------------

    @FunctionalInterface
    public interface NeighbourPredicate {

        boolean test(Shape shapeA, Shape shapeB, int indexA, int indexB);
    }

    private static class FCellList {
        NeighbourPredicate handler;

        List<Shape> particles;

        double minX, minY, minZ, minR;
        double maxX, maxY, maxZ, maxR;

        int d0, d1, d2, gridSize;
        double cellLength = 0;

        int[] head;
        int[] next;

        private FCellList(FAggregate aggregate, NeighbourPredicate handler) {
            this.handler = handler;

            this.particles = aggregate.getRefParticles().asList();

            this.head = new int[0];
            this.next = new int[this.particles.size()];
        }

        public static FCellList create(FAggregate aggregate, NeighbourPredicate handler) {

            return new FCellList(aggregate, handler);
        }

        public void execute() {
            setGeometry();
            setArrays();
            search();
        }

        private void setGeometry() {
            this.minX = Double.POSITIVE_INFINITY;
            this.minY = Double.POSITIVE_INFINITY;
            this.minZ = Double.POSITIVE_INFINITY;
            this.minR = Double.POSITIVE_INFINITY;
            this.maxX = Double.NEGATIVE_INFINITY;
            this.maxY = Double.NEGATIVE_INFINITY;
            this.maxZ = Double.NEGATIVE_INFINITY;
            this.maxR = Double.NEGATIVE_INFINITY;

            for (Shape shape : this.particles) {
                FPoint center = shape.getRefCenter();

                double x = center.getX();
                double y = center.getY();
                double z = center.getZ();
                double r = shape.getRadius();

                this.minX = Math.min(this.minX, x);
                this.minY = Math.min(this.minY, y);
                this.minZ = Math.min(this.minZ, z);
                this.minR = Math.min(this.minR, r);
                this.maxX = Math.max(this.maxX, x);
                this.maxY = Math.max(this.maxY, y);
                this.maxZ = Math.max(this.maxZ, z);
                this.maxR = Math.max(this.maxR, r);
            }

            this.cellLength = (this.maxR * 2) + EPSILON;

            this.d0 = (int) Math.ceil((this.maxX - this.minX) / this.cellLength) + 1;
            this.d1 = (int) Math.ceil((this.maxY - this.minY) / this.cellLength) + 1;
            this.d2 = (int) Math.ceil((this.maxZ - this.minZ) / this.cellLength) + 1;

            this.gridSize = this.d0 * this.d1 * this.d2;
        }

        private void setArrays() {

            if (this.head.length < this.gridSize) {
                this.head = new int[this.gridSize];
            }

            Arrays.fill(this.head, 0, this.gridSize, -1);
            Arrays.fill(this.next, -1);

            int i = 0;
            for (Shape shape : this.particles) {
                int cx = (int) ((shape.getCenterX() - minX) / cellLength);
                int cy = (int) ((shape.getCenterY() - minY) / cellLength);
                int cz = (int) ((shape.getCenterZ() - minZ) / cellLength);
                int cellId = cx + (cy * d0) + (cz * d0 * d1);

                next[i] = head[cellId];
                head[cellId] = i;

                i++;
            }
        }

        private void search() {

            searchLoop:
            for (int i = 0; i < this.particles.size(); i++) {
                Shape shapeA = this.particles.get(i);

                int cx = (int) ((shapeA.getCenterX() - minX) / cellLength);
                int cy = (int) ((shapeA.getCenterY() - minY) / cellLength);
                int cz = (int) ((shapeA.getCenterZ() - minZ) / cellLength);

                for (int nx = cx - 1; nx <= cx + 1; nx++) {
                    if (nx < 0 || nx >= d0) {
                        continue;
                    }

                    for (int ny = cy - 1; ny <= cy + 1; ny++) {
                        if (ny < 0 || ny >= d1) {
                            continue;
                        }

                        for (int nz = cz - 1; nz <= cz + 1; nz++) {
                            if (nz < 0 || nz >= d2) {
                                continue;
                            }

                            int j = head[nx + (ny * d0) + (nz * d0 * d1)];

                            while (j != -1) {
                                if (j > i) {
                                    Shape shapeB = this.particles.get(j);

                                    boolean continueSearch = handler.test(shapeA, shapeB, i, j);

                                    if (!continueSearch) {
                                        break searchLoop;
                                    }
                                }

                                j = next[j];
                            }
                        }
                    }
                }
            }
        }
    }
}
