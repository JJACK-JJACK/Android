package jjackjjack.sopt.com.jjackjjack.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donate.DonateActivity
import kotlinx.android.synthetic.main.fragment_main_activity_image_slider.*
import org.jetbrains.anko.support.v4.startActivity


class FragmentMainActivityImageSlider : Fragment() {

    var category_num : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_activity_image_slider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        fragment_main_image_slider.setOnClickListener {
            startActivity<DonateActivity>("fragment" to this.category_num)
        }

    }

    fun setCategoryNum(category_num : Int){
        this.category_num = category_num
    }


}