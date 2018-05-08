package nl.yogh.gwt.wui.widget.table;

import java.util.HashSet;

import com.google.gwt.view.client.SingleSelectionModel;

public class SelectionModelConnector<T> {
  private final HashSet<IsInteractiveDataTable<T>> tables = new HashSet<>();

  private final HashSet<IsInteractiveDataTable<T>> ignore = new HashSet<>();

  public void connect(final IsInteractiveDataTable<T> model) {
    tables.add(model);

    model.asDataTable().addSelectionChangeHandler(event -> {
      if (ignore.contains(model)) {
        ignore.remove(model);
      } else {
        setSelection(model);
      }
    });
  }

  private void setSelection(final IsInteractiveDataTable<T> source) {
    final T selection = ((SingleSelectionModel<T>) source.asDataTable().getSelectionModel()).getSelectedObject();

    for (final IsInteractiveDataTable<T> model : tables) {
      if (model.equals(source)) {
        continue;
      }

      ignore.add(model);
      model.asDataTable().setSelectedItem(selection);
    }
  }
}
