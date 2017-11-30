package nl.yogh.gwt.wui.daemon;

import com.google.inject.ImplementedBy;

import nl.yogh.gwt.wui.util.Initializable;

@ImplementedBy(ExceptionDaemonImpl.class)
public interface ExceptionDaemon extends Initializable {}
