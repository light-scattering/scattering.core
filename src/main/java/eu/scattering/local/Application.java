package eu.scattering.local;

import eu.scattering.core.design.FactoryDesignConcrete;
import eu.scattering.core.impl.SpringProd;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "dev");
        var context = new AnnotationConfigApplicationContext(SpringProd.class);
        context.registerShutdownHook();

        FactoryDesignConcrete factorySpring = context.getBean(FactoryDesignConcrete.class);
        System.out.println(factorySpring.getEpsilon());

        System.out.println(factorySpring.getEpsilon());
    }

}
