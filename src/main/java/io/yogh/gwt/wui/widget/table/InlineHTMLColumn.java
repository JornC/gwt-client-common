package io.yogh.gwt.wui.widget.table;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.InlineHTML;

public abstract class InlineHTMLColumn<L> extends WidgetFactory<L, InlineHTML> {

  private final boolean displayTitle;

  public InlineHTMLColumn() {
    this(false);
  }

  public InlineHTMLColumn(final boolean displayTitle) {
    this.displayTitle = displayTitle;
  }

  @Override
  public InlineHTML createWidget(final L object) {
    final InlineHTML label = new InlineHTML();

    final SafeHtml value = getValue(object);

    if (value != null) {
      label.setHTML(value);

      if (displayTitle) {
        final String titleText = getTitleText(object);
        label.setTitle(titleText == null ? value.asString() : titleText);
      }
    }

    return label;
  }

  public String getTitleText(final L object) {
    return null;
  }

  public abstract SafeHtml getValue(final L object);
}
