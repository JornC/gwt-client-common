package io.yogh.gwt.wui.widget.table;

public class SimpleDivTable<T> extends DivTable<T, SimpleDivTableRow> {
  /**
   * Create a simple default DivTable.
   */
  public SimpleDivTable() {
    super(false);
  }

  /**
   * Creates a DivTable that's loading by default (or not).
   *
   * @param loadingByDefault
   *          Whether this widget shows 'loading' content if empty.
   */
  public SimpleDivTable(final boolean loadingByDefault) {
    super(new TypedFlowPanel<SimpleDivTableRow>(), loadingByDefault);
  }

  /**
   * Create a DivTable with a custom row panel.
   *
   * @param rowPanel
   *          Custom row panel to use for rows.
   */
  public SimpleDivTable(final TypedFlowPanel<SimpleDivTableRow> rowPanel) {
    super(rowPanel, false);
  }

  /**
   * Creates a DivTable that's loading by default (or not).
   *
   * @param rowPanel
   *          Custom row panel to use for rows.
   * @param loadingByDefault
   *          Whether this widget shows 'loading' content if empty.
   */
  public SimpleDivTable(final TypedFlowPanel<SimpleDivTableRow> rowPanel, final boolean loadingByDefault) {
    super(rowPanel, loadingByDefault);
  }

  @Override
  protected SimpleDivTableRow createDivTableRow(final T object) {
    return new SimpleDivTableRow();
  }
}
