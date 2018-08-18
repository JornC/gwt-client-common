package io.yogh.gwt.wui.widget;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.SimplePanel;

public class Paragraph extends SimplePanel implements HasText {
  public Paragraph() {
    super(DOM.createElement("p"));
  }

  @Override
  public String getText() {
    return getElement().getInnerText();
  }

  @Override
  public void setText(String text) {
    getElement().setInnerText(text);
  }
}
