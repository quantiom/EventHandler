# EventHandler
A simple and lightweight event handler system made in Java.

#### Event Implementation
```java
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
```

### Listener Class Example
```java
public class SomeListener implements EventListener {
    // will be invoked
    @HandleEvent
    public void onTestEvent(TestEvent event) {
        System.out.println("Data: " + event.getData());
    }
    
    // will not be invoked
    public void onNotInvokedTestEvent(TestEvent event) {
        System.out.println("Data: " + event.getData());
    }
}
```

#### Usage
```java
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
```

See [tests](src/test/java/me/quantiom/eventhandler/EventHandlerTests.java) for more.
