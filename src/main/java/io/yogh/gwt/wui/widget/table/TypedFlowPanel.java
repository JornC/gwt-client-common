package io.yogh.gwt.wui.widget.table;

import java.util.Iterator;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;

public class TypedFlowPanel<R extends DivTableRow> implements IsWidget, Iterable<R> {
  private final FlowPanel panel = new FlowPanel();

  public void add(final R child) {
    panel.add(child);
  }

  public void insert(final R w, final int beforeIndex) {
    panel.insert(w, beforeIndex);
  }

  @SuppressWarnings("unchecked")
  public R getWidget(final int index) {
    return (R) panel.getWidget(index);
  }
  
  public int size() {
    return panel.getWidgetCount();
  }

  @SuppressWarnings("unchecked")
  public Iterator<R> iterator() {
    return (Iterator<R>) (Iterator<?>) panel.iterator();
  }

  @Override
  public FlowPanel asWidget() {
    return panel;
  }

  public void clear() {
    panel.clear();
  }

  public boolean isEmpty() {
    return panel.getWidgetCount() == 0;
  }

  public boolean remove(final int i) {
    return panel.remove(i);
  }
}
