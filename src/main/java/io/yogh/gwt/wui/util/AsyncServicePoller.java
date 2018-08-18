package io.yogh.gwt.wui.util;

import java.util.function.Consumer;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AsyncServicePoller<T> implements AsyncCallback<T> {

  private final Consumer<AsyncCallback<T>> serviceCall;
  private final AsyncCallback<T> callback;
  private final Timer timer;
  private final int interval;
  private boolean running;

  public AsyncServicePoller(final int interval, final Consumer<AsyncCallback<T>> func,
      final AsyncCallback<T> callback) {
    this.interval = interval;
    this.serviceCall = func;
    this.callback = callback;
    this.timer = new Timer() {
      @Override
      public void run() {
        doServiceCall();
      }
    };
  }

  public static <T> AsyncServicePoller<T> create(final int interval, final Consumer<AsyncCallback<T>> func,
      final AsyncCallback<T> callback) {
    return new AsyncServicePoller<T>(interval, func, callback);
  }

  public void start() {
    running = true;
    doServiceCall();
  }

  public void stop() {
    running = false;
    timer.cancel();
  }

  @Override
  public void onFailure(final Throwable caught) {
    if (running) {
      callback.onFailure(caught);
      running = false;
    }
  }

  @Override
  public void onSuccess(final T result) {
    if (running) {
      if (result != null) {
        callback.onSuccess(result);
        poll();
      }
    }
  }

  private void poll() {
    timer.schedule(interval);
  }

  private void doServiceCall() {
    serviceCall.accept(this);
  }
}
