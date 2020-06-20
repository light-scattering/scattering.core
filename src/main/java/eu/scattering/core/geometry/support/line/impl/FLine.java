package eu.scattering.core.geometry.support.line.impl;

import eu.scattering.core.geometry.IGeometryAssembly;
import eu.scattering.core.geometry.PresetGeometry;
import eu.scattering.core.geometry.base.point.IFPoint;
import eu.scattering.core.geometry.support.line.IFLine;
import org.json.JSONObject;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class FLine extends PresetGeometry<IFLine> implements IFLine {

    private FLine() { }

    public static IFLine create() {
        return new FLine();
    }

    @Override
    public Consumer<IGeometryAssembly> project() {
        return null;
    }

    @Override
    public Consumer<IGeometryAssembly> reflect() {
        return null;
    }

    @Override
    public Consumer<IGeometryAssembly> rotate() {
        return null;
    }

    @Override
    public Function<IGeometryAssembly, Double> getDistance() {
        return (e) -> (double) e.disassemble().size();
    }

    @Override
    public Function<IGeometryAssembly, Boolean> belongsTo() {
        return null;
    }

    @Override
    public Function<IGeometryAssembly, Boolean> isCloseTo() {
        return null;
    }

    @Override
    public boolean isExact(IFLine element) {
        return false;
    }

    @Override
    public boolean isSimilar(IFLine element) {
        return false;
    }

    @Override
    public JSONObject exportToJSON() {
        return null;
    }

    @Override
    public IFLine importFromJSON(JSONObject json) {
        return null;
    }

    @Override
    public IFLine copy() {
        return null;
    }

    @Override
    public IFLine self() {
        return null;
    }

    @Override
    public Object clone() {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return false;
    }

    @Override
    public IFLine devDescribe() {
        return null;
    }

    @Override
    public IFLine devDescribe(String message) {
        return null;
    }

    @Override
    public IFLine set(IFLine element) {
        return null;
    }

    @Override
    public IFLine swap(IFLine element) {
        return null;
    }

    @Override
    public List<IFPoint> disassemble() {
        return null;
    }
}
