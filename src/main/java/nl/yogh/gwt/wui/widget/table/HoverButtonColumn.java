package nl.yogh.gwt.wui.widget.table;

import com.google.gwt.dom.client.Style.Visibility;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;

public abstract class HoverButtonColumn<T> extends WidgetFactory<T, Button> {
  @Override
  public Button createWidget(final T object) {
    final Button button = new Button();
    button.addClickHandler(event -> {
      event.stopPropagation();
      HoverButtonColumn.this.onClick(button, object);
    });

    return button;
  }

  /**
   * Creates a widget.
   *
   * @param rowContainer
   *          Row container the cell will be stuck in.
   * @param object
   *          Value this widget will be generated with.
   *
   * @return A widget.
   */
  @Override
  public final Button createWidget(final Widget rowContainer, final T object) {
    final Button widget = createWidget(object);

    if (widget != null) {
      applyRowOptions(rowContainer, widget);
    }

    return widget;
  }

  protected abstract void onClick(Button button, final T object);

  @Override
  public void applyRowOptions(final Widget rowContainer, final Button button) {
    button.getElement().getStyle().setVisibility(Visibility.HIDDEN);

    rowContainer.addDomHandler(event -> button.getElement().getStyle().setVisibility(Visibility.VISIBLE), MouseOverEvent.getType());
    rowContainer.addDomHandler(event -> button.getElement().getStyle().setVisibility(Visibility.HIDDEN), MouseOutEvent.getType());
  }
}
