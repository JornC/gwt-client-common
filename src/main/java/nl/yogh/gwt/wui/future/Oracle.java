package nl.yogh.gwt.wui.future;

import java.util.Map;
import java.util.function.Consumer;

public interface Oracle {
  <T> boolean ask(final Map<String, T> cache, final String key, final Consumer<T> call, final Consumer<T> future);
}
