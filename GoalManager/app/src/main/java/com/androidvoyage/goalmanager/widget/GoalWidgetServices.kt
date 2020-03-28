package com.androidvoyage.goalmanager.widget

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.database.GoalRepository
import java.util.*


class GoalWidgetServices() : RemoteViewsService() {

    class WidgetItem(
        val id:Int,val goal: String, val isCompleted: Boolean
    )

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return ListRemoteViewsFactory(applicationContext)
    }

    class ListRemoteViewsFactory(private val mContext: Context) :
        RemoteViewsFactory {
        private val mWidgetItems: MutableList<WidgetItem> = ArrayList()
        override fun onCreate() {
            getData()
        }

        override fun onDestroy() {
            mWidgetItems.clear()
        }

        override fun getCount(): Int {
            return mWidgetItems.size
        }

        override fun getViewAt(position: Int): RemoteViews {
            val widgetItem = mWidgetItems[position]

            val rv = RemoteViews(mContext.packageName, R.layout.widget_layout)
            rv.setTextViewText(R.id.tvWidgetGoal, widgetItem.goal)

            if (widgetItem.isCompleted){
                rv.setImageViewResource(R.id.ivCheck,R.drawable.ic_check)
            }else{
                rv.setImageViewResource(R.id.ivCheck,R.drawable.ic_check_grey)
            }

            val fillInIntent: Intent = Intent().putExtra(DailyGoalWidget.EXTRA_CLICKED_FILE, widgetItem.id)
            rv.setOnClickFillInIntent(R.id.rvMainLayout, fillInIntent)

            return rv
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }

        override fun onDataSetChanged() {
            mWidgetItems.clear()
            getData()
        }

        fun getData() {
            val goalRepository = GoalRepository()
            goalRepository.GoalRepository(mContext)
            val goals = goalRepository.getGoalByTypeList("Daily")
            for (goal in goals) {
                val item = WidgetItem(goal.getGoalId(),goal.goal,goal.isCompleted)
                mWidgetItems.add(item)
            }
        }

    }
}