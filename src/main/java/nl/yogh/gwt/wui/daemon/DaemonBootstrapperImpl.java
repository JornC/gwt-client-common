package nl.yogh.gwt.wui.daemon;

import com.google.inject.Inject;

import nl.yogh.gwt.wui.dev.DevelopmentObserver;
import nl.yogh.gwt.wui.util.EventBusProxy;

public class DaemonBootstrapperImpl extends EventBusProxy implements DaemonBootstrapper {
  private final ExceptionDaemon exceptionDaemon;
  private final DevelopmentObserver observer;

  @Inject
  public DaemonBootstrapperImpl(final ExceptionDaemon exceptionDaemon, final DevelopmentObserver observer) {
    this.exceptionDaemon = exceptionDaemon;
    this.observer = observer;
  }

  @Override
  public void init() {}

  @Override
  public void init(final Runnable complete) {
    exceptionDaemon.init();
    observer.init();

    if (autoComplete()) {
      complete.run();
    }
  }

  protected boolean autoComplete() {
    return true;
  }
}
