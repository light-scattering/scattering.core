package eu.scattering.core.impl.mutable.geometry.shape;

import eu.scattering.core.design.mutable.geometry.primitive.point.FPoint;
import eu.scattering.core.design.mutable.geometry.shape.sphere.FSphere;
import eu.scattering.core.transfer.TransferFactory;
import eu.scattering.core.transfer.TransferFactoryConcrete;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import eu.scattering.core.transfer.container.position.FPos3D.FPos3D;

import java.util.List;

import static eu.scattering.core.impl.ConfigDef.EPSILON;

public class FSphereDef implements FSphere {
    private static final TransferFactory factory = TransferFactoryConcrete.create();
    private static final String JSON_MAIN = "center";
    private static final String JSON_RADIUS = "radius";

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private FPoint center;

    private double radius;

    private FSphereDef() {}

    public static FSphere create(FPoint refCenter, double radius) {

        var fSphere = new FSphereDef();

        fSphere.setRefCenter(refCenter);
        fSphere.setRadius(radius);


        return fSphere;
    }

    @Override
    public FPoint getRefCenter() {

        return this.center;
    }

    @Override
    public FSphere setRefCenter(FPoint refCenter) {

        if (refCenter == null) {
            throw new NullPointerException("The FPoint center cannot be null");
        }

        this.center = refCenter;

        return this;
    }

    @Override
    public double getRadius() {

        return this.radius;
    }

    @Override
    public FSphere setRadius(double radius) {

        if (radius <= 0) {
            throw new IllegalArgumentException("The FPoint radius must be greater than zero");
        }

        this.radius = radius;

        return this;
    }

    // -------------------------------------------------------------------------------------------------
    // The following fields do not have to modified while extending the class.
    // Their behaviour should be correct, however, it is not guaranteed that the current implementation is optimal.
    // -------------------------------------------------------------------------------------------------

    @Override
    public double getOuterRadius() {

        return getRadius();
    }

    @Override
    public FSphere setOuterRadius(double radius) {

        return setRadius(radius);
    }

    @Override
    public double getInnerRadius() {


        return getRadius();
    }

    @Override
    public FSphere setInnerRadius(double radius) {

        return setRadius(radius);
    }

    @Override
    public FSphere setPosCenter(double x, double y, double z) {

        center.set(x, y, z);

        return this;
    }

    @Override
    public FSphere setPosCenter(FPoint fPoint) {

        center.applyStateFrom(fPoint);

        return this;
    }

    @Override
    public FSphere setPosCenter(FPos3D fPos3D) {

        center.applyStateFrom(fPos3D);

        return this;
    }

    @Override
    public FSphere setPosCenterX(double x) {

        center.setX(x);

        return this;
    }

    @Override
    public FSphere setPosCenterY(double y) {

        center.setY(y);

        return this;
    }

    @Override
    public FSphere setPosCenterZ(double z) {

        center.setZ(z);

        return this;
    }

    @Override
    public void getPosCenter(FPoint in) {

        in.applyStateFrom(center);
    }

    @Override
    public double getVolume() {
        double r = getRadius();

        return 4 * Math.PI * r * r * r / 3;
    }

    @Override
    public FSphere setVolume(double volume) {

        setRadius(Math.pow(0.75 * volume / Math.PI, 1.0 / 3));

        return this;
    }

    @Override
    public double getSurface() {
        double r = getRadius();

        return 4 * Math.PI * r * r;
    }

    @Override
    public FSphere setSurface(double surface) {

        setRadius(Math.pow(0.25 * surface / Math.PI, 0.5));

        return this;
    }

    @Override
    public boolean contains(double x, double y, double z, boolean include) {
        double tX = x - center.getX();
        double tY = y - center.getY();
        double tZ = z - center.getZ();

        double radP2 = radius * radius;
        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        if (!include) {
            return distP2 < radP2 - EPSILON;
        }

        return distP2 < radP2 + EPSILON;
    }

    @Override
    public boolean contains(FPoint fPoint, boolean include) {

        return contains(fPoint.getX(), fPoint.getY(), fPoint.getZ(), include);
    }

    @Override
    public boolean contains(FPos3D fPos3D, boolean include) {

        return contains(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2(), include);
    }

    @Override
    public boolean intersects(FSphere shape) {
        double distP2 = center.getDistanceP2(shape.getRefCenter());
        double minDistP2 = (radius + shape.getRadius()) * (radius + shape.getRadius());

        return distP2 < minDistP2 - EPSILON;
    }

    @Override
    public void getVolumeStream(FStream3D stream, double delta) {
        double radiusParsed = radius + delta ;
        double radiusP2 = radius * radius;

        double minX = center.getX() - radiusParsed;
        double maxX = center.getX() + radiusParsed;
        double minY = center.getY() - radiusParsed;
        double maxY = center.getY() + radiusParsed;
        double minZ = center.getZ() - radiusParsed;
        double maxZ = center.getZ() + radiusParsed;

        stream.reset();

        for (double x = minX ; x <= maxX ; x += delta) {
            for (double y = minY ; y <= maxY ; y += delta) {
                for (double z = minZ ; z <= maxZ ; z += delta) {
                    if (contains(radiusP2, x, y, z)) {
                        stream.add(x, y, z, 0);
                    }
                }
            }
        }
    }

    @Override
    public void getVolumeStream(FStream3DI stream, double delta) {

    }

    private boolean contains(double radiusP2, double x, double y, double z) {
        double tX = x - center.getX();
        double tY = y - center.getY();
        double tZ = z - center.getZ();

        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        return distP2 < radiusP2;
    }










    @Override
    public List<FPoint> disassemble() {
        return null;
    }



    @Override
    public void getSurfaceStream(FStream3DI stream, double delta) {

    }

    @Override
    public void getSurfaceStream(FStream3D stream, double delta) {

    }

    @Override
    public boolean push(FSphere arg) {
        return false;
    }

    @Override
    public boolean push(FSphere arg, List<FSphere> field, int bounce) {
        return false;
    }

    @Override
    public boolean project(FPoint aim, List<FSphere> field) {
        return false;
    }


}
