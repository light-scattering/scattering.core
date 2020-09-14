package eu.scattering.core.design;

import eu.scattering.core.design.development.DevelopmentFactory;
import eu.scattering.core.design.main.MainFactory;
import eu.scattering.core.design.support.SupportFactory;

public interface Factory extends MainFactory, SupportFactory, DevelopmentFactory {
}
