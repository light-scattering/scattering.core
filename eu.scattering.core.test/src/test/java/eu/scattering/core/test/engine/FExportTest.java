package eu.scattering.core.test.engine;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.util.support.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FExport")
public class FExportTest {

    @Test
    @DisplayName("Export FLAGE")
    void exportFLAGE() {
        int quantity = 25;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly, 0);

        FModel modelRLA = factory.createFModelRLA2D(fAggregate);

        modelRLA.build();

        StringBuilder builder = new StringBuilder();
        factory.getFExportEngine().exportFLAGE(fAggregate, builder);

        String model = builder.toString();
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("Type_Sphere"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve")
    void exportNGSolve() {
        int quantity = 25;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly, 0);

        FModel modelRLA = factory.createFModelRLA2D(fAggregate);

        modelRLA.build();

        StringBuilder builder = new StringBuilder();
        factory.getFExportEngine().exportNGSolve(fAggregate, builder);

        String model = builder.toString();
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }
}
