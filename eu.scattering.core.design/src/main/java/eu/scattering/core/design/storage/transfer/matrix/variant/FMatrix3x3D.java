package eu.scattering.core.design.storage.transfer.matrix.variant;

import eu.scattering.core.design.storage.transfer.Transfer;

public interface FMatrix3x3D extends Transfer {

    double get0x0();

    double get0x1();

    double get0x2();

    double get1x0();

    double get1x1();

    double get1x2();

    double get2x0();

    double get2x1();

    double get2x2();

    double[][] getArray();
}
