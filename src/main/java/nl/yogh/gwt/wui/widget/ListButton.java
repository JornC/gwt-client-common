package nl.yogh.gwt.wui.widget;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class ListButton<T> extends Composite implements HasValueChangeHandlers<T> {
  private static final String NULL_VALUE = "null";

  private final ListBox listBox;

  private Function<T, String> keyHandler;
  private Function<T, String> textHandler;

  private final HashMap<String, T> dictionary = new HashMap<>();

  public ListButton() {
    listBox = new ListBox();
    clear();
    listBox.addChangeHandler(e -> onChange());
    initWidget(listBox);
  }

  public ListButton(final Function<T, String> keyHandler) {
    this();
    this.setKeyHandler(keyHandler);
  }

  public ListButton(final Function<T, String> keyHandler, final Function<T, String> textHandler) {
    this();
    this.setKeyHandler(keyHandler);
    this.setTextHandler(textHandler);
  }

  public void addItems(final Collection<T> collection) {
    collection.forEach(this::addItem);
  }

  public void addItem(final T value) {
    addItem(keyHandler.apply(value), textHandler.apply(value), value);
  }

  public void addItem(final String key, final T value) {
    addItem(key, textHandler.apply(value), value);
  }

  public void addItem(final String key, final String text, final T value) {
    listBox.addItem(text, key);
    dictionary.put(key, value);

    if (listBox.getSelectedIndex() == -1) {
      listBox.setSelectedIndex(0);
    }
  }

  public void clear() {
    dictionary.clear();
    listBox.clear();
    listBox.addItem("Select..", NULL_VALUE);
  }

  public int size() {
    return dictionary.size();
  }

  public boolean isEmpty() {
    return dictionary.isEmpty();
  }

  public void onChange() {
    final String selectedValue = listBox.getSelectedValue();
    GWT.log(selectedValue);

    if (!NULL_VALUE.equals(selectedValue)) {
      ValueChangeEvent.fire(this, dictionary.get(selectedValue));
    }
  }

  @Override
  public HandlerRegistration addValueChangeHandler(final ValueChangeHandler<T> handler) {
    return addHandler(handler, ValueChangeEvent.getType());
  }

  public void setKeyHandler(final Function<T, String> keyHandler) {
    this.keyHandler = keyHandler;
  }

  public void setTextHandler(final Function<T, String> textHandler) {
    this.textHandler = textHandler;
  }
}
