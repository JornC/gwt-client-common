package nl.yogh.gwt.wui.widget.table;

public interface DivTableRowDecorator<T, R extends DivTableRow> {
  void applyRowOptions(final R rowContainer, final T item);
}
