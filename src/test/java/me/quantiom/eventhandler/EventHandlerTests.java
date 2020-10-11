package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.impl.TestEvent;
import org.junit.Assert;
import org.junit.Test;

public class EventHandlerTests {
    @Test
    public void test_listenerTest() {
        // create the handler and listener instances
        EventHandler handler = new EventHandler();
        ListenerTest testListener = new ListenerTest();

        // register the listener
        handler.registerEvents(testListener);

        // make sure data is not overridden yet
        Assert.assertEquals("None", testListener.getData());
        Assert.assertEquals(0, testListener.getTimesRan());

        // call the test event
        handler.callEvent(new TestEvent("New data"));

        // data should now be what was passed into the TestEvent constructor
        Assert.assertNotEquals("None", testListener.getData());
        Assert.assertEquals("New data", testListener.getData());
        Assert.assertEquals(1, testListener.getTimesRan());

        // unregister the listener
        handler.unregisterEvents(testListener);

        // call the test event, this should not change the data now
        handler.callEvent(new TestEvent("Different data"));
        Assert.assertNotEquals("Different data", testListener.getData());
        Assert.assertEquals(1, testListener.getTimesRan());
    }

    @Test
    public void test_multipleHandlersTest() {
        // create the handler and listener instances
        EventHandler handler = new EventHandler();
        MultipleHandlersTest listener = new MultipleHandlersTest();

        // register the listener
        handler.registerEvents(listener);

        // make sure the neither of the events have ran yet
        Assert.assertEquals(0, listener.getTimesRan());

        // call the event (should be invoked twice in the listener)
        handler.callEvent(new TestEvent("Some data"));

        // check if both ran
        Assert.assertEquals(2, listener.getTimesRan());

        // unregister the listener
        handler.unregisterEvents(listener);

        // call the event (should not be invoked at all)
        handler.callEvent(new TestEvent("Some data"));
        Assert.assertEquals(2, listener.getTimesRan());
    }
}
