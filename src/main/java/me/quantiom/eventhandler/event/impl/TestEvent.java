package me.quantiom.eventhandler.event.impl;

import me.quantiom.eventhandler.event.EventCancellable;

public class TestEvent extends TestSuperEvent implements EventCancellable {
    private String data;
    private boolean isCancelled;

    public TestEvent(String data) {
        super("From TestEvent");
        this.data = data;
    }

    public String getData() {
        return data;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }
}
