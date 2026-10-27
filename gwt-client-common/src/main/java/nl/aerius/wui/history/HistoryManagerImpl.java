/*
 * Copyright (c) Contributors to the project
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */
package nl.aerius.wui.history;

import com.google.gwt.core.shared.GWT;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.aerius.wui.event.NotifyHistoryEvent;
import nl.aerius.wui.event.PlaceChangeEvent;
import nl.aerius.wui.place.ApplicationPlace;
import nl.aerius.wui.place.DefaultPlace;
import nl.aerius.wui.place.Place;
import nl.aerius.wui.place.PlaceController;
import nl.aerius.wui.place.TokenizedPlace;

@Singleton
public class HistoryManagerImpl implements HistoryManager {
  interface HistoryManagerImplEventBinder extends EventBinder<HistoryManagerImpl> {}

  private final HistoryManagerImplEventBinder EVENT_BINDER = GWT.create(HistoryManagerImplEventBinder.class);

  private final ApplicationPlace defaultPlace;

  private final PlaceController placeController;

  private final EventBus eventBus;

  private final HTML5Historian historian;

  private final PlaceHistoryMapper mapper;

  /**
   * True while the place for the browser's current entry is being entered, so that place replaces the entry.
   */
  private boolean arriving;

  @Inject
  public HistoryManagerImpl(@DefaultPlace final ApplicationPlace defaultPlace, final PlaceController placeController, final EventBus eventBus,
      final HTML5Historian historian, final PlaceHistoryMapper mapper) {
    this.defaultPlace = defaultPlace;
    this.placeController = placeController;
    this.historian = historian;
    this.mapper = mapper;
    this.eventBus = eventBus;

    EVENT_BINDER.bindEventHandlers(this, eventBus);

    historian.addValueChangeHandler(event -> {
      if (!handleHistoryToken(event.getValue())) {
        historian.revert();
      }
    });
  }

  @EventHandler
  public void onPlaceChangeEvent(final PlaceChangeEvent e) {
    final Place value = e.getValue();

    if (value instanceof TokenizedPlace) {
      final String token = mapper.getToken((TokenizedPlace) value);

      if (arriving) {
        historian.replaceItem(token);
      } else {
        historian.newItem(token, false);
      }
    }

    arriving = false;
  }

  @Override
  public void handleCurrentHistory() {
    handleHistoryToken(historian.getToken());
  }

  @Override
  public TokenizedPlace getCurrentPlace() {
    final String token = historian.getToken();

    return token == null || token.isEmpty() ? null : mapper.getPlace(token);
  }

  @Override
  public TokenizedPlace getPlace(final String token) {
    return mapper.getPlace(token);
  }

  /**
   * Goes to the place for the token, and returns false if the place change was cancelled.
   */
  private boolean handleHistoryToken(final String token) {
    TokenizedPlace newPlace = null;

    if (token == null || token.isEmpty()) {
      newPlace = defaultPlace;
    }

    if (newPlace == null) {
      newPlace = getPlace(token);
    }

    if (newPlace == null) {
      newPlace = defaultPlace;
    }

    arriving = true;
    placeController.goTo(newPlace, false);
    final boolean entered = !arriving;
    arriving = false;

    eventBus.fireEvent(new NotifyHistoryEvent(newPlace));
    return entered;
  }
}
