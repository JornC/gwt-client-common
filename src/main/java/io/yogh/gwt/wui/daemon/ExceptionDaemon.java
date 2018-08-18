package io.yogh.gwt.wui.daemon;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.util.Initializable;

@ImplementedBy(ExceptionDaemonImpl.class)
public interface ExceptionDaemon extends Initializable {}
