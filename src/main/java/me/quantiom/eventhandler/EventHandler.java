package me.quantiom.eventhandler;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import me.quantiom.eventhandler.event.Event;
import me.quantiom.eventhandler.event.EventCancellable;

import java.util.*;
import java.util.stream.Collectors;

public class EventHandler {
    private Map<Class<? extends Event>, List<RegisteredMethod>> registeredEvents;
    private Map<Class<? extends Event>, Set<Class<? extends Event>>> cachedSuperClasses;

    public EventHandler() {
        this.registeredEvents = Maps.newHashMap();
        this.cachedSuperClasses = Maps.newHashMap();
    }

    // calls an event
    public void callEvent(Event event) {
        this.registeredEvents.forEach((eventClazz, registeredMethods) -> {
            this.cachedSuperClasses.get(eventClazz).forEach(clazz -> callEvent(clazz.cast(event)));

            registeredMethods.forEach(rm -> {
                EventListener instance = rm.getListenerInstance();

                if (rm.getEvent().isAssignableFrom(event.getClass())) {
                    try {
                        if (event instanceof EventCancellable && rm.isSkipIfCancelled() && ((EventCancellable) event).isCancelled()) {
                            return;
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
            });
        });
    }

    @SuppressWarnings("unchecked")
    private List<Class<? extends Event>> findSuperClasses(Class<? extends Event> clazz, List<Class<? extends Event>> list) {
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && superClass.isAssignableFrom(Event.class)) {
            list.addAll(findSuperClasses((Class<? extends Event>)superClass, list));
        }
        return list;
    }

    // registers methods from an EventListener instance
    @SuppressWarnings("unchecked")
    public void registerEvents(EventListener instance) {
        Arrays.stream(instance.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(HandleEvent.class))
                .filter(m -> m.getParameterCount() == 1)
                .filter(m -> Event.class.isAssignableFrom(m.getParameterTypes()[0]))
                .map(m -> new RegisteredMethod(
                        instance,
                        m,
                        m.getParameterTypes()[0],
                        m.getAnnotation(HandleEvent.class).priority(),
                        m.getAnnotation(HandleEvent.class).skipIfCancelled()
                ))
                .forEach(rm -> {
                    this.cachedSuperClasses.computeIfAbsent((Class<? extends Event>)rm.getEvent(), k -> Sets.newHashSet()).addAll(findSuperClasses((Class<? extends Event>)rm.getEvent(), Lists.newArrayList()));
                    this.registeredEvents.computeIfAbsent((Class<? extends Event>)rm.getEvent(), k -> Lists.newArrayList()).add(rm);
                });

        // sort once on register
        this.registeredEvents.values().forEach(list -> list.sort(Comparator.comparing(RegisteredMethod::getPriority)));
    }

    // unregisters an EventListener
    public void unregisterEvents(EventListener instance) {
        this.registeredEvents.keySet().forEach(k -> {
            this.registeredEvents.put(k,
                    this.registeredEvents.get(k)
                            .stream()
                            .filter(rm -> rm.getListenerInstance() != instance)
                            .collect(Collectors.toList())
            );
        });
    }
}
