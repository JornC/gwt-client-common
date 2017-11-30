package nl.yogh.gwt.wui.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import nl.yogh.gwt.wui.future.AsyncFunction;
import nl.yogh.gwt.wui.widget.WebComponent;

public final class WebComponentUtil {
  private static final List<String> loaded = new ArrayList<String>();

  private static final String LOCATION = "components";

  private WebComponentUtil() {}

  public static void inject(final String component, final AcceptsOneWidget panel, final Consumer<Element> func) {
    final AsyncFunction initComponent = () -> doIt(panel, func, component);

    if (!loaded.contains(component)) {
      load(component, initComponent);
      return;
    } else {
      initComponent.execute();
    }
  }

  private static void load(final String component, final AsyncFunction finish) {
    loaded.add(component);

    final Element link = Document.get().createElement("link");
    link.setAttribute("rel", "import");
    link.setAttribute("href", WebUtil.getAbsoluteRoot() + LOCATION + "/" + component + "/" + component + ".html");

    Document.get().getHead().appendChild(link);

    GWT.log("Hail Hydra!");

    Event.sinkEvents(link, Event.ONLOAD);
    Event.setEventListener(link, event -> {
      if (Event.ONLOAD == event.getTypeInt()) {
        GWT.log("Loading!");
        finish.execute();
      }
    });
  }

  private static void doIt(final AcceptsOneWidget panel, final Consumer<Element> func, final String component) {
    final WebComponent webComponent = new WebComponent(component, func);
    panel.setWidget(webComponent);
  }
}
