package nl.yogh.gwt.wui.activity;

import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityManagerImpl.class)
public interface ActivityManager {
  void setPanel(AcceptsOneWidget panel);
}
