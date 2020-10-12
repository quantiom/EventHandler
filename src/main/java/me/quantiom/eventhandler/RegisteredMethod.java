package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.EventPriority;

import java.lang.reflect.Method;

public class RegisteredMethod {
    private Method method;
    private Class<?> event;
    private EventPriority priority;

    public RegisteredMethod(Method method, Class<?> event, EventPriority priority) {
        this.method = method;
        this.event = event;
        this.priority = priority;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getEvent() {
        return event;
    }

    public EventPriority getPriority() {
        return priority;
    }
}
