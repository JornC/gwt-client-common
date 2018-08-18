package io.yogh.gwt.wui.widget;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class SwitchPanel extends FlowPanel {
  private Widget activeWidget;

  public int getVisibleWidget() {
    return super.getWidgetIndex(activeWidget);
  }

  public void showWidget(final int index) {
    final Widget showWidget = getWidget(index);

    if (showWidget == null || showWidget == activeWidget) {
      return;
    }

    if (activeWidget != null) {
      setWidgetVisible(activeWidget, false);
    }

    activeWidget = showWidget;

    setWidgetVisible(activeWidget, true);
  }

  public boolean isShowing(final Widget widg) {
    return widg == activeWidget;
  }

  private void setWidgetVisible(final Widget widget, final boolean visible) {
    widget.setVisible(visible);
  }

  @Override
  public void add(final Widget w) {
    w.setVisible(false);

    super.add(w);
  }

  public void hideWidget() {
    setWidgetVisible(activeWidget, false);
    activeWidget = null;
  }
}