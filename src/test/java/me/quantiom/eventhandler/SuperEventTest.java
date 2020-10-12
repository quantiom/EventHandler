package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.impl.TestEvent;
import me.quantiom.eventhandler.event.impl.TestSuperEvent;

public class SuperEventTest implements EventListener {
    private int timesRan = 0;

    @HandleEvent
    public void onTestEventLowest(TestEvent event) {
        this.timesRan++;
    }

    @HandleEvent
    public void onTestEventHighest(TestSuperEvent event) {
        this.timesRan++;
    }

    public int getTimesRan() {
        return timesRan;
    }
}

