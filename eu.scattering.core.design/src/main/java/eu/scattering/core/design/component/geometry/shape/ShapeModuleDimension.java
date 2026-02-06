package eu.scattering.core.design.component.geometry.shape;

import eu.scattering.core.design.utility.annotation.Fragment;
import eu.scattering.core.design.transfer.complex.FBufferData;
import eu.scattering.core.design.storage.buffer.FBuffer;
import eu.scattering.core.design.storage.layer.FLayer;

import java.util.List;

public interface ShapeModuleDimension {

    double getRadius();
    Shape setRadius(double radius);

    double getInnerRadius();
    Shape setInnerRadius(double radius);

    double getCoatWidth(int index);
    Shape setCoatWidth(int index, double width);

    int getCoatCount();
    int getLayerCount();

    Shape applyCoatsFrom(Shape shape);

    Shape addCoat(double width);
    Shape addCoat(double... width);

    Shape addInternalCoat(double width);
    Shape addInternalCoat(double... width);

    Shape removeCoats();

    Shape scaleSize(double factor);

    // -------------------------------------------------------------------------------------------------

    double getLayerVolume(int index);

    double getCoatVolume(int index);
    double getCoatVolume();

    double getLayerSurface(int index);

    double getCoatSurface(int index);
    double getCoatSurface();

    // -------------------------------------------------------------------------------------------------

    double getVolumeAlgebraic();

    double fillVolumeLayerOverlap(FLayer in, Iterable<? extends Shape> field);

    double fillVolumeLayer(FLayer in);
    double fillVolumeArray(FBuffer<FBufferData> in);

    // -------------------------------------------------------------------------------------------------

    double getSurfaceAlgebraic();

    double fillSurfaceLayerOverlap(FLayer in, Iterable<? extends Shape> field);

    double fillSurfaceLayer(FLayer in);
    double fillSurfaceArray(FBuffer<FBufferData> in);

    // -------------------------------------------------------------------------------------------------

    @Fragment
    double getLayerWidthRemaining(int index);

    @Fragment
    double fillVolumeLayer(FLayer in, List<? extends Shape> structure);
    @Fragment
    double fillVolumeArray(FBuffer<FBufferData> in, List<? extends Shape> structure);

    @Fragment
    double fillSurfaceLayer(FLayer in, List<? extends Shape> structure);
    @Fragment
    double fillSurfaceArray(FBuffer<FBufferData> in, List<? extends Shape> structure);
}
