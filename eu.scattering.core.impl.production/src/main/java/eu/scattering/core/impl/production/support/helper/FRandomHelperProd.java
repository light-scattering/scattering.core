package eu.scattering.core.impl.production.support.helper;

import eu.scattering.core.design.elements.algebra.number.complex.FComplex;
import eu.scattering.core.design.elements.algebra.number.quaternion.FQuaternion;
import eu.scattering.core.design.helpers.engine.FRandomHelper;
import eu.scattering.core.design.transfers.position.FPairPos2D;
import eu.scattering.core.design.transfers.position.FPairPos4D;

public class FRandomHelperProd implements FRandomHelper {


    @Override
    public void rndPosition(FComplex origin, FPairPos2D range, FComplex... exclusion) {

    }

    @Override
    public void rndPosition(FComplex origin, double radius, FComplex... exclusion) {

    }

    @Override
    public void rndPosition(FQuaternion origin, FPairPos4D range, FQuaternion... exclusion) {

    }

    @Override
    public void rndPosition(FQuaternion origin, double radius, FQuaternion... exclusion) {

    }
}
