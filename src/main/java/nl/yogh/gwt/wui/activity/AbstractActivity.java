package nl.yogh.gwt.wui.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;

public abstract class AbstractActivity<P, V extends View<P>> implements Activity<P> {
  protected final V view;

  public AbstractActivity(final V view) {
    this.view = view;

    view.setPresenter(getPresenter());
  }

  @Override
  public void onStop() {}

  @Override
  public String mayStop() {
    return null;
  }

  @Override
  public void onStart(final AcceptsOneWidget panel) {
    panel.setWidget(view);

    onStart();
  }

  protected void onStart() {}

  @Override
  public abstract P getPresenter();
}
