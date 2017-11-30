package nl.yogh.gwt.wui.command;

import nl.yogh.gwt.wui.event.PlaceChangeEvent;
import nl.yogh.gwt.wui.place.ApplicationPlace;

public class PlaceChangeCommand extends SimpleGenericCommand<ApplicationPlace, PlaceChangeEvent> {
  private final boolean silent;

  public PlaceChangeCommand(final ApplicationPlace obj) {
    this(obj, true);
  }

  public PlaceChangeCommand(final ApplicationPlace obj, final boolean silent) {
    super(obj);

    this.silent = silent;
  }

  @Override
  public boolean isSilent() {
    return silent;
  }

  @Override
  public boolean isStrict() {
    return false;
  }

  @Override
  protected PlaceChangeEvent createEvent(final ApplicationPlace value) {
    return new PlaceChangeEvent(value, silent);
  }
}
