package eu.scattering.core.test.aspect.export;

import eu.scattering.core.design.aspect.randomize.generator.module.dist1d.FDist1D;
import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.aggregate.model.pc.dla.FModelPCDLA;
import eu.scattering.core.design.component.aggregate.model.pc.tunable.FModelPCTunable;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.design.utility.type.preset.ExBasic;
import eu.scattering.core.design.utility.type.preset.ExPovRay;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        FModel modelRLA = factory.models().pc().rla(fAggregate);

        modelRLA.build();

        String model = factory.save().components().toFLAGE(fAggregate);
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
    @DisplayName("Export JSON")
    void exportJSON() {
        int quantity = 10;

        FAggregate fAggregate = factory.aggregates().templates().polydisperse(quantity, 10, 1);

        FModel modelRLA = factory.models().pc().rla(fAggregate);

        modelRLA.build();

        String model = factory.save().components().toJSON(fAggregate);
        String[] modelSplit = model.split("\n");

        FAggregate results = factory.load().aggregates().fromJSON(model);

        Assertions.assertAll("Validate model",
                () -> assertEquals(1, modelSplit.length,
                        "The number of lines is incorrect"),
                () -> assertTrue(fAggregate.isExact(results))
        );
    }

    //--------------------------------------------------

    @Test
    @DisplayName("Export basic - Multisphere")
    void exportBasicMultisphere() {
        int quantity = 10;

        FAggregate fAggregate = factory.aggregates().templates().polydisperse(quantity, 10, 1);

        FModel modelRLA = factory.models().pc().rla(fAggregate);

        modelRLA.build();

        String model = factory.save().components().toBasic(fAggregate, ExBasic.MULTISPHERE);
        String[] modelSplit = model.split("\n");

        FAggregate results = factory.load().aggregates().fromBasic(model, ExBasic.MULTISPHERE);

        Assertions.assertAll("Validate model",
                () -> assertEquals(quantity, modelSplit.length,
                        "The number of lines is incorrect"),
                () -> assertTrue(fAggregate.isExact(results))
        );
    }

    //--------------------------------------------------

    @Test
    @DisplayName("Export PovRay RLCA 3D - Plain")
    void exportPovRayRLCA3DPlain() {
        int quantity = 100;

        FDist1D setDist1 = factory.random().generator().getFDist1DNormal(5, 0.5);
        FSphereProducer set1 = factory.getFSphereProducer(setDist1).setMeta("Tag 1");

        FDist1D setDist2 = factory.random().generator().getFDist1DNormal(10, 1);
        FSphereProducer set2 = factory.getFSphereProducer(setDist2).setMeta("Tag 2");

        FAssembly<Shape> core = factory.getFAssembly();

        core.register(set1.getListRandomized((int) (0.75 * quantity)));
        core.register(set2.getListRandomized((int) (0.25 * quantity)));

        FAggregate fAggregate = factory.getRefFAggregate(core);

        FModel fModel = factory.models().cc().rlca(fAggregate);

        fModel.build();

        String model = factory.save().components().toPovRay(fAggregate, ExPovRay.BOUNDARY);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("sphere"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export PovRay RLCA 3D - Box")
    void exportPovRayRLCA3DBox() {
        int quantity = 100;

        FDist1D setDist1 = factory.random().generator().getFDist1DNormal(5, 0.5);
        FSphereProducer set1 = factory.getFSphereProducer(setDist1).setMeta("Tag 1");

        FDist1D setDist2 = factory.random().generator().getFDist1DNormal(10, 1);
        FSphereProducer set2 = factory.getFSphereProducer(setDist2).setMeta("Tag 2");

        FAssembly<Shape> core = factory.getFAssembly();

        core.register(set1.getListRandomized((int) (0.75 * quantity)));
        core.register(set2.getListRandomized((int) (0.25 * quantity)));

        FAggregate fAggregate = factory.getRefFAggregate(core);

        FModel fModel = factory.models().cc().rlca(fAggregate);

        fModel.build();

        String model = factory.save().components().toPovRay(fAggregate, ExPovRay.BOUNDARY);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("sphere"),
                        "The model doesn't contain required shapes")
        );
    }

    @Test
    @DisplayName("Export PovRay RLCA 3D - Radius")
    void exportPovRayRLCA3DRadius() {
        int quantity = 100;

        FDist1D setDist1 = factory.random().generator().getFDist1DNormal(5, 0.5);
        FSphereProducer set1 = factory.getFSphereProducer(setDist1).setMeta("Tag 1");

        FDist1D setDist2 = factory.random().generator().getFDist1DNormal(10, 1);
        FSphereProducer set2 = factory.getFSphereProducer(setDist2).setMeta("Tag 2");

        FAssembly<Shape> core = factory.getFAssembly();

        core.register(set1.getListRandomized((int) (0.75 * quantity)));
        core.register(set2.getListRandomized((int) (0.25 * quantity)));

        FAggregate fAggregate = factory.getRefFAggregate(core);

        FModel fModel = factory.models().cc().rlca(fAggregate);

        fModel.build();

        String model = factory.save().components().toPovRay(fAggregate, ExPovRay.RADIUS);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("sphere"),
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

        FModel modelRLA = factory.models().pc().rla(fAggregate);

        modelRLA.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModel modelRLA = factory.models().pc().rla(Dimension.D2, fAggregate);

        modelRLA.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModel modelBallistic = factory.models().pc().ballistic(fAggregate);

        modelBallistic.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModel modelBallistic = factory.models().pc().ballistic(Dimension.D2, fAggregate);

        modelBallistic.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModelPCTunable modelTunable = factory.models().pc().tunable(fAggregate, 1.8, 1.4);
        modelTunable.setEarlyStageCorrection(true);

        modelTunable.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModelPCTunable modelTunable = factory.models().pc().tunable(Dimension.D2, fAggregate, 1.3, 1.5);
        modelTunable.setEarlyStageCorrection(true);

        modelTunable.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModelPCDLA modelDLA = factory.models().pc().dla(fAggregate);
        modelDLA.build();

        String model = factory.save().components().toNGSolve(fAggregate);
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

        FModelPCDLA modelDLA = factory.models().pc().dla(Dimension.D2, fAggregate);
        modelDLA.build();

        String model = factory.save().components().toNGSolve(fAggregate);
        String[] modelSplit = model.split("\n");

        Assertions.assertAll("Validate model",
                () -> assertTrue(modelSplit.length > quantity,
                        "The number of lines is incorrect"),
                () -> assertTrue(model.contains("particle_0"),
                        "The model doesn't contain required shapes")
        );
    }
}
