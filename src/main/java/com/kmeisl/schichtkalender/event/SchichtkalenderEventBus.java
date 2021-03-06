package com.kmeisl.schichtkalender.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.kmeisl.schichtkalender.SchichtkalenderUI;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for
 * relevant actions.
 */
public class SchichtkalenderEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        SchichtkalenderUI.getSchichtkalenderEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
        SchichtkalenderUI.getSchichtkalenderEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        SchichtkalenderUI.getSchichtkalenderEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception,
            final SubscriberExceptionContext context) {
        exception.printStackTrace();
    }
}
