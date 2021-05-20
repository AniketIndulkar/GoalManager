package com.androidvoyage.goalmanager.activitie.study

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidvoyage.goalmanager.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.io.File


class StudyActivity : AppCompatActivity() {

    lateinit var viewModel: StudyViewModel
    lateinit var rvStudy : RecyclerView
    val adapter = StudyAdapter(this)

    var sheetBehavior: BottomSheetBehavior<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_study)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(StudyViewModel::class.java)
        sheetBehavior = BottomSheetBehavior.from(findViewById<LinearLayout>(R.id.bottom_sheet));
        rvStudy = findViewById(R.id.rvStudy)
        rvStudy.layoutManager = LinearLayoutManager(this@StudyActivity)
        rvStudy.adapter =adapter
        viewModel.postData.observe(this, Observer {
            adapter.addData(it)
        })
    }

    fun onClickAddComponent(view: View) {
        if (sheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            collapseSheep()
        }
    }

    private fun collapseSheep() {
        sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED;
    }

    fun addText(view: View) {
        collapseSheep()
        viewModel.addData(1)
    }

    fun addPara(view: View) {
        collapseSheep()
        viewModel.addData(2)
    }

    fun addImage(view: View) {
        collapseSheep()
        takePhoto()
    }

    fun addList(view: View) {
        collapseSheep()
        viewModel.addData(4)
    }

    fun addQuote(view: View) {
        collapseSheep()
        viewModel.addData(5)
    }

    override fun onBackPressed() {
        if (sheetBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED)
            collapseSheep()
        else
            super.onBackPressed()
    }

    private val TAKE_PICTURE = 1
    private var imageUri: Uri? = null
    fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val photo = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            Uri.fromFile(photo)
        )
        imageUri = Uri.fromFile(photo)
        startActivityForResult(intent, TAKE_PICTURE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            TAKE_PICTURE -> if (resultCode == RESULT_OK) {
                val selectedImage = imageUri!!
                viewModel.addImageData(3 , selectedImage.path!!)

            }
        }
    }

}