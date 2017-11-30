package nl.yogh.gwt.wui.util;

import com.google.web.bindery.event.shared.EventBus;

import nl.yogh.gwt.wui.widget.HasEventBus;

public class EventBusProxy {
  protected void setEventBus(final EventBus eventBus, final HasEventBus... composites) {
    for (final HasEventBus comp : composites) {
      comp.setEventBus(eventBus);
    }
  }
}
