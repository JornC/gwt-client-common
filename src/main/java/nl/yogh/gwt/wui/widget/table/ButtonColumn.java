package nl.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public abstract class ButtonColumn<L> extends SimpleWidgetFactory<L> {

  @Override
  public Widget createWidget(final L object) {
    final Button button = new Button(getButtonText(object));

    button.addClickHandler(event -> {
      event.stopPropagation();
      ButtonColumn.this.onClick(object);
    });

    return button;
  }

  protected abstract String getButtonText(L object);

  protected abstract void onClick(final L object);

}
