package io.yogh.gwt.wui.widget.table;

import java.util.List;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RadioButton;

public abstract class RadioButtonEditor<T> extends FlowPanel implements HasValueChangeHandlers<T> {
  private final String groupId;

  public RadioButtonEditor(final List<T> values, final T selectedValue) {
    groupId = DOM.createUniqueId();

    for (final T value : values) {
      final RadioButton radioButton = new RadioButton(groupId, getText(value));
      radioButton.setValue(value.equals(selectedValue));
      radioButton.addValueChangeHandler(event -> {
        if (event.getValue()) {
          ValueChangeEvent.fire(RadioButtonEditor.this, value);
        }
      });

      add(radioButton);
    }
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<T> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  protected abstract String getText(final T value);
}
