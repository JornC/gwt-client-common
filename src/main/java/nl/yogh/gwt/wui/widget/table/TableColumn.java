package nl.yogh.gwt.wui.widget.table;

import java.util.Collection;

/**
 * Defines a column that is an inner table.
 * 
 * @param <T> Inner content type
 * @param <E> Outer content (row) type
 * @param <W> Type of the table
 */
public abstract class TableColumn<T, E, W extends IsDataTable<T>> extends WidgetFactory<E, W> {

  @Override
  public W createWidget(final E object) {
    final W table = createDataTable(object);

    configureDataTable(table);

    if (table != null) {
      table.asDataTable().setRowData(getRowData(object));
    }

    return table;
  }

  public abstract Collection<T> getRowData(E object);

  public void configureDataTable(final W table) {
    // No-op by default
  }

  public abstract W createDataTable(E object);

}
