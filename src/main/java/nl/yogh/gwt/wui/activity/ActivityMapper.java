package nl.yogh.gwt.wui.activity;

import nl.yogh.gwt.wui.place.Place;

/**
 * Finds the activity to run for a given {@link Place}, used to configure an {@link ActivityManager}.
 */
public interface ActivityMapper {
  /**
   * Returns the activity to run for the given {@link Place}, or null.
   *
   * @param place a Place object
   */
  Activity<?> getActivity(Place place);
}
