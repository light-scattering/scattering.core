package eu.scattering.core.paper.visual;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Geometry")
public class GeometryTest {

    @Test
    void compositeTiO2AgTest() {
        FSphere particleTiO2 = factory.getFSphere(36);
        particleTiO2.setMeta("Tio2");

        FSphereProducer particleAg = factory.getFSphereProducer()
                .withProdCenterAndDistRadius(
                        factory.getFPointProducer().withOnSphere(36),
                        factory.getFRand().getFDist1DNormal(2.5, 0.2))
                .addCorrection((candidate, rand) ->
                        factory.getRandAspect().attachLinear(candidate, particleTiO2))
                .addMutation((cluster) ->
                        cluster.forEach(p -> p.setMeta("Ag")))
                .validateNoOverlap();

        FAggregate composite = factory.getRefFAggregate(particleAg.getListFixed(250));
        composite.addRefParticle(particleTiO2);

        assertEquals(251, composite.size());
        assertTrue(composite.isPointConnected());

        String visual = factory.getSaveAspect().getComponentContext().toPovRay(composite, ExPovRay.BOUNDARY);

        assertFalse(visual.isEmpty());
    }

    @Test
    void aggregateMultimodalTest() {
        FPointProducer area = factory.getFPointProducer().withInSphere(100);

        FSphereProducer particles = factory.getFSphereProducer()
                .withProdCenterAndDistRadius(area, factory.getFRand().getFDist1DNormal(1.0, 0.1), 60)
                .withProdCenterAndDistRadius(area, factory.getFRand().getFDist1DNormal(5.0, 0.5), 30)
                .withProdCenterAndDistRadius(area, factory.getFRand().getFDist1DNormal(20, 2), 10)
                .validateNoOverlap();

        FAggregate geometry = factory.getRefFAggregate(particles.getListRandomized(250));

        assertEquals(250, geometry.size());
        assertTrue(geometry.isNonOverlapping());

        String visual = factory.getSaveAspect().getComponentContext().toPovRay(geometry, ExPovRay.FREE);

        assertFalse(visual.isEmpty());
    }
}
