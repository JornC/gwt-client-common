package nl.yogh.gwt.wui.widget.table;

import java.util.Arrays;
import java.util.List;

public abstract class RadioButtonColumn<O, T> extends WidgetFactory<O, RadioButtonEditor<T>> {
  private List<T> values;

  @Override
  public RadioButtonEditor<T> createWidget(final O object) {
    final RadioButtonEditor<T> panel = new RadioButtonEditor<T>(values, getObject(object)) {
      @Override
      protected String getText(final T value) {
        return RadioButtonColumn.this.getText(value);
      }
    };

    panel.addValueChangeHandler(event -> setObject(object, event.getValue()));

    return panel;
  }

  protected abstract T getObject(O outer);

  protected abstract void setObject(O outer, T object);

  protected abstract String getText(final T value);

  public void setValues(final T[] values) {
    this.values = Arrays.asList(values);
  }

  public void setValues(final List<T> values) {
    this.values = values;
  }
}
