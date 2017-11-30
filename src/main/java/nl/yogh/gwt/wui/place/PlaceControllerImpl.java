package nl.yogh.gwt.wui.place;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

import nl.yogh.gwt.wui.command.PlaceChangeCommand;

@Singleton
public class PlaceControllerImpl implements PlaceController {
  private final EventBus eventBus;

  private ApplicationPlace previousPlace;
  private ApplicationPlace where;

  @Inject
  public PlaceControllerImpl(final EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public ApplicationPlace getPlace() {
    return where;
  }

  @Override
  public ApplicationPlace getPreviousPlace() {
    return previousPlace == null ? null : previousPlace;
  }

  @Override
  public void goTo(final ApplicationPlace place) {
    final ApplicationPlace current = getPlace();
    final boolean loud = place == null || current == null || !place.getClass().equals(current.getClass());

    goTo(place, !loud);
  }

  @Override
  public void goTo(final ApplicationPlace place, final boolean silent) {
    this.previousPlace = getPlace();
    this.where = place;

    final PlaceChangeCommand command = new PlaceChangeCommand(place, silent);

    eventBus.fireEvent(command);

    // Even fire if silent.
    if (command.isSilent()) {
      eventBus.fireEvent(command.getEvent());
    }
  }
}
