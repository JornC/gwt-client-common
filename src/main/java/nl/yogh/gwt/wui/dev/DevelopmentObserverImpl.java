package nl.yogh.gwt.wui.dev;

import com.google.gwt.core.client.GWT;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.yogh.gwt.wui.event.PlaceChangeEvent;

public class DevelopmentObserverImpl extends AbstractLogger implements DevelopmentObserver {
  private final DevelopmentObserverImplEventBinder EVENT_BINDER = GWT.create(DevelopmentObserverImplEventBinder.class);

  interface DevelopmentObserverImplEventBinder extends EventBinder<DevelopmentObserverImpl> {}

  @Inject
  public DevelopmentObserverImpl(final EventBus eventBus) {
    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @EventHandler
  public void onPlaceChangeEvent(final PlaceChangeEvent e) {
    log("Place change event: " + e.getClass().getSimpleName());
  }
}
