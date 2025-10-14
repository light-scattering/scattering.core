package eu.scattering.core.impl.statistics.plot2d;

import eu.scattering.core.design.statistics.construct.utils.FPlot2DRecord;
import org.json.JSONObject;

public class FPlot2DRecordDef implements FPlot2DRecord {
    private static final String JSON_X = "x";
    private static final String JSON_Y = "y";

    private double x;
    private double y;

    private FPlot2DRecordDef(double x, double y) {

        this.x = x;
        this.y = y;
    }

    protected static FPlot2DRecord create(double x, double y) {

        return new FPlot2DRecordDef(x, y);
    }

    protected static FPlot2DRecord create(JSONObject json) {

        return new FPlot2DRecordDef(json.getDouble(JSON_X), json.getDouble(JSON_Y));
    }

    @Override
    public double getX() {

        return this.x;
    }

    @Override
    public void setX(double x) {

        this.x = x;
    }

    @Override
    public double getY() {

        return this.y;
    }

    @Override
    public void setY(double y) {

        this.y = y;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_X, this.x);
        json.put(JSON_Y, this.y);

        return json;
    }

    //--------------------------------------------------

    @Override
    public String toString() {

        return toJSON().toString();
    }
}
