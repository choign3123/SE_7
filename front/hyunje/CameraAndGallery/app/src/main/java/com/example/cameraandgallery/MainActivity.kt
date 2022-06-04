package com.example.cameraandgallery

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.cameraandgallery.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainActivity : AppCompatActivity() {

    val retro = RetrofitService.create()

    var photoUri:Uri? = null
    var data = ClothInfo(null,null,null,null)

    lateinit var cameraPermission:ActivityResultLauncher<String>
    lateinit var storagePermission:ActivityResultLauncher<String>

    lateinit var cameraLauncher:ActivityResultLauncher<Uri>
    lateinit var galleryLauncher:ActivityResultLauncher<String>

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        storagePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                setViews()
            } else {
                Toast.makeText(baseContext, "외부저장소 권한을 승인해야 앱을 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
                finish()
            }
        }

        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                openCamera()
            } else {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess  ->
            if(isSuccess) {
                binding.imagePreview.setImageURI(photoUri)
                Log.d("myTAG","URI on camera "+ photoUri.toString())

                data.clthImgUrl = photoUri.toString()
                data.bookmark = false
                data.category = "스커트"
                data.season = "겨울"
                binding.postButton.setOnClickListener(ButtonListener())
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            binding.imagePreview.setImageURI(uri)
            Log.d("myTAG","URI on album "+ uri.toString())

            data.clthImgUrl = uri.toString()
            data.bookmark = true
            data.category = "상의"
            data.season = "봄"
            binding.postButton.setOnClickListener(ButtonListener())
        }

        storagePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        binding.getButton.setOnClickListener(ButtonListener2())

        //Log.d("myTag","URI on outside "+ photoUri.toString()) // null
    }



    ////////



    fun setViews() {
        binding.buttonCamera.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }
        binding.buttonGallery.setOnClickListener {
            openGallery()
        }
    }

    fun openCamera() {
        val photoFile = File.createTempFile(
            "IMG_",
            ".jpg",
            getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        )

        photoUri = FileProvider.getUriForFile(
            this,
            "${packageName}.provider",
            photoFile
        )

        cameraLauncher.launch(photoUri)
    }

    fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    inner class ButtonListener : View.OnClickListener {
        override fun onClick(p0: View?) {
            retro.postCloth(3,data).enqueue(object: Callback<PostClothResult> {
                override fun onResponse(
                    call: Call<PostClothResult>,
                    response: Response<PostClothResult>
                ) {
                    Log.d("myTAG","! " + response.body()!!.isSuccess.toString())
                    Log.d("myTAG","! " + response.body()!!.message)
                    Log.d("myTAG","! " + response.body()!!.result)
                    Log.d("myTAG","! " + data.clthImgUrl)
                    Log.d("myTAG","! " + data.bookmark.toString())
                    Log.d("myTAG","! " + data.category)
                    Log.d("myTAG","! " + data.season)

                }

                override fun onFailure(call: Call<PostClothResult>, t: Throwable) {
                    Log.d("myTAG",t.message.toString())
                    Log.d("myTAG","fail")
                }

            })
        }

    }

    inner class ButtonListener2 : View.OnClickListener {
        override fun onClick(p0: View?) {
            retro.getCloth(3,29).enqueue(object: Callback<ClothResult>{
                override fun onResponse(call: Call<ClothResult>, response: Response<ClothResult>) {
                    Log.d("myTAG","! " + response.body()!!.isSuccess)
                    Log.d("myTAG","! " + response.body()!!.result.clthImgUrl)

                    binding.imagePreview.setImageURI(response.body()!!.result.clthImgUrl.toUri())
                }

                override fun onFailure(call: Call<ClothResult>, t: Throwable) {
                    Log.d("myTAG",t.message.toString())
                    Log.d("myTAG","fail")                }

            })
        }
    }

}


