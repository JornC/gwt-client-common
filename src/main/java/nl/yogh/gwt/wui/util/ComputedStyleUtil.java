package nl.yogh.gwt.wui.util;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * Work-around util that can force a DOM element to render even if the JS event loop isn't complete yet.
 */
public final class ComputedStyleUtil {
  private ComputedStyleUtil() { }

  private static final String DUMMY_PROP = "height";

  /**
   * Get the computed style for the given property. Renders the element to get the actual/computed property.
   *
   * @param el Element to get the property for.
   * @param prop Property to get.
   * @return Post-render property value.
   */
  public static native String getStyleProperty(Element el, String prop) /*-{
    var computedStyle;
    if (document.defaultView && document.defaultView.getComputedStyle) {
      computedStyle = document.defaultView.getComputedStyle(el, null)[prop];
    } else if (el.currentStyle) {
      computedStyle = el.currentStyle[prop];
    } else {
      computedStyle = el.style[prop];
    }
    return computedStyle;
  }-*/;

  /**
   * Force an element to render, even if the JS event loop isn't complete.
   *
   * @param el Element to render.
   */
  public static void forceStyleRender(final Element el) {
    getStyleProperty(el, DUMMY_PROP);
  }

  /**
   * Force an element to render, even if the JS event loop isn't complete.
   *
   * @param widg Widget to render.
   */
  public static void forceStyleRender(final Widget widg) {
    forceStyleRender(widg.getElement());
  }
}