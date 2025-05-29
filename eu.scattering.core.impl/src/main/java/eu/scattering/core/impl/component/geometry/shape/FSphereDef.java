package eu.scattering.core.impl.component.geometry.shape;

import eu.scattering.core.design.FactoryDesign;
import eu.scattering.core.design.component.geometry.Geometry;
import eu.scattering.core.design.component.geometry.base.point.FPoint;
import eu.scattering.core.design.component.geometry.base.vector.FVector;
import eu.scattering.core.design.component.geometry.shape.sphere.FSphere;
import eu.scattering.core.design.engine.rotate.FRotEngine;
import eu.scattering.core.design.helper.trigonometry.FTrigHelper;
import eu.scattering.core.transfer.container.buffer.FCache.FCache;
import eu.scattering.core.transfer.container.buffer.FStream3D.FStream3D;
import eu.scattering.core.transfer.container.buffer.FStream3DI.FStream3DI;
import eu.scattering.core.transfer.container.storage.FPos3D.FPos3D;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static eu.scattering.core.impl.ConfigDef.EPSILON;
import static eu.scattering.core.impl.config.NameConfigDef.JSON_TYPE;

public class FSphereDef implements FSphere {
    private static final String JSON_MAIN = "sphere";
    private static final String JSON_RADIUS = "radius";
    private static final String JSON_CENTER = "center";

    private static final double DEF_RADIUS = 1;

    // -------------------------------------------------------------------------------------------------
    // The following fields must be redefined while extending the class.
    // -------------------------------------------------------------------------------------------------

    private final FactoryDesign factory;
    private final FCache cache;

    private FPoint center;
    private double radius;

    private FSphereDef(FactoryDesign factory) {

        this.factory = factory;
        this.cache = factory.getFCache();

        this.cache.put(FactoryDesign.class, this.factory);
    }

    public static FSphere create(FactoryDesign factory, FPoint refCenter) {

        var fSphere = new FSphereDef(factory);

        fSphere.setRefCenter(refCenter);
        fSphere.setRadius(DEF_RADIUS);

        return fSphere;
    }

    protected static boolean isParsable(String tag) {

        return tag.equals(JSON_MAIN);
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
    public FSphere applyStateFrom(FSphere arg) {

        getRefCenter().applyStateFrom(arg.getRefCenter());
        setRadius(arg.getRadius());

        return this;
    }

    @Override
    public FSphere set(JSONObject json) {

        if (json.get(JSON_TYPE) != JSON_MAIN) {
            throw new IllegalArgumentException("The object type is incorrect");
        }

        FPoint center = factory.getFPoint().set(json.getJSONObject(JSON_CENTER));
        double radius = json.getDouble(JSON_RADIUS);

       setRefCenter(center);
       setRadius(radius);

        return this;
    }

    @Override
    public FSphere applyStateTo(FSphere in) {

        in.getRefCenter().applyStateFrom(this.getRefCenter());
        in.setRadius(getRadius());

        return this;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public boolean isExact(FSphere arg) {

        return getRefCenter().isExact(arg.getRefCenter()) && getRadius() == arg.getRadius();
    }

    @Override
    public boolean isSimilar(FSphere arg) {

        if (Math.abs(getRadius() - arg.getRadius()) > EPSILON) {
            return false;
        }

        return getRefCenter().isSimilar(arg.getRefCenter());
    }

    @Override
    public FSphere self() {

        return this;
    }

    @Override
    public FSphere copy() {

        return supplyFSphere().applyStateFrom(this);
    }

    @Override
    public Geometry replicate() {

        return copy();
    }

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();

        json.put(JSON_TYPE, JSON_MAIN);
        json.put(JSON_CENTER, getRefCenter().toJSON());
        json.put(JSON_RADIUS, radius);

        return json;
    }

    // -------------------------------------------------------------------------------------------------

    @Override
    public int hashCode() {

        return Objects.hash(getRefCenter(), radius);
    }

    @Override
    public boolean equals(Object object) {

        if (object instanceof FSphere) {
            FSphere ref = (FSphere) object;

            return isExact(ref);
        }

        return false;
    }

    @Override
    public String toString() {

        return toJSON().toString();
    }

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
    public boolean contains(double x, double y, double z) {
        double tX = x - center.getX();
        double tY = y - center.getY();
        double tZ = z - center.getZ();

        double radP2 = radius * radius;
        double distP2 = (tX * tX) + (tY * tY) + (tZ * tZ);

        return distP2 < radP2 + EPSILON;
    }

    @Override
    public boolean contains(FPoint fPoint) {

        return contains(fPoint.getX(), fPoint.getY(), fPoint.getZ());
    }

    @Override
    public boolean contains(FPos3D fPos3D) {

        return contains(fPos3D.getD0(), fPos3D.getD1(), fPos3D.getD2());
    }

    @Override
    public boolean encloses(FSphere shape, double epsilon) {

        if (radius < shape.getRadius()) {
            return false;
        }

        double distP2 = center.getDistanceP2(shape.getRefCenter());
        double reqDist = radius - shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 < reqDistP2;
    }

    @Override
    public boolean touches(FSphere shape, double epsilon) {
        double distP2 = center.getDistanceP2(shape.getRefCenter());

        double reqDist = radius + shape.getRadius() + epsilon;
        double reqDistP2 = reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return false;
        }

        reqDist = radius + shape.getRadius() - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 >= reqDistP2;
    }

    @Override
    public boolean overlaps(FSphere shape, double epsilon) {
        double distP2 = center.getDistanceP2(shape.getRefCenter());
        double reqDist = radius + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 < reqDistP2;
    }

    @Override
    public boolean intersects(FSphere shape, double epsilon) {
        double distP2 = center.getDistanceP2(shape.getRefCenter());

        double reqDist = radius + shape.getRadius() - epsilon;
        double reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        if (distP2 > reqDistP2) {
            return false;
        }

        reqDist = Math.abs(radius - shape.getRadius()) - epsilon;
        reqDistP2 = reqDist < 0 ? 0 : reqDist * reqDist;

        return distP2 > reqDistP2;
    }

    @Override
    public void getVolumeStream(FStream3D stream, double delta) {
        double radiusParsed = radius + delta;
        double radiusP2 = radius * radius;

        double cX = center.getX();
        double cY = center.getY();
        double cZ = center.getZ();

        double minX = cX - radiusParsed;
        double maxX = cX + radiusParsed;
        double minY = cY - radiusParsed;
        double maxY = cY + radiusParsed;
        double minZ = cZ - radiusParsed;
        double maxZ = cZ + radiusParsed;

        double tX, tXP2;
        double tY, tYP2;
        double tZ, tZP2;

        stream.reset();

        for (double x = minX ; x <= maxX ; x += delta) {
            tX = x - cX;
            tXP2 = tX * tX;

            for (double y = minY ; y <= maxY ; y += delta) {
                tY = y - cY;
                tYP2 = tY * tY;

                for (double z = minZ ; z <= maxZ ; z += delta) {
                    tZ = z - cZ;
                    tZP2 = tZ * tZ;

                    if (tXP2 + tYP2 + tZP2 <= radiusP2) {
                        stream.add(x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public void getVolumeStream(FStream3DI stream, double delta) {
        double factor = 1 / delta;

        double radiusParsed = factor * (radius + delta);
        double radiusP2 = (factor * radius ) * (factor * radius);

        double cX = factor * center.getX();
        double cY = factor * center.getY();
        double cZ = factor * center.getZ();

        int minX = (int) Math.floor(cX - radiusParsed);
        int maxX = (int) Math.ceil(cX + radiusParsed);
        int minY = (int) Math.floor(cY - radiusParsed);
        int maxY = (int) Math.ceil(cY + radiusParsed);
        int minZ = (int) Math.floor(cZ - radiusParsed);
        int maxZ = (int) Math.ceil(cZ + radiusParsed);

        int tX, tXP2;
        int tY, tYP2;
        int tZ, tZP2;

        stream.reset();

        for (int x = minX ; x <= maxX ; x++) {
            tX = (int) (x - cX);
            tXP2 = tX * tX;

            for (int y = minY ; y <= maxY ; y++) {
                tY = (int) (y - cY);
                tYP2 = tY * tY;

                for (int z = minZ ; z <= maxZ ; z++) {
                    tZ = (int) (z - cZ);
                    tZP2 = tZ * tZ;

                    if (tXP2 + tYP2 + tZP2 <= radiusP2) {
                        stream.add(x, y, z);
                    }
                }
            }
        }
    }

    @Override
    public boolean attach(FSphere target, double epsilon) {
        boolean isTouching = touches(target, epsilon);
// TODO should return int code 0 already positioned, -1 cannot reposition (same center)
        if (isTouching) {
            return false;
        }

        if (getRefCenter().isSimilar(target.getRefCenter())) {
            // TODO remove random
            factory.getFRandEngine().rndPosOnSphere(getRefCenter(), target.getRadius() * 0.5);
        }

        getRefCenter().setDistance(target.getRefCenter(), radius + target.getRadius());

        return true;
    }

    @Override
    public int attach(FSphere target, double epsilon, Collection<FSphere> field, int maxBounce) {
        // TODO 0 already positioned, -1 same center initial, -2 colinear point after bounce, -3 bounce limit
        int repositions = 1;

        attach(target, epsilon);

        FSphere neighbour = getNeighbour(target, epsilon, field);

        if (neighbour == null) {
            return repositions;
        }

        while (neighbour != null && repositions++ < maxBounce + 1) {
            bounceSpherical(target, neighbour);

            neighbour = getNeighbour(target, epsilon, field);
        }

       return neighbour != null ? -1 : repositions;
    }

    private FSphere getNeighbour(FSphere arg, double epsilon, Collection<FSphere> field) {
        double minDist = Double.MAX_VALUE;
        FSphere neighbour = null;

        for (FSphere fSphere : field) {

            if (fSphere == this || fSphere == arg) {
                continue;
            }

            if (overlaps(fSphere, epsilon)) {
                double dist = getRefCenter().getDistanceP2(fSphere.getRefCenter());

                if (dist < minDist) {
                    neighbour = fSphere;
                    minDist = dist;
                }
            }
        }

        return neighbour;
    }

    private void bounceLinear(FSphere arg, FSphere neighbour) {

    }

    private void bounceSpherical(FSphere arg, FSphere neighbour) {
        // TODO point cannot be on the same line


        FTrigHelper trigHelper = factory.getFTrigHelper();
        FRotEngine rotEngine = factory.getFRotEngine();

        FVector vecRef = cache.get("bVec1", FVector.class,
                (core) -> core.get(FactoryDesign.class).getFVector());

        FVector vecArg = cache.get("bVec2", FVector.class,
                (core) -> core.get(FactoryDesign.class).getFVector());

        vecRef.set(arg.getRefCenter(), this.getRefCenter());
        vecArg.set(arg.getRefCenter(), neighbour.getRefCenter());

        double sideRef = vecRef.getMagnitude();
        double sideArg = vecArg.getMagnitude();

        double dist = getRadius() + neighbour.getRadius();
        double ang = trigHelper.getAngle(sideRef, sideArg, dist);

        rotEngine.setRgAngle(vecArg, vecRef, ang);

        getRefCenter().applyStateFrom(vecRef.getRefHead());
    }










    @Override
    public Collection<FPoint> explode() {
        Collection<FPoint> units = new ArrayList<>();

        units.add(getRefCenter());

        return units;
    }




    @Override
    public void getSurfaceStream(FStream3DI stream, double delta) {

    }

    @Override
    public void getSurfaceStream(FStream3D stream, double delta) {

    }




    @Override
    public boolean project(FPoint aim, List<FSphere> field) {
        return false;
    }




    // -------------------------------------------------------------------------------------------------

    private FSphere supplyFSphere() {

        return factory.getFSphere();
    }
}
