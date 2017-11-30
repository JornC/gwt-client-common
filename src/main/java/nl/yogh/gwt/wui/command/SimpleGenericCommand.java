package nl.yogh.gwt.wui.command;

import nl.yogh.gwt.wui.event.SimpleGenericEvent;

public abstract class SimpleGenericCommand<T, E extends SimpleGenericEvent<T>> extends SimpleGenericEvent<T> implements Command<E> {
  private boolean silent;

  private E event;

  public SimpleGenericCommand() {}

  public SimpleGenericCommand(final T value) {
    super(value);
  }

  public SimpleGenericCommand(final T value, final boolean silent) {
    super(value);

    this.silent = silent;
  }

  @Override
  public void setValue(final T value) {
    this.event = createEvent(value);
  }

  protected abstract E createEvent(T value);

  @Override
  public E getEvent() {
    return event == null ? createEvent(getValue()) : event;
  }

  @Override
  public boolean isSilent() {
    return silent;
  }

  public void setSilent(final boolean silent) {
    this.silent = silent;
  }

  /**
   * Short-hand for setSilent(true), can be used to silence this command's event.
   *
   * Silencing is useful for example when the command has no effect, or when the functionality is duplicated elsewhere.
   */
  public void silence() {
    setSilent(true);
  }
}
