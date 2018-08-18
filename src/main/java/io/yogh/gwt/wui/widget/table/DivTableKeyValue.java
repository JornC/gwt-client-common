package io.yogh.gwt.wui.widget.table;

public class DivTableKeyValue<K, V> {
  private K key;
  private V value;

  public DivTableKeyValue() {}

  public DivTableKeyValue(final K key, final V value) {
    this.key = key;
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public void setKey(final K key) {
    this.key = key;
  }

  public V getValue() {
    return value;
  }

  public void setValue(final V value) {
    this.value = value;
  }
}
