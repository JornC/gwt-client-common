package io.yogh.gwt.wui.widget;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.web.bindery.event.shared.EventBus;

public abstract class FieldedEventComposite extends Composite implements HasEventBus {
  public @UiField(provided = true) EventBus eventBus;

  public FieldedEventComposite() {}

  public FieldedEventComposite(final EventBus eventBus) {
    setEventBus(eventBus);
  }

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
