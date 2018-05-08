package nl.yogh.gwt.wui.widget.table;

public class SimpleInteractiveClickDivTable<T> extends InteractiveClickDivTable<T, SimpleInteractiveDivTableRow> {
  public SimpleInteractiveClickDivTable() {
    super();
  }

  public SimpleInteractiveClickDivTable(final TypedFlowPanel<SimpleInteractiveDivTableRow> panel, final boolean loadingByDefault) {
    super(panel, loadingByDefault);
  }

  @Override
  protected SimpleInteractiveDivTableRow createDivTableRow(final T object) {
    return new SimpleInteractiveDivTableRow();
  }
}
