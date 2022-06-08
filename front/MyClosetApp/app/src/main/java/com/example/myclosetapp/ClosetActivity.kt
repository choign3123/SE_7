package com.example.myclosetapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import com.example.myclosetapp.data.*
import com.example.myclosetapp.databinding.ActivityClosetBinding
import com.example.myclosetapp.databinding.ActivityLoginBinding
import com.example.myclosetapp.databinding.ItemGridBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ClosetActivity : AppCompatActivity() {

    val binding by lazy { ActivityClosetBinding.inflate(layoutInflater)}
    val binding_item by lazy {ItemGridBinding.inflate(layoutInflater)}

    var photoUri:Uri? = null

    var userIdx: Int? = null
    var cloth =ClothInfo(null, null, null, null)

    val retro = RetrofitService.create()

    // !!! 뒤로가기하면 바로 로그인 화면으로 넘어감! 경고 메세지 출력?
    // ??? userIdx 0번 존재?
    // ??? 기존 clthUrl 다 안 됨? 특정 앱에서 된 것만 됨? // 밑과 같은 경로는 앱이 달라서 안되는거임!(cameraandgallery)
    // content://com.example.cameraandgallery.provider/my_images/IMG_1147091379265117107.jpg

    // !!! 옷 등록 시 바로 갤러리로 들어가게끔해야됨 파일관리자의 최근 이미지 선택 시 이미지 불러올 때 런타임에러 뜸!!
    // !!! baseAdapter -> recyclerView로 변경 스크롤 시 렉 걸림

    lateinit var cameraPermission: ActivityResultLauncher<String>
    lateinit var storagePermission: ActivityResultLauncher<String>

    lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    lateinit var galleryLauncher: ActivityResultLauncher<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        Log.d("MYTAG", userIdx.toString())

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

        // 전체 옷 그리드 뷰 출력
        getAllCloth()

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess  ->
            if(isSuccess) {
                cloth.clthImgUrl = photoUri.toString()

                val intent = Intent( this@ClosetActivity ,TestModifyActivity::class.java)
                intent.putExtra("imgURL", photoUri.toString())
                intent.putExtra("userIdx", userIdx)
                startActivity(intent)
            }
        }

        galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            cloth.clthImgUrl = uri.toString()

            val intent = Intent( this@ClosetActivity ,TestModifyActivity::class.java)
            intent.putExtra("imgURL", uri.toString())
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        // 가끔식 새 옷 추가 시 해당 옷이 반영이 안되는지?늦게 되는지?의 경우가 있어서 버튼 추가함
        binding.buttonUpdate.setOnClickListener {
            getAllCloth()
            Toast.makeText(this,"옷장 최신화 중!!",Toast.LENGTH_SHORT)
        }

        storagePermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this@ClosetActivity, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        binding.buttonBookmark.setOnClickListener() {
            val intent = Intent(this@ClosetActivity, BookmarkActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

    }


    fun getAllCloth() {
        // 전체 옷 조회
        retro.getAllCloth(userIdx).enqueue(object: Callback<AllClothResult>{
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {
                binding.gridView.adapter =
                    GridViewAdapter(this@ClosetActivity, response.body()!!.result)

                Log.d("MYTAG", response.body()!!.result.toString())
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

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



    inner class GridViewAdapter(val context: Context, val img_list: ArrayList<AllClothObject>): BaseAdapter() {


        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val grid_View: View = LayoutInflater.from(context).inflate(R.layout.item_grid, null)

            // 여기 뷰바인딩으로 해결 시도!
            // binding_item.gridViewImg.setImageURI(img_list[p0]!!.clthImgUrl.toUri())
            val img = grid_View.findViewById<ImageView>(R.id.gridViewImg)
            img.setImageURI(img_list[p0].clthImgUrl.toUri())

            // 그리드뷰에서 개별 옷 클릭 시
            img.setOnClickListener() {
                val intent = Intent( context ,ClothActivity::class.java)
                intent.putExtra("userIdx", userIdx)
                intent.putExtra("clothIdx", img_list[p0]!!.clthIdx)

                startActivity(intent)
            }




            return grid_View
        }

        override fun getCount(): Int {
            return img_list.size
        }

        override fun getItem(p0: Int): Any {
            return 0
        }

        override fun getItemId(p0: Int): Long {
            return 0
        }
    }
}
