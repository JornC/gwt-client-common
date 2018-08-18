package io.yogh.gwt.wui.dev;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.widget.HasEventBus;

@ImplementedBy(DevelopmentObserverImpl.class)
public interface DevelopmentObserver extends HasEventBus {}
