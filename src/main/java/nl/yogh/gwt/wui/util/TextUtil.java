package nl.yogh.gwt.wui.util;

import java.util.Iterator;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;

public final class TextUtil {
  private TextUtil() {}

  public interface TextFactory<T> {
    String getText(T item);
  }

  /* Text utils */

  /**
   * Capitalises the first letter in the given string.
   *
   * @param nocaps The string to capitalise.
   * @return the given argument if the argument is null or empty, or a string with the first character uppercased.
   */
  public static String capitalise(final String nocaps) {
    if ((nocaps == null) || nocaps.isEmpty()) {
      return nocaps;
    }

    return new StringBuilder(nocaps.substring(0, 1).toUpperCase()).append(nocaps.substring(1)).toString();
  }

  public static <T> String joinItems(final Iterator<T> iterator, final TextFactory<T> textMachine, final String seperator) {
    final JsArrayString jsa = JavaScriptObject.createArray().cast();

    while (iterator.hasNext()) {
      jsa.push(textMachine.getText(iterator.next()));
    }

    return jsa.join(seperator);
  }

  public static String joinStrings(final String... strings) {
    return joinStrings(strings, " ");
  }

  public static String joinStrings(final String[] strings, final String seperator) {
    final JsArrayString jsa = JavaScriptObject.createArray().cast();

    for (int i = 0; i < strings.length; i++) {
      jsa.push(strings[i]);
    }

    return jsa.join(seperator);
  }
}