package eu.scattering.core;

import eu.scattering.core.design.Factory;
import eu.scattering.core.implementation.FactoryDefault;
import eu.scattering.core.implementation.FactoryDevelopment;
import eu.scattering.core.implementation.main.algebra.type.complex.FComplexDefault;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
//        System.setProperty("spring.profiles.default", "dev");
//        var context = new AnnotationConfigApplicationContext(SpringConfigCore.class);
//
//        context.registerShutdownHook();
//
//        Factory factory = context.getBean(Factory.class);
//        System.out.println(factory);

        Factory factory = FactoryDevelopment.create(FactoryDefault.create());
        System.out.println(factory);
    }

}
