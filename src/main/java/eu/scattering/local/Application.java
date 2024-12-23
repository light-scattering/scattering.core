package eu.scattering.local;

import eu.scattering.core.design.Factory;
import eu.scattering.core.impl.production.SpringProd;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        System.setProperty("spring.profiles.default", "dev");
        var context = new AnnotationConfigApplicationContext(SpringProd.class);
        context.registerShutdownHook();

        Factory factorySpring = context.getBean(Factory.class);
        System.out.println(factorySpring.getEpsilon());

        System.out.println(factorySpring.getEpsilon());
    }

}
