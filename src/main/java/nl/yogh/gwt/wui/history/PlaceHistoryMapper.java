package nl.yogh.gwt.wui.history;

import nl.yogh.gwt.wui.place.ApplicationPlace;

public interface PlaceHistoryMapper {
  String getToken(final ApplicationPlace value);

  ApplicationPlace getPlace(String token);
}
