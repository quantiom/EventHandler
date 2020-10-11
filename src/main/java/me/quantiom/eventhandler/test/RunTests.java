package me.quantiom.eventhandler.test;

import me.quantiom.eventhandler.EventHandler;
import me.quantiom.eventhandler.event.impl.TestEvent;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;

public class RunTests {
    public static void main(String[] args) {
        System.out.println("Running tests...");
        Result result = JUnitCore.runClasses(RunTests.class);

        List<Failure> failures = result.getFailures();

        if (failures.isEmpty()) {
            System.out.println("All tests passed. (0 failures)");
        } else {
            System.out.println("Tests failed, " + failures.size() + " failure(s):");
            for (Failure failure : failures) {
                System.out.println(failure.toString());
            }
        }
    }

    @Test
    public void listenerTest() {
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
    public void multipleHandlersTest() {
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
