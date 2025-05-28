package eu.scattering.core.design.component.geometry;

import eu.scattering.core.design.component.geometry.construct.ConstructEngineRand;
import eu.scattering.core.design.component.geometry.base.BaseEngineRand;
import eu.scattering.core.design.component.geometry.container.ContainerEngineRand;
import eu.scattering.core.design.component.geometry.shape.ShapeEngineRand;

public interface GeometryEngineRand extends
        BaseEngineRand,
        ConstructEngineRand,
        ContainerEngineRand,
        ShapeEngineRand {
}
