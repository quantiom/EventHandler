package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.impl.TestEvent;

public class MultipleHandlersTest implements EventListener {
    private int timesRan;

    @HandleEvent
    public void onTestEventOne(TestEvent event) {
        timesRan++;
    }

    @HandleEvent
    public void onTestEventTwo(TestEvent event) {
        timesRan++;
    }

    public int getTimesRan() {
        return timesRan;
    }
}
