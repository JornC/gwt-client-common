package io.yogh.gwt.wui.widget.table;

import com.google.gwt.event.dom.client.ClickEvent;

public abstract class InteractiveClickDivTable<T, R extends InteractiveDivTableRow> extends InteractiveDivTable<T, R> {
  public InteractiveClickDivTable() {
    super();
  }

  public InteractiveClickDivTable(final TypedFlowPanel<R> panel, final boolean loadingByDefault) {
    super(panel, loadingByDefault);
  }

  @Override
  public void applyRowOptions(final R row, final T item) {
    super.applyRowOptions(row, item);

    row.asWidget().addDomHandler(event -> toggleRowSelection(row, item), ClickEvent.getType());
  }
}
