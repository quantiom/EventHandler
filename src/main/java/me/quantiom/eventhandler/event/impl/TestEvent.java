package me.quantiom.eventhandler.event.impl;

import com.sun.istack.internal.NotNull;
import me.quantiom.eventhandler.event.Event;

public class TestEvent extends Event {
    public TestEvent(String data) {
        this.data = data;
    }

    @NotNull
    private String data;

    @NotNull
    public String getData() {
        return data;
    }
}
