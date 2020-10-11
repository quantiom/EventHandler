package me.quantiom.eventhandler;

import java.lang.reflect.Method;

public class RegisteredMethod {
    private Method method;
    private Class<?> event;

    public RegisteredMethod(Method method, Class<?> event) {
        this.method = method;
        this.event = event;
    }

    public Method getMethod() {
        return method;
    }

    public Class<?> getEvent() {
        return event;
    }
}
