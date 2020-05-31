package eu.scattering.core.geometry.d1.fvector.impl;

import eu.scattering.core.CoreObject;
import eu.scattering.core.exception.SamePositionException;
import eu.scattering.core.geometry.d0.IFPoint;
import eu.scattering.core.geometry.d1.fvector.IFVector;

import java.util.ArrayList;
import java.util.List;

public class FVector extends CoreObject implements IFVector {

    private IFPoint initial;
    private IFPoint terminal;

    private FVector() { }

    private FVector(IFPoint initial, IFPoint terminal) {
        this.initial = initial;
        this.terminal = terminal;
    }

    @Override
    public IFVector replace(IFVector fVector) {
        return null;
    }

    @Override
    public IFVector replace(IFPoint initial, IFPoint terminal) {
        return new FVector(initial, terminal);
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof IFVector)) {
            return false;
        }

        IFVector fVector = (IFVector) object;

        return initial.equals(fVector.getInitial()) && terminal.equals(fVector.getTerminal());
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public FVector clone() {
        return copy();
    }

    @Override
    public IFPoint getInitial() {
        return initial;
    }

    @Override
    public FVector setInitial(IFPoint fPoint) {
        initial = fPoint;

        return this;
    }

    @Override
    public IFPoint getTerminal() {
        return terminal;
    }

    @Override
    public IFVector setTerminal(IFPoint fPoint) {
        terminal = fPoint;

        return this;
    }

    @Override
    public IFVector add(IFVector fVector) {
        originShift(this);
        originShift(fVector);

        terminal.add(fVector.getTerminal());

        originRestore(this);
        originRestore(fVector);

        return this;
    }

    @Override
    public IFVector sub(IFVector fVector) {
        originShift(this);
        originShift(fVector);

        terminal.sub(fVector.getTerminal());

        originRestore(this);
        originRestore(fVector);

        return this;
    }

    @Override
    public IFVector add(IFPoint fPoint) {
        initial.add(fPoint);
        terminal.add(fPoint);

        return this;
    }

    @Override
    public IFVector add(double x, double y, double z) {
        initial.add(x, y, z);
        terminal.add(x, y, z);

        return this;
    }

    @Override
    public IFVector addX(double x) {
        initial.addX(x);
        terminal.addX(x);

        return null;
    }

    @Override
    public IFVector addY(double y) {
        initial.addY(y);
        terminal.addY(y);

        return this;
    }

    @Override
    public IFVector addZ(double z) {
        initial.addZ(z);
        terminal.addZ(z);

        return this;
    }

    @Override
    public IFVector sub(IFPoint fPoint) {
        initial.sub(fPoint);
        terminal.sub(fPoint);

        return this;
    }

    @Override
    public IFVector sub(double x, double y, double z) {
        initial.sub(x, y, z);
        terminal.sub(x, y, z);

        return this;
    }

    @Override
    public IFVector subX(double x) {
        initial.subX(x);
        terminal.subX(x);

        return this;
    }

    @Override
    public IFVector subY(double y) {
        initial.subY(y);
        terminal.subY(y);

        return this;
    }

    @Override
    public IFVector subZ(double z) {
        initial.subZ(z);
        terminal.subZ(z);

        return this;
    }

    @Override
    public IFVector mul(IFPoint fPoint) {
        initial.mul(fPoint);
        terminal.mul(fPoint);

        return this;
    }

    @Override
    public IFVector mul(double x, double y, double z) {
        initial.mul(x, y, z);
        terminal.mul(x, y, z);

        return this;
    }

    @Override
    public IFVector mulX(double x) {
        initial.mulX(x);
        terminal.mulX(x);

        return this;
    }

    @Override
    public IFVector mulY(double y) {
        initial.mulY(y);
        terminal.mulY(y);

        return this;
    }

    @Override
    public IFVector mulZ(double z) {
        initial.mulZ(z);
        terminal.mulZ(z);

        return this;
    }

    @Override
    public IFVector div(IFPoint fPoint) {
        initial.div(fPoint);
        terminal.div(fPoint);

        return this;
    }

    @Override
    public IFVector div(double x, double y, double z) {
        initial.div(x, y, z);
        terminal.div(x, y, z);

        return this;
    }

    @Override
    public IFVector divX(double x) {
        initial.divX(x);
        terminal.divX(x);

        return this;
    }

    @Override
    public IFVector divY(double y) {
        initial.divY(y);
        terminal.divY(y);

        return this;
    }

    @Override
    public IFVector divZ(double z) {
        initial.divZ(z);
        terminal.divZ(z);

        return this;
    }

    @Override
    public IFVector scale(double scaleFactor) {
        return mul(scaleFactor, scaleFactor, scaleFactor);
    }

    @Override
    public List<IFPoint> getIFPoints() {
        List<IFPoint> fPointList = new ArrayList<>();
        fPointList.add(initial);
        fPointList.add(terminal);

        return fPointList;
    }

    @Override
    public boolean isExact(IFVector element) {
        return false;
    }

    @Override
    public boolean isSimilar(IFVector element) {
        return false;
    }

    @Override
    public int getHashCode() {
        return 0;
    }

    @Override
    public String exportToJSON() {
        return null;
    }

    @Override
    public IFVector importFromJSON() {
        return null;
    }

    @Override
    public FVector copy() {
        return new FVector(initial.copy(), terminal.copy());
    }

    @Override
    public double getPolarAngle() {

        originShift(this);
        double polar = terminal.getPolarAngle();
        originRestore(this);

        return polar;
    }

    @Override
    public IFVector setPolarAngle(double polar) {

        originShift(this);
        terminal.setSphericalCoordinates(polar, getAzimuthalAngle(), getRadius());
        originRestore(this);

        return this;
    }

    @Override
    public double getAzimuthalAngle() {

        originShift(this);
        double azimuthal = terminal.getAzimuthalAngle();
        originRestore(this);

        return azimuthal;
    }

    @Override
    public IFVector setAzimuthalAngle(double azimuthal) {

        originShift(this);
        terminal.setSphericalCoordinates(getPolarAngle(), azimuthal, getRadius());
        originRestore(this);

        return this;
    }

    @Override
    public double getRadius() {

        double distanceX = terminal.getX() - initial.getX();
        double distanceY = terminal.getY() - initial.getY();
        double distanceZ = terminal.getZ() - initial.getZ();

        return Math.sqrt((distanceX * distanceX) + (distanceY * distanceY) + (distanceZ * distanceZ));
    }

    @Override
    public IFVector setRadius(double distance) throws SamePositionException {

        originShift(this);
        terminal.setRadius(distance);
        originRestore(this);

        return this;
    }

    @Override
    public IFVector setSphericalCoordinates(double polar, double azimuthal, double radius) {
        return null;
    }

    @Override
    public IFVector randomizeOnSphere(double radius) {
        return null;
    }

    @Override
    public IFVector normalize() {
        return null;
    }

    @Override
    public IFVector reflect() {

        originShift(this);
        terminal.reflect();
        originRestore(this);

        return this;
    }

    private IFPoint originShift(IFVector fVector) {
        return fVector.getTerminal().sub(fVector.getInitial());
    }

    private IFPoint originRestore(IFVector fVector) {
        return fVector.getTerminal().add(fVector.getInitial());
    }

}
