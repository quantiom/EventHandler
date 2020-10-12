# EventHandler
A simple and lightweight event handler system made in Java.

### Features
- Scalable and fast
- Custom event support
- Cancellable events
- Event priorities
- Event inheritence support (ex: `TestEvent extends TestSuperEvent`, `TestEvent` gets called from the handler and then `TestSuperEvent` will also be called)

#### Event Implementation
```java
public class TestEvent extends Event implements EventCancellable {
    private boolean isCancelled;
    private String data;

    // can be whatever
    public TestEvent(String data) {
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
```

#### Listener Class Example
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
