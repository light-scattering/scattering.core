package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.extension.Producer;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.design.physics.material.FMaterial;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.statistics.construct.FPlot;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.layer.FLayer;
import eu.scattering.core.design.storage.mesh.FMesh;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.transfer.primitive.FPairPos3D;
import eu.scattering.core.design.transfer.primitive.FPos3D;
import eu.scattering.core.mock.aggregate.F3D_N1000_Mono;
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
                    () -> assertNull(fAggregate.getRefFBuffer()),
                    () -> assertNull(fAggregate.getRefFMaterial())
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
                    () -> assertNull(fAggregate.getRefFBuffer()),
                    () -> assertNull(fAggregate.getRefFMaterial())
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
        @DisplayName("Set reference particles")
        void setReferenceParticles() {
            FAggregate fAggregateA = factory.getFAggregate();

            FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
            FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
            FSphere fSphereC = factory.getFSphere(0, 2, 0, 1);
            FSphere fSphereD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregateB = fAggregateA.setRefParticles(fAssemblyA);

            FAssembly<Shape> fAssemblyB = fAggregateA.getRefParticles();

            Assertions.assertAll("Validate FAggregate",
                    () -> assertSame(fAggregateA, fAggregateB,
                            "The reference not should change"),
                    () -> assertSame(fAssemblyA, fAssemblyB,
                            "The reference should not change")
            );
        }

        @Test
        @DisplayName("Add FBuffer")
        void addFBuffer() {
            FAggregate fAggregateA = factory.getFAggregate();

            assertNull(fAggregateA.getRefFBuffer());

            FAggregate fAggregateB = fAggregateA.addFBuffer(100);

            assertSame(fAggregateA, fAggregateB);
            assertEquals(100, fAggregateA.getRefFBuffer().capacity());
            assertEquals(0, fAggregateA.getRefFBuffer().size());
        }

        @Test
        @DisplayName("Validate FBuffer")
        void validateFBuffer() {
            FBuffer<FBufferData> fBufferA = factory.getFBuffer(123);

            FAggregate fAggregateA = factory.getFAggregate();

            FAggregate fAggregateB = fAggregateA.setRefFBuffer(fBufferA);

            FBuffer<FBufferData> fBufferB = fAggregateA.getRefFBuffer();

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

            assertNull(fAggregateA.getRefFMaterial());

            FAggregate fAggregateB = fAggregateA.addFMaterial();

            fAggregateA.getRefFMaterial().setDensity("X", 1);

            assertSame(fAggregateA, fAggregateB);
            assertEquals(2, fAggregateA.getRefFMaterial().size());
        }

        @Test
        @DisplayName("Validate FMaterial")
        void validateFMaterial() {
            FMaterial fMaterialA = factory.getFMaterial();

            FAggregate fAggregateA = factory.getFAggregate();

            FAggregate fAggregateB = fAggregateA.setRefFMaterial(fMaterialA);

            FMaterial fMaterialB = fAggregateA.getRefFMaterial();

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
            FAggregate fAggregate = factory.getFAggregatePreMono(10, 1);

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
            FAggregate fAggregate = factory.getFAggregatePreMono(10, 1).addFBuffer(10).addFMaterial();
            fAggregate.getRefFMaterial().setDensity("A", 3);
            fAggregate.getRefFMaterial().setDensity("B", 6);
            fAggregate.getRefFMaterial().setRefIndexRe("C", 3);
            fAggregate.getRefFMaterial().setRefIndexIm("D", 6);
            fAggregate.getRefFMaterial().setRefIndex("E", 4, 8);

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
            FAggregate fAggregateA = factory.getFAggregate().addFBuffer(10).addFMaterial();
            FAggregate fAggregateB = factory.getFAggregate().addFBuffer(10).addFMaterial();

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

            fAggregateA.setRefParticles(fAssemblyA);
            fAggregateB.setRefParticles(fAssemblyB);

            fAggregateA.getRefFMaterial().setDensity("X", 5);
            fAggregateB.getRefFMaterial().setDensity("X", 5);

            assertTrue(fAggregateA.isExact(fAggregateB));
            assertTrue(fAggregateB.isExact(fAggregateA));
            assertTrue(fAggregateA.isExactData(fAggregateB));
            assertTrue(fAggregateB.isExactData(fAggregateA));

            fAggregateB.getRefFMaterial().setDensity("X", 6);

            assertFalse(fAggregateA.isExact(fAggregateB));
            assertFalse(fAggregateB.isExact(fAggregateA));
            assertTrue(fAggregateA.isExactData(fAggregateB));
            assertTrue(fAggregateB.isExactData(fAggregateA));

            fAggregateB.getRefFMaterial().setDensity("X", 5);
            fAggregateB.getRefParticles().asList().get(0).setRadius(2);

            assertFalse(fAggregateA.isExact(fAggregateB));
            assertFalse(fAggregateB.isExact(fAggregateA));
            assertFalse(fAggregateA.isExactData(fAggregateB));
            assertFalse(fAggregateB.isExactData(fAggregateA));
        }

        @Test
        @DisplayName("Copy")
        void copy() {
            FAggregate fAggregateA = factory.getFAggregate().addFBuffer(10).addFMaterial();

            Shape fSphereAA = factory.getFSphere(0, 0, 0, 1);
            Shape fSphereAB = factory.getFSphere(2, 0, 0, 1);
            Shape fSphereAC = factory.getFSphere(0, 2, 0, 1);
            Shape fSphereAD = factory.getFSphere(0, 0, 2, 1);

            FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereAA, fSphereAB, fSphereAC, fSphereAD));

            fAggregateA.setRefParticles(fAssemblyA);

            fAggregateA.getRefFMaterial().setDensity("X", 5);

            FAggregate fAggregateB = fAggregateA.copy();

            assertNotSame(fAggregateA, fAggregateB);
            assertTrue(fAggregateA.isExact(fAggregateB));
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

            double volActual = fAggregate.getVolume();
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
            Producer<FPoint> fPointProd = factory.getFPointProducer(20, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1).validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(50));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double volActual = fAggregate.getVolume();
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

            double volActual = fAggregate.getVolume();
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

            double volActual = fAggregate.getVolume();
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

            double volActual = fAggregate.getVolume();
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

            double volActual = fAggregate.getVolume();
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
            double volActual = fAggregate.getVolume(volLayers);

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

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .setDelta(delta)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphere fSphereRef = fSphereProd.produce();

            double[] volLayers = new double[3];
            double volActual = fAggregate.getVolume(volLayers);

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
            double volActual = fAggregate.getVolume(volLayers);

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
            double volActualA = fAggregate.getVolume(volLayers);
            double volActualB = fAggregate.getVolume();

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
            double volActualB = fAggregate.getVolume();

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

            double srfActual = fAggregate.getSurface();
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
            Producer<FPoint> fPointProd = factory.getFPointProducer(20, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1).validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(50));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfActual = fAggregate.getSurface();
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

            double srfActual = fAggregate.getSurface();
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

            double srfActual = fAggregate.getSurface();
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

            double srfActual = fAggregate.getSurface();
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
            double srfActual = fAggregate.getSurface(srfLayers);

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

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .setDelta(delta)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphere fSphereRef = fSphereProd.produce();

            double[] srfLayers = new double[3];
            double srfActual = fAggregate.getSurface(srfLayers);

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
            double srfActual = fAggregate.getSurface(srfLayers);

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
            fAggregate.getSurface(srfLayers);

            double srfActualLayer3 = fAggregate.getSurface();
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
        @DisplayName("Get overlap factor - Same position - Double")
        void getOverlapFactorSamePositionDouble() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.VOLUMETRIC);

            assertEquals(1, overlap, epsilon);
        }

        @Test
        @DisplayName("Get overlap factor - Same position - Triple")
        void getOverlapFactorSamePositionTriple() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();
            FSphere fSphereC = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.VOLUMETRIC);

            assertEquals(1, overlap, epsilon);
        }

        @Test
        @DisplayName("Get overlap factor - Distant")
        void getOverlapFactorDistant() {
            FSphere fSphereA = factory.getFSphere(-1, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.VOLUMETRIC);

            assertEquals(0, overlap, epsilon);
        }

        @Test
        @DisplayName("Get overlap factor - Intersecting")
        void getOverlapFactorIntersecting() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.VOLUMETRIC);

            double volAlgOverlap = 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);
            double volAlgTotal = 2 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double relError = factory.getStatisticsHelper().getRelErr(volAlgOverlap / volAlgTotal, overlap);

            assertTrue(relError < 0.01);
        }

        @Test
        @DisplayName("Get overlap factor - Field")
        void getOverlapFactorField() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 5, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 5);
            FSphere fSphereE = factory.getFSphere(0, 0, 5, 2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.VOLUMETRIC);

            double volAlgOverlap = (4  * Math.PI / 3) +
                    2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);
            double volAlgTotal = (4  * Math.PI * Math.pow(2, 3) / 3) +
                    3 * (4  * Math.PI / 3) -
                    2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

            double relError = factory.getStatisticsHelper().getRelErr(volAlgOverlap / volAlgTotal, overlap);

            assertTrue(relError < 0.01);
        }

        @Test
        @DisplayName("Get overlap factory linear - Same position")
        void getOverlapFactorLinearSamePosition() {
            FSphere fSphereA = factory.getFSphere();
            FSphere fSphereB = factory.getFSphere();

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.LINEAR);

            assertEquals(1, overlap, epsilon);
        }

        @Test
        @DisplayName("Get overlap factory linear - Distant")
        void getOverlapFactorLinearDistant() {
            FSphere fSphereA = factory.getFSphere(-2, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.LINEAR);

            assertEquals(0, overlap, epsilon);
        }

        @Test
        @DisplayName("Get overlap factory linear - Intersecting")
        void getOverlapFactorLinearIntersecting() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.LINEAR);

            assertEquals(0.5, overlap, epsilon);
        }

        @Test
        @DisplayName("Get overlap factory linear - Field")
        void getOverlapFactorLinearField() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(1, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 3, 0);
            FSphere fSphereD = factory.getFSphere(0, 4, 0);
            FSphere fSphereE = factory.getFSphere(5, 5, 5);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double overlap = fAggregate.getOverlapFactor(FAggregate.OF.LINEAR);

            double relError = factory.getStatisticsHelper().getRelErr(0.5, overlap);

            assertTrue(relError < 0.01);
        }

        @Test
        @DisplayName("Is compact")
        void isCompact() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 2);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertTrue(fAggregate.isCompact());
        }

        @Test
        @DisplayName("Is compact - Fail")
        void isCompactFail() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 2);
            FSphere fSphereE = factory.getFSphere(5, 5, 5);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isCompact());
        }

        @Test
        @DisplayName("Is compact - Empty")
        void isCompactEmpty() {
            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of());

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isCompact());
        }

        @Test
        @DisplayName("Is sparse")
        void isSparse() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 4);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertTrue(fAggregate.isSparse());
        }

        @Test
        @DisplayName("Is sparse - Fail")
        void isSparseFail() {
            FSphere fSphereA = factory.getFSphere(0, 0, 0);
            FSphere fSphereB = factory.getFSphere(2, 0, 0);
            FSphere fSphereC = factory.getFSphere(0, 2, 0);
            FSphere fSphereD = factory.getFSphere(0, 0, 1);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isSparse());
        }

        @Test
        @DisplayName("Is sparse - Empty")
        void isSparseEmpty() {
            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of());

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            assertFalse(fAggregate.isSparse());
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
            double lengthX = fAggregate.getLength(FAggregate.Axis.X);
            double lengthY = fAggregate.getLength(FAggregate.Axis.Y);
            double lengthZ = fAggregate.getLength(FAggregate.Axis.Z);
            double lengthMax = fAggregate.getLength(FAggregate.Axis.MAX);
            double lengthMin = fAggregate.getLength(FAggregate.Axis.MIN);


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

            fAggregate.getMassCenter(center);

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

            fAggregate.getMassCenter(center);

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

            fAggregate.getMassCenter(center);

            double volA = factory.getFSphereHelper().getVolume(1);
            double volB = factory.getFSphereHelper().getVolume(3);
            double d0 = ((1 * volA) + (-4 * volB)) / (volA + volB);
            double d1 = ((2 * volA) + (-5 * volB)) / (volA + volB);
            double d2 = ((3 * volA) + (-6 * volB)) / (volA + volB);

            Assertions.assertAll("Validate mass center",
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

            FPos3D center = fAggregate.getMassCenter();

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

            fAggregate.getSphericalCenter(center);

            Assertions.assertAll("Validate spatial center",
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

            FPos3D center = fAggregate.getSphericalCenter();

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

            fAggregate.getSpatialCenter(center);

            double d0 = (2 - 7) / 2d;
            double d1 = (3 - 8) / 2d;
            double d2 = (4 - 9) / 2d;

            Assertions.assertAll("Validate spatial center",
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
        @DisplayName("Position center with FPoint")
        void positionCenterFPoint() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPoint centerBefore = factory.getFPoint();

            fAggregate.getMassCenter(centerBefore);

            assertFalse(centerBefore.isSimilar(0, 0, 0));

            FPoint centerAfter = factory.getFPoint();

            fAggregate.positionCenter(centerBefore);
            fAggregate.getMassCenter(centerAfter);

            assertTrue(centerAfter.isSimilar(0, 0, 0));
        }

        @Test
        @DisplayName("Position center with FPos3D")
        void positionCenterFPos3D() {
            Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
            Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FPos3D centerBefore = fAggregate.getMassCenter();

            assertFalse(factory.getFPointHelper().isSimilar(centerBefore.getD0(), centerBefore.getD1(), centerBefore.getD2(), 0, 0, 0));

            factory.getFPoint();

            fAggregate.positionCenter(centerBefore);

            FPos3D centerAfter = fAggregate.getMassCenter();

            assertTrue(factory.getFPointHelper().isSimilar(centerAfter.getD0(), centerAfter.getD1(), centerAfter.getD2(), 0, 0, 0));
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

            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadius(massCenter.getX(), massCenter.getY(), massCenter.getZ());
            double radiusB = fAggregate.getRadiusFromOrigin();

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.positionCenter(massCenter);
            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadius(massCenter.getX(), massCenter.getY(), massCenter.getZ());
            radiusB = fAggregate.getRadiusFromOrigin();

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

            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadius(massCenter);
            double radiusB = fAggregate.getRadiusFromOrigin();

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.positionCenter(massCenter);
            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadius(massCenter);
            radiusB = fAggregate.getRadiusFromOrigin();

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

            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(3, 3, 3));

            double radiusA = fAggregate.getRadius(massCenter.toFPos3D());
            double radiusB = fAggregate.getRadiusFromOrigin();

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertNotEquals(radiusA, radiusB);

            fAggregate.positionCenter(massCenter);
            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            radiusA = fAggregate.getRadius(massCenter.toFPos3D());
            radiusB = fAggregate.getRadiusFromOrigin();

            assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
            assertEquals(radiusA, radiusB, 1E-6);
        }

        @Test
        @DisplayName("Get volume radius - Single")
        void getVolumeRadiusSingle() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double rExpected = factory.getFSphereHelper().getVolumeRadius(fAggregate.getVolume());
            double rActual = fAggregate.getVolumeRadius();

            double rErr = factory.getStatisticsHelper().getRelErr(rExpected, rActual);

            assertTrue(rErr < 0.01);
        }

        @Test
        @DisplayName("Get volume radius")
        void getVolumeRadius() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphereHelper helper = factory.getFSphereHelper();

            double[] layers = new double[3];
            double volExpected = fAggregate.getVolume(layers);

            double rExpectedLayer0 = helper.getVolumeRadius(layers[0]);
            double rExpectedLayer1 = helper.getVolumeRadius(layers[0] + layers[1]);
            double rExpectedLayer2 = helper.getVolumeRadius(layers[0] + layers[1] + layers[2]);
            double rExpected = helper.getVolumeRadius(volExpected);

            double rActual = fAggregate.getVolumeRadius(layers);

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

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            double srfExpected = fAggregate.getSurface();

            double rExpected = factory.getFSphereHelper().getSurfaceRadius(srfExpected);
            double rActual = fAggregate.getSurfaceRadius();

            double rErr = factory.getStatisticsHelper().getRelErr(rExpected, rActual);

            assertTrue(rErr < 0.01);
        }

        @Test
        @DisplayName("Get surface radius")
        void getSurfaceRadius() {
            int quantity = 25;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .correctAddCoat(1, 1)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

            FSphereHelper helper = factory.getFSphereHelper();

            double[] layers = new double[3];
            double srfExpected = fAggregate.getSurface(layers);

            double rExpectedLayer0 = helper.getSurfaceRadius(layers[0]);
            double rExpectedLayer1 = helper.getSurfaceRadius(layers[1]);
            double rExpectedLayer2 = helper.getSurfaceRadius(layers[2]);
            double rExpected = helper.getSurfaceRadius(srfExpected);

            double rActual = fAggregate.getSurfaceRadius(layers);

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
            fAggregate.getMassCenter(massCenter);

            assertTrue(massCenter.isSimilar(0, 0, 0));

            double rgExpected = factory.getFSphereHelper().getRadiusOfGyration(radius);
            double rgActual = fAggregate.getRadiusOfGyration(FAggregate.RoG.COMPLEX);

            double rgErr = factory.getStatisticsHelper().getRelErr(rgExpected, rgActual);

            assertTrue(rgErr < 0.005);
        }

        @Test
        @DisplayName("Get radius of gyration (legacy)")
        void getRadiusOfGyrationLegacy() {
            int quantity = 25;
            double delta = 0.1;

            Producer<FPoint> fPointProd = factory.getFPointProducer(50, FPointProducer.Location.IN_SPHERE);
            Producer<FSphere> fSphereProd = factory.getFSphereProducer(fPointProd, 1)
                    .setDelta(delta)
                    .validateNoOverlap();

            FAssembly<Shape> fAssembly = factory.getFAssembly(fSphereProd.getListRandomized(quantity));

            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFBuffer(1_000_000).addFMaterial();

            double rgDefault = fAggregate.getRadiusOfGyration(FAggregate.RoG.COMPLEX);
            double rgLegacyMono = fAggregate.getRadiusOfGyration(FAggregate.RoG.SIMPLE_MONO);
            double rgLegacyPoly = fAggregate.getRadiusOfGyration(FAggregate.RoG.SIMPLE_POLY);
            double rgLegacyFilippov = fAggregate.getRadiusOfGyration(FAggregate.RoG.SIMPLE_FILIPPOV);

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

            fAggregate.getRefFMaterial().setDensity("X", 5);

            assertEquals(5, fAggregate.getRefFMaterial().getDensity("X"));
        }

        @Test
        @DisplayName("Set material refractive index")
        void setMaterialRefractiveIndex() {
            FAssembly<Shape> fAssembly = factory.getFAssembly(List.of());
            FAggregate fAggregate = factory.getRefFAggregate(fAssembly).addFMaterial();

            fAggregate.getRefFMaterial().setRefIndex("X", 2, 3);

            assertEquals(2, fAggregate.getRefFMaterial().getRefIndexRe("X"));
            assertEquals(3, fAggregate.getRefFMaterial().getRefIndexIm("X"));
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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FPlot results = fAggregate.getPairDistanceFunction();
            results.mutateFStatY(FStat::distribute);

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
            results.mutateFStatY(FStat::distribute);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FPlot results = fAggregate.getCoordinationNumberFunction();
            results.mutateFStatY(FStat::distribute);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FPlot results = fAggregate.getDensityCorrelationFunction(false);
            results.mutateFStatY(FStat::distribute);

            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertTrue(results.getY(1) < results.getY(0));
            assertTrue(results.getY(2) < results.getY(1));
        }

        @Test
        @DisplayName("Density correlation - Function B")
        void getDensityCorrelationFunctionB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FPlot results = fAggregate.getDensityCorrelationFunction(true);

            assertTrue(results.size() > 25);
        }

        @Test
        @DisplayName("Box coverage - Function A")
        void getBoxCoverageFunctionA() {
            Shape shape = factory.getFSphere(0, 0, 0, 1);
            Shape shapeA = factory.getFSphere(2, 0, 0, 1);
            Shape shapeB = factory.getFSphere(-2, 0, 0, 1);
            Shape shapeC = factory.getFSphere(0, 2, 0, 1);
            Shape shapeD = factory.getFSphere(0, -2, 0, 1);
            Shape shapeE = factory.getFSphere(0, 0, 2, 1);
            Shape shapeF = factory.getFSphere(0, 0, -2, 1);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shape, shapeA, shapeB, shapeC, shapeD, shapeE, shapeF));
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FPlot results = fAggregate.getBoxCoverageFunction(false);
            results.mutateFStatY(FStat::distribute);

            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertTrue(results.size() >= 5);
        }

        @Test
        @DisplayName("Box coverage - Function B")
        void getBoxCoverageFunctionB() {
            FAggregate fAggregate = factory.getFAggregate(F3D_N1000_Mono.get_18_14());

            FPlot results = fAggregate.getBoxCoverageFunction(true);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FPlot results = fAggregate.getTripletAngleFunction();
            results.mutateFStatY(FStat::distribute);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FPlot results = fAggregate.getTripletAngleFunction();

            FTrigHelper helper = factory.getFTrigHelper();
            results.mutateX((x, y) -> helper.convertRadToDeg(x));

            results.mutateFStatY(FStat::distribute);

            results.filter((x, y) -> y > 0);

            assertEquals(2, results.size());
            assertEquals(1, results.getRefCoreY().sum(), 1E-4);
            assertEquals(90, results.getX(0), 1E-4);
            assertEquals(180, results.getX(1), 1E-4);
            assertTrue(results.getY(0) > results.getY(1));
        }

        @Test
        @DisplayName("Get particle radius")
        void getParticleRadius() {
            Shape shapeA = factory.getFSphere(1);
            Shape shapeB = factory.getFSphere(2);
            Shape shapeC = factory.getFSphere(3);
            Shape shapeD = factory.getFSphere(4);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shapeA, shapeB, shapeC, shapeD));
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            FStat stat = fAggregate.getParticleRadius();

            assertEquals(4, stat.size());
            assertEquals(1, stat.min());
            assertEquals(4, stat.max());
        }

        @Test
        @DisplayName("Set epsilon")
        void setEpsilon() {
            Shape shapeA = factory.getFSphere(1);
            Shape shapeB = factory.getFSphere(2);
            Shape shapeC = factory.getFSphere(3);
            Shape shapeD = factory.getFSphere(4);

            FAssembly<Shape> core = factory.getFAssembly(List.of(shapeA, shapeB, shapeC, shapeD));
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            fAggregate.setEpsilon(1);

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
            FAggregate fAggregate = factory.getFAggregate();

            fAggregate.setRefParticles(core);

            fAggregate.setDelta(1);

            fAggregate.getRefParticles().forEach(e -> assertEquals(1, e.getDelta()));
        }
    }

    @Nested
    @Tag("Topology")
    @DisplayName("Topology")
    class FAggregateTopologyTest {

        @Test
        @DisplayName("Get box dimension")
        void getBoxDimension() {
            String schema14 = F3D_N1000_Mono.get_14_18();
            String schema18 = F3D_N1000_Mono.get_18_14();
            String schema22 = F3D_N1000_Mono.get_22_10();

            FAggregate fAggregate14 = factory.getFAggregate(schema14);
            FAggregate fAggregate18 = factory.getFAggregate(schema18);
            FAggregate fAggregate22 = factory.getFAggregate(schema22);

            double dim14 = fAggregate14.getFractalDimension(FAggregate.Dim.BOX);
            double dim18 = fAggregate18.getFractalDimension(FAggregate.Dim.BOX);
            double dim22 = fAggregate22.getFractalDimension(FAggregate.Dim.BOX);

            assertEquals(1.4, dim14, 0.2);
            assertEquals(1.8, dim18, 0.2);
            assertEquals(2.2, dim22, 0.2);
        }

        @Test
        @DisplayName("Get box dimension - Basic geometry")
        void getBoxDimensionBasicGeometry() {
            FAggregate fAggregate1d = factory.getFAggregateGeo1d(10);
            FAggregate fAggregate2d = factory.getFAggregateGeo2d(10, 10);
            FAggregate fAggregate3d = factory.getFAggregateGeo3d(10, 10, 10);

            double dim1d = fAggregate1d.getFractalDimension(FAggregate.Dim.BOX);
            double dim2d = fAggregate2d.getFractalDimension(FAggregate.Dim.BOX);
            double dim3d = fAggregate3d.getFractalDimension(FAggregate.Dim.BOX);

            assertEquals(1, dim1d, 0.01);
            assertEquals(2, dim2d, 0.01);
            assertEquals(3, dim3d, 0.01);
        }

        @Test
        @DisplayName("Get box dimension - Basic geometry (asymmetric)")
        void getBoxDimensionBasicGeometryAsymmetric() {
            FAggregate fAggregate1d = factory.getFAggregateGeo1d(9);
            FAggregate fAggregate2d = factory.getFAggregateGeo2d(9, 11);
            FAggregate fAggregate3d = factory.getFAggregateGeo3d(9, 11, 13);

            double dim1d = fAggregate1d.getFractalDimension(FAggregate.Dim.BOX);
            double dim2d = fAggregate2d.getFractalDimension(FAggregate.Dim.BOX);
            double dim3d = fAggregate3d.getFractalDimension(FAggregate.Dim.BOX);

            assertEquals(1, dim1d, 0.2);
            assertEquals(2, dim2d, 0.3);
            assertEquals(3, dim3d, 0.4);
        }

        @Test
        @DisplayName("Get box dimension - Basic geometry (translated)")
        void getBoxDimensionBasicGeometryTranslated() {
            FAggregate fAggregate1d = factory.getFAggregateGeo1d(10);
            FAggregate fAggregate2d = factory.getFAggregateGeo2d(10, 10);
            FAggregate fAggregate3d = factory.getFAggregateGeo3d(10, 10, 10);

            fAggregate1d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));
            fAggregate2d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));
            fAggregate3d.getRefParticles().translate(factory.getFRand().nextDoubleInSphere(5));

            double dim1d = fAggregate1d.getFractalDimension(FAggregate.Dim.BOX);
            double dim2d = fAggregate2d.getFractalDimension(FAggregate.Dim.BOX);
            double dim3d = fAggregate3d.getFractalDimension(FAggregate.Dim.BOX);

            assertEquals(1, dim1d, 0.01);
            assertEquals(2, dim2d, 0.01);
            assertEquals(3, dim3d, 0.01);
        }

        @Test
        @DisplayName("Get box dimension - Sphere")
        void getBoxDimensionSphereGeometry() {
            FAggregate fAggregate1d = factory.getFAggregateGeoFullCircle(10);
            FAggregate fAggregate2d = factory.getFAggregateGeoFullSphere(10);

            double dim1d = fAggregate1d.getFractalDimension(FAggregate.Dim.BOX);
            double dim2d = fAggregate2d.getFractalDimension(FAggregate.Dim.BOX);

            assertEquals(2, dim1d, 0.10);
            assertEquals(3, dim2d, 0.25);
        }

        @Test
        @DisplayName("Get density dimension")
        void getDensityDimension() {
            String schema14 = F3D_N1000_Mono.get_14_18();
            String schema18 = F3D_N1000_Mono.get_18_14();
            String schema22 = F3D_N1000_Mono.get_22_10();

            FAggregate fAggregate14 = factory.getFAggregate(schema14);
            FAggregate fAggregate18 = factory.getFAggregate(schema18);
            FAggregate fAggregate22 = factory.getFAggregate(schema22);

            double dim14 = fAggregate14.getFractalDimension(FAggregate.Dim.CORRELATION);
            double dim18 = fAggregate18.getFractalDimension(FAggregate.Dim.CORRELATION);
            double dim22 = fAggregate22.getFractalDimension(FAggregate.Dim.CORRELATION);

            assertEquals(1.4, dim14, 0.2);
            assertEquals(1.8, dim18, 0.2);
            assertEquals(2.2, dim22, 0.2);
        }

        @Test
        @DisplayName("Get density dimension - Sphere")
        void getDensityDimensionSphereGeometry() {
            FAggregate fAggregate2d = factory.getFAggregateGeoFullCircle(10);
            FAggregate fAggregate3d = factory.getFAggregateGeoFullSphere(10);

            double dim2d = fAggregate2d.getFractalDimension(FAggregate.Dim.CORRELATION);
            double dim3d = fAggregate3d.getFractalDimension(FAggregate.Dim.CORRELATION);

            assertEquals(2, dim2d, 0.10);
            assertEquals(3, dim3d, 0.25);
        }

    }
}
