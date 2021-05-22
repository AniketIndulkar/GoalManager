package com.androidvoyage.goalmanager.activitie.study

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.androidvoyage.goalmanager.R
import com.androidvoyage.goalmanager.datamodels.PostData
import java.io.File


class StudyAdapter(val context: Context) : RecyclerView.Adapter<StudyAdapter.StudyViewHolder>() {
    var data: MutableList<PostData> = mutableListOf()

    fun addData(dataToAdd: PostData) {
        data.add(dataToAdd)
        notifyItemChanged(data.size - 1)
    }

    fun setPostData(postData: MutableList<PostData>){
        data = postData
        notifyDataSetChanged()
    }

    fun getAllData(): MutableList<PostData> = data

    override fun onViewAttachedToWindow(holder: StudyViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder.tvPara != null && holder.tvPara!!.text!!.isEmpty()) {
            holder.tvPara!!.requestFocus()
        }

        if (holder.etHeader != null && holder.etHeader!!.text!!.isEmpty()) {
            holder.etHeader!!.requestFocus()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyViewHolder {
        return StudyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.study_view, parent, false), context
        )
    }

    override fun onBindViewHolder(holder: StudyViewHolder, position: Int) {
        holder.bind(data[position], position)
    }

    override fun getItemCount(): Int = data.size


    inner class StudyViewHolder(itemView: View, context: Context) :
        RecyclerView.ViewHolder(itemView) {
        var etHeader: EditText? = null
        var tvPara: EditText? = null
        var ivStudyImage: ImageView? = null

        init {
            etHeader = itemView.findViewById(R.id.etStudyHeader)
            tvPara = itemView.findViewById(R.id.tvPara)
            ivStudyImage = itemView.findViewById(R.id.ivStudyImage)
        }


        fun bind(item: PostData, position: Int) = with(itemView) {
            when (item.type) {
                1 -> {
                    etHeader!!.setText(item.stringData)
                    etHeader!!.visibility = View.VISIBLE
                    tvPara!!.visibility = View.GONE
                    ivStudyImage!!.visibility = View.GONE
                }
                2 -> {
                    tvPara!!.setText(item.stringData)
                    etHeader!!.visibility = View.GONE
                    tvPara!!.visibility = View.VISIBLE
                    ivStudyImage!!.visibility = View.GONE

                }
                3 -> {

                    context.contentResolver.notifyChange(Uri.fromFile(File(item.imageData)), null)
                    val bitmap: Bitmap
                    try {
                        bitmap = MediaStore.Images.Media
                            .getBitmap(context.contentResolver, Uri.fromFile(File(item.imageData)))
                        ivStudyImage!!.setImageBitmap(bitmap)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Failed to load", Toast.LENGTH_SHORT)
                            .show()
                    }
                    etHeader!!.visibility = View.GONE
                    tvPara!!.visibility = View.GONE
                    ivStudyImage!!.visibility = View.VISIBLE

                }
                4 -> {
                    etHeader!!.visibility = View.GONE
                    tvPara!!.visibility = View.VISIBLE
                    ivStudyImage!!.visibility = View.GONE

                }
                5 -> {
                    etHeader!!.visibility = View.GONE
                    tvPara!!.visibility = View.VISIBLE
                    ivStudyImage!!.visibility = View.GONE

                }
                else -> {

                }
            }

            etHeader!!.doOnTextChanged { text, _, _, _ ->
                if (etHeader!!.isFocused) {
                    item.stringData = text.toString()
                    data.removeAt(position)
                    data.add(position, item)
                }
            }

            tvPara!!.doOnTextChanged { text, _, _, _ ->
                if (tvPara!!.isFocused) {
                    item.stringData = text.toString()
                    data.removeAt(position)
                    data.add(position, item)
                }
            }
        }
    }
}