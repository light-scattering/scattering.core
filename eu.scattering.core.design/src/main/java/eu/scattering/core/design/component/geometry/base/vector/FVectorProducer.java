package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPointProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FVectorProducer extends Iterable<FVector> {

    FVector produce();
    Stream<FVector> stream();

    // -------------------------------------------------------------------------------------------------

    FVectorProducer withCustomRule(Function<FVectorFactory, FVector> function, int probability);

    FVectorProducer withDirOX(double length, int probability);
    FVectorProducer withDirOY(double length, int probability);
    FVectorProducer withDirOZ(double length, int probability);

    FVectorProducer withBaseAndDirOX(FPointProducer pBase, double length, int probability);
    FVectorProducer withBaseAndDirOY(FPointProducer pBase, double length, int probability);
    FVectorProducer withBaseAndDirOZ(FPointProducer pBase, double length, int probability);

    FVectorProducer withInRadius(double radius, int probability);
    FVectorProducer withOnRadius(double radius, int probability);

    FVectorProducer withBaseAndInRadius(FPointProducer pBase, double radius, int probability);
    FVectorProducer withBaseAndOnRadius(FPointProducer pBase, double radius, int probability);

    FVectorProducer withBase(FPointProducer pBase, int probability);
    FVectorProducer withHead(FPointProducer pHead, int probability);
    FVectorProducer withBaseAndHead(FPointProducer pBase, FPointProducer pHead, int probability);

    // -------------------------------------------------------------------------------------------------

    default FVectorProducer withCustomRule(Function<FVectorFactory, FVector> function) {

        return withCustomRule(function, 1);
    }

    default FVectorProducer withDirOX(double length) {

        return withDirOX(length, 1);
    }

    default FVectorProducer withDirOY(double length) {

        return withDirOY(length, 1);
    }

    default FVectorProducer withDirOZ(double length) {

        return withDirOZ(length, 1);
    }

    default FVectorProducer withBaseAndDirOX(FPointProducer pBase, double length) {

        return withBaseAndDirOX(pBase, length, 1);
    }

    default FVectorProducer withBaseAndDirOY(FPointProducer pBase, double length) {

        return withBaseAndDirOY(pBase, length, 1);
    }

    default FVectorProducer withBaseAndDirOZ(FPointProducer pBase, double length) {

        return withBaseAndDirOZ(pBase, length, 1);
    }

    default FVectorProducer withInRadius(double radius) {

        return withInRadius(radius, 1);
    }

    default FVectorProducer withOnRadius(double radius) {

        return withOnRadius(radius, 1);
    }

    default FVectorProducer withBaseAndInRadius(FPointProducer pBase, double radius) {

        return withBaseAndInRadius(pBase, radius, 1);
    }

    default FVectorProducer withBaseAndOnRadius(FPointProducer pBase, double radius) {

        return withBaseAndOnRadius(pBase, radius, 1);
    }

    default FVectorProducer withBase(FPointProducer pBase) {

        return withBase(pBase, 1);
    }

    default FVectorProducer withHead(FPointProducer pHead) {

        return withHead(pHead, 1);
    }

    default FVectorProducer withBaseAndHead(FPointProducer pBase, FPointProducer pHead) {

        return withBaseAndHead(pBase, pHead, 1);
    }
}
