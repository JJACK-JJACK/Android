package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import jjackjjack.sopt.com.jjackjjack.activities.donate.adapter.DonateSortCategoryPagerAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.sort.SortFragmentAdapter
import jjackjjack.sopt.com.jjackjjack.utillity.Constants
import kotlinx.android.synthetic.main.fragment_environment_category.*
import kotlin.properties.Delegates

class EnvironmentFragment : Fragment() {

    var sortfragmentAdapter: SortFragmentAdapter by Delegates.notNull()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_environment_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        //var sub_adapter = DonateSortCategoryPagerAdapter(activity!!.supportFragmentManager)
//        var sub_adapter =
//            DonateSortCategoryPagerAdapter(childFragmentManager)
//        donate_sort_pager.adapter = sub_adapter
//
//        donate_sort_tab.setupWithViewPager(donate_sort_pager)
//
//        val navDonateListSort: View =
//            (activity!!.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
//                .inflate(R.layout.nav_donatelist_sort, null, false)
//
//        donate_sort_tab.getTabAt(0)!!.customView =
//            navDonateListSort.findViewById(R.id.nav_donatelist_sort_recent) as RelativeLayout
//        donate_sort_tab.getTabAt(1)!!.customView =
//            navDonateListSort.findViewById(R.id.nav_donatelist_sort_popular) as RelativeLayout
//        donate_sort_tab.getTabAt(2)!!.customView =
//            navDonateListSort.findViewById(R.id.nav_donatelist_sort_unpopular) as RelativeLayout

        initailUI()
    }

    private fun initailUI(){
        donate_sort_tab.addTab(donate_sort_tab.newTab().setText("최신순"))
        donate_sort_tab.addTab(donate_sort_tab.newTab().setText("기부율 높은순"))
        donate_sort_tab.addTab(donate_sort_tab.newTab().setText("기부율 낮은순"))

        donate_sort_tab.getTabAt(0)?.select()

        sortfragmentAdapter = SortFragmentAdapter(childFragmentManager, donate_sort_tab)

        donate_sort_tab.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                //이건 프래그먼트 겹칩 현상때문에 넣음
                var fm : FragmentManager = childFragmentManager
                fm.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    Constants.TAB_RECENT->{
                        sortfragmentAdapter.setFragment(Constants.FRAGMENT_RECENT)
                    }
                    Constants.TAB_POPULAR->{
                        sortfragmentAdapter.setFragment(Constants.FRAGMENT_POPULAR)
                    }
                    Constants.TAB_UNPOPULAR->{
                        sortfragmentAdapter.setFragment(Constants.FRAGMENT_UNPOPULAR)
                    }
                }
            }
        })

    }

}