package io.yogh.gwt.wui.daemon;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.UmbrellaException;

import io.yogh.gwt.wui.util.NotificationUtil;

public class ExceptionDaemonImpl implements ExceptionDaemon {
  @Override
  public void setEventBus(final EventBus eventBus) {
    GWT.setUncaughtExceptionHandler(e -> {
      final Throwable cause = findCause(e);

      if (cause instanceof IncompatibleRemoteServiceException) {
        Window.Location.reload();
      }

      if (!isKnownException(cause)) {
        final String message = cause.getMessage();
        Logger.getLogger("UncaughtExceptionHandler").log(Level.SEVERE, message, cause);

        NotificationUtil.broadcastError(eventBus, message);
      }
    });
  }

  private Throwable findCause(final Throwable e) {
    if (e == null) {
      return null;
    }

    if (e instanceof UmbrellaException) {
      return findCause(e.getCause());
    }

    return e;
  }

  private boolean isKnownException(final Throwable e) {
    return e instanceof IncompatibleRemoteServiceException || e instanceof InvocationException || e instanceof StatusCodeException;
  }
}
