package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.HasName;

public class NameColumn extends TextColumn<HasName> {
  @Override
  public String getValue(final HasName object) {
    return object.getName();
  }
}
