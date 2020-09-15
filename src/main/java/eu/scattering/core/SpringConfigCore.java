package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.design.main.algebra.type.quaternion.FQuaternion;
import eu.scattering.core.implementation.FactoryDevelopment;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.box.position.FPositionDefault;
import eu.scattering.core.implementation.main.box.rotation.FRotationDefault;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("core.properties")
public class SpringConfigCore {

    @Value("${jitter}")
    private double jitter;

    @Bean
    public Factory getFactory() {
        return new FactoryDevelopment();
    }

    @PostConstruct
    public void postConstruct() {

        FPointDefault.setJitter(jitter);
        FVectorDefault.setJitter(jitter);
        FLineDefault.setJitter(jitter);
        FPlaneDefault.setJitter(jitter);
        FComplexDefault.setJitter(jitter);
        FQuaternionDefault.setJitter(jitter);
        FPositionDefault.setJitter(jitter);
        FRotationDefault.setJitter(jitter);



        FPointDefault.setFactory(getFactory());
        FVectorDefault.setFactory(getFactory());
        FLineDefault.setFactory(getFactory());
        FPlaneDefault.setFactory(getFactory());
        FComplexDefault.setFactory(getFactory());
        FQuaternionDefault.setFactory(getFactory());
        FPositionDefault.setFactory(getFactory());
        FRotationDefault.setFactory(getFactory());
    }

}
