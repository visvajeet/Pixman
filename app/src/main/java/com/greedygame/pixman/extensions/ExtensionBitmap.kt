package com.greedygame.pixman.extensions

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Align
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import com.greedygame.pixman.R


fun Bitmap.flip(x: Float, y: Float): Bitmap {

    val matrix = Matrix().apply { postScale(x, y, width/2f, width/2f) }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}

fun Bitmap.rotate(): Bitmap {

    val matrix = Matrix()
    matrix.postRotate(90F)
    return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
}


fun Bitmap.opacity(percentage: Int): Bitmap {

    val newBitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(newBitmap)
    val alphaPaint = Paint()
    alphaPaint.alpha = percentage

    canvas.drawBitmap(this, 0f, 0f, alphaPaint)

    return  newBitmap
}


fun Bitmap.addText(text: String): Bitmap {

    val newBitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(newBitmap)
    val rect = Rect()

    val paint = Paint()

    canvas.getClipBounds(rect)
    val cHeight = rect.height()
    val cWidth = rect.width()
    paint.textAlign = Align.LEFT
    paint.textSize = 55f

    paint.getTextBounds(text, 0, text.length, rect)
    val x = cWidth / 2f - rect.width() / 2f - rect.left
    val y = cHeight / 2f + rect.height() / 2f - rect.bottom

    canvas.drawBitmap(this, 0f, 0f, paint)

    paint.color = Color.GREEN
    drawTextBounds(canvas, rect, x.toInt(), y.toInt(),"#000000")
    canvas.drawText(text, x, y, paint)

    return  newBitmap
}

fun Bitmap.addWaterMark(text: String, context: Context): Bitmap {

    val newBitmap = Bitmap.createBitmap(
        this.width,
        this.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = Canvas(newBitmap)
    val rect = Rect()

    val paint = Paint()

    canvas.getClipBounds(rect)
    val cHeight = rect.height()
    val cWidth = rect.width()
    paint.textAlign = Align.LEFT
    paint.textSize = 40f

    paint.getTextBounds(text, 0, text.length, rect)

    val x = width - (rect.width() + 15)
    val y = cHeight - 15

    canvas.drawBitmap(this, 0f, 0f, paint)

    paint.color = Color.WHITE
    drawTextBounds(canvas, rect, x, y, "#A1000000")
    canvas.drawText(text, x.toFloat(), y.toFloat(), paint)
    addLogo(canvas,context,cHeight)

    return  newBitmap
}

fun addLogo(canvas: Canvas, context: Context, cHeight: Int) {

    val height = 50
    val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo)
    val resized = Bitmap.createScaledBitmap(logoBitmap, 200, height, true)
    canvas.drawBitmap(resized,0f,cHeight.toFloat() - height,null)

}


private fun drawTextBounds(canvas: Canvas, rect: Rect, x: Int, y: Int, color:String) {

    val rectPaint = Paint()
    rectPaint.color = Color.parseColor(color)
    rectPaint.style = Paint.Style.FILL_AND_STROKE
    rect.inset(-14,-14)
    rect.offset(x, y)
    canvas.drawRect(rect, rectPaint)

}
