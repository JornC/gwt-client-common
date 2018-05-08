package nl.yogh.gwt.wui.widget.table;

public class TextValueColumn extends TextColumn<DivTableKeyValue<String, String>> {
  @Override
  public String getValue(final DivTableKeyValue<String, String> object) {
    return object.getValue();
  }
}
