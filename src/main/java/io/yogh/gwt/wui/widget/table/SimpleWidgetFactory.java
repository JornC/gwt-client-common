package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple widget factory which can generate a {@link Widget} out of some object type.
 * 
 * Exists because it doesn't care about explicit widget typing.
 * 
 * Creates a panel and nothing else by default. You should override.
 * 
 * @param <L> Type of object to generate a widget out of.
 */
public abstract class SimpleWidgetFactory<L> extends WidgetFactory<L, Widget> {
  @Override
  public Widget createWidget(final L object) {
    return new SimplePanel();
  }
}
