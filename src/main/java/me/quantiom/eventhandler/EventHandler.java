package me.quantiom.eventhandler;

import com.google.common.collect.Maps;
import javafx.util.Pair;
import me.quantiom.eventhandler.event.Event;
import me.quantiom.eventhandler.event.EventCancellable;

import java.util.*;
import java.util.stream.Collectors;

public class EventHandler {
    private Map<EventListener, List<RegisteredMethod>> registeredEvents;

    public EventHandler() {
        this.registeredEvents = Maps.newHashMap();
    }

    // calls an event
    public void callEvent(Event event) {
        List<Pair<EventListener, RegisteredMethod>> registeredMethods = new ArrayList<>();

        // add all methods to one list to sort by priority
        for (Map.Entry<EventListener, List<RegisteredMethod>> entry : this.registeredEvents.entrySet()) {
            entry.getValue().forEach(rm -> registeredMethods.add(new Pair<>(entry.getKey(), rm)));
        }

        // sort by priority
        registeredMethods.sort(Comparator.comparing(o -> o.getValue().getPriority()));

        // call each method
        for (Pair<EventListener, RegisteredMethod> entry : registeredMethods) {
            EventListener instance = entry.getKey();
            RegisteredMethod rm = entry.getValue();

            if (rm.getEvent().isAssignableFrom(event.getClass())) {
                try {
                    // skip if cancelled
                    if (event instanceof EventCancellable && rm.isSkipIfCancelled() && ((EventCancellable) event).isCancelled()) {
                        continue;
                    }

                    boolean notAccessible = false;

                    if (!rm.getMethod().isAccessible()) {
                        rm.getMethod().setAccessible(true);
                        notAccessible = true;
                    }

                    rm.getMethod().invoke(instance, event);

                    if (notAccessible) rm.getMethod().setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
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
                .map(m -> new RegisteredMethod(
                        m,
                        m.getParameterTypes()[0],
                        m.getAnnotation(HandleEvent.class).priorty(),
                        m.getAnnotation(HandleEvent.class).skipIfCancelled()
                ))
                .collect(Collectors.toList());

        this.registeredEvents.put(instance, filteredMethods);
    }

    // unregisters an EventListener
    public void unregisterEvents(EventListener instance) {
        this.registeredEvents.remove(instance);
    }
}
