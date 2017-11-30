package nl.yogh.gwt.wui.service;

import java.util.ArrayList;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AggregatedAsyncCallback<T> implements AsyncCallback<T> {
  private final ArrayList<AsyncCallback<T>> listeners = new ArrayList<AsyncCallback<T>>();
  private final ArrayList<AsyncCallback<T>> lastListeners = new ArrayList<AsyncCallback<T>>();

  @Override
  public void onFailure(final Throwable caught) {
    for (final AsyncCallback<T> listener : listeners) {
      listener.onFailure(caught);
    }
    for (final AsyncCallback<T> listener : lastListeners) {
      listener.onFailure(caught);
    }
  }

  @Override
  public void onSuccess(final T result) {
    for (final AsyncCallback<T> listener : listeners) {
      listener.onSuccess(result);
    }

    // Lazily call all the remainder
    Scheduler.get().scheduleDeferred(() -> {
      for (final AsyncCallback<T> listener : lastListeners) {
        listener.onSuccess(result);
      }
    });
  }

  public void addListener(final AsyncCallback<T> listener) {
    listeners.add(listener);
  }

  public void addLastListener(final AsyncCallback<T> listener) {
    lastListeners.add(listener);
  }
}