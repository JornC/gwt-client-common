package io.yogh.gwt.wui.util;

public final class ObjectUtil {
  private ObjectUtil() {}

  public static boolean equals(final Object a, final Object b) {
    if (a == b) {
      return true;
    }

    if (a == null) {
      return false;
    }

    return a.equals(b);
  }
}
