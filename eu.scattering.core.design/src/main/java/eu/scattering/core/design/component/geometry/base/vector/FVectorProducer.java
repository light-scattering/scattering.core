package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.transfer.container.storage.FPairPos3D.FPairPos3D;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FVectorProducer extends Iterable<FVector> {

    FVector produce();
    Stream<FVector> stream();

    // -------------------------------------------------------------------------------------------------

    FVectorProducer withCustomRule(Function<FVector, FVector> function, int probability);

    FVectorProducer withUnitX(int probability);
    FVectorProducer withUnitY(int probability);
    FVectorProducer withUnitZ(int probability);

    FVectorProducer withInsideSphere(double radius, int probability);
    FVectorProducer withOnSphere(double radius, int probability);
    FVectorProducer withInRange(FPairPos3D range, int probability);

    // -------------------------------------------------------------------------------------------------

    default FVectorProducer withCustomRule(Function<FVector, FVector> function) {

        return withCustomRule(function, 1);
    }

    default FVectorProducer withUnitX() {

        return withUnitX(1);
    }

    default FVectorProducer withUnitY() {

        return withUnitY(1);
    }

    default FVectorProducer withUnitZ() {

        return withUnitZ(1);
    }

    default FVectorProducer withInsideSphere(double radius) {

        return withInsideSphere(radius, 1);
    }

    default FVectorProducer withOnSphere(double radius) {

        return withOnSphere(radius, 1);
    }

    default FVectorProducer withInRange(FPairPos3D range) {

        return withInRange(range, 1);
    }
}
