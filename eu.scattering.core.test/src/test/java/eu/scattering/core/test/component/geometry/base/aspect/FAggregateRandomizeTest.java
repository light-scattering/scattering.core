package eu.scattering.core.test.component.geometry.base.aspect;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.shape.Shape;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FAggregateRandom")
public class FAggregateRandomizeTest {

    @Test
    @DisplayName("Project 3D")
    void project3D() {
        FAggregate aggA = factory.getFAggregatePreMono(25, 1);
        FAggregate aggB = factory.getFAggregatePreMono(25, 1);

        FModelPC modelA = factory.createFModelBallistic3D(aggA);
        FModelPC modelB = factory.createFModelBallistic3D(aggB);

        modelA.build();
        modelB.build();

        boolean results = factory.getRandAspect().project(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(results);
        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Project 2D")
    void project2D() {
        FAggregate aggA = factory.getFAggregatePreMono(25, 1);
        FAggregate aggB = factory.getFAggregatePreMono(25, 1);

        FModelPC modelA = factory.createFModelBallistic2D(aggA);
        FModelPC modelB = factory.createFModelBallistic2D(aggB);

        modelA.build();
        modelB.build();

        boolean results = factory.getRandAspect().project2D(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(results);
        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }
}
