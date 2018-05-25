package nl.yogh.gwt.wui.widget.table;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;

import com.google.gwt.uibinder.client.UiChild;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RequiresResize;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SimpleKeyProvider;

import nl.yogh.gwt.wui.util.StyleUtil;

/**
 * Re-imagining and more practical but less functional implementation of the GWT CellTable.
 * <p>
 * <ul>
 * <li>Supports defining its columns and style in a UiBinder rather than code</li>
 * <li>Construction of cells through WidgetFactory, allows for many specialized cells needing no custom configuration</li>
 * <li>Parameterized in full; full control over objects yet minimally coupled</li>
 * <li>Fully extendible</li>
 * </ul>
 * <p>
 * NOTE TO DEVS WANTING TO TINKER AROUND IN HERE: Try to _avoid_ adding functionality in _this_ class. There's a ton of implementations depending on
 * its behavior and you cannot expect them to deal with changes in here. This class is meant to be a skeleton to which functionality may be added
 * _elsewhere_, this object is _designed_ to be extendible without friction, so look for your salvation in extending classes, or create your own
 * extending class doing something new. Not here. So no additional handlers, no additional behavior.
 * <p>
 * If objects are added to this table through setRowData(Collection&lt;T&gt;, false) (soft set), the table will attempt to replace/add/remove rows,
 * rather than throw everything out and start over. For this to work correctly, the equals() method of T _must_ be implemented correctly. Especially
 * if an extending DivTable adds behavior to rows/cells we don't know about here; they will keep a reference to the _wrong_ object if there's no
 * proper equals method in T and data is being replaced 'softly'.
 * <p>
 * Note that in some cases a soft (re)set is impossible because the equals() method has been spoiled; for example an object employs an ID internally
 * to determine equality while it has other fields that are utterly ignored. While this is not the correct way of determining object equality, it is
 * common practice, and it is incompatible with this DivTable. Do _not_ do soft sets if this is the case.
 *
 * @param <T>
 *          the type of object in the container
 * @param <R>
 *          the row type
 */
public abstract class DivTable<T, R extends DivTableRow> extends Composite implements RequiresResize, IsDataTable<T> {
  protected final ArrayList<WidgetFactory<T, ?>> columns = new ArrayList<WidgetFactory<T, ?>>();

  private final FlowPanel tablePanel = new FlowPanel();

  private String rowStyle;

  /**
   * Flag indicating whether this table is 'loading' when it's empty.
   */
  private boolean loadingByDefault;

  /**
   * Flag indicating whether this table shows the 'no content' widget by default.
   */
  private boolean noContentByDefault;

  /**
   * Content loading widget.
   */
  private Widget contentLoadingWidget;
  private Widget noContentWidget;
  private final SimplePanel headerContainer = new SimplePanel();

  private final FlowPanel contentPanel = new FlowPanel();
  protected final TypedFlowPanel<R> rows;

  private final SimplePanel auxiliary = new SimplePanel();
  private final SimplePanel footerContainer = new SimplePanel();

  /**
   * Base debug ID to be applied to all rows.
   */
  private String baseID;

  /**
   * List of decorators that have subscribed to doing things with rows when they are added.
   */
  private final HashSet<DivTableRowDecorator<T, R>> decorators = new HashSet<>();

  /**
   * KeyProvider to be used for object equality.
   */
  protected ProvidesKey<T> keyProvider;

  /**
   * Whether or not this DivTable is fitted with a key provider that offers safe equality determination.
   */
  protected boolean safeEquals;

  private RowReplacer<T, R> replacer;
  
  public interface RowReplacer<T, R> {
    void apply(T item, R row);
  }

  /**
   * Create a simple default DivTable.
   */
  public DivTable() {
    this(false);
  }

  /**
   * Creates a DivTable that's loading by default (or not).
   *
   * @param loadingByDefault
   *          Whether this widget shows 'loading' content if empty.
   */
  public DivTable(final boolean loadingByDefault) {
    this(new TypedFlowPanel<R>(), loadingByDefault);
  }

  /**
   * Create a DivTable with a custom row panel.
   *
   * @param rowPanel
   *          Custom row panel to use for rows.
   */
  public DivTable(final TypedFlowPanel<R> rowPanel) {
    this(rowPanel, false);
  }

  /**
   * Creates a DivTable that's loading by default (or not).
   *
   * @param rowPanel
   *          Custom row panel to use for rows.
   * @param loadingByDefault
   *          Whether this widget shows 'loading' content if empty.
   */
  public DivTable(final TypedFlowPanel<R> rowPanel, final boolean loadingByDefault) {
    this.rows = rowPanel;
    this.loadingByDefault = loadingByDefault;
    initWidget(tablePanel);

    headerContainer.setVisible(false);

    footerContainer.setVisible(false);

    contentPanel.add(rows);
    contentPanel.add(auxiliary);

    tablePanel.add(headerContainer);
    tablePanel.add(contentPanel);
    tablePanel.add(footerContainer);

    if (loadingByDefault) {
      showLoadingWidget();
    }

    keyProvider = new SimpleKeyProvider<>();
  }

  /**
   * Set the style name for the data panel.
   *
   * @param contentStyle
   *          Style name to set.
   */
  public void setContentStyle(final String contentStyle) {
    contentPanel.setStyleName(contentStyle);
  }

  /**
   * Set the style name for the data panel.
   *
   * @param dataStyle
   *          Style name to set.
   */
  public void setDataStyle(final String dataStyle) {
    rows.asWidget().setStyleName(dataStyle);
  }

  public void setRowStyle(final String rowStyle) {
    this.rowStyle = rowStyle;
  }

  /**
   * Use this to specify a custom header.
   *
   * @param header
   *          Header widget to insert.
   */
  @UiChild(tagname = "header")
  public void setHeader(final Widget header) {
    headerContainer.setWidget(header);
    headerContainer.setVisible(header != null);
  }

  /**
   * Whether the header container is visible.
   *
   * @param visible
   */
  public void setHeaderVisible(final boolean visible) {
    headerContainer.setVisible(visible);
  }

  /**
   * Use this to specify a custom footer.
   *
   * @param footer
   *          Footer widget to insert.
   */
  @UiChild(tagname = "footer")
  public void setFooter(final Widget footer) {
    footerContainer.setWidget(footer);
    footerContainer.setVisible(footer != null);
  }

  /**
   * Whether the footer container is visible.
   *
   * @param visible
   */
  public void setFooterVisible(final boolean visible) {
    footerContainer.setVisible(visible);
  }

  /**
   * Add a column to the table.
   *
   * @param index
   *          index to add the column at.
   * @param column
   *          Typed {@link WidgetFactory} to add.
   */
  public void insertColumn(final int index, final WidgetFactory<T, ?> column) {
    columns.add(index, column);
  }

  /**
   * Add a column to the table.
   *
   * @param index
   *          index to add the column at.
   * @param column
   *          Typed {@link WidgetFactory} to add.
   * @param cellStyles
   *          The column's cell styles.
   */
  public void insertColumn(final int index, final WidgetFactory<T, ?> column, final String... cellStyles) {
    if (cellStyles.length != 0) {
      column.setCellStyle(StyleUtil.joinStyles(cellStyles));
    }

    columns.add(index, column);
  }

  /**
   * Add a column to the table.
   *
   * @param column
   *          Typed {@link WidgetFactory} to add.
   */
  @UiChild
  public void addColumn(final WidgetFactory<T, ?> column) {
    columns.add(column);
  }

  /**
   * Remove the given column. Only removes the factory, if the table is generated before this method is called, the column will remain.
   *
   * @param column
   *          Column to remove.
   */
  public void removeColumn(final WidgetFactory<T, ?> column) {
    columns.remove(column);
  }

  /**
   * Add a column to the table and apply the given style.
   *
   * @param column
   *          Typed {@link WidgetFactory} to add.
   * @param cellStyles
   *          The column's cell styles.
   */
  public void addColumn(final WidgetFactory<T, ?> column, final String... cellStyles) {
    if (cellStyles.length != 0) {
      column.setCellStyle(StyleUtil.joinStyles(cellStyles));
    }

    columns.add(column);
  }

  /**
   * Add a row decorator for this table.
   *
   * @param decorator
   *          Decorator to add.
   */
  public void addRowDecorator(final DivTableRowDecorator<T, R> decorator) {
    decorators.add(decorator);
  }

  /**
   * Remove a row decorator for this table.
   *
   * @param decorator
   *          Decorator to remove.
   */
  public void removeRowDecorator(final DivTableRowDecorator<T, R> decorator) {
    decorators.remove(decorator);
  }

  /**
   * Set the row data for this {@link DivTable}.
   *
   * <p>
   * Clears previous values.
   *
   * @param lst
   *          Row data to set.
   */
  public void setRowData(final Collection<T> lst) {
    setRowData(lst, true);
  }

  /**
   * Set the row data for this {@link DivTable}.
   *
   * <p>
   * Clears previous values.
   *
   * @param lst
   *          Row data to set.
   * @param fireEvent
   *          Whether to fire an event afterwards
   */
  public void setRowData(final Collection<T> lst, final boolean fireEvent) {
    // If the list is null or empty, clear the contents and bug out.
    if (lst == null || lst.isEmpty()) {
      clear();
      hideLoadingContent();

      if (noContentByDefault) {
        showNoContent();
      }

      return;
    } else {
      hideNoContent();
    }

    // TODO Make replacement possible safely, using the keyprovider and adapting DTR to implement HasValue it should
    // be possible to replace rows. As a consequence, IDT needs to stop using HasValueChangeHandler<Boolean> on top
    // of its rows to handle row selections, moving to some custom thing instead (DTRs would be implementing HasValue<T>
    // instead of HasValue<Boolean>)
    if (!safeEquals) {
      // Clear previous values.
      clear();
      // Add the row data.
      addRowData(lst);
    } else {
      // // Replace the row data, adding/replacing only if it doesn't already exist
      replaceRowData(lst);
    }
  }

  private void replaceRowData(Collection<T> lst) {
    Iterator<T> it = lst.iterator();
    int i = 0;
    for (; i < rows.size(); i++) {
      T item = it.next();

      R row = rows.getWidget(i);

      replaceRow(row, item);
    }
    
    addRowData(lst.stream().skip(i).collect(Collectors.toList()));
  }

  private void replaceRow(R row, T item) {
    replacer.apply(item, row);
  }

  public void hideNoContent() {
    auxiliary.setVisible(false);
    if (noContentWidget != null) {
      noContentWidget.removeFromParent();
    }
  }

  public void showNoContent() {
    auxiliary.setVisible(true);
    auxiliary.setWidget(noContentWidget);
  }

  /**
   * @return The number of elements in this data set.
   */
  public int size() {
    return rows.size();
  }

  /**
   * Let a {@link GridVisitor} visit this {@link DivTable} for added interactivity.
   *
   * @param visitor
   *          Visitor to whom table values will be exposed.
   */
  public void exposeToVisitor(final GridVisitor visitor) {
    visitor.exposeRowPanel(contentPanel);
    visitor.exposeDataTable(this);
  }

  public boolean isLoadingByDefault() {
    return loadingByDefault;
  }

  /**
   * Set whether or not content is loading by default until row data is added.
   *
   * @param loadingByDefault
   *          Whether to load by default.
   */
  public void setLoadingByDefault(final boolean loadingByDefault) {
    if (!loadingByDefault && contentLoadingWidget.isAttached()) {
      hideLoadingContent();
    }

    this.loadingByDefault = loadingByDefault;

    if (loadingByDefault && rows.isEmpty()) {
      showLoadingWidget();
    }
  }

  /**
   * Set whether or not to show the 'no content' widget if a data set is added that is empty.
   *
   * @param noContentByDefault
   *          Whether to show 'no content'
   */
  public void setNoContentByDefault(final boolean noContentByDefault) {
    this.noContentByDefault = noContentByDefault;
  }

  /**
   * Show or hide the content loading widget.
   *
   * @param show
   *          Whether to show or hide.
   */
  public final void showLoadingWidget(final boolean show) {
    if (show) {
      showLoadingWidget();
    } else {
      hideLoadingContent();
    }
  }

  /**
   * Set the Widget that is displayed when content is empty.
   *
   * @param noContentWidget
   *          'No content' widget to display
   */
  @UiChild(tagname = "nocontent")
  public void setNoContentWidget(final IsWidget noContentWidget) {
    this.noContentWidget = noContentWidget.asWidget();
  }

  /**
   * Set the Widget that is displayed when content is loading.
   *
   * @param contentLoadingWidget
   *          Loading widget to display
   */
  public void setContentLoadingWidget(final Widget contentLoadingWidget) {
    if (this.contentLoadingWidget.isAttached()) {
      hideLoadingContent();
    }

    this.contentLoadingWidget = contentLoadingWidget;

    if (loadingByDefault && rows.isEmpty()) {
      showLoadingWidget();
    }
  }

  /**
   * Show the content loading widget.
   */
  public final void showLoadingWidget() {
    auxiliary.setVisible(true);
    auxiliary.setWidget(contentLoadingWidget);
  }

  /**
   * Hide the content loading widget.
   */
  public final void hideLoadingContent() {
    auxiliary.setVisible(false);
    contentLoadingWidget.removeFromParent();
  }

  @Override
  public void setPixelSize(final int width, final int height) {}

  /**
   * Clear the data in this table.
   */
  public void clear() {
    rows.clear();

    if (isLoadingByDefault()) {
      showLoadingWidget();
    }
  }

  /**
   * Clear the column definitions from this table.
   */
  public void clearColumns() {
    columns.clear();
  }

  /**
   * Add row data to the table.
   *
   * <p>
   * Does not clear previous values and appends them to the end.
   *
   * @param lst
   *          Row data to add.
   */
  public void addRowData(final Collection<T> lst) {
    if (isLoadingByDefault()) {
      hideLoadingContent();
    }

    constructRows(lst);
  }

  /**
   * Add a single row to the table.
   *
   * <p>
   * Does not clear previous values and appends them to the end.
   *
   * @param obj
   *          Row data to add
   */
  public void addRowData(final T obj) {
    if (isLoadingByDefault()) {
      hideLoadingContent();
    }

    constructRow(obj);
  }

  @Override
  public void onResize() {
    forceLayout();
  }

  @Override
  public DivTable<T, R> asDataTable() {
    return this;
  }

  public void setHeaderStyle(final String headerStyle) {
    headerContainer.setStyleName(headerStyle);
  }

  public void setFooterStyle(final String footerStyle) {
    footerContainer.setStyleName(footerStyle);
  }

  public void setKeyProvider(final ProvidesKey<T> keyProvider) {
    setKeyProvider(keyProvider, true);
  }

  public void setKeyProvider(final ProvidesKey<T> keyProvider, final boolean safe) {
    this.safeEquals = safe;
    this.keyProvider = keyProvider;
  }

  protected R createRow(final T object) {
    final R comp = createDivTableRow(object);

    createRow(comp, object);

    return comp;
  }

  public void setReplacementFunction(RowReplacer<T, R> replacer) {
    this.replacer = replacer;
  }

  protected abstract R createDivTableRow(T object);

  /**
   * Create a single row.
   *
   * @param object
   *          Object to create the row from.
   *
   * @return A widget that contains the row.
   */
  protected void createRow(final DivTableRow rowContainer, final T object) {
    for (final WidgetFactory<T, ?> column : columns) {
      final IsWidget widget = column.createWidget(rowContainer.asWidget(), object);

      final Widget container = column.wrapWidget(widget == null ? null : widget.asWidget());

      column.applyCellOptions(container, object);
      rowContainer.add(container);
    }
  }

  private void applyRowOptions(final R rowContainer, final T item) {
    for (final DivTableRowDecorator<T, R> decorator : decorators) {
      decorator.applyRowOptions(rowContainer, item);
    }
  }

  protected void forceLayout() {
    setPixelSize(getElement().getOffsetWidth(), Math.max(0,
        getElement().getParentElement().getOffsetHeight() - (getElement().getAbsoluteTop() - getElement().getParentElement().getAbsoluteTop())));
  }

  @Override
  protected void onEnsureDebugId(final String baseID) {
    this.baseID = baseID;
  }

  private void addRow(final R row) {
    addRow(row, rows.size());
  }

  private void addRow(final R row, final int index) {
    // Apply a style, if any.
    if (rowStyle != null) {
      row.asWidget().setStyleName(rowStyle, true);
    }

    rows.insert(row, index);

    if (baseID != null) {
      UIObject.ensureDebugId(row.asWidget().getElement(), baseID, String.valueOf(index));
    }
  }

  /**
   * Construct all rows
   */
  private void constructRows(final Collection<T> lst) {
    // Construct the data rows
    for (final T object : lst) {
      constructRow(object);
    }
  }

  private boolean constructRow(final T val) {
    final R row = createRow(val);

    // Apply row options
    applyRowOptions(row, val);

    if (row == null) {
      return false;
    }

    addRow(row);

    return true;
  }
}
