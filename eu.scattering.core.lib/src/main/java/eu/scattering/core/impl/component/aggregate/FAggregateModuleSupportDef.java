package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.GyrationTensor;
import eu.scattering.core.design.utility.type.variant.Center;

import java.util.*;
import java.util.function.BiConsumer;

public class FAggregateModuleSupportDef {
    private final ScatterFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleSupportDef(ScatterFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected void clear() {

        this.aggregate.getRefParticles().clear();
    }

    protected void addParticles(Shape particle, double quantity) {

        for (int i = 0 ; i < quantity ; i++) {
            this.aggregate.getRefParticles().register(particle.copy());
        }
    }

    protected boolean addRefParticle(Shape particle) {

        return this.aggregate.getRefParticles().registerWithCheck(particle);
    }

    protected boolean delRefParticle(Shape particle) {

        return this.aggregate.getRefParticles().deregisterWithCheck(particle);
    }

    protected void addRefParticles(Shape... particles) {

        for (Shape particle : particles) {
            addRefParticle(particle);
        }
    }

    protected void addRefParticles(FAggregate... aggregates) {

        for (FAggregate aggregate : aggregates) {
            for (Shape particle : aggregate) {
                addRefParticle(particle);
            }
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected void setParticleDelta(double delta) {

        this.aggregate.getRefParticles().forEach(e -> e.setDelta(delta));
    }

    protected void setParticleEpsilon(double epsilon) {

        this.aggregate.getRefParticles().forEach(e -> e.setEpsilon(epsilon));
    }

    // -------------------------------------------------------------------------------------------------

    protected void index() {

        int i = 0;
        for (Shape shape : this.aggregate.getRefParticles()) {
            shape.setIndex(i++);
        }
    }

    public void merge(FAggregate arg, boolean removeParticles) {

        for (Shape shape : arg.getRefParticles()) {
            this.aggregate.getRefParticles().register(shape);
        }

        if (removeParticles) {
            arg.getRefParticles().clear();
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected void translate(double x, double y, double z) {

        this.aggregate.getRefParticles().translate(x, y, z);
    }

    protected void translate(FPoint offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void translate(FPos3D offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void translate(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        this.aggregate.getRefParticles().translate(bX, bY, bZ, hX, hY, hZ);
    }

    protected void translate(FVector offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void translate(FPairPos3D offset) {

        this.aggregate.getRefParticles().translate(offset);
    }

    protected void scaleSize(double factor) {

        this.aggregate.forEach(p -> p.scaleSize(factor));
    }

    protected void scalePosition(double factor) {

        this.aggregate.getRefParticles().scalePosition(factor);
    }

    // -------------------------------------------------------------------------------------------------

    protected double project(FAggregate target, FVector dir) {
        FVector translator = dir.copy();
        List<Shape> candidates = new ArrayList<>(this.aggregate.getRefParticles().asList());

        FPoint centerArg = target.getCenter(this.factory.getFPoint(), Center.BOX);

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(centerArg)));

        for (Shape candidate : candidates) {
            translator.moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(target, translator);

            if (shift >= 0) {
                translator.setMagnitude(shift);

                boolean overlaps = this.aggregate.overlapsWithShift(target, translator);

                if (!overlaps) {
                    for (Shape particle : this.aggregate) {
                        translator.set(dir);
                        this.factory.getFRayHelper().shiftForward(translator, particle, shift);
                    }

                    return shift;
                }
            }
        }

        return -1;
    }

    protected double project(FAggregate target, FVector dir, double distLimit) {
        FPoint centerRef = this.aggregate.getCenter(this.factory.getFPoint(), Center.BOX);
        FPoint centerArg = target.getCenter(this.factory.getFPoint(), Center.BOX);

        if (centerRef.getDistance(centerArg) > this.aggregate.getRadiusFrom(centerRef) + this.aggregate.getRadiusFrom(centerArg) + distLimit) {
            return -1;
        }

        FVector translator = dir.copy();
        List<Shape> candidates = new ArrayList<>(this.aggregate.getRefParticles().asList());

        candidates.sort(Comparator.comparingDouble(a -> a.getDistCenterP2(centerArg)));

        for (Shape candidate : candidates) {
            translator.moveBase(candidate.getRefCenter());

            double shift = candidate.projectFromDryRun(target, translator);

            if (shift >= 0 && shift <= distLimit) {
                translator.setMagnitude(shift);

                boolean overlaps = this.aggregate.overlapsWithShift(target, translator);

                if (!overlaps) {
                    for (Shape particle : this.aggregate) {
                        translator.set(dir);
                        this.factory.getFRayHelper().shiftForward(translator, particle, shift);
                    }

                    return shift;
                }
            }
        }

        return -1;
    }

    // -------------------------------------------------------------------------------------------------

    protected void forEachPairInContact(BiConsumer<Shape, Shape> consumer) {
        List<Shape> candidates = new ArrayList<>();

        Queue<Shape> queue = new LinkedList<>(this.aggregate.getRefParticles().asList());

        queue.poll();

        for (Shape shape : this.aggregate) {
            candidates.clear();

            shape.touchesOrOverlaps(queue, candidates);

            candidates.forEach(e -> consumer.accept(shape, e));

            queue.poll();
        }
    }

    // -------------------------------------------------------------------------------------------------

    protected void pca() {
        FMatrix3x3D eigenvectors = getEigenvectors(this.aggregate.getGyrationTensor(GyrationTensor.SIMPLE_POLY));

        double[][] results = new double[3][];

        results[0] = new double[] { eigenvectors.get0x0(), eigenvectors.get1x0(), eigenvectors.get2x0() };
        results[1] = new double[] { eigenvectors.get0x1(), eigenvectors.get1x1(), eigenvectors.get2x1() };
        results[2] = new double[] { eigenvectors.get0x2(), eigenvectors.get1x2(), eigenvectors.get2x2() };

        this.aggregate.rotate(this.factory.getFMatrix3x3D(results));
    }

    private FMatrix3x3D getEigenvectors(FMatrix3x3D tensor) {
        int iterations = 5;

        double[][] a = tensor.getArray();

        double[][] identity = new double[3][3];
        identity[0][0] = identity[1][1] = identity[2][2] = 1.0;

        for (int i = 0; i < iterations; i++) {
            getEigenvectorsRotate(a, identity, 0, 1);
            getEigenvectorsRotate(a, identity, 0, 2);
            getEigenvectorsRotate(a, identity, 1, 2);
        }

        return getEigenvectorsProcess(identity, a);
    }

    private void getEigenvectorsRotate(double[][] a, double[][] v, int p, int q) {

        if (Math.abs(a[p][q]) < 1e-12) {
            return;
        }

        double theta = 0.5 * (a[q][q] - a[p][p]) / a[p][q];
        double t = 1.0 / (Math.abs(theta) + Math.sqrt(theta*theta + 1.0));

        if (theta < 0) {
            t = -t;
        }

        double c = 1.0 / Math.sqrt(t*t + 1.0);
        double s = t * c;
        double tau = s / (1.0 + c);

        double app = a[p][p];
        double aqq = a[q][q];
        double apq = a[p][q];

        a[p][p] = app - t * apq;
        a[q][q] = aqq + t * apq;
        a[p][q] = a[q][p] = 0.0;

        for (int i = 0; i < 3; i++) {
            double vip = v[i][p];
            double viq = v[i][q];

            if (i != p && i != q) {
                double aip = a[i][p];
                double aiq = a[i][q];

                a[i][p] = a[p][i] = aip - s * (aiq + tau * aip);
                a[i][q] = a[q][i] = aiq + s * (aip - tau * aiq);
            }

            v[i][p] = c * vip - s * viq;
            v[i][q] = s * vip + c * viq;
        }
    }

    private FMatrix3x3D getEigenvectorsProcess(double[][] identity, double[][] a) {

        FPos3D e1 = this.factory.getFPos3D(identity[0][0], identity[1][0], identity[2][0]);
        FPos3D e2 = this.factory.getFPos3D(identity[0][1], identity[1][1], identity[2][1]);
        FPos3D e3 = this.factory.getFPos3D(identity[0][2], identity[1][2], identity[2][2]);

        double l1 = a[0][0];
        double l2 = a[1][1];
        double l3 = a[2][2];

        FPos3D[] eigenvectors = new FPos3D[] {e1, e2, e3};
        double[] eigenvalues = new double[] {l1, l2, l3};

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2 - i; j++) {
                if (eigenvalues[j] < eigenvalues[j + 1]) {
                    FPos3D tmpVector = eigenvectors[j];
                    double tmpValue = eigenvalues[j];

                    eigenvalues[j] = eigenvalues[j + 1];
                    eigenvalues[j + 1] = tmpValue;

                    eigenvectors[j] = eigenvectors[j + 1];
                    eigenvectors[j + 1] = tmpVector;
                }
            }
        }

        double[][] results = new double[3][];

        results[0] = new double[] {eigenvectors[0].getD0(), eigenvectors[1].getD0(), eigenvectors[2].getD0()};
        results[1] = new double[] {eigenvectors[0].getD1(), eigenvectors[1].getD1(), eigenvectors[2].getD1()};
        results[2] = new double[] {eigenvectors[0].getD2(), eigenvectors[1].getD2(), eigenvectors[2].getD2()};

        return this.factory.getFMatrix3x3D(results);
    }

    // -------------------------------------------------------------------------------------------------

    protected void shiftBoundaryToZero() {
        FPos3D boundary = this.aggregate.getBoundary().getPosA();

        this.aggregate.forEach(e -> e.translate(-boundary.getD0(), -boundary.getD1(), -boundary.getD2()));
    }

    protected void rotate(FMatrix3x3D matrix) {

        this.aggregate.forEach(e -> e.rotate(matrix));
    }
}
