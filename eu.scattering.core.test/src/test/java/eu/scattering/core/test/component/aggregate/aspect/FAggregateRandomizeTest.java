package eu.scattering.core.test.component.aggregate.aspect;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.type.Center;
import eu.scattering.core.design.type.Dimension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FAggregateRandom")
public class FAggregateRandomizeTest {

    @Test
    @DisplayName("Project 3D - Monodisperse")
    void project3DMonodisperse() {
        FAggregate aggA = factory.getFAggregateContext().base().monodisperse(25, 5);
        FAggregate aggB = factory.getFAggregateContext().base().monodisperse(25, 5);

        FModelPC modelA = factory.getFModelContext().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.getFModelContext().pc().ballistic(aggB);

        modelA.build();
        modelB.build();

        factory.getRandAspect().project(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Project 3D - Polydisperse")
    void project3DPolydisperse() {
        FAggregate aggA = factory.getFAggregateContext().base().polydisperse(25, 10, 1);
        FAggregate aggB = factory.getFAggregateContext().base().polydisperse(25, 10, 1);

        FModelPC modelA = factory.getFModelContext().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.getFModelContext().pc().ballistic(aggB);

        modelA.build();
        modelB.build();

        factory.getRandAspect().project(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Project 2D - Monodisperse")
    void project2DMonodisperse() {
        FAggregate aggA = factory.getFAggregateContext().base().monodisperse(25, 5);
        FAggregate aggB = factory.getFAggregateContext().base().monodisperse(25, 5);

        FModelPC modelA = factory.getFModelContext().pc().ballistic(Dimension.D2, aggA);
        FModelPC modelB = factory.getFModelContext().pc().ballistic(Dimension.D2, aggB);

        modelA.build();
        modelB.build();

        factory.getRandAspect().projectOnSurface(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Project 2D - Polydisperse")
    void project2DPolydisperse() {
        FAggregate aggA = factory.getFAggregateContext().base().polydisperse(25, 10, 1);
        FAggregate aggB = factory.getFAggregateContext().base().polydisperse(25, 10, 1);

        FModelPC modelA = factory.getFModelContext().pc().ballistic(Dimension.D2, aggA);
        FModelPC modelB = factory.getFModelContext().pc().ballistic(Dimension.D2, aggB);

        modelA.build();
        modelB.build();

        factory.getRandAspect().projectOnSurface(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Attach 3D - Monodisperse")
    void attach3DMonodisperse() {
        FAggregate aggA = factory.getFAggregateContext().base().monodisperse(25, 5);
        FAggregate aggB = factory.getFAggregateContext().base().monodisperse(25, 5);

        FModelPC modelA = factory.getFModelContext().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.getFModelContext().pc().tunable(aggB, 1.8, 1.6);

        modelA.build();
        modelB.build();

        factory.getRandAspect().attach(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Attach 3D - Polydisperse")
    void attach3DPolydisperse() {
        FAggregate aggA = factory.getFAggregateContext().base().polydisperse(25, 10, 1);
        FAggregate aggB = factory.getFAggregateContext().base().polydisperse(25, 10, 1);

        FModelPC modelA = factory.getFModelContext().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.getFModelContext().pc().tunable(aggB, 1.8, 1.6);

        modelA.build();
        modelB.build();

        factory.getRandAspect().attach(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Attach 2D")
    void attach2D() {
        FAggregate aggA = factory.getFAggregateContext().base().monodisperse(25, 1);
        FAggregate aggB = factory.getFAggregateContext().base().monodisperse(25, 1);

        FModelPC modelA = factory.getFModelContext().pc().ballistic(Dimension.D2, aggA);
        FModelPC modelB = factory.getFModelContext().pc().ballistic(Dimension.D2, aggB);

        modelA.build();
        modelB.build();

        factory.getRandAspect().attachOnSurface(aggA, aggB);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);

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

        Shape shapeB1 = factory.getFSphere(0, 0, 0, 1);
        Shape shapeB2 = factory.getFSphere(2, 0, 0, 1);
        Shape shapeB3 = factory.getFSphere(4, 0, 0, 1);

        FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
        FAggregate aggB = factory.getRefFAggregate(coreB);

        factory.getRandAspect().moveMassCenter(aggA, aggB, 4);

        factory.getRandAspect().rotate(aggA, aggB, 100);

        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Rotate 2D (simple)")
    void rotateSimple2D() {
        Shape shapeA1 = factory.getFSphere(-4, 0, 0, 1);
        Shape shapeA2 = factory.getFSphere(-2, 0, 0, 1);
        Shape shapeA3 = factory.getFSphere(-0, 0, 0, 1);

        FAssembly<Shape> coreA = factory.getFAssembly(List.of(shapeA1, shapeA2, shapeA3));
        FAggregate aggA = factory.getRefFAggregate(coreA);

        Shape shapeB1 = factory.getFSphere(0, 0, 0, 1);
        Shape shapeB2 = factory.getFSphere(2, 0, 0, 1);
        Shape shapeB3 = factory.getFSphere(4, 0, 0, 1);

        FAssembly<Shape> coreB = factory.getFAssembly(List.of(shapeB1, shapeB2, shapeB3));
        FAggregate aggB = factory.getRefFAggregate(coreB);

        factory.getRandAspect().moveMassCenterOnSurface(aggA, aggB, 4);

        factory.getRandAspect().rotateOnSurface(aggA, aggB, 100);

        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }

    @Test
    @DisplayName("Rotate 3D (complex)")
    void rotateComplex3D() {
        FAggregate aggA = factory.getFAggregateContext().base().monodisperse(25, 1);
        FAggregate aggB = factory.getFAggregateContext().base().monodisperse(25, 1);

        FModelPC modelA = factory.getFModelContext().pc().tunable(aggA, 1.8, 1.6);
        FModelPC modelB = factory.getFModelContext().pc().tunable(aggB, 1.8, 1.6);

        modelA.build();
        modelB.build();

        factory.getRandAspect().moveMassCenter(aggA, aggB, aggA.getRadius(Center.MASS));

        factory.getRandAspect().rotate(aggA, aggB, 100);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);
    }

    @Test
    @DisplayName("Rotate 2D (complex)")
    void rotateComplex2D() {
        FAggregate aggA = factory.getFAggregateContext().base().monodisperse(25, 1);
        FAggregate aggB = factory.getFAggregateContext().base().monodisperse(25, 1);

        FModelPC modelA = factory.getFModelContext().pc().tunable(Dimension.D2, aggA, 1.5, 1.2);
        FModelPC modelB = factory.getFModelContext().pc().tunable(Dimension.D2, aggB, 1.5, 1.2);

        modelA.build();
        modelB.build();

        factory.getRandAspect().moveMassCenterOnSurface(aggA, aggB, aggA.getRadius(Center.MASS));

        factory.getRandAspect().rotateOnSurface(aggA, aggB, 100);
        aggA.merge(aggB, true);

        assertTrue(aggA.isCompact());
        assertEquals(0, aggA.getLinearOverlapFactor(), 1E-4);

        for (Shape shape : aggA) {
            assertEquals(0, shape.getCenterZ(), 1E-6);
        }
    }
}
