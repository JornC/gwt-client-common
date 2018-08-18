package io.yogh.gwt.wui.activity;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import io.yogh.gwt.wui.command.HasCommandRouter;
import io.yogh.gwt.wui.command.PlaceChangeCommand;
import io.yogh.gwt.wui.place.ApplicationPlace;
import io.yogh.gwt.wui.place.PlaceController;
import io.yogh.gwt.wui.widget.HasEventBus;

public class ActivityManagerImpl implements ActivityManager {
  private final ActivityManagerImplEventBinder EVENT_BINDER = GWT.create(ActivityManagerImplEventBinder.class);

  interface ActivityManagerImplEventBinder extends EventBinder<ActivityManagerImpl> {}

  private final ActivityMapper mapper;
  private final PlaceController placeController;

  private AcceptsOneWidget panel;

  private final ResettableEventBus activityEventBus;

  private Activity<?> currentActivity;

  @Inject
  public ActivityManagerImpl(final EventBus globalEventBus, final PlaceController placeController, final ActivityMapper mapper) {
    this.placeController = placeController;
    this.mapper = mapper;

    activityEventBus = new ResettableEventBus(globalEventBus);

    EVENT_BINDER.bindEventHandlers(this, globalEventBus);
  }

  @Override
  public void setPanel(final AcceptsOneWidget panel) {
    this.panel = panel;
  }

  @EventHandler
  public void onPlaceChangeCommand(final PlaceChangeCommand e) {
    final ApplicationPlace previousPlace = placeController.getPreviousPlace();
    final ApplicationPlace place = e.getValue();

    if (previousPlace != null && previousPlace.getClass().equals(place.getClass())) {
      return;
    }

    // Suspend previous activity
    suspendActivity(currentActivity);

    // Remove event handlers
    activityEventBus.removeHandlers();

    // Start next activity
    final Activity<?> activity = mapper.getActivity(place);
    if (activity instanceof HasEventBus) {
      ((HasEventBus) activity).setEventBus(activityEventBus);
    }

    currentActivity = activity;

    activity.onStart(panel);

    if (activity instanceof HasCommandRouter) {
      ((HasCommandRouter) activity).onStart();
    }
  }

  private static void suspendActivity(final Activity<?> currentActivity) {
    if (currentActivity == null) {
      return;
    }

    final String stop = currentActivity.mayStop();
    if (stop != null) {
      Window.alert(stop);
      return;
    }

    currentActivity.onStop();
  }
}
