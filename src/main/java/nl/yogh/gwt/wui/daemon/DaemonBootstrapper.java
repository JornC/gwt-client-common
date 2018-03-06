package nl.yogh.gwt.wui.daemon;

import com.google.inject.ImplementedBy;

import nl.yogh.gwt.wui.util.Initializable;

@ImplementedBy(DaemonBootstrapperImpl.class)
public interface DaemonBootstrapper extends Initializable {
  void init(Runnable complete);
}
