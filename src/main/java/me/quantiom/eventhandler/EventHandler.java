package me.quantiom.eventhandler;

import com.google.common.collect.Maps;
import me.quantiom.eventhandler.event.Event;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EventHandler {
    private Map<EventListener, List<RegisteredMethod>> registeredEvents;

    public EventHandler() {
        this.registeredEvents = Maps.newHashMap();
    }

    // calls an event
    public void callEvent(Event event) {
        for (Map.Entry<EventListener, List<RegisteredMethod>> entry : this.registeredEvents.entrySet()) {
            EventListener instance = entry.getKey();
            List<RegisteredMethod> registeredMethods = entry.getValue();

            for (RegisteredMethod rm : registeredMethods) {
                if (rm.getEvent().isAssignableFrom(event.getClass())) {
                    try {
                        if (!rm.getMethod().isAccessible()) {
                            rm.getMethod().setAccessible(true);
                        }

                        rm.getMethod().invoke(instance, event);

                        rm.getMethod().setAccessible(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // registers methods from an EventListener and filters them
    public void registerEvents(EventListener instance) {
        List<RegisteredMethod> filteredMethods = Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(HandleEvent.class))
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> Event.class.isAssignableFrom(m.getParameterTypes()[0]))
                .map(m -> new RegisteredMethod(m, m.getParameterTypes()[0]))
                .collect(Collectors.toList());

        this.registeredEvents.put(instance, filteredMethods);
    }

    // unregisters an EventListener
    public void unregisterEvents(EventListener instance) {
        this.registeredEvents.remove(instance);
    }
}
