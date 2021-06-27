package com.pekyurek.emircan.presentation.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.pekyurek.emircan.R
import com.pekyurek.emircan.databinding.ActivityMainBinding
import com.pekyurek.emircan.presentation.core.extensions.urlToBitmap
import com.pekyurek.emircan.presentation.ui.base.BaseActivity
import com.pekyurek.emircan.presentation.ui.overlay.OverlayAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override val layoutResId: Int get() = R.layout.activity_main

    override val viewModel: MainViewModel by viewModels()

    private val overlayAdapter = OverlayAdapter { ovarlayUrl ->
        ovarlayUrl?.urlToBitmap(this) { overlayBitmap ->
            binding.photoEditView.overlayBitmap = overlayBitmap
        } ?: kotlin.run { binding.photoEditView.overlayBitmap = null }
    }

    override fun initBinding() {
        super.initBinding()
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun initViews() {
        binding.rvOverlay.apply {
            adapter = overlayAdapter
            itemAnimator = null
        }

        binding.saveImage.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestWriteExternalPermission()
            } else {
                saveImage()
            }
        }
    }

    private fun requestWriteExternalPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_WRITE_EXTERNAL_PERMISSION
        )
    }

    @RequiresPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private fun saveImage() {
        binding.photoEditView.saveImageToExternalStorage()
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_WRITE_EXTERNAL_PERMISSION) {
            permissions.forEachIndexed { index, permission ->
                if (permission == Manifest.permission.WRITE_EXTERNAL_STORAGE
                    && grantResults.getOrNull(index) == PackageManager.PERMISSION_GRANTED
                ) {
                    saveImage()
                } else {
                    Toast.makeText(this, R.string.permission_error_write_external, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun setObservers() {
        super.setObservers()
        viewModel.overlayList.observe(this) {
            overlayAdapter.setData(it)
        }
    }

    companion object {
        private const val REQUEST_WRITE_EXTERNAL_PERMISSION = 2
    }

}