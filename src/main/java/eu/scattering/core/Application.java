package eu.scattering.core;

import eu.scattering.core.design.Factory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
//        System.setProperty("spring.profiles.default", "dev");
        var context = new AnnotationConfigApplicationContext(SpringConfigCore.class);
        context.registerShutdownHook();

        Factory factorySpring = context.getBean(Factory.class);
        Factory factorySpringa = context.getBean(Factory.class);
        Factory factorySpringb = context.getBean(Factory.class);
        System.out.println(factorySpring.getJitter());

//        Factory factory = FactoryDevelopment.create(FactoryDefault.create());
//        System.out.println(factory.getJitter());
    }

}
