package eu.scattering.core.main.engine.extension.line.impl.dec;

import eu.scattering.core.dev.stats.IStats;
import eu.scattering.core.support.exception.DirectionException;
import eu.scattering.core.dev.DevFactory;
import eu.scattering.core.main.engine.base.IBaseExtensionAssembly;
import eu.scattering.core.main.engine.base.point.IFPoint;
import eu.scattering.core.main.engine.base.vector.IFVector;
import eu.scattering.core.main.engine.extension.ExtensionPreset;
import eu.scattering.core.main.engine.extension.line.IFLine;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.debugPrintStream;

public class FLineDev extends ExtensionPreset<IFLine> implements IFLine {

    private static long numberOfInstances = 0;
    private static final IStats statsClass = DevFactory.getIStats(true);
    private final IStats statsObject = DevFactory.getIStats(false);
    private final IFLine core;

    private FLineDev(IFLine core) {

        numberOfInstances++;

        this.core = core;
    }

    public static IFLine create(IFLine core) {

        return new FLineDev(core);
    }

    @Override
    public IFLine devDescribe() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = core.devDescribe();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public boolean isExact(IFLine element) {

        String name = "isExact(IFLine)";
        long time = System.currentTimeMillis();

        var res = core.isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(IFLine element) {

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
    public IFLine importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = core.importFromJSON(json);

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFLine copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = core.copy();

        updateStats(name, time);

        return res == core ? this : create(res);
    }

    @Override
    public IFLine self() {

        return this;
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
    public IFLine setOriginRef(IFVector origin) {

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

    @Override
    public Consumer<IBaseExtensionAssembly> moveForward(double distance) throws DirectionException {

        String name = "moveForward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveForward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<IBaseExtensionAssembly> moveBackward(double distance) throws DirectionException {

        String name = "moveBackward(double)";
        long time = System.currentTimeMillis();

        var res = core.moveBackward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOfRay() {

        String name = "isPartOfRay()";
        long time = System.currentTimeMillis();

        var res = core.isPartOfRay();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<IBaseExtensionAssembly, List<Boolean>> isPartOfSegment() {

        String name = "isPartOfSegment()";
        long time = System.currentTimeMillis();

        var res = core.isPartOfSegment();

        updateStats(name, time);

        return res;
    }

    @Override
    public IFPoint getIFPoint(double length) {

        String name = "getIFPoint(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPoint(length);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<IFPoint> getIFPointAtX(double x) {

        String name = "getIFPointAtX(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPointAtX(x);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<IFPoint> getIFPointAtY(double y) {

        String name = "getIFPointAtY(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPointAtY(y);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<IFPoint> getIFPointAtZ(double z) {

        String name = "getIFPointAtZ(double)";
        long time = System.currentTimeMillis();

        var res = core.getIFPointAtZ(z);

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

    // -------------------------------------------------------------------------------------------------

    @Override
    public Object clone() {

        return create((IFLine) core.clone());

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

        if (object instanceof IFLine) {
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
    public IFLine devResetNumberOfInstances() {

        numberOfInstances = 0;

        return self();
    }

    @Override
    public IFLine devDescribeStats() {

        debugPrintStream.println(statsObject.toString());

        return self();
    }

    @Override
    public IFLine devDescribeClassStats() {

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
