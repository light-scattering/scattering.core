package eu.scattering.core.implementation.support.helper;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.algebra.engine.base.point.FPoint;
import eu.scattering.core.design.main.algebra.engine.base.vector.FVector;
import eu.scattering.core.design.main.algebra.type.complex.FComplex;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.design.support.helper.RandomHelper;
import eu.scattering.core.implementation.FactoryDefault;

import java.util.concurrent.ThreadLocalRandom;

public enum RandomHelperDefault implements RandomHelper {
    INSTANCE {

        private Factory factory = new FactoryDefault();
        private double range = 10000;
        private double separation = 1E-8;

        @Override
        public void configure(Factory factory, double range, double separation) {

            this.factory = factory;
            this.range = range;
            this.separation = separation;
        }

        @Override
        public double getTestValue(double... exclusion) {
            double value = 0;

            mainLoop:
            while (true) {
                value = getRandomValue();

                for (double singularity : exclusion) {
                    if (Math.abs(value - singularity) < separation) {
                        continue mainLoop;
                    }
                }

                return value;
            }
        }

        private double getRandomValue() {

            return ThreadLocalRandom.current().nextDouble(-range, range);
        }

        @Override
        public FPoint getTestPoint(FPoint... exclusion) {
            FPoint value;

            mainLoop:
            while (true) {
                value = getRandomFPoint();

                for (FPoint singularity : exclusion) {
                    if (value.getDistance(singularity) < separation) {
                        continue mainLoop;
                    }
                }

                return value;
            }
        }

        private FPoint getRandomFPoint() {
            double x1 = 0, x2 = 0, f = 10;

            while (f >= 1) {
                x1 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
                x2 = 2 * ThreadLocalRandom.current().nextDouble() - 1;
                f = x1 * x1 + x2 * x2;
            }

            double x = 2 * x1 * Math.sqrt(1 - f);
            double y = 2 * x2 * Math.sqrt(1 - f);
            double z = 1 - 2 * f;

            return factory.getFPoint(x, y, z).setLength(Math.abs(getTestValue()));
        }

        @Override
        public FVector getTestVector(FVector... exclusion) {
            FVector value;

            mainLoop:
            while (true) {
                value = getRandomFVector();

                for (FVector singularity : exclusion) {
                    boolean conditionBaseA = value.getBase().getDistance(singularity.getBase()) < separation;
                    boolean conditionBaseB = value.getBase().getDistance(singularity.getHead()) < separation;
                    boolean conditionHeadA = value.getHead().getDistance(singularity.getBase()) < separation;
                    boolean conditionHeadB = value.getHead().getDistance(singularity.getHead()) < separation;

                    if (conditionBaseA || conditionBaseB || conditionHeadA || conditionHeadB) {
                        continue mainLoop;
                    }
                }

                return value;
            }
        }

        private FVector getRandomFVector() {
            FPoint base, head;

            mainLoop:
            while (true) {
                base = getRandomFPoint();
                head = getRandomFPoint();

                if (base.getDistance(head) < separation) {
                    continue mainLoop;
                }

                break;
            }

            return factory.getFVector(base, head);
        }

        @Override
        public FComplex getTestComplex(FComplex... exclusion) {
            FComplex value;

            mainLoop:
            while (true) {
                value = getRandomFComplex();

                for (FComplex singularity : exclusion) {
                    if (value.getDistance(singularity) < separation) {
                        continue mainLoop;
                    }
                }

                return value;
            }
        }

        private FComplex getRandomFComplex() {

            return factory.getFComplex(getTestValue(), getTestValue());
        }

        @Override
        public FQuaternion getTestQuaternion(FQuaternion... exclusion) {
            FQuaternion value;

            mainLoop:
            while (true) {
                value = getRandomFQuaternion();

                for (FQuaternion singularity : exclusion) {
                    if (value.getDistance(singularity) < separation) {
                        continue mainLoop;
                    }
                }

                return value;
            }
        }

        private FQuaternion getRandomFQuaternion() {

            return factory.getFQuaternion(getTestValue(), getTestValue(), getTestValue(), getTestValue());
        }
    }
}
