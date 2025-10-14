package eu.scattering.core.design.statistics.construct.utils;

import org.json.JSONObject;

public interface FPlot2DRecord {

    double getX();
    void setX(double x);

    double getY();
    void setY(double y);

    // -------------------------------------------------------------------------------------------------

    JSONObject toJSON();
}
