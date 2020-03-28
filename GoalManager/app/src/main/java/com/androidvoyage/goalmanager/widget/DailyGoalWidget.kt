package com.androidvoyage.goalmanager.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.database.GoalRepository


/**
 * Implementation of App Widget functionality.
 */
class DailyGoalWidget : AppWidgetProvider() {

    companion object {
        const val ACTION_WIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"
        const val EXTRA_CLICKED_FILE = "EXTRA_CLICKED_FILE"
        const val REFRESH_WIDGET_ACTION = "REFRESH_WIDGET_ACTION"
        const val LIST_ITEM_CLICKED_ACTION = "LIST_ITEM_CLICKED_ACTION"
    }


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

            // Setup refresh button:
            val refreshIntent =
                Intent(context, DailyGoalWidget::class.java)
            refreshIntent.action = REFRESH_WIDGET_ACTION
            refreshIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            refreshIntent.data = Uri.parse(refreshIntent.toUri(Intent.URI_INTENT_SCHEME))
            val refreshPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                refreshIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            rv.setOnClickPendingIntent(R.id.ivRefresh, refreshPendingIntent)


            val toastIntent =
                Intent(context, DailyGoalWidget::class.java)
            toastIntent.action = LIST_ITEM_CLICKED_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME))
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            rv.setPendingIntentTemplate(R.id.rvWidget, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, rv)
        }
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
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

            REFRESH_WIDGET_ACTION -> {
                val appWidgetId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID
                )
                AppWidgetManager.getInstance(context)
                    .notifyAppWidgetViewDataChanged(appWidgetId, R.id.rvWidget)
            }

            LIST_ITEM_CLICKED_ACTION -> {

                val clickedId =
                    intent.getIntExtra(EXTRA_CLICKED_FILE, -1)

                val goalRepository = GoalRepository()
                goalRepository.GoalRepository(context!!)
                val goal = goalRepository.getGoalById(clickedId)
                goal.isCompleted = !goal.isCompleted
                goalRepository.insertTask(goal)
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
