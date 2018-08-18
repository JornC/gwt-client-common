package io.yogh.gwt.wui.widget.table;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

public class SimpleInteractiveDivTableRow extends SimpleDivTableRow implements InteractiveDivTableRow {
  private boolean value;

  @Override
  public Boolean getValue() {
    return value;
  }

  @Override
  public void setValue(final Boolean value) {
    setValue(value, false);
  }

  @Override
  public void setValue(final Boolean value, final boolean fireEvents) {
    this.value = value;

    if (fireEvents) {
      ValueChangeEvent.fire(this, value);
    }
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Boolean> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }
}
