package eu.scattering.core.implementation.main.algebra.engine.extension.line;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.implementation.main.algebra.engine.extension.ExtensionPreset;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.statisticsFactory;

public class FLineDevelopment extends ExtensionPreset<FLine> implements FLine {

    private static long numberOfInstances = 0;

    private static final Statistics statsClass = statisticsFactory.getStatistics().setEnabled();
    private final Statistics statsObject = statisticsFactory.getStatistics();

    private final FLine core;

    private FLineDevelopment(FLine core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FLine create(FLine core) {

        return new FLineDevelopment(core);
    }

    public FLine objectStatisticsEnable() {

        statsObject.setEnabled();

        return this;
    }

    public FLine objectStatisticsDisable() {

        statsObject.setDisabled();

        return this;
    }

    @Override
    public FLine devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(FLine element) {

        String name = "isExact(FLine)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FLine element) {

        String name = "isSimilar(FLine)";
        long time = System.currentTimeMillis();

        var res = core.isSimilar(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public JSONObject exportToJSON() {

        String name = "exportToJSON()";
        long time = System.currentTimeMillis();

        var res = core.exportToJSON();

        updateStats(name, time);

        return res;
    }

    @Override
    public FLine importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FLine copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = core.copy();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FLine self() {

        return this;
    }

    @Override
    public FVector getOrigin() {

        String name = "getOrigin()";
        long time = System.currentTimeMillis();

        var res = core.getOrigin();

        updateStats(name, time);

        return res;
    }

    @Override
    public FLine setOriginRef(FVector origin) {

        String name = "setOriginRef(FVector)";
        long time = System.currentTimeMillis();

        var res = core.setOriginRef(origin);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPoint getBase() {

        String name = "getBase()";
        long time = System.currentTimeMillis();

        var res = core.getBase();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint getHead() {

        String name = "getHead()";
        long time = System.currentTimeMillis();

        var res = core.getHead();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> project() {

        String name = "project()";
        long time = System.currentTimeMillis();

        var res = core.project();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = core.reflect();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> setDistance(double distance) {

        String name = "setDistance(double)";
        long time = System.currentTimeMillis();

        var res = core.setDistance(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Double>> getDistance() {

        String name = "getDistance()";
        long time = System.currentTimeMillis();

        var res = core.getDistance();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Boolean>> isPartOf() {

        String name = "isPartOf()";
        long time = System.currentTimeMillis();

        var res = core.isPartOf();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> moveForward(double distance) {

        String name = "moveForward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveForward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> moveBackward(double distance) {

        String name = "moveBackward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveBackward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Boolean>> isPartOfRay() {

        String name = "isPartOfRay()";
        long time = System.currentTimeMillis();

        var res = core.isPartOfRay();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Boolean>> isPartOfSegment() {

        String name = "isPartOfSegment()";
        long time = System.currentTimeMillis();

        var res = core.isPartOfSegment();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint getFPoint(double length) {

        String name = "getFPoint(double)";
        long time = System.currentTimeMillis();

        var res = core.getFPoint(length);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {

        String name = "getFPointAtX(double)";
        long time = System.currentTimeMillis();

        var res = core.getFPointAtX(x);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {

        String name = "getFPointAtY(double)";
        long time = System.currentTimeMillis();

        var res = core.getFPointAtY(y);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {

        String name = "getFPointAtZ(double)";
        long time = System.currentTimeMillis();

        var res = core.getFPointAtZ(z);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getCommonFPoint(FLine ref) {

        String name = "getCommonFPoint(FLine)";
        long time = System.currentTimeMillis();

        var res = core.getCommonFPoint(ref);

        updateStats(name, time);

        return res;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        return create((FLine) core.clone());

    }

    @Override
    public String toString() {

        return core.toString();
    }

    @Override
    public int hashCode() {

        return core.hashCode();
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FLine) {
            return core.equals(object);
        }

        return false;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.of(numberOfInstances);
    }

    @Override
    public FLine devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public FLine devDescribeStatistics() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FLine devDescribeClassStatistics() {

        Config.getDebugPrintStream().println(statsClass.toString());

        return self();
    }

    @Override
    public Optional<Statistics> devGetStatistics() {

        return Optional.of(statsObject);
    }

    @Override
    public Optional<Statistics> devGetClassStatistics() {

        return Optional.of(statsClass);
    }

    // -------------------------------------------------------------------------------------------------

    private void updateStats(String name, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        statsClass.recordEvent(name, time);

        if (statsObject.isEnabled()) {
            return;
        }

        statsObject.recordEvent(name, time);
    }

}
