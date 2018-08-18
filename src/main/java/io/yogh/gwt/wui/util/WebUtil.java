package io.yogh.gwt.wui.util;

import com.google.gwt.core.shared.GWT;

public class WebUtil {
  private static String absoluteRoot;

  private WebUtil() {}

  public static String getAbsoluteRoot() {
    return absoluteRoot;
  }

  public static void setAbsoluteRoot(final String absoluteRoot) {
    final String actual = sanitize(absoluteRoot);

    GWT.log("[INFO] Root path set to: " + actual);

    WebUtil.absoluteRoot = actual;
  }

  private static String sanitize(final String absoluteRoot) {
    return "/" + absoluteRoot.replace("http://", "").replaceAll("https://", "").split("/", 2)[1];
  }

  public static String getUrl(final String uri) {
    return uri.startsWith("http") ? uri : getAbsoluteRoot() + uri;
  }
}
