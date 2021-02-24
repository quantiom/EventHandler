package me.quantiom.eventhandler;

import me.quantiom.eventhandler.event.impl.TestEvent;
import me.quantiom.eventhandler.event.impl.TestSuperEvent;
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

    @Test
    public void test_priorityTest() {
        // create the handler and listener instances
        EventHandler handler = new EventHandler();
        PriorityTest listener = new PriorityTest();

        // register the listener
        handler.registerEvents(listener);

        // make sure the data hasn't been overridden yet
        Assert.assertEquals("None", listener.getData());

        // call the event
        handler.callEvent(new TestEvent("Some data"));

        // check if the highest priority was called last
        Assert.assertNotEquals("Lowest priority called", listener.getData());
        Assert.assertNotEquals("Normal priority called", listener.getData());
        Assert.assertNotEquals("Highest priority called", listener.getData());
        Assert.assertEquals("Custom priority called", listener.getData());
        
        // unregister the listener
        handler.unregisterEvents(listener);
    }

    @Test
    public void test_cancelTest() {
        // create the handler and listener instances
        EventHandler handler = new EventHandler();
        CancelTest listener = new CancelTest();

        // register the listener
        handler.registerEvents(listener);

        // make sure the data hasn't been overridden yet
        Assert.assertEquals("None", listener.getData());

        // call the event
        handler.callEvent(new TestEvent("Some data"));

        // check if the lowest priority cancelled and highest priority skipped
        Assert.assertEquals("High priority called", listener.getData());

        // unregister the listener
        handler.unregisterEvents(listener);
    }

    @Test
    public void test_superEventTest() {
        // create the handler and listener instances
        EventHandler handler = new EventHandler();
        SuperEventTest listener = new SuperEventTest();

        // register the listener
        handler.registerEvents(listener);

        // make sure timesRan is 0
        Assert.assertEquals(0, listener.getTimesRan());

        // call the TestEvent (subclass)
        handler.callEvent(new TestEvent("Some data"));

        // check if both the TestEvent and the TestSuperEvent were called (timesRan == 2)
        Assert.assertEquals(2, listener.getTimesRan());

        // call the TestSuperEvent (superclass)
        handler.callEvent(new TestSuperEvent("Some data"));

        // check if only the TestSuperEvent was called
        Assert.assertEquals(3, listener.getTimesRan());
        
        // unregister the listener
        handler.unregisterEvents(listener);
    }
}
