package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.Label;

public abstract class TextColumn<L> extends WidgetFactory<L, Label> {

  private final boolean displayTitle;

  public TextColumn() {
    this(false);
  }

  public TextColumn(final boolean displayTitle) {
    this.displayTitle = displayTitle;
  }

  @Override
  public Label createWidget(final L object) {
    final Label label = new Label();

    final String value = getValue(object);

    if (value != null) {
      label.setText(value);
    }

    if (displayTitle) {
      final String titleText = getTitleText(object);
      label.setTitle(titleText == null ? value : titleText);
    }

    return label;
  }

  public String getTitleText(final L object) {
    return null;
  }

  public abstract String getValue(final L object);
}
