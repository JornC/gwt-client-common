package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Simple interface indicating an implementing entity may represent a DataTable.
 *
 * @param <T>
 *          Type of the datatable
 */
public interface IsDataTable<T> extends IsWidget {
  DivTable<T, ?> asDataTable();
}
