package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.Panel;

public interface GridVisitor {

  /**
   * Have something expose a row panel to this visitor.
   *
   * @param rowPanel
   *          Panel that's being exposed.
   */
  void exposeRowPanel(Panel rowPanel);

  /**
   * Have something expose row values to this visitor.
   *
   * @param dataTable
   *          List of values that are being exposed.
   */
  void exposeDataTable(IsDataTable<?> dataTable);

}
