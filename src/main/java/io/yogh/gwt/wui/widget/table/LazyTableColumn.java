package io.yogh.gwt.wui.widget.table;

import java.util.Collection;

import com.google.gwt.user.client.rpc.AsyncCallback;

import io.yogh.gwt.wui.service.AggregatedAsyncCallback;

/**
 * Defines a column that is an inner table.
 * 
 * @param <C>
 *          The Collection type
 * @param <T>
 *          Inner content type
 * @param <E>
 *          Outer content (row) type
 * @param <W>
 *          Type of the table
 */
public abstract class LazyTableColumn<C extends Collection<T>, T, E, W extends IsDataTable<T>> extends TableColumn<T, E, W> {
  private AsyncCallback<C> callback;

  public LazyTableColumn() {
    this(null);
  }

  public LazyTableColumn(final AsyncCallback<C> callback) {
    this.callback = callback;
  }

  @Override
  public W createWidget(final E object) {
    final W table = createDataTable(object);

    table.asDataTable().setLoadingByDefault(true);
    configureDataTable(table);

    if (isLazyCall(object)) {
      final AggregatedAsyncCallback<C> aggregatedCallback = new AggregatedAsyncCallback<>();

      if (callback != null) {
        aggregatedCallback.addLastListener(callback);
      }

      aggregatedCallback.addListener(new AsyncCallback<C>() {
        @Override
        public void onSuccess(final C result) {
          table.asDataTable().setRowData(result);
        }

        @Override
        public void onFailure(final Throwable caught) {
          // Do nothing, provider should/might handle this.
        }
      });

      doLazyCall(object, aggregatedCallback);
    } else {
      table.asDataTable().setRowData(getRowData(object));
    }

    return table;
  }

  /**
   * Default to true, always do a lazy call.
   * 
   * @param object
   *          The object for which to do a lazy call.
   * @return Whether to do a lazy call.
   */
  protected boolean isLazyCall(final E object) {
    return true;
  }

  protected abstract void doLazyCall(E origin, final AsyncCallback<C> callback);

  @Override
  public C getRowData(final E object) {
    return null;
  }
}
