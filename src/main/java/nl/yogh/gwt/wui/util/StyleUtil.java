package nl.yogh.gwt.wui.util;

import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * Util class to set style properties on widgets/elements.
 */
public class StyleUtil {
  private StyleUtil() {}

  /**
   * Set the text as placeholder property on a html element.
   *
   * @param vbb
   *          Widget to set placeholder
   * @param text
   *          Text to set as placeholder
   */
  public static void setPlaceHolder(final ValueBoxBase<?> vbb, final String text) {
    vbb.getElement().setAttribute("placeholder", text);
  }

  public static String joinStyles(final String... styles) {
    return TextUtil.joinStrings(styles, " ");
  }
}