package io.yogh.gwt.wui.command;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public abstract class AbstractCommand<E> extends GenericEvent implements Command<E> {
  private boolean silent;

  public AbstractCommand() {}

  public AbstractCommand(final boolean silent) {
    this.silent = silent;
  }

  @Override
  public boolean isSilent() {
    return silent;
  }
}
