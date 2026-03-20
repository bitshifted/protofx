# Event Bus

The `co.bitshifted.protofx.core.eventbus` package provides a simple yet powerful event bus implementation that allows for decoupled, asynchronous communication between different components of your application. This is achieved using a publish-subscribe model, which helps in building modular and maintainable systems.

## Introduction

The event bus is designed to allow different parts of your application to communicate without having direct dependencies on each other. A component can publish an event, and any other component that is subscribed to that type of event will be notified.

The main components of this package are:

*   **`EventBus`**: An interface that defines the core API for publishing events and managing subscriptions.
*   **`Event`**: A marker interface that all event objects should implement.
*   **`@EventBusSubscriptionHandler`**: An annotation used to mark methods within a subscriber class that should handle incoming events.

## `EventBus`

The `EventBus` is the central hub for event communication. You would typically inject an instance of `EventBus` into any class that needs to publish or subscribe to events.

```java
@Inject
private EventBus eventBus;
```

### Publishing Events

To publish an event, you simply create an instance of your event object and pass it to the `publishEvent` method. The event object must be a class that implements the `Event` interface.

```java
// Define a custom event
public class UserLoggedInEvent implements Event {
    private final User user;

    public UserLoggedInEvent(User user) {
        this.user = user;
    }

    @Override
    public User source() {
        return user;
    }
}

// Publishing the event
User user = //... get the logged-in user
eventBus.publishEvent(new UserLoggedInEvent(user));
```

### Subscribing to Events

There are two primary ways to subscribe to events:

#### 1. Using `Flow.Subscriber`

For more advanced use cases, you can implement the `java.util.concurrent.Flow.Subscriber` interface and subscribe it to a specific event type.

```java
public class MySubscriber implements Flow.Subscriber<Event> {
    // ... implementation of onSubscribe, onNext, onError, onComplete

    @Override
    public void onNext(Event event) {
        if (event instanceof UserLoggedInEvent) {
            User user = (User) event.source();
            System.out.println("User logged in: " + user.getName());
        }
    }
}

// Subscribing the handler
MySubscriber subscriber = new MySubscriber();
eventBus.subscribe(subscriber, UserLoggedInEvent.class);
```

#### 2. Using Annotated Methods (Recommended)

The simplest and most common way to subscribe is by creating a handler class with one or more methods annotated with `@EventBusSubscriptionHandler`. The event bus will automatically detect these methods and invoke them when a matching event is published.

The handler method must have a single parameter whose type is the event it wishes to receive.

```java
public class UserActivityLogger {

    @Inject
    public UserActivityLogger(EventBus eventBus) {
        // Subscribe this object to receive events of type UserLoggedInEvent
        eventBus.subscribe(this, UserLoggedInEvent.class);
    }

    @EventBusSubscriptionHandler
    public void onUserLoggedIn(UserLoggedInEvent event) {
        User user = (User) event.source();
        System.out.println("Logging user activity: " + user.getName() + " logged in.");
    }
}
```

### Unsubscribing

To stop a handler from receiving events, you can use the `unsubscribe` method. This is important for preventing memory leaks, especially in UI applications where components have a lifecycle.

```java
// When the component is being destroyed or is no longer needed
eventBus.unsubscribe(mySubscriber);
```

## Code Sample: A Simple Chat Application

Here’s a conceptual example of how the event bus could be used in a simple chat application.

**1. Define the Event:**

```java
public class NewChatMessageEvent implements Event {
    private final String message;

    public NewChatMessageEvent(String message) {
        this.message = message;
    }

    @Override
    public String source() {
        return message;
    }
}
```

**2. The Publisher (e.g., a message input component):**

```java
public class MessageInput {
    private final EventBus eventBus;

    @Inject
    public MessageInput(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void sendMessage(String message) {
        // Publish the new message event
        eventBus.publishEvent(new NewChatMessageEvent(message));
    }
}
```

**3. The Subscriber (e.g., a chat display component):**

```java
public class ChatDisplay {

    @Inject
    public ChatDisplay(EventBus eventBus) {
        // Subscribe to new chat messages
        eventBus.subscribe(this, NewChatMessageEvent.class);
    }

    @EventBusSubscriptionHandler
    public void onNewMessage(NewChatMessageEvent event) {
        String message = event.source();
        // Update the UI to display the new message
        displayNewMessage(message);
    }

    private void displayNewMessage(String message) {
        System.out.println("New Message: " + message);
    }
}
```

In this example, `MessageInput` and `ChatDisplay` are completely decoupled. `MessageInput` doesn't know or care who is listening for new messages, and `ChatDisplay` doesn't know where the messages are coming from. They only communicate through the `EventBus`.
