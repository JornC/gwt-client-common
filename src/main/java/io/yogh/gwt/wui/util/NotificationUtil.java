package io.yogh.gwt.wui.util;

import com.google.web.bindery.event.shared.EventBus;

import io.yogh.gwt.wui.event.NotificationEvent;

public final class NotificationUtil {
  private NotificationUtil() {}

  public static void broadcastError(final EventBus eventBus, final String message) {
    eventBus.fireEvent(new NotificationEvent(new Notification(true, message)));
  }

}
