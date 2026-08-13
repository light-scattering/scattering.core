package eu.scattering.core.test.component.aggregate.validator;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.cc.FModelCC;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorFractalDimension;
import eu.scattering.core.design.component.aggregate.validator.module.FValidatorNoOverlap;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.utility.type.variant.FractalDimension;
import eu.scattering.core.design.utility.type.variant.OverlapFactor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.TestConfig.epsilon;
import static eu.scattering.core.test.TestConfig.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("FValidator")
public class FValidatorTest {

    @Nested
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
            int quantity = 250;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.DC_RESTRICTED, 2.5, 0.3);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.5, results.get(results.size() - 1), 0.3);
        }

        @Test
        @DisplayName("Correlation dimension CC - Ballistic")
        void dimensionCorrelationCC() {
            int quantity = 250;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.DC_RESTRICTED, 2.2, 0.3);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.3);
        }
    }

    @Nested
    @DisplayName("FValidator fractal dimension")
    class FValidatorFractalDimensionTest {

        @Test
        @DisplayName("Box counting dimension PC - Ballistic")
        void dimensionBoxPC() {
            int quantity = 250;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.BC_BASELINE, 2.5, 0.3);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.5, results.get(results.size() - 1), 0.3);
        }

        @Test
        @DisplayName("Box counting dimension CC - Ballistic")
        void dimensionBoxCC() {
            int quantity = 250;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.BC_BASELINE, 2.2, 0.3);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.3);
        }

        @Test
        @DisplayName("Correlation dimension PC - Ballistic")
        void dimensionCorrelationPC() {
            int quantity = 250;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelPC fModel = factory.getFModelContext().pc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.DC_RESTRICTED, 2.5, 0.3);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.5, results.get(results.size() - 1), 0.3);
        }

        @Test
        @DisplayName("Correlation dimension CC - Ballistic")
        void dimensionCorrelationCC() {
            int quantity = 250;

            FAggregate fAggregate = factory.getFAggregateContext().base().monodisperse(quantity, 1);

            FModelCC fModel = factory.getFModelContext().cc().ballistic(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorContext().fractalDimension(FractalDimension.DC_RESTRICTED, 2.2, 0.3);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.3);
        }
    }
}
