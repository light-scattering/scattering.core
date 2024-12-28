package eu.scattering.core.design.elements.algebra.geometry.primitive.vector;

import eu.scattering.core.design.annotations.MutableState;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPoint;
import eu.scattering.core.design.elements.algebra.geometry.primitive.point.FPointFactory;
import eu.scattering.core.transfer.containers.position.FPairPos3D.FPairPos3D;
import eu.scattering.core.transfer.containers.position.FPos3D.FPos3D;

public interface FVectorFactory extends FPointFactory {

    FVector getFVector();

    @MutableState
    FVector getFVectorRef();

    //--------------------------------------------------

    default FVector getFVector(double bX, double bY, double bZ, double hX, double hY, double hZ) {

        return getFVector().setRef(getFPoint(bX, bY, bZ), getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint base, FPoint head) {

        return getFVector().setRef(base, head);
    }

    default FVector getFVector(double hX, double hY, double hZ) {

        return getFVector().setRefHead(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(FPoint head) {

        return getFVector().setRefHead(head);
    }

    default FVector getFVector(FPoint base, double hX, double hY, double hZ) {

        return getFVector().setRefBase(base).setRefHead(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(double bX, double bY, double bZ, FPoint head) {

        return getFVector().setRefBase(getFPoint(bX, bY, bZ)).setRefHead(head);
    }

    default FVector getFVector(FVector fVector) {

        return getFVector().applyStateFrom(fVector);
    }

    default FVector getFVector(FPos3D base, double hX, double hY, double hZ) {

        return getFVector().setBase(base).setHead(getFPoint(hX, hY, hZ));
    }

    default FVector getFVector(double bX, double bY, double bZ, FPos3D head) {

        return getFVector().setBase(getFPoint(bX, bY, bZ)).setHead(head);
    }

    default FVector getFVector(FPos3D base, FPos3D head) {

        return getFVector().set(base, head);
    }

    default FVector getFVector(FPairPos3D position) {

        return getFVector().set(position);
    }

    @MutableState
    default FVector getFVectorRef(FPoint base, FPoint head) {

        return getFVectorRef().setRefBase(base).setRefHead(head);
    }

    @MutableState
    default FVector getFVectorRef(FPoint head) {

        return getFVectorRef().rstRefBase().setRefHead(head);
    }
}
