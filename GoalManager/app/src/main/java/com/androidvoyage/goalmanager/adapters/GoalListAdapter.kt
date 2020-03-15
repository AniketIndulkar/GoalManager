package com.androidvoyage.goalmanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.datamodels.GoalData

class GoalListAdapter(private val list: List<GoalData>) : RecyclerView.Adapter<GoalViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GoalViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goalData: GoalData = list[position]
        if (position == list.size-1) {
            holder.hideDivider(true)
        } else {
            holder.hideDivider(false)
        }
        holder.bind(goalData)
    }

    override fun getItemCount(): Int = list.size

}

class GoalViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.rv_item, parent, false)) {
    private var mTitleView: TextView? = null
    private var dividerView: View? = null


    init {
        mTitleView = itemView.findViewById(R.id.tvGoal)
        dividerView = itemView.findViewById(R.id.dividerView)
    }

    fun bind(goalData: GoalData) {
        mTitleView?.text = goalData.goal
    }

    fun hideDivider(hide: Boolean) {
        if (hide) {
            dividerView!!.visibility = View.GONE
        } else {
            dividerView!!.visibility = View.VISIBLE
        }
    }

}