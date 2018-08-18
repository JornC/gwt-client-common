package io.yogh.gwt.wui.command;

import io.yogh.gwt.wui.widget.HasEventBus;

public interface HasCommandRouter extends HasEventBus {
  void onStart();
}
