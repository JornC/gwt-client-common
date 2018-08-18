package io.yogh.gwt.wui.daemon;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.widget.HasEventBus;

@ImplementedBy(DaemonBootstrapperImpl.class)
public interface DaemonBootstrapper extends HasEventBus {
  void init(Runnable complete);
}
