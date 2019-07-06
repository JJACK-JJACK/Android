package jjackjjack.sopt.com.jjackjjack.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.R
import kotlinx.android.synthetic.main.fragment_main_activity_image_slider.*


class FragmentMainActivityImageSlider : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_activity_image_slider, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}