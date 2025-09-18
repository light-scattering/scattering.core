package eu.scattering.core.test.component.aggregate;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.point.FPointProducer;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.util.container.FMetaData;
import eu.scattering.core.design.util.support.Producer;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.array.FArrayMesh;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;
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
                () -> assertEquals(1000000, fAggregate.getRefElements().capacity(),
                        "The capacity is erroneous")
        );
    }

    @Test
    @DisplayName("Construct with capacity")
    void constructWithCapacity() {
        FAggregate fAggregate = factory.getFAggregate(100);

        Assertions.assertAll("Validate FAggregate",
                () -> assertEquals(100, fAggregate.getRefElements().capacity(),
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
                () -> assertEquals(1000000, fAggregate.getRefElements().capacity(),
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
                () -> assertEquals(100, fAggregate.getRefElements().capacity(),
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
                () -> assertEquals(1000000, fAggregate.getRefElements().capacity(),
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
                () -> assertEquals(100, fAggregate.getRefElements().capacity(),
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
                () -> assertSame(fArray, fAggregate.getRefElements(),
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

        FAggregate fAggregateB = fAggregateA.setRefElements(fArrayA);

        FArray<FMetaData> fArrayB = fAggregateA.getRefElements();

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

        fArray.forEach((index, d0, d1, d2, data) -> fLayer.inc(data.getLayer()));

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
        FSphere fSphereE = factory.getFSphere(0, 0, 0);

        FAssembly<Shape> fAssembly = factory.getFAssembly(List.of(fSphereA, fSphereB, fSphereC, fSphereD, fSphereE));

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
}
