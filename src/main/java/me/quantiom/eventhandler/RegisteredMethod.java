package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.EventPriority;

import java.lang.reflect.Method;

public class RegisteredMethod {
    private EventListener listenerInstance;
    private Method method;
    private Class<?> event;
    private EventPriority priority;
    private boolean skipIfCancelled;

    public RegisteredMethod(EventListener listenerInstance, Method method, Class<?> event, EventPriority priority, boolean skipIfCancelled) {
        this.listenerInstance = listenerInstance;
        this.method = method;
        this.event = event;
        this.priority = priority;
        this.skipIfCancelled = skipIfCancelled;
    }

    public EventListener getListenerInstance() {
        return listenerInstance;
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

    public boolean isSkipIfCancelled() {
        return skipIfCancelled;
    }
}
