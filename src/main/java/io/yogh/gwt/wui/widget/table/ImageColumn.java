package io.yogh.gwt.wui.widget.table;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class ImageColumn<L> extends WidgetFactory<L, Image> {
  @Override
  public Image createWidget(final L object) {
    final ImageResource value = getValue(object);
    return value == null ? new Image() : new Image(value);
  }

  @Override
  public Widget wrapWidget(final Widget widget) {
    return new SimplePanel(widget.asWidget());
  }

  public abstract ImageResource getValue(final L object);
}
