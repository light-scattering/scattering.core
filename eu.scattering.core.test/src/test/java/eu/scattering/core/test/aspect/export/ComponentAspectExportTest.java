package eu.scattering.core.test.aspect.export;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelDLA;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.extension.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Component export")
public class ComponentAspectExportTest {

    @Test
    @DisplayName("Export FLAGE RLA 3D")
    void exportFLAGERLA3D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModel modelRLA = factory.createFModelRLA2D(fAggregate);

        modelRLA.build();

        String model = factory.getExportAspect().getComponentContext().toFLAGE(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("Type_Sphere"),
                        "The model doesn't contain required shapes")
        );
    }

    //--------------------------------------------------

    @Test
    @DisplayName("Export NGSolve RLA 3D")
    void exportNGSolveRLA3D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModel modelRLA = factory.createFModelRLA3D(fAggregate);

        modelRLA.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve RLA 2D")
    void exportNGSolveRLA2D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModel modelRLA = factory.createFModelRLA2D(fAggregate);

        modelRLA.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve PC ballistic 3D")
    void exportNGSolvePCBallistic3D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModel modelBallistic = factory.createFModelBallistic3D(fAggregate);

        modelBallistic.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve PC ballistic 2D")
    void exportNGSolvePCBallistic2D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModel modelBallistic = factory.createFModelBallistic2D(fAggregate);

        modelBallistic.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve PC tunable Filippov 3D")
    void exportNGSolvePCTunableFilippov3D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModelPCTunable modelTunable = factory.createFModelFilippov3D(fAggregate, 1.8, 1.4);
        modelTunable.setEarlyStageCorrection(true);

        modelTunable.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve PC tunable Filippov 2D")
    void exportNGSolvePCTunableFilippov2D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModelPCTunable modelTunable = factory.createFModelFilippov2D(fAggregate, 1.3, 1.5);
        modelTunable.setEarlyStageCorrection(true);

        modelTunable.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve DLA 3D")
    void exportNGSolveDLA3D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModelDLA modelDLA = factory.createFModelDLA3D(fAggregate);
        modelDLA.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export NGSolve DLA 2D")
    void exportNGSolveDLA2D() {
        int quantity = 10;

        Producer<FSphere> fProducer = factory.getFSphereProducer(1);
        FAssembly<Shape> fAssembly = factory.getFAssembly(fProducer.getListRandomized(quantity));
        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FModelDLA modelDLA = factory.createFModelDLA2D(fAggregate);
        modelDLA.build();

        String model = factory.getExportAspect().getComponentContext().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }
}
