package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.extension.Producer;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FAggregateFactoryGeoDef {

    public static FAggregate getFAggregateGeo1d(ScatFactory factory, int d1, double radius) {
        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        int index = 0;
        Shape candidate;
        for (int i = 0 ; i < d1 ; i++) {
            candidate = fProducer.produce();
            candidate.setCenterX(i * (2 * radius));
            candidate.setIndex(index++);

            fAssembly.register(candidate);
        }

        FAggregate fAggregate = FAggregateDef.create(factory, fAssembly);

        FPoint center = factory.getFPoint();
        fAggregate.getSpatialCenter(center);

        fAggregate.resetCenter(center);

        return fAggregate;
    }

    public static FAggregate getFAggregateGeo2d(ScatFactory factory, int d1, int d2, double radius) {
        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        int index = 0;
        Shape candidate;
        for (int i = 0 ; i < d1 ; i++) {
            for (int j = 0 ; j < d2 ; j++) {
                candidate = fProducer.produce();
                candidate.setCenterX(i * (2 * radius)).setCenterY(j * (2 * radius));
                candidate.setIndex(index++);

                fAssembly.register(candidate);
            }
        }

        FAggregate fAggregate = FAggregateDef.create(factory, fAssembly);

        FPoint center = factory.getFPoint();
        fAggregate.getSpatialCenter(center);

        fAggregate.resetCenter(center);

        return fAggregate;
    }

    public static FAggregate getFAggregateGeo3d(ScatFactory factory, int d1, int d2, int d3, double radius) {
        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        int index = 0;
        Shape candidate;
        for (int i = 0 ; i < d1 ; i++) {
            for (int j = 0 ; j < d2 ; j++) {
                for (int k = 0 ; k < d3 ; k++) {
                    candidate = fProducer.produce();
                    candidate.setCenterX(i * (2 * radius)).setCenterY(j * (2 * radius)).setCenterZ(k * (2 * radius));
                    candidate.setIndex(index++);

                    fAssembly.register(candidate);
                }
            }
        }

        FAggregate fAggregate = FAggregateDef.create(factory, fAssembly);

        FPoint center = factory.getFPoint();
        fAggregate.getSpatialCenter(center);

        fAggregate.resetCenter(center);

        return fAggregate;
    }

    public static FAggregate getFAggregateGeoFullCircle(ScatFactory factory, int layers, double radius) {

        if (layers < 1) {
            throw new IllegalArgumentException("The number of layers must be greater than zero");
        }

        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        FSphereHelper helper = factory.getFSphereHelper();

        fAssembly.register(fProducer.produce());
        for (int i = 1 ; i < layers ; i++) {
            double sphereRadius = i * radius * (2 + EPSILON);
            double theta = 2 * Math.asin(radius / sphereRadius);

            int points = (int) Math.floor(2 * Math.PI / theta);

            helper.getCirclePoints(sphereRadius, points, (x, y) ->
                    fAssembly.register(fProducer.produce().setCenter(x, y, 0)));
        }

        FAggregate fAggregate = FAggregateDef.create(factory, fAssembly);

        int index = 0;
        for (Shape shape : fAssembly) {
            shape.setIndex(index++);
        }

        return fAggregate;
    }

    // Approximate solution
    public static FAggregate getFAggregateGeoFullSphere(ScatFactory factory, int layers, double radius) {

        if (layers < 1) {
            throw new IllegalArgumentException("The number of layers must be greater than zero");
        }

        Producer<FSphere> fProducer = factory.getFSphereProducer(radius);
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        FSphereHelper helper = factory.getFSphereHelper();

        // Experimental value
        double shapeArea = Math.pow(radius * 2 * 1.15, 2);

        fAssembly.register(fProducer.produce());
        for (int i = 1 ; i < layers ; i++) {
            double sphereRadius = i * radius * (2 + EPSILON);
            double sphereArea = 4 * Math.PI * Math.pow(sphereRadius, 2);

            int points = (int) (sphereArea / shapeArea);

            helper.getSpherePoints(sphereRadius, points, (x, y, z) ->
                    fAssembly.register(fProducer.produce().setCenter(x, y, z)));
        }

        FAggregate fAggregate = FAggregateDef.create(factory, fAssembly);

        int index = 0;
        for (Shape shape : fAssembly) {
            shape.setIndex(index++);
        }

        return fAggregate;
    }
}
