package eu.scattering.core.debug;

public interface IStatRecord {

    String getMethodSignature();

    long getExecutionTime();

    long getAveragedExecutionTime();

}
