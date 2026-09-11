package eu.scattering.cli.command.generate.service;

import eu.scattering.cli.command.generate.service.mixin.TransformationMixin;
import eu.scattering.core.design.component.aggregate.FAggregate;

public class TransformationService {

    public static void transform(FAggregate aggregate, TransformationMixin trans) {

        pca(aggregate, trans);
    }

    //---------------------------------------------------------------------

    private static void pca(FAggregate aggregate, TransformationMixin trans) {

        if (trans.pca) {
            aggregate.pca();
        }
    }
}
