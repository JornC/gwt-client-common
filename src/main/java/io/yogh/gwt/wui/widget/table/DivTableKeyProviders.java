package io.yogh.gwt.wui.widget.table;

import java.util.HashMap;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.view.client.ProvidesKey;

public final class DivTableKeyProviders {
  private static final HashMap<Class<?>, ProvidesKey<?>> PROVIDERS = new HashMap<>();

  private DivTableKeyProviders() {}

  @SuppressWarnings("unchecked")
  public static <T> void autoConfigure(final Class<T> clazz, final DivTable<T, ?> table) {
    if (PROVIDERS.containsKey(clazz)) {
      table.setKeyProvider((ProvidesKey<T>) PROVIDERS.get(clazz));
    } else {
      // Print warning?
      GWT.log("Warning: Using unsafe key provider for type: " + clazz.getName() + " in table " + table.getClass().getName()
          + " while this may not have been intended.");
    }
  }

  public static <T> void register(final Class<T> clazz, final ProvidesKey<T> keyProvider) {
    PROVIDERS.put(clazz, keyProvider);
  }
}
