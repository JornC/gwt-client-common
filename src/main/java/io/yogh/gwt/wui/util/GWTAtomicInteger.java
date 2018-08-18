package io.yogh.gwt.wui.util;

/**
 * Simple wrapper around an int to mimic AtomicInteger behavior.
 * 
 * Not actually synchronized - meant for GWT which runs in 1 thread and
 * does not have concurrency issues.
 */
public class GWTAtomicInteger {
  private int value;

  /**
   * Initialize the atomic integer with the default value (0).
   */
  public GWTAtomicInteger() {
    this(0);
  }

  /**
   * Initialize the atomic integer with the given value.
   * 
   * @param initialValue The initial value.
   */
  public GWTAtomicInteger(final int initialValue) {
    value = initialValue;
  }

  /**
   * Increment the value and return the new value.
   * 
   * @return Incremented value
   */
  public int incrementAndGet() {
    return ++value;
  }

  /**
   * Return the value and increment it.
   * 
   * @return The value before incrementing.
   */
  public int getAndIncrement() {
    return value++;
  }

  /**
   * Get the integer's value.
   * 
   * @return the integer's value
   */
  public int get() {
    return value;
  }

  /**
   * Set the integer's value.
   * 
   * @param newValue Value to set.
   */
  public void set(final int newValue) {
    value = newValue;
  }

  /**
   * Return the current value and set a new value, replacing the old value.
   * @param newValue Value to set
   * @return Old value
   */
  public int getAndSet(final int newValue) {
    final int oldValue = value;
    value = newValue;
    return oldValue;
  }
}