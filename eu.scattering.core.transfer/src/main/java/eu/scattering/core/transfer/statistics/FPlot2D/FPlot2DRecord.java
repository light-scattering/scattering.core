package eu.scattering.core.transfer.statistics.FPlot2D;

import org.json.JSONObject;

public interface FPlot2DRecord {

    double getX();
    void setX(double x);

    double getY();
    void setY(double y);

    // -------------------------------------------------------------------------------------------------

    JSONObject toJSON();
}
