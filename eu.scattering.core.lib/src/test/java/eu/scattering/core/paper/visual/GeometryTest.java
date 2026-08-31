package eu.scattering.core.paper.visual;

import eu.scattering.core.design.aspect.randomize.generator.module.dist3d.FDist3D;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.cc.ballistic.FModelCCBallistic;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPCFactoryContext;
import eu.scattering.core.design.component.aggregate.model.pc.ballistic.FModelPCBallistic;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.statistics.construct.plotbar.FPlotBar;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

//@Disabled
@DisplayName("Geometry")
public class GeometryTest {

//    @Test
//    void geometryAggregation() {
//// Create a preliminary pool of 1,000 monodisperse primary particles.
//        FAggregate aggregate = factory.getFAggregateContext().base().monodisperse(1_000, 1);
//
//// Access the PC model factory context.
//        FModelPCFactoryContext context = factory.getFModelContext().pc();
//
//// Initialize standard PC models.
//        FModelPC rla = context.rla(aggregate);              // Reaction-Limited Aggregation.
//        FModelPC dla = context.dla(aggregate);              // Diffusion-Limited Aggregation.
//        FModelPC ballistic = context.ballistic(aggregate);  // Ballistic aggregation.
//
//// Initialize a tunable aggregation model.
//// This is the only model that accepts explicit fractal parameters (dimension and prefactor).
//        FModelPC tunable = context.tunable(aggregate, 1.8, 1.3);
//
//        rla.build();
//
//        String visio = factory.getSaveAspect().getComponentContext().toPovRay(aggregate, ExPovRay.FREE);
//
//        dla.build();
//        visio = factory.getSaveAspect().getComponentContext().toPovRay(aggregate, ExPovRay.FREE);
//
//        ballistic.build();
//        visio = factory.getSaveAspect().getComponentContext().toPovRay(aggregate, ExPovRay.FREE);
//        tunable.build();
//        visio = factory.getSaveAspect().getComponentContext().toPovRay(aggregate, ExPovRay.FREE);
//
//        System.out.println("test");
//    }

//    @Test
//    void geometryAggregation() {
//        FAggregate aggregate = factory.getFAggregateContext().base().polydisperse(10_000, 1, 0.1);
//
//        FModelCC d3 = factory.getFModelContext().cc().ballistic(aggregate);
//        FModelCC d2 = factory.getFModelContext().cc().ballistic(Dimension.D2, aggregate);
//
//        d3.build();
//
//        String d3Visual = factory.getSaveAspect().getComponentContext().toPovRay(aggregate, ExPovRay.FREE);
//
//        d2.build();
//
//        String d2Visual = factory.getSaveAspect().getComponentContext().toPovRay(aggregate, ExPovRay.FREE);
//
//        System.out.println();
//
//    }

    @Test
    void geometryManual() {
        FAggregate aggregate = factory.getFAggregateContext().base().monodisperse(30, 1);

        FModelCCBallistic model = factory.getFModelContext().cc().ballistic(aggregate);



        FAggregate container = factory.getFAggregate();
        model.addStepAcceptor((clusterA, clusterB) -> {

            if (clusterA.size() + clusterB.size() < 100) {
                return true;
            }

            container.clear();
            container.addRefParticles(clusterA, clusterB);

            return container.getFractalDimension(FractalDimension.DC_RESTRICTED) > 2;
        });


        FPlotBar diameterA = factory.getFPlotBar();
        model.addFragmentViewer((fragment) -> diameterA.add(fragment.size(), fragment.getDiameter()));

        FPlotBar diameter = factory.getFPlotBar();
        model.addStepMonitor((clusterA, clusterB, index) -> {

            diameter.add(clusterA.size(), clusterA.getDiameter());

            if (clusterB != null) {
                diameter.add(clusterB.size(), clusterB.getDiameter());
            }
        });

        model.build();

        System.out.println("Test");

    }

    @Test
    void compositeTiO2AgTest() {
        FSphere particleTiO2 = factory.getFSphere(36).setMeta("TiO2");

        FSphereProducer particleAg = factory.getFSphereProducer()
                .withProdCenterAndDistRadius(
                        factory.getFPointProducer().withOnSphere(36),
                        factory.getFRand().getFDist1DNormal(2.5, 0.2))
                .addCorrection((candidate, rand) ->
                        factory.getRandAspect().attachLinear(candidate, particleTiO2))
                .validateNoOverlap()
                .setMeta("Ag");

        FAggregate composite = factory.getRefFAggregate(particleAg.getListFixed(250));
        composite.addRefParticle(particleTiO2);

        assertEquals(251, composite.size());
        assertTrue(composite.isPointConnected());

        String visual = factory.getSaveAspect().getComponentContext().toPovRay(composite, ExPovRay.FREE);

        assertFalse(visual.isEmpty());
    }


    @Test
    void aggregateMultimodalTest() {
        int size = 2500;

        FDist3D rangeA = factory.getFRand().getFDist3DUniform(factory.getFPairPos3D(-200, -100, -100, 200, 100, 100));
        FDist3D rangeB = factory.getFRand().getFDist3DNormal().setStd(50, 25, 25);

        FSphereProducer particles = factory.getFSphereProducer()
                .withDistCenterAndDistRadius(rangeA, factory.getFRand().getFDist1DNormal(1.0, 0.1), 90)
                .withDistCenterAndDistRadius(rangeB, factory.getFRand().getFDist1DNormal(5.0, 1), 10)
                .validateNoOverlap()
                .setRetriesInfinite();

        FAggregate geometry = factory.getRefFAggregate(particles.getListRandomized(size));

        assertEquals(size, geometry.size());
        assertTrue(geometry.isNonOverlapping());

        String visual = factory.getSaveAspect().getComponentContext().toPovRay(geometry, ExPovRay.BOUNDARY);

        assertFalse(visual.isEmpty());
    }
}
