package io.yogh.gwt.wui.event;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.SimpleEventBus;

import io.yogh.gwt.wui.command.Command;

public class CommandEventBus extends SimpleEventBus {
  private boolean commandInProgress;

  @Override
  public void fireEvent(final Event<?> event) {
    final boolean enforce = isCommandPattern(event);

    if (enforce) {
      final boolean strict = isStrict(event);
      if (strict && commandInProgress) {
        commandInProgress = false;

        throw new RuntimeException("Pattern violation: Command fired while previous command still in progress. " + event.getClass().getSimpleName());
      }

      if (strict) {
        commandInProgress = true;
      }
    }

    super.fireEvent(event);

    if (enforce) {
      commandInProgress = false;

      // Silence state could have been changed in-flight
      if (!isSilent(event)) {
        // Finish the command by firing its event.
        fireEvent(((Command<?>) event).getEvent());
      }
    }
  }

  private boolean isSilent(final Event<?> event) {
    return ((Command<?>) event).isSilent();
  }

  private boolean isStrict(final Event<?> event) {
    return ((Command<?>) event).isStrict();
  }

  private boolean isCommandPattern(final Event<?> command) {
    return command instanceof Command;
  }
}