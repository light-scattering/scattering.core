package eu.scattering.core.impl.main.engine.extension.line;

import eu.scattering.core.logic.dev.stats.Stats;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.factory.DevFactory;
import eu.scattering.core.logic.main.engine.base.BaseExtensionAssembly;
import eu.scattering.core.logic.main.engine.base.point.FPoint;
import eu.scattering.core.logic.main.engine.base.vector.FVector;
import eu.scattering.core.impl.main.engine.extension.ExtensionPreset;
import eu.scattering.core.logic.main.engine.extension.line.FLine;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.debugPrintStream;

public class FLineDev extends ExtensionPreset<FLine> implements FLine {

    private static long numberOfInstances = 0;
    private static final Stats statsClass = DevFactory.getIStats(true);
    private final Stats statsObject = DevFactory.getIStats(false);
    private final FLine core;

    private FLineDev(FLine core) {

        numberOfInstances++;

        this.core = core;
    }

    public static FLine create(FLine core) {

        return new FLineDev(core);
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

        String name = "isExact(IFLine)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FLine element) {

        String name = "isSimilar(IFLine)";
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

    @Override
    public Consumer<BaseExtensionAssembly> moveForward(double distance) throws DirectionException {

        String name = "moveForward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveForward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<BaseExtensionAssembly> moveBackward(double distance) throws DirectionException {

        String name = "moveBackward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveBackward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isPartOfRay() {

        String name = "isPartOfRay()";
        long time = System.currentTimeMillis();

        var res = core.isPartOfRay();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<BaseExtensionAssembly, List<Boolean>> isPartOfSegment() {

        String name = "isPartOfSegment()";
        long time = System.currentTimeMillis();

        var res = core.isPartOfSegment();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint getIFPoint(double length) {

        String name = "getIFPoint(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPoint(length);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getIFPointAtX(double x) {

        String name = "getIFPointAtX(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPointAtX(x);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getIFPointAtY(double y) {

        String name = "getIFPointAtY(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPointAtY(y);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getIFPointAtZ(double z) {

        String name = "getIFPointAtZ(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPointAtZ(z);

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
    public FLine devDescribeStats() {

        debugPrintStream.println(statsObject.toString());

        return self();
    }

    @Override
    public FLine devDescribeClassStats() {

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
