package eu.scattering.core.design.component.aggregate.monitor.construct;

public interface FMonitorConstructFactory {


    FMonitorConstruct getFMonitorRoG(int skip);


    FMonitorConstruct getFMonitorRoGMono(int skip);
    FMonitorConstruct getFMonitorRoGPoly(int skip);

    // -------------------------------------------------------------------------------------------------

    default FMonitorConstruct getFMonitorRoG() {

        return getFMonitorRoG(-1);
    }

    default FMonitorConstruct getFMonitorRoGMono() {

        return getFMonitorRoGMono(-1);
    }

    default FMonitorConstruct getFMonitorRoGPoly() {

        return getFMonitorRoGPoly(-1);
    }
}
