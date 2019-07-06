package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.detail

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentManager
import android.util.Log
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlin.properties.Delegates

class DetailFragmentAdapter(val fragmentManager: FragmentManager, val detailTab: TabLayout){

    var curFragmentId : Int by Delegates.notNull()
    var curTabId : Int by Delegates.notNull()

    private val settext = arrayOf(
        "기부스토리", "사용계획"
    )
    private val fragments = arrayOf(
        DonateStoryFragment(), UsePlanFragment()
    )

    init{
        curFragmentId = Constants.FRAGMENT_DONATE_STORY
        curTabId = Constants.TAB_DONATE_STORY

        val fragment = fragments[curFragmentId]
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.donate_detailed_container, fragment)
        fragmentTransaction.commit()
        detailTab.getTabAt(curTabId)?.setText(settext[curTabId])
    }

    fun setTab(what: Int){
        if(what == -1) return
        curTabId = what
        detailTab.getTabAt(curTabId)?.select()
    }

    fun setFragment(what: Int) = setFragment(what, null)
    fun setFragment(what: Int, bundle: Bundle?){
        val curTab = when(what){
            Constants.FRAGMENT_DONATE_STORY->
                Constants.TAB_DONATE_STORY
            Constants.FRAGMENT_USE_PLAN->
                Constants.TAB_USE_PLAN
            else->
                -1
        }

        setTab(curTab)

        curFragmentId = what
        Log.d("curFragmentId", curFragmentId.toString())

        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragments[curFragmentId]

        fragmentTransaction.replace(R.id.donate_detailed_container, fragment)

        fragmentTransaction.commit()
    }
}