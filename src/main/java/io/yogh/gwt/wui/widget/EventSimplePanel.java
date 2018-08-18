package io.yogh.gwt.wui.widget;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

public class EventSimplePanel extends SimplePanel implements HasEventBus {
  protected EventBus eventBus;

  @Override
  public void setEventBus(final EventBus eventBus) {
    this.eventBus = eventBus;
  }

  protected void setEventBus(final EventBus eventBus, final HasEventBus... composites) {
    this.eventBus = eventBus;

    for (final HasEventBus comp : composites) {
      comp.setEventBus(eventBus);
    }
  }
}
