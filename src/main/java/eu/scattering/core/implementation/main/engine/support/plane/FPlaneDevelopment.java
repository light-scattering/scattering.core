package eu.scattering.core.implementation.main.engine.support.plane;

import eu.scattering.core.Config;
import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.injection.DevelopmentFactory;
import eu.scattering.core.design.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.design.main.engine.base.point.FPoint;
import eu.scattering.core.design.main.engine.base.vector.FVector;
import eu.scattering.core.implementation.main.engine.support.SupportPreset;
import eu.scattering.core.design.main.engine.support.line.FLine;
import eu.scattering.core.design.main.engine.support.plane.FPlane;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FPlaneDevelopment extends SupportPreset<FPlane> implements FPlane {

    private static long numberOfInstances = 0;
    private static final Statistics statsClass = DevelopmentFactory.getIStats(true);
    private final Statistics statsObject = DevelopmentFactory.getIStats(false);
    private final FPlane core;

    private FPlaneDevelopment(FPlane core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FPlane create(FPlane core) {

        return new FPlaneDevelopment(core);
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isInHalfSpace() {

        String name = "isInHalfSpace()";
        long time = System.currentTimeMillis();

        var res = core.isInHalfSpace();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isCut(BaseExtensionAssembly assembly) {

        String name = "isCut(IBaseExtensionAssembly)";
        long time = System.currentTimeMillis();

        var res = core.isCut(assembly);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getCommonIFPoint(FLine ref) {

        String name = "getCommonIFPoint(IFLine)";
        long time = System.currentTimeMillis();

        var res = core.getCommonIFPoint(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FLine> getCommonIFLine(FPlane ref) {

        String name = "getCommonIFLine(IFPlane)";
        long time = System.currentTimeMillis();

        var res = core.getCommonIFLine(ref);

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

        String name = "isExact(IFPlane)";
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

        String name = "setOriginRef(IFVector)";
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
    public Consumer<BaseExtensionAssembly> project() {

        String name = "project()";
        long time = System.currentTimeMillis();

        var res = core.project();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<BaseExtensionAssembly> reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = core.reflect();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<BaseExtensionAssembly> setDistance(double distance) throws DirectionException {

        String name = "setDistance(double)";
        long time = System.currentTimeMillis();

        var res = core.setDistance(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<BaseExtensionAssembly, List<Double>> getDistance() {

        String name = "getDistance()";
        long time = System.currentTimeMillis();

        var res = core.getDistance();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isPartOf() {

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
    public FPlane devDescribeStats() {

        Config.getDebugPrintStream().println(statsObject.toString());

        return self();
    }

    @Override
    public FPlane devDescribeClassStats() {

        Config.getDebugPrintStream().println(statsClass.toString());

        return self();
    }

    @Override
    public Optional<Statistics> devGetStats() {

        return Optional.of(statsObject);
    }

    @Override
    public Optional<Statistics> devGetClassStats() {

        return Optional.of(statsClass);
    }

    // -------------------------------------------------------------------------------------------------

    private void updateStats(String name, long startTime) {

        long time = System.currentTimeMillis() - startTime;

        statsClass.recordEvent(name, time);

        if (statsObject.isSuspended()) {
            return;
        }

        statsObject.recordEvent(name, time);
    }

}
