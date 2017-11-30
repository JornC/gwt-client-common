package nl.yogh.gwt.wui.history;

import com.google.inject.ImplementedBy;

import nl.yogh.gwt.wui.place.ApplicationPlace;

@ImplementedBy(HistoryManagerImpl.class)
public interface HistoryManager {
  void handleCurrentHistory();

  ApplicationPlace getPlace(String token);
}
