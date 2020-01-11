package com.greedygame.pixman

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.greedygame.pixman.databinding.ActivityEditImageBinding
import java.io.File

class EditImage : AppCompatActivity() {

    private lateinit var binding: ActivityEditImageBinding
    private lateinit var viewModel: EditImageViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_image)

        viewModel = ViewModelProviders.of(this).get(EditImageViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        binding.ivBack.setOnClickListener { onBackPressed() }

        setImage()

        viewModel.bitmapArray.observe(this, Observer {

            if(it.isNotEmpty()){
                Log.d("AAA",it.size.toString())
                binding.ivPreview.setImageBitmap(it.last())
            }

        })

        viewModel.imageSaveStatus.observe(this, Observer {

            when (it) {

                SaveImageStatus.ERROR -> Toast.makeText(this, getString(R.string.error_while_saving_image),Toast.LENGTH_LONG).show()
                SaveImageStatus.DONE -> {
                    Toast.makeText(this, getString(R.string.image_saved_to),Toast.LENGTH_LONG).show()
                    finish()
                }

                else -> {Log.d("ImageSaveStatus","IDLE" )}
            }
        })


    }

    private fun setImage() {

            intent.getParcelableExtra<ImagePojo>("image")?.let {

            val imgFile =  File(it.path)
            if(imgFile.exists())
            { viewModel.loadImage(it.path) }
        }

    }


}
