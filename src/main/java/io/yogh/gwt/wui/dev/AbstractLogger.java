package io.yogh.gwt.wui.dev;

import com.google.gwt.core.shared.GWT;

public class AbstractLogger {
  // private static final Logger logger = Logger.getLogger("AbstractLogger");

  protected void log(final String origin, final Object val) {
    log("[" + origin + "] " + String.valueOf(val));
  }

  protected void brbr() {
    log("");
  }

  protected void log(final String txt) {
    // logger.log(Level.INFO, txt);
    GWT.log(txt);
  }
}
