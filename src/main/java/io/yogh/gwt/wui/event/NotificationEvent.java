package io.yogh.gwt.wui.event;

import io.yogh.gwt.wui.util.Notification;

public class NotificationEvent extends SimpleGenericEvent<Notification> {
  public NotificationEvent(final Notification notification) {
    super(notification);
  }
}
