package eu.scattering.core.test.component.aggregate.validator;

import eu.scattering.core.design.component.aggregate.FAggregate;
import eu.scattering.core.design.component.aggregate.model.pc.FModelPC;
import eu.scattering.core.design.component.aggregate.validator.common.module.FValidatorFractalDimension;
import eu.scattering.core.design.statistics.base.FStat;
import eu.scattering.core.design.type.FractalDimension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static eu.scattering.core.test.Config.factory;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("FValidator")
public class FValidatorTest {

    @Nested
    @Tag("Construct")
    @DisplayName("FValidator construct")
    class FValidatorConstructTest {

        @Test
        @DisplayName("Box counting dimension - Ballistic")
        void dimensionBox() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().template().monodisperse(quantity, 1);

            FModelPC fModel = factory.createFModelBallistic3D(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorFractalDimension(FractalDimension.BOX, 2.0, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.0, results.get(results.size() - 1), 0.2);
        }

        @Test
        @DisplayName("Correlation dimension - Ballistic")
        void dimensionCorrelation() {
            int quantity = 100;

            FAggregate fAggregate = factory.getFAggregateContext().template().monodisperse(quantity, 1);

            FModelPC fModel = factory.createFModelBallistic3D(fAggregate);
            FValidatorFractalDimension fValidator = factory.getFValidatorFractalDimension(FractalDimension.CORRELATION, 2.2, 0.2);

            fModel.addCompletionValidator(fValidator);
            fModel.build();

            FStat results = fValidator.getRefFStat();

            assertEquals(2.2, results.get(results.size() - 1), 0.2);
        }
    }
}
