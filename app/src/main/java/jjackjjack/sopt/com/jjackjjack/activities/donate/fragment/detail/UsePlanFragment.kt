package jjackjjack.sopt.com.jjackjjack.activities.donate.fragment.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jjackjjack.sopt.com.jjackjjack.list.DonateUsePlanRecyclerViewAdapter
import jjackjjack.sopt.com.jjackjjack.R
import jjackjjack.sopt.com.jjackjjack.model.DonateUsePlan
import kotlinx.android.synthetic.main.fragment_use_plan.*

class UsePlanFragment : Fragment(){

    lateinit var donateUsePlanRecyclerViewAdapter: DonateUsePlanRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_use_plan, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initialUI()
    }

    private fun initialUI(){
        var list: ArrayList<DonateUsePlan> = ArrayList()

        list.add(
            DonateUsePlan(
                "1", "입양지원 활동 및 입양진행", "30.000"
            )
        )
        list.add(
            DonateUsePlan(
                "2", "위급한 유기견 대상 영양제 지원, 예방접종 진행", "19.000"
            )
        )
        list.add(
            DonateUsePlan(
                "3", "약품 및 물품비용", "23.000"
            )
        )
        list.add(
            DonateUsePlan(
                "4", "족발 대자 3개", "120.000"
            )
        )
        donateUsePlanRecyclerViewAdapter =
            DonateUsePlanRecyclerViewAdapter(context!!, list)
        rv_donate_use_plan_container.adapter = donateUsePlanRecyclerViewAdapter
        rv_donate_use_plan_container.layoutManager = LinearLayoutManager(context!!)
    }

}