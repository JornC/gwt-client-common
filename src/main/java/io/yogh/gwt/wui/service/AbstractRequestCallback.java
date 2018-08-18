package io.yogh.gwt.wui.service;

import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractRequestCallback<T> implements RequestCallback {
  private final AsyncCallback<T> callback;

  public AbstractRequestCallback(final AsyncCallback<T> callback) {
    this.callback = callback;
  }

  @Override
  public void onResponseReceived(final Request request, final Response response) {
    try {
      if (response.getStatusCode() == Response.SC_OK) {
        callback.onSuccess(processResponse(response.getText()));
      } else {
        callback.onFailure(new RuntimeException());
      }
    } catch (final Exception e) {
      callback.onFailure(e);
    }
  }

  @Override
  public void onError(final Request request, final Throwable exception) {
    callback.onFailure(exception);
  }

  protected abstract T processResponse(String resp);
}