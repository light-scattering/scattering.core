package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereHelper;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FAggregate")
public class FAggregateTest {

    @Test
    @DisplayName("Construct")
    void construct() {
        FAggregate fAggregate = factory.getFAggregate();

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(1000000, fAggregate.getRefDipoles().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with capacity")
    void constructWithCapacity() {
        FAggregate fAggregate = factory.getFAggregate(100);

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(100, fAggregate.getRefDipoles().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with particles")
    void constructWithParticles() {
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        FAggregate fAggregate = factory.getFAggregate(fAssembly);

        Assertions.assertAll("Validate FAggregate",
                () -> assertNotSame(fAssembly, fAggregate.getRefParticles(),
                        "The reference should change"),
                () -> assertEquals(1000000, fAggregate.getRefDipoles().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with particles and capacity")
    void constructWithParticlesAndCapacity() {
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        FAggregate fAggregate = factory.getFAggregate(fAssembly, 100);

        Assertions.assertAll("Validate FAggregate",
                () -> assertNotSame(fAssembly, fAggregate.getRefParticles(),
                        "The reference should change"),
                () -> assertEquals(100, fAggregate.getRefDipoles().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with reference particles")
    void constructWithReferenceParticles() {
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        Assertions.assertAll("Validate FAggregate",
                () -> assertSame(fAssembly, fAggregate.getRefParticles(),
                        "The reference should not change"),
                () -> assertEquals(1000000, fAggregate.getRefDipoles().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with reference particles and capacity")
    void constructWithReferenceParticlesAndCapacity() {
        FAssembly<Shape> fAssembly = factory.getFAssembly();

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly, 100);

        Assertions.assertAll("Validate FAggregate",
                () -> assertSame(fAssembly, fAggregate.getRefParticles(),
                        "The reference should not change"),
                () -> assertEquals(100, fAggregate.getRefDipoles().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with reference particles and reference elements")
    void constructWithReferenceParticlesAndReferenceElements() {
        FAssembly<Shape> fAssembly = factory.getFAssembly();
        FArray<FMetaData> fArray = factory.getFArray(10);

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly, fArray);

        Assertions.assertAll("Validate FAggregate",
                () -> assertSame(fAssembly, fAggregate.getRefParticles(),
                        "The reference should not change"),
                () -> assertSame(fArray, fAggregate.getRefDipoles(),
                        "The reference should not change")
        );
    }

    @Test
    @DisplayName("Get particles")
    void getParticles() {
        FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
        FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
        FSphere fSphereC = factory.getFSphere(0, 2, 0, 1);
        FSphere fSphereD = factory.getFSphere(0, 0, 2, 1);

        FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

        FAggregate fAggregate = factory.getRefFAggregate(fAssemblyA);

        FAssembly<Shape> fAssemblyB = fAggregate.getParticles();

        Assertions.assertAll("Validate FAggregate",
                () -> assertNotSame(fAssemblyA, fAssemblyB,
                        "The reference should change"),
                () -> assertEquals(fAssemblyA, fAssemblyB,
                        "The geometry should be the same")
        );

        fSphereA.setRadius(10);

        assertNotEquals(fAssemblyA, fAssemblyB, "The geometry should not be the same");
    }

    @Test
    @DisplayName("Set particles")
    void setParticles() {
        FAggregate fAggregateA = factory.getFAggregate();

        FSphere fSphereA = factory.getFSphere(0, 0, 0, 1);
        FSphere fSphereB = factory.getFSphere(2, 0, 0, 1);
        FSphere fSphereC = factory.getFSphere(0, 2, 0, 1);
        FSphere fSphereD = factory.getFSphere(0, 0, 2, 1);

        FAssembly<Shape> fAssemblyA = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD));

        FAggregate fAggregateB = fAggregateA.setParticles(fAssemblyA);

        FAssembly<Shape> fAssemblyB = fAggregateA.getRefParticles();

        Assertions.assertAll("Validate FAggregate",
                () -> assertSame(fAggregateA, fAggregateB,
                        "The reference not should change"),
                () -> assertNotSame(fAssemblyA, fAssemblyB,
                        "The reference should change"),
                () -> assertEquals(fAssemblyA, fAssemblyB,
                        "The geometry should be the same")
        );

        fSphereA.setRadius(10);

        assertNotEquals(fAssemblyA, fAssemblyB, "The geometry should not be the same");
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
    @DisplayName("Validate reference elements")
    void validateReferenceElements() {
        FArray<FMetaData> fArrayA = factory.getFArray(123);

        FAggregate fAggregateA = factory.getFAggregate();

        FAggregate fAggregateB = fAggregateA.setRefDipoles(fArrayA);

        FArray<FMetaData> fArrayB = fAggregateA.getRefDipoles();

        Assertions.assertAll("Validate FAggregate",
                () -> assertSame(fAggregateA, fAggregateB,
                        "The reference should not change"),
                () -> assertSame(fArrayA, fArrayB,
                        "The reference should not change")
        );
    }

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

        double relError = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double relError = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double relError = factory.getFStatHelper().getRelErr(expected, volActual);

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

        double relError = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double relError = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double relError = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double volErrLayer0 = factory.getFStatHelper().getRelErr(volExpectedLayer0, volLayers[0]);
        double volErrLayer1 = factory.getFStatHelper().getRelErr(volExpectedLayer1, volLayers[1]);
        double volErrLayer2 = factory.getFStatHelper().getRelErr(volExpectedLayer2, volLayers[2]);
        double volErr = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double volErrLayer0 = factory.getFStatHelper().getRelErr(volExpectedLayer0, volLayers[0]);
        double volErrLayer1 = factory.getFStatHelper().getRelErr(volExpectedLayer1, volLayers[1]);
        double volErrLayer2 = factory.getFStatHelper().getRelErr(volExpectedLayer2, volLayers[2]);
        double volErr = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double volErrLayer0 = factory.getFStatHelper().getRelErr(volExpectedLayer0, volLayers[0]);
        double volErrLayer1 = factory.getFStatHelper().getRelErr(volExpectedLayer1, volLayers[1]);
        double volErrLayer2 = factory.getFStatHelper().getRelErr(volExpectedLayer2, volLayers[2]);
        double volErr = factory.getFStatHelper().getRelErr(volExpected, volActual);

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

        double volErrLayer0 = factory.getFStatHelper().getRelErr(volExpectedLayer0, volLayers[0]);
        double volErr = factory.getFStatHelper().getRelErr(volActualB, volActualA);

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

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FArrayMesh<FMetaData> fArray = fAggregate.getVolumeMesh();

        FLayerCounter fLayer = factory.getFLayerCounter();

        fArray.forEach((index, d0, d1, d2, data) -> fLayer.inc(data.getLayerIndex()));

        double volActualLayer0 = fLayer.get(0) * 0.001;
        double volActualA = fLayer.addSelf() * 0.001;
        double volActualB = fAggregate.getVolume();

        double volExpectedLayer0 = 4 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

        double volErrLayer0 = factory.getFStatHelper().getRelErr(volExpectedLayer0, volActualLayer0);
        double volErr = factory.getFStatHelper().getRelErr(volActualA, volActualB);

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

        double relError = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double relError = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double relError = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double relError = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double relError = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double srfErrLayer0 = factory.getFStatHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
        double srfErrLayer1 = factory.getFStatHelper().getRelErr(srfExpectedLayer1, srfLayers[1]);
        double srfErrLayer2 = factory.getFStatHelper().getRelErr(srfExpectedLayer2, srfLayers[2]);
        double srfErr = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double srfErrLayer0 = factory.getFStatHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
        double srfErrLayer1 = factory.getFStatHelper().getRelErr(srfExpectedLayer1, srfLayers[1]);
        double srfErrLayer2 = factory.getFStatHelper().getRelErr(srfExpectedLayer2, srfLayers[2]);
        double srfErr = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double srfErrLayer0 = factory.getFStatHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
        double srfErrLayer1 = factory.getFStatHelper().getRelErr(srfExpectedLayer1, srfLayers[1]);
        double srfErrLayer2 = factory.getFStatHelper().getRelErr(srfExpectedLayer2, srfLayers[2]);
        double srfErrTotal = factory.getFStatHelper().getRelErr(srfExpected, srfActual);

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

        double srfErrLayer0 = factory.getFStatHelper().getRelErr(srfExpectedLayer0, srfLayers[0]);
        double srfErrLayer3 = factory.getFStatHelper().getRelErr(srfActualLayer3, srfLayers[3]);

        Assertions.assertAll("Validate FAggregate",
                () -> assertTrue(srfErrLayer0 < 0.01,
                        "Layer 0 volume is erroneous"),
                () -> assertTrue(srfErrLayer3 < 0.01,
                        "Layer 0 volume is erroneous")
        );
    }

    @Test
    @DisplayName("Get overlap factory - Same position - Double")
    void getOverlapFactorSamePositionDouble() {
        FSphere fSphereA = factory.getFSphere();
        FSphere fSphereB = factory.getFSphere();

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactor();

        assertEquals(1, overlap, epsilon);
    }

    @Test
    @DisplayName("Get overlap factory - Same position - Triple")
    void getOverlapFactorSamePositionTriple() {
        FSphere fSphereA = factory.getFSphere();
        FSphere fSphereB = factory.getFSphere();
        FSphere fSphereC = factory.getFSphere();

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactor();

        assertEquals(1, overlap, epsilon);
    }

    @Test
    @DisplayName("Get overlap factory - Distant")
    void getOverlapFactorDistant() {
        FSphere fSphereA = factory.getFSphere(-1, 0, 0);
        FSphere fSphereB = factory.getFSphere(1, 0, 0);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactor();

        assertEquals(0, overlap, epsilon);
    }

    @Test
    @DisplayName("Get overlap factory - Intersecting")
    void getOverlapFactorIntersecting() {
        FSphere fSphereA = factory.getFSphere(0, 0, 0);
        FSphere fSphereB = factory.getFSphere(1, 0, 0);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactor();

        double volAlgOverlap = 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);
        double volAlgTotal = 2 * (4  * Math.PI / 3) - 2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

        double relError = factory.getFStatHelper().getRelErr(volAlgOverlap / volAlgTotal, overlap);

        assertTrue(relError < 0.01);
    }

    @Test
    @DisplayName("Get overlap factory - Field")
    void getOverlapFactorField() {
        FSphere fSphereA = factory.getFSphere(0, 0, 0);
        FSphere fSphereB = factory.getFSphere(1, 0, 0);
        FSphere fSphereC = factory.getFSphere(0, 5, 0);
        FSphere fSphereD = factory.getFSphere(0, 0, 5);
        FSphere fSphereE = factory.getFSphere(0, 0, 5, 2);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactor();

        double volAlgOverlap = (4  * Math.PI / 3) +
                2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);
        double volAlgTotal = (4  * Math.PI * Math.pow(2, 3) / 3) +
                3 * (4  * Math.PI / 3) -
                2 * (Math.PI * (0.5 * 0.5) / 3) * (3 - 0.5);

        double relError = factory.getFStatHelper().getRelErr(volAlgOverlap / volAlgTotal, overlap);

        assertTrue(relError < 0.01);
    }

    @Test
    @DisplayName("Get overlap factory legacy - Same position")
    void getOverlapFactorLegacySamePosition() {
        FSphere fSphereA = factory.getFSphere();
        FSphere fSphereB = factory.getFSphere();

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactorLegacy();

        assertEquals(1, overlap, epsilon);
    }

    @Test
    @DisplayName("Get overlap factory legacy - Distant")
    void getOverlapFactorLegacyDistant() {
        FSphere fSphereA = factory.getFSphere(-2, 0, 0);
        FSphere fSphereB = factory.getFSphere(2, 0, 0);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactorLegacy();

        assertEquals(0, overlap, epsilon);
    }

    @Test
    @DisplayName("Get overlap factory legacy - Intersecting")
    void getOverlapFactorLegacyIntersecting() {
        FSphere fSphereA = factory.getFSphere(0, 0, 0);
        FSphere fSphereB = factory.getFSphere(1, 0, 0);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactorLegacy();

        assertEquals(0.5, overlap, epsilon);
    }

    @Test
    @DisplayName("Get overlap factory legacy - Field")
    void getOverlapFactorLegacyField() {
        FSphere fSphereA = factory.getFSphere(0, 0, 0);
        FSphere fSphereB = factory.getFSphere(1, 0, 0);
        FSphere fSphereC = factory.getFSphere(0, 3, 0);
        FSphere fSphereD = factory.getFSphere(0, 4, 0);
        FSphere fSphereE = factory.getFSphere(5, 5, 5);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double overlap = fAggregate.getOverlapFactorLegacy();

        double relError = factory.getFStatHelper().getRelErr(0.5, overlap);

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
    @DisplayName("Get range")
    void getRange() {
        Shape fSphereA = factory.getFSphere(1, 2, 3, 1);
        Shape fSphereB = factory.getFSphere(-4, -5, -6, 3);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FPairPos3D range = fAggregate.getRange();

        Assertions.assertAll("Validate range",
                () -> assertEquals(factory.getFPairPos3D(-7, -8, -9, 2, 3, 4), range,
                        "The range is incorrect")
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
    @DisplayName("Get radius from")
    void getRadiusFrom() {
        Shape fSphereA = factory.getFSphere(1, 1, 1, 1);
        Shape fSphereB = factory.getFSphere(3, 3, 3, 1);
        Shape fSphereC = factory.getFSphere(5, 5, 5, 1);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC));

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FPoint massCenter = factory.getFPoint();

        fAggregate.getMassCenter(massCenter);

        assertTrue(massCenter.isSimilar(3, 3, 3));

        double radiusA = fAggregate.getRadiusFrom(massCenter);
        double radiusB = fAggregate.getRadiusFromZero();

        assertEquals(2 * Math.sqrt(3) + 1, radiusA, 1E-6);
        assertNotEquals(radiusA, radiusB);

        fAggregate.positionCenter(massCenter);
        fAggregate.getMassCenter(massCenter);

        assertTrue(massCenter.isSimilar(0, 0, 0));

        radiusA = fAggregate.getRadiusFrom(massCenter);
        radiusB = fAggregate.getRadiusFromZero();

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

        double rErr = factory.getFStatHelper().getRelErr(rExpected, rActual);

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

        double rErrLayer0 = factory.getFStatHelper().getRelErr(rExpectedLayer0, layers[0]);
        double rErrLayer1 = factory.getFStatHelper().getRelErr(rExpectedLayer1, layers[1]);
        double rErrLayer2 = factory.getFStatHelper().getRelErr(rExpectedLayer2, layers[2]);
        double rErr = factory.getFStatHelper().getRelErr(rExpected, rActual);

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

        double rErr = factory.getFStatHelper().getRelErr(rExpected, rActual);

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

        double rErrLayer0 = factory.getFStatHelper().getRelErr(rExpectedLayer0, layers[0]);
        double rErrLayer1 = factory.getFStatHelper().getRelErr(rExpectedLayer1, layers[1]);
        double rErrLayer2 = factory.getFStatHelper().getRelErr(rExpectedLayer2, layers[2]);
        double rErr = factory.getFStatHelper().getRelErr(rExpected, rActual);

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

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        FPoint massCenter = factory.getFPoint();
        fAggregate.getMassCenter(massCenter);

        assertTrue(massCenter.isSimilar(0, 0, 0));

        double rgExpected = factory.getFSphereHelper().getRadiusOfGyration(radius);
        double rgActual = fAggregate.getRadiusOfGyration();

        double rgErr = factory.getFStatHelper().getRelErr(rgExpected, rgActual);

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

        FAggregate fAggregate = factory.getRefFAggregate(fAssembly);

        double rgDefault = fAggregate.getRadiusOfGyration();
        double rgLegacyMono = fAggregate.getRadiusOfGyrationMonodisperse();
        double rgLegacyPoly = fAggregate.getRadiusOfGyrationPolydisperse();

        double rgErrMono = factory.getFStatHelper().getRelErr(rgDefault, rgLegacyMono);
        double rgErrPoly = factory.getFStatHelper().getRelErr(rgDefault, rgLegacyPoly);

        assertTrue(rgErrMono < 0.05);
        assertTrue(rgErrPoly < 0.05);
    }
}
