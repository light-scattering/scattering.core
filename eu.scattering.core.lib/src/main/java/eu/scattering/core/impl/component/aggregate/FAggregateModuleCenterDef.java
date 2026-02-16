package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.transfer.box.variant.FBoxDouble;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.GyrationTensor;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.method.MassCenter;

import java.util.ArrayList;
import java.util.List;

public class FAggregateModuleCenterDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleCenterDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected void getCenter(FPoint in, Center type) {

        switch (type) {
            case ORIGIN -> in.set(0, 0, 0);
            case MASS -> getMassCenter(in, null, MassCenter.ADAPTIVE);
            case SPATIAL -> getSpatialCenter(in);
            case SPHERICAL -> getSphericalCenter(in, 100);
        }
    }

    protected FPos3D getCenter(Center type) {
        FPoint center = factory.getFPoint();

        getCenter(center, type);

        return center.toFPos3D();
    }

    protected void getSpatialCenter(FPoint in) {

        this.aggregate.getRefParticles().getSpatialCenter(in);
    }

    protected FPos3D getSpatialCenter() {
        FPoint center = factory.getFPoint();

        getSpatialCenter(center);

        return center.toFPos3D();
    }

    protected void getSphericalCenter(FPoint in, int steps) {

        this.aggregate.getRefParticles().getSphericalCenter(in, steps);
    }

    protected FPos3D getSphericalCenter(int steps) {
        FPoint center = this.factory.getFPoint();

        getSphericalCenter(center, steps);

        return center.toFPos3D();
    }

    protected void getMassCenter(FPoint in, List<Double> massData, MassCenter type) {

        switch (type) {
            case SIMPLE_MONO -> getMassCenterMethodSimpleMono(in, massData);
            case SIMPLE_POLY -> getMassCenterMethodSimplePoly(in, massData);
            case COMPLEX -> getMassCenterMethodComplex(in, massData);
            case ADAPTIVE -> getMassCenterMethodAdaptive(in, massData);
        }
    }

    protected FPos3D getMassCenter(List<Double> massData, MassCenter type) {
        FPoint center = this.factory.getFPoint();

        getMassCenter(center, massData, type);

        return center.toFPos3D();
    }

    private void getMassCenterMethodSimpleMono(FPoint in, List<Double> massData) {
        double radius = this.aggregate.getFStatParticleRadius().mean();

        double mass = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            double massFragment = getMassCenterMethodSimpleMonoStep(in, shape, radius);

            mass += massFragment;

            if (massData != null) {
                massData.add(massFragment);
            }
        }

        in.divFactor(mass);
    }

    private void getMassCenterMethodSimplePoly(FPoint in, List<Double> massData) {
        double mass = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            double massFragment = getMassCenterMethodSimplePolyStep(in, shape);

            mass += massFragment;

            if (massData != null) {
                massData.add(massFragment);
            }
        }

        in.divFactor(mass);
    }

    private void getMassCenterMethodAdaptive(FPoint in, List<Double> massData) {
        double mass = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            double massFragment = getMassCenterMethodAdaptiveStep(in, shape);

            mass += massFragment;

            if (massData != null) {
                massData.add(massFragment);
            }
        }

        in.divFactor(mass);
    }

    private void getMassCenterMethodComplex(FPoint in, List<Double> massData) {
        double mass = 0;

        in.set(0, 0, 0);

        for (Shape shape : this.aggregate) {
            double massFragment = getMassCenterMethodComplexStep(in, shape);

            mass += massFragment;

            if (massData != null) {
                massData.add(massFragment);
            }
        }

        in.divFactor(mass);
    }

    private double getMassCenterMethodSimpleMonoStep(FPoint center, Shape shape, double radius) {

        if (shape.getCoatCount() > 0) {
            throw new IllegalArgumentException("SIMPLE_MONO option cannot be used with coated particles");
        }

        return getMassCenterMethodSimpleMonoPrecise(center, shape, radius);
    }

    private double getMassCenterMethodSimpleMonoPrecise(FPoint center, Shape shape, double radius) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            return getMassCenterMethodSimpleMonoPreciseMath(center, shape, radius);
        }

        return getMassCenterMethodSimpleMonoPrecisePhys(center, shape, radius);
    }

    private double getMassCenterMethodSimpleMonoPreciseMath(FPoint center, Shape shape, double radius) {
        double volume = this.factory.getFSphereHelper().getVolume(radius);

        center.setX(center.getX() + (shape.getCenterX() * volume));
        center.setY(center.getY() + (shape.getCenterY() * volume));
        center.setZ(center.getZ() + (shape.getCenterZ() * volume));

        return volume;
    }

    private double getMassCenterMethodSimpleMonoPrecisePhys(FPoint center, Shape shape, double radius) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        double volume = this.factory.getFSphereHelper().getVolume(radius);
        double mass = volume * material.getDensity(shape.getMeta());

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterMethodSimplePolyStep(FPoint center, Shape shape) {

        return getMassCenterMethodSimplePolyPrecise(center, shape);
    }

    private double getMassCenterMethodSimplePolyPrecise(FPoint center, Shape shape) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            return getMassCenterMethodSimplePolyPreciseMath(center, shape);
        }

        return getMassCenterMethodSimplePolyPrecisePhys(center, shape);
    }

    private double getMassCenterMethodSimplePolyPreciseMath(FPoint center, Shape shape) {
        double volume = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            volume += shape.getLayerVolume(i);
        }

        center.setX(center.getX() + (shape.getCenterX() * volume));
        center.setY(center.getY() + (shape.getCenterY() * volume));
        center.setZ(center.getZ() + (shape.getCenterZ() * volume));

        return volume;
    }

    private double getMassCenterMethodSimplePolyPrecisePhys(FPoint center, Shape shape) {
        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        double mass = 0;

        for (int i = 0 ; i < shape.getLayerCount() ; i++) {
            String meta = shape.getMetaData().get(i).getMeta();

            mass += shape.getLayerVolume(i) * material.getDensity(meta);
        }

        center.setX(center.getX() + (shape.getCenterX() * mass));
        center.setY(center.getY() + (shape.getCenterY() * mass));
        center.setZ(center.getZ() + (shape.getCenterZ() * mass));

        return mass;
    }

    private double getMassCenterMethodComplexStep(FPoint center, Shape shape) {

        return getMassCenterMethodComplexApprox(center, shape);
    }

    private double getMassCenterMethodComplexApprox(FPoint center, Shape shape) {

        if (this.aggregate.getRefFExtension().getRefFMaterial() == null) {
            return getMassCenterMethodComplexApproxMath(center, shape);
        }

        return getMassCenterMethodComplexApproxPhys(center, shape);
    }

    private double getMassCenterMethodComplexApproxMath(FPoint center, Shape shape) {
        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        buffer.clear();

        double unitVolume = shape.fillVolumeArray(buffer, this.aggregate.getRefParticles().asList());

        FBoxDouble volume = this.factory.getFBoxDouble();

        this.aggregate.getRefFExtension().getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            center.setX(center.getX() + (d0 * unitVolume));
            center.setY(center.getY() + (d1 * unitVolume));
            center.setZ(center.getZ() + (d2 * unitVolume));

            volume.setValue(volume.getValue() + unitVolume);
        });

        return volume.getValue();
    }

    private double getMassCenterMethodComplexApproxPhys(FPoint center, Shape shape) {
        FBuffer<FBufferData> buffer = this.aggregate.getRefFExtension().getRefFBuffer();

        if (buffer == null) {
            throw new IllegalStateException("To perform this operation a FBuffer object must be added to the structure");
        }

        FMaterial material = this.aggregate.getRefFExtension().getRefFMaterial();

        buffer.clear();

        double unitVolume = shape.fillVolumeArray(buffer, this.aggregate.getRefParticles().asList());

        FBoxDouble mass = this.factory.getFBoxDouble();

        this.aggregate.getRefFExtension().getRefFBuffer().forEach((index, d0, d1, d2, data, meta) -> {
            double unitMass = unitVolume * material.getDensity(meta.getMeta());

            center.setX(center.getX() + (d0 * unitMass));
            center.setY(center.getY() + (d1 * unitMass));
            center.setZ(center.getZ() + (d2 * unitMass));

            mass.setValue(mass.getValue() + unitMass);
        });

        return mass.getValue();
    }

    private double getMassCenterMethodAdaptiveStep(FPoint center, Shape shape) {

        if (shape.overlaps(this.aggregate.getRefParticles()) == 0) {
            return getMassCenterMethodSimplePolyPrecise(center, shape);
        }

        return getMassCenterMethodComplexApprox(center, shape);
    }

    // -------------------------------------------------------------------------------------------------

    protected void setPositionAsZero(FPoint center) {

        this.aggregate.getRefParticles().translate(-center.getX(), -center.getY(), -center.getZ());
    }

    protected void setPositionAsZero(FPos3D center) {

        this.aggregate.getRefParticles().translate(-center.getD0(), -center.getD1(), -center.getD2());
    }

    protected void setCenterAsZero(Center type) {

        switch (type) {
            case ORIGIN -> {}
            case MASS -> setMassCenterAsZero(MassCenter.ADAPTIVE);
            case SPATIAL -> setSpatialCenterAsZero();
            case SPHERICAL -> setSphericalCenterAsZero(100);
        }
    }

    protected void setMassCenterAsZero(MassCenter type) {
        FPoint center = this.factory.getFPoint();

        getMassCenter(center, null, type);

        setPositionAsZero(center);
    }

    protected void setSpatialCenterAsZero() {
        FPoint center = this.factory.getFPoint();

        getSpatialCenter(center);

        setPositionAsZero(center);
    }

    protected void setSphericalCenterAsZero(int steps) {
        FPoint center = this.factory.getFPoint();

        getSphericalCenter(center, steps);

        setPositionAsZero(center);
    }

    protected void setCenter(Center type, double x, double y, double z) {

        this.aggregate.getRefParticles().translate(getCenter(type), x, y, z);
    }

    protected void setCenter(Center type, FPoint position) {

        setCenter(type, position.getX(), position.getY(), position.getZ());
    }

    protected void setCenter(Center type, FPos3D position) {

        setCenter(type, position.getD0(), position.getD1(), position.getD2());
    }

    protected void setMassCenter(double x, double y, double z, MassCenter type) {

        this.aggregate.getRefParticles().translate(getMassCenter(null, type), x, y, z);
    }

    protected void setMassCenter(FPoint position, MassCenter type) {

        setMassCenter(position.getX(), position.getY(), position.getZ(), type);
    }

    protected void setMassCenter(FPos3D position, MassCenter type) {

        setMassCenter(position.getD0(), position.getD1(), position.getD2(), type);
    }

    protected void setSpatialCenter(double x, double y, double z) {

        this.aggregate.getRefParticles().translate(getSpatialCenter(), x, y, z);
    }

    protected void setSpatialCenter(FPoint position) {

        setSpatialCenter(position.getX(), position.getY(), position.getZ());
    }

    protected void setSpatialCenter(FPos3D position) {

        setSpatialCenter(position.getD0(), position.getD1(), position.getD2());
    }

    protected void setSphericalCenter(double x, double y, double z, int steps) {

        this.aggregate.getRefParticles().translate(getSphericalCenter(steps), x, y, z);
    }

    protected void setSphericalCenter(FPoint position, int steps) {

        setSphericalCenter(position.getX(), position.getY(), position.getZ(), steps);
    }

    protected void setSphericalCenter(FPos3D position, int steps) {

        setSphericalCenter(position.getD0(), position.getD1(), position.getD2(), steps);
    }

    // -------------------------------------------------------------------------------------------------

    protected FMatrix3x3D getGyrationTensor(GyrationTensor type) {
        double[][] tensor = new double[3][3];

        List<Double> massData = new ArrayList<>(this.aggregate.size());

        MassCenter massCenterType = switch (type) {
            case ADAPTIVE -> MassCenter.ADAPTIVE;
            case SIMPLE_MONO -> MassCenter.SIMPLE_MONO;
            case SIMPLE_POLY -> MassCenter.SIMPLE_POLY;
            case COMPLEX -> MassCenter.COMPLEX;
        };

        FPos3D massCenter = getMassCenter(massData, massCenterType);

        for (int i = 0 ; i < this.aggregate.size() ; i++) {
            Shape particle = this.aggregate.getRefParticles().asList().get(i);
            double mass = massData.get(i);

            double dx = particle.getCenterX() - massCenter.getD0();
            double dy = particle.getCenterY() - massCenter.getD1();
            double dz = particle.getCenterZ() - massCenter.getD2();

            tensor[0][0] += mass * dx * dx;
            tensor[0][1] += mass * dx * dy;
            tensor[0][2] += mass * dx * dz;

            tensor[1][1] += mass * dy * dy;
            tensor[1][2] += mass * dy * dz;

            tensor[2][2] += mass * dz * dz;
        }

        tensor[1][0] = tensor[0][1];
        tensor[2][0] = tensor[0][2];
        tensor[2][1] = tensor[1][2];

        return this.factory.getFMatrix3x3D(tensor);
    }

    protected FMatrix3x3D getEigenvectors(FMatrix3x3D tensor) {
        int iterations = 5;

        double[][] a = tensor.getArray();

        double[][] identity = new double[3][3];
        identity[0][0] = identity[1][1] = identity[2][2] = 1.0;

        for (int i = 0; i < iterations; i++) {
            rotate(a, identity, 0, 1);
            rotate(a, identity, 0, 2);
            rotate(a, identity, 1, 2);
        }

        return process(identity, a);
    }

    private void rotate(double[][] a, double[][] v, int p, int q) {

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

    private FMatrix3x3D process(double[][] identity, double[][] a) {

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

    protected FMatrix3x3D getRotationPCA() {
        FMatrix3x3D eigenvectors = getEigenvectors(getGyrationTensor(GyrationTensor.SIMPLE_POLY));

        double[][] results = new double[3][];

        results[0] = new double[] { eigenvectors.get0x0(), eigenvectors.get1x0(), eigenvectors.get2x0() };
        results[1] = new double[] { eigenvectors.get0x1(), eigenvectors.get1x1(), eigenvectors.get2x1() };
        results[2] = new double[] { eigenvectors.get0x2(), eigenvectors.get1x2(), eigenvectors.get2x2() };

        return this.factory.getFMatrix3x3D(results);
    }

}