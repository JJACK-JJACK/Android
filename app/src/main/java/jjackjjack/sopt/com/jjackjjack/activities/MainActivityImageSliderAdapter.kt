package jjackjjack.sopt.com.jjackjjack.activities

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import java.util.ArrayList

class MainActivityImageSliderAdapter : FragmentPagerAdapter {

    constructor(fragmentPagerAdapter: FragmentManager) : super(fragmentPagerAdapter){}

    var imageFragmentList = ArrayList<Fragment>()

    override fun getItem(position: Int): Fragment {
        return imageFragmentList.get(position)
    }

    override fun getCount(): Int {
        return imageFragmentList.size
    }

    fun addImage(fragment: Fragment) {
        imageFragmentList.add(fragment)
    }
}