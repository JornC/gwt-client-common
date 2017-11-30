package nl.yogh.gwt.wui.event;

import nl.yogh.gwt.wui.place.ApplicationPlace;

public class PlaceChangeEvent extends SimpleGenericEvent<ApplicationPlace> {
  private final boolean silent;

  public PlaceChangeEvent(final ApplicationPlace obj) {
    this(obj, true);
  }

  public PlaceChangeEvent(final ApplicationPlace obj, final boolean silent) {
    super(obj);

    this.silent = silent;
  }

  public boolean isSilent() {
    return silent;
  }
}
