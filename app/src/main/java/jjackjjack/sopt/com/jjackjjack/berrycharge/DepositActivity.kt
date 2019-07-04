package jjackjjack.sopt.com.jjackjjack.berrycharge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_berry_deposit.*


class DepositActivity : AppCompatActivity(){
    var activeView = null //이거 용도 뭐얌?ㅅ?

    var clickTest = arrayOf(0, 0, 0, 0, 0)

    lateinit var ivList: Array<ImageView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berry_deposit)

        ivList = arrayOf(
            deposit_img_1, deposit_img_2, deposit_img_3, deposit_img_4, deposit_img_5
        )

        InitialUI()

    }


    private fun InitialUI(){
      for(i in 0 until ivList.size){
          ivList[i].setOnClickListener {
              clickTest[i] = 1 - clickTest[i]
              for(j in 0 until ivList.size) {
                  ivList[j].isSelected = false
              }
              ivList[i].isSelected=true

          }
      }
        //이게 되긴 되는데...터치해보니 버튼 크기가 작아서 잘 클릭안됨. 다른 곳을 눌러도
        //터치가 되는건지 확인 필요

        btn_berry_deposit_charge.setOnClickListener {
            if( clickTest.sum()>0){
                //계좌뜨는 다이얼로그 창
            }
        }

    }


//        deposit_img_1.setOnClickListener {
//            if(deposit_img_1 != null){
//                deposit_img_1.isSelected = true
//                deposit_img_2.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_2.setOnClickListener {
//            if(deposit_img_2 != null){
//                deposit_img_2.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_3.setOnClickListener {
//            if(deposit_img_3 != null){
//                deposit_img_3.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_2.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_4.setOnClickListener {
//            if(deposit_img_4 != null){
//                deposit_img_4.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_2.isSelected = false
//                deposit_img_5.isSelected = false
//            }
//        }
//        deposit_img_5.setOnClickListener {
//            if(deposit_img_5 != null){
//                deposit_img_5.isSelected = true
//                deposit_img_1.isSelected = false
//                deposit_img_3.isSelected = false
//                deposit_img_4.isSelected = false
//                deposit_img_2.isSelected = false
//            }
//        }
//    }
//    private fun configureState(){
//        if(deposit_img_1.isSelected) {
//            deposit_img_2.isSelected = false
//            deposit_img_3.isSelected = false
//            deposit_img_4.isSelected = false
//            deposit_img_5.isSelected = false
//        }

}




