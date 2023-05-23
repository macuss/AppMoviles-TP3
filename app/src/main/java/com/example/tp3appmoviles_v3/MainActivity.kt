package com.example.tp3appmoviles_v3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    private var flashLightStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnCamara = findViewById<Button>(R.id.butFoto)
        val btnLint = findViewById<Button>(R.id.butLint)
        btnCamara.setOnClickListener {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
        btnLint.setOnClickListener {
            encenderLuz()
        }

    }

    private fun encenderLuz() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val camaraId = cameraManager.cameraIdList[0]
        if(!flashLightStatus) {
            try {
                cameraManager.setTorchMode(camaraId, true)
                flashLightStatus = true
            } catch (e: CameraAccessException) {}
        } else{
            try {
                cameraManager.setTorchMode(camaraId, false)
                flashLightStatus = false
            } catch (e: CameraAccessException){}
        }
    }


    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result : ActivityResult ->
            if(result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val imageBitMap = intent?.extras?.get("data") as Bitmap
                val imageViu = findViewById<ImageView>(R.id.imageviu)
                imageViu.setImageBitmap(imageBitMap)
            }
    }


}
