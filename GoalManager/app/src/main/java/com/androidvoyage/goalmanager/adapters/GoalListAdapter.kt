package com.androidvoyage.goalmanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.datamodels.GoalData
import com.androidvoyage.goalmanager.utils.DoubleClickListener

class GoalListAdapter(
    private val list: List<GoalData>,
    private val mContext: Context,
    val clickEvents: ClickEvents
) :
    RecyclerView.Adapter<GoalListAdapter.GoalViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GoalViewHolder(inflater, parent, clickEvents)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goalData: GoalData = list[position]
        if (position == list.size - 1) {
            holder.hideDivider(true)
        } else {
            holder.hideDivider(false)
        }
        holder.bind(goalData)
    }

    override fun getItemCount(): Int = list.size


    class GoalViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        val clickEvents: ClickEvents
    ) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.rv_item, parent, false)) {
        private var mTitleView: TextView? = null
        private var dividerView: View? = null
        private var ivCheck: ImageView? = null
        private var ivDelete: ImageView? = null


        init {
            mTitleView = itemView.findViewById(R.id.tvGoal)
            dividerView = itemView.findViewById(R.id.dividerView)
            ivCheck = itemView.findViewById(R.id.ivCheck)
            ivDelete = itemView.findViewById(R.id.ivDelete)

        }

        fun bind(goalData: GoalData) {
            mTitleView?.text = goalData.goal

            if (goalData.isCompleted) {
                ivCheck!!.setImageResource(R.drawable.ic_check)
            } else {
                ivCheck!!.setImageResource(R.drawable.ic_check_grey)
            }

            itemView.setOnClickListener(object : DoubleClickListener() {
                override fun onSingleClick(v: View?) {
                }

                override fun onDoubleClick(v: View?) {
                    if (goalData.isCompleted) {
                        ivCheck!!.setImageResource(R.drawable.ic_check_grey)
                        goalData.isCompleted = false
                    } else {
                        ivCheck!!.setImageResource(R.drawable.ic_check)
                        goalData.isCompleted = true
                    }
                    clickEvents.onDoubleClick(goalData)
                }
            })

            ivDelete!!.setOnClickListener {
                clickEvents.onDeleteGoal(goalData)
            }
        }

        fun hideDivider(hide: Boolean) {
            if (hide) {
                dividerView!!.visibility = View.GONE
            } else {
                dividerView!!.visibility = View.VISIBLE
            }
        }
    }


    interface ClickEvents {
        fun onDoubleClick(goalData: GoalData)

        fun onDeleteGoal(goalData: GoalData)
    }
}