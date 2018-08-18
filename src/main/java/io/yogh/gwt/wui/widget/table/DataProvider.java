package io.yogh.gwt.wui.widget.table;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.editor.client.LeafValueEditor;

public class DataProvider<C extends Collection<T>, T> implements LeafValueEditor<C> {
  private final Set<DivTable<T, ?>> displays = new HashSet<DivTable<T, ?>>();

  private C value;

  private Comparator<T> comparator;

  @Override
  public void setValue(final C value) {
    this.value = value;

    refresh();
  }

  @Override
  public C getValue() {
    return value;
  }

  public void setComparator(final Comparator<T> comparator) {
    this.comparator = comparator;
  }

  public boolean addDataDisplay(final IsDataTable<T> table) {
    return displays.add(table.asDataTable());
  }

  public boolean addDataDisplay(final DivTable<T, ?> table) {
    return displays.add(table);
  }

  public boolean removeDataDisplay(final IsDataTable<T> table) {
    return displays.remove(table.asDataTable());
  }

  public boolean removeDataDisplay(final DivTable<T, ?> table) {
    return displays.remove(table);
  }

  public void refresh() {
    if (comparator != null) {
      if (value instanceof List) {
        Collections.sort((List<T>) value, comparator);
      }
    }

    redraw();
  }

  public void redraw() {
    for (final DivTable<T, ?> table : displays) {
      table.setRowData(value, true);
    }
  }
}
