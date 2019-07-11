package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_modify.*
import android.R.attr.data
import android.app.Activity
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.CursorLoader
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.annotations.Nullable
import java.io.IOException
import java.util.jar.Manifest


class MyPageModifyActivity : AppCompatActivity() {
    val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_page_modify)

        btn_modify_nickname.setOnClickListener {
            val intent = Intent(this, MyPageNicknameModifyActivity::class.java)
            intent.putExtra("nickname", curr_nickname.text)
            startActivityForResult(intent, 3000)
        }

        btn_back.setOnClickListener {
            finish()
        }
        btn_profile_img_change.setOnClickListener{
            chooseImage()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === RESULT_OK) {
            when (requestCode) {
                3000 -> {
                    curr_nickname.text = data?.getStringExtra("nickname_changed")
                    curr_nickname2.text = data?.getStringExtra("nickname_changed")
                }
            }
        }
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null){
            val uri = data.data

            try{

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                imageView_round.setImageBitmap(bitmap)
            }catch(e: IOException){
                e.printStackTrace()
            }
        }
    }

    private fun chooseImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

//    fun getRealPathFromURI(cotent:Uri) : String{
//        val proj = arrayOf(MediaStore.Images.Media.DATA)
//        val loader : CursorLoader = CursorLoader(this, content, proj, null,null,null)
//
//    }
}
