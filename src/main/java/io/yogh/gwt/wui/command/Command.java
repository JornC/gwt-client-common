package io.yogh.gwt.wui.command;

import com.google.web.bindery.event.shared.Event;

public interface Command<H> {
  boolean isSilent();

  default boolean isStrict() {
    return true;
  }

  Event<?> getEvent();

  default void silence() {
    setSilent(true);
  }

  void setSilent(boolean silence);
}
