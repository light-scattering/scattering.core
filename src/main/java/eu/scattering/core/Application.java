package eu.scattering.core;

import eu.scattering.core.design.Factory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfigCore.class);
        context.registerShutdownHook();

        Factory factory = context.getBean(Factory.class);
    }

}
