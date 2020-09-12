package eu.scattering.core.implementation.main.algebra.engine.extension.plane;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.implementation.main.algebra.engine.extension.ExtensionPreset;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.developmentFactory;

public class FPlaneDevelopment extends ExtensionPreset<FPlane> implements FPlane {

    private static long numberOfInstances = 0;

    private static final Statistics statsClass = developmentFactory.getStatistics().setEnabled(true);
    private final Statistics statsObject = developmentFactory.getStatistics();

    private final FPlane core;

    private FPlaneDevelopment(FPlane core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FPlane create(FPlane core) {

        return new FPlaneDevelopment(core);
    }

    public FPlane objectStatisticsEnable() {

        statsObject.setEnabled(true);

        return this;
    }

    public FPlane objectStatisticsDisable() {

        statsObject.setEnabled(false);

        return this;
    }

    @Override
    public Function<Engine, List<Boolean>> isInHalfSpace() {

        String name = "isInHalfSpace()";
        long time = System.currentTimeMillis();

        var res = core.isInHalfSpace();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isCut(Engine assembly) {

        String name = "isCut(IBaseExtensionAssembly)";
        long time = System.currentTimeMillis();

        var res = core.isCut(assembly);

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

    @Override
    public Optional<FLine> getCommonFLine(FPlane ref) {

        String name = "getCommonFLine(FPlane)";
        long time = System.currentTimeMillis();

        var res = core.getCommonFLine(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public Object clone() {

        String name = "clone()";
        long time = System.currentTimeMillis();

        var res = core.clone();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FPlane) {
            return core.equals(object);
        }

        return false;
    }

    @Override
    public boolean isExact(FPlane element) {

        String name = "isExact(FPlane)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FPlane element) {

        String name = "isSimilar(element)";
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
    public FPlane importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPlane copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = core.copy();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public FPlane self() {

        return this;
    }

    @Override
    public FPlane devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
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
    public FPlane setOriginRef(FVector origin) {

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
    public Consumer<Engine> setDistance(double distance) throws IllegalStateException {

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

    // -------------------------------------------------------------------------------------------------

    @Override
    public Optional<Long> devGetNumberOfInstances() {

        return Optional.of(numberOfInstances);
    }

    @Override
    public FPlane devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public FPlane devDescribeStatistics() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FPlane devDescribeClassStatistics() {

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
