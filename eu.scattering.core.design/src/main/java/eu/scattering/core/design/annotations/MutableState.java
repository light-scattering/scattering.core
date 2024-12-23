package eu.scattering.core.design.annotations;

public @interface MutableState {
    String value() default "";
}
