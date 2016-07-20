package com.example.castiel.clashofclanmulti.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.castiel.clashofclanmulti.Load_CoC;

import com.example.castiel.clashofclanmulti.clashofclanmulti.Control;

/**
 * Created by castiel on 7/5/16.
 */
public class widgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            // Intent intent = new Intent(context, MainActivity.class);
            // PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            String account = Control.getWidget(context, appWidgetId);
                    //widget_config.getNickName(context,appWidgetId);
            this.onUpdateByConfig(context, appWidgetManager, appWidgetId, account);
        }
        //super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        int N = appWidgetIds.length;
        for ( int i = 0; i<N; i++ ){
            int appWidgetId = appWidgetIds[i];
            Control.deleteWidget(context, appWidgetId);
        }
    }


    // Tu viet
    public static void onUpdateByConfig(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String account) {

        RemoteViews views = new RemoteViews(context.getPackageName(), com.castiel.clashofclanmulti.R.layout.widget);

        views.setTextViewText(com.castiel.clashofclanmulti.R.id.widget_label, Control.getAccountName(context,account));
        Log.d(widgetProvider.class.getName(), appWidgetId + ":" + account + ":" + Control.getAccountName(context,account));
        Intent intent = new Intent(context, Load_CoC.class);
        intent.putExtra(Control.account,account);
        //intent.getExtras().putString(Control.account, account);
        PendingIntent pi = PendingIntent.getActivity(context, appWidgetId, intent,  PendingIntent.FLAG_UPDATE_CURRENT );

        views.setOnClickPendingIntent(com.castiel.clashofclanmulti.R.id.widget_icon,pi);
        //views.setOnClickFillInIntent();

        // Tell the AppWidgetManager to perform an update on the current app com.example.castiel.com.example.castiel.clashofclanmulti.clashofclanmulti.widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

}
