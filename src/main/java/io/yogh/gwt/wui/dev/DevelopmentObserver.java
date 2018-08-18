package io.yogh.gwt.wui.dev;

import com.google.inject.ImplementedBy;

import io.yogh.gwt.wui.util.Initializable;

@ImplementedBy(DevelopmentObserverImpl.class)
public interface DevelopmentObserver extends Initializable {}
