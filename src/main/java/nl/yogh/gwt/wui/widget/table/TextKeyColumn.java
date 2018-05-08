package nl.yogh.gwt.wui.widget.table;

public class TextKeyColumn extends TextColumn<DivTableKeyValue<String, String>> {
  @Override
  public String getValue(final DivTableKeyValue<String, String> object) {
    return object.getKey();
  }
}
