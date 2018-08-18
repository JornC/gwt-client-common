package io.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple widget factory which can generate a widget out of some object type.
 *
 * @param <L> Type of object to generate a widget out of.
 * @param <W> the widget type to return
 */
public abstract class WidgetFactory<L, W extends IsWidget> {
  private String debugId;
  private String cellStyle;

  public void setCellStyle(final String style) {
    this.cellStyle = style;
  }

  /**
   * Creates a widget.
   *
   * @param rowContainer Row container the cell will be stuck in.
   * @param object Value this widget will be generated with.
   *
   * @return A widget.
   */
  public W createWidget(final Widget rowContainer, final L object) {
    final W widget = createWidget(object);

    applyRowOptions(rowContainer, widget);

    return widget;
  }

  /**
   * @param object Value this widget will be generated with.
   * @return A widget.
   */
  public abstract W createWidget(final L object);

  /**
   * Apply row options to a created widget. Does nothing by default.
   *
   * @param rowContainer Row container.
   * @param widget Created widget.
   */
  public void applyRowOptions(final Widget rowContainer, final W widget) {
    // No-op by default
  }

  public Widget wrapWidget(final Widget widget) {
    return widget == null ? new SimplePanel() : widget;
  }

  /**
   * Apply cell options to a created widget. Adds a debug ID and/or style, if any by default.
   *
   * May be overridden to do something more complicated (be sure to call .super)
   *
   * @param cell Cell container to apply options to.
   * @param object Object contained in the cell.
   */
  public void applyCellOptions(final Widget cell, final L object) {
    if (cell == null) {
      return;
    }

    if (debugId != null) {
      cell.ensureDebugId(debugId);
    }

    if (cellStyle != null) {
      cell.addStyleName(cellStyle);
    }
  }

  /**
   * Ensure passing a debugID to created widgets.
   *
   * @param debugId ID to pass.
   */
  public void ensureDebugId(final String debugId) {
    this.debugId = debugId;
  }
}
