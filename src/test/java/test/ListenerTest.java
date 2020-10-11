package test;

import me.quantiom.eventhandler.EventListener;
import me.quantiom.eventhandler.HandleEvent;
import me.quantiom.eventhandler.event.impl.TestEvent;

public class ListenerTest implements EventListener {
    private int timesRan = 0;
    private String data = "None";

    @HandleEvent
    public void onTestEvent(TestEvent event) {
        this.timesRan++;
        this.data = event.getData();
    }

    public int getTimesRan() {
        return timesRan;
    }

    public String getData() {
        return data;
    }
}
