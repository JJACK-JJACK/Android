package jjackjjack.sopt.com.jjackjjack.activities.berrycharge

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.LinearLayout
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.activity_berry_deposit.*


class DepositActivity : AppCompatActivity(){
    var clickTest = arrayOf(0, 0, 0, 0, 0)

    lateinit var ivList: Array<ImageView>

    lateinit var lyList: Array<LinearLayout>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_berry_deposit)


        InitialUI()

    }


    private fun InitialUI(){

        btn_back.setOnClickListener {
            finish()
        }

        lyList = arrayOf(
            ly_deposit_radio10, ly_deposit_radio50, ly_deposit_radio100, ly_deposit_radio300, ly_deposit_radio500
        )
        ivList = arrayOf(
            deposit_img_1, deposit_img_2, deposit_img_3, deposit_img_4, deposit_img_5
        )


      for(i in 0 until lyList.size){
          lyList[i].setOnClickListener {
              clickTest[i] = 1 - clickTest[i]
              for(j in 0 until ivList.size) {
                  ivList[j].isSelected = false
              }
              ivList[i].isSelected=true

          }
      }
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




