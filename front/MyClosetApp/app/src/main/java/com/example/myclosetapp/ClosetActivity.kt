package com.example.myclosetapp

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myclosetapp.data.AllClothObject
import com.example.myclosetapp.data.AllClothResult
import com.example.myclosetapp.data.ClothInfo
import com.example.myclosetapp.databinding.ActivityClosetBinding
import com.example.myclosetapp.databinding.ItemGridBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ClosetActivity : AppCompatActivity() {

    val binding by lazy { ActivityClosetBinding.inflate(layoutInflater)}

    var photoUri:Uri? = null

    var userIdx: Int? = null
    var cloth =ClothInfo(null, null, null, null)

    val retro = RetrofitService.create()

    var waitTime = 0L

    // !!! 옷 등록 시 바로 갤러리로 들어가게끔해야됨 파일관리자의 최근 이미지 선택 시 이미지 불러올 때 런타임에러 뜸!!

    lateinit var cameraPermission: ActivityResultLauncher<String>
    //lateinit var storagePermission: ActivityResultLauncher<String>

    lateinit var cameraLauncher: ActivityResultLauncher<Uri>
    lateinit var galleryLauncher: ActivityResultLauncher<String>

    var PERMISSION_REQUEST_CODE = 99

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        Log.d("MYTAG", "화면 점프 성공!!!")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        Log.d("MYTAG", "userIdx : "+userIdx.toString())


        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                openCamera()
            } else {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            }
        }

        // test checking!!!

        // 전체 옷 그리드 뷰 출력
//        getAllCloth()

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



        // 권한 확인 실행
        // 이거 밖으로 빼자
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            Log.d("MYTAG", "권한 취득 완료")
            Log.d("MYTAG", "처음 실행 분기")
            setViews()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE)
            Log.d("MYTAG", "권한 취득 시도")
        }



        // 가끔식 새 옷 추가 시 해당 옷이 반영이 늦게 되는 경우가 있어서 버튼 추가함
        binding.buttonUpdate.setOnClickListener {
//            getAllCloth()
//            Toast.makeText(this,"옷장 최신화 중!!",Toast.LENGTH_SHORT).show()
            // 홈 화면으로 이동
            // 아니면 새로운 홈 화면 생성?
//            val intent = Intent(this, ClosetActivity::class.java)
//            intent.putExtra("userIdx", userIdx)
//            startActivity(intent)
            Toast.makeText(this,"이미 홈 화면입니다!!",Toast.LENGTH_SHORT).show()
        }

        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        binding.buttonBookmark.setOnClickListener() {
            val intent = Intent(this, BookmarkActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        if(System.currentTimeMillis() - waitTime >=1500 ) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
        } else {
            finish() // 액티비티 종료
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MYTAG", "권한 취득 시도 이후 분기")
                    setViews()
                }
                else {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Log.d("MYTAG", "사용자가 권한 허용 거부")
                        Log.d("MYTAG", "거절 이후 분기")

                        Toast.makeText(this,"필수 권한입니다!!!",Toast.LENGTH_SHORT).show()

                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
//                        // 2000 밀리초 지연 이후 실행하려면...
//                        Handler(Looper.getMainLooper()).postDelayed({
//                            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                                PERMISSION_REQUEST_CODE)
//                        }, 2000)

                    } else {
                        // 더 이상 권한 요청을 해도 팝업이 뜨지 않음
                        Log.d("MYTAG", "사용자가 권한 허용 계속 거부")

                        // 설정 창으로 이동
                        showDialogToGetPermission()
                    }
                }
            }
        }
    }

    private fun showDialogToGetPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Permisisons request")
            .setMessage("파일 및 미디어 권한은 필수 권한입니다!!\n" +
                    "OK 버튼 터치 시 설정 화면으로 이동..")

        builder.setPositiveButton("OK") { dialogInterface, i ->
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)   // 6
            finish()
        }
        builder.setNegativeButton("EXIT") { dialogInterface, i ->
            finish()
        }
        val dialog = builder.create()
        dialog.show()
    }


    fun getAllCloth() {
        Log.d("gana", "getAllCloth: 옷 정보 레트로핏")
        // 전체 옷 조회
        retro.getAllCloth(userIdx).enqueue(object: Callback<AllClothResult>{
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {
                val listManager = GridLayoutManager(this@ClosetActivity,2)
                var listAdapter = ListAdapterGrid(this@ClosetActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridView.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }

//                binding.gridView.adapter =
//                    GridViewAdapter(this@ClosetActivity, userIdx, response.body()!!.result)

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

        getAllCloth()
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



}
