package io.yogh.gwt.wui.widget;

import java.util.function.Consumer;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.user.client.ui.Widget;

public class WebComponent extends Widget {
  @UiConstructor
  public WebComponent(final String name, final Consumer<Element> func) {
    final Element elem = Document.get().createElement(name);

    func.accept(elem);

    setElement(elem);
  }
}
