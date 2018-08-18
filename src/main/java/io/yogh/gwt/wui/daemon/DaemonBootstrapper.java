package io.yogh.gwt.wui.daemon;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.util.Initializable;

@ImplementedBy(DaemonBootstrapperImpl.class)
public interface DaemonBootstrapper extends Initializable {
  void init(Runnable complete);
}
