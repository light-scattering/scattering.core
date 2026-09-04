package eu.scattering.core.test.component.aggregate.aspect;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.utility.type.variant.Center;
import eu.scattering.core.design.utility.type.option.Dimension;
import eu.scattering.core.design.utility.type.method.MassCenter;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("FAggregateRandom")
public class FAggregateRandomizeTest {

    @Test
    @DisplayName("Project 3D - Monodisperse")
    void project3DMonodisperse() {
        FAggregate aggA = factory.aggregates().templates().monodisperse(25, 5);
        FAggregate aggB = factory.aggregates().templates().monodisperse(25, 5);

        FModelPC modelA = factory.models().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.models().pc().ballistic(aggB);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        factory.random().mutate().project(aggA, aggB);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);
    }

    @Test
    @DisplayName("Project 3D - Polydisperse")
    void project3DPolydisperse() {
        FAggregate aggA = factory.aggregates().templates().polydisperse(25, 10, 1);
        FAggregate aggB = factory.aggregates().templates().polydisperse(25, 10, 1);

        FModelPC modelA = factory.models().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.models().pc().ballistic(aggB);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        factory.random().mutate().project(aggA, aggB);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);
    }

    @Test
    @DisplayName("Project 2D - Monodisperse")
    void project2DMonodisperse() {
        FAggregate aggA = factory.aggregates().templates().monodisperse(25, 5);
        FAggregate aggB = factory.aggregates().templates().monodisperse(25, 5);

        FModelPC modelA = factory.models().pc().ballistic(Dimension.D2, aggA);
        FModelPC modelB = factory.models().pc().ballistic(Dimension.D2, aggB);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        factory.random().mutate().projectOnPlane(aggA, aggB);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Project 2D - Polydisperse")
    void project2DPolydisperse() {
        FAggregate aggA = factory.aggregates().templates().polydisperse(25, 10, 1);
        FAggregate aggB = factory.aggregates().templates().polydisperse(25, 10, 1);

        FModelPC modelA = factory.models().pc().ballistic(Dimension.D2, aggA);
        FModelPC modelB = factory.models().pc().ballistic(Dimension.D2, aggB);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        factory.random().mutate().projectOnPlane(aggA, aggB);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Attach 3D - Monodisperse")
    void attach3DMonodisperse() {
        FAggregate aggA = factory.aggregates().templates().monodisperse(25, 5);
        FAggregate aggB = factory.aggregates().templates().monodisperse(25, 5);

        FModelPC modelA = factory.models().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.models().pc().tunable(aggB, 1.8, 1.6);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().attach(aggA, aggB);

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);
    }

    @Test
    @DisplayName("Attach 3D - Polydisperse")
    void attach3DPolydisperse() {
        FAggregate aggA = factory.aggregates().templates().polydisperse(25, 10, 1);
        FAggregate aggB = factory.aggregates().templates().polydisperse(25, 10, 1);

        FModelPC modelA = factory.models().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.models().pc().tunable(aggB, 1.8, 1.6);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().attach(aggA, aggB);

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);
    }

    @Test
    @DisplayName("Attach 2D")
    void attach2D() {
        FAggregate aggA = factory.aggregates().templates().monodisperse(25, 1);
        FAggregate aggB = factory.aggregates().templates().monodisperse(25, 1);

        FModelPC modelA = factory.models().pc().ballistic(Dimension.D2, aggA);
        FModelPC modelB = factory.models().pc().ballistic(Dimension.D2, aggB);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().attachOnPlane(aggA, aggB);

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Rotate 3D (simple)")
    void rotateSimple3D() {
        Shape shapeA1 = factory.getFSphere(-4, 0, 0, 1);
        Shape shapeA2 = factory.getFSphere(-2, 0, 0, 1);
        Shape shapeA3 = factory.getFSphere(-0, 0, 0, 1);

        FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
        FAggregate aggA = factory.getRefFAggregate(coreA);
        FAggregate aggACopy = aggA.copy(true);

        Shape shapeB1 = factory.getFSphere(0, 0, 0, 1);
        Shape shapeB2 = factory.getFSphere(2, 0, 0, 1);
        Shape shapeB3 = factory.getFSphere(4, 0, 0, 1);

        FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
        FAggregate aggB = factory.getRefFAggregate(coreB);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().moveMassCenter(aggA, aggB, MassCenter.SIMPLE_POLY, 4);

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        aggACopy = aggA.copy(true);

        FPoint cAggA = aggA.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);
        FPoint cAggB = aggB.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);

        factory.random().mutate().rotate(aggA, aggB, cAggA, cAggB, 100);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);
    }

    @Test
    @DisplayName("Rotate 2D (simple)")
    void rotateSimple2D() {
        Shape shapeA1 = factory.getFSphere(-4, 0, 0, 1);
        Shape shapeA2 = factory.getFSphere(-2, 0, 0, 1);
        Shape shapeA3 = factory.getFSphere(-0, 0, 0, 1);

        FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
        FAggregate aggA = factory.getRefFAggregate(coreA);
        FAggregate aggACopy = aggA.copy(true);

        Shape shapeB1 = factory.getFSphere(0, 0, 0, 1);
        Shape shapeB2 = factory.getFSphere(2, 0, 0, 1);
        Shape shapeB3 = factory.getFSphere(4, 0, 0, 1);

        FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
        FAggregate aggB = factory.getRefFAggregate(coreB);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().moveMassCenterOnPlane(aggA, aggB, MassCenter.SIMPLE_POLY, 4);

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        aggACopy = aggA.copy(true);

        FPoint cAggA = aggA.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);
        FPoint cAggB = aggB.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);

        factory.random().mutate().rotateOnPlane(aggA, aggB, cAggA, cAggB, 100);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Rotate 3D (complex)")
    void rotateComplex3D() {
        FAggregate aggA = factory.aggregates().templates().monodisperse(25, 1);
        FAggregate aggB = factory.aggregates().templates().monodisperse(25, 1);

        FModelPC modelA = factory.models().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.models().pc().tunable(aggB, 1.8, 1.6);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().moveMassCenter(aggA, aggB, MassCenter.SIMPLE_POLY, aggA.getRadiusFrom(Center.MASS));

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        FPoint cAggA = aggA.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);
        FPoint cAggB = aggB.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);

        aggACopy = aggA.copy(true);

        factory.random().mutate().rotate(aggA, aggB, cAggA, cAggB, 100);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);
    }

    @Test
    @DisplayName("Rotate 2D (complex)")
    void rotateComplex2D() {
        FAggregate aggA = factory.aggregates().templates().monodisperse(25, 1);
        FAggregate aggB = factory.aggregates().templates().monodisperse(25, 1);

        FModelPC modelA = factory.models().pc().tunable(Dimension.D2, aggA, 1.5, 1.2);
        FModelPC modelB = factory.models().pc().tunable(Dimension.D2, aggB, 1.5, 1.2);

        modelA.build();
        modelB.build();

        FAggregate aggACopy = aggA.copy(true);
        FAggregate aggBCopy = aggB.copy(true);

        FAggregate aggARef = factory.random().mutate().moveMassCenterOnPlane(aggA, aggB, MassCenter.SIMPLE_POLY, aggA.getRadiusFrom(Center.MASS));

        assertSame(aggA, aggARef);

        assertFalse(aggACopy.isExact(aggA));
        assertTrue(aggBCopy.isExact(aggB));

        FPoint cAggA = aggA.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);
        FPoint cAggB = aggB.getMassCenter(factory.getFPoint(), MassCenter.SIMPLE_POLY);

        aggACopy = aggA.copy(true);

        factory.random().mutate().rotateOnPlane(aggA, aggB, cAggA, cAggB, 100);

        assertFalse(aggACopy.isExact(aggA));
        assertFalse(aggBCopy.isExact(aggB));

        aggA.merge(aggB, true);

        assertTrue(aggA.isConnected());
        assertEquals(0, aggA.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }
}
