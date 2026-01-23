package eu.scattering.core.test.component.aggregate.validator;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorFractalDimension;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorNoOverlap;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.type.FractalDimension;
import eu.scattering.core.design.type.OverlapFactor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.epsilon;
import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FValidator")
public class FValidatorTest {

    @Nested
    @Tag("FValidator")
    @DisplayName("FValidator overlap")
    class FValidatorOverlapTest {

        @Test
        @DisplayName("Overlap PC - Ballistic")
        void overlapPC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorNoOverlap fValidator = factory.getFValidatorContext().noOverlap();

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            assertTrue(fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() < epsilon);
        }

        @Test
        @DisplayName("Overlap CC - Ballistic")
        void overlapCC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorNoOverlap fValidator = factory.getFValidatorContext().noOverlap();

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            assertTrue(fAggregate.getOverlapFactor(OverlapFactor.PARTICLE_LINEAR).max() < epsilon);
        }

        @Test
        @DisplayName("Correlation dimension PC - Ballistic")
        void dimensionCorrelationPC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.CORRELATION, 2.2, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.2);
        }

        @Test
        @DisplayName("Correlation dimension CC - Ballistic")
        void dimensionCorrelationCC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.CORRELATION, 2.2, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.2);
        }
    }

    @Nested
    @Tag("FValidator")
    @DisplayName("FValidator fractal dimension")
    class FValidatorFractalDimensionTest {

        @Test
        @DisplayName("Box counting dimension PC - Ballistic")
        void dimensionBoxPC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.BOX, 2.0, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.0, results.get(results.size() - 1), 0.2);
        }

        @Test
        @DisplayName("Box counting dimension CC - Ballistic")
        void dimensionBoxCC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.BOX, 2.0, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.0, results.get(results.size() - 1), 0.2);
        }

        @Test
        @DisplayName("Correlation dimension PC - Ballistic")
        void dimensionCorrelationPC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.CORRELATION, 2.2, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.2);
        }

        @Test
        @DisplayName("Correlation dimension CC - Ballistic")
        void dimensionCorrelationCC() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.CORRELATION, 2.2, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.2);
        }
    }
}
