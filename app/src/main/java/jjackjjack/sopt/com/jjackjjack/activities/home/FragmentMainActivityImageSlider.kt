package jjackjjack.sopt.com.jjackjjack.activities.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import kotlinx.android.synthetic.main.content_activity_main.*
import kotlinx.android.synthetic.main.fragment_main_activity_image_slider.*
import org.jetbrains.anko.image
import org.jetbrains.anko.support.v4.startActivity


class FragmentMainActivityImageSlider : Fragment() {

   var category_num: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_activity_image_slider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_main_image_slider.setOnClickListener {
            startActivity<DonateActivity>("fragment" to this.category_num)
        }
        Log.d("category", category_num.toString())
        when (category_num) {
            0 -> {
                fragment_main_image_slider.setBackgroundResource(R.drawable.img_cildren)
            }
            1 -> {
                fragment_main_image_slider.setBackgroundResource(R.drawable.img_older)
            }
            2 -> {
                fragment_main_image_slider.setBackgroundResource(R.drawable.img_animal)
            }
            3 -> {
                fragment_main_image_slider.setBackgroundResource(R.drawable.img_disabled)
            }
            4 -> {
                fragment_main_image_slider.setBackgroundResource(R.drawable.img_environment)
            }
            5 -> {
                fragment_main_image_slider.setBackgroundResource(R.drawable.img_emergency)
            }
        }
    }

    fun setCategoryNum(category_num: Int) {
        this.category_num = 0
        this.category_num = category_num
    }

}