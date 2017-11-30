package nl.yogh.gwt.wui.history;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.place.shared.PlaceHistoryHandler.Historian;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.yogh.gwt.wui.event.PlaceChangeEvent;
import nl.yogh.gwt.wui.place.ApplicationPlace;
import nl.yogh.gwt.wui.place.DefaultPlace;
import nl.yogh.gwt.wui.place.PlaceController;

public class HistoryManagerImpl implements HistoryManager {
  interface HistoryManagerImplEventBinder extends EventBinder<HistoryManagerImpl> {}

  private final HistoryManagerImplEventBinder EVENT_BINDER = GWT.create(HistoryManagerImplEventBinder.class);

  private final ApplicationPlace defaultPlace;

  private final PlaceController placeController;

  private final Historian historian;

  private final PlaceHistoryMapper mapper;

  @Inject
  public HistoryManagerImpl(@DefaultPlace final ApplicationPlace defaultPlace, final PlaceController placeController, final EventBus eventBus,
      final Historian historian, final PlaceHistoryMapper mapper) {
    this.defaultPlace = defaultPlace;
    this.placeController = placeController;
    this.historian = historian;
    this.mapper = mapper;

    EVENT_BINDER.bindEventHandlers(this, eventBus);

    historian.addValueChangeHandler(event -> {
      final String token = event.getValue();
      handleHistoryToken(token, false);
    });
  }

  @EventHandler
  public void onPlaceChangeEvent(final PlaceChangeEvent e) {
    historian.newItem(mapper.getToken(e.getValue()), false);
  }

  @Override
  public void handleCurrentHistory() {
    GWT.log("Handling: " + historian.getToken());

    handleHistoryToken(historian.getToken(), false);
  }

  @Override
  public ApplicationPlace getPlace(final String token) {
    return mapper.getPlace(token);
  }

  private void handleHistoryToken(final String token, final boolean silent) {
    ApplicationPlace newPlace = null;

    if ("".equals(token)) {
      newPlace = defaultPlace;
    }

    if (newPlace == null) {
      newPlace = getPlace(token);
    }

    if (newPlace == null) {
      newPlace = defaultPlace;
    }

    placeController.goTo(newPlace, silent);
  }
}
