package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.EventPriority;
import me.quantiom.eventhandler.event.impl.TestEvent;

public class CancelTest implements EventListener {
    private String data = "None";

    @HandleEvent(priority = EventPriority.LOWEST)
    public void onTestEventLowest(TestEvent event) {
        this.data = "Cancelling event on lowest";

        event.setCancelled(true);
    }

    @HandleEvent(priority = EventPriority.HIGH)
    public void onTestEventHigh(TestEvent event) {
        this.data = "High priority called";
    }

    // will not be called
    @HandleEvent(priority = EventPriority.HIGHEST, skipIfCancelled = true)
    public void onTestEventHighest(TestEvent event) {
        this.data = "Highest called";
    }

    public String getData() {
        return data;
    }
}
