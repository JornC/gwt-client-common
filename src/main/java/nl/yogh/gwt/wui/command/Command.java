package nl.yogh.gwt.wui.command;

import com.google.web.bindery.event.shared.Event;

public interface Command<H> {
  boolean isSilent();

  default boolean isStrict() {
    return true;
  }

  Event<?> getEvent();
}
