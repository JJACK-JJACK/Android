package jjackjjack.sopt.com.jjackjjack

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jjackjjack.sopt.com.jjackjjack.fragment.donate.detail.DonateStoryFragment
import jjackjjack.sopt.com.jjackjjack.fragment.donate.detail.UsePlanFragment

class DonateDetailedPagerAdapter : FragmentPagerAdapter {

    constructor(fragmentPagerAdapter: FragmentManager) : super(fragmentPagerAdapter){
    }

    companion object { //싱글톤 design pattern ( 기존에 생성된 객체 재사용, 그때그때마다 새롭게 생성 방지 )

        private var donateStoryFragment: DonateStoryFragment? = null
        private var usePlanFragment: UsePlanFragment? = null

        @Synchronized
        fun getDonateStoryFragment() : DonateStoryFragment{
            if(donateStoryFragment == null) donateStoryFragment = DonateStoryFragment()
            return donateStoryFragment!!
        }

        @Synchronized
        fun getUsePlanFragment() : UsePlanFragment{
            if(usePlanFragment == null) usePlanFragment = UsePlanFragment()
            return usePlanFragment!!
        }
    }
    override fun getItem(p0: Int): Fragment? {
        return when(p0){ //몇번째 인덱스냐에 따라서 다른 fragment 반환
            0-> getDonateStoryFragment()
            1-> getUsePlanFragment()
            else -> null
        }
    }
    override fun getCount(): Int {
        return 2
    }
}