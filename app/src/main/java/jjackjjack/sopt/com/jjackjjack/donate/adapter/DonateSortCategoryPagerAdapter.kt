package jjackjjack.sopt.com.jjackjjack.donate.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jjackjjack.sopt.com.jjackjjack.donate.fragment.PopularFragment
import jjackjjack.sopt.com.jjackjjack.donate.fragment.RecentFragment
import jjackjjack.sopt.com.jjackjjack.donate.fragment.UnpopularFragment

class DonateSortCategoryPagerAdapter : FragmentPagerAdapter {

    constructor(fragmentPagerAdapter: FragmentManager) : super(fragmentPagerAdapter){}

//    companion object { //싱글톤 design pattern ( 기존에 생성된 객체 재사용, 그때그때마다 새롭게 생성 방지 )
//
//        private var popularFragment: PopularFragment? = null
//        private var unpopularFragment: UnpopularFragment? = null
//        private var recentFragment: RecentFragment? = null
//
//        @Synchronized
//        fun getPopularFragment() : PopularFragment{
//            if(popularFragment == null) popularFragment = PopularFragment()
//            return popularFragment!!
//        }
//
//        @Synchronized
//        fun getUnpopularFragment() : UnpopularFragment{
//            if(unpopularFragment == null) unpopularFragment = UnpopularFragment()
//            return unpopularFragment!!
//        }
//        @Synchronized
//        fun getRecentFragment() : RecentFragment{
//            if(recentFragment == null) recentFragment = RecentFragment()
//            return recentFragment!!
//        }
//
//    }
//    override fun getItem(p0: Int): Fragment? {
//        return when(p0){ //몇번째 인덱스냐에 따라서 다른 fragment 반환
//            0-> getRecentFragment()
//            1-> getPopularFragment()
//            2-> getUnpopularFragment()
//            else -> null
//        }
//    }

    override fun getItem(p0: Int): Fragment? {
        return when(p0){ //몇번째 인덱스냐에 따라서 다른 fragment 반환
            0-> RecentFragment()
            1-> PopularFragment()
            2-> UnpopularFragment()
            else -> null
        }
    }
    override fun getCount(): Int {
        return 3
    }

}