package eu.scattering.core.implementation.main.algebra.engine.extension.plane;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.design.main.algebra.engine.extension.plane.FPlane;
import eu.scattering.core.implementation.development.statistics.StatisticsDefault;
import eu.scattering.core.implementation.main.algebra.AlgebraPresetDevelopment;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class FPlaneDevelopment extends AlgebraPresetDevelopment<FPlane> implements FPlane {

    private static final Statistics classStatistics = StatisticsDefault.create().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FPlane create(FPlane core) {

        numberOfInstances++;
        return new FPlaneDevelopment(core);
    }

    @Override
    protected Statistics getClassStatistics() {

        return classStatistics;
    }

    @Override
    protected long getNumberOfInstances() {

        return numberOfInstances;
    }

    @Override
    protected void setNumberOfInstances(long numberOfInstances) {

        FPlaneDevelopment.numberOfInstances = numberOfInstances;
    }

    private FPlaneDevelopment(FPlane core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public Function<Engine, List<Boolean>> isInHalfSpace() {

        String name = "isInHalfSpace()";
        long time = System.currentTimeMillis();

        var res = getCore().isInHalfSpace();

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isCut(Engine assembly) {

        String name = "isCut(IBaseExtensionAssembly)";
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
    public Consumer<Engine> project() {

        String name = "project()";
        long time = System.currentTimeMillis();

        var res = getCore().project();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> reflect() {

        String name = "reflect()";
        long time = System.currentTimeMillis();

        var res = getCore().reflect();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> setDistance(double distance) {

        String name = "setDistance(double)";
        long time = System.currentTimeMillis();

        var res = getCore().setDistance(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Double>> getDistance() {

        String name = "getDistance()";
        long time = System.currentTimeMillis();

        var res = getCore().getDistance();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Double>> getDistanceP2() {

        String name = "getDistanceP2()";
        long time = System.currentTimeMillis();

        var res = getCore().getDistanceP2();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Boolean>> isPartOf() {

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