package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.FModel;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.functionality.Producer;
import eu.scattering.core.design.mathematics.helper.FTrigHelper;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.plot.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.buffer.transfer.variant.FBufferData;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.storage.transfer.matrix.variant.FMatrix3x3D;
import eu.scattering.core.design.storage.transfer.position.p2.variant.FPairPos3D;
import eu.scattering.core.design.storage.transfer.position.p1.variant.FPos3D;
import eu.scattering.core.design.utility.type.method.*;
import eu.scattering.core.design.utility.type.option.Length;
import eu.scattering.core.design.utility.type.option.Location;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;
import eu.scattering.core.predefined.aggregate.F3D_N1000_Mono;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FAggregate")
public class FAggregateTest {

    @Nested
    @Tag("Basic")
    @DisplayName("Functionality")
    class FAggregateBasicTest {

        @Test
        @DisplayName("Construct")
        void construct() {
            FAggregate fAggregate = factory.getFAggregate();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(0, fAggregate.size()),
                    () -> assertNull(fAggregate.getRefFExtension().getRefFBuffer()),
                    () -> assertNull(fAggregate.getRefFExtension().getRefFMaterial())
            );
        }

        @Test
        @DisplayName("Construct with reference particles")
        void constructWithReferenceParticles() {
            FAssembly<Shape> fAssembly = factory.getFAssembly();

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertSame(fAssembly, fAggregate.getRefParticles()),
                    () -> assertEquals(fAssembly.size(), fAggregate.size()),
                    () -> assertNull(fAggregate.getRefFExtension().getRefFBuffer()),
                    () -> assertNull(fAggregate.getRefFExtension().getRefFMaterial())
            );
        }

        @Test
        @DisplayName("Get reference particles")
        void getReferenceParticles() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssemblyA);

            FAssembly<Shape> fAssemblyB = fAggregate.getRefParticles();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertSame(fAssemblyA, fAssemblyB,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Add FBuffer")
        void addFBuffer() {
            FAggregate fAggregateA = factory.getFAggregate();

            assertNull(fAggregateA.getRefFExtension().getRefFBuffer());

            FAggregate fAggregateB = fAggregateA.addFBuffer(100);

            assertSame(fAggregateA, fAggregateB);
            assertEquals(100, fAggregateA.getRefFExtension().getRefFBuffer().capacity());
            assertEquals(0, fAggregateA.getRefFExtension().getRefFBuffer().size());
        }

        @Test
        @DisplayName("Validate FBuffer")
        void validateFBuffer() {
            FBuffer<FBufferData> fBufferA = factory.getFBuffer(123);

            FAggregate fAggregateA = factory.getFAggregate();

            FAggregate fAggregateB = fAggregateA.setRefFBuffer(fBufferA);

            FBuffer<FBufferData> fBufferB = fAggregateA.getRefFExtension().getRefFBuffer();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertSame(fAggregateA, fAggregateB,
                            "The reference should not change"),
                    () -> assertSame(fBufferA, fBufferB,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Add FMaterial")
        void addFMaterial() {
            FAggregate fAggregateA = factory.getFAggregate();

            assertNull(fAggregateA.getRefFExtension().getRefFMaterial());

            FAggregate fAggregateB = fAggregateA.addFMaterial();

            fAggregateA.getRefFExtension().getRefFMaterial().setDensity("X", 1);

            assertSame(fAggregateA, fAggregateB);
            assertEquals(2, fAggregateA.getRefFExtension().getRefFMaterial().size());
        }

        @Test
        @DisplayName("Validate FMaterial")
        void validateFMaterial() {
            FMaterial fMaterialA = factory.getFMaterial();

            FAggregate fAggregateA = factory.getFAggregate();

            FAggregate fAggregateB = fAggregateA.setRefFMaterial(fMaterialA);

            FMaterial fMaterialB = fAggregateA.getRefFExtension().getRefFMaterial();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertSame(fAggregateA, fAggregateB,
                            "The reference should not change"),
                    () -> assertSame(fMaterialA, fMaterialB,
                            "The reference should not change")
            );
        }
    }

    @Nested
    @Tag("Core")
    @DisplayName("Core features")
    class FAggregateCoreTest {

        @Test
        @DisplayName("Parse JSON - A")
        void parseJSONA() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(10, 1);

            JSONObject json = fAggregate.toJSON();

            FAggregate fAggregateCopy = factory.getFAggregate(json);

            assertTrue(fAggregate.isExactData(fAggregateCopy));
            assertTrue(fAggregateCopy.isExactData(fAggregate));
            assertTrue(fAggregate.isExact(fAggregateCopy));
            assertTrue(fAggregateCopy.isExact(fAggregate));
        }

        @Test
        @DisplayName("Parse JSON - B")
        void parseJSONB() {
            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(10, 1).addFBuffer(10).addFMaterial();
            fAggregate.getRefFExtension().getRefFMaterial().setDensity("A", 3);
            fAggregate.getRefFExtension().getRefFMaterial().setDensity("B", 6);
            fAggregate.getRefFExtension().getRefFMaterial().setRefIndexRe("C", 3);
            fAggregate.getRefFExtension().getRefFMaterial().setRefIndexIm("D", 6);
            fAggregate.getRefFExtension().getRefFMaterial().setRefIndex("E", 4, 8);

            JSONObject json = fAggregate.toJSON();

            FAggregate fAggregateCopy = factory.getFAggregate(json);

            assertTrue(fAggregate.isExactData(fAggregateCopy));
            assertTrue(fAggregateCopy.isExactData(fAggregate));
            assertTrue(fAggregate.isExact(fAggregateCopy));
            assertTrue(fAggregateCopy.isExact(fAggregate));
        }

        @Test
        @DisplayName("Exactness")
        void isExact() {
            Shape fSphereAA = factory.getFSphere(0, 0, 0, 1);
            Shape fSphereAB = factory.getFSphere(2, 0, 0, 1);
            Shape fSphereAC = factory.getFSphere(0, 2, 0, 1);
            Shape fSphereAD = factory.getFSphere(0, 0, 2, 1);

            Shape fSphereBA = fSphereAA.copy();
            Shape fSphereBB = fSphereAB.copy();
            Shape fSphereBC = fSphereAC.copy();
            Shape fSphereBD = fSphereAD.copy();

            FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereAA, fSphereAB, fSphereAC, fSphereAD));
            FAssembly<Shape> fAssemblyB = factory.getFAssembly(List.of(fSphereBA, fSphereBB, fSphereBC, fSphereBD));

            FAggregate fAggregateA = factory.getRefFAggregate(fAssemblyA).addFBuffer(10).addFMaterial();
            FAggregate fAggregateB = factory.getRefFAggregate(fAssemblyB).addFBuffer(10).addFMaterial();

            fAggregateA.getRefFExtension().getRefFMaterial().setDensity("X", 5);
            fAggregateB.getRefFExtension().getRefFMaterial().setDensity("X", 5);

            assertTrue(fAggregateA.isExact(fAggregateB));
            assertTrue(fAggregateB.isExact(fAggregateA));
            assertTrue(fAggregateA.isExactData(fAggregateB));
            assertTrue(fAggregateB.isExactData(fAggregateA));

            fAggregateB.getRefFExtension().getRefFMaterial().setDensity("X", 6);

            assertFalse(fAggregateA.isExact(fAggregateB));
            assertFalse(fAggregateB.isExact(fAggregateA));
            assertTrue(fAggregateA.isExactData(fAggregateB));
            assertTrue(fAggregateB.isExactData(fAggregateA));

            fAggregateB.getRefFExtension().getRefFMaterial().setDensity("X", 5);
            fAggregateB.getRefParticles().asList().getFirst().setRadius(2);

            assertFalse(fAggregateA.isExact(fAggregateB));
            assertFalse(fAggregateB.isExact(fAggregateA));
            assertFalse(fAggregateA.isExactData(fAggregateB));
            assertFalse(fAggregateB.isExactData(fAggregateA));
        }

        @Test
        @DisplayName("Copy - Deep")
        void copyDeep() {
            Shape fSphereAA = factory.getFSphere(0, 0, 0, 1);
            Shape fSphereAB = factory.getFSphere(2, 0, 0, 1);
            Shape fSphereAC = factory.getFSphere(0, 2, 0, 1);
            Shape fSphereAD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereAA, fSphereAB, fSphereAC, fSphereAD));

            FAggregate fAggregateA = factory.getRefFAggregate(fAssemblyA).addFBuffer(10).addFMaterial();

            fAggregateA.getRefFExtension().getRefFMaterial().setDensity("X", 5);

            FAggregate fAggregateB = fAggregateA.copy(true);

            assertNotSame(fAggregateA, fAggregateB);
            assertTrue(fAggregateA.isExact(fAggregateB));
        }

        @Test
        @DisplayName("Copy - Shallow")
        void copyShallow() {
            Shape fSphereAA = factory.getFSphere(0, 0, 0, 1);
            Shape fSphereAB = factory.getFSphere(2, 0, 0, 1);
            Shape fSphereAC = factory.getFSphere(0, 2, 0, 1);
            Shape fSphereAD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereAA, fSphereAB, fSphereAC, fSphereAD));

            FAggregate fAggregateA = factory.getRefFAggregate(fAssemblyA).addFBuffer(10).addFMaterial();

            fAggregateA.getRefFExtension().getRefFMaterial().setDensity("X", 5);

            FAggregate fAggregateB = fAggregateA.copy(false);

            assertNotSame(fAggregateA, fAggregateB);
            assertFalse(fAggregateA.isExact(fAggregateB));
            assertTrue(fAggregateA.isExactData(fAggregateB));
        }
    }

    @Nested
    @Tag("Advanced")
    @DisplayName("Functionality - Advanced")
    class FAggregateAdvancedTest {

        @Test
        @DisplayName("Get volume, point contact A")
        void getVolumePointContactA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume(Volume.ADAPTIVE);
            double volExpected = 4 * (4  * Math.PI / 3);

            double relError = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume, point contact B")
        void getVolumePointContactB() {
            Producer<FPoint> fPointProd = factory.getFPointProducer(20, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1).validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(50));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume(Volume.ADAPTIVE);
            double volExpected = 50 * (4  * Math.PI / 3);

            double relError = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume, overlap (full) - monodisperse")
        void getVolumeOverlapFullMonodisperse() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume(Volume.ADAPTIVE);
            double expected = 1 * (4  * Math.PI / 3);

            double relError = factory.getStatisticsHelper().getRelErr(expected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume, overlap (full) - polydisperse A")
        void getVolumeOverlapFullPolydisperseA() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1).setDelta(0.1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 2).setDelta(0.1);
            Shape fSphereC = factory.getFSphere(0, 0, 0, 3).setDelta(0.1);
            Shape fSphereD = factory.getFSphere(0, 0, 0, 4).setDelta(0.1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume(Volume.ADAPTIVE);
            double volExpected = 1 * (4  * Math.PI * Math.pow(4, 3) / 3);

            double relError = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume, overlap (full) - polydisperse B")
        void getVolumeOverlapFullPolydisperseB() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 4).setDelta(0.1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 3).setDelta(0.1);
            Shape fSphereC = factory.getFSphere(0, 0, 0, 2).setDelta(0.1);
            Shape fSphereD = factory.getFSphere(0, 0, 0, 1).setDelta(0.1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume(Volume.ADAPTIVE);
            double volExpected = 1 * (4  * Math.PI * Math.pow(4, 3) / 3);

            double relError = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume, overlap (mixed)")
        void getVolumeOverlapMixed() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1).setDelta(0.1);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1).setDelta(0.1);
            Shape fSphereC = factory.getFSphere(0, 5, 0, 1).setDelta(0.1);
            Shape fSphereD = factory.getFSphere(0, 0, 5, 1).setDelta(0.1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume(Volume.ADAPTIVE);
            double volExpected = 4 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double relError = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume layer, point contact A")
        void getVolumeLayerPointContactA() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(6, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 6, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 6, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double[] volLayers = new double[3];
            double volActual = fAggregate.getVolume(volLayers, Volume.ADAPTIVE);

            double volExpectedLayer0 = 4 * fSphereA.getLayerVolume(0);
            double volExpectedLayer1 = 4 * fSphereA.getLayerVolume(1);
            double volExpectedLayer2 = 4 * fSphereA.getLayerVolume(2);
            double volExpected = 4 * fSphereA.getVolumeAlgebraic();

            double volErrLayer0 = factory.getStatisticsHelper().getRelErr(volExpectedLayer0, volLayers[0]);
            double volErrLayer1 = factory.getStatisticsHelper().getRelErr(volExpectedLayer1, volLayers[1]);
            double volErrLayer2 = factory.getStatisticsHelper().getRelErr(volExpectedLayer2, volLayers[2]);
            double volErr = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(volErrLayer0 < 0.01,
                            "Layer 0 volume is erroneous"),
                    () -> assertTrue(volErrLayer1 < 0.01,
                            "Layer 1 volume is erroneous"),
                    () -> assertTrue(volErrLayer2 < 0.01,
                            "Layer 2 volume is erroneous"),
                    () -> assertTrue(volErr < 0.01,
                            "The total volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume layer, point contact B")
        void getVolumeLayerPointContactB() {
            double delta = 0.1;
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .setDelta(delta)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphere fSphereRef = fSphereProd.produce();

            double[] volLayers = new double[3];
            double volActual = fAggregate.getVolume(volLayers, Volume.ADAPTIVE);

            double volExpectedLayer0 = quantity * fSphereRef.getLayerVolume(0);
            double volExpectedLayer1 = quantity * fSphereRef.getLayerVolume(1);
            double volExpectedLayer2 = quantity * fSphereRef.getLayerVolume(2);
            double volExpected = quantity * fSphereRef.getVolumeAlgebraic();

            double volErrLayer0 = factory.getStatisticsHelper().getRelErr(volExpectedLayer0, volLayers[0]);
            double volErrLayer1 = factory.getStatisticsHelper().getRelErr(volExpectedLayer1, volLayers[1]);
            double volErrLayer2 = factory.getStatisticsHelper().getRelErr(volExpectedLayer2, volLayers[2]);
            double volErr = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(volErrLayer0 < 0.01,
                            "Layer 0 volume is erroneous"),
                    () -> assertTrue(volErrLayer1 < 0.01,
                            "Layer 1 volume is erroneous"),
                    () -> assertTrue(volErrLayer2 < 0.01,
                            "Layer 2 volume is erroneous"),
                    () -> assertTrue(volErr < 0.01,
                            "The total volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume layer, overlap (full) - monodisperse")
        void getVolumeLayerOverlapFullMonodisperse() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double[] volLayers = new double[3];
            double volActual = fAggregate.getVolume(volLayers, Volume.ADAPTIVE);

            double volExpectedLayer0 = fSphereA.getLayerVolume(0);
            double volExpectedLayer1 = fSphereA.getLayerVolume(1);
            double volExpectedLayer2 = fSphereA.getLayerVolume(2);
            double volExpected = fSphereA.getVolumeAlgebraic();

            double volErrLayer0 = factory.getStatisticsHelper().getRelErr(volExpectedLayer0, volLayers[0]);
            double volErrLayer1 = factory.getStatisticsHelper().getRelErr(volExpectedLayer1, volLayers[1]);
            double volErrLayer2 = factory.getStatisticsHelper().getRelErr(volExpectedLayer2, volLayers[2]);
            double volErr = factory.getStatisticsHelper().getRelErr(volExpected, volActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(volErrLayer0 < 0.01,
                            "Layer 0 volume is erroneous"),
                    () -> assertTrue(volErrLayer1 < 0.01,
                            "Layer 1 volume is erroneous"),
                    () -> assertTrue(volErrLayer2 < 0.01,
                            "Layer 2 volume is erroneous"),
                    () -> assertTrue(volErr < 0.01,
                            "The total volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume layer, overlap (mixed)")
        void getVolumeLayerOverlapMixed() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 5, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 5, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double[] volLayers = new double[4];
            double volActualA = fAggregate.getVolume(volLayers, Volume.ADAPTIVE);
            double volActualB = fAggregate.getVolume(Volume.ADAPTIVE);

            double volExpectedLayer0 = 4 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double volErrLayer0 = factory.getStatisticsHelper().getRelErr(volExpectedLayer0, volLayers[0]);
            double volErr = factory.getStatisticsHelper().getRelErr(volActualB, volActualA);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(volErrLayer0 < 0.01,
                            "Layer 0 volume is erroneous"),
                    () -> assertTrue(volErr < 0.01,
                            "The total volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get volume array, overlap (mixed)")
        void getVolumeArrayOverlapMixed() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 5, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 5, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFBuffer(1_000_000);

            FMesh<FBufferData> fArray = fAggregate.getVolumeMesh();

            FLayer fLayer = factory.getFLayer();

            fArray.forEach((index, d0, d1, d2, data) -> fLayer.inc(data.getLayerIndex()));

            double volActualLayer0 = fLayer.get(0) * 0.001;
            double volActualA = fLayer.addSelf() * 0.001;
            double volActualB = fAggregate.getVolume(Volume.ADAPTIVE);

            double volExpectedLayer0 = 4 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double volErrLayer0 = factory.getStatisticsHelper().getRelErr(volExpectedLayer0, volActualLayer0);
            double volErr = factory.getStatisticsHelper().getRelErr(volActualA, volActualB);

            double duplicates = fArray.deduplicate();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(0, duplicates,
                            "The number of duplicates is erroneous"),
                    () -> assertTrue(volErrLayer0 < 0.01,
                            "Layer 0 volume is erroneous"),
                    () -> assertTrue(volErr < 0.01,
                            "The total volume is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface, point contact A")
        void getSurfacePointContactA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfActual = fAggregate.getSurface(Surface.ADAPTIVE);
            double srfExpected = 4 * (4  * Math.PI);

            double relError = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface, point contact B")
        void getSurfacePointContactB() {
            Producer<FPoint> fPointProd = factory.getFPointProducer(20, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1).validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(50));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfActual = fAggregate.getSurface(Surface.ADAPTIVE);
            double srfExpected = 50 * (4  * Math.PI);

            double relError = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface, overlap (full) - monodisperse")
        void getSurfaceOverlapFullMonodisperse() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfActual = fAggregate.getSurface(Surface.ADAPTIVE);
            double srfExpected = 1 * (4  * Math.PI);

            double relError = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface, overlap (full) - polydisperse A")
        void getSurfaceOverlapFullPolydisperseA() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 1).setDelta(0.1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 2).setDelta(0.1);
            Shape fSphereC = factory.getFSphere(0, 0, 0, 3).setDelta(0.1);
            Shape fSphereD = factory.getFSphere(0, 0, 0, 4).setDelta(0.1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfActual = fAggregate.getSurface(Surface.ADAPTIVE);
            double srfExpected = 1 * (4  * Math.PI * Math.pow(4, 2));

            double relError = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface, overlap (full) - polydisperse B")
        void getSurfaceOverlapFullPolydisperseB() {
            Shape fSphereA = factory.getFSphere(0, 0, 0, 4).setDelta(0.1);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 3).setDelta(0.1);
            Shape fSphereC = factory.getFSphere(0, 0, 0, 2).setDelta(0.1);
            Shape fSphereD = factory.getFSphere(0, 0, 0, 1).setDelta(0.1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfActual = fAggregate.getSurface(Surface.ADAPTIVE);
            double srfExpected = 1 * (4  * Math.PI * Math.pow(4, 2));

            double relError = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(relError < 0.01,
                            "The surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface layer, point contact A")
        void getSurfaceLayerPointContactA() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(6, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 6, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 6, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double[] srfLayers = new double[3];
            double srfActual = fAggregate.getSurface(srfLayers, Surface.ADAPTIVE);

            double srfExpectedLayer0 = 4 * fSphereA.getLayerSurface(0);
            double srfExpectedLayer1 = 4 * fSphereA.getLayerSurface(1);
            double srfExpectedLayer2 = 4 * fSphereA.getLayerSurface(2);
            double srfExpected = srfExpectedLayer0 + srfExpectedLayer1 + srfExpectedLayer2;

            double srfErrLayer0 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
            double srfErrLayer1 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer1, srfLayers[1]);
            double srfErrLayer2 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer2, srfLayers[2]);
            double srfErr = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(srfErrLayer0 < 0.01,
                            "Layer 0 surface is erroneous"),
                    () -> assertTrue(srfErrLayer1 < 0.01,
                            "Layer 1 surface is erroneous"),
                    () -> assertTrue(srfErrLayer2 < 0.01,
                            "Layer 2 surface is erroneous"),
                    () -> assertTrue(srfErr < 0.01,
                            "The total surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface layer, point contact B")
        void getSurfaceLayerPointContactB() {
            double delta = 0.1;
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .setDelta(delta)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphere fSphereRef = fSphereProd.produce();

            double[] srfLayers = new double[3];
            double srfActual = fAggregate.getSurface(srfLayers, Surface.ADAPTIVE);

            double srfExpectedLayer0 = quantity * fSphereRef.getLayerSurface(0);
            double srfExpectedLayer1 = quantity * fSphereRef.getLayerSurface(1);
            double srfExpectedLayer2 = quantity * fSphereRef.getLayerSurface(2);
            double srfExpected = srfExpectedLayer0 + srfExpectedLayer1 + srfExpectedLayer2;

            double srfErrLayer0 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
            double srfErrLayer1 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer1, srfLayers[1]);
            double srfErrLayer2 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer2, srfLayers[2]);
            double srfErr = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(srfErrLayer0 < 0.01,
                            "Layer 0 surface is erroneous"),
                    () -> assertTrue(srfErrLayer1 < 0.01,
                            "Layer 1 surface is erroneous"),
                    () -> assertTrue(srfErrLayer2 < 0.01,
                            "Layer 2 surface is erroneous"),
                    () -> assertTrue(srfErr < 0.01,
                            "The total surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface layer, overlap (full) - monodisperse")
        void getSurfaceLayerOverlapFullMonodisperse() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double[] srfLayers = new double[3];
            double srfActual = fAggregate.getSurface(srfLayers, Surface.ADAPTIVE);

            double srfExpectedLayer0 = fSphereA.getLayerSurface(0);
            double srfExpectedLayer1 = fSphereA.getLayerSurface(1);
            double srfExpectedLayer2 = fSphereA.getLayerSurface(2);
            double srfExpected = srfExpectedLayer0 + srfExpectedLayer1 + srfExpectedLayer2;

            double srfErrLayer0 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
            double srfErrLayer1 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer1, srfLayers[1]);
            double srfErrLayer2 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer2, srfLayers[2]);
            double srfErrTotal = factory.getStatisticsHelper().getRelErr(srfExpected, srfActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(srfErrLayer0 < 0.01,
                            "Layer 0 surface is erroneous"),
                    () -> assertTrue(srfErrLayer1 < 0.01,
                            "Layer 1 surface is erroneous"),
                    () -> assertTrue(srfErrLayer2 < 0.01,
                            "Layer 2 surface is erroneous"),
                    () -> assertTrue(srfErrTotal < 0.01,
                            "The total surface is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface layer, overlap (mixed)")
        void getSurfaceLayerOverlapMixed() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(1, 0, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(0, 5, 0, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);
            Shape fSphereD = factory.getFSphere(0, 0, 5, 1)
                    .addCoat(1, 1, 1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double[] srfLayers = new double[4];
            fAggregate.getSurface(srfLayers, Surface.ADAPTIVE);

            double srfActualLayer3 = fAggregate.getSurface(Surface.ADAPTIVE);
            double srfExpectedLayer0 = 2 * factory.getFSphereHelper().getSurface(1) +
                    factory.getFSphereHelper().getSurface(factory.getFPos3D(0, 0, 0), factory.getFPos3D(1, 0, 0), 1, 1);

            double srfErrLayer0 = factory.getStatisticsHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
            double srfErrLayer3 = factory.getStatisticsHelper().getRelErr(srfActualLayer3, srfLayers[3]);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(srfErrLayer0 < 0.01,
                            "Layer 0 volume is erroneous"),
                    () -> assertTrue(srfErrLayer3 < 0.01,
                            "Layer 0 volume is erroneous")
            );
        }

        @Test
        @DisplayName("Overlaps")
        void overlaps() {
            Shape shapeA1 = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeA2 = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA3 = factory.getFSphere(2, 0, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(2.5, 0.5, 0.5, 1);
            Shape shapeB2 = factory.getFSphere(4.5, 0.5, 0.5, 1);
            Shape shapeB3 = factory.getFSphere(6.5, 0.5, 0.5, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            assertTrue(aggregateA.overlaps(aggregateB));
        }

        @Test
        @DisplayName("Overlaps (fail)")
        void overlapsFail() {
            Shape shapeA1 = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeA2 = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA3 = factory.getFSphere(2, 0, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(4, -2, 0, 1);
            Shape shapeB2 = factory.getFSphere(4, 0, 0, 1);
            Shape shapeB3 = factory.getFSphere(4, 2, 0, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            assertFalse(aggregateA.overlaps(aggregateB));
        }

        @Test
        @DisplayName("Overlaps with shift")
        void overlapsWithShift() {
            Shape shapeA1 = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeA2 = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA3 = factory.getFSphere(2, 0, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(2.5, 4.5, -3.5, 1);
            Shape shapeB2 = factory.getFSphere(4.5, 4.5, -3.5, 1);
            Shape shapeB3 = factory.getFSphere(6.5, 4.5, -3.5, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            assertFalse(aggregateA.overlaps(aggregateB));

            assertFalse(aggregateA.overlapsWithShift(aggregateB, factory.getFVector()));
            assertTrue(aggregateA.overlapsWithShift(aggregateB, factory.getFVector(0, 4, -4)));
        }

        @Test
        @DisplayName("Overlaps with rotation")
        void overlapsWithRotation() {
            Shape shapeA1 = factory.getFSphere(-6, 0, 0, 1);
            Shape shapeA2 = factory.getFSphere(-4, 0, 0, 1);
            Shape shapeA3 = factory.getFSphere(-2, 0, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB2 = factory.getFSphere(4, 0, 0, 1);
            Shape shapeB3 = factory.getFSphere(6, 0, 0, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            assertFalse(aggregateA.overlaps(aggregateB));

            assertFalse(aggregateA.overlapsWithRotation(aggregateB, factory.getFVector(0, 1, 0), 0));
            assertFalse(aggregateA.overlapsWithRotation(aggregateB, factory.getFVector(0, 0, 1), 0));
            assertTrue(aggregateA.overlapsWithRotation(aggregateB, factory.getFVector(0, 1, 0), Math.PI));
            assertTrue(aggregateA.overlapsWithRotation(aggregateB, factory.getFVector(0, 0, 1), Math.PI));
            assertTrue(aggregateA.overlapsWithRotation(aggregateB, factory.getFVector(-2, 0, 0, -2, 1, 0), Math.PI));
            assertTrue(aggregateA.overlapsWithRotation(aggregateB, factory.getFVector(-2, 0, 0, -2, 0, 1), Math.PI));
        }

        @Test
        @DisplayName("Touches")
        void touches() {
            FAggregate aggregateA = factory.getFAggregate();
            aggregateA.addRefParticle(factory.getFSphere(-2, 0, 0, 1));
            aggregateA.addRefParticle(factory.getFSphere(0, 0, 0, 1));
            aggregateA.addRefParticle(factory.getFSphere(2, 0, 0, 1));

            FAggregate aggregateB = factory.getFAggregate();
            aggregateB.addRefParticle(factory.getFSphere(4, -2, 0, 1));
            aggregateB.addRefParticle(factory.getFSphere(4, 0, 0, 1));
            aggregateB.addRefParticle(factory.getFSphere(4, 2, 0, 1));

            assertTrue(aggregateA.touches(aggregateB));
        }

        @Test
        @DisplayName("Touches (fail) - A")
        void touchesFailA() {
            FAggregate aggregateA = factory.getFAggregate();
            aggregateA.addRefParticle(factory.getFSphere(-2, 0, 0, 1));
            aggregateA.addRefParticle(factory.getFSphere(0, 0, 0, 1));
            aggregateA.addRefParticle(factory.getFSphere(2, 0, 0, 1));

            FAggregate aggregateB = factory.getFAggregate();
            aggregateB.addRefParticle(factory.getFSphere(3.9, -2, 0, 1));
            aggregateB.addRefParticle(factory.getFSphere(3.9, 0, 0, 1));
            aggregateB.addRefParticle(factory.getFSphere(3.9, 2, 0, 1));

            assertFalse(aggregateA.touches(aggregateB));
        }

        @Test
        @DisplayName("Touches (fail) - B")
        void touchesFailB() {
            FAggregate aggregateA = factory.getFAggregate();
            aggregateA.addRefParticle(factory.getFSphere(-2, 0, 0, 1));
            aggregateA.addRefParticle(factory.getFSphere(0, 0, 0, 1));
            aggregateA.addRefParticle(factory.getFSphere(2, 0, 0, 1));

            FAggregate aggregateB = factory.getFAggregate();
            aggregateB.addRefParticle(factory.getFSphere(4.1, -2, 0, 1));
            aggregateB.addRefParticle(factory.getFSphere(4.1, 0, 0, 1));
            aggregateB.addRefParticle(factory.getFSphere(4.1, 2, 0, 1));

            assertFalse(aggregateA.touches(aggregateB));
        }

        @Test
        @DisplayName("Is contact-connected")
        void isContactConnected() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertTrue(fAggregate.isConnected());
        }

        @Test
        @DisplayName("Is contact-connected - Fail")
        void isContactConnectedFail() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 2);
            FSphere fSphereE = factory.getFSphere(5, 5, 5);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isConnected());
        }

        @Test
        @DisplayName("Is point-connected")
        void isPointConnected() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertTrue(fAggregate.isPointConnected());
        }

        @Test
        @DisplayName("Is point-connected - Fail A")
        void isPointConnectedFailA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isPointConnected());
        }

        @Test
        @DisplayName("Is point-connected - Fail B")
        void isPointConnectedFailB() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isPointConnected());
        }

        @Test
        @DisplayName("Is non-overlapping")
        void isNonOverlapping() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 4);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Is non-overlapping - Fail")
        void isNonOverlappingFail() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Get total overlap factor volumetric - Same position - Double")
        void getTotalOverlapFactorVolumetricSamePositionDouble() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat overlap = fAggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC);

            assertEquals(1, overlap.sum(), epsilon);
        }

        @Test
        @DisplayName("Get total overlap factor volumetric - Same position - Triple")
        void getTotalOverlapFactorVolumetricSamePositionTriple() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();
            FSphere fSphereC = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat overlap = fAggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC);

            assertEquals(1, overlap.sum(), epsilon);
        }

        @Test
        @DisplayName("Get total overlap factor volumetric - Distant")
        void getTotalOverlapFactorVolumetricDistant() {
            FSphere fSphereA = factory.getFSphere(-1, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat overlap = fAggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC);

            assertEquals(0, overlap.sum(), epsilon);
        }

        @Test
        @DisplayName("Get total overlap factor volumetric - Intersecting")
        void getTotalOverlapFactorVolumetricIntersecting() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat overlap = fAggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC);

            double volAlgOverlap = 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);
            double volAlgTotal = 2 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double relError = factory.getStatisticsHelper().getRelErr(volAlgOverlap / volAlgTotal, overlap.sum());

            assertTrue(relError < 0.01);
        }

        @Test
        @DisplayName("Get total overlap factor volumetric - Field")
        void getTotalOverlapFactorVolumetricField() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 5, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 5);
            FSphere fSphereE = factory.getFSphere(0, 0, 5, 2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat overlap = fAggregate.getOverlapFactor(OverlapFactor.CLUSTER_VOLUMETRIC);

            double volAlgOverlap = (4  * Math.PI / 3) +
                    2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);
            double volAlgTotal = (4  * Math.PI * Math.pow(2, 3) / 3) +
                    3 * (4  * Math.PI / 3) -
                    2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double relError = factory.getStatisticsHelper().getRelErr(volAlgOverlap / volAlgTotal, overlap.sum());

            assertTrue(relError < 0.01);
        }

        @Test
        @DisplayName("Get overlap factor volumetric - Same position - Double")
        void getOverlapFactorVolumetricSamePositionDouble() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC);

            assertEquals(1, data.mean(), epsilon);
            assertEquals(2, data.size());
            assertEquals(2, data.sum(), epsilon);
        }

        @Test
        @DisplayName("Get overlap factor volumetric - Same position - Triple")
        void getOverlapFactorVolumetricSamePositionTriple() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();
            FSphere fSphereC = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC);

            assertEquals(1, data.mean(), epsilon);
            assertEquals(3, data.size());
            assertEquals(3, data.sum(), epsilon);
        }

        @Test
        @DisplayName("Get overlap factor volumetric - Distant")
        void getOverlapFactorVolumetricDistant() {
            FSphere fSphereA = factory.getFSphere(-1, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC);

            assertEquals(0, data.mean(), epsilon);
            assertEquals(2, data.size());
            assertEquals(0, data.sum(), epsilon);
        }

        @Test
        @DisplayName("Get overlap factor volumetric - Intersecting")
        void getOverlapFactorVolumetricIntersecting() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC);

            double ovAlg = 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5) / fSphereA.getVolumeAlgebraic();

            double relError = factory.getStatisticsHelper().getRelErr(ovAlg, data.mean());

            assertTrue(relError < 0.01);
            assertEquals(2, data.size());
            assertEquals(data.get(0), data.get(1), 1E-4);
        }

        @Test
        @DisplayName("Get overlap factor volumetric - Field")
        void getOverlapFactorVolumetricField() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 5, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 5);
            FSphere fSphereE = factory.getFSphere(0, 0, 5, 2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_VOLUMETRIC);

            double ovAlgA = 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5) / fSphereA.getVolumeAlgebraic();
            double ovAlgB = 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5) / fSphereB.getVolumeAlgebraic();
            double ovAlgC = 0;
            double ovAlgD = (4  * Math.PI / 3) / fSphereD.getVolumeAlgebraic();
            double ovAlgE = (4  * Math.PI / 3) / fSphereE.getVolumeAlgebraic();

            double ovAlg = (ovAlgA + ovAlgB + ovAlgC + ovAlgD + ovAlgE) / 5;

            double relError = factory.getStatisticsHelper().getRelErr(ovAlg, data.mean());

            assertTrue(relError < 0.01);
            assertEquals(5, data.size());
            assertEquals(data.get(0), ovAlgA, 1E-3);
            assertEquals(data.get(1), ovAlgB, 1E-3);
            assertEquals(data.get(2), ovAlgC, 1E-3);
            assertEquals(data.get(3), ovAlgD, 1E-3);
            assertEquals(data.get(4), ovAlgE, 1E-3);
        }

        @Test
        @DisplayName("Get overlap factory quantitative - Same position")
        void getOverlapFactorQuantitativeSamePosition() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE);

            assertEquals(1, data.mean());
            assertEquals(2, data.size());
            assertEquals(2, data.sum());
        }

        @Test
        @DisplayName("Get overlap factory quantitative - Distant")
        void getOverlapFactorQuantitativeDistant() {
            FSphere fSphereA = factory.getFSphere(-2, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE);

            assertEquals(0, data.mean());
            assertEquals(2, data.size());
            assertEquals(0, data.sum());
        }

        @Test
        @DisplayName("Get overlap factory quantitative - Intersecting")
        void getOverlapFactorQuantitativeIntersecting() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE);

            assertEquals(1, data.mean());
            assertEquals(2, data.size());
            assertEquals(2, data.sum());
        }

        @Test
        @DisplayName("Get overlap factory quantitative - Field")
        void getOverlapFactorQuantitativeField() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(5, 5, 5, 1);
            FSphere fSphereC = factory.getFSphere(0, 1.5, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 3, 0, 1);
            FSphere fSphereE = factory.getFSphere(0, 1.5, 1, 0.5);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_QUANTITATIVE);

            assertEquals(1.2, data.mean(), epsilon);
            assertEquals(5, data.size());
            assertEquals(6, data.sum());
            assertEquals(1, data.get(0));
            assertEquals(0, data.get(1));
            assertEquals(3, data.get(2));
            assertEquals(1, data.get(3));
            assertEquals(1, data.get(4));
        }

        @Test
        @DisplayName("Get overlap factory linear - Same position")
        void getOverlapFactorLinearSamePosition() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR);

            assertEquals(2, data.size(), epsilon);
            assertEquals(1, data.mean(), epsilon);
        }

        @Test
        @DisplayName("Get overlap factory linear - Distant")
        void getOverlapFactorLinearDistant() {
            FSphere fSphereA = factory.getFSphere(-2, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR);

            assertEquals(2, data.size(), epsilon);
            assertEquals(0, data.mean(), epsilon);
        }

        @Test
        @DisplayName("Get overlap factory linear - Intersecting")
        void getOverlapFactorLinearIntersecting() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR);

            assertEquals(2, data.size(), epsilon);
            assertEquals(0.5, data.mean(), epsilon);
        }

        @Test
        @DisplayName("Get overlap factory linear - Field")
        void getOverlapFactorLinearField() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 3, 0);
            FSphere fSphereD = factory.getFSphere(0, 5, 0);
            FSphere fSphereE = factory.getFSphere(5, 5, 5);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FStat data = fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR);

            assertEquals(0.2, data.mean(), epsilon);
            assertEquals(5, data.size(), epsilon);
            assertEquals(0.5, data.get(0), epsilon);
            assertEquals(0.5, data.get(1), epsilon);
            assertEquals(0, data.get(2), epsilon);
            assertEquals(0, data.get(3), epsilon);
            assertEquals(0, data.get(4), epsilon);
        }

        @Test
        @DisplayName("For each pair in contact - A")
        void forEachPairInContactA() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 2);
            FSphere fSphereE = factory.getFSphere(0, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            AtomicInteger count = new AtomicInteger(0);

            fAggregate.forEachPairInContact((a, b) -> count.incrementAndGet());

            assertEquals(7, count.get());
        }

        @Test
        @DisplayName("For each pair in contact - B")
        void forEachPairInContactB() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(-2, 0, 0);
            FSphere fSphereD = factory.getFSphere(0, 2, 0);
            FSphere fSphereE = factory.getFSphere(0, -2, 0);
            FSphere fSphereF = factory.getFSphere(0, 0, 2);
            FSphere fSphereG = factory.getFSphere(0, 0, -2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(
                    List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE, fSphereF, fSphereG)
            );

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            AtomicInteger count = new AtomicInteger(0);

            fAggregate.forEachPairInContact((a, b) -> count.incrementAndGet());

            assertEquals(6, count.get());
        }

        @Test
        @DisplayName("For each pair in contact - C")
        void forEachPairInContactC() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(3, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 3, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 3);
            FSphere fSphereE = factory.getFSphere(-3, -3, -3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            AtomicInteger count = new AtomicInteger(0);

            fAggregate.forEachPairInContact((a, b) -> count.incrementAndGet());

            assertEquals(0, count.get());
        }

        @Test
        @DisplayName("For each pair in contact - D")
        void forEachPairInContactD() {
            FSphere fSphereA = factory.getFSphere(-1, -1, 0);
            FSphere fSphereB = factory.getFSphere(-1, 1, 0);
            FSphere fSphereC = factory.getFSphere(1, -1, 0);
            FSphere fSphereD = factory.getFSphere(1, 1, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            AtomicInteger count = new AtomicInteger(0);

            fAggregate.forEachPairInContact((a, b) -> count.incrementAndGet());

            assertEquals(4, count.get());
        }

        @Test
        @DisplayName("Get boundary")
        void getBoundary() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPairPos3D boundary = fAggregate.getBoundary();

            Assertions.assertAll("Validate range",
                    () -> assertEquals(factory.getFPairPos3D(-7, -8, -9, 2, 3, 4), boundary,
                            "The range is incorrect")
            );
        }

        @Test
        @DisplayName("Get length")
        void getLength() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPos3D length = fAggregate.getLength();
            double lengthX = fAggregate.getLength(Length.X);
            double lengthY = fAggregate.getLength(Length.Y);
            double lengthZ = fAggregate.getLength(Length.Z);
            double lengthMax = fAggregate.getLength(Length.MAX);
            double lengthMin = fAggregate.getLength(Length.MIN);


            Assertions.assertAll("Validate range",
                    () -> assertEquals(factory.getFPos3D(9, 11, 13), length),
                    () -> assertEquals(9, lengthX),
                    () -> assertEquals(11, lengthY),
                    () -> assertEquals(13, lengthZ),
                    () -> assertEquals(9, lengthMin),
                    () -> assertEquals(13, lengthMax)
            );
        }

        @Test
        @DisplayName("Get diameter")
        void getDiameter() {
            FAggregate fAggregate = factory.getFAggregateContext().geometry().d1(10);

            double diameter = fAggregate.getDiameter();

            assertEquals(20, diameter, 1E-4);
        }

        @Test
        @DisplayName("Get mass center - Point contact")
        void getMassCenterPointContact() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(1, 0, 0, 1)
                    .addCoat(1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(5, 0, 0, 1)
                    .addCoat(1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(9, 0, 0, 1)
                    .addCoat(1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint center = factory.getFPoint();

            fAggregate.getMassCenter(center, MassCenter.ADAPTIVE);

            Assertions.assertAll("Validate mass center",
                    () -> assertEquals(5, center.getX(),
                            delta, "The X value is erroneous"),
                    () -> assertEquals(0, center.getY(),
                            delta, "The Y value is erroneous"),
                    () -> assertEquals(0, center.getZ(),
                            delta, "The Z value is erroneous")
            );
        }

        @Test
        @DisplayName("Get mass center - Overlap")
        void getMassCenterOverlap() {
            double delta = 0.1;

            Shape fSphereA = factory.getFSphere(3, 0, 0, 1)
                    .addCoat(1)
                    .setDelta(delta);
            Shape fSphereB = factory.getFSphere(5, 0, 0, 1)
                    .addCoat(1)
                    .setDelta(delta);
            Shape fSphereC = factory.getFSphere(7, 0, 0, 1)
                    .addCoat(1)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFBuffer(1_000_000);

            FPoint center = factory.getFPoint();

            fAggregate.getMassCenter(center, MassCenter.ADAPTIVE);

            Assertions.assertAll("Validate mass center",
                    () -> assertEquals(5, center.getX(),
                            delta, "The X value is erroneous"),
                    () -> assertEquals(0, center.getY(),
                            delta, "The Y value is erroneous"),
                    () -> assertEquals(0, center.getZ(),
                            delta, "The Z value is erroneous")
            );
        }

        @Test
        @DisplayName("Get mass center with FPoint")
        void getMassCenterFPoint() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint center = factory.getFPoint();

            FPoint results = fAggregate.getMassCenter(center, MassCenter.ADAPTIVE);

            double volA = factory.getFSphereHelper().getVolume(1);
            double volB = factory.getFSphereHelper().getVolume(3);
            double d0 = ((1 * volA) + (-4 * volB)) / (volA + volB);
            double d1 = ((2 * volA) + (-5 * volB)) / (volA + volB);
            double d2 = ((3 * volA) + (-6 * volB)) / (volA + volB);

            Assertions.assertAll("Validate mass center",
                    () -> assertSame(center, results,
                            "The reference should not change"),
                    () -> assertTrue(center.isSimilar(d0, d1, d2),
                            "The mass center position is incorrect")
            );
        }

        @Test
        @DisplayName("Get mass center")
        void getMassCenter() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPos3D center = fAggregate.getMassCenter(MassCenter.ADAPTIVE);

            double volA = factory.getFSphereHelper().getVolume(1);
            double volB = factory.getFSphereHelper().getVolume(3);
            double d0 = ((1 * volA) + (-4 * volB)) / (volA + volB);
            double d1 = ((2 * volA) + (-5 * volB)) / (volA + volB);
            double d2 = ((3 * volA) + (-6 * volB)) / (volA + volB);

            Assertions.assertAll("Validate mass center",
                    () -> assertTrue(factory.getFPointHelper().isSimilar(center.getD0(), center.getD1(), center.getD2(), d0, d1, d2),
                            "The mass center position is incorrect")
            );
        }

        @Test
        @DisplayName("Get spherical center with FPoint")
        void getSphericalCenterFPoint() {
            Shape fSphereA = factory.getFSphere(-5, 0, 0, 1);
            Shape fSphereB = factory.getFSphere(5, 0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint center = factory.getFPoint();

            FPoint results = fAggregate.getSphericalCenter(center, 100);

            Assertions.assertAll("Validate spatial center",
                    () -> assertSame(center, results,
                            "The reference should not change"),
                    () -> assertEquals(0, center.getX(),
                            1E-4, "Value X is incorrect"),
                    () -> assertEquals(0, center.getY(),
                            1E-4, "Value Y is incorrect"),
                    () -> assertEquals(0, center.getZ(),
                            1E-4, "Value Z is incorrect")
            );
        }

        @Test
        @DisplayName("Get spherical center")
        void getSphericalCenter() {
            Shape fSphereA = factory.getFSphere(-5, 0, 0, 1);
            Shape fSphereB = factory.getFSphere(5, 0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPos3D center = fAggregate.getSphericalCenter(100);

            Assertions.assertAll("Validate spatial center",
                    () -> assertEquals(0, center.getD0(),
                            1E-4, "Value X is incorrect"),
                    () -> assertEquals(0, center.getD1(),
                            1E-4, "Value Y is incorrect"),
                    () -> assertEquals(0, center.getD2(),
                            1E-4, "Value Z is incorrect")
            );
        }

        @Test
        @DisplayName("Get spatial center with FPoint")
        void getSpatialCenterFPoint() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint center = factory.getFPoint();

            FPoint results = fAggregate.getSpatialCenter(center);

            double d0 = (2 - 7) / 2d;
            double d1 = (3 - 8) / 2d;
            double d2 = (4 - 9) / 2d;

            Assertions.assertAll("Validate spatial center",
                    () -> assertSame(center, results,
                            "The reference should not change"),
                    () -> assertTrue(center.isSimilar(d0, d1, d2),
                            "The spatial center position is incorrect")
            );
        }

        @Test
        @DisplayName("Get spatial center")
        void getSpatialCenter() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPos3D center = fAggregate.getSpatialCenter();

            double d0 = (2 - 7) / 2d;
            double d1 = (3 - 8) / 2d;
            double d2 = (4 - 9) / 2d;

            Assertions.assertAll("Validate spatial center",
                    () -> assertTrue(factory.getFPointHelper().isSimilar(center.getD0(), center.getD1(), center.getD2(), d0, d1, d2),
                            "The spatial center position is incorrect")
            );
        }

        @Test
        @DisplayName("Reset position with FPoint")
        void resetPositionFPoint() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint centerBefore = factory.getFPoint();

            fAggregate.getMassCenter(centerBefore, MassCenter.ADAPTIVE);

            assertFalse(centerBefore.isSimilar(0, 0, 0));

            FPoint centerAfter = factory.getFPoint();

            fAggregate.setPositionAsZero(centerBefore);
            fAggregate.getMassCenter(centerAfter, MassCenter.ADAPTIVE);

            assertTrue(centerAfter.isSimilar(0, 0, 0));
        }

        @Test
        @DisplayName("Reset position with FPos3D")
        void resetPositionFPos3D() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPos3D centerBefore = fAggregate.getMassCenter(MassCenter.ADAPTIVE);

            assertFalse(factory.getFPointHelper().isSimilar(centerBefore.getD0(), centerBefore.getD1(), centerBefore.getD2(), 0, 0, 0));

            factory.getFPoint();

            fAggregate.setPositionAsZero(centerBefore);

            FPos3D centerAfter = fAggregate.getMassCenter(MassCenter.ADAPTIVE);

            assertTrue(factory.getFPointHelper().isSimilar(centerAfter.getD0(), centerAfter.getD1(), centerAfter.getD2(), 0, 0, 0));
        }

        @Test
        @DisplayName("Reset center with enum")
        void resetCenterEnum() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenterAsZero(Center.ORIGIN);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(2, 0, 0))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(0, 2, 0))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(0, 0, 2)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.getMassCenter(center, MassCenter.ADAPTIVE);

            assertFalse(center.isSimilar(0, 0, 0));

            fAggregate.setCenterAsZero(Center.MASS);

            fAggregate.getMassCenter(center, MassCenter.ADAPTIVE);

            assertTrue(center.isSimilar(0, 0, 0));

            fAggregate.getSpatialCenter(center);

            assertFalse(center.isSimilar(0, 0, 0));

            fAggregate.setCenterAsZero(Center.SPATIAL);

            fAggregate.getSpatialCenter(center);

            assertTrue(center.isSimilar(0, 0, 0));

            fAggregate.getSphericalCenter(center, 100);

            assertFalse(center.isSimilar(0, 0, 0));

            fAggregate.setCenterAsZero(Center.SPHERICAL);

            fAggregate.getSphericalCenter(center, 100);

            assertTrue(center.isSimilar(0, 0, 0));
        }

        @Test
        @DisplayName("Reset mass center")
        void resetMassCenter() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            FPoint center = factory.getFPoint();

            fAggregate.setMassCenterAsZero(MassCenter.SIMPLE_POLY);

            fAggregate.getMassCenter(center, MassCenter.SIMPLE_POLY);

            assertTrue(center.isSimilar(0, 0, 0));
        }

        @Test
        @DisplayName("Reset spatial center")
        void resetSpatialCenter() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            FPoint center = factory.getFPoint();

            fAggregate.setSpatialCenterAsZero();

            fAggregate.getSpatialCenter(center);

            assertTrue(center.isSimilar(0, 0, 0));
        }

        @Test
        @DisplayName("Reset spherical center")
        void resetSphericalCenter() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            FPoint center = factory.getFPoint();

            fAggregate.setSphericalCenterAsZero(100);

            fAggregate.getSphericalCenter(center, 100);

            assertEquals(0, center.getX(), 1E-4);
            assertEquals(0, center.getY(), 1E-4);
            assertEquals(0, center.getZ(), 1E-4);
        }

        @Test
        @DisplayName("Get center with FPoint")
        void getCenterWithFPoint() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            FPoint center = factory.getFPoint();

            fAggregate.getCenter(center, Center.ORIGIN);

            assertTrue(center.isSimilar(0, 0, 0));

            fAggregate.getCenter(center, Center.MASS);

            assertTrue(center.isSimilar(fAggregate.getMassCenter(MassCenter.ADAPTIVE)));

            fAggregate.getCenter(center, Center.SPATIAL);

            assertTrue(center.isSimilar(fAggregate.getSpatialCenter()));

            fAggregate.getCenter(center, Center.SPHERICAL);

            assertTrue(center.isSimilar(fAggregate.getSphericalCenter(100)));
        }

        @Test
        @DisplayName("Get center with FPos3D")
        void getCenterWithFPos3D() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            FPoint center = factory.getFPoint();

            center.set(fAggregate.getCenter(Center.ORIGIN));

            assertTrue(center.isSimilar(0, 0, 0));

            center.set(fAggregate.getCenter(Center.MASS));

            assertTrue(center.isSimilar(fAggregate.getMassCenter(MassCenter.ADAPTIVE)));

            center.set(fAggregate.getCenter(Center.SPATIAL));

            assertTrue(center.isSimilar(fAggregate.getSpatialCenter()));

            center.set(fAggregate.getCenter(Center.SPHERICAL));

            assertTrue(center.isSimilar(fAggregate.getSphericalCenter(100)));
        }

        @Test
        @DisplayName("Set center with primitives")
        void setCenterWithPrimitives() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenter(Center.ORIGIN, 1, 2, 3);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.setCenter(Center.MASS, -1, -2, -3);

            center.set(fAggregate.getMassCenter(MassCenter.ADAPTIVE));

            assertTrue(center.isSimilar(-1, -2, -3));

            fAggregate.setCenter(Center.SPATIAL, 1, 2, 3);

            center.set(fAggregate.getSpatialCenter());

            assertTrue(center.isSimilar(1, 2, 3));

            fAggregate.setCenter(Center.SPHERICAL, -1, -2, -3);

            center.set(fAggregate.getSphericalCenter(100));

            assertTrue(center.isSimilar(-1, -2, -3));
        }

        @Test
        @DisplayName("Set center with FPoint")
        void setCenterWithFPoint() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenter(Center.ORIGIN, factory.getFPoint(1, 2, 3));

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.setCenter(Center.MASS, factory.getFPoint(-1, -2, -3));

            center.set(fAggregate.getMassCenter(MassCenter.ADAPTIVE));

            assertTrue(center.isSimilar(-1, -2, -3));

            fAggregate.setCenter(Center.SPATIAL, factory.getFPoint(1, 2, 3));

            center.set(fAggregate.getSpatialCenter());

            assertTrue(center.isSimilar(1, 2, 3));

            fAggregate.setCenter(Center.SPHERICAL, factory.getFPoint(-1, -2, -3));

            center.set(fAggregate.getSphericalCenter(100));

            assertTrue(center.isSimilar(-1, -2, -3));
        }

        @Test
        @DisplayName("Set center with FPos3D")
        void setCenterWithFPos3D() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenter(Center.ORIGIN, factory.getFPos3D(1, 2, 3));

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.setCenter(Center.MASS, factory.getFPos3D(-1, -2, -3));

            center.set(fAggregate.getMassCenter(MassCenter.ADAPTIVE));

            assertTrue(center.isSimilar(-1, -2, -3));

            fAggregate.setCenter(Center.SPATIAL, factory.getFPos3D(1, 2, 3));

            center.set(fAggregate.getSpatialCenter());

            assertTrue(center.isSimilar(1, 2, 3));

            fAggregate.setCenter(Center.SPHERICAL, factory.getFPos3D(-1, -2, -3));

            center.set(fAggregate.getSphericalCenter(100));

            assertEquals(0, Math.abs(-1 - center.getX()), 1E-4);
            assertEquals(0, Math.abs(-2 - center.getY()), 1E-4);
            assertEquals(0, Math.abs(-3 - center.getZ()), 1E-4);
        }

        @Test
        @DisplayName("Set mass center")
        void setMassCenter() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenter(Center.ORIGIN, 1, 2, 3);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.setMassCenter(1, 2, 3, MassCenter.ADAPTIVE);

            center.set(fAggregate.getMassCenter(MassCenter.ADAPTIVE));

            assertTrue(center.isSimilar(1, 2, 3));

            fAggregate.setMassCenter(factory.getFPoint(-1, -2, -3), MassCenter.ADAPTIVE);

            center.set(fAggregate.getMassCenter(MassCenter.ADAPTIVE));

            assertTrue(center.isSimilar(-1, -2, -3));

            fAggregate.setMassCenter(factory.getFPos3D(1, 2, 3), MassCenter.ADAPTIVE);

            center.set(fAggregate.getMassCenter(MassCenter.ADAPTIVE));

            assertTrue(center.isSimilar(1, 2, 3));
        }

        @Test
        @DisplayName("Set spatial center")
        void setSpatialCenter() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenter(Center.ORIGIN, 1, 2, 3);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.setSpatialCenter(1, 2, 3);

            center.set(fAggregate.getSpatialCenter());

            assertTrue(center.isSimilar(1, 2, 3));

            fAggregate.setSpatialCenter(factory.getFPoint(-1, -2, -3));

            center.set(fAggregate.getSpatialCenter());

            assertTrue(center.isSimilar(-1, -2, -3));

            fAggregate.setSpatialCenter(factory.getFPos3D(1, 2, 3));

            center.set(fAggregate.getSpatialCenter());

            assertTrue(center.isSimilar(1, 2, 3));
        }

        @Test
        @DisplayName("Set spherical center")
        void setSphericalCenter() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(2, 0, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0, 1));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, 2, 1));

            fAggregate.setCenter(Center.ORIGIN, 1, 2, 3);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );

            FPoint center = factory.getFPoint();

            fAggregate.setSphericalCenter(1, 2, 3, 100);

            center.set(fAggregate.getSphericalCenter(100));

            assertEquals(0, Math.abs(1 - center.getX()), 1E-4);
            assertEquals(0, Math.abs(2 - center.getY()), 1E-4);
            assertEquals(0, Math.abs(3 - center.getZ()), 1E-4);

            fAggregate.setSphericalCenter(factory.getFPoint(-1, -2, -3), 100);

            center.set(fAggregate.getSphericalCenter(100));

            assertEquals(0, Math.abs(-1 - center.getX()), 1E-4);
            assertEquals(0, Math.abs(-2 - center.getY()), 1E-4);
            assertEquals(0, Math.abs(-3 - center.getZ()), 1E-4);

            fAggregate.setSphericalCenter(factory.getFPos3D(1, 2, 3), 100);

            center.set(fAggregate.getSphericalCenter(100));

            assertEquals(0, Math.abs(1 - center.getX()), 1E-4);
            assertEquals(0, Math.abs(2 - center.getY()), 1E-4);
            assertEquals(0, Math.abs(3 - center.getZ()), 1E-4);
        }

        @Test
        @DisplayName("Get radius with primitives")
        void getRadiusWithPrimitives() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1);
            Shape fSphereB = factory.getFSphere(3, 3, 3, 1);
            Shape fSphereC = factory.getFSphere(5, 5, 5, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint massCenter = factory.getFPoint();

            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadiusFrom(massCenter.getX(), massCenter.getY(), massCenter.getZ());
            double radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.setPositionAsZero(massCenter);
            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadiusFrom(massCenter.getX(), massCenter.getY(), massCenter.getZ());
            radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertEquals(radiusA, radiusB, 1E-6);
        }

        @Test
        @DisplayName("Get radius with FPoint")
        void getRadiusWithFPoint() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1);
            Shape fSphereB = factory.getFSphere(3, 3, 3, 1);
            Shape fSphereC = factory.getFSphere(5, 5, 5, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint massCenter = factory.getFPoint();

            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadiusFrom(massCenter);
            double radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.setPositionAsZero(massCenter);
            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadiusFrom(massCenter);
            radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertEquals(radiusA, radiusB, 1E-6);
        }

        @Test
        @DisplayName("Get radius with FPos3D")
        void getRadiusWithFPos3D() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1);
            Shape fSphereB = factory.getFSphere(3, 3, 3, 1);
            Shape fSphereC = factory.getFSphere(5, 5, 5, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint massCenter = factory.getFPoint();

            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadiusFrom(massCenter.toFPos3D());
            double radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.setPositionAsZero(massCenter);
            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadiusFrom(massCenter.toFPos3D());
            radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertEquals(radiusA, radiusB, 1E-6);
        }

        @Test
        @DisplayName("Get radius with enum")
        void getRadiusWithEnum() {
            Shape fSphereA = factory.getFSphere(1, 1, 1, 1);
            Shape fSphereB = factory.getFSphere(3, 3, 3, 1);
            Shape fSphereC = factory.getFSphere(5, 5, 5, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint massCenter = factory.getFPoint();

            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadiusFrom(Center.MASS);
            double radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.setPositionAsZero(massCenter);
            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadiusFrom(Center.MASS);
            radiusB = fAggregate.getRadiusFrom(Center.ORIGIN);

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertEquals(radiusA, radiusB, 1E-6);
        }

        @Test
        @DisplayName("Get volume radius - Single")
        void getVolumeRadiusSingle() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double rExpected = factory.getFSphereHelper().getVolumeRadius(fAggregate.getVolume(Volume.ADAPTIVE));
            double rActual = fAggregate.getVolumeRadius(Volume.ADAPTIVE);

            double rErr = factory.getStatisticsHelper().getRelErr(rExpected, rActual);

            assertTrue(rErr < 0.01);
        }

        @Test
        @DisplayName("Get volume radius")
        void getVolumeRadius() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphereHelper helper = factory.getFSphereHelper();

            double[] layers = new double[3];
            double volExpected = fAggregate.getVolume(layers, Volume.ADAPTIVE);

            double rExpectedLayer0 = helper.getVolumeRadius(layers[0]);
            double rExpectedLayer1 = helper.getVolumeRadius(layers[0] + layers[1]);
            double rExpectedLayer2 = helper.getVolumeRadius(layers[0] + layers[1] + layers[2]);
            double rExpected = helper.getVolumeRadius(volExpected);

            double rActual = fAggregate.getVolumeRadius(layers, Volume.ADAPTIVE);

            double rErrLayer0 = factory.getStatisticsHelper().getRelErr(rExpectedLayer0, layers[0]);
            double rErrLayer1 = factory.getStatisticsHelper().getRelErr(rExpectedLayer1, layers[1]);
            double rErrLayer2 = factory.getStatisticsHelper().getRelErr(rExpectedLayer2, layers[2]);
            double rErr = factory.getStatisticsHelper().getRelErr(rExpected, rActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(rErrLayer0 < 0.01,
                            "Layer 0 radius is erroneous"),
                    () -> assertTrue(rErrLayer1 < 0.01,
                            "Layer 1 radius is erroneous"),
                    () -> assertTrue(rErrLayer2 < 0.01,
                            "Layer 2 radius is erroneous"),
                    () -> assertTrue(rErr < 0.01,
                            "The total radius is erroneous")
            );
        }

        @Test
        @DisplayName("Get surface radius - Single")
        void getSurfaceRadiusSingle() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfExpected = fAggregate.getSurface(Surface.ADAPTIVE);

            double rExpected = factory.getFSphereHelper().getSurfaceRadius(srfExpected);
            double rActual = fAggregate.getSurfaceRadius(Surface.ADAPTIVE);

            double rErr = factory.getStatisticsHelper().getRelErr(rExpected, rActual);

            assertTrue(rErr < 0.01);
        }

        @Test
        @DisplayName("Get surface radius")
        void getSurfaceRadius() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphereHelper helper = factory.getFSphereHelper();

            double[] layers = new double[3];
            double srfExpected = fAggregate.getSurface(layers, Surface.ADAPTIVE);

            double rExpectedLayer0 = helper.getSurfaceRadius(layers[0]);
            double rExpectedLayer1 = helper.getSurfaceRadius(layers[1]);
            double rExpectedLayer2 = helper.getSurfaceRadius(layers[2]);
            double rExpected = helper.getSurfaceRadius(srfExpected);

            double rActual = fAggregate.getSurfaceRadius(layers, Surface.ADAPTIVE);

            double rErrLayer0 = factory.getStatisticsHelper().getRelErr(rExpectedLayer0, layers[0]);
            double rErrLayer1 = factory.getStatisticsHelper().getRelErr(rExpectedLayer1, layers[1]);
            double rErrLayer2 = factory.getStatisticsHelper().getRelErr(rExpectedLayer2, layers[2]);
            double rErr = factory.getStatisticsHelper().getRelErr(rExpected, rActual);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(rErrLayer0 < 0.01,
                            "Layer 0 radius is erroneous"),
                    () -> assertTrue(rErrLayer1 < 0.01,
                            "Layer 1 radius is erroneous"),
                    () -> assertTrue(rErrLayer2 < 0.01,
                            "Layer 2 radius is erroneous"),
                    () -> assertTrue(rErr < 0.01,
                            "The total radius is erroneous")
            );
        }

        @Test
        @DisplayName("Get radius of gyration")
        void getRadiusOfGyration() {
            double delta = 0.1;
            double radius = 1;

            Shape fSphereA = factory.getFSphere(0, 0, 0, radius)
                    .setDelta(delta);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFBuffer(1_000_000);

            FPoint massCenter = factory.getFPoint();
            fAggregate.getMassCenter(massCenter, MassCenter.ADAPTIVE);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            double rgExpected = factory.getFSphereHelper().getRadiusOfGyration(radius);
            double rgActual = fAggregate.getRadiusOfGyration(RadiusOfGyration.COMPLEX);

            double rgErr = factory.getStatisticsHelper().getRelErr(rgExpected, rgActual);

            assertTrue(rgErr < 0.005);
        }

        @Test
        @DisplayName("Get radius of gyration (legacy)")
        void getRadiusOfGyrationLegacy() {
            int quantity = 25;
            double delta = 0.1;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .setDelta(delta)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFBuffer(1_000_000).addFMaterial();

            double rgDefault = fAggregate.getRadiusOfGyration(RadiusOfGyration.COMPLEX);
            double rgLegacyMono = fAggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_MONO_06R1);
            double rgLegacyPoly = fAggregate.getRadiusOfGyration(RadiusOfGyration.SIMPLE_POLY_06R1);
            double rgLegacyFilippov = fAggregate.getRadiusOfGyration(RadiusOfGyration.DEDICATED_FILIPPOV);

            double rgErrMono = factory.getStatisticsHelper().getRelErr(rgDefault, rgLegacyMono);
            double rgErrPoly = factory.getStatisticsHelper().getRelErr(rgDefault, rgLegacyPoly);
            double rgErrFilippov = factory.getStatisticsHelper().getRelErr(rgDefault, rgLegacyFilippov);

            assertTrue(rgErrMono < 0.05);
            assertTrue(rgErrPoly < 0.05);
            assertTrue(rgErrFilippov < 0.05);
        }

        @Test
        @DisplayName("Set material density")
        void setMaterialDensity() {
            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of());
            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFMaterial();

            fAggregate.getRefFExtension().getRefFMaterial().setDensity("X", 5);

            assertEquals(5, fAggregate.getRefFExtension().getRefFMaterial().getDensity("X"));
        }

        @Test
        @DisplayName("Set material refractive index")
        void setMaterialRefractiveIndex() {
            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of());
            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFMaterial();

            fAggregate.getRefFExtension().getRefFMaterial().setRefIndex("X", 2, 3);

            assertEquals(2, fAggregate.getRefFExtension().getRefFMaterial().getRefIndexRe("X"));
            assertEquals(3, fAggregate.getRefFExtension().getRefFMaterial().getRefIndexIm("X"));
        }

        @Test
        @DisplayName("Pair distance - A")
        void getPairDistanceA() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FStat results = fAggregate.getPairDistance();

            results.sort(true);

            assertEquals(21, results.size());
            assertEquals(2, results.get(0), 1E-4);
            assertEquals(4, results.get(results.size() - 1), 1E-4);

            results.deduplicate();

            assertEquals(3, results.size());
        }

        @Test
        @DisplayName("Pair distance - B")
        void getPairDistanceB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FStat results = fAggregate.getPairDistance();

            assertTrue(results.size() > 100000);
        }

        @Test
        @DisplayName("Pair distance - Distribution A")
        void getPairDistanceFunctionA() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FPlot results = fAggregate.getPairDistanceFunction();
            results.mutateY(FStat::distribute);

            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertEquals(0, results.getY(0));
            assertEquals(0, results.getY(1));
            assertEquals(0, results.getY(3));
            assertTrue(results.getY(2) > results.getY(4));
        }

        @Test
        @DisplayName("Pair distance - Distribution B")
        void getPairDistanceFunctionB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FPlot results = fAggregate.getPairDistanceFunction();
            results.mutateY(FStat::distribute);

            assertTrue(results.size() > 100);
        }

        @Test
        @DisplayName("Coordination number - A")
        void coordinationNumberA() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FStat results = fAggregate.getCoordinationNumber();

            results.sort(true);

            assertEquals(7, results.size());
            assertEquals(1, results.get(0), 1E-4);
            assertEquals(6, results.get(results.size() - 1), 1E-4);

            results.deduplicate();

            assertEquals(2, results.size());
        }

        @Test
        @DisplayName("Coordination number - B")
        void coordinationNumberB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FStat results = fAggregate.getCoordinationNumber();

            assertEquals(1000, results.size());
        }

        @Test
        @DisplayName("Coordination number - Distribution")
        void getCoordinationNumberFunction() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FPlot results = fAggregate.getCoordinationNumberFunction();
            results.mutateY(FStat::distribute);

            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertEquals(0, results.getY(1));
            assertEquals(0, results.getY(2));
            assertEquals(0, results.getY(3));
            assertTrue(results.getY(0) > results.getY(4));
        }

        @Test
        @DisplayName("Density correlation - Function A")
        void getDensityCorrelationFunctionA() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FPlot results = fAggregate.getDensityCorrelationFunction(1.1);
            results.mutateY(FStat::distribute);

            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertTrue(results.getY(1) < results.getY(0));
        }

        @Test
        @DisplayName("Density correlation - Function B")
        void getDensityCorrelationFunctionB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FPlot results = fAggregate.getDensityCorrelationFunction(1.1);

            assertTrue(results.size() > 25);
        }

        @Test
        @DisplayName("Box coverage - Function")
        void getBoxCoverageFunctionB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FPlot results = fAggregate.getBoxCoverageFunction(2, 1, true, false);

            assertTrue(results.size() >= 5);
            assertTrue(results.getY(0) < results.getY(1));
            assertTrue(results.getY(1) < results.getY(2));
            assertTrue(results.getY(2) < results.getY(3));
            assertTrue(results.getY(3) < results.getY(4));
        }

        @Test
        @DisplayName("Triplet angle - A")
        void getTripletAngleA() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FStat results = fAggregate.getTripletAngle();

            results.sort(true);

            assertEquals(15, results.size());
            assertEquals(Math.PI * 0.5, results.get(0), 1E-4);
            assertEquals(Math.PI, results.get(results.size() - 1), 1E-4);

            results.deduplicate();

            assertEquals(2, results.size());
        }

        @Test
        @DisplayName("Triplet angle - B")
        void getTripletAngleB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FStat results = fAggregate.getTripletAngle();

            assertTrue(results.size() > 1000);
        }

        @Test
        @DisplayName("Triplet angle (degree)")
        void getTripletAngleDegree() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FStat results = fAggregate.getTripletAngle();

            FTrigHelper helper = factory.getFTrigHelper();
            results.mutate(helper::convertRadToDeg);

            results.sort(true);

            assertEquals(15, results.size());
            assertEquals(90, results.get(0), 1E-4);
            assertEquals(180, results.get(results.size() - 1), 1E-4);

            results.deduplicate();

            assertEquals(2, results.size());
        }

        @Test
        @DisplayName("Triplet angle - Distribution")
        void getTripletAngleFunction() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FPlot results = fAggregate.getTripletAngleFunction();
            results.mutateY(FStat::distribute);

            results.filter((x, y) -> y > 0);

            assertEquals(2, results.size());
            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertEquals(Math.PI * 0.5, results.getX(0), 1E-4);
            assertEquals(Math.PI, results.getX(1), 1E-4);
            assertTrue(results.getY(0) > results.getY(1));
        }

        @Test
        @DisplayName("Triplet angle - Distribution (degree)")
        void getTripletAngleFunctionDegree() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FPlot results = fAggregate.getTripletAngleFunction();

            FTrigHelper helper = factory.getFTrigHelper();
            results.mutateX((x, y) -> helper.convertRadToDeg(x));

            results.mutateY(FStat::distribute);

            results.filter((x, y) -> y > 0);

            assertEquals(2, results.size());
            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertEquals(90, results.getX(0), 1E-4);
            assertEquals(180, results.getX(1), 1E-4);
            assertTrue(results.getY(0) > results.getY(1));
        }

        @Test
        @DisplayName("Get FStat particle radius")
        void getFStatParticleRadius() {
            Shape shapeA = factory.getFSphere(1);
            Shape shapeB = factory.getFSphere(2);
            Shape shapeC = factory.getFSphere(3);
            Shape shapeD = factory.getFSphere(4);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shapeA, shapeB, shapeC, shapeD));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            FStat stat = fAggregate.getFStatParticleRadius();

            assertEquals(4, stat.size());
            assertEquals(1, stat.min());
            assertEquals(4, stat.max());
        }

        @Test
        @DisplayName("Get FStat distance")
        void getFStatDistance() {
            FAggregate fAggregate = factory.getFAggregate();
            fAggregate.addRefParticle(factory.getFSphere(-1, 0, 0));
            fAggregate.addRefParticle(factory.getFSphere(0, 2, 0));
            fAggregate.addRefParticle(factory.getFSphere(0, 0, -3));

            FStat stat = fAggregate.getFStatDistance(Center.ORIGIN);

            assertEquals(3, stat.size());
            assertEquals(1, stat.min());
            assertEquals(3, stat.max());
        }

        @Test
        @DisplayName("Set epsilon")
        void setEpsilon() {
            Shape shapeA = factory.getFSphere(1);
            Shape shapeB = factory.getFSphere(2);
            Shape shapeC = factory.getFSphere(3);
            Shape shapeD = factory.getFSphere(4);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shapeA, shapeB, shapeC, shapeD));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            fAggregate.setParticleEpsilon(1);

            fAggregate.getRefParticles().forEach(e -> assertEquals(1, e.getEpsilon()));
        }

        @Test
        @DisplayName("Set delta")
        void setDelta() {
            Shape shapeA = factory.getFSphere(1);
            Shape shapeB = factory.getFSphere(2);
            Shape shapeC = factory.getFSphere(3);
            Shape shapeD = factory.getFSphere(4);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shapeA, shapeB, shapeC, shapeD));
            FAggregate fAggregate = factory.getRefFAggregate(core);

            fAggregate.setParticleDelta(1);

            fAggregate.getRefParticles().forEach(e -> assertEquals(1, e.getDelta()));
        }

        @Test
        @DisplayName("Project A")
        void projectA() {
            Shape shapeA1 = factory.getFSphere(5, 0, 0, 1);
            Shape shapeA2 = factory.getFSphere(5, 2, 0, 1);
            Shape shapeA3 = factory.getFSphere(5, 4, 0, 1);
            Shape shapeA4 = factory.getFSphere(7, 2, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3, shapeA4));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(0, 2, 0, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            aggregateA.project(aggregateB, factory.getFVector(100, 0, 0, 0, 0, 0));

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Project B")
        void projectB() {
            Shape shapeA1 = factory.getFSphere(3, 0, 0, 1);
            Shape shapeA2 = factory.getFSphere(5, 2, 0, 1);
            Shape shapeA3 = factory.getFSphere(3, 4, 0, 1);
            Shape shapeA4 = factory.getFSphere(7, 2, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3, shapeA4));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(0, 2, 0, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            aggregateA.project(aggregateB, factory.getFVector(100, 0, 0, 0, 0, 0));

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Project C")
        void projectC() {
            Shape shapeA1 = factory.getFSphere(5, 3, 0, 1);
            Shape shapeA2 = factory.getFSphere(5, 5, 0, 1);
            Shape shapeA3 = factory.getFSphere(5, 7, 0, 1);
            Shape shapeA4 = factory.getFSphere(7, 5, 0, 1);

            FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3, shapeA4));
            FAggregate aggregateA = factory.getRefFAggregate(coreA);

            Shape shapeB1 = factory.getFSphere(0, 2, 0, 1);

            FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1));
            FAggregate aggregateB = factory.getRefFAggregate(coreB);

            aggregateA.project(aggregateB, factory.getFVector(100, 0, 0, 0, 0, 0));

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Project, composite A")
        void projectCompositeA() {
            FAggregate aggregateA = factory.getFAggregateContext().geometry().fullSphere(3);
            FAggregate aggregateB = factory.getFAggregateContext().geometry().d2(5, 5);

            aggregateA.setParticleEpsilon(1);

            aggregateA.getRefParticles().translate(factory.getFRand().nextDoubleOnSphere(100));

            FPoint centerA = factory.getFPoint();
            FPoint centerB = factory.getFPoint();

            aggregateA.getSpatialCenter(centerA);
            aggregateB.getSpatialCenter(centerB);

            aggregateA.project(aggregateB, factory.getRefFVector(centerA, centerB));

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Project, composite B")
        void projectCompositeB() {
            FAggregate aggregateA = factory.getFAggregateContext().geometry().d3(2, 3, 4);
            FAggregate aggregateB = factory.getFAggregateContext().geometry().d3(3, 4, 5);

            aggregateA.getRefParticles().translate(factory.getFRand().nextDoubleOnSphere(100));

            FPoint centerA = factory.getFPoint();
            FPoint centerB = factory.getFPoint();

            aggregateA.getSpatialCenter(centerA);
            aggregateB.getSpatialCenter(centerB);

            aggregateA.project(aggregateB, factory.getRefFVector(centerA, centerB));

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Project with limit, composite A")
        void projectWithLimitCompositeA() {
            FAggregate aggregateA = factory.getFAggregateContext().geometry().fullSphere(3);
            FAggregate aggregateB = factory.getFAggregateContext().geometry().d2(5, 5);

            aggregateA.setParticleEpsilon(1);

            aggregateA.getRefParticles().translate(factory.getFRand().nextDoubleOnSphere(100));

            FPoint centerA = factory.getFPoint();
            FPoint centerB = factory.getFPoint();

            aggregateA.getSpatialCenter(centerA);
            aggregateB.getSpatialCenter(centerB);

            double shiftA = aggregateA.project(aggregateB, factory.getRefFVector(centerA, centerB), 50);

            assertTrue(shiftA < 0);

            double shiftB = aggregateA.project(aggregateB, factory.getRefFVector(centerA, centerB), 100);

            assertTrue(shiftB > 0);

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Project with limit, composite B")
        void projectWithLimitCompositeB() {
            FAggregate aggregateA = factory.getFAggregateContext().geometry().d3(2, 3, 4);
            FAggregate aggregateB = factory.getFAggregateContext().geometry().d3(3, 4, 5);

            aggregateA.getRefParticles().translate(factory.getFRand().nextDoubleOnSphere(100));

            FPoint centerA = factory.getFPoint();
            FPoint centerB = factory.getFPoint();

            aggregateA.getSpatialCenter(centerA);
            aggregateB.getSpatialCenter(centerB);

            double shiftA = aggregateA.project(aggregateB, factory.getRefFVector(centerA, centerB), 50);

            assertTrue(shiftA < 0);

            double shiftB = aggregateA.project(aggregateB, factory.getRefFVector(centerA, centerB), 100);

            assertTrue(shiftB > 0);

            aggregateA.merge(aggregateB, true);

            assertTrue(aggregateA.isConnected());
        }

        @Test
        @DisplayName("Add particles")
        void addParticles() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphere = factory.getFSphere(1, 2, 3, 4);

            fAggregate.addParticles(fSphere, 2);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(2, fAggregate.size()),
                    () -> assertNotSame(fSphere, fAggregate.getRefParticles().asList().get(0)),
                    () -> assertNotSame(fSphere, fAggregate.getRefParticles().asList().get(1)),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(1, 2, 3, 4))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 2, 3, 4)))
            );
        }

        @Test
        @DisplayName("Add ref particle")
        void addRefParticle() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            boolean resultsA = fAggregate.addRefParticle(fSphereA);
            boolean resultsB = fAggregate.addRefParticle(fSphereB);
            boolean resultsC = fAggregate.addRefParticle(fSphereC);

            boolean resultsDuplicate = fAggregate.addRefParticle(fSphereB);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(3, fAggregate.size()),
                    () -> assertTrue(resultsA),
                    () -> assertTrue(resultsB),
                    () -> assertTrue(resultsC),
                    () -> assertFalse(resultsDuplicate)
            );

            fSphereA.setCenter(1, 2, 3);

            assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(1, 2, 3, 1)));
        }

        @Test
        @DisplayName("Remove ref particle")
        void removeRefParticle() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            boolean resultsA = fAggregate.deleteRefParticle(fSphereA);

            boolean resultsEmpty = fAggregate.deleteRefParticle(fSphereA);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertEquals(2, fAggregate.size()),
                    () -> assertTrue(resultsA),
                    () -> assertFalse(resultsEmpty)
            );
        }

        @Test
        @DisplayName("Translate (FPoint) with primitives")
        void translateFPointWithPrimitives() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            fAggregate.translate(1, 2, 3);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );
        }

        @Test
        @DisplayName("Translate (FPoint) with FPoint")
        void translateFPointWithFPoint() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            fAggregate.translate(factory.getFPoint(1, 2, 3));

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );
        }

        @Test
        @DisplayName("Translate (FPoint) with FPos3D")
        void translateFPointWithFPos3D() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            fAggregate.translate(factory.getFPos3D(1, 2, 3));

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );
        }

        @Test
        @DisplayName("Translate (FVector) with primitives")
        void translateFVectorWithPrimitives() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            fAggregate.translate(-3, -2, -1, -2, 0, 2);

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );
        }

        @Test
        @DisplayName("Translate (FVector) with FVector")
        void translateFVectorWithFVector() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            fAggregate.translate(factory.getFVector(-3, -2, -1, -2, 0, 2));

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );
        }

        @Test
        @DisplayName("Translate (FVector) with FPairPos3D")
        void translateFVectorWithFPairPos3D() {
            FAggregate fAggregate = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 0, 2, 1);

            fAggregate.addRefParticle(fSphereA);
            fAggregate.addRefParticle(fSphereB);
            fAggregate.addRefParticle(fSphereC);

            fAggregate.translate(factory.getFPairPos3D(-3, -2, -1, -2, 0, 2));

            Assertions.assertAll("Validate FAggregate",
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(0).isExact(factory.getFSphere(3, 2, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(1).isExact(factory.getFSphere(1, 4, 3))),
                    () -> assertTrue(fAggregate.getRefParticles().asList().get(2).isExact(factory.getFSphere(1, 2, 5)))
            );
        }

        @Test
        @DisplayName("Set radius with primitives")
        void setRadiusWithPrimitives() {
            int quantity = 100;
            double size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 10);
            FModel fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);

            fModel.build();

            FPoint center = factory.getFPoint();

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerA = center.copy();
            double radiusA = fAggregate.getRadiusFrom(centerA);

            fAggregate.setRadiusFrom(center.getX(), center.getY(), center.getZ(), size);

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerB = center.copy();
            double radiusB = fAggregate.getRadiusFrom(centerB);

            assertTrue(centerA.isSimilar(centerB));
            assertTrue(radiusA > radiusB);
            assertEquals(size, radiusB, epsilon);
            assertTrue(fAggregate.isConnected());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Set radius with FPoint")
        void setRadiusWithFPoint() {
            int quantity = 100;
            double size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 10);
            FModel fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);

            fModel.build();

            FPoint center = factory.getFPoint();

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerA = center.copy();
            double radiusA = fAggregate.getRadiusFrom(centerA);

            fAggregate.setRadiusFrom(center, size);

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerB = center.copy();
            double radiusB = fAggregate.getRadiusFrom(centerB);

            assertTrue(centerA.isSimilar(centerB));
            assertTrue(radiusA > radiusB);
            assertEquals(size, radiusB, epsilon);
            assertTrue(fAggregate.isConnected());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Set radius with FPos3D")
        void setRadiusWithFPos3D() {
            int quantity = 100;
            double size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 10);
            FModel fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);

            fModel.build();

            FPoint center = factory.getFPoint();

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerA = center.copy();
            double radiusA = fAggregate.getRadiusFrom(centerA);

            fAggregate.setRadiusFrom(center.toFPos3D(), size);

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerB = center.copy();
            double radiusB = fAggregate.getRadiusFrom(centerB);

            assertTrue(centerA.isSimilar(centerB));
            assertTrue(radiusA > radiusB);
            assertEquals(size, radiusB, epsilon);
            assertTrue(fAggregate.isConnected());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("Set radius with center type")
        void setRadiusWithCenterType() {
            int quantity = 100;
            double size = 10;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 10);
            FModel fModel = factory.getFModelContext().pc().tunable(fAggregate, 1.8, 1.6);

            fModel.build();

            FPoint center = factory.getFPoint();

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerA = center.copy();
            double radiusA = fAggregate.getRadiusFrom(centerA);

            fAggregate.setRadiusFrom(Center.SPATIAL, size);

            fAggregate.getCenter(center, Center.SPATIAL);

            FPoint centerB = center.copy();
            double radiusB = fAggregate.getRadiusFrom(centerB);

            assertTrue(centerA.isSimilar(centerB));
            assertTrue(radiusA > radiusB);
            assertEquals(size, radiusB, epsilon);
            assertTrue(fAggregate.isConnected());
            assertTrue(fAggregate.isNonOverlapping());
        }

        @Test
        @DisplayName("PCA")
        void pca() {
            FAggregate aggregate = factory.getFAggregate();

            for (int i = 0; i < 100; i += 2) {
                aggregate.addRefParticle(factory.getFSphere(0, i, 0));
            }

            FPos3D lengthBefore = aggregate.getLength();

            aggregate.pca();

            FPos3D lengthAfter = aggregate.getLength();

            assertTrue(lengthAfter.getD0() > lengthBefore.getD0());
            assertEquals(2, lengthAfter.getD1(), epsilon);
            assertEquals(2, lengthAfter.getD2(), epsilon);
        }

        @Test
        @DisplayName("PCA - Aggregate")
        void pcaAggregate() {
            FAggregate aggregate = factory.getFAggregateContext().base().polydisperse(100, 10, 1);
            FModel model = factory.getFModelContext().cc().ballistic(aggregate);

            model.build();

            aggregate.pca();

            FMatrix3x3D tensor = aggregate.getGyrationTensor(GyrationTensor.SIMPLE_POLY);

            assertEquals(0.0, tensor.get0x1(), 1e-5, "XY correlation must be zero");
            assertEquals(0.0, tensor.get0x2(), 1e-5, "XZ correlation must be zero");
            assertEquals(0.0, tensor.get1x2(), 1e-5, "YZ correlation must be zero");

            assertTrue(tensor.get0x0() >= tensor.get1x1(), "X variance should be >= Y variance");
            assertTrue(tensor.get1x1() >= tensor.get2x2(), "Y variance should be >= Z variance");

            assertTrue(aggregate.isPointConnected());
        }
    }

    @Nested
    @Tag("Topology")
    @DisplayName("Topology")
    class FAggregateTopologyTest {

        @Nested
        @Tag("Topology")
        @DisplayName("Box counting")
        class BoxCountingTest {

            @Test
            @DisplayName("Get box dimension")
            void getBoxDimension() {
                String schema14 = F3D_N1000_Mono.get_14_18();
                String schema18 = F3D_N1000_Mono.get_18_14();
                String schema22 = F3D_N1000_Mono.get_22_10();

                FAggregate fAggregate14 = factory.getFAggregate(schema14);
                FAggregate fAggregate18 = factory.getFAggregate(schema18);
                FAggregate fAggregate22 = factory.getFAggregate(schema22);

                double dim14 = fAggregate14.getFractalDimension(FractalDimension.BC_SIMPLIFIED);
                double dim18 = fAggregate18.getFractalDimension(FractalDimension.BC_SIMPLIFIED);
                double dim22 = fAggregate22.getFractalDimension(FractalDimension.BC_SIMPLIFIED);

                assertEquals(1.4, dim14, 0.2);
                assertEquals(1.8, dim18, 0.2);
                assertEquals(2.2, dim22, 0.2);
            }

            @Test
            @DisplayName("Get box dimension - Basic geometry")
            void getBoxDimensionBasicGeometry() {
                FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(50);
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

                double dim1d = fAggregate1d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);
                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);

                assertEquals(1, dim1d, 1 * 0.05);
                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get box dimension - Basic geometry (asymmetric)")
            void getBoxDimensionBasicGeometryAsymmetric() {
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(15, 17);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(15, 17, 19);

                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);

                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get box dimension - Sphere")
            void getBoxDimensionSphereGeometry() {
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2Hex(30, 1);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3Hex(20, 1);

                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.BC_SIMPLIFIED);

                assertEquals(2, dim2d, 0.10);
                assertEquals(3, dim3d, 0.25);
            }
        }

        @Nested
        @Tag("Topology")
        @DisplayName("Density correlation")
        class DensityCorrelationTest {

            @Test
            @DisplayName("Get density dimension")
            void getDensityDimension() {
                String schema14 = F3D_N1000_Mono.get_14_18();
                String schema18 = F3D_N1000_Mono.get_18_14();
                String schema22 = F3D_N1000_Mono.get_22_10();

                FAggregate fAggregate14 = factory.getFAggregate(schema14);
                FAggregate fAggregate18 = factory.getFAggregate(schema18);
                FAggregate fAggregate22 = factory.getFAggregate(schema22);

                double dim14 = fAggregate14.getFractalDimension(FractalDimension.CORRELATION);
                double dim18 = fAggregate18.getFractalDimension(FractalDimension.CORRELATION);
                double dim22 = fAggregate22.getFractalDimension(FractalDimension.CORRELATION);

                assertEquals(1.4, dim14, 1.4 * 0.1);
                assertEquals(1.8, dim18, 1.8 * 0.1);
                assertEquals(2.2, dim22, 2.2 * 0.1);
            }

            @Test
            @DisplayName("Get density dimension - Basic geometry")
            void getDensityDimensionBasicGeometry() {
                FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(50);
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

                double dim1d = fAggregate1d.getFractalDimension(FractalDimension.CORRELATION);
                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.CORRELATION);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.CORRELATION);

                assertEquals(1, dim1d, 1 * 0.05);
                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get density dimension - Basic geometry (asymmetric)")
            void getDensityDimensionBasicGeometryAsymmetric() {
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(15, 17);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(15, 17, 19);

                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.CORRELATION);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.CORRELATION);

                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get density dimension - Basic geometry (translated)")
            void getDensityDimensionBasicGeometryTranslated() {
                FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(50);
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

                fAggregate1d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));
                fAggregate2d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));
                fAggregate3d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));

                double dim1d = fAggregate1d.getFractalDimension(FractalDimension.CORRELATION);
                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.CORRELATION);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.CORRELATION);

                assertEquals(1, dim1d, 1 * 0.05);
                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get density dimension - Sphere")
            void getDensityDimensionSphereGeometry() {
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2Hex(30, 1);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3Hex(20, 1);

                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.CORRELATION);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.CORRELATION);

                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }
        }

        @Nested
        @Tag("Topology")
        @DisplayName("Mass radius")
        class MassRadiusTest {

            @Test
            @DisplayName("Get mass dimension")
            void getMassDimension() {
                String schema14 = F3D_N1000_Mono.get_14_18();
                String schema18 = F3D_N1000_Mono.get_18_14();
                String schema22 = F3D_N1000_Mono.get_22_10();

                FAggregate fAggregate14 = factory.getFAggregate(schema14);
                FAggregate fAggregate18 = factory.getFAggregate(schema18);
                FAggregate fAggregate22 = factory.getFAggregate(schema22);

                double dim14 = fAggregate14.getFractalDimension(FractalDimension.MASS);
                double dim18 = fAggregate18.getFractalDimension(FractalDimension.MASS);
                double dim22 = fAggregate22.getFractalDimension(FractalDimension.MASS);

                assertEquals(1.4, dim14, 0.2);
                assertEquals(1.8, dim18, 0.2);
                assertEquals(2.2, dim22, 0.2);
            }

            @Test
            @DisplayName("Get mass dimension - Basic geometry")
            void getMassDimensionBasicGeometry() {
                FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(50);
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

                double dim1d = fAggregate1d.getFractalDimension(FractalDimension.MASS);
                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.MASS);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.MASS);

                assertEquals(1, dim1d, 1 * 0.05);
                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get mass dimension - Basic geometry (asymmetric)")
            void getMassDimensionBasicGeometryAsymmetric() {
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(15, 17);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(15, 17, 19);

                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.MASS);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.MASS);

                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get mass dimension - Basic geometry (translated)")
            void getMassDimensionBasicGeometryTranslated() {
                FAggregate fAggregate1d = factory.getFAggregateContext().geometry().d1(50);
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2(25, 25);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3(20, 20, 20);

                fAggregate1d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));
                fAggregate2d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));
                fAggregate3d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));

                double dim1d = fAggregate1d.getFractalDimension(FractalDimension.MASS);
                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.MASS);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.MASS);

                assertEquals(1, dim1d, 1 * 0.05);
                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }

            @Test
            @DisplayName("Get mass dimension - Sphere")
            void getMassDimensionSphereGeometry() {
                FAggregate fAggregate2d = factory.getFAggregateContext().geometry().d2Hex(30, 1);
                FAggregate fAggregate3d = factory.getFAggregateContext().geometry().d3Hex(20, 1);

                double dim2d = fAggregate2d.getFractalDimension(FractalDimension.MASS);
                double dim3d = fAggregate3d.getFractalDimension(FractalDimension.MASS);

                assertEquals(2, dim2d, 2 * 0.05);
                assertEquals(3, dim3d, 3 * 0.05);
            }
        }
    }
}
