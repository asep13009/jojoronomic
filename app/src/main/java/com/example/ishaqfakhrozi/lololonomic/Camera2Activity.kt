//package com.example.ishaqfakhrozi.lololonomic
//
//import android.app.Activity
//import android.content.ContentValues
//import android.content.Context
//import android.content.Intent
//import android.graphics.Bitmap
//import android.graphics.BitmapFactory
//import android.net.Uri
//import android.support.v7.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Environment
//import android.provider.MediaStore
//import android.support.v4.content.FileProvider
//
//import android.widget.Toast
//import com.ebanx.swipebtn.SwipeButton
//import kotlinx.android.synthetic.main.activity_camera2.*
//import java.io.ByteArrayOutputStream
//import java.io.File
//import java.io.IOException
//import java.text.SimpleDateFormat
//import java.util.*
//
//
//class Camera2Activity : AppCompatActivity() {
//    val CAMERA_REQUEST_CODE = 0
//    lateinit var imageFilePath : String
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_camera2)
////        val swipebutton =findViewById(R.id.swipe) as SwipeButton
//        camerabutton.setOnClickListener{
//            try {
//                val imageFile = createImagefile()
//                val callCameraintent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//                if (callCameraintent.resolveActivity(packageManager)!= null){
//                    val autoritez= packageName +".fileprovider"
//                    val imageUri = FileProvider.getUriForFile(this,autoritez,imageFile)
//                    callCameraintent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
//                    startActivityForResult(callCameraintent,CAMERA_REQUEST_CODE)
//                }
//            }catch (e:Exception){
//                Toast.makeText(this,"ga jalan",Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }
//
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when(requestCode){
//            CAMERA_REQUEST_CODE->{
//                if (resultCode== Activity.RESULT_OK){
//                    photoimgview.setImageBitmap(scaledBitmap())
//                }
//            }
//            else->{
//                Toast.makeText(this,"ga bisa kehubung ke request",Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//    @Throws(IOException::class)
//    fun createImagefile(): File {
//        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
//        val imageFileName = "CodeNomic_"+timestamp+".jpg"
//        val storagedil = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
//        if(!storagedil.exists()) storagedil.mkdirs()
//        val imageFile = File(storagedil,imageFileName)
//
//        imageFilePath = imageFile.absolutePath
//        return imageFile
//    }
//    fun scaledBitmap():Bitmap{
//        val imageViewWitdh = photoimgview.width
//        val imageViewHeight = photoimgview.height
//        val bmOption = BitmapFactory.Options()
//        bmOption.inJustDecodeBounds =true
//        BitmapFactory.decodeFile(imageFilePath,bmOption)
//        val bitmapWidth=bmOption.outWidth
//        val bitmapHeight = bmOption.outHeight
//        val bytes = ByteArrayOutputStream()
//
//
//        val scaleFactory = Math.min(bitmapWidth/imageViewWitdh, bitmapHeight/imageViewHeight)
//        bmOption.inSampleSize =scaleFactory
//        bmOption.inJustDecodeBounds = false
//
//        return BitmapFactory.decodeFile(imageFilePath, bmOption)
//    }
//
//}
