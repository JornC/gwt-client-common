package io.yogh.gwt.wui.place;

import com.google.inject.ImplementedBy;

@ImplementedBy(PlaceControllerImpl.class)
public interface PlaceController {

  ApplicationPlace getPreviousPlace();

  ApplicationPlace getPlace();

  void goTo(ApplicationPlace place, boolean silent);

  void goTo(ApplicationPlace place);

}
