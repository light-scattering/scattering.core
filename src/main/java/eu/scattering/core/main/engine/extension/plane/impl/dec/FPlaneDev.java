package eu.scattering.core.main.engine.extension.plane.impl.dec;

import eu.scattering.core.dev.stats.IStats;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.dev.DevFactory;
import eu.scattering.core.main.engine.base.IBaseExtensionAssembly;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.vector.IFVector;
import eu.scattering.core.main.engine.extension.ExtensionPreset;
import eu.scattering.core.main.engine.extension.line.IFLine;
import eu.scattering.core.main.engine.extension.plane.IFPlane;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.debugPrintStream;

public class FPlaneDev extends ExtensionPreset<IFPlane> implements IFPlane {

    private static long numberOfInstances = 0;
    private static final IStats statsClass = DevFactory.getIStats(true);
    private final IStats statsObject = DevFactory.getIStats(false);
    private final IFPlane core;

    private FPlaneDev(IFPlane core) {

        numberOfInstances++;

        this.core = core;
    }

    public static IFPlane create(IFPlane core) {

        return new FPlaneDev(core);
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isInHalfSpace() {

        String name = "isInHalfSpace()";
        long time = System.currentTimeMillis();

        var res = core.isInHalfSpace();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isCut(IBaseExtensionAssembly assembly) {

        String name = "isCut(IBaseExtensionAssembly)";
        long time = System.currentTimeMillis();

        var res = core.isCut(assembly);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<IFPoint> getCommonIFPoint(IFLine ref) {

        String name = "getCommonIFPoint(IFLine)";
        long time = System.currentTimeMillis();

        var res = core.getCommonIFPoint(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<IFLine> getCommonIFLine(IFPlane ref) {

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

        if (object instanceof IFPlane) {
            return core.equals(object);
        }

        return false;
    }

    @Override
    public boolean isExact(IFPlane element) {

        String name = "isExact(IFPlane)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(IFPlane element) {

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
    public IFPlane importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPlane copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = core.copy();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPlane self() {

        return this;
    }

    @Override
    public IFPlane devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFVector getOrigin() {

        String name = "getOrigin()";
        long time = System.currentTimeMillis();

        var res = core.getOrigin();

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPlane setOriginRef(IFVector origin) {

        String name = "setOriginRef(IFVector)";
        long time = System.currentTimeMillis();

        var res = core.setOriginRef(origin);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFPoint getBase() {

        String name = "getBase()";
        long time = System.currentTimeMillis();

        var res = core.getBase();

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint getHead() {

        String name = "getHead()";
        long time = System.currentTimeMillis();

        var res = core.getHead();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<IBaseExtensionAssembly> project() {

        String name = "project()";
        long time = System.currentTimeMillis();

        var res = core.project();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<IBaseExtensionAssembly> reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = core.reflect();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<IBaseExtensionAssembly> setDistance(double distance) throws DirectionException {

        String name = "setDistance(double)";
        long time = System.currentTimeMillis();

        var res = core.setDistance(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Double>> getDistance() {

        String name = "getDistance()";
        long time = System.currentTimeMillis();

        var res = core.getDistance();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOf() {

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
    public IFPlane devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public IFPlane devDescribeStats() {

        debugPrintStream.println(statsObject.toString());

        return self();
    }

    @Override
    public IFPlane devDescribeClassStats() {

        debugPrintStream.println(statsClass.toString());

        return self();
    }

    @Override
    public Optional<IStats> devGetStats() {

        return Optional.of(statsObject);
    }

    @Override
    public Optional<IStats> devGetClassStats() {

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
