package eu.scattering.core.impl;

import eu.scattering.core.design.FactoryDesignConcrete;
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

    @Value("${random.separation}")
    private double separationDistance;

    @Resource(name = "prod")
    private FactoryDesignConcrete factory;

    @Bean("prod")
    public FactoryDesignConcrete getFactoryDefault() {

        return FactoryProd.create();
    }

    @PostConstruct
    public void postConstruct() {
    }
}
