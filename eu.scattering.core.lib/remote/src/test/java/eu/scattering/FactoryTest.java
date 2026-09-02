package eu.scattering;

import eu.scattering.core.design.ScatterFactory;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.impl.ScatterCore;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Consumer test")
public class FactoryTest {

    @Test
    @DisplayName("Create factory")
    void getFactory() {
        ScatterFactory factory = ScatterCore.createFactory();

        FAggregate fAggregate = factory.aggregates().geometries().grid3D(3, 4, 5);

        assertEquals(60, fAggregate.size());
    }
}
