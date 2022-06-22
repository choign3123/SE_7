package com.example.myclosetapp

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myclosetapp.data.AllClothResult
import com.example.myclosetapp.data.ClothInfo
import com.example.myclosetapp.databinding.ActivityClosetBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ClosetActivity : AppCompatActivity() {

    val binding by lazy { ActivityClosetBinding.inflate(layoutInflater)}
    val retro = RetrofitService.create()

    var photoUri:Uri? = null

    var userIdx: Int? = null
    var cloth =ClothInfo(null, null, null, null)

    var waitTime = 0L

    lateinit var cameraPermission: ActivityResultLauncher<String>

    var PERMISSION_REQUEST_CODE = 99

    lateinit var openCamera: ActivityResultLauncher<Uri>
    lateinit var openGallery: ActivityResultLauncher<String>


    // 옷 등록 이후 진입 시
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        getAllCloth()
        // 로그
        Log.d("MYTAG", "onNewIntent's uerIdx : "+userIdx.toString())

    }

    // !!! 옷 등록 시 바로 갤러리로 들어가게끔해야됨 파일관리자의 최근 이미지 선택 시 이미지 불러올 때 런타임에러 뜸!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        userIdx = intent.getIntExtra("userIdx", 0)
        // 로그
        Log.d("MYTAG", "onCreate's userIdx : "+userIdx.toString())

        /******************************************************************************************/
        ////// 카메라 갤러리 관련 //////
        openCamera = registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess  ->
            if(isSuccess) {
                cloth.clthImgUrl = photoUri.toString()

                val intent = Intent( this@ClosetActivity ,ModifyActivity::class.java)
                intent.putExtra("imgURL", photoUri.toString())
                intent.putExtra("userIdx", userIdx)
                startActivity(intent)
            }
        }

        openGallery = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                cloth.clthImgUrl = uri.toString()

                val intent = Intent(this@ClosetActivity, ModifyActivity::class.java)
                intent.putExtra("imgURL", uri.toString())
                intent.putExtra("userIdx", userIdx)
                startActivity(intent)
            }
        }
        /******************************************************************************************/

        // 권한 확인 및 실행
        cameraPermission()
        galleryPermission()

        // 하단 바 관련 메소드 (홈화면/검색/즐겨찾기/마이페이지)
        bottomBar()
    }

    // 전체 옷 조회
    fun getAllCloth() {
        retro.getAllCloth(userIdx).enqueue(object: Callback<AllClothResult>{
            override fun onResponse(call: Call<AllClothResult>, response: Response<AllClothResult>) {

                ////// 그리드 뷰로 화면 출력 //////
                val listManager = GridLayoutManager(this@ClosetActivity,2)
                var listAdapter = ListAdapterGrid(this@ClosetActivity, userIdx, response.body()!!.result)

                var recyclerList = binding.gridView.apply {
                    setHasFixedSize(true)
                    layoutManager = listManager
                    adapter = listAdapter
                }
                ////// 그리드 뷰로 화면 출력 //////

                Log.d("MYTAG", "홈 화면 : "+response.body()?.isSuccess.toString())
            }

            override fun onFailure(call: Call<AllClothResult>, t: Throwable) {
                Log.d("MYTAG",t.message.toString())
                Log.d("MYTAG","FAIL")
            }
        })
    }

    fun bottomBar() {
        // 홈 버튼
        binding.buttonHome.setOnClickListener {

            Toast.makeText(this,"이미 홈 화면입니다!!",Toast.LENGTH_SHORT).show()
//            getAllCloth()
//            Toast.makeText(this,"옷장 최신화 중!!",Toast.LENGTH_SHORT).show()
        }

        // 마이페이지 버튼
        binding.buttonMypage.setOnClickListener() {
            val intent = Intent(this, MypageActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        // 즐겨찾기 버튼
        binding.buttonBookmark.setOnClickListener() {
            val intent = Intent(this, BookmarkActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }

        // 검색 버튼
        binding.buttonSearch.setOnClickListener() {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("userIdx", userIdx)
            startActivity(intent)
        }
    }

    // 액티비티 화면에 출력
    fun setViews() {
        binding.buttonCamera.setOnClickListener {
            cameraPermission.launch(Manifest.permission.CAMERA)
        }
        binding.buttonGallery.setOnClickListener {
            galleryLauncher()
        }

        getAllCloth()
    }

    // 2번 뒤로가기 시 종료 기능
    override fun onBackPressed() {
        if(System.currentTimeMillis() - waitTime >=1500 ) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }

    // 권한 관련
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

                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            , PERMISSION_REQUEST_CODE
                        )
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

    // dialog 출력
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


    fun cameraPermission() {
        cameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if(isGranted) {
                cameraLauncher()
            } else {
                Toast.makeText(baseContext, "카메라 권한을 승인해야 카메라를 사용할 수 있습니다.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun galleryPermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED) {
            Log.d("MYTAG", "권한 취득 완료")
            setViews()
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE)
            Log.d("MYTAG", "권한 취득 시도")
        }
    }

    fun cameraLauncher() {
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

        openCamera.launch(photoUri)
    }

    fun galleryLauncher() {
        openGallery.launch("image/*")
    }
}
