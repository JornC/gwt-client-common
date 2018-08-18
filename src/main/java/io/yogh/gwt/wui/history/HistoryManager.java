package io.yogh.gwt.wui.history;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.place.ApplicationPlace;

@ImplementedBy(HistoryManagerImpl.class)
public interface HistoryManager {
  void handleCurrentHistory();

  ApplicationPlace getPlace(String token);
}
