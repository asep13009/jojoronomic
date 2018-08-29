package com.example.ishaqfakhrozi.lololonomic

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequestBuilder
import kotlinx.android.synthetic.main.activity_camera.*
import kotlinx.android.synthetic.main.activity_camera2.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Classe principal da aplicação.
 *
 * @author Augusto Santos
 * @version 1.0
 */
class CameraActivity : AppCompatActivity() {

    val CAMERA_REQUEST_CODE = 0
    lateinit var txtWaktu : TextView
    lateinit var txtTanggal : TextView
    lateinit var imageFilePath: String
//    internal var media_image = 100
//

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)
        txtWaktu = findViewById(R.id.txtWaktu)
        txtTanggal = findViewById(R.id.txtTanggal)
        camerabutton.setOnClickListener {

                val callCameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (callCameraintent.resolveActivity(packageManager) != null) {
                    startActivityForResult(callCameraintent, CAMERA_REQUEST_CODE)

                }

        }
        logcheck.setOnClickListener{
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val photo = data.extras.get("data") as Bitmap
                    photoimgview.setImageBitmap(photo)
                    val waktu = SimpleDateFormat("hh:mm aaa").format(Date())
                    val tanggal = SimpleDateFormat("yyyy MMM dd").format(Date())
                    txtWaktu.text = waktu
                    txtTanggal.text=tanggal
                    val tempUri = getImageUri(applicationContext, photo)
                    val finalFile = File(getRealPathFromURI(tempUri))
                    Toast.makeText(this, "anda berhasil check in",Toast.LENGTH_LONG).show()
//                if (resultCode== Activity.RESULT_OK){
//                    photoimgview.setImageBitmap(scaledBitmap())
//                }
                } else {
                   Toast.makeText(this, "ga bisa kehubung ke request", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

        fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null)
        return Uri.parse(path)
    }

        fun getRealPathFromURI(uri: Uri): String {
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor!!.moveToFirst()
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor.getString(idx)
        }


}
