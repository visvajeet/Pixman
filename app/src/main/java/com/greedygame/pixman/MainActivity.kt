package com.greedygame.pixman

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import androidx.databinding.DataBindingUtil
import com.esafirm.imagepicker.features.ImagePicker
import com.greedygame.pixman.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


    }

    //Image Picker
    fun imagePicker(view: View) {

        ImagePicker.create(this)
            .theme(R.style.ImagePickerTheme)
            .single()
            .limit(1)
            .folderMode(true)
            .toolbarFolderTitle(getString(R.string.folder))
            .toolbarImageTitle(getString(R.string.tap_to_select))
            .toolbarArrowColor(Color.WHITE)
            .start()

    }


    //Get image picker Result and start editing activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {

            val image = ImagePicker.getFirstImageOrNull(data)
            val imagePojo = ImagePojo(image.id,image.name,image.path)
            val editImageIntent = Intent(this, EditImage::class.java)
            editImageIntent.putExtra("image",imagePojo)
            startActivity(editImageIntent)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}
