package nl.yogh.gwt.wui.place;

import java.util.Map;

public abstract class ParameteredTokenizer<P extends ApplicationPlace> extends AbstractTokenizer {
  /**
   * Returns a new instance of the place.
   *
   * @return new place instance
   */
  protected abstract P createPlace();

  /**
   * In the implementation place any additional key values present in the tokens in the given place object and
   *
   * @param tokens
   * @return
   */
  protected abstract void updatePlace(Map<String, String> tokens, P place);

  /**
   * In the implementation add additional key/values from the place into the token map.
   *
   * @param place place object
   * @param tokens token map
   */
  protected abstract void setTokenMap(P place, Map<String, String> tokens);
}
