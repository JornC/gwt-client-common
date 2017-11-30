package nl.yogh.gwt.wui.dev;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.logging.client.SimpleRemoteLogHandler;

public class AbstractLogger {
  private static final SimpleRemoteLogHandler remoteLogger = new SimpleRemoteLogHandler();

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

    if (!txt.isEmpty()) {
      remoteLogger.publish(new LogRecord(Level.INFO, txt));
    }
  }
}
