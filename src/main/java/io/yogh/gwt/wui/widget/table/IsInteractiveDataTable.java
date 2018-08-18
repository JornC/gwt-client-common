package io.yogh.gwt.wui.widget.table;

public interface IsInteractiveDataTable<T> extends IsDataTable<T> {
  @Override
  InteractiveDivTable<T, ?> asDataTable();
}
