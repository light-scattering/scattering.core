package eu.scattering.core.test.component.geometry.shape;

import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.design.component.geometry.shape.ShapeProducer;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphereProducer;
import eu.scattering.core.design.util.support.Producer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.List;
import java.util.stream.Collectors;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.*;

@Timeout(5)
@DisplayName("ShapeProducer")
public class ShapeProducerTest {

    @Test
    @DisplayName("Produce empty")
    void produceEmpty() {
        ShapeProducer producer = factory.getShapeProducer();

        assertThrows(IllegalStateException.class, producer::produce,
                "The producer should not be configured");
    }

    @Test
    @DisplayName("Produce")
    void produceWithProvider() {
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withFixRadius(3);

        Producer<Shape> producer = factory.getShapeProducer()
                .withProducer(fSphereProducer, 1);

        Shape resultA = producer.produce();
        Shape resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA instanceof FSphere,
                        "The type of shape A is erroneous"),
                () -> assertTrue(resultB instanceof FSphere,
                        "The type of shape B is erroneous"),
                () -> assertEquals(3, resultA.getRadius(),
                        epsilon, "The shape A radius is erroneous"),
                () -> assertEquals(3, resultB.getRadius(),
                        epsilon, "The shape B radius is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce (simple)")
    void produceWithProviderSimple() {
        Producer<FSphere> fSphereProducer = factory.getFSphereProducer()
                .withFixRadius(3);

        Producer<Shape> producer = factory.getShapeProducer()
                .withProducer(fSphereProducer);

        Shape resultA = producer.produce();
        Shape resultB = producer.produce();

        Assertions.assertAll("Validate FPoint values",
                () -> assertTrue(resultA instanceof FSphere,
                        "The type of shape A is erroneous"),
                () -> assertTrue(resultB instanceof FSphere,
                        "The type of shape B is erroneous"),
                () -> assertEquals(3, resultA.getRadius(),
                        epsilon, "The shape A radius is erroneous"),
                () -> assertEquals(3, resultB.getRadius(),
                        epsilon, "The shape B radius is erroneous"),
                () -> assertNotSame(resultA, resultB,
                        "Elements should not be the same")
        );
    }

    @Test
    @DisplayName("Produce distribution")
    void produceDistribution() {
        FSphereProducer fSphereProducer1 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(1));
        FSphereProducer fSphereProducer2 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(2));

        ShapeProducer producer = factory.getShapeProducer()
                .withProducer(fSphereProducer1, 1)
                .withProducer(fSphereProducer2, 3);

        int countA = 0;
        int countB = 0;

        for (int i = 0 ; i < 1000 ; i++) {

            if (producer.produce().getRadius() == 1) {
                countA++;
            } else {
                countB++;
            }
        }

        int countFinalA = countA;
        int countFinalB = countB;

        Assertions.assertAll("Validate Shape values",
                () -> assertTrue(countFinalA * 1.5 < countFinalB,
                        "The distribution is erroneous")
        );
    }

    @Test
    @DisplayName("Iterate, auto")
    void iterateAuto() {
        FSphereProducer fSphereProducer1 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(1));
        FSphereProducer fSphereProducer2 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(2));
        FSphereProducer fSphereProducer3 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(3));

        ShapeProducer producer = factory.getShapeProducer()
                .withProducer(fSphereProducer1, 5)
                .withProducer(fSphereProducer2, 10)
                .withProducer(fSphereProducer3, 15);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        int i = 0;
        for (Shape shape : producer.getList()) {

            if (shape.getIndex() != i++) {
                throw new IllegalStateException("The index is erroneous");
            }

            if (shape.getRadius() == 1) {
                qRadius1++;
            } else if (shape.getRadius() == 2) {
                qRadius2++;
            } else if (shape.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals( 5, qRadius1, "Distribution 1 is erroneous");
        assertEquals(10, qRadius2, "Distribution 2 is erroneous");
        assertEquals(15, qRadius3, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, fixed")
    void iterateFixed() {
        FSphereProducer fSphereProducer1 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(1));
        FSphereProducer fSphereProducer2 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(2));
        FSphereProducer fSphereProducer3 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(3));

        ShapeProducer producer = factory.getShapeProducer()
                .withProducer(fSphereProducer1, 1)
                .withProducer(fSphereProducer2, 1)
                .withProducer(fSphereProducer3, 1);

        int qRadius1 = 0;
        int qRadius2 = 0;
        int qRadius3 = 0;

        for (Shape shape : producer.getListFixed(8)) {

            if (shape.getRadius() == 1) {
                qRadius1++;
            } else if (shape.getRadius() == 2) {
                qRadius2++;
            } else if (shape.getRadius() == 3) {
                qRadius3++;
            } else {
                throw new IllegalStateException("The produced element is erroneous");
            }
        }

        assertEquals(8, qRadius1 + qRadius2 + qRadius3, "The number of elements is incorrect");
        assertEquals(3, qRadius1, 1, "Distribution 1 is erroneous");
        assertEquals(3, qRadius2, 1, "Distribution 2 is erroneous");
        assertEquals(3, qRadius3, 1, "Distribution 3 is erroneous");
    }

    @Test
    @DisplayName("Iterate, random")
    void iterateRandom() {
        FSphereProducer fSphereProducer1 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(1));
        FSphereProducer fSphereProducer2 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(2));
        FSphereProducer fSphereProducer3 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(3));

        ShapeProducer producer = factory.getShapeProducer()
                .withProducer(fSphereProducer1, 20)
                .withProducer(fSphereProducer2, 20)
                .withProducer(fSphereProducer3, 20);

        List<Shape> results = producer.getListRandomized(60);

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }

    @Test
    @DisplayName("Stream")
    void stream() {
        FSphereProducer fSphereProducer1 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(1));
        FSphereProducer fSphereProducer2 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(2));
        FSphereProducer fSphereProducer3 = factory.getFSphereProducer()
                .withCustomRule((factory) -> factory.getFSphere(3));

        ShapeProducer producer = factory.getShapeProducer()
                .withProducer(fSphereProducer1, 20)
                .withProducer(fSphereProducer2, 20)
                .withProducer(fSphereProducer3, 20);

        List<Shape> results = producer.stream().limit(60).collect(Collectors.toList());

        boolean sequence = true;
        for (int i = 0 ; i < 20 ; i++) {
            if (results.get(i).getRadius() != 1) {
                sequence = false;
                break;
            }
        }

        assertEquals(60, results.size(), "The number of elements is incorrect");
        assertFalse(sequence, "The elements are not randomized");
    }
}
