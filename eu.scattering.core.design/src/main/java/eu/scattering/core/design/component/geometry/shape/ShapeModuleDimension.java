package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.util.annotation.Fragment;
import eu.scattering.core.design.util.container.DipoleData;
import eu.scattering.core.transfer.container.buffer.array.FArray;
import eu.scattering.core.transfer.container.buffer.layer.FLayerCounter;

import java.util.List;

public interface ShapeModuleDimension {

    double getRadius();
    Shape setRadius(double radius);

    double getInnerRadius();
    Shape setInnerRadius(double radius);

    double getCoatWidth(int index);
    Shape setCoatWidth(int index, double width);

    int getLayerCount();
    int getCoatCount();

    Shape applyCoatsFrom(Shape shape);

    Shape addCoat(double width);
    Shape addCoat(double... width);

    Shape addCoatInternal(double width);
    Shape addCoatInternal(double... width);

    Shape removeCoats();

    // -------------------------------------------------------------------------------------------------

    double getLayerVolume(int index);

    double getCoatVolume(int index);
    double getCoatVolume();

    double getLayerSurface(int index);

    double getCoatSurface(int index);
    double getCoatSurface();

    // -------------------------------------------------------------------------------------------------

    double getVolumeAlgebraic();

    double fillVolumeLayerOverlap(FLayerCounter in, Iterable<? extends Shape> field);

    double fillVolumeLayer(FLayerCounter in);
    double fillVolumeArray(FArray<DipoleData> in);

    // -------------------------------------------------------------------------------------------------

    double getSurfaceAlgebraic();

    double fillSurfaceLayerOverlap(FLayerCounter in, Iterable<? extends Shape> field);

    double fillSurfaceLayer(FLayerCounter in);
    double fillSurfaceArray(FArray<DipoleData> in);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    double getLayerWidthRemaining(int index);

    @Fragment
    double fillVolumeLayer(FLayerCounter in, List<? extends Shape> structure);
    @Fragment
    double fillVolumeArray(FArray<DipoleData> in, List<? extends Shape> structure);

    @Fragment
    double fillSurfaceLayer(FLayerCounter in, List<? extends Shape> structure);
    @Fragment
    double fillSurfaceArray(FArray<DipoleData> in, List<? extends Shape> structure);
}
