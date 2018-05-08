package nl.yogh.gwt.wui.widget.table;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * A widget which displays content for a given widget.
 *
 * @param <T> The object to display context for.
 * 
 * TODO: Get setArrowTop out of here and replace this with WidgetFactory.
 */
public interface ContextualContent<T> extends IsWidget {
  /**
   * Show context for the given object.
   * 
   * @param obj Object to show context for.
   * 
   * @return Whether context is being shown.
   */
  boolean view(T obj);

  /**
   * TODO Get the arrow out of here. ContextDivTable should take care of this.
   */
  void setArrowTop(int top);
}
