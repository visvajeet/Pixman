package com.greedygame.pixman

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.view.View
import androidx.lifecycle.*
import com.greedygame.pixman.extensions.*
import java.io.File
import java.io.FileOutputStream


class EditImageViewModel(application: Application) : AndroidViewModel(application) {

    private val _bitmapArray = MutableLiveData<ArrayList<Bitmap>>()
    val bitmapArray: LiveData<ArrayList<Bitmap>> = _bitmapArray

    private val _imageSaveStatus = MutableLiveData<SaveImageStatus>()
    val imageSaveStatus: LiveData<SaveImageStatus> = _imageSaveStatus


    init {

        _imageSaveStatus.value = SaveImageStatus.IDLE
        _bitmapArray.value = ArrayList()
    }


    fun loadImage(path: String) {

        val bmp = BitmapFactory.decodeFile(path)

        val temp = ArrayList<Bitmap>()
        temp.clear()
        temp.add(bmp)

        _bitmapArray.value = temp

    }

    fun rotateImage() {

        if (_bitmapArray.value?.isNotEmpty()!!) {

            val bmp = _bitmapArray.value!!.last()

            val temp = ArrayList<Bitmap>()
            temp.clear()
            temp.addAll(_bitmapArray.value!!)

            temp.add(bmp.rotate())

            _bitmapArray.value = temp

        }
    }

    fun undoAction() {

        if(_bitmapArray.value!!.size > 1) {

            val temp = ArrayList<Bitmap>()
            temp.clear()
            temp.addAll(_bitmapArray.value!!)
            temp.removeAt(temp.size - 1 )

            _bitmapArray.value = temp

        }


    }


    fun flipX() {


        val bmp = _bitmapArray.value!!.last()

        val temp = ArrayList<Bitmap>()
        temp.clear()
        temp.addAll(_bitmapArray.value!!)

        temp.add(bmp.flip(-1f, 1f))

        _bitmapArray.value = temp
    }


    fun flipY() {

        val bmp = _bitmapArray.value!!.last()

        val temp = ArrayList<Bitmap>()
        temp.clear()
        temp.addAll(_bitmapArray.value!!)

        temp.add(bmp.flip(1f, -1f))

        _bitmapArray.value = temp
    }

    fun changeOpacity() {

        val bmp = _bitmapArray.value!!.last()

        val temp = ArrayList<Bitmap>()
        temp.clear()
        temp.addAll(_bitmapArray.value!!)

        temp.add(bmp.opacity(50))

        _bitmapArray.value = temp
    }


    fun addText() {

        val txt = "GreedyGame"

        val bmp = _bitmapArray.value!!.last()

        val temp = ArrayList<Bitmap>()
        temp.clear()
        temp.addAll(_bitmapArray.value!!)

        temp.add(bmp.addText(txt))

        _bitmapArray.value = temp
    }


    fun saveImage(view: View) {

        if (view.isEnabled) {

            val bmp = _bitmapArray.value!!.last()
            val bmpWithWaterMark = bmp.addWaterMark("GreedyGame",view.context)


            val root = Environment.getExternalStorageDirectory().absolutePath
            val dir = File("$root/Pixman")
            if (!dir.exists()) {
                dir.mkdirs()
            }

            val fileName = "${System.currentTimeMillis()}.jpg"
            val file = File(dir, fileName)
            if (file.exists()) file.delete()

            try {
                val out = FileOutputStream(file)
                bmpWithWaterMark.compress(Bitmap.CompressFormat.JPEG, 100, out)
                out.flush()
                out.close()

                _imageSaveStatus.value = SaveImageStatus.DONE

            } catch (e: Exception) {
                _imageSaveStatus.value = SaveImageStatus.ERROR
                e.printStackTrace()
            }

        }
    }

}

enum class SaveImageStatus {IDLE, DONE, ERROR}







