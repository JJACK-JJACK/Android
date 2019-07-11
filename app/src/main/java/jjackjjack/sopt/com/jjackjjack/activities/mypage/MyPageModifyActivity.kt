package jjackjjack.sopt.com.jjackjjack.activities.mypage

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_my_page_modify.*
import android.R.attr.data
import android.content.pm.PackageManager
import android.os.Build
import kotlinx.android.synthetic.main.activity_mypage_berryhistory.*
import java.util.jar.Manifest


class MyPageModifyActivity : AppCompatActivity() {

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
//        btn_profile_img_change.setOnClickListener{
//            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
//                if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
//                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
//                     requestPermissions(permissions,PERMISSION_CODE)
//                }
//                else{
//                    pickImageFromGallery()
//                }
//            }
//            else{
//                pickImageFromGallery()
//            }
//        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === RESULT_OK) {
            when (requestCode) {
                3000 -> {
                    curr_nickname.setText(data?.getStringExtra("nickname_changed"))
                    curr_nickname2.setText(data?.getStringExtra("nickname_changed"))
                }
            }
        }
    }

//    private fun pickImageFromGallery(){
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//       startActivityForResult(intent. IMAGE_PICK_CODE)
//    }
}
