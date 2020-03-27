package com.androidvoyage.goalmanager.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.androidvoyage.goalmanager.R

/**
 * Implementation of App Widget functionality.
 */
class DailyGoalWidget : AppWidgetProvider() {
    private val ACTION_WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"


    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            val rv = RemoteViews(context.packageName, R.layout.daily_goal_widget)
            rv.setEmptyView(R.id.rvWidget, R.id.tvNoGoals)
            val intent = Intent(context, GoalWidgetServices::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            rv.setRemoteAdapter(R.id.rvWidget, intent)
            appWidgetManager.updateAppWidget(appWidgetId, rv)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        when (intent!!.action) {

            ACTION_WIDGET_UPDATE -> {
                val appWidgetManager = AppWidgetManager.getInstance(context)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(
                    ComponentName(
                        context!!,
                        DailyGoalWidget::class.java
                    )
                )
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.rvWidget)
            }
        }
    }
}
