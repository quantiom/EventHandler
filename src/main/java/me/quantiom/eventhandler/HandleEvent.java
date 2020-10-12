package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.EventPriority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HandleEvent {
    EventPriority priorty() default EventPriority.NORMAL;
    boolean skipIfCancelled() default false;
}
