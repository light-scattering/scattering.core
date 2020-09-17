package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.implementation.FactoryDefault;
import eu.scattering.core.implementation.FactoryDevelopment;
import eu.scattering.core.implementation.main.algebra.engine.base.point.FPointDefault;
import eu.scattering.core.implementation.main.algebra.engine.base.vector.FVectorDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.line.FLineDefault;
import eu.scattering.core.implementation.main.algebra.engine.extension.plane.FPlaneDefault;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import eu.scattering.core.implementation.main.algebra.type.quaternion.FQuaternionDefault;
import eu.scattering.core.implementation.main.box.position.FPositionDefault;
import eu.scattering.core.implementation.main.box.rotation.FRotationDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@PropertySource("core.properties")
public class SpringConfigCore {

    @Value("${jitter}")
    private double jitter;

    @Value("${random.range}")
    private double random_range;

    @Value("${random.spacing}")
    private double random_spacing;

    @Resource(name = "prod")
    private Factory factory;

    @Bean("prod")
    public Factory getFactoryDefault() {

        return FactoryDefault.create();
    }

    @Primary
    @Bean("dev")
    @Profile("dev")
    public Factory getFactoryDevelopment() {

        return FactoryDevelopment.create(factory);
    }

    @PostConstruct
    public void postConstruct() {

        factory.setJitter(jitter);
        factory.getHelperRandom().setFactory(factory); // Doesn't make much sense
        factory.getHelperRandom().setSpacing(random_spacing);
        factory.getHelperRandom().setRange(random_range);
    }

}
