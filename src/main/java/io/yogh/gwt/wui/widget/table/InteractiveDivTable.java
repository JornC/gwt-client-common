package io.yogh.gwt.wui.widget.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent.Handler;
import com.google.gwt.view.client.SelectionChangeEvent.HasSelectionChangedHandlers;
import com.google.gwt.view.client.SelectionModel;
import com.google.gwt.view.client.SingleSelectionModel;

/**
 * Interactive Div Table which allows for row selection through a SelectionModel. Supports all flavors of selection models.
 *
 * Rows can be selected or deselected by their firing a ValueChangeEvent&lt;Boolean&gt; on the row element.
 *
 * An explicit reference to the row widget is maintained internally (outside of the SelectionModel) and synchronized with the selection model upon a
 * selection change or a row change.
 *
 * Extending classes may expand on the behavior of row selection by manually firing a ValueChangeEvent on the row element _or_ calling
 * setRowSelected().
 *
 * @param <T>
 *          Type of object in this table.
 */
public abstract class InteractiveDivTable<T, R extends InteractiveDivTableRow> extends DivTable<T, R>
implements HasSelectionChangedHandlers, IsInteractiveDataTable<T>, DivTableRowDecorator<T, R>, HasValueChangeHandlers<Integer> {
  private static class InternalValueChangeEvent extends ValueChangeEvent<Boolean> {
    protected InternalValueChangeEvent(final Boolean value) {
      super(value);
    }
  }

  class InteractiveSelectionChangeHandler implements ValueChangeHandler<Boolean> {
    private final T item;
    private final R row;

    public InteractiveSelectionChangeHandler(final R row, final T item) {
      this.row = row;
      this.item = item;
    }

    @Override
    public void onValueChange(final ValueChangeEvent<Boolean> event) {
      // If the event's origin is internal, ignore
      if (event instanceof InternalValueChangeEvent) {
        return;
      }

      setRowSelected(row, item, event.getValue());
    }
  }

  protected SelectionModel<T> selectionModel;

  private String selectedStyle;

  private final HashMap<T, R> rowsDEPRR = new HashMap<T, R>();
  private final HashMap<T, R> selectedRows = new HashMap<T, R>();

  private String unselectableStyle;

  public InteractiveDivTable() {
    super();

    addRowDecorator(this);
  }

  public InteractiveDivTable(final TypedFlowPanel<R> panel, final boolean loadingByDefault) {
    super(panel, loadingByDefault);

    addRowDecorator(this);
  }

  @Override
  public void applyRowOptions(final R row, final T item) {
    // If this item cannot be selected, set the unselectable style (if any) and get out.
    if (!isItemSelectable(item)) {
      if (unselectableStyle != null) {
        row.asWidget().addStyleName(unselectableStyle);
      }
      return;
    }

    row.addValueChangeHandler(new InteractiveSelectionChangeHandler(row, item));

    if (selectionModel.isSelected(item)) {
      // Row object may not be the exact same (if replaced or redrawn)
      selectedRows.remove(item);
      selectedRows.put(item, row);
      selectionModel.setSelected(item, true);

      // Set the row style
      setRowStyle(row, true);

      // Notify the row of selection, if aware
      row.setValue(true, false);
    }

    rowsDEPRR.put(item, row);
  }

  @Override
  public void clear() {
    deselectAll();
    rowsDEPRR.clear();

    super.clear();
  }

  /**
   * DO NOT MAKE THIS METHOD PUBLIC!
   *
   * You CANNOT call this method if you want to set row selection programmatically; do it by firing a ValueChangeEvent on the row. This method handles
   * the event where a row has been selected through an event.
   *
   * As a last resort, create a _method_ in _this class_ to do the firing, but try to avoid it anyway.
   *
   * Search for alternatives! There's better ways to do explicit row selection (for example, let the row itself handle it.)
   *
   * @param row
   * @param item
   * @param selected
   */
  private void setRowSelected(final R row, final T item, final boolean selected) {
    if (!row.asWidget().isAttached() || item == null || !isItemSelectable(item)) {
      return;
    }

    setRowStyle(row, selected);

    if (selectionModel == null) {
      return;
    }

    if (selected) {
      selectedRows.put(item, row);
    } else {
      selectedRows.remove(item);
    }

    selectionModel.setSelected(item, selected);
    reselectAll();
  }

  /**
   * Toggles the selection of the given row and item.
   *
   * Does this by firing an event on the row.
   *
   * @param row
   *          Row element for which this is relevant.
   * @param item
   *          Item object which has been selected.
   */
  protected void toggleRowSelection(final R row, final T item) {
    if (selectionModel == null || !isItemSelectable(item)) {
      return;
    }

    final boolean select = !selectionModel.isSelected(item);

    setRowSelection(row, item, select);
  }

  public void setSelectedItem(final T selectedObject) {
    setSelectedItem(selectedObject, true);
  }

  public void setSelectedItem(final T selectedObject, final boolean select) {
    final R row = rowsDEPRR.get(selectedObject);

    setRowSelection(row, selectedObject, select);
  }

  public void setRowSelection(final R row, final T selectedObject, final boolean select) {
    if (selectedObject == null || row == null) {
      deselectAll();
      return;
    }

    row.setValue(select, true);
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<Integer> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  protected boolean isItemSelectable(final T item) {
    return true;
  }

  public void setSelectionModel(final SelectionModel<T> selectionModel) {
    this.selectionModel = selectionModel;
  }

  public SelectionModel<T> getSelectionModel() {
    return selectionModel;
  }

  @Override
  public void addRowData(final Collection<T> lst) {
    final int size = rows.size();

    super.addRowData(lst);

    if (rows.size() != size) {
      ValueChangeEvent.fire(this, size);
    }
  }

  @Override
  public void addRowData(final T obj) {
    final int size = rows.size();

    super.addRowData(obj);

    if (rows.size() != size) {
      ValueChangeEvent.fire(this, size);
    }
  }

  public void setSingleSelectionModel() {
    setSelectionModel(new SingleSelectionModel<>(keyProvider));
  }

  public void setMultiSelectionModel() {
    setSelectionModel(new MultiSelectionModel<>(keyProvider));
  }

  @Override
  public void setRowData(final Collection<T> lst) {
    final int size = rows.size();

    super.setRowData(lst, false);

    // Reselect or deselect everything
    if (safeEquals) {
      reselectAll();
    } else {
      deselectAll();
    }

    if (rows.size() != size) {
      ValueChangeEvent.fire(this, size);
    }
  }

  /**
   * Deselect every row selection.
   */
  public void deselectAll() {
    if (selectionModel == null) {
      return;
    }

    for (final Entry<T, R> entry : selectedRows.entrySet()) {
      entry.getValue().setValue(false, false);
      setRowStyle(entry.getValue(), false);
      selectionModel.setSelected(entry.getKey(), false);
    }

    selectedRows.clear();
  }

  public void reselectAll() {
    if (selectionModel == null) {
      return;
    }

    final ArrayList<T> deselects = new ArrayList<>();

    for (final Entry<T, R> entry : selectedRows.entrySet()) {
      final T item = entry.getKey();
      final R row = entry.getValue();

      final boolean selected = selectionModel.isSelected(item);

      setRowStyle(row, selected);

      if (!selected) {
        deselects.add(item);
      }
    }

    for (final T item : deselects) {
      selectedRows.get(item).setValue(false, false);
      selectedRows.remove(item);
    }
  }

  private void setRowStyle(final R row, final boolean add) {
    if (selectedStyle != null) {
      row.asWidget().setStyleName(selectedStyle, add);
    }
  }

  public void setUnselectableStyle(final String unselectableStyle) {
    this.unselectableStyle = unselectableStyle;
  }

  public void setSelectedStyle(final String selectedStyle) {
    this.selectedStyle = selectedStyle;
  }

  @Override
  public InteractiveDivTable<T, R> asDataTable() {
    return this;
  }

  @Override
  public HandlerRegistration addSelectionChangeHandler(final Handler handler) {
    return selectionModel != null ? selectionModel.addSelectionChangeHandler(handler) : null;
  }
}
