package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_modify.*
import android.app.Activity
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController.getUserEmail
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController.getUserNickname
import jjackjjack.sopt.com.jjackjjack.db.SharedPreferenceController.setUserImg
import jjackjjack.sopt.com.jjackjjack.network.ApplicationController
import jjackjjack.sopt.com.jjackjjack.network.NetworkService
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostImageResponse
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostProfileModifyResponse
import jjackjjack.sopt.com.jjackjjack.utillity.ColorToast
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_LIST_SUCCESS
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_PW_FAIL
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStream

class MyPageModifyActivity : AppCompatActivity() {
    val MY_READ_STORAGE_REQUEST_CODE = 1
    val REQUEST_CODE_SELECT_IMAGE: Int = 1004

    //private var mImage: MultipartBody.Part? = null
    private var mImageURL: String? = null

    var imageURI = ""
    //var real_URI = ""

    private var receiveURL: String? = ""

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_modify)

        InitialUI()
    }

    override fun onResume() {
        super.onResume()
        curr_nickname.text = getUserNickname(this)
        curr_nickname2.text = getUserNickname(this)
        tv_mypage_modify_email.text = getUserEmail(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    //postProfileResponse()

                    val selectedImageUri: Uri = data.data
                    mImageURL = getRealPathFromURI(selectedImageUri)
                    Log.d("eeeee1", mImageURL)

                    Glide.with(this).load(selectedImageUri)
                        .apply(RequestOptions.circleCropTransform())?.into(imageView_round)

                    //      Glide.with(this@MyPageModifyActivity).load(selectedImageUri).thumbnail(0.1f).into(imageView_round)
                    postaImageRegisterResponse()
                    var sendURL = SharedPreferenceController.getUserImg(this@MyPageModifyActivity)
                    Log.d("eeeee!!", sendURL.toString()+"오잉오잉")
                    if(!receiveURL.isNullOrEmpty()){
                        postProfileModifyResponse()
                    }

                }
            }
        }
    }


    private fun InitialUI() {
        btn_modify_nickname.setOnClickListener {
            val intent = Intent(this, MyPageNicknameModifyActivity::class.java)
            intent.putExtra("nickname", curr_nickname.text)
            startActivityForResult(intent, 3000)
        }

        btn_back.setOnClickListener {
            finish()
        }
        btn_profile_img_change.setOnClickListener {
            //chooseImage()
            requestReadExternalStoragePermission()
        }
        curr_nickname.text = getUserNickname(this)
        curr_nickname2.text = getUserNickname(this)
        Log.d("email", getUserEmail(this))
        tv_mypage_modify_email.text = getUserEmail(this)
    }




    private fun postaImageRegisterResponse(){
        val file: File = File(mImageURL)
        val requestFile: RequestBody = RequestBody.create(MediaType.parse("application/json"), file)
        val data: MultipartBody.Part = MultipartBody.Part.createFormData("image", file.name, requestFile)
        val postImageRegisterResponse = networkService.postImageRegisterResponse(SharedPreferenceController.getAuthorization(this), data)
        Log.d("eeeee2", "보냈음")
        postImageRegisterResponse.enqueue(object: Callback<PostImageResponse>{
            override fun onFailure(call: Call<PostImageResponse>, t: Throwable) {
                ColorToast(this@MyPageModifyActivity, "잠시 후 다시 접속해주세요")
            }

            override fun onResponse(call: Call<PostImageResponse>, response: Response<PostImageResponse>) {
                if(response.isSuccessful){
                    //ColorToast(this@MyPageModifyActivity, "등록 성공")
                    Log.d("eeeee2", response.body()!!.data.toString()+"아 왜 안떠 설마 null?")
                    response.body()!!.data?.let{
                        receiveURL = response.body()!!.data
                        SharedPreferenceController.setUserImg(this@MyPageModifyActivity, response.body()!!.data)
                        Log.d("eeeeeeeeeeeeee", SharedPreferenceController.getUserImg(this@MyPageModifyActivity)+"???")
                        Log.d("eeeee2", response.body()!!.data.toString()+"아 왜 안떠")
                        Log.d("eeee2", receiveURL+"오잉")
                    }
                }
            }
        })
    }

    private fun postProfileModifyResponse(){
        val input_profile : String = receiveURL.toString()

        if(!input_profile.isNullOrEmpty()){
            var jsonObject = JSONObject()
            jsonObject.put("profile", input_profile)

            val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

            val postProfileModifyResponse =
                networkService.postProfileModifyResponse("application/json", SharedPreferenceController.getAuthorization(this),gsonObject)

            postProfileModifyResponse.enqueue(object : Callback<PostProfileModifyResponse>{
                override fun onFailure(call: Call<PostProfileModifyResponse>, t: Throwable) {
                    ColorToast(this@MyPageModifyActivity, "잠시 후 다시 접속해주세요")
                }

                override fun onResponse(
                    call: Call<PostProfileModifyResponse>,
                    response: Response<PostProfileModifyResponse>
                ) {
                    if(response.isSuccessful){
                        ColorToast(this@MyPageModifyActivity, "등록 성공하셨습니다")
                    }else{
                        ColorToast(this@MyPageModifyActivity, response.body()!!.message)
                    }
                }
            })
        }
    }

    private fun requestReadExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {

            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_READ_STORAGE_REQUEST_CODE
                )
            }
        } else {
            showAlbum()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == MY_READ_STORAGE_REQUEST_CODE) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showAlbum()
            } else {
                finish()
            }
        }
    }

    private fun showAlbum() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE)
    }



    fun getRealPathFromURI(content: Uri): String {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val loader: CursorLoader = CursorLoader(this, content, proj, null, null, null)
        val cursor: Cursor? = loader.loadInBackground()
        val column_idx = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor!!.moveToFirst()
        val result = cursor.getString(column_idx)
        cursor.close()
        return result
    }


}