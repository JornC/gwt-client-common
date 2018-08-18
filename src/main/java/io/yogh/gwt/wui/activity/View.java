package io.yogh.gwt.wui.activity;

import com.google.gwt.user.client.ui.IsWidget;

public interface View<P> extends IsWidget, HasPresenter<P> {
  public interface Presenter {}
}
