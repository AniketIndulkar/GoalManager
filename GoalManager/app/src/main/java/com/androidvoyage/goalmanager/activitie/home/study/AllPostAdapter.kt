package com.androidvoyage.goalmanager.activitie.home.study

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.datamodels.Post

class AllPostAdapter : RecyclerView.Adapter<AllPostAdapter.AllPostViewHolder>() {

    var allPostData: List<Post>? = null
    lateinit var allPostClickListener: AllPostClickListener

    fun setData(data: List<Post>) {
        allPostData = data
    }

    fun setClickListener(listener: AllPostClickListener){
        allPostClickListener = listener
    }



    inner class AllPostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvPostHeader : TextView = itemView.findViewById(R.id.tvPostHeader)


        fun bind(post: Post) {
            tvPostHeader.text = post.header
            tvPostHeader.setOnClickListener {
                allPostClickListener.postClicked(post.postId)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPostViewHolder {
        return AllPostViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.all_post_layout,parent, false))
    }

    override fun onBindViewHolder(holder: AllPostViewHolder, position: Int) {
        holder.bind(allPostData!![position])
    }

    override fun getItemCount(): Int =allPostData!!.size

    interface AllPostClickListener{
        fun postClicked(postId : Long)
    }
}