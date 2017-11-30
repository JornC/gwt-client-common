package nl.yogh.gwt.wui.future;

import java.util.Map;
import java.util.function.Consumer;

import nl.yogh.gwt.wui.service.AppAsyncCallback;

public class CachingCallback<T> extends AppAsyncCallback<T> {
  private final Map<String, T> cache;
  private final Consumer<T> future;
  private final String key;

  public CachingCallback(final String key, final Map<String, T> cache, final Consumer<T> future) {
    this.key = key;
    this.cache = cache;
    this.future = future;
  }

  @Override
  public void onSuccess(final T result) {
    cache.put(key, result);
    future.accept(result);
  }
}
