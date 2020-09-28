package eu.scattering.core.impl.production;

import eu.scattering.core.design.Factory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@PropertySource("core.properties")
public class SpringProd {

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

        return FactoryProd.create();
    }

    @PostConstruct
    public void postConstruct() {

        factory.setJitter(jitter);
        factory.getHelperRandom().setSpacing(random_spacing);
        factory.getHelperRandom().setRange(random_range);
    }
}
