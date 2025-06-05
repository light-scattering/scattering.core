package eu.scattering.core.design.component.geometry.base.vector;

import eu.scattering.core.design.component.geometry.base.point.FPointProducer;

import java.util.function.Function;
import java.util.stream.Stream;

public interface FVectorProducer extends Iterable<FVector> {

    FVector produce();
    Stream<FVector> stream();

    // -------------------------------------------------------------------------------------------------

    FVectorProducer withCustomRule(Function<FVectorFactory, FVector> function, int weight);

    FVectorProducer withDirOX(double length, int weight);
    FVectorProducer withDirOY(double length, int weight);
    FVectorProducer withDirOZ(double length, int weight);

    FVectorProducer withBaseAndDirOX(FPointProducer pBase, double length, int weight);
    FVectorProducer withBaseAndDirOY(FPointProducer pBase, double length, int weight);
    FVectorProducer withBaseAndDirOZ(FPointProducer pBase, double length, int weight);

    FVectorProducer withInSphere(double radius, int weight);
    FVectorProducer withRadius(double radius, int weight);

    FVectorProducer withBaseAndInSphere(FPointProducer pBase, double radius, int weight);
    FVectorProducer withBaseAndRadius(FPointProducer pBase, double radius, int weight);

    FVectorProducer withBase(FPointProducer pBase, int weight);
    FVectorProducer withHead(FPointProducer pHead, int weight);
    FVectorProducer withBaseAndHead(FPointProducer pBase, FPointProducer pHead, int weight);

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

    default FVectorProducer withInSphere(double radius) {

        return withInSphere(radius, 1);
    }

    default FVectorProducer withRadius(double radius) {

        return withRadius(radius, 1);
    }

    default FVectorProducer withBaseAndInSphere(FPointProducer pBase, double radius) {

        return withBaseAndInSphere(pBase, radius, 1);
    }

    default FVectorProducer withBaseAndRadius(FPointProducer pBase, double radius) {

        return withBaseAndRadius(pBase, radius, 1);
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
