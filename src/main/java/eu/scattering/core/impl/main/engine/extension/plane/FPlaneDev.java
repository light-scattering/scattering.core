package eu.scattering.core.impl.main.engine.extension.plane;

import eu.scattering.core.logic.dev.stats.Stats;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.factory.DevFactory;
import eu.scattering.core.logic.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.base.vector.FVector;
import eu.scattering.core.impl.main.engine.extension.ExtensionPreset;
import eu.scattering.core.logic.main.engine.extension.line.FLine;
import eu.scattering.core.logic.main.engine.extension.plane.FPlane;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.debugPrintStream;

public class FPlaneDev extends ExtensionPreset<FPlane> implements FPlane {

    private static long numberOfInstances = 0;
    private static final Stats statsClass = DevFactory.getIStats(true);
    private final Stats statsObject = DevFactory.getIStats(false);
    private final FPlane core;

    private FPlaneDev(FPlane core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FPlane create(FPlane core) {

        return new FPlaneDev(core);
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

        debugPrintStream.println(statsObject.toString());

        return self();
    }

    @Override
    public FPlane devDescribeClassStats() {

        debugPrintStream.println(statsClass.toString());

        return self();
    }

    @Override
    public Optional<Stats> devGetStats() {

        return Optional.of(statsObject);
    }

    @Override
    public Optional<Stats> devGetClassStats() {

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
