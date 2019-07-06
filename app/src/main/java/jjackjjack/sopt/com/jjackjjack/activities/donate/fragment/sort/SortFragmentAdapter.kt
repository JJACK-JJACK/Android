package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.sort

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.fragment_emergency_category.view.*
import kotlin.properties.Delegates

class SortFragmentAdapter(val fragmentManager: FragmentManager, val SortTab: TabLayout){
    var curFragmentId : Int by Delegates.notNull()
    var curTabId : Int by Delegates.notNull()

    private val settext = arrayOf(
        "최신순", "기부율 높은순", "기부율 낮은순"
    )
    //drawable 로 대체할 계획

    private val fragments = arrayOf(
        RecentFragment(), PopularFragment(), UnpopularFragment()
    )

    init{
        curFragmentId = Constants.FRAGMENT_RECENT
        curTabId = Constants.TAB_RECENT

        val fragment = fragments[curFragmentId]
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.donate_sort_container, fragment)
        fragmentTransaction.commit()
        SortTab.getTabAt(curTabId)?.setText(settext[curTabId])
    }

    fun setTab(what: Int){
        if(what == -1) return
        curTabId = what
        SortTab.getTabAt(curTabId)?.select()
    }

    fun setFragment(what: Int) = setFragment(what, null)
    fun setFragment(what: Int, bundle: Bundle?){
        val curTab = when(what){
            Constants.FRAGMENT_RECENT->
                Constants.TAB_RECENT
            Constants.FRAGMENT_POPULAR->
                Constants.TAB_POPULAR
            Constants.FRAGMENT_UNPOPULAR->
                Constants.TAB_UNPOPULAR
            else->
                -1
        }

        setTab(curTab)

        curFragmentId = what
        Log.d("Sort_curFragmentId", curFragmentId.toString())

        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragments[curFragmentId]

        fragmentTransaction.replace(R.id.donate_sort_container, fragment)

        fragmentTransaction.commit()
    }

}