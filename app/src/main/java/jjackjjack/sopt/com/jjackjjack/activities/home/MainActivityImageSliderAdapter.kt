package jjackjjack.sopt.com.jjackjjack.activities.home

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jjackjjack.sopt.com.jjackjjack.activities.home.FragmentMainActivityImageSlider
import java.util.ArrayList



class MainActivityImageSliderAdapter : FragmentPagerAdapter {

    var mContext : Context

    constructor(context : Context, fragmentPagerAdapter: FragmentManager) : super(fragmentPagerAdapter){
        this.mContext = context
    }

    var imageFragmentList = ArrayList<FragmentMainActivityImageSlider>()

    override fun getItem(position: Int): Fragment? {
        return imageFragmentList.get(position)
    }

    override fun getCount(): Int {
        return imageFragmentList.size
    }

    fun addImage(fragment: FragmentMainActivityImageSlider, category: Int) {
        imageFragmentList.add(fragment)
    }
}