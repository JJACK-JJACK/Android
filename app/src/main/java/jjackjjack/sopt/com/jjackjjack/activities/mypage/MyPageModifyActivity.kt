package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_modify.*
import android.R.attr.data
import android.app.Activity
import android.content.ContentUris
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.CursorLoader
import android.util.Log
import android.webkit.URLUtil
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
import jjackjjack.sopt.com.jjackjjack.network.response.post.PostProfileResponse
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_LIST_SUCCESS
import jjackjjack.sopt.com.jjackjjack.utillity.Secret.Companion.NETWORK_PW_FAIL
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*
import kotlinx.android.synthetic.main.content_activity_mypage.*
import kotlinx.android.synthetic.main.nav_drawer.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import org.jetbrains.annotations.Nullable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.util.jar.Manifest

class MyPageModifyActivity : AppCompatActivity() {
    val MY_READ_STORAGE_REQUEST_CODE = 1
    val REQUEST_CODE_SELECT_IMAGE: Int = 1004
    private var mImage: MultipartBody.Part? = null
    var imageURI = ""
    var real_URI = ""

    val networkService: NetworkService by lazy {
        ApplicationController.instance.networkService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_modify)

        InitialUI()
    }

    private fun InitialUI(){
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
        tv_mypage_email.text = getUserEmail(this)

    }

    private fun postProfileResponse() {
        var token: String = SharedPreferenceController.getAuthorization(this)

        var jsonObject = JSONObject()

        val file: File = File(imageURI)
        val requestfile: RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val data: MultipartBody.Part = MultipartBody.Part.createFormData("photo", file.name, requestfile)

//        var photoUri : Uri = Uri.parse(data.toString())
//        var filePath : String = photoUri.getPath()
//        var c : Cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, "_data ='"+filePath+"'",null,null)
//        c.moveToNext()
//
//        var id :Int = c.getInt(0)
//
//        var uri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id.toLong())
//
        Log.d("asdfsdfad", imageURI.toString())

        real_URI = "https:/" + imageURI
        jsonObject.put("profile", real_URI)

        val gsonObject = JsonParser().parse(jsonObject.toString()) as JsonObject

        val postProfileResponse =
            networkService.postProfileResponse("application/json", token, gsonObject)

        postProfileResponse.enqueue(object : Callback<PostProfileResponse> {

            override fun onFailure(call: Call<PostProfileResponse>, t: Throwable) {
                Log.d("hi1", "hi1")
                Log.d("write fail", t.toString())
            }

            override fun onResponse(call: Call<PostProfileResponse>, response: Response<PostProfileResponse>) {
                if (response.isSuccessful) {
                    Log.d("h2", "h2")
                    if (response.body()!!.status == NETWORK_LIST_SUCCESS) {
                        Log.d("123", "123")
                        toast("success")

                        setUserImg(this@MyPageModifyActivity, data.toString())
                        Log.d("url", imageURI)
                    }
                } else if (response.body()!!.status == NETWORK_PW_FAIL) {
                    Log.d("h3", "h3")
                    toast("fail")
                }
            }
        })
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
//                data?.let{
//                    var selectedPictureUri = it.data
//                    val options = BitmapFactory.Options()
//                    val inputStream: InputStream = contentResolver.openInputStream(selectedPictureUri)
//                    val bitmap = BitmapFactory.decodeStream(inputStream, null, options)
//                    val byteArrayOutputStream = ByteArrayOutputStream()
//                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream)
//                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), byteArrayOutputStream.toByteArray())
//                    mImage = MultipartBody.Part.createFormData("photo", File(selectedPictureUri.toString()).name, photoBody)
//                    Glide.with(this@MyPageModifyActivity).load(selectedPictureUri).thumbnail(0.1f).into(imageView_round)
//                }
                if (data != null) {
                    postProfileResponse()
                    val selectedImageUri: Uri = data.data



                    imageURI = getRealPathFromURI(selectedImageUri)
                    Log.d("kim", imageURI)

                    if(URLUtil.isValidUrl(SharedPreferenceController.getUserImg(this))){
                        Glide.with(this).load(SharedPreferenceController.getUserImg(this))
                            .apply(RequestOptions.circleCropTransform())?.into(imageView_round)
                    } //이미지 DB에서 가져오기 나중에 없을때 default 이미지 뜨게 처리해야함

                    //Glide.with(this@MyPageModifyActivity).load(selectedImageUri).thumbnail(0.1f).into(imageView_round)
                }
            }
        }
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
