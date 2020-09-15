package eu.scattering.core;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Application {

    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(SpringConfigCore.class);
        context.registerShutdownHook();
    }

}
