package eu.scattering.core.implementation.support.helper;

import eu.scattering.core.design.support.helper.AngleHelper;

public enum AngleHelperDefault implements AngleHelper {
    INSTANCE {

        @Override
        public double radToDeg(double radian) {

            return radian * 180 / Math.PI;
        }

        @Override
        public double degToRad(double degree) {

            return degree * Math.PI / 180;
        }
    }
}
