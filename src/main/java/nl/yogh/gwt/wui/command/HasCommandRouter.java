package nl.yogh.gwt.wui.command;

import nl.yogh.gwt.wui.widget.HasEventBus;

public interface HasCommandRouter extends HasEventBus {
  void onStart();
}
