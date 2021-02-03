package eu.scattering.local;

import eu.scattering.core.design.Factory;
import eu.scattering.core.impl.development.FactoryDev;
import eu.scattering.core.impl.development.SpringDev;
import eu.scattering.core.impl.production.FactoryProd;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "dev");
        var context = new AnnotationConfigApplicationContext(SpringDev.class);
        context.registerShutdownHook();

        Factory factorySpring = context.getBean(Factory.class);
        Factory factorySpringa = context.getBean(Factory.class);
        Factory factorySpringb = context.getBean(Factory.class);
        System.out.println(factorySpring.getJitter());

        Factory factory = FactoryDev.create(FactoryProd.create());
        System.out.println(factory.getJitter());
    }

}
