package eu.scattering.core.impl.component.aggregate;

import eu.scattering.core.design.ScatFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;

import java.util.LinkedList;
import java.util.List;

public class FAggregateModuleMorphologyDef {
    private final ScatFactory factory;
    private final FAggregate aggregate;

    protected FAggregateModuleMorphologyDef(ScatFactory factory, FAggregate aggregate) {

        this.factory = factory;
        this.aggregate = aggregate;
    }

    // -------------------------------------------------------------------------------------------------

    protected FStat getPairDistance() {
        FStat distance = this.factory.getFStat();

        List<Shape> particles = this.aggregate.getRefParticles().asList();

        for (int i = 0 ; i < this.aggregate.size() - 1 ; i++) {
            for (int j = i + 1 ; j < this.aggregate.size() ; j++) {
                distance.add(particles.get(i).getDistCenter(particles.get(j)));
            }
        }

        return distance;
    }

    protected FPlot getPairDistanceFunction() {
        FStat distance = getPairDistance();
        FStat radius = this.aggregate.getFStatParticleRadius();

        double max = distance.max();
        int steps = (int) (max / radius.min());

        return distance.toFPlotHistogram(0, max, steps);
    }

    // -------------------------------------------------------------------------------------------------

    protected FStat getCoordinationNumber() {
        FStat coordination = factory.getFStat();

        for (Shape shape : this.aggregate) {
            coordination.add(shape.touchesOrOverlaps(this.aggregate.getRefParticles()));
        }

        return coordination;
    }

    protected FPlot getCoordinationNumberFunction() {
        FStat coordination = getCoordinationNumber();

        double max = coordination.max();

        return coordination.toFPlotHistogram(1, max, (int) max - 1);
    }

    // -------------------------------------------------------------------------------------------------

    protected FStat getTripletAngle() {
        FStat angle = this.factory.getFStat();

        List<Shape> neighbours = new LinkedList<>();
        FVector vecA = this.factory.getFVector();
        FVector vecB = this.factory.getFVector();

        for (Shape shape : this.aggregate) {
            shape.touchesOrOverlaps(this.aggregate.getRefParticles(), neighbours);

            if (neighbours.size() < 2) {
                continue;
            }

            for (int i = 0 ; i < neighbours.size() - 1 ; i++) {
                for (int j = i + 1 ; j < neighbours.size() ; j++) {
                    vecA.setBase(shape.getRefCenter());
                    vecB.setBase(shape.getRefCenter());

                    vecA.setHead(neighbours.get(i).getRefCenter());
                    vecB.setHead(neighbours.get(j).getRefCenter());

                    angle.add(vecA.getAngle(vecB));
                }
            }
        }

        return angle;
    }

    protected FPlot getTripletAngleFunction() {

        return getTripletAngle().toFPlotHistogram(0, Math.PI, 180);
    }
}
