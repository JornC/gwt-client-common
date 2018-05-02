/*
 * Copyright Dutch Ministry of Economic Affairs
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
package nl.yogh.gwt.wui.widget;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SliderBar extends Composite {
  private static final int ALLOWED_EVENTS = Event.ONCLICK | Event.ONCHANGE;

  /**
   * This Event type is not listed in GWT's {@link Event} constant list, it is the event that is fired on Input elements for each change the user
   * makes to it. Contrasting the OnChange Event, which only fires when a change is committed (final, typically on release).
   */
  private static final String INPUT_EVENT_TYPE = "input";

  private static final SliderBarUiBinder UI_BINDER = GWT.create(SliderBarUiBinder.class);

  interface SliderBarUiBinder extends UiBinder<Widget, SliderBar> {}

  @UiField InputElement input;

  // private boolean dragging;

  public SliderBar(final Consumer<Double> callback) {
    initWidget(UI_BINDER.createAndBindUi(this));


    Event.sinkEvents(input, Event.ONCHANGE);
    Event.sinkEvents(input, Event.getTypeInt(INPUT_EVENT_TYPE));
    Event.setEventListener(input, e -> {
      if ((e.getTypeInt() & ALLOWED_EVENTS) == 0) {
        return;
      }

      callback.accept(Double.parseDouble(input.getValue()));

      // The code below would make the slider update while sliding. Unfortunately the updated value isn't passed, the old one is.

      // // 1101 = Mouse down, mouse up, change, click, move
      // if ((e.getTypeInt() & 1101) == 0) {
      // return;
      // }
      //
      // // 4 = Mouse down
      // if ((e.getTypeInt() & 4) == 0) {
      // dragging = true;
      // }
      //
      // // 8 = Mouse up
      // if ((e.getTypeInt() & 8) == 0) {
      // dragging = false;
      // }
      //
      // // 64 = Mouse move
      // // 1025 = click, change
      // if ((dragging && (e.getTypeInt() & 64) != 0) || (e.getTypeInt() & 1025) == 0) {
      // callback.accept(Double.parseDouble(input.getValue()));
      // return;
      // }
    });
  }

  public void setValue(final double opacity) {
    input.setValue(String.valueOf(opacity));
  }
}