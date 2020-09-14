package eu.scattering.core.implementation.main.algebra.engine.extension.line;

import eu.scattering.core.design.development.statistics.Statistics;
import eu.scattering.core.design.main.algebra.engine.Engine;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.engine.extension.line.FLine;
import eu.scattering.core.implementation.main.algebra.AlgebraPresetDevelopment;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static eu.scattering.core.Config.factory;

public class FLineDevelopment extends AlgebraPresetDevelopment<FLine> implements FLine {

    private static final Statistics classStatistics = factory.getStatistics().setEnabled(true);
    private static long numberOfInstances = 0;

    public static FLine create(FLine core) {

        numberOfInstances++;
        return new FLineDevelopment(core);
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

        FLineDevelopment.numberOfInstances = numberOfInstances;
    }

    private FLineDevelopment(FLine core) {

        setCore(core);
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FLine element) {

        String name = "isExact(FLine)";
        long time = System.currentTimeMillis();

        var res = getCore().isExact(element);

        updateStats(name, time);

        return res;
    }

    @Override
    public boolean isSimilar(FLine element) {

        String name = "isSimilar(FLine)";
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
    public FLine importFromJSON(JSONObject json) {

        String name = "importFromJSON(JSONObject)";
        long time = System.currentTimeMillis();

        var res = getCore().importFromJSON(json);

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FLine copy() {

        String name = "copy()";
        long time = System.currentTimeMillis();

        var res = getCore().copy();

        updateStats(name, time);

        return res == getCore() ? this : create(res);
    }

    @Override
    public FLine self() {

        return this;
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
    public FLine setOriginRef(FVector origin) {

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
    public Consumer<Engine> moveForward(double distance) {

        String name = "moveForward(double)";
        long time = System.currentTimeMillis();

        var res = getCore().moveForward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> moveBackward(double distance) {

        String name = "moveBackward(double)";
        long time = System.currentTimeMillis();

        var res = getCore().moveBackward(distance);

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Boolean>> isPartOfRay() {

        String name = "isPartOfRay()";
        long time = System.currentTimeMillis();

        var res = getCore().isPartOfRay();

        updateStats(name, time);

        return res;
    }

    @Override
    public Function<Engine, List<Boolean>> isPartOfSegment() {

        String name = "isPartOfSegment()";
        long time = System.currentTimeMillis();

        var res = getCore().isPartOfSegment();

        updateStats(name, time);

        return res;
    }

    @Override
    public Consumer<Engine> rotate(double angle) {

        String name = "rotate(double)";
        long time = System.currentTimeMillis();

        var res = getCore().rotate(angle);

        updateStats(name, time);

        return res;
    }

    @Override
    public FPoint getFPoint(double length) {

        String name = "getFPoint(double)";
        long time = System.currentTimeMillis();

        var res = getCore().getFPoint(length);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getFPointAtX(double x) {

        String name = "getFPointAtX(double)";
        long time = System.currentTimeMillis();

        var res = getCore().getFPointAtX(x);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getFPointAtY(double y) {

        String name = "getFPointAtY(double)";
        long time = System.currentTimeMillis();

        var res = getCore().getFPointAtY(y);

        updateStats(name, time);

        return res;
    }

    @Override
    public Optional<FPoint> getFPointAtZ(double z) {

        String name = "getFPointAtZ(double)";
        long time = System.currentTimeMillis();

        var res = getCore().getFPointAtZ(z);

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
    public List<FPoint> disassemble() {

        String name = "disassemble()";
        long time = System.currentTimeMillis();

        var res = getCore().disassemble();

        updateStats(name, time);

        return res;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object object) {

        if (object instanceof FLine) {
            return getCore().equals(object);
        }

        return false;
    }

}
