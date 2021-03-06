package view_inspector.util;

import android.support.v7.internal.widget.ActionBarOverlayLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import view_inspector.ViewInspector;

public final class ViewUtil {

  public static String getViewId(View view) {
    return view.getId() > 0 ? view.getResources().getResourceName(view.getId()) : "";
  }

  public static String getSimpleViewId(View view) {
    String viewId = getViewId(view);
    if (viewId.length() > 0 && !viewId.startsWith("android")) viewId = viewId.split(":")[1];
    return viewId;
  }

  public static String getClassName(View view) {
    if (view.getClass().getSimpleName().contains("$")) { // build-time proxy case
      String className = view.getClass().getSimpleName().split("\\$")[1];
      String[] strArray = className.split("_");
      className = strArray[strArray.length - 1];
      return className;
    } else { // runtime-time proxy case
      return view.getClass().getSimpleName().split("_")[0];
    }
  }

  public static int getViewLevel(View view) {
    int level = 0;
    ViewParent parent = view.getParent();
    while (parent != null && !parent.toString().contains("android.view.ViewRootImpl")) {
      parent = parent.getParent();
      level++;
    }
    return level;
  }

  public static boolean isNotSupportedViewClass(View view) {
    return view instanceof ActionBarOverlayLayout;
  }

  public static boolean isLevelTwoView(View view) {
    return getViewLevel(view) == 2;
  }

  public static boolean isViewRoot(View view) {
    return view.equals(ViewInspector.viewRoot);
  }

  private ViewUtil() {
    throw new AssertionError("No instances.");
  }
}
