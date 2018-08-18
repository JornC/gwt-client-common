package io.yogh.gwt.wui.widget;

import java.util.function.Consumer;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class FlipButton extends Composite implements ClickHandler {
  public static final double NORTH = -90D;
  public static final double SOUTH = 90D;
  public static final double WEST = 180D;
  public static final double EAST = 0D;

  private static final FlipButtonUiBinder UI_BINDER = GWT.create(FlipButtonUiBinder.class);

  interface FlipButtonUiBinder extends UiBinder<Widget, FlipButton> {}

  private boolean closed;
  private Consumer<Boolean> callback;

  private double closedRotation = NORTH;
  private double openRotation = SOUTH;

  public FlipButton() {
    initWidget(UI_BINDER.createAndBindUi(this));

    addDomHandler(this, ClickEvent.getType());
  }

  public FlipButton(final Consumer<Boolean> callback) {
    this();

    this.callback = callback;
  }

  public void init() {
    init(true);
  }

  public void init(final boolean initial) {
    setState(initial);
  }

  public void setState(final boolean close) {
    if (close) {
      orientClosed();
    } else {
      orientOpen();
    }

    closed = close;
    if (callback != null) {
      callback.accept(!close);
    }
  }

  private void orientClosed() {
    setRotation(closedRotation);
  }

  private void orientOpen() {
    setRotation(openRotation);
  }

  private void setRotation(final double rotation) {
    getElement().getStyle()
        .setProperty("transform", "rotate(" + rotation + "deg)");
  }

  @Override
  public void onClick(final ClickEvent event) {
    setState(!closed);
  }

  public void setClosedRotation(final double closedRotation) {
    this.closedRotation = closedRotation;
  }

  public void setOpenRotation(final double openRotation) {
    this.openRotation = openRotation;
  }

  public boolean isClosed() {
    return closed;
  }
}