package io.yogh.gwt.wui.daemon;

import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;

import io.yogh.gwt.wui.dev.DevelopmentObserver;
import io.yogh.gwt.wui.util.EventBusProxy;

public class DaemonBootstrapperImpl extends EventBusProxy implements DaemonBootstrapper {
  private final ExceptionDaemon exceptionDaemon;
  private final DevelopmentObserver observer;

  @Inject
  public DaemonBootstrapperImpl(final ExceptionDaemon exceptionDaemon, final DevelopmentObserver observer) {
    this.exceptionDaemon = exceptionDaemon;
    this.observer = observer;
  }

  @Override
  public void init(final Runnable complete) {
    if (autoComplete()) {
      complete.run();
    }
  }

  protected boolean autoComplete() {
    return true;
  }

  @Override
  public void setEventBus(final EventBus eventBus) {
    super.setEventBus(eventBus, exceptionDaemon, observer);
  }
}
