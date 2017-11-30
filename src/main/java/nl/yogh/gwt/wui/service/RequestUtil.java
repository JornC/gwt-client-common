package nl.yogh.gwt.wui.service;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.URL;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public final class RequestUtil {
  private static final int CLIENT_TIMEOUT = 15 * 1000;

  private RequestUtil() {}

  public static void doTextPost(final String url, final String payload, final AsyncCallback<String> callback) {
    doPost(url, payload, new SimpleTextCallback(callback));
  }

  public static void doTextGet(final String url, final AsyncCallback<String> callback) {
    doGet(url, new SimpleTextCallback(callback));
  }

  public static void doJSONPost(final String url, final String payload, final AsyncCallback<JSONValue> callback) {
    doPost(url, payload, new JSONCallback(callback));
  }

  public static void doJSONGet(final String url, final AsyncCallback<JSONValue> callback) {
    doGet(url, new JSONCallback(callback));
  }

  public static void doPost(final String url, final String payload, final RequestCallback callback) {
    doRequest(url, RequestBuilder.POST, payload, callback);
  }

  public static void doGet(final String url, final RequestCallback callback) {
    doRequest(url, RequestBuilder.GET, null, callback);
  }

  private static void doRequest(final String url, final Method method, final String payload, final RequestCallback callback) {
    final RequestBuilder builder = new RequestBuilder(method, URL.encode(url));
    builder.setHeader("Content-Type", "application/json");
    builder.setHeader("Accept", "application/json");

    builder.setTimeoutMillis(CLIENT_TIMEOUT);

    try {
      builder.sendRequest(payload, callback);
    } catch (final RequestException requestException) {
      callback.onError(null, requestException);
    }
  }
}