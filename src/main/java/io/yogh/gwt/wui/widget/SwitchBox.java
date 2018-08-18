package io.yogh.gwt.wui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SwitchBox extends Composite implements HasValueChangeHandlers<Boolean>, TakesValue<Boolean> {
  private static final SwitchBoxUiBinder UI_BINDER = GWT.create(SwitchBoxUiBinder.class);

  interface SwitchBoxUiBinder extends UiBinder<Widget, SwitchBox> {}

  @UiField InputElement input;
  @UiField SpanElement slider;

  private String colorLeft;
  private String colorRight;

  public SwitchBox() {
    initWidget(UI_BINDER.createAndBindUi(this));

    Event.sinkEvents(input, Event.ONCHANGE);
    Event.setEventListener(input, e -> {
      if ((e.getTypeInt() & Event.ONCHANGE) == Event.ONCHANGE) {
        boolean checked = getValue();
        ValueChangeEvent.fire(SwitchBox.this, checked);
        slider.getStyle().setBackgroundColor(checked ? colorRight : colorLeft);
      }
    });
  }

  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  public void setLeft(String colorLeft) {
    this.colorLeft = colorLeft;

    if (!input.isChecked()) {
      slider.getStyle().setBackgroundColor(colorLeft);
    }
  }

  public void setRight(String colorRight) {
    this.colorRight = colorRight;

  }

  @Override
  public void setValue(Boolean value) {
    input.setChecked(value);
  }

  @Override
  public Boolean getValue() {
    return input.isChecked();
  }
}