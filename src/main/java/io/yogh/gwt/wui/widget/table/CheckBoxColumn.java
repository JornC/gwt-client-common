package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.CheckBox;

public abstract class CheckBoxColumn<O> extends WidgetFactory<O, CheckBox> {
  @Override
  public CheckBox createWidget(final O object) {
    final CheckBox checkBox = new CheckBox();

    checkBox.setValue(getValue(object));
    checkBox.addValueChangeHandler(event -> setValue(object, event.getValue()));

    return checkBox;
  }

  @Override
  public void ensureDebugId(final String debugId) {
    // Turn this into a no-op -- setting a double ID on CheckBox destroys it, so pre-emptively disable it.
  }

  protected abstract Boolean getValue(O outer);

  protected abstract void setValue(O object, Boolean value);
}
