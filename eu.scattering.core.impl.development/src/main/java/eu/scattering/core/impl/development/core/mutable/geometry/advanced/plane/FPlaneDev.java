package eu.scattering.core.impl.development.core.mutable.geometry.advanced.plane;

import eu.scattering.core.design.debug.stats.Stats;
import eu.scattering.core.design.core.mutable.geometry.Geometry;
import eu.scattering.core.design.core.mutable.geometry.simple.point.FPoint;
import eu.scattering.core.design.core.mutable.geometry.simple.vector.FVector;
import eu.scattering.core.design.core.mutable.geometry.advanced.line.FLine;
import eu.scattering.core.design.core.mutable.geometry.advanced.plane.FPlane;
import eu.scattering.core.impl.production.debug.stats.StatsProd;
import eu.scattering.core.impl.development.core.mutable.MutablePresetDev;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FPlaneDev extends MutablePresetDev<FPlane> implements FPlane {

    private static final Stats classStatistics = StatsProd.create().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FPlane create(FPlane core) {

        numberOfInstances++;
        return new FPlaneDev(core);
    }

    @Override
    protected Stats getClassStatistics() {

        return classStatistics;
    }

    @Override
    protected long getNumberOfInstances() {

        return numberOfInstances;
    }

    @Override
    protected void setNumberOfInstances(long numberOfInstances) {

        FPlaneDev.numberOfInstances = numberOfInstances;
    }

    private FPlaneDev(FPlane core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Function<Geometry, List<Boolean>> isInHalfSpace() {

        String name = "isInHalfSpace()";
        long time = System.currentTimeMillis();

        var res = getCore().isInHalfSpace();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isCut(Geometry assembly) {

        String name = "isCut(BaseExtensionAssembly)";
        long time = System.currentTimeMillis();

        var res = getCore().isCut(assembly);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getCommonFPoint(FLine ref) {

        String name = "getCommonFPoint(FLine)";
        long time = System.currentTimeMillis();

        var res = getCore().getCommonFPoint(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FLine> getCommonFLine(FPlane ref) {

        String name = "getCommonFLine(FPlane)";
        long time = System.currentTimeMillis();

        var res = getCore().getCommonFLine(ref);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isExact(FPlane element) {

        String name = "isExact(FPlane)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FPlane element) {

        String name = "isSimilar(element)";
        long time = System.currentTimeMillis();

        var res = getCore().isSimilar(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public JSONObject exportToJSON() {

        String name = "exportToJSON()";
        long time = System.currentTimeMillis();

        var res = getCore().exportToJSON();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPlane importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = getCore().importFromJSON(json);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPlane copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = getCore().copy();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPlane self() {

        return this;
    }

    @Override
    public FPlane devDesc() {

        String name = "devDescribe()";
        long time = System.currentTimeMillis();

        var res = getCore().devDesc();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FVector getOrigin() {

        String name = "getOrigin()";
        long time = System.currentTimeMillis();

        var res = getCore().getOrigin();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPlane setOriginRef(FVector origin) {

        String name = "setOriginRef(FVector)";
        long time = System.currentTimeMillis();

        var res = getCore().setOriginRef(origin);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FPoint getBase() {

        String name = "getBase()";
        long time = System.currentTimeMillis();

        var res = getCore().getBase();

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint getHead() {

        String name = "getHead()";
        long time = System.currentTimeMillis();

        var res = getCore().getHead();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Geometry> project() {

        String name = "project()";
        long time = System.currentTimeMillis();

        var res = getCore().project();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Geometry> reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = getCore().reflect();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Geometry> setDistance(double distance) {

        String name = "setDistance(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setDistance(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Geometry, List<Double>> getDistance() {

        String name = "getDistance()";
        long time = System.currentTimeMillis();

        var res = getCore().getDistance();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Geometry, List<Double>> getDistanceP2() {

        String name = "getDistanceP2()";
        long time = System.currentTimeMillis();

        var res = getCore().getDistanceP2();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Geometry, List<Boolean>> isPartOf() {

        String name = "isPartOf()";
        long time = System.currentTimeMillis();

        var res = getCore().isPartOf();

        updateStats(name, time);

        return res;
    }

    @Override
    public List<FPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = getCore().disassemble();

        updateStats(name, time);

        return res;
    }

}