package io.yogh.gwt.wui.history;

import io.yogh.gwt.wui.place.ApplicationPlace;

public interface PlaceHistoryMapper {
  String getToken(final ApplicationPlace value);

  ApplicationPlace getPlace(String token);
}
