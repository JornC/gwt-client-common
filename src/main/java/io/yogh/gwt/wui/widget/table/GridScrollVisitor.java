package io.yogh.gwt.wui.widget.table;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;
import com.google.gwt.user.client.ui.Panel;

public class GridScrollVisitor implements GridVisitor, ScrollHandler {
  /**
   * At what percentage of scrolling a new data load will be triggered.
   */
  private static final double TRIGGER_SENSITIVITY = 0.99;

  private Panel rowPanel;
  private final DataTableFetcher provider;

  private DivTable<?, ?> values;

  public GridScrollVisitor(final DataTableFetcher provider) {
    this.provider = provider;
  }

  @Override
  public final void onScroll(final ScrollEvent event) {
    final double scrollHeight = rowPanel.getElement().getScrollHeight();
    final double scrollTop = rowPanel.getElement().getScrollTop();
    final double clientHeight = rowPanel.getElement().getClientHeight();
    if ((scrollTop + clientHeight) / scrollHeight > TRIGGER_SENSITIVITY) {
      provider.loadData(values.size());
    }
  }

  @Override
  public void exposeRowPanel(final Panel rowPanel) {
    this.rowPanel = rowPanel;

    rowPanel.asWidget().addDomHandler(this, ScrollEvent.getType());
  }

  @Override
  public void exposeDataTable(final IsDataTable<?> values) {
    this.values = values.asDataTable();
  }
}
