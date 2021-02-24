package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.EventPriority;
import me.quantiom.eventhandler.event.impl.TestEvent;

public class PriorityTest implements EventListener {
    private String data = "None";

    @HandleEvent(priority = EventPriority.LOWEST)
    public void onTestEventLowest(TestEvent event) {
        this.data = "Lowest priority called";
    }

    @HandleEvent(priority = EventPriority.HIGHEST)
    public void onTestEventHighest(TestEvent event) {
        this.data = "Highest priority called";
    }

    @HandleEvent(priority = EventPriority.NORMAL)
    public void onTestEventNormal(TestEvent event) {
        this.data = "Normal priority called";
    }

    public String getData() {
        return data;
    }
}
