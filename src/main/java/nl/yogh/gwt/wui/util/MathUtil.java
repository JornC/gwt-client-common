package nl.yogh.gwt.wui.util;

/**
 * Math class specific for GWT.
 */
public final class MathUtil {

  private static final double ROUND_UP = 0.5;

  private MathUtil() {
    // util class.
  }

  /**
   * Round a double to an int. Not using standard Math.round because that returns a long and long is not nicely supported in JavaScript.
   * @param value value to round
   * @return value rounded to int
   */
  public static int round(final double value) {
    return (int) (value + (value < 0 ? -ROUND_UP : ROUND_UP));
  }
}