package eu.scattering.core.design.component.aggregate;

import eu.scattering.core.design.component.Component;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.container.assembly.FAssembly;
import eu.scattering.core.design.component.geometry.shape.Shape;
import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

public interface FAggregate extends Component {

    FAssembly<Shape> getCore();

    //--------------------------------------------------

    void setName(String name);
    String getName();

    void setType(String type);
    String getType();

    //--------------------------------------------------

    void setMaterialDensity(String tag, double density);
    void setMaterialComment(String tag, String comment);

    //--------------------------------------------------

    double getVolume();
    double getSurface();

    void getCenterGeometric(FPoint in);
    void resetCenterGeometric();

    void getCenterMass(FPoint in);
    void resetCenterMass();

    double getDiameter();
    double getRadiusOfGyration();

    FPairPos3D getDimension();
}
