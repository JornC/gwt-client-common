package io.yogh.gwt.wui.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;

public interface Activity<P> {
  void onStart(AcceptsOneWidget panel);

  void onStop();

  String mayStop();

  P getPresenter();
}
