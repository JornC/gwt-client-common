package nl.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public abstract class IntegerColumn<L> extends SimpleWidgetFactory<L> {
  private static final String NOT_AVAILABLE_VALUE = "-";

  @Override
  public Widget createWidget(final L object) {
    return new Label(formatValue(getValue(object)));
  }

  private String formatValue(final Integer value) {
    if (value == null) {
      return NOT_AVAILABLE_VALUE;
    }

    return String.valueOf(value);
  }

  public abstract Integer getValue(final L object);
}
