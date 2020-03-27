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
        val goal: String, val isCompleted: Boolean
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
            rv.setTextViewText(R.id.tvGoal, widgetItem.goal)
            return rv
        }

        override fun getLoadingView(): RemoteViews? {
            return RemoteViews(mContext.packageName, R.layout.widget_layout)

        }

        override fun getViewTypeCount(): Int {
            return 0
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


        fun getData(){
            val goalRepository = GoalRepository()
            goalRepository.GoalRepository(mContext)
            val goals=goalRepository.getGoalByTypeList("Daily")
            for (goal in goals) {
                val item = WidgetItem(goal.goal, goal.isCompleted)
                mWidgetItems.add(item)
            }
        }

    }
}