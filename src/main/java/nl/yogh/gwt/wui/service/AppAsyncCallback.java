package nl.yogh.gwt.wui.service;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * This is an abstract implementation of AsyncCallback which implements the
 * onFailure method.
 *
 *@param <T> The type of the return value that was declared in the synchronous
 *          version of the method. If the return type is a primitive, use the
 *          boxed version of that primitive (for example, an <code>int</code>
 *          return type becomes an {@link Integer} type argument, and a
 *          <code>void</code> return type becomes a {@link Void} type
 *          argument, which is always <code>null</code>).
 */
public abstract class AppAsyncCallback<T> implements AsyncCallback<T> {

  /**
   * NOTE: If you override this method put A super.onFailure as the LAST 
   * statement in the method because this method will throw a RuntimeException
   * and therefore you code after the super call won't be executed.
   * 
   * Throws a {@link RuntimeException} with the passed throwable. 
   * 
   * @param caught actual exception returned from the server
   */
  @Override
  public void onFailure(final Throwable caught) {
    throw new RuntimeException(caught);
  }
}