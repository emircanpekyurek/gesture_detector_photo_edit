package com.pekyurek.emircan.presentation.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.view.ViewCompat
import androidx.core.view.drawToBitmap
import androidx.core.view.updateLayoutParams
import com.pekyurek.emircan.R
import com.pekyurek.emircan.presentation.core.extensions.saveExternalStorage


class PhotoEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyle: Int = 0
) : View(context, attrs, defStyleAttr, defStyle) {

    private var backgroundImageDrawable: Drawable? = null
        set(value) {
            field = value
            value?.let { updateViewSizeWhenBackgroundSelected(it) }
            ViewCompat.postInvalidateOnAnimation(this@PhotoEditView)
        }

    var overlayMatrix: Matrix = Matrix()

    var overlayBitmap: Bitmap? = null
        set(value) {
            field = value
            value?.let { bitmap ->
                overlayMatrix = Matrix().apply {
                    postTranslate(
                        width.toFloat() / 2 - bitmap.width / 2,
                        height.toFloat() / 2 - bitmap.height / 2
                    )
                }
            }
            ViewCompat.postInvalidateOnAnimation(this@PhotoEditView)
        }

    private val gestureDetector =
        GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent?,
                distanceX: Float,
                distanceY: Float
            ): Boolean {
                overlayMatrix.postTranslate(-distanceX, -distanceY)
                ViewCompat.postInvalidateOnAnimation(this@PhotoEditView)
                return true
            }
        })

    private val scaleGestureDetector = ScaleGestureDetector(context, object :
        ScaleGestureDetector.SimpleOnScaleGestureListener() {
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val scaleFactor: Float = detector.scaleFactor

            val overlaySize = getOverlaySize(scaleFactor) ?: return false

            overlayMatrix.postScale(
                scaleFactor,
                scaleFactor,
                overlaySize.first / 2f,
                overlaySize.second / 2f
            )
            ViewCompat.postInvalidateOnAnimation(this@PhotoEditView)
            return true
        }
    })

    private fun getOverlaySize(scaleFactor: Float): Pair<Float, Float>? {
        return overlayBitmap?.let { bitmap ->
            val f = FloatArray(9)
            overlayMatrix.getValues(f)

            val scaleX = f[Matrix.MSCALE_X]
            val scaleY = f[Matrix.MSCALE_Y]

            val overlayWidth = bitmap.width * scaleX * scaleFactor
            val overlayHeight = bitmap.height * scaleY * scaleFactor
            overlayWidth to overlayHeight
        }
    }

    init {
        context.obtainStyledAttributes(attrs, R.styleable.PhotoEditView, defStyle, 0).apply {
            backgroundImageDrawable = getDrawable(
                R.styleable.PhotoEditView_selectedDrawable
            )?.apply {
                callback = this@PhotoEditView
            }
            recycle()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        gestureDetector.onTouchEvent(event)
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundImage(canvas)
        overlayBitmap?.let { canvas.drawBitmap(it, overlayMatrix, null) }
    }


    private fun setBackgroundImage(canvas: Canvas) {
        backgroundImageDrawable?.setBounds(0, 0, width, height)
        backgroundImageDrawable?.draw(canvas)
    }

    private fun updateViewSizeWhenBackgroundSelected(drawable: Drawable) = post {
        val imageRatio = drawable.intrinsicWidth.toFloat() / drawable.intrinsicHeight
        val viewRatio = width.toFloat() / height

        val imageWith: Float
        val imageHeight: Float
        when {
            imageRatio > viewRatio -> {
                imageWith = width.toFloat()
                imageHeight = width.toFloat() / imageRatio
            }
            viewRatio > imageRatio -> {
                imageHeight = height.toFloat()
                imageWith = height.toFloat() * imageRatio
            }
            else -> {
                imageHeight = height.toFloat()
                imageWith = width.toFloat()
            }
        }

        updateLayoutParams {
            width = imageWith.toInt()
            height = imageHeight.toInt()
        }
    }

    @RequiresPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    fun saveImageToExternalStorage() {
        if (backgroundImageDrawable == null) {
            Toast.makeText(context, R.string.error_no_photo_selected, Toast.LENGTH_SHORT).show()
            return
        }
        try {
            drawToBitmap().saveExternalStorage(context, System.currentTimeMillis().toString())
            Toast.makeText(context, R.string.success_save_image, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
}
