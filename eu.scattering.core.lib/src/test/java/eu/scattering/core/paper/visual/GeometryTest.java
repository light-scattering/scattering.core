package eu.scattering.core.paper.visual;

import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3D;
import eu.scattering.core.design.aspect.randomize.distribution.dist3d.FRandDist3DFactory;
import eu.scattering.core.design.aspect.randomize.mutation.FRandMutation;
import eu.scattering.core.design.aspect.rotate.mutation.FRotMutate;
import eu.scattering.core.design.aspect.rotate.state.FRotState;
import eu.scattering.core.design.component.aggregate.FAggregate;
import org.junit.jupiter.api.*;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled
@DisplayName("Geometry")
public class GeometryTest {

    @Test
    void geometryManual() {
        FAggregate agg = factory.aggregates().templates().monodisperse(1000, 1);

        factory.models().cc().ballistic(agg).build();

        FRandDist3D xxx = factory.random().dist3D().normal();
        FRandDist3DFactory oi = factory.random().dist3D();



        FRandMutation x = factory.random().mutate();


        FRotState xt = factory.rotate().state().aroundAxis(factory.getFPos3D(null), 3);
        FRotMutate y = factory.rotate().mutate();


        System.out.println("ll");
    }
}
