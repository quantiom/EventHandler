package me.quantiom.eventhandler.event.impl;

import me.quantiom.eventhandler.event.Event;

public class TestSuperEvent extends Event {
    private String superData;

    public TestSuperEvent(String superData) {
        this.superData = superData;
    }

    public String getSuperData() {
        return superData;
    }
}
