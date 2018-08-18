package io.yogh.gwt.wui.daemon;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.widget.HasEventBus;

@ImplementedBy(ExceptionDaemonImpl.class)
public interface ExceptionDaemon extends HasEventBus {}
